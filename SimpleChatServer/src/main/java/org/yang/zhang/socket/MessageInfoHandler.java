package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:10
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.yang.zhang.enums.MessageType;
import org.yang.zhang.mapper.ChatRoomMapper;
import org.yang.zhang.module.FileMessage;
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

public class MessageInfoHandler extends SimpleChannelInboundHandler<MessageInfo> {



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageInfo info) throws Exception {

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
                    targetChannel.writeAndFlush(info);
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
                targetChannel.writeAndFlush(info);
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
