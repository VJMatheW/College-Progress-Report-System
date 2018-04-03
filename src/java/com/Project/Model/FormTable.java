/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author vijay_ravi
 */
public class FormTable {   
    
    public static ArrayList createHeader(ResultSet rs) throws SQLException, Exception{
       // System.out.println("Inside form table create header");
        ArrayList<String> data = new ArrayList<>();
        
        ResultSetMetaData rsmd = rs.getMetaData();
                
        int c_count = rsmd.getColumnCount();
        
        int rowCount = rs.last()?rs.getRow():0;
        
        rs.beforeFirst();
        
        //System.out.println("rowCount value = "+rowCount);
        
        if(rowCount>0){
            ArrayList<String> c_name = new ArrayList<>();
            ArrayList<String> c_type = new ArrayList<>();
            ArrayList<String> t_head = new ArrayList<>();

            for(int j=1;j<=c_count;j++)
            {
                t_head.add("<th id=\"th\" onclick=\" sortTable("+(j-1)+");\" >"+rsmd.getColumnName(j)+"</th>");
                c_name.add(rsmd.getColumnName(j));
                c_type.add(rsmd.getColumnTypeName(j));

            }
            String className = new Exception().getStackTrace()[1].getClassName();        
            if(className.equals("com.Project.Servlets.ResultAnalysis")){
                data.add("<tr id=\"tr\"><th id=\"th\">S.No</th>"+t_head.toString().replace(",","").replace("[", "").replace("]", "")+"<th id=\"th\" >Result</th><th id=\"th\" >No of sub<br/> failed</th><th id=\"th\" >No of sub<br/> Absent</th></tr>");
            }else{
                data.add("<tr id=\"tr\">"+t_head.toString().replace(",","").replace("[", "").replace("]", "")+"</tr>");
            }
            data.add(FormTable.fetchData(rs, rowCount, c_count, c_name, c_type,className));
        }else {
            System.out.println("in else clause");
            data.add("No data retrived");
            data.add("posibbly data may not be upploded");
        }
        return data;
    }    
    
    public static String fetchData(ResultSet rs,int rowCount,int c_count,ArrayList<String> c_name,ArrayList<String> c_type,String className) throws SQLException{
        
        String[][] data = new String[rowCount][c_count];
    //    System.out.println("inside fetch data");
        int c =0,o=0;        
        while(rs.next())
        {
        //    System.out.println("result : "+rs.getLong(1)+" : "+rs.getString(2)+" : "+rs.getInt(3)+" : "+rs.getInt(4)+" : ");
            
            for (int k=0;k<c_count;k++)
            {
                switch(c_type.get(k))
                {
                    case "BIGINT":
                        Long temp = rs.getLong(k+1);
                        data[c][k] = temp.toString();
                        break;
                    case "INT":
                        data[c][k] = Integer.toString(rs.getInt(k+1));
                        if(rs.wasNull()){
                            data[c][k]="AB";
                        }
                        break;
                    case "VARCHAR":
                        data[c][k] = rs.getString(k+1);
                        break;
                    case "DECIMAL":
                        System.out.println("inside decimal "+ (++o));
                        //BigDecimal b = new BigDecimal(rs.getBigDecimal(k+1)+"");
                        data[c][k] = rs.getBigDecimal(k+1)+"";                        
                        if(rs.wasNull()){
                            System.out.println("Inside decimal and it is null");
                            data[c][k] = "0.0";
                        }
                        break;

                }
            }c++;

        }
        //System.out.println("After inserting data to two dimensional array");
        
        ArrayList<String> row = new ArrayList<>();
        for (int a=0;a<rowCount;a++){
            String d="";
            System.out.println("tr : "+(a+1));
           // System.out.println("inside for loop ");
            for(int b=0;b<c_count;b++){
                if(data[a][b].equals("-1")){
                    data[a][b]="AB";
                }
                d=d+"<td id=\"td\">" +data[a][b]+ "</td>";
            }
            //System.out.println("<tr>"+d+"</tr>\n");
            if(className.equals("com.Project.Servlets.ResultAnalysis")){
                row.add("<tr id=\"tr\"><td id=\"td\" >"+(a+1)+"</td>"+d+"<td id=\"td\" ></td><td id=\"td\" ></td><td id=\"td\" ></td></tr>");
            }else{
                row.add("<tr id=\"tr\">"+d+"</tr>");
            }
        }
        return row.toString().replace("[", "").replace("]", "").replace(",", "");
        
    }
    
    public static XSSFWorkbook dbtoSpreadSheet(String query) throws SQLException {
        
        XSSFWorkbook work_book = null;
        ResultSet rs = null;
        try{
            //fetching from database
            rs = Components.executeSelectQuery(query);
            
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
            
            
            // creating Empty spreadsheet
            work_book = new XSSFWorkbook();
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
                            Double k = rs.getDouble(column_number);
                            cell.setCellType(CellType.NUMERIC);
                            if(rs.wasNull()){
                                cell.setCellValue(0.0);
                            }else{
                                cell.setCellValue(k);
                            }
                            break;
                        case 4:     //---INT
                            //data = ""+rs.getInt(column_number);
                            int n = rs.getInt(column_number);
                            if(rs.wasNull()){
                                cell.setCellType(CellType.STRING);
                                cell.setCellValue("AB");
                            }else{
                                cell.setCellType(CellType.NUMERIC);
                                cell.setCellValue(n);
                            }
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
            rs.getStatement().getConnection().close();
        }catch(Exception e){
            if(rs != null){
                rs.getStatement().getConnection().close();
            }            
            work_book = null;
        }      
        return work_book;
    }
    
}
