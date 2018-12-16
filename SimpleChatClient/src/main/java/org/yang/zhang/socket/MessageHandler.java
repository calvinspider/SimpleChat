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
import org.yang.zhang.module.FileMessage;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.utils.*;
import org.yang.zhang.view.ChatView;
import org.yang.zhang.view.RightFileMessageView;
import org.yang.zhang.view.SendFileView;
import org.yang.zhang.view.SmallFileMessage;

import com.fasterxml.jackson.core.type.TypeReference;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * Title: NettyClientHandler
 * Description: 客户端业务逻辑实现
 * Version:1.0.0
 * @author Administrator
 * @date 2017-8-31
 */
public class MessageHandler extends SimpleChannelInboundHandler<MessageInfo> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageInfo info) throws Exception {

        Integer userId=info.getSourceclientid();
        Integer targetId=info.getTargetclientid();
        Platform.setImplicitExit(false);
        switch (info.getMsgtype()){
            case NORMAL:
                Platform.runLater(()->{
                    ChatView chatWindow=ChatViewManager.getStage(String.valueOf(userId));
                    //聊天框未打开,头像闪动
                    if(chatWindow==null){
                        /*TreeItem<ContractItemView> itemViewTreeItem= ClientCache.getContractMap().get(String.valueOf(userId));
                        ContractItemView contractItemView=itemViewTreeItem.getValue();
                        contractItemView.startBlink();*/
                    }else {
                        ChatUtils.appendBubble(chatWindow.getChatPane(),BubbleType.LEFT,info.getMsgcontent(),ImageUtiles.getUserIcon(userId),670D);
                    }
//                    rightConerPop(info.getMsgcontent(),String.valueOf(userId),userId);

                });
                break;
            case ROOM:
                Platform.runLater(()->rightConerPop(info.getMsgcontent(),String.valueOf(userId),userId));
                break;
        }
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
}
