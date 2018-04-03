/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.BasicInfo;
import com.Project.Model.Components;
import com.Project.Model.log;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vijay
 */
public class Login extends HttpServlet {

   
    String id=null,password=null;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {       
        response.setContentType("text/html;charset=UTF-8");
        ResultSet rs = null;
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
            System.out.println("IP-ADDRESS " +remoteAddr+" "+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
            
            
            if (id.equals(null)|| password.equals(null))
            {
                request.setAttribute("error", " Invalid Login-ID/Password");
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.include(request, response);
            }else{
                Statement st = BasicInfo.con().createStatement();
                rs = st.executeQuery("select * from tbllogin where id='"+id+"' and password='"+password+"'");
                if (rs.next()){
                    String dept = rs.getString(3);                    
                    if (rs.wasNull())
                    {
                        dept="admin";
                        session.setAttribute("dept", "admin");
                        log.println(dept,log.time()+","+remoteAddr+",Logged In");
                        rs.getStatement().getConnection().close();
                        RequestDispatcher rd = request.getRequestDispatcher("./jsp/admin/Admin.jsp");
                        rd.forward(request, response);
                                
                    }
                    int enableOrDisabled = rs.getInt(5);
                    session.setAttribute("dept",dept);
                    session.setAttribute("ed", enableOrDisabled);
                    log.println(dept,log.time()+","+remoteAddr+",Logged In");
                    session.setAttribute("deptff", rs.getString(4).toUpperCase());
                    System.out.println("fetched dept from session  = "+session.getAttribute("dept"));
                    //response.sendRedirect("./jsp/Home.jsp");
                    RequestDispatcher rd = request.getRequestDispatcher("/jsp/Home.jsp");
                    rd.forward(request, response);
                    rs.getStatement().getConnection().close();
                }else{
                request.setAttribute("error", " Invalid Login-ID/Password");
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.include(request, response);
                rs.getStatement().getConnection().close();
                }
            }
        }finally{
            if(rs!= null){
                //rs.getStatement().getConnection().close();
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			String clas,type;
			int value;
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			//out.println("Hello world");
			
			clas = request.getParameter("class");
			type = request.getParameter("type");
			value = Integer.parseInt(request.getParameter("value"));
			//out.println(clas+" "+type+" "+value);
			try{
				Components.executeDataManipulation("set sql_safe_updates=0");
				Components.executeDataManipulation("update tblfileuploadsentry set "+type+"="+value+" where class='"+clas+"'");
				log.println("admin",log.time()+","+log.ip(request)+", Updated DataUploads Entry "+type+" = "+value+" for class = "+clas);
				out.println("Updation of <b>"+type.toUpperCase()+"</b> of class <b>"+clas.toUpperCase()+"</b> is Successful ");
				
			}catch(Exception e){
				out.println("Updation Not Successful");
			}        
            //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            id = request.getParameter("id");
            password = request.getParameter("password");
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
