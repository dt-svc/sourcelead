<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="resources/js/bootstrap-select.min.js"></script>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 
<link rel="stylesheet" href="resources/css/chosen.min.css">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="resources/css/chosen.min.css">

<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/chosen.jquery.min.js"></script> 



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 
     @author : David Raju.
     this script is for edit Candidate.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<style>
	.er .bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn){
	width:100px;
	}
	.er .btn-default{
	background:#337ab7;
	border:1px solid #ccc;
	color:#fff;
	text-shadow: none;
	}
	.clsDatePicker {
		z-index: 100000 !important;
	}
	#uploadFileGroup p.jquery_form_error_message{
	position: absolute;
	left:0;
	bottom:-16px;
	}
	.selp-custom-min .btn-default{
	/* z-index: -55 !important; */
	-webkit-border-radius:0 3px 3px 0;
	-moz-border-radius:0 3px 3px 0;
	-ms-border-radius:0 3px 3px 0;
	border-radius:0 3px 3px 0;
	}
	.selp-custom-min .open {
	z-index: 999999999 !important;
	}
	.ratetype p {
	bottom: -14px;
	left:0px !important;
	position: absolute;
	}
	
	.pras.req-title p{
	bottom:-14px;
	}
	.exprience .select-field select{
	background-position: 96% center !important;
	}
	</style>

