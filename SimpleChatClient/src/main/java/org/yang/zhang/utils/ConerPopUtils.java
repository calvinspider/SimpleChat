package org.yang.zhang.utils;

import java.util.HashMap;
import java.util.Map;

import org.yang.zhang.view.ChatView;

import javafx.scene.control.TitledPane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 08 24 11:02
 */

public class ConerPopUtils {
    public static Map<Integer,TitledPane> titledPaneMap=new HashMap<>();

    public static void registerConer(Integer userId, TitledPane stage) {
        titledPaneMap.put(userId, stage);
    }

    public static void unregisterConer(Integer userId) {
        titledPaneMap.remove(userId);
    }

    public static TitledPane getConer(Integer userId) {
        return titledPaneMap.get(userId);
    }

}
