<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="resources/css/chosen.min.css" rel="stylesheet" type="text/css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
</head>
<body>
<div class="container">
  <h2>Input Sizing</h2>
  <p>You can quickly size labels and form controls within a Horizontal form by adding .form-group-lg or .form-group-sm to the div class="form-group" element:</p>
  <form class="form-horizontal" role="form" id="createUser_form" name="createUser_form">
  <div class="col-md-6">
    <div class="form-group">
      <label class="col-md-3">Manager<span class="text-danger">*</span></label>
      <div class="col-md-9">
        <select multiple class="chosen form-control mod" name="sel1[]" id="sel1" >
          <option>Select Manager</option>
          <option value="man1">Manager-1</option>
          <option>Manager-2</option>
          <option>Manager-3</option>
        </select>
      </div>
    </div>
    </div>
    
    
    <div class="col-md-6">
    <div class="form-group">
      <label class="col-md-3">Manager2<span class="text-danger">*</span></label>
      <div class="col-md-9">
        <select multiple class="chosen form-control mod1" name="sel2" id="sel2" onchange="getAllLocations('${sourceleadUrl}');" >
         <!--  <option>Select Manager</option> -->
           <c:forEach var="option" items="${countryList}">
					   <option value="${option.name}">${option.name}</option>
	        </c:forEach>
        </select>
      </div>
    </div>
    </div>
    
    
    
    <input type="button" value="sub" onClick="getVal();"/>
  </form>
</div>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> 
<script src="resources/js/chosen.jquery.min.js" type="text/javascript"></script> 
<script type="text/javascript" src="resources/js/chosen.order.jquery.min.js"></script>

  
<script type="text/javascript">  
  $(document).ready(function(){
    $(".chosen-select-deselect").chosen({allow_single_deselect:true});
    $(".mod").chosen({ max_selected_options: 3 });
    $(".mod").bind("chosen:maxselected", function () { alert("max elements selected"); });
    $(".mod").chosen().change( function () { alert("change"); } );
});
 </script>
 
 <script type="text/javascript">  
  $(document).ready(function(){
    $(".chosen-select-deselect").chosen({allow_single_deselect:true});
    $(".mod1").chosen({ max_selected_options: 1 });
    $(".mod1").bind("chosen:maxselected", function () { /* alert("max elements selected"); */ });
    $(".mod1").chosen().change( function () { alert("change"); } );
});
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
   function getVal(){
   /*  var selectedValues = [];    
    $("#sel :selected").each(function(){
        selectedValues.push($(this).val()); 
    });
    alert(selectedValues); */
    
    //get values by order
    $('select[multiple].chosen').chosen();
    var MY_SELECT = $($('select[multiple].chosen').get(0));
    var selection = MY_SELECT.getSelectionOrder();
    var selectedValues = []; 
    $(selection).each(function(i)
    {
        selectedValues.push(selection[i]);
    });
    alert(selectedValues);
    //end get values by order
    
   var frm = $('#createUser_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        
	        var jsonData = encodeURIComponent(con);
	        alert(con); 
   }
  
  </script>
  
  <!-- <script type="text/javascript">
   function change(){
    var selectedValues = [];    
    $("#sel1 :selected").each(function(){
        selectedValues.push($(this).val()); 
    });
    alert(selectedValues);
   }
  
  </script> -->
  
  
  <script>
function getAllLocations(sourceleadUrl){
	var countryName = document.getElementById("sel2").value;
	if(countryName != null && countryName !="" && countryName !='undefined'){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: sourceleadUrl+"/locationAutoComplete?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
        success: function(data) {
        	var json = JSON.parse(data);
        	/* $('#location').tagit({
        		allowSpaces: false,
        		availableTags: json
        		
	       	 	}); */
	       	 	
        	var output = "";
        	//output = output + "<option value=''>-Select Manager-</option>"; 
        	for (var i=0; i<json.length; i++)
			{
	        		var opt = json[i];
	        		output = output + "<option value='" +opt+ "'>"+opt+"</option>";
			}
        	$("#sel1").empty();
	        $("#sel1").append(output);
	        $('#sel1').trigger("chosen:updated");
        },
        error: function(e){
        }
    });
	}
}
</script> 
  
  
</body>
</html>
