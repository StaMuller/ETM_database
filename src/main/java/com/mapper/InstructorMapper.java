package com.mapper;
import com.bean.Instructor;
import com.bean.Takes;

import java.util.HashMap;
import java.util.List;

public interface InstructorMapper {
    List<Instructor> getAllInstructor();
    Instructor getInstructorById(Long id);
    void deleteInstructor(Long id);
    List<Long> getInstructedEmployee(Long id);
    void setScore(HashMap map);
}
