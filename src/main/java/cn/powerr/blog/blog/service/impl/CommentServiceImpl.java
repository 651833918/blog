package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.ArticleMapper;
import cn.powerr.blog.blog.dao.CommentMapper;
import cn.powerr.blog.blog.entity.Comment;
import cn.powerr.blog.blog.entity.CommentWithUser;
import cn.powerr.blog.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<CommentWithUser> getArticleComments(Integer articleId) {
        List<CommentWithUser> comments = null;
        try {
            comments = commentMapper.selectCommentsWithUser(articleId);
        } catch (Exception e) {
            log.error(e.getMessage()+":查询评论失败");
        }
        return comments;
    }

    /**
     * 新增回复
     * @param comment
     */
    @Override
    @Transactional
    public int submitComment(Comment comment) {
        int result = 0;
        try {
           result = commentMapper.insertSelective(comment);
            articleMapper.updateCommentnum(comment.getArticleId());
        } catch (Exception e) {
            log.error("新增回复失败");
        }
        return result;
    }
}
