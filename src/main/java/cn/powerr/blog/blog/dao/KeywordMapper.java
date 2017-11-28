package cn.powerr.blog.blog.dao;

import cn.powerr.blog.blog.entity.Keyword;
import cn.powerr.blog.blog.entity.KeywordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KeywordMapper {
    int countByExample(KeywordExample example);

    int deleteByExample(KeywordExample example);

    int insert(Keyword record);

    int insertSelective(Keyword record);

    List<Keyword> selectByExample(KeywordExample example);

    int updateByExampleSelective(@Param("record") Keyword record, @Param("example") KeywordExample example);

    int updateByExample(@Param("record") Keyword record, @Param("example") KeywordExample example);
}