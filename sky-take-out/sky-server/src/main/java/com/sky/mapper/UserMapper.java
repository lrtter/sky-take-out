package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
    @Select("select * from user where openid=#{openid} ")
    User getByOpenId(String openid);


    void insert(User user);

    @Select("select * from user where id=#{userId}")
    User getById(Long userId);

    @Select("select count(id) from user where create_time between #{begin} and #{end}")
    Integer getnewuser(LocalDateTime begin, LocalDateTime end);

    @Select("select count(id) from user where create_time<#{of}")
    Integer getTotalUser(LocalDateTime of);
}
