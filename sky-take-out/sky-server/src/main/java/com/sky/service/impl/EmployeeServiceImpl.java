package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对

        password = DigestUtils.md5DigestAsHex(password.getBytes());//密码进行md5加密
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }



    @Override
    public void save(Employee employee) {
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        employee.setStatus(StatusConstant.ENABLE);

        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));



        Long currentId = BaseContext.getCurrentId();

//        employee.setCreateUser(currentId);
//        employee.setUpdateUser(currentId);

        employeeMapper.save(employee);
    }

    @Override
    public PageResult page(String name, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Employee> list =employeeMapper.page(name);
        Page<Employee> pageInfo=(Page<Employee>) list;
        return new PageResult(pageInfo.getTotal(),pageInfo.getResult());
    }

    @Override
    public void setstatus(Integer status, Integer id) {
        employeeMapper.setstatus(status,id);
    }

    @Override
    public Employee selectbyid(Integer id) {
        return employeeMapper.selectbyid(id);
    }

    @Override
    public void update(Employee employee) {
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

    @Override
    public Employee editpassword(PasswordEditDTO passwordEditDTO) {
        passwordEditDTO.setEmpId(BaseContext.getCurrentId());
        passwordEditDTO.setOldPassword(DigestUtils.md5DigestAsHex(passwordEditDTO.getOldPassword().getBytes()));
        passwordEditDTO.setNewPassword(DigestUtils.md5DigestAsHex(passwordEditDTO.getNewPassword().getBytes()));
        Employee employee = employeeMapper.selectbyidandpassword(passwordEditDTO);
        if(employee==null)
            return null;
        else{
            employeeMapper.editpassword(passwordEditDTO);
            return employee;
        }

    }


}
