package org.yang.zhang.fxcontroller;


import javax.xml.soap.Text;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.entity.Result;
import org.yang.zhang.entity.ResultConstants;
import org.yang.zhang.service.LoginService;
import org.yang.zhang.service.impl.LoginServiceImpl;
import org.yang.zhang.utils.DialogUtils;
import org.yang.zhang.utils.StageManager;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 08 31 11:18
 */
@FXMLController
public class PasswordBackController {

    @FXML
    private TextField userId;

    @Autowired
    private LoginService loginService;

    @FXML
    public void findPwd(){
        if(StringUtils.isEmpty(userId.getText())){
            DialogUtils.alert("请输入账号");
            return;
        }
        Result<Void> result=loginService.findPassWord(userId.getText());
        if(ResultConstants.RESULT_SUCCESS.equals(result.getCode())){
            DialogUtils.alert("密码已发送至注册邮箱!");
            StageManager.getStage(StageCodes.PASSBACK).close();
        }else{
            DialogUtils.alert("密码找回失败!请检查注册邮箱是否正确！");
        }
    }

    @FXML
    public void cancel(){
        StageManager.getStage(StageCodes.PASSBACK).hide();
    }


}
