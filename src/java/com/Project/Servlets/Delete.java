/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.Admin;
import com.Project.Model.Components;
import com.Project.Model.log;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vijay_ravi
 */
public class Delete extends HttpServlet {

    String dept,batch;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("Processing the Request...");
            
            System.out.println("Dept "+dept+"Batch "+ batch);
            Components.executeDataManipulation(Admin.createDeleteQuery(dept, batch));
            Components.executeDataManipulation("set sql_safe_updates = 0");
            System.out.println("deptentry delete query : delete from tbldeptentry where year="+batch+" and dept=\""+dept+"\"");
            Components.executeDataManipulation("delete from tbldeptentry where year="+batch+" and dept=\""+dept+"\"");
            Components.executeDataManipulation("update tblsectionentry set "+dept+"=0 where batch="+batch);
            Components.executeDataManipulation(Admin.createDeleteInfo(dept, batch));
            Components.executeDataManipulation("delete from tblfileuploadsentry where class like \""+batch+dept+"%\"");
            
           out.println("<br>Successfully deleted data of <b>"+batch+"</b> batch of <b>"+dept+"</b> department");
           log.println("admin",log.time()+","+log.ip(request)+", Deleted data of dept"+dept+" batch "+batch);
           log.println(dept,log.time()+","+log.ip(request)+", Data of batch "+batch+" was deleted by admin");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        dept = request.getParameter("dept");
        batch = request.getParameter("batch");
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
