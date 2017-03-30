<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<link href="resources/css/bootstrap-colorpicker.min.css" rel="stylesheet">
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/bootstrap-colorpicker.min.js"></script> 
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.selectChosen .chosen-container {
width: 100% !important;
}
</style>

<!-- end files for autocomplete with tagit -->
<script type="text/javascript">
window.onload = function () {
	getAllCountries();
	colourpicker();
 };
</script>

<script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','resources/images/office-image.jpg');
	}
</script>

<script>
	$(document).ready(function() {
		document.getElementById("orgSettings_form").reset();
	});
</script>

<script type="text/javascript">
  var loadFile = function(event) {
    var output = document.getElementById('orgImage');
    
    var uploadedImage = document.getElementById('uploadedImage').value;
    var sizeinbytes = document.getElementById('uploadedImage').files[0].size;
    document.getElementById("errorImage").style.display="none";
    
    var extension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1).toLowerCase();
    
    if(extension != 'jpg'){
    	output.src = URL.createObjectURL(event.target.files[0]);
    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
 		var msg = "."+extensionName+" extension not allowed";
 		alertMessage(msg);
 		document.getElementById("errorImage").style.display=""; 
 		$("#save_button").attr("disabled", "disabled");
    	
    }else if(sizeinbytes > 200000){
    	output.src = URL.createObjectURL(event.target.files[0]);
 		var msg = "Image size should be less than 200 KB";
 		alertMessage(msg);
 		document.getElementById("errorImage").style.display=""; 
 		$("#save_button").attr("disabled", "disabled");
    	
    }else{
    	output.src = URL.createObjectURL(event.target.files[0]);
    	$("#save_button").removeAttr("disabled")
    }
    
  };
</script>

<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelOrgImg();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelOrgImg();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}

</script>

<script type="text/javascript">
function deleteImage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteOrgImage();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelOrgImg();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function deleteOrgImage(){
	var sourceleadUrl ='${sourceleadUrl}';
	
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteOrgImage",
	        success: function(data) {
	        	 var result = data;
     		if(result == "success"){
     			location.replace(sourceleadUrl+"/orgSettings"); 
        	 }
	        
	       },
	        
     }); 
}
</script>

