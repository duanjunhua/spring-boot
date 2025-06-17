package com.duanjh.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-06-17 周二 17:25
 * @Version: v1.0
 * @Description: 提示词配置
 */
@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(OpenAiChatModel model){
        return ChatClient.builder(model)
                .defaultSystem("你是一位高速公路养护专家，请以专家的语气进行回答问题.")
                .build();
    }
}
