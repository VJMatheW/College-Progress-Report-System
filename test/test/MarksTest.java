/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarksTest {

  
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
//        String sql = "select rollno,hs6251, ma6251, ph6251, cy6251, cs6201, cs6202,\n" +
//"truncate ((ifnull(hs6251,0)+ifnull(ma6251,0)+ifnull(ph6251,0)+ifnull(cy6251,0)+ifnull(cs6201,0)\n" +
//"+ifnull(cs6202,0))/(ifnull(hs6251/hs6251,0)+ifnull(ma6251/ma6251,0)+ifnull(ph6251/ph6251,0)+ifnull(cy6251/cy6251,0)\n" +
//"+ifnull(cs6201/cs6201,0)+ifnull(cs6202/cs6202,0)),1) as Percentage\n" +
//"from tblcse2014pt1 where rollno=412814104045;";
        String sql = "show tables where Tables_in_internalmarks like 'sem%';";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/internalmarks","root","oracle");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        System.out.println(rsmd.getColumnTypeName(1));                
        
        
//        ArrayList<String> s = new ArrayList<>();
//        for(int i=1;i<=8;i++){
//            s.add(rsmd.getColumnTypeName(i));
//        }
        Map<String,String> test = new HashMap<>();
        while(rs.next()){
            String s = rs.getString(1);
            String dept = s.substring(11,s.length()-7);
            String reg = s.substring(s.length()-7);
            System.out.println(rs.getString(1)+"  "+dept+"  "+reg);
            if(test.containsKey(s.substring(12,s.length()-7))){
                test.put(dept, test.get(dept)+","+reg);
            }else{
                test.put(dept,reg);
            }
        }
        System.out.println(test.get("cse"));
        for(Map.Entry<String,String> temp : test.entrySet()){
            System.out.println(temp.getKey()+"   "+temp.getValue());
        }
        
//        for(String smt :s){
//            System.out.println(smt);
//        }
        
//        while(rs.next()){
//            String sr = rs.getBigDecimal(8)+"";
//            if(rs.wasNull()){
//                System.out.println("boo its null "+rs.getBigDecimal(8));
//            }else{
//                System.out.println("Not null else ");
//            }
//            
//        } 
        con.close();
    }
    
}
