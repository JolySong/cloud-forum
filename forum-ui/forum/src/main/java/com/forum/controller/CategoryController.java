package com.forum.controller;


import com.forum.common.R;
import com.forum.entity.Category;
import com.forum.service.CategoryService;
import com.forum.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public R<List<Category>> list(){
        return R.ok(categoryService.getAllCategories());
    }
}
