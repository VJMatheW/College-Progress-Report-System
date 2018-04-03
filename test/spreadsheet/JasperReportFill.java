/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spreadsheet;

/**
 *
 * @author vijay_ravi
 */
import com.Project.Model.Components;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JasperReportFill {
   @SuppressWarnings("unchecked")
   public static void main(String[] args) throws Exception {
      String sourceFileName = 
         "/home/vijay_ravi/NetBeansProjects/test_fileupload/web/JasperResult/testResult.jasper";
      String destFileName = "/home/vijay_ravi/Desktop/test/sample.pdf";
      DataBeanList DataBeanList = new DataBeanList();
      
      ResultSet rs = Components.executeSelectQuery("select b.rollno ,a.name, cs6503, ma6566, cs6501, cs6502, cs6504,truncate ((ifnull(cs6503,0)+ifnull(ma6566,0)+ifnull(cs6501,0)+ifnull(cs6502,0)+ifnull(cs6504,0))/(ifnull(cs6503/cs6503,1)+ifnull(ma6566/ma6566,1)+ifnull(cs6501/cs6501,1)+ifnull(cs6502/cs6502,1)+ifnull(cs6504/cs6504,1)),2) as Percentage from tblcse2014pt1 as b, tblcse2014info as a where b.rollno in(select a.rollno from tblcse2014info where section=2)");
      ArrayList<DataBean> dataList = DataBeanList.getDataBeanList(rs);

      JRBeanCollectionDataSource beanColDataSource = new 
         JRBeanCollectionDataSource(dataList);
      Map p = new HashMap();
      p.put("scode1", "toc");
      p.put("scode2", "dm");
      p.put("scode3", "ooad");
      p.put("scode4", "cg");
      p.put("scode5", "ip");
      p.put("scode6", "");
      try {
        JasperPrint jp = JasperFillManager.fillReport(sourceFileName, p, beanColDataSource);
        JasperExportManager.exportReportToPdfFile(jp, destFileName);
      } catch (JRException e) {
         e.printStackTrace();
      }
   }
}