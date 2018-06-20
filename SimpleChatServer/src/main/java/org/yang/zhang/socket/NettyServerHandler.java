package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:10
 */

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import org.yang.zhang.entity.MessageInfo;
import org.yang.zhang.utils.ChannelManager;
import org.yang.zhang.utils.JsonUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        TypeReference type = new TypeReference<List<MessageInfo>>(){};
        MessageInfo info=JsonUtils.fromJson(msg,type);
        String targetUser=info.getTargetclientid();
        String sourceUser=info.getSourceclientid();
        if(targetUser==null&&info.getMsgcontent().equals("register")){
            //注册
            ChannelManager.registerChannel(info.getSourceclientid(),ctx);
        }else{
            //转发消息
            ChannelHandlerContext targetChannel=ChannelManager.getChannel(targetUser);
            targetChannel.writeAndFlush(msg);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ \n");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
        super.channelInactive(ctx);
    }
}
