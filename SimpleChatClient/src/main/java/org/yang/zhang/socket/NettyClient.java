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
        if (file.exists()) {
            if (!file.isFile()) {
                System.out.println("Not a file :" + file);
                return;
            }
        }
        RandomAccessFile randomAccessFile=null;
        try {
            int size=1024*50;
            Long totalByte=file.length();
            randomAccessFile = new RandomAccessFile(file, "r");
            byte[] bytes = new byte[size];
            int byteRead = randomAccessFile.read(bytes);
            totalByte=totalByte-byteRead;
            Boolean create=true;
            while (byteRead!=-1) {
                FileUploadFile uploadFile = new FileUploadFile();
                uploadFile.setBytes(bytes);
                uploadFile.setFileName(fileName);
                uploadFile.setCreate(create);
                channel.writeAndFlush(uploadFile);
                create = false;
                totalByte=totalByte-byteRead;
                if(totalByte<size){
                    bytes=new byte[byteRead];
                }
                byteRead = randomAccessFile.read(bytes);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(randomAccessFile!=null){
                try {
                    randomAccessFile.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
