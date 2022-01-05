package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.*;
import com.operation.AdministratorOp;

import java.util.List;

public interface AdministratorMapper extends BaseMapper<Administrator> {
    Administrator getAdministratorById(Long id);
    void addEmployee(Employee employee);
    void updateCourse(Course course);
    List<Course> queryAllCourse();
    List<Log> queryAllLog();
    void addLog(Log log);
    List<Employee> queryAllEmployee();
}