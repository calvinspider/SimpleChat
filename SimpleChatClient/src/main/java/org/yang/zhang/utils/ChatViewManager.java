package org.yang.zhang.utils;

import java.util.HashMap;
import java.util.Map;

import org.yang.zhang.view.ChatView;

import javafx.stage.Stage;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 12:10
 */

public class ChatViewManager {

    private static Map<String, ChatView> chatViewMap = new HashMap<>();


    public static void registerStage(String name, ChatView stage) {
        chatViewMap.put(name, stage);
    }

    public static void unregisterStage(String name) {
        chatViewMap.remove(name);
    }

    public static ChatView getStage(String name) {
        return chatViewMap.get(name);
    }
}
