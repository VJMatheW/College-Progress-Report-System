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
import com.Project.Model.BasicInfo;
import com.Project.Model.Components;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class TestSpreadsheet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        // TODO code application logic here
        String t= "vj";
                t =System.getProperty("java.classpath");
        System.out.println(t);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("test");
        XSSFRow row = spreadsheet.createRow(0);
        ArrayList<String> sub = Components.fetchSemSubject("cse", "reg2013", "3");
        int cellid=0;
        //String head = sub.toString().replace("[", "").replace("]", "");
        ArrayList<String> fnl = new ArrayList<>();
        fnl.add("Reg no");
            fnl.add("Name");
        
        for (String s : sub){
            fnl.add(s);
        }
        
        for (String subject : fnl){
            Cell cell = row.createCell(cellid++);
            
            cell.setCellValue(subject);
        }
        
        FileOutputStream out = new FileOutputStream(new File(BasicInfo.storageLocation+"test6.xlsx"));
        workbook.write(out);
        out.close();
        System.out.println("test5 success fully created");
    }
    
}
