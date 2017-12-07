package cn.powerr.blog.user.controller;

import cn.powerr.blog.blog.service.MainhomeService;
import cn.powerr.blog.user.entity.User;
import cn.powerr.blog.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class PersonDataController {
    @Autowired
    private UserService userService;
    @Autowired
    private MainhomeService mainhomeService;

    @RequestMapping("/getPersonData")
    public String getPersonData() {
        return "person";
    }

    @RequestMapping("/savePersonData")
    public String savePersonData(User user, HttpSession session, @RequestParam(value = "file", required = true) MultipartFile file, Model model) {
        try {
            User sessionUser = (User) session.getAttribute("sessionUser");
            user.setId(sessionUser.getId());
            int result = userService.savePersonData(user, file);
            session.setAttribute("userInfo", sessionUser);
        } catch (IOException e) {
            log.error("资料修改失败");
        }
        return "person";
    }

}
