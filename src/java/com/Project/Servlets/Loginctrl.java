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
public class Loginctrl extends HttpServlet {

    String dept,ed;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String query = null,temp;
        try (PrintWriter out = response.getWriter()) {            
            if(ed.equals("enable")){
                query = "update tbllogin set ed = 1 where dept=\""+dept+"\"";
                temp = " department UPDATION option <b>Enabled</b>";
            }else {
                query = "update tbllogin set ed = 0 where dept=\""+dept+"\"";
                temp = " department UPDATION option <b>Disabled</b>";
            }
            System.out.println("Query : "+query);
            int i = Components.executeDataManipulation(query);
            if(i == -1){
                out.println("<strong style=\" color:red;\" >Update failed</strong>");
            }else{
                out.println("<strong>"+dept.toUpperCase()+"</strong> "+temp);
                log.println("admin",log.time()+","+log.ip(request)+", updating accessControl for dept :"+dept+" "+ed+"ed");
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
        dept = request.getParameter("dept");
        ed =  request.getParameter("ed");
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Loginctrl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Loginctrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
