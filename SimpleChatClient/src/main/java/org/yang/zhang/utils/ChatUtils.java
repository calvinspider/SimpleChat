package org.yang.zhang.utils;

import java.util.Date;
import java.util.List;

import org.yang.zhang.enums.BubbleType;
import org.yang.zhang.enums.MessageType;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.view.ChatView;
import org.yang.zhang.view.LeftMessageBubble;
import org.yang.zhang.view.RightMessageBubble;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 26 11:34
 */

public class ChatUtils {

    public static void openChatWindow(Integer id,String nickName,Image userIcon) {
        try {
            //不重复打开聊天框
            if(ChatViewManager.getStage(String.valueOf(id))!=null){
                return;
            }
            ChatService chatService=SpringContextUtils.getBean("chatService");
            List<MessageInfo> messageInfos=chatService.getOneDayRecentChatLog(id,UserUtils.getCurrentUserId());
            ChatView chatView= new ChatView(id,nickName,userIcon,messageInfos);
            ChatViewManager.registerStage(String.valueOf(id),chatView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sendMessage(Integer targetId,MessageType messageType, String content){
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setSourceclientid(UserUtils.getCurrentUserId());
        messageInfo.setTargetclientid(targetId);
        messageInfo.setMsgtype(messageType);
        messageInfo.setMsgcontent(content);
        NettyClient.sendMessage(JsonUtils.toJson(messageInfo));
    }

    public static void appendBubble(ScrollPane scrollPane, BubbleType bubbleType, String content, Image icon, Double width){
        VBox chatHistory=(VBox)scrollPane.getContent();
        Label time=new Label(DateUtils.formatDateTime(new Date()));
        time.setPrefWidth(width-200);
        time.setAlignment(Pos.CENTER);
        time.setPrefHeight(30);
        switch (bubbleType){
            case LEFT:
                LeftMessageBubble leftMessageBubble=new LeftMessageBubble(content,icon);
                leftMessageBubble.getPane().setPrefWidth(width);
                chatHistory.getChildren().add(time);
                chatHistory.getChildren().add(leftMessageBubble.getPane());
                AnimationUtils.slowScrollToBottom(scrollPane);
                break;
            case RIGHT:
                RightMessageBubble rightMessageBubble=new RightMessageBubble(content,icon);
                rightMessageBubble.getPane().setPrefWidth(width);
                chatHistory.getChildren().add(time);
                chatHistory.getChildren().add(rightMessageBubble.getPane());
                AnimationUtils.slowScrollToBottom(scrollPane);
                break;
        }
    }


}
