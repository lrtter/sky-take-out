package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;
    @Scheduled(cron="0 * * * * ?")
    //@Scheduled(cron= "1/5 * * * * ?")
    public void processTimeoutOrder(){
        log.info("开始处理订单支付超时:{}", LocalDateTime.now());
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(-15);

        List<Orders> orders=orderMapper.selectByTimeandStatus(Orders.PENDING_PAYMENT,localDateTime);

        if(orders!= null&& orders.size()>0){
            for (Orders order : orders) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单支付超时");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")//凌晨一点钟执行
    //@Scheduled(cron= "0/5 * * * * ?")
    public void processDeliveryOrder(){
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);

        List<Orders> orders = orderMapper.selectByTimeandStatus(Orders.DELIVERY_IN_PROGRESS, time);

        if(orders!= null&& orders.size()>0){
            for (Orders order : orders) {
                order.setStatus(Orders.COMPLETED);
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }
}
