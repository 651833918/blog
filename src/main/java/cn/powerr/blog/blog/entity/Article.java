package cn.powerr.blog.blog.entity;

import cn.powerr.blog.user.entity.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class Article {
    private Integer id;

    private Integer userId;

    private String title;

    private Integer likenum;

    private Integer looknum;

    private String time;

    private Integer tagId;

    private Integer commentnum;

    private Integer state;


}