package cn.powerr.blog.blog.dao;

import cn.powerr.blog.blog.entity.Blogroll;
import cn.powerr.blog.blog.entity.BlogrollExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlogrollMapper {
    int countByExample(BlogrollExample example);

    int deleteByExample(BlogrollExample example);

    int deleteByPrimaryKey(Integer blogrollId);

    int insert(Blogroll record);

    int insertSelective(Blogroll record);

    List<Blogroll> selectByExample(BlogrollExample example);

    Blogroll selectByPrimaryKey(Integer blogrollId);

    int updateByExampleSelective(@Param("record") Blogroll record, @Param("example") BlogrollExample example);

    int updateByExample(@Param("record") Blogroll record, @Param("example") BlogrollExample example);

    int updateByPrimaryKeySelective(Blogroll record);

    int updateByPrimaryKey(Blogroll record);

    List<Blogroll> selectAll(Integer userId);
}