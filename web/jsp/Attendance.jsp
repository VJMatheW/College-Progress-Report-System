<%-- 
    Document   : Attendance
    Created on : 26 Apr, 2017, 2:39:59 PM
    Author     : Vijay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css"> 
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/general.css">
        <script src="/fileupload/Html/test.js"></script>
        <script src="/fileupload/Html/section.js"></script>
        <title>Upload Attendance</title>
        <style type="text/css">
            body{
                background-color: #ecfdeb !important;
            }
        </style>
    </head>
    <body class="container-fluid" >
        <%
            HttpSession s = request.getSession();            
            String dept = "na";            
            try{            
            dept = s.getAttribute("dept").toString();            
            }catch(Exception e){
                System.out.println("Excetion in Retriveinfo_JSP : "+e);
            } 
        %>
        <h2><b>Upload Attendance</b><hr style="border-color: black;"/></h2>
        <form class="form-horizontal" action="../upload" method="post" class="" enctype="multipart/form-data">    
           
            <input type="hidden" name="dept" id="dept" value="<%=dept %>">
            <div class="form-group">
                <label class="control-label col-sm-2">Batch : </label> 
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="text" id="batch" name="batch" minlength="4" maxlength="4" onblur="process()" onkeyup="numberOnly(this)" required />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Section : </label> 
                <div class="col-sm-4">
                    <select class="form-control input-sm" id="section" name="section" required>
                        <option value="">Select Section</option>
                    </select> <span style="color:red;"id="msg"></span>
                </div>
            </div>  
            <div class="form-group">
                <label class="control-label col-sm-2">Semester : </label> 
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="sem" required>
                    <option value="">Select Semester</option>
                    <option value="1">1</option><option value="2">2</option><option value="3">3</option>
                    <option value="4">4</option><option value="5">5</option><option value="6">6</option>
                    <option value="7">7</option><option value="8">8</option>
                </select>
                </div>
            </div>  
            <div class="form-group">
                <label class="control-label col-sm-2">Type : </label> 
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="type" required>
                        <option value="">Select Term</option>
                        <option value="term1">Term-1</option>
                        <option value="term2">Term-2</option>
                        <option value="term3">Term-3</option>
                    </select>
                </div>
            </div> 
            <div class="form-group">
                <label class="control-label col-sm-2">FileName : </label> 
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="text" name="filename" maxlength="20" title="The file will be stored in the server with this name you enter"placeholder="Don't add extension"  required />
                </div>
            </div> 
            <div class="form-group">
                <label class="control-label col-sm-2">Select File : </label> 
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="file" name="file" required />
                </div>
            </div> 
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-4" >
                    <input class="btn btn-sm btn-success" type="submit" />
                </div>
            </div> 
        </form>
    </body>
</html>
