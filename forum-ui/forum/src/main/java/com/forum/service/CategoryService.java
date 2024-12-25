package com.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.entity.Category;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

public interface CategoryService extends IService<Category> {
    /**
     * 分页查询分类
     *
     * @param page 分页参数
     * @param status 状态（可选）
     * @return 分类列表
     */
    IPage<Category> getCategoryPage(Page<Category> page, Integer status);

    /** 查询所有分类 **/
    List<Category> getAllCategories();

    /**
     * 获取热门分类
     *
     * @param limit 限制数量
     * @return 分类列表
     */
    List<Category> getHotCategories(Integer limit);

    /**
     * 更新分类的话题数量
     *
     * @param categoryId 分类ID
     */
    void updateTopicCount(Long categoryId);
} 