package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.ArticleMapper;
import cn.powerr.blog.blog.dao.BlogMapper;
import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.ArticleExample;
import cn.powerr.blog.blog.entity.ArticleWithUser;
import cn.powerr.blog.blog.entity.Blog;
import cn.powerr.blog.user.dao.UserMapper;
import cn.powerr.blog.user.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Xue
 * @date 2017/12/8 20:30
 * @description 博客站长页面相关信息
 */
@Service
@Slf4j
public class StationMasterBlogServiceImpl implements cn.powerr.blog.blog.service.StationMasterBlogService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Map searchArticle(Integer id, Integer pageNum) {
        List<Article> articles = null;
        PageInfo<Article> pageInfo = null;
        Map resultMap = new HashMap<>();
        try {
            PageHelper.startPage(pageNum, 15);
            ArticleExample example = new ArticleExample();
            ArticleExample.Criteria criteria = example.createCriteria();
            criteria.andStateEqualTo(1);
            criteria.andUserIdEqualTo(id);
            criteria.andTitleIsNotNull();
            example.setOrderByClause("time DESC");
            articles = articleMapper.selectByExampleWithBLOBs(example);
            pageInfo = new PageInfo<>(articles);
            Iterator<Article> iterator = articles.iterator();
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
        } catch (Exception e) {
            log.error("查询文章失败");
        }
        resultMap.put("articles", articles);
        resultMap.put("pageInfo", pageInfo);
        return resultMap;
    }

    @Transactional
    @Override
    public Map<String, Object> searchBlogAndUser(Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        User user = null;
        Blog blog = null;
        try {
            userMapper.updateBlogLookNum(id);
            user = userMapper.selectByPrimaryKey(id);
            blog = blogMapper.selectBlogThroughUserId(id);
        } catch (Exception e) {
            log.error("查询用户或博客信息失败");
        }
        resultMap.put("user", user);
        resultMap.put("blog", blog);
        return resultMap;
    }

    @Transactional
    @Override
    public Map<String, List> searchsidebarExceptTag(Integer userId) {
        Map<String, List> resultMap = new HashMap<>();
        List<Article> readHot = null;
        List<Article> commentHot = null;
        List<Article> likeHot = null;
        try {
            readHot = articleMapper.selectReadHot(userId);
            commentHot = articleMapper.selectCommentHot(userId);
            likeHot = articleMapper.selectLikeHot(userId);
            Iterator<Article> readIterator = readHot.iterator();
            while (readIterator.hasNext()) {
                Article next = readIterator.next();
                if (next.getTitle() != null) {
                    String title = next.getTitle();
                    if (title.length() > 20) {
                        String subTitle = title.substring(0, 20);
                        next.setTitle(subTitle);
                    }
                }
            }
            Iterator<Article> commentIterator = commentHot.iterator();
            while (commentIterator.hasNext()) {
                Article next = commentIterator.next();
                if (next.getTitle() != null) {
                    String title = next.getTitle();
                    if (title.length() > 20) {
                        String subTitle = title.substring(0, 20);
                        next.setTitle(subTitle);
                    }
                }
            }
            Iterator<Article> likeIterator = likeHot.iterator();
            while (likeIterator.hasNext()) {
                Article next = likeIterator.next();
                if (next.getTitle() != null) {
                    String title = next.getTitle();
                    if (title.length() > 20) {
                        String subTitle = title.substring(0, 20);
                        next.setTitle(subTitle);
                    }
                }
            }
        } catch (Exception e) {
            log.error("博客排行信息查找错误");
        }
        resultMap.put("readHot", readHot);
        resultMap.put("commentHot", commentHot);
        resultMap.put("likeHot", likeHot);
        return resultMap;
    }
}
