package com.sky.service;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employee
     */
    void save(Employee employee);

    PageResult page(String name, Integer page, Integer pageSize);

    void setstatus(Integer status, Integer id);

    Employee selectbyid(Integer id);

    void update(Employee employee);
}
