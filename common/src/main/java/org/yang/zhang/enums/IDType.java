package org.yang.zhang.enums;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 26 18:31
 */

public enum IDType {
    CHATWINDOW("C"),
    ROOMWINDOW("R"),
    ID("I");
    private String code;
    IDType(String code){
        this.code=code;
    }
    public String getCode(){
        return code;
    }
}
