package com.operation;

import com.bean.Course;
import com.bean.Employee;
import com.bean.Instructor;
import com.bean.Takes;
import com.mapper.CourseMapper;
import com.mapper.EmployeeMapper;
import com.mapper.InstructorMapper;
import com.mapper.TakesMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InstructorOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    InstructorMapper instructorMapper = sqlSession.getMapper(InstructorMapper.class);
    EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
    TakesMapper takesMapper = sqlSession.getMapper(TakesMapper.class);
    CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
    EmployeeOp employeeOp = new EmployeeOp();
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

    // instructor (1 查看该教员教授的员工的信息
    public List<Employee> findInstructedEmployee(Long employeeId) {
        if (instructorMapper.getInstructedEmployee(employeeId) == null) {
            return null;
        } else {
            List<Long> temp = instructorMapper.getInstructedEmployee(employeeId);
            List<Employee> employeeList = new ArrayList<>();
            for (Long i : temp) {
                employeeList.add(employeeOp.getEmployeeById(Long.parseLong(String.valueOf(i))));
            }
            return employeeList;
        }
    }

    //instructor (2 根据员工号以及课程号为员工录入培训成绩
    public boolean inputScore(HashMap<String, Object> map, Long instructorId){
        // 需判断该员工是否修过该课
        Long employeeId = Long.parseLong((String) map.get("employeeId"));
        Long courseId = Long.parseLong((String) map.get("courseId"));
        List<Takes> takesList = takesMapper.getCourseOfNoNumber(employeeId, courseId);
        int index = 0;
        for(Takes takes : takesList){
            if(takes.getState() == null){
                break;
            }
            ++index;
        }
        if(index == takesList.size()){
            return false;
        }
        // 判断该课是否为该教员教授
        Course course = courseMapper.getCourseById(courseId);
        if(course.getInstructor_id() != instructorId){
            return false;
        }else{
            instructorMapper.setScore(map);
            sqlSession.commit();
            return true;
        }
    }

    public void close(){
        sqlSession.close();
    }
}

