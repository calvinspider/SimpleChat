package enums;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 07 15:51
 */

public enum  MessageType {
 MSG(0),
 NOTIFICATION(1);

 private Integer code;
 MessageType(Integer code){
     this.code=code;
 }
 public Integer getCode(){
     return code;
 }
}
