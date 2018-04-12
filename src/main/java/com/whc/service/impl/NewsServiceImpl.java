package com.whc.service.impl;

import com.whc.mapper.NewsMapper;
import com.whc.pojo.News;
import com.whc.service.NewsService;
import com.whc.util.BlogNews;
import com.whc.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import us.codecraft.webmagic.Spider;

import java.util.*;

/**
 * author:WangHaoChao
 * Date:2018/4/10
 * Time:19:04
 * Description:
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    private BlogNews blogNews;

    @Override
    public void startCrawlerNews() {
        blogNews = new BlogNews(newsMapper);

        if (newsMapper.getNewsCount()!=0){
            newsMapper.deleteAllNews();
        }
        //定时爬取
        timeset();
    }

    //对新闻分页查询
    @Override
    public void selectNewssByPage(Integer pageNow, Model model) {
        Page page =null;

        List<News> newsList = new ArrayList<News>();

        int newsCount = newsMapper.getNewsCount();

        if (pageNow!=null){
            page = new Page(newsCount,pageNow);
            newsList =this.newsMapper.selectNewsByPage(page.getStartPos(),page.getPageSize());
        }else{
            page = new Page(newsCount,1);
            newsList =this.newsMapper.selectNewsByPage(page.getStartPos(),page.getPageSize());
        }
        model.addAttribute("newsList",newsList);
        model.addAttribute("page",page);
    }

    @Override
    public News selectNewsById(Integer id) {
        News news = newsMapper.selectNewsById(id);
        return news;
    }

    //定时器  每天半夜2点爬取博客
    public void timeset(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 2); // 控制时
        calendar.set(Calendar.MINUTE, 0);    // 控制分
        calendar.set(Calendar.SECOND, 0);    // 控制秒

        Date time = calendar.getTime();     // 得出执行任务的时间

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Spider.create(blogNews).addUrl("https://news.cnblogs.com/n/digg?type=today&page=1").thread(5).run();
            }
        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }
}
