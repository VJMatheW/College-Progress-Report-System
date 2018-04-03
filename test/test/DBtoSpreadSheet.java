/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Math.round;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Vijay
 */
public class DBtoSpreadSheet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException, Exception {
        
        String sql = "select b.rollno ,a.name, hs6151, ma6151, ph6151, cy6151, ge6151, ge6152,truncate ((ifnull(hs6151,0)+ifnull(ma6151,0)+ifnull(ph6151,0)+ifnull(cy6151,0)+ifnull(ge6151,0)+ifnull(ge6152,0))/(ifnull(hs6151/hs6151,1)+ifnull(ma6151/ma6151,1)+ifnull(ph6151/ph6151,1)+ifnull(cy6151/cy6151,1)+ifnull(ge6151/ge6151,1)+ifnull(ge6152/ge6152,1)),1) as Percentage from tblcse2017pt1 as b, tblcse2017info as a where b.rollno in(select a.rollno from tblcse2017info where section=2)";

        //fetching from database
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/internalmarks","root","oracle");
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(sql);
        
        //fetching metadata of data
        ResultSetMetaData rsmd = rs.getMetaData();
        int column_count = rsmd.getColumnCount();
        String[] column_name = new String[column_count];
        String[] column_type = new String[column_count];
        int[] column_type_value = new int[column_count];
        for(int i=0;i<column_count;i++){
            column_name[i] = rsmd.getColumnName(i+1).toUpperCase();
            column_type[i] = rsmd.getColumnTypeName(i+1);
            column_type_value[i] = rsmd.getColumnType(i+1);
        }
        System.out.println("Column_NAME----Column_TYPE");
        for(int j=0;j<column_name.length;j++){
            System.out.println(column_name[j]+"-----------------"+column_type[j]+"----------------("+column_type_value[j]+")");
        }
        
        
        // creating Empty spreadsheet
        XSSFWorkbook work_book = new XSSFWorkbook();
        XSSFCellStyle style=(XSSFCellStyle) work_book.createCellStyle();
        style.setDataFormat(work_book.createDataFormat().getFormat("0.00"));
        XSSFSheet work_sheet = work_book.createSheet();
        
        
        // filling heading in spreadsheet
        Row head_row = work_sheet.createRow(0);
        for(int head_col =0;head_col < column_count;head_col++ ){
            Cell cell = head_row.createCell(head_col);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(column_name[head_col]);
        }
        
        
        // getting data to spreadsheet
        int row_num = 1;
        while(rs.next()){
            Row row = work_sheet.createRow(row_num++);
            int data_type = 0;String data="";
            for(int column_number = 1;column_number <= column_count;column_number++){
                Cell cell = row.createCell(column_number-1);
                data_type = rsmd.getColumnType(column_number);
                switch(data_type){
                    case 3:     //---DECIMAL                       
                        //data = ""+rs.getFloat(column_number);
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(rs.getFloat(column_number));
                        break;
                    case 4:     //---INT
                        //data = ""+rs.getInt(column_number);
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(rs.getInt(column_number));
                        break;
                    case -5:     //---BIGINT
                        //data = ""+rs.getLong(column_number);
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(rs.getLong(column_number));
                        break;
                    case 12:    //---VARCHAR
                        //data = rs.getString(column_number);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(rs.getString(column_number));
                        break;
                    default:
                        System.out.println("Undefined data_type_value = "+data_type);
                        throw new Exception("Undefined data type in DBtoSpreadSheet");
                }
                //cell.setCellValue(data);
            }            
        }
        
        FileOutputStream fs = new FileOutputStream("C:\\Users\\Vijay\\Desktop\\Test\\testInsertFinal.xlsx");
        work_book.write(fs);
        
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet();
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Hello");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("world");
        
        FileOutputStream fos = new FileOutputStream("C:\\Users\\Vijay\\Desktop\\Test\\testInsert.xlsx");
        book.write(fos);
    }
    
    //  Support function for rounding float value
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    
}
// DATA_TYPES TO FETCH
//  BIGINT- -5
//  INT- 4
//  VARCHAR- 12
//  DECIMAL- 3