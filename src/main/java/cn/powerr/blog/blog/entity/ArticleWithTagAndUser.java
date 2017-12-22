package cn.powerr.blog.blog.entity;

import cn.powerr.blog.user.entity.User;
import lombok.Data;

@Data
public class ArticleWithTagAndUser extends ArticleWithBLOBs {
    private Tag tag;
    private User user;
}
