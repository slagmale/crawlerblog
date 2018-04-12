package com.whc.mapper;

import com.whc.pojo.Blog;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface BlogMapper {
    /**
     * 使用注解传入多个参数
     * @param startPos
     * @param pageSize
     * @return
     */
    List<Blog> selectBlogsByPage(@Param(value="startPos") Integer startPos, @Param(value="pageSize") Integer pageSize);

    //取得博客数量
    int getBlogsCount();

    //删除全部博文
    int deleteAllBlogs();

    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);
}