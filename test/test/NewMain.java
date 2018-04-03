/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.Project.Model.Admin;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 *
 * @author Vijay
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        byte[] decoded = Base64.getDecoder().decode("Y3NlLDIwMTQscHQxLDI=");
        System.out.println(new String(decoded, StandardCharsets.UTF_8));
        
        String s = "elective4";
        System.out.println(s.charAt(8));
    }
    
}
