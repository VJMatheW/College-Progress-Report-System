
import com.Project.Model.Components;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vijay_ravi
 */
public class test2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        // TODO code application logic here
        
        String info[] = {"registration no"};
        
        if (info[0].equals("registration no") || info[0].equals("Registration No"))
        {
            System.out.println("inside");
        }else {
            System.out.println("outside");
        }
       // ArrayList a = QueryGenerator.buildQuery("cse", "2014", "reg2013", "", "info", "pa");
       // System.out.println(a.toString());
        
        BufferedReader br = Components.readFile("cse", "2014", "hello");
            int i=1;
            String line1 ;
        while ((line1= br.readLine())!= null){
            
            System.out.println("Readind line "+i+" Content :"+line1);
            if(!line1.startsWith(",,,"))
            {
                String line[] = line1.split(",");
                if(line != null){
                if (line[0].startsWith("4128")){
                    System.out.println("Query forms");
                    }else{
                    System.out.println("Start with 4128 not satisfied");
                }
                }else{
                    System.out.println("line != null");
                } 
            }else{
                System.out.println("Starts with : ,,,,,,,");
            }
            i++;
        }
    }}


//                      if(count != 0)
//                        {
//                            String concat="";
//                            String info[] = line.split(",");
//                            int c = 0;
//                            for(int i=0;i<info.length;i++)
//                            {                                
//                                if(c == 0)
//                                {
//                                    concat += info[i]; c++;
//                                }else{concat += ",\""+info[i]+"\"";}
//                            }
//                            query.add(keywords+concat+")");
//                          
//                        }count++;