package com.whc.service;

import com.whc.pojo.Blog;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author:WangHaoChao
 * Date:2018/4/8
 * Time:11:09
 * Description:爬虫业务层
 */
public interface CrawlerService {
    //开始爬数据
    public void startCrawler(String userName);

    //得到爬取的博客

    public void selectBlogsByPage(Integer pageNow, Model model);

    //通过id得到对应的博文

    public Blog selectBlogById(Integer id);
}
