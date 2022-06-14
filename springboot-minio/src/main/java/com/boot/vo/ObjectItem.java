package com.boot.vo;

import lombok.Data;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: Minio封装查看对象
 */
@Data
public class ObjectItem {

    private String objectName;

    private Long size;
}
