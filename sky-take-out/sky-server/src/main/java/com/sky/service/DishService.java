package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void save(DishDTO dishDTO);

    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    void setstatus(Integer status, Integer id);

    DishVO selectbyid(Integer id);

    void delete(String ids);

    void update(DishDTO dishDTO);

    List<Dish> list(Integer categoryId);

    List<DishVO> list1(Integer categoryId);
}
