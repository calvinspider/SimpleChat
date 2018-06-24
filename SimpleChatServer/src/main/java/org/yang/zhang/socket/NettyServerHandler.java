package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:10
 */

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.yang.zhang.entity.MessageInfo;
import org.yang.zhang.utils.ChannelManager;
import org.yang.zhang.utils.JsonUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg==null){
            return;
        }
        TypeReference type = new TypeReference<List<MessageInfo>>(){};
        MessageInfo info=JsonUtils.fromJson((String) msg,type);
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
