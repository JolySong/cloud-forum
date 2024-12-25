package com.iomc.forum.topic.controller;


import com.iomc.common.core.Res;
import com.iomc.forum.topic.api.dto.CategoryDTO;
import com.iomc.forum.topic.api.vo.CategoryVO;
import com.iomc.forum.topic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/topic/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     *
     * @param categoryDTO
     * @return
     */
    @PostMapping
    public Res addCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.saveCategory(categoryDTO);
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public Res deleteCategory(@RequestParam Long id) {
        return categoryService.deleteCategory(id);
    }

    /**
     * 修改分类
     *
     * @param categoryDTO
     */
    @PutMapping
    public Res updateCategory(@RequestBody CategoryDTO categoryDTO){
        return categoryService.updateCategory(categoryDTO);
    }

    /**
     * 排序分类
     *
     * @param vos
     * @return
     */
    @PutMapping("/sort")
    public Res sortCategory(@RequestBody List<CategoryVO> vos){
        return categoryService.sortCategory(vos);
    }

    /**
     * 获取所有分类
     *
     * @return
     */
    @GetMapping
    public Res list(){
        return Res.success(categoryService.getAllCategories());
    }

    /**
     * 获取分类详情
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public Res getCategoryNameById(@PathVariable(value = "categoryId") Long categoryId){
        return Res.success(categoryService.getCategoryById(categoryId));
    }

    /**
     * 获取热门分类
     *
     * @param limit
     */
    @GetMapping("/hot")
    public Res<List<CategoryVO>> getHotCategories(@RequestParam("limit") Integer limit) {
        return Res.success(categoryService.getHotCategories(limit));
    }

}
