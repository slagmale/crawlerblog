import com.whc.pojo.Blog;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;

/**
 * author:WangHaoChao
 * Date:2018/4/3
 * Time:23:58
 * Description:
 */
public class Test implements PageProcessor {
    //用户名
    private static String userName="whcwkw1314";

    //文章列表 正则表达式
    public static final String URL_LIST ="http://www\\.cnblogs\\.com/"+userName+"/default\\.html\\?page\\=\\d";
    //文章页 正则表达式
    public static final String URL_POST ="http://www\\.cnblogs\\.com/"+userName+"/p/\\w+\\.html";

    //文章数
    private static int size = 0;

    //抓取网站的配置  包括编码 抓取间隔 重试次数
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {
        //列表页
       if(page.getUrl().regex(URL_LIST).match()){
           //添加所有文章页 到抓取列表
           page.addTargetRequests(page.getHtml().css("div.forFlow").links()
                   .regex(URL_POST)
                   .all());
           //下一页
           page.addTargetRequests(page.getHtml().links()
                   .regex(URL_LIST)
                   .all());

       }else {
           size++;
           Blog blog = new Blog();
           blog.setTitle(String.valueOf(page.getHtml().xpath("//h1[@class='postTitle']/a/text()")));
           blog.setTime(String.valueOf( page.getHtml().css("span#post-date","text")));
           blog.setText(String.valueOf(page.getHtml().xpath("//div[@class='postBody']")));

            //page.putField("body", page.getHtml().xpath("//div[@class='postBody']"));
           //标题：
           //page.putField("title",page.getHtml().xpath("//h1[@class='postTitle']/a/text()"));
           //摘要：
           //时间：
           //page.putField("time",page.getHtml().css("span#post-date","text"));
           //正文
           //page.putField("text", page.getHtml().xpath("//div[@class='postBody']"));
       }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new Test()).addUrl("http://www.cnblogs.com/"
                + userName+"/default.html?page=1").thread(5).run();
        System.out.println(size);

    }
}
