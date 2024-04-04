package com.ann.reggie.controller;

import com.ann.reggie.common.R;
import com.ann.reggie.entity.Employee;
import com.ann.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> add(HttpServletRequest request,@RequestBody Employee employee){
//        设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //创建时间
        employee.setCreateTime(LocalDateTime.now());
//        更新时间
        employee.setUpdateTime(LocalDateTime.now());
//        创建人id 当前登录用户id
        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));

        boolean save = employeeService.save(employee);
        if (save) return R.success("新增员工成功");
        return R.error("新增员工失败");
    }

    /**
     * 员工分页查询
     * @param page 页码
     * @param pageSize 条目数
     * @param name 员工姓名
     * @return page对象
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
//        构建分页对象
        Page pageInfo= new Page(page,pageSize);
//        queryWrapper
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
//       判断姓名是否为空
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
//        构造器添加排序条件 更新时间
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

        /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
//        补全字段 更新时间 更新人
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
//MybatisPlus内置方法
        employeeService.updateById(employee);
        return R.success("修改成功!");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee != null) return R.success(employee);
        return R.error("没有查询到该员工");
    }
}
