package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:12
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;

/**
 *
 * Title: NettyClient
 * Description: Netty客户端
 * Version:1.0.0
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyClient {

    private  String host = "127.0.0.1";
    private  int port = 6789;
    private  EventLoopGroup group = new NioEventLoopGroup();
    private  Bootstrap b = new Bootstrap();
    private  Channel channel;

    public NettyClient(){
        try {
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.handler(new NettyClientFilter());
            channel=b.connect(host, port).sync().channel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        channel.writeAndFlush(message);
    }

}
