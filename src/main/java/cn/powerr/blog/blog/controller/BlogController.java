package cn.powerr.blog.blog.controller;

import cn.powerr.blog.blog.entity.ArticleWithUser;
import cn.powerr.blog.blog.entity.Blog;
import cn.powerr.blog.blog.entity.Blogroll;
import cn.powerr.blog.blog.service.BlogManageService;
import cn.powerr.blog.blog.service.BlogrollService;
import cn.powerr.blog.blog.service.StationMasterBlogService;
import cn.powerr.blog.user.entity.User;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class BlogController {

    @Autowired
    private BlogManageService blogManageService;
    @Autowired
    private StationMasterBlogService stationMasterBlogService;
    @Autowired
    private BlogrollService blogrollService;

    @RequestMapping("/blogManage")
    public String manageBlog(Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Blog blogInfo = blogManageService.searchBlogInfo(sessionUser.getUserId());
        model.addAttribute("blogInfo", blogInfo);
        return "blogmanage";
    }

    @RequestMapping("/saveBlog")
    public String saveBlog(Blog blog, HttpSession session, HttpServletRequest request, Model model) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        User sessionUser = (User) session.getAttribute("sessionUser");
        blog.setUserId(sessionUser.getUserId());
        try {
            Blog result = blogManageService.saveBlog(file, blog);
            model.addAttribute("blogInfo", result);
        } catch (IOException e) {
            log.error("文件上传失败");
        }
        return "blogmanage";
    }

    @RequestMapping("/commentManage")
    public String manageComment(Model model) {
        return "commentmanage";
    }

    /**
     * 跳到展示友链页面
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/blogrollManage")
    public String manageBlogroll(Model model,HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer userId = sessionUser.getUserId();
        List<Blogroll> blogrolls = null;
        blogrolls = blogrollService.searchBlogrolls(userId);
        if (blogrolls.size() > 0) {
            model.addAttribute("blogrolls", blogrolls);
        }
        return "addblogroll";
    }


    /**
     * 跳到新增友链界面
     * @return
     */
    @RequestMapping("/showAddBlogroll")
    public String showAddBlogroll(){
        return "blogrollmanage";
    }

    @RequestMapping("/delBlogroll/{id}")
    public String delBlogroll(@PathVariable(value = "id")Integer blogrollId){
        blogrollService.delBlogroll(blogrollId);
        return "redirect:/blogrollManage";
    }

    /**
     * 展示editBlogroll页面
     * @param blogrollId
     * @param model
     * @return
     */
    @RequestMapping("/showEditBlogroll/{id}")
    public String showEditBlogroll(@PathVariable(value = "id") Integer blogrollId,Model model){
        Blogroll blogroll = blogrollService.searchBlogroll(blogrollId);
        model.addAttribute("blogrollInfo",blogroll);
        return "blogrollmanage";
    }

    @RequestMapping("/editBlogroll/{id}")
    @ResponseBody
    public String editBlogroll(@PathVariable(value = "id")Integer id,@RequestBody Blogroll blogroll){
        blogroll.setBlogrollId(id);
        int result = blogrollService.editBlogroll(blogroll);
        if(result > 0){
            return "edit_succ";
        }
        return "edit_fail";
    }
    /**
     * 新增友链
     * @param blogroll
     * @param session
     * @return
     */
    @RequestMapping("/addBlogroll")
    @ResponseBody
    public String addBlogroll(@RequestBody Blogroll blogroll,HttpSession session){
        User userSession = (User) session.getAttribute("sessionUser");
        blogroll.setUserId(userSession.getUserId());
        int resp = blogrollService.addBlogroll(blogroll);
        if(resp >0){
            return "add_succ";
        }
        return "add_fail";
    }


    @RequestMapping("/tagManage")
    public String manageTag(Model model) {

        return "tagmanage";
    }

    @RequestMapping("/draftsManage")
    public String manageDrafts(Model model) {

        return "draftsmanage";
    }

    @RequestMapping("/articleManage/{pageNum}")
    public String manageArticle(@PathVariable("pageNum") Integer pageNum, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<ArticleWithUser> articles = null;
        PageInfo<ArticleWithUser> pageInfo = null;
        try {
            Map stringListMap = stationMasterBlogService.searchArticle(sessionUser.getUserId(), pageNum, 10);
            articles = (List<ArticleWithUser>) stringListMap.get("articles");
            pageInfo = (PageInfo<ArticleWithUser>) stringListMap.get("pageInfo");
        } catch (Exception e) {
            log.error("展示文章管理页面失败", e.getMessage());
        }
        model.addAttribute("articlesManage", articles);
        model.addAttribute("articlesPageInfo", pageInfo);
        return "articlemanage";
    }

    @RequestMapping("/writeArticle")
    public String writeArticle() {
        return "write_article";
    }
}
