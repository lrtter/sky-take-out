package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    @Insert("insert into employee(name, username, phone, sex,password,id_number, create_time, update_time, create_user, update_user) " +
            "values (#{name},#{username},#{phone},#{sex},#{password},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void save(Employee employee);


    List<Employee> page(String name);

    @Update("update employee set status=#{status} where id=#{id} ")
    @AutoFill(value = OperationType.UPDATE)
    void setstatus(Integer status, Integer id);

    @Select("select * from employee where id=#{id}")
    Employee selectbyid(Integer id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);
    @Update("update employee set password=#{newPassword} where id=#{empId} and password=#{oldPassword}")
    @AutoFill(value = OperationType.UPDATE)
    void editpassword(PasswordEditDTO passwordEditDTO);

    @Select("select * from employee where id= #{empId} and password= #{oldPassword}")
    Employee selectbyidandpassword(PasswordEditDTO passwordEditDTO);
}