<style type="text/css">
.require_wrap_details{
text-align:left;
}
</style>
<script>
function editCandidate(candidateId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  var loginCountryName = '${loginCountryName}';
	  
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		
		
 if ($('#candidateProfileEdit_form').validate(false, validationSettings)){ 
	  
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getCandidateById?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
         success :function(data) {  
              if(data != null && data != 'undefined' && data != ""){  
              	 var json = JSON.parse(data);
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{   
            			 	var lastName = '';
            			 	if(json.lastName != null && json.lastName != '' && json.lastName != 'undefined'){
            			 		lastName = json.lastName;            			 		
            			 	}
            			 	var mobilePhone = '';
            			 	if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone != 'undefined'){
            			 		mobilePhone = json.mobilePhone;            			 		
            			 	}
            			 	var designation = '';
            			 	if(json.designation != null && json.designation != '' && json.designation != 'undefined'){
            			 		designation = json.designation;            			 		
            			 	}
            			 	var currentRate = '';
            			 	if(json.currentRate != null && json.currentRate != '' && json.currentRate != 'undefined'){
            			 		currentRate = json.currentRate;            			 		
            			 	}
            			 	var expectedRate = '';
            			 	if(json.expectedRate != null && json.expectedRate != '' && json.expectedRate != 'undefined'){
            			 		expectedRate = json.expectedRate;            			 		
            			 	}
            			 	var experienceYears = '';
            			 	if(json.experienceYears != null && json.experienceYears != '' && json.experienceYears != 'undefined'){
            			 		experienceYears = json.experienceYears;            			 		
            			 	}
            			 	var summary = '';
            			 	if(json.summary != null && json.summary != '' && json.summary != 'undefined'){
            			 		summary = json.summary;            			 		
            			 	}
							var result ='';
							result = result + '		    <div class="edit-details col-md-8 edit-candaite canprofile-popright"> <i class="fa fa-close close-div" onclick="cancelEdit();"></i>';
							result  = result + '         <form id="candidateProfileEdit_form" name="candidateProfileEdit_form" method="post">';
							result = result + '		      <div class="form-horizontal clearfix">';
							result = result + '<input type="hidden" class="form-control input-sm" name="candidateId"  value="'+candidateId+'">';
							
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="firstName" value="'+json.firstName+'" placeholder="First Name *" field-name="First Name"  data-validation="validate_Space required">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="lastName" value="'+lastName+'" placeholder="Last Name">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="email" value="'+json.email+'" placeholder="Email*" field-name="Email"  data-validation="validate_Space required validate_email">';
							result = result + '		          </div>';
							
							result = result + '<div class="col-md-6">';
							result = result + '<input type="text" class="form-control input-sm" name="mobilePhone"  value="'+mobilePhone+'" placeholder="Mobile">';
							result = result + '</div>';
							result = result + '</div>';
							
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6 custom-fields">';
							result = result + '<select class="chosen-select form-control select-bg" name="country" id="country" value="'+json.country+'" field-name="Country">';
							result = result + '</select>';
							result = result + '</div>';
							
							 
							result = result + '<input type="hidden" name="updateState" id="updateState" value="'+json.state+'" />'; 
							result = result + '<input type="hidden" name="updateCountry" id="updateCountry" value='+json.country+' />';
							result = result + '<input type="hidden" name="updateCity" id="updateCity" value='+json.city+' />';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow">';
							result = result + '<select  class="chosen-select form-control select-bg" name="state" value="'+json.state+'" id="state" field-name="State">';
							result = result + '</select>';
							result = result + '</div>';
							result = result + '</div>';
							
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6 custom-fields">';
							result = result + '<select  class="chosen-select form-control select-bg" name="city" id="city" value="'+json.city+'" field-name="City">';
							result = result + '</select>';
							result = result + '</div>';
							
							
							result = result + '<div class="col-md-6 custom-fields">';
							result = result + '<input type="text" class="form-control input-sm" name="designation"  value="'+designation+'" placeholder="Designation">';
							result = result + '</div>';
							result = result + '</div>';
							
							
							result = result + '<div class="clearfix mb10">';
							
							result = result + '<div class="col-md-6">';
							
							result = result + '<div class="input-group er ratetype">';
							
							result = result + '<div class="input-group-btn tblminwidth-75 select-field">';
			                  
							result = result + ' <select class="selct-picker" id="currentRateCurrency" name="currentRateCurrency">';
							result = result + ' <option '+(json.currencyCode == "INR" ? "selected" : '') +'><a href="#">INR</a></option>';
							result = result + '<option   '+(json.currencyCode == "AUD" ? "selected" : '') +'><a href="#">AUD</a></option>';
							result = result + ' <option  '+(json.currencyCode == "USD" ? "selected" : '') +'><a href="#">USD</a></option>';
							result = result + '  <option '+(json.currencyCode == "GPB" ? "selected" : '') +'><a href="#">GPB</a></option>';
							result = result + '  <option '+(json.currencyCode == "SGD" ? "selected" : '') +'><a href="#">SGD</a></option>';
							result = result + '<option   '+(json.currencyCode == "EUR" ? "selected" : '') +'><a href="#">EUR</a></option>'; 
			                result = result + ' </select>';
			                
			                result = result + '</div>';
							
			                result = result + '<input type="text" class="form-control input-sm" name="currentRate"  value="'+currentRate+'" placeholder="Current Rate">';
							
							result = result + '<div class="input-group-btn tblminwidth-75 ratetype select-field">';
							result = result + '<select class="selct-picker" id="currentRateType" name="currentRateType" field-name="Rate Type">';
							result = result + '<option value="">Rate Per</option>';
							result = result + ' <option '+(json.currentRateType == "Hourly" ? "selected" : '') +'>Hourly</option>';
							result = result + '<option  '+ (json.currentRateType == "Weekly" ? "selected" : '') +'>Weekly</option>';
							result = result + '<option  '+(json.currentRateType == "Bi-weekly" ? "selected" : '') +'>Bi-weekly</option>';
							result = result + '<option  '+(json.currentRateType == "SemiMonthly" ? "selected" : '') +'>Semi-Monthly</option>';
							result = result + '<option  '+(json.currentRateType == "Monthly" ? "selected" : '') +'>Monthly</option>';
							result = result + '<option  '+(json.currentRateType == "Bi-Monthly" ? "selected" : '') +'>Bi-Monthly</option>';
							result = result + '<option '+(json.currentRateType == "Yearly" ? "selected" : '') +'>Yearly</option>';
							result = result + ' </select>';
							result = result + '</div>';
							
							result = result + '</div>';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6">';
							
							result = result + '<div class="input-group er ratetype">';
							result = result + '<div class="input-group-btn tblminwidth-75 select-field">';
			                  
							result = result + ' <select class="selct-picker" id="expectedRateCurrencey" name="expectedRateCurrencey">';
							result = result + ' <option '+(json.currencyCode == "INR" ? "selected" : '') +'><a href="#">INR</a></option>';
							result = result + '<option   '+(json.currencyCode == "AUD" ? "selected" : '') +'><a href="#">AUD</a></option>';
							result = result + ' <option  '+(json.currencyCode == "USD" ? "selected" : '') +'><a href="#">USD</a></option>';
							result = result + '  <option '+(json.currencyCode == "GPB" ? "selected" : '') +'><a href="#">GPB</a></option>';
							result = result + '  <option '+(json.currencyCode == "SGD" ? "selected" : '') +'><a href="#">SGD</a></option>';
							result = result + '<option   '+(json.currencyCode == "EUR" ? "selected" : '') +'><a href="#">EUR</a></option>'; 
			                result = result + ' </select>';
			                
			                result = result + '</div>';
							
							result = result + '<input type="text" class="form-control input-sm" name="expectedRate"  value="'+expectedRate+'" placeholder="Expected Rate">';
							
							result = result + '<div class="input-group-btn tblminwidth-75 ratetype select-field">';
							result = result + '<select class="selct-picker" id="rateType" name="rateType" field-name="Rate Type">';
							result = result + '<option value="">Rate Per</option>';
							result = result + ' <option '+(json.rateType == "Hourly" ? "selected" : '') +'>Hourly</option>';
							result = result + '<option  '+ (json.rateType == "Weekly" ? "selected" : '') +'>Weekly</option>';
							result = result + '<option  '+(json.rateType == "Biweekly" ? "selected" : '') +'>Bi-weekly</option>';
							result = result + '<option  '+(json.rateType == "SemiMonthly" ? "selected" : '') +'>Semi-Monthly</option>';
							result = result + '<option  '+(json.rateType == "Monthly" ? "selected" : '') +'>Monthly</option>';
							result = result + '<option  '+(json.rateType == "BiMonthly" ? "selected" : '') +'>Bi-Monthly</option>';
							result = result + '<option '+(json.rateType == "Yearly" ? "selected" : '') +'>Yearly</option>';
							result = result + ' </select>';
							result = result + '</div>';
							
							
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
							
							
							result = result + '<div class="clearfix mb10 exprience">';
							result = result + '<div class="col-md-6">';
							result = result + '<div class="row">';
							result = result + '<div class="col-md-7">';
							result = result + '<input type="text" class="form-control input-sm" name="experienceYears"  value="'+experienceYears+'" placeholder=" Experience Years">';
							result = result + '</div>';
							
							result = result + '<div class="col-md-5 custom-fields select-arrow select-field">';
							result = result + '<select  class="input-sm form-control" name="experienceMonths"  id="experienceMonths" field-name="experienceMonths">';
							result = result + '<option value="">Select Months</option>';
							result = result  +'<option value="1" '  + (json.experienceMonths == 1 ? "selected" : '') +  '>1</option>';
							result = result  +'<option value="2"  '+ (json.experienceMonths == 2 ? "selected" : '') +' >2</option>';
							result = result  +'<option value="3"  '+ (json.experienceMonths == 3 ? "selected" : '') +' >3</option>';
							result = result  +'<option value="4"  '+ (json.experienceMonths == 4 ? "selected" : '') +' >4</option>';
							result = result  +'<option value="5"  '+ (json.experienceMonths == 5 ? "selected" : '') +' >5</option>';
							result = result  +'<option value="6"  '+ (json.experienceMonths == 6 ? "selected" : '') +' >6</option>';
							result = result  +'<option value="7"  '+ (json.experienceMonths == 7 ? "selected" : '') +' >7</option>';
							result = result  +'<option value="8"  '+ (json.experienceMonths == 8 ? "selected" : '') +' >8</option>';
							result = result  +'<option value="9"  '+ (json.experienceMonths == 9 ? "selected" : '') +' >9</option>';
							result = result  +'<option value="10"  '+ (json.experienceMonths == 10 ? "selected" : '') +' >10</option>';
							result = result  +'<option value="11"  '+ (json.experienceMonths == 11 ? "selected" : '') +' >11</option>';
							
							result = result + '</select>';
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6">';
							result = result + '<textarea class="form-control input-sm" placeholder = "Summary"  name="summary" rows="2" cols="0" > '+summary+'</textarea>';
							//result = result + ''+json.summary+'</textarea>';
							result = result + ' </div>';
							result = result + '</div>';
							result = result + '<div class="clearfix mr">';
							result = result + '<div class="pull-right">';
							result = result + '<button type="button"  onClick ="upDateCandidate('+candidateId+');" class="btn btn-sm btn-primary mr">Save</button>';
							result = result + '<button type="button" class="btn btn-sm btn-danger" onClick ="cancelEdit();">Cancel</button>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		      </div>';
							result  = result + '              </form>';
							result = result + '		    </div>';
							result = result + '		  </div>';
							
							
							$("#profileEditPopup").empty(); 
							$("#profileEditPopup").append(result); 
							
							$(".edit-details").toggleClass("edit-details-active");
							$(".collapse-div").removeClass("edit-details-active");
							
							
							$(function(){
							    var config = {
									  '.chosen-select'           : {},
									  '.chosen-select-deselect'  : {allow_single_deselect:true},
									  '.chosen-select-no-single' : {disable_search_threshold:10},
									  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
									  '.chosen-select-width'     : {width:"95%"}
									}
							    $("#country").chosen();
							    $("#state").chosen();
							    $("#city").chosen();
							   
							    
							    $("#country").on('change', function (e) {
							 	   getStates();
							    });
							   
							    $("#state").on('change', function (e) {
							 	   getCities();
							    });
							   
							    $('.chosen-select').each(function() {
										var $this = $(this);
										$this.next().css({'width': '100%'});
								  })
							});
            		 
            		 }
                }else{
         	    	         var result ="";
        		    	     result = result+"   <div class='row'>"; 
        					 result = result+"	<div class='col-lg-12'>"; 
        					 result = result+"		<div class='content-area clearfix'>"; 
        					 result = result+"			<div class='blank-pages'>";
        					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
        					 result = result+"			        <p>No Candidates Found</p>";
        					 result = result+"			 </div>";
        					 result = result+"		</div>"; 
        					 result = result+"	</div>"; 
        					 result = result+"  </div> "; 
        					$("#profileEditPopup").empty();
        				    $("#profileEditPopup").append(result); 
                        }
                 },
                   
              }); 
		 }
        getAllCountries(); 
}
</script>

