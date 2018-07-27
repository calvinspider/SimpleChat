package org.yang.zhang.utils;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 21 15:31
 */

public class AnimationUtils {

    public static void slowScrollToBottom(ScrollPane scrollPane) {
        Animation animation = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new KeyValue(scrollPane.vvalueProperty(), 1)));
        animation.play();
    }

}
