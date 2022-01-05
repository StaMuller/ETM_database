package com.runApp;

import com.bean.Course;
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

public class EditCourseScene {

    AdministratorOp administratorOp = new AdministratorOp();

    public Scene setCourse(Stage primaryStage, Scene primaryScene){
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
        final Label title_label = new Label("管理所有课程信息（系统管理员权限）" );
        title_label.setLayoutX(350);
        title_label.setLayoutY(30);
        title_label.setFont(title);
        root.getChildren().add(title_label);

        // 管理课程号不能更改
        List<Course> allCourse = administratorOp.queryAllCourse();
        Font area = new Font("楷体", 15);
        final Label info_label = new Label("课程信息");
        info_label.setLayoutX(50);
        info_label.setLayoutY(120);
        info_label.setFont(area);
        root.getChildren().add(info_label);
        TableView<Course> course = new TableView<>();
        course.setPrefWidth(760);
        course.setPrefHeight(280);
        ObservableList<Course> data = FXCollections.observableArrayList();
        data.addAll(allCourse);
        course.setEditable(true);
        course.setLayoutX(150);
        course.setLayoutY(120);
        // 设置列
        TableColumn<Course, String> tableColumnID = new TableColumn<>("课程号");
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        tableColumnID.setMinWidth(100);
        TableColumn<Course, String> tableColumnName = new TableColumn<>("课程名");
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnName.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCourse_name(t.getNewValue()));
        tableColumnName.setMinWidth(150);
        TableColumn<Course, Long> tableColumnInstructor = new TableColumn<>("导师员工号");
        tableColumnInstructor.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
        tableColumnInstructor.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        tableColumnInstructor.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, Long> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setInstructor_id(t.getNewValue()));
        tableColumnInstructor.setMinWidth(100);
        TableColumn<Course, String> tableColumnType = new TableColumn<>("类型");
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnType.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnType.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setType(t.getNewValue()));
        tableColumnType.setMinWidth(150);
        TableColumn<Course, String> tableColumnContent = new TableColumn<>("内容");
        tableColumnContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        tableColumnContent.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnContent.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setContent(t.getNewValue()));
        tableColumnContent.setMinWidth(150);
        TableColumn<Course, Integer> tableColumnState = new TableColumn<>("结课状态");
        tableColumnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        tableColumnState.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnState.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, Integer> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setState(t.getNewValue()));
        tableColumnState.setMinWidth(50);
        course.setItems(data);
        course.getColumns().addAll(tableColumnID, tableColumnName, tableColumnInstructor,
                tableColumnContent, tableColumnType, tableColumnState);
        root.getChildren().add(course);
        final Button commit = new Button("修改提交");
        commit.setLayoutX(1000);
        commit.setLayoutY(120);
        commit.setMinHeight(30);
        commit.setMinWidth(40);
        commit.setFont(button);
        root.getChildren().add(commit);
        commit.setOnMouseClicked(e -> {
            for(Course courseTemp : data){
                Course temp = new Course();
                temp.setCourse_id(courseTemp.getCourse_id());
                temp.setCourse_name(courseTemp.getCourse_name());
                temp.setInstructor_id(courseTemp.getInstructor_id());
                temp.setType(courseTemp.getType());
                temp.setContent(courseTemp.getContent());
                temp.setState(courseTemp.getState());
                administratorOp.updateCourse(courseTemp);
            }
            new Alert(Alert.AlertType.NONE, "修改成功", new ButtonType[]{ButtonType.CLOSE}).show();
        });

        // 删除课程
        final TextArea delete_area = new TextArea();
        delete_area.setLayoutX(930);
        delete_area.setLayoutY(180);
        delete_area.setPrefWidth(100);
        delete_area.setPrefHeight(10);
        delete_area.setWrapText(true);
        final Button delete = new Button("删除该课程");
        delete.setLayoutX(1050);
        delete.setLayoutY(180);
        delete.setMinHeight(30);
        delete.setMinWidth(70);
        delete.setFont(button);
        root.getChildren().addAll(delete_area, delete);
        delete.setOnMouseClicked(e -> {
            if(administratorOp.deleteCourse(Long.parseLong(delete_area.getText()))){
                new Alert(Alert.AlertType.NONE, "删除成功", new ButtonType[]{ButtonType.CLOSE}).show();
            }else{
                new Alert(Alert.AlertType.NONE, "删除失败", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            allCourse.clear();
            allCourse.addAll(administratorOp.queryAllCourse());
            data.clear();
            data.addAll(allCourse);
            course.setItems(data);
        });

        // 增加课程
        TableView<Course> addCourse = new TableView<>();
        addCourse.setFixedCellSize(25);
        addCourse.prefHeightProperty().bind(addCourse.fixedCellSizeProperty().multiply(Bindings.size(addCourse.getItems()).add(1.7)));
        addCourse.minHeightProperty().bind(addCourse.prefHeightProperty());
        addCourse.maxHeightProperty().bind(addCourse.prefHeightProperty());
        addCourse.setPrefWidth(740);
        addCourse.setFixedCellSize(40);
        ObservableList<Course> newCourseData = FXCollections.observableArrayList();
        Course newCourse = new Course();
        newCourseData.add(newCourse);
        addCourse.setEditable(true);
        addCourse.setLayoutX(150);
        addCourse.setLayoutY(450);
        // 设置列
        TableColumn<Course, Long> newTableColumnID = new TableColumn<>("课程号");
        newTableColumnID.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        newTableColumnID.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        newTableColumnID.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, Long> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCourse_id(t.getNewValue()));
        newTableColumnID.setMinWidth(100);
        TableColumn<Course, String> newTableColumnName = new TableColumn<>("课程名");
        newTableColumnName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        newTableColumnName.setCellFactory(TextFieldTableCell.forTableColumn());
        newTableColumnName.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCourse_name(t.getNewValue()));
        newTableColumnName.setMinWidth(100);
        TableColumn<Course, Long> newTableColumnInstructor = new TableColumn<>("导师员工号");
        newTableColumnInstructor.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
        newTableColumnInstructor.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        newTableColumnInstructor.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, Long> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setInstructor_id(t.getNewValue()));
        newTableColumnInstructor.setMinWidth(100);
        TableColumn<Course, String> newTableColumnType = new TableColumn<>("类型");
        newTableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        newTableColumnType.setCellFactory(TextFieldTableCell.forTableColumn());
        newTableColumnType.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setType(t.getNewValue()));
        newTableColumnType.setMinWidth(150);
        TableColumn<Course, String> newTableColumnContent = new TableColumn<>("内容");
        newTableColumnContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        newTableColumnContent.setCellFactory(TextFieldTableCell.forTableColumn());
        newTableColumnContent.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setContent(t.getNewValue()));
        newTableColumnContent.setMinWidth(150);
        TableColumn<Course, Integer> newTableColumnState = new TableColumn<>("结课状态");
        newTableColumnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        newTableColumnState.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        newTableColumnState.setOnEditCommit(
                (TableColumn.CellEditEvent<Course, Integer> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setState(t.getNewValue()));
        newTableColumnState.setMinWidth(50);
        addCourse.getColumns().addAll(newTableColumnID, newTableColumnName, newTableColumnInstructor,
                newTableColumnType, newTableColumnContent, newTableColumnState);
        addCourse.setItems(newCourseData);
        root.getChildren().add(addCourse);
        final Button add = new Button("添加新课程信息");
        add.setLayoutX(1000);
        add.setLayoutY(450);
        add.setMinHeight(30);
        add.setMinWidth(130);
        add.setFont(button);
        root.getChildren().add(add);
        add.setOnMouseClicked(e -> {
            Course temp = newCourseData.get(0);
            if(administratorOp.addCourse(temp)){
                allCourse.clear();
                allCourse.addAll(administratorOp.queryAllCourse());
                data.clear();
                data.addAll(allCourse);
                course.setItems(data);
                new Alert(Alert.AlertType.NONE, "添加成功", new ButtonType[]{ButtonType.CLOSE}).show();
            }else{
                new Alert(Alert.AlertType.NONE, "添加失败", new ButtonType[]{ButtonType.CLOSE}).show();
            }
        });
        return scene;
    }

}
