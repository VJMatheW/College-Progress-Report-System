<%-- 
    Document   : Admin
    Created on : 12 Apr, 2017, 3:40:39 PM
    Author     : Vijay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="/fileupload/Html/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css">     
        <script src="/fileupload/Stylesheet/bootstrap/bootstrap.min.js"></script>
        <noscript>
            <div style="border: 1px solid purple; padding: 10px; text-align: center;">
                <h1 style="margin:0 auto;padding: 0;"> <span style="color:red">JavaScript is not enabled!</span></h1><br/>
                <span style="color:red">The site cannot function with JAVASCRIPT Disabled</span>
             </div>
        </noscript>
        <title>Admin Home</title>
        
        <style type="text/css">
            main{
                background-color: #ecfdeb !important;
            }   
            #frame{
                height: 367px;
                width:100%;
                border: none;
            }
        </style>
        
    </head>
    <body style="height: 100%;">
        
        <header><%@include file="../../Html/Header.html"%></header>
        <main>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header">
                      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                      </button>
                    </div>
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" >
                        <ul class="nav navbar-nav">
                            <li><a href="/fileupload/jsp/admin/UploadSubjectCode.jsp" target="frame">Upload Subject Code</a></li>
                            <li><a href="/fileupload/jsp/admin/UploadSubjectName.jsp" target="frame">Upload Subject Name</a></li>
                            <li><a href="/fileupload/jsp/admin/AdminCreate.jsp" target="frame">Create Data</a> </li>
                            <li><a href="/fileupload/jsp/admin/AdminDelete.jsp" target="frame">Delete Data</a></li>
                            <li><a href="/fileupload/jsp/admin/CreateUser.jsp" target="frame">Create / Delete User</a></li>
                            <li><a href="/fileupload/jsp/admin/logincontrol.jsp" target="frame">Access Control</a></li>
                            <li><a href="/fileupload/jsp/admin/EditDataUploads.jsp" target="frame">Edit Data Uploads</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                        <li>
                            <form class="navbar-form navbar-left" action="./Logout" method="get">
                                <button type="submit" class="btn btn-primary">LOGOUT</button>
                            </form>
                        </li>                    
                      </ul>
                    </div><!-- navbar-collapse-->
                </div><!-- container fluid-->
            </nav>
            <div class="container" >
                <iframe name="frame" id="frame"></iframe>
            </div>
            
        </main>
        <footer id="foot" ><%@include file="../../Html/Footer.html" %></footer>
    </body>
</html>
