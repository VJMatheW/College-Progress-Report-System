/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import static com.Project.Model.Components.*;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Vijay
 */
public class QueryGenerator {   
    
    public static ArrayList buildQuery(String dept,String batch,String regulation, String sem,String type,String fileName,FileInputStream fis) throws Exception, ClassNotFoundException, SQLException, FileNotFoundException, IOException
    {
        ArrayList<String> query = new ArrayList<>();
        ArrayList<String> error = new ArrayList<>();
        String keywords="",subQuery = "",finalQuery = "",updateKey = "update "+"tbl"+dept+batch+type+" set ";
        String line = ""; int count = 0;
        XSSFWorkbook book = new XSSFWorkbook(fis);
        Sheet datatypeSheet = book.getSheetAt(0);
        Iterator<Row> rows = datatypeSheet.iterator();
        Row temp = rows.next();
        int lenOfRow = (int)temp.getLastCellNum();
        temp = null;      
        
        switch(type){            
            case"info": 
                if(lenOfRow != 19){
                    error.add("Error : Their is "+(19 - lenOfRow)+" column missing in the file <br> Check to it that there is 19 Columns");
                }else{
                    int[] character_no={20,100,100,100,100,100,100,100,100,100,20,20,20,100,100,10,10,20,10};
                    String wordTest_temp="";int rowNo=1;
                    while(rows.hasNext()){
                        rowNo++;
                        keywords = "insert into tbl"+dept+batch+"info"+" values(";
                        Row currentRow = rows.next();
                        Iterator<Cell> cell = currentRow.iterator();
                        long l = (long)cell.next().getNumericCellValue();
                        if(l == 0){
                            continue;
                        }
                        keywords += l;                    

                        for(int k=1;k<lenOfRow;k++){                          
                            Cell currentCell = currentRow.getCell(k);
                            try {
                                CellType cellType = currentCell.getCellTypeEnum();

                                if(cellType == CellType.STRING){
                                    //System.out.println("String : "+currentCell.getStringCellValue());
                                    wordTest_temp = currentCell.getStringCellValue().replace("\"", "").replace("\'", "");
                                    if(wordTest_temp.equals("")){
                                        wordTest_temp = "NA";
                                    }
                                }else if(cellType == CellType.NUMERIC){                            
                                    if(HSSFDateUtil.isCellDateFormatted(currentCell)){
                                        DataFormatter dataFormatter = new DataFormatter();
                                        wordTest_temp = dataFormatter.formatCellValue(currentCell);
                                    }else{
                                        long xl = (long)currentCell.getNumericCellValue();
                                        wordTest_temp = ""+xl;
                                    }                           
                                }else if(currentCell.getCellTypeEnum() == CellType.BLANK){
                                    wordTest_temp = "NA";
                                }

                                // checking for length of the words
                                if(wordTest_temp.length() <= character_no[k]){
                                    if(wordTest_temp == ""){
                                        wordTest_temp = "NA";
                                    }
                                }else{
                                    wordTest_temp = "Too Long";
                                    error.add("Error : RowNo :"+rowNo+" ColumnNo :"+(k+1)+" length of word is too long <br> Length of character should be less than "+character_no[k]
                                    + " (including spaces tabspaces and special characters) <br> The word you have entered contains : "+wordTest_temp.length()+"<br>");
                                }

                            }catch(NullPointerException npe){
                                System.out.println("Exception build query xlsx file rowno:"+rowNo+" columnNo : "+k+" is blank");
                                wordTest_temp="NA";
                            }                                                
                            keywords += ",\""+wordTest_temp+"\"";
                        }
                        keywords += ")";
                       // keywords = keywords.replace("values(\"", "values(").replaceFirst("\",\"", ",\"");
                        System.out.println(keywords);
                        query.add(keywords);
                        //System.out.println("---------------------------------------------------------------");
                        keywords = null;
                    } 
                }//else of length check 

                    // append error arraylist to queries if their is any error
                    if(error.size() != 0){
                        //String toSingleLine = error.toString().replace("[", "").replace("]", "").replace(",", "<br>").trim();
                        query.add(error.toString().replace("[", "").replace("]", "").replace(",", "<br>").trim());
                    }
                    error.clear();

                    break;

                case"pt1":
                case"pt2":
                case"pt3":   
                    //System.out.println("Inside PT build Query = "+dept+" ");
                    ArrayList<String> semSubjects = Components.fetchSemSubject(dept,regulation,sem);
                    if(semSubjects.get(semSubjects.size()-1).startsWith("Error")){
                        error.add(semSubjects.get(semSubjects.size()-1));
                    }
    //                ArrayList<String> semSubjects = new ArrayList<>();
    //                semSubjects.add("cs6801");
    //                semSubjects.add("elective4");
    //                semSubjects.add("elective5");
                    if(semSubjects.size() == lenOfRow - 2){
                        //System.out.println("inside if 1");
                        int o=1,i;
                   
                        while(rows.hasNext()){
                            ++o;
                            //System.out.println("inside while");
                            Row row = rows.next();
                            Iterator<Cell> cell = row.iterator();
                            long regNo = (long)cell.next().getNumericCellValue();
                            if(regNo == 0){
                                continue;
                            }
                            cell.next();// for skipping NAME of the student

                            //     iterating through cell of a rows to get marks of single regNo
                            for(i = 0;i < semSubjects.size(); i++){
                                String f = null;
                                Cell cel = cell.next();
                                //System.out.println("inside for");
                                switch(QueryGenerator.getType(cel)){
                                    case'i':
                                        int mark = (int)cel.getNumericCellValue();
                                        if(mark <= 100){
                                            f = ","+semSubjects.get(i)+"="+mark;
                                        }else{
                                            //f = ","+semSubjects.get(i)+"=";
                                            error.add("Error : Mark greater than 100 at Row :"+o+" Column :"+i+" !!!");                                            
                                        }                                            
                                        //System.out.println("Int : val of i "+i+" val of rows "+o);
                                        break;
                                    case's':
                                        f="ab";
                                        //System.out.println("String : val of i "+i+" val of rows "+o+" regNo"+regNo);
                                        break;                       
                                }
                                if(!f.equals("ab")){
                                    subQuery += f;
                                }
                            }
                            finalQuery = updateKey + subQuery + " where rollno="+regNo;
                            finalQuery = finalQuery.replace("set ,","set ");// to remove comma after "update table set ,cs6801=50 where reg..."
                            subQuery = "";
                            if(finalQuery.contains("update tbl"+dept+batch+type+" set  where")){

                            }else{
                                query.add(finalQuery);                            
                            }
                            finalQuery="";
                        }
                        
                }else{
                    error.add("Error : Column Mismatch");
                }
                    
                // append error arraylist to queries if their is any error
                if(error.size() != 0){
                    //String toSingleLine = error.toString().replace("[", "").replace("]", "").replace(",", "<br>").trim();
                    query.add(error.toString().replace("[", "").replace("]", "").replace(",", "<br>").trim());
                }
                error.clear();                    
                    
                break;
            case "term1":
            case "term2":
            case "term3":
                while(rows.hasNext()){
                    Row row = rows.next();
                    long regNo = (long)row.getCell(0).getNumericCellValue();
                    if(regNo == 0){
                        continue;
                    }
                    try{                        
                        subQuery = "thoursem"+sem+"="+(int)row.getCell(2).getNumericCellValue()+",sem"+sem+"="+(int)row.getCell(3).getNumericCellValue();
                        finalQuery = updateKey + subQuery + " where rollno="+regNo;
                        subQuery="";
                        query.add(finalQuery);
                        finalQuery="";
                    }catch(Exception e){
                        System.out.println("Exception in QueryGenerator.buildQuery  case:\"term\"  "+e);                        
                        break;
                    }                    
                }
        }
        return query;
    }
    
