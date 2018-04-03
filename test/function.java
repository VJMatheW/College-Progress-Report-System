
import static com.Project.Model.BasicInfo.con;
import com.Project.Model.Components;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vijay
 */
public class function {
    
 public static void main(String args[]) throws ClassNotFoundException, SQLException
 {
    String dep="cse",reg="reg2013",sem="";
    ArrayList<String> subjects = Components.fetchSemSubject(dep, reg, sem);
    System.out.println("no of subjects : "+subjects.size());
    System.out.println("subjects :"+subjects);
    String queryPrefix = "( rollno bigint",querySuffix=",foreign key (rollno) references ";
    String test = "";
    for(int j=0;j<subjects.size();j++)
    {
        test += ","+subjects.get(j)+" int default 0 ";
    }
    String query = queryPrefix+test+querySuffix;
    System.out.println("Final query : "+query);
    for (int i=1;i<=3;i++)
    {
        
    }
 }
}
