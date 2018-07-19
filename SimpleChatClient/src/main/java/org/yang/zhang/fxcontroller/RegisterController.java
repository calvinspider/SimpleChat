package org.yang.zhang.fxcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.entity.Result;
import org.yang.zhang.entity.ResultConstants;
import org.yang.zhang.module.User;
import org.yang.zhang.service.UserService;
import org.yang.zhang.utils.StageManager;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 13 11:47
 */
@FXMLController
public class RegisterController implements Initializable {

    @FXML TextField nickName;
    @FXML TextField icon;
    @FXML TextField shuxiang;
    @FXML TextField phone;
    @FXML TextField page;
    @FXML TextField email;
    @FXML TextField birthday;
    @FXML TextField sex;
    @FXML TextField xingzuo;
    @FXML TextField bloodType;
    @FXML TextField hometoem;
    @FXML TextField locate;
    @FXML TextField age;
    @FXML TextField personword;
    @FXML TextField password;

    @Autowired
    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void register(ActionEvent event){
        User user=new User();
        user.setPassword(password.getText());
        user.setNickName(nickName.getText());
        user.setAge(age.getText());
        user.setBirthday(birthday.getText());
        user.setBloodType(bloodType.getText());
        user.setConstellation(xingzuo.getText());
        user.setEmail(email.getText());
        user.setHomeTown(hometoem.getText());
        user.setLocate(locate.getText());
        user.setZodiac(shuxiang.getText());
        user.setPhone(phone.getText());
        user.setPersonWord(personword.getText());
        user.setPage(page.getText());
        user.setSex(sex.getText());
        Result<User> result=userService.register(user);
        System.out.println("用户注册："+result.getData());
        if(result.getCode().equals(ResultConstants.RESULT_SUCCESS)){
            Alert _alert = new Alert(Alert.AlertType.INFORMATION);
            _alert.setTitle("信息");
            _alert.setContentText("用户注册成功！\n您的登陆账号为 "+result.getData().getId());
            _alert.initOwner(StageManager.getStage(StageCodes.REGISTER));
            _alert.show();
        }else{
            Alert _alert = new Alert(Alert.AlertType.INFORMATION);
            _alert.setTitle("信息");
            _alert.setContentText("注册失败！");
            _alert.initOwner(StageManager.getStage(StageCodes.REGISTER));
            _alert.show();
        }
    }
}