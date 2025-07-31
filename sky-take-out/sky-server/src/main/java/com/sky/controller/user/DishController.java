package com.sky.controller.user;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
public class DishController {
    @Autowired
    private DishService service;
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/list")
    public Result list(Integer categoryId){
        String key="dish_"+categoryId;

        List<DishVO> list =(List<DishVO>) redisTemplate.opsForValue().get(key);

        if(list!=null && list.size()>0){
            return Result.success(list);
        }
        List<DishVO> result =service.list1(categoryId);
        redisTemplate.opsForValue().set(key,result);
       return Result.success(result);
    }
}