<!-- <script type="text/javascript">
function disableMonths() {
    $("#experienceYears").change(function() {
   	 
        if ($(this).val() == '25+'){
            $("#experienceMonths").attr("disabled", true);
            $("#experienceMonths").val("disabled", true);
        }else
       	 $("#experienceMonths").attr("disabled", false); 
    });
}
</script> -->

<!-- Country,State,City Script Starts Here -->

 <script type="text/javascript">
function getAllCountries(){
	setTimeout(function(){ 
	var countryName = $("#updateCountry").val();
	var loginCountryName = '${loginCountryName}';
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---select---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == countryName){
            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}else if(json[i] == '${loginCountryName}' && countryName ==null && countryName ==''){
            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{ 
            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	} 
            	
            }
            $('#country').trigger("chosen:updated");
            getStates();
        },
        error: function(e){
        }
    });
	}, 100);
} 
</script>
 
<script>
function getStates(){
	
var countryName = $("#country").val();
	
	//to set existing state value(Auto populate)
	var stateName = $("#state").val();
	
	if(stateName == null || stateName == 'undefined' || stateName == ''){
		stateName = document.getElementById("updateState").value;
	}
	
	/* var countryName = $("#updateCountry").val();
	
	//to set existing state value(Auto populate)
	var stateName = $("#updateState").val(); */
	
	//end state Auto populate
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == stateName){
	            		$('#state').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#state').trigger("chosen:updated");
	            getCities();
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script>  


<script>
function getCities(){
	
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	var cityName = $("#city").val();
	if(cityName == null || cityName == 'undefined' || cityName == ''){
		cityName = document.getElementById("updateCity").value;
	}
	   /* var countryName = $("#updateCountry").val();
	
	//to set existing state value(Auto populate)
	var stateName = $("#updateState").val();
	
	//to set existing city value(Auto populate)
	var cityName = $("#updateCity").val();
	//end city Auto populate */
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == cityName){
	            		$('#city').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script> 

 <!-- <script>
function updateValues(){
	var str_array = sequentialApprover1.split(',');
	for (var i = 0; i < str_array.length; i++) {
	    str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
	}
	$("#sequentialApprover1").val(str_array).trigger("chosen:updated");
}
</script> 
 -->

    	    



<!-- Country,State,City Script Ends Here -->



<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token, candidateId
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function deleteCandidate(candidateId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  var reason = document.getElementById("rejectReason").value;
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/deleteCandidateByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&reason="+reason,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != ""){
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{
            			 location.replace(sourceleadUrl+"/candidatesList");
            		 }
                }
                 },
              }); 
}
</script>


