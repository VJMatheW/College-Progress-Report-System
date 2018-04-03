<%-- 
    Document   : AdminCreate
    Created on : 21 Jun, 2017, 7:23:09 PM
    Author     : vijay_ravi
--%>

<%@page import="java.util.Map"%>
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
        <script src="/fileupload/Html/section.js"></script>
        <title>Admin Create</title>
        <style type="text/css">
            #msg{
                color:red;
            }
            body{
                background-color: #ecfdeb !important;
            }
            .table-striped>tbody>tr:nth-of-type(odd){
                background-color:#ecddee;
            }
            .table-striped>tbody>tr:nth-of-type(even), .table-striped>thead>tr>th{
                background-color:white;
            }
            .table-striped tr td,.table-striped tr th{
                border-color: black !important;
            }
            #tab{
                height:270px;
            }
        </style>
    </head>
    <body>        
        <%
            String regulation = Components.fetch("regulation");
            String dept = Components.fetch("dept");
        %>
        
        <h2><b>Admin Create</b><hr style="border-color: black;"/></h2>
        <div class="container-fluid" >
            <div class="col-sm-6">
                <form class="form-horizontal" action="../../create" method="post">
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
                        <label class="control-label col-sm-4">Batch : </label> 
                        <div class="col-sm-8">
                            <input class="form-control input-sm" type="text" id="batch" name="batch" minlength="4" maxlength="4" onblur="process()" onkeyup="numberOnly(this)" required />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Regulation : </label> 
                        <div class="col-sm-8">
                            <select class="form-control input-sm" name="regulation" required> 
                                <option value="">Select Regulation</option>
                                <%=regulation %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">No of Section : </label> 
                        <div class="col-sm-8">
                            <input class="form-control input-sm" name="noofsection"type="text" maxlength="1" onkeyup="numberOnly(this)" required="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class=" col-sm-offset-4 col-sm-7">
                            <input class="btn btn-sm btn-success" type="submit" value="Create">
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-sm-6">
                <%
                String tbl="";
                Map<String,String> tbody = Components.fetchDeptAndYear();
                for(Map.Entry<String,String> temp : tbody.entrySet()){
                        tbl = tbl+"<tr><td>"+temp.getKey().toUpperCase()+"</td><td>"+temp.getValue().replace(",", ",<br>").toUpperCase()+"</td></tr>";
                    }
                %>
                <div id="tab" >
                    <table class="table table-striped table-bordered" >
                        <thead>
                            <tr><th>Department</th><th>Batch</th></tr>
                        </thead>
                        <tbody>
                           <%=tbl %>
                        </tbody>                    
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
