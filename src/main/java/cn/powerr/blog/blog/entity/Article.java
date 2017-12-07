package cn.powerr.blog.blog.entity;

import cn.powerr.blog.user.entity.User;
import lombok.Data;

@Data
public class Article {
    private Integer id;

    private Integer userId;

    private String title;

    private Integer likenum;

    private Integer looknum;

    private String time;

    private Integer tagId;

    private String imgUrl;

    private String username;

    private String content;


}