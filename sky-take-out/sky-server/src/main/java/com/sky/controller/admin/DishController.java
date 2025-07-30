package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminDishController")
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService service;
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO){
        service.save(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result page(DishPageQueryDTO dishPageQueryDTO){
        PageResult result =service.page(dishPageQueryDTO);
        return Result.success(result);
    }

    @PostMapping("/status/{status}")
    public Result setstatus(@PathVariable Integer status, Integer id){
        service.setstatus(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        DishVO dish =service.selectbyid(id);
        return Result.success(dish);
    }

    @DeleteMapping
    public Result delete(String ids){
        service.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO){
        service.update(dishDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(Integer categoryId){
       List<Dish> result =service.list(categoryId);
       return Result.success(result);
    }
}
