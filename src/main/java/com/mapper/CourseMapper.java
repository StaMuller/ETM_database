package com.mapper;

import com.bean.Course;

public interface CourseMapper {
    Course getCourseById(Long courseId);
    void addCourse(Course course);
    void deleteCourse(Long courseId);
}
