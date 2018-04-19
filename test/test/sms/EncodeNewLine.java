/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author Vijay
 */
public class EncodeNewLine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String content = "PT-3 MARKS\n\nVIJAY\n412815104502\n\nCS6601-89-PASS\nIT6501-96-PASS\ncs6603-AB-ABSENT\n\nATTENDANCE : 85%";
        String content2 = "PT-1 MARKS\n\nVIJAY.R\n412817104044\n\nHS8151-50-PASS\nMA8151-61-PASS\nPH8151-72-PASS\nCY8151-83-PASS\nGE8151-94-PASS\nGE8152-55-PASS\n\nAttendance : 40.0";
        System.out.println(content2);
        String data = "";
        data += "&" + URLEncoder.encode("text", "UTF-8") + "=" + URLEncoder.encode(content2, "UTF-8");
        System.out.println("URLEncoded Text : "+data);
        String type = "pt1";
        System.out.println("Marks : "+type.substring(0, 2).toUpperCase()+"-"+type.charAt(2)+" MARKS");
    }
    
}
