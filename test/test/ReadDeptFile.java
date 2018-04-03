/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.Project.Model.BasicInfo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ReadDeptFile {
    public static void main(String args[]) throws IOException{
        String line,data[]=null,fin="na",fileName="hello",location = "C:\\Users\\Vijay\\Desktop\\Project_Internalmarks\\" ;
        ArrayList<String> s = new ArrayList<>();
        BufferedReader br1=null;
        try{
             //br1 = new BufferedReader(new FileReader(BasicInfo.storageLocation+"res/"+fileName+".txt"));
             System.out.println(location+"res\\"+fileName+".txt");
             br1 = new BufferedReader(new FileReader(location+"res\\"+fileName+".txt"));
             while((line=br1.readLine())!=null){
            data = line.split(",");
            System.out.println("data length = "+data.length);
            System.out.println("<option value=\""+data[0]+"\"> "+data[1]+"</option>");
            s.add("<option value=\""+data[0]+"\"> "+data[1]+"</option>");
            fin = s.toString().replace("[", "").replace("]", "").replace(",", "");
            
        }
             br1.close();
        }catch(FileNotFoundException fnfe){
            System.out.println("file no found ");
        }
    }
    
}
