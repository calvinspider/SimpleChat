package org.yang.zhang.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;

import org.yang.zhang.module.User;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 05 10:16
 */

public class UserUtils {

    private static User currentUser;
    private static Image userIcon;
    private static Map<Integer,User> userMap=new HashMap<>();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserUtils.currentUser = currentUser;
    }

    public static Integer getCurrentUserId(){
        return currentUser.getId();
    }

    public static String getCurrentUserNickName(){
        return currentUser.getNickName();
    }

    public static String getCurrentUserIcon(){
        return currentUser.getIconUrl();
    }

    public static Image getUserIcon() {
        return userIcon;
    }

    public static void setUserIcon(Image userIcon) {
        UserUtils.userIcon = userIcon;
    }

    public static void setUser(Integer id,User user){
        userMap.put(id,user);
    }

    public static User getUser(Integer id){
        return userMap.get(id);
    }
}
