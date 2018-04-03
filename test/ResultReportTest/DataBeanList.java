/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResultReportTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author vijay_ravi
 */
public class DataBeanList {
    
    public static ArrayList<DataBean> createDataSource() throws SQLException{
        
        
        ArrayList<DataBean> d = new ArrayList<>();
        String name,regNo;
        int sl = 1;
                    
            DataBean obj = new DataBean();
            obj.setSlno("1");
            obj.setRegno("412814104084");
            obj.setName("Pasupathy.CH");
            sl++;
        
        return d;
    }
}
