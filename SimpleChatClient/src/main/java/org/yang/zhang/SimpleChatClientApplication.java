package org.yang.zhang;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.config.CustomSplash;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.utils.ListenerManager;
import org.yang.zhang.utils.StageManager;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yang.zhang.view.LoginView;

@SpringBootApplication
public class SimpleChatClientApplication extends AbstractJavaFxApplicationSupport {

    //启动应用弹出登陆框
    public static void main(String[] args) {
        launch(SimpleChatClientApplication.class,LoginView.class,new CustomSplash(),args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setIconified(false);
        stage.setTitle("登录");
        //注册登陆页面
        StageManager.registerStage(StageCodes.LOGIN,stage);
        ListenerManager.setOnCloseExistListener(stage);
        super.start(stage);
    }


}
