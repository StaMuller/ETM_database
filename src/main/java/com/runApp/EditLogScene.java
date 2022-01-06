package com.runApp;

import com.bean.Course;
import com.bean.Log;
import com.operation.AdministratorOp;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import java.util.List;

public class EditLogScene {

    AdministratorOp administratorOp = new AdministratorOp();

    public Scene setLog(Stage primaryStage, Scene primaryScene){
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);
        scene.setFill(Paint.valueOf("LightGrey"));

        // 返回主页面按钮
        Font button = new Font("楷体", 13);
        final Button back = new Button("退出");
        back.setLayoutX(1130);
        back.setLayoutY(30);
        back.setMinHeight(30);
        back.setMinWidth(40);
        back.setFont(button);
        root.getChildren().add(back);
        back.setOnMouseClicked(e -> primaryStage.setScene(primaryScene));

        // 设置标题
        Font title = new Font("FangSong", 30);
        final Label title_label = new Label("管理所有日志信息（系统管理员权限）" );
        title_label.setLayoutX(350);
        title_label.setLayoutY(30);
        title_label.setFont(title);
        root.getChildren().add(title_label);

        // 管理课程号不能更改
        List<Log> allLog = administratorOp.queryAllLog();
        Font area = new Font("楷体", 15);
        final Label info_label = new Label("日志信息");
        info_label.setLayoutX(50);
        info_label.setLayoutY(120);
        info_label.setFont(area);
        root.getChildren().add(info_label);
        TableView<Log> Log = new TableView<>();
        Log.setPrefWidth(760);
        Log.setPrefHeight(280);
        ObservableList<Log> data = FXCollections.observableArrayList();
        data.addAll(allLog);
        Log.setEditable(true);
        Log.setLayoutX(150);
        Log.setLayoutY(120);
        // 设置列
        TableColumn<Log, Integer> tableColumnID = new TableColumn<>("操作编号");
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("log_id"));
        tableColumnID.setMinWidth(150);
        TableColumn<Log, Long> tableColumnName = new TableColumn<>("操作人员员工号");
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        tableColumnName.setMinWidth(150);
        TableColumn<Log, String> tableColumnOperation = new TableColumn<>("操作");
        tableColumnOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));
        tableColumnOperation.setMinWidth(300);
        TableColumn<Log, String> tableColumnTime = new TableColumn<>("操作时间");
        tableColumnTime.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnTime.setMinWidth(150);
        Log.getColumns().addAll(tableColumnID, tableColumnName, tableColumnOperation, tableColumnTime);
        Log.setItems(data);
        root.getChildren().add(Log);

        // 增加日志
        TableView<Log> newLog = new TableView<>();
        newLog.setFixedCellSize(25);
        newLog.prefHeightProperty().bind(newLog.fixedCellSizeProperty().multiply(Bindings.size(newLog.getItems()).add(1.8)));
        newLog.minHeightProperty().bind(newLog.prefHeightProperty());
        newLog.maxHeightProperty().bind(newLog.prefHeightProperty());
        newLog.setPrefWidth(760);
        newLog.setFixedCellSize(40);
        ObservableList<Log> newData = FXCollections.observableArrayList();
        Log tempLog = new Log();
        newData.addAll(tempLog);
        newLog.setEditable(true);
        newLog.setLayoutX(150);
        newLog.setLayoutY(450);
        // 设置列
        TableColumn<Log, Long> newTableColumnName = new TableColumn<>("操作人员员工号");
        newTableColumnName.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        newTableColumnName.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        newTableColumnName.setOnEditCommit(
                (TableColumn.CellEditEvent<Log, Long> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setEmployee_id((t.getNewValue())));
        newTableColumnName.setMinWidth(100);
        TableColumn<Log, String> newTableColumnOperation = new TableColumn<>("操作");
        newTableColumnOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));
        newTableColumnOperation.setCellFactory(TextFieldTableCell.forTableColumn());
        newTableColumnOperation.setOnEditCommit(
                (TableColumn.CellEditEvent<Log, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setOperation(t.getNewValue()));
        newTableColumnOperation.setMinWidth(650);
        newLog.setItems(newData);
        newLog.getColumns().addAll(newTableColumnName, newTableColumnOperation);
        root.getChildren().add(newLog);
        final Button add = new Button("添加新日志信息");
        add.setLayoutX(1000);
        add.setLayoutY(450);
        add.setMinHeight(30);
        add.setMinWidth(125);
        add.setFont(button);
        root.getChildren().add(add);
        add.setOnMouseClicked(e -> {
            Log log = newData.get(0);
            if(administratorOp.addLog(log.getEmployee_id(), log.getOperation())){
                allLog.clear();
                allLog.addAll(administratorOp.queryAllLog());
                data.clear();
                data.addAll(allLog);
                Log.setItems(data);
                new Alert(Alert.AlertType.NONE, "添加成功", new ButtonType[]{ButtonType.CLOSE}).show();
            }else{
                new Alert(Alert.AlertType.NONE, "添加失败", new ButtonType[]{ButtonType.CLOSE}).show();
            }
        });
        return scene;
    }

}
