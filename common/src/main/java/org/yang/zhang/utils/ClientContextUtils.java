package org.yang.zhang.utils;

import org.yang.zhang.module.User;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 05 10:16
 */

public class ClientContextUtils {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        ClientContextUtils.currentUser = currentUser;
    }
}
