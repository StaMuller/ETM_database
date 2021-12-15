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
public class Log {
    @TableId(value = "log_id", type = IdType.AUTO)
    private int log_id;
    private long a_id;
    private String operation;
    private Timestamp date;
}
