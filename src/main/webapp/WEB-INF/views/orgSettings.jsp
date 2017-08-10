<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.selectChosen .chosen-container {
width: 100% !important;
}
.icon1 {
right: 15px;
}
</style>

<!-- end files for autocomplete with tagit -->
<script type="text/javascript">
window.onload = function () {
	editOrgLocationAuto('orgEditState','orgEditCity','orgEditZipCode');
	getAllCountries();
	colourpicker();
	orgShortCode();
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
		zipcodeMask('${SourceLeadWeb_country}','orgEditZipCode');
		
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
        
        <c:if test="${SourceLeadWeb_isSuperAdmin == 'Y'}">
        	<li><a data-toggle="tab" href="#orgRegisteredlocations" onclick="loadRegisteredOrgs();">Locations</a></li>
        </c:if>
        
        <li><a data-toggle="tab" href="#emailSign" onclick="loadSignature();">Email Signature</a></li>
        <li><a data-toggle="tab" href="#configCoolingOff" onclick="configCoolingOff();">Configure</a></li>
      </ul>
      <!-- Tab Pills navbar -->
      <div class="tab-content">
        <div id="orgsetting" class="tab-pane fade in active">   
        <div class="row">
          <div class="clearfix">
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab org-settings-area">
		  <div class="tab-content panel-body">
			<div class="clearfix">
			  <form:form class="login-form clearfix"  id="orgSettings_form"  name="orgSettings_form" method="post" > 
			  
			  <div class="col-xs-12 col-sm-3 col-md-3">
				 <div class="well well-sm text-center">
				  <div class="profile-img-container" style="position:relative;">
				   <img id="orgImage" onerror="predefineOrgImage(this);" src="<c:url value='/displayOrgImage/${SourceLeadWeb_orgCode}'/>" alt="Org Image" class="comp-image img-responsive">
				  
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
							<input type="text" id="orgLocationCompanyName" name="companyName" class="form-control"  field-name="Company" data-validation="required validate_Space validate_length length2-250" onblur="validateCompanyName();"  value="${SourceLeadWeb_orgName}" />
							</div>
							</div>
							 
							<div class="form-group">
								<label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-9">
								 <input type="text" class=" form-control" name="country" value="${SourceLeadWeb_country}" id="orgEditCountry" field-name="Country" data-validation="required" readonly="readonly" >
								</div>
							</div>
								
							<div class="form-group">
								<label class="col-md-3">City<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-9">
								 <input type="text" class=" form-control" name="city" value="${SourceLeadWeb_orgLocations.city}" id="orgEditCity" field-name="City" data-validation="required">
								 <i class="fa fa-refresh btn btn-primary refresh-icon icon1 " title="Refresh" onClick="refreshOrgEditGoogleApiLocation('orgEditState','orgEditCity','orgEditZipCode');"></i>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3">State<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-9">
								 <input type="text" class=" form-control" name="state" value="${SourceLeadWeb_orgLocations.state}" id="orgEditState" field-name="State" data-validation="required" >
								</div>
							</div>
								
							<div class="form-group">
								<label class="col-md-3">Zip</label>
								<div class="col-md-9">
								<input type="text"  class="form-control" name="zipcode" id="orgEditZipCode" field-name="Zip" data-validation="validate_int validate_Space validate_length length5-10" data-validation-optional="true" value="${SourceLeadWeb_orgLocations.zipCode}" />
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
							
							<div class="form-group">
							<label class="col-md-3">Date Format</label>
							<div class="col-md-9">
							
							 <select class="chosen-select form-control" id="dateFormat1" name="dateFormat1">
							 <option ${SourceLeadWeb_orgLocations.dateFormat  == 'dd-MM-yyyy' ? 'selected' : ''} value="dd-MM-yyyy">dd-MM-yyyy</option>
							 <option ${SourceLeadWeb_orgLocations.dateFormat  == 'MM-dd-yyyy' ? 'selected' : ''} value="MM-dd-yyyy">MM-dd-yyyy</option>
							 <option ${SourceLeadWeb_orgLocations.dateFormat  == 'yyyy-dd-MM' ? 'selected' : ''} value="yyyy-dd-MM">yyyy-dd-MM</option>
							 <option ${SourceLeadWeb_orgLocations.dateFormat  == 'yyyy-MM-dd' ? 'selected' : ''} value="yyyy-MM-dd">yyyy-MM-dd</option>
							 </select>
							</div>
							</div>
							
							<input type="hidden" id="existingCity" value="${SourceLeadWeb_orgLocations.city}" >
				   </div>
				
					<div class="col-md-5">
						<div class="form-group">
							<label class="col-md-3">Website<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9" id="websiteDiv">
							<c:if test="${SourceLeadWeb_website == null}">
							   <input type="text"  class="form-control"  onblur="validateOrgLocationWebsite();" data-validation="required" field-name="Website" id="website"  name="website" value="${SourceLeadWeb_website}">
							</c:if>
							
							<c:if test="${SourceLeadWeb_website != null}">
							   <input type="text" readonly="readonly" class="form-control" name="website" value="${SourceLeadWeb_website}">
							</c:if>
							
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
							   <textarea rows="6" name="aboutCompany" field-name="About" data-validation-optional="true" data-validation="validate_length length5-5000" class="form-control">${SourceLeadWeb_orgLocations.orgDescription}</textarea>
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
		
		<div id="orgRegisteredlocations" class="tab-pane fade form-horizontal"> 
			<div class="accountsettings-tab">
	                        <div class="tab-content panel-body">
	                            <div class="clearfix mb">
	                                <div class="pull-left">
	                                    <h4 id="locationOrgsName"></h4>
	                                </div>
                               <div class="pull-right">
                                <button class="btn btn-default btm-sm" id="orgsList" data-toggle="tab" href="#orgRegisteredlocations" onclick="loadRegisteredOrgs();" style="display: none"><i class="fa fa-building"></i>
	                                         &nbsp;&nbsp;Locations List</button>
                                &nbsp;&nbsp;<button class="btn btn-default btm-sm" onclick="createOrg();" id="enrollNewOrgLocation"><i class="fa fa-building"></i>
                                    &nbsp;&nbsp;Add Location</button>
                               </div>
	                            </div>
	                			<div class="clearfix form-area form-horizontal mt">
	                			
	                			<form:form class="login-form clearfix"  id="createOrg_form"  name="createOrg_form" method="post" >
	                			<div id="registeredOrgs"></div>  
	                			</form:form>
	                			
	                			<form:form class="login-form clearfix"  id="update_module_form"  name="update_module_form" method="post" >
	                			<div id="registeredOrgLocationSummary"></div>  
	                			</form:form>
	                			
	                			<form:form class="login-form clearfix"  id="editPrimaryUser_form"  name="editPrimaryUser_form" method="post" >
	                			<div id="editPrimaryUserLoading"></div>
	                			<div id="editPrimaryUserContactDiv"></div>
	                			</form:form>
	                			
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
                                    <h4 id="coolingOffHeading">Cooling-Off Period</h4>
                                    <div id="coolingOffLoading"></div>
                                </div>
                            </div>
                			<div id="coolingOffPeriodDiv"></div>            
            			</div>
            			
            			
                        <div class="tab-content panel-body">
                            <div class="clearfix mb">
                                <div class="pull-left">
                                    <h4 id="orgShortCodeHeading">Organization Short Code</h4>
                                    <div id="orgShortCodeLoading"></div>
                                </div>
                            </div>
                			<div class="form-group">
                			<label class="col-md-3">Organization Short Code<i class="" title="System Generated Id"></i></label>  
                			<div class="col-md-4"><input type="text" name="generatedUserId" value="${generatedUserId}" id="generatedUserId" class="form-control input-sm" readonly></div>
                			</div>         
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
	var con = document.getElementById("orgLocationCompanyName").value;
	var company = document.getElementById("orgLocationCompanyName").value;
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
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          		
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
					result = result + '<label class="col-md-3">Cooling Off Period &nbsp;&nbsp;<i class="fa fa-info-circle" title="A Cooling Off Period is the time during which an applicant is disqualified from applying for Jobs, because of applied recently."></i></label>';
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
					result = result + '<input type="button" value="Cancel" class="btn btn-default" onClick="cancelBtn();">';
					result = result + '</div>';
					result = result + ' </form>';
					$("#coolingOffPeriodDiv").empty(); 
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
	 
	 $("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 
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
								
							}
							 else if(opt.labelName == 'Designation'){
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
							result = result + '<div class="col-md-3"><input type="text" name=\''+name+'\' field-name="Sign Off" data-validation="required validate_Space validate_length length2-100" value="'+labelText+'" class="form-control input-sm" placeholder="Text Box"></div>';
						}else{
							result = result + '<div class="col-md-3"><input type="text" '+readonly+' value="'+labelText+'" class="form-control input-sm"></div>';
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
						result = result + '<i title="Bold" class="btn btn-default btn-sm fa fa-bold '+boldClass+'" id="boldActive'+opt.id+'" ></i>';
						result = result + '</label>';
						result = result + '</div>';
						result = result + '<div class="pull-left positionRelative">';
						result = result + '<label class="btn-styles">';
						result = result + '<input type="checkbox" name="underline'+opt.id+'" id="underline'+opt.id+'" '+underlineChecked+' '+underlineValue+' onclick="getunderlineStyle('+opt.id+')" >';
						result = result + '<i title="Underline" class="btn btn-default btn-sm fa fa-underline '+underlineClass+'" id="underlineActive'+opt.id+'"></i>';
						result = result + '</label>';
						result = result + '</div>';
						result = result + '<div class="pull-left positionRelative">';
						result = result + '<label class="btn-styles">';
						result = result + '<input type="checkbox" name="italic'+opt.id+'" id="italic'+opt.id+'" '+italicChecked+' '+italicValue+' onclick="getitalicStyle('+opt.id+')" >';
						result = result + '<i title="Italic" class="btn btn-default btn-sm fa fa-italic '+italicClass+'" id="italicActive'+opt.id+'"></i>';
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
					result = result + '<input type="button" value="Cancel" class="btn btn-default" onClick="cancelBtn();">';
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
  		    $("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      		
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
        		    $("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	           		
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
	$("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
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


<script>
function loadRegisteredOrgs(){
	 document.getElementById("enrollNewOrgLocation").style.display="";
     document.getElementById("orgsList").style.display="none"; 
     
     $("#locationOrgsName").html("Locations List");
     $("#registeredOrgs").empty();
     $("#registeredOrgLocationSummary").empty();
     $("#editPrimaryUserContactDiv").empty();
     
     
     
     var sourceleadUrl = '${sourceleadUrl}';
     $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
     
     $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getRegisteredOrgsLocationsList?${_csrf.parameterName}=${_csrf.token}",
	    			 
         success: function(data) {
	        var json = JSON.parse(data);
	        var result = "";
	        
	    	 if(json.length > 0)
	   		{
	    		
	 			for(var i=0; i<json.length; i++){
		       	 
	 				var orgLoc = json[i];
	 				var mobilePhone = "";
	 			    var email = "";
	 				
		    	    if(orgLoc.orglocationUserPhone != null && orgLoc.orglocationUserPhone != ''){
	 			    	mobilePhone = orgLoc.orglocationUserPhone;
	 			    }else{
	 			    	mobilePhone = "---"
	 			    }
	 				
	 				if(orgLoc.orglocationUserEmail != null && orgLoc.orglocationUserEmail != ''){
	 			    	email = orgLoc.orglocationUserEmail;
	 			    }else{
	 			    	email = "---"
	 			    }
	 				
	       	    	result = "<div class='col-xs-12 col-sm-12 col-md-6 col-lg-4'>";
	       	    	result = result +"<div class='user-profile'>";
	       	    	result = result +"<div class='col-xs-4 col-sm-4'>";
	       	    	result = result +"<div class='text-center image-parent'>";
	       	    	result = result +"<div class='imageParent'>";
	       	    	result = result +"<img onerror='predefineOrgImage(this);' class='m-t-xs' src='"+sourceleadUrl+"/displayOrgImage/"+orgLoc.orgCode+"' alt='image'>";
	       	    	result = result +"</div>";
	       	    	
	       	    	result = result +"</div>";
	       	    	result = result +"</div>";
	       	    	result = result +"<div class='col-xs-8 col-sm-8'>";
	       	    	result = result +"<h4 style='margin:0px 0px 5px 0px'><a href='javascript:void(0)' title='"+orgLoc.orgLocationName+"' onClick='getOrgLocationDetails("+orgLoc.id+")'>"+orgLoc.orgLocationName+" </a> </h4>";
	       	    	result = result +"<p><label><i class='fa fa-map-marker' aria-hidden='true'></i>&nbsp;&nbsp;&nbsp; "+orgLoc.country+" </lable></p>";
	       	    	result = result +"<p><label><i class='fa fa-envelope-o' aria-hidden='true'></i>&nbsp; "+email+" </lable></p>";
	       	    	result = result +"<p><label><i class='fa fa-phone'  aria-hidden='true'></i>&nbsp; "+mobilePhone+" </label></p>";
	       	        
	       	    	if(orgLoc.orglocationUserStatus != null && orgLoc.orglocationUserStatus != "" && orgLoc.orglocationUserStatus == 'NOT_LOGGEDIN'){
	 			    	 result = result +"<p><label id='user"+orgLoc.orglocationUserId+"' class='label label-info'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+orgLoc.orglocationUserId+");'>&nbsp;&nbsp;Resend</a></label></p>";
	 			    }else if(orgLoc.orglocationUserStatus != null && orgLoc.orglocationUserStatus != "" && orgLoc.orglocationUserStatus == 'ACTIVE'){
	 			    	result = result +"<p><label class='label label-success'><i class='fa fa-check'></i>&nbsp;&nbsp;Active</label></p>";
	 			    }else if(orgLoc.orglocationUserStatus != null && orgLoc.orglocationUserStatus != "" && orgLoc.orglocationUserStatus == 'INACTIVE'){
	 			    	result = result +"<p><label class='label label-danger'><i class='fa fa-ban'></i>&nbsp;&nbsp;In-active</label></p>";
	 			    }
	       	    	
	       	    	result = result +"</div>";
	       	    	result = result +"<div class='clearfix'></div>";
	       	    	result = result +"</div>";
	       	    	result = result +"</div>";
	       	    	
	       	    	$("#loading").removeAttr("style"); 
	       	 	    $('#registeredOrgs').append(result);
				   }
	   		    }
	    	else{
	    		result = "<div class='content-area mt text-center text-muted'><i class='mt fa fa-5x fa-building'></i><h3>Orgs List is Empty</h3></div>";
	    		$("#loading").removeAttr("style");
	    		$('#registeredOrgs').append(result);
	    	} 

			 },
	        error: function(e){
	        }
	    }); 
}
</script>

<script>
function getOrgLocationDetails(orgLocationId){
	
	$("#locationOrgsName").html("Location Details");
    $("#registeredOrgs").empty();
    $("#registeredOrgLocationSummary").empty();
    $("#editPrimaryUserContactDiv").empty();
    document.getElementById("orgsList").style.display=""; 
    
    var sourceleadUrl = '${sourceleadUrl}';
    $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    
     $.ajax({ 
    	type: "POST",
    	url: sourceleadUrl+"/getOrgLocationDetails?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
    	 success: function(data) {
    		 
    		    var json = JSON.parse(data);
    	        var result = '';
    	        var editPrimaryUser = '';
    	        var website = '';
    	        
    	        if(json.orglocationUserStatus == 'NOT_LOGGEDIN'){
    	        	editPrimaryUser = '<a id="edit-user" href="javascript:editPrimaryUser('+orgLocationId+');"><i class="fa fa-edit" title="Edit Contact Details"></i></a>';
    	        }
    	        if(json['website'] != null && json['website'] != '' && json['website'] != 'undefined'){
    	        	website = json['website'];
    	        }else{
    	        	website = "-------";
    	        }
    	        
    	        result = result +'<div class="col-xs-12 col-sm-3 col-md-3">';
    	        result = result +'<div class="well well-sm text-center">';
    	        result = result +'<div class="profile-img-container" style="position:relative;">';
    	        result = result +'<img id="orgImage" onerror="predefineOrgImage(this);" src="<c:url value="/displayOrgImage/${orgloc.org.orgCode}"/>" alt="Org Image" class="comp-image img-responsive">';
    	        result = result +'</div>';
    	        result = result +'</div>';
    	        result = result +'</div>';
    	        result = result +'<div class="col-xs-12 col-sm-9 col-md-9">';
    	        result = result +'<div class="row mrng0">';
    	        result = result +'<div class="col-md-6">';
    	        
    	        result = result +'<div id="editUserContact"></div>';
    								
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<div class="col-md-12 clearfix">';
	    	        result = result +'<h5>Org Details</h5>';
	    	        result = result +'</div>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4">Company :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['orgLocationName']+'</p>';
	    	        result = result +'</div>';
	    								 
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4">Website :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+website+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4">Country :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['country']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4">State :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['state']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4">City :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['city']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        if(null != json['orgLocationAddress'] &&  json['orgLocationAddress'] != 'undefined' && json['orgLocationAddress'] != ''){
	    	        	    result = result +'<div class="form-group">';
	    	    	        result = result +'<label class="col-md-4">Address :</label>';
	    	    	        result = result +'<p class="tbldata-dotdot-me">'+json['orgLocationAddress']+'</p>';
	    	    	        result = result +'</div>';	
	    	        }
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4">Enrolled Date :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['createdDateStr']+'</p>';
	    	        result = result +'</div>';
	    								
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4">Time Zone :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['timeZoneName']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4">Date Format :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['dateFormat']+'</p>';
	    	        result = result +'</div>';
    	        
    	        result = result +'</div>';
    	        
    	        
    	        result = result +'<div class="col-md-6">';
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<div class="col-md-12 clearfix">';
	    	        result = result +'<h5>Contact Details&nbsp;'+editPrimaryUser+'</h5>';
	    	        result = result +'</div>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4">Contact User : </label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['orglocationUserName']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4">Email : </label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['orglocationUserEmail']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        if(null != json['orglocationUserPhone'] &&  json['orglocationUserPhone'] != 'undefined' && json['orglocationUserPhone'] != ''){
	    	        	result = result +'<div class="form-group">';
	 	    	        result = result +'<label class="col-md-4">Mobile : </label>';
	 	    	        result = result +'<p class="tbldata-dotdot-me">'+json['orglocationUserPhone']+'</p>';
	 	    	        result = result +'</div>';
	    	        }
	    	        
    	            result = result +'</div>';
    	            
    	             result = result +'<div class="col-md-6">';
	    	            
    	            	result = result +'<div class="form-group">';
		    	        result = result +'<div class="col-md-12 clearfix">';
		    	        result = result +'<h5>Registered Modules</h5>';
		    	        result = result +'</div>';
		    	        result = result +'</div>';
		    	        
		    	        result = result +'<div class="form-group">';
		    	        result = result +'<div class="col-md-12 checkbox">';
		    	        
		    	        if(json.modulesList != null && json.modulesList != "" && json.modulesList != 'undefined' && json.modulesList.length > 0){
		    	        	
		    	        	 for(var i=0; i<json.modulesList.length; i++){
				    	        	var module = json.modulesList[i];
				    	        	
				    	       	 result = result +'<ul class="list-unstyled pull-left">';
			    	        	 result = result +'<li>';
			    	        	 
			    	        	 	if(module.id == '1'){
			    	        	 		result = result +'<label class="mr pull-left NoPad">';
			    	        	        result = result +'<p>'+module.moduleName+'</p>';
			    	        	        result = result +'</label>';
			    	        	        
			    	        	        result = result +'<ul class="list-unstyled pull-left tree-checkbox">';
			    	        	        if(json.subModulesList != null && json.subModulesList != "" && json.subModulesList != 'undefined' && json.subModulesList.length > 0){
			    	        	        	for(var j=0; j<json.subModulesList.length; j++){
			    	        	        		var subModules = json.subModulesList[j];
			    	        	        		    result = result +'<label>';
			    	        	        	        if(subModules.isModuleConfigured == 'Y'){
				    	        	        	         result = result +'<input type="checkbox" class="allsubmodules" id="submodules"  checked="checked" disabled="disabled">';
				    	        	        	         result = result +'<p>'+subModules.moduleName+'</p>';
			    	        	        			} 
			    	        	        	        if(subModules.isModuleConfigured != 'Y'){
				    	        	        	         result = result +'<input type="checkbox" class="allsubmodules" value="'+subModules.id+'"  id="submodules"  name="submodulesList[]">';
				    	        	        	         result = result +'<p>'+subModules.moduleName+'</p>';
			    	        	        			} 
			    	        	        	        result = result +'</label><br/>';
			    	        	        	}
			    	        	        }
			    	        	        
			    	        	        result = result +'</ul>';
			    	        	 	}
			    	        	 
			    	        	 result = result +'</li>';
			    	        	 
			    	        	 
			    	        	 
			    	        	 
			    	        	 result = result +'<li>';
			    	             result = result +'<label class="mr pull-left">';
				    	             if(module.id != "1" && module.isModuleConfigured  == "Y"){
				    	            	 result = result +'<input type="checkbox" id="isRecruiting"  checked="checked" disabled="disabled" >';
				    	            	 result = result +'<p>'+module.moduleName+'</p>';
				    	             }
				    	             
				    	             if(module.id != "1" && module.isModuleConfigured  != "Y"){
				    	            	 result = result +'<input type="checkbox" id="isRecruiting" value="'+module.id+'"  name="modulesList[]"  >';
				    	            	 result = result +'<p>'+module.moduleName+'</p>';
				    	             } 
			    	             result = result +'</label>';
			    	             result = result +'</li>';
			    	        	 
			    	        	 result = result +'</ul>';
				    	        	
				    	        	
				    	        }
		    	        }
		    	        
		    	        result = result +'</div>';
		    	        result = result +'</div>';
		    	        
		    	        result = result +'<div class="clearfix">';
		    	        result = result +'<div class="pull-right">';
		    	        result = result +'<input type="button" value="Upgrade" onclick="updateModules('+orgLocationId+');" id="save_button" class="btn btn-primary mr">';
		    	        result = result +'<input type="button" value="Cancel" class="btn btn-default" onClick="loadRegisteredOrgs();">';
		    	        result = result +'</div>';
		    	        result = result +'</div>';
		    	        
    	                result = result +'</div>'; 
    	       	
    	       	    	
    	    	$("#loading").removeAttr("style"); 
    	 	    $('#registeredOrgLocationSummary').append(result);
    	 }
    }); 
}
</script>


<script type="text/javascript">
	function updateModules(orgLocationId) {
		
			var sourceleadUrl ='${sourceleadUrl}'; 
			
			 var modulesList = [];
			 $("input[name='modulesList[]']:checked").each( function (i) {
				modulesList[i] = $(this).val();
			});
			 
			
			 var submodulesList = [];
			 $("input[name='submodulesList[]']:checked").each( function (i) {
				submodulesList[i] = $(this).val();
			});
			 
	 	 
		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({ 
		    	type: "POST",
		    	 url: sourceleadUrl+"/updateOrgModules?${_csrf.parameterName}=${_csrf.token}&modulesList="+modulesList+"&submodulesList="+submodulesList+"&orgLocationId="+orgLocationId,
		        success: function(result) {
		        	if((result == 'success')){
		        		loadRegisteredOrgs();
			        } 
		        },
		 });  
		  
	}

</script>





<script type="text/javascript">
function resendInvitation(userId){
	var sourceleadUrl ='${sourceleadUrl}';
	if(userId != null && userId != ""){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendInvitationEmail?${_csrf.parameterName}=${_csrf.token}&userId="+userId,  
			        success: function(result) {
			        	$("#user"+userId).html("<i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)'  onClick='resendInvitation("+userId+");'>&nbsp;Resend</a>");
			        	$("#loading").removeAttr("style"); 
			        	$("#mailMsg").fadeIn(200);
		   		        $('#mailMsg').delay(2000).fadeOut('slow');
			        },
			    });
		 }
	}
  
