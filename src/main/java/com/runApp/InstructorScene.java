package com.runApp;

import com.bean.Employee;
import com.operation.DepartmentOp;
import com.operation.InstructorOp;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

public class InstructorScene {

    InstructorOp instructorOp = new InstructorOp();
    DepartmentOp departmentOp = new DepartmentOp();

    public Scene setInstructor(Employee instructor, Stage primaryStage, Scene primaryScene) {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);
        scene.setFill(Paint.valueOf("LightGrey"));

        // 返回主页面按钮
        Font button = new Font("楷体", 13);
        final Button back = new Button("退出");
        back.setLayoutX(1130);
        back.setLayoutY(30);
        back.setFont(button);
        root.getChildren().add(back);
        back.setOnMouseClicked(e -> primaryStage.setScene(primaryScene));

        // 设置标题
        Font title = new Font("FangSong", 30);
        final Label title_label = new Label(instructor.getName() + "，欢迎来到员工管理系统（教员权限）");
        title_label.setLayoutX(350);
        title_label.setLayoutY(30);
        title_label.setFont(title);
        root.getChildren().add(title_label);

        Font area = new Font("楷体", 15);
        // 查询教授的员工信息
        final Label teach_label = new Label("教授的员工信息");
        teach_label.setLayoutX(50);
        teach_label.setLayoutY(120);
        teach_label.setFont(area);
        final TextArea teach_area = new TextArea();
        teach_area.setLayoutX(50);
        teach_area.setLayoutY(155);
        teach_area.setPrefWidth(1100);
        teach_area.setPrefHeight(250);
        teach_area.setWrapText(true);
        final Button teach_query = new Button("查询");
        teach_query.setLayoutX(180);
        teach_query.setLayoutY(118);
        teach_query.setFont(button);
        root.getChildren().addAll(teach_label, teach_area, teach_query);

        teach_query.setOnMouseClicked(e -> {
            StringBuilder showString = new StringBuilder();
            List<Employee> employeeList = instructorOp.findInstructedEmployee(instructor.getId());
            for(Employee employee : employeeList){
                showString.append(
                        employee.getId()).append("  ")
                        .append(employee.getName()).append("  ")
                        .append(employee.getGender()).append("  ")
                        .append(employee.getAge()).append("  ")
                        .append(employee.getTime()).append("  ")
                        .append(employee.getAddress()).append("  ")
                        .append(employee.getTelephone()).append("  ")
                        .append(employee.getEmail()).append("  ")
                        .append(departmentOp.getDeptNameById(employee.getDept())).append("\n");
            }
            teach_area.setText(showString.toString());
        });

        // 录入培训成绩
        final Label grade_label = new Label("录入员工培训信息");
        grade_label.setLayoutX(50);
        grade_label.setLayoutY(445);
        grade_label.setFont(area);
        final Label grade_label1 = new Label("员工号");
        grade_label1.setLayoutX(50);
        grade_label1.setLayoutY(480);
        grade_label1.setFont(area);
        final TextArea grade_area1 = new TextArea();
        grade_area1.setLayoutX(100);
        grade_area1.setLayoutY(480);
        grade_area1.setPrefWidth(150);
        grade_area1.setPrefHeight(10);
        grade_area1.setWrapText(true);
        final Label grade_label2 = new Label("课程号");
        grade_label2.setLayoutX(280);
        grade_label2.setLayoutY(480);
        grade_label2.setFont(area);
        final TextArea grade_area2 = new TextArea();
        grade_area2.setLayoutX(330);
        grade_area2.setLayoutY(480);
        grade_area2.setPrefWidth(150);
        grade_area2.setPrefHeight(10);
        grade_area2.setWrapText(true);
        final Label grade_label3 = new Label("培训成绩");
        grade_label3.setLayoutX(530);
        grade_label3.setLayoutY(480);
        grade_label3.setFont(area);
        final TextArea grade_area3 = new TextArea();
        grade_area3.setLayoutX(600);
        grade_area3.setLayoutY(480);
        grade_area3.setPrefWidth(150);
        grade_area3.setPrefHeight(10);
        grade_area3.setWrapText(true);
        root.getChildren().addAll(grade_label, grade_label1, grade_label2, grade_label3,
                                  grade_area1, grade_area2, grade_area3);
        final Button insert_grade = new Button("录入");
        insert_grade.setLayoutX(800);
        insert_grade.setLayoutY(480);
        insert_grade.setFont(button);
        root.getChildren().addAll(insert_grade);

        insert_grade.setOnMouseClicked(e ->{
            String employeeId = grade_area1.getText();
            String courseId = grade_area2.getText();
            int number = Integer.parseInt(grade_area3.getText());
            HashMap<String, Object> map = new HashMap<>();
            map.put("employeeId", employeeId);
            map.put("courseId", courseId);
            map.put("number", number);
            instructorOp.inputScore(map);
        });

        return scene;
    }

}
