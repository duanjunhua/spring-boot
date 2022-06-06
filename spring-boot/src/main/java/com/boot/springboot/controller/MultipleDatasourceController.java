package com.boot.springboot.controller;

import com.boot.springboot.dao.primary.XtUserRepository;
import com.boot.springboot.dao.secondary.TuumUserRepository;
import com.boot.springboot.domain.primary.XtUser;
import com.boot.springboot.domain.secondary.TuumUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-30
 * @Version: V1.0
 * @Description: 多数据源查看
 */
@Controller
public class MultipleDatasourceController {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate secondaryJdbcTemplate;

    @GetMapping("/datasource/multiple")
    public String multipleDataSource(Model model){
        Map<String, Object> primary = primaryJdbcTemplate.queryForMap("select * from xt_user limit 1");
        Map<String, Object> secondary = secondaryJdbcTemplate.queryForMap("select * from t_uum_user limit 1");
        model.addAttribute("primary", emtryObjectToString(primary));
        model.addAttribute("secondary", emtryObjectToString(secondary));
        return "multiple";
    }

    /**
     * Freemarker 处理Map空值时会报错
     */
    private Map<String, Object> emtryObjectToString(Map<String, Object> source){
        if(null == source) return source;
        for (String key : source.keySet()){
            if(null == source.get(key)){
                source.put(key, "");
            }
        }
        return source;
    }

    @Autowired
    XtUserRepository xtUserRepository;
    @Autowired
    TuumUserRepository tuumUserRepository;

    @GetMapping("/repo/primary")
    public String primaryRepository(Model model){
        List<XtUser> all = xtUserRepository.findAll();
        model.addAttribute("primary", all);
        return "primary";
    }

    @GetMapping("/repo/secondary")
    public String secondaryRepository(Model model){
        List<TuumUser> all = tuumUserRepository.findAll();
        model.addAttribute("secondary", all);
        return "secondary";
    }
}
