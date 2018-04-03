/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.Components;
import com.Project.Email.EmailBean;
import com.Project.Model.BasicInfo;
import com.Project.Model.CheckList;
import static com.Project.Model.Components.executeSelectQuery;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vijay
 */
public class PrepareEmail extends HttpServlet {

    String batch,section,regulation,sem,type,dept,aday,amonth,ayear;
    public HashMap<String,String> subFF = new HashMap<String,String>();
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ArrayList<String> subjectCode = new  ArrayList<String>();
        ResultSet rs =null;
  
        Thread fetch = new Thread(){
            public void run(){
                try{
                    subFF = Components.fetchWholeSubCodeAndFF(dept, regulation);
                }catch(Exception e){
                    System.out.println("Error in Thread of Prepare Mail ");
                }
            }
        };
        
        fetch.start();        
       
        try{
            subjectCode = Components.fetchSemSubject(dept, regulation, sem);
                                                                                                                  
            rs = Components.getMessageData(sem, dept, batch, section, type, subjectCode);
            ResultSetMetaData rsmt = rs.getMetaData();
            int k = rsmt.getColumnCount();            
            int thour,ahour,percentage,noOfSubject = subjectCode.size();
            
            String rollno,name,mail,mobile,adate;
            String subMarks[] = new String[noOfSubject];
            ArrayList<EmailBean> z = new ArrayList<EmailBean>();
            
            while(rs.next()){
                int offset = noOfSubject+6;
                String message;
                rollno = rs.getLong(1)+"";
                name = rs.getString(2);
                mail = rs.getString(3);
                mobile = rs.getString(4);
                thour = rs.getInt(5);        
                ahour = rs.getInt(6);
                percentage = (int)(((float)ahour/(float)thour)*100);
                String[] mon = amonth.split(",");
                //adate = aday+"-"+mon[0]+"-"+ayear;
                adate = mon[1].toUpperCase()+"-"+aday+","+ayear;
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
                    subMarks[j] = "<tr><td style=\"padding:5px;text-align:center;\" >"+temp.toUpperCase()+"</td><td style=\"padding:5px;\" >"+subFF.get(temp)+"</td><td style=\"padding:5px;text-align:center;\" >"+isabsent(mark)+"</td><td style=\"padding:5px;text-align:center;\" >"+result(mark)+"</td></tr>";
                }
                System.out.println("rollno "+rollno+" name "+name+" mail "+mail+" mobile "+mobile+" thour "+thour+" ahour "+ahour+" percentage "+percentage+" adate "+adate);
                message = "<div style=\"\" >"
                        + "<div style=\"text-align:center;\" >"
                        + "<h3 style=\"margin-bottom:0px;\" ><b>VALLIAMMAI ENGINEERING COLLEGE</b></h3>"
                        + "<div style=\"margin-bottom:5px;\" >(  A member of SRM group  )</div>"
                        + "</div>"
                        + "Name : <b>"+name+"</b><br/>"
                        + "RegisterNo : <b>"+rollno+"</b><br/>"
                        + "Semester : "+Components.toRoman(Integer.parseInt(sem))+"<br/>"
                        + "<h4 style=\"text-align:center;\"><b>"+Components.ptResolver(type)+"</b></h4><br/>"
                        + "<table style=\" border-collapse: collapse;width:100%;\" border=\"1\" >"
                        + "<tr><th style=\"padding:5px;text-align:center;\" >Subject Code</th><th style=\"padding:5px;text-align:center;\" >Subject Name</th><th style=\"padding:5px;text-align:center;\" >Mark</th><th style=\"padding:5px;text-align:center;\" >Result</th></tr>";
                message += Arrays.toString(subMarks).replace("[", "").replace("]", "").replace(",", "");
                message += "</table>"
                        + "<h4>Attendance</h4>"
                        + "<table style=\" border-collapse: collapse;width:100%;\" border=\"1\" >"
                        + "<tr><th style=\"padding:5px;text-align:center;\" colspan=\"2\" >Attendance Percentage as on</th><td style=\"padding:5px;text-align:center;\" colspan=\"3\" >"+adate+"</td></tr>"
                        + "<tr><th rowspan=\"2\" style=\"padding:5px;text-align:center;\" >Attendance</th><th style=\"padding:5px;text-align:center;\" >Working Hours</th><th style=\"padding:5px;text-align:center;\" >Present</th><th style=\"padding:5px;text-align:center;\" >Absent</th><th style=\"padding:5px;text-align:center;\" >Percentage</th></tr>"
                        + "<tr><th style=\"padding:5px;text-align:center;\" >"+thour+"</th><th style=\"padding:5px;text-align:center;\" >"+ahour+"</th><th style=\"padding:5px;text-align:center;\" >"+(thour-ahour)+"</th><th style=\"padding:5px;text-align:center;\" >"+percentage+"%</th></tr>"
                        + "</table>"
                        + "<h4><b>Note : </b>This is an auto generated Email.For correction Contact Class-Coordinator</h4>"
                        + "<b>Do not reply.</b></div>";
                
                EmailBean t = new EmailBean();
                t.setTo(mail);
                t.setSubject("(Do-not-reply)PROGRESS REPORT");
                t.setMessage(message);
                
                z.add(t);
            }
            String fileName = Calendar.getInstance().get(Calendar.MINUTE)+","+dept+","+batch+","+type+","+section;
            boolean s = Components.saveObject(dept, batch, z, fileName);
            byte[] message = fileName.getBytes(StandardCharsets.UTF_8);
            String encoded = Base64.getEncoder().encodeToString(message);
            out.println("Email Prepared..Waiting for Confirmation by Class Co-Ordinator ");
            out.println("<br>The Confirmation code is : <b>"+encoded+"</b>");
            
        }catch(Exception e){
            out.println("Oops ! Something went Wrong... :( "+ e);
        }
        finally{
            try{
                rs.getStatement().getConnection().close();
            }catch(Exception e){
                System.out.println("Exception in finally of prepareMail servlet "+e);
            }
        }
    }
    
    private static String isabsent(int n){
        if(n != -1){
            return ""+n;
        }
        return "";
    }
    
    private static String result(int m){
        String result = "";
        if(m>= 50){
            result = "PASS";
        }else if(m<50 && m!=-1){
            result = "FAIL";
        }else if(m == -1){
            result = "ABSENT";
        }
        return result;
    }

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      //  processRequest(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        batch = request.getParameter("batch");
        type = request.getParameter("type");
        section = request.getParameter("sec");
        regulation = request.getParameter("regulation");
        sem = request.getParameter("sem");
        aday = request.getParameter("aday");
        amonth = request.getParameter("amonth");
        ayear = request.getParameter("ayear");
        System.out.println("batch "+batch+" type "+type+" day "+aday+" month "+amonth+" year "+ayear);
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
