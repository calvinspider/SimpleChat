package org.yang.zhang.cellimpl;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.yang.zhang.view.RecentContractView;

public class RecentContractViewCellImpl extends ListCell<RecentContractView> {

    public static Callback<ListView<RecentContractView>,ListCell<RecentContractView>> callback=new Callback<ListView<RecentContractView>,ListCell<RecentContractView>>(){
        @Override
        public ListCell<RecentContractView> call(ListView<RecentContractView> param) {
            return new RecentContractViewCellImpl();
        }
    };


    @Override
    public void updateItem(RecentContractView pane, boolean empty) {
        super.updateItem(pane,empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setGraphic(pane.getRecentContractPane());
        }
    }


}
