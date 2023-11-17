package com.example.find_my_tutor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentMainFormController implements Initializable {

    @FXML
    private Circle circle_image;

    @FXML
    private Button signout_btn;

    @FXML
    private TableColumn<TeacherData,String> student_col_experience;

    @FXML
    private TableColumn<TeacherData, String> student_col_name;

    @FXML
    private TableColumn<TeacherData, String> student_col_teacherid;
    @FXML
    private TableColumn<TeacherData, String> student_col_salary;
    @FXML
    private Label student_id;

    @FXML
    private Button studentinformation_btn;

    @FXML
    private Label teacher_id;

    @FXML
    private Label teacher_name;

    @FXML
    private TableView<TeacherData> student_tableview;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private AlertMessage alert=new AlertMessage();


    public ObservableList<TeacherData> teacherSetData() {

        ObservableList<TeacherData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM teachers";

        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            TeacherData dsh;

            while (result.next()) {

                dsh = new TeacherData(result.getInt("id"),
                        result.getString("teacher_id"),
                        result.getString("full_name"),
                        result.getString("year_experience"),
                        result.getDouble("salary"));

                listData.add(dsh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<TeacherData> teacherListData;

    public void teacherDisplayData() {
        teacherListData = teacherSetData();

        student_col_teacherid.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        student_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        student_col_experience.setCellValueFactory(new PropertyValueFactory<>("yearExperience"));
        student_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        student_tableview.setItems(teacherListData);
    }

    public void teacherSelectData() {
        TeacherData dsh = student_tableview.getSelectionModel().getSelectedItem();
        int num = student_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        String sql = "SELECT * FROM teachers WHERE teacher_id = '"
                + dsh.getTeacherID() + "'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {

                String path = "File:" + result.getString("image");

                Image image = new Image(path, 164, 73, false, true);
                circle_image.setFill(new ImagePattern(image));
                teacher_id.setText(result.getString("teacher_id"));
                teacher_name.setText(result.getString("full_name"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void studentIDDisplay() {

        String sql = "SELECT * FROM all_users WHERE username = '"
                + ListData.student_username + "'";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                student_id.setText(result.getString("student_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void logoutBtn() {

        try {
            if (alert.confirmMessage("Are you sure you want to logout?")) {
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDoccument.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();

                signout_btn.getScene().getWindow().hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ///teacherSetData();
        teacherDisplayData();
        studentIDDisplay();
    }
}
