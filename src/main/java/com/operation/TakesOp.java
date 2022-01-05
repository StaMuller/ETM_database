package com.operation;

import com.bean.Takes;
import com.mapper.TakesMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class TakesOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    TakesMapper takesMapper = sqlSession.getMapper(TakesMapper.class);

    // 判断该员工是否符合转部门条件
    public boolean trans(Long employeeId){
        List<Long> allCourse = takesMapper.courseOfEmployee(employeeId);
        List<Long> passedCourse = takesMapper.passedCourseOfEmployee(employeeId);
        return allCourse.size() == passedCourse.size();
    }

    public void close(){
        sqlSession.close();
    }
}
