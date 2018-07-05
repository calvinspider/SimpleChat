package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:12
 */

import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.StageManager;

import com.fasterxml.jackson.core.type.TypeReference;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * Title: NettyClientHandler
 * Description: 客户端业务逻辑实现
 * Version:1.0.0
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg==null||"".equals(msg)){
            return;
        }
        TypeReference type = new TypeReference<MessageInfo>(){};
        MessageInfo info=JsonUtils.fromJson((String) msg,type);
        if(info==null){
            throw new Exception("MessageInfo must not be NULL!");
        }
        String userName=info.getSourceclientid();
        Stage chatWindow=StageManager.getStage(userName);
        //聊天框未打开,头像闪动
        if(chatWindow==null){
            System.out.println("聊天框未打开");
        }else{
            Platform.runLater(()->{
                Scene scene=chatWindow.getScene();
                Pane otherChat = (Pane) scene.lookup("#chatHistory");
                ImageView imageView=new ImageView("images/personIcon.jpg");
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                Label label=new Label(info.getMsgcontent(),imageView);
                label.setAlignment(Pos.CENTER_RIGHT);
                label.setPrefWidth(570);
                otherChat.getChildren().add(label);
            });

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
}
