package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.BlogMapper;
import cn.powerr.blog.blog.entity.Blog;
import cn.powerr.blog.blog.entity.BlogExample;
import cn.powerr.blog.blog.service.BlogManageService;
import cn.powerr.blog.common.constants.Constants;
import cn.powerr.blog.common.utils.QiniuFileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class BlogManageServiceImpl implements BlogManageService {
    @Autowired
    private BlogMapper blogMapper;


    @Override
    public Blog searchBlogInfo(Integer id) {
        Blog blog = blogMapper.selectBlogThroughUserId(id);
        return blog;
    }

    /**
     * 博客配置
     * @param file
     * @param blog
     * @return
     * @throws IOException
     */
    @Transactional
    @Override
    public Blog saveBlog(MultipartFile file, Blog blog) throws IOException {
        String fileName = QiniuFileUploadUtil.uploadHeadImg(file);
        String blogUrl = Constants.QINIU_PROTOCOL + Constants.QINIU_HEAD_IMG_BUCKET_URL + "/" + fileName;
        blog.setBlogUrl(blogUrl);

        BlogExample example = new BlogExample();
        BlogExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(blog.getUserId());
        blogMapper.updateByExampleSelective(blog,example);
        return blog;
    }
}
