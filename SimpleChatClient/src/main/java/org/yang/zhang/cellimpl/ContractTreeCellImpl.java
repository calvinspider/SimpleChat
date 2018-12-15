package org.yang.zhang.cellimpl;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import org.yang.zhang.fxcontroller.MainController;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.utils.ClientCache;
import org.yang.zhang.utils.SpringContextUtils;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.view.ContractItemView;

public class ContractTreeCellImpl extends TreeCell<ContractItemView> {

    private String cellId;

    public static Callback<TreeView<ContractItemView>,TreeCell<ContractItemView>> callback=new Callback<TreeView<ContractItemView>,TreeCell<ContractItemView>>(){
        @Override
        public TreeCell<ContractItemView> call(TreeView<ContractItemView> pane) {
            return new ContractTreeCellImpl();
        }
    };

    public ContractTreeCellImpl() {
        MainController mainController=SpringContextUtils.getBean("mainController");
        ChatService chatService=SpringContextUtils.getBean("chatService");
        setOnDragEntered(e -> {
            //收缩分组
            for(TreeItem<ContractItemView> paneTreeItem:ClientCache.getGroupList()){
                paneTreeItem.setExpanded(false);
            }
            ClipboardContent content = new ClipboardContent();
            content.putString(this.cellId);
            e.getDragboard().setContent(content);
            e.consume();
        });
        setOnDragDetected(e -> {
            Dragboard db = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString( "Hello!" );
            db.setContent(content);
            e.consume();
        });
        setOnDragDone(e -> {
            e.consume();
        });
        setOnDragDropped(e -> {
            //将用户移动到目标分组中
            Dragboard dragboard=e.getDragboard();
            String itemId=dragboard.getString();
            String newGroupId=this.cellId;
            String oldGroupId=ClientCache.getContractMap().get(itemId).getParent().getValue().getId();
            chatService.updateContractGroup(itemId,oldGroupId.substring(oldGroupId.indexOf("P")+1,oldGroupId.length()),
                    newGroupId.substring(newGroupId.indexOf("P")+1,newGroupId.length()));
            e.setDropCompleted(true);
            e.consume();
            mainController.initContractList();
        });
        setOnDragExited(e -> {
            e.consume();
        });
        setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });
    }

    @Override
    public void updateItem(ContractItemView pane, boolean empty) {
        super.updateItem(pane,empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if(pane==null||pane.getId()==null){
                setGraphic(null);
            }else{
                this.cellId=pane.getId();
                setGraphic(pane.getItemPane());
                if(cellId.startsWith("GROUP")){
                    setContextMenu(MainController.groupMenu);
                }else{
                    setContextMenu(MainController.userMenu);
                }

            }

        }
    }
}
