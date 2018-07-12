package org.yang.zhang.fxcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 17:51
 */
@FXMLController
public class ContractItemController implements Initializable {

    @FXML
    ImageView usericon;
    @FXML
    Label userName;
    @FXML
    Label commoncount;
    @FXML
    Button addBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addContract(ActionEvent event){
        System.out.println("添加好友");
    }
}
