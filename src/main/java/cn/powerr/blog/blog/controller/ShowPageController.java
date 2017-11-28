package cn.powerr.blog.blog.controller;

import cn.powerr.blog.blog.service.MainhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShowPageController {
    @Autowired
    private MainhomeService mainhomeService;

    @RequestMapping(value = "/mainHome")
    public String redirctToMainHome(Model model){
        String mainHomeUrl = "mainhome";

        return mainHomeUrl;
    }
}
