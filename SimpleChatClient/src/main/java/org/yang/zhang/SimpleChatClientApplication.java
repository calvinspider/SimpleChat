package org.yang.zhang;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.enums.StageType;
import org.yang.zhang.utils.StageManager;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yang.zhang.view.LoginView;
import org.yang.zhang.view.MainView;

@SpringBootApplication
public class SimpleChatClientApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(SimpleChatClientApplication.class,LoginView.class,args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setIconified(false);
        stage.setTitle("登录");
        StageManager.registerStage("login",stage);
        super.start(stage);
    }

}
