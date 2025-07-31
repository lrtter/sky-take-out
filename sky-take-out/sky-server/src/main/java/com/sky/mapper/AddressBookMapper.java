package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    @Insert("insert into address_book (user_id,consignee,phone,sex,province_code,province_name,city_code,city_name,district_code,district_name,detail,label,is_default) " +
            "values(#{userId},#{consignee},#{phone},#{sex},#{provinceCode},#{provinceName},#{cityCode},#{cityName},#{districtCode},#{districtName},#{detail},#{label},#{isDefault})")
    void save(AddressBook addressBook);

    @Select("select * from address_book where user_id=#{currentId}")
    List<AddressBook> list(Long currentId);

    @Update("update address_book set is_default=1 where id=#{id}")
    void setdefault(Integer id);

    @Update("update address_book set is_default=0 where is_default=1")
    void reset();

    @Select("select * from address_book where id=#{id}")
    AddressBook getById(Integer id);

    @Delete("delete from address_book where id= #{id}")
    void delete(Integer id);


    void update(AddressBook addressBook);

    @Select("select * from address_book where user_id= #{userId} and is_default=1")
    AddressBook get(Long userId);

}
