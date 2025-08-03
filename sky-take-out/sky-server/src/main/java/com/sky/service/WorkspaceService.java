package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDate;

public interface WorkspaceService {
    OrderOverViewVO overviewOrders();

    SetmealOverViewVO overviewSetmeals();


    DishOverViewVO overviewDishes();

    BusinessDataVO businessData();

    BusinessDataVO businessData(LocalDate begin, LocalDate end);
}
