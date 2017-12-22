package cn.powerr.blog.blog.controller;

import cn.powerr.blog.blog.entity.*;
import cn.powerr.blog.blog.service.*;
import cn.powerr.blog.user.entity.User;
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
public class ShowPageController {
    @Autowired
    private MainhomeService mainhomeService;
    @Autowired
    private StationMasterBlogService stationMasterBlogService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BlogrollService blogrollService;
    @Autowired
    private TagService tagService;

    /**
     * 主页用于展示的文章信息等
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/mainHome/{pageNum}")
    public String redirctToMainHome(@PathVariable("pageNum") Integer pageNum, Model model, HttpSession session) {
        String mainHomeUrl = "mainhome";
        try {
            List<ArticleWithUser> likeHotList = mainhomeService.searchLikeHotInfo();
            Map result = mainhomeService.searchLookHotInfo(pageNum);
            List lookHotList = (List) result.get("lookHot");
            PageInfo pageInfo = (PageInfo) result.get("pageInfo");
            List<ArticleWithUser> recentPublishList = mainhomeService.searchRecentPublishInfo();
            List<User> users = mainhomeService.searchHotUser();
            model.addAttribute("likeHotList", likeHotList);
            model.addAttribute("lookHotList", lookHotList);
            model.addAttribute("recentPublishList", recentPublishList);
            model.addAttribute("pageInfo", pageInfo);
            session.setAttribute("users", users);
        } catch (Exception e) {
            log.error("查看主页信息失败");
        }
        return mainHomeUrl;
    }

    /**
     * 返回具体的数据
     *
     * @param userId
     * @param tagId
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/showArticlesWithTag/{userId}/{tagId}/{pageNum}")
    public String showArticlesWithTag(@PathVariable(value = "userId") Integer userId,
                                      @PathVariable(value = "tagId") Integer tagId,
                                      @PathVariable(value = "pageNum") Integer pageNum,
                                      Model model) {
        Map map = articleService.showArticlesWithTag(userId, tagId, pageNum);
        List<ArticleWithTagAndUser> articles = (List<ArticleWithTagAndUser>) map.get("articles");
        PageInfo<ArticleWithTagAndUser> pageInfo = (PageInfo<ArticleWithTagAndUser>) map.get("pageInfo");
        model.addAttribute("articlesWithTag", articles);
        model.addAttribute("articlesWithTagPageInfo", pageInfo);
        model.addAttribute("userId", userId);
        model.addAttribute("tagId", tagId);
        List<Blogroll> blogrolls = blogrollService.searchBlogrolls(userId);
        List<Tag> tags = tagService.searchTags(userId);
        model.addAttribute("articlesWithTagtags", tags);
        model.addAttribute("articlesWithTagblogrollInfo", blogrolls);
        Map<String, Object> blogAndUser = null;
        blogAndUser = stationMasterBlogService.searchBlogAndUser(userId);
        User userInfo = (User) blogAndUser.get("user");
        Blog blogInfo = (Blog) blogAndUser.get("blog");
        model.addAttribute("articlesWithTaguserInfo", userInfo);
        model.addAttribute("articlesWithTagblogInfo", blogInfo);
        Map<String, List> sidebarExceptTag = stationMasterBlogService.searchsidebarExceptTag(userId);
        List<Article> readHot = sidebarExceptTag.get("readHot");
        List<Article> likeHot = sidebarExceptTag.get("likeHot");
        List<Article> commentHot = sidebarExceptTag.get("commentHot");
        model.addAttribute("articlesWithTagreadHot", readHot);
        model.addAttribute("articlesWithTaglikeHot", likeHot);
        model.addAttribute("articlesWithTagcommentHot", commentHot);
        return "articles_tag";
    }


}
