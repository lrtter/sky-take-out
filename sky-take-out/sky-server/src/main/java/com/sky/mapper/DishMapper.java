package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper

public interface DishMapper {
    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    List<DishVO> list(DishPageQueryDTO dishPageQueryDTO);


    @AutoFill(value = OperationType.UPDATE)
    void setstatus(Dish dish);

    @Select("select d.*,c.name categoryName from dish d join category c on d.category_id = c.id where d.id=#{id}")
    DishVO selectbyid(Integer id);

    void delete(String[] idsArray);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    @Select("select * from dish where category_id=#{categoryId}")
    List<Dish> selectByCategoryId(Integer categoryId);


    @Select("select d.*,c.name categoryName from dish d join category c on d.category_id = c.id where d.category_id= #{categoryId} and d.status=1 ")
    List<DishVO> select1(Integer categoryId);

    @Select("select count(*) from dish where status= #{status}")
    Integer count(int status);
}
