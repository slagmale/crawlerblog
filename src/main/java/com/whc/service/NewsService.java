package com.whc.service;


import com.whc.pojo.News;
import org.springframework.ui.Model;

/**
 * author:WangHaoChao
 * Date:2018/4/10
 * Time:18:04
 * Description:
 */
public interface NewsService {
    //开始爬新闻
    public void startCrawlerNews();

    //得到爬取的新闻

    public void selectNewssByPage(Integer pageNow, Model model);

    //通过id得到对应的新闻

    public News selectNewsById(Integer id);
}
