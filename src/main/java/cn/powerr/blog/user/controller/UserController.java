package cn.powerr.blog.user.controller;

import cn.powerr.blog.user.entity.User;
import cn.powerr.blog.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/logIn")
    public String register() {
        String registerUrl = "login";
        return registerUrl;
    }

    /**
     * 注册检查username是否已经被注册
     *
     * @return "username_succ"或者"username_fail"
     */
    @RequestMapping("/user/checkUsername")
    @ResponseBody
    public String checkUsername(HttpServletRequest request) {
        String result = userService.checkUsername(request.getParameter("username"));
        return result;
    }

    @RequestMapping("/user/checkEmail")
    @ResponseBody
    public String checkEmail(HttpServletRequest request) {
        String result = userService.checkEmail(request.getParameter("email"));
        return result;
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody User user) {
        Integer isRegister = userService.register(user);
        String result = "regist_succ";
        if (isRegister > 0) {
            return result;
        }
        result = "regist_fail";
        return result;
    }

}
