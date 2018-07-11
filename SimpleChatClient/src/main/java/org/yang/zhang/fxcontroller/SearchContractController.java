package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class SearchContractController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    @FXML
    private GridPane friendList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void searchFriend(ActionEvent event){


    }



}
