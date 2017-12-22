package cn.powerr.blog.blog.service;

import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.ArticleWithBLOBs;
import cn.powerr.blog.blog.entity.ArticleWithTagAndUser;
import cn.powerr.blog.blog.entity.ArticleWithUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ArticleService {

    String uploadArticlePicture(MultipartFile file) throws IOException;

    ArticleWithBLOBs saveArticle(ArticleWithBLOBs article);

    ArticleWithTagAndUser refreshArticleMess(Integer articleId, Integer userId);

    void delArticle(Integer articleId);

    Integer editArticle(ArticleWithBLOBs articleWithBLOBs);

    int updateTagId(Integer articleId, Integer tagId);

    Map getArticlesWithTag(Integer tagId, Integer userId, Integer pageNum);

    Map showArticlesWithTag(Integer userId, Integer tagId, Integer pageNum);

    List<ArticleWithBLOBs> showArticlesNoState(Integer userId);

    int savaDrafts(ArticleWithBLOBs article);

    void publishDraft(Integer articleId);

    ArticleWithTagAndUser getEditArticle(Integer articleId);
}