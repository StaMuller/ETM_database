package com.operation;

import com.bean.Employee;
import com.bean.Instructor;
import com.bean.Takes;
import com.mapper.EmployeeMapper;
import com.mapper.InstructorMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InstructorOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    InstructorMapper instructorMapper = sqlSession.getMapper(InstructorMapper.class);
    EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);

    public List<Instructor> getAllInstructor() {
        return instructorMapper.getAllInstructor();
    }

    public Employee getInstructorById(Long employeeId) {
        if (instructorMapper.getInstructorById(employeeId) == null) {
            return null;
        } else {
            return employeeMapper.getEmployeeById(employeeId);
        }
    }

    // instructor (1 查看该教员教授的员工的信息
    public List<Employee> findInstructedEmployee(Long employeeId) {
        if (instructorMapper.getInstructedEmployee(employeeId).size() == 0) {
            System.out.println("查询失败");
            return null;
        } else {
            List<Long> temp = instructorMapper.getInstructedEmployee(employeeId);
            List<Employee> employeeList = new ArrayList<>();
            for (Long i : temp) {
                employeeList.add(employeeMapper.getEmployeeById(Long.parseLong(String.valueOf(i))));
            }
            return employeeList;
        }
    }

    //instructor (2 根据员工号以及课程号为员工录入培训成绩
    // 参数map: number, employeeId, courseId
    public void inputScore(HashMap<String, Object> map) {
        if((int)map.get("number") > 100){
            System.out.println("wrong number");
            return;
        }
        instructorMapper.setScore(map);
        sqlSession.commit();
    }
}
