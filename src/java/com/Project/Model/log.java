/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author vijay_ravi
 */
public class log {
    
    public static void println(String dept,String text) throws IOException{
        Calendar cal = Calendar.getInstance();
        FileWriter fw = null;
        BufferedWriter bw = null;
        //String folderName = BasicInfo.storageLocation+"log/"+dept+"/";
        String folderName = BasicInfo.storageLocation+"log\\"+dept+"\\";
        //String fileName= BasicInfo.storageLocation+"log/"+dept+"/"+new SimpleDateFormat("dd_MM_yyyy").format(cal.getTime())+".txt";
        String fileName= BasicInfo.storageLocation+"log\\"+dept+"\\"+new SimpleDateFormat("dd_MM_yyyy").format(cal.getTime())+".csv";
        System.out.println("Log folder Name : "+folderName);
        System.out.println("Log file name : "+fileName);
        
        
        File folder = new File(folderName);
        if(!folder.exists()){
            folder.mkdirs();
        }  
        File f = new File(fileName);
        if (!f.exists()) {
            f.createNewFile();
        }        
        fw = new FileWriter(fileName,true);
        bw = new BufferedWriter(fw);
        bw.write(text+"\n");
        bw.close(); 
    }
    
    public static String time(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
    
    public static String ip(HttpServletRequest request){
        String remoteAddr = null;

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
    
}
