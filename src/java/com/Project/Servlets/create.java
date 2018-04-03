/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.Project.Model.Admin;
import com.Project.Model.Components;
import com.Project.Model.log;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vijay
 */
public class create extends HttpServlet {

    String dept,batch,regulation,noofsection;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        if (!Admin.checkDeptEntry(batch, dept)){
           out.println("dept : "+dept+"<br/>batch : "+batch+"<br/>regulation : "+regulation+" <br/>");
           out.println("create Folder : "+Admin.createFolder(dept,batch)+"<br/>");
           out.println("create Info table : "+Admin.createInfoTbl(dept, batch)+"<br/>");
           out.println("create PT table : "+Admin.createPtTable(dept,batch,regulation)+"<br/>");
           out.println("create Attendace table : "+Admin.createAttendanceTable(dept, batch)+"<br/>");
           out.println("creat Elective table : "+Admin.createElective(dept, batch, regulation)+"</br>");
           out.println("create Trigger : "+Admin.createTrigger(dept, batch)+"<br/>");
           out.println("executing query : "+Components.executeQuery(Admin.query)+"<br/>");
           //System.out.println("update tblsectionentry set "+dept+"="+noofsection+" where batch="+batch);
           out.println("inserting section entry : "+Components.executeDataManipulation("update tblsectionentry set "+dept+"="+noofsection+" where batch="+batch)+"<br/>");
           out.println("inserting dept entry : "+Admin.insertIntoDeptEntry(batch, dept));
           
           //updating for fileuploads maintenance
           for (int i = 1;i <= Integer.parseInt(noofsection);i++){
               out.println("<br>inserting file upload entry "+i+": "+Components.executeDataManipulation("insert into tblfileuploadsentry(class) values (\""+batch+dept+i+"\") "));
           }
           
           out.println("<br><strong>SUCCESS</strong>");
           log.println("admin", log.time()+","+log.ip(request)+", Table created for "+batch+" batch "+dept+" regulation "+regulation+" No.Section "+noofsection);
            }else{
            out.println("Table for Dept :<b>"+dept+"</b> of Batch :<b>"+batch+"</b> already created...");
        }
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
        regulation = request.getParameter("regulation");
        noofsection = request.getParameter("noofsection");
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(create.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(create.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(create.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
