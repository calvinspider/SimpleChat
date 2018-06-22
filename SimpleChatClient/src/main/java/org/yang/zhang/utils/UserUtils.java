package org.yang.zhang.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.view.MainView;

import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 22 17:52
 */

public class UserUtils {

    @Autowired
    private static MainView mainView;

    public static String getCurrentUser(){
        Parent root=mainView.getView();
        Label nameLabel = (Label)root.lookup("#nameLabel");
        return nameLabel.getText();
    }
}
