<%-- 
    Document   : Email
    Created on : 10 Nov, 2017, 9:54:16 AM
    Author     : Vijay
--%>

<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="com.Project.Model.Components"%>
<%@page import="com.Project.Email.EmailBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Base64"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css"> 
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/general.css">
        <title>Email Confirmation</title>
        <style type="text/css">
            #emailDiv{
                display: none;
            }
            body{
                width: 100%;
                height: 100%;
            }
            .outer{
                min-height: 100%;
                position: relative;
            }
            footer{
                    width: 100%;                    
                    bottom: 0;
                    left: 0;
            }
            
        </style>
        
    </head>
    <body>
        <header>  <%@include file="../../Html/Header.html"%></header>
        <%
            try{
            String str = request.getParameter("cipher").trim();
            String decode = new String(Base64.getDecoder().decode(str),StandardCharsets.UTF_8);
            String temp[] = decode.split(",");
            ArrayList<EmailBean> ten = Components.fetchObject(temp[1], temp[2], decode);        
        %>
        <div id="" class="container-fluid outer">
            <div id="" class="col-sm-3">
                <form class="form-horizontal" action="../../EmailConfirm" method="post">
                    <input type="hidden" name="cipher" value="<%=str %>">
                    <div class="form-group" >
                        <div class="col-xs-12" >
                            <div class="checkbox">
                                <label for="#true"> <input type="radio" id="true" name="decision" value="true" onclick='show(this);' > Confirm and Send</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-xs-12" >
                            <div class="checkbox">
                                <label for="#true"><input type="radio" id="false" name="decision" value="false" onclick='show(this);' > Decline,There is Correction</label>
                            </div>
                        </div>
                    </div>
                    <div id="emailDiv">
                        <div class="form-group" >
                            <div class="col-xs-12" >
                                <input type="email" class="form-control input-sm" name="uname" id="mail" placeholder="Email-ID" >
                            </div>
                        </div>
                        <div class="form-group" >
                            <div class="col-xs-12" >
                                <input type="password" class="form-control input-sm" onkeyup="check();"  onchange="check();" name="password" placeholder="Enter Password" id="pw1">
                            </div>
                        </div>
                        <div class="form-group" >
                            <div class="col-xs-12" >
                                <input type="password" class="form-control input-sm" onkeyup="check();" onchange="check();" placeholder="Re-Enter Password" id="pw2">
                            </div>
                            <span style="margin:5px; padding: 5px;"  id="ms" style="color:red;"></span>
                        </div>                        
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12" >
                            <input class="btn btn-sm btn-success" id="button" type="submit" disabled="true"  value="Send Mail" />
                        </div>
                    </div>
                </form>                
            </div>
            <div id="" class="col-sm-9" style="background-color: #ecfdeb !important;">
        
        <%
            for(int i=0;i<ten.size();i++){
                EmailBean tep = ten.get(i);
                %>
        
                To : <%=tep.getTo() %><br/>
                <%=tep.getMessage() %>
                <hr style="border-color: black;" />           
        <%
                }
            }catch(Exception e){
                out.println("Oops something went wrong!!");
            }
        %>
         </div>
        </div>
        <footer> <%@include file="../../Html/Footer.html"%></footer>
        <script type='text/javascript'>
            function show(obj){
                id("true").checked = false;
                id("false").checked = false;
                //alert("Hello World"+obj.value);
                if(obj.value == "true"){
                    obj.checked = true;
                    id("button").disabled = true;
                    id("emailDiv").style.display = "block";
                    id("mail").required = true;
                    id("pw1").required = true;
                    id("pw2").required = true;
                }else if(obj.value == "false"){
                    id("button").disabled = false;
                    id("emailDiv").style.display = "none";
                    id("mail").required = false;
                    id("pw1").required = false;
                    id("pw2").required = false; 
                    obj.checked = false;
                    obj.checked = true;
                }
            }
            function id(id){
                return document.getElementById(id);
            }
            function check(){
                if(id("pw1").value == "" || id("pw2").value == ""){
                    id("ms").innerHTML = "";
                }else{
                    if(id("pw1").value !== id("pw2").value){
                        id("button").disabled = true;
                        id("ms").style.color = "red";
                        id("ms").innerHTML = "Password Not-Matching";
                    }else{
                        id("button").disabled = false;
                        id("ms").style.color = "green";
                        id("ms").innerHTML = "Password Matches";
                    }
                }
            }
        </script>
    </body>
</html>
