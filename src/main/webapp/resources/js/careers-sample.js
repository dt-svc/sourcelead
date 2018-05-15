/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function loadScript(url, callback)
{
    // Adding the script tag to the head as suggested before
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = url;

    // Then bind the event to the callback function.
    // There are several events for cross browser compatibility.
    script.onreadystatechange = callback;
    script.onload = callback;

    document.appendChild(script);
}

loadScript("https://code.jquery.com/jquery-3.2.1.min.js", "loadCode");

function loadCode() {
    var fileName = "careers-sample.js";
    var p = getParams(fileName);
    var path = "";
    var b=document.getElementsByTagName("script");
    for(var i=0;i<b.length;i++){
        if(b[i].src.indexOf(fileName) !== -1) {
         path = b[i].src.replace("https://", "").replace("http://", "").substring(0, b[i].src.replace("https://", "").replace("http://", "").indexOf("/"));   
        }
    }
    console.log(path);
    console.log(p);
    $(location.href.replace("careers-sample.js", "app/careers-js"), {}, function(data) {
        var div = document.createElement('div');
        div.id = "carrersDiv";
        document.appendChild(div);
        
        $("#carrersDiv").html(data);
    });
}

function getParams(a){var b=document.getElementsByTagName("script");for(var i=0;i<b.length;i++){if(b[i].src.indexOf("/"+a)>-1){var c=b[i].src.split("?").pop().split("&");var p={};for(var j=0;j<c.length;j++){var d=c[j].split("=");p[d[0]]=d[1]}return p}}return{}}
