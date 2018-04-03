/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.CheckList;
import com.Project.Model.Components;
import com.Project.Model.log;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author vijay_ravi
 */
public class ByReg extends HttpServlet {
    
    String regNo,type,sem,regulation,dept,batch;
    String[] options;
    ArrayList<String> d = new ArrayList<>();
    HttpSession s = null;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        
        batch = Components.batchResolver(regNo);
        String query;
        
        try (PrintWriter out = response.getWriter()) {
            if(CheckList.checkEnableOrDisabled((int)s.getAttribute("ed"))){              
                out.println("<head>"
                        + "<script src=\"/fileupload/Html/section.js\"></script>"
                        + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
    "        <link type=\"text/css\" rel=\"stylesheet\" href=\"/fileupload/Stylesheet/bootstrap/bootstrap.min.css\"> \n" +
    "        <link type=\"text/css\" rel=\"stylesheet\" href=\"/fileupload/Stylesheet/general.css\">"
                        + "<style type=\"text/css\">"
                        + "body{background-color: #ecfdeb !important;}"
                        + "</style></head>");

                if(type.startsWith("pt")){

                    ArrayList<String> subjects = Components.fetchSemSubject(dept, regulation, sem);
                    ResultSet rs = Components.executeSelectQuery("select "+subjects.toString().replace("[","").replace("]", "")+""
                            + " from tbl"+dept+batch+type+" where rollno="+regNo);

                    out.println("<div class=\"container-fluid\"><h3><b>Updating SEM:"+sem+"  "+type.toUpperCase()+" marks of "+regNo.toUpperCase()+"</b><hr style=\"border-color: black;\"/></h3>");
                    out.println("<form class=\"form-horizontal\" action=\"./ByRegUpdate\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"info\" value=\""+type+","+batch+","+regNo+"\">");
                    out.println("<input type=\"hidden\" name=\"subject\" value=\""+subjects.toString().replace("[", "").replace("]", "")+"\">");
                    out.println("Specify \" <b>AB</b> \" if <b>ABSENT</b><br/><br/><br/>");
                    String temp;
                    try{

                        rs.next();
                        rs.beforeFirst();
                        while(rs.next()){
                            for(int i=0; i < subjects.size();i++){
                                int j = rs.getInt(i+1);
                                if(rs.wasNull()){
                                    temp="AB";
                                }else{
                                    temp=j+"";
                                }
                                out.println("<div class=\"form-group\">\n" +
    "                <label class=\"control-label col-sm-2\">"+subjects.get(i).toUpperCase()+" : </label> \n" +
    "                <div class=\"col-sm-4\">\n" +
    "                    <input class=\"form-control input-sm\" type=\"text\" id=\"batch\" name=\"marks\"  onkeyup=\"numberOnly(this)\" value=\""+temp+"\" required />\n" +
    "                </div>\n" +
    "            </div>");
                            }
                        }
                        out.println("<div class=\"form-group\">\n" +
    "                <div class=\"col-xs-offset-2 col-sm-4\" >\n" +
    "                    <input class=\"btn btn-sm btn-success\" type=\"submit\" value=\"Update\" />\n" +
    "                </div>\n" +
    "            </div> </div></form>");
                        rs.getStatement().getConnection().close();
                    }catch(Exception e){
                        rs.getStatement().getConnection().close();
                        out.println("Something went wrong... check your input data again");
                    }
                }else if (type.startsWith("term")){
                    query = "select thoursem"+sem+",sem"+sem+" from tbl"+dept+batch+type+" where rollno="+regNo;
                    ResultSet rs = Components.executeSelectQuery(query);

                    out.println("<div class=\"container-fluid\" ><h3><b>Updating SEM:"+sem+"  "+type.toUpperCase()+" attendance of "+regNo.toUpperCase()+"</b><hr style=\"border-color: black;\"/></h3>");
                    out.println("<form class=\"form-horizontal\" action=\"./ByRegUpdate\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"info\" value=\""+type+","+batch+","+regNo+"\">");
                    out.println("<input type=\"hidden\" name=\"sem\" value=\""+sem+"\">");
                    try{
                        rs.next();
                        rs.beforeFirst();
                        while(rs.next()){
                        out.println("<input type=\"hidden\" name=\"thour\" value=\""+rs.getInt(1)+"\">");
                        out.println("<div class=\"form-group\">\n" +
    "                <label class=\"control-label col-sm-2\">Total Hour : </label> \n" +
    "                <div class=\"col-sm-4\">\n" +
    "                    <input class=\"form-control input-sm\" type=\"text\" disabled=\"true\" value=\""+rs.getInt(1)+"\"/>\n" +
    "                </div>\n" +
    "            </div>");
                        out.println("<div class=\"form-group\">\n" +
    "                <label class=\"control-label col-sm-2\">Attended Hour : </label> \n" +
    "                <div class=\"col-sm-4\">\n" +
    "                    <input class=\"form-control input-sm\" type=\"text\" name=\"ahour\" value=\""+rs.getInt(2)+"\"/>\n" +
    "                </div>\n" +
    "            </div>");
                        }
                        out.println("<div class=\"form-group\">\n" +
    "                <div class=\"col-xs-offset-2 col-sm-4\" >\n" +
    "                    <input class=\"btn btn-sm btn-success\" type=\"submit\" value=\"Update\" />\n" +
    "                </div>\n" +
    "            </div> </div></form>");
                        rs.getStatement().getConnection().close();
                        log.println(dept, log.time()+","+log.ip(request)+", Initiated update of "+type+" for regNo :"+regNo);
                    }catch(Exception e){
                        rs.getStatement().getConnection().close();
                        out.println("Oops somthing went wrong... check your input data again");
                    }
                }else{
                    String option = Arrays.toString(options).replace("{", "").replace("}","").replace("[", "").replace("]", "");
                    query = "select "+option+" from tbl"+dept+batch+type+""
                            + " where rollno="+regNo;
                    ResultSet rs = Components.executeSelectQuery(query);

                    out.println("<div class=\"container-fluid\"><h3><b>Updating Basic Information of "+regNo.toUpperCase()+"</b><hr style=\"border-color: black;\"/></h3>");
                    out.println("<form class=\"form-horizontal\" action=\"./ByRegUpdate\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"info\" value=\"info,"+batch+","+regNo+"\">");
                    out.println("<input type=\"hidden\" name=\"option\" value=\""+option+"\">");
                    out.println("Specify \" <b>NA</b> \" if <b>Info</b> is not available<br/><br/><br/>");

                    options = option.split(",");
                    try{
                        rs.next();
                        rs.beforeFirst();
                        while(rs.next()){
                            for(int i=0; i < options.length;i++){     
                                String temp = rs.getString(i+1);
                                out.println("<div class=\"form-group\">\n" +
    "                <label class=\"control-label col-sm-2\">"+options[i].toUpperCase()+" : </label> \n" +
    "                <div class=\"col-sm-4\">\n" +
    "                    <input class=\"form-control input-sm\" type=\"text\" name=\"optionval\"  value=\""+temp+"\" required />\n" +
    "                </div>\n" +
    "            </div>");                          
                            }
                        }
                        out.println("<div class=\"form-group\">\n" +
    "                <div class=\"col-sm-offset-2 col-sm-4\" >\n" +
    "                    <input class=\"btn btn-sm btn-success\" type=\"submit\" value=\"Update\" />\n" +
    "                </div>\n" +
    "            </div> </div></form>");
                        rs.getStatement().getConnection().close();
                        log.println(dept, log.time()+","+log.ip(request)+", Initiated update of "+type+" for regNo :"+regNo);
                    }catch(Exception e){
                        rs.getStatement().getConnection().close();
                        out.println("Something went wrong...check your input data again");
                    }
                    rs.getStatement().getConnection().close();
                } 
            }else{
                out.println("Updation is <span style=\"color:red;\"><b>Disabled</b></span>. Contact <b>Admin</b> for Support");
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
        type = request.getParameter("type");
        
        if(type.startsWith("pt")){
            regNo = request.getParameter("regNo");
            sem = request.getParameter("sem");
            regulation = request.getParameter("regulation");
        }else if(type.startsWith("term")){
            regNo = request.getParameter("regNo");
            sem = request.getParameter("sem");
        }else{
            regNo = request.getParameter("regNo");
            options = request.getParameterValues("options");
        }
        try{
            s = request.getSession();
            dept = s.getAttribute("dept").toString();
            processRequest(request, response);            
        }catch(Exception e){
            response.sendRedirect("./Html/session_exp.html");
//            RequestDispatcher rd = request.getRequestDispatcher("./Html/session_exp.html");
//            rd.forward(request, response);
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
