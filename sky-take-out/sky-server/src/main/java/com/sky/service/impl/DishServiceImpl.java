package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Override
    @Transactional
    public void save(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.save(dish);
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && flavors.size()>0){
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorMapper.save(flavors);
        }
    }

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        List<DishVO> list=dishMapper.list(dishPageQueryDTO);
        Page<DishVO> pageInfo = (Page<DishVO>) list;
        return new PageResult(pageInfo.getTotal(),pageInfo.getResult());
    }

    @Override
    public void setstatus(Integer status, Integer id) {
        Dish dish=new Dish();
        dish.setId((long)id);
        dish.setStatus(status);

        dishMapper.setstatus(dish);
    }

    @Override
    @Transactional
    public DishVO selectbyid(Integer id) {
        DishVO dishVO=dishMapper.selectbyid(id);
        List<DishFlavor> flavors=dishFlavorMapper.selectbyid(id);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Override
    @Transactional
    public void delete(String ids) {
        String[] idsArray = ids.split(",");
        dishMapper.delete(idsArray);
        dishFlavorMapper.delete(idsArray);
    }

    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();

        flavors.forEach(dishFlavor -> dishFlavor.setDishId(dish.getId()));

        dishMapper.update(dish);
        String[] ids={dish.getId().toString()};
        dishFlavorMapper.delete(ids);
        if(flavors!=null && flavors.size()>0){
            dishFlavorMapper.save(flavors);
        }



    }

    @Override
    public List<Dish> list(Integer categoryId) {
        return dishMapper.selectByCategoryId(categoryId);
    }


}
