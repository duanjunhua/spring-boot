package com.kingroad.pulsar.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;

import java.util.Objects;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 11:26
 * @Version: v1.0
 * @Description: 数据对比工具
 */
public class DataCompareUtil {

    /**
     * 对象转JSON，超长截断，默认不截断
     * @param object
     * @param maxLength 最大长度
     * @return
     */
    public static String toTruncJson(Object object, Integer maxLength) {

        if(Objects.isNull(object)) return "";

        String json = JSONUtil.toJsonStr(object);

        return ObjectUtil.isNull(maxLength) ? json : json.substring(0,maxLength);

    }

}
