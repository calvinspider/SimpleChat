package org.yang.zhang.view;

import org.yang.zhang.dto.ChatRoomDto;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.utils.SpringContextUtils;
import org.yang.zhang.utils.StageManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 13:27
 */

public class ChatRoomView {
    private Integer id;
    private Stage chatRommStage;
    private Image icon;
    private ImageView imageView;
    private Label nameLabel;
    private String name;
    private Label tagLabel;
    private String tag;
    private ScrollPane publicMsgPane;
    private ScrollPane memberPane;
    private TextArea textArea;
    private ScrollPane chatPane;
    private Pane root;

    public ChatRoomView(String roomId,Image icon){
        try {
            ChatService chatService=SpringContextUtils.getBean("chatService");
            ChatRoomDto chatRoomDto=chatService.getRoomDetail(Integer.valueOf(roomId));
            root=FXMLLoader.load(getClass().getResource("/fxml/chatRoomWindow.fxml"));
            Label nameLabel=(Label) root.lookup("#nameLabel");
            nameLabel.setText(chatRoomDto.getChatRoom().getName());
            Label tagLabel=(Label) root.lookup("#tagLabel");
            ScrollPane publicMsgPane=(ScrollPane)root.lookup("#publicMsgPane");
            ScrollPane memberPane=(ScrollPane)root.lookup("#memberPane");
            TextArea textArea=(TextArea)root.lookup("#textArea");
            ScrollPane chatPane=(ScrollPane)root.lookup("#chatPane");
            imageView=(ImageView)root.lookup("#imageView");
            imageView.setImage(icon);

            chatRommStage=new Stage();
            chatRommStage.setScene(new Scene(root));
            chatRommStage.show();
            StageManager.registerStage("CHATROOM"+roomId,chatRommStage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Stage getChatRommStage() {
        return chatRommStage;
    }

    public void setChatRommStage(Stage chatRommStage) {
        this.chatRommStage = chatRommStage;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Label getTagLabel() {
        return tagLabel;
    }

    public void setTagLabel(Label tagLabel) {
        this.tagLabel = tagLabel;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ScrollPane getPublicMsgPane() {
        return publicMsgPane;
    }

    public void setPublicMsgPane(ScrollPane publicMsgPane) {
        this.publicMsgPane = publicMsgPane;
    }

    public ScrollPane getMemberPane() {
        return memberPane;
    }

    public void setMemberPane(ScrollPane memberPane) {
        this.memberPane = memberPane;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public ScrollPane getChatPane() {
        return chatPane;
    }

    public void setChatPane(ScrollPane chatPane) {
        this.chatPane = chatPane;
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }
}
