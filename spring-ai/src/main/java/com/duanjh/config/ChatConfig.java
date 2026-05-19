package com.duanjh.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-06-17 周二 17:25
 * @Version: v1.0
 * @Description: 客户端角色配置
 */
@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(DashScopeChatModel model){
        return ChatClient
                .builder(model)
                // 配置系统角色
                .defaultSystem("你是一位公路行业专家，你的名字叫路文，请以公路专家的身份回答用户的问题")

                // 日志Advisor
                .defaultAdvisors(new SimpleLoggerAdvisor())

                .build();
    }
}
