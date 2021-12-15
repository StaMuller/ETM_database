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
public class Necessity {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private long course_id;
    private int dept_id;
}

