package com.operation;

import com.bean.Employee;
import com.bean.Instructor;
import com.mapper.EmployeeMapper;
import com.mapper.InstructorMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class InstructorOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    InstructorMapper instructorMapper = sqlSession.getMapper(InstructorMapper.class);
    EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);

    public List<Instructor> getAllInstructor(){
        return instructorMapper.getAllInstructor();
    }

    public Employee getInstructorById(Long employeeId){
        if(instructorMapper.getInstructorById(employeeId) == null){
            return null;
        }else{
            return employeeMapper.getEmployeeById(employeeId);
        }
    }
}
