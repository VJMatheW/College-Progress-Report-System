

var xmlhttp = xmlhttprequest();
var ip = "172.16.5.8:8080";

function xmlhttprequest(){
    var http;
    if(window.ActiveXObject){
        http = new ActiveXObject("Microsoft.XMLHTTP");
    }else{
        http = new XMLHttpRequest();
    }
    
    return http;
}


function move(){
    //alert("inside move");
    var elem = document.getElementById("bar");
    //alert("after bar");
    var width = 1;
    var id = setInterval(frame,1000);
    function frame(){
        //alert("inside frame");
        if(width >=100){
            document.getElementById("success").style.opacity = "1";
            document.getElementById("bar").style.backgroundColor = "#4CAF50";
            clearInterval(id);
            
            // for fetching and viewing the summary of MAIL Sent 
            if(xmlhttp.readyState === 0 || xmlhttp.readyState === 4){
                xmlhttp.open("GET","http://"+ip+"/fileupload/EmailConfirm?decision=listSentItems",true);
                xmlhttp.onreadystatechange = function(){
                    if(this.status == 200){
                        response = xmlhttp.responseText;                       
                        //alert(response);                        
                        document.getElementById("temp").innerHTML = response;                        
                    }
                };
                xmlhttp.send(null);
            }
        }else{
            //alert("inside else");
            if(xmlhttp.readyState === 0 || xmlhttp.readyState === 4){
                xmlhttp.open("GET","http://"+ip+"/fileupload/EmailConfirm?decision=progress",true);
                xmlhttp.onreadystatechange = function(){
                    if(this.status == 200){
                        response = xmlhttp.responseText;
                        //alert(response);
                        width = parseInt(response) ;
                        document.getElementById("per").innerHTML = width+"%";
                        elem.style.width = width+"%";
                        if(width == 100){
                            
                        }
                    }
                };
                xmlhttp.send(null);
            }
        }
    }
 }

function getElectiveNo(){
    //alert("inside getelectiveNo");
    var batch = document.getElementById("batch").value;
    var dept = document.getElementById("dept").value;
    var reg = document.getElementById("regulation").value;
    var sem = document.getElementById("semester").value;
    
    //alert(batch+dept+reg+sem);
    // /NoOfElective
    
    if (dept != "na"){
      
        if (xmlhttp.readyState === 0 || xmlhttp.readyState === 4) {
            //alert("url = http://"+ip+"/fileupload/NoOfElective?batch="+batch+"&dept="+dept+"&regulation="+reg+"&sem="+sem);
            xmlhttp.open("GET","http://"+ip+"/fileupload/NoOfElective?batch="+batch+"&dept="+dept+"&regulation="+reg+"&sem="+sem,true);
            xmlhttp.onreadystatechange = handleResponse;
            xmlhttp.send(null);
        }else{
            document.getElementById("msg").innerHTML = '<h3>Unable to create AjaxRequest <br/>Try refreshing the page...</h3>'
        }
    }else{
        //alert('dept is NA');
        document.getElementById("msg").innerHTML='<b>Session Expired</b> Please <a href="/fileupload/index.jsp" target="_blank"><i>Login</i></a>';
    }
}

function handleResponse(){
    if(xmlhttp.status === 200){
        //alert("Haldling response");
        xmlresponse = xmlhttp.responseXML;
        xmldoc = xmlresponse;//.documentElement;
        var nameofelective = xmldoc.getElementsByTagName("noofelective")[0].childNodes[0].nodeValue;
        //alert("NO Elective : "+nameofelective.trim());
        if(nameofelective == "null"){
            document.getElementById("elective").innerHTML = '<option value="">Select Section</option>';
            document.getElementById("msg").innerHTML='<b>Elective file </b> uploads might not be done or Selected <b>SEMESTER</b> might not have Elective Subject';
        }else{
            //alert("Inside else part");
            document.getElementById("msg").innerHTML='';
            document.getElementById("elective").innerHTML = '<option value="">Select Elective</option>';
            var array = nameofelective.split(",");
            for(var i=0;i<=array.length;i++)
            {
                var newoption = document.createElement("option");
                //alert("Value : "+array[i].trim());
                newoption.value = array[i].trim();
                newoption.innerHTML = ""+array[i].toUpperCase();
                elective.options.add(newoption);
            }
        }
    }
    
}

