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
    public void test2(){//instructor (1 查看该教员教授的员工的信息
            System.out.println("我在打印二号测试");
            System.out.println(instructorOp.findInstructedEmployee(Long.parseLong("10231106002")));
//        }
    }

    @Test
    public void test3(){//根据id获取instructor

        Employee temp = instructorOp.getInstructorById(Long.parseLong("10231106003"));
        System.out.println("打印九"+temp);
    }

    @Test
    public void test4(){//录入成绩
          HashMap<String,Object> map=new HashMap<String,Object>();
            map.put("number",888);
            map.put("employee_id",Long.parseLong("10231106002"));
            map.put("course_id",Long.parseLong("35140"));
        instructorOp.inputScore(map, Long.parseLong("10231106002"));
        System.out.println("打印九");
    }
}
