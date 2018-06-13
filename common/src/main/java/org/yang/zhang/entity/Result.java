package org.yang.zhang.entity;

import java.io.Serializable;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 04 28 10:56
 */

public class Result implements Serializable {

    private String code;
    private String message;
    private Object data;

    public Result(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result() {

    }

    public static Result create(String code, String message, Object data){
        return new Result(code,message,data);
    }

    public static Result create(String code,String message){
        return new Result(code,message,null);
    }

    public static Result successData(Object data)
    {
        return create(ResultConstants.RESULT_SUCCESS,null,data);
    }

    public static Result successData(String message,Object data)
    {
        return create(ResultConstants.RESULT_SUCCESS,message,data);
    }

    public static Result errorData(String message,Object data)
    {
        return create(ResultConstants.RESULT_FAILED,message,data);
    }

    public static Result errorMessage(String message,Object data)
    {
        return create(ResultConstants.RESULT_FAILED,message,data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
