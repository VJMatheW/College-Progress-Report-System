/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChartTest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vijay_ravi
 */
public class DataBeanList {
    
  public ArrayList<DataBean> getDataBeanList() {
      ArrayList<DataBean> dataBeanList = new ArrayList<DataBean>();

      dataBeanList.add(produce("English", (float) 87.9));
      dataBeanList.add(produce("SocialStudies", 68));
      dataBeanList.add(produce("Maths", 38));
      dataBeanList.add(produce("Hindi", 88));
      dataBeanList.add(produce("Scince", 78));
      
      return dataBeanList;
   }

   /*
    * This method returns a DataBean object, with subjectName ,
    * and marks set in it.
    */
   private DataBean produce(String subjectName, float marks) {
      DataBean dataBean = new DataBean();

      dataBean.setSubjectName(subjectName);
      dataBean.setMarks(marks);

      return dataBean;
   }
    
}
