package com.Project.Servlets;

import com.Project.Email.EmailBean;
import com.Project.Model.SendEmail;
import com.Project.Model.Components;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Vijay
 */
public class EmailConfirm extends HttpServlet {

   String fileName,username,password;
   boolean decision;
   PrintWriter out = null;
   SendEmail obj = null;
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try{
            out = response.getWriter();
        }catch(Exception e){}
        byte[] fil = Base64.getDecoder().decode(fileName);
        fileName = new String(fil);
        String temp[] = fileName.split(",");
        System.out.println("FileNAme : "+fileName);
        ArrayList<EmailBean> bean = Components.fetchObject(temp[1], temp[2], fileName);
        obj = new SendEmail(username,password);   
        Thread send = new Thread(){
            public void run(){
                try{
                    obj.sendMail(bean,username,password);
                    //out.println("Successful");
                }catch(Exception e){ 
                   // out.println("Not successful");
                }   
            }
        };        
        send.start();
//        try{
//                    obj.sendMail(bean,username,password);
//                    out.println("Successful");
//                }catch(Exception e){ 
//                    System.out.println("Exception in Process Request of EmailConfirm : "+e);
//                    out.println("Not successful");
//                }   
        response.sendRedirect("/fileupload/jsp/email/SuccessEmail.jsp");
        }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //System.out.println("incoming Ajax"+Math.floorDiv(SendEmail.progress,SendEmail.total));        
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, OPTIONS, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "86400");
        PrintWriter o = response.getWriter();
        String decisionMaker = request.getParameter("decision");
        System.out.println("Decisions : "+decisionMaker);
        if(decisionMaker.equals("progress")){
            System.out.println("Requesting for progress");
            response.setContentType("text/plain;charset=UTF-8");
            int temp = Math.floorDiv(SendEmail.progress,SendEmail.total);
            //System.out.println("Value of temp : "+(temp*100));
            o.println((temp*100));
            o.close();
        }else if(decisionMaker.equals("listSentItems")){
            System.out.println("Requesting for ListSent Items");
            response.setContentType("text/plain;charset=UTF-8");
            o.println("hello wolrd request sent items");
            o.close();
        }
          //  response.setContentType("application/json");            
           // for(int i=0;i<SendEmail.list.size();i++){
                //JSONObject 
           // }
       // }
        
        //processRequest(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        decision = Boolean.parseBoolean(request.getParameter("decision"));
        if(decision){
            fileName = request.getParameter("cipher");
            username = request.getParameter("uname");
            password = request.getParameter("password");            
            processRequest(request, response);
        }else{
            response.setContentType("text/html");
            PrintWriter p = response.getWriter();
            p.println("Forwarded for reviewing purpose <a href=\"./\">Home</a>");
            p.close();
        }        
    }

      @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
