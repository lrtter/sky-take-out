package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    void save(List<SetmealDish> setmealDishes);

    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> selectbyid(Integer id);

    void delete(String[] idsArray);
}
