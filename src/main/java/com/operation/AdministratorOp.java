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
    DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);

    public Employee getAdminById(Long employeeId){
        if(administratorMapper.getAdministratorById(employeeId) == null){
            return null;
        }else{
            return employeeMapper.getEmployeeById(employeeId);
        }
    }

    // ad (1 增加
    public boolean addEmployee(Employee employee){
        String department = departmentMapper.getNameById(employee.getDept());
        if(department == null){
            return false;
        }
        administratorMapper.addEmployee(employee);
        sqlSession.commit();
        return true;
    }

    // ad (1 删除
    public boolean deleteEmployee(Long employeeId){
        // 删除教员
        // 1、设置对应课程的教员号为全0
        // 2、删除instructor数据
        // 3、删除employee数据
        if(employeeMapper.getEmployeeById(employeeId) == null){
            return false;
        }
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
        return true;
    }

    // ad (2 查找：显示全部
    public List<Employee> queryAllEmployee(){
        return administratorMapper.queryAllEmployee();
    }

    // ad (2 增加
    public boolean addCourse(Course course){
        Long instructorId = course.getInstructor_id();
        if(instructorMapper.getInstructorById(instructorId) == null){
            return false;
        }
        courseMapper.addCourse(course);
        sqlSession.commit();
        return true;
    }

    // ad (2 删除
    public boolean deleteCourse(Long courseId){
        // 1、删除necessity数据
        // 2、删除course数据
        if(courseMapper.getCourseById(courseId) == null){
            return false;
        }
        courseMapper.deleteCourse(courseId);
        necessityMapper.deleteNecessity(courseId);
        sqlSession.commit();
        return true;
    }

    // ad (2 修改
    public void updateCourse(Course course){
        administratorMapper.updateCourse(course);
        sqlSession.commit();
    }

    // ad (2 查找
    public List<Course> queryAllCourse(){
        return administratorMapper.queryAllCourse();
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
    public boolean addLog(Long employee_id, String operation){
        if(employeeMapper.getEmployeeById(employee_id) == null){
            return false;
        }
        Log log = new Log();
        log.setEmployee_id(employee_id);
        log.setOperation(operation);
        Date date = new Date();
        log.setDate(new Timestamp(date.getTime()));
        administratorMapper.addLog(log);
        sqlSession.commit();
        return true;
    }

    public List<Log> queryAllLog(){
        return administratorMapper.queryAllLog();
    }

    public void close(){
        sqlSession.close();
    }
}
