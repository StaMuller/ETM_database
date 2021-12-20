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
public class EmployeeView {
    private long id;
    private String name;
    private String gender;
    private int age;
    private Timestamp time;
    private String address;
    private long telephone;
    private String email;
    private String dept;
}
