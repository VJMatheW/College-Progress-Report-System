/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.Project.Email.EmailBean;
import com.Project.Model.BasicInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;


/**
 *
 * @author Vijay
 */
public class hashtest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        try {
            File f = new File(BasicInfo.storageLocation+"cse\\2014\\Email\\emailtest.txt");
                      
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            
//            EmailBean b = new EmailBean();
//            b.setFrom("valliammmai ");
//            b.setTo("mail2vjamthew");
//            b.setSubject("ptmarks");
//            b.setMessage("hello world java");
//            
//            ArrayList<EmailBean> bean = new ArrayList<EmailBean>();
//            oos.writeObject(bean);
//            oos.flush();
//            oos.close();

              ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
              ArrayList<EmailBean> test = (ArrayList<EmailBean>)ois.readObject();
              System.out.println(test.size());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }
    
}
