package com.whc.util;

import com.whc.mapper.BlogMapper;
import com.whc.pojo.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * author:WangHaoChao
 * Date:2018/4/8
 * Time:11:10
 * Description:爬虫的核心代码
 */
public class Crawler implements PageProcessor {

    private BlogMapper blogMapper;

    //用户名
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Crawler(BlogMapper blogMapper, String userName) {
        this.blogMapper = blogMapper;
        this.userName = userName;
    }

    //用户名
    //public static String userName="whcwkw1314";

    //文章列表 正则表达式
   // public  String URL_LIST ="http://www\\.cnblogs\\.com/"+userName+"/default\\.html\\?page\\=\\d+";
    //文章页 正则表达式
    //public  String URL_POST ="http://www\\.cnblogs\\.com/"+userName+"/p/\\w+\\.html";

    //文章数
    private static int size = 0;

    //抓取网站的配置  包括编码 抓取间隔 重试次数
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {

        //列表页
        if(page.getUrl().regex("http://www\\.cnblogs\\.com/"+userName+"/default\\.html\\?page\\=\\d+").match()){

            //添加所有文章页 到抓取列表
            page.addTargetRequests(page.getHtml().css("div.forFlow").links()
                    .regex("http://www\\.cnblogs\\.com/"+userName+"/p/\\w+\\.html")
                    .all());

            //下一页
            page.addTargetRequests(page.getHtml().links()
                    .regex("http://www\\.cnblogs\\.com/"+userName+"/default\\.html\\?page\\=\\d+")
                    .all());

        }else {
            size++;
            Blog blog = new Blog();
            String title = String.valueOf(page.getHtml().xpath("//h1[@class='postTitle']/a/text()"));
            String time = String.valueOf( page.getHtml().css("span#post-date","text"));
            String text = String.valueOf(page.getHtml().xpath("//div[@class='postBody']"));
            blog.setTitle(title);
            blog.setTime(time);
            blog.setText(text);
            blogMapper.insert(blog);
            System.out.println(size);
        }
    }
    public Site getSite() {
        return site;
    }
}
