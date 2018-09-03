package org.yang.zhang.cellimpl;

import org.yang.zhang.view.UserStatusView;
import org.yang.zhang.view.UserStatusView;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 03 14:08
 */

public class UserStatusCellImpl extends ListCell<UserStatusView> {
    public static Callback<ListView<UserStatusView>,ListCell<UserStatusView>> callback=new Callback<ListView<UserStatusView>,ListCell<UserStatusView>>(){
        @Override
        public ListCell<UserStatusView> call(ListView<UserStatusView> param) {
            return new UserStatusCellImpl();
        }
    };

    @Override
    public void updateItem(UserStatusView pane, boolean empty) {
        super.updateItem(pane, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setGraphic(pane.getPane());
        }
    }
}
