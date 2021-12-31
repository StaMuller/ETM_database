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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.List;

public class AdministratorScene {
    CourseScene courseScene = new CourseScene();
    EditScene editScene = new EditScene();
    TransScene transScene = new TransScene();
    ExamScene examScene = new ExamScene();
    EmployeeOp employeeOp = new EmployeeOp();
    DepartmentOp departmentOp = new DepartmentOp();
    ManagerOp managerOp = new ManagerOp();
    CourseOp courseOp = new CourseOp();

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

        Font area = new Font("楷体", 15);

        final TextArea manage_area = new TextArea();
        manage_area.setLayoutX(50);
        manage_area.setLayoutY(155);
        manage_area.setPrefWidth(1100);
        manage_area.setPrefHeight(300);
        manage_area.setWrapText(true);
        root.getChildren().addAll(manage_area);


        final Label id_label = new Label("员工号/姓名");
        id_label.setLayoutX(50);
        id_label.setLayoutY(100);
        id_label.setFont(area);
        final TextArea id_area = new TextArea();
        id_area.setLayoutX(150);//员工号/姓名/员工姓名
        id_area.setLayoutY(100);
        id_area.setPrefWidth(150);
        id_area.setPrefHeight(10);
        id_area.setWrapText(true);



        final Button single_query = new Button("查询用户信息");
        single_query.setLayoutX(320);
        single_query.setLayoutY(100);
        single_query.setFont(button);
        root.getChildren().addAll(id_label,id_area,single_query);
        single_query.setOnMouseClicked(e -> {
            try{
                String employeeId = id_area.getText();
                Employee employee = employeeOp.getEmployeeById(Long.parseLong(employeeId));
                if (employee == null){
                    new Alert(Alert.AlertType.NONE, "员工号/姓名不存在", new ButtonType[]{ButtonType.CLOSE}).show();
                }else{
                    StringBuilder showString = new StringBuilder("员工号 员工名 性别 入职时间 地址 联系电话 邮箱地址 所在部门\n");
                    showString.append(
                            employee.getId()).append("  ")
                            .append(employee.getName()).append("  ")
                            .append(employee.getGender()).append("  ")
                            .append(employee.getAge()).append("  ")
                            .append(employee.getTime()).append("  ")
                            .append(employee.getAddress()).append("  ")
                            .append(employee.getTelephone()).append("  ")
                            .append(employee.getEmail()).append("  ")
                            .append(departmentOp.getDeptNameById(employee.getDept()))
                            .append("\n");
                    List<Takes> takesList = employeeOp.getTakes(employee.getId());
                    showString.append("课程号 课程名 导师 课程类型 课程内容 结课状态\n");
                    for(Takes takes : takesList){
                        System.out.println(takes);
                        showString.append(takes.getCourse_id()).append("  ")
                                .append(takes.getNumber()).append("  ")
                                .append(takes.getState()).append("  ")
                                .append(takes.getTime()).append("  ")
                                .append("\n");
                    }
                    manage_area.setText(showString.toString());
                }
            } catch (NumberFormatException exception){
                new Alert(Alert.AlertType.NONE, "请填写相应员工号/姓名", new ButtonType[]{ButtonType.CLOSE}).show();
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });
//切换到修改用户信息界面
        final Button edit = new Button("修改用户信息");
        edit.setLayoutX(420);
        edit.setLayoutY(100);
        edit.setFont(button);
        root.getChildren().addAll(edit);
//切换到修改用户信息界面
        edit.setOnMouseClicked(e -> {
            try{
                String employeeId = id_area.getText();
                Employee employee = employeeOp.getEmployeeById(Long.parseLong(employeeId));
                primaryStage.setScene(editScene.setEmployee(employee, primaryStage, scene));
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });


        final Label course_label = new Label("课程名");
        course_label.setLayoutX(550);
        course_label.setLayoutY(100);
        course_label.setFont(area);
        final TextArea course_area = new TextArea();
        course_area.setLayoutX(650);
        course_area.setLayoutY(100);
        course_area.setPrefWidth(150);
        course_area.setPrefHeight(10);
        course_area.setWrapText(true);

        // 切换到修改用户信息界面
        final Button edit_course = new Button("修改课程信息");
        edit_course.setLayoutX(850);
        edit_course.setLayoutY(100);
        edit_course.setFont(button);
        root.getChildren().addAll(course_label,course_area,edit_course);
        // 切换到修改课程信息界面
        edit_course.setOnMouseClicked(e -> {
            try{
                String courseId = course_area.getText();
                if(!courseId.equals("")){
                    Course course = courseOp.getCourseById(Long.parseLong(courseId));
                    primaryStage.setScene(courseScene.setCourse(course, primaryStage, scene));
                }else{
                    new Alert(Alert.AlertType.NONE, "请输入课程号", new ButtonType[]{ButtonType.CLOSE}).show();
                }
            } catch (Exception exception){
                exception.printStackTrace();
            }
        });



// primaryStage.setScene(examScene.setTrans(manager, primaryStage, scene));


        Employee employee = new Employee();
        // 管理个人信息：性别、员工号、入职时间与部门号不能更改
     
//        final Label info_label = new Label("个人信息");
//        info_label.setLayoutX(50);
//        info_label.setLayoutY(120);
//        info_label.setFont(area);
//        root.getChildren().add(info_label);
//        TableView<EmployeeView> person = new TableView<>();
//        person.setFixedCellSize(25);
//        person.prefHeightProperty().bind(person.fixedCellSizeProperty().multiply(Bindings.size(person.getItems()).add(2)));
//        person.minHeightProperty().bind(person.prefHeightProperty());
//        person.maxHeightProperty().bind(person.prefHeightProperty());
//        person.setPrefWidth(750);
//        person.setFixedCellSize(40);
//        ObservableList<EmployeeView> data = FXCollections.observableArrayList(
//                new EmployeeView(employee.getId(), employee.getName(), employee.getGender(), employee.getAge(),
//                        employee.getTime(), employee.getAddress(), employee.getTelephone(), employee.getEmail(),
//                        departmentOp.getDeptNameById(employee.getDept()))
//        );
//        person.setEditable(true);
//        person.setLayoutX(180);
//        person.setLayoutY(120);
//        // 设置列
//        TableColumn<EmployeeView, String> tableColumnID = new TableColumn<>("id");
//        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        TableColumn<EmployeeView, String> tableColumnName = new TableColumn<>("name");
//        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn());
//        tableColumnName.setOnEditCommit(
//                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
//                        t.getTablePosition().getRow()).setName(t.getNewValue()));
//        TableColumn<EmployeeView, String> tableColumnGender = new TableColumn<>("gender");
//        tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
//        TableColumn<EmployeeView, String> tableColumnTime = new TableColumn<>("time");
//        tableColumnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
//        TableColumn<EmployeeView, Integer> tableColumnAge = new TableColumn<>("age");
//        tableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
//        tableColumnAge.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        tableColumnAge.setOnEditCommit(
//                (TableColumn.CellEditEvent<EmployeeView, Integer> t) -> t.getTableView().getItems().get(
//                        t.getTablePosition().getRow()).setAge(t.getNewValue()));
//        TableColumn<EmployeeView, String> tableColumnAddress = new TableColumn<>("address");
//        tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
//        tableColumnAddress.setCellFactory(TextFieldTableCell.forTableColumn());
//        tableColumnAddress.setOnEditCommit(
//                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
//                        t.getTablePosition().getRow()).setAddress(t.getNewValue()));
//        TableColumn<EmployeeView, Long> tableColumnTelephone = new TableColumn<>("telephone");
//        tableColumnTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
//        tableColumnTelephone.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
//        tableColumnTelephone.setOnEditCommit(
//                (TableColumn.CellEditEvent<EmployeeView, Long> t) -> t.getTableView().getItems().get(
//                        t.getTablePosition().getRow()).setTelephone(t.getNewValue()));
//        TableColumn<EmployeeView, String> tableColumnEmail = new TableColumn<>("email");
//        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
//        tableColumnEmail.setCellFactory(TextFieldTableCell.forTableColumn());
//        tableColumnEmail.setOnEditCommit(
//                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
//                        t.getTablePosition().getRow()).setEmail(t.getNewValue()));
//        TableColumn<EmployeeView, Integer> tableColumnDept = new TableColumn<>("dept");
//        tableColumnDept.setCellValueFactory(new PropertyValueFactory<>("dept"));
//        person.setItems(data);
//        person.getColumns().addAll(tableColumnID, tableColumnName, tableColumnGender, tableColumnAge,
//                tableColumnTime, tableColumnAddress, tableColumnTelephone, tableColumnEmail, tableColumnDept);
//        root.getChildren().add(person);
//
//        final Button commit = new Button("修改提交");
//        commit.setLayoutX(950);
//        commit.setLayoutY(123);
//        commit.setMinHeight(30);
//        commit.setMinWidth(40);
//        commit.setFont(button);
//        root.getChildren().add(commit);
//        commit.setOnMouseClicked(e -> {
//            Employee temp = new Employee();
//            temp.setId(employee.getId());
//            temp.setName(data.get(0).getName());
//            temp.setGender(employee.getGender());
//            temp.setAge(data.get(0).getAge());
//            temp.setTime(employee.getTime());
//            temp.setAddress(data.get(0).getAddress());
//            temp.setTelephone(data.get(0).getTelephone());
//            temp.setEmail(data.get(0).getEmail());
//            temp.setDept(employee.getDept());
//            employeeOp.reviseInfo(temp);
//        });
















        return scene;
    }


}
