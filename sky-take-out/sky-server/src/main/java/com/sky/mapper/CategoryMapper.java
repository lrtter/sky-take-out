package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> page(Category category);



    @Update("update category set status=#{status} where id=#{id} ")
    void setstatus(Integer status, Integer id);

    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void save(Category category);

    @Delete("delete from category where id=#{id}")
    void delete(Integer id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);


    List<Category> list(Integer type);
}
