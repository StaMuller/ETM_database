package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.*;
import com.operation.AdministratorOp;

import java.util.List;

public interface AdministratorMapper extends BaseMapper<Administrator> {
    Administrator getAdministratorById(Long id);
    void addEmployee(Employee employee);
    void updateEmployee(Employee employee);
    List<Employee> searchEmployee(String searchString);
    void updateCourse(Course course);
    List<Course> searchCourse(String searchString);
    List<Log> queryLog(String searchString);
    void addLog(Log log);
}