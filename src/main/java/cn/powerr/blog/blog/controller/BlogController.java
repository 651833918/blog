package cn.powerr.blog.blog.controller;

import cn.powerr.blog.blog.service.MainhomeService;
import cn.powerr.blog.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BlogController {
    @Autowired
    private MainhomeService mainhomeService;


    @RequestMapping("/blogManage")
    public String manageBlog(Model model){

        return "blogmanage";
    }

    @RequestMapping("/commentManage")
    public String manageComment(Model model){

        return "commentmanage";
    }
    @RequestMapping("/blogrollManage")
    public String manageBlogroll(Model model){

        return "blogrollmanage";
    }
    @RequestMapping("/tagManage")
    public String manageTag(Model model){

        return "tagmanage";
    }

    @RequestMapping("/draftsManage")
    public String manageDrafts(Model model) {

        return "draftsmanage";
    }
}
