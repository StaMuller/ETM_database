package com.mapper;

import java.util.List;

public interface NecessityMapper {
    List<Long> getCourseByDept(int deptId);
    void deleteNecessity(Long courseId);
}
