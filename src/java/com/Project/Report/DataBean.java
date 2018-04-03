/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Report;

/**
 *
 * @author vijay_ravi
 */
public class DataBean {
    
    String std_name,rollno,mark1,mark2,mark3,mark4,mark5,mark6,res1,res2,res3,res4,res5,res6;
    String fname,addr1,addr2,addr3,attd_p,attd_percentage,attd_ab,attd_wh,f_mobile,arrear_no;
    String s1,s2,s3,s4,s5,s6,sf1,sf2,sf3,sf4,sf5,sf6;

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public String getS4() {
        return s4;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }

    public String getS5() {
        return s5;
    }

    public void setS5(String s5) {
        this.s5 = s5;
    }

    public String getS6() {
        return s6;
    }

    public void setS6(String s6) {
        this.s6 = s6;
    }

    public String getSf1() {
        return sf1;
    }

    public void setSf1(String sf1) {
        this.sf1 = sf1;
    }

    public String getSf2() {
        return sf2;
    }

    public void setSf2(String sf2) {
        this.sf2 = sf2;
    }

    public String getSf3() {
        return sf3;
    }

    public void setSf3(String sf3) {
        this.sf3 = sf3;
    }

    public String getSf4() {
        return sf4;
    }

    public void setSf4(String sf4) {
        this.sf4 = sf4;
    }

    public String getSf5() {
        return sf5;
    }

    public void setSf5(String sf5) {
        this.sf5 = sf5;
    }

    public String getSf6() {
        return sf6;
    }

    public void setSf6(String sf6) {
        this.sf6 = sf6;
    }

    public String getArrear_no() {
        return arrear_no;
    }

    public void setArrear_no(String arrear_no) {
        this.arrear_no = arrear_no;
    }

    public String getAttd_wh() {
        return attd_wh;
    }

    public String getF_mobile() {
        return f_mobile;
    }

    public void setF_mobile(String f_mobile) {
        this.f_mobile = f_mobile;
    }

    public void setAttd_wh(String attd_wh) {
        this.attd_wh = attd_wh;
    }

    public String getAttd_ab() {
        return attd_ab;
    }

    public void setAttd_ab(String attd_ab) {
        this.attd_ab = attd_ab;
    }

    public String getRes1() {
        return res1;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr3() {
        return addr3;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getAttd_p() {
        return attd_p;
    }

    public void setAttd_p(String attd_p) {
        this.attd_p = attd_p;
    }

    public String getAttd_percentage() {
        return attd_percentage;
    }

    public void setAttd_percentage(String attd_percentage) {
        this.attd_percentage = attd_percentage;
    }

    public void setRes1(String res1) {
        this.res1 = res1;
    }

    public String getRes2() {
        return res2;
    }

    public void setRes2(String res2) {
        this.res2 = res2;
    }

    public String getRes3() {
        return res3;
    }

    public void setRes3(String res3) {
        this.res3 = res3;
    }

    public String getRes4() {
        return res4;
    }

    public void setRes4(String res4) {
        this.res4 = res4;
    }

    public String getRes5() {
        return res5;
    }

    public void setRes5(String res5) {
        this.res5 = res5;
    }

    public String getRes6() {
        return res6;
    }

    public void setRes6(String res6) {
        this.res6 = res6;
    }

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getMark1() {
        return mark1;
    }

    public void setMark1(String mark1) {
        if(mark1 == "" || mark1 == "AB"){
            setRes1(""); 
        }else if(Integer.parseInt(mark1)< 50){
            setRes1("FAIL");
        }else{
            setRes1("PASS");
        }
        this.mark1 = mark1;
    }

    public String getMark2() {
        return mark2;
    }

    public void setMark2(String mark2) {
        if(mark2 == "" || mark2 == "AB"){
            setRes2(""); 
        }else if(Integer.parseInt(mark2)<50){
            setRes2("FAIL");
        }else{
            setRes2("PASS");
        }
        this.mark2 = mark2;
    }

    public String getMark3() {
        return mark3;
    }

    public void setMark3(String mark3) {
        if(mark3 == "" || mark3 == "AB"){
            setRes3(""); 
        }else if(Integer.parseInt(mark3)<50){
            setRes3("FAIL");
        }else{
            setRes3("PASS");
        }
        this.mark3 = mark3;
    }

    public String getMark4() {
        return mark4;
    }

    public void setMark4(String mark4) {
        if(mark4 == "" || mark4 == "AB"){
            setRes4(""); 
        }else if(Integer.parseInt(mark4)<50){
            setRes4("FAIL");
        }else{
            setRes4("PASS");
        }
        this.mark4 = mark4;
    }

    public String getMark5() {
        return mark5;
    }

    public void setMark5(String mark5) {
        if(mark5 == "" || mark5 == "AB"){
            setRes5(""); 
        }else if(Integer.parseInt(mark5)<50){
            setRes5("FAIL");
        }else{
            setRes5("PASS");
        }
        this.mark5 = mark5;
    }

    public String getMark6() {
        return mark6;
    }

    public void setMark6(String mark6) {
        if(mark6 == "" || mark6 == "AB"){
            setRes6(""); 
        }else
        if(Integer.parseInt(mark6)<50){
            setRes6("FAIL");
        }else{
            setRes6("PASS");
        }
        this.mark6 = mark6;
    }
    
}
