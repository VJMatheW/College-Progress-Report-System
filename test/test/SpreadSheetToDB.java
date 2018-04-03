/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.Project.Model.QueryGenerator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Vijay
 */
public class SpreadSheetToDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        FileInputStream fis = null;
        boolean status = false;
        ArrayList<String> queries = new ArrayList<String>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/internalmarks","root","oracle");
        Statement s = con.createStatement();
        
       try {
           
            fis = new FileInputStream("C:\\Users\\Vijay\\Desktop\\Project_Internalmarks\\Prof Made\\NEW\\civil1.xlsx");
//           System.out.println("after reading file "); 
//            
//            // for phone number separation 
//            XSSFWorkbook book = new XSSFWorkbook(fis);
//            Sheet datatypeSheet = book.getSheetAt(0);
//            Iterator<Row> row = datatypeSheet.iterator();
//            row.next();
//            while(row.hasNext()){
//                Cell cell = row.next().getCell(10);               
//                queries.add(cell.getStringCellValue());
//                System.out.println(cell.getStringCellValue());
//            }
//            
//           System.out.println(queries.size());
//           
//           // creating Empty spreadsheet
//        XSSFWorkbook work_book = new XSSFWorkbook();
//        XSSFCellStyle style=(XSSFCellStyle) work_book.createCellStyle();
//        style.setDataFormat(work_book.createDataFormat().getFormat("0.00"));
//        XSSFSheet work_sheet = work_book.createSheet();
//        
//        Row head_row = work_sheet.createRow(0);
//        
//            Cell cell = head_row.createCell(0);
//            cell.setCellType(CellType.STRING);
//            cell.setCellValue("Father_No");
//            
//            Cell cell1 = head_row.createCell(1);
//            cell1.setCellType(CellType.STRING);
//            cell1.setCellValue("Mother_No"); 
//            
//            int rownum = 1;
//            String[] two = null;
//            int count = 0;
//            for(String se : queries ){                 
//                System.out.println("CountNo : "+count+" Phone : "+se);
//                se = se.replace("PH", "").replace(":", "").trim();
//                System.out.println(se);
//                if(se.contains("/")){
//                    two = se.split("/");                   
//                }else{
//                    two[0] = se;                    
//                }
//                Row rowv = work_sheet.createRow(rownum);
//                
//                Cell cel = rowv.createCell(0);
//                cel.setCellType(CellType.STRING);
//                cel.setCellValue(two[0]);
//                
//                if(two.length == 2){
//                    Cell cel1 = rowv.createCell(1);
//                    cel1.setCellType(CellType.STRING);
//                    cel1.setCellValue(two[1]);
//                }
////                if(two.length == 2){
////                    Cell cel1 = rowv.createCell(1);
////                    if(two[1] == "NA"){
////                        cell.setCellType(CellType.STRING);
////                    }else{
////                        cel1.setCellType(CellType.STRING);
////                    }                
////                    cel.setCellValue(two[1]);
////                }
//
//                rownum++;
//                count++;
//            }
//        
//           FileOutputStream fs = new FileOutputStream("C:\\Users\\Vijay\\Desktop\\Project_Internalmarks\\Prof Made\\NEW\\numbers\\civil1No.xlsx");
//           work_book.write(fs);
           // for queries 
//           queries = QueryGenerator.buildQuery("cse", "2017", "reg2017", "nil", "info", "kjsdfh", fis);
//           for(int l = 0;l<queries.size();l++){
//               System.out.println(queries.get(l));
//               status = s.execute(queries.get(l));
//               if(status){
//                   
//               }else{
//                   System.out.println("queryNo : "+(l+1) +"not executed " );
//               }
//           }
           
           
       }catch(Exception e){
           System.out.println("File exception : "+e);
           fis.close();
       }
        
        
        fis.close();
        con.close();
    }
    
}
