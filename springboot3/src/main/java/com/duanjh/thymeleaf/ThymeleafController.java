package com.duanjh.thymeleaf;

import com.duanjh.jpa.entity.BootUser;
import com.duanjh.jpa.repository.BootUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-06 周五 14:54
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {

    @Autowired
    BootUserRepository reposotory;

    @GetMapping("/index")
    public String index(){
        return "common";
    }

    /*------------------- 结合JPA与Thymeleaf -------------------*/
    @RequestMapping("/list")
    public String list(Model model) {
        List<BootUser> users = reposotory.findAll();
        model.addAttribute("users", users);
        return "boot_user/list";
    }
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "boot_user/add";
    }
    @RequestMapping("/add")
    public String add(BootUser user) {
        reposotory.save(user);
        return "redirect:/thymeleaf/list";
    }
    @RequestMapping("/toEdit")
    public String toEdit(Model model,Long id) {
        BootUser user = reposotory.findById(id).get();
        model.addAttribute("user", user);
        return "boot_user/edit";
    }
    @RequestMapping("/edit")
    public String edit(BootUser user) {
        reposotory.save(user);
        return "redirect:/thymeleaf/list";
    }
    @RequestMapping("/delete")
    public String delete(Long id) {
        reposotory.deleteById(id);
        return "redirect:/thymeleaf/list";
    }

}
