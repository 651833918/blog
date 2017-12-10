package cn.powerr.blog.blog.entity;

import lombok.Data;

@Data
public class Blogroll {
    private Integer blogrollId;

    private Integer userId;

    private String describe;

    private String url;

}