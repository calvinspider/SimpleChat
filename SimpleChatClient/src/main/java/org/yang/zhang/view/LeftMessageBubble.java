package org.yang.zhang.view;


import javax.xml.soap.Text;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 19 15:53
 */

public class LeftMessageBubble {
    private Pane pane;
    public LeftMessageBubble(String message, Image icon) {
        try {
            pane= FXMLLoader.load(getClass().getResource("/fxml/LeftMessageBubble.fxml"));
            TextArea textArea=(TextArea)pane.lookup("#messageTextLeft");
            textArea.wrapTextProperty().set(true);
            textArea.setEditable(false);
            textArea.textProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    javafx.scene.text.Text text = new javafx.scene.text.Text();
                    text.setWrappingWidth(textArea.getWidth());
                    text.setText((String)newValue);
                    double height=text.getLayoutBounds().getHeight();
                    double width = text.getLayoutBounds().getWidth();
                    double padding = 20 ;
                    textArea.setMaxWidth(width+padding);
                    textArea.setPrefHeight(height);
                    pane.setPrefHeight(height+40);
                }
            });
            textArea.appendText(message);
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
