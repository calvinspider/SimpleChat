package org.yang.zhang.enums;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 07 15:51
 */

public enum  MessageType {
 REGISTER(1),
 UNREGISTER(2),
 ROOM(3),
 NORMAL(4);

 private Integer code;
 MessageType(Integer code){
     this.code=code;
 }
 public Integer getCode(){
     return code;
 }
}