</script>


<script>
function createOrg(){
	$("#locationOrgsName").html("Add New Location");
	$("#registeredOrgs").empty();
	$("#registeredOrgLocationSummary").empty();
	$("#editPrimaryUserContactDiv").empty();
	 
	var result = '';
	
	result = result +'<div class="row">';
	result = result +'<div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">';
           
	result = result +'<div class="form-group" id="companyNameDiv">';
	result = result +'<label class="col-md-3">Company Name<span class="text-danger">&nbsp;*</span></label>';
	result = result +'<div class="col-md-9">';
	result = result +'<input type="text" name="companyName" id="companyName"  value="${companyName}" class="form-control" onblur="validateCompanyName();"  field-name="Company Name" data-validation="required validate_Space validate_length length2-250" />';
	result = result +'<span class="hidden">Company</span>';
	result = result +'</div>';
	result = result +'</div>';
          
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Website<span class="text-danger">&nbsp;*</span></label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<input type="text" name="website"  value="${website}"   id="companyWebsite"  onblur="validateWebsite();validateCompanyEmail();"  field-name=" Website" data-validation="required validate_Space validate_domain validate_length length2-100"    class="form-control" />';
	            result = result +'<span class="hidden">Website</span>';
	            result = result +'</div>';
	            result = result +'</div>';
	            
	            result = result +'<input type="hidden" name="registeredOrgId" id="registeredOrgId"  value="${SourceLeadWeb_org.id}" />';
           
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<input type="text" name="email" id="email"   value="${email}" onblur="validateCompanyEmail();validateWebsite();getUserDetails();" field-name="Email" data-validation="required validate_Space validate_email validate_length length2-100" class="form-control"  />';
	            result = result +'<span class="hidden">Email</span>';
	            result = result +'</div>';
	            result = result +' </div>';
          
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<input type="text" class="form-control" id="firstName" name="firstName" value="${firstName}" field-name="First Name" data-validation="required validate_Alphanumeric validate_Space validate_length length1-100" />';
	            result = result +'<span class="hidden">First Name</span>';
	            result = result +'</div>';
	            result = result +'</div>';
        
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Last Name </label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<input type="text" name="lastName"  id="lastName"  value="${lastName}" class="form-control" field-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100"  />';
	            result = result +'<span class="hidden">Last Name</span>';
	            result = result +'</div>';
	            result = result +'</div>';
          
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Phone Number </label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<input type="text" name="mobilePhone"  id="mobilePhone"  value="${mobilePhone}" class="form-control" field-name="Phone Number" data-validation-optional="true" data-validation="validate_Space validate_length length9-20 validate_phone"  />';
	            result = result +'<span class="hidden">Phone Number</span>';
	            result = result +'</div>';
	            result = result +'</div>';
          
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Modules<span class="text-danger">&nbsp;*</label>';
	            result = result +'<div class="col-md-9 checkBox">';
	            result = result +'<div class="clearfix reMar">';
	            result = result +'<c:forEach var="module" items="${modulesList}">';
	            result = result +'<ul class="list-unstyled pull-left">';
	            result = result +'<li>';
	            result = result +'<label class="mr pull-left">';
	            result = result +'<input type="checkbox" id="isRecruiting" value="${module.id}" onclick="getRecruitmentTypes(this)" name="modulesList[]" onblur="moduleCheckValidation(this)">${module.moduleName}';
	            result = result +'</label>';
	            result = result +'<ul class="list-unstyled pull-left tree-checkbox" id="recrutmentTypes" style="display: none;">';
	            result = result +'<li>';
	            result = result +'<c:forEach var="submodules" items="${requirementSubModulesList}">';
	            result = result +'<label>';
	            result = result +'<input type="checkbox" class="allsubmodules" id="submodules" value="${submodules.id}" name="submodulesList[]" >';
	            result = result +'${submodules.moduleName}';
	            result = result +'</label><br/>';
	            result = result +'</c:forEach>';
	            result = result +'</li>';
	            result = result +'</ul>';
	            result = result +'</li>';
	            result = result +'</ul>';
	            result = result +'</c:forEach>';
	            result = result +'</div>';
	            result = result +'<p id="subModule" style="display: none;"><font color="red">Sub Modules Required</font></p>';
	            result = result +'<input type="hidden"  id="moduleValue" name="module" value="${module}" field-name="Module" data-validation="required"/>'; 			    
	            result = result +'</div>';
	            result = result +'</div>'; 
        
        
	            result = result +'</div>';
                                 
	            result = result +'<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">';
          
				result = result +'           <div class="form-group">';
				result = result +' 			<label class="col-md-3">Location<span class="text-danger">&nbsp;*</span></label>';
				result = result +' 			 <div class="col-md-9" id="locationField">';
				result = result +' 		          <div class="positionRelative">';
				result = result +' 			          <input class="form-control positionRelative" id="orgSetLocation" name="location" placeholder="Enter your Location"  type="text" field-name="Locatin" onclick="initLocationAuto(\'orgSetLocation\',\'orgSetCountry\',\'orgSetState\',\'orgSetCity\',\'orgSetZipCode\',\'orgSetLatitude\',\'orgSetLongitude\',\'invite\');" data-validation="required"></input>';
				result = result +' 			          <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="addOrgRefreshGoogleApiLocation();"></i>';
				result = result +' 			        </div>';
				result = result +' 	          </div>';
				result = result +' 		 </div>';
				result = result +'       <div class="form-group">';
				result = result +'         <label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>';
				result = result +'         <div class="col-md-9">';
				result = result +'         <input class="form-control" id="orgSetCountry" name="country" placeholder="Country" field-name="Country" value ="${country}" type="text" data-validation="required" readonly></input>';
				result = result +'         </div>';
				result = result +'       </div>';
				result = result +'       <div class="form-group">';
				result = result +'         <label class="col-md-3">State<span class="text-danger">&nbsp;*</span></label>';
				result = result +'         <div class="col-md-9">';
				result = result +'         <input class="form-control" id="orgSetState" name="state" placeholder="State" type="text" field-name="State" data-validation="required" readonly></input>';
				result = result +'         </div>';
				result = result +'       </div>';
				result = result +'       <div class="form-group">';
				result = result +'         <label class="col-md-3">City<span class="text-danger">&nbsp;*</span></label>';
				result = result +'         <div class="col-md-9">';
				result = result +'         <input class="form-control" id="orgSetCity" name="city" placeholder="City" type="text" field-name="City" data-validation="required"  readonly></input>';
				result = result +'         </div>';
				result = result +'       </div>';
				result = result +'       <div class="form-group">';
				result = result +'         <label class="col-md-3">ZipCode</label>';
				result = result +'         <div class="col-md-9">';
				result = result +'         <input class="form-control" id="orgSetZipCode" name="zipCode" placeholder="Zip code" field-name="Zip code" type="text"></input>';
				result = result +'         </div>';
				result = result +'       </div>';
						        
				
				/* 
	            result = result +' <div class="form-group">';
	            result = result +'<label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>';
	            result = result +'<div class="col-md-9 dropdown-select">';
	            result = result +'<select class="chosen-select form-control select-bg" name="country" id="locationCountry" field-name="Country" data-validation="required" value="${country}">';
	            result = result +'<option value="" >Select Location</option>';
	            result = result +'<option value="Australia">Australia</option>';
	            result = result +'<option value="France">France</option>';
	            result = result +'<option value="India">India</option>';
	            result = result +'<option value="Singapore">Singapore</option>';
	            result = result +'<option value="United Kingdom">United Kingdom</option>';
	            result = result +'<option value="United States">United States</option>';
	            result = result +'</select>';
	            result = result +'<span class="hidden">Country</span>';
	            result = result +'</div>';
	            result = result +'</div>';
          
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">State<span class="text-danger">&nbsp;*</span></label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<select  class="chosen-select form-control" name="state" id="locationState" field-name="State" data-validation="required" > </select>';
	            result = result +'</div>';
	            result = result +'</div>';
							
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">City<span class="text-danger">&nbsp;*</span></label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<select  class="chosen-select form-control" name="city" id="locationCity" field-name="City" data-validation="required" ></select>';
	            result = result +'</div>';
	            result = result +'</div>'; */
			
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Address</label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<input type="text" name="address" id="address" field-name="Address" data-validation-optional="true" data-validation="validate_length length5-5000" class="form-control"/>';
	            result = result +'</div>';
	            result = result +'</div>';
     
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Time Zone<span class="text-danger">&nbsp;*</span></label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<select class="chosen-select form-control" data-placeholder="Time Zone" field-name="Time zone" data-validation="required" id="locationTimeZone" name="timeZone">';
	            result = result +'</select>';
	            result = result +'<span class="hidden">Time Zone</span>';
	            result = result +'</div>';
	            result = result +'</div>';
	            
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Date Format</label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<select class="chosen-select form-control" data-placeholder="Date Format" field-name="Date Format" data-validation="required" id="dateFormat" name="dateFormat">';
	            result = result +'</select>';
	            //result = result +'<span class="hidden">Time Zone</span>';
	            result = result +'</div>';
	            result = result +'</div>';
	            
          
	            
        
	            result = result +'</div>';
	            result = result +'</div>';
    
	            result = result +'<div class="form-footer clearfix row">';
	            result = result +'<div class="col-lg-12">';
	            result = result +'<div class="pull-right">';
	            result = result +'<input type="submit" value="Register" class="btn btn-primary" id="register_button">';
	            result = result +'</div>';
	            result = result +'</div>';
	            result = result +'</div>';
	            
	            $("#registeredOrgs").append(result);
	            
	            
	            document.getElementById("enrollNewOrgLocation").style.display="none";
	            document.getElementById("orgsList").style.display="";
	            
	            
	            var config = {
	      			  '.chosen-select'           : {},
	      			  '.chosen-select-deselect'  : {allow_single_deselect:true},
	      			  '.chosen-select-no-single' : {disable_search_threshold:10},
	      			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	      			  '.chosen-select-width'     : {width:"95%"}
	      			}
	           
	           $("#locationCountry").chosen();
	 	       $("#locationState").chosen();
	 	       $("#locationCity").chosen();
	 	       $("#locationTimeZone").chosen();
	 	       
	 	       $("#locationCountry").on('change', function (e) {
		    	   getLocationStates();
		    	   getTimezonesList();
		       });
		       $("#locationState").on('change', function (e) {
		    	   getLocationCities();
		       });
		      
		       $('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({'width': '100%'});
			  })
	      	       
	
}
</script>


