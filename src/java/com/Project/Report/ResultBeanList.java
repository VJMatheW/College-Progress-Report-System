/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Report;

import com.Project.Model.Components;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.catalina.tribes.util.Arrays;

/**
 *
 * @author vijay_ravi
 */
public class ResultBeanList {
    
    //for Rank List 
    float[] rankPercent = {0,0,0};
    String[] rankStudName = {"","",""};
    
    //for summary
    private int p1=0,p2=0,p3=0,p4=0,p5=0,p6=0,f1=0,f2=0,f3=0,f4=0,f5=0,f6=0,total;
    private int a1=0,a2=0,a3=0,a4=0,a5=0,a6=0,tpass=0,tfail=0;
    private int pa1,pa2,pa3,pa4,pa5,pa6;    
    
    public ArrayList<ResultBean> createDataSource(ResultSet rs) throws SQLException{
        
        total = rs.last()?rs.getRow():0;
        rs.beforeFirst();
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int l=1;
        ArrayList<ResultBean> d = new ArrayList<>();
        while (rs.next()){
        int c = 0,a = 0;
        String sno="",regno="",name="",mark1="",mark2="",mark3="",mark4="",mark5="",mark6="",avg="",
                result="",subabsent="",subfailed="";  
            ResultBean obj = new ResultBean();
           
            try{
                int i = rs.getInt(4);
                if(rs.wasNull()){
                    mark1="AB";a++;a1++;
                }else{
                mark1=i+"";
                p1++;
                if(i<50){
                    f1++;
                    c++;
                }
                }
            }catch(Exception e){
                mark1="";
            }
            try{
                int i = rs.getInt(5);
                if(rs.wasNull()){
                    mark2="AB";a++;a2++;
                }else{
                mark2=i+"";
                p2++;
                if(i<50){
                    f2++;
                    c++;
                }
                }
            }catch(Exception e){
                mark2="";
            }
            try{
                int i = rs.getInt(6);
                if(rs.wasNull()){
                    mark3="AB";a++;a3++;
                }else{
                mark3=i+"";
                p3++;
                if(i<50){
                    f3++;
                    c++;
                }
                }
            }catch(Exception e){
                mark3="";
            }
            try{
                int i = rs.getInt(7);
                if(rs.wasNull()){
                    mark4="AB";a++;a4++;
                }else{
                mark4=i+"";
                p4++;
                if(i<50){
                    f4++;
                    c++;
                }
                }
            }catch(Exception e){
                mark4="";
            }
            try{
                int i = rs.getInt(8);
                if(rs.wasNull()){
                    mark5="AB";a++;a5++;
                }else{
                mark5=i+"";
                p5++;
                if(i<50){
                    f5++;
                    c++;
                }
                }
            }catch(Exception e){
                mark5="";
            }
            try{
                int i = rs.getInt(9);
                if(rs.wasNull()){
                    mark6="AB";a++;a6++;
                }else{
                mark6=i+"";
                p6++;
                if(i<50){
                    f6++;
                    c++;
                }
                }
            }catch(Exception e){
                mark6="";
            }             
            sno=l+"";l++;
            regno= rs.getLong(1)+"";
            name=rs.getString(2);            
            avg=rs.getBigDecimal(3)+"";
            if(rs.wasNull()){
                avg ="0.0";
            }
            subabsent=a+"";
            subfailed=c+"";
            
            if(c==0 && a== 0){
                result="PASS";tpass++;    
                Float tempPercent = Float.parseFloat(rs.getBigDecimal(3)+"");               
                
                // if current percent is greater than rank 1 
                if( tempPercent > rankPercent[0]){                    
                    // 2nd rank as third
                    rankPercent[2] = rankPercent[1];
                    rankStudName[2] = rankStudName[1];
                    
                    // 1st rank as second
                    rankPercent[1] = rankPercent[0];
                    rankStudName[1] = rankStudName[0];
                    
                    // new first rank is inserted
                    rankPercent[0] = tempPercent;
                    rankStudName[0] = name;
                }else if(tempPercent == rankPercent[0] || tempPercent > rankPercent[1] ) 
                /*  if current percent is equal to rank 1 || current percent is greater than rank 2 */{
                    // 2nd rank as third
                    rankPercent[2] = rankPercent[1];
                    rankStudName[2] = rankStudName[1];
                    
                    // new second rank inserted
                    rankPercent[1] = tempPercent;
                    rankStudName[1] = name;
                }else if(tempPercent == rankPercent[1])/* if current percent is equal to rank 2  */{
                    // new third rank inserted 
                    rankPercent[2] = tempPercent;                                       
                    rankStudName[2] = name;
                }
                
            }else{
                result="FAIL";
            }
            
            d.add(produce(sno,regno,name,mark1,mark2,mark3,mark4,mark5,mark6,avg,result,subabsent,subfailed));
        }
        System.out.println("Present : "+p1+","+p2+","+p3+","+p4+","+p5+","+p6);
        System.out.println("Fail : "+f1+","+f2+","+f3+","+f4+","+f5+","+f6);
        System.out.println("Absent : "+a1+","+a2+","+a3+","+a4+","+a5+","+a6);
        System.out.println("rankStudName : "+Arrays.toString(rankStudName));
        System.out.println("rankStudPercentage : "+rankPercent[0]+" "+rankPercent[1]+"  "+"  "+rankPercent[2]);
//      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
//      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
//      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
//      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
//      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
//      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
//      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
//      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
//      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
//      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
        return d;
    }
    
