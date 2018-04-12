package com.whc.util;

import com.whc.mapper.NewsMapper;
import com.whc.pojo.News;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * author:WangHaoChao
 * Date:2018/4/10
 * Time:15:19
 * Description:爬最新新闻
 */
public class BlogNews implements PageProcessor{

    private NewsMapper newsMapper;

    public BlogNews(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    //抓取网站的配置  包括编码 抓取间隔 重试次数
    private Site site = Site.me().setRetryTimes(5).setSleepTime(5000);
    //新闻数
    private static int size = 0;

    @Override
    public void process(Page page) {
        News news = new News();

       /* //访问最新新闻下面的链接
        page.addTargetRequests(page.getHtml().css("div.w_r").links().regex("https://news\\.cnblogs\\.com/n/\\w+").all());

        news.setTitle(String.valueOf(page.getHtml().xpath("//div[@id='news_title']/a/text()")));

        System.out.println(news.getTitle());*/
        //列表页
        if(page.getUrl().regex("https://news\\.cnblogs\\.com/n/digg\\?type\\=today\\&page\\=\\d").match()) {

            //
            page.addTargetRequests(page.getHtml().css("div.content")
                    .links().regex("https://news\\.cnblogs\\.com/n/\\d+/").all());

            //下一页
            page.addTargetRequests(page.getHtml().links()
                    .regex("https://news\\.cnblogs\\.com/n/digg\\?type\\=today\\&page\\=\\d")
                    .all());
        }else {
            size++;
            News n = new News();
            n.setTitle(String.valueOf(page.getHtml().xpath("//div[@id='news_title']/a/text()")));
            n.setText(String.valueOf(page.getHtml().xpath("//div[@id='news_body']")));
            newsMapper.insertNews(n);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    /*public static void main(String[] args) {
        Spider.create(new BlogNews()).addUrl("https://news.cnblogs.com/n/digg?type=today&page=1").thread(5).run();
    }*/
}
