package com.iomc.forum.topic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iomc.common.core.Res;
import com.iomc.forum.topic.api.dto.CategoryDTO;
import com.iomc.forum.topic.api.entity.Category;
import com.iomc.forum.topic.api.vo.CategoryVO;

import java.util.List;

public interface CategoryService extends IService<Category> {

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    Res saveCategory(CategoryDTO categoryDTO);

    /**
     * 删除分类
     * @param id
     * @return
     */
    Res deleteCategory(Long id);

    /**
     * 更新分类
     * @param categoryDTO
     * @return
     */
    Res updateCategory(CategoryDTO categoryDTO);

    /**
     * 分类排序
     * @param list
     */
    Res sortCategory(List<CategoryVO> list);

    /** 查询所有分类 **/
    List<CategoryVO> getAllCategories();

    /**
     * 获取热门分类
     *
     * @param limit 限制数量
     * @return 分类列表
     */
    List<CategoryVO> getHotCategories(Integer limit);

    /**
     * 根据id获取分类
     *
     * @param id
     * @return
     */
    CategoryVO getCategoryById(Long id);
} 