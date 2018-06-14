package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class MainController  implements Initializable {

    @FXML
    private TreeView<Label> GroupList;

    @FXML
    private ListView<Label> messageList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //好友列表属性
        GroupList.setScaleShape(false);

        //根节点
         TreeItem<Label> rootItem = new TreeItem<Label>();
         Label root=new Label();
         root.setVisible(false);
         rootItem.setValue(root);
         GroupList.setRoot(rootItem);
         GroupList.setShowRoot(false);
         rootItem.setExpanded(true);
         //子节点
         for(int i=0;i<30;i++){
             TreeItem<Label> i1 = new TreeItem<Label>();
             ImageView imageView=new ImageView("images/personIcon.jpg");
             imageView.setFitWidth(25);
             imageView.setFitHeight(25);
             Label sign=new Label("个性签名",imageView);

             i1.setValue(sign);
             rootItem.getChildren().add(i1);
         }
    }
}
