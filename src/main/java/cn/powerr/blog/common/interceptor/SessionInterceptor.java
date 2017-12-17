package cn.powerr.blog.common.interceptor;

import cn.powerr.blog.user.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        if ((uri.indexOf("register") >= 0) || (uri.indexOf("login") > 0) || (uri.indexOf("mainHome") >= 0 || (uri.indexOf("showStationMasterBlog")) >= 0 || (uri.indexOf("logInPage") >= 0 || (uri.equals("/"))))) {
            return true;
        }
        if ((uri.indexOf("checkUsername") >= 0) || (uri.indexOf("checkEmail") >= 0) || (uri.indexOf("showPerosonPage") >= 0) || (uri.indexOf("getArticleMessage") >= 0)) {
            return true;
        }
        User user = (User) httpServletRequest.getSession().getAttribute("sessionUser");
        if (user != null) {
            return true;
        }
        httpServletRequest.getRequestDispatcher("/mainHome/1").forward(httpServletRequest, httpServletResponse);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
