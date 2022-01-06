package com.mapper;

public interface DepartmentMapper {
    String getNameById(int deptId);
    int getDeptIdByName(String deptName);
}
