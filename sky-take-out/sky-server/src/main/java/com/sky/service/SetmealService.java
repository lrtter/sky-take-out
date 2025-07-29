package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

public interface SetmealService {
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    void save(SetmealDTO setmealDTO);

    void setstatus(Integer status, Integer id);

    SetmealVO selectById(Integer id);

    void delete(String ids);

    void update(SetmealDTO setmealDTO);
}
