package com.runApp;

import com.bean.Instructor;
import com.operation.InstructorOp;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class InstructorScene {

    InstructorOp instructorOp = new InstructorOp();

    public Scene setInstructor(Stage primaryStage, Scene primaryScene) {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);
        scene.setFill(Paint.valueOf("LightGrey"));

        // 返回主页面按钮
        Font button = new Font("楷体", 13);
        final Button back = new Button("退出");
        back.setLayoutX(1150);
        back.setLayoutY(10);
        back.setMinHeight(30);
        back.setMinWidth(40);
        back.setFont(button);
        root.getChildren().add(back);
        back.setOnMouseClicked(e -> primaryStage.setScene(primaryScene));

        Font area = new Font("楷体", 15);
        // 获取教员教工号
        final Label id_label = new Label("员工号：");
        id_label.setLayoutX(50);
        id_label.setLayoutY(50);
        id_label.setFont(area);
        final TextArea id_area = new TextArea();
        id_area.setLayoutX(120);
        id_area.setLayoutY(50);
        id_area.setPrefWidth(150);
        id_area.setPrefHeight(5);
        id_area.setWrapText(true);
        root.getChildren().addAll(id_label, id_area);

        // 查询教授的员工信息
        final Label teach_label = new Label("教授的员工信息");
        teach_label.setLayoutX(50);
        teach_label.setLayoutY(120);
        teach_label.setFont(area);
        final TextArea teach_area = new TextArea();
        teach_area.setLayoutX(50);
        teach_area.setLayoutY(155);
        teach_area.setPrefWidth(400);
        teach_area.setPrefHeight(400);
        final Button teach_query = new Button("查询");
        teach_query.setLayoutX(180);
        teach_query.setLayoutY(118);
        teach_query.setMinHeight(30);
        teach_query.setMinWidth(40);
        teach_query.setFont(button);
        root.getChildren().addAll(teach_label, teach_area, teach_query);

        // 查询按钮触发
        teach_query.setOnMouseClicked(e -> {
            int id = 0;
            try{
                id = Integer.parseInt(id_area.getText());
            }catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "教员ID错误", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            List<Instructor> instructorList = instructorOp.getAllInstructor();
            StringBuilder text = new StringBuilder();
            for(Instructor instructor : instructorList){
                text.append(instructor.getId()).append("\n");
            }
            teach_area.setText(text.toString());
        });

        return scene;
    }

}
