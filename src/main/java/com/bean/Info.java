package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 辅助类
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    Employee employee;
    List<Takes> takes;
}
