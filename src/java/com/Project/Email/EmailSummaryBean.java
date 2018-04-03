/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Email;

import org.json.simple.JSONArray;

/**
 *
 * @author Admin
 */
public class EmailSummaryBean {
    private String rollNo,Name,toMailID,timeStamp;

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getToMailID() {
        return toMailID;
    }

    public void setToMailID(String toMailID) {
        this.toMailID = toMailID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public JSONArray getJSON(){
        JSONArray rootList = new JSONArray();
        rootList.add(rollNo);
        rootList.add(Name);
        rootList.add(toMailID);
        rootList.add(timeStamp);        
        return rootList;
    }
}
