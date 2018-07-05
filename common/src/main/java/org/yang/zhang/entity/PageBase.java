package org.yang.zhang.entity;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 04 28 11:26
 */
@Data
public class PageBase {

    private Integer page;
    private Integer pageNumber;
    private String searchContent;
    private String sortField;
    private String sortType;
}
