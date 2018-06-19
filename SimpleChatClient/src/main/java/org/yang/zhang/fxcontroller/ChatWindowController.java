package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class ChatWindowController  implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Pane chatHistory;

    @FXML
    private TextArea chatArea;

    @FXML
    private Button closeBtn;

    @FXML
    private Button sendBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void sendMessage(ActionEvent event) {
        System.out.println("发送消息");
    }


}
