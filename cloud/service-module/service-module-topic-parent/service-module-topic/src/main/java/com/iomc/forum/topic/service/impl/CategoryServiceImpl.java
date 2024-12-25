package com.iomc.forum.topic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.common.core.Res;
import com.iomc.common.core.utils.ListUtils;
import com.iomc.forum.topic.api.dto.CategoryDTO;
import com.iomc.forum.topic.api.entity.Category;
import com.iomc.forum.topic.api.mapstruct.CategoryMapper;
import com.iomc.forum.topic.api.vo.CategoryVO;
import com.iomc.forum.topic.dao.CategoryDao;
import com.iomc.forum.topic.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {


    /**
     * 新增分类
     *
     * @param categoryDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Res saveCategory(CategoryDTO categoryDTO) {

        Category category = baseMapper.selectOne(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getName, categoryDTO.getName()));
        if (category != null) {
            return Res.fail("分类名称已存在");
        }

        if (categoryDTO.getSort() == null) {
            categoryDTO.setSort(baseMapper.selectCount(null));
        }

        category = CategoryMapper.INSTANCE.toEntity(categoryDTO);
        return Res.success(baseMapper.insert(category));
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @Override
    public Res deleteCategory(Long id) {
        return Res.success(removeById(id));
    }

    /**
     * 更新分类
     *
     * @param categoryDTO
     * @return
     */
    @Override
    public Res updateCategory(CategoryDTO categoryDTO) {

        Category category = baseMapper.selectOne(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getName, categoryDTO.getName())
        );
        if (category != null && !category.getId().equals(categoryDTO.getId())) {
            return Res.fail("分类名称已存在");
        }

        category = CategoryMapper.INSTANCE.toEntity(categoryDTO);
        return Res.success(updateById(category));
    }

    /**
     * 分类排序
     *
     * @param list
     */
    @Override
    public Res sortCategory(List<CategoryVO> list) {
        return Res.success(baseMapper.sortCategory(list));
    }

    /**
     * 查询所有分类
     **/
    @Override
    public List<CategoryVO> getAllCategories() {

        List<Category> categories = baseMapper.selectList(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getStatus, 1)
                        .orderByAsc(Category::getSort));

        if (ListUtils.isEmpty(categories)) {
            return null;
        }

        List<CategoryVO> vos = new ArrayList<>();
        for (Category category : categories) {
            CategoryVO vo = CategoryMapper.INSTANCE.toVO(category);
            vos.add(vo);
        }

        return vos;
    }

    /**
     * 查询热门分类
     *
     * @param limit 限制数量
     * @return
     */
    @Override
    public List<CategoryVO> getHotCategories(Integer limit) {
        return baseMapper.selectHotCategories(limit);
    }

    /**
     * 根据id获取分类
     *
     * @param id
     * @return
     */
    @Override
    public CategoryVO getCategoryById(Long id) {
        Category category = getById(id);
        if (category == null) {
            return null;
        }
        return CategoryMapper.INSTANCE.toVO(category);
    }
} 