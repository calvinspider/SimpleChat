package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class LoginController  implements Initializable {

    @FXML
    private Button loginButton;
    @FXML
    private CheckBox remember;
    @FXML
    private CheckBox autoLogin;
    @FXML
    private TextField userName;
    @FXML
    private TextField passWord;
    @FXML
    private ImageView userIcon;

    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        loginButton.setText("登录中...");
        String name=userName.getText();
        String pwd=passWord.getText();

    }
}
