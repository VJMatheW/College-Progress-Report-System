
import com.Project.Model.BasicInfo;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vijay_ravi
 */
public class test3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Statement st = BasicInfo.con().createStatement();
        ResultSet rs = st.executeQuery("select name from tblcse2014info");
        ResultSetMetaData rsmd = rs.getMetaData();
        int i = rs.last()?rs.getRow():0;
        System.out.println("No of rows : "+i);
        rs.beforeFirst();
        while(rs.next())
        {
            System.out.println(rs.getString(1));
        }
    }
    
}
