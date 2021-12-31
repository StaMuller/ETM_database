package com.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Takes {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private long employee_id;
    private long course_id;
    private int number;
    private String state;
    private Timestamp time;
}
