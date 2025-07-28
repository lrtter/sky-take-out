package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/page")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult result =service.page(categoryPageQueryDTO);
        return Result.success(result);
    }

    @PostMapping("/status/{status}")
    public Result setstatus(@PathVariable Integer status,Integer id){
        service.setstatus(status,id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryD){
        service.save(categoryD);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        service.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result upadte(@RequestBody CategoryDTO categoryDTO){
        service.update(categoryDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(Integer type){
        List<Category> list =service.list(type);
        return Result.success(list);
    }
}

