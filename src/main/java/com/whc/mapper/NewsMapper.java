package com.whc.mapper;


import com.whc.pojo.News;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * author:WangHaoChao
 * Date:2018/4/10
 * Time:17:48
 * Description:新闻接口
 */
public interface NewsMapper {
    /**
     * 使用注解传入多个参数
     * @param startPos
     * @param pageSize
     * @return
     */
    List<News> selectNewsByPage(@Param(value="startPos") Integer startPos, @Param(value="pageSize") Integer pageSize);

    //取得博客数量
    int getNewsCount();

    //删除全部博文
    int deleteAllNews();

    int insertNews(News news);

    News selectNewsById(Integer id);

}
