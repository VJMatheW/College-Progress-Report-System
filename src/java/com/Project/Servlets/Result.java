/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Servlets;

import com.Project.Model.CheckList;
import com.Project.Model.Components;
import com.Project.Model.QueryGenerator;
import com.Project.Model.log;
import com.Project.Report.ChartBean;
import com.Project.Report.ChartBeanList;
import com.Project.Report.ResultBean;
import com.Project.Report.ResultBeanList;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author vijay_ravi
 */
public class Result extends HttpServlet {
    @SuppressWarnings("unchecked")
    //static Logger logger = Logger.getLogger(Result.class);
            
    String batch,regulation,sem,section,dept,type,deptff,teachersName[];
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, Exception {
        
        //try (PrintWriter out = response.getWriter()) {
        
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            String hyear = new SimpleDateFormat("MMM").format(cal.getTime()).toUpperCase()+" - "+year;
            
            int month = cal.get(Calendar.MONTH);
            if(month >= 7){
                year++;
            }
            ArrayList<String> subjects = Components.fetchSemSubject(dept, regulation, sem+"");            
            ArrayList<String> subff = Components.fetchSemSubjectff(subjects,dept,regulation);            
            ArrayList<String> subsf = Components.fetchSubjectShortForm(subjects, dept, regulation);            
            List<String> temp = Arrays.asList(teachersName);
            ArrayList<String> subf = new ArrayList<>(temp); //Components.fetchSubjectFaculty(subjects, dept, regulation);
            //System.out.println("STAFF  F  F  :"+subf.toString());
           
            String query = QueryGenerator.createMarksRetriveQuery(subjects, dept, batch, type, section);
            ResultSet rs = Components.executeSelectQuery(query);
                        
            ResultBeanList rbl = new ResultBeanList();
            ArrayList<ResultBean> dataSource = rbl.createDataSource(rs);
            rs.getStatement().getConnection().close();
            System.out.println("DataSource created for RA1 & RA2");            
            Map param = rbl.setParameter(subjects,subsf,subff,subf,dept,deptff,type,sem,Components.toRoman(Integer.parseInt(section)),Components.toRoman(year - Integer.parseInt(batch)),hyear);
            System.out.println("parameter was set for RA1 & RA2");
            
            ChartBeanList cbl = new ChartBeanList();
            ArrayList<ChartBean> chartdata = cbl.getchartBeanList(subsf,rbl.chartValue());
            System.out.println("DataSource created for Chart");            
            Map chartParam = cbl.createParam(dept, deptff, type, sem, Components.toRoman(Integer.parseInt(section)), Components.toRoman(year - Integer.parseInt(batch)), hyear)  ;
            System.out.println("Parameter was set for Chart");
            
            String p = System.getProperty("user.dir");
            //System.out.println("User directory "+p);
            //PropertyConfigurator.configure(p);
            
            String src1 = getServletContext().getRealPath("/")+
        "JasperResult/ResultAnalysis.jasper";
            String src2 = getServletContext().getRealPath("/")+
        "JasperResult/ResultAnalysis1.jasper";
            String src3 = getServletContext().getRealPath("/")+
        "JasperResult/chart.jasper";            
            String destFileName = "/home/vijay_ravi/Desktop/chartNew.pdf";
            String fileName = null;
            
            
            //passing field source 
            JRBeanCollectionDataSource bcds = new JRBeanCollectionDataSource(dataSource);
            JRBeanCollectionDataSource bcds1 = new JRBeanCollectionDataSource(dataSource);
            JRBeanCollectionDataSource bcds2 = new JRBeanCollectionDataSource(chartdata);
            
            try{
                response.setContentType("application/zip");
                response.setHeader("Content-Disposition","attachment; filename=\"ResultAnalysis"+batch+"_"+dept+section+"_sem"+sem+"_"+type+".zip\"");
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                ZipOutputStream zos = new ZipOutputStream(bos);
                
                JasperPrint jp1 = JasperFillManager.fillReport(src1, param, bcds);
                byte[] bytes1 = JasperExportManager.exportReportToPdf(jp1);
                ZipEntry ze1 = new ZipEntry("ResultAnalysis1.pdf");
                zos.putNextEntry(ze1);
                zos.write(bytes1, 0, bytes1.length);
                zos.closeEntry();
                
                JasperPrint jp2 = JasperFillManager.fillReport(src2, param, bcds1);
                byte[] bytes2 = JasperExportManager.exportReportToPdf(jp2);
                ZipEntry ze2 = new ZipEntry("ResultAnalysis2.pdf");
                zos.putNextEntry(ze2);
                zos.write(bytes2, 0, bytes2.length);
                zos.closeEntry();
                
                JasperPrint jp3 = JasperFillManager.fillReport(src3, chartParam, bcds2);
                byte[] bytes3 = JasperExportManager.exportReportToPdf(jp3);
                ZipEntry ze3 = new ZipEntry("ResultAnalysisChart.pdf");
                zos.putNextEntry(ze3);
                zos.write(bytes3, 0, bytes3.length);
                zos.closeEntry();
                
                zos.close();
                response.flushBuffer();                                                               
                JasperExportManager.exportReportToPdfFile(jp3, destFileName);
//                sos.flush();
//                sos.close();
                log.println(dept,log.time()+","+log.ip(request)+", generated result for dept "+dept+" batch "+batch+" section "+section);
            
            }catch(Exception e){
                System.out.println("Oops... something went wrong  :(  !!!");
                System.out.println(""+e);
            }
            
        //}
//        finally{
//               // rs.getStatement().getConnection().close();                   
//                
//        }
    }
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("inside get of /result for resultanalysis professors name ");
        sem = "";   // for select semster error : it show all the subjects due to dual type in function Components.fetchSemSubjects()
        dept = request.getParameter("dept");
        regulation = request.getParameter("reg");
        sem = request.getParameter("sem");
        
