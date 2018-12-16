package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:12
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.io.RandomAccessFile;

import org.yang.zhang.module.FileMessage;
import org.yang.zhang.module.MessageInfo;

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

    public static void sendMessage(MessageInfo message){
        channel.writeAndFlush(message);
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
                FileMessage uploadFile = new FileMessage();
                uploadFile.setType(0);
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

    public static void sendFileWithProcess(Integer originalUserId,Integer targetUserId,File file, String fileName, ProgressBar progressBar){
            if (file.exists()) {
                if (!file.isFile()) {
                    System.out.println("Not a file :" + file);
                    return;
                }
            }
        RandomAccessFile randomAccessFile=null;
        try {
            int size=1024;
            Long totalByte=file.length();
            Long original=totalByte;
            randomAccessFile = new RandomAccessFile(file, "r");
            byte[] bytes = new byte[size];
            int byteRead = randomAccessFile.read(bytes);
            totalByte=totalByte-byteRead;
            Boolean create=true;
            FileMessage uploadFile = new FileMessage();
            while (byteRead!=-1) {
                uploadFile.setOriginalUserId(originalUserId);
                uploadFile.setTargetUserId(targetUserId);
                uploadFile.setType(1);
                uploadFile.setBytes(bytes);
                uploadFile.setFileName(fileName);
                uploadFile.setCreate(create);
                uploadFile.setTotal(original);
                uploadFile.setRemain(totalByte.intValue());
                channel.writeAndFlush(uploadFile);
                progressBar.setProgress((original-totalByte)/original);
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
