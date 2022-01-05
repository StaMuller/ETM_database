package com.runApp;

import com.bean.Employee;
import com.operation.AdministratorOp;
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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class EditEmployeeScene {

    EmployeeOp employeeOp = new EmployeeOp();
    DepartmentOp departmentOp = new DepartmentOp();
    AdministratorOp administratorOp = new AdministratorOp();

    public Scene setEmployee(Stage primaryStage, Scene primaryScene){
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
        final Label title_label = new Label("管理所有员工信息（系统管理员权限）" );
        title_label.setLayoutX(350);
        title_label.setLayoutY(30);
        title_label.setFont(title);
        root.getChildren().add(title_label);

        // 管理个人信息：性别、员工号、入职时间与部门号不能更改
        List<Employee> allEmployee = administratorOp.queryAllEmployee();
        Font area = new Font("楷体", 15);
        final Label info_label = new Label("员工信息");
        info_label.setLayoutX(50);
        info_label.setLayoutY(120);
        info_label.setFont(area);
        root.getChildren().add(info_label);
        TableView<EmployeeView> person = new TableView<>();
        person.setPrefWidth(760);
        person.setPrefHeight(280);
        ObservableList<EmployeeView> data = FXCollections.observableArrayList();
        for(Employee employee : allEmployee){
            EmployeeView employeeView = new EmployeeView(employee.getId(), employee.getName(), employee.getGender(), employee.getAge(),
                    employee.getTime(), employee.getAddress(), employee.getTelephone(), employee.getEmail(),
                    departmentOp.getDeptNameById(employee.getDept()));
            data.add(employeeView);
        }
        person.setEditable(true);
        person.setLayoutX(150);
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
        TableColumn<EmployeeView, Integer> tableColumnDept = new TableColumn<>("所在部门");
        tableColumnDept.setCellValueFactory(new PropertyValueFactory<>("dept"));
        person.setItems(data);
        person.getColumns().addAll(tableColumnID, tableColumnName, tableColumnGender, tableColumnAge,
                 tableColumnTime, tableColumnAddress, tableColumnTelephone, tableColumnEmail, tableColumnDept);
        root.getChildren().add(person);
        final Button commit = new Button("修改提交");
        commit.setLayoutX(1000);
        commit.setLayoutY(120);
        commit.setMinHeight(30);
        commit.setMinWidth(40);
        commit.setFont(button);
        root.getChildren().add(commit);
        commit.setOnMouseClicked(e -> {
            for(EmployeeView employeeView : data){
                Employee temp = new Employee();
                temp.setId(employeeView.getId());
                temp.setName(employeeView.getName());
                temp.setGender(employeeView.getGender());
                temp.setAge(employeeView.getAge());
                temp.setTime(employeeView.getTime());
                temp.setAddress(employeeView.getAddress());
                temp.setTelephone(employeeView.getTelephone());
                temp.setEmail(employeeView.getEmail());
                temp.setDept(departmentOp.getDeptIdByName(employeeView.getDept()));
                employeeOp.reviseInfo(temp);
                new Alert(Alert.AlertType.NONE, "修改成功", new ButtonType[]{ButtonType.CLOSE}).show();
            }
        });

        // 删除员工
        final TextArea delete_area = new TextArea();
        delete_area.setLayoutX(930);
        delete_area.setLayoutY(180);
        delete_area.setPrefWidth(100);
        delete_area.setPrefHeight(10);
        delete_area.setWrapText(true);
        final Button delete = new Button("删除该员工");
        delete.setLayoutX(1050);
        delete.setLayoutY(180);
        delete.setMinHeight(30);
        delete.setMinWidth(70);
        delete.setFont(button);
        root.getChildren().addAll(delete_area, delete);
        delete.setOnMouseClicked(e -> {
            if(administratorOp.deleteEmployee(Long.parseLong(delete_area.getText()))){
                new Alert(Alert.AlertType.NONE, "删除成功", new ButtonType[]{ButtonType.CLOSE}).show();
            }else{
                new Alert(Alert.AlertType.NONE, "删除失败", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            allEmployee.clear();
            allEmployee.addAll(administratorOp.queryAllEmployee());
            data.clear();
            for(Employee employee : allEmployee){
                EmployeeView employeeView = new EmployeeView(employee.getId(), employee.getName(), employee.getGender(), employee.getAge(),
                        employee.getTime(), employee.getAddress(), employee.getTelephone(), employee.getEmail(),
                        departmentOp.getDeptNameById(employee.getDept()));
                data.add(employeeView);
            }
            person.setItems(data);
        });

        // 增加员工
        TableView<EmployeeView> addEmployee = new TableView<>();
        addEmployee.setFixedCellSize(25);
        addEmployee.prefHeightProperty().bind(addEmployee.fixedCellSizeProperty().multiply(Bindings.size(addEmployee.getItems()).add(1.7)));
        addEmployee.minHeightProperty().bind(addEmployee.prefHeightProperty());
        addEmployee.maxHeightProperty().bind(addEmployee.prefHeightProperty());
        addEmployee.setPrefWidth(740);
        addEmployee.setFixedCellSize(40);
        ObservableList<EmployeeView> newEmployeeData = FXCollections.observableArrayList();
        EmployeeView newEmployeeView = new EmployeeView();
        newEmployeeData.add(newEmployeeView);
        addEmployee.setEditable(true);
        addEmployee.setLayoutX(150);
        addEmployee.setLayoutY(450);
        // 设置列
        TableColumn<EmployeeView, Long> newTableColumnID = new TableColumn<>("员工号");
        newTableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        newTableColumnID.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        newTableColumnID.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, Long> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setId(t.getNewValue()));
        newTableColumnID.setMinWidth(100);
        TableColumn<EmployeeView, String> newTableColumnName = new TableColumn<>("姓名");
        newTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        newTableColumnName.setCellFactory(TextFieldTableCell.forTableColumn());
        newTableColumnName.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setName(t.getNewValue()));
        newTableColumnName.setMinWidth(50);
        TableColumn<EmployeeView, String> newTableColumnGender = new TableColumn<>("性别");
        newTableColumnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        newTableColumnGender.setCellFactory(TextFieldTableCell.forTableColumn());
        newTableColumnGender.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setGender(t.getNewValue()));
        newTableColumnGender.setMinWidth(50);
        TableColumn<EmployeeView, Integer> newTableColumnAge = new TableColumn<>("年龄");
        newTableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        newTableColumnAge.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        newTableColumnAge.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, Integer> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setAge(t.getNewValue()));
        newTableColumnAge.setMinWidth(50);
        TableColumn<EmployeeView, String> newTableColumnAddress = new TableColumn<>("住址");
        newTableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        newTableColumnAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        newTableColumnAddress.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setAddress(t.getNewValue()));
        newTableColumnAddress.setMinWidth(50);
        TableColumn<EmployeeView, Long> newTableColumnTelephone = new TableColumn<>("联系电话");
        newTableColumnTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        newTableColumnTelephone.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        newTableColumnTelephone.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, Long> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setTelephone(t.getNewValue()));
        newTableColumnTelephone.setMinWidth(150);
        TableColumn<EmployeeView, String> newTableColumnEmail = new TableColumn<>("邮箱");
        newTableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        newTableColumnEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        newTableColumnEmail.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setEmail(t.getNewValue()));
        newTableColumnEmail.setMinWidth(150);
        TableColumn<EmployeeView, String> newTableColumnDept = new TableColumn<>("所在部门");
        newTableColumnDept.setCellValueFactory(new PropertyValueFactory<>("dept"));
        newTableColumnDept.setCellFactory(TextFieldTableCell.forTableColumn());
        newTableColumnDept.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeView, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setDept(t.getNewValue()));
        newTableColumnDept.setMinWidth(100);
        addEmployee.setItems(newEmployeeData);
        addEmployee.getColumns().addAll(newTableColumnID, newTableColumnName, newTableColumnGender, newTableColumnAge,
                newTableColumnAddress, newTableColumnTelephone, newTableColumnEmail, newTableColumnDept);
        root.getChildren().add(addEmployee);
        final Button add = new Button("添加新员工信息");
        add.setLayoutX(1000);
        add.setLayoutY(450);
        add.setMinHeight(30);
        add.setMinWidth(130);
        add.setFont(button);
        root.getChildren().add(add);
        add.setOnMouseClicked(e -> {
            EmployeeView employeeView = newEmployeeData.get(0);
            Employee temp = new Employee();
            temp.setId(employeeView.getId());
            temp.setName(employeeView.getName());
            temp.setGender(employeeView.getGender());
            temp.setAge(employeeView.getAge());
            Date date = new Date();
            temp.setTime(new Timestamp(date.getTime()));
            temp.setAddress(employeeView.getAddress());
            temp.setTelephone(employeeView.getTelephone());
            temp.setEmail(employeeView.getEmail());
            temp.setDept(departmentOp.getDeptIdByName(employeeView.getDept()));
            if(administratorOp.addEmployee(temp)){
                allEmployee.clear();
                allEmployee.addAll(administratorOp.queryAllEmployee());
                data.clear();
                for(Employee employee : allEmployee){
                    EmployeeView tempEmployeeView = new EmployeeView(employee.getId(), employee.getName(), employee.getGender(), employee.getAge(),
                            employee.getTime(), employee.getAddress(), employee.getTelephone(), employee.getEmail(),
                            departmentOp.getDeptNameById(employee.getDept()));
                    data.add(tempEmployeeView);
                }
                person.setItems(data);
                new Alert(Alert.AlertType.NONE, "添加成功", new ButtonType[]{ButtonType.CLOSE}).show();
            }else{
                new Alert(Alert.AlertType.NONE, "添加失败", new ButtonType[]{ButtonType.CLOSE}).show();
            }
        });
        return scene;
    }

}
