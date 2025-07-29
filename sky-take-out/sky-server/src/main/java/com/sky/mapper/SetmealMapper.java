package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SetmealMapper {


    List<SetmealVO> page(SetmealPageQueryDTO setmealPageQueryDTO);

    @AutoFill(value = OperationType.INSERT)
    void save(Setmeal setmeal);

    @Update("update setmeal set status=#{status},update_time=#{updateTime},update_user=#{currentId} where id=#{id} ")
    void setstatus(Integer status, Integer id, LocalDateTime updateTime, Long currentId);

    @Select("select s.*,c.name categoryName from  setmeal s join category c on s.category_id = c.id where s.id=#{id}")
    SetmealVO selectById(Integer id);

    void delete(String[] idsArray);

    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);
}
