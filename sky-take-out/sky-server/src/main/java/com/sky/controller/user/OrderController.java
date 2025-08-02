package com.sky.controller.user;

import com.alibaba.fastjson.JSON;
import com.sky.annotation.AutoFill;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.websocket.WebSocketServer;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService service;
    @Autowired
    private WebSocketServer webSocketServer;
    @PostMapping("/submit")
    public Result submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        OrderSubmitVO result =service.submit(ordersSubmitDTO);
        return Result.success(result);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderController orderService;
        OrderPaymentVO orderPaymentVO = service.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);

        //来单提醒
        Map map=new HashMap();
        map.put("type", 1);
        map.put("orderId", ordersPaymentDTO.getOrderNumber());
        map.put("content", "订单号："+ordersPaymentDTO.getOrderNumber());

        String jsonString = JSON.toJSONString(map);
        webSocketServer.sendToAllClient(jsonString);

        return Result.success(orderPaymentVO);
    }

    @GetMapping("/orderDetail/{id}")
    public Result getOrderDetail(@PathVariable Integer id){
        OrderVO result =service.getOrderDetail(id);
        return Result.success(result);
    }

    @GetMapping("/historyOrders")
    public Result historyOrders(OrdersPageQueryDTO ordersPageQueryDTO){
        PageResult result =service.historyOrders(ordersPageQueryDTO);
        return Result.success(result);
    }

    @PutMapping("/cancel/{id}")
    public Result cancle(@PathVariable Integer id){
        service.cancle(id);
        return Result.success();
    }

    @PostMapping("/repetition/{id}")
    public Result repetition(@PathVariable Integer id){
        service.repetition(id);
        return Result.success();
    }

    @GetMapping("/reminder/{id}")
    public Result reminder(@PathVariable Integer id){
        service.reminder(id);
        return Result.success();
    }
}
