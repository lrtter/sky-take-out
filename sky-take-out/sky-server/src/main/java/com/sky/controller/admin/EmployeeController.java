package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Employee employee) {
        log.info("新增员工，员工数据：{}", employee);
        employeeService.save(employee);
        return Result.success();
    }

    @GetMapping("/page")
    public Result page(String name, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult pageResult = employeeService.page(name, page, pageSize);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    public Result setstatus(@PathVariable("status") Integer status, @RequestParam("id") Integer id) {

        employeeService.setstatus(status, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        Employee employee =employeeService.selectbyid(id);
        return Result.success(employee);
    }

    @PutMapping
    public Result update(@RequestBody Employee employee){
        employeeService.update(employee);
        return Result.success();
    }

    @PutMapping("/editPassword")
    public Result editpassword(){
        return Result.success();
    }
}
