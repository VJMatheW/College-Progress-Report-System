/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Model;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Vijay
 */
public class Admin {
    
    static ArrayList<String> error = new ArrayList<>();
    public static ArrayList<String> query = new ArrayList<>();
    
    public static boolean createFolder(String dept,String batch)
    {
        boolean s = false;
        File folder = new File(BasicInfo.storageLocation+dept+"/"+batch);
        if (!folder.exists())
        {
            s = folder.mkdirs();
        }else {
            s = folder.exists();
        }
        return s;
    }
    
    public static String createInfoTbl(String dept,String batch) throws ClassNotFoundException, SQLException 
    {
        boolean s;
        //System.out.println("inside create_info_table method INPUT_GIVEN dept : "+dept+" batch : "+batch);
        String sql = BasicInfo.sqlTblKeyword + dept + batch +"info"+BasicInfo.basicInfo;
        query.add(sql);
        return "Query Created for INFO Table";
    }
    
    public static String createTrigger(String dept,String batch) throws SQLException, ClassNotFoundException
    {
        boolean g;
        System.out.println("Dept : "+dept);
        System.out.println("Batch value : "+batch);
        String sql = new String("create trigger "+dept+batch+" after insert on tbl"+dept+batch+"info for each row\n" +
"begin\n" +
"insert into tbl"+dept+batch+"pt1(rollno)select rollno from tbl"+dept+batch+"info where rollno=new.rollno;\n" +
"insert into tbl"+dept+batch+"pt2(rollno)select rollno from tbl"+dept+batch+"info where rollno=new.rollno;\n" +
"insert into tbl"+dept+batch+"pt3(rollno)select rollno from tbl"+dept+batch+"info where rollno=new.rollno;\n" +
"insert into tbl"+dept+batch+"term1(rollno)select rollno from tbl"+dept+batch+"info where rollno=new.rollno;\n" +
"insert into tbl"+dept+batch+"term2(rollno)select rollno from tbl"+dept+batch+"info where rollno=new.rollno;\n" +
"insert into tbl"+dept+batch+"term3(rollno)select rollno from tbl"+dept+batch+"info where rollno=new.rollno;\n" +
"insert into tbl"+dept+batch+"elective(rollno)select rollno from tbl"+dept+batch+"info where rollno=new.rollno;\n" +
"end");
        query.add(sql);
    return "Trigger Query Created ";
    }
    
    public static String createPtTable(String dept,String batch,String regulation) throws ClassNotFoundException, SQLException
    {
        System.out.println("Inside ceatePtTable ");
        boolean v = true;
        String fkey_suffix = " tbl"+dept+batch+"info(rollno) on delete cascade on update cascade ) ";
        for (int i=1;i<=3;i++)
        {
            System.out.println("Iteration : "+i);            
            String sql = BasicInfo.sqlTblKeyword + dept + batch + "pt" +i+ Components.createQuerySuffix(dept, regulation)+fkey_suffix;
            System.out.println("CreatePtTable query "+i+" : "+sql);
            query.add(sql);
        }

        return "PT Table query Created";

    }   
    
    public static String createAttendanceTable(String dept,String batch) throws ClassNotFoundException, SQLException
    {
        String fkey = dept+batch+"info(rollno) on delete cascade on update cascade);";
        for(int i=1;i<=3;i++)
        {
            String sql = BasicInfo.sqlTblKeyword+dept+batch+"term"+i+BasicInfo.attendance+fkey;
            query.add(sql);
        }
        return "Attendance Table Query Created";
    }
    
    public static boolean deleteTable(String dept,String batch) throws ClassNotFoundException, SQLException
    {
        Connection con = BasicInfo.con();
        Statement st = con.createStatement();
        for (int i=1;i<=3;i++)
            {
                String sql = "drop table if exists tbl"+dept+batch+"pt"+i;                                
                st.executeUpdate(sql);
            }
        st.close();
        con.close();
        return true;
    }
    
    public static boolean checkDeptEntry(String batch,String dept) throws ClassNotFoundException, SQLException{
        Connection con = BasicInfo.con();
        Statement s = con.createStatement();
        System.out.println("deptEntry Query : "+"select yn from tbldeptentry where year="+batch+" and dept ="+dept);
        ResultSet rs = s.executeQuery("select yn from tbldeptentry where year="+batch+" and dept =\""+dept+"\"");
        boolean x =rs.next();
        rs.getStatement().getConnection().close();
        return  x;
    }
    
    public static int insertIntoDeptEntry(String batch,String dept) throws ClassNotFoundException, SQLException{
        Connection con = BasicInfo.con();
        Statement s = con.createStatement();
        String sql = "insert into tbldeptentry values("+batch+",\""+dept+"\",1)";
        System.out.println("DeptEntry : "+sql);
        int si = s.executeUpdate(sql);
        s.close();
        con.close();
        return si;
    }
    
    public static String createElective(String dept,String batch,String regulation) throws Exception{
        ArrayList<String> subcode = Components.fetchSemSubject(dept, regulation, "");
        String sql = BasicInfo.sqlTblKeyword+dept+batch+"elective";
        ArrayList<String> elective = new ArrayList<>();
        String sub="",temp=" (rollno bigint ";
        for(String s : subcode){
            if(s.trim().toLowerCase().startsWith("elective")){
               sub =  s.trim().toLowerCase();
               temp += ","+sub+" varchar(20)" ;
            }
        }
        String finalQuery = sql+temp+", foreign key (rollno) references  tbl"+dept+batch+"info(rollno) on delete cascade on update cascade )";
        System.out.println("Final query : "+finalQuery);
        query.add(finalQuery);
        return finalQuery;
    }
    public static String createDeleteQuery(String dept,String batch){
        
        ArrayList<String> tbl = new ArrayList<>();
        
        for (int i=1;i<=3;i++){
            tbl.add("tbl"+dept+batch+"pt"+i);
        }
        for (int j=1;j<=3;j++){
            tbl.add("tbl"+dept+batch+"term"+j);
        }
        tbl.add("tbl"+dept+batch+"elective");
        
        System.out.println(tbl.toString());
        
        return "drop table if exists "+tbl.toString().replace("[", "").replace("]", "");
    }
    
    public static String createDeleteInfo(String dept,String batch){
        
        return "drop table if exists tbl"+dept+batch+"info";
        
    }
    
}