//function addNewOption(item){
//    alert("Item : "+item);
//    var newoption = document.createElement("option");
//    newoption.value = item.trim();
//    alert("After trimming");
//    newoption.innerHTML = item.toUppercase();
//    elective.options.add(newoption);
//}

function process(){
    //alert("inside process");
    var batch = document.getElementById("batch").value;
    var dept = document.getElementById("dept").value;
    //alert(dept+':'+batch);
    if (dept != "na"){
      
        if (xmlhttp.readyState === 0 || xmlhttp.readyState === 4) {
            //alert('dept :'+dept+' batch : '+batch);
            //alert("http://192.168.1.100:8080/fileupload/SectionAjax?batch="+batch+"&dept="+dept);
            //xmlhttp.open("GET","http://getname.ddns.net/fileupload/SectionAjax?batch="+batch+"&dept="+dept,true);
            xmlhttp.open("GET","http://"+ip+"/fileupload/SectionAjax?batch="+batch+"&dept="+dept,true);
            //xmlhttp.open("GET","http://172.16.5.35/fileupload/SectionAjax?batch="+batch+"&dept="+dept,true); 
            xmlhttp.onreadystatechange = handleServerResponse;
            xmlhttp.send(null);
        }else{
            document.getElementById("msg").innerHTML = '<h3>Try refreshing the page...</h3>'
        }
    }else{
        //alert('dept is NA');
        document.getElementById("msg").innerHTML='<b>Session Expired</b> Please <a href="/fileupload/index.jsp" target="_blank"><i>Login</i></a>';
    }
}

function handleServerResponse(){
    if(xmlhttp.status === 200){
        //alert('insdie response ');
        xmlresponse = xmlhttp.responseXML;
        xmldoc = xmlresponse;//.documentElement;
        var noofsection = xmldoc.getElementsByTagName("section")[0].childNodes[0].nodeValue;
        var dept = document.getElementById("dept").value;
        
        if(dept == 'na' || noofsection == "0" )
                {
                    document.getElementById("section").innerHTML = '<option value="">Select Section</option>';
                    document.getElementById("msg").innerHTML='Information about <b>section</b> not updated Contact <b> Admin </b>';

                }else{
                        document.getElementById("msg").innerHTML='';
                        document.getElementById("section").innerHTML = '<option value="">Select Section</option>';
                        for (i = 1; i<= noofsection ; i++)
                        {
                            var newoption = document.createElement("option");
                            newoption.value = i;
                            newoption.innerHTML = dept.charAt(0).toUpperCase()+dept.slice(1)+' '+i;
                            section.options.add(newoption);
                        }
                }
    }
}

