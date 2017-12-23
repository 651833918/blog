package cn.powerr.blog.blog.controller;

import cn.powerr.blog.blog.entity.*;
import cn.powerr.blog.blog.service.*;
import cn.powerr.blog.user.entity.User;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class BlogController {

    @Autowired
    private BlogManageService blogManageService;
    @Autowired
    private StationMasterBlogService stationMasterBlogService;
    @Autowired
    private BlogrollService blogrollService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleService articleService;


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
            model.addAttribute("blogInfo", result);
        } catch (IOException e) {
            log.error("文件上传失败");
        }
        return "blogmanage";
    }

    @RequestMapping("/commentManage")
    public String manageComment(Model model) {
        return "commentmanage";
    }

    /**
     * 跳到展示友链页面
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/blogrollManage")
    public String manageBlogroll(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer userId = sessionUser.getUserId();
        List<Blogroll> blogrolls = null;
        blogrolls = blogrollService.searchBlogrolls(userId);
        if (blogrolls.size() > 0) {
            model.addAttribute("blogrolls", blogrolls);
        }
        return "addblogroll";
    }


    /**
     * 跳到新增友链界面
     *
     * @return
     */
    @RequestMapping("/showAddBlogroll")
    public String showAddBlogroll() {
        return "blogrollmanage";
    }

    @RequestMapping("/delBlogroll/{id}")
    public String delBlogroll(@PathVariable(value = "id") Integer blogrollId) {
        blogrollService.delBlogroll(blogrollId);
        return "redirect:/blogrollManage";
    }

    /**
     * 展示editBlogroll页面
     *
     * @param blogrollId
     * @param model
     * @return
     */
    @RequestMapping("/showEditBlogroll/{id}")
    public String showEditBlogroll(@PathVariable(value = "id") Integer blogrollId, Model model) {
        Blogroll blogroll = blogrollService.searchBlogroll(blogrollId);
        model.addAttribute("blogrollInfo", blogroll);
        return "blogrollmanage";
    }

    @RequestMapping("/editBlogroll/{id}")
    @ResponseBody
    public String editBlogroll(@PathVariable(value = "id") Integer id, @RequestBody Blogroll blogroll) {
        blogroll.setBlogrollId(id);
        int result = blogrollService.editBlogroll(blogroll);
        if (result > 0) {
            return "edit_succ";
        }
        return "edit_fail";
    }

    /**
     * 新增友链
     *
     * @param blogroll
     * @param session
     * @return
     */
    @RequestMapping("/addBlogroll")
    @ResponseBody
    public String addBlogroll(@RequestBody Blogroll blogroll, HttpSession session) {
        User userSession = (User) session.getAttribute("sessionUser");
        blogroll.setUserId(userSession.getUserId());
        int resp = blogrollService.addBlogroll(blogroll);
        if (resp > 0) {
            return "add_succ";
        }
        return "add_fail";
    }


    /**
     * 跳到文章标签页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/tagManage")
    public String manageTag(Model model, HttpSession session) {
        User user = (User) session.getAttribute("sessionUser");
        List<Tag> tags = tagService.searchTags(user.getUserId());
        model.addAttribute("tags", tags);
        return "tagmanage";
    }

    /**
     * 添加标签
     *
     * @param tag
     * @param session
     * @return
     */
    @RequestMapping("/addTag")
    @ResponseBody
    public String addTag(@RequestBody Tag tag, HttpSession session) {
        User user = (User) session.getAttribute("sessionUser");
        tag.setUserId(user.getUserId());
        int result = tagService.addTag(tag);
        if (result > 0) {
            return "add_succ";
        }
        return "add_fail";
    }

    /**
     * 根据标签查看文章信息
     * @param tagId
     * @return
     */
    @RequestMapping("/getArticlesWithTag/{tagId}/{pageNum}")
    public String getArticlesWithTag(@PathVariable(value = "tagId")Integer tagId,Model model,
                                     @PathVariable(value = "pageNum")Integer pageNum, HttpSession session){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Map result = articleService.getArticlesWithTag(tagId, sessionUser.getUserId(),pageNum);
        List<ArticleWithTagAndUser> articles = (List<ArticleWithTagAndUser>) result.get("articles");
        PageInfo<ArticleWithTagAndUser> pageInfo = (PageInfo<ArticleWithTagAndUser>) result.get("pageInfo");
        Tag tag = (Tag) result.get("tag");
        model.addAttribute("articlesWithTag",articles);
        model.addAttribute("articlesWithTagPageInfo",pageInfo);
        model.addAttribute("articleTag",tag);
        return "article_withtag";
    }

    /**
     * 删除tag
     *
     * @param tagId
     * @return
     */
    @RequestMapping("/delTag/{tagId}")
    public String delTag(@PathVariable(value = "tagId") Integer tagId) {
        int result = tagService.delTag(tagId);
        return "redirect:/tagManage";
    }

    /**
     * 展示修改标签的页面
     *
     * @param tagId
     * @param model
     * @return
     */
    @RequestMapping("/showEditTagName/{tagId}")
    public String showEditTagName(@PathVariable(value = "tagId") Integer tagId, Model model) {
        Tag tag = tagService.refreshTagName(tagId);
        model.addAttribute("tagInfo", tag);
        return "addtag";
    }

    @RequestMapping("/showAddTag")
    public String showAddTag() {
        return "addtag";
    }

    /**
     * 编辑标签页面
     * @param tagId
     * @param tag
     * @return
     */
    @RequestMapping("/editTag/{tagId}")
    @ResponseBody
    public String editTagName(@PathVariable(value = "tagId") Integer tagId,
                              @RequestBody Tag tag) {
        tag.setTagId(tagId);
        int resp = tagService.editTagName(tag);
        if (resp > 0) {
            return "edit_succ";
        }
        return "edit_fail";
    }

    /**
     * 跳到草稿管理页面
     * @param model
     * @return
     */
    @RequestMapping("/draftsManage")
    public String manageDrafts(Model model,HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<ArticleWithBLOBs> articles = articleService.showArticlesNoState(sessionUser.getUserId());
        model.addAttribute("draftsArticles",articles);
        return "draftsmanage";
    }

    /**
     * 新建草稿
     * @return
     */
    @RequestMapping("/savaToDrafts")
    @ResponseBody
    public String savaToDrafts(@RequestBody ArticleWithBLOBs article,HttpSession session){
        String content = article.getContent();
        String replaceLeft= content.replace("<", "&lt;");
        String replaceRight = replaceLeft.replace(">", "&gt;");
        article.setContent(replaceRight);
        User sessionUser = (User) session.getAttribute("sessionUser");
        article.setUserId(sessionUser.getUserId());
        article.setState(0);
        article.setTime(String.valueOf(System.currentTimeMillis()));
        int resp = articleService.savaDrafts(article);
        if (resp > 0) {
            return "drafts_succ";
        }
        return "drafts_fail";
    }

    /**
     * 删除草稿
     * @param articleId
     * @return
     */
    @RequestMapping("/delDraft/{articleId}")
    public String delDraft(@PathVariable(value = "articleId")Integer articleId){
        articleService.delArticle(articleId);
        return "redirect:/draftsManage";
    }

    @RequestMapping("/editDraft/{articleId}")
    public String editDraft(@PathVariable(value = "articleId")Integer articleId,Model model){
        ArticleWithTagAndUser editArticle = articleService.getEditArticle(articleId);
        model.addAttribute("editDraft",editArticle);
        return "write_article";
    }

    /**
     * 草稿发布博客
     * @param articleId
     * @param session
     * @return
     */
    @RequestMapping("/pulishDraft/{articleId}")
    public String publishDraft(@PathVariable(value = "articleId")Integer articleId,HttpSession session){
        articleService.publishDraft(articleId);
        return "redirect:/showUserBlog/1";
    }


    @RequestMapping("/articleManage/{pageNum}")
    public String manageArticle(@PathVariable("pageNum") Integer pageNum, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<ArticleWithUser> articles = null;
        PageInfo<ArticleWithUser> pageInfo = null;
        List<Tag> tags = null;
        try {
            Map stringListMap = stationMasterBlogService.searchArticle(sessionUser.getUserId(), pageNum, 5);
            tags = tagService.searchTags(sessionUser.getUserId());
            articles = (List<ArticleWithUser>) stringListMap.get("articles");
            pageInfo = (PageInfo<ArticleWithUser>) stringListMap.get("pageInfo");
        } catch (Exception e) {
            log.error("展示文章管理页面失败", e.getMessage());
        }
        model.addAttribute("articlesManage", articles);
        model.addAttribute("articlesPageInfo", pageInfo);
        model.addAttribute("tags", tags);
        return "articlemanage";
    }

    @RequestMapping("/writeArticle")
    public String writeArticle() {
        return "write_article";
    }
}
