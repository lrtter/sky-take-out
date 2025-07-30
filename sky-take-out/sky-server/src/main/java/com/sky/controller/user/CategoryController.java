package com.sky.controller.user;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/page")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult result =service.page(categoryPageQueryDTO);
        return Result.success(result);
    }



    @GetMapping("/list")
    public Result list(Integer type){
        List<Category> list =service.list(type);
        return Result.success(list);
    }
}

