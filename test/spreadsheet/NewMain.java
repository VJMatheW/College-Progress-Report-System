/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spreadsheet;

import com.Project.Model.Components;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vijay_ravi
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<String> sub = Components.fetchSemSubject("cse", "reg2013", "5");
//        ArrayList<String> subff = Components.fetchSemSubjectff(sub);
//        for (String s : subff)
//            System.out.println(s);
//        }
//        
        String query ="select subjectname from subjectsnamecsereg2013 where subjectcode=\"ma6566\"";
        ResultSet rs = Components.executeSelectQuery(query);
        while(rs.next()){
            String s1;
            String s = rs.getString(1);
            System.out.println(s);
            try{
            s1 = rs.getString(2);
            }catch(Exception e){
                s1="vj";
                System.out.println(s1+e);
            }
        }
        int j = 20;
        if(Integer.parseInt(j+"")<40){
            System.out.println("value : "+j);
            System.out.println("final");
        }
        
        int batch =2014;
        System.out.println(batch+"-"+(batch+4));
        String s="initialized";
        Map m = new HashMap();
        try{
            m.put("6",sub.get(5));
        }catch(IndexOutOfBoundsException e){
            //sub.add(5,"vj");
            //s = sub.get(5);
            m.put("6","");
        }
        System.out.println("after get "+m.get("6") );
    }
    
}
