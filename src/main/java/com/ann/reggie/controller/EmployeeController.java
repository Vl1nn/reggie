package com.ann.reggie.controller;

import com.ann.reggie.common.R;
import com.ann.reggie.entity.Employee;
import com.ann.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 登录功能
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R login(HttpServletRequest request,
                             @RequestBody Employee employee){
        log.info("backend_login_run");
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        // 将页面提交的密码使用md5加密处理。
        String password = employee.getPassword();
       // 将密码使用MD5算法进行加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//       将前端页面提交的用户名进行查询  lambdaQueryWrapper
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
//        没有查询到则说明登录失败
        if (emp == null) return R.error("登录失败");
        /*
        前端明文处理(md5加密后)的密码和数据库密码进行比对
        不一样返回false，取反后为true 执行登录失败返回
         */
        if (!password.equals(emp.getPassword())) return R.error("密码错误");
//        判断账号使用状态
        if (emp.getStatus() == 0) return R.error("账号已禁用");
//       返回登录对象
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
    @PostMapping("/logout")
    public R logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功！");
    }
}
