package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    void setstatus(Integer status, Integer id);

    void save(CategoryDTO categoryD);

    void delete(Integer id);

    void update(CategoryDTO categoryDTO);

    List<Category> list(Integer type);
}
