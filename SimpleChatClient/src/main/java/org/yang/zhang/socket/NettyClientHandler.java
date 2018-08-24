package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:12
 */

import org.yang.zhang.enums.BubbleType;
import org.yang.zhang.fxcontroller.MainController;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.utils.*;
import org.yang.zhang.view.ChatView;
import org.yang.zhang.view.ContractItemView;
import org.yang.zhang.view.LeftMessageBubble;

import com.fasterxml.jackson.core.type.TypeReference;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
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
        Integer userId=info.getSourceclientid();
        Integer targetId=info.getTargetclientid();
        Platform.setImplicitExit(false);
        switch (info.getMsgtype()){
            case NORMAL:
                Platform.runLater(()->{
                    ChatView chatWindow=ChatViewManager.getStage(String.valueOf(userId));
                    //聊天框未打开,头像闪动
                    if(chatWindow==null){
                        TreeItem<ContractItemView> itemViewTreeItem= ClientCache.getContractMap().get(String.valueOf(userId));
                        ContractItemView contractItemView=itemViewTreeItem.getValue();
                        contractItemView.startBlink();
                    }else {
                        ChatUtils.appendBubble(chatWindow.getChatPane(),BubbleType.RIGHT,info.getMsgcontent(),UserUtils.getUserIcon(),570D);
                    }
                    rightConerPop(info.getMsgcontent(),String.valueOf(userId),userId);
                });
                break;
            case ROOM:
                Platform.runLater(()->rightConerPop(info.getMsgcontent(),String.valueOf(userId),userId));
                break;
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
        try {
            TitledPane page=new TitledPane();
            page.setText(title);
            page.setContent(new Text(content));
            FadeTransition ft = new FadeTransition(Duration.millis(3000), page);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
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
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
