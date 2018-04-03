/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spreadsheet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author vijay_ravi
 */
public class property {

    /**
     * @param args the command line arguments
     *
     * 
     * class.getClass().getClassLoader().getResourceAsStream("Web-INF/web.xml")*/
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Properties p = new Properties();
        p.setProperty("dbAddress", "jdbc:mysql://localhost:3306/internalmarks");
        p.setProperty("userName", "root");
        p.setProperty("password", "oracle");
        p.setProperty("storageLocation", "/home/vijay_ravi/Desktop/Project_InternalMarks/");
        FileOutputStream fos = new FileOutputStream("/home/vijay_ravi/apache-tomcat-8.0.27/bin/vjconfig.properties");
        p.store(fos, null);
        System.out.println("File Created");
        System.out.println(System.getProperty("user.dir"));
//        FileInputStream fis = new FileInputStream("/home/vijay_ravi/apache-tomcat-8.0.27/bin/vjconfig.properties");
        
    }
    
   
    
}
