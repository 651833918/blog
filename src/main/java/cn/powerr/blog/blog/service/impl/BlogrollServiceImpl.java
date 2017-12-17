package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.BlogrollMapper;
import cn.powerr.blog.blog.entity.Blogroll;
import cn.powerr.blog.blog.service.BlogrollService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogrollServiceImpl implements BlogrollService {
    @Autowired
    private BlogrollMapper blogrollMapper;

    @Override
    public List<Blogroll> searchBlogrolls(Integer userId) {
        List<Blogroll> blogrolls = blogrollMapper.selectAll(userId);
        return blogrolls;
    }

    @Override
    public int addBlogroll(Blogroll blogroll) {
        int resp = 0;
        try {
            resp = blogrollMapper.insertSelective(blogroll);
        } catch (Exception e) {
            log.error("新增友链失败");
        }
        return resp;
    }

    @Override
    public void delBlogroll(Integer blogrollId) {
        try {
            blogrollMapper.deleteByPrimaryKey(blogrollId);
        } catch (Exception e) {
            log.error("删除友链失败");
        }
    }

    @Override
    public int editBlogroll(Blogroll blogroll) {
        int reslut = 0;
        try {
           reslut = blogrollMapper.updateByPrimaryKeySelective(blogroll);
        } catch (Exception e) {
            log.error("更新友链失败");
        }
        return reslut;
    }

    @Override
    public Blogroll searchBlogroll(Integer blogrollId) {
        Blogroll blogroll = null;
        try {
            blogroll = blogrollMapper.selectByPrimaryKey(blogrollId);
        } catch (Exception e) {
            log.error("查看单条友链失败");
        }
        return blogroll;
    }
}
