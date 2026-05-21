package com.duanjh.langchain;

import com.duanjh.jpa.entity.BootUser;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import reactor.core.publisher.Flux;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-06 周三 15:19
 * @Version: v1.0
 * @Description: 定义AI服务接口
 *  LangChain4j 会自动生成动态代理，把方法调用翻译成对 LLM 的调用
 */
public interface Assistant {

    @SystemMessage("你是一位高速公路养护专家")
    Flux<String> chat(String message);

    @SystemMessage("你是一位资深的中英互译专家，翻译力求准确、自然、避免直译腔。")
    Flux<String> translate(@UserMessage String text);

    /**
     * 底层原理是动态代理 + 模板渲染：方法入参通过@V绑定到Prompt模板的变量
     */
    @SystemMessage("你是一位科技博客的主编，擅长把复杂概念讲成故事。")
    @UserMessage("请根据下面的关键词写一段不少于 200 字的引言：" +
            "关键词：{{keywords}} " +
            "目标读者：{{audience}}")
    Flux<String> write(@V("keywords") String keywords, @V("audience") String audience);

    /**
     * 使用@MemoryId时必须配置ChatMemoryProvider
     */
    Flux<String> chatWithMemory(@MemoryId String userId, @UserMessage String message);

    /**
     * 工具调用
     */
    BootUser functionalCall(@UserMessage String username);

    /**
     * RAG
     */
    String chatWithRag(@UserMessage String question);

}
