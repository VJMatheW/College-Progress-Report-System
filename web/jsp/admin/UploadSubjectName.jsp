<%-- 
    Document   : UploadSubjectName
    Created on : 29 Jun, 2017, 6:11:30 PM
    Author     : vijay_ravi
--%>

<%@page import="java.util.Map"%>
<%@page import="com.Project.Model.Components"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css"> 
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/general.css">
        <title>Upload SubjectName</title>
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
        <h2><b>Upload Dept Subjects</b><hr style="border-color: black;"/></h2> 
        <div class="container-fluid" >
            <div class="col-sm-6">
                <form class="form-horizontal" action="../../UploadSubjects" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="type" value="subname">
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
                        <label class="control-label col-sm-4">Regulation : </label> 
                        <div class="col-sm-8">
                            <select class="form-control input-sm" name="regulation" required> 
                                <option value="">Select Regulation</option>
                                <%=regulation %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Select File : </label> 
                        <div class="col-sm-8">
                            <input class="form-control input-sm" type="file" name="file" required />
                        </div>
                    </div>  
                    <div class="form-group">
                            <div class=" col-sm-offset-4 col-sm-7">
                                <input class="btn btn-sm btn-success" type="submit" value="Upload">
                            </div>
                    </div>
                </form>                
            </div>    <!-- FIRST COLUMN -->
            <div class="col-sm-6">
                <%
                    String tbody="";
                    Map<String,String> content = Components.fetchSemSubjectCodeORName("subName");
                    for(Map.Entry<String,String> temp : content.entrySet()){
                        tbody = tbody+"<tr><td>"+temp.getKey().toUpperCase()+"</td><td>"+temp.getValue().replace(",", ",<br>").toUpperCase()+"</td></tr>";
                    }
                %>
                <div id="tab" >
                    <table class="table table-striped table-bordered" >
                        <thead>
                            <tr><th>Department</th><th>Regulation</th></tr>
                        </thead>
                        <tbody>
                           <%=tbody %>
                        </tbody>                    
                    </table>
                </div>
            </div>   <!-- SECOND COLUMN -->
        </div>
    </body>
</html>
