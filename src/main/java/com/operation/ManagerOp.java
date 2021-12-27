package com.operation;

import com.bean.Course;
import com.bean.Employee;
import com.bean.Takes;
import com.mapper.CourseMapper;
import com.mapper.EmployeeMapper;
import com.mapper.ManagerMapper;
import com.mapper.TakesMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class ManagerOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    ManagerMapper managerMapper = sqlSession.getMapper(ManagerMapper.class);
    EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
    TakesMapper takesMapper = sqlSession.getMapper(TakesMapper.class);
    CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);

    TakesOp takesOp = new TakesOp();
    CourseOp courseOp = new CourseOp();

//获取管理员id
    public Employee getManagerById(Long employeeId){
        if(managerMapper.findManagerById(employeeId) == null){
            return null;
        }else{
            return employeeMapper.getEmployeeById(employeeId);
        }
    }

    // manager (1  查看自己部门下的员工信息
    public List<Employee> findManagedEmployee(int dept_id){
        return managerMapper.findManagedEmployee(dept_id);
    }

    // manager (2   根据员工号为员工分配培训课程
    public void addCourseById(Long courseId, Long employeeId){
        Takes takes = new Takes();
        takes.setEmployee_id(employeeId);
        takes.setCourse_id(courseId);
        managerMapper.addCourse(takes);
        sqlSession.commit();
    }

    // manager (2    根据姓名为员工分配培训课程
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

    // manager (3   根据姓名或员工号查询员工的培训成绩
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

    // manager (4 将自己部门下的员工转入到其他部门
    public boolean transDeptById(Long employeeId, int deptId){
        // 是否满足转部门条件
        if(takesOp.trans(employeeId)){
            managerMapper.transDept(employeeId, deptId);
            sqlSession.commit();
            return true;
        }
        return false;
    }

    // manager (4
    public int transDeptByName(String name, int deptId){
        List<Employee> employeeList = employeeMapper.getEmployeeByName(name);
        // 查询到有多个员工的话需要报错并提示
        if(employeeList.size() != 1){
            return 1;   // 表示有多个员工，需要指定员工号
        }else{
            Employee employee = employeeList.get(0);
            boolean confirm = transDeptById(employee.getId(), deptId);
            if(confirm){
                return 0;
            }else{
                return 2;   // 不符合转部门要求
            }
        }
    }

    // manager (5 查看某一门课的成绩
    public List<Takes> queryTakesOfCourse(Long courseId){
        return managerMapper.queryTakesOfCourse(courseId);
    }

    // manager (5 查看所有通过的课程成绩
    public List<Takes> queryPassedTakes(){
        return managerMapper.queryPassedTakes();
    }

    // manager (6 根据考试的次数定向查找某门课程未通过三次的员工信息
    public List<Employee> queryNotPassedThree(){
        List<Long> employeeIdList = managerMapper.queryNotPassedThree();
        List<Employee> employeeList = new ArrayList<>();
        for(Long id : employeeIdList){
            employeeList.add(employeeMapper.getEmployeeById(id));
        }
        return employeeList;
    }

    // manager (7 查询本部门下符合转部门条件的员工
    public List<Employee> queryTransEmployee(int deptId){
        List<Employee> transEmployee = new ArrayList<>();
        List<Employee> employeeList = managerMapper.findManagedEmployee(deptId);
        for(Employee employee : employeeList){
            if(takesOp.trans(employee.getId())){
                transEmployee.add(employee);
            }
        }
        return transEmployee;
    }

    // manager (8
    public boolean employeeTransById(Long employeeId){
        return takesOp.trans(employeeId);
    }

    // manager (8
    public int employeeTransByName(String name){
        List<Employee> employeeList = employeeMapper.getEmployeeByName(name);
        if(employeeList.size() != 1){
            // 有重名员工，需要指定员工号
            return 2;
        }else{
            Employee employee = employeeList.get(0);
            if(takesOp.trans(employee.getId())){
                return 0;   // 符合
            }else{
                return 1;   // 有不通过课程
            }
        }
    }

    // manager (9 查看该员工转入新部门后需要培训的课程
    public List<Course> courseAfterTransById(Long employeeId, int newDeptId){
        List<Course> courseList = courseOp.getCourseByDept(newDeptId);
        List<Long> passedCourse = takesMapper.passedCourseOfEmployee(employeeId);
        for(Long courseId : passedCourse){
            // 去除员工已通过的该部门的必修课程
            Course course = courseMapper.getCourseById(courseId);
            courseList.remove(course);
        }
        return courseList;
    }

    // manager (9
    public List<Course> courseAfterTransByName(String name, int newDeptId){
        List<Employee> employeeList = employeeMapper.getEmployeeByName(name);
        if(employeeList.size() == 1){
            Employee employee = employeeList.get(0);
            return courseAfterTransById(employee.getId(), newDeptId);
        }else{
            return null;  // 需要特定员工号
        }
    }

    public void close(){
        sqlSession.close();
    }
}
