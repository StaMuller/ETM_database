package com;

import com.bean.Employee;
import com.operation.AdministratorOp;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;


public class AdministratorTest {

    AdministratorOp administratorOp = new AdministratorOp();

    @Test
    public void testAddEmployee(){
        Employee employee = new Employee();
        employee.setName("蒋玥");
        employee.setGender("女");
        employee.setAge(29);
        Date date = new Date();
        employee.setTime(new Timestamp(date.getTime()));
        employee.setAddress("西安");
        employee.setTelephone(12345);
        employee.setEmail("example@xx.com");
        employee.setDept(6);
        administratorOp.addEmployee(employee);
    }
}
