package org.yang.zhang.view;

import java.util.List;

import org.yang.zhang.cellimpl.RoomContractItemViewCellImpl;
import org.yang.zhang.dto.ChatRoomDto;
import org.yang.zhang.dto.RoomChatInfoDto;
import org.yang.zhang.enums.BubbleType;
import org.yang.zhang.enums.IDType;
import org.yang.zhang.fxcontroller.MainController;
import org.yang.zhang.module.ChatRoom;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.RoomChatInfo;
import org.yang.zhang.module.User;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.utils.ChatUtils;
import org.yang.zhang.utils.DateUtils;
import org.yang.zhang.utils.IDUtils;
import org.yang.zhang.utils.ImageUtiles;
import org.yang.zhang.utils.SpringContextUtils;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.utils.UserUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 13:27
 */

public class ChatRoomView {

    private String id;
    private Scene scene;
    private Stage stage;
    private Label roomIdLabel;
    private Label nameLabel;
    private ImageView icon;
    private TitledPane friendPane;
    private ScrollPane chatPane;
    private ListView<ContractItemView> friendList=new ListView<>();

    public  static Double CHATBOXDEFAULTWIDTH=685D;

    public ChatRoomView(String roomId,Image icon){
        try {
            scene=new Scene(FXMLLoader.load(getClass().getResource("/fxml/chatRoomWindow.fxml")));
        }catch (Exception e){
            e.printStackTrace();
        }
        ChatService chatService=SpringContextUtils.getBean("chatService");
        ChatRoomDto chatRoomDto=chatService.getRoomDetail(Integer.valueOf(roomId));
        initMember();
        initMemberValue(chatRoomDto,roomId,icon);
        initStage(roomId);
        show();
    }

    private void show() {
        stage.show();
    }

    private void initStage(String roomId) {
        stage=new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        StageManager.registerStage(IDUtils.formatID(roomId,IDType.ROOMWINDOW),stage);
    }

    private void initMemberValue(ChatRoomDto chatRoomDto,String roomId,Image icon) {
        this.roomIdLabel.setText(roomId);
        this.nameLabel.setText(chatRoomDto.getChatRoom().getName());
        this.icon.setImage(icon);
        this.id=roomId;
        initMemberPane(chatRoomDto.getUsers());
        initRecentChat(chatRoomDto.getRecentMessage());
    }

    private void initMember() {
        this.roomIdLabel=(Label)scene.lookup("#roomIdLabel");
        this.nameLabel=(Label) scene.lookup("#nameLabel");
        this.icon=(ImageView)scene.lookup("#imageView");
        this.friendPane=(TitledPane) scene.lookup("#friendPane");
        this.chatPane=(ScrollPane)scene.lookup("#chatPane");
    }

    private void initRecentChat(List<RoomChatInfoDto> recentMessage) {
        VBox chatBox=new VBox();
        chatPane.setContent(chatBox);
        chatBox.setPrefWidth(CHATBOXDEFAULTWIDTH);
        Integer id=UserUtils.getCurrentUserId();
        for (RoomChatInfoDto chatInfo:recentMessage){
            if(!id.equals(chatInfo.getUserId())){
                ChatUtils.appendBubble(chatPane,BubbleType.LEFT,chatInfo.getMessage(),ImageUtiles.getUserIcon(chatInfo.getIcon()),670D);
            }else{
                ChatUtils.appendBubble(chatPane,BubbleType.RIGHT,chatInfo.getMessage(),UserUtils.getUserIcon(),670D);
            }
        }
    }

    private void initMemberPane(List<User> users) {
        friendList.setCellFactory(RoomContractItemViewCellImpl.callback);
        ObservableList<ContractItemView> items =FXCollections.observableArrayList();
        for (User user:users){
            user.setPersonWord("");
            ContractItemView contractItemView=new ContractItemView(user);
            contractItemView.setId(String.valueOf(user.getId()));
            items.add(contractItemView);
        }
        friendList.setItems(items);
        friendPane.setContent(friendList);
        friendList.setOnMouseClicked(click->{
            if (click.getClickCount() == 2) {
                ContractItemView selectedItem = friendList.getSelectionModel().getSelectedItem();
                String userid=selectedItem.getId();
                if (userid != null) {
                    ChatUtils.openChatWindow(Integer.valueOf(userid),selectedItem.getNickName(),ImageUtiles.getUserIcon(Integer.valueOf(userid)));
                }
            }
        });
    }

    public String getId() {
        return id;
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public Label getRoomIdLabel() {
        return roomIdLabel;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public ImageView getIcon() {
        return icon;
    }

    public TitledPane getFriendPane() {
        return friendPane;
    }

    public ScrollPane getChatPane() {
        return chatPane;
    }

    public ListView<ContractItemView> getFriendList() {
        return friendList;
    }
}
