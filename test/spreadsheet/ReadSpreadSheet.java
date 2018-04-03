/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spreadsheet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
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
public class ReadSpreadSheet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\Vijay\\Desktop\\test.xlsx");
        XSSFWorkbook book = new XSSFWorkbook(fis);
        Sheet datatypeSheet = book.getSheetAt(0);
        Iterator<Row> rows = datatypeSheet.iterator();    
        int i=1;
        String wordTest_temp = "";
        while(rows.hasNext()){            
            Row temp = rows.next();                        
            Iterator<Cell> cell = temp.iterator();
            System.out.println("Row NO : "+ i++ +"   \tRow Length :"+temp.getLastCellNum());
            for(int cn = 0;cn<3;cn++){
                Cell currentCell = temp.getCell(cn);
                //System.out.println("Type : "+currentCell.getCellTypeEnum().toString());
                try{
                    
                    CellType type = currentCell.getCellTypeEnum();
                    
                if(type == CellType.STRING){
                            //System.out.println("String : "+currentCell.getStringCellValue());
                            if(currentCell.getStringCellValue() != ""){
                                wordTest_temp = currentCell.getStringCellValue();
                            }else{
                                wordTest_temp = "NA";
                            }                            
                            //System.out.println(keywords);
                        }else if(type == CellType.NUMERIC){                            
                            if(HSSFDateUtil.isCellDateFormatted(currentCell)){
                                DataFormatter dataFormatter = new DataFormatter();
                                wordTest_temp = dataFormatter.formatCellValue(currentCell);
                            }else{
                                long xl = (long)currentCell.getNumericCellValue();
                                wordTest_temp = ""+xl;
                            }                           
                        }else if(type == CellType.BLANK){
                            wordTest_temp = "NA";
                        }
                }catch(NullPointerException e){
                    System.out.println("Exception occured");
                    wordTest_temp = "NA";
                }
                System.out.println("Content of cell "+cn+" : "+wordTest_temp);
                wordTest_temp = "";
                
            }
            System.out.println("\n");
            
        }
        
    }
    
}
