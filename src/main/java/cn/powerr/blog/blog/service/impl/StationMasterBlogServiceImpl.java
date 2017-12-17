package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.ArticleMapper;
import cn.powerr.blog.blog.dao.BlogMapper;
import cn.powerr.blog.blog.entity.ArticleWithBLOBs;
import cn.powerr.blog.blog.entity.ArticleExample;
import cn.powerr.blog.blog.entity.ArticleWithUser;
import cn.powerr.blog.blog.entity.Blog;
import cn.powerr.blog.user.dao.UserMapper;
import cn.powerr.blog.user.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
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
    private DateTime dateTime;

    @Override
    public Map searchArticle(Integer id, Integer pageNum,Integer pageSize) {
        List<ArticleWithUser> articles = null;
        PageInfo<ArticleWithUser> pageInfo = null;
        Map resultMap = new HashMap<>();
        try {
            PageHelper.startPage(pageNum, pageSize);
            articles = articleMapper.selectUserRecent(id);
            pageInfo = new PageInfo<>(articles);
            Iterator<ArticleWithUser> iterator = articles.iterator();
            while (iterator.hasNext()) {
                ArticleWithUser next = iterator.next();
                dateTime = new DateTime(Long.parseLong(next.getTime()));
                next.setTime(dateTime.toString("EEEE dd MMMM, yyyy HH:mm:ssa"));
                if (next.getContent() != null) {
                    String content = next.getContent();
                    if (content.length() > 200) {
                        String subContent = content.substring(0, 200);
                        next.setContent(subContent);
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

    @Override
    public Map<String, Object> searchBlogAndUser(Integer id, Integer userId) {
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

    @Override
    public Map<String, Object> searchStationMaster(Integer id) {
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
    public Map<String, Object> searchBlogAndUser(Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        User user = null;
        Blog blog = null;
        try {
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
        List<ArticleWithUser> readHot = null;
        List<ArticleWithUser> commentHot = null;
        List<ArticleWithUser> likeHot = null;
        try {
            readHot = articleMapper.selectReadHot(userId);
            commentHot = articleMapper.selectCommentHot(userId);
            likeHot = articleMapper.selectLikeHot(userId);
            Iterator<ArticleWithUser> readIterator = readHot.iterator();
            while (readIterator.hasNext()) {
                ArticleWithUser next = readIterator.next();
                if (next.getTitle() != null) {
                    String title = next.getTitle();
                    if (title.length() > 20) {
                        String subTitle = title.substring(0, 20);
                        next.setTitle(subTitle);
                    }
                }
            }
            Iterator<ArticleWithUser> commentIterator = commentHot.iterator();
            while (commentIterator.hasNext()) {
                ArticleWithUser next = commentIterator.next();
                if (next.getTitle() != null) {
                    String title = next.getTitle();
                    if (title.length() > 20) {
                        String subTitle = title.substring(0, 20);
                        next.setTitle(subTitle);
                    }
                }
            }
            Iterator<ArticleWithUser> likeIterator = likeHot.iterator();
            while (likeIterator.hasNext()) {
                ArticleWithUser next = likeIterator.next();
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
