package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper
public interface ReportMapper {

    @Select("select sum(amount) from orders where status=5 and order_time between #{begin} and #{end}")
    Double getTurnover(LocalDateTime begin, LocalDateTime end);

}
