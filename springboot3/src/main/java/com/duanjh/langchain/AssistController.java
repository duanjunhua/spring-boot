package com.duanjh.langchain;

import com.duanjh.jpa.entity.BootUser;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;


/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-06 周三 15:20
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/ai")
public class AssistController {

    @Autowired
    @Qualifier("memoryAssistant")
    Assistant assistant;

    @RequestMapping("/index")
    public String list(Model model) {
        return "ai";
    }

    @ResponseBody
    @GetMapping(value = "/chat", headers={})
    public Flux<ServerSentEvent<String>> answer(String question, HttpServletResponse response){
        return assistant.chat(question).map(chunk ->
                ServerSentEvent.<String>builder()
                        .data(chunk).build()
        );
    }

    @ResponseBody
    @GetMapping("/translate")
    public Flux<ServerSentEvent<String>> translate(String text){
        return assistant.translate(text).map(chunk ->
                ServerSentEvent.<String>builder()
                        .data(chunk).build()
        );
    }

    @ResponseBody
    @GetMapping("/chat-memory")
    public Flux<ServerSentEvent<String>> answerWithMemory(String userId, String question){
        return assistant.chatWithMemory(userId, question).map(chunk ->
                ServerSentEvent.<String>builder()
                        .data(chunk).build()
        );
    }

    @ResponseBody
    @GetMapping("/tool-call")
    public BootUser functionalCall(String username){
        return assistant.functionalCall(username);
    }
}
