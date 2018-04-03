/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var total=0;
var abs = [];
var fail = [];
var data = [];
var label = [];

function analysis() {
    
    //alert('no of sub :');
    var c = document.getElementById("sub").value;
    //alert('no of sub :'+c);
    var z = parseInt(c);
    var col= z+6;
    var ls =col-4;
    var row;
    var sf = col-1;
    var rcell =col-2;
    for(var e =0;e<z;e++ ){
        abs.push(0);
        fail.push(0);
    }
    //alert('No of columns : '+col+' last subject : '+ls+' no of subject failed : '+sf+' result ce ll:'+rcell);
    var rows = (document.getElementById("tbl").getElementsByTagName("tr").length);
    var table = document.getElementById("tbl");
    for(var i=1,row;row = table.rows[i];i++){
    var result = "PASS";var no_of_sub_failed=0;var ab=0;
    	for(var j=3;j<=ls;j++){
        var c = row.cells[j].innerHTML;
        
        if(c == "AB" || c == "ab"){
        	ab++;
            result = "FAIL";
            abs[j-3]++;
        }else if(parseInt(c)<50){
              //alert('failed');
              result ="FAIL";
              //alert(result);
              no_of_sub_failed++; 
              fail[j-3]++;
          }
        //alert('i : '+i+' j : '+j+" mark : "+c);
        }
        row.cells[col].innerHTML = ab;
        row.cells[sf].innerHTML = no_of_sub_failed;
        row.cells[rcell].innerHTML=result;
    }
    total = rows -1;
    var failed = 0;
    for(var j=1,r;r = table.rows[j];j++){
        if(r.cells[rcell].innerHTML == "FAIL"){
            failed++;
        }
    }
    document.getElementById("reg").innerHTML = total;
    document.getElementById("fail").innerHTML = failed;
    document.getElementById("pass").innerHTML = total-failed;
    document.getElementById("percent").innerHTML = (((total-failed)/total)*100).toFixed(2)+" %";
    
    //alert('absent '+abs);
    //alert('failed '+fail);
    
}

function subjectName(){
    var c = parseInt(document.getElementById("sub").value);
    var tbl = document.getElementById("tblsub");
    var passed = 0;var p ;var sc;var sf;
    //alert('after tbla');
    for(var i=1,row;row = tbl.rows[i];i++){
        //alert('hello'+i);
        row.cells[4].innerHTML = total;
        row.cells[6].innerHTML = abs[i-1];
        row.cells[7].innerHTML = fail[i-1];
        passed = total -(abs[i-1]+fail[i-1]);
        row.cells[5].innerHTML = passed;
        p = Math.round((passed/total)*100);
        row.cells[8].innerHTML = p;
        sc = row.cells[1].innerHTML;
        sf = row.cells[9].innerHTML;
        data.push(p);
        label.push(sc+'('+sf+' )');
    }
    
}

function graph(){
    //["Theory of computation","Discrete mathematics","Internet Programming","Object Oriented Analysis and Design",
    //"Computer Graphics"]
    var la = label;
    var d = data;
   var barData = {
    labels : la,
    datasets : [
        {
            fillColor : "#48A497",
            strokeColor : "#48A4D1",
            data : d
        }
    ]
};

var income = document.getElementById("income").getContext("2d");
var myBar = new Chart(income).Bar(barData, {
    showTooltips: true,
scaleOverride:true,
  scaleSteps:10,
  scaleStartValue:0,
  scaleStepWidth:10,
  options :    {
        tooltipTemplate: "<%= value %>",
        
        showTooltips: true,
        
        onAnimationComplete: function()
        {    
            this.showTooltip(this.datasets[0].points, true);          
        },
        tooltipEvents: []
    },
    onAnimationComplete: function () {

        var ctx = this.chart.ctx;
        ctx.font = this.scale.font;
        ctx.fillStyle = this.scale.textColor
        ctx.textAlign = "center";
        ctx.textBaseline = "bottom";

        this.datasets.forEach(function (dataset) {
            dataset.bars.forEach(function (bar) {
                ctx.fillText(bar.value, bar.x, bar.y - 4);
            });
        })
    }
});
}