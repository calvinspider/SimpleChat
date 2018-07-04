package org.yang.zhang.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 14 10:47
 */
@Component
public class StageManager{


    private static Map<String,Stage> stageMap=new HashMap<>();
    private static Map<String,Parent> chatWindowMap=new HashMap<>();

    private static Logger logger=Logger.getLogger(StageManager.class);

    public static void registerStage(String name, Stage stage){
        stageMap.put(name,stage);
    }
    public static void unregisterStage(String name){
        stageMap.remove(name);
    }
    public static Stage getStage(String name){
        return stageMap.get(name);
    }

    public static void registerChatWindows(String id, Parent root) {
        chatWindowMap.put(id,root);
    }

    public static Parent getParent(String id){
        return chatWindowMap.get(id);
    }

}
