package org.yang.zhang.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.yang.zhang.constants.Constant;

import javafx.scene.image.Image;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 18 17:56
 */

public class ImageUtiles {

    public static Map<Integer,Image> userIconMap=new HashMap<>();

    public static Image getHttpImage(String path){
        try {
            URL url=new URL(path);
            URLConnection conn = url.openConnection();
            InputStream stream = conn.getInputStream();
            return new Image(stream);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Image getUserIcon(String uerIcon){
        return getHttpImage(Constant.serverHost+"/static/images/userIcon/"+uerIcon);
    }

    public static void setUserIcon(Integer id,Image icon){
        userIconMap.put(id,icon);
    }

    public static Image getUserIcon(Integer id){
        return userIconMap.get(id);
    }

}
