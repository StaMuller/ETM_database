package com.runApp.Container;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CourseView {
    private long course_id;
    private String course_name;
    private long instructor_id;
    private String type;
    private String content;
    private int state;
}
