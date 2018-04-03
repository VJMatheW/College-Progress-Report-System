/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Ajax;

import com.Project.Model.BasicInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vijay_ravi
 */
public class SectionAjax extends HttpServlet {
    
    String batch,dept,sectionfromdb;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/xml;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, OPTIONS, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "86400");
        Connection con;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            String sql = "select "+dept+" from tblsectionentry where batch="+batch;
            System.out.println("query : "+sql);
            con = BasicInfo.con();
            Statement s = con.createStatement();
            
            if(dept != "na") {
                
                try{
                    ResultSet rs = s.executeQuery(sql);
                    int i=0;
                    while(rs.next()){
    //                rs.beforeFirst();
                     i = rs.getInt(1);
                     if(rs.wasNull()){
                         i = 0;
                     }
                    }
                    sectionfromdb = ""+i;
                    con.close();
                    System.out.println("Sectionfromdb : "+sectionfromdb);
                }catch(Exception e){
                    sectionfromdb = "na";
                    con.close();
                    System.out.println("from process request try catch block Exception:"+e);
                }
            }
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<root>");
            out.println("<dept>"+dept+"</dept>");
            out.println("<section>"+sectionfromdb+"</section>");
            out.println("</root>");
            }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
        batch = request.getParameter("batch");
        dept = request.getParameter("dept"); 
        
        System.out.println("dept from Ajax : "+dept);
        
        processRequest(request, response);
        
        }catch(Exception e){
            System.out.println(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
