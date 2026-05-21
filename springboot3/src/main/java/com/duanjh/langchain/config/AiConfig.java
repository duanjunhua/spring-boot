package com.duanjh.langchain.config;

import com.duanjh.jpa.repository.BootUserRepository;
import com.duanjh.langchain.Assistant;
import com.duanjh.langchain.tool.BootUserTool;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-07 周四 14:41
 * @Version: v1.0
 * @Description: 配置 ChatMemoryProvider，为每个 memoryId 返回独立的 ChatMemory 实例
 */
@Configuration
public class AiConfig {

    @Value("${langchain4j.open-ai.chat-model.base-url}")
    private String baseUrl;

    @Value("${langchain4j.open-ai.chat-model.api-key}")
    private String apiKey;

    @Value("${langchain4j.open-ai.chat-model.model-name}")
    private String modelName;

    @Value("${langchain4j.open-ai.chat-model.temperature}")
    private Double temperature;

    @Autowired
    BootUserRepository repository;

    /**
     * 向量化内容检索器
     */
    @Autowired
    @Qualifier("memoryContentRetriever")
    ContentRetriever retriever;

    @Bean
    public OpenAiChatModel chatModel() {
        return OpenAiChatModel.builder()
                // 模型调用地址
                .baseUrl(baseUrl)
                // API Key
                .apiKey(apiKey)
                // 使用的模型
                .modelName(modelName)

                // 温度系数
                .temperature(temperature)

                .responseFormat("json")

                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @Bean
    public StreamingChatModel streamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(temperature)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        return memoryId ->
                // 每个会话独立存储
                MessageWindowChatMemory.builder()
                // 指定对话ID
                .id(memoryId)
                // 限制记忆条数，防内存溢出
                .maxMessages(20)
                // 注入持久化存储，可替换为 Redis 等持久化存储
                .chatMemoryStore(new InMemoryChatMemoryStore())
                .build();
    }

    @Bean(name = "memoryAssistant")
    public Assistant assistant(){

        return AiServices.builder(Assistant.class)

                // 模型设置
                .chatModel(chatModel())
                .streamingChatModel(streamingChatModel())

                // 设置RAG向量化内容检索器
                .contentRetriever(retriever)

                // 工具调用
                .tools(new BootUserTool(repository))

                // 聊天内容缓存
                .chatMemoryProvider(chatMemoryProvider())

                .build();
    }
}
