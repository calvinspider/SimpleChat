package org.yang.zhang.application;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends AbstractJavaFxApplicationSupport {

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("主页面");
        super.start(stage);
    }
}
