package com;


import com.bean.Instructor;
import com.mapper.InstructorMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class InstructorMapperTest {

    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        InstructorMapper instructorMapper = sqlSession.getMapper(InstructorMapper.class);
        List<Instructor> instructorList = instructorMapper.getAllInstructor();

        for(Instructor instructor : instructorList){
            System.out.println(instructor);
        }

        sqlSession.close();
    }
}
