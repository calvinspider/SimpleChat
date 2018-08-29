package org.yang.zhang.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 08 29 10:19
 */
public class SystemConfig implements Serializable {

    private Boolean remember;
    private Boolean autoLogin;
    private Map<String,String> pwdMap=new HashMap<>();

    public Map<String, String> getPwdMap() {
        return pwdMap;
    }

    public void setPwdMap(Map<String, String> pwdMap) {
        this.pwdMap = pwdMap;
    }

    public Boolean getRemember() {
        return remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }

    public Boolean getAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }
}
