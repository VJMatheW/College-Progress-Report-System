/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.listner;

import com.Project.Model.BasicInfo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Web application lifecycle listener.
 *
 * @author vijay_ravi
 */
public class MyListner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties prop = new Properties();
        FileInputStream fis;
        try {
            String path=System.getProperty("user.dir")+"\\vjconfig.properties";
            System.out.println("THe path for file is : "+path);
            //String path=System.getProperty("user.dir")+"\\vjconfig.properties";
            fis = new FileInputStream(path);
            prop.load(fis);
            BasicInfo.dbAddress = prop.getProperty("dbAddress");
            BasicInfo.username = prop.getProperty("userName");
            BasicInfo.password = prop.getProperty("password");
            BasicInfo.storageLocation = prop.getProperty("storageLocation");
            fis.close();
             
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyListner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyListner.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
