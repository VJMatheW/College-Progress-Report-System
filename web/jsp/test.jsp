<%-- 
    Document   : test
    Created on : 7 Dec, 2017, 8:46:27 PM
    Author     : Vijay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test</title>
        <style type="text/css">
            html,body {
                  margin: 0;
                  padding: 0;
                  height: 100%;
                }
                
                .outer{
                    min-height: 100%;
                    position: relative;
                }
                header {
                    background: #ededed;
                }
                #content {
                    padding-bottom: 40px;
                    /* Height of the footer element */
                }
                footer{
                    width: 100%;                    
                    position: absolute;
                    bottom: 0;
                    left: 0;
                }
        </style>
    </head>
    <body>
        <div class="outer">
            <header ><%@include file="../Html/Header.html"%></header>
            <div id="content" ><h1>Hello World!</h1></div>
            <footer><%@include file="../Html/Footer.html"%></footer>
        </div>
        
    </body>
</html>
