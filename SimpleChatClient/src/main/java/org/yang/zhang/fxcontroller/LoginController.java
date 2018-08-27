package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.SimpleChatClientApplication;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.entity.Result;
import org.yang.zhang.entity.ResultConstants;
import org.yang.zhang.module.User;
import org.yang.zhang.service.impl.LoginServiceImpl;
import org.yang.zhang.utils.ActionManager;
import org.yang.zhang.utils.DialogUtils;
import org.yang.zhang.utils.TrayManger;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.view.ChatRoomItemView;
import org.yang.zhang.view.LoginErrorView;
import org.yang.zhang.view.LoginView;
import org.yang.zhang.view.LoginedUserView;
import org.yang.zhang.view.MainView;
import org.yang.zhang.view.RegisterView;

@FXMLController
public class LoginController  implements Initializable {

    private static String fileRoot="D:\\simpleChatFiles";
    private static String historyUserFileName="historyUser.txt";

    @FXML
    private Pane root;
    @FXML
    private Button loginButton;
    @FXML
    private CheckBox remember;
    @FXML
    private CheckBox autoLogin;
    @FXML
    private ComboBox<LoginedUserView> userName;
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
    private TrayManger trayManger=new TrayManger();
    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize(URL url, ResourceBundle rb) {
        initHistoryUsers();
        initEvent();
    }

    private void initHistoryUsers() {
        userName.getItems().addAll(findLoginedUsers());
        userName.setEditable(true);
        userName.setCellFactory(
                new Callback<ListView<LoginedUserView>, ListCell<LoginedUserView>>() {
                    @Override public ListCell<LoginedUserView> call(ListView<LoginedUserView> param) {
                        final ListCell<LoginedUserView> cell = new ListCell<LoginedUserView>() {
                            @Override
                            public void updateItem(LoginedUserView pane, boolean empty) {
                                super.updateItem(pane,empty);
                                if (empty) {
                                    setText(null);
                                    setGraphic(null);
                                } else {
                                    setGraphic(pane.getPane());
                                }
                            }
                        };
                        return cell;
                    }
                });
    }

    private void initEvent() {
        root.setOnMousePressed(event ->  {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            StageManager.getStage(StageCodes.LOGIN).setX(event.getScreenX() - xOffset);
            StageManager.getStage(StageCodes.LOGIN).setY(event.getScreenY() - yOffset);
        });
    }

    /**
     * 登陆事件
     * @param event
     */
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        //登陆
        String name=userName.getValue().getUserId().getText();
        String pwd=passWord.getText();
        if(StringUtils.isBlank(name)||StringUtils.isBlank(pwd)){
            return;
        }
        loginButton.setText("登录中...");
        Result<User> result=loginService.login(name,pwd);
        if(ResultConstants.RESULT_FAILED.equals(result.getCode())){
            //登陆失败显示失败框
            DialogUtils.alert("登陆失败,用户名或密码错误!");
            loginButton.setText("登录");
            passWord.setText("");
            return;
        }
        User user= result.getData();
        UserUtils.setCurrentUser(user);

        //文件保存历史登陆用户
        saveUserLoginHistory(user);

        //登陆成功关闭登陆框
        Stage login=StageManager.getStage(StageCodes.LOGIN);
        login.hide();

        Stage mainStage=new Stage();
        mainStage.setScene(new Scene(mainView.getView()));
        mainStage.initStyle(StageStyle.UNDECORATED);
        ActionManager.setOnCloseExistListener(mainStage);
        //初始化主界面
        mainController.init(user);
        mainStage.show();
        mainStage.setResizable(false);

        //注册主界面
        StageManager.registerStage(StageCodes.MAIN,mainStage);
        trayManger.tray(mainStage);
    }

    private List<LoginedUserView> findLoginedUsers() {
        List<LoginedUserView> result=new ArrayList<>();
        FileInputStream inputStream=null;
        BufferedReader bufferedReader=null;
        String fileName=fileRoot+File.separator+historyUserFileName;
        try{
            inputStream = new FileInputStream(fileName);
            bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while((str = bufferedReader.readLine()) != null)
            {
                String[] array=str.split(",");
                result.add(new LoginedUserView(array[0],array[2],array[1]));
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                if(inputStream!=null)
                    inputStream.close();
                if(bufferedReader!=null)
                    bufferedReader.close();
            }catch (Exception e1){
                e.printStackTrace();
            }
        }
        return result;
    }

    private void saveUserLoginHistory(User user) {
        FileInputStream inputStream=null;
        FileOutputStream outputStream=null;
        BufferedReader bufferedReader=null;
        BufferedWriter bufferedWriter=null;
        try{
            Boolean userInHistory=false;
            String fileName=fileRoot+File.separator+historyUserFileName;
            File file=new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            inputStream = new FileInputStream(fileName);
            outputStream= new FileOutputStream(fileName);
            bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream));
            String str;
            while((str = bufferedReader.readLine()) != null)
            {
                if(str.equals(String.valueOf(user.getId()))){
                    userInHistory=true;
                    break;
                }
            }
            if(!userInHistory){
                bufferedWriter.write(user.getId()+","+user.getIconUrl()+","+user.getNickName()+"\r\n");
                bufferedWriter.flush();
            }
            inputStream.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
            try {
                if(inputStream!=null)
                    inputStream.close();
                if(outputStream!=null)
                    outputStream.close();
                if(bufferedReader!=null)
                    bufferedReader.close();
                if(bufferedWriter!=null)
                    bufferedWriter.close();
            }catch (Exception e1){
                e.printStackTrace();
            }
        }
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

    @FXML
    public void closeApp(){
        System.exit(0);
    }


}
