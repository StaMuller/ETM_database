package com.mapper;

import com.bean.Employee;
import com.bean.Manager;
import com.bean.Takes;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ManagerMapper {
    Manager findManagerById(Long id);
    List<Employee> findManagedEmployee(int dept_id);
    void addCourse(Takes takes);
    List<Takes> queryTakesOfEmployee(Long employeeId);
    void transDept(@Param("employeeId") Long employeeId, @Param("deptId") int deptId);
    List<Takes> queryTakesOfCourse(Long courseId);
    List<Takes> queryPassedTakes();
    List<HashMap<String, Object>> queryNotPassed(@Param("courseId") Long courseId, @Param("notPassedTime") int notPassedTime);
    void deleteManager(Long id);
}
