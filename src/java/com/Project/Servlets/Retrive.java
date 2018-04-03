/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.Components;
import com.Project.Model.FormTable;
import com.Project.Model.QueryGenerator;
import com.Project.Model.log;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author vijay_ravi
 */
public class Retrive extends HttpServlet {
   
    public String batch,regulation,type,sem,dept=null,section=null,options[]= new String[18],head="";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        
        System.out.println("dept : "+dept);
        ResultSet rs=null,rs1=null;
        HttpSession s = request.getSession();
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.print("Your queries are processing...(RETRIVE) <br/>");                                  

            if (type.equals("info")){
                
                head = "BASIC INFORMATION";
                System.out.println("Inside info option : "+batch+":"+section+":"+Arrays.toString(options));
                
                String query = QueryGenerator.createInfoRetriveQuery(dept, batch, options,section);
                out.println(query+"<br/>");                
                
                rs = Components.executeSelectQuery(query); 
                s.setAttribute("dbtoss", query);
            }
            else if(type.equals("pt1") || type.equals("pt2") || type.equals("pt3"))                
            {
                head = "PERIODICAL TEST - "+type.charAt(2);
                ArrayList<String> subjects = Components.fetchSemSubject(dept, regulation, sem);
                System.out.println("Fetched Subjects from fetchSemSubject :"+subjects);

                String query = QueryGenerator.createMarksRetriveQuery(subjects, dept, batch, type, section);
                System.out.println("Generated query for retriving : "+query);

                rs = Components.executeSelectQuery(query);
                s.setAttribute("dbtoss", query);
                
            }else if(type.equals("term1") || type.equals("term2") || type.equals("term3"))
            {
                head = "ATTENDANCE TERM - "+type.charAt(4);
                String query = QueryGenerator.createAttendanceRetrieveQuery(dept, batch, type, section, sem);
                System.out.println("Generated query for retriving : "+query);
                
                rs = Components.executeSelectQuery(query);
                s.setAttribute("dbtoss", query);
                
                try{
                    rs.next();
                    rs.beforeFirst();
                }catch(Exception e){
                    System.out.println("hello bitch "+e);
                }
            }                       
            
            try
            {
                rs.next();
                rs.beforeFirst();
                ArrayList<String> data = FormTable.createHeader(rs);
                data.add(head);
                data.add(batch);
                data.add(dept.toUpperCase());
                data.add(section);
                data.add(sem);
                data.add(type);
                System.out.println("Length of List : "+data.size());
                request.setAttribute("tableHead",data);
                rs.getStatement().getConnection().close();
                RequestDispatcher rd = request.getRequestDispatcher("./jsp/ViewData.jsp");
                rd.forward(request, response);  
                
                log.println(dept, log.time()+","+log.ip(request)+",viewed "+type+" of batch "+batch+" sem "+sem);
                
            }catch(NullPointerException nul){
                System.out.println("Retrive.java catch Null Pointer exception : "+nul);
                out.println("<strong>Unknown problem "+nul+"</strong>");
            }catch(Exception e){
                System.out.println("result set returns nothing" + e);
                out.println("<strong>Queried Data has not been Inserted/Created</strong>");
            }
        }
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        PrintWriter out = null;
        try{
            HttpSession s = request.getSession();
            String query = (String)s.getAttribute("dbtoss");
            System.out.println("Inside doGet of Retrive \n Query : "+query);
            XSSFWorkbook book = FormTable.dbtoSpreadSheet(query);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=\"downloads.xlsx\"");
            book.write(response.getOutputStream());
            book.close();
        }catch(Exception e){
            out = response.getWriter();
            response.setContentType("text/html");
            out.println("OOps Something went Wrong "+e);
        }finally{
            if(out != null){
                out.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        batch = request.getParameter("batch");
        type = request.getParameter("type");
        section = request.getParameter("sec");
       try{ regulation = request.getParameter("regulation");}catch(Exception e){System.out.println("regulation");}
        try{sem = request.getParameter("sem");        }catch(Exception e){System.out.println("sem");}
        try{options = request.getParameterValues("options");}catch(Exception e){System.out.println("options array");}
       
        System.out.println(batch +":"+type+":"+section+":"+regulation+":"+sem+":"+Arrays.toString(options));
        try {           
            
            HttpSession s = request.getSession();
            dept = s.getAttribute("dept").toString();        
            processRequest(request, response);
            
        } catch (SQLException ex) {
            Logger.getLogger(Retrive.class.getName()).log(Level.SEVERE, null, ex);
        }  catch(NullPointerException nul){    
            System.out.println(" in side catch of expire session "+nul);
            if(dept==null){
            response.sendRedirect("./Html/session_exp.html");}
        }catch (Exception ex) {
            Logger.getLogger(Retrive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
