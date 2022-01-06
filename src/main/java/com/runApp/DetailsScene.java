package com.runApp;

import com.bean.Course;
import com.bean.Employee;
import com.bean.Takes;
import com.operation.CourseOp;
import com.operation.DepartmentOp;
import com.operation.EmployeeOp;
import com.runApp.Container.EmployeeView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.List;


public class DetailsScene {
    EmployeeOp employeeOp = new EmployeeOp();
    DepartmentOp departmentOp = new DepartmentOp();
    CourseOp courseOp = new CourseOp();

    public Scene setEmployee(Employee employee, Stage primaryStage, Scene primaryScene) {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);
        scene.setFill(Paint.valueOf("LightGrey"));

        // 返回主页面按钮
        Font button = new Font("楷体", 13);
        final Button back = new Button("返回");
        back.setLayoutX(1130);
        back.setLayoutY(30);
        back.setMinHeight(30);
        back.setMinWidth(40);
        back.setFont(button);
        root.getChildren().add(back);
        back.setOnMouseClicked(e -> primaryStage.setScene(primaryScene));

        // 设置标题
        Font title = new Font("FangSong", 30);
        final Label title_label = new Label("员工信息详情页（管理员权限）  " + "员工名：" + employee.getName());
        title_label.setLayoutX(350);
        title_label.setLayoutY(30);
        title_label.setFont(title);
        root.getChildren().add(title_label);

        Font area = new Font("楷体", 15);
        final Label info_label = new Label("个人基本信息");
        // 展示个人基本信息
        info_label.setLayoutX(50);
        info_label.setLayoutY(80);
        info_label.setFont(area);
        final TextArea info_area = new TextArea();
        info_area.setLayoutX(50);
        info_area.setLayoutY(115);
        info_area.setPrefWidth(1100);
        info_area.setPrefHeight(100);
        info_area.setWrapText(true);
        root.getChildren().addAll(info_label,info_area);

        StringBuilder showString = new StringBuilder();
        showString.append(String.format("%-18s", "员工号")).append("\t")
                .append(String.format("%-20s", "员工名")).append("\t")
                .append(String.format("%-8s", "性别")).append("\t")
                .append(String.format("%-4s", "年龄")).append("\t")
                .append(String.format("%-45s", "入职时间")).append("\t")
                .append(String.format("%-20s", "地址")).append("\t")
                .append(String.format("%-38s", "联系电话")).append("\t")
                .append(String.format("%-40s", "邮箱地址")).append("\t")
                .append(String.format("%-6s", "所在部门")).append("\t").append("\n");
        showString.append(String.format("%-18s", employee.getId())).append("\t")
                .append(String.format("%-20s", employee.getName())).append("\t")
                .append(String.format("%-8s", employee.getGender())).append("\t")
                .append(String.format("%-4s", employee.getAge())).append("\t")
                .append(String.format("%-45s", employee.getTime())).append("\t")
                .append(String.format("%-20s", employee.getAddress())).append("\t")
                .append(String.format("%-38s", employee.getTelephone())).append("\t")
                .append(String.format("%-40s", employee.getEmail())).append("\t")
                .append(String.format("%-6s", departmentOp.getDeptNameById(employee.getDept()))).append("\n");
        info_area.setText(showString.toString());

        // 展示培训成绩信息
        final Label score_label = new Label("培训成绩信息");
        score_label.setLayoutX(50);
        score_label.setLayoutY(235);
        score_label.setFont(area);
        final TextArea score_area = new TextArea();
        score_area.setLayoutX(50);
        score_area.setLayoutY(270);
        score_area.setPrefWidth(1100);
        score_area.setPrefHeight(300);
        score_area.setWrapText(true);
        root.getChildren().addAll(score_label,score_area);

        StringBuilder course_data = new StringBuilder();
        course_data.append(String.format("%-12s", "课程号")).append("\t")
                .append(String.format("%-18s", "课程名")).append("\t")
                .append(String.format("%-8s", "考试成绩")).append("\t")
                .append(String.format("%-12s", "通过状态")).append("\t")
                .append(String.format("%-20s", "成绩录入时间")).append("\t")
                .append("\n");

        List<Takes> takesList = employeeOp.findScore(employee.getId());
        for (Takes takes : takesList) {
            course_data.append(String.format("%-12s", takes.getCourse_id())).append("\t")
                    .append(String.format("%-18s", courseOp.getCourseById(takes.getCourse_id()).getCourse_name())).append("\t")
                    .append(String.format("%-20s", takes.getNumber())).append("\t");
            if (takes.getState() == null) {
                course_data.append(String.format("%-8s", "未考试"));
            } else {
                course_data.append(String.format("%-8s", takes.getState())).append("\t");
            }
            if (takes.getTime() == null) {
                course_data.append(String.format("%-20s", " ")).append("\t").append("\n");
            } else {
                course_data.append(String.format("%-20s", takes.getTime())).append("\t").append("\n");
            }
        }
        score_area.setText(String.valueOf(course_data));
        return scene;
    }

}
