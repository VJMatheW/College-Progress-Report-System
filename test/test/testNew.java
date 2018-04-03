/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.Project.Model.Components;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Vijay
 */
public class testNew {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ArrayList<String> semSubjects = new ArrayList<>();
                semSubjects.add("cs6801");
                semSubjects.add("elective4");
                semSubjects.add("elective5");
        //String s = Components.getMessageData("cse", "2017", "2", "pt1", semSubjects);
        //System.out.println(semSubjects.toString());
        int t=20,a=9;
        System.out.println((int)(((float)a/(float)t)*100));
        
        String s = "java.sql.BatchUpdateException: Duplicate entry \'412817103103\'";
        System.out.println("Size : "+s.length()+ "  Char At : "+s.charAt(31)+s.substring(31, 60));
                      
        String sql = "update tbleee2017pt1 set hs8251=64,ma8251=68,ph8253=50,be8252=80,ee8251=51,ge8291=81 where rollno=412817103056;";
        String sql1 = "update tbleee2017pt1 set hs8251=64,ma8251=68,ph8253=50,be8252=80,ee8251=51,ge8291=81 where rollno=412817103057;";
        String sql2 = "update tbleee2017pt1 set hs8251=64,ma8251=68,ph8253=50,be8252=80,ee8251=51,ge8291=81 where rollno=412817103058;"; 
        Class.forName("com.mysql.jdbc.Driver");
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/internalmarks","root","oracle");
        Statement st = connect.createStatement();
        st.addBatch(sql);
        st.addBatch(sql1);
        st.addBatch(sql2);
        int[] rs = st.executeBatch();
        int r = st.getUpdateCount();        
        
        
        System.out.println("rs = "+Arrays.toString(rs).contains("0")+"   r = "+r);
        
    }
    
}