<script type="text/javascript">
function cancelOrgImg(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<body>



<!-- Content Area -->
  <!-- Tab for Profile setting-area starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Org Settings</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Account Settings</div>
    </section>
    <div class="content-area clearfix">
      <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
      <div id="loading"></div> 
      <div id="signatureLoading"></div>
        <!-- Tab Pills navbar -->
      <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#orgsetting">Settings</a></li>
        <li><a data-toggle="tab" href="#emailSign" onclick="loadSignature();">Email Signature</a></li>
        <li><a data-toggle="tab" href="#configCoolingOff" onclick="configCoolingOff();">Configure</a></li>
      </ul>
      <!-- Tab Pills navbar -->
      <div class="tab-content">
        <div id="orgsetting" class="tab-pane fade in active">   
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab org-settings-area">
		  <div class="tab-content panel-body">
			<div class="clearfix">
			  <form:form class="login-form clearfix"  id="orgSettings_form"  name="orgSettings_form" method="post" > 
			  
			  <div class="col-xs-12 col-sm-3 col-md-3">
				 <div class="well well-sm text-center">
				  <div class="profile-img-container" style="position:relative;">
				   <img id="orgImage" onerror="predefineOrgImage(this);" src="<c:url value='/displayOrgImage/${SourceLeadWeb_orgCode}'/>" alt="Org Image" class="comp-image img-responsive">
				  <sec:authorize access="hasAnyRole('ROLE_VIEW_EDIT_ORG_SETTINGS')">
				  <div class="btn-group uploadfile">
				  <button type="button" class="btn btn-sm btn-primary">
				  <i class="fa fa-upload"></i></button>
				   <label class="btn btn-sm btn-primary">
                    Upload / Change<input type="file" name="file" id="uploadedImage"  onchange="loadFile(event)" style="visibility:hidden;height:0;width:10px;overflow:hidden" >
                  </label>
 				  <c:if test="${!empty SourceLeadWeb_org.fileContentType}">
	                  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage()">
					  <i class="fa fa-trash"></i></button>
				  </c:if>
				  </div>
				  </sec:authorize>
				  </div>
				  </div>
				  <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>
				  <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not allowed</p>
				  <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>
			  </div>
			  <div id="popUp" ></div>
			  			
				<div class="col-xs-12 col-sm-9 col-md-9">
					<div class="row">
						<div class="col-md-7">
							<div class="form-group">
							<label class="col-md-3">Company<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9">
							<input type="text" id="companyName" name="companyName" class="form-control"  field-name="Company" data-validation="required validate_Space validate_length length2-250" onblur="validateCompanyName();"  value="${SourceLeadWeb_orgName}" />
							</div>
							</div>
							<div class="form-group">
							<label class="col-md-3">Website</label>
							<div class="col-md-9">
							<input type="text" readonly="readonly" class="form-control" value="${SourceLeadWeb_website}">
							</div>
							</div>
								
							<div class="form-group">
								<label class="col-md-3">Country</label>
								<div class="col-md-9">
								 <select class="chosen-select form-control select-bg" name="country" id="country" field-name="Country" readonly="readonly" >
				                 </select>
								</div>
							</div>
							 <input type="hidden" id="existingCountry" value="${SourceLeadWeb_country}"/>
							
							 <div class="form-group">
								<label class="col-md-3">State</label>
								<div class="col-md-9">
								     <select  class="chosen-select form-control" name="state" id="state" field-name="State"  > </select>
								</div>
							 </div>
							 <input type="hidden" id="existingState" value="${SourceLeadWeb_orgLocations.state}">
							
							<div class="form-group">
								<label class="col-md-3">City</label>
								<div class="col-md-9">
								     <select  class="chosen-select form-control" name="city" id="city" field-name="City"  ></select>
								</div>
							</div>
							
							<div class="form-group">
							<label class="col-md-3">Address</label>
							<div class="col-md-9">
							<input type="text" id="address" name="address" class="form-control"  field-name="Address" data-validation="validate_Space" data-validation-optional="true" value="${SourceLeadWeb_address}" />
							</div>
							</div>
							
							<div class="form-group">
				            <label class="col-md-3">Time Zone<span class="text-danger">&nbsp;*</span></label>
				            <div class="col-md-9">
								 <select class="chosen-select form-control" data-placeholder="Time Zone" field-name="Timezone" data-validation="required" id="timeZone" name="timeZone">
									 <option value="" >Select Timezone</option>
									 <c:forEach items="${timezoneList}" var="timezone">
			                            <c:choose>
			                                <c:when test="${timezone.timeZoneId eq orgLocations.timezone.timeZoneId}">
			                                    <option value= "${timezone.id}" selected="selected">${timezone.timeZoneId}</option>
			                                </c:when>
			                                <c:otherwise>
			                                    <option value="${timezone.id}">${timezone.timeZoneId}</option>
			                                </c:otherwise>
			                            </c:choose>
			                         </c:forEach>
			                     </select>
			                     <span class="hidden">Time Zone</span>
							</div>
							</div>
							
							<input type="hidden" id="existingCity" value="${SourceLeadWeb_orgLocations.city}" >
				   </div>
				
					<div class="col-md-5">
						<div class="form-group">
							<label class="col-md-3">Zip</label>
							<div class="col-md-9">
							<input type="text"  class="form-control" name="zipcode" field-name="Zip" data-validation="validate_int validate_Space validate_length length5-10" data-validation-optional="true" value="${SourceLeadWeb_orgLocations.zipCode}" />
							</div>
						</div>
						<div class="form-group mt">
							<label class="col-md-3">Phone</label>
							<div class="col-md-9">
							<input type="text"  class="form-control" name="phone" field-name="phone" data-validation="validate_phone validate_Space validate_length length9-20" data-validation-optional="true" value="${SourceLeadWeb_orgLocations.phone}" />
							</div>
						</div>
						<div class="form-group mt">
							<label class="col-md-3">Fax</label>
							<div class="col-md-9">
							<input type="text"  class="form-control" name="fax" id="faxId" field-name="fax" data-validation="validate_phone validate_Space validate_length length9-20" data-validation-optional="true" value="${SourceLeadWeb_orgLocations.fax}" />
							</div>
						</div>
						
						<div class="form-group comp-textarea">
						  <label class="col-md-3">About</label>
							<div class="col-md-9">
							   <textarea rows="6" name="aboutCompany" field-name="About" data-validation-optional="true" data-validation="validate_length length5-5000" class="form-control">${SourceLeadWeb_org.orgDescription}</textarea>
							</div>
						</div>
							
						</div>
						</div>
					<div class="clearfix">
					<div class="pull-right">
					<sec:authorize access="hasAnyRole('ROLE_VIEW_EDIT_ORG_SETTINGS')">
					<input type="submit" value="Save" id="save_button" class="btn btn-primary mr">
					</sec:authorize>
					<input type="button" value="Cancel" class="btn btn-default" onClick="self.location='/sourcelead/dashboard/${SourceLeadWeb_orgLocationId}'">
					</div>
					</div>
					</div>
					</form:form>
				</div>

			
			</div>
			</div>
		   </div>
		</div>
		</div>
		
		<div id="emailSign" class="tab-pane fade form-horizontal"> 
                <!-- Tab for Profile setting-area -->
                    <div class="accountsettings-tab">
                        <div class="tab-content panel-body">
                            <div class="clearfix mb">
                                <div class="pull-left">
                                    <h4 id="SignatureHeading">Create Default Signature</h4>
                                    <div id="signLoading"></div>
                                    <div id="restorePopUp"></div>
                                </div>
                                <div class="pull-right">
                                     <button type="reset" id="reset" class="btn btn-default btm-sm" data-toggle="tab" href="#emailSign" onclick="restoreSignature();"><i class="fa fa-refresh"></i>
                                         &nbsp;&nbsp;Restore</button>
                                         
                                        <button type="reset" class="btn btn-default btm-sm" id="settings"  data-toggle="tab" href="#emailSign" onclick="loadSignature();"><i class="fa fa-gears"></i>
                                         &nbsp;&nbsp;Settings</button>
                                     
                                     <button type="reset" class="btn btn-default btm-sm" onclick="getSignaturePreview();"><i class="fa fa-eye"></i>
                                         &nbsp;&nbsp;Preview</button>
                                </div>
                            </div>
                			<div id="campaignSignatrue"></div>            
            			</div>
        			</div>
    		</div>
    
    		<div id="configCoolingOff" class="tab-pane fade form-horizontal"> 
                <!-- Tab for Profile setting-area -->
                    <div class="accountsettings-tab">
                        <div class="tab-content panel-body">
                            <div class="clearfix mb">
                                <div class="pull-left">
                                    <h4 id="coolingOffHeading">Configure Cooling-Off Period</h4>
                                    <div id="coolingOffLoading"></div>
                                </div>
                            </div>
                			<div id="coolingOffPeriodDiv"></div>            
            			</div>
        			</div>
    			</div>
    
  	</div>
  </div>
		</div>
		
		
			<!-- Tab for Profile setting-area ends -->
  </section>
  <!-- Tab for Profile setting-area starts Ends -->
  <!-- Content Area Ends-->

</body>

<script>
$("#faxId").keyup(function () {
    this.value = this.value.replace(/ /g, "-");
});
</script>

<script type="text/javascript">
function validateCompanyName(){
	var con = document.getElementById("companyName").value;
	var company = document.getElementById("companyName").value;
	var companyName = encodeURIComponent(con);
	var sourceleadUrl ='${sourceleadUrl}';
	 if(companyName != null && companyName != ''){
		 $("#save_button").attr("disabled", "disabled");
		 $( "#save_button" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/validateCompanyName?companyName="+companyName,
			        success: function(data) {
			        	 var result = data;
			        		if(result == "Company Duplicated") {
		        			//Existed company
		    	    		$('#companyNameDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#companyName').attr('class','error form-control');
		    	    		$("input#companyName").closest('b').after("<p class='jquery_form_error_message'> "+company+" "+result+"</p>");
		    				$('input#companyName').attr('record-exist','yes');
		    				$('input#companyName').attr('record-exist-errorMsg',company+' '+result+''); 
		    				$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
		    				$("#save_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New company
			        		$("#save_button").removeAttr("disabled");
				    		$('input#companyName').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyNameDiv').find('p.jquery_form_error_message').remove();
							$('input#companyName').attr('class','valid form-control');
							$( "#save_button" ).removeAttr("style");
							$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
							$( "#save_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
			        	}  
			       },
			        
		   }); 
		 
	 }
}
</script>

    

<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#orgSettings_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
	          		var frm = $('#orgSettings_form').serializeFormJSON();
	            	var con = JSON.stringify(frm);
				    con = con.replace(/[{}]/g, "");
				  //It will escape all the special characters
    		        var jsonData = encodeURIComponent(con);
				    
	          		
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		var form = $('#orgSettings_form')[0];
	           		var formData = new FormData(form);
	           		var uploadedImage = document.getElementById('uploadedImage').value;
             		var imageExtension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
	           		
             		if(imageExtension == 'gif' || imageExtension == 'png' || imageExtension == 'svg'){
		        		document.getElementById("errorImage").style.display=""; 
             		}else{
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	          		
	                  $.ajax({
	              	      type: "POST",
	              	      data: formData,
	              	      contentType : 'application/json; charset=utf-8',
	              	      enctype: 'multipart/form-data',
	          	          processData: false,
	          	          contentType:false,
	              	      dataType : 'json',
	              	      
	                    	  url: sourceleadUrl+"/saveOrgSettings?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
	                      	success: function(result) {
	  			        	 if((result == 'home')){
	  			        		location.replace(sourceleadUrl+"/orgSettings");  
	  			        	}
	  			        },  
	                  });
             		}
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#orgSettings_form').showHelpOnFocus();
         $('#orgSettings_form').validateOnBlur(false, validationSettings); 
            
     
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
function getAllCountries(){
		var countryName = $("#existingCountry").val();
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/sourceLeadCountryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#country').empty();
	        	$('#country').append('<option value="">---select country---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == countryName){
	            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}else{ 
	            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            	
	            }
	            $('#country').attr("disabled", true); 
	            $('#country').trigger("chosen:updated");
	            getStates();
	        },
	        error: function(e){
	        }
	    });
} 
</script>


