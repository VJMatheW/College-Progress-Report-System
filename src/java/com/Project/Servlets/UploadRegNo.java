/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.CheckList;
import com.Project.Model.Components;
import com.Project.Model.QueryGenerator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Vijay
 */
@MultipartConfig
public class UploadRegNo extends HttpServlet {

    String batch,dept;
    Part filepart;
    FileInputStream fis;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadRegNo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadRegNo at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        PrintWriter out=null;
        batch = request.getParameter("batch");
        filepart = request.getPart("file");
        HttpSession s = request.getSession();
        try{
            dept = (String)s.getAttribute("dept");
        }catch(NullPointerException ev){
            System.out.println("Exception : "+ev);
            response.sendRedirect("./Html/session_exp.html");
        }
        try{            
            System.out.println("Batch :"+batch+" Dept : "+dept);
            response.setContentType("text/html");
            out=response.getWriter();
            if(CheckList.checkEnableOrDisabled((int)s.getAttribute("ed"))){
                fis = (FileInputStream)filepart.getInputStream();
                ArrayList<String> query = QueryGenerator.rollNoToRegNoQuery(dept, batch, fis);
                for(String si : query){
                    System.out.println(si);
                }
                if(!query.get(0).startsWith("Error : ")){
                    boolean status = Components.executeQuery(query);
                    if(status){
                        out.println("Successfully Updated Register No. for batch : <b>"+batch+"</b>");
                    }else{
                        out.println("Updation UnSuccessful");
                    }
                }else{
                    out.println("<b>"+query.get(0).replace("Error : ","")+"</b>");
                }
            }else{
                out.println("Updation is <span style=\"color:red;\"><b>Disabled</b></span>. Contact <b>Admin</b> for Support");
            }
        }catch(NullPointerException n){
            if(out!=null){
                out.close();
            }
        }catch(Exception e){
            System.out.println("Exception in POST of UploadRegNo Servlet "+e);
            out.println("Error Occured "+e);
            if(out!=null){
                out.close();
            }
        }     
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
