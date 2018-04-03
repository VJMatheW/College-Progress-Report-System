
import static com.Project.Model.BasicInfo.storageLocation;
import java.io.BufferedWriter;
import java.io.FileWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vijay_ravi
 */
public class test7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
       //boolean s = Components.executeDataManipulation("set sql_safe_updates = 0");
       //System.out.println("status : "+true);
//       
        BufferedWriter bw = new BufferedWriter(new FileWriter(storageLocation+"/regulationnew.java"));
        bw.write("reg2017,Regulation2017");
        
    
    }
    
}