    private ResultBean produce(String slno, String regno,String name,String mark1,String mark2,String mark3,String mark4,String mark5
   ,String mark6,String avg,String result,String ab,String failed ) {
      ResultBean d = new ResultBean();
      d.setName(name);
      d.setSno(slno);
      d.setRegno(regno);
      d.setMark1(mark1);
      d.setMark2(mark2);
      d.setMark3(mark3);
      d.setMark4(mark4);
      d.setMark5(mark5);
      d.setMark6(mark6);
      d.setAvg(avg);
      d.setResult(result);
      d.setSubabsent(ab);
      d.setSubfailed(failed);     
      return d;
   }
    
    public Map setParameter(ArrayList<String> subjects, ArrayList<String> subsf,ArrayList<String> subff,ArrayList<String> subf,String dept,String deptff,String type,String sem,String section,String year,String hyear){
        Map p = new HashMap();
        String temp="",temp1="";
        float t,t1;
        for(int j = 0;j<6;j++){
            try{
                temp = "scode"+(j+1);
                temp1 = subsf.get(j).toUpperCase();
                System.out.println("Parameters : "+temp+" , "+temp1);
                p.put(temp,temp1);
                p.put("subff"+(j+1),subff.get(j).toUpperCase());
                p.put("code"+(j+1),subjects.get(j).toUpperCase());
                System.out.println("f"+(j+1)+"  "+subf.get(j).toUpperCase());
                p.put("fac"+(j+1),subf.get(j).toUpperCase());
                p.put("pp"+(j+1),"test");
                temp="";temp1="";
            }catch(Exception e){
                System.out.println("Parameters : "+temp+" , "+temp1);
                p.put(temp,temp1);
                p.put("subff"+(j+1),"NA");
                p.put("code"+(j+1),"NA");
                p.put("fac"+(j+1),"NA");
                temp="";temp1="";
            }
        }
        //Parameters for heading
        System.out.println(sem);
        if(Integer.parseInt(sem) < 3){
            p.put("dept","FYCC");
        }else{
            p.put("dept",dept.toUpperCase());
        }        
        p.put("deptff","DEPARTMENT OF "+deptff);
        p.put("pt",Components.ptResolver(type));
        p.put("sem",Components.toRoman(Integer.parseInt(sem)));
        p.put("section",section);
        p.put("year",(year+"").toUpperCase());
        p.put("hyear",hyear);
        p.put("r",total+"");
        p.put("tpass",tpass+"");
        p.put("tfail",(total-tpass)+"");
        p.put("tpercentage",convert(tpass,total)+"");
        
        
        
        //No. of student PRESENT parameter
        p.put("p1",p1+"");
        p.put("p2",p2+"");
        p.put("p3",p3+"");
        p.put("p4",p4+"");
        p.put("p5",p5+"");
        p.put("p6",p6+"");
        
        //No. of students FAILED parameter
        p.put("f1",f1+"");
        p.put("f2",f2+"");
        p.put("f3",f3+"");
        p.put("f4",f4+"");
        p.put("f5",f5+"");
        p.put("f6",f6+"");
        
        //No. of students ABSENT parameter
        p.put("a1",a1+"");
        p.put("a2",a2+"");
        p.put("a3",a3+"");
        p.put("a4",a4+"");
        p.put("a5",a5+"");
        p.put("a6",a6+"");
        
        pa1=p1-f1; pa2=p2-f2; pa3=p3-f3; pa4=p4-f4; pa5=p5-f5; pa6=p6-f6;
        //No. of students PASSED parameter
        p.put("pa1",pa1+"");
        p.put("pa2",pa2+"");
        p.put("pa3",pa3+"");
        p.put("pa4",pa4+"");
        p.put("pa5",pa5+"");
        p.put("pa6",pa6+"");
        
        //Subject Pass Percentage
        p.put("av1",convert(pa1,p1)+"");
        p.put("av2",convert(pa2,p2)+"");
        p.put("av3",convert(pa3,p3)+"");
        p.put("av4",convert(pa4,p4)+"");
        p.put("av5",convert(pa5,p5)+"");
        p.put("av6",convert(pa6,p6)+"");
        
        //Pass % for subreport
        //p.put("pp1",(pa1/(total-a1)));
        p.put("pp1",convert(pa1,(total-a1))+"");
        p.put("pp2",convert(pa2,(total-a2))+"");
        p.put("pp3",convert(pa3,(total-a3))+"");
        p.put("pp4",convert(pa4,(total-a4))+"");
        p.put("pp5",convert(pa5,(total-a5))+"");
        p.put("pp6",convert(pa6,(total-a6))+"");
        
        //rankList StudentName params
        p.put("rankName1",rankStudName[0]+"");
        p.put("rankName2",rankStudName[1]+"");
        p.put("rankName3",rankStudName[2]+"");
        
        //rankList Percentage params
        p.put("rankPercentage1",rankPercent[0]+"");
        p.put("rankPercentage2",rankPercent[1]+"");
        p.put("rankPercentage3",rankPercent[2]+"");
        
        return p;
    }
    
    public ArrayList<Float> chartValue(){
        ArrayList<Float> data = new ArrayList<>();
        data.add((float)convert(pa1,(total-a1)));
        data.add((float)convert(pa2,(total-a2)));
        data.add((float)convert(pa3,(total-a3)));
        data.add((float)convert(pa4,(total-a4)));
        data.add((float)convert(pa5,(total-a5)));
        data.add((float)convert(pa6,(total-a6)));        
        
        return data;
    }
    
    static double convert(float pa,float p){
       return Math.round(((pa/p)*100)*10.0)/10.0 ;
   }
    
}
