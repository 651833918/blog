package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.ArticleMapper;
import cn.powerr.blog.blog.dao.CommentMapper;
import cn.powerr.blog.blog.dao.TagMapper;
import cn.powerr.blog.blog.entity.ArticleExample;
import cn.powerr.blog.blog.entity.ArticleWithBLOBs;
import cn.powerr.blog.blog.entity.ArticleWithTagAndUser;
import cn.powerr.blog.blog.entity.Tag;
import cn.powerr.blog.blog.service.ArticleService;
import cn.powerr.blog.common.constants.Constants;
import cn.powerr.blog.common.utils.QiniuFileUploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    @Qualifier("articleMapper")
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private TagMapper tagMapper;
    private DateTime dateTime;
    /**
     * 文章图片上传回传图片路径
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public String uploadArticlePicture(MultipartFile file) throws IOException {
        String filename = QiniuFileUploadUtil.uploadArticleImg(file);
        String result = Constants.QINIU_PROTOCOL+ Constants.QINIU_ARTICLE_IMG_BUCKET_URL+"/"+filename;
        return result;
    }

    @Override
    @Transactional
    public ArticleWithBLOBs saveArticle(ArticleWithBLOBs article) {
        ArticleWithBLOBs articleRestlt = null;
        try {
            articleMapper.insertSelective(article);
            articleRestlt = articleMapper.selectByPrimaryKey(article.getId());
        } catch (Exception e) {
            log.error("发表博客失败："+e.getMessage());
        }
        return articleRestlt;
    }

    @Override
    public ArticleWithTagAndUser refreshArticleMess(Integer articleId, Integer userId) {
        articleMapper.updateArticleByLookNum(articleId);
        ArticleWithTagAndUser article = articleMapper.selectWithTag(articleId);
        return article;
    }

    @Override
    public ArticleWithTagAndUser getEditArticle(Integer articleId) {
        ArticleWithTagAndUser article = articleMapper.selectWithTag(articleId);
        return article;
    }

    /**
     * 点赞功能
     * @param articleId
     */
    @Override
    public int clickLikeButton(Integer articleId) {
        int resp = 0;
        try {
            resp = articleMapper.updateLikeNum(articleId);
        } catch (Exception e) {
            log.error("点赞失败");
        }
        return resp;
    }

    @Override
    @Transactional
    public void delArticle(Integer articleId) {
        try{
            ArticleExample example = new ArticleExample();
            ArticleExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(articleId);
            articleMapper.deleteByExample(example);
            commentMapper.deleteCommentsByArticle(articleId);
        }catch (Exception e){
            log.error("删除文章失败");
        }
    }

    @Override
    public Integer editArticle(ArticleWithBLOBs articleWithBLOBs) {
        int result = 0;
        try {
            result = articleMapper.updateByPrimaryKeySelective(articleWithBLOBs);
        } catch (Exception e) {
            log.error("编辑文章失败");
        }
        return result;
    }

    @Override
    public int updateTagId(Integer articleId, Integer tagId) {
        int result = 0;
        try {
             result =  articleMapper.updateArticleTag(articleId, tagId);
        } catch (Exception e) {
            log.error("更新文章标签失败");
        }
        return result;
    }

    /**
     * 根据标签和用户ID查找文章
     * @param tagId
     * @param userId
     * @param pageNum
     * @return
     */
    @Override
    @Transactional
    public Map getArticlesWithTag(Integer tagId, Integer userId, Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        List<ArticleWithTagAndUser> articles = articleMapper.selectArticlesWithTag(tagId, userId);
        PageInfo<ArticleWithTagAndUser> pageInfo = new PageInfo<>(articles);
        Tag tag = tagMapper.selectByPrimaryKey(tagId);
        //返回结果
        Map result = new HashMap();
        result.put("articles",articles);
        result.put("pageInfo",pageInfo);
        result.put("tag",tag);
        return result;
    }

    @Override
    public Map showArticlesWithTag(Integer userId, Integer tagId, Integer pageNum) {
        PageHelper.startPage(pageNum,15);
        List<ArticleWithTagAndUser> articleWithTagAndUsers = articleMapper.selectArticlesWithTag(tagId, userId);
        PageInfo<ArticleWithTagAndUser> pageInfo = new PageInfo<>(articleWithTagAndUsers);
        Map resultMap = new HashMap();
        Iterator<ArticleWithTagAndUser> iterator = articleWithTagAndUsers.iterator();
        while (iterator.hasNext()) {
            ArticleWithTagAndUser next = iterator.next();
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
        resultMap.put("articles",articleWithTagAndUsers);
        resultMap.put("pageInfo",pageInfo);
        return resultMap;
    }

    @Override
    public List<ArticleWithBLOBs> showArticlesNoState(Integer userId) {
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andStateEqualTo(0);
        List<ArticleWithBLOBs> articles = articleMapper.selectByExampleWithBLOBs(example);
        return articles;
    }

    @Override
    public int savaDrafts(ArticleWithBLOBs article) {
        int result = 0;
        try {
            result  = articleMapper.insertSelective(article);
        } catch (Exception e) {
            log.error("新建草稿失败");
        }
        return result;
    }

    @Override
    public void publishDraft(Integer articleId) {

        try {
            articleMapper.updateDraft(articleId);
        } catch (Exception e) {
            log.error("发布草稿失败");
        }
    }
}
