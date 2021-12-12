package com.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private long course_id;
    private String course_name;
    private long i_id;
    private String type;
    private String content;
    private int state;
}
