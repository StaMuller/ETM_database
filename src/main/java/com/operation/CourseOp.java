package com.operation;

import com.bean.Course;
import com.mapper.CourseMapper;
import com.mapper.NecessityMapper;
import com.mapper.TakesMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class CourseOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    NecessityMapper necessityMapper = sqlSession.getMapper(NecessityMapper.class);
    CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);

    public List<Course> getCourseByDept(int deptId){
        List<Course> courseList = new ArrayList<>();
        List<Long> courseId = necessityMapper.getCourseByDept(deptId);
        for(Long id : courseId){
            courseList.add(courseMapper.getCourseById(id));
        }
        return courseList;
    }

    public void close(){
        sqlSession.close();
    }
}
