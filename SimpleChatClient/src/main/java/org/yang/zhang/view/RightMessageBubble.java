package org.yang.zhang.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 19 15:53
 */

public class RightMessageBubble {
    private Pane pane;
    public RightMessageBubble(String message, Image icon) {
        try {
            pane= FXMLLoader.load(getClass().getResource("/fxml/RightMessageBubble.fxml"));
            TextArea textArea=(TextArea)pane.lookup("#messageTextRight");
            textArea.wrapTextProperty().set(true);
            textArea.setEditable(false);
            textArea.appendText(message);
            textArea.textProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    Text text = new Text();
                    text.setWrappingWidth(textArea.getWidth());
                    text.setText((String)newValue);
                    Double height=text.getLayoutBounds().getHeight();
                    Double width=text.getLayoutBounds().getWidth();
                    textArea.setPrefHeight(height+10);
                    System.out.println(width);
                    textArea.setPrefWidth(width);
                    pane.setPrefHeight(height+20);

                }
            });
            textArea.appendText("");
            ImageView imageView=(ImageView)pane.lookup("#userIcon");
            imageView.setImage(icon);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Pane getPane() {
        return pane;
    }
}
