package org.yang.zhang.utils;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 21 11:02
 */

public class ListenerManager {
    public static void setOnCloseExistListener(Stage stage){
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }
}