        System.out.println("sem : "+sem);
        
        String label="";        
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        
        if(!sem.equals("")){
            try {
                ArrayList<String> semSubjects = Components.fetchSemSubject(dept, regulation, sem);
                for(String sub : semSubjects){
                    label = sub.toUpperCase();
                    String tags = "<div class=\"form-group\">" +
    "                <label class=\"control-label col-sm-4\">"+label+" :</label>" +
    "                <div class=\"col-sm-8\">" +
    "                    <input class=\"form-control input-sm\" type=\"text\" name=\"teachersName\" placeholder=\"Professor's Name\" maxlength=\"50\" required />" +
    "                </div>" +
    "            </div> ";
                    list.add(tags);
                }
                obj.put("tags", list);


            }catch(Exception e){
                System.out.println("Error in Result Servlet doGET Method "+e);
            }
        }else{
            obj.put("tags", list);
        }
        PrintWriter out = response.getWriter();
        
        response.setContentType("application/json");
        out.println(obj);
        out.flush();
        
        //out.println("dept "+dept+" reg : "+regulation+" sem : "+sem);      
        
        //String s[] = request.getParameterValues("world");
        //ArrayList<String> sg = new ArrayList<>(Arrays.asList(s));
        //out.println(sg.toString());
        out.close();
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
        teachersName = request.getParameterValues("teachersName");
        System.out.println("Teachers Names are "+Arrays.toString(teachersName));
        try {   
            HttpSession s = request.getSession();
            dept = s.getAttribute("dept").toString(); 
            deptff = s.getAttribute("deptff").toString();
            
            ArrayList<String> check = CheckList.checkUpload("result", dept, batch, section, sem, type);
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
            //processRequest(request, response);
            
        } catch (Exception e){
            RequestDispatcher rd = request.getRequestDispatcher("./Html/session_exp.html");
            rd.forward(request, response);
        }
        
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
