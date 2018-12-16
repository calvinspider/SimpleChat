package org.yang.zhang.module;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 30 18:01
 */

import lombok.Data;

import java.io.File;
import java.io.Serializable;

@Data
public class FileMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fileName;// 文件名
    private byte[] bytes;// 文件字节数组
    private Boolean create;
    private Integer originalUserId;
    private Integer targetUserId;
    private Integer dataLength;
    private String tag;
    private Integer type;


}
