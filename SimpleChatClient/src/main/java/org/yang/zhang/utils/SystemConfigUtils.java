package org.yang.zhang.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.yang.zhang.config.SystemConfig;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.module.User;
import org.yang.zhang.view.LoginedUserView;

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

    public static List<LoginedUserView> findLoginedUsers() {
        List<LoginedUserView> result=new ArrayList<>();
        FileInputStream inputStream=null;
        BufferedReader bufferedReader=null;
        String fileName=Constant.fileRoot+File.separator+Constant.historyUserFileName;
        File file=new File(fileName);
        if(!file.exists()){
            return new ArrayList<>();
        }
        try{
            inputStream = new FileInputStream(fileName);
            bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while((str = bufferedReader.readLine()) != null)
            {
                String[] array=str.split(",");
                result.add(new LoginedUserView(array[0],array[2],array[1]));
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                if(inputStream!=null)
                    inputStream.close();
                if(bufferedReader!=null)
                    bufferedReader.close();
            }catch (Exception e1){
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void saveUserLoginHistory(User user) {
        FileReader fr=null;
        BufferedReader bufferedReader=null;
        BufferedWriter bufferedWriter=null;
        FileWriter fw=null;
        try{
            Boolean userInHistory=false;
            String fileName=Constant.fileRoot+File.separator+Constant.historyUserFileName;
            File file=new File(fileName);
            File dir=new File(Constant.fileRoot);
            if(!dir.exists()){
                dir.mkdir();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            fr = new FileReader(fileName);
            fw = new FileWriter(file, true);
            bufferedReader= new BufferedReader(fr);
            bufferedWriter= new BufferedWriter(fw);
            String str;
            String userid=String.valueOf(user.getId());
            while((str = bufferedReader.readLine()) != null)
            {
                String[] tmp=str.split(",");
                String now=tmp[0];
                System.out.println(tmp[0]+userid);
                if(now.equals(userid)){
                    userInHistory=true;
                    break;
                }
            }
            if(!userInHistory){
                bufferedWriter.write(user.getId()+","+user.getIconUrl()+","+user.getNickName()+"\n");
                bufferedWriter.flush();
            }
            fr.close();
            fw.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
            try {
                if(fr!=null)
                    fr.close();
                if(fw!=null)
                    fw.close();
                if(bufferedReader!=null)
                    bufferedReader.close();
                if(bufferedWriter!=null)
                    bufferedWriter.close();
            }catch (Exception e1){
                e.printStackTrace();
            }
        }
    }
}
