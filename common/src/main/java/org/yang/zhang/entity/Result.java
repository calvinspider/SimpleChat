package org.yang.zhang.entity;

import java.io.Serializable;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 04 28 10:56
 */

public class Result<T> implements Serializable {

    private String code;
    private String message;
    private T data;

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result() {

    }

    public static <T> Result<T> create(String code, String message, T data){
        return new Result(code,message,data);
    }

    public static <T> Result<T> create(String code,String message){
        return new Result(code,message,null);
    }

    public static <T> Result<T> successData(T data)
    {
        return create(ResultConstants.RESULT_SUCCESS,null,data);
    }

    public static <T> Result<T> successData(String message,T data)
    {
        return create(ResultConstants.RESULT_SUCCESS,message,data);
    }

    public static <T> Result<T> errorData(String message,T data)
    {
        return create(ResultConstants.RESULT_FAILED,message,data);
    }

    public static <T> Result<T> errorMessage(String message)
    {
        return create(ResultConstants.RESULT_FAILED,message);
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

    public void setData(T data) {
        this.data = data;
    }
}
