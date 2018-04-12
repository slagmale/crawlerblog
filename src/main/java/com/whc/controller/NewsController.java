package com.whc.controller;

import com.whc.pojo.News;
import com.whc.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * author:WangHaoChao
 * Date:2018/4/10
 * Time:17:54
 * Description:新闻前端控制器
 */
@Controller
@RequestMapping("news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/start.action")
    public @ResponseBody Map<String,Object> startCrawlerNews(){
        newsService.startCrawlerNews();
        Map<String,Object> map = new HashMap<>();
        map.put("msg","爬取完毕");
        return map;
    }

    @RequestMapping(value = "/selectNewsByPage.action")
    public String selectNewsByPage(Integer pageNow,Model model){
        newsService.selectNewssByPage(pageNow,model);
        return "newsByPage";
    }

    @RequestMapping(value = "/selectNewsById.action")
    public String selectNewsById(Integer id,Model model){
        News news = newsService.selectNewsById(id);
        model.addAttribute("news", news);
        return "newsDetail";
    }
}
