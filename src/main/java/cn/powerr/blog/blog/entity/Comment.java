package cn.powerr.blog.blog.entity;

public class Comment {
    private Integer id;

    private Integer userId;

    private Integer articleId;

    private String commentContent;

    private String commontTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public String getCommontTime() {
        return commontTime;
    }

    public void setCommontTime(String commontTime) {
        this.commontTime = commontTime == null ? null : commontTime.trim();
    }
}