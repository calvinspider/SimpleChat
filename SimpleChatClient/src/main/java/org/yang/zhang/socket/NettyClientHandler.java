package org.yang.zhang.socket;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 08 15:12
 */

import java.util.List;

import org.yang.zhang.entity.Contract;
import org.yang.zhang.entity.MessageInfo;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.StageManager;

import com.fasterxml.jackson.core.type.TypeReference;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        TypeReference type = new TypeReference<List<MessageInfo>>(){};
        MessageInfo info=JsonUtils.fromJson(msg,type);
        String userName=info.getTargetclientid();
        Parent chatWindow=StageManager.getParent(userName);
        Pane otherChat = (Pane) chatWindow.lookup("#otherChat");
        Label label=new Label(info.getMsgcontent());
        otherChat.getChildren().add(label);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭! ");
        super.channelInactive(ctx);
    }
}
