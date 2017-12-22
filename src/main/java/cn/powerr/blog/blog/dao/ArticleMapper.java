package cn.powerr.blog.blog.dao;

import cn.powerr.blog.blog.entity.*;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan(value = "articleMapper")
public interface ArticleMapper {
    int countByExample(ArticleExample example);

    int deleteByExample(ArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleWithBLOBs record);

    int insertSelective(ArticleWithBLOBs record);

    List<ArticleWithBLOBs> selectByExampleWithBLOBs(ArticleExample example);

    List<Article> selectByExample(ArticleExample example);

    ArticleWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleWithBLOBs record, @Param("example") ArticleExample example);

    int updateByExampleWithBLOBs(@Param("record") ArticleWithBLOBs record, @Param("example") ArticleExample example);

    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByPrimaryKeySelective(ArticleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleWithBLOBs record);

    int updateByPrimaryKey(Article record);


    List<ArticleWithUser> selectMainPost();
    List<ArticleWithUser> selectMediumPost();

    List<ArticleWithUser> selectReadHot(@Param("userId") Integer userId);

    List<ArticleWithUser> selectCommentHot(@Param("userId")Integer userId);

    List<ArticleWithUser> selectLikeHot(@Param("userId")Integer userId);

    List<ArticleWithUser> selectRecent();

    List<ArticleWithTagAndUser> selectUserRecent(@Param("userId")Integer userId);

    void updateArticleByLookNum(Integer articleId);

    ArticleWithTagAndUser selectWithTag(Integer articleId);

    int updateArticleTag(@Param("articleId") Integer articleId, @Param("tagId") Integer tagId);

    int updateArticleTagToNull(Integer tagId);

    List<ArticleWithTagAndUser> selectArticlesWithTag(@Param(value = "tagId") Integer tagId,@Param(value = "userId") Integer userId);

    void updateDraft(@Param("articleId")Integer articleId);

    int updateLikeNum(@Param("articleId") Integer articleId);

    int updateCommentnum(@Param("articleId")Integer articleId);
}