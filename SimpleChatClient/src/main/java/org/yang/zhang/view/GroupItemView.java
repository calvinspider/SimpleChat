package org.yang.zhang.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 12:16
 */

public class GroupItemView {
    private Pane groupItepane;
    private String groupName;

    public GroupItemView(String groupName) {
        try {
            Pane pane=FXMLLoader.load(getClass().getResource("/fxml/groupItem.fxml"));
            Label label=(Label)pane.lookup("#groupName");
            label.setText(groupName);
            this.groupName = groupName;
            this.groupItepane=pane;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Pane getGroupItepane() {
        return groupItepane;
    }

    public void setGroupItepane(Pane groupItepane) {
        this.groupItepane = groupItepane;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
