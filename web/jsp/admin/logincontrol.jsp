<%-- 
    Document   : logincontrol
    Created on : 26 Jun, 2017, 8:24:38 PM
    Author     : vijay_ravi
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.Project.Model.Components"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css"> 
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/general.css">
        <title>Login Control</title>
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
        <%
            String dept = Components.fetch("dept");
            ArrayList<String> d = new ArrayList<>();
            ResultSet rs = Components.executeSelectQuery("select dept,ed from tbllogin");
            String dt,ed;
            while(rs.next()){
                dt=rs.getString(1);
                if(rs.wasNull()){
                    continue;
                }else{
                    if(rs.getInt(2)==1){
                        ed = "Enabled";
                    }else{
                        ed = "Disabled";
                    }
                d.add("<tr><td>"+dt.toUpperCase()+"</td><td>"+ed+"</td></tr>");
                }
            }
            rs.getStatement().getConnection().close();
        %>
        <h2><b>Access Control</b><hr style="border-color: black;"/></h2>
        <div class="container-fluid">
            <div class="col-sm-6" >
                <form class="form-horizontal" action="../../Loginctrl" method="post">
                    <div class="form-group">
                        <label class="control-label col-sm-4">Department : </label> 
                        <div class="col-sm-8">
                            <select class="form-control input-sm" name="dept" required>
                                <option value="">Select Dept</option>
                                <%=dept %>                
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Enable / Disable : </label> 
                        <div class="col-sm-8">
                            <label><input type="radio" name="ed" value="enable" checked> Enable</label><br/>
                            <label><input type="radio" name="ed" value="disable" > Disable</label>                            
                        </div>
                    </div>      
                    <div class="form-group">
                        <div class=" col-sm-offset-4 col-sm-7">
                            <input class="btn btn-sm btn-success" type="submit" value="Enable / Disable">
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-sm-6" >
                <table class="table table-striped table-bordered" border="1">
                    <tr><th>Department</th><th>Enabled/Disabled</th></tr>
                    <%
                        for(String s : d){%>
                            <%=s %> 
                        <% }
                    %>
                </table>
            </div>
        </div>
        
        
    </body>
</html>
