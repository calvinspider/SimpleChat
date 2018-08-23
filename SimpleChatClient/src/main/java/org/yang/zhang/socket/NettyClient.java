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
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import org.yang.zhang.module.FileUploadFile;

/**
 *
 * Title: NettyClient
 * Description: Netty客户端
 * Version:1.0.0
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyClient{

    private  static String host = "127.0.0.1";
    private  static int port = 6789;
    private  static Channel channel;
    private static int byteRead;
    private static volatile int start = 0;
    private static volatile int lastLength = 0;
    private static RandomAccessFile randomAccessFile;

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

    public static void sendFile(File file, String fileName){
        FileUploadFile uploadFile = new FileUploadFile();
        uploadFile.setFile(file);
        uploadFile.setFile_md5(fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length()));
        uploadFile.setStarPos(0);// 文件开始位置
        if (uploadFile.getFile().exists()) {
            if (!uploadFile.getFile().isFile()) {
                System.out.println("Not a file :" + uploadFile.getFile());
                return;
            }
        }
        try {
            randomAccessFile = new RandomAccessFile(uploadFile.getFile(), "r");
            randomAccessFile.seek(uploadFile.getStarPos());
            lastLength = (int) randomAccessFile.length() / 10;
            byte[] bytes = new byte[lastLength];
            if ((byteRead = randomAccessFile.read(bytes)) != -1) {
                uploadFile.setEndPos(byteRead);
                uploadFile.setBytes(bytes);
                channel.writeAndFlush(uploadFile);
            } else {
                System.out.println("文件已经读完");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
