/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.BasicInfo;
import com.Project.Model.Components;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author vijay_ravi
 */
public class Template extends HttpServlet {

    String type,dept,regulation,sem;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
                       
           if(type.startsWith("pt")){
               
               System.out.println("type : "+type+" sem : "+sem+" dept : "+dept+" regulation : "+regulation);
               
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("content-Disposition", "attachment;filename="+dept+"-"+type+"-"+"sem"+sem+".xlsx");
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet spreadsheet = workbook.createSheet(type);
                XSSFRow row = spreadsheet.createRow(0);

                ArrayList<String> sub = Components.fetchSemSubject(dept, regulation,sem);
                int cellid=0;
                ArrayList<String> fnl = new ArrayList<>();
                fnl.add("Reg no");
                fnl.add("Name");

                for (String s : sub){
                    fnl.add(s);
                }

                for (String subject : fnl){
                    Cell cell = row.createCell(cellid++);

                    cell.setCellValue(subject);
                }
                workbook.write(response.getOutputStream());
                workbook.close();
           }else if(type.startsWith("attendance")){
               
                System.out.println("type : "+type+" sem :"+sem);
               
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("content-Disposition", "attachment;filename=\"attendance.xlsx\"");
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet spreadsheet = workbook.createSheet(type);
                XSSFRow row = spreadsheet.createRow(0);

                int cellid=0;
                ArrayList<String> fnl = new ArrayList<>();
                fnl.add("Reg no");
                fnl.add("Name");
                fnl.add("Total Hour ");
                fnl.add("Attended Hour");


                for (String data : fnl){
                    Cell cell = row.createCell(cellid++);

                    cell.setCellValue(data);
                }
                workbook.write(response.getOutputStream());
                workbook.close();
           }else if(type.equals("rollToReg")){
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("content-Disposition", "attachment;filename=\"RollNo to RegNo.xlsx\"");
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet spreadsheet = workbook.createSheet(type);
                XSSFRow row = spreadsheet.createRow(0);
                
                Cell cell0 = row.createCell(0);
                cell0.setCellValue("Roll No");
                Cell cell1 = row.createCell(1);
                cell1.setCellValue("Register No");
                
                workbook.write(response.getOutputStream());
                workbook.close();
           }else{
               
                   System.out.println("inside info");
                   try (PrintWriter out = response.getWriter()) {
                   response.setContentType("APPLICATION/OCTET-STREAM"); 
                   response.sendRedirect("/fileupload/res/info.xlsx");                 
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
        if(type.startsWith("term")){
            sem = request.getParameter("sem");
        }else if(type.startsWith("pt")){
            sem = request.getParameter("sem");
            dept =request.getParameter("dept");
            regulation = request.getParameter("regulation"); 
        }
        System.out.println(type+sem+dept+regulation);
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
