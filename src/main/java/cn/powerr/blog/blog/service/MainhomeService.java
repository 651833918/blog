package cn.powerr.blog.blog.service;

import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.ArticleWithUser;
import cn.powerr.blog.user.entity.User;

import java.util.List;
import java.util.Map;

public interface MainhomeService {
    Map searchLookHotInfo(Integer pageNum);
    List<ArticleWithUser> searchLikeHotInfo();
    List<Article> searchRecentPublishInfo();
    List<User> searchHotUser();
}