<script>
function getStates(){
	var countryName = $("#country").val();
	
	//to set existing state value(Auto populate)
	var stateName = $("#state").val();
	if(stateName == null || stateName == 'undefined' || stateName == ''){
		stateName = document.getElementById("existingState").value;
	}
	//end state Auto populate
	
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select state---</option>');
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
    	$('#state').append('<option value="">---select state---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select city---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCities(){
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	//to set existing city value(Auto populate)
	var cityName = $("#city").val();
	if(cityName == null || cityName == 'undefined' || cityName == ''){
		cityName = document.getElementById("existingCity").value;
	}
	//end city Auto populate
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select city---</option>');
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
    	$('#city').append('<option value="">---select city---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>

<script type="text/javascript">
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
</script>

<script>
function configCoolingOff(){
	$("#coolingOffPeriodDiv").empty(); 
	var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCoolingOffPeriod?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(data) {
		        	var json = JSON.parse(data);
					var result = "";
					var coolingOffPeriod = 0;
					if(null != json.coolingOffPeriod && '' != json.coolingOffPeriod && 'undefined' != json.coolingOffPeriod){
						coolingOffPeriod = json.coolingOffPeriod;
					}
					result = result + '<form id="coolingOff_form"  name="coolingOff_form" method="post">'; 
					
					result = result + '<div class="form-group">';
					result = result + '<label class="col-md-3">Cooling Off Period</label>';
					result = result + '<div class="col-md-4 select-field selectChosen">';
					result = result + '<select  class="form-control" name="coolingOff" id="coolingOff" field-name="CoolingOff">';
					result = result + '		<option value="'+coolingOffPeriod+'" selected>'+coolingOffPeriod+'</option>';
					result = result + '		<option value="0 Days">0 Days</option>';
                    result = result + '		<option value="1 Week">1 Week</option>';
                    result = result + '		<option value="2 Weeks">2 Weeks</option>';
                    result = result + '		<option value="3 Weeks">3 Weeks</option>';
                    result = result + '		<option value="1 Month">1 Month</option>';
                    result = result + '		<option value="2 Months">2 Months</option>';
                    result = result + '		<option value="3 Months">3 Months</option>';
                    result = result + '		<option value="4 Months">4 Months</option>';
                    result = result + '		<option value="5 Months">5 Months</option>';
                    result = result + '		<option value="6 Months">6 Months</option>';
                    result = result + '		<option value="7 Months">7 Months</option>';
                    result = result + '		<option value="8 Months">8 Months</option>';
                    result = result + '		<option value="9 Months">9 Months</option>';
                    result = result + '		<option value="10 Months">10 Months</option>';
                    result = result + '		<option value="11 Months">11 Months</option>';
                    result = result + '		<option value="1 Year">1 Year</option>';
					result = result + '</select>';
					result = result + '</div>';
					result = result + '</div>';
					
					result = result + '<div class="pull-right">';
					result = result + '<input type="submit" value="Save" id="saveCoolingOff_button" class="btn btn-primary mr">';
					result = result + '</div>';
					result = result + ' </form>';
					
					$("#coolingOffPeriodDiv").append(result);
					
					 var config = {
							  '.chosen-select'           : {},
							  '.chosen-select-deselect'  : {allow_single_deselect:true},
							  '.chosen-select-no-single' : {disable_search_threshold:10},
							  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
							  '.chosen-select-width'     : {width:"95%"}
							}
					       $('.chosen-select').each(function() {
								var $this = $(this);
								$this.next().css({'width': '100%'});
						  })
						  $('.chosen-container').css({'width': '100%'});
					 
				       $("#coolingOff").chosen();
				       $("#coolingOff").trigger('chosen:update');
		        },
		 });
}
</script>

<script>
function loadSignature(){
	
	
	$("#campaignSignatrue").empty(); 
	$("#SignatureHeading").html("Create Default Signature");
	document.getElementById("reset").style.display="";
	document.getElementById("settings").style.display="none";

	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		 
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getOrgSignature?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(data) {
		        	
		        	 var json = JSON.parse(data);
					 var result = "";
					
					result = result + '<form id="signature_form"  name="signature_form" method="post">'; 
					result = result + '<input type="hidden" name="signatureListSize" value="'+json.signatureList.length+'">';
					for (var i=0; i<json.signatureList.length; i++)
 					{
						var opt = json.signatureList[i];
						var name = opt.labelName.replace(/\s+/g, '');
						var checked = "";
						var readonly = "";
						
						var boldClass = "";
						var italicClass = "";
						var underlineClass = "";
						var boldValue = "";
						var italicValue = "";
						var underlineValue = "";
						var boldChecked = "";
						var italicChecked = "";
						var underlineChecked = "";

						if(null != opt.fontStyle){
							 var fontStyle = opt.fontStyle.split(',');
							  for(var fn = 0; fn < fontStyle.length; fn++) {
								 if(fontStyle[fn] == "Bold"){
									 boldClass = "btn-active";
									 boldValue = "value = 'Y'";
									 boldChecked = "checked";
								 }
								  
								 if(fontStyle[fn] == "Italic"){
									 italicClass = "btn-active";
									 italicValue = "value = 'Y'";
									 italicChecked = "checked";
								 }
								 
								 if(fontStyle[fn] == "Underline"){
									 underlineClass = "btn-active";
									 underlineValue = "value = 'Y'";
									 underlineChecked = "checked";
								 }
							 } 
						}
						
						if(opt.status == '1'){
							checked = "checked"+" "+"value = 'Y'";
						}else{
							checked = "value = 'N'";
						}
						
						var labelText = "";
						
						if(opt.labelName == 'Sign Off'){
							labelText = opt.labelText;
						}else{
							
							if(opt.labelName == 'Name'){
								if('${SourceLeadWeb_user.lastName}' != null){
									labelText = '${SourceLeadWeb_user.firstName}'+" "+'${SourceLeadWeb_user.lastName}'
								}else{
									labelText = '${SourceLeadWeb_user.firstName}'
								}
								
							}else if(opt.labelName == 'Designation'){
								if('${SourceLeadWeb_designation}' != null){
									labelText = '${SourceLeadWeb_designation}';
								}
								
							}
							else if(opt.labelName == 'Email'){
								if('${SourceLeadWeb_user.email}' != null){
									labelText = '${SourceLeadWeb_user.email}';
								}
								
							}
							
							else if(opt.labelName == 'Mobile'){
								if('${SourceLeadWeb_user.mobilePhone}' != null){
									labelText = '${SourceLeadWeb_user.mobilePhone}';
								}
								
							}
							
							else if(opt.labelName == 'Phone'){
								if('${SourceLeadWeb_orgLocations.phone}' != null){
									labelText = '${SourceLeadWeb_orgLocations.phone}';
								}
								
							}
							
							
							else if(opt.labelName == 'Company Name'){
								if("${SourceLeadWeb_orgName}" != null){
									labelText = "${SourceLeadWeb_orgName}";
								}
								
							}
							
							else if(opt.labelName == 'Address'){
								if('${SourceLeadWeb_address}' != null){
									labelText = '${SourceLeadWeb_address}';
								}
								
							}
							
							else if(opt.labelName == 'Fax'){
								if('${SourceLeadWeb_orgLocations.fax}' != null){
									labelText = '${SourceLeadWeb_orgLocations.fax}';
								}
								
							}
							
							else if(opt.labelName == 'Website'){
								if('${SourceLeadWeb_website}' != null){
									labelText = '${SourceLeadWeb_website}';
								}
								
							}
							
							readonly = "readonly";
						}
						
							
						result = result + '<div class="form-group">';
						result = result + '<label class="control-label col-md-2"><strong>'+opt.labelName+'</strong></label>';
						result = result + '<div class="col-md-10 colpadding">';
						result = result + '<div class="row">';
						if(opt.labelName != 'Logo'){
						
						if(opt.labelName == "Sign Off"){
							result = result + '<div class="col-md-3" title="Text Box"><input type="text" name=\''+name+'\' field-name="Sign Off" data-validation="required validate_Space validate_length length2-100" value="'+labelText+'" class="form-control input-sm" placeholder="Text Box"></div>';
						}else{
							result = result + '<div class="col-md-3" title="Text Box"><input type="text" '+readonly+' value="'+labelText+'" class="form-control input-sm" placeholder="Text Box"></div>';
						}
						
						result = result + '<div class="col-md-2 select-field">';
						result = result + '<select name="fontFamily'+opt.id+'" class="form-control input-sm">';
						for (var j=0; j<json.fontFamilyList.length; j++)
	 					{
							var fontFamily = json.fontFamilyList[j];
							result = result + '<option value="'+fontFamily+'"';
							if(opt.fontFamily == fontFamily){
								result = result+' selected';
							}
							result = result + '>'+fontFamily+'</option>';
	 					}
						result = result + '</select>';
						result = result + '</div>';
						
						result = result + '<div class="col-md-2 select-field">';
						result = result + '<select name="fontsize'+opt.id+'" class="form-control input-sm">';
						for (var k=0; k<json.fontFamilyList.length; k++)
	 					{
							var fontSize = json.fontSizeList[k];
							result = result + '<option value="'+fontSize+'"';
							
							if(opt.fontSize == fontSize){
								result = result+' selected';
							}
							
							result = result + '>'+fontSize+'</option>';
	 					}
						result = result + '</select>';
						result = result + '</div>';
						result = result + '<div class="col-md-2" title="Color Picker">';
						result = result + '<div id="cp'+opt.id+'"  class="input-group colorpicker-component">';
						result = result + '<input type="text" name="fontcolor'+opt.id+'" value="'+opt.fontColor+'" readonly="readonly" class="form-control" />';
						result = result + '<span class="input-group-addon"><i></i></span>';
						result = result + '</div>';
						result = result + '</div>';
						result = result + '<div class="col-md-2 email-button">';
						result = result + '<div class="pull-left positionRelative">';
						result = result + '<label class="btn-styles">';
						result = result + '<input type="checkbox" name="bold'+opt.id+'" id="bold'+opt.id+'" '+boldChecked+'  '+boldValue+' onclick="getboldStyle('+opt.id+')" >';
						result = result + '<i class="btn btn-default btn-sm fa fa-bold '+boldClass+'" id="boldActive'+opt.id+'" ></i>';
						result = result + '</label>';
						result = result + '</div>';
						result = result + '<div class="pull-left positionRelative">';
						result = result + '<label class="btn-styles">';
						result = result + '<input type="checkbox" name="underline'+opt.id+'" id="underline'+opt.id+'" '+underlineChecked+' '+underlineValue+' onclick="getunderlineStyle('+opt.id+')" >';
						result = result + '<i class="btn btn-default btn-sm fa fa-underline '+underlineClass+'" id="underlineActive'+opt.id+'"></i>';
						result = result + '</label>';
						result = result + '</div>';
						result = result + '<div class="pull-left positionRelative">';
						result = result + '<label class="btn-styles">';
						result = result + '<input type="checkbox" name="italic'+opt.id+'" id="italic'+opt.id+'" '+italicChecked+' '+italicValue+' onclick="getitalicStyle('+opt.id+')" >';
						result = result + '<i class="btn btn-default btn-sm fa fa-italic '+italicClass+'" id="italicActive'+opt.id+'"></i>';
						result = result + '</label>';
						result = result + '</div>';
						result = result + '</div>';
						
						}else{
							result = result + '<div class="col-md-3" title="Text Box"><img id="orgImage" onerror="predefineOrgImage(this);" src="<c:url value="/displayOrgImage/${SourceLeadWeb_orgCode}"/>" alt="Org Image" style="height:60px; width: 100px;"></div>';
							result = result + '<div class="col-md-8 select-field">';
							result = result + '</div>';
						}
						result = result + '<div class="col-md-1 email-switch" title="On/Off">';
						result = result + '<label class="switch">';
						result = result + '<input type="checkbox" onclick="checkSignature('+opt.id+')"  name="check'+opt.id+'"  id="check'+opt.id+'" '+checked+'></input>';
						result = result + '<div class="slider" ></div>';
						result = result + ' </label>';
						result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
 					} 
					result = result + '<div class="pull-right">';
					result = result + '<input type="submit" value="Save" id="save_button" class="btn btn-primary mr">';
					result = result + '</div>';
					result = result + ' </form>';
					
					$("#campaignSignatrue").append(result);  
					$("#signLoading").removeAttr("style");
					for (var i=0; i<=json.signatureList.length; i++)
 					{
						var opt = json.signatureList[i];
						$('#cp'+opt.id+'').colorpicker();
 					}
					
					
					
		        },
		 });
}
</script>

