package cn.powerr.blog;

import cn.powerr.blog.user.dao.UserMapper;
import cn.powerr.blog.user.entity.User;
import cn.powerr.blog.user.entity.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class Tests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testPageHelper() {
        PageHelper.startPage(1, 10);
        UserExample example = new UserExample();
        List<User> list = userMapper.selectByExample(example);

        PageInfo<User> info = new PageInfo<>(list);
        System.out.println(info.getTotal());
    }
}
