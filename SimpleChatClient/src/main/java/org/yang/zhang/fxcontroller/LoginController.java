package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.SimpleChatClientApplication;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.entity.Result;
import org.yang.zhang.entity.ResultConstants;
import org.yang.zhang.module.User;
import org.yang.zhang.service.impl.LoginServiceImpl;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.view.LoginErrorView;
import org.yang.zhang.view.LoginView;
import org.yang.zhang.view.MainView;
import org.yang.zhang.view.RegisterView;

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
    @FXML
    private Label userRegister;
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private MainController mainController;
    @Autowired
    private LoginView loginView;
    @Autowired
    private MainView mainView;
    @Autowired
    private RegisterView registerView;

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
        if(StringUtils.isBlank(name)||StringUtils.isBlank(pwd)){
            return;
        }
        Result<User> result=loginService.login(name,pwd);
        if(ResultConstants.RESULT_FAILED.equals(result.getCode())){
            //登陆失败显示失败框
            SimpleChatClientApplication.showView(LoginErrorView.class);
            loginButton.setText("登录");
            userName.setText("");
            passWord.setText("");
            return;
        }
        User user= result.getData();
        UserUtils.setCurrentUser(user);
        //登陆成功关闭登陆框
        Stage login=StageManager.getStage(StageCodes.LOGIN);
        login.hide();

        Stage mainStage=new Stage();
        mainStage.setScene(new Scene(mainView.getView()));
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        //初始化主界面
        mainController.init(user);
        mainStage.show();
        mainStage.setResizable(false);

        //注册主界面
        StageManager.registerStage(StageCodes.MAIN,mainStage);
    }

    @FXML
    public void userRegister(){
        if(StageManager.getStage(StageCodes.REGISTER)==null){
            Stage registerStage=new Stage();
            registerStage.setScene(new Scene(registerView.getView()));
            registerStage.show();
            StageManager.registerStage(StageCodes.REGISTER,registerStage);
        }
    }
}
