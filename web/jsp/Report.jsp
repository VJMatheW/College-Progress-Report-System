<%-- 
    Document   : Report
    Created on : 29 Jun, 2017, 4:13:00 PM
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
        <title>Report Card</title>
        <style type="text/css">
            body{
                background-color: #ecfdeb !important;
            }
            #date-pad{
                padding-left: 7px !important;
                padding-right: 7px !important;
            }
            
        </style>
    </head>
    <body class="container-fluid" >
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
        <h2><b>Progress Report</b><hr style="border-color: black;"/></h2>
        <form class="form-horizontal" action="../Report" method="post" >
            <input type="hidden" name="dept" id="dept" value="<%=dept %>">
            <div class="form-group">
                <label class="control-label col-sm-2">Batch : </label> 
                <div class="col-sm-4">
                    <input class="form-control input-sm" type="text" id="batch" name="batch" minlength="4" maxlength="4" onblur="process()" onkeyup="numberOnly(this)" required />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Regulation : </label> 
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="regulation" required> 
                        <option value="">Select Regulation</option>
                        <%=data %>
                    </select>
                </div>
            </div>            
            <div class="form-group">
                <label class="control-label col-sm-2">Semester : </label> 
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="sem" required>
                    <option value="">Select Semester</option>
                    <option value="1">1</option><option value="2">2</option><option value="3">3</option>
                    <option value="4">4</option><option value="5">5</option><option value="6">6</option>
                    <option value="7">7</option><option value="8">8</option>
                </select>
                </div>
            </div> 
            <div class="form-group">
                <label class="control-label col-sm-2">Type : </label>
                <div class="col-sm-4">
                    <select class="form-control input-sm" name="type" required>
                        <option value="">Select PT</option>
                        <option value="pt1">Periodical Test-1</option>
                        <option value="pt2">Periodical Test-2</option>
                        <option value="pt3">Periodical Test-3</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">Section : </label> 
                <div class="col-sm-4">
                    <select class="form-control input-sm" id="section" name="sec" required>
                        <option value="">Select Section</option>
                    </select> <span style="color:red;"id="msg"></span>
                </div>
            </div>  
        <!--    Report Date : <select name="day" required="true">
                <option value="">Day</option>
                <option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>
                <option>6</option><option>7</option><option>8</option><option>9</option><option>10</option>
                <option>11</option><option>12</option><option>13</option><option>14</option><option>15</option>
                <option>16</option><option>17</option><option>18</option><option>19</option><option>20</option>
                <option>21</option><option>22</option><option>23</option><option>24</option><option>25</option>
                <option>26</option><option>27</option><option>28</option><option>29</option><option>30</option>
                <option>31</option>
            </select>
            <select name="month" required="true">
                <option value="">Month</option>
                <option value="1,jan">JAN</option><option value="2,feb">FEB</option><option value="3,mar">MAR</option>
                <option value="4,apr">APR</option><option value="5,may">MAY</option><option value="6,jun">JUN</option>
                <option value="7,jul">JUL</option><option value="8,aug">AUG</option><option value="9,sep">SEP</option>
                <option value="10,oct">OCT</option><option value="11,nov">NOV</option><option value="12,dec">DEC</option>
            </select>
            <input type="text" size="3" name="year" minlength="4" maxlength="4" onkeyup="numberOnly(this);" required="true" placeholder="YYYY"><br/><br/>
        !-->
            <div class="form-group">
                <label class="control-label col-sm-2">Attendance Date : </label> 
                <div class="col-sm-4">
                    <div id="date-pad" class="col-xs-4">
                        <select class="form-control input-sm" name="aday" required="true">
                            <option value="">Day</option>
                            <option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>
                            <option>6</option><option>7</option><option>8</option><option>9</option><option>10</option>
                            <option>11</option><option>12</option><option>13</option><option>14</option><option>15</option>
                            <option>16</option><option>17</option><option>18</option><option>19</option><option>20</option>
                            <option>21</option><option>22</option><option>23</option><option>24</option><option>25</option>
                            <option>26</option><option>27</option><option>28</option><option>29</option><option>30</option>
                            <option>31</option>
                        </select>
                    </div>
                    <div id="date-pad" class="col-xs-4">
                        <select class="form-control input-sm" name="amonth" required="true">
                            <option value="">Month</option>
                            <option value="1,jan">JAN</option><option value="2,feb">FEB</option><option value="3,mar">MAR</option>
                            <option value="4,apr">APR</option><option value="5,may">MAY</option><option value="6,jun">JUN</option>
                            <option value="7,jul">JUL</option><option value="8,aug">AUG</option><option value="9,sep">SEP</option>
                            <option value="10,oct">OCT</option><option value="11,nov">NOV</option><option value="12,dec">DEC</option>
                        </select>
                    </div>
                    <div id="date-pad" class="col-xs-4">
                        <input class="form-control input-sm" type="text" size="3" name="ayear" minlength="4" maxlength="4" onkeyup="numberOnly(this);" required="true" placeholder="YYYY">
                    </div>
                </div>    
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-4" >
                    <input class="btn btn-sm btn-success" type="submit" />
                </div>
            </div>
        </form>
    </body>
</html>
