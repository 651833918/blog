package cn.powerr.blog.user.service;

import cn.powerr.blog.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
     String checkUsername(String username);

     String checkEmail(String email);

     Integer register(User user);

    User checkUser(String username, String password);

    int savePersonData(User user, MultipartFile file) throws IOException;

    User searchUser(Integer id);

    User searchUserByEmail();

}
