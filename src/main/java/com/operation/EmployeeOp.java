package com.operation;

import com.bean.Employee;
import com.bean.Takes;
import com.mapper.EmployeeMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class EmployeeOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);

    // manager (1
    public List<Takes> getTakes(long id){
        return employeeMapper.getTakes(id);
    }
    public Employee getEmployeeById(Long employeeId){
        return employeeMapper.getEmployeeById(employeeId);
    }

    public void close(){
        sqlSession.close();
    }

}

