package org.yang.zhang;


import org.yang.zhang.config.CustomSplash;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.utils.ActionManager;
import org.yang.zhang.utils.ClientCache;
import org.yang.zhang.utils.StageManager;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yang.zhang.utils.SystemConfigUtils;
import org.yang.zhang.view.LoginView;

@SpringBootApplication
public class SimpleChatClientApplication extends AbstractJavaFxApplicationSupport {

    //启动应用弹出登陆框
    public static void main(String[] args) {
        //加载全局配置
        ClientCache.systemConfig=SystemConfigUtils.loadSystemConfig();
        launch(SimpleChatClientApplication.class,LoginView.class,new CustomSplash(),args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setIconified(false);
        StageManager.registerStage(StageCodes.LOGIN,stage);
        ActionManager.setOnCloseExistListener(stage);
//        stage.setAlwaysOnTop(true);
        super.start(stage);
    }


}
