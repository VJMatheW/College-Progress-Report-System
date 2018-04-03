/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResultReportTest;

import com.Project.Model.Components;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author vijay_ravi
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    
    static Logger logger = Logger.getLogger(NewMain.class);
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        
        String dept="cse",regulation="reg2013",sem="5";
        
        ArrayList<String> subject = Components.fetchSemSubject(dept, regulation, sem);
        System.out.println(subject.toString());
        
        ResultSet rs = Components.executeSelectQuery("select rollno,name from tblcse2014info");
        
        if (rs != null){
            
        ArrayList<DataBean> data = DataBeanList.createDataSource();
        
        Map param = new HashMap();
        
        param.put("result", "Pass");
        
        String src = "/home/vijay_ravi/NetBeansProjects/test_fileupload/web/jasper/test.jasper";
                String fileName = null;
                String dest = "/home/vijay_ravi/Desktop/test23.pdf";
                String p = System.getProperty("user.dir")+"/log4j.properties";
                PropertyConfigurator.configure(p);
                System.out.println(p);
            JRBeanCollectionDataSource s = new JRBeanCollectionDataSource(data);
            try {
                JasperPrint jp = JasperFillManager.fillReport(src, param, s);
                JasperExportManager.exportReportToPdfFile(jp,dest);
            }catch(Exception e){
                System.out.println("Inside catch block ");
                System.out.println(e);
            }
        
        }
}
    
}
