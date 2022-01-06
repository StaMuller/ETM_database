package com.runApp;

import com.bean.Course;
import com.bean.Employee;
import com.bean.Takes;
import com.operation.CourseOp;
import com.operation.DepartmentOp;
import com.operation.EmployeeOp;
import com.runApp.Container.EmployeeView;
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


public class EmployeeScene {

    EmployeeOp employeeOp = new EmployeeOp();
    DepartmentOp departmentOp = new DepartmentOp();
    CourseOp courseOp = new CourseOp();

    public Scene setEmployee(Employee employee, Stage primaryStage, Scene primaryScene){
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
        final Label title_label = new Label(employee.getName() + "，欢迎来到员工管理系统（员工权限）");
        title_label.setLayoutX(350);
        title_label.setLayoutY(30);
        title_label.setFont(title);
        root.getChildren().add(title_label);

        // 管理个人信息：性别、员工号、入职时间与部门号不能更改
        Font area = new Font("楷体", 15);
        final Label info_label = new Label("个人信息");
        info_label.setLayoutX(50);
        info_label.setLayoutY(120);
        info_label.setFont(area);
        root.getChildren().add(info_label);
        TableView<EmployeeView> person = new TableView<>();
        person.setFixedCellSize(25);
        person.prefHeightProperty().bind(person.fixedCellSizeProperty().multiply(Bindings.size(person.getItems()).add(2)));
        person.minHeightProperty().bind(person.prefHeightProperty());
        person.maxHeightProperty().bind(person.prefHeightProperty());
        person.setPrefWidth(750);
        person.setFixedCellSize(40);
        ObservableList<EmployeeView> data = FXCollections.observableArrayList(
                new EmployeeView(employee.getId(), employee.getName(), employee.getGender(), employee.getAge(),
                        employee.getTime(), employee.getAddress(), employee.getTelephone(), employee.getEmail(),
                        departmentOp.getDeptNameById(employee.getDept()))
        );
        person.setEditable(true);
        person.setLayoutX(180);
        person.setLayoutY(120);
        // 设置列
        TableColumn<EmployeeView, String> tableColumnID = new TableColumn<>("员工号");
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<EmployeeView, String> tableColumnName = new TableColumn<>("姓名");
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnName.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setName(t.getNewValue()));
        TableColumn<EmployeeView, String> tableColumnGender = new TableColumn<>("性别");
        tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        TableColumn<EmployeeView, String> tableColumnTime = new TableColumn<>("入职时间");
        tableColumnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        TableColumn<EmployeeView, Integer> tableColumnAge = new TableColumn<>("年龄");
        tableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableColumnAge.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnAge.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, Integer> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setAge(t.getNewValue()));
        TableColumn<EmployeeView, String> tableColumnAddress = new TableColumn<>("住址");
        tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableColumnAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnAddress.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setAddress(t.getNewValue()));
        TableColumn<EmployeeView, Long> tableColumnTelephone = new TableColumn<>("联系电话");
        tableColumnTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        tableColumnTelephone.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        tableColumnTelephone.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, Long> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setTelephone(t.getNewValue()));
        TableColumn<EmployeeView, String> tableColumnEmail = new TableColumn<>("邮箱");
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnEmail.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setEmail(t.getNewValue()));
        TableColumn<EmployeeView, String> tableColumnDept = new TableColumn<>("所在部门");
        tableColumnDept.setCellValueFactory(new PropertyValueFactory<>("dept"));
        person.setItems(data);
        person.getColumns().addAll(tableColumnID, tableColumnName, tableColumnGender, tableColumnAge,
                 tableColumnTime, tableColumnAddress, tableColumnTelephone, tableColumnEmail, tableColumnDept);
        root.getChildren().add(person);

        final Button commit = new Button("修改提交");
        commit.setLayoutX(950);
        commit.setLayoutY(123);
        commit.setMinHeight(30);
        commit.setMinWidth(40);
        commit.setFont(button);
        root.getChildren().add(commit);
        commit.setOnMouseClicked(e -> {
            Employee temp = new Employee();
            temp.setId(employee.getId());
            temp.setName(data.get(0).getName());
            temp.setGender(employee.getGender());
            temp.setAge(data.get(0).getAge());
            temp.setTime(employee.getTime());
            temp.setAddress(data.get(0).getAddress());
            temp.setTelephone(data.get(0).getTelephone());
            temp.setEmail(data.get(0).getEmail());
            temp.setDept(employee.getDept());
            employeeOp.reviseInfo(temp);
            new Alert(Alert.AlertType.NONE, "修改成功", new ButtonType[]{ButtonType.CLOSE}).show();
        });

        // 查看被分配的课程与教员信息
        final Label course_label = new Label("课程与教员信息");
        course_label.setLayoutX(50);
        course_label.setLayoutY(250);
        course_label.setFont(area);
        final TextArea course_area = new TextArea();
        course_area.setLayoutX(50);
        course_area.setLayoutY(300);
        course_area.setPrefWidth(450);
        course_area.setPrefHeight(200);
        course_area.setWrapText(true);
        final Button course_query = new Button("查询");
        course_query.setLayoutX(200);
        course_query.setLayoutY(250);
        course_query.setFont(button);
        root.getChildren().addAll(course_label, course_area, course_query);
        course_query.setOnMouseClicked(e -> {
            List<Course> courseList = employeeOp.findCourse(employee.getId());
            if(courseList.size() != 0){
                StringBuilder showString = new StringBuilder("课程号 课程名 导师 课程类型 课程内容 结课状态\n");
                for(Course course : courseList){
                    showString
                            .append(course.getCourse_id()).append("   ")
                            .append(course.getCourse_name()).append("   ")
                            .append(employeeOp.getEmployeeById(course.getInstructor_id()).getName()).append("   ")
                            .append(course.getType()).append("   ")
                            .append(course.getContent()).append("   ");
                    if(course.getState() == 0){
                        showString.append("未结课\n");
                    }else{
                        showString.append("已结课\n");
                    }
                }
                course_area.setText(showString.toString());
            }else{
                course_area.setText("暂无课程信息");
            }
        });

        // 查看历史培训信息
        final Label score_label = new Label("历史培训信息");
        score_label.setLayoutX(550);
        score_label.setLayoutY(250);
        score_label.setFont(area);
        final TextArea score_area = new TextArea();
        score_area.setLayoutX(550);
        score_area.setLayoutY(300);
        score_area.setPrefWidth(450);
        score_area.setPrefHeight(200);
        score_area.setWrapText(true);
        final Button score_query = new Button("查询");
        score_query.setLayoutX(650);
        score_query.setLayoutY(250);
        score_query.setFont(button);
        root.getChildren().addAll(score_label, score_area, score_query);

        score_query.setOnMouseClicked(e -> {
            StringBuilder showString = new StringBuilder("课程号 课程名 分数 通过情况 成绩录入时间\n");
            List<Takes> takesList = employeeOp.findScore(employee.getId());
            for(Takes takes : takesList){
                showString.append(takes.getCourse_id()).append("  ")
                        .append(courseOp.getCourseById(takes.getCourse_id()).getCourse_name()).append("  ");
                if(takes.getState() == null){
                    showString.append("未考试\n");
                }else{
                    showString.append(takes.getNumber()).append("  ")
                            .append(takes.getState()).append("  ");
                    if (takes.getTime()==null){
                        showString.append("").append("\n");
                    }
                }
            }
            score_area.setText(showString.toString());
        });

        return scene;
    }

}
