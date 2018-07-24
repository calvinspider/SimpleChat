package org.yang.zhang.fxcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.service.impl.ChatServiceImpl;
import org.yang.zhang.utils.SpringContextUtils;
import org.yang.zhang.utils.UserUtils;

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
    @FXML
    Label userId;

    private ChatService chatService;

    private SearchContractController searchContractController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addContract(ActionEvent event){
        chatService=SpringContextUtils.getBean("chatService");
        searchContractController=SpringContextUtils.getBean("searchContractController");
        Integer id=Integer.valueOf(userId.getText());
        Integer userid=UserUtils.getCurrentUserId();
        chatService.addFriend(userid,id);
        searchContractController.init(String.valueOf(UserUtils.getCurrentUserId()));
    }
}
