/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Ajax;

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

/**
 *
 * @author Vijay
 */
public class NoOfElective extends HttpServlet {

    String batch,reg,dept,sem;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/xml;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, OPTIONS, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "86400");
        
        int i=0;
//        BufferedReader br = new BufferedReader(new FileReader(BasicInfo.storageLocation+"elective\\"+dept+reg+".csv"));
//        while(br.readLine()!=null){
//            i++;
//        }
        ArrayList<String> sub = Components.fetchSemSubject(dept, reg, sem);
        ArrayList<String> electiveNo = new ArrayList<>();
        String temp=null+"";
        for(String s : sub){
            if(s.startsWith("electi")){
                electiveNo.add(s);
            }
        }
        if(electiveNo.isEmpty()){
            electiveNo.add("null");
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<root>");
            out.println("<noofelective>"+electiveNo.toString().replace("[", "").replace("]", "")+"</noofelective>");
            out.println("</root>");
        }
    }

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        batch = request.getParameter("batch");
        dept = request.getParameter("dept");
        reg = request.getParameter("regulation");
        sem = request.getParameter("sem");
        System.out.println("Ajax came for elective : "+batch+dept+reg);
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NoOfElective.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(NoOfElective.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
