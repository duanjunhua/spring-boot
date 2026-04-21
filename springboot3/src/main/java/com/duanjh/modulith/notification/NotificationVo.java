package com.duanjh.modulith.notification;

import com.duanjh.modulith.notification.internal.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-15 周三 14:48
 * @Version: v1.0
 * @Description: 供外部调用的Notification
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationVo {

    private Date date;

    private String format;

    private String productName;


}
