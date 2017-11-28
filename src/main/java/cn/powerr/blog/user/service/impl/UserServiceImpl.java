package cn.powerr.blog.user.service.impl;

import cn.powerr.blog.common.utils.MD5Utils;
import cn.powerr.blog.user.dao.UserMapper;
import cn.powerr.blog.user.entity.User;
import cn.powerr.blog.user.entity.UserExample;
import cn.powerr.blog.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public String checkUsername(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(example);
        if (!userList.isEmpty()) {
            return "username_fail";
        }
        return "username_succ";
    }

    @Override
    public String checkEmail(String email) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> userList = userMapper.selectByExample(example);
        if (!userList.isEmpty()){
            return "email_fail";
        }
        return "email_succ";
    }

    @Override
    public Integer register(User user) {
        int result = 0;
        try {
            user.setPassword(MD5Utils.encryptPassword(user.getPassword()));
            result = userMapper.insertSelective(user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }
}
