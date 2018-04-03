<%-- 
    Document   : index
    Created on : 18 Mar, 2017, 9:56:13 PM
    Author     : Vijay
--%>

<%@page import="com.Project.Model.Components"%>
<%@page import="java.io.FileInputStream"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="Stylesheet/bootstrap/bootstrap.min.css" rel="stylesheet">
        <link href="Stylesheet/general.css" rel="stylesheet">
        <script src="/fileupload/Html/section.js"></script>
        <title>Login</title>
        <style type="text/css">         
            #pt{
                display: none;
            }
            #sem{
                display: none;
            }
            body{
                background-color: #ecfdeb !important;
            }    
           
        </style>
        
        <noscript>
            <div style="border: 1px solid purple; padding: 10px;display: block;width: 100%;height: 100%; text-align: center;">
                <h1 style="margin:0 auto;padding: 0;"> <span style="color:red">JavaScript is not enabled!</span></h1><br/>
                <span style="color:red">The site cannot function with JAVASCRIPT Disabled</span>
            </div>            
        </noscript>        
    </head>
    
    <body>     
        <header>  <%@include file="Html/Header.html"%></header>
        <main style="overflow: auto;padding-bottom: 65px;padding-top:10px;">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <div id="email">
                        <form action="/fileupload/jsp/email/Email.jsp" method="post" class="form-inline" >
                            <input type="text" name="cipher" placeholder="Email-Confirmation" class="form-control input-sm" >
                            <input type="submit" value="CONFIRM" class="btn btn-sm btn-success">
                        </form>
                    </div>
                </div>
                <div class="col-md-6">
                    <div id="login" class="row text-right" style="padding: 5px;" >
                        <form action="./Login" method="post" class="form-inline">              
                            <input type="text" placeholder="Department - ID" class="form-control input-sm" autofocus="true" maxlength="5" minlength="3" onkeyup="forAdmin(this)" name="id" >
                            <input type="password" class="form-control input-sm" placeholder="Password" name="password" required="true">  
                            <input type="submit" value="LOGIN" class="btn btn-sm btn-success" ><br/>
                            <% 
                                String regulation = Components.fetch("regulation");
                                String dept = Components.fetch("dept");
                                String error = (String)request.getAttribute("error");
                                if(error != null )
                                {
                            %><div class="text-center" ><span class="text-danger" id="invalid" ><b><%=error %></b></span></div>
                            <% } %>
                        </form>
                    </div>
                    <div id="template" class="row">                        
                        <div class="col-md-offset-3 col-md-6">
                            <h4 class="text-center" ><b>Download Template</b></h4>
                            <form class="form-horizontal" action="./Template" method="post">     
                                <label for="type">Type </label><select id="type" class="form-control input-sm" name="type" required="true"  onchange="show(this);" >
                                        <option value="" >Select Type</option>
                                        <option value="info" >Basic Info</option>
                                        <option value="pt">Periodical Test</option>
                                        <option value="attendance">Attendance</option>
                                        <option value="rollToReg">RollNo to RegNo</option>
                                    </select>                      
                                    <div id="pt">
                                        <label for="dept">Department </label> <select class="form-control input-sm" id="dept" name="dept">
                                        <option value="">Select Dept</option>
                                        <%=dept %>                
                                        </select>
                                        <label id="reg">Regulation</label> <select id="reg" class="form-control input-sm" name="regulation"> 
                                        <option value="na">Select Regulation</option>
                                        <%=regulation %>
                                        </select>
                                    </div>
                                    <div id="sem">
                                        <label for="semno">Semester</label> <select class="form-control input-sm" name="sem" id="semno">
                                            <option value="na">Select Semester</option>
                                            <option value="1">1</option><option value="2">2</option><option value="3">3</option>
                                            <option value="4">4</option><option value="5">5</option><option value="6">6</option>
                                            <option value="7">7</option><option value="8">8</option>
                                        </select>
                                    </div>
                                        <br>
                                        <input type="submit" value="Download" class="btn btn-sm btn-success">                       
                            </form> 
                            </div>
                            
                        </div>                                                                         
                    </div>
                </div>
            </div>                                            
        </div>
        </main>
        <footer class="navbar navbar-fixed-bottom" id="copyright" style="background-color: white;">    <%@include file="Html/Footer.html"%></footer>                            
    </body>
</html>
