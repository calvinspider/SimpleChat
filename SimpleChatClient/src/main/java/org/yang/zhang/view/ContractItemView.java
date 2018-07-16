package org.yang.zhang.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 12:00
 */

public class ContractItemView {

    private Pane itemPane;
    private String userIcon;
    private String userName;
    private String personWord;
    private TextField groupName;

    public ContractItemView(String userIcon, String userName, String personWord,Boolean group) {
        Pane pane;
        try {
            if(!group){
                pane=FXMLLoader.load(getClass().getResource("/fxml/contractItem.fxml"));
                ImageView usericon = (ImageView)pane.lookup("#usericon");
                usericon.setImage(new Image("images/personIcon.jpg"));
                Label username = (Label) pane.lookup("#username");
                Label personword = (Label) pane.lookup("#personword");
                username.setText(userName);
                personword.setText(personWord);
            }else{
                pane=FXMLLoader.load(getClass().getResource("/fxml/groupItem.fxml"));
                groupName=(TextField) pane.lookup("#groupName");
                groupName.setEditable(false);
                groupName.setText(userName);
                groupName.setStyle("-fx-background-color: #A1BBD8;");
                pane.setStyle("-fx-background-color: #A1BBD8;");
            }
            this.itemPane=pane;
            this.userIcon = userIcon;
            this.userName = userName;
            this.personWord = personWord;
        }catch (Exception e){

        }
    }

    public Pane getItemPane() {
        return itemPane;
    }

    public void setItemPane(Pane itemPane) {
        this.itemPane = itemPane;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonWord() {
        return personWord;
    }

    public void setPersonWord(String personWord) {
        this.personWord = personWord;
    }

    public TextField getGroupName() {
        return groupName;
    }

    public void setGroupName(TextField groupName) {
        this.groupName = groupName;
    }
}
