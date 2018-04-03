package test;

import com.Project.Model.QueryGenerator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestFileUpload {

    public static void main(String[] args) throws FileNotFoundException, Exception {
        FileInputStream fis = new FileInputStream("C:\\Users\\Vijay\\Desktop\\Test\\testRollNotoRegNo.xlsx");
        //ArrayList<String> query = QueryGenerator.buildQuery("cse", "2018", "reg2013", "8", "pt1", "test", fis);
        ArrayList<String> query = QueryGenerator.rollNoToRegNoQuery("cse", "2017", fis);
        for (String s : query){
            System.out.println("hell yeah : "+s);
        }
        
//        String keywords = "insert into "+"tbl"+"cse"+"2014"+"info"+" values(";
//                    XSSFWorkbook book = new XSSFWorkbook(fis);
//                    Sheet dataTypeSheet = book.getSheetAt(0);
//                    Iterator<Row> rows = dataTypeSheet.iterator();
//                    rows.next();
//                    while(rows.hasNext()){
//                        keywords = "insert into "+"tbl"+"cse"+"2014"+"info"+" values(";
//                        Row row = rows.next();
//                        if(row != null){
//                            Iterator<Cell> cells = row.iterator();
//                            long t = (long)cells.next().getNumericCellValue();
//                            keywords += t;
//                            while(cells.hasNext()){
//                                Cell curCell = cells.next();
//                                if(curCell.getCellTypeEnum() == CellType.STRING){
//                                    keywords += ","+curCell.getStringCellValue();
//                                }else if(curCell.getCellTypeEnum() == CellType.NUMERIC){
//                                    if(HSSFDateUtil.isCellDateFormatted(curCell)){
//                                        DataFormatter df = new DataFormatter();
//                                        keywords += ","+df.formatCellValue(curCell);
//                                    }
//                                }
//                            }
//                        }
//                        keywords += ")";
//                        String replace = keywords.replace(",", "\",\"");
//                        System.out.println(replace);
//                        keywords = null;
//                    }                            
    }
    
}
