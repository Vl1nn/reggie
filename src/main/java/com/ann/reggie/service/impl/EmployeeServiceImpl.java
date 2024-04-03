package com.ann.reggie.service.impl;

import com.ann.reggie.entity.Employee;
import com.ann.reggie.mapper.EmployeeMapper;
import com.ann.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
implements EmployeeService{

}
