package com.runApp;

import com.bean.Course;
import com.bean.Employee;
import com.bean.Takes;
import com.operation.DepartmentOp;
import com.operation.EmployeeOp;
import com.operation.ManagerOp;
import com.runApp.Container.EmployeeView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class ManagerScene {

    TransScene transScene = new TransScene();
    ExamScene examScene = new ExamScene();
    EmployeeOp employeeOp = new EmployeeOp();
    DepartmentOp departmentOp = new DepartmentOp();
    ManagerOp managerOp = new ManagerOp();

    public Scene setManager(Employee manager, Stage primaryStage, Scene primaryScene){
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
        final Label title_label = new Label(manager.getName() + "，欢迎来到员工管理系统（部门主管权限）");
        title_label.setLayoutX(350);
        title_label.setLayoutY(30);
        title_label.setFont(title);
        root.getChildren().add(title_label);

        Font area = new Font("楷体", 15);
        // 查询部门的员工信息
        final Label manage_label = new Label("部门的员工信息");
        manage_label.setLayoutX(50);
        manage_label.setLayoutY(120);
        manage_label.setFont(area);

        // 进入查询成绩页面按钮
        final Button switch_exam = new Button("进入查询成绩页面");
        switch_exam.setLayoutX(700);
        switch_exam.setLayoutY(118);
        switch_exam.setFont(button);
        root.getChildren().addAll(switch_exam);
        // 切换到查询成绩页面
        switch_exam.setOnMouseClicked(e -> {
            try{
                primaryStage.setScene(examScene.setTrans(manager, primaryStage, scene));
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        // 进入转部门页面按钮
        final Button switch_trans = new Button("进入转部门页面");
        switch_trans.setLayoutX(900);
        switch_trans.setLayoutY(118);
        switch_trans.setFont(button);
        root.getChildren().addAll(switch_trans);
        // 切换到转部门页面
        switch_trans.setOnMouseClicked(e -> {
            try{
                    primaryStage.setScene(transScene.setTrans(manager, primaryStage, scene));
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        // 执行分配课程操作
        final Label course_label1 = new Label("课程号");
        course_label1.setLayoutX(300);//380
        course_label1.setLayoutY(480);
        course_label1.setFont(area);
        final TextArea course_area = new TextArea();
        course_area.setLayoutX(370);//部门号480
        course_area.setLayoutY(480);
        course_area.setPrefWidth(150);
        course_area.setPrefHeight(10);
        course_area.setWrapText(true);

        final Label id_label1 = new Label("员工号");
        id_label1.setLayoutX(50);
        id_label1.setLayoutY(480);
        id_label1.setFont(area);
        final TextArea id_area = new TextArea();
        id_area.setLayoutX(100);//员工号/员工姓名
        id_area.setLayoutY(480);
        id_area.setPrefWidth(150);
        id_area.setPrefHeight(10);
        id_area.setWrapText(true);


        // 分配培训课程按钮
        final Button add_course = new Button("分配");
        add_course.setLayoutX(600);
        add_course.setLayoutY(480);
        add_course.setFont(button);
        root.getChildren().addAll(add_course);

        root.getChildren().addAll(course_label1,id_label1,id_area,course_area);

        final TextArea manage_area = new TextArea();
        manage_area.setLayoutX(50);
        manage_area.setLayoutY(155);
        manage_area.setPrefWidth(1100);
        manage_area.setPrefHeight(250);
        manage_area.setWrapText(true);
        final Button manage_query = new Button("查询");
        manage_query.setLayoutX(180);
        manage_query.setLayoutY(118);
        manage_query.setFont(button);
        root.getChildren().addAll(manage_label, manage_area, manage_query);

        manage_query.setOnMouseClicked(e -> {
            try{
                List<Employee> managedEmployee = managerOp.findManagedEmployee(manager.getDept());
                System.out.println("部门"+manager.getDept());
                if (managedEmployee == null){
                    new Alert(Alert.AlertType.NONE, "没有查询到结果", new ButtonType[]{ButtonType.CLOSE}).show();
                }else{
                    StringBuilder showString = new StringBuilder("员工号 员工名 性别 入职时间 地址 联系电话 邮箱地址 所在部门\n");
                    for(Employee employee : managedEmployee){
                        showString.append(
                                employee.getId()).append("  ")
                                .append(employee.getName()).append("  ")
                                .append(employee.getGender()).append("  ")
                                .append(employee.getAge()).append("  ")
                                .append(employee.getTime()).append("  ")
                                .append(employee.getAddress()).append("  ")
                                .append(employee.getTelephone()).append("  ")
                                .append(employee.getEmail()).append("  ")
                                .append(departmentOp.getDeptNameById(employee.getDept()))
                                .append("\n");
                        List<Takes> takesList = employeeOp.getTakes(employee.getId());
                        showString.append("课程号 课程名 导师 课程类型 课程内容 结课状态\n");
                        for(Takes takes : takesList){
                            System.out.println(takes);
                            showString.append(takes.getCourse_id()).append("  ")
                                    .append(takes.getNumber()).append("  ")
                                    .append(takes.getState()).append("  ")
                                    .append(takes.getTime()).append("  ")
                                    .append("\n");
                        }
                    }
                    manage_area.setText(showString.toString());
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }

        });


//        为员工分配课程
        add_course.setOnMouseClicked(e -> {
            try{
                String employeeId = id_area.getText();
                String courseId = course_area.getText();
                System.out.println("employeeId"+employeeId);
                System.out.println("courseId"+courseId);
                managerOp.addCourseById(Long.parseLong(courseId), Long.parseLong(employeeId));
                new Alert(Alert.AlertType.NONE, "分配课程成功", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });
        return scene;
    }


}
