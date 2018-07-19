package org.yang.zhang.view;

import java.util.Date;

import org.yang.zhang.constants.Constant;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.utils.DateUtils;
import org.yang.zhang.utils.ImageUtiles;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 14:16
 */

public class RecentContractView {
    private Pane recentContractPane;
    private Date messageTime;
    private String lastMessage;
    private String userIcon;

    public RecentContractView(RecentContract recentContract,Integer mainId) {
        try {
            Pane pane=FXMLLoader.load(getClass().getResource("/fxml/RecentContract.fxml"));
            Label namelabel = (Label)pane.lookup("#namelabel");
            namelabel.setText(recentContract.getContractId());
            pane.setId(recentContract.getContractId());
            Label messagelabel = (Label)pane.lookup("#messagelabel");
            messagelabel.setText(recentContract.getLastMessage());
            Label timelabel = (Label)pane.lookup("#timelabel");
            timelabel.setText(DateUtils.formatDate(recentContract.getLastMessageDate(),"YYYY-MM-dd"));
            ImageView contracticon=(ImageView)pane.lookup("#contracticon");
            contracticon.setImage(ImageUtiles.getHttpImage(Constant.serverHost+"/static/images/userIcon/"+recentContract.getIcon()));
            contracticon.setFitWidth(45);
            contracticon.setFitHeight(45);
            this.messageTime = recentContract.getLastMessageDate();
            this.lastMessage = recentContract.getLastMessage();
            this.userIcon = "";
            this.recentContractPane=pane;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Pane getRecentContractPane() {
        return recentContractPane;
    }

    public void setRecentContractPane(Pane recentContractPane) {
        this.recentContractPane = recentContractPane;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }
}