<script type="text/javascript">
	function moduleCheckValidation(id){
		
		 var modulesList = [];
 		 $("input[name='modulesList[]']:checked").each( function (i) {
 			modulesList[i] = $(this).val();
 		});
 		 
		if (modulesList.length > 0) { 
		document.getElementById("moduleValue").value = id.value;
		}else{
			$('#moduleValue').val(''); 
		}
	} 
</script>



 <script type="text/javascript">
function getRecruitmentTypes(id){
		if (document.getElementById('isRecruiting').checked) {
		 document.getElementById("recrutmentTypes").style.display="";
		}else{
			document.getElementById("recrutmentTypes").style.display="none";
			 $('.allsubmodules').each(function() {
	                this.checked = false;      
	            }); 
		}
	} 
</script>


<script>
function getLocationStates(){
	var countryName = $("#locationCountry").val();
	
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#locationState').empty();
	        	$('#locationState').append('<option value="">---Select State---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#locationState').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#locationState').trigger("chosen:updated");
	            getCities();
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#locationState').empty();
    	$('#locationState').append('<option value="">---Select State---</option>');
    	$('#locationState').trigger("chosen:updated");
    	$('#locationCity').empty();
    	$('#locationCity').append('<option value="">---Select City---</option>');
        $('#locationCity').trigger("chosen:updated");
	}
}
</script> 


