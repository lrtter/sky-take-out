package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    OrderVO getOrderDetail(Integer id);

    PageResult historyOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    void cancle(Integer id);

    void repetition(Integer id);

    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    void confirm(Integer id);

    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    void cancel(OrdersCancelDTO ordersCancelDTO);

    void delivery(Integer id);

    void complete(Integer id);

    OrderStatisticsVO statistics();

    void reminder(Integer id);

}
