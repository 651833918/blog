package cn.powerr.blog.blog.entity;

import lombok.Data;

@Data
public class Comment {
    private Integer commentId;

    private Integer userId;

    private Integer articleId;

    private String commentContent;

    private String commontTime;

}