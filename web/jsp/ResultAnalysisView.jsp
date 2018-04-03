<%-- 
    Document   : ResultAnalysisView
    Created on : 2 Jul, 2017, 10:12:12 PM
    Author     : vijay_ravi
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <script src="/fileupload/Html/resultanalysis.js"></script>
                <script src="/fileupload/Html/section.js"></script>
              <!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js"></script>-->
              <script src="/fileupload/Html/Chart.js"></script>
        <title>Result Analysis</title>
         <style type="text/css" >
            #tr:nth-child(odd) {background-color: #ecfdeb} 
            table{
                margin:0 auto;
                border-collapse:collapse;
                border: 1px black solid;
                width: 100%;
            }
            #th{
                height: 20px;
                background-color: #27b71a;
                color:white;
            }
            #th:hover{
                background-color: #239219;
            }
            #th, #td{
                padding: 10px;
                text-align: left;
            }
            #tr:hover{
                background-color: #d0fdce;
                cursor: pointer;
            }
            #tbldiv{
                margin-bottom: 20px;
            }
            #tblsub td{
                text-align: center;
            }
        </style>
    </head>
    <body onload="analysis(); thUpperCase(); subjectName(); graph();">
        <%
            ArrayList<String> data = (ArrayList<String>)request.getAttribute("tableHead");
            ArrayList<String> tbody = (ArrayList<String>)request.getAttribute("subName");
        %>
        
        <h1>Result Analysis</h1>
        <hr>
        <input type="hidden" id="sub" value="<%=data.get(2) %>">
        <table id="tbl" border="1">
            <%=data.get(0) %>
            <%=data.get(1) %>    
        </table>
        
        <div style="border: 1px black solid;width: 500px; margin-top: 20px;margin-bottom: 20px;padding-left: 30px;">
            <h3>No of students Registered : <span id="reg" ></span></h3>
            <h3>No of students Passed : <span id="pass" ></span></h3>
            <h3>No of students Failed : <span id="fail" ></span></h3>
            <h3>Overall Pass Percentage : <span id="percent" ></span></h3>
        </div>
        <div >
            <table id="tblsub" border="1" style="border-collapse: collapse; " >
                <tr id="tr">
                    <th id="th">S No.</th>
                    <th id="th">Subject Code</th>
                    <th id="th">Subject Name</th>
                    <th id="th">Name of the Staff</th>
                    <th id="th">No. of <br/>students Registered</th>
                    <th id="th">No. of <br/>students Passed</th>
                    <th id="th">No. of <br/>students Absent</th>
                    <th id="th">No. of <br/>students Failed</th>
                    <th id="th">Pass %</th>
                    <th id="th" hidden="true">hidden</th>
                </tr>
                <%
                    for(String s:tbody){
                        %><%=s %><%
                    }
                %>
            </table>
        </div>
        
              <canvas id="income" width="700" height="500"></canvas>
    </body>
</html>
