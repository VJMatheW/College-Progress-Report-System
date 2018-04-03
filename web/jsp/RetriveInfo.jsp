<%-- 
    Document   : RetriveInfo
    Created on : 17 Jun, 2017, 2:31:37 PM
    Author     : vijay_ravi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css"> 
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/general.css">
        <script src="/fileupload/Html/section.js"></script>
        <title>View Info</title>
        
        <style type="text/css">
            #option{
                border:1px solid #cccccc;
            }
            #msg{
                color:red;
            }
            body{
                background-color: #ecfdeb !important;
            }
            .col-sm-6{
                padding-right: 5px !important;
            }
        </style>
    </head>
    <body>
        <%
            HttpSession s = request.getSession();
            
            
            String dept = "na";
            
            try{            
            dept = s.getAttribute("dept").toString();
            
            }catch(Exception e){
                System.out.println("Excetion in Retriveinfo_JSP : "+e);
            } 
        %>
        <h2><b>View Information</b><hr style="border-color: black;"/></h2>
        <div class="container-fluid">
            <div class="col-sm-6" >
                <form class="form-horizontal" action="../Retrive" method="post" target="_blank">
                    <input type="hidden" name="type" value="info">
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
                        <label class="control-label col-sm-4" >Info Option : </label>
                        <div class="col-sm-8" id="option" >
                            <div class="checkbox">
                            <label><input type="checkbox" name="options" value="rollno" required="true" checked="true">Register No</label><br/>
                            <label><input type="checkbox" name="options" value="name">Name</label>
                            <label><input type="checkbox" name="options" value="f_name">Father Name</label>&nbsp;
                            <label><input type="checkbox" name="options" value="m_name">Mother Name</label><br/>
                            <label><input type="checkbox" name="options" value="p_addr_line1,p_addr_line2,p_addr_line3">Permanent Address</label>&nbsp;
                            <label><input type="checkbox" name="options" value="c_addr_line1,c_addr_line2,c_addr_line3">Communication Address</label><br/>
                            <label><input type="checkbox" name="options" value="f_mobile">Father Mobile</label>&nbsp;
                            <label><input type="checkbox" name="options" value="m_mobile">Mother Mobile</label>&nbsp;
                            <label><input type="checkbox" name="options" value="s_mobile">Student Mobile</label>
                            <label><input type="checkbox" name="options" value="f_mail">Father Mail</label>
                            <label><input type="checkbox" name="options" value="s_mail">Student Mail</label><br/>                            
                            <label><input type="checkbox" name="options" value="gender">Gender</label>&nbsp;
                            <label><input type="checkbox" name="options" value="dob">DOB</label>&nbsp;
                            <label><input type="checkbox" name="options" value="ds_h">DayScholar/Hosteller</label>&nbsp;
                            <label><input type="checkbox" name="options" value="section">Section</label>&nbsp;
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-7">
                            <input class="btn btn-sm btn-success" type="submit">
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-sm-6">
                <h3>&nbsp;Edit Information by Reg. No</h3>
                <form class="form-horizontal" action="../ByReg" method="post">
                    <input type="hidden" name="type" value="info">
                    <div class="form-group">
                        <label class="control-label col-sm-4" >Register No : </label>
                        <div class="col-sm-8">
                            <input class="form-control input-sm" type="text" name="regNo" maxlength="12" minlength="12" onkeyup="numberOnly(this)" required="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4" >Info Option : </label>
                        <div class="col-sm-8" id="option" >
                            <div class="checkbox">
                            <label><input type="checkbox" name="options" value="rollno" required="true" checked="true">Register No</label><br/>
                            <label><input type="checkbox" name="options" value="name">Name</label>
                            <label><input type="checkbox" name="options" value="f_name">Father Name</label>&nbsp;
                            <label><input type="checkbox" name="options" value="m_name">Mother Name</label><br/>
                            <label><input type="checkbox" name="options" value="p_addr_line1,p_addr_line2,p_addr_line3">Permanent Address</label>&nbsp;
                            <label><input type="checkbox" name="options" value="c_addr_line1,c_addr_line2,c_addr_line3">Communication Address</label><br/>
                            <label><input type="checkbox" name="options" value="f_mobile">Father Mobile</label>&nbsp;
                            <label><input type="checkbox" name="options" value="m_mobile">Mother Mobile</label>&nbsp;
                            <label><input type="checkbox" name="options" value="s_mobile">Student Mobile</label>
                            <label><input type="checkbox" name="options" value="f_mail">Father Mail</label>
                            <label><input type="checkbox" name="options" value="s_mail">Student Mail</label><br/>
                            <label><input type="checkbox" name="options" value="gender">Gender</label>&nbsp;
                            <label><input type="checkbox" name="options" value="dob">DOB</label>&nbsp;
                            <label><input type="checkbox" name="options" value="ds_h">DayScholar/Hosteller</label>&nbsp;
                            <label><input type="checkbox" name="options" value="section">Section</label>&nbsp;
                            </div>
                        </div>                        
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-7">
                            <input class="btn btn-sm btn-success" type="submit">
                        </div>
                    </div>
                </form>
            </div>            
        </div>
    </body>
