package com.duanjh;

import com.duanjh.jpa.entity.BootUser;
import com.duanjh.langchain.Assistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-20 周三 13:59
 * @Version: v1.0
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LangChainTests {

    @Resource
    @Qualifier("memoryAssistant")
    Assistant assistant;

    @Resource
    @Qualifier("localOpenAiEmbeddingModel")
    EmbeddingModel  embeddingModel;

    @Resource
    @Qualifier("memoryEmbeddingStore")
    EmbeddingStore  embeddingStore;

    @Autowired
    StreamingChatModel streamModel;

    @Autowired
    OpenAiChatModel chatModel;

    @Test
    public void chatTest(){
        Flux<String> answer = assistant.chat("请用一句话解释什么是LangChain4j");
        System.out.println(answer);
    }

    @Test
    public void functionalCallTest(){

        BootUser lucy = assistant.functionalCall("lucy");

        Assert.assertNotNull(lucy);

        Assert.assertEquals(lucy.getUsername(), "lucy");
    }

    @Test
    public void embeddingTest(){

        // 加载文档
        Document document = FileSystemDocumentLoader.loadDocument(Path.of("ReadMe.md"));

        // 文档切片
        DocumentSplitter splitter = DocumentSplitters.recursive(500, 50);
        List<TextSegment> segments = splitter.split(document);

        // 向量化
        List<Embedding> embeddings = embeddingModel.embedAll(segments).content();
        embeddingStore.addAll(embeddings, segments);

        String answer = assistant.chatWithRag("集成Shiro的登录认证怎么实现？");

        Assert.assertNotNull(answer);

        System.out.println(answer);
    }

    @Test
    public void streamChatTest(){
        streamModel.chat("介绍下LangChain4j，要求字数在200字以内", new StreamingChatResponseHandler() {
            @Override
            public void onPartialResponse(String partialResponse) {
                System.out.println(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                System.out.println("\n结束！");
            }

            @Override
            public void onError(Throwable error) {
                System.out.println(error.getMessage());
            }
        });
    }

}
