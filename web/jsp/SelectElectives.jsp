<%-- 
    Document   : SelectElectives
    Created on : 15 Oct, 2017, 7:47:44 PM
    Author     : Vijay
--%>

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
        <title>electives</title>
        <style type="text/css">
            body{
                background-color: #ecfdeb !important;
            }
        </style>
    </head>
    <body class="container-fluid" >
        <%
            String data = Components.fetch("regulation");
            String dept="";
            try{
                 dept = request.getSession().getAttribute("dept").toString();
            }catch(Exception e){
                RequestDispatcher rd = request.getRequestDispatcher("./Html/session_exp.html");
            }
        %>
        <h2><b>Select Electives</b><hr style="border-color: black;"/></h2>
        
        <form class="form-horizontal" action="../FetchRollNo" method="post" target="_blank">
            <input type="hidden" name="dept" id="dept" value="<%=dept %>">
            <div class="form-group">
                <label class="control-label col-sm-2">Batch : </label>
                <div class="col-sm-4">
                    <input type="text" class="form-control input-sm" id="batch" name="batch" maxlength="4" onblur="process()" onkeyup="numberOnly(this)" required />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Regulation : </label>
                <div class="col-sm-4">
                    <select class="form-control input-sm" id="regulation" name="regulation" required> 
                        <option value="">Select Regulation</option>
                        <%=data %>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Section : </label>
                <div class="col-sm-4">
                    <select class="form-control input-sm" id="section" name="section" required>
                        <option value="">Select Section</option>
                    </select><span style="color:red;"id="msg"></span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Semester : </label> 
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="sem" id="semester" onblur="getElectiveNo()" required>
                    <option value="">Select Semester</option>
                    <option value="1">1</option><option value="2">2</option><option value="3">3</option>
                    <option value="4">4</option><option value="5">5</option><option value="6">6</option>
                    <option value="7">7</option><option value="8">8</option>
                </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Elective : </label> 
                <div class="col-sm-4">
                    <select class="form-control input-sm" id="elective" name="elective"  required>
                        <option value="">Select Elective</option>            
                    </select>
                </div>
            </div> 
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-4">
                        <input class="btn btn-sm btn-success" type="submit">
                    </div>
                </div>
        </form>        
    </body>
</html>
<!--
Batch : <input type="text" id="batch" name="batch" maxlength="4" onblur="process()" onkeyup="numberOnly(this)" required /><br/><br/>
            Regulation : <select id="regulation" name="regulation" required> 
                    <option value="">Select Regulation</option>
                    //
                    </select><br/><br/>
            Section : <select id="section" name="section" required>
                <option value="">Select Section</option>
            </select> <br/> <span style="color:red;"id="msg"></span><br/>
            Semester : <select name="sem" id="semester" onblur="getElectiveNo()" required>
                    <option value="">Select Semester</option>
                    <option value="1">1</option><option value="2">2</option><option value="3">3</option>
                    <option value="4">4</option><option value="5">5</option><option value="6">6</option>
                    <option value="7">7</option><option value="8">8</option>
                </select><br/><br/>  
            Elective : <select id="elective" name="elective"  required>
                <option value="">Select Elective</option>            
            </select><br/><br/>    

-->