</html>

<!--
    LEFT AND RIGHT 

Batch : <input type="text" id="batch" name="batch" minlength="4" maxlength="4" required onblur="process()" onkeyup="numberOnly(this)" placeholder="Year" /><br/>
            <br/>
            Section : <select id="section" name="sec" required>
                    <option value="">Select Section</option>
            </select> &nbsp;
                <label id ="msg"></label>
                <br/><br/>
            Info Option :<br/><hr>
            <div id="option">
            <input type="checkbox" name="options" value="rollno" required="true" checked="true"><label>Register No</label><br/>
            <input type="checkbox" name="options" value="name"><label>Name</label>
            <input type="checkbox" name="options" value="f_name"><label>Father Name</label>
            <input type="checkbox" name="options" value="m_name"><label>Mother Name</label><br/>
            <input type="checkbox" name="options" value="p_addr_line1,p_addr_line2,p_addr_line3"><label>Permanent Address</label>
            <input type="checkbox" name="options" value="c_addr_line1,c_addr_line2,c_addr_line3"><label>Communication Address</label><br/>
            <input type="checkbox" name="options" value="f_mobile"><label>Father Mobile</label>
            <input type="checkbox" name="options" value="m_mobile"><label>Mother Mobile</label>
            <input type="checkbox" name="options" value="s_mobile"><label>Student Mobile</label><br/>
            <input type="checkbox" name="options" value="gender"><label>Gender</label>
            <input type="checkbox" name="options" value="dob"><label>DOB</label>
            <input type="checkbox" name="options" value="ds_h"><label>DayScholar/Hosteller</label>
            <input type="checkbox" name="options" value="section"><label>Section</label>
            </div><hr>
            
            <br/><input type="submit">
        </form>
        <br/><br/><br/><br/><br/><br/>
        
        <h3>Edit Information by Reg. No</h3>
        <form action="../ByReg" method="post">
            <input type="hidden" name="type" value="info">
            Register No : <input type="text" name="regNo" maxlength="12" minlength="12" onkeyup="numberOnly(this)" required="true"><br/><br/>
            Info Option :<br/><hr>
            <div id="option">
                <input type="checkbox" name="options" value="name"><label>Name</label>
                <input type="checkbox" name="options" value="f_name"><label>Father Name</label>
                <input type="checkbox" name="options" value="m_name"><label>Mother Name</label><br/>
                <input type="checkbox" name="options" value="p_addr_line1,p_addr_line2,p_addr_line3"><label>Permanent Address</label>
                <input type="checkbox" name="options" value="c_addr_line1,c_addr_line2,c_addr_line3"><label>Communication Address</label><br/>
                <input type="checkbox" name="options" value="f_mobile"><label>Father Mobile</label>
                <input type="checkbox" name="options" value="m_mobile"><label>Mother Mobile</label>
                <input type="checkbox" name="options" value="s_mobile"><label>Student Mobile</label><br/>
                <input type="checkbox" name="options" value="gender"><label>Gender</label>
                <input type="checkbox" name="options" value="dob"><label>DOB</label>
                <input type="checkbox" name="options" value="ds_h"><label>DayScholar/Hosteller</label>
                <input type="checkbox" name="options" value="section"><label>Section</label>
            </div><hr>
            
            <br/><input type="submit">
-->