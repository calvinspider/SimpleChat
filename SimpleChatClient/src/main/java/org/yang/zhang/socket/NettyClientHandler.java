package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:12
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

import org.yang.zhang.constants.Constant;
import org.yang.zhang.enums.BubbleType;
import org.yang.zhang.enums.IDType;
import org.yang.zhang.fxcontroller.MainController;
import org.yang.zhang.module.FileUploadFile;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.utils.*;
import org.yang.zhang.view.ChatView;
import org.yang.zhang.view.ContractItemView;
import org.yang.zhang.view.LeftMessageBubble;
import org.yang.zhang.view.RightFileMessageView;
import org.yang.zhang.view.SendFileView;
import org.yang.zhang.view.SmallFileMessage;

import com.fasterxml.jackson.core.type.TypeReference;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 *
 * Title: NettyClientHandler
 * Description: 客户端业务逻辑实现
 * Version:1.0.0
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    private static String fileDir = "D:\\simpleChatFiles\\receiveFiles";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){

        if(msg==null||"".equals(msg)){
            return;
        }

        //文件传输
        if (msg instanceof FileUploadFile) {
            dealFileMessage(msg);
        }else{
            //文字传输
            dealTextMessage(msg);
        }
    }

    private void dealTextMessage(Object msg) {
        TypeReference type = new TypeReference<MessageInfo>(){};
        MessageInfo info=JsonUtils.fromJson((String) msg,type);
        if(info==null){
            return;
        }
        Integer userId=info.getSourceclientid();
        Integer targetId=info.getTargetclientid();
        Platform.setImplicitExit(false);
        switch (info.getMsgtype()){
            case NORMAL:
                Platform.runLater(()->{
                    ChatView chatWindow=ChatViewManager.getStage(String.valueOf(userId));
                    //聊天框未打开,头像闪动
                    if(chatWindow==null){
//                        TreeItem<ContractItemView> itemViewTreeItem= ClientCache.getContractMap().get(String.valueOf(userId));
//                        ContractItemView contractItemView=itemViewTreeItem.getValue();
//                        contractItemView.startBlink();
                    }else {
                        ChatUtils.appendBubble(chatWindow.getChatPane(),BubbleType.LEFT,info.getMsgcontent(),ImageUtiles.getUserIcon(userId),670D);
                    }
                    rightConerPop(info.getMsgcontent(),String.valueOf(userId),userId);
                });
                break;
            case ROOM:
                Platform.runLater(()->rightConerPop(info.getMsgcontent(),String.valueOf(userId),userId));
                break;
        }
    }

    private void dealFileMessage(Object msg) {

        FileUploadFile ef = (FileUploadFile) msg;

        String path=ef.getOriginalUserId()+"";
        File file=new File(fileDir+File.separator+path);
        if(!file.exists()){
            file.mkdir();
        }

        String userId=String.valueOf(ef.getOriginalUserId());
        ChatView chatWindow=ChatViewManager.getStage(userId);
        if(chatWindow!=null){
            ScrollPane chatPane=chatWindow.getChatPane();
            VBox chatHistory=(VBox)chatPane.getContent();

            if(ef.getCreate()){
                    SendFileWindowManager.openStage(userId);
                    SendFileView sendFileView=SendFileWindowManager.getView(userId);
                    SmallFileMessage smallFileMessage=new SmallFileMessage(new Image(Constant.DEFAULT_FILE_ICON),ef.getFileName());
                    if(sendFileView!=null){
                        VBox vBox=sendFileView.getvBox();
                        //插入文件传送框
                        vBox.getChildren().add(smallFileMessage.getRoot());
                    }

                    //提交文件传输线程
                    ThreadPoolUtils.run(()-> {
                        saveFile(ef,fileDir+path+ef.getFileName());

                        smallFileMessage.getProcessbar().setProgress((ef.getTotal()-ef.getRemain())/ef.getTotal());
                    });
                    //传输完成后将文件框添加到聊天框中
                    RightFileMessageView messageView=new RightFileMessageView(new Image(Constant.DEFAULT_FILE_ICON)
                            ,ef.getFileName()
                            , "("+FileSizeUtil.getFileOrFilesSize(file,FileSizeUtil.SIZETYPE_KB)+"KB"+")"
                            ,UserUtils.getUserIcon());
                    chatHistory.getChildren().add(messageView.getRoot());
                    //聊天框下拉到底
                    Platform.runLater(()->chatPane.setVvalue(1.0));
                    AnimationUtils.slowScrollToBottom(chatPane);
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("channelRead0");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    public void rightConerPop(String content,String userName,Integer userId){
        String title="来自 "+userName+" 的信息";
        TitledPane titledPane=ConerPopUtils.getConer(userId);
        if(ConerPopUtils.getConer(userId)!=null){
            titledPane.setContent(new Text(content));
        }else{
            TitledPane page=new TitledPane();
            page.setText(title);
            page.setContent(new Text(content));
            Scene scene = new Scene(page);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 200);
            stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 200);
            stage.setWidth(200);
            stage.setHeight(200);
            stage.setIconified(false);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    ConerPopUtils.unregisterConer(userId);
                }
            });
            ConerPopUtils.registerConer(userId,page);
        }
    }

    private void saveFile(FileUploadFile ef, String fileDir) {
        byte[] bytes = ef.getBytes();
        String fileName=ef.getFileName();
        File file=null;
        FileOutputStream out=null;
        RandomAccessFile randomAccessFile=null;
        try {
            file= new File(fileDir + File.separator + fileName);
            if(!file.exists()){
                randomAccessFile= new RandomAccessFile(file, "rw");
                randomAccessFile.write(bytes);
            }else{
                out= new FileOutputStream(file,true);
                out.write(bytes);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(randomAccessFile!=null){
                    randomAccessFile.close();
                }
                if(out!=null){
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
