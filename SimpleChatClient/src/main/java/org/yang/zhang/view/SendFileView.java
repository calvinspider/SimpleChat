package org.yang.zhang.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 13 16:52
 */
public class SendFileView{

    private ScrollPane root;
    private VBox vBox;

    public SendFileView() {
        try {
            root=FXMLLoader.load(getClass().getResource("/fxml/SnedFileWindow.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        root = (ScrollPane)root.lookup("#root");
    }

    public ScrollPane getRoot() {
        return root;
    }

    public VBox getvBox() {
        return (VBox) root.getContent();
    }
}
