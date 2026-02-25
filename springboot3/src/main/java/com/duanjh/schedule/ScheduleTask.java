package com.duanjh.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 09:05
 * @Version: v1.0
 * @Description: 定时任务
 */
@Slf4j
@Component
public class ScheduleTask {

    /**
     * 启动不一定会执行，会按照0 5 10 ..秒执行
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void cronSchedule() {
        log.info("Cron间隔5秒打印一次：{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    /**
     * fxiedRate = 5000：表示上一次【开始执行】时间点之后5秒再执行，启动就会执行一次
     * fixedDelay = 5000：表示上一次【执行完毕】时间点之后6秒再执行
     * initialDelay=1000, fixedRate=5000：表示第一次延迟1秒后执行，之后按 fixedRate 的规则每5秒执行一次
     */
    @Scheduled(fixedRate  = 30000)
    public void fixRateSchedule() {
        log.info("FixedRate间隔5秒打印一次：{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
