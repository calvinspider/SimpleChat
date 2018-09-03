package org.yang.zhang.view;

import org.yang.zhang.utils.ImageUtiles;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 08 27 15:02
 */

public class LoginedUserView {

    private Pane pane;
    private ImageView icon;
    private Image iconImage;
    private String iconUrl;
    private Label nickName;
    private Label userId;
    private Scene scene;
    private String id;

    public LoginedUserView(String userId,String nickName,String icon){
        try {
            scene=new Scene(FXMLLoader.load(getClass().getResource("/fxml/loginHistory.fxml")));
        }catch (Exception e){
            e.printStackTrace();
        }
        initMember(scene);
        setMemberValue(userId,nickName,icon);
    }

    private void setMemberValue(String userId, String nickName, String icon) {
        this.iconImage=ImageUtiles.getUserIcon(icon);
        this.icon.setImage(iconImage);
        this.userId.setText(userId);
        this.nickName.setText(nickName);
        this.id=userId;
        this.iconUrl=icon;
    }

    private void initMember(Scene scene) {
        pane=(Pane)scene.lookup("#root");
        icon  = (ImageView)scene.lookup("#userIcon");
        userId=(Label)scene.lookup("#userId");
        nickName=(Label)scene.lookup("#nickName");
    }

    @Override
    public String toString() {
        return id;
    }

    public Pane getPane() {
        return pane;
    }

    public ImageView getIcon() {
        return icon;
    }

    public Image getIconImage() {
        return iconImage;
    }

    public Label getNickName() {
        return nickName;
    }

    public Label getUserId() {
        return userId;
    }

    public Scene getScene() {
        return scene;
    }

    public String getId() {
        return id;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
