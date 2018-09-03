package org.yang.zhang.service;
import org.yang.zhang.entity.Result;
/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 13 16:27
 */

public interface LoginService {

    Result login(String userName,String passWord,Integer status);

    Result<Void> findPassWord(String text);
}
