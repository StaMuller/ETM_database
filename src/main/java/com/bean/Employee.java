package com.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

import java.sql.Timestamp;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private long e_id;
    private String name;
    private String gender;
    private int age;
    private Timestamp time;
    private String address;
    private long telephone;
    private String email;
    private int dept_id;
}
