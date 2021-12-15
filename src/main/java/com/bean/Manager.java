package com.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Manager {  // 部门主管
    private long id;
    private int dept_id;
}
