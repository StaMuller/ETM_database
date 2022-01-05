package com.operation;

import com.bean.Course;
import com.bean.Employee;
import com.bean.Takes;
import com.mapper.EmployeeMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class EmployeeOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);


    public List<Takes> getTakes(long id){
        return employeeMapper.getTakes(id);
    }
    public Employee getEmployeeById(Long employeeId){
        return employeeMapper.getEmployeeById(employeeId);
    }

    public List<Employee> getEmployeeByName(String name){
        return employeeMapper.getEmployeeByName(name);
    }

    // employee (1查看被分配到的课程以及教员信息
    public List<Course> findCourse(Long employeeId) {
        return employeeMapper.getCourse(employeeId);
    }

    // employee (2查看自己的历史培训成绩信息
    public List<Takes> findScore(Long employeeId) {
        return employeeMapper.getScore(employeeId);
    }

    //employee (3 维护个人信息
    public void reviseInfo(Employee employee){
        employeeMapper.reviseInfo(employee);
        sqlSession.commit();
    }
    public void close(){
        sqlSession.close();
    }

}

