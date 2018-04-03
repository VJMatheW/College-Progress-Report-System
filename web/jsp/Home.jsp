<%-- 
    Document   : Home
    Created on : 12 Apr, 2017, 2:56:36 PM
    Author     : Vijay
--%>

<!--<%@page contentType="text/html" pageEncoding="UTF-8"%>-->
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
        <noscript>
            <META HTTP-EQUIV="Refresh" CONTENT="0;URL=index.jsp">
        </noscript>
        <title>Home Page</title>
                
        <style type="text/css">
            body{
                background-color: #ecfdeb !important;
            }   
            #frame{
                width: 100%;
                height: 80vh;
                padding:0px;
                margin:0px;
                border:none;
                background-color: #ecfdeb;
            }
            #pageinfo{
                background-color: black;
                color: white;
                padding: 5px;
                letter-spacing: 5px;
            }
            #pageinfo h5{
                margin: 0px;
                padding:0px;                
            }
           
        </style>
    </head>
    <body id="body">
        <%
            HttpSession s = request.getSession();
            String deptff = (String) s.getAttribute("deptff");
        %>
        <header><%@include file="../Html/Header.html" %></header>
        <main>
            <div id="pageinfo" class="text-center" >
                <h5>DEPARTMENT OF <b><%=deptff.toUpperCase() %></b></h5>
            </div>

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

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                      <ul class="nav navbar-nav">
                        <li class="dropdown">
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Upload <span class="caret"></span></a>
                          <ul class="dropdown-menu">
                            <li><a href="../fileupload/jsp/BasicInfo.jsp" target="frame">Basic Info</a></li>
                            <li><a href="../fileupload/jsp/PtMarks.jsp" target="frame">PT Marks</a></li>
                            <li><a href="../fileupload/jsp/Attendance.jsp" target="frame">Attendance</a></li>
                          </ul>
                        </li>
                        <li class="dropdown">
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">ViewData <span class="caret"></span></a>
                          <ul class="dropdown-menu">
                            <li><a href="../fileupload/jsp/RetriveInfo.jsp" target="frame">View Info</a></li>
                            <li><a href="../fileupload/jsp/RetriveMarks.jsp" target="frame">View Marks</a></li>
                            <li><a href="../fileupload/jsp/RetriveAttendance.jsp" target="frame">View Attendance</a></li>
                          </ul>
                        </li>
                        <li><a href="../fileupload/jsp/Report.jsp" href="#pageinfo" target="frame">Report Card</a></li>
                        <li><a href="../fileupload/jsp/ResultAnalysis.jsp" target="frame">Result Analysis</a></li>
                        <li><a href="../fileupload/jsp/SelectElectives.jsp" target="frame">Select Electives</a></li>
                        <li><a href="../fileupload/jsp/Email.jsp" target="frame">Send Mail</a></li>
                        <li><a href="../fileupload/jsp/TextMsg.jsp" target="frame">Send Message</a></li>
                      </ul>

                      <ul class="nav navbar-nav navbar-right">
                        <li>
                            <form class="navbar-form navbar-left" action="./Logout" method="get">
                                <button type="submit" class="btn btn-primary">LOGOUT</button>
                            </form>
                        </li>                    
                      </ul>
                    </div><!-- /.navbar-collapse -->
                  </div><!-- /.container-fluid -->
            </nav>
            <div class="container" >
                <iframe name="frame" id="frame"></iframe>
            </div>
        </main>
        <footer class="nav navbar-fixed-bottom" id="copyright" style="background-color: white;" ><%@include file="../Html/Footer.html" %></footer>
    </body>
</html>


    
           <!-- <div id="con"> 
                <form action="./Logout" method="get" style="float: right;">
                    <button type="submit"><a>LOGOUT</a></button>
                </form>             
                <a href="../fileupload/jsp/BasicInfo.jsp" target="frame"><button>Basic Info</button></a>
                <a href="../fileupload/jsp/PtMarks.jsp" target="frame"><button >PT Marks</button></a>
                <a href="../fileupload/jsp/Attendance.jsp" target="frame"><button>Attendance</button></a>
                <a href="../fileupload/jsp/RetriveInfo.jsp" target="frame"><button>View Info</button></a>
                <a href="../fileupload/jsp/RetriveMarks.jsp" target="frame"><button>View Marks</button></a>
                <a href="../fileupload/jsp/RetriveAttendance.jsp" target="frame"><button>View Attendance</button></a>
                <a href="../fileupload/jsp/Report.jsp" target="frame"><button>Report Card</button></a>
                <a href="../fileupload/jsp/ResultAnalysis.jsp" target="frame"><button>Result Analysis</button></a>
                <a href="../fileupload/jsp/SelectElectives.jsp" target="frame"><button>Select Electives</button></a>
                <a href="../fileupload/jsp/Email.jsp" target="frame"><button>Send Mail</button></a>
            </div>-->  