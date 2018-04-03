
function countElective(){
   //   alert("inside test");
//    var rowIndex=1;
//    var table = byid("tbl1");
//    var rob = table.getElementsByTagName('tr')[rowIndex];
//    var cells = rob.getElementsByTagName('td');
//    alert(cells.length);
    var head = bytagname("th");
   // alert(head.length);
    var sub = (head.length-2);
    var r = bytagname("tr");
    
    for(var i=2;i<head.length;i++){
        var count = 0;        
    //    alert(r.length);    
        for(var j=1;j<(r.length-1);j++){
            var td = r[j].getElementsByTagName("td");
            //alert("Size of td : "+td.length);
            var radio = td[i].getElementsByTagName("input");
            if( radio[0].checked  ){
                count++;
                //alert("checked");
                //alert(td[0].innerHTML+" "+radio[0].value);
            }
        }
      //  alert("Count Value : "+count);
      //  alert("value of last row "+i+" cloumn :"+r[(r.length-1)].getElementsByTagName("td")[i].innerHTML);
        r[(r.length-1)].getElementsByTagName("td")[i].innerHTML = count+"";
    }
}

function byid(t){
    return document.getElementById(t);
}

function byname(y){
    return document.getElementsByName(y);
}

function bytagname(z){
    return document.getElementsByTagName(z);
}

