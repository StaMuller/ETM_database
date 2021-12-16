package com.runApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

// 主界面
public class MainScene extends Application {

    InstructorScene instructorScene = new InstructorScene();

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);
        scene.setFill(Paint.valueOf("LightGrey"));

        primaryStage.setTitle("Employee Training System");
        primaryStage.setScene(scene);
        primaryStage.show();

        Font button = new Font("楷体", 13);
        // 设置跳转按钮
        final Button manager = new Button("部门主管");
        manager.setLayoutX(400);
        manager.setLayoutY(300);
        manager.setMinHeight(30);
        manager.setMinWidth(40);
        manager.setFont(button);
        root.getChildren().add(manager);
        final Button instructor = new Button("教员");
        instructor.setLayoutX(200);
        instructor.setLayoutY(300);
        instructor.setMinHeight(30);
        instructor.setMinWidth(40);
        instructor.setFont(button);
        root.getChildren().add(instructor);

        instructor.setOnMouseClicked(e ->{
            try{
                primaryStage.setScene(instructorScene.setInstructor(primaryStage, scene));
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });
    }

}
