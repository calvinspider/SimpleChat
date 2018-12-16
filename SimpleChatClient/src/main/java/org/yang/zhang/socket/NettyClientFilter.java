package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:12
 */

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 *
 * Title: NettyClientFilter
 * Description: Netty客户端 过滤器
 * Version:1.0.0
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyClientFilter extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        ph.addLast(new ObjectEncoder());
        ph.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
        ph.addLast(new FileMessageHandler());
        ph.addLast(new MessageHandler());
    }
}
