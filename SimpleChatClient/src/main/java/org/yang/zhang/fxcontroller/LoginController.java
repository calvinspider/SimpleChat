package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.entity.Result;
import org.yang.zhang.entity.ResultConstants;
import org.yang.zhang.enums.StageType;
import org.yang.zhang.service.impl.LoginServiceImpl;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.view.LoginErrorView;
import org.yang.zhang.view.LoginView;
import org.yang.zhang.view.MainView;

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
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private MainController mainController;
    @Autowired
    private LoginView loginView;

    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * 登陆事件
     * @param event
     */
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        //登陆
        loginButton.setText("登录中...");
        String name=userName.getText();
        String pwd=passWord.getText();
        Result result=loginService.login(name,pwd);
        if(ResultConstants.RESULT_FAILED.equals(result.getCode())){
            //登陆失败显示失败框
            SimpleChatClientApplication.showView(LoginErrorView.class);
            loginButton.setText("登录");
            userName.setText("");
            passWord.setText("");
            return;
        }

        //登陆成功关闭登陆框
        Stage login=StageManager.getStage(StageCodes.LOGIN);
        login.close();

        //弹出主页面
        Label nameLabel =mainController.nameLabel;
        nameLabel.setText(userName.getText());
        Stage mainStage=new Stage();
        mainStage.setScene(new Scene(loginView.getView()));
        mainStage.show();

        //注册主页面
        StageManager.registerStage(StageCodes.MAIN,mainStage);

    }
}
