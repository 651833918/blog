package cn.powerr.blog.blog.service;

import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.ArticleWithUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArticleService {

    String uploadArticlePicture(MultipartFile file) throws IOException;

    Article saveArticle(Article article);
}
