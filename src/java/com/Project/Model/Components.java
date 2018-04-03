/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Model;

import com.Project.Email.EmailBean;
import static com.Project.Model.BasicInfo.*;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vijay
 */
public class Components {
    
    public static ArrayList<String> error = new ArrayList<>();
    
    public static boolean executeQuery(ArrayList<String> query) throws ClassNotFoundException, SQLException
    {
        error.clear();
        System.out.println("Inside execute query");
       // System.out.println("Query passed to ExecuteQuery : "+query.toString());
        boolean status = false;
        Connection con = BasicInfo.con();
        con.setAutoCommit(false);
        Statement smt = con.createStatement();
        
        try{
            for (String sql : query)
            {
                System.out.println(sql);
                smt.addBatch(sql);
            }
            try{
            int[] rs = smt.executeBatch();
            //int s = smt.getUpdateCount();
            //System.out.println("EXECUTE BATCH count smt.getUpdateCount : "+s+"  QUERY SIZE : ");
                if (Arrays.toString(rs).contains("0") ){
                   status = false;
                   error.add("One of the Register Number You uploaded do not match with Register Number already in database.<br>"
                           + "Hint : Download register Number from <b>ViewData</b> -> <b>ViewMarks Tab</b>");
                   con.rollback();
                   smt.close();
                   con.close();                   
                }else{
                    status = true;
                    con.commit();
                    smt.close();
                    con.close();
                    //System.out.println("Inside Execute query = result :  "+s);
                }
            }catch(Exception e ){
                smt.close();
                con.close();
                System.out.println("Exception in Componets.execute Query "+e.getMessage());
                
                if(e.toString().startsWith("java.sql.BatchUpdateException: Duplicate entry")){
                    error.add("One or more Register Number already uploaded for same Batch"
                            + "<br> <b>(or)</b><br>Check the file for duplicate of Register Number"
                            + "<br> <b>Suggestion : </b>"+e.getMessage().replace("for key 'PRIMARY'", ""));
                }            
            status = false;
            }
        }catch(Exception e){
            smt.close();
            con.close();
        }
        return status;
    }
    
    public static boolean excecuteDDL(String query) throws ClassNotFoundException, SQLException{
        boolean r = false;
        Connection con = null;
        try{
        con = BasicInfo.con();
        Statement st = con.createStatement();
        st.execute(query);
        System.out.println("execute DDL  Status : "+r);
        con.close();
        }catch(Exception e){
            System.out.println("Error in executeDDL : "+e);
            System.out.println("Query is : "+query);
            con.close();
        }
        return r;
    }
    
    public static ResultSet executeSelectQuery(String query)throws Exception{
        
        System.out.println("inside execute select query : "+query);
        Connection con = BasicInfo.con();
        Statement smt = con.createStatement();
        ResultSet rs = null;
         try {rs = smt.executeQuery(query);
         }catch(Exception e){
            System.out.println("Exception in executeSelectQuery : "+e);
            rs.getStatement().getConnection().close();
         }
        return rs;
    }
    
