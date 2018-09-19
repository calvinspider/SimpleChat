package org.yang.zhang.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

import org.yang.zhang.module.FileMessage;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 19 15:14
 */

public class FileUtils {

    public static void saveFile(FileMessage ef, String fileDir) {
        byte[] bytes = ef.getBytes();
        String fileName=ef.getFileName();
        File file=null;
        FileOutputStream out=null;
        RandomAccessFile randomAccessFile=null;
        try {
            file= new File(fileDir + File.separator + fileName);
            if(!file.exists()){
                randomAccessFile= new RandomAccessFile(file, "rw");
                randomAccessFile.write(bytes);
            }else{
                out= new FileOutputStream(file,true);
                out.write(bytes);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(randomAccessFile!=null){
                    randomAccessFile.close();
                }
                if(out!=null){
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
