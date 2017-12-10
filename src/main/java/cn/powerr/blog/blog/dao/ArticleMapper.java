package cn.powerr.blog.blog.dao;

import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.entity.ArticleExample;
import java.util.List;

import cn.powerr.blog.blog.entity.ArticleWithUser;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan(value = "articleMapper")
public interface ArticleMapper {
    int countByExample(ArticleExample example);

    int deleteByExample(ArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    List<Article> selectByExampleWithBLOBs(ArticleExample example);

    List<Article> selectByExample(ArticleExample example);

    Article selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByExampleWithBLOBs(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List<ArticleWithUser> selectMainPost();
    List<ArticleWithUser> selectMediumPost();

    List<Article> selectReadHot(@Param("userId") Integer userId);

    List<Article> selectCommentHot(@Param("userId")Integer userId);

    List<Article> selectLikeHot(@Param("userId")Integer userId);
}