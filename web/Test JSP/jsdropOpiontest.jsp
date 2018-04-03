<%-- 
    Document   : jsdropOpiontest
    Created on : 19 Jun, 2017, 9:02:19 PM
    Author     : vijay_ravi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="/fileupload/Html/test.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        
        Dept : <input type="text" id="dept" value="cse">
        Batch : <input type="text" id="batch" name="batch" minlength="4" maxlength="4"required onblur="process()" onkeyup="numberOnly(this)" placeholder="Year"><br/>
        <br/>
        Section : <select id="section" name="sec" required>
                <option value="">Select Section</option>
        </select><br/><br/>
            <span id ="msg"></span>
    </body>
</html>
