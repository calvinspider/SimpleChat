package org.yang.zhang.utils;

import java.util.List;

import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.view.ChatView;

import javafx.scene.image.Image;

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
}
