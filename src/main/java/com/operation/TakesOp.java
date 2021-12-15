package com.operation;

import com.bean.Takes;
import com.mapper.TakesMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

public class TakesOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    TakesMapper takesMapper = sqlSession.getMapper(TakesMapper.class);

    public void addCourse(long employee_id, int course_id){
        Takes takes = new Takes();
        takes.setEmployee_id(employee_id);
        takes.setCourse_id(course_id);
        takesMapper.addCourse(takes);
    }
}
