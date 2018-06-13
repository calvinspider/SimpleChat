package org.yang.zhang;

import javafx.stage.StageStyle;
import org.yang.zhang.stageview.LoginView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleChatClientApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(SimpleChatClientApplication.class, LoginView.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setIconified(false);
        stage.setTitle("登录");
        super.start(stage);
    }

}
