package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.ArticleMapper;
import cn.powerr.blog.blog.dao.CommentMapper;
import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.ArticleExample;
import cn.powerr.blog.blog.entity.ArticleWithUser;
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
     *
     * @return
     */
    @Override
    @Transactional
    public Map searchLookHotInfo(Integer pageNum) {
        Map result = new HashMap();
        try {
            PageHelper.startPage(pageNum, 6);
            List<ArticleWithUser> lookHotList = articleMapper.selectMainPost();
            PageInfo<ArticleWithUser> pageInfo = new PageInfo<>(lookHotList);
            Iterator<ArticleWithUser> iterator = lookHotList.iterator();
            while (iterator.hasNext()) {
                Article next = iterator.next();
                if (next.getContent() != null) {
                    String content = next.getContent();
                    if (content.length() > 100) {
                        String subContent = content.substring(0, 100);
                        next.setContent(subContent);
                    }
                }
                if (next.getTitle() != null) {
                    String title = next.getTitle();
                    if (title.length() > 20) {
                        String subTitle = title.substring(0, 20);
                        next.setTitle(subTitle);
                    }
                }
            }
            result.put("pageInfo", pageInfo);
            result.put("lookHot", lookHotList);
        } catch (Exception e) {
            log.error("查询热门文章失败");
        }
        return result;
    }

    /**
     * 主页sidebar中mediumPost
     *
     * @return
     */
    @Override
    @Transactional
    public List<ArticleWithUser> searchLikeHotInfo() {
        List<ArticleWithUser> likeHot = null;
        try {
            PageHelper.startPage(1, 10);
            likeHot = articleMapper.selectMediumPost();
            Iterator<ArticleWithUser> iterator = likeHot.iterator();
            while (iterator.hasNext()) {
                ArticleWithUser next = iterator.next();
                if (next.getContent() != null) {
                    String content = next.getContent();
                    if (content.length() > 100) {
                        String subContent = content.substring(0, 100);
                        next.setContent(subContent);
                    }
                }
                if (next.getTitle() != null) {
                    String title = next.getTitle();
                    if (title.length() > 14) {
                        String subTitle = title.substring(0, 14);
                        next.setTitle(subTitle);
                    }
                }
            }
        } catch (Exception e) {
            log.error("查询喜欢文章失败");
        }
        return likeHot;
    }

    /**
     * 主页sidebar中最新发表文章
     *
     * @return
     */
    @Override
    @Transactional
    public List<Article> searchRecentPublishInfo() {
        List<Article> recentPublish = null;
        try {
            ArticleExample articleExample1 = new ArticleExample();
            ArticleExample.Criteria criteria = articleExample1.createCriteria();
            criteria.andTitleIsNotNull();
            criteria.andStateEqualTo(1);
            articleExample1.setOrderByClause("time DESC");
            PageHelper.startPage(1, 10);
            recentPublish = articleMapper.selectByExampleWithBLOBs(articleExample1);
            Iterator<Article> iterator = recentPublish.iterator();
            while (iterator.hasNext()) {
                Article next = iterator.next();
                if (next.getTitle() != null) {
                    String title = next.getTitle();
                    if (title.length() > 20) {
                        String subTitle = title.substring(0, 20);
                        next.setTitle(subTitle);

                    }
                }
            }
        } catch (Exception e) {
            log.error("查询最近发表文章失败");
        }
        return recentPublish;
    }

    /**
     * 热门博主实现
     *
     * @return
     */
    @Override
    public List<User> searchHotUser() {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNickNameIsNotNull();
        example.setOrderByClause("looknum DESC");
        PageHelper.startPage(1, 5);
        List<User> users = userMapper.selectByExample(example);
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()){
            User user = userIterator.next();
            if (user.getUserIntro() != null) {
                String userIntro = user.getUserIntro();
                if (userIntro.length() > 30) {
                    String subUserIntro = userIntro.substring(0, 30);
                    user.setUserIntro(subUserIntro);

                }
            }
        }
        return users;
    }
}
