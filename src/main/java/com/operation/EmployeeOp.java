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


    public List<Takes> getTakes(long id){
        return employeeMapper.getTakes(id);
    }
    public Employee getEmployeeById(Long employeeId){
        return employeeMapper.getEmployeeById(employeeId);
    }

    //employee (1查看被分配到的课程以及教员信息
    public List findCourse(Long employeeId) {
        List courseInfo = employeeMapper.getCourse(employeeId);
        return courseInfo;
    }

    //employee (2查看自己的历史培训成绩信息
    public List findScore(Long employeeId) {
        List scoreInfo = employeeMapper.getScore(employeeId);
        return scoreInfo;
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

