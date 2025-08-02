package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.entity.SaleReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderDetailMapper {
    void insertBatch(List<OrderDetail> list1);

    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> selectByOrderId(Long id);


    List<SaleReport> top10(LocalDateTime begin, LocalDateTime end);
}
