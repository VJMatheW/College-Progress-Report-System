<%-- 
    Document   : ResultAnalysis
    Created on : 30 Jun, 2017, 2:22:20 PM
    Author     : vijay_ravi
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
        <title>Result Analysis</title>
        <style type="text/css">
            body{
                background-color: #ecfdeb !important;
            }
        </style>
    </head>
    <body class="container-fluid" >
        <%
            String data = Components.fetch("regulation");   
            HttpSession s = request.getSession();            
            String dept = "na";            
            try{            
            dept = s.getAttribute("dept").toString();
            }catch(Exception e){
                System.out.println("Excetion in Retriveinfo_JSP : "+e);
            } 
        %>
        <h2><b>Result Analysis</b><hr style="border-color: black;"/></h2>
        <form class="form-horizontal" action="../Result" method="post" target="_blank">
            <div class="col-sm-6" >
                <input type="hidden" name="dept" id="dept" value="<%=dept %>">
                <div class="form-group">
                    <label class="control-label col-sm-4">Batch : </label> 
                    <div class="col-sm-8">
                        <input class="form-control input-sm" type="text" id="batch" name="batch" minlength="4" maxlength="4" onblur="process()" onkeyup="numberOnly(this)" required />
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4">Section : </label> 
                    <div class="col-sm-8">
                        <select class="form-control input-sm" id="section" name="sec" required>
                            <option value="">Select Section</option>
                        </select> <span style="color:red;"id="msg"></span>
                    </div>
                </div>  
                <div class="form-group">
                    <label class="control-label col-sm-4">Type : </label>
                    <div class="col-sm-8">
                        <select class="form-control input-sm" name="type" required>
                            <option value="">Select PT</option>
                            <option value="pt1">Periodical Test-1</option>
                            <option value="pt2">Periodical Test-2</option>
                            <option value="pt3">Periodical Test-3</option>
                        </select>
                    </div>
                </div> 
                <div class="form-group">
                    <label class="control-label col-sm-4">Regulation : </label> 
                    <div class="col-sm-8">
                        <select class="form-control input-sm" name="regulation" required> 
                            <option value="">Select Regulation</option>
                            <%=data %>
                        </select>
                    </div>
                </div> 
                <div class="form-group">
                    <label class="control-label col-sm-4">Semester : </label> 
                    <div class="col-sm-8">
                        <select class="form-control input-sm" name="sem" onchange="fectchSubject();" required>
                        <option value="">Select Semester</option>
                        <option value="1">1</option><option value="2">2</option><option value="3">3</option>
                        <option value="4">4</option><option value="5">5</option><option value="6">6</option>
                        <option value="7">7</option><option value="8">8</option>
                    </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-8" >
                        <input class="btn btn-sm btn-success" type="submit" id="btn" value="Generate" disabled="true" />
                    </div>
                </div>
            </div>
            <div class="col-sm-6" id="teachersName" style="display:none;" ></div>            
        </form>
    </body>
</html>
