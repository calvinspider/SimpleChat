package org.yang.zhang.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.ConcurrentHashMap;

import org.yang.zhang.module.FileMessage;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 19 15:14
 */

public class FileUtils {

    
    public static ConcurrentHashMap<String,FileOutputStream> fdMap=new ConcurrentHashMap<>();


    public static void saveFile(FileMessage ef, String fileDir) {
        byte[] bytes = ef.getBytes();
        String fileName=ef.getFileName();
        File file;
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=fdMap.get(fileName);
            if("end".equals(ef.getTag())) {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    fdMap.remove(ef.getFileName());
                    return;
                }
            }
            if(fileOutputStream==null){
                file= new File(fileDir + File.separator + fileName);
                fileOutputStream= new FileOutputStream(file,true);
                fdMap.put(fileName,fileOutputStream);
            }
            fileOutputStream.write(bytes,0,ef.getDataLength());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
