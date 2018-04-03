/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChartTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author vijay_ravi
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JRException {
        
        String sourceFileName = 
         "/home/vijay_ravi/NetBeansProjects/test_fileupload/web/JasperResult/chart.jasper";
            String destFileName = "/home/vijay_ravi/Desktop/sample1.pdf";
            
            DataBeanList DataBeanList = new DataBeanList();
      ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();

      JRBeanCollectionDataSource beanColDataSource = new 
         JRBeanCollectionDataSource(dataList);

      Map p = new HashMap();
      p.put("min",0);
      p.put("max",100);
      try {
         JasperPrint jp = JasperFillManager.fillReport( sourceFileName,p, beanColDataSource);
         JasperExportManager.exportReportToPdfFile(jp, destFileName);
      } catch (JRException e) {
         e.printStackTrace();
      }
    }
    
}