    public static String createMarksRetriveQuery(ArrayList<String> subjects,String dept,String batch,String type,String sec){
        String query="",sub,tblName;
        
        sub = subjects.toString().replace("[","").replace("]","");
        
        tblName = "tbl"+dept+batch+"info as a inner join tbl"+dept+batch+type+" as b on a.rollno = b.rollno";
        String className = new Exception().getStackTrace()[1].getClassName();        
        if(className.equals("com.Project.Servlets.Report")){
            query = "select a.rollno ,a.name, "+sub+" from "+tblName+" where a.section="+sec;                
            System.out.println("Query for report");
        }else if(className.equals("com.Project.Servlets.Result")){
            query = "select a.rollno ,a.name, "+percentageCalc(subjects)+" as Percentage, "+sub+"  from "+tblName+" where a.section="+sec;                
        }else{
            query = "select b.rollno ,a.name, "+sub+","+percentageCalc(subjects)+" as Percentage from "+tblName+" where a.section="+sec;                
        }
        
        System.out.println(className);
        System.out.println("THe generated query for Marks : "+query);  
        return query;
    } 

    public static String createInfoRetriveQuery(String dept,String batch,String[] col,String sec) {
        
        String tblName,query;
        
        tblName = "tbl"+dept+batch+"info";
        
        System.out.println("Table name "+ tblName);
        query = "select  "+Arrays.toString(col).replace("[", "").replace("]", "").replace("{", "").replace("}", "")+
                " from "+tblName+" where section="+sec;
       // System.out.println("generated query : "+query);
        return query;
    }
    
