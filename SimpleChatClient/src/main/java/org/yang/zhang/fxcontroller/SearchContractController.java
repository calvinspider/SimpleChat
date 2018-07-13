package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.dto.AddContractDto;
import org.yang.zhang.module.User;
import org.yang.zhang.service.ContractService;
import org.yang.zhang.utils.ClientContextUtils;
import org.yang.zhang.view.AddContractView;

@FXMLController
public class SearchContractController implements Initializable {

    @Autowired
    private ContractService contractService;

    @FXML
    CheckBox online;

    @FXML
    ChoiceBox<String> sex;

    @FXML
    ChoiceBox<String> age;

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
        friendList.getChildren().clear();
        if(StringUtils.isNotBlank(searchField.getText())){
            List<AddContractDto> list=contractService.searchContract(ClientContextUtils.getCurrentUser().getId(),searchField.getText());
            if(list.size()==0){
                Label label=new Label("未找到符合条件的用户!");
                friendList.add(label,0,0);
                friendList.setAlignment(Pos.CENTER);
                return;
            }
            int j=0;
            for (int i=0;i<list.size();i++){
                AddContractView contractView=new AddContractView("",list.get(i).getUserName(),list.get(i).getCommonCount());
                Pane pane=contractView.getContractPane();
                if(i!=0&&i%5==0){
                    j+=1;
                }
                friendList.add(pane,i%5,j);
            }
        }
    }

    public void init(String userName){
      friendList.getChildren().clear();
      List<AddContractDto> list=contractService.getRecommendContract(userName);
      int j=0;
      for (int i=0;i<list.size();i++){
          AddContractView contractView=new AddContractView("",list.get(i).getUserName(),list.get(i).getCommonCount());
          Pane pane=contractView.getContractPane();
          if(i!=0&&i%5==0){
              j+=1;
          }
          friendList.add(pane,i%5,j);
      }
    }


}