<script>
function colourpicker(){
	$('#cp2, #cp3').colorpicker();
}
</script>


<script>
function checkSignature(id){
	var checked = document.getElementById("check"+id).checked
	if(checked == true){
		$("#check"+id).val("Y");
	}else{
		$("#check"+id).val("N");
	}
}
</script>

<script>
function getboldStyle(id){
	var checked = document.getElementById("bold"+id).checked
	if(checked == true){
		$("#bold"+id).val("Y");
		document.getElementById("boldActive"+id).removeAttribute("class");
		document.getElementById("boldActive"+id).className = 'btn btn-default btn-sm fa fa-bold btn-active'
	}else{
		$("#bold"+id).val("N");
		document.getElementById("boldActive"+id).removeAttribute("class");
		document.getElementById("boldActive"+id).className = 'btn btn-default btn-sm fa fa-bold'
	}
}
</script>

<script>
function getunderlineStyle(id){
	var checked = document.getElementById("underline"+id).checked
	if(checked == true){
		$("#underline"+id).val("Y");
		document.getElementById("underlineActive"+id).removeAttribute("class");
		document.getElementById("underlineActive"+id).className = 'btn btn-default btn-sm fa fa-underline btn-active'
	}else{
		$("#underline"+id).val("N");
		document.getElementById("underlineActive"+id).removeAttribute("class");
		document.getElementById("underlineActive"+id).className = 'btn btn-default btn-sm fa fa-underline'
	}
}
</script>

