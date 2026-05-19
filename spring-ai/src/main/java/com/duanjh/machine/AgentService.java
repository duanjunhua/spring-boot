package com.duanjh.machine;

import dev.langchain4j.agent.tool.P;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-14 周四 10:16
 * @Version: v1.0
 * @Description: 智能体
 */
@Component
public class AgentService {

    @Tool(description = "查询指定城市的天气")
    public String getWeather(@P("城市名") String city){
        // 调用天气API
        return city + "今天晴天，25℃";
    }
}
