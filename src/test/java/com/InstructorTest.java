package com;


import com.bean.Employee;
import com.bean.Instructor;
import com.operation.InstructorOp;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class InstructorTest {

    InstructorOp instructorOp = new InstructorOp();

    @Test
    public void test1(){

        List<Instructor> instructorList = instructorOp.getAllInstructor();

        for(Instructor instructor : instructorList){
            System.out.println("我在打印");
            System.out.println(instructor);
        }
    }


    @Test
    public void test2(){

//        Long id = 10231106003L;

//        List<Employee> employeeList = instructorOp.findInstructedEmployee(Long.parseLong("10231106003"));
//        List<Long> id = instructorOp.findInstructedEmployee(Long.parseLong("10231106003"));
//        for(Employee employee : employeeList){
            System.out.println("我在打印二号测试");
            System.out.println(instructorOp.findInstructedEmployee(Long.parseLong("10231106003")));
//        }
    }

    @Test
    public void test3(){

        Employee temp = instructorOp.getInstructorById(Long.parseLong("10231106003"));
        System.out.println("打印九"+temp);
    }

    @Test
    public void test4(){
//        instructorOp.inputScore(Long.parseLong("35140"),Long.parseLong("10231106001"),90);

          HashMap<String,Object> map=new HashMap<String,Object>();
            map.put("number",1);
            map.put("employee_id",Long.parseLong("10231106002"));
            map.put("course_id",Long.parseLong("35140"));

        instructorOp.updateTest(map);
        System.out.println("打印九");
    }
}
