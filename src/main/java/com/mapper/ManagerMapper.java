package com.mapper;

import com.bean.Employee;
import com.bean.Takes;

import java.util.List;

public interface ManagerMapper {
    List<Employee> findManagedEmployee(int dept_id);
    void addCourse(Takes takes);
    List<Takes> queryTakesOfEmployee(Long employeeId);
    void transDept(Long employeeId, int deptId);
    List<Takes> queryTakesOfCourse(Long courseId);
    List<Takes> queryPassedTakes();
    List<Long> queryNotPassedThree();
}
