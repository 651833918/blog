package cn.powerr.blog.blog.service;

import cn.powerr.blog.blog.entity.Article;

import java.util.List;
import java.util.Map;

public interface StationMasterBlogService {
    Map<String, List> searchArticle(Integer id, Integer pageNum);

    Map<String, Object> searchBlogAndUser(Integer id, Integer userId);

    Map<String, Object> searchStationMaster(Integer id);

    Map<String, Object> searchBlogAndUser(Integer id);

    Map<String, List> searchsidebarExceptTag(Integer id);
}