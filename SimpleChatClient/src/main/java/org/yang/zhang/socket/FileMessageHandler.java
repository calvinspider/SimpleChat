package org.yang.zhang.socket;

import java.io.File;

import org.yang.zhang.constants.Constant;
import org.yang.zhang.module.FileMessage;
import org.yang.zhang.utils.AnimationUtils;
import org.yang.zhang.utils.ChatViewManager;
import org.yang.zhang.utils.FileSizeUtil;
import org.yang.zhang.utils.FileUtils;
import org.yang.zhang.utils.SendFileWindowManager;
import org.yang.zhang.utils.ThreadPoolUtils;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.view.ChatView;
import org.yang.zhang.view.RightFileMessageView;
import org.yang.zhang.view.SendFileView;
import org.yang.zhang.view.SmallFileMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 19 15:08
 */

public class FileMessageHandler extends SimpleChannelInboundHandler<FileMessage> {

    private static String fileDir = "D:\\simpleChatFiles\\receiveFiles";

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FileMessage info) throws Exception {
        
        File file=new File(fileDir);
        if(!file.exists()){
            file.mkdir();
        }

        String userId=String.valueOf(info.getOriginalUserId());
        ChatView chatWindow=ChatViewManager.getStage(userId);
        if(chatWindow!=null){
            ScrollPane chatPane=chatWindow.getChatPane();
            VBox chatHistory=(VBox)chatPane.getContent();

            if(info.getCreate()){
                Platform.runLater(()->{
                    SendFileWindowManager.openStage(userId);
                    SendFileView sendFileView=SendFileWindowManager.getView(userId);
                    SmallFileMessage smallFileMessage=new SmallFileMessage(new Image(Constant.DEFAULT_FILE_ICON),info.getFileName());
                    if(sendFileView!=null){
                        VBox vBox=sendFileView.getvBox();
                        //插入文件传送框
                        vBox.getChildren().add(smallFileMessage.getRoot());
                    }
                    //提交文件传输线程
                    ThreadPoolUtils.run(()-> {
                        FileUtils.saveFile(info,fileDir);
                        smallFileMessage.getProcessbar().setProgress((info.getTotal()-info.getRemain())/info.getTotal());
                    });
                    //传输完成后将文件框添加到聊天框中
                    RightFileMessageView messageView=new RightFileMessageView(new Image(Constant.DEFAULT_FILE_ICON)
                            ,info.getFileName()
                            , "("+FileSizeUtil.getFileOrFilesSize(file,FileSizeUtil.SIZETYPE_KB)+"KB"+")"
                            ,UserUtils.getUserIcon());
                    chatHistory.getChildren().add(messageView.getRoot());
                    //聊天框下拉到底
                    Platform.runLater(()->chatPane.setVvalue(1.0));
                    AnimationUtils.slowScrollToBottom(chatPane);
                });
            }
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
