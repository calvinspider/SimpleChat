package org.yang.zhang.utils;

import org.yang.zhang.constants.StageCodes;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 08 27 13:31
 */

public class DialogUtils {

    public static void alert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(false);
//        alert.initStyle(StageStyle.DECORATED);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
