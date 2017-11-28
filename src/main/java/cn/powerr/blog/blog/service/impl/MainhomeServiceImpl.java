package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.ArticleMapper;
import cn.powerr.blog.blog.dao.CommentMapper;
import cn.powerr.blog.blog.service.MainhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.applet.Main;

import java.util.Map;

@Service
public class MainhomeServiceImpl implements MainhomeService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public Map serchInfo() {
        return null;
    }
}
