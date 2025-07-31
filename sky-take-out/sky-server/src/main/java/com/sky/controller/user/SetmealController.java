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
    public Result selectById(Integer categoryId){
        String key="setmeal_"+categoryId;
        List<Setmeal> list =(List<Setmeal>) redisTemplate.opsForValue().get(key);

        if(list!=null && list.size()>0){
            return Result.success(list);
        }

        List<Setmeal> result = service.selectById1(categoryId);
        redisTemplate.opsForValue().set(key,result);
       return Result.success(result);
    }

    @GetMapping("/dish/{id}")
    public Result selectBySetmealid(@PathVariable Integer id){
        List<DishItemVO> result=service.selectBySetmealid(id);
        return Result.success(result);
    }
}
