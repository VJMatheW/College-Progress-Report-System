<%-- 
    Document   : EditDataUploads
    Created on : 7 Mar, 2018, 8:52:45 PM
    Author     : Vijay
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="com.Project.Model.Components"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css">
        <script src="/fileupload/Html/section.js"></script>
        <title>Edit Data Uploads</title>
        <style type="text/css">
            body{
                background-color: #ecfdeb !important;
            }
            .table-striped tr:nth-child(even) td{
                background-color: #ecddee;
            }
            .table-striped tr td,.table-striped tr th{
                border-color: black !important;
            }
        </style>
    </head>
    <body>
        <h3><b>Data Uploads</b><hr style="border-color: black;"/></h3>
        <div class="container-fluid">
            <div class="row" >
                <div class="col-xs-12 col-sm-5" >
                    <form method="GET" action="../../Login" >
                        <div class="form-group">
                            <label >Class : </label> 
                            <input class="form-control input-sm" type="text" maxlength="12" name="class" required>                                
                        </div>
                        <div class="form-group">
                            <label >Type : </label> 
                            <select class="form-control input-sm" name="type" required>
                                <option value="">Select Type</option>
                                <option value="info">INFO</option>
                                <option value="pt1">PT-1</option>
                                <option value="pt2">PT-2</option>
                                <option value="pt3">PT-3</option>
                                <option value="term1">Term-1</option>
                                <option value="term2">Term-2</option>
                                <option value="term3">Term-3</option>
                                <option value="elective">Elective</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label >Value </label> 
                            <input class="form form-control" type="text" name="value" maxlength="1" onkeyup="numberOnly(this);">
                        </div>
                        <div class="form-group">
                            <input class="btn btn-sm btn-success" type="submit" value="Update">                           
                        </div>
                    </form>                    
                </div>
            </div>
            <div class="row" >
                    <table class="table table-striped table-bordered" border="1" style="border-collapse: collapse;">
                        <tr><th>Class</th><th>INFO</th><th>PT-1</th><th>PT-2</th><th>PT-3</th><th>Term-1</th><th>Term-2</th><th>Term-3</th><th>Elective</th></tr>
                        <%
                        ResultSet rs = Components.getDataUploads();
                        String tbl = "";
                        if(rs != null){
                            while(rs.next()){
                                tbl += "<tr><td>"+rs.getString(1)+"</td><td>"+rs.getInt(2)+"</td><td>"+rs.getInt(3)+"</td><td>"+rs.getInt(4)+"</td>"
                                        + "<td>"+rs.getInt(5)+"</td><td>"+rs.getInt(6)+"</td><td>"+rs.getInt(7)+"</td><td>"+rs.getInt(8)+"</td>"
                                        + "<td>"+rs.getInt(9)+"</td></tr>";                        
                            }
                            %> 
                        
                        <%=tbl %>
                        
                        
                        <%
                        }else{
                        %>
                        Some Internal Error Occurred !!!
                        <%
}
                        %>
                    </table>
                </div>
        </div>
    </body>
</html>
