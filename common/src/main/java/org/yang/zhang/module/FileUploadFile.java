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
    private int type;
    private Integer originalUserId;
    private Integer targetUserId;
    private Long total;
    private Integer remain;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public Integer getOriginalUserId() {
        return originalUserId;
    }

    public void setOriginalUserId(Integer originalUserId) {
        this.originalUserId = originalUserId;
    }

    public Integer getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Integer targetUserId) {
        this.targetUserId = targetUserId;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Boolean getCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }
}
