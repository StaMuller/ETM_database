package com.operation;

import com.bean.*;
import com.mapper.*;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdministratorOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    AdministratorMapper administratorMapper = sqlSession.getMapper(AdministratorMapper.class);
    EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
    InstructorMapper instructorMapper = sqlSession.getMapper(InstructorMapper.class);
    CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
    ManagerMapper managerMapper = sqlSession.getMapper(ManagerMapper.class);
    TakesMapper takesMapper = sqlSession.getMapper(TakesMapper.class);
    NecessityMapper necessityMapper = sqlSession.getMapper(NecessityMapper.class);

    public Employee getAdminById(Long employeeId){
        if(administratorMapper.getAdministratorById(employeeId) == null){
            return null;
        }else{
            return employeeMapper.getEmployeeById(employeeId);
        }
    }

    // ad (1 增加
    public void addEmployee(Employee employee){
        administratorMapper.addEmployee(employee);
        sqlSession.commit();
    }

    // ad (1 删除
    public void deleteEmployee(Long employeeId){
        // 删除教员
        // 1、设置对应课程的教员号为全0
        // 2、删除instructor数据
        // 3、删除employee数据
        Instructor instructor = instructorMapper.getInstructorById(employeeId);
        if(instructor != null){
            courseMapper.updateInstructorOfCourse(employeeId);
            instructorMapper.deleteInstructor(employeeId);
        }

        // 删除部门主管
        // 1、删除manager数据
        // 2、删除employee数据
        Manager manager = managerMapper.findManagerById(employeeId);
        if(manager != null){
            managerMapper.deleteManager(employeeId);
        }

        // 1、删除employee数据
        // 2、删除takes数据
        employeeMapper.deleteEmployee(employeeId);
        takesMapper.deleteTakesRecord(employeeId);
        sqlSession.commit();
    }

    // ad (1 修改
    public void updateEmployee(Long employeeId, String section, String update){
        Employee employee = employeeMapper.getEmployeeById(employeeId);
        // 系统管理员只允许修改姓名、地址、号码与邮箱
        switch (section) {
            case "name" -> employee.setName(update);
            case "address" -> employee.setAddress(update);
            case "telephone" -> employee.setTelephone(Long.parseLong(update));
            case "email" -> employee.setEmail(update);
        }
        administratorMapper.updateEmployee(employee);
        sqlSession.commit();
    }

    // ad (1 查找
    public List<Employee> queryEmployee(String searchString){
        return administratorMapper.searchEmployee(searchString);
    }

    // ad (2 增加
    public void addCourse(Course course){
        courseMapper.addCourse(course);
        sqlSession.commit();
    }

    // ad (2 删除
    public void deleteCourse(Long courseId){
        // 1、删除necessity数据
        // 2、删除course数据
        courseMapper.deleteCourse(courseId);
        necessityMapper.deleteNecessity(courseId);
        sqlSession.commit();
    }

    // ad (2 修改
    public void updateCourse(Long courseId, String section, String update){
        Course course = courseMapper.getCourseById(courseId);
        // 系统管理员只能修改课程名、员工号、课程类型与课程内容
        switch (section){
            case "course_name" -> course.setCourse_name(update);
            case "instructor_id" -> course.setInstructor_id(Integer.parseInt(update));
            case "type" -> course.setType(update);
            case "content" -> course.setContent(update);
        }
        administratorMapper.updateCourse(course);
        sqlSession.commit();
    }

    // ad (2 查找
    public List<Course> queryCourse(String searchString){
        return administratorMapper.searchCourse(searchString);
    }

    // ad (3 根据员工号查询用户的个人基本信息与培训成绩信息
    public Info queryInfoById(Long employeeId){
        Employee employee = employeeMapper.getEmployeeById(employeeId);
        List<Takes> takesList = takesMapper.gradeOfEmployee(employeeId);
        Info info = new Info();
        info.setEmployee(employee);
        info.setTakes(takesList);
        return info;
    }

    // ad (3 根据姓名查询用户的个人基本信息与培训成绩信息：可能会有重名用户
    public List<Info> queryInfoByName(String name){
        List<Employee> employeeList = employeeMapper.getEmployeeByName(name);
        List<Info> infoList = new ArrayList<>();
        for(Employee employee : employeeList){
            infoList.add(queryInfoById(employee.getId()));
        }
        return infoList;
    }

    // ad (4 管理日志信息：默认只能查看日志与增加日志，不能删除或修改日志
    public List<Log> queryLog(String searchString){
        return administratorMapper.queryLog(searchString);
    }

    public void addLog(Long a_id, String operation){
        Log log = new Log();
        log.setA_id(a_id);
        log.setOperation(operation);
        Date date = new Date();
        log.setDate(new Timestamp(date.getTime()));
        administratorMapper.addLog(log);
        sqlSession.commit();
    }

    public void close(){
        sqlSession.close();
    }
}
