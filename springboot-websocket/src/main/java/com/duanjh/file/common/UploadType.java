package com.duanjh.file.common;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 上传类型枚举类
 */
@Getter
public enum UploadType {

    UNKONW(1, "未知"),
    TYPE1(2, "类型1"),
    TYPE2(3, "类型3");

    private int code;

    private String desc;

    private static Map<Integer, UploadType> map = new HashMap<>();

    static {
        for (UploadType value : UploadType.values()){
            map.put(value.code, value);
        }
    }

    UploadType(int code ,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static UploadType getByCode(Integer code){
        return map.get(code);
    }
}