<script>
function getLocationCities(){
	var countryName = $("#locationCountry").val();
	var stateName = $("#locationState").val();
	
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#locationCity').empty();
	        	$('#locationCity').append('<option value="">---Select City---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#locationCity').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#locationCity').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#locationCity').empty();
    	$('#locationCity').append('<option value="">---Select City---</option>');
    	$('#locationCity').trigger("chosen:updated");
	}
}
</script>


<script>
function getTimezonesList(){
	var sourceleadUrl ='${sourceleadUrl}';
	var country = $('#orgSetCountry').val();
	$("#dateFormat").empty();
	if(country == "India"){
		$('#dateFormat').append("<option value='dd-MM-yyyy' selected>dd-MM-yyyy</option>");
		$('#dateFormat').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
		$('#dateFormat').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
		$('#dateFormat').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
	}else{
		$('#dateFormat').append("<option value='MM-dd-yyyy' selected>MM-dd-yyyy</option>");
		$('#dateFormat').append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
		$('#dateFormat').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
		$('#dateFormat').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
	}
	 $.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getTimezonesList?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(data) {
	        var json = JSON.parse(data);
	        var output = "";
	        output = output + "<option value=''>--Select--</option>";
	        if(country == 'India'){
    			output = output + "<option value='246' selected> Asia/Kolkata </option>";
    		}else if(country == 'United States'){
    			output = output + "<option value='132' selected> America/Chicago </option>";
    		}else if(country == 'United Kingdom'){
    			output = output + "<option value='333' selected> Europe/London </option>";
    		}else if(country == 'Singapore'){
    			output = output + "<option value='272' selected> Asia/Singapore </option>";
    		}else if(country == 'France'){
    			output = output + "<option value='342' selected> Europe/Paris </option>";
    		}else if(country == 'Australia'){
    			output = output + "<option value='298' selected> Australia/Adelaide </option>";
    		}
	        	for (var i=0; i<json.length; i++){
	        		var opt = json[i];
	        		output = output + "<option value='" +opt.id+ "'>"+opt.timeZoneId+"</option>";
				}
	        	$("#locationTimeZone").empty();
	        	$("#locationTimeZone").append(output);
	        	$("#locationTimeZone").trigger("chosen:updated");
	        }
	 });
}
</script>

