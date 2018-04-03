/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.Components;
import com.Project.Model.FormTable;
import com.Project.Model.QueryGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class ResultAnalysis extends HttpServlet {

    String batch,regulation,sem,section,dept,type;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            ArrayList<String> subjects = Components.fetchSemSubject(dept, regulation, sem);
            System.out.println("Fetched Subjects from fetchSemSubject :"+subjects);
            
            String query = QueryGenerator.createMarksRetriveQuery(subjects, dept, batch, type, section);
            System.out.println("Generated query for retriving : "+query);
            
            ResultSet rs = Components.executeSelectQuery(query);
            
            ArrayList<String> subjectName = QueryGenerator.subjectNameRetrive(subjects, dept, regulation);
            
            try{
            rs.next();
            rs.beforeFirst();
            
            ArrayList<String> data = FormTable.createHeader(rs);            
            data.add(subjects.size()+"");
            
            request.setAttribute("tableHead",data);
            request.setAttribute("subName",subjectName);
            RequestDispatcher rd = request.getRequestDispatcher("./jsp/ResultAnalysisView.jsp");
            rd.forward(request, response);
            
            }catch(Exception e){
                out.println("Oops... something went wrong  :(  !!!");
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
        batch = request.getParameter("batch");
        regulation = request.getParameter("regulation");
        sem = request.getParameter("sem");
        section = request.getParameter("sec");
        type = request.getParameter("type");
        try {                       
            HttpSession s = request.getSession();
            dept = s.getAttribute("dept").toString();        
            processRequest(request, response);
            
        } catch (Exception e){
            RequestDispatcher rd = request.getRequestDispatcher("./Html/session_exp.html");
            rd.forward(request, response);
        }
        
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
