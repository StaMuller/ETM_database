package com.runApp;

import com.bean.Course;
import com.bean.Employee;
import com.bean.Takes;
import com.operation.AdministratorOp;
import com.operation.DepartmentOp;
import com.operation.EmployeeOp;
import com.operation.ManagerOp;
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
    AdministratorOp administratorOp = new AdministratorOp();

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

        // 1）查询部门的员工信息
        final Label manage_label = new Label("部门的员工信息");
        manage_label.setLayoutX(50);
        manage_label.setLayoutY(120);
        manage_label.setFont(area);
        final TextArea manage_area = new TextArea();
        manage_area.setLayoutX(50);
        manage_area.setLayoutY(155);
        manage_area.setPrefWidth(1100);
        manage_area.setPrefHeight(250);
        manage_area.setWrapText(true);
        final Button manage_query = new Button("查询员工基本信息");
        manage_query.setLayoutX(180);
        manage_query.setLayoutY(118);
        manage_query.setFont(button);
        final Button course_query = new Button("查询员工课程信息");
        course_query.setLayoutX(310);
        course_query.setLayoutY(118);
        course_query.setFont(button);
        root.getChildren().addAll(manage_label, manage_area, manage_query,course_query);

        manage_query.setOnMouseClicked(e -> {
            try{
                List<Employee> managedEmployee = managerOp.findManagedEmployee(manager.getDept());
                if (managedEmployee == null){
                    new Alert(Alert.AlertType.NONE, "没有查询到结果", new ButtonType[]{ButtonType.CLOSE}).show();
                }else{
                    StringBuilder showString = new StringBuilder();
                    showString.append(String.format("%-18s","员工号")).append("\t")
                            .append(String.format("%-20s","员工名")).append("\t")
                            .append(String.format("%-8s","性别")).append("\t")
                            .append(String.format("%-4s","年龄")).append("\t")
                            .append(String.format("%-45s","入职时间")).append("\t")
                            .append(String.format("%-20s","地址")).append("\t")
                            .append(String.format("%-30s","联系电话")).append("\t")
                            .append(String.format("%-40s","邮箱地址")).append("\t")
                            .append(String.format("%-6s","所在部门")).append("\t").append("\n");


                    for(Employee employee : managedEmployee) {
                        showString.append(String.format("%-18s", employee.getId())).append("\t")
                                .append(String.format("%-20s",employee.getName())).append("\t")
                                .append(String.format("%-8s",employee.getGender())).append("\t")
                                .append(String.format("%-4s",employee.getAge())).append("\t")
                                .append(String.format("%-45s",employee.getTime())).append("\t")
                                .append(String.format("%-20s",employee.getAddress())).append("\t")
                                .append(String.format("%-30s",employee.getTelephone())).append("\t")
                                .append(String.format("%-40s",employee.getEmail())).append("\t")
                                .append(String.format("%-6s",departmentOp.getDeptNameById(employee.getDept()))).append("\n");
                    }
                    manage_area.setText(String.valueOf(showString));
                    }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }

        });

        course_query.setOnMouseClicked(e->{
            try{
                List<Employee> managedEmployee = managerOp.findManagedEmployee(manager.getDept());
                if (managedEmployee == null){
                    new Alert(Alert.AlertType.NONE, "没有查询到结果", new ButtonType[]{ButtonType.CLOSE}).show();
                }else{
                    StringBuilder showString = new StringBuilder();
                    showString.append(String.format("%-18s","员工号")).append("\t")
                            .append(String.format("%-20s","员工名")).append("\t")
                            .append(String.format("%-12s","课程号")).append("\t")
                            .append(String.format("%-18s","课程名")).append("\t")
                            .append(String.format("%-24s","导师")).append("\t")
                            .append(String.format("%-12s","课程类型")).append("\t")
                            .append(String.format("%-12s","课程内容")).append("\t")
                            .append(String.format("%-12s","结课状态")).append("\t").append("\n");

                    for(Employee employee : managedEmployee) {
                        List<Course> courseList = employeeOp.findCourse(employee.getId());
                        if(courseList.size() != 0){
                            for(Course takes : courseList){
                                showString.append(String.format("%-18s", employee.getId())).append("\t")
                                        .append(String.format("%-20s",employee.getName())).append("\t")
                                        .append(String.format("%-18s",takes.getCourse_id())).append("\t")
                                        .append(String.format("%-20s",takes.getCourse_name())).append("\t")
                                        .append(String.format("%-18s",takes.getInstructor_id())).append("\t")
                                        .append(String.format("%-18s",takes.getType())).append("\t")
                                        .append(String.format("%-16s",takes.getContent())).append("\t");
                                if(takes.getState() == 0){
                                    showString.append(String.format("%-18s","未结课")).append("\t");
                                }else{
                                    showString.append(String.format("%-18s","已结课")).append("\t");
                                }
                                showString.append("\n");
                            }
                        }
                        showString.append("\n");
                    }
                    manage_area.setText(String.valueOf(showString));
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        // 2）执行分配课程操作
        final Label course_label1 = new Label("课程号");
        course_label1.setLayoutX(450);
        course_label1.setLayoutY(480);
        course_label1.setFont(area);
        final TextArea course_area = new TextArea();
        course_area.setLayoutX(520);
        course_area.setLayoutY(480);
        course_area.setPrefWidth(150);
        course_area.setPrefHeight(10);
        course_area.setWrapText(true);
        final Label id_label1 = new Label("员工号/员工姓名");
        id_label1.setLayoutX(50);
        id_label1.setLayoutY(480);
        id_label1.setFont(area);
        final TextArea id_area = new TextArea();
        id_area.setLayoutX(200);
        id_area.setLayoutY(480);
        id_area.setPrefWidth(150);
        id_area.setPrefHeight(10);
        id_area.setWrapText(true);
        final Button add_course = new Button("分配");
        add_course.setLayoutX(700);
        add_course.setLayoutY(480);
        add_course.setFont(button);
        root.getChildren().addAll(add_course);

        root.getChildren().addAll(course_label1,id_label1,id_area,course_area);

        // 为员工分配课程
        add_course.setOnMouseClicked(e -> {
            try{
                String employeeId = id_area.getText();
                String courseId = course_area.getText();
                if(employeeId.charAt(0) >= '0' && employeeId.charAt(0) <= '9'){
                    // 员工号
                    if(employeeOp.getEmployeeById(Long.parseLong(employeeId)).getDept() != manager.getDept()){
                        new Alert(Alert.AlertType.NONE, "无权限操作", new ButtonType[]{ButtonType.CLOSE}).show();
                    }else{
                        if(managerOp.addCourseById(Long.parseLong(courseId), Long.parseLong(employeeId))){
                            new Alert(Alert.AlertType.NONE, "分配课程成功", new ButtonType[]{ButtonType.CLOSE}).show();
                            administratorOp.addLog(manager.getId(),"分配课程"+courseId+"给员工"+employeeId);
                        }else{
                            new Alert(Alert.AlertType.NONE, "该员工号或课程号不存在", new ButtonType[]{ButtonType.CLOSE}).show();
                        }
                    }
                }else{
                    // 员工姓名
                    List<Employee> employeeList = employeeOp.getEmployeeByName(employeeId);
                    if(employeeList.size() > 1){
                        new Alert(Alert.AlertType.NONE, "查询到多员工，请指定员工号", new ButtonType[]{ButtonType.CLOSE}).show();
                    }else{
                        Employee employee = employeeList.get(0);
                        if(employee.getDept() != manager.getDept()){
                            new Alert(Alert.AlertType.NONE, "无权限操作", new ButtonType[]{ButtonType.CLOSE}).show();
                        }else{
                            if(managerOp.addCourseByName(Long.parseLong(courseId), employeeId) == 1){
                                new Alert(Alert.AlertType.NONE, "分配课程成功", new ButtonType[]{ButtonType.CLOSE}).show();
                            }else if(managerOp.addCourseByName(Long.parseLong(courseId), employeeId) == 0){
                                new Alert(Alert.AlertType.NONE, "该员工或课程号不存在", new ButtonType[]{ButtonType.CLOSE}).show();
                            }else{
                                new Alert(Alert.AlertType.NONE, "查询到多员工，请指定员工号", new ButtonType[]{ButtonType.CLOSE}).show();
                            }
                        }
                    }
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号或员工姓名", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

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

        return scene;
    }

}
