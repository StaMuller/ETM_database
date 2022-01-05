package com.runApp;

import com.bean.Employee;
import com.operation.AdministratorOp;
import com.operation.EmployeeOp;
import com.operation.InstructorOp;
import com.operation.ManagerOp;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.text.Font;

import java.util.List;

// 主界面
public class MainScene extends Application {

    InstructorScene instructorScene = new InstructorScene();
    EmployeeScene employeeScene = new EmployeeScene();
    ManagerScene managerScene = new ManagerScene();
    AdministratorScene administratorScene = new AdministratorScene();

    InstructorOp instructorOp = new InstructorOp();
    EmployeeOp employeeOp = new EmployeeOp();
    ManagerOp managerOp = new ManagerOp();
    AdministratorOp administratorOp = new AdministratorOp();
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);
        scene.setFill(Paint.valueOf("LightGrey"));

        primaryStage.setTitle("Employee Training System");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 设置标题
        Font title = new Font("FangSong", 30);
        final Label title_label = new Label("员工培训管理系统");
        title_label.setLayoutX(470);
        title_label.setLayoutY(30);
        title_label.setFont(title);
        root.getChildren().add(title_label);

        Font area = new Font("楷体", 15);
        // 获取教员教工号
        final Label id_label = new Label("员工号：");
        id_label.setLayoutX(480);
        id_label.setLayoutY(150);
        id_label.setFont(area);
        final TextArea id_area = new TextArea();
        id_area.setLayoutX(550);
        id_area.setLayoutY(150);
        id_area.setPrefWidth(150);
        id_area.setPrefHeight(5);
        id_area.setWrapText(true);
        root.getChildren().addAll(id_label, id_area);

        Font button = new Font("楷体", 16);
        // 设置跳转按钮

        final Button instructorButton = new Button("教员");
        instructorButton.setLayoutX(200);
        instructorButton.setLayoutY(300);
        instructorButton.setPrefHeight(30);
        instructorButton.setPrefWidth(120);
        instructorButton.setFont(button);
        root.getChildren().add(instructorButton);
        final Button managerButton = new Button("部门主管");
        managerButton.setLayoutX(400);
        managerButton.setLayoutY(300);
        managerButton.setPrefWidth(120);
        managerButton.setPrefHeight(30);
        managerButton.setFont(button);
        root.getChildren().add(managerButton);
        final Button employeeButton = new Button("员工");
        employeeButton.setLayoutX(600);
        employeeButton.setLayoutY(300);
        employeeButton.setPrefHeight(30);
        employeeButton.setPrefWidth(120);
        employeeButton.setFont(button);
        root.getChildren().add(employeeButton);
        final Button administratorButton = new Button("系统管理员");
        administratorButton.setLayoutX(800);
        administratorButton.setLayoutY(300);
        administratorButton.setPrefWidth(120);
        administratorButton.setPrefHeight(30);
        administratorButton.setFont(button);
        root.getChildren().add(administratorButton);

        instructorButton.setOnMouseClicked(e ->{
            try{
                Employee instructor = instructorOp.getInstructorById(Long.parseLong(id_area.getText()));
                if(instructor == null){
                    new Alert(Alert.AlertType.NONE, "该员工号无法使用该权限", new ButtonType[]{ButtonType.CLOSE}).show();
                }else{
                    primaryStage.setScene(instructorScene.setInstructor(instructor, primaryStage, scene));
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });
        employeeButton.setOnMouseClicked(e ->{
            try{
                Employee employee = employeeOp.getEmployeeById(Long.parseLong(id_area.getText()));
                if(employee == null){
                    new Alert(Alert.AlertType.NONE, "不存在该员工号", new ButtonType[]{ButtonType.CLOSE}).show();
                }else{
                    primaryStage.setScene(employeeScene.setEmployee(employee, primaryStage, scene));
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        managerButton.setOnMouseClicked(e ->{
            try{
                Employee manager = managerOp.getManagerById(Long.parseLong(id_area.getText()));
                if(manager == null){
                    new Alert(Alert.AlertType.NONE, "该员工号无法使用该权限", new ButtonType[]{ButtonType.CLOSE}).show();
                }else{
                    primaryStage.setScene(managerScene.setManager(manager, primaryStage, scene));
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });

        administratorButton.setOnMouseClicked(e ->{
            try{
                Employee administrator = administratorOp.getAdminById(Long.parseLong(id_area.getText()));
                if(administrator == null){
                    new Alert(Alert.AlertType.NONE, "该员工号无法使用该权限", new ButtonType[]{ButtonType.CLOSE}).show();
                }else{
                    primaryStage.setScene(administratorScene.setAdministrator(administrator, primaryStage, scene));
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });
    }

}
