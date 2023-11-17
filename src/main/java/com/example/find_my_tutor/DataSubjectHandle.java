package com.example.find_my_tutor;

import java.util.Date;

public class DataSubjectHandle {

    private Integer id;
    private String subjectCode;
    private String subject;
    private Date insertDate;
    private String status;

    public DataSubjectHandle(Integer id, String subjectCode,
                             String subject, Date insertDate, String status) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subject = subject;
        this.insertDate = insertDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubject() {
        return subject;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public String getStatus() {
        return status;
    }

}
