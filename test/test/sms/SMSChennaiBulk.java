/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.sms;

/**
 *
 * @author Vijay
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;
public class SMSChennaiBulk {
    public static String retval = "";
    public static String SMSSender(String APIKey, String number, String text, String senderid,String channel ,String flashsms,String route){
        String rsp = "";
        try {
            
            // http://online.chennaisms.com/api/mt/SendSMS?user=demo2020&password=india147&senderid=SRMVEC&channel=Trans&DCS=0&flashsms=0&number=919042307071&text=HELLO%20WORLD&route=28
            // Construct The Post Data
            String data = URLEncoder.encode("APIKey", "UTF-8") + "=" + URLEncoder.encode(APIKey, "UTF-8");
            data += "&" + URLEncoder.encode("senderid", "UTF-8") + "=" + URLEncoder.encode(senderid, "UTF-8");
            data += "&" + URLEncoder.encode("channel", "UTF-8") + "=" + URLEncoder.encode(channel, "UTF-8");
            data += "&" + URLEncoder.encode("DCS", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
            data += "&" + URLEncoder.encode("flashsms", "UTF-8") + "=" + URLEncoder.encode(flashsms, "UTF-8");
            data += "&" + URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8");
            data += "&" + URLEncoder.encode("text", "UTF-8") + "=" + URLEncoder.encode(text, "UTF-8");
            data += "&" + URLEncoder.encode("route", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8");
            String url1="http://smppsmshub.in/api/mt/SendSMS";
            url1+="?"+data;
            URL url=new URL(url1);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb = sb.append(line);
            }
            rsp = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rsp;
    }
    public static void main(String[] args) {

    String response = SMSSender("Enter your API key", "Enter your number", "Enter test message", "Enter SenderID"," Enter Channel" ,"Flash", "Route");
    System.out.println("Response"+response);
    }
}