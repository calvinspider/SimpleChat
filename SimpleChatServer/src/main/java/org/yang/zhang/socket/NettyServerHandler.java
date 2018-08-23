package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:10
 */

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.yang.zhang.enums.MessageType;
import org.yang.zhang.mapper.ChatRoomMapper;
import org.yang.zhang.module.FileUploadFile;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.RecentContract;
import org.yang.zhang.module.RoomChatInfo;
import org.yang.zhang.module.User;
import org.yang.zhang.repository.ChatMessageRepository;
import org.yang.zhang.repository.ChatRoomMessageRepository;
import org.yang.zhang.repository.RecentContractRepository;
import org.yang.zhang.utils.ChannelManager;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.SpringContextUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    private int byteRead;
    private volatile int start = 0;
    private String file_dir = "D:\\Documents\\SimpleChat\\SimpleChatServer\\src\\main\\resources\\static\\images\\userIcon";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg==null){
            return;
        }
        //文件传输
        if (msg instanceof FileUploadFile) {
            FileUploadFile ef = (FileUploadFile) msg;
            byte[] bytes = ef.getBytes();
            byteRead = ef.getEndPos();
            String md5 = ef.getFile_md5();//文件名
            String path = file_dir + File.separator + md5;
            File file = new File(path);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(start);
            randomAccessFile.write(bytes);
            if (byteRead <= 0) {
                randomAccessFile.close();
                ctx.close();
            }
            return;
        }
        TypeReference type = new TypeReference<MessageInfo>(){};
        MessageInfo info=JsonUtils.fromJson((String)msg,type);
        if(info==null){
            throw new Exception("MessageInfo must not be NULL!");
        }

        ChatMessageRepository chatMessageRepository=SpringContextUtils.getBean("chatMessageRepository");
        RecentContractRepository recentContractRepository=SpringContextUtils.getBean("recentContractRepository");
        ChatRoomMapper chatRoomMapper=SpringContextUtils.getBean("chatRoomMapper");
        ChatRoomMessageRepository chatRoomMessageRepository=SpringContextUtils.getBean("chatRoomMessageRepository");
        Integer targetUser=info.getTargetclientid();
        Integer sourceUser=info.getSourceclientid();
        if(info.getMsgtype()==MessageType.REGISTER){//注册channel
            ChannelManager.registerChannel(String.valueOf(sourceUser),ctx);
        }else if(MessageType.UNREGISTER==info.getMsgtype()){//注销channel
            ChannelManager.unregisterChannel(String.valueOf(sourceUser));
        }else if(MessageType.ROOM==info.getMsgtype()){//群发消息
            List<User> member=chatRoomMapper.getChatRoomUsers(targetUser);
            List<Integer> userIds=member.stream().map(item->item.getId()).collect(Collectors.toList());
            for (Integer id:userIds){
                if(id.equals(sourceUser)){
                    continue;
                }
                ChannelHandlerContext targetChannel=ChannelManager.getChannel(String.valueOf(id));
                if(targetChannel!=null){
                    targetChannel.writeAndFlush(msg+"\n");
                }
            }
            RoomChatInfo roomChatInfo=new RoomChatInfo();
            roomChatInfo.setMessage(info.getMsgcontent());
            roomChatInfo.setMessageTime(new Date());
            roomChatInfo.setRoomId(targetUser);
            roomChatInfo.setUserId(sourceUser);
            chatRoomMessageRepository.save(roomChatInfo);
        }else{//转发消息
            ChannelHandlerContext targetChannel=ChannelManager.getChannel(String.valueOf(targetUser));
            //未找到接收方的channel,将该消息记为离线消息
            if(targetChannel==null){
                info.setSendflag(0);
            }else{
                targetChannel.writeAndFlush(msg+"\n");
                info.setSendflag(1);
            }
            info.setTime(new Date());
            //本地存储消息
            RecentContract recentContract=recentContractRepository.findByUserIdAndContractUserId(Integer.valueOf(info.getSourceclientid()),Integer.valueOf(info.getTargetclientid()));
            if(recentContract!=null){
                recentContract.setLastMessage(info.getMsgcontent());
                recentContract.setMessageTime(info.getTime());
                recentContractRepository.saveAndFlush(recentContract);
            }else{
                RecentContract recentContract1=new RecentContract();
                recentContract1.setMessageTime(info.getTime());
                recentContract1.setLastMessage(info.getMsgcontent());
                recentContract1.setUserId(info.getSourceclientid());
                recentContract1.setContractUserId(info.getTargetclientid());
                recentContractRepository.save(recentContract1);
            }
            chatMessageRepository.save(info);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }
}
