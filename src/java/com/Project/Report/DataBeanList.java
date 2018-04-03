/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Report;

import com.Project.Model.Components;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vijay_ravi
 */
public class DataBeanList {
    
    public static ArrayList<DataBean> createDataSource(ResultSet rs,ResultSet rsa,ArrayList<String> subCode,HashMap<String,String> subCodeFF, HashMap<String,HashMap<String,String>> electiveSubCode) throws SQLException{
        
        ArrayList<DataBean> data = new ArrayList<>();
        
        try{
        System.out.println("inside createdataSource ");
        while(rs.next() && rsa.next()){
            
            System.out.println("Inside while ");
            HashMap<String,String> innerMap ;
            String temp,code ;
            int c=0;
            DataBean obj = new DataBean();
             
            try{
                obj.setRollno(rs.getString(1));
            }catch(Exception e){
                obj.setRollno("");
            }
            try{
                obj.setStd_name(rs.getString(2));
            }catch(Exception e){
                obj.setStd_name("");
            }
            try{
                int i = rs.getInt(3);
                if(rs.wasNull()){
                    obj.setMark1("AB");c++;
                }else{
                obj.setMark1(i+"");
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                obj.setMark1("");
            }
            try{
                int i = rs.getInt(4);
                if(rs.wasNull()){
                    obj.setMark2("AB");c++;
                }else{
                obj.setMark2(i+"");
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                obj.setMark2("");
            }
            try{
                int i = rs.getInt(5);
                if(rs.wasNull()){
                    obj.setMark3("AB");c++;
                }else{
                obj.setMark3(i+"");
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                obj.setMark3("");
            }
            try{
                int i = rs.getInt(6);
                if(rs.wasNull()){
                    obj.setMark4("AB");c++;
                }else{
                obj.setMark4(i+"");
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                obj.setMark4("");
            }
            try{
                int i = rs.getInt(7);
                if(rs.wasNull()){
                    obj.setMark5("AB");c++;
                }else{
                obj.setMark5(i+"");
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                obj.setMark5("");
            }
            try{
                int i = rs.getInt(8);
                if(rs.wasNull()){
                    obj.setMark6("AB");c++;
                }else{
                obj.setMark6(i+"");
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                obj.setMark6("");
            }
            
             // for first subject code and fullform 
            try{
                code = subCode.get(0).trim();
                if(code.startsWith("elective")){
                    innerMap = electiveSubCode.get(code);
                    temp = innerMap.get(rs.getString(1));
                    obj.setS1(temp.toUpperCase());
                    obj.setSf1(subCodeFF.get(temp).toUpperCase());
                }else{
                    obj.setS1(code.toUpperCase());
                    obj.setSf1(subCodeFF.get(code).toUpperCase());
                }
            }catch(Exception e){
                obj.setS1("");
                obj.setSf1("");
            }
            
            //setting second sub code and subFullForm
            try{
                code = subCode.get(1).trim();
                if(code.startsWith("elective")){
                    innerMap = electiveSubCode.get(code);
                    temp = innerMap.get(rs.getString(1));
                    obj.setS2(temp.toUpperCase());
                    obj.setSf2(subCodeFF.get(temp).toUpperCase());
                }else{
                    obj.setS2(code.toUpperCase());
                    obj.setSf2(subCodeFF.get(code).toUpperCase());
                }
            }catch(Exception e){
                obj.setS2("");
                obj.setSf2("");
            }
            
            //setting third sub code and subFullForm
            try{
                code = subCode.get(2).trim();
                if(code.startsWith("elective")){
                    innerMap = electiveSubCode.get(code);
                    temp = innerMap.get(rs.getString(1));
                    obj.setS3(temp.toUpperCase());
                    obj.setSf3(subCodeFF.get(temp).toUpperCase());
                }else{
                    obj.setS3(code.toUpperCase());
                    obj.setSf3(subCodeFF.get(code).toUpperCase());
                }
            }catch(Exception e){
                obj.setS3("");
                obj.setSf3("");
            }
            
            //setting fourth sub code and subFullForm
            try{
                code = subCode.get(3).trim();
                if(code.startsWith("elective")){
                    innerMap = electiveSubCode.get(code);
                    temp = innerMap.get(rs.getString(1));
                    obj.setS4(temp.toUpperCase());
                    obj.setSf4(subCodeFF.get(temp).toUpperCase());
                }else{
                    obj.setS4(code.toUpperCase());
                    obj.setSf4(subCodeFF.get(code).toUpperCase());
                }
            }catch(Exception e){
                obj.setS4("");
                obj.setSf4("");
            }
            
            //setting fifth sub code and subFullForm
            try{
                code = subCode.get(4).trim();
                if(code.startsWith("elective")){
                    innerMap = electiveSubCode.get(code);
                    temp = innerMap.get(rs.getString(1));
                    obj.setS5(temp.toUpperCase());
                    obj.setSf5(subCodeFF.get(temp).toUpperCase());
                }else{
                    obj.setS5(code.toUpperCase());
                    obj.setSf5(subCodeFF.get(code).toUpperCase());
                }
            }catch(Exception e){
                obj.setS5("");
                obj.setSf5("");
            }
            
            //setting sixth sub code and subFullForm
            try{
                code = subCode.get(5).trim();
                if(code.startsWith("elective")){
                    innerMap = electiveSubCode.get(code);
                    temp = innerMap.get(rs.getString(1));
                    obj.setS6(temp.toUpperCase());
                    obj.setSf6(subCodeFF.get(temp).toUpperCase());
                }else{
                    obj.setS6(code.toUpperCase());
                    obj.setSf6(subCodeFF.get(code).toUpperCase());
                }
            }catch(Exception e){
                obj.setS6("");
                obj.setSf6("");
            }
            
                 
            
            obj.setFname(rsa.getString(1));
            obj.setAddr1(rsa.getString(2));
            obj.setAddr2(rsa.getString(3));
            obj.setAddr3(rsa.getString(4));
            obj.setAttd_wh(rsa.getInt(5)+"");
            obj.setAttd_ab(rsa.getLong(6)+"");
            obj.setAttd_p(rsa.getInt(7)+"");
            obj.setAttd_percentage(rsa.getBigDecimal(8)+"");
            obj.setF_mobile(rsa.getString(9));
            
            obj.setArrear_no(c+"");
            
            data.add(obj);
                
            }
            rs.getStatement().getConnection().close();
            rsa.getStatement().getConnection().close();
        }catch(Exception e){
            rs.getStatement().getConnection().close();
            rsa.getStatement().getConnection().close();
        }
        return data;
        }
    
    public static Map setParameter(ArrayList<String> subjects,ArrayList<String> subjectff,String deptff,String dept,String batch,String sem,String section,String r_date,String type,String month,String year,String adate){
        
        System.out.println("inside set parameter");
        int b = Integer.parseInt(batch);
        String bat = batch+"-"+(b+4);
        String course = "BE("+dept.toUpperCase()+")";
        String sem_sec = (Components.toRoman(Integer.parseInt(sem))+"/"+dept+"-"+section).toUpperCase();
        String head=type;
        if(type.equals("pt1")){
            System.out.println("Inside pt1");
            head = "PERIODICAL TEST-I MARK "+month.toUpperCase()+" - "+year.toUpperCase();
        }else if(type.equals("pt2")){
            head = "PERIODICAL TEST-II MARK "+month.toUpperCase()+" - "+year.toUpperCase();
        }else if(type.equals("pt1")){
            head = "PERIODICAL TEST-III MARK "+month.toUpperCase()+" - "+year.toUpperCase();
        }
        
        Map parameters = new HashMap();
        
        // for HoD / (FYCC (or) DEPT )
        if(Integer.parseInt(sem) < 3){
            parameters.put("deptfycc","FYCC");
        }else{
            parameters.put("deptfycc",dept.toUpperCase());
        }
        
        parameters.put("dept",dept.toUpperCase());
        parameters.put("deptnameff",deptff);
        parameters.put("academicyear",bat);
        parameters.put("course",course);
        parameters.put("sem_section",sem_sec);
        parameters.put("date",r_date);
        parameters.put("attd_date",adate);
        parameters.put("head",head);
        
        for(int j = 0;j<6;j++){
            try{
                parameters.put("subname"+(j+1),subjectff.get(j).toUpperCase());
            }catch(Exception e){
                parameters.put("subname"+(j+1),"");
            }
            try{
                parameters.put("subcode"+(j+1),subjects.get(j).toUpperCase());
            }catch(Exception e){
                parameters.put("subcode"+(j+1),"");
            }
        }        
        return parameters;        
    }
        
        
    }
