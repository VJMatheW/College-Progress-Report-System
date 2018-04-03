/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Model;

import java.sql.ResultSet;
import java.time.Year;
import java.util.ArrayList;
import javax.servlet.http.Part;

/**
 *
 * @author Vijay
 */
public class CheckList {
    public static ArrayList checkList(String dept,String batch,String fileName,Part filePart)
    {
        ArrayList<String> error = new ArrayList<>();
       if (Integer.parseInt(batch) > Integer.parseInt(Year.now().toString()))
            {
                error.add("Slected year greater than current year");
                System.out.println("Slected year greater than current year");
            }
       if(!Components.checkFolderExist(dept, batch))
            {
                error.add("Table is not created <b>cotact admin</b> ");
            }else {
                if(Components.checkXLSXFileExist(dept, batch, fileName))
                {
                    error.add("File with this name already exists");
                   // Components.deleteFile(dept, batch, fileName);
                    System.out.println("File with this name already exists");
                } 

            }    
       System.out.println("File type : "+filePart.getContentType());
       //if (!"application/vnd.ms-excel".equals(filePart.getContentType()))
       if (!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(filePart.getContentType()))
            {                
                error.add("Inappropriate file type you can upload only <b>.xlsx</b> files you have uploaded "+filePart.getContentType()+" type of file");
                System.out.println("Inappropriate file type you can upload only .csv files");
            }
       return error;
    }
    
    public static ArrayList<String> checkUpload(String type, String dept, String batch, String section,String sem,String ptNo) throws Exception{
        String query="";
        boolean info=false;
        int ptSem=0,attdSem=0,elective=0;
        ArrayList<String> error = new ArrayList<>();
        query = "select info,"+ptNo+",term"+ptNo.charAt(2)+",elective from tblfileuploadsentry where class=\""+batch+dept+section+"\"";
        
                ResultSet rs = Components.executeSelectQuery(query);
                if(rs.next()){
                    info = rs.getBoolean(1);
                    ptSem = rs.getInt(2);
                    attdSem = rs.getInt(3);
                    rs.getStatement().getConnection().close();
                    System.out.println("From CheckList.checkUpload Info "+info+" ptSem "+ptSem+" attdSem "+attdSem);
                }else{
                    rs.getStatement().getConnection().close();
                    System.out.println("From CheckList.checkUpload there is no class like that");
                }
        switch(type){
            case "report/email":
                if(info){
                    if(ptSem >= Integer.parseInt(sem)){
                        if(attdSem >= Integer.parseInt(sem)){
                            
                        }else{
                            error.add("TERM-"+ptNo.charAt(2)+" attendance for semester-"+sem+" not Uploaded");
                        }
                    }else{
                        error.add(ptNo.toUpperCase()+" Marks for the selected semester not Uploaded");
                    }
                }else{
                    error.add("Basic Infomation Not Uploaded...!!!");
                }
                break;
            case "result":
                if(info){
                    if(ptSem >= Integer.parseInt(sem)){
                        
                    }else{
                        error.add(ptNo.toUpperCase()+" Marks for the selected semester not Uploaded");
                    }
                }else{
                    error.add("Basic Infomation Not Uploaded...!!!");
                }
                break;
        }
        
        return error;
    }
    
    public static boolean checkEnableOrDisabled(int i){
        if(i == 0){
            return false;
        }
        return true;
    }

    
}
