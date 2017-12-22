package cn.powerr.blog.blog.controller;

import cn.powerr.blog.blog.entity.*;
import cn.powerr.blog.blog.service.ArticleService;
import cn.powerr.blog.blog.service.CommentService;
import cn.powerr.blog.blog.service.StationMasterBlogService;
import cn.powerr.blog.common.resp.ArticlePicResult;
import cn.powerr.blog.user.entity.User;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 发表文章相关
 */
@Controller
@Slf4j
public class ArticleController {

    @Autowired
    @Qualifier("articleServiceImpl")
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private StationMasterBlogService stationMasterBlogService;
    DateTime dateTime;


    @RequestMapping(value = "/uploadArticlePicture", method = RequestMethod.POST)
    @ResponseBody
    public ArticlePicResult uploadArticleImg(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file) {
        ArticlePicResult result = new ArticlePicResult();
        try {
            String url = articleService.uploadArticlePicture(file);
            result.setUrl(url);
            result.setMessage("图片上传成功");
        } catch (IOException e) {
            result.setSuccess(0);
            result.setMessage("图片上传失败");
        }
        return result;
    }

    @RequestMapping("/publishArticle")
    @ResponseBody
    public String publishArticle(@RequestBody ArticleWithBLOBs article, HttpSession session, Model model) {
        String result = "publish_fail";
        User sessionUser = (User) session.getAttribute("sessionUser");
        article.setTime(String.valueOf(System.currentTimeMillis()));
        article.setState(1);
        article.setUserId(sessionUser.getUserId());
        try {
            Article articleResult = articleService.saveArticle(article);
            result = "publish_succ";
        } catch (Exception e) {
            log.error("发表博客失败");
        }
        return result;
    }

    /**
     * 跳转到用户页面
     *
     * @return
     */
    @RequestMapping("/published")
    public String seeArticle() {
        return "redirect:/showUserBlog/1";
    }

    /**
     * 展示文章界面
     *
     * @param articleId
     * @param userId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/getArticleMessage/{articleId}/{userId}")
    public String getArticleMessage(@PathVariable(value = "articleId") Integer articleId,
                                    @PathVariable(value = "userId") Integer userId, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        //用户登录后自己的id
        Integer userIdLocal = null;
        if (sessionUser != null) {
            userIdLocal = sessionUser.getUserId();
        }
        try {
            Map<String, Object> blogAndUser = null;
            if (sessionUser != null) {
                blogAndUser = stationMasterBlogService.searchBlogAndUser(userId, userIdLocal);
            } else {
                blogAndUser = stationMasterBlogService.searchStationMaster(userId);
            }
            User userInfo = (User) blogAndUser.get("user");
            Blog blogInfo = (Blog) blogAndUser.get("blog");
            model.addAttribute("userInfo3", userInfo);
            model.addAttribute("blogInfo3", blogInfo);
        } catch (Exception e) {
            log.error("用户信息或者博客信息查找失败");
        }
        ArticleWithTagAndUser articleWithBLOBs = articleService.refreshArticleMess(articleId, userId);
        long time = Long.parseLong(articleWithBLOBs.getTime());
        dateTime = new DateTime(time);
        articleWithBLOBs.setTime(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        dateTime = null;
        model.addAttribute("article", articleWithBLOBs);
        try {
            Map<String, List> sidebarExceptTag = stationMasterBlogService.searchsidebarExceptTag(userId);
            List<ArticleWithUser> readHot = sidebarExceptTag.get("readHot");
            List<ArticleWithUser> likeHot = sidebarExceptTag.get("likeHot");
            List<ArticleWithUser> commentHot = sidebarExceptTag.get("commentHot");
            model.addAttribute("readHot3", readHot);
            model.addAttribute("likeHot3", likeHot);
            model.addAttribute("commentHot3", commentHot);
        } catch (Exception e) {
            log.error("排行信息错误");
        }
        List<CommentWithUser> articleComments = commentService.getArticleComments(articleId);
        for (int i = 0; i < articleComments.size(); i++) {
            String commontTimeString = articleComments.get(i).getCommontTime();
            long commontTime = Long.parseLong(commontTimeString);
            dateTime = new DateTime(commontTime);
            articleComments.get(i).setCommontTime(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        }
        model.addAttribute("commentsWithUser", articleComments);
        return "article";
    }

    /**
     * 跳到编辑文章
     *
     * @param articleId
     * @param model
     * @return
     */
    @RequestMapping("/editArticle/{articleId}")
    public String editArticle(@PathVariable(value = "articleId") Integer articleId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("sessionUser");
        Integer userId = user.getUserId();
        ArticleWithTagAndUser articleWithBLOBs = articleService.getEditArticle(articleId);
        model.addAttribute("editArticle", articleWithBLOBs);
        return "write_article";
    }

    /**
     * 编辑文章
     *
     * @return
     */
    @RequestMapping("/editArticled")
    @ResponseBody
    public String editArticled(@RequestBody ArticleWithBLOBs articleWithBLOBs) {
        Integer resp = articleService.editArticle(articleWithBLOBs);
        if (resp > 0) {
            return "edit_succ";
        }
        return "edit_fail";
    }

    @RequestMapping("/setAricleTagId/{articleId}/{tagId}")
    @ResponseBody
    public String setAricleTagId(@PathVariable(value = "articleId") Integer articleId,
                                 @PathVariable(value = "tagId") Integer tagId) {

        int result = articleService.updateTagId(articleId, tagId);
        if (result > 0) {
            return "setTagId_succ";
        }
        return "setTagId_fail";
    }

    /**
     * 删除文章
     *
     * @param articleId
     * @param model
     * @return
     */
    @RequestMapping("/delArticle/{articleId}/{articlePageNum}")
    public String delArticle(@PathVariable(value = "articleId") Integer articleId,
                             @PathVariable(value = "articlePageNum") Integer articlePageNum, Model model) {
        articleService.delArticle(articleId);
        return "redirect:/articleManage/" + articlePageNum;
    }

    @RequestMapping("/clickLikeButton/{articleId}")
    @ResponseBody
    public String clickLikeButton(@PathVariable(value = "articleId") Integer articleId) {
        int resp = articleService.clickLikeButton(articleId);
        if (resp > 0) {

            return "click_succ";
        }
        return "click_fail";
    }

    @RequestMapping("/submitComment/{articleId}")
    @ResponseBody
    public String submitComment(@PathVariable(value = "articleId") Integer articleId,
                                @RequestBody Comment comment, HttpSession session) {
        comment.setArticleId(articleId);
        if (session.getAttribute("sessionUser") != null) {
            comment.setUserId(((User) session.getAttribute("sessionUser")).getUserId());
        }
        comment.setCommontTime(String.valueOf(System.currentTimeMillis()));
        int resp = commentService.submitComment(comment);
        if (resp > 0) {
            return "submit_succ";
        }
        return "submit_fail";
    }

}
