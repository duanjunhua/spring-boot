package com.boot.vue.base;

import lombok.Data;

import java.util.List;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-07
 * @Version: V1.0
 * @Description: 因为前端分页数据是 rows ，不是recodrs,在这里转换下
 */
@Data
public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> {

    /**
     * 将records数据存入rows
     */
    public List<T> getRows(){
        return super.getRecords();
    }

    /**
     * 将records数据清空
     */
    public List<T> getRecords(){
        return null;
    }

    public Page(long current, long size){
        super(current, size);
    }

}
