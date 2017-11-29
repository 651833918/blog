package cn.powerr.blog.blog.service;

import cn.powerr.blog.blog.entity.Article;

import java.util.List;
import java.util.Map;

public interface MainhomeService {
    Map searchLookHotInfo(Integer pageNum);
    List<Article> searchLikeHotInfo();
    List<Article> searchRecentPublishInfo();
}
