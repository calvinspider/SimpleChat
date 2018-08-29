package org.yang.zhang.utils;

import javafx.scene.control.TreeItem;

import org.yang.zhang.config.SystemConfig;
import org.yang.zhang.view.ContractItemView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientCache {
    private static List<TreeItem<ContractItemView>> groupList=new ArrayList<>();
    public static Map<String,TreeItem<ContractItemView>> contractMap=new HashMap<>();
    public static SystemConfig systemConfig;

    public static void addGroup(TreeItem<ContractItemView> item){
        groupList.add(item);
    }

    public static void addContract(String id,TreeItem<ContractItemView> item){
        contractMap.put(id,item);
    }

    public static void clearGroup(){
        groupList.clear();
    }

    public static void clearContract(){
        contractMap.clear();
    }

    public static List<TreeItem<ContractItemView>> getGroupList() {
        return groupList;
    }

    public static Map<String, TreeItem<ContractItemView>> getContractMap() {
        return contractMap;
    }
}
