/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.POJO;

import java.util.ArrayList;

/**
 *
 * @author Vijay
 */
public class ForSMS {
    String name, rollno, parentMobile;// result is ( PASS / FAIL / ABSENT ) for each subject 

    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }
    float AttendancePercentage;
    ArrayList<String> marksAndResult;

    public ArrayList<String> getMarksAndResult() {
        return marksAndResult;
    }

    public void setMarksAndResult(ArrayList<String> marksAndResult) {
        this.marksAndResult = marksAndResult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public float getAttendancePercentage() {
        return AttendancePercentage;
    }

    public void setAttendancePercentage(float AttendancePercentage) {
        this.AttendancePercentage = AttendancePercentage;
    }
    
}
