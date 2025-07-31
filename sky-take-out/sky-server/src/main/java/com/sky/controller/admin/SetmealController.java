package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService service;
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/page")
    public Result page(SetmealPageQueryDTO setmealPageQueryDTO){
       PageResult result =service.page(setmealPageQueryDTO);
       return Result.success(result);
    }

    @PostMapping
    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        service.save(setmealDTO);

        return Result.success();
    }

    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result setstatus(@PathVariable Integer status,Integer id){
        service.setstatus(status,id);

        return Result.success();
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        SetmealVO result = service.selectById(id);
       return Result.success(result);
    }

    @DeleteMapping
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result delete(String ids){
        service.delete(ids);
        return Result.success();
    }

    @PutMapping
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        service.update(setmealDTO);

        return Result.success();
    }


}
