package com.duanjh.warmflow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-06-02 周二 10:04
 * @Version: v1.0
 * @Description:
 *      设计器页面入口是访问后端地址(前后端不分离)：ip:port/warm-flow-ui/index.html?id=${definitionId}&onlyDesignShow=${onlyDesignShow}&Authorization=${token}
 *          definitionId：流程定义id。若没传，则认定是新增流程，会初始化流程节点，否则则是编辑或者查看
 *          onlyDesignShow：是否独显流程设计，传true单独访问流程设计器，不显示流程基础信息
 *          disabled：是否可编辑 , true:不可标记 false:可标记
 *          theme： 主题，默认是theme-light， 可选值：theme-light：浅色, theme-dark：深色
 *          darkColors：深色主题颜色，比如想要使用#111827作为背景颜色，可以传入darkColors=111827
 *          token：用户token，共享后端权限(如token)
 */
@Controller
@RequestMapping("/warm-flow")
public class WarmFlowController {

    /**
     * 新建流程
     */
    @GetMapping()
    public String index(){
        return "redirect:/warm-flow-ui/index.html?&onlyDesignShow=false";
    }

    /**
     * 根据流程定义ID修改流程
     * @param definitionId
     * @param onlyDesignShow
     */
    @GetMapping("/design")
    public String index(String definitionId, Boolean onlyDesignShow){
        return "redirect:/warm-flow-ui/index.html?id=" + definitionId + "&onlyDesignShow=" + onlyDesignShow;
    }

    /**
     * 查看流程图
     *  地址拼接showGrid=true，表示流程图显示网格
     *  insId： 流程实例ID
     */
    @GetMapping("view")
    public String index(String insId)
    {
        return "redirect:/warm-flow-ui/index.html?id=" + insId + "&type=FlowChart";
    }

}
