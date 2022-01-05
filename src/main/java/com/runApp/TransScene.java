package com.runApp;

import com.bean.Course;
import com.bean.Employee;
import com.bean.Takes;
import com.operation.CourseOp;
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

public class TransScene {

    CourseOp courseOp = new CourseOp();
    EmployeeOp employeeOp = new EmployeeOp();
    DepartmentOp departmentOp = new DepartmentOp();
    ManagerOp managerOp = new ManagerOp();

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
        final TextArea manage_area = new TextArea();
        manage_area.setLayoutX(50);
        manage_area.setLayoutY(155);
        manage_area.setPrefWidth(1100);
        manage_area.setPrefHeight(250);
        manage_area.setWrapText(true);
        root.getChildren().addAll(manage_area);

        // 查询本部门符合转部门条件的员工
        final Button query_trans = new Button("查询本部门符合转部门条件的员工");
        query_trans.setLayoutX(50);
        query_trans.setLayoutY(100);
        query_trans.setFont(button);
        root.getChildren().addAll(query_trans);
        query_trans.setOnMouseClicked(e -> {
            try{
                List<Employee> employeeList = managerOp.queryTransEmployee(manager.getDept());
                if (employeeList == null){
                    manage_area.setText("本部门下无符合转部门条件的员工");
                }else{
                    StringBuilder showString = new StringBuilder("员工号 员工名 性别 入职时间 地址 联系电话 邮箱地址 所在部门\n");
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
                    manage_area.setText(showString.toString());
                }
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        // 查询转部门后要上的课
        final Label trans_label = new Label("员工号/姓名");
        trans_label.setLayoutX(330);
        trans_label.setLayoutY(100);
        trans_label.setFont(area);
        final TextArea trans_area = new TextArea();
        trans_area.setLayoutX(430);
        trans_area.setLayoutY(100);
        trans_area.setPrefWidth(150);
        trans_area.setPrefHeight(10);
        trans_area.setWrapText(true);
        final Label wanted_dept = new Label("意向部门");
        wanted_dept.setLayoutX(610);
        wanted_dept.setLayoutY(100);
        wanted_dept.setFont(area);
        final TextArea wanted_area = new TextArea();
        wanted_area.setLayoutX(690);
        wanted_area.setLayoutY(100);
        wanted_area.setPrefWidth(100);
        wanted_area.setPrefHeight(10);
        wanted_area.setWrapText(true);
        final Button query_course = new Button("查询需要培训的课程");
        query_course.setLayoutX(810);
        query_course.setLayoutY(100);
        query_course.setFont(button);
        root.getChildren().addAll(trans_label,trans_area,wanted_dept,wanted_area,query_course);
        query_course.setOnMouseClicked(e -> {
            try{
                String employeeId = trans_area.getText();
                String deptId = wanted_area.getText();
                if(employeeId.charAt(0) >= '0' && employeeId.charAt(0) <= '9'){
                    List<Course> courseList = managerOp.courseAfterTransById(Long.parseLong(employeeId),Integer.parseInt(deptId));
                    StringBuilder showString = new StringBuilder("课程号 课程名 \n");
                    for(Course course : courseList) {
                        showString.append(course.getCourse_id()).append("  ")
                                .append(courseOp.getCourseById(course.getCourse_id()).getCourse_name()).append("  ")
                                .append("\n");
                    }
                    manage_area.setText(showString.toString());
                }else{
                    StringBuilder showString = new StringBuilder("课程号 课程名 \n");
                    List<Employee> employeeList = employeeOp.getEmployeeByName(employeeId);
                    if(employeeList.size() > 1){
                        showString.append("\n");
                    }
                    for(Employee employee : employeeList){
                        if(employeeList.size() > 1){
                            showString.append(employee.getName()).append(" 的查询信息\n");
                        }
                        List<Course> courseList = managerOp.courseAfterTransById(employee.getId(),Integer.parseInt(deptId));
                        for(Course course : courseList) {
                            showString.append(course.getCourse_id()).append("  ")
                                    .append(courseOp.getCourseById(course.getCourse_id()).getCourse_name()).append("  ")
                                    .append("\n");
                        }
                    }
                    manage_area.setText(showString.toString());
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号/姓名", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        // 执行转部门操作
        final Label trans_label1 = new Label("部门号");
        trans_label1.setLayoutX(330);
        trans_label1.setLayoutY(480);
        trans_label1.setFont(area);
        final TextArea trans_area1 = new TextArea();
        trans_area1.setLayoutX(390);
        trans_area1.setLayoutY(480);
        trans_area1.setPrefWidth(100);
        trans_area1.setPrefHeight(10);
        trans_area1.setWrapText(true);
        final Label trans_label2 = new Label("员工号/姓名");
        trans_label2.setLayoutX(50);
        trans_label2.setLayoutY(480);
        trans_label2.setFont(area);
        final TextArea trans_area2 = new TextArea();
        trans_area2.setLayoutX(150);
        trans_area2.setLayoutY(480);
        trans_area2.setPrefWidth(150);
        trans_area2.setPrefHeight(10);
        trans_area2.setWrapText(true);
        final Button trans_op = new Button("转部门");
        trans_op.setLayoutX(500);
        trans_op.setLayoutY(480);
        trans_op.setFont(button);
        root.getChildren().addAll(trans_op);
        root.getChildren().addAll(trans_label1,trans_label2,trans_area2,trans_area1);

        trans_op.setOnMouseClicked(e -> {
            try{
                String employeeId = trans_area2.getText();
                String deptId = trans_area1.getText();
                if(employeeId.charAt(0) >= '0' && employeeId.charAt(0) <= '9'){
                    // 员工号
                    if(employeeOp.getEmployeeById(Long.parseLong(employeeId)).getDept() == manager.getDept()){
                        boolean flag = managerOp.transDeptById(Long.parseLong(employeeId),Integer.parseInt(deptId));
                        if (flag){
                            new Alert(Alert.AlertType.NONE, "转部门成功", new ButtonType[]{ButtonType.CLOSE}).show();
                        }else{
                            new Alert(Alert.AlertType.NONE, "转部门失败", new ButtonType[]{ButtonType.CLOSE}).show();
                        }
                    }else{
                        new Alert(Alert.AlertType.NONE, "无权限操作，该员工不属于您管理的部门", new ButtonType[]{ButtonType.CLOSE}).show();
                    }
                }else{
                    // 员工姓名
                    if(employeeOp.getEmployeeByName(employeeId).size() > 1){
                        new Alert(Alert.AlertType.NONE, "查询到多名员工，请具体指定员工号", new ButtonType[]{ButtonType.CLOSE}).show();
                    }else if(employeeOp.getEmployeeByName(employeeId).size() == 0){
                        new Alert(Alert.AlertType.NONE, "查无此人", new ButtonType[]{ButtonType.CLOSE}).show();
                    }else{
                        Employee employee = employeeOp.getEmployeeByName(employeeId).get(0);
                        if(employee.getDept() == manager.getDept()){
                            int flag = managerOp.transDeptByName(employeeId, Integer.parseInt(deptId));
                            if (flag == 0){
                                new Alert(Alert.AlertType.NONE, "转部门成功", new ButtonType[]{ButtonType.CLOSE}).show();
                            }else{
                                new Alert(Alert.AlertType.NONE, "该员工不符合转部门要求", new ButtonType[]{ButtonType.CLOSE}).show();
                            }
                        }else{
                            new Alert(Alert.AlertType.NONE, "无权限操作，该员工不属于您管理的部门", new ButtonType[]{ButtonType.CLOSE}).show();
                        }
                    }
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号/姓名", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        // 查询员工是否符合转部门要求
        final Label trans_label3 = new Label("员工号/姓名");
        trans_label3.setLayoutX(50);
        trans_label3.setLayoutY(430);
        trans_label3.setFont(area);
        final TextArea trans_area3 = new TextArea();
        trans_area3.setLayoutX(150);
        trans_area3.setLayoutY(430);
        trans_area3.setPrefWidth(150);
        trans_area3.setPrefHeight(10);
        trans_area3.setWrapText(true);
        final Button single_query = new Button("查询是否可以转部门");
        single_query.setLayoutX(350);
        single_query.setLayoutY(430);
        single_query.setFont(button);
        root.getChildren().addAll(trans_label3,trans_area3,single_query);
        single_query.setOnMouseClicked(e -> {
            try{
                String employeeId = trans_area3.getText();
                if(employeeId.charAt(0) >= '0' && employeeId.charAt(0) <= '9'){
                    // 员工号
                    if(managerOp.employeeTransById(Long.parseLong(employeeId))){
                        manage_area.setText(employeeOp.getEmployeeById(Long.parseLong(employeeId)).getName()
                                + " 符合转部门要求");
                    }else{
                        manage_area.setText(employeeOp.getEmployeeById(Long.parseLong(employeeId)).getName()
                                + " 不符合转部门要求");
                    }
                }else{
                    // 员工姓名
                    StringBuilder showString = new StringBuilder();
                    List<Employee> employeeList = employeeOp.getEmployeeByName(employeeId);
                    for(Employee employee : employeeList){
                        if(managerOp.employeeTransById(employee.getId())){
                            showString.append(employee.getName()).append(" 符合转部门要求\n");
                        }else{
                            showString.append(employee.getName()).append(" 不符合转部门要求\n");
                        }
                    }
                    manage_area.setText(showString.toString());
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号/姓名", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });


        return scene;
    }



}
