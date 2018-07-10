package org.yang.zhang;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.config.CustomSplash;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.enums.StageType;
import org.yang.zhang.utils.StageManager;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yang.zhang.view.LoginView;
import org.yang.zhang.view.MainView;

@SpringBootApplication
public class SimpleChatClientApplication extends AbstractJavaFxApplicationSupport {

    //启动应用弹出登陆框
    public static void main(String[] args) {
        launch(SimpleChatClientApplication.class,LoginView.class,new CustomSplash(),args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setIconified(false);
        stage.setTitle("登录");
        //注册登陆页面
        StageManager.registerStage(StageCodes.LOGIN,stage);
        super.start(stage);
    }



}
