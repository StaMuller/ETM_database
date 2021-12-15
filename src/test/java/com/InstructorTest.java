package com;


import com.bean.Instructor;
import com.operation.InstructorOp;
import org.junit.Test;

import java.util.List;

public class InstructorTest {

    InstructorOp instructorOp = new InstructorOp();

    @Test
    public void test(){

        List<Instructor> instructorList = instructorOp.getAllInstructor();

        for(Instructor instructor : instructorList){
            System.out.println(instructor);
        }

    }
}
