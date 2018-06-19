package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.yang.zhang.utils.StageManager;
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

         TreeItem<Label> i1 = new TreeItem<Label>();
         ImageView imageView=new ImageView("images/personIcon.jpg");
         imageView.setFitWidth(25);
         imageView.setFitHeight(25);
         Label sign=new Label("10001",imageView);
         i1.setValue(sign);
         rootItem.getChildren().add(i1);

         i1 = new TreeItem<Label>();
         imageView=new ImageView("images/personIcon.jpg");
         imageView.setFitWidth(25);
         imageView.setFitHeight(25);
         sign=new Label("10002",imageView);
         i1.setValue(sign);
         rootItem.getChildren().add(i1);

        GroupList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue,Object newValue) {
                TreeItem<Label> selectedItem = (TreeItem<Label>) newValue;
                String id=selectedItem.getValue().getText();
                openChatWindow(id);
            }
        });
    }

    private void openChatWindow(String id) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource( "/fxml/chatWindow.fxml"));
            Label nameLabel = (Label)root.lookup("#nameLabel");
            nameLabel.setText(id);
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            StageManager.registerStage("chat"+id,stage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
