package org.yang.zhang.fxcontroller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.entity.Result;
import org.yang.zhang.entity.ResultConstants;
import org.yang.zhang.module.User;
import org.yang.zhang.service.UserService;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.DialogUtils;
import org.yang.zhang.utils.StageManager;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 13 11:47
 */
@FXMLController
public class RegisterController implements Initializable {

    @FXML
    Pane root;
    @FXML
    TextField nickName;
    @FXML
    Button chooseFileBtn;
    @FXML
    ChoiceBox<String> xingzuo;
    @FXML
    TextField mobile;
    @FXML
    TextField email;
    @FXML
    TextField birthday;
    @FXML
    ChoiceBox<String> sex;
    @FXML
    ChoiceBox<String> blood;
    @FXML
    TextField personWord;
    @FXML
    TextField personalDesc;
    @FXML
    ChoiceBox<String> job;
    @FXML
    ImageView userIcon;
    @FXML
    TextField passwordSure;
    @FXML
    TextField password;
    @FXML
    TextField userIconFIeld;

    @Autowired
    private UserService userService;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      nickName.setText("");
      xingzuo.setValue("");
      mobile.setText("");
      email.setText("");
      birthday.setText("");
      sex.setValue("");
      blood.setValue("");
      personWord.setText("");
      personalDesc.setText("");
      job.setValue("");
      passwordSure.setText("");
      password.setText("");

        root.setOnMousePressed(event ->  {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            StageManager.getStage(StageCodes.REGISTER).setX(event.getScreenX() - xOffset);
            StageManager.getStage(StageCodes.REGISTER).setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    public void close(){
        StageManager.getStage(StageCodes.REGISTER).close();
    }

    @FXML
    public void register(){
        User user=new User();
        String nickNameStr=nickName.getText();
        String xingzuoStr=xingzuo.getValue();
        String mobileStr=mobile.getText();
        String emailStr=email.getText();
        String birthdayStr=birthday.getText();
        String sexStr=sex.getValue();
        String bloodStr=blood.getValue();
        String personWordStr=personWord.getText();
        String personalDescStr=personalDesc.getText();
        String jobStr=job.getValue();
        String passwordSureStr=passwordSure.getText();
        String passwordStr=password.getText();
        if(StringUtils.isBlank(nickNameStr)){
            DialogUtils.alert("请填写昵称!");
            return;
        }
        if(StringUtils.isBlank(passwordStr)){
            DialogUtils.alert("请输入密码!");
            return;
        }
        if(StringUtils.isBlank(passwordSureStr)){
            DialogUtils.alert("请确认密码!");
            return;
        }
        if(!passwordSureStr.equals(passwordStr)){
            DialogUtils.alert("俩次输入的密码不一致!");
            return;
        }
        String fileNameStr=userIconFIeld.getText();
        if(StringUtils.isBlank(fileNameStr)){
            DialogUtils.alert("请选择头像!");
            return;
        }
        user.setPassword(passwordStr);
        user.setNickName(nickNameStr);
        user.setBirthday(birthdayStr);
        user.setBloodType(bloodStr);
        user.setConstellation(xingzuoStr);
        user.setEmail(emailStr);
        user.setPhone(mobileStr);
        user.setPersonWord(personWordStr);
        user.setSex(sexStr);
//        user.setJob(jobStr);
//        user.setPersonalDesc(personalDescStr);

        String format=fileNameStr.substring(fileNameStr.lastIndexOf("."),fileNameStr.length());
        String realName=String.valueOf(System.nanoTime())+format;
        NettyClient.sendFile(new File(fileNameStr),realName);
        user.setIconUrl(realName);

        Result<User> result=userService.register(user);
        if(result.getCode().equals(ResultConstants.RESULT_SUCCESS)){
           DialogUtils.alert("用户注册成功！您的登陆账号为 "+result.getData().getId()+"");
           StageManager.getStage(StageCodes.REGISTER).close();
        }else{
            DialogUtils.alert("注册失败!");
            StageManager.getStage(StageCodes.REGISTER).close();
        }
    }

    @FXML
    private void chooseFile(){
        Stage mainStage = null;
        FileChooser fileChooser = new FileChooser();//构建一个文件选择器实例
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        userIconFIeld.setText(selectedFile.getPath());
        File file = new File(selectedFile.getPath());
        userIcon.setImage(new Image(file.toURI().toString()));
    }


}
