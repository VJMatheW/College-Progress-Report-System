/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Report;

import com.Project.Model.Components;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vijay_ravi
 */
public class ChartBeanList {
    
     public ArrayList<ChartBean> getchartBeanList(ArrayList<String> subname,ArrayList<Float> marks) {
      ArrayList<ChartBean> chartBeanList = new ArrayList<>();

      for(int j = 0;j<6;j++){
         try{
             chartBeanList.add(produce(subname.get(j),marks.get(j)));
         }catch(Exception e){
             
         }
     }
      
      return chartBeanList;
   }

  
   private ChartBean produce(String subjectName, float marks) {
      ChartBean chartBean = new ChartBean();

      chartBean.setSubjectName(subjectName);
      chartBean.setMarks(marks);

      return chartBean;
   }
   
   public Map createParam(String dept,String deptff,String type,String sem,String section,String year,String hyear){
       
       Map p = new HashMap();
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
        p.put("hyear","RESULT ANALYSIS "+hyear);      
       
       return p;
       
   }
    
}
