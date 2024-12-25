package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.entity.Category;
import com.forum.mapper.CategoryMapper;
import com.forum.service.CategoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public IPage<Category> getCategoryPage(Page<Category> page, Integer status) {
        return baseMapper.selectCategoryPage(page, status);
    }

    /**
     * 查询所有分类
     **/
    @Override
    public List<Category> getAllCategories() {

        return baseMapper.selectList(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getStatus, 1)
                        .orderByAsc(Category::getSort)
        );
    }

    @Override
    public List<Category> getHotCategories(Integer limit) {
        return baseMapper.selectHotCategories(limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTopicCount(Long categoryId) {
        baseMapper.updateTopicCount(categoryId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Category category) {
        return super.save(category);
    }
} 