<%-- 
    Document   : test
    Created on : 5 Dec, 2017, 9:42:07 PM
    Author     : Vijay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
        String message = "<div style=\"\" >"
                        + "<div style=\"text-align:center;\" >"
                        + "<h3><b>VALLIAMMAI ENGINEERING COLLEGE</b></h3>"
                        + "(  A member of SRM group  )"
                        + "</div>"
                        + "Name : <b>Vijay</b><br/>"
                        + "RegisterNo : <b>141044</b><br/>"
                        + "Semester : 1<br/>"
                        + "<h4 style=\"text-align:center;\"><b>Periodical Test 1</b></h4><br/>"
                        + "<table style=\" border-collapse: collapse;width:100%;\" border=\"1\" >"
                        + "<tr><th style=\"padding:5px;text-align:center;\" >Subject Code</th><th style=\"padding:5px;text-align:center;\" >Subject Name</th><th style=\"padding:5px;text-align:center;\" >Mark</th><th style=\"padding:5px;text-align:center;\" >Result</th></tr>";
                message += "</table>"
                        + "<h4>Attendance</h4>"
                        + "<table style=\" border-collapse: collapse;width:100%;\" border=\"1\" >"
                        + "<tr><th style=\"padding:5px;text-align:center;\" colspan=\"2\" >Attendance Percentage as on</th><td style=\"padding:5px;text-align:center;\" colspan=\"3\" >05/09/1996</td></tr>"
                        + "<tr><th rowspan=\"2\" style=\"padding:5px;text-align:center;\" >Attendance</th><th style=\"padding:5px;text-align:center;\" >Working Hours</th><th style=\"padding:5px;text-align:center;\" >Present</th><th style=\"padding:5px;text-align:center;\" >Absent</th><th style=\"padding:5px;text-align:center;\" >Percentage</th></tr>"
                        + "<tr><th style=\"padding:5px;text-align:center;\" >20</th><th style=\"padding:5px;text-align:center;\" >10</th><th style=\"padding:5px;text-align:center;\" >10</th><th style=\"padding:5px;text-align:center;\" >50%</th></tr>"
                        + "</table>"
                        + "<h4><b>Note : </b>This is an auto generated Email.For correction Contact Class-Coordinator</h4>"
                        + "<b>Do not reply.</b></div>";
        
        %>
        <%= message%>
    </body>
    
</html>
