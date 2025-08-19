package com.duanjh.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-19 周二 10:54
 * @Version: v1.0
 * @Description:
 */
@Data
@TableName("t_disaster_slope")
public class TSlope {

    @TableId
    private Long id;

    private String startPileNo;

    private String endPileNo;

    private String description;
}
