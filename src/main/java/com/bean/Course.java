package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private long course_id;
    private String course_name;
    private long instructor_id;
    private String type;
    private String content;
    private int state;
}
