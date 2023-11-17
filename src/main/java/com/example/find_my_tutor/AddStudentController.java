package com.example.find_my_tutor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {


    @FXML
    private Button Student_addbtn;

    @FXML
    private ComboBox<String> Student_class;

    @FXML
    private ComboBox<String> Student_group;

    @FXML
    private Button Student_importbtn;
    @FXML
    private ImageView Student_imageview;
    @FXML
    private TextField Student_name;

    @FXML
    private TextField Student_number;

    @FXML
    private TextField Student_pay;

    @FXML
    private ComboBox<String> Student_paymentstatus;

    @FXML
    private ComboBox<String> Student_subject;

    @FXML
    private AnchorPane main_form;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private AlertMessage alert = new AlertMessage();

    private Image image;
    public void addBtn() {

        if (Student_number.getText().isEmpty()
                || Student_name.getText().isEmpty()
                || Student_group.getSelectionModel().getSelectedItem().isEmpty()
                || Student_class.getSelectionModel().getSelectedItem().isEmpty()
                || Student_subject.getSelectionModel().getSelectedItem().isEmpty()
                || Student_pay.getText().isEmpty()
                || Student_paymentstatus.getSelectionModel().getSelectedItem().isEmpty()){

            alert.errorMessage("Please fill all blank fields.");
        } else {
            connect = Database.connectDB();

            String checkStudentNum = "SELECT * FROM students WHERE student_id = '"
                    + Student_number.getText() + "'";
            try {
                prepare = connect.prepareStatement(checkStudentNum);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert.errorMessage("Student Number: " + Student_number.getText() + " is already taken");
                } else {
                    String insertData = "INSERT INTO students "
                            + "(student_id, full_name, `group`, `class`, subject,status_payment, Date_insert, image) "
                            + "VALUES(?,?,?,?,?,?,?,?)";
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, Student_number.getText());
                    prepare.setString(2, Student_name.getText());
                    prepare.setString(3, Student_group.getSelectionModel().getSelectedItem());
                    prepare.setString(4, Student_class.getSelectionModel().getSelectedItem());
                    prepare.setString(5, Student_subject.getSelectionModel().getSelectedItem());

                    prepare.setString(6, Student_paymentstatus.getSelectionModel().getSelectedItem());
                    prepare.setString(7, String.valueOf(sqlDate));

                    String path = ListData.path;
                    path = path.replace("\\", "\\\\");

                    prepare.setString(8, path);

//                    C:\Users\WINDOWS 10\Documents\NetBeansProjects\UniversitiyManagementSystem\src\Student_Directory

                    prepare.executeUpdate();

                    alert.successMessage("Added Successfully!");
                   /*  Path transfer = Paths.get(path);
                    Path copy = Paths.get("C:\\Users\\WINDOWS 10\\Documents\\NetBeansProjects\\UniversitiyManagementSystem\\src\\Student_Directory\\"
                            + student_number.getText() + ".jpg");

                    Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);



                   clearFields();*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }




    public void importBtn() {

        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image", "*.jpg", "*.jpeg", "*.png"));

        File file = open.showOpenDialog(Student_importbtn.getScene().getWindow());

        if (file != null) {
            ListData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 100, 113, false, true);
            Student_imageview.setImage(image);
        }
    }

    public void groupList() {
        List<String> listG = new ArrayList<>();

        for (String data : ListData.group) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        Student_group.setItems(listData);
    }
    public void student_classList() {
        List<String> listC = new ArrayList<>();

        for(String data : ListData.student_class) {
            listC.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listC);
        Student_class.setItems(listData);
    }

    public void subjectList() {
        List<String> listS = new ArrayList<>();

        for (String data : ListData.subject) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        Student_subject.setItems(listData);
    }
    public void paymentList() {
        List<String> listP = new ArrayList<>();

        for (String data : ListData.paymentStatus) {
            listP.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listP);
        Student_paymentstatus.setItems(listData);
    }


    public void displayStudentNumber() {
        HelloController control=new HelloController();
        int getnumber = control.studentIDGenerator();

        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyy");

        String getyear = format.format(date) + "0000";

        int getStudentNum = Integer.parseInt(getyear) + getnumber;

        Student_number.setText(String.valueOf(getStudentNum));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupList();
        student_classList();
        subjectList();
        paymentList();
    }
}
