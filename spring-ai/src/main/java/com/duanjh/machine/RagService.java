package com.duanjh.machine;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-14 周四 09:44
 * @Version: v1.0
 * @Description: RAG（检索增强生成）
 *  答案可溯源、知识实时更新、防止幻觉；但多了一步检索，延迟增加；检索质量决定答案质量
 */
@Service
public class RagService {

    /**
     * 向量数据库，存公司文档
     */
    @Autowired
    VectorStore vectorStore;

    @Autowired
    ChatClient chatClient;

    public String ask(String question) {


        // 1. 检索相关文档
        List<Document> docs = vectorStore.similaritySearch(SearchRequest.query(question).withTopK(3));

        // 2. 把文档内容拼成上下文
        String context = docs.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));

        // 3. 让LLM基于上下文回答
        return chatClient.prompt()
                .system("请基于以下资料回答：" + context)
                .user(question)
                .call()
                .content();

    }

}
