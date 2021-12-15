package com.operation;

import com.bean.Employee;
import com.bean.Takes;
import com.mapper.EmployeeMapper;
import com.mapper.ManagerMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class ManagerOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    ManagerMapper managerMapper = sqlSession.getMapper(ManagerMapper.class);
    EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);

    // manager (1
    public List<Employee> findManagedEmployee(int dept_id){
        return managerMapper.findManagedEmployee(dept_id);
    }

    // manager (2
    public void addCourseById(Long courseId, Long employeeId){
        Takes takes = new Takes();
        takes.setEmployee_id(employeeId);
        takes.setCourse_id(courseId);
        managerMapper.addCourse(takes);
        sqlSession.commit();
    }

    // manager (2
    public void addCourseByName(Long courseId, String name){
        // 首先找到对应员工的ID
        List<Employee> employeeList = employeeMapper.getEmployeeByName(name);
        for(Employee employee : employeeList){
            Takes takes = new Takes();
            takes.setCourse_id(courseId);
            takes.setEmployee_id(employee.getId());
            managerMapper.addCourse(takes);
        }
        sqlSession.commit();
    }

    // manager (3
    public List<Takes> queryTakesById(Long employeeId){
        return managerMapper.queryTakesOfEmployee(employeeId);
    }

    // manager (3
    public List<Takes> queryTakesByName(String name){
        List<Takes> takesResult = new ArrayList<>();
        List<Employee> employeeList = employeeMapper.getEmployeeByName(name);
        for(Employee employee : employeeList){
            takesResult.addAll(managerMapper.queryTakesOfEmployee(employee.getId()));
        }
        return takesResult;
    }

    public void close(){
        sqlSession.close();
    }
}
