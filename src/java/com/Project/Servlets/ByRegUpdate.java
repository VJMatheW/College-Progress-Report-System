/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.Components;
import com.Project.Model.log;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vijay_ravi
 */
public class ByRegUpdate extends HttpServlet {

    String batch,type,dept,regNo,thour,ahour,sem;
    String[] info,subject,marks,option,optionval;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           Components.excecuteDDL("set sql_safe_updates=0");
           String query;
           if(type.startsWith("pt")){
                for(int i =0;i<subject.length;i++){
                    if(marks[i].equals("AB")||marks[i].equals("ab")){
                        query="update tbl"+dept+batch+type+" set "+subject[i]+"="+null+" where rollno="+regNo ;                  
                    }else{
                        query="update tbl"+dept+batch+type+" set "+subject[i]+"="+marks[i]+" where rollno="+regNo ;
                     }
                    System.out.println(query);
                    Components.executeDataManipulation(query);
                 }
                 out.println("<h3>Marks of "+regNo+" Successfully Updated...</h3>");
            }else if (type.startsWith("term")){
                query = "update tbl"+dept+batch+type+" set sem"+sem+"="+ahour+" where rollno="+regNo;
                Components.executeDataManipulation(query);
                out.println("<h3>Attendance of "+regNo+" Successfully Updated...</h3>");
            }else{
                for(int i=0;i<optionval.length;i++){
                    query="update tbl"+dept+batch+type+" set "+option[i]+"=\""+optionval[i].toUpperCase()+"\" where rollno="+regNo;
                    Components.executeDataManipulation(query);
                }
                out.println("<h3>Information update for "+regNo+" is Successful...</h3>");
            }
           log.println(dept, log.time()+","+log.ip(request)+", Updating "+type+" for rollNo :"+regNo);
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String temp = request.getParameter("info");
        info = temp.split(",");
        batch = info[1]; type=info[0]; regNo=info[2];
        
        if(type.startsWith("pt")){
            temp = request.getParameter("subject");
            subject = temp.split(",");
            marks = request.getParameterValues("marks");
        }else if(type.startsWith("term")){ 
            thour = request.getParameter("thour");
            ahour = request.getParameter("ahour");
            sem = request.getParameter("sem");
        }else{
            temp = request.getParameter("option");
            option = temp.split(",");
            optionval = request.getParameterValues("optionval");
        }
        
        try{
            HttpSession s = request.getSession();
            dept = s.getAttribute("dept").toString();
            processRequest(request, response);
        }catch(Exception e){
            RequestDispatcher rd = request.getRequestDispatcher("./Html/session_exp.html");
            rd.forward(request, response);
        }
        
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
