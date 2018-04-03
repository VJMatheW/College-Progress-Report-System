package com.Project.Servlets;

import com.Project.Model.BasicInfo;
import com.Project.Model.Components;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


public class FetchRollNo extends HttpServlet {

    String batch,reg,section,elective,dept;
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, Exception {
        
        try{
                HttpSession s = request.getSession();
                dept = s.getAttribute("dept").toString();
                System.out.println("Dept : "+dept);
                if(dept == null){
                    throw new NullPointerException();
                }else{
                    String sql = "select elective from tblfileuploadsentry where class =\""+batch+dept+section+"\"";
                    ResultSet rst = Components.executeSelectQuery(sql);
                    boolean check = false;
                    while(rst.next()){
                        System.out.println("elective form table : "+rst.getInt(1)+"  from elective : "+elective.substring(8));
                        if(rst.getInt(1) < Integer.parseInt(elective.substring(8))){
                            check = true;
                        }
                    }
                    rst.getStatement().getConnection().close();
                    
                    response.setContentType("text/html;charset=UTF-8");
                    String line,head="<th>RegisterNo</th><th>Name</th>";
                    String[] values;
                    int i=1,totalNoOfSub=0;
                    ArrayList<String> subjectCode = new  ArrayList<>();
                    try (PrintWriter out = response.getWriter()) {
                        if(check){
                        BufferedReader br = new BufferedReader(new FileReader(BasicInfo.storageLocation+"elective\\"+dept+reg+".csv"));
                        br.readLine();
                        System.out.println(head);
                        int h=1;
                        while((line=br.readLine().toLowerCase())!= null){
                            //System.out.println(h++ + ""+line);
                                values = line.split(",");
                                System.out.println(Arrays.toString(values)+" vaue[0]:"+values[0]+" Elective : "+elective);
                                if(values[0].toString().equals(elective)){
                                totalNoOfSub = (values.length-1);
                                System.out.println("Value Length : "+values.length);
                                for(int j=1;j<=values.length-1;j++){
                                    System.out.println(values[j]);
                                    subjectCode.add(values[j]);                                    
                                    head = head + "<th>"+values[j].toUpperCase()+"</th>";                                      
                                }
                                break;
                                }else{
                                    System.out.println("Else not equals");                                   
                                }
                        }
                        System.out.println("Head : "+head);
                        Statement st = BasicInfo.con().createStatement();
                        ResultSet rs = st.executeQuery("select rollno,name from tbl"+dept+batch+"info where section="+section);

                        request.setAttribute("thead", head);
                        request.setAttribute("tbody", rs);
                        request.setAttribute("subject",elective+","+dept+","+batch+","+section);
                        request.setAttribute("totalSubject",totalNoOfSub);
                        request.setAttribute("subjectCode", subjectCode);
                        //System.out.println("TotalNoOfSubject : "+totalNoOfSub);
                        RequestDispatcher rsd = request.getRequestDispatcher("/jsp/SelectElective.jsp");
                        rsd.forward(request,response);
                        System.out.println(batch+reg+section+elective+dept); 
                        }else{
                            System.out.println("Check else loop : "+check);
                            out.println("You have already updated "+elective+" subjects for class "+dept+" "+section+" of batch "+batch);
                        }

                        }catch(Exception e){
                            System.out.println("PrintWriter Exception : "+e);
                        }

                }
            }catch(NullPointerException e ){
                RequestDispatcher rd = request.getRequestDispatcher("/Html/session_exp.html");
                rd.forward(request, response);
                }   
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Inside roll no ");
        batch = request.getParameter("batch");
        reg = request.getParameter("regulation");
        section = request.getParameter("section"); 
        elective = request.getParameter("elective");
        System.out.println(batch+reg+section+elective);
        
        try {        
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FetchRollNo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FetchRollNo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FetchRollNo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
