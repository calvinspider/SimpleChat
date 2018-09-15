package org.yang.zhang.utils;

import java.util.HashMap;
import java.util.Map;

import org.yang.zhang.enums.IDType;
import org.yang.zhang.view.SendFileView;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 14 16:15
 */

/**
 * 文件发送窗口管理类
 */
public class SendFileWindowManager {

    private static Map<String, SendFileView> viewMap = new HashMap<>();
    private static Map<String, Stage> stageMap = new HashMap<>();


    public static void registerStage(String name, Stage sendFileView) {
        stageMap.put(name, sendFileView);
    }

    public static Stage getStage(String name) {
        return stageMap.get(name);
    }


    public static void registerView(String name, SendFileView sendFileView) {
        viewMap.put(name, sendFileView);
    }

    public static void unregisterView(String name) {
        stageMap.remove(name);
        viewMap.remove(name);
    }

    public static SendFileView getView(String name) {
        return viewMap.get(name);
    }

    public static Stage openStage(String userId){
        Stage chatWindow=ChatViewManager.getStage(userId).getChatStage();
        Stage fileWindow=SendFileWindowManager.getStage(userId);
        if(fileWindow!=null){
            fileWindow.setX(chatWindow.getX()+680);
            fileWindow.setY(chatWindow.getY()+60);
            fileWindow.show();
        }else{
            Stage window=new Stage();
            SendFileView sendFileView=new SendFileView();
            Scene scene=new Scene(sendFileView.getRoot());
            window.setScene(scene);
            window.setX(chatWindow.getX()+680);
            window.setY(chatWindow.getY()+60);
            window.initStyle(StageStyle.UNDECORATED);
            sendFileView.getRoot().setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            sendFileView.getRoot().setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            window.show();
            registerStage(userId,window);
            registerView(userId,sendFileView);
        }
        return fileWindow;
    }

    public static void closeStage(String userId) {
        Stage window=getStage(userId);
        if(window!=null){
            window.close();
            unregisterView(userId);
        }
    }

    public static void hideStage(String userId){
        Stage window=getStage(userId);
        if(window!=null){
            window.hide();
        }
    }
}
