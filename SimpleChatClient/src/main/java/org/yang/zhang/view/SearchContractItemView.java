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

public class SearchContractItemView {

    private String id;//userId
    private Pane itemPane;
    private ImageView usericon;
    private Label commoncount;
    private Label username;
    private Label userId;
    private Image icon;
    private String nickName;
    private String count;


    public SearchContractItemView(Image icon,String nickName,String userId,String count) {
     try {
         itemPane=FXMLLoader.load(getClass().getResource("/fxml/addContractItem.fxml"));
     }catch (Exception e){
         e.printStackTrace();
     }
     initMember();
     initMemberValue(icon,nickName,userId,count);
    }

    private void initMemberValue(Image icon, String nickName, String userId, String count) {
        this.usericon.setImage(icon);
        this.username.setText(nickName);
        this.userId.setText(userId);
        this.commoncount.setText(count+"位共同好友");
        this.id=userId;
        this.icon=icon;
        this.nickName=nickName;
        this.count=count;
    }

    private void initMember() {
     usericon=(ImageView)itemPane.lookup("#usericon");
     commoncount=(Label)itemPane.lookup("#commoncount");
     username=(Label)itemPane.lookup("#userName");
     userId=(Label)itemPane.lookup("#userId");
    }

    public String getId() {
        return id;
    }

    public Image getIcon() {
        return icon;
    }

    public String getNickName() {
        return nickName;
    }

    public String getCount() {
        return count;
    }

    public Pane getItemPane() {
        return itemPane;
    }

    public ImageView getUsericon() {
        return usericon;
    }

    public Label getCommoncount() {
        return commoncount;
    }

    public Label getUsername() {
        return username;
    }

    public Label getUserId() {
        return userId;
    }
}
