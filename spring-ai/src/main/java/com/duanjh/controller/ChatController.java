package com.duanjh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-06-16 周一 17:29
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class ChatController {

    public final ChatClient chatClient;

    /**
     * 阻塞式
     */
    @RequestMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    /**
     * 流式
     */
    @RequestMapping(value = "/chat-stream", produces = "text/html;charset=utf-8")
    public Flux<String> chatStream(@RequestParam("message") String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