<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate alert
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function cancelAlert(){ 
	$("#removeCandidateAlert").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>



<%-- 
     @author : Venkat Kesav
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function removeCandidateAlertMessage(candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteCandidate("+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
	result = result +"	</div>";
	$("#removeCandidateAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>



<%-- 
     @author :Sammeta David Raju
    
     1.usage     : used in reActivateCandidateAlertMessage() / candidateProfile Line number 388
     2.parameters : candidateId
--%>

<script type="text/javascript">
function reActivateCandidateAlertMessage(candidateId){
	var result = "";
	var sourceleadUrl ='${sourceleadUrl}';
	var message = confirm("You are choosing to ReActivate the Candidate");
	if (message == true) {
		var reactivate = '1';
		$.ajax({
	        type : "POST",
	         url: sourceleadUrl+"/reActivateCandidateByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&reActivate="+reactivate,        
	         success :function(data) {
	               if(data != null && data != 'undefined' && data != ""){
	            		 if( data.indexOf('Sign In') >= 0){
				    		location.replace(sourceleadUrl);
				    	 }
	            		 else if (data.indexOf('exception') >= 0) {
	            			 location.replace(sourceleadUrl+"/errorPage");
	            		 }
	            		 else{
	            			 location.replace(sourceleadUrl+"/candidatesList");
	            		 }
	                } 
	                 },
	              }); 
		
	}
	
}
</script>



<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
function candidateProfile(candidateId){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var skillsList = '${skillsList}'; 
	  
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getCandidateById?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
         success :function(data) {  
              if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{ 
							var result ="";
							
							result = result+'<div id="removeCandidateAlert" ></div>  ';
							if(json.status == '0'){
								'<sec:authorize access="hasAnyRole(\'ROLE_REACTIVATE_CANDIDATE\')">'; 
								result = result + '<div class="project_tabs_inn_head"><a href="#" class="positionRelative showInfo"><i class="glyphicon glyphicon-info-sign"></i>Terminated<span class="infoContent col-xs-12 col-md-5"> '+json.terminateReason+' </span></a><a href="javascript:void(0)" onClick="reActivateCandidateAlertMessage('+candidateId+');"><i class="glyphicon glyphicon-trash"></i>ReActivate</a> ';
								'</sec:authorize>';
							}else{
								   result = result + '<div class="project_tabs_inn_head "> <sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE\')">  <a href="#" onClick="editCandidate('+candidateId+');" class="mr addUser-org"><i class="glyphicon glyphicon-pencil"></i>Edit</a></sec:authorize> <sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_CANDIDATE\')"><a href="javascript:void(0)" onClick="removeCandidateAlertMessage('+candidateId+');"><i class="glyphicon glyphicon-trash"></i>Remove</a> </sec:authorize>';
							}
							result = result + '<div id="profileEditPopup"></div>';
							result = result +'<div class="require_wrap_details candidate_wrap_details">';	 
							result = result +'		<h1>Summary:</h1>';
							if(json.summary != null && json.summary != '' && json.summary != 'undifined'){
							result = result +'	    <textarea placeholder="Enter description" readonly>'+json.summary+'</textarea>';
							}
							else{
					           	 result = result +'   <textarea placeholder="Enter description" readonly> NO DATA</textarea>';
				    	        }
							
							result = result +'		<div class="clearfix dashboard-widget candidate-profile">                                                                                                    ';
							result = result +'		  <ul class="nav nav-tabs">                                                                                                                   ';
							result = result +'		    <li class="active"><a data-toggle="tab" href="#skills">General View</a></li>                                                              ';
							//result = result +'		    <li><a data-toggle="tab" href="#milestone">Milestone View</a></li>                                                                        ';
							result = result +'		  </ul>                                                                                                                                       ';
							result = result +'		  <div class="tab-content">                                                                                                                   ';
							result = result +'		    <div id="skills" class="tab-pane fade in active">                                                                                         ';
							result = result +'		      <div class="skills-summary">                                                                                                            ';
							result = result +'		        <div class="design-view">                                                                                                             ';
							
							result = result +'		          <!-- Select Skills -->                                                                                                              ';
							
							result = result +'		          <h2 class="education">Skills:</h2>                                                                                                  ';
							result = result +'		          <div class="select-skills">                                                                                                         ';
							result = result +'		            <div class="divskill col-md-12">                                                                                                  ';
							result = result +'		              <!-- Add skills div -->                                                                                                         ';
							
							if(window.candidateStatusGlobal == '0'){
								result = result +'		              <div class="addSkills"> <a href="#" class="btn btn-primary" title="Add Skills"> <i class="fa fa-code"></i>Add Skills</a>         ';
								result = result +'		                <div class="col-md-7 add-skills"> <i class="fa fa-close close-div"></i>                                                       ';

							}else{
								result = result +'		              <div class="addSkills"> <a href="#" class="btn-addskills" title="Add Skills" onClick ="removeSkillPopUp('+candidateId+');"> <i class="fa fa-code"></i>Add Skills </a>         ';
							result = result +'		                <div class="col-md-7 add-skills"> <i class="fa fa-close close-div" onClick="removeSkillPopUp('+candidateId+');"></i>                                                       ';
							}
							result = result +'		                  <div class="form-horizontal clearfix">                                                                                      ';
							result = result +'		                    <div class="clearfix div-padding">                                                                                        ';
							result = result +'		                      <div class="col-md-10">                                                                                                 ';
							
							result = result + '<select multiple class="chosen-select form-control" name="skillName" id="skillName" data-validation="validate_Space" data-validation-optional="true">';
			                <c:forEach var="skill" items="${skillsList}">
			              			  result = result + ' <option value="${skill.name}">${skill.name}</option>';
			                </c:forEach>
			                result = result + '</select>';
							
							result = result +'		                      </div>                                                                                                                  ';
							result = result +'		                      <div class="col-md-2">                                                                                                  ';
							result = result + '                              <button class="btn btn-sm btn-primary pull-right" type="submit" onClick="saveSkils('+candidateId+');" >Add</button>';
							result = result +'		                      </div>                                                                                                                  ';
							result = result +'		                    </div>                                                                                                                    ';
							result = result +'		                  </div>                                                                                                                      ';
							result = result +'		                </div>                                                                                                                        ';
							result = result +'		              </div>                                                                                                                          ';
							
							result = result + '<div id="candidateSkillList" class="heightScroll clearfix"></div> ';
							
							result = result +'		              <!-- Add skills div ends -->                                                                                                    ';
							
							result = result +'		             </div>       ';
							result = result +'		          </div>                                                                                                                              ';
							result = result +'		          <!-- Select Skills -->                                                                                                              ';
							result = result +'		        </div>                                                                                                                                ';
							
							//Employee Div
							result = result + '<div id="employeeSubmissionProfile"></div> '; 
							
							//Education Div
							result = result + '<div id="educationSubmissionProfile"></div> ';
							
							//Certification Div
							result = result + '<div id="certificationSubmissionProfile"></div> ';
							
							result = result +'		      </div>                                                                                                                                  ';
							result = result +'		    </div>                                                                                                                                    ';
							result = result +'		    <div id="milestone" class="tab-pane fade">                                                                                                ';
							result = result +'		      <div class="skills-graph">                                                                                                              ';
							result = result +'		        <h2 class="education">Skills Level</h2>                                                                                               ';
							result = result +'		        <textarea placeholder="Enter description">Typesetting industry.</textarea>                                                            ';
							result = result +'		      </div>                                                                                                                                  ';
							result = result +'		    </div>                                                                                                                                    ';
							result = result +'		  </div>                                                                                                                                      ';
							result = result +'		</div>';
							
							
							
							
							
							
							
							
							/* result = result +'		<h2>Skills:</h2>';
							
							result = result + '	<div class="row select-skills">';
							result = result + '    <div class="divskill col-md-8"> ';
							result = result + '      <div class="addSkills"> <a href="#" class="btn-addskills" title="Add Skills"> <i class="fa fa-code"></i>Add Skills </a>';
							result = result + '        <div class="col-md-7 add-skills" style="display:none;"> <i class="fa fa-close close-div" onClick ="removeSkillPopUp('+candidateId+');"></i>';
							result = result + '          <div class="form-horizontal clearfix">';
							result = result + '            <div class="clearfix div-padding">';
							result = result + '              <div class="col-md-10">';
							
							result = result + '<select multiple class="chosen-select form-control" name="skillName" id="skillName" data-validation="validate_Space" data-validation-optional="true">';
			                <c:forEach var="skill" items="${skillsList}">
			                
			              			  result = result + ' <option value="${skill.name}">${skill.name}</option>';
			                </c:forEach>
			                result = result + '</select>';
							result = result + '              </div>';
							result = result + '              <div class="col-md-2">';
							result = result + '                <button class="btn btn-sm btn-primary pull-right" type="submit" onClick="saveSkils('+candidateId+');" >Add</button>';   
							result = result + '              </div>';
							result = result + '            </div>';
							result = result + '          </div>';
							result = result + '        </div>';
							result = result + '      </div>';
							
							
							result = result + '<div id="candidateSkillList"></div> '; */
							
							/* result = result + '      <a href="javascript:void(0)">more <i class="fa fa-angle-right"></i></a>'; */
							result = result + '    </div>';
							result = result + '  </div>';
							$("#tab-1").empty();
							$("#tab-2").empty(); 
							$("#tab-3").empty();  
							$("#tab-4").empty();  
							$("#tab-5").empty();  
							$("#tab-6").empty();  
							$("#tab-1").append(result);  
							    
							candidateSkillsList(candidateId);
							employeeSubmissionProfile(candidateId);
							educationSubmissionProfile(candidateId);
							certificationSubmissionProfile(candidateId);
							
							$("#tab-1").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
								
								$(".btn-addskills").click(function(){
									$(".add-skills").css({"display":"block","opacity":"1"});
									$("#skillName").val('');
									 
									/* skill auto complete */
									var config = {
											  '.chosen-select'           : {},
											  '.chosen-select-deselect'  : {allow_single_deselect:true},
											  '.chosen-select-no-single' : {disable_search_threshold:10},
											  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
											  '.chosen-select-width'     : {width:"95%"}
								    }
									for (var selector in config) {
									  $(selector).chosen(config[selector]);
									}
									$('.chosen-select').each(function() {
										var $this = $(this);
										$this.next().css({'width': '100%'});
										})
												
									
									});
								$(".close-div").click(function(){
									$(".add-skills").removeClass('add-skills-active');
									});
								$(".add-skills").click(function(e){
									 e.stopPropagation();
								});
								
								 $(".addUser-org").click(function(){
									 $(".edit-details").toggleClass("edit-details-active");
									 $(".collapse-div").removeClass("edit-details-active");
								 });
								 $(".close-div").click(function(){
									 $(this).parent().toggleClass("edit-details-active");
									 });
								 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
									 e.stopPropagation();
								 });
								/*  $('html').click(function(){
								  $(".edit-details").removeClass("edit-details-active");
							     });	 */
							     $("#endDate").click(function(){
								   $(".endDate").toggle(200);
							     });
							     event.preventDefault();
								   
            		 }
              }else{
                     	 $("#candidateSideDiv").removeAttr("style");
       	    	         var result ="";
      		    	     result = result+"   <div class='row'>"; 
      					 result = result+"	<div class='col-lg-12'>"; 
      					 result = result+"		<div class='content-area clearfix'>"; 
      					 result = result+"			<div class='blank-pages'>";
      					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
      					 result = result+"			        <p>No Candidates Found</p>";
      					 result = result+"			 </div>";
      					 result = result+"		</div>"; 
      					 result = result+"	</div>"; 
      					 result = result+"  </div> "; 
      					$("#candidateSideDiv").empty();
      				    $("#candidateSideDiv").append(result); 
                      }
               },
                 
            }); 
}
</script>


