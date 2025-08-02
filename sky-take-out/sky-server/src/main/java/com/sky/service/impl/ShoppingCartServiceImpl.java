package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper mapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
       ShoppingCart shoppingCart = new ShoppingCart();
       BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
       shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = mapper.list(shoppingCart);

        if(list!=null && list.size()>0){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber()+1);
            mapper.updateNumberByid(cart);
        }else{
            Long dishId = shoppingCart.getDishId();

            if(dishId!=null){
                DishVO dishVO = dishMapper.selectbyid(Integer.parseInt(dishId.toString()));
                shoppingCart.setName(dishVO.getName());
                shoppingCart.setImage(dishVO.getImage());
                shoppingCart.setAmount(dishVO.getPrice());

            }else{
                SetmealVO setmealVO = setmealMapper.selectById(Integer.parseInt(shoppingCart.getSetmealId().toString()));
                shoppingCart.setName(setmealVO.getName());
                shoppingCart.setImage(setmealVO.getImage());
                shoppingCart.setAmount(setmealVO.getPrice());

            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            mapper.insert(shoppingCart);
        }


    }

    @Override
    public List<ShoppingCart> list() {
        Long id = BaseContext.getCurrentId();
        return mapper.selectByUserId(id);
    }

    @Override
    public void clean() {
        Long id = BaseContext.getCurrentId();
        mapper.delete(id);
    }

    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = mapper.list(shoppingCart);
        if(list==null || list.size()==0){
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        ShoppingCart cart = list.get(0);
        if(cart.getNumber()==1){
            mapper.deleteById(cart.getId());
        }else{
            cart.setNumber(cart.getNumber()-1);
            mapper.updateNumberByid(cart);
        }
    }


}
