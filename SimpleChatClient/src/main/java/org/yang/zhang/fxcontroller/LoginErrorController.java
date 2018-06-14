package org.yang.zhang.fxcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.SimpleChatClientApplication;
import org.yang.zhang.enums.StageType;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.view.LoginErrorView;
import org.yang.zhang.view.LoginView;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 14 13:41
 */
@FXMLController
public class LoginErrorController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void backToLogin(ActionEvent event) {
        SimpleChatClientApplication.showView(LoginView.class);
    }


}
