package org.yang.zhang.utils;

import org.yang.zhang.enums.IDType;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 26 18:31
 */
//ROOM.CHAT,ID
public class IDUtils {
    public static Integer parseID(String id, IDType type){
        switch (type){
            case CHATWINDOW:
                return Integer.valueOf(id.substring(id.indexOf("C")+1,id.length()));

            case ROOMWINDOW:
                return Integer.valueOf(id.substring(id.indexOf("R")+1,id.length()));
            case ID:
                return Integer.valueOf(id.substring(id.indexOf("I")+1,id.length()));
        }
        return null;
    }
    public static String formatID(Integer id, IDType type){
        switch (type){
            case CHATWINDOW:
                return "C"+id;
            case ROOMWINDOW:
                return "R"+id;
            case ID:
                return "I"+id;
        }
        return null;
    }

    public static String formatID(String id, IDType type){
        return formatID(Integer.valueOf(id),type);
    }
}
