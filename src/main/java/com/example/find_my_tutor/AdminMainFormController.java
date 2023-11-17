package com.example.find_my_tutor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AdminMainFormController implements Initializable {

    @FXML
    private TableColumn<StudentData, String> AddStudent_col_class;

    @FXML
    private TableColumn<StudentData, String> AddStudent_col_dateimport;

    @FXML
    private TableColumn<StudentData, String> AddStudent_col_group;

    @FXML
    private TableColumn<StudentData, String> AddStudent_col_interestedsubject;

    @FXML
    private TableColumn<StudentData, String> AddStudent_col_name;

    @FXML
    private TableColumn<StudentData, String> AddStudent_col_paymentstatus;

    @FXML
    private TableColumn<StudentData, String> AddStudent_col_studentnumber;

    @FXML
    private TableView<StudentData> AddStudent_tableview;

    @FXML
    private Button AddTeacher_addbtn;

    @FXML
    private TableColumn<TeacherData, String> AddTeacher_col_experience;

    @FXML
    private TableColumn<TeacherData, String> AddTeacher_col_name;

    @FXML
    private TableColumn<TeacherData, String> AddTeacher_col_salary;

    @FXML
    private TableColumn<StudentData, String> AddTeacher_col_status;

    @FXML
    private TableColumn<TeacherData, String> AddTeacher_col_subject;

    @FXML
    private TableColumn<TeacherData, String> AddTeacher_col_teacherid;

    @FXML
    private TextField AddTeacher_exp;

    @FXML
    private TextField AddTeacher_fullname;

    @FXML
    private TextField AddTeacher_id;

    @FXML
    private ImageView AddTeacher_imageview;

    @FXML
    private Button AddTeacher_importbtn;

    @FXML
    private TextField AddTeacher_salary;

    @FXML
    private ComboBox<String> AddTeacher_status;

    @FXML
    private ComboBox<String> AddTeacher_subject;

    @FXML
    private TableView<TeacherData> AddTeacher_tableview;

    @FXML
    private Button add_btn;

    @FXML
    private Button addclass_btn;

    @FXML
    private Button addstudent_btn;

    @FXML
    private AnchorPane addstudent_form;

    @FXML
    private Button addsubject_btn;

    @FXML
    private Button addteacher_btn;

    @FXML
    private AnchorPane addteacher_form;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button Signout_btn;
    @FXML
    private AnchorPane dashboard_form;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;



    private AlertMessage alert =new AlertMessage();
    private Image image;


    public ObservableList<StudentData> addStudentGetData() {

        ObservableList<StudentData> listData = FXCollections.observableArrayList();
        String selectData = "SELECT * FROM students";//' WHERE date_delete IS NULL";

        connect = Database.connectDB();

        StudentData sData;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {

                sData = new StudentData(
                        result.getInt("id"),
                        result.getString("student_id"),
                        result.getString("full_name"),
                        result.getString("group"),
                        result.getString("class"),
                        result.getString("payment"),
                        result.getString("status_payment"),
                        result.getDate("date_insert"),
                        result.getString("subject")
                );
                listData.add(sData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<StudentData> addStudentListData;
    public void addStudentDisplayData(){
        addStudentListData=addStudentGetData();

        AddStudent_col_studentnumber.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        AddStudent_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        AddStudent_col_class.setCellValueFactory(new PropertyValueFactory<>("student_class"));
        AddStudent_col_interestedsubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        AddStudent_col_group.setCellValueFactory(new PropertyValueFactory<>("group"));
        AddStudent_col_paymentstatus.setCellValueFactory(new PropertyValueFactory<>("payment"));
        AddStudent_col_dateimport.setCellValueFactory(new PropertyValueFactory<>("dateInsert"));
        AddStudent_tableview.setItems(addStudentListData);

    }
public void addStudentBtn(){
        try{
            Parent root= FXMLLoader.load(getClass().getResource("AddStudent.fxml"));
            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){e.printStackTrace();}
}



    public ObservableList<TeacherData> addTeacherGetData() {

        ObservableList<TeacherData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM teachers";//" WHERE date_delete IS NULL";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            TeacherData tData;

            while (result.next()) {

                tData = new TeacherData(result.getInt("id"),
                        result.getString("teacher_id"),
                        result.getString("full_name"),
                        result.getString("gender"),
                        result.getString("year_experience"),
                        result.getString("experience"),
                        result.getString("subject"),
                        result.getDouble("salary"),
                        result.getString("salary_status"),
                        result.getString("image"),
                        result.getDate("date_insert"),
                        result.getDate("date_update"),
                        result.getDate("date_delete"),
                        result.getString("status"));

                listData.add(tData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<TeacherData> addTeacherListData;

    public void addTeacherDisplayData() {
        addTeacherListData = addTeacherGetData();

        AddTeacher_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        AddTeacher_col_teacherid.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        AddTeacher_col_experience.setCellValueFactory(new PropertyValueFactory<>("yearExperience"));
        AddTeacher_col_subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        AddTeacher_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        AddTeacher_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        AddTeacher_tableview.setItems(addTeacherListData);

    }

    public void addTeacherSelectItems() {

        TeacherData tData = AddTeacher_tableview.getSelectionModel().getSelectedItem();
        int num = AddTeacher_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        AddTeacher_id.setText(tData.getTeacherID());
        AddTeacher_fullname.setText(tData.getFullName());
        AddTeacher_exp.setText(tData.getYearExperience());;
        AddTeacher_subject.getSelectionModel().select(tData.getSubject());
        AddTeacher_salary.setText("" + tData.getSalary());
        AddTeacher_status.getSelectionModel().select(tData.getStatus());

        ListData.path = tData.getImage();

        Image image = new Image("File:" + tData.getImage(), 90, 103, false, true);
        AddTeacher_imageview.setImage(image);
    }
    public void subjectList() {
        List<String> listG = new ArrayList<>();

        for (String data : ListData.subject) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        AddTeacher_subject.setItems(listData);
    }
    public void statusList() {
        List<String> listG = new ArrayList<>();

        for (String data : ListData.statusA) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        AddTeacher_status.setItems(listData);
    }

    public void addTeacherAddBtn() {

        if (AddTeacher_id.getText().isEmpty()
                || AddTeacher_fullname.getText().isEmpty()
                || AddTeacher_exp.getText().isEmpty()
                || AddTeacher_subject.getSelectionModel().getSelectedItem().isEmpty()
                ||AddTeacher_salary.getText().isEmpty()
                || AddTeacher_status.getSelectionModel().getSelectedItem().isEmpty()
                ) {
            alert.errorMessage("Please fill all blank fields");
        } else {

            String insertData = "INSERT INTO teachers "
                    + "(teacher_id, full_name,year_experience"
                    + ", subject, salary, image,status) "
                    + "VALUES(?,?,?,?,?,?,?)";

            connect = Database.connectDB();

            String path = ListData.path;
            path = path.replace("\\", "\\\\");

            try {
                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, AddTeacher_id.getText());
                prepare.setString(2, AddTeacher_fullname.getText());
                prepare.setString(3, AddTeacher_exp.getText());
                prepare.setString(4, AddTeacher_subject.getSelectionModel().getSelectedItem());
                prepare.setString(5, AddTeacher_salary.getText());;
                prepare.setString(6, path);
                prepare.setString(7, AddTeacher_status.getSelectionModel().getSelectedItem());

                prepare.executeUpdate();

                addTeacherDisplayData();

                alert.successMessage("Added Successfully!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public  void addTeacherImportbtn(){
        FileChooser open =new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image","*.jpg","*.jpeg","*.png"));
        File file=open.showOpenDialog(AddTeacher_importbtn.getScene().getWindow());

        if(file!=null)
        {
            ListData.path= file.getAbsolutePath();

            image =new Image(file.toURI().toString(),90,103,false,true);
            AddTeacher_imageview.setImage(image);
        }

    }
    public void switchform(ActionEvent event)
    {
        /*addstudent_form.setVisible(false);
        addteacher_form.setVisible(false);
        dashboard_form.setVisible(true);*/

        if(event.getSource() == addstudent_btn)
        {
            addstudent_form.setVisible(true);
            addteacher_form.setVisible(false);
            dashboard_form.setVisible(false);
        }
        if(event.getSource() == addteacher_btn)
        {
            addstudent_form.setVisible(false);
            addteacher_form.setVisible(true);
            dashboard_form.setVisible(false);
        }
        if(event.getSource() == dashboard_btn)
        {
            addstudent_form.setVisible(false);
            addteacher_form.setVisible(false);
            dashboard_form.setVisible(true);
        }

    }

    public void logoutBtn() {
        try {
            if (alert.confirmMessage("Are you sure you want to logout?")) {
                // TO SHOW THE LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDoccument.fxml"));
                Stage stage = new Stage();

                stage.setScene(new Scene(root));
                stage.show();

                // TO HIDE YOUR MAIN FORM
                Signout_btn.getScene().getWindow().hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addStudentDisplayData();

        addTeacherDisplayData();
        subjectList();
        statusList();

    }
}
