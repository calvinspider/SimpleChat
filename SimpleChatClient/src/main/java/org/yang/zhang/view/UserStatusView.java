package org.yang.zhang.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 08 31 15:50
 */

public class UserStatusView {
    private Label statusNameLabel;
    private ImageView statusIcon;
    private Scene scene;
    private Pane pane;
    private Image image;

    public UserStatusView(String icon,String name) {
        try {
            scene=new Scene(FXMLLoader.load(getClass().getResource("/fxml/userStatus.fxml")));
        }catch (Exception e){
            e.printStackTrace();
        }
        statusIcon=(ImageView)scene.lookup("#statusIcon");
        image=new Image(icon);
        statusIcon.setImage(image);

        statusNameLabel=(Label)scene.lookup("#statusName");
        statusNameLabel.setText(name);
        pane=(Pane)scene.lookup("#pane");
    }

    public Pane getPane() {
        return pane;
    }

    public Label getStatusNameLabel() {
        return statusNameLabel;
    }

    public ImageView getStatusIcon() {
        return statusIcon;
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public String toString() {
        return statusNameLabel.getText();
    }

    public Image getImage() {
        return image;
    }
}
