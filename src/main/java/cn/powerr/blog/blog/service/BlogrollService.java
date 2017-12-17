package cn.powerr.blog.blog.service;

import cn.powerr.blog.blog.entity.Blogroll;

import java.util.List;

public interface BlogrollService {
    List<Blogroll> searchBlogrolls(Integer userId);

    int addBlogroll(Blogroll blogroll);

    void delBlogroll(Integer blogrollId);

    int editBlogroll(Blogroll blogrollId);

    Blogroll searchBlogroll(Integer blogrollId);
}
