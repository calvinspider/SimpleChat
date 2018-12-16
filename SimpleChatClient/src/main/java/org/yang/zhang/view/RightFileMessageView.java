package org.yang.zhang.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 07 22:23
 */

public class RightFileMessageView {

    private Pane root;
    private ImageView fileIcon;
    private Label fileName;
    private Label fileSize;
    private Label messageLabel;
    private ImageView userIcon;
    private ProgressBar processBar;

    public RightFileMessageView(Image icon, String fileName, String fileSize,Image userIcon) {
        try {
            root=FXMLLoader.load(getClass().getResource("/fxml/FileMessage.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        this.fileIcon=(ImageView)root.lookup("#fileIcon");
        this.userIcon=(ImageView)root.lookup("#userIcon");
        this.fileName=(Label)root.lookup("#fileName");
        this.fileSize=(Label)root.lookup("#fileSize");
        messageLabel=(Label)root.lookup("#messageLabel");
        this.processBar=(ProgressBar)root.lookup("#processBar");
        this.processBar.setProgress(1);
        this.fileIcon.setImage(icon);
        this.fileName.setText(fileName);
        this.fileSize.setText(fileSize);
        this.userIcon.setImage(userIcon);
    }


    public ImageView getUserIcon() {
        return userIcon;
    }

    public Pane getRoot() {
        return root;
    }

    public ImageView getFileIcon() {
        return fileIcon;
    }

    public Label getFileName() {
        return fileName;
    }

    public Label getFileSize() {
        return fileSize;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public ProgressBar getProcessBar() {
        return processBar;
    }
}
