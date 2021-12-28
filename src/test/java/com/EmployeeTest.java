package com;


import com.bean.Course;
import com.bean.Employee;
import com.bean.Instructor;
import com.bean.Takes;
import com.operation.EmployeeOp;
import com.operation.InstructorOp;
import javafx.scene.Scene;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class EmployeeTest {


    EmployeeOp employeeOp = new EmployeeOp();
    @Test
    public void test1(){// employee (1查看被分配到的课程以及教员信息  注意：课程信息和导师信息不在一个集合里
        List<Course>course = employeeOp.findCourse(Long.parseLong("10231106004"));
        for (Course temp : course){
            Long instructorId = temp.getInstructor_id();//获取instructorId
            System.out.println(temp);//课程信息
            System.out.println(employeeOp.getEmployeeById(instructorId));//导师信息
        }
    }

    @Test
    public void test2(){// employee (2 查看自己的历史培训成绩信息
        List<Takes>score = employeeOp.findScore(Long.parseLong("10231106004"));
        for (Takes temp : score){
            System.out.println(temp);//历史培训成绩信息
        }
    }

    @Test
    public void test3(){// employee (3 维护个人信息
        Employee employee = new Employee();
        employee.setId(Long.parseLong("10231106002"));
        employee.setName("李耀");
        employee.setGender("男");
        employee.setAge(35);
        employee.setAddress("老挝");
        employee.setTelephone(233);
        employee.setEmail("example@xx.com");
        employee.setDept(1);
        System.out.println(employee);
        employeeOp.reviseInfo(employee);

    }


}
