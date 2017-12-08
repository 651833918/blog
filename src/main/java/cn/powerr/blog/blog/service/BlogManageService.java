package cn.powerr.blog.blog.service;

import cn.powerr.blog.blog.entity.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BlogManageService {

    Blog searchBlogInfo(Integer id);

    Blog saveBlog(MultipartFile file, Blog blog) throws IOException;
}
