package com.sky.controller.user;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService service;
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/list")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result selectById(Integer categoryId){
        List<Setmeal> result = service.selectById1(categoryId);
       return Result.success(result);
    }

    @GetMapping("/dish/{id}")
    public Result selectBySetmealid(@PathVariable Integer id){
        List<DishItemVO> result=service.selectBySetmealid(id);
        return Result.success(result);
    }
}