<script type="text/javascript">
function validateCompanyName(){
	var con = document.getElementById("companyName").value;
	var company = document.getElementById("companyName").value;
	var companyName = encodeURIComponent(con);
	var sourceleadUrl ='${sourceleadUrl}';
	 if(companyName != null && companyName != ''){
		 $("#register_button").attr("disabled", "disabled");
		 $( "#register_button" ).addClass('button').val('Processing..');
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
		    				$( "#register_button" ).addClass('sign_btn').val('Register');
		    				$("#register_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New company
			        		$("#register_button").removeAttr("disabled");
				    		$('input#companyName').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyNameDiv').find('p.jquery_form_error_message').remove();
							$('input#companyName').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Register');
							$( "#register_button" ).click(function() {
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
function validateOrgLocationWebsite (){
      var website = document.getElementById("website").value; 
     var sourceleadUrl ='${sourceleadUrl}';
     var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}';
	 $('input#website').removeAttr( "record-exist record-exist-errormsg");
	 if(website != null && website != ''){
		
		 $("#register_button").attr("disabled", "disabled");
		 $( "#register_button" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/ws/validateOrgWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
			        success: function(data) {
			        	 var result = data;
			        		if (result == "Website Duplicated" || result == "Invalid Website") {
		        			//existed website
		    	    		$('#websiteDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#website').attr('class','error form-control');
		    	    		$("input#website").closest('b').after("<p class='jquery_form_error_message'> "+website+" "+result+"</p>");
		    				$('input#website').attr('record-exist','yes');
		    				$('input#website').attr('record-exist-errorMsg',website+' '+result+''); 
		    				$( "#save_button" ).addClass('sign_btn').val('Register');
		    				$("#save_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New WebSite
			        		$("#register_button").removeAttr("disabled");
				    		$('input#website').removeAttr( "class record-exist record-exist-errormsg");
							$('#websiteDiv').find('p.jquery_form_error_message').remove();
							$('input#website').attr('class','valid form-control');
							$( "#save_button" ).removeAttr("style");
							$( "#save_button" ).addClass('sign_btn').val('Register');
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
function validateWebsite (){
      var website = document.getElementById("companyWebsite").value; 
     var sourceleadUrl ='${sourceleadUrl}';
     var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}';
	 $('input#companyWebsite').removeAttr( "record-exist record-exist-errormsg");
	 if(website != null && website != ''){
		
		 $("#register_button").attr("disabled", "disabled");
		 $( "#register_button" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/ws/validateOrgWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
			        success: function(data) {
			        	 var result = data;
			        		if (result == "Website Duplicated" || result == "Invalid Website") {
		        			//existed website
		    	    		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#companyWebsite').attr('class','error form-control');
		    	    		$("input#companyWebsite").closest('b').after("<p class='jquery_form_error_message'> "+website+" "+result+"</p>");
		    				$('input#companyWebsite').attr('record-exist','yes');
		    				$('input#companyWebsite').attr('record-exist-errorMsg',website+' '+result+''); 
		    				$( "#register_button" ).addClass('sign_btn').val('Register');
		    				$("#register_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New WebSite
			        		$("#register_button").removeAttr("disabled");
				    		$('input#companyWebsite').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
							$('input#companyWebsite').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Register');
							$( "#register_button" ).click(function() {
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
function validateCompanyEmail(){
    var websiteValue = document.getElementById("companyWebsite").value;
    var emailValue = document.getElementById("email").value;
    var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}'; 
    var sourceleadUrl ='${sourceleadUrl}';
		if((websiteValue != null && websiteValue != '') && (emailValue != null && emailValue != '')){
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/ws/emailAndWebsiteRegistrationValidation?website="+websiteValue+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites+"&email="+emailValue,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid Email'){
					    	    //Invalid Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#email').attr('class','error form-control');
			    	    		$("input#email").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Invalid</p>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',emailValue+' is Invalid'); 
			    				$( "#register_button" ).addClass('sign_btn').val('Register');
			    				$("#register_button").removeAttr("disabled");
					     } 
					     else if(data == 'Existed Email'){
					    	   //Existed Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#email').attr('class','error form-control');
			    	    		$("input#email").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Already Existed</p>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',emailValue+' is Already Existed'); 
			    				$( "#register_button" ).addClass('sign_btn').val('Register');
			    				$("#register_button").removeAttr("disabled");
					    	 
					     }
				       	else{
				       		//Valid Email
				       		$("#register_button").removeAttr("disabled");
				    		$('input#email').removeAttr( "class record-exist record-exist-errormsg");
							$('#emailDiv').find('p.jquery_form_error_message').remove();
							$('input#email').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Register');
							$( "#register_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
				       	}
			       	}
		    	   
		      },
		       
		}); 
	   }
}
</script>

<script type="text/javascript">
function getUserDetails(){
	var email = document.getElementById("email").value;
	var sourceleadUrl ='${sourceleadUrl}';
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getUserDetails?${_csrf.parameterName}=${_csrf.token}&email="+email,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		 if(json[0] != null && json[0] != ''){
	        			$("#firstName").attr("readonly", "readonly");
	        		    $("#firstName").val(json[0]);
	        		 }else{
	        			 $("#firstName").removeAttr("readonly", "readonly");
	        			 $("#firstName").val('');
	        		 }
	        		 
	        		 if(json[1] != null && json[1] != ''){
		        			$("#lastName").attr("readonly", "readonly");
		        		    $("#lastName").val(json[1]);
	        		 }else{
	        			    $("#lastName").removeAttr("readonly", "readonly");
	        			    $("#lastName").val('');
	        		 }
	        		
	        		 if(json[2] != null && json[2] != ''){
		        			$("#mobilePhone").attr("readonly", "readonly");
		        		    $("#mobilePhone").val(json[2]);
	        		 }else{
	        			    $("#mobilePhone").removeAttr("readonly", "readonly");
	        			    $("#mobilePhone").val('');
	        		 }
	        	}
			 },
	        error: function(e){
	            //alert('Ajax Error: ' + e.value);
	        }
	    });
	}else{
		$("#firstName").removeAttr("readonly");
	    $("#firstName").val('');
	    
	    $("#lastName").removeAttr("readonly");
	    $("#lastName").val('');
	    
	   
	}
}
</script>



<script>
function editPrimaryUser(orgLocationId){
	
	var sourceleadUrl ='${sourceleadUrl}';
	$("#registeredOrgLocationSummary").empty();
	$("#editPrimaryUserContactDiv").empty();
	$("#locationOrgsName").html("Edit Contact Details");
	
	$.ajax({
    	type: "GET",
    	url : sourceleadUrl+"/getPrimaryUserContactDetailsByOrgLocationId?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
        	if(data != null && data != ''){
        		var json = JSON.parse(data);
        		
        		var result = '';
        				
        		result = result + '<div class="user_summary_edit_cta">';
        		result = result + '<label>First Name<span class="text-danger">&nbsp; *</span></label>';
        		result = result + '<input type="text" value="'+json['orgLocationUserFirstName']+'"  id="primaryUserFirstName" name="primaryUserFirstName" field-name="First Name" data-validation="required validate_Alphanumeric validate_Space validate_length length1-100" class="form-control">';
        		result = result + '</div>';
        				
        		result = result + '<div class="user_summary_edit_cta">';
        		result = result + '<label>Last Name</label>';
        		result = result + '<input type="text"value="'+json['orgLocationUserLastName']+'" id="primaryUserLastName"  name="primaryUserLastName" field-name="Last Name" data-validation="validate_Alphanumeric validate_Space validate_length length1-100" class="form-control" data-validation-optional="true">';
        		result = result + '</div>';
        				
        		result = result + '<div class="user_summary_edit_cta" id="primaryUserEmailDiv">';
        		result = result + '<label>Email<span class="text-danger">&nbsp; *</span></label>';
        		result = result + '<input type="text" value="'+json['orglocationUserEmail']+'" name="primaryUserEmail" id="primaryUserEmail" field-name="Email" onblur="validatePrimaryUserEmail()" data-validation="required validate_Space validate_email validate_length length2-100"  class="form-control">';
        		result = result + '</div>';
        				
        		result = result + '<div class="user_summary_edit_cta" id="phoneNumberDiv">';
        		result = result + '<label>Phone Number</span></label>';
        		result = result + '<input type="text" value="'+json['orglocationUserPhone']+'" name="mobilePhone" id="mobilePhone" field-name="Phone Number"  data-validation-optional="true" data-validation="validate_Space validate_length length9-20 validate_phone" class="form-control">';
        		result = result + '</div>';
        				
        				 
        		result = result + '<div class="clearfix"></div>';
        		result = result + '<div class="user_summary_edit_cta pull-right">';
        		result = result + '<input class="us-btn" value="Save" id="update_primaryuser" onclick="validatePrimaryUserEmail();savePrimaryUserDetails('+orgLocationId+','+json['orglocationUserId']+');" type="button" > <input class="us-btn us-btn_a" value="Cancel" onClick="getOrgLocationDetails('+orgLocationId+');" type="button">';
        		result = result + '</div>';
        		
        		$("#editPrimaryUserContactDiv").append(result);
        		
        	}
		 }
    });
	
	
}
</script>

<script>
function validatePrimaryUserEmail(){
	var email = document.getElementById("primaryUserEmail").value;
	var sourceleadUrl ='${sourceleadUrl}';
	var orgLocationId = '${registeredOrgLocationId}';
	if(null != email  && '' != email){
		$("#update_primaryuser").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		    url: sourceleadUrl+"/validateOrgPrimaryUserEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&orgLocationId="+orgLocationId,
		       success: function(data) {
		    		if(data != null && data != ''){
		    			if(data == 'Existed Email'){
			        		  $('#primaryUserEmailDiv').find('p.jquery_form_error_message').remove();
							  $("input#primaryUserEmail").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
							  document.getElementById("primaryUserEmail").setAttribute('record-exist','yes');
							  document.getElementById("primaryUserEmail").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
			        	}
			        	else{
			        		 $('input#primaryUserEmail').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#primaryUserEmailDiv').find('p.jquery_form_error_message').remove(); 
	                         $("#update_primaryuser").removeAttr("disabled");
			        	}
					     
		    			
			       	}
		    	   
		      },
		       
		}); 
	}
	
}
</script>

<script type="text/javascript">
 function savePrimaryUserDetails(orgLocationId,userId) {
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
		   }; 
	 if ($('#editPrimaryUser_form').validate(false, validationSettings)){ 
		 
		 var frm = $('#editPrimaryUser_form').serializeFormJSON();
         var con = JSON.stringify(frm);
         con = con.replace(/[{}]/g, "");
         var jsonData = encodeURIComponent(con);
         var sourceleadUrl ='${sourceleadUrl}';
         
         $("#editPrimaryUserLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
          
          $.ajax({
             type: "POST",
             url: sourceleadUrl+"/editOrgPrimaryUser?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId+"&json="+jsonData,
             success: function(data) {
                 if(data == "success"){
                	 $("#editPrimaryUserLoading").removeAttr("style");
                	 getOrgLocationDetails(orgLocationId);
                 }
                 
             }
             });  
         
		 
		 return true;
	 }else{
		 return false;
	 }
    }
 
 $('body').on('blur', '#editPrimaryUser_form', function() {
		$("#editPrimaryUser_form").showHelpOnFocus();;
		$("#editPrimaryUser_form").validateOnBlur(false, validationSettings);  
	});
</script>



<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#createOrg_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
            	
        		var frm = $('#createOrg_form').serializeFormJSON();
            	var con = JSON.stringify(frm);
			    con = con.replace(/[{}]/g, ""); 
			    var jsonData = encodeURIComponent(con);
         		var sourceleadUrl ='${sourceleadUrl}';
         		
         		 var modulesList = [];
         		 $("input[name='modulesList[]']:checked").each( function (i) {
         			modulesList[i] = $(this).val();
         		});
         		 
         		
         		 var submodulesList = [];
         		 $("input[name='submodulesList[]']:checked").each( function (i) {
         			submodulesList[i] = $(this).val();
         		});
         		
         		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         		 	$.ajax({
          	      type: "POST",
          	      contentType : 'application/json; charset=utf-8',
          	      dataType : 'json',
                	  url: sourceleadUrl+"/companyRegistration?&${_csrf.parameterName}=${_csrf.token}&modulesList="+modulesList+"&submodulesList="+submodulesList+"&json="+jsonData,
                			  
                			  
                  	success: function(result) {
			        	if((result == 'orgList')){
			        		loadRegisteredOrgs();
			        	}
			        	else if((result == 'Invalid Sub Modules')){
			        		$("#loading").removeAttr("style");
			        		document.getElementById("subModule").style.display="";
			        	}
			        	
			        },  
               });  
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         //$('#createOrg_form').showHelpOnFocus();
            
         $('body').on('blur', '#createOrg_form', function() {
     		$("#createOrg_form").showHelpOnFocus();
     		$("#createOrg_form").validateOnBlur(false, validationSettings);
     	});
     
	</script>


<script>
function cancelBtn(){
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  var sourceleadUrl ='${sourceleadUrl}';
	  location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_orgLocationId}");
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
function editOrgLocationAuto(orgStateId,orgCityId,orgZipCodeId) {
	editOrgInitialize(orgStateId,orgCityId,orgZipCodeId);
}
function editOrgInitialize(orgStateId,orgCityId,orgZipCodeId) {
	var country = '${SourceLeadWeb_country}';
	if(country == "India"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'IN'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}else if(country == "United States"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'US'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}else if(country == "Australia"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'AU'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}else if(country == "France"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'FR'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}else if(country == "Singapore"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'SG'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}else if(country == "United kingdom"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'GB'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}
    	 
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
  place = autocomplete.getPlace();
  var address = {};
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    switch (addressType) {
      case "locality":
        address.city = place.address_components[i].long_name;
        break;
      case "administrative_area_level_1":
        address.state = place.address_components[i].long_name;
        break;
      case "postal_code":
        address.zipcode = place.address_components[i].long_name;
        
    }
  }
  
  $("#"+orgStateId).val(address.state).change();
  $("#"+orgCityId).val(address.city).change();
  $("#"+orgZipCodeId).val(address.zipcode).change();
});
}
</script>
<script>
function refreshOrgEditGoogleApiLocation(eidtStateId,editCityId,editZipCodeId){
	$("#"+eidtStateId).val('');
	$("#"+editCityId).val('');
	$("#"+editZipCodeId).val('');
}
function addOrgRefreshGoogleApiLocation(){
	$("#orgSetLocation").val('');
	$("#orgSetCountry").val('');
	$("#orgSetState").val('');
	$("#orgSetCity").val('');
	$("#orgSetZipCode").val('');
}
</script>
<jsp:include page="masterFooter.jsp" />
</html>






