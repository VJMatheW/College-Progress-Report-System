/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.CheckList;
import com.Project.Model.Components;
import com.Project.Model.SendEmail;
import com.Project.POJO.ForSMS;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vijay
 */
public class SMS extends HttpServlet {

    String batch,section,regulation,sem,type,dept;
    private HashMap<String,String> subFF = new HashMap<String,String>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");        
        PrintWriter out = response.getWriter();
        ArrayList<String> subjectCode = new  ArrayList<String>();
        String uniqueIdentifier = "",uniquePT = "";
        ResultSet rs =null;
            
        Thread fetch = new Thread(){
            public void run(){
                try{
                    subFF = Components.fetchWholeSubCodeAndFF(dept, regulation);
                }catch(Exception e){
                    System.out.println("Error in Thread of Prepare Mail ");
                    out.println("Subjects full form not Uploaded contact <b>Admin</b>");
                }
            }
        };
        fetch.start();
        out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link type=\"text/css\" rel=\"stylesheet\" href=\"/fileupload/Stylesheet/bootstrap/bootstrap.min.css\"> \n" +
        "<link type=\"text/css\" rel=\"stylesheet\" href=\"/fileupload/Stylesheet/general.css\">");
            out.println("<style type=\"text/css\">\n" +                      
"            .table-striped>tbody>tr:nth-of-type(odd){\n" +
"                background-color:#ecddee;\n" +
"            }\n" +
"            .table-striped>tbody>tr:nth-of-type(even), .table-striped>thead>tr>th{\n" +
"                background-color:white;\n" +
"            }\n" +
"            .table-striped tr td,.table-striped tr th{\n" +
"                border-color: black !important;\n" +
"            }\nbody{\n" +
"                background-color: #ecfdeb !important;\n" +
"            }"+            
"        </style>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h3><strong>Message Details</strong></h3>");
            out.println("<table class=\"table table-striped table-bordered\" >\n" +
"                        <thead>\n" +
"                            <tr><th>Register Number</th><th>Name</th><th>Parent's Contact Number</th></tr>\n" +
"                        </thead>\n" +
"                        <tbody>");
            
                       
        try{            
            subjectCode = Components.fetchSemSubject(dept, regulation, sem);
            int noOfSubject = subjectCode.size();
            ArrayList<ForSMS> toSession = new ArrayList<ForSMS>();
            // on getMessageData - true( for email ) & false ( for message )
            rs = Components.getMessageData(false,sem, dept, batch, section, type, subjectCode);
            ResultSetMetaData rsmt = rs.getMetaData();
            int k = rsmt.getColumnCount();  
            while(rs.next()){
                int offset = noOfSubject+6;
                
                // getting marks 
                ArrayList<String> marksAndResults = new ArrayList<String>();                                             
                for(int j=0;j<noOfSubject;j++){
                    String temp = subjectCode.get(j);
                    if(temp.startsWith("elective")){
                        temp = rs.getString(++offset);
                    }
                    int mark;
                    try{
                       mark = rs.getInt(j+7);
                       if(rs.wasNull()){
                           mark=-1;
                       }
                    }catch(Exception e){
                        mark = -1;
                    }                    
                    marksAndResults.add(temp.toUpperCase()+"-"+PrepareEmail.isabsent(mark)+"-"+PrepareEmail.result(mark));                    
                }
                ForSMS obj = new ForSMS();
                obj.setRollno(rs.getLong(1)+"");
                obj.setName(rs.getString(2));
                obj.setParentMobile(rs.getString(4));
                obj.setMarksAndResult(marksAndResults);
                obj.setAttendancePercentage(((float)rs.getInt(6)/(float)rs.getInt(5))*100);
                toSession.add(obj);
                out.println("<tr><td>"+obj.getRollno()+"</td><td>"+obj.getName()+"</td><td>"+obj.getParentMobile()+"</td></tr>");
            }
            uniqueIdentifier = UUID.randomUUID().toString().substring(0, 7);
            uniquePT = UUID.randomUUID().toString().substring(0, 7);
            HttpSession s = request.getSession();
            s.setAttribute(uniqueIdentifier, toSession);            
            s.setAttribute(uniquePT, type.substring(0, 2).toUpperCase()+"-"+type.charAt(2)+" MARKS");
            
        }catch(Exception e){
            System.out.println("Error in SMS servlet : "+e);
            out.println("Error Occured :( ");
        }
        out.println("</tbody></table>");
        out.println("<a href='/fileupload/SMS?uniqueObj="+uniqueIdentifier+"&uniquePT="+uniquePT+"' class='btn btn-sm btn-success'>Verified</a>");
        out.println("</body>");
        out.println("</html>");
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String unique = request.getParameter("uniqueObj");
        String pt = request.getParameter("uniquePT").toString();
        ArrayList<ForSMS> dataToSend = null;
        System.out.println("Reached doGet method "+ unique);
        try{
            HttpSession s = request.getSession();
            dataToSend = (ArrayList<ForSMS>) s.getAttribute(unique);
            pt = (String) s.getAttribute(pt);
            Map<String,String> finalLink = SendEmail.prepareMessaage(dataToSend,pt);
            
            for(Map.Entry<String,String> m : finalLink.entrySet()){
                System.out.println(m.getKey()+" : "+m.getValue());
            }
        }catch(Exception e){
            System.out.println("doGET /SMS Threw exception "+ e);
        }
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        batch = request.getParameter("batch");
        type = request.getParameter("type");
        section = request.getParameter("section");
        regulation = request.getParameter("regulation");
        sem = request.getParameter("sem");        
        System.out.println("batch "+batch+" type "+type+" secction = "+section);
        try{
            HttpSession s = request.getSession();
            dept = s.getAttribute("dept").toString();
            ArrayList<String> check = CheckList.checkUpload("report/email", dept, batch, section, sem, type);
            if(check.isEmpty()){
                processRequest(request, response);
            }else{
                PrintWriter out1 = response.getWriter();
                response.setContentType("text/html");
                for(String sx : check){
                    out1.println("<strong>"+sx+"</strong>");
                }
                out1.close();
            }
            
        }catch(Exception e){
            System.out.println("Exception in PrepareMail doPost : "+e);
            response.sendRedirect("./Html/session_exp.html");
        }        
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
