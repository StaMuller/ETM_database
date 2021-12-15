package com.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

import java.sql.Timestamp;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    private String name;
    private String gender;
    private int age;
    private Timestamp time;
    private String address;
    private long telephone;
    private String email;
    private int dept_id;
}
