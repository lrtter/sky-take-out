package com.sky.controller.user;

import com.sky.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
public class ShopController {

    @Autowired
    RedisTemplate redisTemplate;


    @GetMapping("/status")
    public Result getstatus(){
        return Result.success(redisTemplate.opsForValue().get("SHOP_STATUS"));
    }
}
