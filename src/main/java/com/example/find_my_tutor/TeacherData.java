package com.example.find_my_tutor;


import java.sql.Date;

public class TeacherData {

    private Integer id;
    private String teacherID;
    private String fullName;
    private String gender;
    private String yearExperience;
    private String experience;
    private String subject;
    private Double salary;
    private String image;
    private Date dateInsert;
    private Date dateUpdate;
    private Date dateDelete;
    private String status;
    private String salaryStatus;

    public TeacherData(Integer id, String teacherID, String fullName, String gender,
                       String yearExperience, String experience, String subject, Double salary,
                       String salaryStatus,
                       String image, Date dateInsert, Date dateUpdate, Date dateDelete, String status) {
        this.id = id;
        this.teacherID = teacherID;
        this.fullName = fullName;
        this.gender = gender;
        this.yearExperience = yearExperience;
        this.experience = experience;
        this.subject = subject;
        this.salary = salary;
        this.salaryStatus = salaryStatus;
        this.image = image;
        this.dateInsert = dateInsert;
        this.dateUpdate = dateUpdate;
        this.dateDelete = dateDelete;
        this.status = status;
    }

    public TeacherData(Integer id, String teacherID, String fullName,
                       String gender, Double salary, String salaryStatus, Date dateInsert,
                       Date dateUpdate, String status) {
        this.id = id;
        this.teacherID = teacherID;
        this.fullName = fullName;
        this.gender = gender;
        this.salary = salary;
        this.salaryStatus = salaryStatus;
        this.dateInsert = dateInsert;
        this.dateUpdate = dateUpdate;
        this.status = status;
    }

    public TeacherData(Integer id, String teacherID, String fullName,
                       String gender, String yearExperience, Date dateInsert) {
        this.id = id;
        this.teacherID = teacherID;
        this.fullName = fullName;
        this.gender = gender;
        this.yearExperience = yearExperience;
        this.dateInsert = dateInsert;
    }
    public TeacherData(Integer id, String teacherID, String fullName,
                        String yearExperience, Double salary) {
        this.id = id;
        this.teacherID = teacherID;
        this.fullName = fullName;
        this.yearExperience = yearExperience;
        this.salary = salary;
    }


    public String getSalaryStatus() {
        return salaryStatus;
    }

    public Integer getId() {
        return id;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getYearExperience() {
        return yearExperience;
    }

    public String getExperience() {
        return experience;
    }

    public String getSubject() {
        return subject;
    }

    public Double getSalary() {
        return salary;
    }

    public String getImage() {
        return image;
    }

    public Date getDateInsert() {
        return dateInsert;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public Date getDateDelete() {
        return dateDelete;
    }

    public String getStatus() {
        return status;
    }

}
