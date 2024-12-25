package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 分页查询分类列表
     *
     * @param page   分页参数
     * @param status 状态（可选）
     * @return 分类列表
     */
    IPage<Category> selectCategoryPage(Page<Category> page, @Param("status") Integer status);

    /**
     * 更新分类的话题数量
     *
     * @param categoryId 分类ID
     * @return 影响行数
     */
    int updateTopicCount(@Param("categoryId") Long categoryId);

    /**
     * 获取热门分类
     *
     * @param limit 限制数量
     * @return 分类列表
     */
    List<Category> selectHotCategories(@Param("limit") Integer limit);
} 