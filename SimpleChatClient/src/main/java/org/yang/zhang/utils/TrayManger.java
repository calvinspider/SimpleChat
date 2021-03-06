package org.yang.zhang.utils;

import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.yang.zhang.constants.Constant;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 27 14:09
 */

public class TrayManger {

    private Stage stage;
    public  void tray(Stage stage){
        File file=new File(Constant.fileRoot+File.separator+Constant.trayFile);
        String iconImageLoc="file:"+file.getAbsolutePath();
        this.stage=stage;
        try {
            java.awt.Toolkit.getDefaultToolkit();
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support");
                return;
            }
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(iconImageLoc);
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);
            Platform.setImplicitExit(false);
            trayIcon.addActionListener(event -> {
                Platform.runLater(this::showStage);
            });
            tray.add(trayIcon);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }
}
