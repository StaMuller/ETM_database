package com.operation;

import com.bean.Department;
import com.mapper.*;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

public class DepartmentOp {
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);

    public String getDeptNameById(int deptId){
        return departmentMapper.getNameById(deptId);
    }
}
