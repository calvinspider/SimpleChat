package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.abstracts.AbstractController;
import org.yang.zhang.cellimpl.LoginedUserCellImpl;
import org.yang.zhang.cellimpl.UserStatusCellImpl;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.entity.Result;
import org.yang.zhang.entity.ResultConstants;
import org.yang.zhang.enums.UserStatusType;
import org.yang.zhang.utils.VirtualKeyboard;
import org.yang.zhang.module.User;
import org.yang.zhang.service.impl.LoginServiceImpl;
import org.yang.zhang.utils.ActionManager;
import org.yang.zhang.utils.ClientCache;
import org.yang.zhang.utils.DialogUtils;
import org.yang.zhang.utils.ImageUtiles;
import org.yang.zhang.utils.SystemConfigUtils;
import org.yang.zhang.utils.TrayManger;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.view.LoginedUserView;
import org.yang.zhang.view.MainView;
import org.yang.zhang.view.PasswordBackView;
import org.yang.zhang.view.RegisterView;
import org.yang.zhang.view.UserStatusView;

@FXMLController
public class LoginController extends AbstractController implements Initializable {

    @FXML
    private Pane root;
    @FXML
    private Button loginButton;
    @FXML
    private CheckBox remember;
    @FXML
    private ComboBox userName;
    @FXML
    private TextField passWord;
    @FXML
    private ImageView userIcon;
    @FXML
    private ImageView keyborad;
    @FXML
    private ComboBox<UserStatusView> userStatus;

    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private MainController mainController;
    @Autowired
    private MainView mainView;
    @Autowired
    private RegisterView registerView;
    @Autowired
    private PasswordBackView passwordBackView;

    //加载托盘图标
    private TrayManger trayManger=new TrayManger();
    //密码虚拟键盘
    private Stage keyBoardStage;
    //默认登陆状态
    private UserStatusType status=UserStatusType.ONLINE;
    //历史登陆账号缓存
    private Map<String,String> loginedMap=new HashMap<>();

    public void initialize(URL url, ResourceBundle rb) {
        //初始化历史登陆用户数据（账号下拉框）
        initHistoryLoginedUsers();
        //初始化用户状态选择框
        initUserStatusList();
        //初始化密码虚拟键盘
        initKeyBoard();
        //初始化系统配置
        initConfig();
    }

    private void initUserStatusList() {
        userStatus.getItems().add(new UserStatusView(Constant.STATUS_ONLINE_ICON,UserStatusType.ONLINE.getText()));
        userStatus.getItems().add(new UserStatusView(Constant.STATUS_BUSY_ICON,UserStatusType.BUSY.getText()));
        userStatus.getItems().add(new UserStatusView(Constant.STATUS_INVISIBLE_ICON,UserStatusType.INVISIBLE.getText()));
        userStatus.setCellFactory(UserStatusCellImpl.callback);
        userStatus.setButtonCell(new StatusButtonCell());
        userStatus.getSelectionModel().selectFirst();
        userStatus.valueProperty().addListener(new ChangeListener<UserStatusView>() {
            @Override public void changed(ObservableValue ov, UserStatusView old, UserStatusView newv) {
                if(UserStatusType.ONLINE.getText().equals(newv.getStatus())){
                    status=UserStatusType.ONLINE;
                }else if(UserStatusType.BUSY.getText().equals(newv.getStatus())){
                    status=UserStatusType.BUSY;
                }else if(UserStatusType.INVISIBLE.getText().equals(newv.getStatus())){
                    status=UserStatusType.INVISIBLE;
                }
            }
        });
    }

    public class StatusButtonCell extends ListCell<UserStatusView> {
        protected void updateItem(UserStatusView item, boolean empty){
            super.updateItem(item, empty);
            if (empty) {
                setItem(null);
                setGraphic(null);
            }else{
                setText(item.toString());
                ImageView imageView=new ImageView(item.getImage());
                setGraphic(imageView);
            }
        }
    }

    private void initConfig() {
       if(ClientCache.systemConfig.getRemember()!=null&&ClientCache.systemConfig.getRemember()){
           remember.setSelected(true);
           Map<String,String> map=ClientCache.systemConfig.getPwdMap();
           if(map!=null){
               for (Map.Entry<String,String> entry:map.entrySet()){
                   userName.setValue(entry.getKey());
                   passWord.setText(entry.getValue());
                   break;
               }
           }
       }else{
           remember.setSelected(false);
       }
       setDragable();
    }

