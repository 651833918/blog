package cn.powerr.blog.user.service;

import cn.powerr.blog.user.entity.User;

public interface UserService {
     String checkUsername(String username);

     String checkEmail(String email);

     Integer register(User user);

    User checkUser(String username, String password);
}
