package cn.powerr.blog;

import cn.powerr.blog.common.utils.MD5Utils;
import cn.powerr.blog.user.dao.UserMapper;
import cn.powerr.blog.user.entity.User;
import cn.powerr.blog.user.entity.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class Tests {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Test
    public void testPageHelper() {
        PageHelper.startPage(1, 10);
        UserExample example = new UserExample();
        List<User> list = userMapper.selectByExample(example);

        PageInfo<User> info = new PageInfo<>(list);
        System.out.println(info.getTotal());
    }

    @Test
    public void insertTest() {
        User user = new User();
        user.setUsername("xuebengang");
        user.setPassword("1111");
        userMapper.insertSelective(user);
        User resultUser = userMapper.selectByPrimaryKey(user.getUserId());
        System.out.println(resultUser.getUserId());
    }

    @Test
    public void testjodaTime(){
        DateTime dateTime = new DateTime(System.currentTimeMillis());
        System.out.println(dateTime);
        System.out.println(dateTime.dayOfYear());
        System.out.println(dateTime.dayOfMonth());
        System.out.println(dateTime.toLocalDateTime());
        System.out.println(dateTime.toDateTime());
        System.out.println("1512887671865");
        System.out.println(System.currentTimeMillis());
    }
}
