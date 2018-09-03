package org.yang.zhang.cellimpl;

import org.yang.zhang.view.ChatRoomItemView;
import org.yang.zhang.view.LoginedUserView;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 03 13:58
 */

public class LoginedUserCellImpl extends ListCell<LoginedUserView> {

    public static Callback<ListView<LoginedUserView>,ListCell<LoginedUserView>> callback=new Callback<ListView<LoginedUserView>,ListCell<LoginedUserView>>(){
        @Override
        public ListCell<LoginedUserView> call(ListView<LoginedUserView> param) {
            return new LoginedUserCellImpl();
        }
    };

    @Override
    public void updateItem(LoginedUserView pane, boolean empty) {
        super.updateItem(pane, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setGraphic(pane.getPane());
        }
    }
}
