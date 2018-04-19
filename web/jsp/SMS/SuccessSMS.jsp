<%-- 
    Document   : SuccessSMS
    Created on : 19 Apr, 2018, 11:07:49 PM
    Author     : Vijay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css">    
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/general.css">        
        <title>SMS - Report</title>
        <style type="text/css">
            body{
                height: 100%;
            }
            #outer{
                display:flex;
            }
            #left{
                flex:1;
            }
            #right{
                flex: 1;
            }
            #center{
                flex:5;
                text-align: center;
            }
            #progress{
                width: 100%;
                margin-top: 10px;
            }
            #bar{
                width: 0%;
                height: 25px;
                -webkit-transition: width 0.5s; /* For Safari 3.1 to 6.0 */
                transition: width 0.5s;
            }
            #foot{
                position: fixed;
                bottom: 0px;
                width: 99%;
            }
            #success{
                opacity: 0;
                -webkit-transition: opacity 1s ease-in; /* For Safari 3.1 to 6.0 */
                transition: opacity 1s ease-in;
                
            }            
            
        </style>
    </head>
    <body onload="" >
        <header>  <%@include file="../../Html/Header.html"%></header>
        
        <div id='outer' >
            <div id='left' ></div>
            <div id='center'>
                <h3 >Progress </h3>
                <div id='progress' class="progress progress-striped active" >
                   <div id='bar' class="progress-bar progress-bar-danger " > <span id='per'><b>0%</b></span> </div>
                </div>
                <div id='success'>
                   <h3>Mail Successfully Sent</h3><br>
                   <div class=" col-xs-offset-5 col-xs-2 text-center" ><a class="btn btn-block btn-success" href='../../index.jsp'>HOME</a></div>
                </div>               
                <div id="temp" >
                    
                </div>
            </div>            
            <div id='right' ></div>
        </div>
        
        <footer id='foot'><%@include file="../../Html/Footer.html" %></footer>
        <script src="/fileupload/Html/section.js"></script>
    </body>
</html>