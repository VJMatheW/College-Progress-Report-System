<%-- 
    Document   : RetriveMarks
    Created on : 11 Jun, 2017, 7:51:00 PM
    Author     : vijay_ravi
--%>

<%@page import="com.Project.Model.Components"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css"> 
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/general.css">
        <script src="/fileupload/Html/section.js"></script>
        <title>View Marks</title>
        <style type="text/css">
            #msg{
                color:red;
            }
            body{
                background-color: #ecfdeb !important;
            }
        </style>
    </head>
    <body>
        <%
            String data = Components.fetch("regulation");            
            HttpSession s = request.getSession();           
            String dept = "na";            
            try{            
            dept = s.getAttribute("dept").toString();
            }catch(Exception e){
                System.out.println("Excetion in Retriveinfo_JSP : "+e);
            } 
        %>
        <h2><b>View Marks</b><hr style="border-color: black;"/></h2>
        <div class="container-fluid">
            <div class="col-sm-6" >
                <form class="form-horizontal" action="../Retrive" method="post" target="_blank">
                    <input type="hidden" name="dept" id="dept" value="<%=dept %>">
                    <div class="form-group">
                        <label class="control-label col-sm-4">Batch : </label> 
                        <div class="col-sm-8">
                            <input class="form-control input-sm" type="text" id="batch" name="batch" minlength="4" maxlength="4" onblur="process()" onkeyup="numberOnly(this)" required />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Section : </label> 
                        <div class="col-sm-8">
                            <select class="form-control input-sm" id="section" name="sec" required>
                                <option value="">Select Section</option>
                            </select> <span style="color:red;"id="msg"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Regulation : </label>
                        <div class="col-sm-8">
                            <select class="form-control input-sm" name="regulation" required> 
                                <option value="">Select Regulation</option>
                                <%=data %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Semester : </label>
                        <div class="col-sm-8">
                            <select class="form-control input-sm" name="sem" required>
                                <option value="">Select Semester</option>
                                <option value="1">1</option><option value="2">2</option><option value="3">3</option>
                                <option value="4">4</option><option value="5">5</option><option value="6">6</option>
                                <option value="7">7</option><option value="8">8</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Type : </label>
                        <div class="col-sm-8">
                            <select class="form-control input-sm" name="type" required>
                                <option value="">Select PT</option>
                                <option value="pt1">Periodical Test-1</option>
                                <option value="pt2">Periodical Test-2</option>
                                <option value="pt3">Periodical Test-3</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class=" col-sm-offset-4 col-sm-8">
                            <input class="btn btn-sm btn-success" type="submit">
                        </div>
                    </div>                   
                </form>   
            </div>
            <div class="col-sm-6">
                <h3>Edit Marks By Reg. No</h3>
                <form class="form-horizontal" action="../ByReg" method="post">
                    <div class="form-group">
                        <label class="control-label col-sm-4" >Register No : </label>
                        <div class="col-sm-8">
                            <input class="form-control input-sm" type="text" name="regNo" maxlength="12" minlength="12" onkeyup="numberOnly(this)" required="true">
                        </div>
                    </div>
                   <div class="form-group">
                        <label class="control-label col-sm-4">Type : </label>
                        <div class="col-sm-8">
                            <select class="form-control input-sm" name="type" required>
                                <option value="">Select PT</option>
                                <option value="pt1">Periodical Test-1</option>
                                <option value="pt2">Periodical Test-2</option>
                                <option value="pt3">Periodical Test-3</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Regulation : </label>
                        <div class="col-sm-8">
                            <select class="form-control input-sm" name="regulation" required> 
                                <option value="">Select Regulation</option>
                                <%=data %>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Semester : </label>
                        <div class="col-sm-8">
                            <select class="form-control input-sm" name="sem" required>
                                <option value="">Select Semester</option>
                                <option value="1">1</option><option value="2">2</option><option value="3">3</option>
                                <option value="4">4</option><option value="5">5</option><option value="6">6</option>
                                <option value="7">7</option><option value="8">8</option>
                            </select>
                        </div>
                    </div>
                            <div class="form-group ">
                                <div class="col-sm-offset-4 col-sm-8">
                                    <input type="submit" class="btn btn-success btn-sm">
                                </div>
                            </div>
                </form>
            </div>    
            </div>
    </body>
</html>


<!--

            LEFT
    Section : <select id="section" name="sec" required>
                <option value="">Select Section</option>
            </select> <br/> <span id="msg"></span><br/>
            Regulation : <select name="regulation" required> 
            <option value="">Select Regulation</option>
            //data
            </select><br/><br/>
            Semester : <select name="sem" required>
                    <option value="">Select Semester</option>
                    <option value="1">1</option><option value="2">2</option><option value="3">3</option>
                    <option value="4">4</option><option value="5">5</option><option value="6">6</option>
                    <option value="7">7</option><option value="8">8</option>
                </select><br/><br/>
            Type : <select name="type" required>
                    <option value="">Select PT</option>
                    <option value="pt1">Periodical Test-1</option>
                    <option value="pt2">Periodical Test-2</option>
                    <option value="pt3">Periodical Test-3</option>
                </select><br/><br/>
                <input type="submit">
-->

<!-- 
        RIGHT

         Register No : <input type="text" name="regNo" maxlength="12" minlength="12" onkeyup="numberOnly(this)" required="true"><br/><br/>
                    Type : <select name="type" required>
                    <option value="">Select PT</option>
                    <option value="pt1">Periodical Test-1</option>
                    <option value="pt2">Periodical Test-2</option>
                    <option value="pt3">Periodical Test-3</option>
                </select><br/><br/>
                Semester : <select name="sem" required>
                    <option value="">Select Semester</option>
                    <option value="1">1</option><option value="2">2</option><option value="3">3</option>
                    <option value="4">4</option><option value="5">5</option><option value="6">6</option>
                    <option value="7">7</option><option value="8">8</option>
                </select><br/><br/>
                Regulation : <select name="regulation" required> 
            <option value="">Select Regulation</option>
            //data
            </select><br/><br/>
                <input type="submit">
-->