package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.Employee;
import com.bean.Takes;

import java.util.List;

public interface EmployeeMapper extends BaseMapper<Employee> {
    List<Takes> getTakes(long id);
    List<Employee> getEmployeeByName(String name);
}
