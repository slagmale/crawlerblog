package com.whc.util;

import com.whc.mapper.BlogMapper;
import com.whc.pojo.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * author:WangHaoChao
 * Date:2018/4/8
 * Time:14:21
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CrawlerTest {
    @Autowired
    private BlogMapper blogMapper;

    @Test
    public void test(){
        Blog blog = new Blog();
        blog.setText("111");

        try{
            int i = blogMapper.insert(blog);
            System.out.println(i);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}