<script>
function getitalicStyle(id){
	var checked = document.getElementById("italic"+id).checked
	if(checked == true){
		$("#italic"+id).val("Y");
		document.getElementById("italicActive"+id).removeAttribute("class");
		document.getElementById("italicActive"+id).className = 'btn btn-default btn-sm fa fa-italic btn-active'
	}else{
		$("#italic"+id).val("N");
		document.getElementById("italicActive"+id).removeAttribute("class");
		document.getElementById("italicActive"+id).className = 'btn btn-default btn-sm fa fa-italic'
	}
}
</script>


<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
   $('body').on('submit', '#coolingOff_form', function(e){
  	  if ($(this).validate(false, validationSettings)){
  		    e.preventDefault(); 
     		
  		    var sourceleadUrl = '${sourceleadUrl}';
  			var frm = $('#coolingOff_form').serializeFormJSON();
  			var con = JSON.stringify(frm);
  			con = con.replace(/[{}]/g, ""); 
  		    var jsonData = encodeURIComponent(con);
  		    $("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
      		
  		     $.ajax({
  				type : "POST",
  				url : sourceleadUrl+"/saveCoolingOffPeriod?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
  				success : function(result) {
  					configCoolingOff();
  				},
  			});  
          
      	    return true;
      }
      else{
      	return false;	
         }
      });
      $('#coolingOff_form').showHelpOnFocus();
      $('#coolingOff_form').validateOnBlur(false, validationSettings); 
</script>


<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#signature_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
        		    var sourceleadUrl = '${sourceleadUrl}';
        			var frm = $('#signature_form').serializeFormJSON();
        			var con = JSON.stringify(frm);
        			con = con.replace(/[{}]/g, ""); 
        		    var jsonData = encodeURIComponent(con);
        		    $("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	           		
        		     $.ajax({
        				type : "POST",
        				url : sourceleadUrl+"/saveOrgSignature?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
        				success : function(result) {
        					loadSignature();
        				},
        			});  
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#signature_form').showHelpOnFocus();
         $('#signature_form').validateOnBlur(false, validationSettings); 
            
     
</script> 

<script>
function getSignaturePreview(){
	
	$("#campaignSignatrue").empty(); 
	var sourceleadUrl ='${sourceleadUrl}';
	
	   $.ajax({
			type : "POST",
			url : sourceleadUrl+"/getSignaturePreview?${_csrf.parameterName}=${_csrf.token}",
			success : function(result) {
				
				$("#campaignSignatrue").html(result);
				$("#SignatureHeading").html("Signature Preview");
				document.getElementById("reset").style.display="none";
				document.getElementById("settings").style.display="";
				
			},
		}); 
	 
}
</script>


<script type="text/javascript">
function restoreSignature(){
	
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to restore</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='restSignature();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#restorePopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){
	$("#restorePopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script type="text/javascript">
function restSignature(){
	var sourceleadUrl ='${sourceleadUrl}';
	$("#restorePopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	$("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	
	   $.ajax({
			type : "POST",
			url : sourceleadUrl+"/restoreSignature?${_csrf.parameterName}=${_csrf.token}",
			success : function(result) {
				
				if(result == "success"){
					loadSignature();
				}
				
				
			},
		}); 
	 
}
</script>



<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 

<jsp:include page="masterFooter.jsp" />
</html>






