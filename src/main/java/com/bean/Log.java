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
public class Log {
    @TableId(value = "log_id", type = IdType.AUTO)
    private int log_id;
    private long a_id;
    private String operation;
    private Timestamp date;
}
