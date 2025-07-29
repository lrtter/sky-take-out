package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface DishMapper {
    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);
}
