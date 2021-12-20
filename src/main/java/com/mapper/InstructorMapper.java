package com.mapper;

import com.bean.Instructor;

import java.util.List;

public interface InstructorMapper {
    List<Instructor> getAllInstructor();
    Instructor getInstructorById(Long id);
    void deleteInstructor(Long id);
}
