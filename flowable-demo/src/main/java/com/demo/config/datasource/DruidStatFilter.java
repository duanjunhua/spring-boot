package com.demo.config.datasource;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @author Michael
 * @Date: 2018年12月7日_下午3:59:15
 * @Version: v0.1
 */
@WebFilter(
    urlPatterns = "/*",
    initParams = {
            @WebInitParam(name = "exclusions",value = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*")
})
public class DruidStatFilter extends WebStatFilter {

}
