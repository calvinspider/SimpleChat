package org.yang.zhang.cellimpl;

import org.yang.zhang.view.ContractItemView;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 27 11:13
 */

public class RoomContractItemViewCellImpl extends ListCell<ContractItemView> {

    public static Callback<ListView<ContractItemView>,ListCell<ContractItemView>> callback=new Callback<ListView<ContractItemView>,ListCell<ContractItemView>>(){
        @Override
        public ListCell<ContractItemView> call(ListView<ContractItemView> param) {
            return new RoomContractItemViewCellImpl();
        }
    };

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