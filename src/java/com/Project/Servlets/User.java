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
public class User extends HttpServlet {
    
    String type,dept,deptff,id,password,ed;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String query;
            Components.excecuteDDL("set sql_safe_updates=0");
            if(type.startsWith("cr")){
                query = "insert into tbllogin values(\""+id+"\",\""+password+"\",\""+dept+"\",\""+deptff+"\","+ed+")";
                Components.executeDataManipulation(query);
                out.println("Login for Id : <b>"+id+"</b> successfully created");
                log.println("admin",log.time()+","+log.ip(request)+", New User Created for dept "+dept+" ("+deptff+") details  id "+id+" password "+password);
            }else{
                query = "delete from tbllogin where id=\""+id+"\"";
                Components.executeDataManipulation(query);
                out.println("Successfully deleted Login-Id : <b>"+id+"</b>");
                log.println("admin",log.time()+","+log.ip(request)+", User Deleted for ID "+id);
            }
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
        type = request.getParameter("type");
        
        if(type.equals("create")){
            id = request.getParameter("id");
            password = request.getParameter("password");
            dept = request.getParameter("dept");
            deptff = request.getParameter("deptff");
            ed = request.getParameter("ed");
        }else{
            id = request.getParameter("id");
        }
        
        try{
            HttpSession s = request.getSession();
            String d = (String)s.getAttribute("dep");
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