<%-- 
     @author : sarath kumar.
     this script is for save Skils in Candidate.
     1.usage     :  used in editCandidate() function  line 13
     2.parameter : csrf token ,candidateId.
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
 function saveSkils(candidateId){
	 var skillName = $("#skillName").val();
	 if(skillName != null && skillName != '' && skillName !='undefined'){
		 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({  
	          type : "POST",
	          url: sourceleadUrl+"/saveSkilsBycandidateID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&skillName="+skillName,        
	          success :function(data) {
	               if(data != null && data != 'undefined' && data != ""){
	            	   candidateSkillsList(candidateId);
	            	   removeSkillPopUp(candidateId);
	               }
	        },
	          
	     }); 
	 }else{
		 
	 }
	 
 }  
</script>
 
 
 <%-- 
     @author : sarath kumar.
     this script is for remove Skill PopUp.
     1.usage     :  used in saveSkils() function line number 339
     2.parameter : candidateId  .
--%>
<script>  
function removeSkillPopUp(candidateId){           
		/* $(".add-skills").removeClass('add-skills-active'); */
		$(".add-skills").css({"display":"none","opacity":"0"});
}
</script>             
            
   
<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in editCandidate() function  line 78
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>         
<script type="text/javascript">
	function upDateCandidate(candidateId) {
	
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		
		
		 if ($('#candidateProfileEdit_form').validate(false, validationSettings)){ 
			 
			var frm = $('#candidateProfileEdit_form').serializeFormJSON();
			
			 var con = JSON.stringify(frm);
				 con = con.replace(/[{}]/g, "");
				var jsonData = encodeURIComponent(con);
				
				
			  //It will escape all the special characters
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/updateCandidate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success : function(result) {
							/* location.replace(sourceleadUrl+"/candidatesList");  */
							candidateSideBar(candidateId);
						},
					});
			return true;
		 }else{ 
				return false;
		 } 
		 $('body').on('blur','#candidateProfileEdit_form',
					function() {
						$("#candidateProfileEdit_form").showHelpOnFocus();
						$("#candidateProfileEdit_form").validateOnBlur(false,validationSettings);
					});
	} 
