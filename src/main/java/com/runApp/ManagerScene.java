package com.runApp;

import com.bean.Employee;
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
//        final TextArea manage_area = new TextArea();
        final Label manage_area = new Label();
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
//                if(manager == null){
//                    new Alert(Alert.AlertType.NONE, "该员工号无法使用该权限", new ButtonType[]{ButtonType.CLOSE}).show();
//                }else{
//                    primaryStage.setScene(managerScene.setManager(manager, primaryStage, scene));
//                }
                StringBuilder res = new StringBuilder();
                for (Employee i : managedEmployee){
//                    manage_area.set(i.getName());
//                    manage_area.appendText(i.getName());
                    res.append(i);
                }
                manage_area.setText(String.valueOf(res));

//                root.getChildren().addAll(manage_label, manage_area, manage_query);

            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }

        });



        return scene;
    }


}
