package com.example.find_my_tutor;

import java.util.Date;

public class StudentData {
    private Integer id;
    private String studentID;
    private String fullName;

    private String group;  //As year
    private String student_class;   //As course
    private String subject;
    private String payment;
    private String statusPayment;
    private String image;
    private Date dateInsert;
    private Date dateUpdate;
    private Date dateDelete;
    //private String status;


    public StudentData(Integer id, String studentID, String fullName,String group, String student_class,
                       String payment, String statusPayment, String image, Date dateInsert,
                       Date dateUpdate, Date dateDelete,String subject) {

        this.id = id;
        this.studentID = studentID;
        this.fullName = fullName;
        this.group = group;
        this.student_class = student_class;
        this.subject = subject;
        this.payment = payment;
        this.statusPayment = statusPayment;
        this.image = image;
        this.dateInsert = dateInsert;
        this.dateUpdate = dateUpdate;
        this.dateDelete = dateDelete;

    }

    public StudentData(Integer id, String studentID, String fullName, String student_class, String group,
                       String payment, String statusPayment, Date dateInsert,String subject) {
        this.id = id;
        this.studentID = studentID;
        this.fullName = fullName;
        this.group = group;
        this.student_class = student_class;
        this.subject = subject;
        this.payment = payment;
        this.statusPayment = statusPayment;
        this.dateInsert = dateInsert;

    }
        public Integer getId() {
            return id;
        }

        public String getStudentID() {
            return studentID;
        }

        public String getFullName() {
            return fullName;
        }

        public String getGroup() {
            return group;
        }

        public String getStudent_class() {

            return student_class;
        }

        public String getPayment() {
            return payment;
        }

        public String getSubject() {
            return subject;
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

}
