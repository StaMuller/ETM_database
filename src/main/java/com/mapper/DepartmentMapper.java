package com.mapper;

import com.bean.Department;

public interface DepartmentMapper {
    String getNameById(int deptId);
    int getDeptIdByName(String deptName);
}
