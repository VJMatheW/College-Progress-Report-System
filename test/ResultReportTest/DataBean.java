/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResultReportTest;

/**
 *
 * @author vijay_ravi
 */
public class DataBean {
    
    String slno,name,regno;

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        System.out.println("Setting sl "+slno);
        this.slno = slno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("Setting name "+name);
        this.name = name;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        System.out.println("Setting regNo "+regno);
        this.regno = regno;
    }
    
}
