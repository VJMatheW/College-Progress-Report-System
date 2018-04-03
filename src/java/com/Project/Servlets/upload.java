/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;


import com.Project.Model.BasicInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.Project.Model.CheckList;
import com.Project.Model.Components;
import com.Project.Model.QueryGenerator;
import com.Project.Model.log;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vijay
 */
@MultipartConfig
public class upload extends HttpServlet {

    String filename,batch,semester,type,regulation,dept,section;
    boolean newStudent;
    Part filepart;
    InputStream is ;
    FileInputStream fis;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession s = request.getSession();
        try{
            dept = (String)s.getAttribute("dept");
            if(dept==null ){
                throw new NullPointerException("blah");
            }
            System.out.println("Dept from session : "+dept);
            
            
            try (PrintWriter out = response.getWriter()) {
                
                if(CheckList.checkEnableOrDisabled((int)s.getAttribute("ed"))) {
                    boolean status = false;
                    ArrayList<String> error = CheckList.checkList(dept, batch, filename, filepart);
                    System.out.println("Error ArrayList :"+error);
                    if(!error.isEmpty())
                    {
                        for(String a : error)
                        {
                            out.println(a+"<br/>");
                        }
                    }
                    is = filepart.getInputStream();
                    fis = (FileInputStream)filepart.getInputStream();
                    Components.saveXLSXFile(is, dept, batch, filename);
                    System.out.println("after create file ");
                    ArrayList<String> queries = new ArrayList<>();
                    System.out.println("type : "+type);
                    System.out.println("Query : "+queries.toString());

                    Statement st = BasicInfo.con().createStatement();
                    System.out.println("select "+type+",info from tblfileuploadsentry where class=\""+batch+dept+section+"\"");
                    ResultSet rs = st.executeQuery("select "+type+",info from tblfileuploadsentry where class=\""+batch+dept+section+"\"");  
                    rs.next();
                    System.out.println("get fileuloadInfo : "+rs.getBoolean(1)+" fileUpload"+rs.getBoolean(2));
                    boolean t = false;
                    if(type.startsWith("info")){
                        System.out.println("Came into info");
                        if((!rs.getBoolean(1) && !newStudent) || (rs.getBoolean(1) && newStudent)){
                        //if(!rs.getBoolean(1)){
                            t = true;
                            System.out.println("Inside info temp true");
                        }
                    }else if(type.startsWith("pt") || type.startsWith("term")){
                        if((rs.getInt(1)+1) == Integer.parseInt(semester) && rs.getBoolean(2)){
                            t = true;
                        }else{
                            t = false;
                        }
                    }

                    System.out.println("Value of : "+t+type+" info "+rs.getBoolean(2));
                    rs.getStatement().getConnection().close();

                    if(t) { 
                        System.out.println("Not Already Uploaded...so continuing to execute");
                        switch (type)
                        {
                            case "info":
                                System.out.println("Inside switch in info ");
                                queries = QueryGenerator.buildQuery(dept, batch, regulation, dept, type, filename,fis);
                                System.out.println("Queries :"+queries);
                                if(queries.get(queries.size()-1).startsWith("Error")){
                                    System.out.println("Inside Error of info, error arraylist in queryGenerator is added to queries ");
                                    out.println(queries.get(queries.size()-1).replace("[", "").replace("]", ""));
                                    Components.deleteXLSXFile(dept, batch, filename);
                                }

                                break;

                            case "pt1":
                            case "pt2":
                            case "pt3":
                                queries = QueryGenerator.buildQuery(dept, batch, regulation, semester, type, filename,fis);
                                if(queries.get(queries.size()-1).startsWith("Error")){
                                    System.out.println("Inside Error of Upload servlet switch(pt), error arraylist in queryGenerator is added to queries ");
                                    out.println(queries.get(queries.size()-1));
                                    Components.deleteXLSXFile(dept, batch, filename);
                                }
                                if ("Error : Column Mismatch".equals(queries.get(queries.size()-1)))
                                {
                                    System.out.println("inside mismatch");
                                    //status = Components.deleteFile(dept,batch,filename);
                                    //out.println("Columns not matching at "+ (queries.size()) +"th row ");
                                    out.println("<b>No of subjects</b> in the uploaded file is <b>not matching</b> with No of subjects for the selected semester ");
                                    Components.deleteXLSXFile(dept, batch, filename);
                                }else if("Error : " == ""){
                                    
                                }
                                //out.println("Inserting Marks for"+type+" :) <br/>");
                                break;

                            case "term1":
                            case "term2":
                            case "term3":
                                queries = QueryGenerator.buildQuery(dept, batch, regulation, semester, type, filename,fis);
                                if("Something is wrong".equals(queries.get(queries.size()-1))){
                                    queries.clear();
                                    Components.deleteXLSXFile(dept, batch, filename);
                                    System.out.println("Inside Something is Wrong of attendance term");
                                    out.println("Something Went Wrong<br/>Check the Uploaded File again, check for if it contains any character in any of the columns except NAME ");                                    
                                }
                                System.out.println(queries.toString());
                                break;
                        }
                        //out.println("Query : "+queries.toString());
                        //status = true;
                        //System.out.println("Query = "+queries.toString());
//                        int j = 1;
//                        for(String h : queries){
//                            System.out.println();
//                            boolean sta = Components.excecuteDDL(h);
//                            if(!sta){
//                                System.out.println("Execution results false query count "+j);
//                                status = false;
//                                break;
//                            }
//                            j++;
//                        }
                        status = Components.executeQuery(queries);                        
                        queries.clear();
                        out.println("<br>Completion Status of inserting "+type+" : <strong>"+Boolean.toString(status).toUpperCase()+"</strong>");
                        if(!status){
                            if(Components.checkXLSXFileExist(dept, batch, filename))
                                out.println("<br/><b>Error</b> : "+Components.error.toString().replace(",","<br/>"));
                                out.println("<br>File deletion status : "+Components.deleteXLSXFile(dept, batch, filename));
                        }else{
                            if(type.equals("info"))
                                Components.executeDataManipulation("update tblfileuploadsentry set "+type+"=true where class=\""+batch+dept+section+"\" ");
                            else if(type.startsWith("pt") || type.startsWith("term"))
                                Components.executeDataManipulation("update tblfileuploadsentry set "+type+"="+semester+" where class=\""+batch+dept+section+"\" ");
                        }
                        log.println(dept,log.time()+","+log.ip(request)+",Uploaded "+type+" infos");
                    }else{
                        Components.deleteXLSXFile(dept, batch, filename);
                        if(type.startsWith("info")){
                            out.println("You have already uploaded the <strong>"+type.toUpperCase()+"</strong> data for the Class : <strong>"+dept+"-"+section+"</strong> of Batch : <b>"+batch+"</b>");
                        }else if(type.startsWith("pt")){
                            out.println("You may have not uploaded the <b>Basic Information</b> of Batch : "+batch+"<br/>(or)<br/>");
                            out.println("You may have not uploaded the <b>Previous Semester Marks </b> of Batch : "+batch+"<br/>(or)<br/>");
                            out.println("You may have already uploaded the <strong>"+type.toUpperCase()+"</strong> marks of semester : "+semester+" for the Class : <strong>"+dept+"-"+section+"</strong> of Batch :<b>"+batch+"</b>");
                        }else if(type.startsWith("term")){
                            out.println("You may have not uploaded the Basic Information of Batch : "+batch+" <br/>(or)<br/>");
                            out.println("You have already uploaded the <strong>"+type.toUpperCase()+"</strong> attendance data for the Class : <strong>"+dept+"-"+section+"</strong> of Batch : <b>"+batch+"</b>");
                        }

                    }
                   // rs.getStatement().getConnection().close();
                }else{
                    out.println("Updation is <span style=\"color:red;\"><b>Disabled</b></span>. Contact <b>Admin</b> for Support");
                }
            }            
            fis = null;
        }catch (NullPointerException e){
            fis = null;
            //response.sendRedirect("./Html/session_exp.html");
            RequestDispatcher rd = request.getRequestDispatcher("./Html/session_exp.html");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        batch = request.getParameter("batch");
        section = request.getParameter("section");
        regulation = request.getParameter("regulation");
        semester = request.getParameter("sem");
        type = request.getParameter("type");
        filename = request.getParameter("filename");   
        newStudent  = Boolean.parseBoolean(request.getParameter("newEntry"));
        
        filepart = request.getPart("file");
        
        
        System.out.println("batch :"+batch+"semester :"+semester+"type :"+type+"filename :"+filename+" checked status :"+newStudent);
               
        try {
            processRequest(request,response);
        } catch (SQLException ex) {
            Logger.getLogger(upload.class.getName()).log(Level.SEVERE, null, ex);
        } 
catch (Exception ex) {
            Logger.getLogger(upload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
