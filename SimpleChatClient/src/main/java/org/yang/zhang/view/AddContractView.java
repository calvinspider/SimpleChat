package org.yang.zhang.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 17:45
 */

public class AddContractView {
    private Pane contractPane;
    private String userIcon;
    private String userName;
    private String commonCount;

    public AddContractView(String userIcon, String userName, String commonCount) {
     try {
         Pane pane=FXMLLoader.load(getClass().getResource("/fxml/addContractItem.fxml"));
         ImageView usericon=(ImageView)pane.lookup("#usericon");
         usericon.setImage(new Image("images/personIcon.jpg"));
         Label username=(Label)pane.lookup("#userName");
         username.setText(userName);
         Label commoncount=(Label)pane.lookup("#commoncount");
         commoncount.setText(commonCount+"位共同好友");
         this.contractPane=pane;
         this.userIcon = userIcon;
         this.userName = userName;
         this.commonCount = commonCount;
     }catch (Exception e){
         e.printStackTrace();
     }
    }

    public Pane getContractPane() {
        return contractPane;
    }

    public void setContractPane(Pane contractPane) {
        this.contractPane = contractPane;
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

    public String getCommonCount() {
        return commonCount;
    }

    public void setCommonCount(String commonCount) {
        this.commonCount = commonCount;
    }
}
