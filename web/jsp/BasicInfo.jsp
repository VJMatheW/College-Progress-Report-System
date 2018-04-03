<%-- 
    Document   : BasicInfo
    Created on : 12 Apr, 2017, 11:27:17 AM
    Author     : Vijay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="/fileupload/Stylesheet/bootstrap/bootstrap.min.css"> 
        <script src="/fileupload/Html/test.js"></script>
        <script src="/fileupload/Html/section.js"></script>
        <title>Basic Info-Upload</title>
        <style type="text/css">
            body{
                background-color: #ecfdeb !important;
            }
        </style>
    </head>
    <body >
        <%
            HttpSession s = request.getSession();           
            String dept = "na";            
            try{            
            dept = s.getAttribute("dept").toString();
            }catch(Exception e){
                System.out.println("Excetion in Retriveinfo_JSP : "+e);
            } 
        %>
        <h2><b>Upload Basic Information</b><hr style="border-color: black;"/></h2>
        <div class="container-fluid" >
            <div class="col-sm-6" >
                <form class="form-horizontal" action="../upload" method="post" enctype="multipart/form-data">
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
                            <select class="form-control input-sm" id="section" name="section" required>
                                <option value="">Select Section</option>
                            </select> <span style="color:red;"id="msg"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">FileName : </label> 
                        <div class="col-sm-8">
                            <input class="form-control input-sm" type="text" name="filename" maxlength="20" title="The file will be stored in the server with this name you enter"placeholder="Don't add extension"  required />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Select File : </label> 
                        <div class="col-sm-8">
                            <input class="form-control input-sm" type="file" name="file" required />
                        </div>
                    </div>             
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-8">
                            <div class="checkbox">
                                <label><input type="checkbox" name="newEntry" value="true" >For lateral entries only.</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-8">
                            <input class="btn btn-sm btn-success" type="submit" value="Upload">
                        </div>
                    </div>  
                    <input type="hidden" name="regulation" value="nil">
                    <input type="hidden" name="sem" value="nil">
                    <input type="hidden" name="type" value="info">
                </form>
            </div>
            <div class="col-sm-6">
                <h3>Replace RollNo with Register No</h3>
                <form class="form-horizontal" action="../UploadRegNo" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="dept" id="dept" value="<%=dept %>">
                    <div class="form-group">
                        <label class="control-label col-sm-4">Batch : </label> 
                        <div class="col-sm-8">
                            <input class="form-control input-sm" type="text" id="batch" name="batch" minlength="4" maxlength="4" onkeyup="numberOnly(this)" required />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Select File : </label> 
                        <div class="col-sm-8">
                            <input class="form-control input-sm" type="file" name="file" required />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-8">
                            <input class="btn btn-sm btn-success" type="submit" value="Upload" >
                        </div>
                    </div>
                    
                </form>        
            </div>
        </div><!-- Container   -->
    </body>
</html>