function numberOnly(input){
    var regex = /[^0-9,A,B]/gi;    
    input.value = input.value.replace(regex,"");
}
function sqlInject(input){
    var regex = /["]/gi;    
    input.value = input.value.replace(regex,"");
}
function date(input){
    var regex = /[^0-9,/]/gi;    
    input.value = input.value.replace(regex,"");
}

function forAdmin(input){
    var regex =  /[^0-9,a,d,m,i,n]/gi;
    input.value = input.value.replace(regex,""); 
}

function thUpperCase() {
    var x = document.getElementsByTagName("TH");
    for(var i=0;i<x.length;i++){
    x[i].innerHTML = x[i].innerHTML.toUpperCase();}
}

function absent() {
    var x = document.getElementsByTagName("TD");
    for(var i=0;i<x.length;i++){
    if (x[i].innerHTML == "AB" ){
    x[i].style.backgroundColor = "#ecf719";
    x[i].style.borderLeft = "1px solid black";
    }
    }
}

function failed() {   
    
    var x = document.getElementsByTagName("TD");
    var y = document.getElementsByTagName("TH").length;
    var l = y - 1;
    var src = document.getElementById("src").value;
    if (src == 'pt1' || src == 'pt2' || src == 'pt3')
    {
        for(var i=0;i<x.length;i++){
            if (x[i].innerHTML < 50 ){
            x[i].style.backgroundColor = "red";
            x[i].style.borderLeft = "1px solid black";
            }
           }
        for(var k=0;k<x.length;k++){
            k=k+l;
            x[k].style.backgroundColor = "transparent";            
            x[k].style.borderLeft = "none";            
        }
        var j = subsf();
        
        }else if (src == 'term1' || src == 'term2' || src == 'term3' ){
            for(var a=0;a<x.length;a++){
            a=a+l;
                if(x[a].innerHTML < 75 ){
                   x[a].style.backgroundColor = "red";  
                   x[a].style.borderLeft = "1px solid black";
                }
            }
        }
}

function length(){
    var x = document.getElementsByTagName("TD");
    var y = document.getElementsByTagName("TR");
    var z = document.getElementsByTagName("TH");
    var t = x.length;
    var s = y.length;
    var u = z.length;
    alert('length : '+t+' no of rows : '+s+' no of columns : '+u);
}

function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("tbl");
  switching = true;
  dir = "asc"; 
  while (switching) {
    switching = false;
    rows = table.getElementsByTagName("TR");
    for (i = 1; i < (rows.length-1); i++) {
      shouldSwitch = false;
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      
      var a = x.innerHTML=='AB'?0:x.innerHTML;
      var b = y.innerHTML=='AB'?0:y.innerHTML;
      
      if (dir == "asc") {
        if (parseFloat(a) > parseFloat(b)) {
          shouldSwitch= true;
          break;
        }
      } else if (dir == "desc") {
        if ( parseFloat(a) < parseFloat(b)) {
          shouldSwitch= true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      switchcount ++;      
    } else {
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}


function show(input){
    var x = input.options[input.selectedIndex].value;
    //alert(x);
    
    if(x == 'pt'){
      //  alert('periodical test');
        document.getElementById("pt").style.display = "block";
        document.getElementById("sem").style.display = "block";
        document.getElementById("semno").required=true;
    }else if(x == 'attendance'){
        document.getElementById("sem").style.display = "none";
        document.getElementById("pt").style.display = "none";
    }else if(x == 'info' || x == ''){
        document.getElementById("pt").style.display = "none";
        document.getElementById("sem").style.display = "none";
    }
}

function subsf(){
    if(document.getElementById("src").value.startsWith("pt")){
        var str = document.getElementsById("subsf");
        alert(str);   
    }    
}

function byName(name){
    return document.getElementsByName(name)[0];
}
function byId(id){
    return document.getElementById(id);
}

function fectchSubject(){
    //console.log("Hey inside fetchSubject");
    var dept = byId("dept").value;
    var regulation = byName("regulation").value;
    var sem = byName("sem").value; 
    var div = byId("teachersName");
    //console.log("dept : "+dept+" reg : "+regulation+" sem : "+sem);
    
    if (xmlhttp.readyState === 0 || xmlhttp.readyState === 4) {
            //alert("url = http://"+ip+"/fileupload/NoOfElective?batch="+batch+"&dept="+dept+"&regulation="+reg+"&sem="+sem);
            xmlhttp.open("GET","http://"+ip+"/fileupload/Result?dept="+dept+"&reg="+regulation+"&sem="+sem,true);
            xmlhttp.onreadystatechange = function(){
                if(this.status == 200){
                    var response = JSON.parse(xmlhttp.responseText);
                    console.log(response.tags.length);
                    if(response.tags.length != 0){
                        var text = "<h4><b>Enter Professor's Name </b></h4>";
                        for(var z=0;z<response.tags.length;z++){
                            //console.log(response.tags[z]);
                            text += response.tags[z];
                        }
                        div.innerHTML = text;
                        byId("btn").disabled = false;
                        byId("teachersName").style.display = "block";
                    }else{
                        byId("teachersName").style.display = "none";
                    }
                }
            };
            xmlhttp.send(null);
        }else{
            document.getElementById("msg").innerHTML = '<h3>Unable to create AjaxRequest <br/>Try refreshing the page...</h3>'
        }
    
        
}