</script>

<script>
function cancelEdit(){
	$("#profileEditPopup").empty();
}
</script>



<%-- 
     @author : sarath Kumar.
     this script is for upDate Candidate Details .
     1.usage     :  used in candidateProfile() function  line 258
     2.parameter : csrf token candidateId.
     3.ajax url implementation in CandidateListRestController.
--%>  
<script>
function candidateSkillsList(candidateId){  
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidatesSkillsBycandidateID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != "" && data != '[]'){
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#candidateSkillList").empty();
	                	 var result = "";
                	     result = result + '      <table class="table table-responsive table-hover clearfix">';
                		 result = result + '        <thead>';
                		 result = result + '          <tr>';
                		 result = result + '            <th>Skills</th>';
                		 result = result + '            <th>Level</th>';
                		 result = result + '            <th>Experience</th>';
                		 result = result + '            <th>Action</th>';
                		 result = result + '          </tr>';
                		 result = result + '        </thead>';
                		 result = result + '        <tbody>';
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) {  
	                    	 var opt = json[i];   
	                    	    result = result + '          <tr>';
		                		result = result + '            <td><p class="form-control-static">'+opt.name+'</p></td>';
		                		
		                		if(window.candidateStatusGlobal == '0'){
		                			result = result + '            <td><select id="scale" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);" >';
		                			if(opt.scale == 'undefined'|| opt.scale==null || opt.scale== 0){
			                			 result = result + '                <option selected=""disabled="">select</option>'; 
				                	 }else{
		                			result = result + '                <option selected="" value="'+opt.scale+'" disabled="">'+opt.scale+'</option>';
				                	 }
		                		}else{
		                		
		                		result = result + '            <td><select id="scale" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);" >';
		                		result = result + '                <option selected="" value="'+opt.scale+'" disabled="">Select</option>';
		                		
		                		if(opt.scale == "Beginner"){
		                			result = result + '<option selected>Beginner</option>';
		                		}else{
		                			result = result + '<option>Beginner</option>';
		                		}
		                		if(opt.scale == "Familiar"){
		                		    result = result +'<option selected>Familiar</option>';
		                		}else{
		                			result = result +'<option>Familiar</option>';
		                		}
		                		if(opt.scale == "proficient"){
		                			result = result + '<option selected>proficient</option>';
		                		}else{
		                			result = result + '<option>proficient</option>';
		                		}
		                		if(opt.scale == "Expert"){
		                			result = result + '<option selected>Expert</option>';
		                		}else{
		                			result = result + '<option>Expert</option>';
		                		}
		                		if(opt.scale == "Master"){
		                			result = result + '<option selected>Master</option>';
		                		}else{
		                			result = result + '  <option>Master</option>';
		                		}
		                		}
		                		result = result + '              </select></td>';
		                		
		                		 if(window.candidateStatusGlobal == '0'){ 
		                			 result = result + '            <td><select id="totalExperience" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);">';
				                	 if(opt.totalExperience == 'undefined'|| opt.totalExperience==null || opt.totalExperience== 0){
			                			 result = result + '                <option selected=""disabled="">select</option>'; 
				                	 }else{
		                			 result = result + '                <option selected="" value="'+opt.totalExperience+'" disabled="">'+opt.totalExperience+'</option>'; 
		                		 }
				                }else{
		                		result = result + '            <td><select id="totalExperience" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);">';
		                		result = result + '                <option selected="" disabled="">Select</option>';
		                		if(opt.totalExperience == "0"){
		                			result = result + '<option selected>0</option>';   
		                		}else{
		                			result = result + '<option>0</option>';
		                		}
		                		if(opt.totalExperience == "1"){
		                			result = result + '<option selected>1</option>';   
		                		}else{
		                			result = result + '<option>1</option>';
		                		}
		                		if(opt.totalExperience == "2"){
		                			result = result + '<option selected>2</option>';   
		                		}else{
		                			result = result + '<option>2</option>';
		                		}
		                		if(opt.totalExperience == "3"){
		                			result = result + '<option selected>3</option>';   
		                		}else{
		                			result = result + '<option>3</option>';
		                		}
		                		if(opt.totalExperience == "4"){
		                			result = result + '<option selected>4</option>';   
		                		}else{
		                			result = result + '<option>4</option>';
		                		}
		                		if(opt.totalExperience == "5"){
		                			result = result + '<option selected>5</option>';   
		                		}else{
		                			result = result + '<option>5</option>';
		                		}
		                		if(opt.totalExperience == "6"){
		                			result = result + '<option selected>6</option>';   
		                		}else{
		                			result = result + '<option>6</option>';
		                		}
		                		}
		                		result = result + '              </select></td>';
		                		
		                		 if(window.candidateStatusGlobal == '0'){ 
				                result = result + '            <td><div class="text-center"><a href="javascript:void(0)"> <i class="fa fa-trash"></i></a></div></td>';
		                		 }else{
		                		result = result + '            <td><div class="text-center"><a onclick="removeSkillById('+candidateId+', '+opt.id+');" href="javascript:void(0)"> <i class="fa fa-trash"></i></a></div></td>';
		                		 }
		                		result = result + '          </tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateSkillList").append(result); 
			    	} 
               }else{
	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Skills Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#candidateSkillList").empty();
				    $("#candidateSkillList").append(result); 
               }
        },
          
     }); 
}
</script>



