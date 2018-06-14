package org.yang.zhang.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.yang.zhang.enums.StageType;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 14 10:47
 */
@Component
public class StageManager{

    private static Map<StageType,Stage> stageMap=new HashMap<>();

    private static Logger logger=Logger.getLogger(StageManager.class);

    public static void registerStage(StageType name, Stage stage){
        stageMap.put(name,stage);
    }

    public static Stage getStage(StageType name){
        return stageMap.get(name);
    }

}
