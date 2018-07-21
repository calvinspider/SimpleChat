package org.yang.zhang.utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 21 15:31
 */

public class AnimationUtils {
    public static void slowScrollToBottom(ScrollPane scrollPane) {
        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(scrollPane.vvalueProperty(), 1)));
        animation.play();
    }
}