<%-- 
     @author : Venkat Kesav
     this script is for Remove Candidate Skill.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token, candidateId, Skill Name
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function removeSkillById(candidateId, skillId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/removeSkillBySkillId?${_csrf.parameterName}=${_csrf.token}&skillId="+skillId,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != ""){
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{
            			 candidateSkillsList(candidateId);
            		 }
                }
                 },
		}); 
}
</script>
    
     
          
<%-- 
     @author : david sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in upDateCandidate() function  line 379
--%>         
<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
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






<%-- 
     @author : sarath kumar.
     this script is for inline edit saving .
     1.usage     :  used in  skillList(); 
     2.parameter : csrf token ,name , value.
     3.ajax url implementation in CandidateListRestController.
--%>         
<script type="text/javascript">
	function fieldUpdateCandidate(candidateSkillId,local) {
			var sourceleadUrl = '${sourceleadUrl}'; 
			var value = local.value;
			var name = local.id;
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/fieldUpdateCandidate?${_csrf.parameterName}=${_csrf.token}&candidateSkillId="+candidateSkillId+"&name="+name+"&value="+value,
						success : function(result) {
							 
						},
			}); 	 	
	} 
</script>

           
           

<script>
	function employeeSubmissionProfile(candidateId) {
		var sourceleadUrl = '${sourceleadUrl}';
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId=" + candidateId,
					success : function(data) {
						if (data != null && data != 'undefined' && data != "" && data != '[]') {
							if (data.indexOf('Sign In') >= 0) {
								location.replace(sourceleadUrl);
							} else if (data.indexOf('exception') >= 0) {
								location.replace(sourceleadUrl + "/errorPage");
							} else {
								$("#employeeSubmissionDetailsList").empty();
								var result = "";
								result = result +'        <div class="clearfix design-view">';
								result = result +'          <h2 class="education"><i class="fa fa-black-tie"></i>&nbsp;&nbsp;Employers</h2>';
								result = result +'          <div class="row">';
								result = result +'            <div class="col-md-12">';
								result = result +'              <ul class="list-unstyled personal-info">';
								var json = JSON.parse(data);
								for (var i = 0; i < json.length; i++) {
									var opt = json[i];
									if (opt.name != null && opt.name != '' && opt.name != 'undefined') {
										name = opt.name;
									} else {
										name = " ";
									}
									if (opt.designation != null	&& opt.designation!='' && opt.designation != 'undefined') {
										designation = opt.designation;
									} else {
										designation = " "; 
									}
									
									
									 if (opt.startMonth != null && opt.startMonth != '' && opt.startMonth != 'undefined') {
										startMonth=   opt.startMonth +' - ';
									}else{
										startMonth = " ";
									}
									
									if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
										startYear=   opt.startYear  +' TO ';
									}else{
										startYear = " ";
									}
									
									if (opt.endMonth != null	&& opt.endMonth != '' && opt.endMonth != 'undefined') {
										endMonth = opt.endMonth +' - ';  
									} else {
										endMonth = " ";
									}
									
									if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
										endYear = opt.endYear;  
									} else {
										endYear = " ";
									}
									result = result +'   <li>';
									result = result +'   <div class="clearfix">';
									result = result +' <h3>'+name+'</h3>';
									result = result +' <p>'+designation+'</p>';
									result = result +' <p>';
								    result = result + startMonth +''+startYear +' '+endMonth +''+endYear; 
									result = result +'  </p>';
									result = result +'  </div>';
									result = result +'</li>';
								}
								result = result +'              </ul>';
								result = result +'            </div>';
								result = result +'          </div>';
								result = result +'        </div>';
								$("#employeeSubmissionProfile").append(result);
							}
						}
					},

				});
	}
