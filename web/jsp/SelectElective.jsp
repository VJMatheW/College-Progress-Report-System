<%-- 
    Document   : SelectElective
    Created on : 16 Oct, 2017, 10:22:56 PM
    Author     : Vijay
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="/fileupload/Html/CountElective.js"></script>
        <link rel="stylesheet" type="text/css" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css">     
        <title>Elective Select</title>
        <style type="text/css" >
            .table-striped{
                background-color: #ecfdeb !important;
            }
            thead tr{
                background-color: #27b71a !important;
                color:white;
            }
            
            .table-fixed {
                width: 100%;
                tbody {
                  overflow-y: auto;
                  width: 100%;
                }
                thead,
                tbody,
                tr,
                td,
                th {
                  display: block;
                }
                tbody {
                  td {
                    float: left;
                  }
                }
                thead {
                  tr {
                    th {
                      float: left;
                    }
                  }
                }
              }

            
            
        </style>
    </head>
    <body>
        <%@include file="../Html/Header.html" %>
        <%
        String head = request.getAttribute("thead").toString();
        String subject = request.getAttribute("subject").toString();
        ResultSet rs = (ResultSet)request.getAttribute("tbody");
        int no = (Integer)request.getAttribute("totalSubject"); 
        ArrayList<String> subCode = (ArrayList)request.getAttribute("subjectCode");
        ArrayList<String> regNo = new ArrayList();
        %>
        <div class="container-fluid">
        <form action="./UpdateElective" method="post">
            <div class="table-responsive">
            <table class="table table-fixed table-striped table-hover">
                <thead>
                    <tr><%=head %></tr>
                </thead>
                <tbody>
                    <%
                        Long rollno;
                        while(rs.next()){
                            String temp="";
                            rollno = rs.getLong(1);
                            regNo.add(rollno+"");
                            temp = "<tr><td>"+rollno+"</td><td>"+rs.getString(2)+"</td>";
                            for(String si : subCode){
                                temp = temp + "<td><input type=\"radio\" onclick=\"countElective()\" value=\""+si+"\" name=\""+rollno+"\" required></td>";
                            }
                            temp = temp + "</tr>";
                            %><%=temp %><%
                        }
                        rs.getStatement().getConnection().close();
                        %>
                        <tr><td></td><td></td>
                        <%
                            String temp1="";
                            for(int l=1; l<=no;l++){
                        %>
                            <td id="col<%=l %>"></td>
                    <%
                        }
                    %>
                    </tr>
                </tbody>
            </table>
            </div>
                    <input type="hidden" name="regNo" value="<%=regNo.toString().replace("[","").replace("]", "") %>">
                    <input type="hidden" name="elective" value="<%=subject %>">
                    <input class="btn btn-success" type="submit">
        </form>
                    Total No of Subject :<%=no %>
        </div>
            
            <!--<%=temp1 %> -->
            <footer class="" id="copyright" style="background-color: white;" ><%@include file="../Html/Footer.html" %></footer>
    </body>
</html>
