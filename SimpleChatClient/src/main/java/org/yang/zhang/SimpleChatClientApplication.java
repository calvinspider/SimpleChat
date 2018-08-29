package org.yang.zhang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;

import org.springframework.context.annotation.Bean;
import org.yang.zhang.config.CustomSplash;
import org.yang.zhang.config.SystemConfig;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.utils.ActionManager;
import org.yang.zhang.utils.ClientCache;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.StageManager;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yang.zhang.utils.SystemConfigUtils;
import org.yang.zhang.view.LoginView;
import org.yang.zhang.view.MainView;

@SpringBootApplication
public class SimpleChatClientApplication extends AbstractJavaFxApplicationSupport {

    //启动应用弹出登陆框
    public static void main(String[] args) {
        ClientCache.systemConfig=SystemConfigUtils.loadSystemConfig();
        launch(SimpleChatClientApplication.class,LoginView.class,new CustomSplash(),args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setIconified(false);
        StageManager.registerStage(StageCodes.LOGIN,stage);
        ActionManager.setOnCloseExistListener(stage);
        super.start(stage);

    }


}
