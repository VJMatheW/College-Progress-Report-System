
package com.Project.Servlets;

import com.Project.Model.Components;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UpdateElective extends HttpServlet {

    String[] regNo;
    String subject="hello",dept,batch;
    ArrayList<String> subCode = new ArrayList<>();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet UpdateElective</title>");            
//            out.println("</head>");
//            out.println("<body>");
            System.out.println(regNo.length);
            String[] subjectdeptbatch = subject.split(",");
            ArrayList<String> query = new ArrayList<>();
            //for(String txt :regNo){
                //out.println(txt+"<br>");
            //}
            for(int k=0;k<=(regNo.length-1);k++){
                //out.println(regNo[k]+"  "+subCode.get(k)+"<br>");
                query.add("update tbl"+subjectdeptbatch[1]+subjectdeptbatch[2]+"elective set "+subjectdeptbatch[0]+"=\""+subCode.get(k)+"\" where rollno="+regNo[k].trim());
            }
            Boolean i = false; 
            String content="";
            try{
                i = Components.executeQuery(query);
                content = "Updation of Elective "+subjectdeptbatch[0].charAt(8)+" subjects for class :"+subjectdeptbatch[1]+"-"+subjectdeptbatch[3]+" of batch : "+subjectdeptbatch[2]+ " was <b>Successful</b> ";
                if(i){
                    String sql = "update tblfileuploadsentry set elective="+subjectdeptbatch[0].charAt(8)+" where class=\""+subjectdeptbatch[2]+subjectdeptbatch[1]+subjectdeptbatch[3]+"\"";
                    Components.executeDataManipulation(sql); 
                }
            }catch(Exception e){
                i=false;
                content ="Updation of Elective "+subjectdeptbatch[0].charAt(8)+" subjects for class :"+subjectdeptbatch[1]+"-"+subjectdeptbatch[3]+" of batch : "+subjectdeptbatch[2]+ " <b>Failed</b> ";
                content += Components.error.toString().replace("[", "").replace("]","");
                System.out.println("UpdateElective catch statement : "+e);
            }
                
//            out.println("<h1>Servlet UpdateElective at " + request.getContextPath() + "</h1>");
//            out.println("<h2>"+subject+" status of Updating : "+i+"</h2>");
//            out.println("</body>");
//            out.println("</html>");
            request.setAttribute("content", content);
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/ElectiveUpdationResult.jsp");
            rd.forward(request, response);
        }
        subCode.clear();
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        subject = request.getParameter("elective");
        String fetch="";
        fetch = request.getParameter("regNo") ;
        regNo = fetch.split(",");
        System.out.println(Arrays.toString(regNo));
        String temp="";
        for(String txt :regNo){
                System.out.println("Hello"+txt);
                temp = request.getParameter(txt.trim());
                System.out.println(txt + "   "+temp );
                subCode.add(temp);                
            }
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateElective.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateElective.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
