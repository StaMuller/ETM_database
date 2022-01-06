package com.mapper;

import com.bean.Takes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TakesMapper {
    // 获取某员工的所有课程：返回课程号
    List<Long> courseOfEmployee(Long employeeId);
    // 获取某员工通过的课程：返回课程号
    List<Long> passedCourseOfEmployee(Long employeeId);
    List<Takes> getCourseOfNoNumber(@Param("employeeId") Long employeeId, @Param("courseId") Long courseId);
}