    private void initHistoryLoginedUsers() {
        List<LoginedUserView> list=SystemConfigUtils.findLoginedUsers();
        for (LoginedUserView view:list){
            loginedMap.put(view.getId(),view.getIconUrl());
        }
        userName.getItems().addAll(list);
        userName.setEditable(true);
        userName.getEditor().setOnKeyReleased((KeyEvent)->{
            String text=userName.getEditor().getText();
            setIcon(text);}
        );
        userName.setCellFactory(LoginedUserCellImpl.callback);
        userName.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue instanceof String){
                setIcon((String) newValue);
            }
            if(newValue instanceof LoginedUserView){
                userIcon.setImage(((LoginedUserView) newValue).getIconImage());
            }
        });
    }

    private void initKeyBoard(){
        keyBoardStage=new Stage();
        final VBox root = new VBox();
        Scene scene = new Scene(root);
        VirtualKeyboard vkb = new VirtualKeyboard(passWord);
        root.getChildren().addAll(vkb.view());
        keyBoardStage.setScene(scene);
        keyBoardStage.initStyle(StageStyle.UNDECORATED);
        keyBoardStage.setResizable(false);
        keyborad.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(keyBoardStage.isShowing()){
                    keyBoardStage.hide();
                }else{
                    keyBoardStage.setY(StageManager.getStage(StageCodes.LOGIN).getY()+260);
                    keyBoardStage.setX(StageManager.getStage(StageCodes.LOGIN).getX()+100);
                    keyBoardStage.show();
                }
                event.consume();
            }
        });
        keyborad.cursorProperty().setValue(Cursor.HAND);
    }

    private void setIcon(String id){
        if(!loginedMap.containsKey(id)){
            userIcon.setImage(ImageUtiles.getUserIcon("defaultIcon.png"));
            return;
        }
        Image image=ImageUtiles.getUserIcon(loginedMap.get(id));
        userIcon.setImage(image);
    }

    @FXML
    public void login() {
        String name=userName.getEditor().getText();
        String pwd=passWord.getText();
        Integer s=this.status.getValue();
        if(StringUtils.isBlank(name)||StringUtils.isBlank(pwd)){
            return;
        }
        loginButton.setText("登录中...");
        Result<User> result=loginService.login(name,pwd,s);
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
        SystemConfigUtils.saveUserLoginHistory(user);
        if(remember.isSelected()){
            saveUserPwdMap(user.getId(),pwd);
        }
        //登陆成功关闭登陆框
        Stage login=StageManager.getStage(StageCodes.LOGIN);
        login.hide();
        keyBoardStage.hide();
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

    private void saveUserPwdMap(Integer id, String pwd) {
        Map<String,String> map=ClientCache.systemConfig.getPwdMap();
        map.put(String.valueOf(id),pwd);
        SystemConfigUtils.flushConfig(ClientCache.systemConfig);
    }

    @FXML
    public void userRegister(){
        if(StageManager.getStage(StageCodes.REGISTER)==null){
            Stage registerStage=new Stage();
            registerStage.setScene(new Scene(registerView.getView()));
            registerStage.setResizable(false);
            registerStage.initStyle(StageStyle.TRANSPARENT);
            registerStage.show();
            StageManager.registerStage(StageCodes.REGISTER,registerStage);
            registerStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    registerStage.hide();
                }
            });
        }else{
            StageManager.getStage(StageCodes.REGISTER).show();
        }
    }

    @FXML
    public void passwordBack(){
        if(StageManager.getStage(StageCodes.PASSBACK)==null){
            Scene scene=new Scene(passwordBackView.getView());
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            StageManager.registerStage(StageCodes.PASSBACK,stage);
            stage.show();
        }else{
            StageManager.getStage(StageCodes.PASSBACK).show();
        }
    }

    @FXML
    public void closeApp(){
        System.exit(0);
    }

    @FXML
    public void rememberPwd(){
        if(remember.isSelected()){
            ClientCache.systemConfig.setRemember(true);
        }else{
            ClientCache.systemConfig.setRemember(false);
        }
        SystemConfigUtils.flushConfig(ClientCache.systemConfig);
    }

    @Override
    public void setDragable() {
        root.setOnMousePressed(event ->  {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            StageManager.getStage(StageCodes.LOGIN).setX(event.getScreenX() - xOffset);
            StageManager.getStage(StageCodes.LOGIN).setY(event.getScreenY() - yOffset);
        });
    }

}
