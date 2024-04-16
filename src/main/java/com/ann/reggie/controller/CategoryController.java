package com.ann.reggie.controller;

import com.ann.reggie.common.R;
import com.ann.reggie.entity.Category;
import com.ann.reggie.service.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @PostMapping
    public R<String> add(@RequestBody Category category) {
        category.setCreateUser(1L);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(@RequestParam(value = "page") int page,
                        @RequestParam(value = "pageSize") int pageSize) {
        //        构建分页对象
        Page pageModel = new Page(page,pageSize);
        Page pageInfo = categoryService.page(pageModel);
        return R.success(pageInfo);
    }

}
