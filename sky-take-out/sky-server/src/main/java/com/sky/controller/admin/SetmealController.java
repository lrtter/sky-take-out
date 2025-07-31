package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result save(@RequestBody SetmealDTO setmealDTO){
        service.save(setmealDTO);
        delredis("setmeal_" + setmealDTO.getCategoryId());
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result setstatus(@PathVariable Integer status,Integer id){
        service.setstatus(status,id);
        delredis("setmeal_*");
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        SetmealVO result = service.selectById(id);
       return Result.success(result);
    }

    @DeleteMapping
    public Result delete(String ids){
        service.delete(ids);
        delredis("setmeal_*");
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO){
        service.update(setmealDTO);
        delredis("setmeal_*");
        return Result.success();
    }

    private void delredis(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
