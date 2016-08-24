 <%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


 <meta charset="utf-8">
      <title>jQuery UI Autocomplete functionality</title>
      <link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
      <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
      <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      
      
      <style>
         #project-label {
            display: block;
            font-weight: bold;
            margin-bottom: 1em;
         }
         #project-icon {
            float: left;
            height: 32px;
            width: 32px;
         }
         #project-description {
            margin: 0;
            padding: 0;
         }
      </style>
      <!-- Javascript -->
      
     <script>
        function getClients(){
        	var sourceleadUrl ='${sourceleadUrl}';
            $.ajax({
          		type: "POST",
          		 url: sourceleadUrl+"/clientAutoComplete?${_csrf.parameterName}=${_csrf.token}",
          	     success: function(data) {
          	    					var json = JSON.parse(data);
          	    					//clients.push(json); 
          	    					var clients = [];
          	    					for (var i=0; i<json.length; i++)
          	    					{
          	    						var client = json[i];
          	    						temp = 
          	    		 			    {
          	    								
          	    								value: client.clientLocationId.id,
          	    								label: client.clientName,
          	    								 desc: "the official user interface library for jQuery",
          	    								 img : "resources/images/at.png", 
          	    		 			    } 
          	    						clients.push(temp);
          	    					}
          	    					
          	    					 $( "#project" ).autocomplete({
          	    			            minLength: 0,
          	    			            source: clients,
          	    			            focus: function( event, ui ) {
          	    			               $( "#project" ).val( ui.item.label );
          	    			                  return false;
          	    			               },
          	    			             
          	    			             
          	    			               
          	    			            select: function( event, ui ) {
          	    			               $( "#project" ).val( ui.item.label );
          	    			               $( "#project-id" ).val( ui.item.value );
          	    			               $( "#project-description" ).html( ui.item.desc );
          	    			               
          	    			              /*  $('#project').tagit({
          	    			              	 availableTags: clients,
          	    			              	 singleField: true,
          	    			              	 tagLimit:1
          	    			      		  }); */
          	    			               return false;
          	    			            }
          	    			               
          	    			         }).data("ui-autocomplete")._renderItem = function (ul, item) {     
          	    			            var inner_html = '<a><div class="list_item_container"><div class="image"><img src="http://localhost:8080/sourcelead/userImage"></div><div class="label"><h3> Reputation:  ' + item.desc + '</h3></div><div class="description">' + item.label + '</div></div></a><hr/>';
          	    			            return $("<li></li>")
          	    			                    .data("item.autocomplete", item)
          	    			                    .append(inner_html)
          	    			                    .appendTo(ul);
          	    			        };
          	    },
          	    error: function(e){
          		         
          	    } 
          	     
          	});
                  
        }
       
       </script> 
      
</head>

 
<body>
      
         <div id="project-label">Select a project (type "a" for a start):</div>
         <input id="project" onclick="getClients();">
         <input type="hidden" id="project-id">
         <p id="project-description"></p>
         
</body>
</html>  --%>





<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/flick/jquery-ui.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.12/jquery-ui.min.js" type="text/javascript" charset="utf-8"></script>

<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>




<script type="text/javascript">
window.onload = function () {
	cscInit();
 };
</script>

<form id="createUser_form" name="createUser_form"  method="post">
<label for="CountryAutocomplete">Country</label> 
<input id="CountryAutocomplete" type="hidden" name="country" value="" field-name="<fmt:message key="CandidateExperience.Country" />"
	data-validation="required"/><br />
	
	
<ul id="country" onkeypress="getAllCountries('${sourceleadUrl}');"></ul>
 <input type="button" value="Save" id="create_user" onclick="testVal();" class="btn btn-primary">
</form>

<script>
function getAllCountries(sourceleadUrl){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: sourceleadUrl+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').tagit({
        		allowSpaces: false,
        		availableTags: json,
        		
	       	 	});
        },
        error: function(e){
        }
    });
}
</script> 

<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>

<script type="text/javascript">
 function testVal() {
		    var frm = $('#createUser_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        
	        //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
	        alert(con);
	 
    }
 
</script>  






