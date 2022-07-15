package com.duanjh.scheduler;

import com.duanjh.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@EnableScheduling
public class WebSocketTask {

    private AtomicInteger onlineCount = new AtomicInteger(0);

    WebSocketServer server = new WebSocketServer();

    @Scheduled(cron = "0/10 * * * * *")
    public void socketTask() throws IOException {
        server.sendMessage("WebScoket 发送的数据: " + onlineCount.getAndIncrement());
    }

}
