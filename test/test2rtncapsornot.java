
import com.Project.Model.BasicInfo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vijay
 */
public class test2rtncapsornot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Statement smt = BasicInfo.con().createStatement();
        ResultSet rs = smt.executeQuery("select * from tbllogin where id='admin' ");
        while(rs.next())
        {
            String name = rs.getString(1);
            System.out.println("Name : "+name);
        }
        
    }
    
}
