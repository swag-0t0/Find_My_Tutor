package com.example.find_my_tutor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherMainFormController implements Initializable {


    @FXML
    private Button Signout_btn;

    @FXML
    private Button subjecthandle_addbtn;

    @FXML
    private ComboBox<String> subjecthandle_code;

    @FXML
    private ComboBox<String> subjecthandle_status;

    @FXML
    private ComboBox<String> subjecthandle_subject;

    @FXML
    private TableColumn<DataSubjectHandle, String> subjecthandle_col_dateinsert;

    @FXML
    private TableColumn<DataSubjectHandle, String> subjecthandle_col_status;

    @FXML
    private TableColumn<DataSubjectHandle, String> subjecthandle_col_subjectcode;

    @FXML
    private TableColumn<DataSubjectHandle, String> subjecthandle_col_subjectname;

    @FXML
    private TableView<DataSubjectHandle> subjecthandle_tableview;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private AlertMessage alert = new AlertMessage();

    private ObservableList<DataSubjectHandle> subjectHandleListData;

    public ObservableList<DataSubjectHandle> subjectHandleGetData() {

        ObservableList<DataSubjectHandle> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM teacher_handle";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            DataSubjectHandle dshData;

            while (result.next()) {

                dshData = new DataSubjectHandle(result.getInt("id"),
                        result.getString("subject_code"),
                        result.getString("subject"),
                        result.getDate("date"),
                        result.getString("status"));

                listData.add(dshData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    public void subjectHandleDisplayData() {
        subjectHandleListData = subjectHandleGetData();

        subjecthandle_col_subjectcode.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        subjecthandle_col_subjectname.setCellValueFactory(new PropertyValueFactory<>("subject"));
        subjecthandle_col_dateinsert.setCellValueFactory(new PropertyValueFactory<>("insertDate"));
        subjecthandle_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        subjecthandle_tableview.setItems(subjectHandleListData);
    }

    public void subjectHandleSelectItem() {

        DataSubjectHandle dshData = subjecthandle_tableview.getSelectionModel().getSelectedItem();
        int num = subjecthandle_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        subjecthandle_code.getSelectionModel().select(dshData.getSubjectCode());
        subjecthandle_subject.getSelectionModel().select(dshData.getSubject());
        subjecthandle_status.getSelectionModel().select(dshData.getStatus());

    }
    public void subjectHandleAddBtn() {

        if (subjecthandle_code.getSelectionModel().getSelectedItem().isEmpty()
                || subjecthandle_subject.getSelectionModel().getSelectedItem().isEmpty()
                ) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            String insertData = "INSERT INTO teacher_handle (subject_code, subject, date,status) "
                    + "VALUES(?,?,?,?)";
            connect = Database.connectDB();

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            try {
                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, subjecthandle_code.getSelectionModel().getSelectedItem());
                prepare.setString(2, subjecthandle_subject.getSelectionModel().getSelectedItem());
                prepare.setString(3, String.valueOf(sqlDate));
                prepare.setString(4, subjecthandle_status.getSelectionModel().getSelectedItem());

                prepare.executeUpdate();

                subjectHandleDisplayData();

                alert.successMessage("Added successfully!");

            } catch (Exception e) {
                e.printStackTrace();
            }
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


    public void subjectHandleSubjectList() {

        List<String> listS = new ArrayList<>();

        for (String data : ListData.subject) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        subjecthandle_subject.setItems(listData);
    }
    public void subjectHandleStatusList() {

        List<String> listS = new ArrayList<>();

        for (String data : ListData.statusA) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        subjecthandle_status.setItems(listData);
    }

    public void subjectHandleSubjectcodeList() {

        List<String> listS = new ArrayList<>();

        for (String data : ListData.subject_code) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        subjecthandle_code.setItems(listData);
    }


        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        subjectHandleSubjectList();
        subjectHandleSubjectcodeList();
        subjectHandleDisplayData();
        subjectHandleStatusList();
    }
}
