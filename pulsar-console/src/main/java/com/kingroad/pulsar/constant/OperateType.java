package com.kingroad.pulsar.constant;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 10:40
 * @Version: v1.0
 * @Description: 操作类型
 */
public enum OperateType {

    /*----------------- 通用操作 --------------------*/
    QUERY("查询"),
    CREATE("新增"),
    UPDATE("修改"),
    DELETE("删除"),

    /*----------------- TOPIC操作 --------------------*/
    CREATE_TOPIC("新增Topic"),
    UPDATE_TOPIC("更新Topic"),
    DELETE_TOPIC("删除Topic"),

    /*----------------- EVENT操作 --------------------*/
    CREATE_EVENT("新增事件"),
    UPDATE_EVENT("更新事件"),
    DELETE_EVENT("删除事件"),
    PUBLISH_EVENT("发布事件"),

    /*----------------- 登录 --------------------*/
    PC_LOGIN("电脑登录"),
    MOBILE_LOGIC("移动端登录"),


    /*----------------- 其他操作 --------------------*/
    UNKNOW("未知操作");

    public String description;

    private OperateType() {

    }

    private OperateType(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
