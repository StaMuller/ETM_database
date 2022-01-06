package com.runApp;

import com.bean.Employee;
import com.bean.Takes;
import com.operation.CourseOp;
import com.operation.DepartmentOp;
import com.operation.EmployeeOp;
import com.operation.ManagerOp;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

public class ExamScene {

    EmployeeOp employeeOp = new EmployeeOp();
    DepartmentOp departmentOp = new DepartmentOp();
    ManagerOp managerOp = new ManagerOp();
    CourseOp courseOp = new CourseOp();

    public Scene setTrans(Employee manager, Stage primaryStage, Scene primaryScene){
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

        // 显示框
        final TextArea manage_area = new TextArea();
        manage_area.setLayoutX(50);
        manage_area.setLayoutY(155);
        manage_area.setPrefWidth(1100);
        manage_area.setPrefHeight(250);
        manage_area.setWrapText(true);

        root.getChildren().addAll(manage_area);

        // 根据课程查找&根据考试的通过次数查找
        final Label course_label = new Label("课程号");
        course_label.setLayoutX(50);
        course_label.setLayoutY(113);
        course_label.setFont(area);
        final TextArea course_area = new TextArea();
        course_area.setLayoutX(100);
        course_area.setLayoutY(113);
        course_area.setPrefWidth(150);
        course_area.setPrefHeight(10);
        course_area.setWrapText(true);
        final Label num_label = new Label("未通过次数");
        num_label.setLayoutX(280);
        num_label.setLayoutY(113);
        num_label.setFont(area);
        final TextArea num_area = new TextArea();
        num_area.setLayoutX(370);
        num_area.setLayoutY(113);
        num_area.setPrefWidth(150);
        num_area.setPrefHeight(10);
        num_area.setWrapText(true);
        final Button course_query = new Button("查询");
        course_query.setLayoutX(580);
        course_query.setLayoutY(113);
        course_query.setFont(button);
        root.getChildren().addAll(course_label,course_area,num_label,num_area,course_query);
        course_query.setOnMouseClicked(e -> {
            try{
                String courseId = course_area.getText();
                String num = num_area.getText();
                if(num.equals("")){
                    if(courseOp.getCourseById(Long.parseLong(courseId)) == null){
                        new Alert(Alert.AlertType.NONE, "课程号错误", new ButtonType[]{ButtonType.CLOSE}).show();
                    }else{
                        List<Takes> takesList = managerOp.queryTakesOfCourse(Long.parseLong(courseId));
                        if(takesList.size() != 0){
                            StringBuilder showString = new StringBuilder();
                            showString.append(String.format("%-12s","课程号")).append("\t")
                                    .append(String.format("%-18s","课程名")).append("\t")
                                    .append(String.format("%-18s","员工号")).append("\t")
                                    .append(String.format("%-20s","员工名")).append("\t")
                                    .append(String.format("%-12s","考试成绩")).append("\t").append("\n");
                            for(Takes takes : takesList){
                                showString.append(String.format("%-12s",takes.getCourse_id())).append("\t")
                                        .append(String.format("%-18s",courseOp.getCourseById((Long) takes.getCourse_id()).getCourse_name())).append("\t")
                                        .append(String.format("%-18s",takes.getEmployee_id())).append("\t")
                                        .append(String.format("%-20s",(employeeOp.getEmployeeById((Long)takes.getEmployee_id())).getName())).append("\t")
                                        .append(String.format("%-12s",takes.getNumber())).append("\t")
                                        .append("\n");
                            }
                            manage_area.setText(showString.toString());
                        }else{
                            manage_area.setText("无该课程相关培训信息");
                        }
                    }
                }else{
                    List<HashMap<String, Object>> takesList = managerOp.queryNotPassed(Long.parseLong(courseId), Integer.parseInt(num));
                    if(takesList.size() == 0){
                        manage_area.setText("无相关信息");
                    }else{
                        StringBuilder showString = new StringBuilder();
                        showString.append(String.format("%-12s","课程号")).append("\t")
                                .append(String.format("%-18s","课程名")).append("\t")
                                .append(String.format("%-18s","员工号")).append("\t")
                                .append(String.format("%-20s","员工名")).append("\t")
                                .append(String.format("%-12s","未通过次数")).append("\t").append("\n");
                        for(HashMap<String, Object> takes : takesList){
                            showString.append(String.format("%-12s",takes.get("course_id"))).append("\t")
                                    .append(String.format("%-18s",courseOp.getCourseById((Long) takes.get("course_id")).getCourse_name())).append("\t")
                                    .append(String.format("%-18s",takes.get("employee_id"))).append("\t")
                                    .append(String.format("%-20s",(employeeOp.getEmployeeById((Long)takes.get("employee_id"))).getName())).append("\t")
                                    .append(String.format("%-12s",takes.get("count(id)"))).append("\t")
                                    .append("\n");
                        }
                        manage_area.setText(showString.toString());
                    }
                }
            } catch (NumberFormatException exception){
                exception.printStackTrace();
                new Alert(Alert.AlertType.NONE, "请填写相应课程号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        // 查看所有通过的
        final Button passed_query = new Button("查看通过的所有成绩");
        passed_query.setLayoutX(800);
        passed_query.setLayoutY(118);
        passed_query.setFont(button);

        root.getChildren().addAll(passed_query);

        // queryPassedTakes
        passed_query.setOnMouseClicked(e -> {
            try{
                List<Takes> takesList = managerOp.queryPassedTakes();
                StringBuilder showString = new StringBuilder();
                showString.append(String.format("%-12s","课程号")).append("\t")
                        .append(String.format("%-18s","课程名")).append("\t")
                        .append(String.format("%-18s","员工号")).append("\t")
                        .append(String.format("%-20s","员工名")).append("\t")
                        .append(String.format("%-12s","考试成绩")).append("\t").append("\n");
                for(Takes takes : takesList){
                    showString.append(String.format("%-12s",takes.getCourse_id())).append("\t")
                            .append(String.format("%-18s",courseOp.getCourseById(takes.getCourse_id()).getCourse_name())).append("\t")
                            .append(String.format("%-18s",takes.getEmployee_id())).append("\t")
                            .append(String.format("%-20s",(employeeOp.getEmployeeById((Long)takes.getEmployee_id())).getName())).append("\t")
                            .append(String.format("%-12s",takes.getNumber())).append("\t")
                            .append("\n");
                }
                manage_area.setText(showString.toString());
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        // 根据姓名或员工号查询员工的培训成绩
        final Label id_label = new Label("员工号/姓名");
        id_label.setLayoutX(50);
        id_label.setLayoutY(480);
        id_label.setFont(area);
        final TextArea id_area = new TextArea();
        id_area.setLayoutX(200);
        id_area.setLayoutY(480);
        id_area.setPrefWidth(150);
        id_area.setPrefHeight(10);
        id_area.setWrapText(true);
        final Button score_query = new Button("查询成绩");
        score_query.setLayoutX(380);
        score_query.setLayoutY(480);
        score_query.setFont(button);
        root.getChildren().addAll(score_query,id_label,id_area);
        score_query.setOnMouseClicked(e -> {
            try{
                String employeeId = id_area.getText();
                if(employeeId.charAt(0) >= '0' && employeeId.charAt(0) <= '9'){
                    // 员工号
                    if(employeeOp.getEmployeeById(Long.parseLong(employeeId)) == null){
                        new Alert(Alert.AlertType.NONE, "该员工不存在", new ButtonType[]{ButtonType.CLOSE}).show();
                    }else{
                        List<Takes> takesList = managerOp.queryTakesById(Long.parseLong(employeeId));
                        StringBuilder showString = new StringBuilder();
                        showString.append(String.format("%-12s","课程号")).append("\t")
                                .append(String.format("%-18s","课程名")).append("\t")
                                .append(String.format("%-18s","员工号")).append("\t")
                                .append(String.format("%-20s","员工名")).append("\t")
                                .append(String.format("%-8s","考试成绩")).append("\t")
                                .append(String.format("%-12s","通过状态")).append("\t")
                                .append(String.format("%-20s","考试时间")).append("\t")
                                .append("\n");
                        for(Takes takes : takesList){
                            showString.append(String.format("%-12s",takes.getCourse_id())).append("\t")
                                    .append(String.format("%-18s",courseOp.getCourseById(takes.getCourse_id()).getCourse_name())).append("\t")
                                    .append(String.format("%-18s",takes.getEmployee_id())).append("\t")
                                    .append(String.format("%-20s",(employeeOp.getEmployeeById(takes.getEmployee_id())).getName())).append("\t")
                                    .append(String.format("%-20s",takes.getNumber())).append("\t");
                            if (takes.getState()==null){
                                showString.append(String.format("%-8s","未考试"));
                            }else{
                                showString.append(String.format("%-8s",takes.getState())).append("\t");
                            }
                            if(takes.getTime()==null){
                                showString.append(String.format("%-20s","")).append("\t").append("\n");
                            }else{
                                showString.append(String.format("%-20s","")).append(takes.getTime()).append("\n");
                            }
                        }
                        manage_area.setText(showString.toString());
                    }
                }else{
                    // 员工姓名
                    List<Takes> takesList = managerOp.queryTakesByName(employeeId);
                    StringBuilder showString = new StringBuilder();
                    showString.append(String.format("%-12s","课程号")).append("\t")
                            .append(String.format("%-18s","课程名")).append("\t")
                            .append(String.format("%-18s","员工号")).append("\t")
                            .append(String.format("%-20s","员工名")).append("\t")
                            .append(String.format("%-8s","考试成绩")).append("\t")
                            .append(String.format("%-12s","通过状态")).append("\t")
                            .append(String.format("%-20s","考试时间")).append("\t")
                            .append("\n");
                    for(Takes takes : takesList){
                        showString.append(String.format("%-12s",takes.getCourse_id())).append("\t")
                                .append(String.format("%-18s",courseOp.getCourseById(takes.getCourse_id()).getCourse_name())).append("\t")
                                .append(String.format("%-18s",takes.getEmployee_id())).append("\t")
                                .append(String.format("%-20s",(employeeOp.getEmployeeById(takes.getEmployee_id())).getName())).append("\t")
                                .append(String.format("%-20s",takes.getNumber())).append("\t");
                        if (takes.getState()==null){
                            showString.append(String.format("%-8s","未考试"));
                        }else{
                            showString.append(String.format("%-8s",takes.getState())).append("\t");
                        }
                        if(takes.getTime()==null){
                            showString.append(String.format("%-20s","")).append("\t").append("\n");
                        }else{
                            showString.append(String.format("%-20s","")).append(takes.getTime()).append("\n");
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
        return scene;
    }


}
