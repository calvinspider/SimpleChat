package org.yang.zhang.cellimpl;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.yang.zhang.view.ChatRoomItemView;

public class ChatRoomViewCellImpl extends ListCell<ChatRoomItemView> {

    public static Callback<ListView<ChatRoomItemView>,ListCell<ChatRoomItemView>> callback=new Callback<ListView<ChatRoomItemView>,ListCell<ChatRoomItemView>>(){
        @Override
        public ListCell<ChatRoomItemView> call(ListView<ChatRoomItemView> param) {
            return new ChatRoomViewCellImpl();
        }
    };

    @Override
    public void updateItem(ChatRoomItemView pane, boolean empty) {
        super.updateItem(pane,empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setGraphic(pane.getRoomPane());
        }
    }
}
