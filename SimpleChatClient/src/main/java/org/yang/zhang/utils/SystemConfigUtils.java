package org.yang.zhang.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.yang.zhang.config.SystemConfig;
import org.yang.zhang.constants.Constant;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 08 29 11:40
 */

public class SystemConfigUtils {

    public static void flushConfig(SystemConfig config){
        FileOutputStream outputStream=null;
        String fileName=Constant.fileRoot+File.separator+Constant.SystemConfigFileName;
        File file=new File(fileName);
        try {
            if(!file.exists()){
                File dir=new File(Constant.fileRoot);
                if(!dir.exists()){
                    dir.mkdir();
                }
                file.createNewFile();
            }
            outputStream = new FileOutputStream(fileName);
            outputStream.write(JsonUtils.toJson(config).getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                }catch (Exception e){e.printStackTrace();}
            }
        }
    }

    public static SystemConfig loadSystemConfig() {
        FileInputStream inputStream=null;
        String fileName=Constant.fileRoot+File.separator+Constant.SystemConfigFileName;
        File file=new File(fileName);
        try {
            if(!file.exists()){
                File dir=new File(Constant.fileRoot);
                if(!dir.exists()){
                    dir.mkdir();
                }
                file.createNewFile();
            }
            inputStream = new FileInputStream(fileName);
            Long filelength = file.length();
            byte[] filecontent = new byte[filelength.intValue()];
            inputStream.read(filecontent);
            String config=new String(filecontent);
            SystemConfig systemConfig= JsonUtils.fromJson(config,SystemConfig.class);
            if(systemConfig==null){
                return new SystemConfig();
            }else{
                return systemConfig;
            }
        }catch (Exception e){
            e.printStackTrace();
            return new SystemConfig();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                }catch (Exception e){e.printStackTrace();}
            }
        }
    }
}
