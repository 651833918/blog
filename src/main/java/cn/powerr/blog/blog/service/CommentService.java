package cn.powerr.blog.blog.service;

import cn.powerr.blog.blog.entity.Comment;
import cn.powerr.blog.blog.entity.CommentWithUser;

import java.util.List;

public interface CommentService {
    List<CommentWithUser> getArticleComments(Integer articleId);

    int submitComment(Comment comment);
}
