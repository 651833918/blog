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

import java.util.List;
import java.util.Map;

/**
 *@author Xue
 *@date 2017/12/5 19:55
 *@description
 */
@Controller
@Slf4j
public class StationMasterBlogController {

    @Autowired
    private StationMasterBlogService stationMasterBlogService;
    @Autowired
    private UserService userService;

    @RequestMapping("/showStationMasterBlog/{pageNum}")
    public String searchStationMasterBlog(@PathVariable("pageNum") Integer pageNum,Model model){
        User user =  userService.searchUserByEmail();
        try {
            Map articles =  stationMasterBlogService.searchArticle(user.getUserId(),pageNum);
            PageInfo pageInfo = (PageInfo) articles.get("pageInfo");
            List<Article> articleInfo = (List<Article>) articles.get("articles");
            model.addAttribute("pageInfo1",pageInfo);
            model.addAttribute("articleInfo1",articleInfo);
        } catch (Exception e) {
            log.error("文章查询失败");
        }
        try {
            Map<String, Object> blogAndUser =  stationMasterBlogService.searchBlogAndUser(user.getUserId());
            User userInfo = (User) blogAndUser.get("user");
            Blog blogInfo = (Blog) blogAndUser.get("blog");
            model.addAttribute("userInfo1",userInfo);
            model.addAttribute("blogInfo1",blogInfo);
        } catch (Exception e) {
            log.error("用户信息或者博客信息查找失败");
        }
        try {
            Map<String,List> sidebarExceptTag =  stationMasterBlogService.searchsidebarExceptTag(user.getUserId());
            List<Article> readHot =  sidebarExceptTag.get("readHot");
            List<Article> likeHot = sidebarExceptTag.get("likeHot");
            List<Article> commentHot = sidebarExceptTag.get("commentHot");
            model.addAttribute("readHot1",readHot);
            model.addAttribute("likeHot1",likeHot);
            model.addAttribute("commentHot1",commentHot);
        } catch (Exception e) {
            log.error("排行信息错误");
        }
        return "stationmasterhome";
    }
}
