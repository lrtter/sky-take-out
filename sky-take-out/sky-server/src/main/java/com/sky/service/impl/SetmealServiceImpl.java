package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper mapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        List<SetmealVO> list=mapper.page(setmealPageQueryDTO);
        Page<SetmealVO> page=(Page<SetmealVO>) list;
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        mapper.save(setmeal);
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });

        setmealDishMapper.save(setmealDishes);

    }

    @Override
    public void setstatus(Integer status, Integer id) {
        LocalDateTime updateTime = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        mapper.setstatus(status,id,updateTime,currentId);
    }

    @Override
    @Transactional
    public SetmealVO selectById(Integer id) {
        SetmealVO setmealVO=mapper.selectById(id);
        List<SetmealDish> setmealDishes=setmealDishMapper.selectbyid(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    public void delete(String ids) {
        String[] idsArray = ids.split(",");
        mapper.delete(idsArray);
        setmealDishMapper.delete(idsArray);
    }

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        String[] ids={setmeal.getId().toString()};
        mapper.update(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(dish->dish.setSetmealId(setmeal.getId()));
        setmealDishMapper.delete(ids);
        setmealDishMapper.save(setmealDishes);
    }
}
