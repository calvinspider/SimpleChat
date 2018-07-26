package org.yang.zhang.view;

import java.util.List;

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
    private Integer id;
    private Stage chatRommStage;
    private Image icon;
    private ImageView imageView;
    private Label nameLabel;
    private String name;
    private Label tagLabel;
    private String tag;
    private ListView publicMsgPane;
    private TitledPane friendPane;
    private ListView<ContractItemView> friendList=new ListView<>();
    private TextArea textArea;
    private ScrollPane chatPane;


    public ChatRoomView(String roomId,Image icon){
        try {
            ChatService chatService=SpringContextUtils.getBean("chatService");
            ChatRoomDto chatRoomDto=chatService.getRoomDetail(Integer.valueOf(roomId));
            Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/fxml/chatRoomWindow.fxml")));
            Label roomIdLabel=(Label)scene.lookup("#roomId");
            Label nameLabel=(Label) scene.lookup("#nameLabel");
            imageView=(ImageView)scene.lookup("#imageView");
            friendPane=(TitledPane) scene.lookup("#friendPane");
            chatPane=(ScrollPane)scene.lookup("#chatPane");
            roomIdLabel.setText(roomId);
            nameLabel.setText(chatRoomDto.getChatRoom().getName());
            imageView.setImage(icon);
            initMemberPane(chatRoomDto.getUsers());
            initRecentChat(chatRoomDto.getRecentMessage());
            chatRommStage=new Stage();
            chatRommStage.setScene(scene);
            chatRommStage.show();
            chatRommStage.setResizable(false);
            StageManager.registerStage(IDUtils.formatID(roomId,IDType.ROOMWINDOW),chatRommStage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initRecentChat(List<RoomChatInfoDto> recentMessage) {
        VBox chatBox=new VBox();
        chatBox.setPrefWidth(685);
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

        friendList.setCellFactory(new Callback<ListView<ContractItemView>,ListCell<ContractItemView>>(){
            @Override
            public ListCell<ContractItemView> call(ListView<ContractItemView> param) {
                return new ContractItemViewCellImpl();
            }
        });

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


    private final class ContractItemViewCellImpl extends ListCell<ContractItemView>{
        @Override
        public void updateItem(ContractItemView pane, boolean empty) {
            super.updateItem(pane,empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(pane.getItemPane());
            }
        }
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
