package com.duanjh.langchain.rag;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-20 周三 15:51
 * @Version: v1.0
 * @Description: RAG向量化配置
 */
@Configuration
public class EmbeddingModelConfig {

    @Value("${langchain4j.open-ai.embedding-model.base-url}")
    private String baseUrl;

    @Value("${langchain4j.open-ai.embedding-model.api-key}")
    private String apiKey;

    @Value("${langchain4j.open-ai.embedding-model.model-name}")
    private String modelName;

    @Bean(name = "localOpenAiEmbeddingModel")
    public EmbeddingModel embeddingModel(){
        return OpenAiEmbeddingModel.builder()
                // 模型调用地址
                .baseUrl(baseUrl)
                // API Key
                .apiKey(apiKey)
                // 使用的模型
                .modelName(modelName)
                // 向量化失败重试次数
                .maxRetries(5)
                .build();
    }

    @Bean(name = "memoryEmbeddingStore")
    public EmbeddingStore  embeddingStore(){
        // 可换成 Milvu、PgVector等
        return new InMemoryEmbeddingStore();
    }

    @Bean(name = "memoryContentRetriever")
    public ContentRetriever retriever(){
        return EmbeddingStoreContentRetriever.builder()
                // 向量化模型
                .embeddingModel(embeddingModel())
                // 向量化持久化
                .embeddingStore(embeddingStore())
                // 最多查询5条结果
                .maxResults(5)
                // 最小相似分
                .minScore(0.7)
                .build();
    }
}
