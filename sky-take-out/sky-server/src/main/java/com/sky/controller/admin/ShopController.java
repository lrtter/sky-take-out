package com.sky.controller.admin;

import com.sky.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {

    @Autowired
    RedisTemplate redisTemplate;
    @PutMapping("/{status}")
    public Result setstatus(@PathVariable Integer status){
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result getstatus(){
        return Result.success(redisTemplate.opsForValue().get("SHOP_STATUS"));
    }
}
