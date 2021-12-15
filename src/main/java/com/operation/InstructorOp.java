package com.operation;

import com.bean.Instructor;
import com.mapper.InstructorMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class InstructorOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    InstructorMapper instructorMapper = sqlSession.getMapper(InstructorMapper.class);

    public List<Instructor> getAllInstructor(){
        return instructorMapper.getAllInstructor();
    }
}
