package cn.powerr.blog.blog.controller;

import cn.powerr.blog.blog.entity.Blog;
import cn.powerr.blog.blog.service.BlogManageService;
import cn.powerr.blog.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@Slf4j
public class BlogController {

    @Autowired
    private BlogManageService blogManageService;

    @RequestMapping("/blogManage")
    public String manageBlog(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Blog blogInfo = blogManageService.searchBlogInfo(sessionUser.getUserId());
        model.addAttribute("blogInfo", blogInfo);
        return "blogmanage";
    }

    @RequestMapping("/saveBlog")
    public String saveBlog(Blog blog, HttpSession session, HttpServletRequest request, Model model) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        User sessionUser = (User) session.getAttribute("sessionUser");
        blog.setUserId(sessionUser.getUserId());
        try {
            Blog result = blogManageService.saveBlog(file, blog);
            model.addAttribute("blogInfo",result);
        } catch (IOException e) {
            log.error("文件上传失败");
        }
        return "blogmanage";
    }

    @RequestMapping("/commentManage")
    public String manageComment(Model model) {

        return "commentmanage";
    }

    @RequestMapping("/blogrollManage")
    public String manageBlogroll(Model model) {

        return "blogrollmanage";
    }

    @RequestMapping("/tagManage")
    public String manageTag(Model model) {

        return "tagmanage";
    }

    @RequestMapping("/draftsManage")
    public String manageDrafts(Model model) {

        return "draftsmanage";
    }

    @RequestMapping("/articleManage")
    public String manageArticle(Model model){
        return "articlemanage";
    }

    @RequestMapping("/writeArticle")
    public String writeArticle(){
        return "write_article";
    }
}
