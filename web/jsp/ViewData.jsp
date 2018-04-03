<%-- 
    Document   : ViewData
    Created on : 11 Jun, 2017, 10:16:20 PM
    Author     : vijay_ravi
--%>

<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1.0">
        <script src="/fileupload/Html/section.js"></script>
        <script src="/fileupload/Html/tableExport.js"></script>
        <noscript>
            <div style="border: 1px solid purple; padding: 10px; text-align: center;">
                <h1 style="margin:0 auto;padding: 0;"> <span style="color:red">JavaScript is not enabled!</span></h1><br/>
              <span style="color:red">The site cannot function with JAVASCRIPT Disabled</span>
            </div>
        </noscript>
        <title>View Data</title>
        
        <style type="text/css" >
            tr:nth-child(odd) {background-color: #ecfdeb !important} 
            
            th{
                background-color: #27b71a !important;
                color:white;
            }
            th:hover{
                background-color: #239219 !important;
            }
            
            tr:hover{
                background-color: #d0fdce !important;
                cursor: pointer;
            }
            .pad{
                padding-top: 12px !important;
            }
            body{
                width: 100%;
                height: 100%;
            }
           .table tbody{
                max-height: 300px;
            }
            tbody tr td{
                white-space: nowrap;
            }
        </style>
    </head>
    <body onload="thUpperCase(); absent();failed();">
        
        <%@include file="../Html/Header.html" %>
        <%
            ArrayList<String> data = (ArrayList<String>)request.getAttribute("tableHead");
        %>
        <input type="hidden" id="src" value="<%=data.get(7)%>">
        <div class="container-fluid text-center" >
            <h3 ><%=data.get(2) %></h3>
            <div class="row" style="background-color: #dfdfdf;" >
                <div class="col-sm-3 pad"><b>Batch : <%=data.get(3) %> </b></div>
                <div class="col-sm-3 pad"><b>Section : <%=data.get(4)+"-"+data.get(5) %></b></div>
                <div class="col-sm-3 pad" >
                    <% if((data.get(6)) == null){                           
                        }else{ %><b>Semester : <%=data.get(6) %></b><% } %>
                </div>
                <div class="col-sm-3">
                    <!--<button class="btn btn-primary" style="margin-top: 5px;margin-bottom: 5px;" onclick="exportToExcel('tbl')"  >Download as Excel</button>-->
                    <form action="./Retrive" method="get"> 
                        <input type="submit" class="btn btn-primary" style="margin-top: 5px;margin-bottom: 5px;" value="Download As Excel">
                    </form>
                </div>
            </div>
        </div>
        <div style="width: 100%;overflow-x: auto;">       
            <table class="table table-responsive table-hover" id="tbl">                
                <thead> <%=data.get(0) %>   </thead>
                <tbody style="" > <%=data.get(1) %>   </tbody>   
            </table>  
        </div>
        <footer><%@include file="../Html/Footer.html"%></footer>
    </body>
</html>
