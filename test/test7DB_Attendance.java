
import com.Project.Model.Components;
import com.Project.Model.QueryGenerator;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vijay_ravi
 */
public class test7DB_Attendance {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String sql = "select f_name,p_addr_line1,p_addr_line2,p_addr_line3,thoursem1,(thoursem1-sem1) as absent,sem1, truncate((sem1/thoursem1)*100,2) as Percentage from tblcse2014term1 as b, tblcse2014info as a where b.rollno in (select a.rollno from tblcse2014info  where section = 2);";
        ResultSet rs = Components.executeSelectQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        for(int i=1;i<=count;i++){
            System.out.println(rsmd.getColumnName(i)+" , "+rsmd.getColumnTypeName(i));
        }
        ArrayList<String> sem = Components.fetchSemSubject("cse", "reg2013", "5");
        System.out.println(QueryGenerator.createMarksRetriveQuery(sem,"cse", "2014", "pt1", "2"));
    }
    
}
