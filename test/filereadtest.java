

import static com.Project.Model.Components.readFile;
import com.Project.Model.QueryGenerator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
public class filereadtest {
    public static void main(String args[]) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException, Exception
    {
        String batch = "2013",dept="cse",regulation="reg2013",type="pt1",semester="5",filename="vijay",line="";int count = 0;
//        String keywords = "insert into "+"tbl"+dept+batch+type+" values(";
//                    BufferedReader brr = readFile(dept,batch,fileName);
//                    while((line = brr.readLine())!= null)
//                    {
//                        if(count != 0)
//                        {
//                            System.out.println("Br value"+line);
//                        }count++;
//                    }

   // ArrayList<String> s = QueryGenerator.buildQuery(dept, batch, regulation, semester, type, filename);
   // System.out.println(s);
   
   
   String s[]=";;;;4128141;34;45;45".split(";");
   for (int i=0;i<s.length;i++)
   {
       System.out.println("Index ["+i+"] data :"+s[i]);
   }
    }
    
}
