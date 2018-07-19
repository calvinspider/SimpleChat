package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:10
 */

import java.util.Date;

import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.RecentContract;
import org.yang.zhang.repository.ChatMessageRepository;
import org.yang.zhang.repository.RecentContractRepository;
import org.yang.zhang.utils.ChannelManager;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.SpringContextUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg==null){
            return;
        }
        TypeReference type = new TypeReference<MessageInfo>(){};
        MessageInfo info=JsonUtils.fromJson((String) msg,type);
        if(info==null){
            throw new Exception("MessageInfo must not be NULL!");
        }
        Integer targetUser=info.getTargetclientid();
        Integer sourceUser=info.getSourceclientid();
        if(info.getMsgcontent().equals("REGEIST")){
            //注册channel
            ChannelManager.registerChannel(String.valueOf(sourceUser),ctx);
        }else if(info.getMsgcontent().equals("loginOut")){
            //注销channel
            ChannelManager.unregisterChannel(String.valueOf(sourceUser));
        }else{
            //转发消息
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
            ChatMessageRepository chatMessageRepository=SpringContextUtils.getBean("chatMessageRepository");
            RecentContractRepository recentContractRepository=SpringContextUtils.getBean("recentContractRepository");
            RecentContract recentContract=recentContractRepository.findByUserIdAndContractUserId(Integer.valueOf(info.getSourceclientid()),Integer.valueOf(info.getTargetclientid()));
            if(recentContract!=null){
                recentContract.setLastMessage(info.getMsgcontent());
                recentContract.setMessageTime(info.getTime());
                recentContractRepository.saveAndFlush(recentContract);
            }else{
                RecentContract recentContract1=new RecentContract();
                recentContract1.setMessageTime(info.getTime());
                recentContract1.setLastMessage(info.getMsgcontent());
                recentContract1.setUserId(Integer.valueOf(info.getSourceclientid()));
                recentContract1.setContractUserId(Integer.valueOf(info.getTargetclientid()));
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
