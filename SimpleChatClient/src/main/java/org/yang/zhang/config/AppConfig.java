package org.yang.zhang.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Auther: Administrator
 * @Date: 2018/12/13 23:33
 * @Description:SimpleChat 全局配置变量
 */
@Component
@Data
public class AppConfig {

    private Boolean alwaysTop;//始终保持在其他窗口前端
    private Boolean borderAutoHide;//停靠在桌面边缘时自动隐藏
    private Boolean autoLeaveWord;//忙碌时自动回复
    private String leaveWord;//自动回复内容
    private String fileDir;//文件保存地址


}
