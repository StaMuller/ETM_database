package com.runApp;

import com.bean.Administrator;
import com.bean.Course;
import com.bean.Employee;
import com.operation.AdministratorOp;
import com.operation.CourseOp;
import com.operation.DepartmentOp;
import com.operation.EmployeeOp;
import com.runApp.Container.CourseView;
import com.runApp.Container.CourseView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;


public class CourseScene {

    AdministratorOp administratorOp = new AdministratorOp();
    EmployeeOp employeeOp = new EmployeeOp();
    DepartmentOp departmentOp = new DepartmentOp();
    CourseOp courseOp = new CourseOp();

    public Scene setCourse(Course course, Stage primaryStage, Scene primaryScene){
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
        final Label title_label = new Label("您当前正在修改"+course.getCourse_name() +"的信息（系统管理员权限）" );
        title_label.setLayoutX(350);
        title_label.setLayoutY(30);
        title_label.setFont(title);
        root.getChildren().add(title_label);

        // 管理课程信息：性别、员工号、入职时间与部门号不能更改
        Font area = new Font("楷体", 15);
        final Label info_label = new Label("课程信息");
        info_label.setLayoutX(50);
        info_label.setLayoutY(120);
        info_label.setFont(area);
        root.getChildren().add(info_label);
        TableView<CourseView> person = new TableView<>();
        person.setFixedCellSize(25);
        person.prefHeightProperty().bind(person.fixedCellSizeProperty().multiply(Bindings.size(person.getItems()).add(2)));
        person.minHeightProperty().bind(person.prefHeightProperty());
        person.maxHeightProperty().bind(person.prefHeightProperty());
        person.setPrefWidth(750);
        person.setFixedCellSize(40);
        ObservableList<CourseView> data = FXCollections.observableArrayList(
                new CourseView(course.getCourse_id(), course.getCourse_name(), course.getInstructor_id(), course.getType(),
                        course.getContent(),course.getState())
        );
        person.setEditable(true);
        person.setLayoutX(180);
        person.setLayoutY(120);
        // 设置列
        TableColumn<CourseView, String> tableColumnID = new TableColumn<>("course_id");
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        TableColumn<CourseView, String> tableColumnName = new TableColumn<>("course_name");
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnName.setOnEditCommit(
                (TableColumn.CellEditEvent<CourseView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCourse_name(t.getNewValue()));
        TableColumn<CourseView, Long> tableColumnInstructorID = new TableColumn<>("instructor_id");
        tableColumnInstructorID.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
        tableColumnInstructorID.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        tableColumnInstructorID.setOnEditCommit(
                (TableColumn.CellEditEvent<CourseView, Long> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setInstructor_id(t.getNewValue()));

        TableColumn<CourseView, String> tableColumnType = new TableColumn<>("type");
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnType.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnType.setOnEditCommit(
                (TableColumn.CellEditEvent<CourseView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setType(t.getNewValue()));

        TableColumn<CourseView, String> tableColumnContent = new TableColumn<>("content");
        tableColumnContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        tableColumnContent.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnContent.setOnEditCommit(
                (TableColumn.CellEditEvent<CourseView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setContent(t.getNewValue()));
        TableColumn<CourseView, Integer> tableColumnState = new TableColumn<>("state");
        tableColumnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        tableColumnState.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnState.setOnEditCommit(
                (TableColumn.CellEditEvent<CourseView, Integer> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setState(t.getNewValue()));
        person.setItems(data);
        person.getColumns().addAll(tableColumnID, tableColumnName, tableColumnInstructorID, tableColumnType,tableColumnContent,tableColumnState);
        root.getChildren().add(person);

        final Button commit = new Button("修改提交");
        commit.setLayoutX(950);
        commit.setLayoutY(123);
        commit.setMinHeight(30);
        commit.setMinWidth(40);
        commit.setFont(button);
        root.getChildren().add(commit);
        commit.setOnMouseClicked(e -> {
//            Course temp = new Course();
//            temp.setCourse_id(course.getCourse_id());
//            temp.setCourse_name(data.get(0).getCourse_name());
//            temp.setInstructor_id(course.getInstructor_id());
//            temp.setType(data.get(0).getType());
//            temp.setContent(course.getContent());
//            temp.setState(data.get(0).getState());
//            administratorOp.updateCourse(course.getCourse_id(),course.getCourse_name(),course.getContent(),course.getState(),course.getType());
        });
        return scene;
    }

}
