package com.kingroad.pulsar.common;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:13
 * @Version: v1.0
 * @Description:
 */
@Data
public class PageQuery {

    /**
     * 默认查询页码
     */
    private static final int DEFAULT_PAGE_NUMBER = 1;

    /**
     * 默认分页大小
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum = DEFAULT_PAGE_NUMBER;

    @Min(value = 1, message = "每页条数不能小于1")
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    // 排序字段
    private String sortField;

    // asc / desc
    private String sortOrder;

}
