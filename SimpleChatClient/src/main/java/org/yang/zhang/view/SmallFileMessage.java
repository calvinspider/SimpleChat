package org.yang.zhang.view;

import com.sun.javaws.progress.Progress;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 13 17:50
 */

public class SmallFileMessage {

    private Pane root;
    private ImageView fileIcon;
    private Label fileName;
    private ProgressBar processbar;

    public SmallFileMessage(Image icon,String name) {
        try {
            root=FXMLLoader.load(getClass().getResource("/fxml/SmallFileMessage.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        fileIcon=(ImageView) root.lookup("#fileIcon");
        fileIcon.setImage(icon);
        fileName=(Label) root.lookup("#fileName");
        fileName.setText(name);
        processbar=(ProgressBar) root.lookup("#processbar");
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

    public ProgressBar getProcessbar() {
        return processbar;
    }
}
