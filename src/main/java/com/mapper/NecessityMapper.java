package com.mapper;

import com.bean.Necessity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NecessityMapper {
    List<Long> getCourseByDept(int deptId);
    void courseDept(@Param("courseId") Long courseId, @Param("deptId") int deptId);
    Necessity findNecessity(@Param("courseId") Long courseId, @Param("deptId") int deptId);
}
