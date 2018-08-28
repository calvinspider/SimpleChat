package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javafx.util.StringConverter;
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
import org.yang.zhang.utils.ImageUtiles;
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
    private ComboBox userName;
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

    Map<String,String> loginedMap=new HashMap<>();

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
                                super.updateItem(pane, empty);
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
        userName.getEditor().setOnKeyReleased(this::handleKeyPressedForComboBox);
        userName.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue instanceof String){
                setIcon((String) newValue);
            }
            if(newValue instanceof LoginedUserView){
                userIcon.setImage(((LoginedUserView) newValue).getIconImage());
            }
        });
    }

    private void handleKeyPressedForComboBox(KeyEvent keyEvent) {
        String text=userName.getEditor().getText();
        setIcon(text);
    }

    private void setIcon(String id){
        if(!loginedMap.containsKey(id)){
            userIcon.setImage(ImageUtiles.getUserIcon("defaultIcon.png"));
            return;
        }
        Image image=ImageUtiles.getUserIcon(loginedMap.get(id));
        userIcon.setImage(image);
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
        String name=userName.getEditor().getText();
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
        File file=new File(fileName);
        if(!file.exists()){
            return new ArrayList<>();
        }
        try{
            inputStream = new FileInputStream(fileName);
            bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while((str = bufferedReader.readLine()) != null)
            {
                String[] array=str.split(",");
                result.add(new LoginedUserView(array[0],array[2],array[1]));
                loginedMap.put(array[0],array[1]);
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
        FileReader fr=null;
        BufferedReader bufferedReader=null;
        BufferedWriter bufferedWriter=null;
        FileWriter fw=null;
        try{
            Boolean userInHistory=false;
            String fileName=fileRoot+File.separator+historyUserFileName;
            File file=new File(fileName);
            File dir=new File(fileRoot);
            if(!dir.exists()){
                dir.mkdir();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            fr = new FileReader(fileName);
            fw = new FileWriter(file, true);
            bufferedReader= new BufferedReader(fr);
            bufferedWriter= new BufferedWriter(fw);
            String str;
            String userid=String.valueOf(user.getId());
            while((str = bufferedReader.readLine()) != null)
            {
                String[] tmp=str.split(",");
                String now=tmp[0];
                System.out.println(tmp[0]+userid);
                if(now.equals(userid)){
                    userInHistory=true;
                    break;
                }
            }
            if(!userInHistory){
                bufferedWriter.write(user.getId()+","+user.getIconUrl()+","+user.getNickName()+"\n");
                bufferedWriter.flush();
            }
            fr.close();
            fw.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
            try {
                if(fr!=null)
                    fr.close();
                if(fw!=null)
                    fw.close();
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
