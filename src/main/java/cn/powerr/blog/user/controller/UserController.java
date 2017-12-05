package cn.powerr.blog.user.controller;

import cn.powerr.blog.user.entity.User;
import cn.powerr.blog.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 *@author Xue
 *@date 2017/12/5 21:27
 *@description  实现用户相关的信息
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/user/logInPage")
    public String register(HttpSession session) {
        String registerUrl = "login";
//        User sessionUser = (User) session.getAttribute("sessionUser");
//        if (sessionUser != null) {
//            session.invalidate();
//        }
//        session.invalidate();
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

    /**
     * 登录模块
     * @param req
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/login")
    @ResponseBody
    public String login(HttpServletRequest req,HttpSession session){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userService.checkUser(username, password);
        session.setAttribute("sessionUser",user);
        session.setMaxInactiveInterval(60*60*24);
//        model.addAttribute("user",user);
        if (user != null){
            return "login_succ";
        }
        return "login_fail";
    }

    @RequestMapping(value = "/user/logOut")
    @ResponseBody
    public String logOut(HttpSession session){
        session.invalidate();
        return "logout_succ";
    }

}
