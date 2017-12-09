package cn.powerr.blog.user.entity;

import lombok.Data;

@Data
public class User {
    private Integer userId;

    private String username;

    private String password;

    private String nickName;

    private String email;

    private String sex;

    private String headImg;

    private String userIntro;

    private String birthday;

    private String looknum;

}