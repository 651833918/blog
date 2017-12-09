package cn.powerr.blog.blog.controller;

import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.Blog;
import cn.powerr.blog.blog.service.StationMasterBlogService;
import cn.powerr.blog.user.entity.User;
import cn.powerr.blog.user.service.UserService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class UserBlogController {
    @Autowired
    private StationMasterBlogService stationMasterBlogService;
    @Autowired
    private UserService userService;

    @RequestMapping("/showUserBlog/{pageNum}")
    public String showUserBlog(@PathVariable("pageNum") Integer pageNum, Model model, HttpSession session){
        User sessionUser = (User) session.getAttribute("sessionUser");
        try {
            Map articles =  stationMasterBlogService.searchArticle(sessionUser.getUserId(),pageNum);
            PageInfo pageInfo = (PageInfo) articles.get("pageInfo");
            List<Article> articleInfo = (List<Article>) articles.get("articles");
            model.addAttribute("pageInfo2",pageInfo);
            model.addAttribute("articleInfo2",articleInfo);
        } catch (Exception e) {
            log.error("文章查询失败");
        }
        try {
            Map<String, Object> blogAndUser =  stationMasterBlogService.searchBlogAndUser(sessionUser.getUserId());
            User userInfo = (User) blogAndUser.get("user");
            Blog blogInfo = (Blog) blogAndUser.get("blog");
            model.addAttribute("userInfo2",userInfo);
            model.addAttribute("blogInfo2",blogInfo);
        } catch (Exception e) {
            log.error("用户信息或者博客信息查找失败");
        }
        try {
            Map<String,List> sidebarExceptTag =  stationMasterBlogService.searchsidebarExceptTag(sessionUser.getUserId());
            List<Article> readHot =  sidebarExceptTag.get("readHot");
            List<Article> likeHot = sidebarExceptTag.get("likeHot");
            List<Article> commentHot = sidebarExceptTag.get("commentHot");
            model.addAttribute("readHot2",readHot);
            model.addAttribute("likeHot2",likeHot);
            model.addAttribute("commentHot2",commentHot);
        } catch (Exception e) {
            log.error("排行信息错误");
        }
        return "userhome";
    }

    @RequestMapping("/showPerosonPage/{userId}/{pageNum}")
    public String getUserPage(@PathVariable(value = "userId") Integer userId, @PathVariable("pageNum") Integer pageNum, Model model) {
        try {
            Map articles = stationMasterBlogService.searchArticle(userId, pageNum);
            PageInfo pageInfo = (PageInfo) articles.get("pageInfo");
            List<Article> articleInfo = (List<Article>) articles.get("articles");
            model.addAttribute("pageInfo3", pageInfo);
            model.addAttribute("articleInfo3", articleInfo);
        } catch (Exception e) {
            log.error("文章查询失败");
    }
        try {
            Map<String, Object> blogAndUser = stationMasterBlogService.searchBlogAndUser(userId);
            User userInfo = (User) blogAndUser.get("user");
            Blog blogInfo = (Blog) blogAndUser.get("blog");
            model.addAttribute("userInfo3", userInfo);
            model.addAttribute("blogInfo3", blogInfo);
        } catch (Exception e) {
            log.error("用户信息或者博客信息查找失败");
        }
        try {
            Map<String, List> sidebarExceptTag = stationMasterBlogService.searchsidebarExceptTag(userId);
            List<Article> readHot = sidebarExceptTag.get("readHot");
            List<Article> likeHot = sidebarExceptTag.get("likeHot");
            List<Article> commentHot = sidebarExceptTag.get("commentHot");
            model.addAttribute("readHot3", readHot);
            model.addAttribute("likeHot3", likeHot);
            model.addAttribute("commentHot3", commentHot);
        } catch (Exception e) {
            log.error("排行信息错误");
        }
        return "person";
    }
}