    public static boolean executeUploadQuery(ArrayList<String> query) throws ClassNotFoundException, SQLException{
        Connection con =null;
        boolean status = true;
        int count = 1;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(BasicInfo.dbAddress,BasicInfo.username,BasicInfo.password);
            con.setAutoCommit(false);
            Statement s = con.createStatement();
            boolean i= true;
            
            for (String sql : query){
                i = false;
                System.out.println("status : "+i+" query : "+sql);
                i = s.execute(sql);
                count++;                
                System.out.println(count+"  status : "+i+" query : "+sql);
                if (!i){
                    status = false;
                    con.rollback();
                    con.close();
                    System.out.println("rollback executed");
                    break;
                }            
            }
            if(status == true){
                con.commit();
            }
        }catch(Exception e){
            System.out.println("Error query : "+count);
            status = false;
            System.out.println(e);
        }
        finally{
            con.close();
        }
        return status;
    }
    
    public static int executeDataManipulation(String query) throws ClassNotFoundException, SQLException{
        Connection con = BasicInfo.con();
        Statement snt = con.createStatement();
        int s=0;
        try{
        System.out.println("Query : "+query);        
        s = snt.executeUpdate(query);
        }catch(Exception e){
            snt.close();
            con.close();
            System.out.println(e);
        }
        finally{
            snt.close();
            con.close();
        }
        return s;
    }
    
    public static ArrayList fetchSemSubject(String dept,String regulation, String sem) throws ClassNotFoundException, SQLException
    {
        ArrayList<String> subjects = new ArrayList<>();
        String sql;
        if(sem.equals(""))
        {
            sql = "select * from semsubjects"+dept+regulation;            
        }else{
            sql = "select * from semsubjects"+dept+regulation+" where sem = "+sem;
        }
        Connection con = BasicInfo.con();
        try{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        ResultSetMetaData rsmt = rs.getMetaData();
        //System.out.println("After execute query");
        System.out.println("Column count returned : "+rsmt.getColumnCount());
        while (rs.next())
        {
           String s;
           for(int i=2;i<=rsmt.getColumnCount();i++)
           {
               s = rs.getString(i).trim();
              // System.out.println("iteration value :"+s);
               if (!s.equalsIgnoreCase("nil"))
               {
                   subjects.add(s);
               }
           }
        }
        System.out.println("fetchSemSubjects returned : "+subjects.toString());   
        }catch(MySQLSyntaxErrorException mse){
            String msg = mse.getMessage();
            if(msg.contains("doesn't exist")){
                subjects.add("Error : Subject Code not yet Uploaded!!  Contact <b>Admin</b>");
            }
        }catch(Exception e){
            System.out.println(e);
            subjects.add("Error : Oops Unknow error occurred !!!");
        }
        finally{
            con.close();
        }
        return subjects;
    }
    
    public static boolean deleteXLSXFile(String dept,String batch,String fileName){
        //System.out.println("inside delete : "+BasicInfo.storageLocation+dept+"\\"+batch+"\\"+fileName+".csv");
        System.out.println("inside delete : "+BasicInfo.storageLocation+dept+"\\"+batch+"\\"+fileName+".xlsx");
        //File dir = new File(BasicInfo.storageLocation+"/"+dept+"/"+batch+"/"+fileName+".csv");
        //File dir = new File(BasicInfo.storageLocation+dept+"\\"+batch+"\\"+fileName+".csv");
        boolean status = true;
        File dir = new File(BasicInfo.storageLocation+dept+"\\"+batch+"\\"+fileName+".xlsx");
        if(dir.exists()){
            status = dir.delete();
        }         
        return status;        
    }
    public static boolean deleteFile(String dept,String batch,String fileName){
        System.out.println("inside delete : "+BasicInfo.storageLocation+dept+"\\"+batch+"\\"+fileName+".csv");
        //File dir = new File(BasicInfo.storageLocation+"/"+dept+"/"+batch+"/"+fileName+".csv");
        //File dir = new File(BasicInfo.storageLocation+dept+"\\"+batch+"\\"+fileName+".csv");
        File dir = new File(BasicInfo.storageLocation+dept+"\\"+batch+"\\"+fileName+".csv");
        boolean status = dir.delete();
        return status;        
    }
    
    public static void saveXLSXFile(InputStream is, String dept,String batch, String fileName) throws IOException
    {
        String saveFileLocation = storageLocation+"\\"+dept+"\\"+batch;
        //String saveFileLocation = storageLocation+dept+"\\"+batch;
        File dir = new File(saveFileLocation);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        //System.out.println("Save File : "+saveFileLocation+"\\"+fileName+".csv");
        System.out.println("Save File : "+saveFileLocation+"\\"+fileName+".xlsx");
        //Files.copy(is ,new File(saveFileLocation+"\\"+fileName+".csv").toPath());
        Files.copy(is ,new File(saveFileLocation+"\\"+fileName+".xlsx").toPath());
        is.close();
    }
    
     public static void saveFile(InputStream is, String dept,String batch, String fileName) throws IOException
    {
        String saveFileLocation = storageLocation+"\\"+dept+"\\"+batch;
        //String saveFileLocation = storageLocation+dept+"\\"+batch;
        File dir = new File(saveFileLocation);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        //System.out.println("Save File : "+saveFileLocation+"\\"+fileName+".csv");
        System.out.println("Save File : "+saveFileLocation+"\\"+fileName+".csv");
        //Files.copy(is ,new File(saveFileLocation+"\\"+fileName+".csv").toPath());
        Files.copy(is ,new File(saveFileLocation+"\\"+fileName+".csv").toPath());
        is.close();
    }
    
    public static boolean checkFileExist(String dept,String batch,String fileName)
    {
        //System.out.println("Inside check file");
        //File file = new File(storageLocation+dept+"/"+batch+"/"+fileName+".csv");
        //File file = new File(storageLocation+dept+"\\"+batch+"\\"+fileName+".csv");
        File file = new File(storageLocation+dept+"\\"+batch+"\\"+fileName+".csv");
        System.out.println("Check File : "+storageLocation+dept+"\\"+batch+"\\"+fileName+".csv");
        System.out.println("Status fileExists :"+file.exists());
        return file.exists();       
    }
    
    public static boolean checkXLSXFileExist(String dept,String batch,String fileName)
    {
        //System.out.println("Inside check file");
        //File file = new File(storageLocation+dept+"/"+batch+"/"+fileName+".csv");
        //File file = new File(storageLocation+dept+"\\"+batch+"\\"+fileName+".csv");
        File file = new File(storageLocation+dept+"\\"+batch+"\\"+fileName+".xlsx");
        System.out.println("Check File : "+storageLocation+dept+"\\"+batch+"\\"+fileName+".xlsx");
        System.out.println("Status fileExists :"+file.exists());
        return file.exists();       
    }
    
    public static boolean checkFolderExist(String dept,String batch)
    {
        //File folder = new File(storageLocation+dept+"/"+batch);
        File folder = new File(storageLocation+dept+"\\"+batch);
        return folder.exists();
    }
    
    //  To create a String  (rollno bigint, sub1 int,sub2 int,,,,, foreign key(rollno) references 
    public static String createQuerySuffix(String dept,String regulation) throws ClassNotFoundException, SQLException
    {
        ArrayList<String> subjects = fetchSemSubject(dept, regulation, "");
        //System.out.println("no of subjects : "+subjects.size());
        //System.out.println("subjects :"+subjects);
        String queryPrefix = "( rollno bigint";
        String querySuffix=", foreign key (rollno) references ";
        String test = "";
        for(int j=0;j<subjects.size();j++)
        {
            test += ","+subjects.get(j)+" int ";
        }
        String query = queryPrefix+test+querySuffix;
        System.out.println("createQuerySuffix retuens : "+query);
        return query;
    }
    
    public static BufferedReader readFile(String dept,String batch,String fileName) throws FileNotFoundException
    {
        //BufferedReader br = new BufferedReader(new FileReader(storageLocation+dept+"/"+batch+"/"+fileName+".csv"));
        BufferedReader br = new BufferedReader(new FileReader(storageLocation+dept+"\\"+batch+"\\"+fileName+".csv"));
        return br;
    }
    public static BufferedReader readXLSXFile(String dept,String batch,String fileName) throws FileNotFoundException
    {
        //BufferedReader br = new BufferedReader(new FileReader(storageLocation+dept+"/"+batch+"/"+fileName+".csv"));
        BufferedReader br = new BufferedReader(new FileReader(storageLocation+dept+"\\"+batch+"\\"+fileName+".xlsx"));
        return br;
    }
    
    public static String fetch(String fileName) throws IOException {
        
        String line,data[]=null,fin="na" ;
        ArrayList<String> s = new ArrayList<>();
        BufferedReader br1=null;
        try{
             //br1 = new BufferedReader(new FileReader(BasicInfo.storageLocation+"res/"+fileName+".txt"));
             System.out.println(BasicInfo.storageLocation+"res\\"+fileName+".txt");
             br1 = new BufferedReader(new FileReader(BasicInfo.storageLocation+"res\\"+fileName+".txt"));
             while((line=br1.readLine())!=null){
            data = line.split(",");
            //System.out.println("data length = "+data.length);
            s.add("<option value=\""+data[0]+"\"> "+data[1]+"</option>");
            fin = s.toString().replace("[", "").replace("]", "").replace(",", "");
            
        }
             br1.close();
        }catch(FileNotFoundException fnfe){
            System.out.println("file no found ");
        }
        
        return fin;
    }
    
    public static ArrayList<String> fetchSemSubjectff(ArrayList<String> subCode,String dept, String regulation) throws Exception{
        ResultSet rs = Components.executeSelectQuery("select * from subjectsname"+dept+regulation);
        Map<String,String> wholeSub = fetchWholeSubCodeAndFF(dept, regulation);
        ArrayList<String> fullForm = new ArrayList<>();
        
        for(String a : subCode){
            fullForm.add(wholeSub.get(a));
        }        
        return fullForm;        
    }
    
