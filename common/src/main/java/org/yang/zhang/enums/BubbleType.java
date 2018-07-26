package org.yang.zhang.enums;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 26 18:22
 */

public enum BubbleType {
    LEFT(1),
    RIGHT(2);
    private Integer code;
    BubbleType(Integer code){
        this.code=code;
    }
    public Integer getCode(){
        return code;
    }
}
