package com.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @TableId(value = "dept_id", type = IdType.AUTO)
    private int dept_id;
    private String dept_name;
}
