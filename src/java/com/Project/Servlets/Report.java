/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.BasicInfo;
import com.Project.Model.CheckList;
import com.Project.Model.Components;
import com.Project.Model.QueryGenerator;
import com.Project.Model.log;
import com.Project.Report.DataBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.Project.Report.DataBeanList;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletOutputStream;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author vijay_ravi
 */
public class Report extends HttpServlet {

    static Logger logger = Logger.getLogger(Report.class);
    String dept,batch,regulation,sem,section,type,deptff,rpt_date,day,month,year,aday,amonth,ayear,attd_date;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, Exception {
        
            // fetching regno,name,subject_code,marks
            ResultSet rs=null,rsa=null,rselective=null;
            HashMap<String,HashMap<String,String>> electiveMap = new HashMap<>();
            HashMap<String,String> subFF = new HashMap<String,String>();
            Calendar cal = Calendar.getInstance();
            
            // fetching subject code
            ArrayList<String> subjects = Components.fetchSemSubject(dept, regulation, sem);
            ArrayList<String> ele = new ArrayList<>();
            for(String s : subjects){
                if(s.startsWith("electiv")){
                    ele.add(s.trim());
                }
            }
            boolean tem = true;
            String sr = "";
            if(!ele.isEmpty()){
                sr  = ele.get((ele.size()-1));
                System.out.println("inside ele if contains last elective name = "+sr);
                String q = "select elective,info from tblfileuploadsentry where class=\""+batch+dept+section+"\"";
                ResultSet rsg = Components.executeSelectQuery(q);
                rsg.next();
                int c =  Integer.parseInt(sr.charAt(8)+"");
                System.out.println(" next booleans  "+(rsg.getInt(1) == c)+"get boolean = "+rsg.getBoolean(2));
                if(rsg.getBoolean(2) && (rsg.getInt(1) == Integer.parseInt(sr.charAt(8)+"")) ){
                    tem = true;
                }else{
                    tem = false; 
                }
                rsg.getStatement().getConnection().close();
            }else{
                tem = true;
            }
            
            if(tem){
            
            // fetching fullform of all subjects
            subFF = Components.fetchWholeSubCodeAndFF(dept, regulation);
            
            // fetching regNo with elective Subject if eletive found
            for(String temp : subjects){
                if(temp.startsWith("elect")){                 
                    HashMap<String,String> regSubMap = new HashMap<String,String>();
                    String sql = "select rollno,"+temp.trim()+" from tbl"+dept+batch+"elective where rollno in (select rollno from tbl"+dept+batch+"info where section = "+section+" )";
                    Connection con = BasicInfo.con();
                    Statement sta = con.createStatement();
                    rselective = sta.executeQuery(sql);
                    while(rselective.next()){
                        regSubMap.put( rselective.getLong(1)+"" , rselective.getString(2));
                    }
                    rselective.getStatement().getConnection().close();
                    con.close();
                    electiveMap.put(temp.trim(), regSubMap);
                }
            }
                System.out.println("Fetched Subjects from fetchSemSubject :"+subjects);

                String query = QueryGenerator.createMarksRetriveQuery(subjects, dept, batch, type, section);
                System.out.println("Generated query for retriving marks : "+query);

                rs = Components.executeSelectQuery(query);
                
                query = QueryGenerator.createAttendanceRetrieveQuery(dept, batch, type, section, sem);
                System.out.println("Generated query for retriving attendance : "+query);
                
                rsa = Components.executeSelectQuery(query);
            
            // fething full form of subjects
            ArrayList<String> subjectff = Components.fetchSemSubjectff(subjects,dept,regulation);
            
            //if(subjects.size() == subjectff.size()){
                if(true){
                System.out.println("inside if statement");
                
                //String m[] = month.split(",") yyyy/MM/dd;
                rpt_date = new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());//day+"."+m[0]+"."+year;
                System.out.println("rpt_date"+rpt_date);
                
                String n[] = amonth.split(",");
                attd_date = aday+"."+n[0]+"."+ayear;
                System.out.println("attd_date"+Arrays.toString(n));
                // creating data source
                ArrayList<DataBean> dataBeanList = DataBeanList.createDataSource(rs,rsa,subjects,subFF,electiveMap);
                System.out.println("after databeanlist");
                
                              
                String semText = Components.toRoman(Integer.parseInt(sem));
                // setting parameter
                Map param = DataBeanList.setParameter(subjects, subjectff, deptff, dept, batch, sem, section, rpt_date,type,new SimpleDateFormat("MMM").format(cal.getTime()).toUpperCase(),
                        new SimpleDateFormat("yyyy").format(cal.getTime()).toUpperCase(),attd_date);
                System.out.println("parameter was set");
                
                
//                String p = System.getProperty("user.dir")+"/log4j.properties";
//                PropertyConfigurator.configure(p);
                
                //String src = "/home/vijay_ravi/NetBeansProjects/fileupload/web/jasper/ProgressReport.jasper";
                String src = getServletContext().getRealPath("/")+"jasper\\ProgressReport.jasper";
                System.out.println("PROGRESS REPORT .JASPER PATH : "+src);
                String fileName = null;
                //String destfilename = "/home/vijay_ravi/Desktop/report5.pdf";
                //JasperPrint jasperPrint = null;
                
                //passing field source 
                JRBeanCollectionDataSource bcds = new JRBeanCollectionDataSource(dataBeanList);
                
                
                try{
                    //filling the report 
                    //fileName = JasperFillManager.fillReportToFile(src, param, bcds);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(src, param, bcds);
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition","attachment; filename=\""+batch+"_"+dept+section+"_sem"+sem+"_"+type+".pdf\""); 
                    ServletOutputStream sos = response.getOutputStream();
                    //JasperExportManager.exportReportToPdfStream(jasperPrint, sos);
                    JasperExportManager.exportReportToPdfStream(jasperPrint, sos);
                    //JasperExportManager.exportReportToPdfFile(jasperPrint, destfilename);
                    sos.flush();
                    sos.close();
//                  out.println("File <b>successfully</b> created");                    
//                    if(fileName != null){
//                        
//                        JasperExportManager.exportReportToPdfFile(fileName, destfilename);
////                        JasperViewer jv = new JasperViewer(jasperPrint,false);
////                        jv.setVisible(true);
//                    out.println("File <b>successfully</b> created");
//                    }
                log.println(dept, log.time()+","+log.ip(request)+", rEPORTcard generated for batch"+batch+" dept "+dept+" section "+section);
                }catch(Exception e){
                    System.out.println(e);
                    e.printStackTrace();
                }
                finally{
                    rs.getStatement().getConnection().close();
                    rsa.getStatement().getConnection().close();
                    rselective.getStatement().getConnection().close();
                }
                
            }else{
                response.setContentType("text/html;charset=UTF-8");
                try(PrintWriter o = response.getWriter()){
                    o.println("There is a mismatch between <b>subject_code</b> and <b>subject_name</b> in DataBase Contact <b>Admin</b>");
                    o.println("");
                }
           // }
            }
        }else{
                try(PrintWriter o = response.getWriter()){
                    o.println("Elective Subject Name for "+sr+" for "+batch+" batch not updated");
                    o.println("");
                }
            }
            rs.getStatement().getConnection().close();
            rsa.getStatement().getConnection().close();
            rselective.getStatement().getConnection().close();
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        batch = request.getParameter("batch");
        regulation = request.getParameter("regulation");
        sem = request.getParameter("sem");
        section = request.getParameter("sec");
        type = request.getParameter("type");
        //day = request.getParameter("day");
        //month = request.getParameter("month");
        //year = request.getParameter("year");
        aday = request.getParameter("aday");
        amonth = request.getParameter("amonth");
        ayear = request.getParameter("ayear");
        
        System.out.println(batch+regulation+sem+section+type+aday+amonth+ayear);
        
        try {                      
            HttpSession s = request.getSession();
            dept = s.getAttribute("dept").toString(); 
            deptff = s.getAttribute("deptff").toString();  
            
            try{               
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
                System.out.println("Exception = "+e);
            }            
        }catch(NullPointerException nul){    
            System.out.println(" in side catch of expire session "+nul);
            if(dept==null){
            response.sendRedirect("./Html/session_exp.html");}
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

//jasperPrint = JasperFillManager.fillReport(jasperReport(.jasper),parameters,conn);
//ServletOutputStream servletOutputStream = response.getOutputStream();
//response.setContentType("application/pdf");
//JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
// 
//servletOutputStream.flush();
//servletOutputStream.close();