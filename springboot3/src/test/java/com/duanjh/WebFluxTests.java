package com.duanjh;

import com.duanjh.flux.FluxController;
import com.duanjh.web.HelloController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-02 周一 17:02
 * @Version: v1.0
 * @Description:
 */
public class WebFluxTests {

    MockMvc mockMvc;    //提供了对Controller层的测试

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FluxController()).build();
    }

    @Test
    public void helloTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/flux/stream")
                        .accept(MediaType.TEXT_EVENT_STREAM)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
