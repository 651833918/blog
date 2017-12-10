package cn.powerr.blog.blog.controller;

import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.ArticleWithUser;
import cn.powerr.blog.blog.service.ArticleService;
import cn.powerr.blog.common.resp.ArticlePicResult;
import cn.powerr.blog.user.entity.User;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 发表文章相关
 */
@Controller
@Slf4j
public class ArticleController {

    @Autowired
    @Qualifier("articleServiceImpl")
    private ArticleService articleService;


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
    public String publishArticle(@RequestBody Article article, HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("sessionUser");
        article.setTime(String.valueOf(System.currentTimeMillis()));
        article.setState(1);
        article.setUserId(sessionUser.getUserId());
        Article articleResult = articleService.saveArticle(article);
        model.addAttribute("article",articleResult);
        return "article";
    }

}
