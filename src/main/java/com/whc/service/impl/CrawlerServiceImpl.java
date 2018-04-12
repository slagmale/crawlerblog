package com.whc.service.impl;

import com.whc.mapper.BlogMapper;
import com.whc.pojo.Blog;
import com.whc.service.CrawlerService;
import com.whc.util.Crawler;
import com.whc.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import us.codecraft.webmagic.Spider;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * author:WangHaoChao
 * Date:2018/4/8
 * Time:11:14
 * Description:爬虫业务实现类
 */
@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Autowired
    private BlogMapper blogMapper;

    private Crawler crawler;

    private List<Blog> list;

    @Override
    public void startCrawler(String userName) {

        crawler = new Crawler(blogMapper,userName);

        //爬之前把数据库原有的数据全部清空 保证数据是最新的
        if (blogMapper.getBlogsCount()!= 0){
            blogMapper.deleteAllBlogs();
        }

        //定时爬取
        timeset();

    }

    //对博客进行分页查询
    @Override
    public void selectBlogsByPage(Integer pageNow, Model model) {

        Page page = null;

        List<Blog> blogs = new ArrayList<Blog>();

        //查询用户总数
        int blogsCount = blogMapper.getBlogsCount();

        if (pageNow != null) {
            page = new Page(blogsCount, pageNow);
            blogs = this.blogMapper.selectBlogsByPage(page.getStartPos(), page.getPageSize());
        } else {
            page = new Page(blogsCount, 1);
            blogs = this.blogMapper.selectBlogsByPage(page.getStartPos(), page.getPageSize());
        }

        model.addAttribute("blogs", blogs);
        model.addAttribute("page", page);
    }


    //通过id查看博文
    @Override
    public Blog selectBlogById(Integer id) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        return blog;
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
                Spider.create(crawler).addUrl("http://www.cnblogs.com/"
                        +crawler.getUserName()+"/default.html?page=1").thread(5).run();
            }
        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }
}


