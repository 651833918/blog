package cn.powerr.blog.blog.dao;

import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.ArticleExample;
import java.util.List;

import cn.powerr.blog.blog.entity.ArticleWithBLOBs;
import cn.powerr.blog.blog.entity.ArticleWithUser;
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

    List<ArticleWithUser> selectUserRecent(@Param("userId")Integer userId);

    void updateArticleByLookNum(Integer articleId);
}