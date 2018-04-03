/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spreadsheet;

/**
 *
 * @author vijay_ravi
 */
import com.Project.Report.ResultBean;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBeanList {
   public ArrayList<DataBean> getDataBeanList(ResultSet rs) throws SQLException {
       
       ResultSetMetaData rsmd = rs.getMetaData();
        int col = rsmd.getColumnCount();
        int c = 0,a = 0;int l=1;
       
      ArrayList<DataBean> d = new ArrayList<DataBean>();
      while (rs.next()){
        String sno="",regno="",name="",mark1="",mark2="",mark3="",mark4="",mark5="",mark6="",avg="",
                result="",subabsent="",subfailed="";  
            ResultBean obj = new ResultBean();
           
            try{
                int i = rs.getInt(3);
                if(rs.wasNull()){
                    mark1="AB";a++;
                }else{
                mark1=i+"";
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                mark1="";
            }
            try{
                int i = rs.getInt(4);
                if(rs.wasNull()){
                    mark2="AB";a++;
                }else{
                mark2=i+"";
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                mark2="";
            }
            try{
                int i = rs.getInt(5);
                if(rs.wasNull()){
                    mark3="AB";a++;
                }else{
                mark3=i+"";
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                mark3="";
            }
            try{
                int i = rs.getInt(6);
                if(rs.wasNull()){
                    mark4="AB";a++;
                }else{
                mark4=i+"";
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                mark4="";
            }
            try{
                int i = rs.getInt(7);
                if(rs.wasNull()){
                    mark5="AB";a++;
                }else{
                mark5=i+"";
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                mark5="";
            }
            try{
                int i = rs.getInt(8);
                if(rs.wasNull()){
                    mark6="AB";a++;
                }else{
                mark6=i+"";
                if(i<50){
                    c++;
                }
                }
            }catch(Exception e){
                mark6="";
            }             
            sno=l+"";l++;
            regno= rs.getLong(1)+"";
            name=rs.getString(2);
            avg=rs.getBigDecimal(col)+"";
            subabsent=a+"";
            subfailed=c+"";
            
            if(c==0 && a== 0){
                result="PASS";
            }else{
                result="FAIL";
            }
            
            d.add(produce(sno,regno,name,mark1,mark2,mark3,mark4,mark5,mark6,avg,result,subfailed,subabsent));
        }
      

      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
      d.add(produce("1", "412814104084","Vijay.R","89","87","98","96","90","","90.0","PASS","0","0"));
      
      return d;
   }

   /**
    * This method returns a DataBean object,
    * with name and country set in it.
    */
   private DataBean produce(String slno, String regno,String name,String mark1,String mark2,String mark3,String mark4,String mark5
   ,String mark6,String avg,String result,String ab,String failed ) {
      DataBean d = new DataBean();
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
}