package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public OrderOverViewVO overviewOrders() {
        OrderOverViewVO result = new OrderOverViewVO();
        result.setCompletedOrders(orderMapper.count(Orders.COMPLETED));
        result.setCancelledOrders(orderMapper.count(Orders.CANCELLED));
        result.setDeliveredOrders(orderMapper.count(Orders.CONFIRMED));
        result.setWaitingOrders(orderMapper.count(Orders.TO_BE_CONFIRMED));
        result.setAllOrders(orderMapper.count(null));
        return result;
    }

    @Override
    public SetmealOverViewVO overviewSetmeals() {
        SetmealOverViewVO result = new SetmealOverViewVO();
        result.setDiscontinued(setmealMapper.count(0));
        result.setSold(setmealMapper.count(1));
        return result;
    }

    @Override
    public DishOverViewVO overviewDishes() {
        DishOverViewVO result = new DishOverViewVO();
        result.setDiscontinued(dishMapper.count(0));
        result.setSold(dishMapper.count(1));

        return result;
    }

    @Override
    public BusinessDataVO businessData() {
        BusinessDataVO result = new BusinessDataVO();
        LocalDate time = LocalDate.now();
        result.setNewUsers(userMapper.getnewuser(LocalDateTime.of(time,LocalTime.MIN), LocalDateTime.of(time,LocalTime.MAX)));
        result.setTurnover(orderMapper.turnover(LocalDateTime.of(time,LocalTime.MIN), LocalDateTime.of(time,LocalTime.MAX)));
        if(result.getTurnover()==null)
            result.setTurnover(0.0);
        result.setValidOrderCount(orderMapper.calculate(LocalDateTime.of(time,LocalTime.MIN), LocalDateTime.of(time,LocalTime.MAX), Orders.COMPLETED));
        if(result.getValidOrderCount()== 0){
            result.setUnitPrice(0.0);
        }else{
            result.setUnitPrice(result.getTurnover()/result.getValidOrderCount().doubleValue());
        }

        int totalOrder=orderMapper.calculate(LocalDateTime.of(time,LocalTime.MIN), LocalDateTime.of(time,LocalTime.MAX),null);
        if (totalOrder==0){
            result.setOrderCompletionRate(0.0);
        }else{
            result.setOrderCompletionRate(result.getValidOrderCount().doubleValue()/totalOrder);
        }


        return result;
    }


    public BusinessDataVO businessData(LocalDate begin, LocalDate end) {
        BusinessDataVO result = new BusinessDataVO();
        LocalDate time = LocalDate.now();
        result.setNewUsers(userMapper.getnewuser(LocalDateTime.of(begin,LocalTime.MIN), LocalDateTime.of(end,LocalTime.MAX)));
        result.setTurnover(orderMapper.turnover(LocalDateTime.of(begin,LocalTime.MIN), LocalDateTime.of(end,LocalTime.MAX)));
        if(result.getTurnover()==null)
            result.setTurnover(0.0);
        result.setValidOrderCount(orderMapper.calculate(LocalDateTime.of(begin,LocalTime.MIN), LocalDateTime.of(end,LocalTime.MAX), Orders.COMPLETED));
        if(result.getValidOrderCount()== 0){
            result.setUnitPrice(0.0);
        }else{
            result.setUnitPrice(result.getTurnover()/result.getValidOrderCount().doubleValue());
        }

        int totalOrder=orderMapper.calculate(LocalDateTime.of(begin,LocalTime.MIN), LocalDateTime.of(end,LocalTime.MAX),null);
        if (totalOrder==0){
            result.setOrderCompletionRate(0.0);
        }else{
            result.setOrderCompletionRate(result.getValidOrderCount().doubleValue()/totalOrder);
        }


        return result;
    }
}
