package org.yang.zhang;

import javafx.scene.Scene;
import javafx.stage.StageStyle;
import org.yang.zhang.fxcontroller.MainStageView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleChatClientApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(SimpleChatClientApplication.class, MainStageView.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.TRANSPARENT);
        super.start(stage);
    }

}
