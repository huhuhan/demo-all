package com.yh.auth.security.view.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

/**
 * 主页和登录页面映射
 *
 * @author yanghan
 * @date 2020/6/3
 */
@Controller
public class HomeController {
    @GetMapping({"", "/index"})
    public String index(Model model) {
        this.setAuthentication2Model(model);
        return "index";
    }

    /**
     * 指定的登录页
     * 走security安全过滤器，允许请求，默认为ROLE_ANONYMOUS
     *
     * @param model
     * @return
     */
    @GetMapping("/login_h")
    public String login(Model model) {
        this.setAuthentication2Model(model);
        return "login_h";
    }

    /**
     * 不走security安全过滤器，没有身份认证对象
     *
     * @param model
     * @return
     */
    @GetMapping("/login_y")
    public String login_p(Model model) {
        this.setAuthentication2Model(model);
        return "login_y";
    }

    private void setAuthentication2Model(Model model) {
        //从SecurityContextHolder中得到Authentication对象，进而获取权限列表，传到前端
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Collection<GrantedAuthority> authorityCollection = (Collection<GrantedAuthority>) auth.getAuthorities();
            model.addAttribute("authorities", authorityCollection.toString());
            model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }
}
