package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/workspace")
public class WorkspaceController {
    @Autowired
    private WorkspaceService service;

    @GetMapping("/overviewOrders")
    public Result overviewOrders(){
        OrderOverViewVO result =service.overviewOrders();
        return Result.success(result);
    }

    @GetMapping("/overviewSetmeals")
    public Result overviewSetmeals(){
       SetmealOverViewVO result =service.overviewSetmeals();
       return Result.success(result);
    }

    @GetMapping("/overviewDishes")
    public Result overviewDishes(){
        DishOverViewVO result =service.overviewDishes();
        return Result.success(result);
    }

    @GetMapping("/businessData")
    public Result businessDate(){
        BusinessDataVO result =service.businessData();
        return Result.success(result);
    }
}
