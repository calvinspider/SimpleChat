package org.yang.zhang.view;

import org.yang.zhang.dto.AddContractDto;
import org.yang.zhang.utils.ImageUtiles;

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
    private Integer userId;

    public AddContractView(AddContractDto dto) {
     try {
         Pane pane=FXMLLoader.load(getClass().getResource("/fxml/addContractItem.fxml"));
         ImageView usericon=(ImageView)pane.lookup("#usericon");
         Label commoncount=(Label)pane.lookup("#commoncount");
         Label username=(Label)pane.lookup("#userName");
         Label userId=(Label)pane.lookup("#userId");
         usericon.setImage(ImageUtiles.getUserIcon(dto.getUserIcon()));
         username.setText(userName);
         userId.setText(dto.getUserId());
         commoncount.setText(commonCount+"位共同好友");
         this.contractPane=pane;
         this.userIcon = dto.getUserIcon();
         this.userName = dto.getUserName();
         this.commonCount = dto.getCommonCount();
         this.userId=Integer.valueOf(dto.getUserId());
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
