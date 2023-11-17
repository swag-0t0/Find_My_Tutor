package com.example.find_my_tutor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Date;

import static com.example.find_my_tutor.ListData.role;

public class HelloController implements Initializable {


    @FXML
    private AnchorPane Admin_form;

    @FXML
    private Hyperlink Admin_login;

    @FXML
    private PasswordField Admin_password;

    @FXML
    private Button Admin_signupbtn;

    @FXML
    private TextField Admin_username;

    @FXML
    private PasswordField Student_confirmpassword;

    @FXML
    private TextField Student_email;

    @FXML
    private AnchorPane Student_form;

    @FXML
    private Hyperlink Student_login;

    @FXML
    private PasswordField Student_password;

    @FXML
    private Button Student_signupbtn;

    @FXML
    private TextField Student_username;

    @FXML
    private AnchorPane Teacher_form;

    @FXML
    private PasswordField Admin_confirmpassword;

    @FXML
    private Button login_button;
    @FXML
    private AnchorPane Login_form;
    @FXML
    private ComboBox<String> login_combobox;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_username;

    @FXML
    private PasswordField teacher_confirmpassword;

    @FXML
    private TextField teacher_email;

    @FXML
    private Hyperlink teacher_login;

    @FXML
    private PasswordField teacher_password;

    @FXML
    private Button teacher_signupbtn;

    @FXML
    private TextField teacher_username;



    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;
    private AlertMessage alert =new AlertMessage();

