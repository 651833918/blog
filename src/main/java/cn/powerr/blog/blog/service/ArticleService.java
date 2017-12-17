package cn.powerr.blog.blog.service;

import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.ArticleWithBLOBs;
import cn.powerr.blog.blog.entity.ArticleWithUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArticleService {

    String uploadArticlePicture(MultipartFile file) throws IOException;

    ArticleWithBLOBs saveArticle(ArticleWithBLOBs article);

    ArticleWithBLOBs refreshArticleMess(Integer articleId, Integer userId);

    void delArticle(Integer articleId);

    Integer editArticle(ArticleWithBLOBs articleWithBLOBs);
}