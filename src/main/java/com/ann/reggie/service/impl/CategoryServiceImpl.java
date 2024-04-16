package com.ann.reggie.service.impl;

import com.ann.reggie.entity.Category;
import com.ann.reggie.mapper.CategoryMapper;
import com.ann.reggie.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
}
