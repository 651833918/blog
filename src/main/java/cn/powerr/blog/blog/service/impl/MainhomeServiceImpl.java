package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.ArticleMapper;
import cn.powerr.blog.blog.dao.CommentMapper;
import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.ArticleExample;
import cn.powerr.blog.blog.service.MainhomeService;
import cn.powerr.blog.user.dao.UserMapper;
import cn.powerr.blog.user.entity.User;
import cn.powerr.blog.user.entity.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class MainhomeServiceImpl implements MainhomeService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 主页MainPost浏览数最多文章
     * @return
     */
    @Override
    @Transactional
    public Map searchLookHotInfo(Integer pageNum) {
        List<Article> lookHot = null;
        Map result = new HashMap();
        try {
            ArticleExample articleExample = new ArticleExample();
            articleExample.setOrderByClause("looknum DESC,likenum DESC");
            PageHelper.startPage(pageNum,3);
            lookHot = articleMapper.selectByExampleWithBLOBs(articleExample);
            PageInfo<Article> pageInfo = new PageInfo<>(lookHot);
            Iterator<Article> iterator = lookHot.iterator();
            while (iterator.hasNext()){
                Article next = iterator.next();
                String content = next.getContent().substring(0,100);
                next.setContent(content);
            }
            result.put("pageInfo",pageInfo);
            result.put("lookHot",lookHot);
        } catch (Exception e) {
            log.error("查询热门文章失败");
        }

        return result;
    }

    /**
     * 主页sidebar中mediumPost
     * @return
     */
    @Override
    @Transactional
    public List<Article> searchLikeHotInfo(){
        List<Article> likeHot = null;
        try {
            ArticleExample articleExample2 = new ArticleExample();
            articleExample2.setOrderByClause("likenum DESC");
            PageHelper.startPage(1, 4);
            likeHot = articleMapper.selectByExampleWithBLOBs(articleExample2);
            Iterator<Article> iterator = likeHot.iterator();
            while (iterator.hasNext()){
                Article next = iterator.next();
                String content = next.getContent().substring(0,50);
                next.setContent(content);
            }
        } catch (Exception e) {
            log.error("查询喜欢文章失败");
        }
        return likeHot;
    }

    /**
     * 主页sidebar中最新发表文章
     * @return
     */
    @Override
    @Transactional
    public List<Article> searchRecentPublishInfo(){
        List<Article> recentPublish = null;
        try {
            ArticleExample articleExample1 = new ArticleExample();
            articleExample1.setOrderByClause("time DESC");
            PageHelper.startPage(1,5);
            recentPublish = articleMapper.selectByExampleWithBLOBs(articleExample1);
            Iterator<Article> iterator = recentPublish.iterator();
            while (iterator.hasNext()){
                Article next = iterator.next();
                String content = next.getContent().substring(0,50);
                next.setContent(content);
            }
        } catch (Exception e) {
            log.error("查询最近发表文章失败");
        }
        return recentPublish;
    }

    /**
     * 热门博主实现
     * @return
     */
    @Override
    public List<User> searchHotUser() {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNickNameIsNotNull();
        example.setOrderByClause("looknum DESC");
        PageHelper.startPage(1,5);
        List<User> users = userMapper.selectByExample(example);
        return users;
    }
}
