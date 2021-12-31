package com;


import com.bean.Employee;
import com.bean.Instructor;
import com.bean.Takes;
import com.operation.EmployeeOp;
import com.operation.InstructorOp;
import com.operation.ManagerOp;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class ManagerTest {

    ManagerOp managerOp = new ManagerOp();
    EmployeeOp employeeOp = new EmployeeOp();

    @Test
    public void test1(){

        List<Employee> employeeList = managerOp.findManagedEmployee(3);

        for(Employee employee : employeeList){
            System.out.println(employee);
            List<Takes> takesList = employeeOp.getTakes(employee.getId());
            for(Takes takes : takesList){
                System.out.println(takes);
            }
        }
    }

    @Test
    public void test2(){
        managerOp.addCourseById(Long.parseLong("35140"), Long.parseLong("10231106004"));
        managerOp.addCourseByName(Long.parseLong("35140"), "李耀");
    }

    @Test
    public void test3(){
        List<Takes> takesList = managerOp.queryTakesById(Long.parseLong("10231106001"));
        for(Takes takes : takesList){
            System.out.println("打印四："+takes);
        }
        takesList = managerOp.queryTakesByName("卢潇");
        for(Takes takes : takesList){
            System.out.println("打印四："+takes);
        }
    }

    @Test
    public void test5(){
       Employee temp = managerOp.getManagerById(Long.parseLong("10231106004"));
       System.out.println("打印八"+temp);

    }
    @Test
    public void test4(){
        managerOp.transDeptById(Long.parseLong("10231106004"),6);
    }

    @Test
    public void test6(){
        List<HashMap<String, Object>> list = managerOp.queryNotPassed(Long.parseLong("35140"), 0);
        System.out.println(list);
    }

    @Test
    public void test7(){
        List<Employee> employeeList = managerOp.queryTransEmployee(1);
        System.out.println(employeeList);
    }

}
