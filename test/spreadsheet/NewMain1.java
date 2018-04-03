/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spreadsheet;
import com.Project.Model.Components;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;


public class NewMain1 {

   public static void main(String[] args) {
      String sourceFileName = "/home/vijay_ravi/NetBeansProjects/test_fileupload/web/JasperResult/ResultAnalysis1.jrxml";

      System.out.println("Compiling Report Design ...");
      try {
          /**
          * Compile the report to a file name same as
          * the JRXML file name
          */
         JasperCompileManager.compileReportToFile(sourceFileName);
      } catch (JRException e) {
         e.printStackTrace();
      }
      float temp = 36,temp1=49;
      float x =(temp/49)*100;
      System.out.println("Done compiling!!! ..."+convert(temp,temp1)+ " " + Components.toRoman(7));
      
      Calendar cal = Calendar.getInstance();
System.out.println(new SimpleDateFormat("MMM").format(cal.getTime()));
   }
   static double convert(float t,float t1){
       return Math.round(((t/t1)*100)*10.0)/10.0 ;
   }
}