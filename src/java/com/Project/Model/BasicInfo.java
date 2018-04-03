/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author Vijay
 */
//  /home/vijay_ravi/Desktop/Project_InternalMarks/
public class BasicInfo {
    
    public static String dbAddress = "";
    public static String username = "";
    public static String password = "";
    public static String storageLocation = "";
    public static String sqlTblKeyword = "create table if not exists tbl";
    
    
    public static String basicInfo = "(rollno bigint not null primary key, name varchar(100),f_name varchar(100),m_name varchar(100),\n" +
"p_addr_line1 varchar(100),p_addr_line2 varchar(100),p_addr_line3 varchar (100),\n" +
"c_addr_line1 varchar(100),c_addr_line2 varchar(100),c_addr_line3 varchar (100),\n" +
"f_mobile varchar(20),m_mobile varchar(20),s_mobile varchar(20),f_mail varchar(100), \n" +
"s_mail varchar(100), gender varchar(10),dob varchar(10), ds_h varchar(20), section varchar(10))";
    
    public static String adminDeptEntry = "create table tbldeptentry(year int,dept varchar(20))";
    
    public static String attendance = "(rollno bigint,thoursem1 int,sem1 int,thoursem2 int,sem2 int,thoursem3 int,sem3 int,"
            + "thoursem4 int,sem4 int,thoursem5 int,sem5 int,thoursem6 int,sem6 int,thoursem7 int,sem7 int,"
            + "thoursem8 int,sem8 int,foreign key(rollno) references tbl";
    
    public  static Connection con() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("dbAddress = "+dbAddress);
        Connection con = DriverManager.getConnection(dbAddress,username,password);
        return con;        
    }
    
}


