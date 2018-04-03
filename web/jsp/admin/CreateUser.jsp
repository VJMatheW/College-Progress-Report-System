<%-- 
    Document   : CreateUser
    Created on : 9 Jul, 2017, 2:40:22 PM
    Author     : vijay_ravi
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="com.Project.Model.Components"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css"> 
        <title>Create User</title>
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
            String query = "select * from tbllogin";
            ResultSet rs = Components.executeSelectQuery(query);
        %>
        <div class="container-fluid">
            <div class="col-sm-4 " >
                <h3><b>Create User</b><hr style="border-color: black;"/></h3>
                <form action="../../User" method="post">
                    <input type="hidden" name="type" value="create">
                    <div class="form-group">
                        <label >Department : </label> 
                        <select class="form-control input-sm" name="dept" required>
                            <option value="">Select Dept</option>
                            <%=dept %>                
                        </select>
                    </div>
                    <div class="form-group">
                        <label >Login-ID : </label> 
                        <input class="form-control input-sm" type="text" name="id" required="true">
                    </div>
                    <div class="form-group">
                        <label>Password : </label> 
                        <input class="form-control input-sm" type="text" name="password" required="true">
                    </div>
                    <div class="form-group">
                        <label>Dept Full-Form : </label> 
                        <input class="form-control input-sm" type="text" name="deptff" placeholder="eg. Civil Engineering" required="true">
                    </div>
                    <div class="form-group">
                        <label>Enable / Disable : </label>
                        <select class="form-control" name="ed" title="Access Control Enabled / Disabled" >
                            <option value="1">Enabled</option>
                            <option value="0">Disabled</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input class="btn btn-sm btn-success" type="submit" value="Create">
                    </div>
                </form>
            </div>
            <div class="col-sm-8">
                <div class="row" style="margin-bottom: 10px;">
                    <h3><b>Delete User</b><hr style="border-color: black;"/></h3>
                    <div class="col-sm-6">                        
                        <form action="../../User" method="post">
                            <input type="hidden" name="type" value="delete">  
                            <div class="form-group" >
                                <label>Login-ID : </label>                             
                                <input class="form-control input-sm" style="margin-bottom: 10px;" type="text" name="id" required="true">                                                       
                            </div>
                            <div class="form-group">
                                <input class="btn btn-sm btn-success" type="submit" value="Delete">                           
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row" >
                    <table class="table table-striped table-bordered" border="1" style="border-collapse: collapse;">
                        <tr><th>ID</th><th>Password</th><th>Dept</th><th>Dept FF</th><th>Enable / Disable</th></tr>
                        <%
                            int i ;
                            String temp;
                            while(rs.next()){ 
                            i= rs.getInt(5);
                            if(i==0){
                                temp = "Disabled";
                            }else{
                                temp = "Enabled";
                            }
                        %>
                        <tr><td><%=rs.getString(1) %></td><td><%=rs.getString(2)%></td><td><%=rs.getString(3) %></td><td><%=rs.getString(4)%></td><td><%=temp%></td></tr>        
                            <%} 
                            rs.getStatement().getConnection().close();
                            %>

                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
