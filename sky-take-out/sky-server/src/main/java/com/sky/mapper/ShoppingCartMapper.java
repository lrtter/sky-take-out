package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
   List<ShoppingCart> list(ShoppingCart shoppingCart);
   @Update("update shopping_cart set number=#{number} where id=#{id} ")
   void updateNumberByid(ShoppingCart shoppingCart);


   @Insert("insert into shopping_cart (name,user_id,dish_id,setmeal_id,dish_flavor,number,amount,image,create_time) " +
           "values (#{name},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{image},#{createTime})")
   void insert(ShoppingCart shoppingCart);


   @Select("select * from shopping_cart where user_id=#{id}")
   List<ShoppingCart> selectByUserId(Long id);

   @Delete("delete from shopping_cart where user_id= #{id}")
   void delete(Long id);


   @Delete("delete from shopping_cart where id= #{id}")
   void deleteById(Long id);
}
