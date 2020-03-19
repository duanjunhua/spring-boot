package com.fxkj.login.controller;

import com.fxkj.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Michael J H Duan
 * @version: v1.0
 * @date Date: 2020-03-18 16:59
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/signIn")
    @ResponseBody
    public Response signIn(String loginName, String password, String ticket, String remember){
        Response response = new Response(Response.RES_SUC);
        response.setData(loginName);
        return response;
    }

    @GetMapping("/index")
    public String index(Model model){
        return "login";
    }
}