    public void registerAdmin() {

        if (Admin_username.getText().isEmpty() || Admin_password.getText().isEmpty()
                || Admin_confirmpassword.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            connect = Database.connectDB();

            String selectData = "SELECT * FROM all_users WHERE username = '"
                    + Admin_username.getText() + "'";

            try {
                Statement statement =connect.createStatement();
                result = statement.executeQuery(selectData);
                if (result.next()) {
                    alert.errorMessage(Admin_username.getText() + " is already exist");
                } else if (!Admin_password.getText().equals(Admin_confirmpassword.getText())) {
                    alert.errorMessage("Password does not match.");
                } else if (Admin_password.getText().length() < 4) {
                    alert.errorMessage("Invalid password, at least 4 characters needed");
                } else {
                    String insertData = "INSERT INTO all_users (username, password, role, date) "
                            + "VALUES(?,?,?,?)";

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, Admin_username.getText());
                    prepare.setString(2, Admin_password.getText());
                    prepare.setString(3, "Admin");
                    prepare.setString(4, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert.successMessage("Registered successfully!");

                    Login_form.setVisible(true);
                    Admin_form.setVisible(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void registerStudent() {
        studentIDGenerator();
        System.out.println(studentID);

        if (Student_email.getText().isEmpty() || Student_username.getText().isEmpty()
                || Student_password.getText().isEmpty()
                || Student_confirmpassword.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            connect = Database.connectDB();

            String selectData = "SELECT * FROM all_users WHERE username = '"
                    + Student_username.getText() + "'";

            try {
                Statement statement = connect.createStatement();
                result = statement.executeQuery(selectData);

                if (result.next()) {
                    alert.errorMessage(Student_username.getText() + " is already exist");
                } else {
                    if (!Student_password.getText().equals(Student_confirmpassword.getText())) {
                        alert.errorMessage("Password does not match.");
                    } else if (Student_password.getText().length() < 4) {
                        alert.errorMessage("Invalid password, at least 4 characters needed");
                    } else {
                        String insertData = "INSERT INTO all_users (username,email, password, role,student_id, date) "
                                + "VALUES(?,?,?,?,?,?)";        //register er somoy all_users table e insert hobe

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        SimpleDateFormat format = new SimpleDateFormat("yyyy");
                        String getYear = format.format(date);
                        String studentNum = getYear + "0000";
                        int sNum = Integer.parseInt(studentNum) + studentIDGenerator();

                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, Student_username.getText());
                        prepare.setString(2, Student_email.getText());
                        prepare.setString(3, Student_password.getText());
                        prepare.setString(4, "Student");
                        prepare.setString(5, String.valueOf(sNum));
                        prepare.setString(6, String.valueOf(sqlDate));

                        prepare.executeUpdate();

                        studentIDGenerator();

                        String insertStudent = "INSERT INTO students (student_id, date_insert, status_payment,full_name) "
                                + "VALUES(?,?,?,?)";          //id generating er time e students table e insert hobe

                        prepare = connect.prepareStatement(insertStudent);
                        prepare.setString(1, String.valueOf(sNum));
                        prepare.setString(2, String.valueOf(sqlDate));
                        prepare.setString(3, "Approval");
                        prepare.setString(4,Student_username.getText());

                        prepare.executeUpdate();

                        alert.successMessage("Registered successfully!");

                        Login_form.setVisible(true);
                        Student_form.setVisible(false);
                    }
                }
                connect.close();
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }

    private int studentID;

    public int studentIDGenerator()
    {
        String selectData = "SELECT MAX(id) FROM students";

        connect = Database.connectDB();

        int temp_studentID = 0;

        try {
           Statement statement = connect.createStatement();
            result = statement.executeQuery(selectData);

            if (result.next()) {
                temp_studentID = result.getInt("MAX(id)");
            }

            if (temp_studentID == 0) {
                studentID = 1;
            } else {
                studentID = temp_studentID + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentID;
    }


    public void registerTeacher() {

        if (teacher_email.getText().isEmpty() || teacher_username.getText().isEmpty()
                || teacher_password.getText().isEmpty()
                || teacher_confirmpassword.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            connect = Database.connectDB();

            String selectData = "SELECT * FROM all_users WHERE username = '"
                    + teacher_username.getText() + "'";

            try {
                Statement statement = connect.createStatement();
                result = statement.executeQuery(selectData);

                if (result.next()) {
                    alert.errorMessage(teacher_username.getText() + " is already exist");
                } else if (!teacher_password.getText().equals(teacher_confirmpassword.getText())) {
                    alert.errorMessage("Password does not match.");
                } else if (teacher_password.getText().length() < 4) {
                    alert.errorMessage("Invalid password, at least 4 characters needed");
                } else {

                    String temp_teacherID = "TID-" + String.valueOf(teacherIDGenerator());

                    String insertData = "INSERT INTO all_users "
                            + "(username, email, password, role, teacher_id, date) "
                            + "VALUES(?,?,?,?,?,?)";

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, teacher_username.getText());
                    prepare.setString(2, teacher_email.getText());
                    prepare.setString(3, teacher_password.getText());
                    prepare.setString(4, "Teacher");
                    prepare.setString(5, temp_teacherID);
                    prepare.setString(6, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    String insertStudent = "INSERT INTO teachers "
                            + "(teacher_id, date_insert, salary_status,full_name) "
                            + "VALUES(?,?,?,?)";

                    prepare = connect.prepareStatement(insertStudent);
                    prepare.setString(1, temp_teacherID);
                    prepare.setString(2, String.valueOf(sqlDate));
                    prepare.setString(3, "paid");
                    prepare.setString(4,teacher_username.getText());

                    prepare.executeUpdate();

                    alert.successMessage("Registered successfully!");

                    Login_form.setVisible(true);
                    Teacher_form.setVisible(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int teacherID = 0;

    public int teacherIDGenerator() {

        String sql = "SELECT MAX(id) FROM teachers";

        connect = Database.connectDB();
        int temp_teacherID = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                temp_teacherID = result.getInt("MAX(id)");
            }

            if (temp_teacherID == 0) {
                teacherID = 1;
            } else {
                teacherID = temp_teacherID + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacherID;
    }

public void loginAccount(){
        if(login_username.getText().isEmpty() || login_password.getText().isEmpty())
        {
            alert.errorMessage("Fill the blank fields");
        }
        else{
            String selectData="SELECT * FROM all_users WHERE username =? AND password =?";
            connect=Database.connectDB();
            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, login_username.getText());
                prepare.setString(2, login_password.getText());

                result = prepare.executeQuery();
                if(result.next()){
                   String role=result.getString("role");
                    System.out.println(role);

                    Thread.sleep(1000);
                    alert.successMessage("Login successfull!");

                if (role.equals("Admin")) {
                    Parent root;
                    root = FXMLLoader.load(getClass().getResource("AdminMainForm.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Find My Tutor | Admin Portal");
                    stage.setScene(new Scene(root));

                   // stage.initStyle(StageStyle.TRANSPARENT);

                    stage.show();

                    login_button.getScene().getWindow().hide();

                } else if (role.equals("Student")) {

                    String tempStudentID = result.getString("student_id");

                    String checkData = "SELECT * FROM students WHERE student_id = '"
                            + tempStudentID + "'";

                   Statement statement = connect.createStatement();
                    result = statement.executeQuery(checkData);

                    if (result.next()) {

                            // TO GET THE USERNAME
                            // ListData.student_username = login_username.getText();

                            Parent root = FXMLLoader.load(getClass().getResource("StudentMainForm.fxml"));
                            Stage stage = new Stage();

                            stage.setTitle("Find_My_Tutor | Student Portal");
                            stage.setScene(new Scene(root));
                            stage.show();

                            // TO HIDE YOUR LOGIN FORM
                            login_button.getScene().getWindow().hide();

                    }

                } else if (role.equals("Teacher")) {
                    Parent root=FXMLLoader.load(getClass().getResource("TeacherMainForm.fxml"));

                    Stage stage=new Stage();

                    stage.setTitle("Find_My_Tutor | Teacher Portal");
                    stage.setScene(new Scene(root));
                    stage.show();
                    login_button.getScene().getWindow().hide();
                }
            }else{
                alert.errorMessage("Incorrect Password/Username");
            }


            }catch(Exception e){e.printStackTrace();}
        }
}
    public void roleList(){
        List<String> listR=new ArrayList<>();

        for(String data : role){
            listR.add(data);
        }

        ObservableList listData= FXCollections.observableArrayList(listR);
        login_combobox.setItems(listData);
    }

    public void signInform(){
        Login_form.setVisible(true);
        Admin_form.setVisible(false);
        Student_form.setVisible(false);
        Teacher_form.setVisible(false);
    }


    public void switchForm(ActionEvent event) {
        switch (login_combobox.getSelectionModel().getSelectedItem()) {
            case "Student":
                Student_form.setVisible(true);
                Login_form.setVisible(false);
                Admin_form.setVisible(false);
                Teacher_form.setVisible(false);
                break;

            case "Admin":
                Login_form.setVisible(false);
                Admin_form.setVisible(true);
                Student_form.setVisible(false);
                Teacher_form.setVisible(false);
                break;

            case "Teacher":
                Login_form.setVisible(false);
                Admin_form.setVisible(false);
                Student_form.setVisible(false);
                Teacher_form.setVisible(true);
                break;
            default:
                break;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleList();


    }
}

