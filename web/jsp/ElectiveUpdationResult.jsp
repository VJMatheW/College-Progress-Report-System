<%-- 
    Document   : ElectiveUpdationResult
    Created on : 20 Oct, 2017, 5:24:01 PM
    Author     : Vijay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            html, body{
                width: 100%;
                height: 100%;
                margin: 0px;
                padding: 0px;
                position: relative;
                background-color: #ecfdeb !important;
            }
            footer{
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;                
            }
        </style>
    </head>
    <body>
        <header><%@include file="../Html/Header.html" %></header>
        <%
            String content = request.getAttribute("content").toString();
        %>
        
        <div class="container" >
            <h3><%=content %></h3>
        </div>
            <footer style="padding-top: 5px;" ><%@include file="../Html/Footer.html"%></footer>
    </body>
</html>
