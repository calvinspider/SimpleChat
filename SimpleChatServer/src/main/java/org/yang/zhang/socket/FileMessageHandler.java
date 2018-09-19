package org.yang.zhang.socket;

import java.io.File;

import org.yang.zhang.module.FileMessage;
import org.yang.zhang.utils.ChannelManager;
import org.yang.zhang.utils.FileUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 19 15:16
 */

public class FileMessageHandler extends SimpleChannelInboundHandler<FileMessage> {

    private static String fileDir = "D:\\simpleChatFiles";

    private static String userIconDir = "D:\\simpleChatFiles\\usericon";

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FileMessage ef) throws Exception {

        if(0==ef.getType()){//上传头像
            FileUtils.saveFile(ef,userIconDir);
        }else if(1==ef.getType()){//上传文件
            File file=new File(fileDir);
            if(!file.exists()){
                file.mkdir();
            }
            FileUtils.saveFile(ef,fileDir);
            /*ChannelHandlerContext targetChannel=ChannelManager.getChannel(String.valueOf(ef.getTargetUserId()));
            targetChannel.writeAndFlush(ef);*/
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
