package org.yang.zhang.module;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 30 18:01
 */

import java.io.File;
import java.io.Serializable;

public class FileUploadFile implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fileName;// 文件名
    private byte[] bytes;// 文件字节数组
    private Boolean create;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Boolean getCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }
}
