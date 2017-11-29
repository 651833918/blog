package cn.powerr.blog.blog.controller;

import cn.powerr.blog.blog.entity.Article;
import cn.powerr.blog.blog.service.MainhomeService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ShowPageController {
    @Autowired
    private MainhomeService mainhomeService;


    /**
     * 主页用于展示的文章信息等
     * @param model
     * @return
     */
    @RequestMapping(value = "/mainHome/{pageNum}")
    @Transactional
    public String redirctToMainHome(@PathVariable("pageNum")Integer pageNum,Model model){
        String mainHomeUrl = "mainhome";
        try {
            List<Article> likeHotList = mainhomeService.searchLikeHotInfo();
            Map result = mainhomeService.searchLookHotInfo(pageNum);
            List lookHotList = (List) result.get("lookHot");
            PageInfo pageInfo = (PageInfo) result.get("pageInfo");
            List<Article> recentPublishList = mainhomeService.searchRecentPublishInfo();
            model.addAttribute("likeHotList",likeHotList);
            model.addAttribute("lookHotList",lookHotList);
            model.addAttribute("recentPublishList",recentPublishList);
            model.addAttribute("pageInfo",pageInfo);
        } catch (Exception e) {
            log.error("查看主页信息失败");
        }
        return mainHomeUrl;
    }

}
