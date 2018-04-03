/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vijay_ravi
 */
import java.io.File;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
public class JSON_Test {

    public static void main(String[] args) {

        JSONObject obj = new JSONObject();
        obj.put("name", "mkyong.com");
        obj.put("age", 100);

        JSONArray list = new JSONArray();
        list.add("hello");
        list.add("bitch");
        list.add("bitch");

        obj.put("messages", list);
        File f =new File("/home/vijay_ravi/Desktop/JSON_Data");
        f.mkdirs();
        try (FileWriter file = new FileWriter(f+"/test.json")) {

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(obj);
    }
}