</script>

<script>
function educationSubmissionProfile(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
        	  if (data != null && data != 'undefined' && data != "" && data != '[]') {
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#educationSubmissionDetailsList").empty();
	                	 var result = "";
	                	 
	                	 
	                	     result = result +'        <div class="clearfix design-view">';
							result = result +'          <h2 class="education"><i class="fa fa-graduation-cap"></i>&nbsp;&nbsp;Education</h2>';
							result = result +'          <div class="row">';
							result = result +'            <div class="col-md-12">';
							result = result +'              <ul class="list-unstyled personal-info">';
							
							
							 var json = JSON.parse(data);
		                     for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i];  
		                    	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
	                    		        EducationName = opt.name;
		                    	    }else{
		                    	    	EducationName = " ";
		                    	    }
			                     if(opt.specialization != null && opt.specialization != '' && opt.specialization != 'undefined'){
			                    	 specialization= '(' + opt.specialization +')';
		                    	  }else{
		                    		  specialization= " ";
		                       	 }
			                     if(opt.institute != null && opt.institute != '' && opt.institute != 'undefined'){
			                    	 institute = opt.institute;
		                    	 }else{
		                    		 institute = " ";
		                    	 }
			                     if(opt.grade != null && opt.grade != '' && opt.grade  != 'undefined'){
			                    	 Educationgrade= opt.grade + ' , ';
		                    	 }else{
		                    		 Educationgrade = ""; 
		                    	 }
		                    	 if(opt.gradeType != null && opt.gradeType != '' && opt.grgradeTypeade  != 'undefined'){
		                    		 EducationgradeType  = opt.gradeType + ' - ';
				                 }else{
				                	 EducationgradeType = " "; 
				                 }
		                    	 if(opt.address != null && opt.address != '' && opt.address != 'undefined'){
		                    		 Educationaddress = ' , ' +opt.address;
		                    	 }else{
		                    		 Educationaddress = " ";
		                    	 }
		                    	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
		                    		 EducationstartDateStr = opt.startDateStr +' TO ';
		                    	 }else{
		                    		 EducationstartDateStr = " ";
		                    	 }
		                    	 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){ 
		                    		 EducationendDateStr = opt.endDateStr;
		                    	 }else{
		                    		 EducationendDateStr = " ";
		                    	 }
			                        result = result +'                <li>';
									result = result +'                  <div class="clearfix">';
		                    	    result = result +'                    <h3>'+EducationName+' <span class="small text-muted">&nbsp;'+specialization+'</span></h3>';
		                    	    result = result +'                    <p>'+EducationgradeType + ' '+Educationgrade+' '+institute+' '+Educationaddress+'</p>';
									result = result +'                    <p>'+EducationstartDateStr+' '+EducationendDateStr+'</p>';
									result = result +'                  </div>';
									result = result +'                </li>';
		                     }
							
							
							result = result +'              </ul>';
							result = result +'            </div>';
							result = result +'          </div>';
							result = result +'        </div>';
	                     $("#educationSubmissionProfile").append(result); 
			    	} 
        	  }
        },
          
     }); 
}
</script> 

<script>
function certificationSubmissionProfile(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
        	  if (data != null && data != 'undefined' && data != "" && data != '[]') {
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#certificationSubmissionProfile").empty();
	                	 var result = "";
	                	 
	                	 result = result +'        <div class="clearfix design-view">';
							result = result +'          <h2 class="education"><i class="fa fa-certificate"></i>&nbsp;&nbsp;Certification</h2>';
							result = result +'          <div class="row">';
							result = result +'            <div class="col-md-12">';
							result = result +'              <ul class="list-unstyled personal-info">';
							 var json = JSON.parse(data);
							for (var i = 0; i < json.length; i++) { 
		                    	var opt = json[i];  
		                    	if(opt.score != null && opt.score != 'undefined' && opt.score != ""){
				            		score =' ( ' + opt.score;
				            	}else{
				            		score = " ";
				            	}
				            	if(opt.scoreType != null && opt.scoreType != 'undefined' && opt.scoreType != ""){
				            		scoreType = opt.scoreType +' ) ';
				            	}else{
				            		scoreType = " ";
				            	}
				            	if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != ""){
				            		candidatevalidFromDateToString = opt.candidatevalidFromDateToString;
				            	}else{
				            		candidatevalidFromDateToString = " ";
				            	}
				            	if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
				            		candidatevalidToDateToString = opt.candidatevalidToDateToString;
				            	}else{
				            		candidatevalidToDateToString = " ";
				            	}
				            	
								result = result +'                <li>';
								result = result +'                  <div class="clearfix">';
								result = result +'                    <h3>'+opt.name+' <span class="small text-muted">&nbsp;'+score+'  '+scoreType+'</span></h3>';
								result = result +'                    <p>'+opt.provider+' </p>';
								result = result +'                    <p> '+candidatevalidFromDateToString+' TO '+candidatevalidToDateToString+'</p>';
								result = result +'                  </div>';
								result = result +'                </li>';
							}
							result = result +'              </ul>';
							result = result +'            </div>';
							result = result +'          </div>';
							result = result +'        </div>';
							
							$("#certificationSubmissionProfile").append(result); 
			    	} 
        	  }
        },
          
     }); 
}
</script>
<script>
$('.selectpicker').selectpicker('refresh');
</script>
           
  