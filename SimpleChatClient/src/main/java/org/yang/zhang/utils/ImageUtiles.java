package org.yang.zhang.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.yang.zhang.constants.Constant;

import javafx.scene.image.Image;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 18 17:56
 */

public class ImageUtiles {
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
}
