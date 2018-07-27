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
import org.yang.zhang.service.ContractService;
import org.yang.zhang.utils.ImageUtiles;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.view.SearchContractItemView;

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
            List<AddContractDto> list=contractService.searchContract(UserUtils.getCurrentUserId(),searchField.getText());
            if(list.size()==0){
                Label label=new Label("未找到符合条件的用户!");
                friendList.add(label,0,0);
                friendList.setAlignment(Pos.CENTER);
                return;
            }
            int j=0;
            for (int i=0;i<list.size();i++){
                AddContractDto dto=list.get(i);
                SearchContractItemView contractView=new SearchContractItemView(ImageUtiles.getUserIcon(dto.getUserIcon()),dto.getUserName(),dto.getUserId(),dto.getCommonCount());
                Pane pane=contractView.getItemPane();
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
      if(list.size()==0){
          Label label=new Label("未找到符合条件的用户!");
          friendList.add(label,0,0);
          friendList.setAlignment(Pos.CENTER);
          return;
      }
      for (int i=0;i<list.size();i++){
          AddContractDto dto=list.get(i);
          SearchContractItemView contractView=new SearchContractItemView(ImageUtiles.getUserIcon(dto.getUserIcon()),dto.getUserName(),dto.getUserId(),dto.getCommonCount());
          Pane pane=contractView.getItemPane();
          if(i!=0&&i%5==0){
              j+=1;
          }
          friendList.add(pane,i%5,j);
      }
    }


}
