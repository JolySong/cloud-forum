package com.iomc.forum.topic.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import com.iomc.forum.topic.api.entity.Category;
import com.iomc.forum.topic.api.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryDao extends SpiceBaseMapper<Category> {

    /**
     * 分页查询分类列表
     *
     * @param page   分页参数
     * @param status 状态（可选）
     * @return 分类列表
     */
    IPage<Category> selectCategoryPage(Page<Category> page, @Param("status") Integer status);

    /**
     * 排序分类
     */
    int sortCategory(@Param("list") List<CategoryVO> list);


    /**
     * 获取热门分类
     *
     * @param limit 限制数量
     * @return 分类列表
     */
    List<CategoryVO> selectHotCategories(@Param("limit") Integer limit);
} 