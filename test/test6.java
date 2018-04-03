
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.net.*;
import java.sql.Statement;
import com.Project.Model.BasicInfo;
import com.Project.Model.Components;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vijay_ravi
 */
public class test6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, UnknownHostException, SocketException, IOException {
        // TODO code application logic here
      //  Admin.insertIntoDeptEntry("2019", "cse");
       Statement smt =  BasicInfo.con().createStatement();
//       ResultSet rs = smt.executeQuery("select ma6503 from tblcse2015pt1 where rollno=412814104057");
//       rs.next();
//       int ival=0; //= //rs.getInt(1);
//       System.out.println("before null : "+rs.getInt(1));
//       if(rs.wasNull()){
//           System.out.println("rs was null");
//           ival=99;
//       }
//       System.out.println("value : "+ival);

     // System.out.println(Components.fetch("dept"));
     ResultSet rs = smt.executeQuery("select * from tblfileuploadsentry");
     while (rs.next()){
         String a = rs.getString(1);
         Boolean b = rs.getBoolean(2);
         String g = b.toString();
         System.out.println("Boolean string value : " + g);
     }
      
    }
}
