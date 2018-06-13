package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.SimpleChatClientApplication;
import org.yang.zhang.application.MainApplication;
import org.yang.zhang.entity.Result;
import org.yang.zhang.entity.ResultConstants;
import org.yang.zhang.service.impl.LoginServiceImpl;
import org.yang.zhang.stageview.LoginView;
import org.yang.zhang.stageview.MainView;

@FXMLController
public class LoginController  implements Initializable {

    @Autowired
    private LoginServiceImpl loginService;

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
//        loginButton.setText("登录中...");
//        String name=userName.getText();
//        String pwd=passWord.getText();
//        Result result=loginService.login(name,pwd);
//        if(ResultConstants.RESULT_FAILED.equals(result.getCode())){
//            System.out.println("登陆失败");
//            return;
//        }
//        System.out.println("登陆成功");
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Parent root=fxmlLoader.load();
            Scene scene=new Scene(root,280,700);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }





    }
}
