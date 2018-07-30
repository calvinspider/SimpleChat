package org.yang.zhang.socket.file;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 30 18:01
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.yang.zhang.socket.NettyClientFilter;

public class FileUploadClient {

    private static int byteRead;
    private static volatile int start = 0;
    private static volatile int lastLength = 0;
    private static RandomAccessFile randomAccessFile;
    private static Channel channel;

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                    ch.pipeline().addLast(new FileUploadClientHandler());
                }
            });
            channel = b.connect(host, port).sync().channel();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void sendFile(File file,String fileName){
        FileUploadFile uploadFile = new FileUploadFile();
        uploadFile.setFile(file);
        uploadFile.setFile_md5(fileName);
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

    public void run() {
        int port = 8087;
        String host="127.0.0.1";
        try {
            new FileUploadClient().connect(port, host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
