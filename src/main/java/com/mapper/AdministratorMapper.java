package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.Administrator;
import com.bean.Employee;

public interface AdministratorMapper extends BaseMapper<Administrator> {
    void addEmployee(Employee employee);
}