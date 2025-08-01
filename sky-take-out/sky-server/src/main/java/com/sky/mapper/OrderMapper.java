package com.sky.mapper;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.Orders;
import com.sky.result.Result;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);


    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);


    @Update("update orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{checkOutTime} " +
            "where number = #{orderNumber}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime checkOutTime, String orderNumber);

    @Select("select * from orders where id = #{id}")
    Orders getOrderDetails(Integer id);

    List<Orders> historyOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    @Update("update orders set status =6,cancel_time=#{time} where id = #{id}")
    void cancle(Integer id,LocalDateTime time);


    @Update("update orders set status =3 where id = #{id}")
    void confirm(Integer id);

    @Update("update orders set status =6,pay_status=2,rejection_reason=#{rejectionReason} where id = #{id}")
    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    @Update("update orders set status =6,pay_status=2,cancel_time= #{now},cancel_reason=#{ordersCancelDTO.cancelReason} where id = #{ordersCancelDTO.id}")
    void cancel(OrdersCancelDTO ordersCancelDTO, LocalDateTime now);

    @Update("update orders set status =4,delivery_time= #{time} where id = #{id}")
    void delivery(Integer id, LocalDateTime time);

    @Update("update orders set status =5 where id = #{id}")
    void complete(Integer id);

    @Select("select count(id) from orders where status = #{status}")
    Integer count(Integer status);
}
