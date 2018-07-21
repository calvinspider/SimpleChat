package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:12
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * Title: NettyClient
 * Description: Netty客户端
 * Version:1.0.0
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyClient{

    private  static String host = "47.52.164.19";
    private  static int port = 6789;
    private  static Channel channel;

    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new NettyClientFilter());
            channel = b.connect(host, port).sync().channel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sendMessage(String message){
        channel.writeAndFlush(message+"\n");
    }

}
