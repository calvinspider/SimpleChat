package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.abstracts.AbstractController;
import org.yang.zhang.cellimpl.LoginedUserCellImpl;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.dto.in.QrLoginDto;
import org.yang.zhang.entity.Result;
import org.yang.zhang.entity.ResultConstants;
import org.yang.zhang.enums.UserStatusType;
import org.yang.zhang.service.LoginService;
import org.yang.zhang.utils.QrCodeCreateUtil;
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

import com.sun.org.apache.xpath.internal.operations.Bool;

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
    private MenuButton statusMenu;

    @Autowired
    private LoginService loginService;
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
    private Map<String,String> loginedUserIconMap=new HashMap<>();

    private Stage qrStage=null;

    public void initialize(URL url, ResourceBundle rb) {

        initHistoryLoginedUsers();

        initUserStatusList();

        initKeyBoard();

        initConfig();
    }

    /**
     * 初始化用户状态选择框
     */
    private void initUserStatusList() {

        //默认状态在线
        statusMenu.setGraphic(new ImageView(new Image(Constant.STATUS_ONLINE_ICON)));

        //三种登陆状态
        MenuItem online=new MenuItem(UserStatusType.ONLINE.getText(),new ImageView(new Image(Constant.STATUS_ONLINE_ICON)));
        MenuItem busy=new MenuItem(UserStatusType.BUSY.getText(),new ImageView(new Image(Constant.STATUS_BUSY_ICON)));
        MenuItem invisible=new MenuItem(UserStatusType.INVISIBLE.getText(),new ImageView(new Image(Constant.STATUS_INVISIBLE_ICON)));

        //添加状态菜单
        statusMenu.getItems().add(online);
        statusMenu.getItems().add(busy);
        statusMenu.getItems().add(invisible);

        //设置全局用户状态
        online.setOnAction((event)->{
            statusMenu.setGraphic(new ImageView(new Image(Constant.STATUS_ONLINE_ICON)));
            status=UserStatusType.ONLINE;
        });
        busy.setOnAction((event)->{
            statusMenu.setGraphic(new ImageView(new Image(Constant.STATUS_BUSY_ICON)));
            status=UserStatusType.BUSY;
        });
        invisible.setOnAction((event)->{
            statusMenu.setGraphic(new ImageView(new Image(Constant.STATUS_INVISIBLE_ICON)));
            status=UserStatusType.INVISIBLE;
        });


    }

    /**
     * 初始化系统配置
     */
    private void initConfig() {
        //若勾选过记住账号,使用第一个账号
        Boolean setRemember=ClientCache.systemConfig.getRemember()!=null&&ClientCache.systemConfig.getRemember();
       if(setRemember){
           remember.setSelected(true);
           Map<String,String> map=ClientCache.systemConfig.getPwdMap();
           if(map!=null&&map.size()!=0){
               Map.Entry<String,String> entry= map.entrySet().iterator().next();
                   userName.setValue(entry.getKey());
                   passWord.setText(entry.getValue());
           }
       }else{
           remember.setSelected(false);
       }

       //登陆框可拖拽
       setDragable();
    }

    /**
     * 初始化历史登陆用户数据（账号下拉框）
     */
    private void initHistoryLoginedUsers() {

        userName.setCellFactory(LoginedUserCellImpl.callback);

        //从登陆历史文件中获取用户账号
        List<LoginedUserView> list=SystemConfigUtils.findLoginedUsers();

        //填充用户id-用户头像MAP后续展示使用
        list.forEach((item)->loginedUserIconMap.put(item.getId(),item.getIconUrl()));

        //用户名下拉框元素填充
        userName.getItems().addAll(list);
        userName.setEditable(true);

        //用户名下拉框元素选中后切换左侧头像框图片
        userName.getEditor().setOnKeyReleased((KeyEvent)->{
            String text=userName.getEditor().getText();
            setIcon(text);
        });
        userName.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue instanceof String){
                setIcon((String) newValue);
            }
            if(newValue instanceof LoginedUserView){
                userIcon.setImage(((LoginedUserView) newValue).getIconImage());
            }
        });
    }

    /**
     * 初始化密码虚拟键盘
     */
    private void initKeyBoard(){
        keyBoardStage=new Stage();
        final VBox root = new VBox();
        Scene scene = new Scene(root);
        VirtualKeyboard vkb = new VirtualKeyboard(passWord);
        root.getChildren().addAll(vkb.view());
        keyBoardStage.setScene(scene);
        keyBoardStage.initStyle(StageStyle.UNDECORATED);
        keyBoardStage.setResizable(false);
        keyborad.addEventHandler(MouseEvent.MOUSE_CLICKED, (event)->{
            if(keyBoardStage.isShowing()){
                keyBoardStage.hide();
            }else{
                keyBoardStage.setY(StageManager.getStage(StageCodes.LOGIN).getY()+260);
                keyBoardStage.setX(StageManager.getStage(StageCodes.LOGIN).getX()+100);
                keyBoardStage.show();
            }
            event.consume();
        });
        keyborad.cursorProperty().setValue(Cursor.HAND);
    }

    /**
     * 根据用户ID来设置左侧头像
     * @param id
     */
    private void setIcon(String id){
        if(!loginedUserIconMap.containsKey(id)){
            userIcon.setImage(ImageUtiles.getUserIcon("defaultIcon.png"));
            return;
        }
        Image image=ImageUtiles.getUserIcon(loginedUserIconMap.get(id));
        userIcon.setImage(image);
    }

    @FXML
    public void login() {
        String name=userName.getEditor().getText();
        String pwd=passWord.getText();
        Integer userStatus=status.getValue();
        if(StringUtils.isBlank(name)||StringUtils.isBlank(pwd)){
            return;
        }
        Result<User> result=loginService.login(name,pwd,userStatus);
        if(ResultConstants.RESULT_FAILED.equals(result.getCode())){
            //登陆失败显示失败框
            DialogUtils.alert("登陆失败,用户名或密码错误!");
            return;
        }
        User user= result.getData();
        dealAfterLogin(user);
    }

    private void dealAfterLogin(User user) {
        UserUtils.setCurrentUser(user);
        //文件保存历史登陆用户
        SystemConfigUtils.saveUserLoginHistory(user);
        if(remember.isSelected()){
            saveUserPwdMap(user.getId(),"");
        }
        //登陆成功关闭登陆框
        Stage login=StageManager.getStage(StageCodes.LOGIN);
        login.close();
        if(qrStage!=null){
            qrStage.close();
        }
        keyBoardStage.close();
        //弹出主页面
        Stage mainStage=new Stage();
        mainStage.setScene(new Scene(mainView.getView()));
        mainStage.initStyle(StageStyle.UNDECORATED);
        ActionManager.setOnCloseExistListener(mainStage);
        //初始化主界面
        mainController.init();
        mainStage.show();
        mainStage.setResizable(false);
        mainStage.getIcons().add(userIcon.getImage());
        //注册主界面
        StageManager.registerStage(StageCodes.MAIN,mainStage);
        //托盘图标显示
        trayManger.tray(mainStage);
    }

    /**
     * 文件保存用户账号信息
     * @param id
     * @param pwd
     */
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
            registerStage.setOnCloseRequest((event)->{
                registerStage.hide();
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

    @FXML
    private void showLoginQrCode()
    {
        try{
            Integer userId=1;
            String qrToken=UUID.randomUUID().toString().replace("-","");

            QrLoginDto qrLoginDto=new QrLoginDto();
            qrLoginDto.setQrToken(qrToken);
            qrLoginDto.setUserId(userId);

            String url=Constant.registerQrCode+"?userId="+userId+"&qrToken="+qrToken;
            File file=new File(Constant.fileRoot+File.separator+Constant.QrCodeFile);
            QrCodeCreateUtil.createQrCode(new FileOutputStream(file),url,1200,"JPEG");
            ImageView imageView=new ImageView(new Image(Constant.FILE_PROTOTAL+file.getAbsolutePath()));
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            //弹出二维码框
            Pane pane = new Pane(imageView);
            Scene scene = new Scene(pane);

            qrStage = new Stage();
            qrStage.initStyle(StageStyle.TRANSPARENT);
            qrStage.setScene(scene);
            qrStage.show();

            //展示完二维码后启动线程来轮询二维码登陆接口
            new Thread(()->{
                //轮询登陆接口
                try {
                    for (;;){
                        Result<User> result=loginService.loginByQrCode(qrLoginDto);
                        if(ResultConstants.RESULT_SUCCESS.equals(result.getCode())){
                            Platform.runLater(()->{
                                dealAfterLogin(result.getData());
                            });
                            break;
                        }
                        Thread.sleep(1000);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
