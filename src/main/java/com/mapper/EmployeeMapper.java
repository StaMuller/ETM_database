package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bean.Course;
import com.bean.Employee;
import com.bean.Takes;

import java.util.List;

public interface EmployeeMapper extends BaseMapper<Employee> {
    List<Takes> getTakes(long id);
    Employee getEmployeeById(Long id);
    List<Employee> getEmployeeByName(String name);
    void deleteEmployee(Long id);
    void reviseInfo(Employee employee);
    List<Course> getCourse(Long id);
    List<Takes> getScore(Long id);
}
