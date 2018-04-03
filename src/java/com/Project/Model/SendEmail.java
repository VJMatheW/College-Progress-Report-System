
package com.Project.Model;

import com.Project.Email.EmailBean;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLConnection;
import java.util.Map;



/**
 *
 * @author Vijay
 */
public class SendEmail {
    
    
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
   // private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    public static int progress = 0;
    public static int total = 1;
    
    public static String info = "";
    Session session = null;
    
    // for List to display at the Successfinal page
    public static ArrayList<ArrayList<String>> list = new ArrayList<>();
    
    public SendEmail(String username,String password){
        System.out.println("inside constructor");
        boolean debug = true;
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        //props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.smtp.socketFactory.fallback", "false");
        
        System.out.println("Before Session");
        
        session = Session.getInstance(props,null);       
        info += "Getting Session<br>";
//                new javax.mail.Authenticator( {
//                    @Override
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(username, password);
//                    }
//        });
//        System.out.println("after session ");
//        session.setDebug(false);
    }
    
    public void sendMail(ArrayList<EmailBean> bean,String username,String password) throws MessagingException{
        Transport t = null;
        try{
            // clear the list which may contain already view success email page content
            //list.clear();
            
            progress = 0;
            total = bean.size();
//            System.out.println("t = session ");
            t = session.getTransport("smtp");
//            System.out.println("after t=session");
            
            t.connect(username,password);
            if(t.isConnected()){
                info += "Authenticated UserName and Password Correct<br>";
            }else{
                info += "Not-Authenticated UserName or Password In-Correct<br>";
            }            
//            System.out.println("ater t.connect");
            int p = 0; 
            for(EmailBean obj : bean){
                System.out.println("Value of Iteration "+(++p)+" Time "+new Date());
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(username));
                
//                InternetAddress[] to = new InternetAddress[1];
//                to[0] = new InternetAddress(obj.getTo());
                
                // adding from to and timestamp to view in success email page
//                ArrayList<String> temp = new ArrayList<>();
//                temp.add(obj.getFrom());
//                temp.add(obj.getTo());
//                temp.add(new Date()+"");
//                list.add(temp);
                
                msg.addRecipient(Message.RecipientType.TO,new InternetAddress(obj.getTo()));
                msg.setSubject(obj.getSubject());
                msg.setContent(obj.getMessage(),"text/html");
                msg.saveChanges();
                t.sendMessage(msg,msg.getAllRecipients());
               // Transport.send(msg);
                ++progress;
            }
            info += "Completing the Task <br>";
        }catch(Exception e){
            System.out.println("Error Occured in sendMail method Exception : "+e);
        }     
        finally{
            t.close();
        }
    }
    
    public void sendMessage(String userName,String password,String message,String msisdn){
        
        try {
            // Construct data
            String data = "";
            /*
             * Note the suggested encoding for certain parameters, notably
             * the username, password and especially the message.  ISO-8859-1
             * is essentially the character set that we use for message bodies,
             * with a few exceptions for e.g. Greek characters.  For a full list,
             * see:  http://developer.bulksms.com/eapi/submission/character-encoding/
             */
            data += "username=" + URLEncoder.encode(userName, "ISO-8859-1");
            data += "&password=" + URLEncoder.encode(password, "ISO-8859-1");
            data += "&message=" + URLEncoder.encode(message, "ISO-8859-1");
            data += "&want_report=1";
            data += "&msisdn="+msisdn;

            // Send data
            // Please see the FAQ regarding HTTPS (port 443) and HTTP (port 80/5567)
            URL url = new URL("https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                // Print the response output...
                System.out.println(line);
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            System.out.println("Error in send Message : "+e);
            e.printStackTrace();
        }

    }
    
    
}



//Transport t = session.getTransport();
//t.connect();
//try {
//  for(Message m : messages) {
//    m.saveChanges();
//    t.sendMessage(m, m.getAllRecipients());
//  }
//} finally {
//  t.close();
//}