/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.Components;
import com.Project.Model.log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.catalina.tribes.util.Arrays;

/**
 *
 * @author vijay_ravi
 */
@MultipartConfig
public class UploadSubjects extends HttpServlet {

    String dept,regulation,type;
    Part file;
    InputStream in = null;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (in != null){
                
                String query,tblname;
                
                if(type.equals("subcode")) {
                    tblname = "semsubjects"+dept+regulation;
                    Components.excecuteDDL("drop table if exists "+tblname);
                    query = "create table if not exists "+tblname+" (sem varchar(5),sub1 varchar(20),sub2 varchar(20),sub3 varchar(20),sub4 varchar(20),sub5 varchar(20),sub6 varchar(20))";
                    boolean r = Components.excecuteDDL(query);
                    System.out.println(r);

                    if (!r){                    
                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        br.readLine();
                        try{
                        while((line = br.readLine())!= null){
                            //System.out.println("Line : "+line);
                            String row[] = line.split(",");
                            row[0]="\""+row[0];
//                            for(String a : row){
//                                System.out.println(a);
//                            }
                           // System.out.println("ROw value : "+Arrays.toString(row).replace("{","").replace("}", ""));
                           System.out.println("String of array : "+Arrays.toString(row));
                            query = "insert into "+tblname+" values("+Arrays.toString(row).replace("{","").replace("}","").replace(",", "\",\"")+"\")";
                            System.out.println(query);
                           
                                Components.executeDataManipulation(query);                    
                        }

                        out.println("<br/> Uploaded subject_code of <b>"+dept.toUpperCase()+"</b> dept, regulation : "+regulation);
                        }catch(Exception w){
                                System.out.println("Error : "+w);
                                out.println("<br><strong>Error Occurred</strong>");
                            }
                        }else{
                        out.println("table creation for <b>subjectCode</b> failed");
                    }
                    log.println("admin",log.time()+","+log.ip(request)+", subcode for dept "+dept+" regulation "+regulation+" uploaded");
                    }
                
                if(type.equals("subname")){
                    tblname = "subjectsname"+dept+regulation;
                    Components.excecuteDDL("drop table if exists "+tblname);
                    query = "create table if not exists "+tblname+"(subjectcode varchar(10) primary key,subjectname varchar(200),shortform varchar(50),faculty varchar(200))";
                    boolean r = Components.excecuteDDL(query);
                    System.out.println(r);
                    
                    if(!r){
                        out.println("<b>Uploading...</b>");
                        String line;                        
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        br.readLine();
                        try{
                        while((line = br.readLine())!= null){
                            String row[] = line.split(",");
                           // System.out.println("Row : "+row[0]+row[1]);
                            row[0]="\""+row[0];
                            query = "insert into "+tblname+" values("+Arrays.toString(row).replace("{", "").replace("}", "").replace(",", "\",\"")+"\")";
                            System.out.println(query);
                            Components.executeDataManipulation(query);                                                      
                        }
                        out.println("<br/>Successfully Uploaded subject_name of <b>"+dept.toUpperCase()+"</b> dept, regulation : "+regulation);
                        }catch(Exception e){
                            System.out.println("Error : "+e);
                            out.println("<br><strong>Error Occurred</strong>");
                        }
                    }else{
                        out.println("table creation for <b>subjectName</b> failed");
                    }
                    log.println("admin",log.time()+","+log.ip(request)+", subName for dept "+dept+" regulation "+regulation+" uploaded");
                }
            }else{
                out.println("File should be Uploaded in <b>CSV</b> format");
                }
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        type = request.getParameter("type");
        dept = request.getParameter("dept");
        regulation = request.getParameter("regulation");
        file = request.getPart("file");
        System.out.println(file.getContentType());
        if ("application/vnd.ms-excel".equals(file.getContentType())){
            in = file.getInputStream();
        }
        try {
            processRequest(request, response);           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UploadSubjects.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UploadSubjects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
