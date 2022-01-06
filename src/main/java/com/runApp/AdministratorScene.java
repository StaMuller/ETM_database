package com.runApp;

import com.bean.Employee;
import com.operation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdministratorScene {

    EditEmployeeScene editEmployeeScene = new EditEmployeeScene();
    EditCourseScene editCourseScene = new EditCourseScene();
    EditLogScene editLogScene = new EditLogScene();

    public Scene setAdministrator(Employee administrator, Stage primaryStage, Scene primaryScene){
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
        final Label title_label = new Label(administrator.getName() + "，欢迎来到员工管理系统（部门主管权限）");
        title_label.setLayoutX(350);
        title_label.setLayoutY(30);
        title_label.setFont(title);
        root.getChildren().add(title_label);

        final Button employeeInfoButton = new Button("管理员工相关信息");
        employeeInfoButton.setLayoutX(250);
        employeeInfoButton.setLayoutY(300);
        employeeInfoButton.setPrefHeight(50);
        employeeInfoButton.setPrefWidth(150);
        employeeInfoButton.setFont(button);
        root.getChildren().add(employeeInfoButton);
        employeeInfoButton.setOnMouseClicked(e ->{
            try{
                primaryStage.setScene(editEmployeeScene.setEmployee(administrator,primaryStage, scene));
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });
        final Button courseInfoButton = new Button("管理课程相关信息");
        courseInfoButton.setLayoutX(500);
        courseInfoButton.setLayoutY(300);
        courseInfoButton.setPrefHeight(50);
        courseInfoButton.setPrefWidth(150);
        courseInfoButton.setFont(button);
        root.getChildren().add(courseInfoButton);
        courseInfoButton.setOnMouseClicked(e ->{
            try{
                primaryStage.setScene(editCourseScene.setCourse(administrator,primaryStage, scene));
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });
        final Button logInfoButton = new Button("管理日志信息");
        logInfoButton.setLayoutX(750);
        logInfoButton.setLayoutY(300);
        logInfoButton.setPrefHeight(50);
        logInfoButton.setPrefWidth(150);
        logInfoButton.setFont(button);
        root.getChildren().add(logInfoButton);
        logInfoButton.setOnMouseClicked(e ->{
            try{
                primaryStage.setScene(editLogScene.setLog(primaryStage, scene));
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });
        return scene;
    }
}