//    public static ArrayList<String> fetchSemSubjectff(ArrayList<String> subjectcode) throws Exception{
//        String query;
//        ResultSet rs=null;
//        ArrayList<String> subjectff = new ArrayList<>();
//        for(String subcode : subjectcode){
//            query = "select subjectname from subjectsnamecsereg2013 where subjectcode=\""+subcode+"\" ";
//            rs = executeSelectQuery(query);
//            while(rs.next()){
//                subjectff.add(rs.getString(1));
//            }
//        }
//        rs.getStatement().getConnection().close();
//        return subjectff;
//    }
    
    public static String batchResolver(String in){
        return "20"+in.substring(4, 6);
    }
    
    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }
    
    public final static String toRoman(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }
    
    public static String ptResolver(String type){
        String temp="Unresolved";
        if(type.equals("pt1")){
            temp = "PERIODICAL TEST I ";
        }else if(type.equals("pt2")){
            temp = "PERIODICAL TEST II";
        }else if(type.equals("pt3")){
            temp = "PERIODICAL TEST III";
        }
        return temp;
    }
    
    public static ArrayList<String> fetchSubjectShortForm(ArrayList<String> subjects,String dept,String regulation) throws Exception{
        ArrayList<String> sf = new ArrayList<>();
        Map<String,String> wholeSubSF = new HashMap<>();
        String query="select subjectcode,shortform from subjectsname"+dept+regulation;
        
        ResultSet rs = Components.executeSelectQuery(query);
        while(rs.next()){
            wholeSubSF.put(rs.getString(1), rs.getString(2));
        }
        rs.getStatement().getConnection().close();
        
        for(String a : subjects){
            sf.add(wholeSubSF.get(a));
        }       
        return sf;
    }
    
    public static ArrayList<String> fetchSubjectFaculty(ArrayList<String>subjects,String dept,String regulation) throws Exception{
        ArrayList<String> fn = new ArrayList<>();
        String query="";
        for(String f : subjects){
            query = "select faculty from subjectsname"+dept+regulation+" where subjectcode=\""+f+"\"";
            System.out.println("Staff query :"+query);
            ResultSet r = Components.executeSelectQuery(query);
            r.next();
            fn.add(r.getString(1));
            r.getStatement().getConnection().close();
        }
        
        return fn;
    }
    
    public static ArrayList<String> fetchElectiveSubjectCode(String dept,String reg,String electiveNo) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(BasicInfo.storageLocation+"elective\\"+dept+reg+".csv"));
        br.readLine();
        String line = "",values[];
        ArrayList<String> electiveCode = new ArrayList<String>();
        
        while((line=br.readLine().toLowerCase())!= null){
            values = line.split(",");
            System.out.println(Arrays.toString(values)+" vaue[0]:"+values[0]+" Elective : "+electiveNo);
            if(values[0].toString().equals(electiveNo)){
                System.out.println("Value Length : "+values.length);
                for(int j=1;j<=values.length-1;j++){
                    System.out.println(values[j]);
                    electiveCode.add(values[j].trim());                                    
            }
            break;
            }
        }
        br.close();
        return electiveCode;
    }
    
    public static HashMap<String,String> fetchWholeSubCodeAndFF(String dept,String regulation) throws Exception{
        String sql = "select subjectcode, subjectname from subjectsname"+dept+regulation;
        HashMap<String,String> code = new HashMap<String,String>();
        ResultSet rs = null;
        try{
            rs = executeSelectQuery(sql);
            while(rs.next()){
                code.put(rs.getString(1),rs.getString(2));
            }        
        }catch(Exception e){
            rs.getStatement().getConnection().close();
            System.out.println("Exception in fetchWholeSubCodeAndFF");
            System.out.println("Exception : "+e);
        }
        rs.getStatement().getConnection().close();
        return code;        
    }

    public static ResultSet getMessageData(String sem,String dept,String batch,String section,String type,ArrayList<String> subjectCode) throws Exception{
        String csub="";
        for(String s :subjectCode){
                if(s.startsWith("elective")){                    
                    csub += ",c."+s+" as "+s+"c";
                }
            }
        String subject = "";
        for(String sd : subjectCode){
            subject += "";
        }
        String info = "tbl"+dept+batch+"info";
        String pt = "tbl"+dept+batch+type;
        String elective = "tbl"+dept+batch+"elective";
        String thour = "thoursem"+sem.trim();
        String ahour = "sem"+sem.trim();
        String attendance = "tbl"+dept+batch+type.replace("pt","term");
        String sql = "select a.rollno,a.name,a.f_mail,a.f_mobile,d."+thour+",d."+ahour+",b."+subjectCode.toString().replace("[","").replace("]", "").replace(", ", ",b.")+
                    csub+" from "+info+" as a inner join "+pt+" as b on a.rollno = b.rollno inner join "+elective+" as c on b.rollno = c.rollno inner join "+attendance+" as d on c.rollno = d.rollno"+
                    " where a.f_mail != \"NA\" and section="+section;
        ResultSet rs = executeSelectQuery(sql);       
        return rs;        
    }
    
    public static boolean saveObject(String dept,String batch,ArrayList<EmailBean> obj,String fileName) {
        File file = new File(BasicInfo.storageLocation+dept+"\\"+batch+"\\Email");
        ObjectOutputStream oos = null;
        boolean status = false;
        if(file.exists()){
                        
        }else{
            file.mkdirs();
        }
        try {
                oos = new ObjectOutputStream(new FileOutputStream(file+"\\"+fileName+".txt"));
                oos.writeObject(obj);
                oos.flush();    
                oos.close();
                status = true;
            }catch(Exception e){
                System.out.println(" Exception Components.saveObject : "+e);
            }
            finally{
                try {
                    oos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Components.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        return status;
    }
    
    public static ArrayList<EmailBean> fetchObject(String dept,String batch,String filename) throws IOException{
        File file = new File(BasicInfo.storageLocation+dept+"\\"+batch+"\\Email\\"+filename+".txt");
        ObjectInputStream ois = null;
        ArrayList<EmailBean> bean = null;
        try{
            ois = new ObjectInputStream(new FileInputStream(file));
            bean = (ArrayList<EmailBean>)ois.readObject();
        }catch(Exception e){
            System.out.println("Exception in fetchObject : "+e);
        }
        finally{
            ois.close();
        }
        return bean;
    }
    
    public static Map<String,String> fetchSemSubjectCodeORName(String type) throws SQLException, Exception{
        ResultSet rs = null;
        String sql = null,temp=null,dept,reg;       
        Map<String,String> content = new HashMap<>();
        try{
            switch(type){
                case "subCode":
                    sql = "show tables where Tables_in_internalmarks like 'sem%';";
                    rs = Components.executeSelectQuery(sql);
                    while(rs.next()){
                        temp = rs.getString(1);
                        dept = temp.substring(11,temp.length()-7);
                        reg = temp.substring(temp.length()-7);
                        if(content.containsKey(dept)){
                            content.put(dept,content.get(dept)+","+reg);
                        }else{
                            content.put(dept,reg);
                        }                                             
                    }
                    break;
                case "subName":
                    sql = "show tables where Tables_in_internalmarks like 'subject%';";
                    rs = Components.executeSelectQuery(sql);
                    while(rs.next()){
                        temp = rs.getString(1);
                        dept = temp.substring(12,temp.length()-7);
                        reg = temp.substring(temp.length()-7);
                        if(content.containsKey(dept)){
                            content.put(dept,content.get(dept)+","+reg);
                        }else{
                            content.put(dept,reg);
                        } 
                    }
                    break;
            }
            
            rs.getStatement().getConnection().close();
        }catch(Exception e){
            System.out.println("Exception in fetchSemSubjectCodeORName : "+e);
            rs.getStatement().getConnection().close();
        } 
        return content;
    }    
    
    public static Map<String, String> fetchDeptAndYear() throws Exception{
        System.out.println("Inside fetchDeptAndYear");
        Map<String,String> map = new HashMap<>();
        String sql = "select dept, year from tbldeptentry",dept;
        int year;
        ResultSet rs = Components.executeSelectQuery(sql);
        while(rs.next()){
            dept = rs.getString(1);
            year = rs.getInt(2);            
            if(map.containsKey(dept)){
                map.put(dept, map.get(dept)+","+year);
                System.out.println(dept +"      " +year);
            }else{
                map.put(dept,year+"");
            }
        }
        rs.getStatement().getConnection().close();
        
        return map;
    }
	
	public static ResultSet getDataUploads(){                      
        String sql = "select * from tblfileuploadsentry";
        ResultSet rs = null;
        try{
            rs = Components.executeSelectQuery(sql);            
        }catch(Exception e){
            
        }       
        return rs;
    }
}

//          ELECTIVE SUBJECT FULL FORM  ANOTHER WAY
//                    electiveSubCode = Components.fetchElectiveSubjectCode(dept, regulation, temp);
//                    String p = electiveSubCode.get(0);
//                    for(int k=1;k<electiveSubCode.size();k++){
//                        p += ","+electiveSubCode.get(k);
//                    }
//                    System.out.println("ELectiveSubCode : "+electiveSubCode.toString());                
//                    //String sl = "select subjectcode, subjectname from subjectsname"+dept+regulation+" where subjectcode in (\""+electiveSubCode.toString().trim().replace("[", "").replace("]", "").replace(",","\",\"")  +"\")";
//                    String sl = "select subjectcode, subjectname from subjectsname"+dept+regulation+" where subjectcode in (\""+p.replace(",","\",\"")+"\")";
//                    System.out.println("SUBJECT FF QUERY = "+sl);
//                    rsElectiveSubFullForm = Components.executeSelectQuery(sl);
//                    
//                    while(rsElectiveSubFullForm.next()){
//                        electiveSubjectFFMap.put(rsElectiveSubFullForm.getString(1), rsElectiveSubFullForm.getString(2));
//                    }