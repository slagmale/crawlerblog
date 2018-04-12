package com.whc.controller;

import com.whc.pojo.Blog;
import com.whc.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:WangHaoChao
 * Date:2018/4/8
 * Time:11:05
 * Description:爬博客前端控制器
 */
@Controller
@RequestMapping("crawler")
public class CrawlerController {
    @Autowired
    private CrawlerService crawlerService;

    @RequestMapping(value = "/start.action")
    public @ResponseBody Map<String,Object> startCrawler(String userName){
        crawlerService.startCrawler(userName);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","爬取完毕");
        return map;
    }

    @RequestMapping(value = "/selectBlogsByPage.action")
    public String selectBlogsByPage(Integer pageNow,Model model){
        crawlerService.selectBlogsByPage(pageNow,model);
        return "blogsByPage";
    }

    @RequestMapping(value = "/selectBlogById.action")
    public String selectBlogById(Model model,Integer id){
        Blog blog = crawlerService.selectBlogById(id);
        model.addAttribute("blog",blog);
        return "blogDetail";
    }
}