    public static String createAttendanceRetrieveQuery(String dept,String batch,String type,String sec,String sem){
        
        String tblName="",query="";
        tblName = "tbl"+dept+batch+"info as a inner join tbl"+dept+batch+type+" as b on a.rollno = b.rollno";
        String className = new Exception().getStackTrace()[1].getClassName();        
        
        if(className.equals("com.Project.Servlets.Report")){
            
            if(type.equals("pt1")){
                type="term1";
            }if(type.equals("pt2")){
                type="term2";
            }if(type.equals("pt3")){
                type="term3";
            }            
            tblName = "tbl"+dept+batch+"info as a inner join tbl"+dept+batch+type+" as b on a.rollno = b.rollno";
                     
            query = "select f_name,p_addr_line1,p_addr_line2,p_addr_line3,thoursem"+sem+",(thoursem"+sem+"-sem"+sem+")as absent,sem"+sem+",truncate((sem"+sem+"/thoursem"+sem+")*100,2) as Percentage,f_mobile"+
                " from "+tblName+" where a.section="+sec;
            System.out.println("Query for Reportcard Attendance");
            
        }else{
            //        select b.rollno,a.name,thoursem1,sem1 from tblcse2014term1 as b, tblcse2014info as a 
//where b.rollno in (select a.rollno from tblcse2014info where section=2);
            query = "select b.rollno,a.name,thoursem"+sem+",sem"+sem+",truncate((sem"+sem+"/thoursem"+sem+")*100,2) as Percentage"+
                " from "+tblName+" where a.section = "+sec;
        }                
        System.out.println("Generated Attendance Query : "+query);
        return query;
    }
   
//    truncate((ifnull(ma6503,0)+  ifnull(ma6566,0)+ ifnull(cs6501,0)+ ifnull(cs6502,0)+ ifnull(cs6504,0))
//    /(ifnull(ma6503/ma6503,0)+ifnull(ma6566/ma6566,0)+ifnull(cs6501/cs6501,0)+ifnull(cs6502/cs6502,0)+
//    ifnull(cs6504/cs6504,0)),2)
//    
      
    public static String percentageCalc(ArrayList<String> subjects){
        
        String middle,numerator,denominator,test="",test2="";
        
        int size = subjects.size();
        
        ArrayList<String> f = new ArrayList<>();
        ArrayList<String> d = new ArrayList<>();
                 
        for (int y=0;y<size;y++){
            f.add("ifnull("+subjects.get(y)+",0)");
            d.add("ifnull("+subjects.get(y)+"/"+subjects.get(y)+",0)");
        }
        
        for (int z=0;z<size;z++){
            test += f.get(z);
            test2 +=d.get(z);
            if(z<size-1){
                test += "+";
                test2 += "+";
            }
        }
        
        middle = "truncate (("+test+")/("+test2+"),1)";
        
        return middle;
    }
    
    public static ArrayList<String> subjectNameRetrive(ArrayList<String> subjects,String dept,String reg) throws Exception{
        ArrayList<String> tbody = new ArrayList<>();
        String query="";int c=1;
        for (String s : subjects){
            query = "select subjectname,faculty,shortform from subjectsname"+dept+reg+" where subjectcode=\""+s+"\"";
            ResultSet r = Components.executeSelectQuery(query);
            r.next();
            tbody.add("<tr id=\"tr\"><td>"+c+"</td><td>"+s.toUpperCase()+"</td><td>"+r.getString(1).toUpperCase()+"</td><td>"+r.getString(2)+"</td><td></td><td></td>"
                    + "<td></td><td></td><td></td><td hidden=\"true\">"+r.getString(3).toUpperCase()+"</td></tr>");
            c++;
        }        
        return tbody;
    }  
    
    public static char getType(Cell cell){
        if(cell.getCellTypeEnum() == CellType.STRING){
            return 's';
        }
        return 'i';
    }
    
    public static ArrayList<String> rollNoToRegNoQuery(String dept,String batch,FileInputStream fis){
        ArrayList<String> query = new ArrayList<>();
        XSSFWorkbook book;
        String tbl = "tbl"+dept+batch+"info";
        try {
            book = new XSSFWorkbook(fis);
            Sheet datatypeSheet = book.getSheetAt(0);
            Iterator<Row> rows = datatypeSheet.iterator();
            Row temp = rows.next();
            int lenOfRow = (int)temp.getLastCellNum();
            if(lenOfRow == 2){
                while(rows.hasNext()){
                    Row row = rows.next();
                    if(row.getCell(0).getCellTypeEnum() == CellType.NUMERIC && row.getCell(1).getCellTypeEnum() == CellType.NUMERIC){
                        query.add("update "+tbl+" set rollno="+(long)row.getCell(1).getNumericCellValue()+" where rollno="+(long)row.getCell(0).getNumericCellValue());
                    }else{
                        query.clear();
                        query.add("Error : One of cell Contains Alphabetic Character");
                        throw new Exception("Alphabetic Character is one of the cell");
                    }
                }
            }else{
                query.add("Error : No of Columns Mismatch");
            }
        } catch (Exception ex) {
            System.out.println("Exception in rollNoToRegNoQuery Exception is "+ex);
        }
        return query;
    }
}