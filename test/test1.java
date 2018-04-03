
import com.Project.Model.BasicInfo;
import com.Project.Model.FormTable;
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
public class test1 {

    /**
     * @param args the command line arguments
     */
    
  //  public static int c = 0;
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        // TODO code application logic here
        
        Statement smt1 = BasicInfo.con().createStatement();
        ResultSet t = smt1.executeQuery("select rollno,ma6503, ma6566,cs6501,cs6502,cs6504 from tblcse2014pt1 ");
                        
        Statement smt = BasicInfo.con().createStatement();
        ResultSet rs = smt.executeQuery("select rollno,ma6503, ma6566,cs6501,cs6502,cs6504 from tblcse2014pt1 ");
             
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        ArrayList<String> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        ArrayList<String> c_name = new ArrayList<>();
        for (int i = 1;i<=count;i++)
        {
            String temp = "c_name :"+rsmd.getColumnName(i)+" c_typeName : "+rsmd.getColumnTypeName(i)+
                    " c_Label :"+rsmd.getColumnLabel(i)+" Column_type(int) :"+rsmd.getColumnType(i);
            a.add(temp);
            
            b.add(rsmd.getColumnTypeName(i));
            c_name.add(rsmd.getColumnName(i));
            
        }
        for (String as : a){
            System.out.println(as);
        }
        
//        while(rs.next())
//        {
//            System.out.println(rs.getLong(1)+""
//                    + ":"+rs.getInt(2)+":");
//        }
//      
//        int rowCount=0,v=0;
//        while (t.next()){
//            v++;
//   }
//      
        
        int rowCount= t.last()?t.getRow():0;
        System.out.println("ROW COUNT RETURNED : "+rowCount);
        String[][] data = new String[rowCount][count];
       int c =0;
       
        while(rs.next())
        {
            
            System.out.println("Inside while and value of c : "+c);
           
            for (int k=0;k<count;k++)
            {
                System.out.println("TYpe value : "+b.get(k));
                switch(b.get(k))
                {
                    case "BIGINT":
                        System.out.println("inside bigint k value :"+k+" c_name val :"+c_name.get(k));
                        Long temp = rs.getLong(k+1);
                        System.out.println("Long value :"+temp);
                        data[c][k] = temp.toString();
                        break;
                    case "INT":
                        System.out.println("Inside int");
                        String temp2 = c_name.get(k);
                        int d = rs.getInt(temp2);
                        System.out.println("int value :"+d);
                        data[c][k]= Integer.toString(d);                        
                        break;                    
                }
            } 
            ++c;          
        }
        
        for (int q=0;q<rowCount;q++){
            for (int w=0;w<count;w++){
                
                if(data[q][w].equals("-1")){
                    data[q][w]="AB";
                } 
                System.out.print(data[q][w]+",");
            }System.out.println();
        }
    }
    
}
