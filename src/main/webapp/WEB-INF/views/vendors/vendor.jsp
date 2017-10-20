<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript">
window.onload = function () {
	initLocationAuto('clientLocation','clientCountry','clientState','clientCity','clientZipCode','clientLatitude','clientLongitude','invite');
 };
</script>
<!-- <script>
	$(document).ready(function() {
		document.getElementById("client_form").reset();
	});
</script> -->

<style>
.project_tabs {
    border:none !important;
    }
    
.disabledLink
{
	color: #333;
	text-decoration : none;
}
</style>

<script>
	$(document).ready(function() {
		document.getElementById("client_form").reset();
		
		var orgRelationId = '${orgRelationId}';
	
		if(orgRelationId != null && orgRelationId != '' && orgRelationId != 'undefined'){
			
			var orgRelationUUID = '${orgRelationUUID}';
			var vendorOrgLocationId = '${vendorOrgLocationId}';
			var vendorOnboardId = '${vendorOnboardId}';
			
			$("#orgRelationId").val(orgRelationId);
			$("#vendorOrgLocationId").val(vendorOrgLocationId);
			$("#vendorOnboardId").val(vendorOnboardId);
			getVendorDetails();
			
		
			 var vendorDocuments = document.getElementById("vendorDocuments");
			 $("#vendorDocuments").removeClass("disabledLink");
			 vendorDocuments.href="#tab-b";
			 vendorDocuments.addEventListener("click", function() {
				 getOnboardDocuments();
			  });
			
			var isDocumentsFilled = '${isDocumentsFilled}';
			if(isDocumentsFilled == 'N'){
				var vendorTasks = document.getElementById("vendorTasks");
				if(vendorTasks){
					vendorTasks.href = "#";
					vendorTasks.className = "disabledLink";
				}
			}else{
				 var vendorTasks = document.getElementById("vendorTasks");
				 $("#vendorTasks").removeClass("disabledLink");
				 vendorTasks.href="#tab-c";
				 vendorTasks.addEventListener("click", function() {
					 getVendorOnboardTasksList();
				  });
			}
			
		}else{
			var vendorDocuments = document.getElementById("vendorDocuments");
			if(vendorDocuments){
				vendorDocuments.href = "#";
				vendorDocuments.className = "disabledLink";
			}
			
			var vendorTasks = document.getElementById("vendorTasks");
			if(vendorTasks){
				vendorTasks.href = "#";
				vendorTasks.className = "disabledLink";
			}
			
		}
		
		$("#tab-a").show()
	    $(".newPrTabsMenu a").click(function(event) {
	        event.preventDefault();
	        $(this).parent().addClass("current");
	        $(this).parent().siblings().removeClass("current");
	        var tab = $(this).attr("href");
	        $(".newPrTabs_inn").not(tab).css("display", "none");
	        $(tab).fadeIn();
	    });
	});
</script>


<script>
function getVendorDetails(){
	var vendorOrgLocationId = document.getElementById("vendorOrgLocationId").value;
	var orgRelationId = document.getElementById("orgRelationId").value;
	if(orgRelationId){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			
	        $.ajax({
		    	type: "GET",
		    	 url: sourceleadUrl+"/getOrgRelationDetailsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId, 
		        success: function(json) {
		        	
		        	   $("input[name=orgName]").val(json.orgName);
		        	   $("input[name=orgName]").attr("readonly","readonly");
	        		   $('#orgName').removeAttr('onblur');
		        	   
	        		   if(json.webSite != null && json.webSite != 'undefined' && json.webSite != ''){
	        			   $("input[name=webSite]").val(json.webSite);
	        		   }
	        		   
	        		   
	        		   $("input[name=location]").val(json.vendorOrgLocation);
	        		   $("input[name=country]").val(json.country);
	        		   $("input[name=state]").val(json.state);
	        		   $("input[name=city]").val(json.city);
	        		   
	        		   if(json.zipcode != null && json.zipcode != 'undefined' && json.zipcode != ''){
	        			   $("input[name=zipcode]").val(json.zipcode);
	        		   }
	        		   $("input[name=zipcode]").removeAttr("readonly");
	        		   
	        		   $("select[name=level]").val(json.level);
	        		   
	        		   if(json.address != null && json.address != 'undefined' && json.address != ''){
	        			   $("textarea[name=address]").val(json.address);
	        		   }
	        		   
	        		   if(json.billingAddress != null && json.billingAddress != 'undefined' && json.billingAddress != ''){
	        			   $("textarea[name=billingAddress]").val(json.billingAddress);
	        		   }
	        		   
	        		   
	        		   if(json.paymentTerms != null && json.paymentTerms != 'undefined' && json.paymentTerms != ''){
	        			   $("select[name=paymentTerms]").val(json.paymentTerms);
	        		   }
	        		   
	        		   
	        			if(json.vendorOnboardTemplateId != null && json.vendorOnboardTemplateId != 'undefined'){
	        				$("#templateId").empty();
	        				var templatedIds = json.vendorOnboardTemplateId;
	        				var internalOutput = "";
	        				<c:forEach var="template" items="${templatesList}">
		        			  var existed = 0;
			        			
		        			    var template_array = templatedIds.split(',');
			        			for(var i = 0; i < template_array.length; i++) {
				        			if(template_array[i] == "${template.id}"){
					        			existed = 1;
					        			internalOutput = internalOutput+"<option value='" +template_array[i]+ "' selected>${template.templateName}</option>";
				        			}
			        			}
		        			if(existed == 0){
		        			    internalOutput = internalOutput+"<option value='${template.id}'>${template.templateName}</option>";
		        			}
		        			</c:forEach>
		        			$("#templateId").append(internalOutput);
     		        		$("#templateId").trigger("chosen:updated");
	        			}
	        			
	        			
	        			 $("input[name=firstName]").val(json.firstName);
	        			 if(json.lastName != null && json.lastName != 'undefined' && json.lastName != ''){
	        				 $("input[name=lastName]").val(json.lastName);
	        			 }
	        			
	        			 $("input[name=email]").val(json.email);
	        			 
	        			 if(json.phoneNumber != null && json.phoneNumber != 'undefined' && json.phoneNumber != ''){
	        				 $("input[name=phoneNumber]").val(json.phoneNumber);
	        			 }
	        			 
	        			 if(json.extensionNumber != null && json.extensionNumber != 'undefined' && json.extensionNumber != ''){
	        				 $("input[name=extensionNumber]").val(json.extensionNumber);
	        			 }
	        			
	        			

	        			if(json.clientContactIds != null && json.clientContactIds != 'undefined'){
	        				$("#owner").empty();
	        				var clientContactIds = json.clientContactIds;
	        				var internalOutput = "";
	        				<c:forEach var="user" items="${userList}">
		        			  var existed = 0;
			        			
		        			    var user_array = clientContactIds.split(',');
			        			for(var i = 0; i < user_array.length; i++) {
				        			if(user_array[i] == "${user.id}"){
					        			existed = 1;
					        			internalOutput = internalOutput+"<option value='" +user_array[i]+ "' selected>${user.fullName}</option>";
				        			}
			        			}
		        			if(existed == 0){
		        			    internalOutput = internalOutput+"<option value='${user.id}'>${user.fullName}</option>";
		        			}
		        			</c:forEach>
		        			$("#owner").append(internalOutput);
     		        		$("#owner").trigger("chosen:updated");
	        			}
	        			
	        			
        			   $("select[name=spocStatus]").val(json.spocStatus);
	        		   
	      			   $("#loading").removeAttr("style");
			        
		        },
		    }); 
	        
	        document.getElementById("update_vendor").style.display="";
	    	document.getElementById("save_vendor").style.display="none";
     } 
}
</script>


<style>
.clsDatePicker {
	z-index: 100000 !important;
}

.ui-front {
	z-index: 9999999;
}
</style>




<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Vendor</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='/vendorsList'/>"> Vendor List</a> &raquo; Create
			Vendor
		</div>
	</section>
	
	<div id="loading"></div>
	<div class="newPrDetails newPrDetailsPMO"> 
      		<div class="newPrTabs">
   			  <ul class="newPrTabsMenu">
				<li class="current" onclick="getVendorDetails();"><a href="#tab-a" data-toggle="tab">Vendor Details</a></li>
				<li><a href="#tab-b" data-toggle="tab" id="vendorDocuments">Documents</a></li>
				<li><a href="#tab-c" data-toggle="tab" id="vendorTasks">Tasks</a></li>
				<div class="clearfix mb"></div>
			  </ul>
			  <div class="clearfix"></div>
			  
		 <div class="project_tabs">
		 <div class="clearfix form-area form-horizontal toggle-div remove-div  newPrTabs_inn mt" id="tab-a">
			 <form:form id="client_form" name="client_form" method="post">
			<div class="clearfix form-horizontal inner-content">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
					
					 <input type="hidden" name="vendorOrgLocationId" id="vendorOrgLocationId" value=""/>
				     <input type="hidden" name="orgRelationId" id="orgRelationId" value=""/>
				     <input type="hidden" name="vendorOnboardId" id="vendorOnboardId" value=""/>
					 
						<div class="form-group">
							<label class="col-md-3" for="Project Id">Vendor Name<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9" id="clientNameDiv">
								<input type="text" class="form-control" id="orgName"
									name="orgName" value="${orgName}" onclick="getOrgName();"
									onblur="getOrgDetails(); validateOrgName();"
									field-name="Vendor Name"
									data-validation="required validate_length length2-250"
									value=""> <input type="hidden" id="orgLocationId" /> <span
									class="hidden">Enter Vendor Name</span>
							</div>
						</div>

						<div class="form-group " id="companyWebsiteDiv">
							<label class="col-md-3">Website</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="webSite"
									name="webSite" value="" field-name="Vendor Website"
									onkeyup="validateWebsite();"
									data-validation="validate_Space validate_domain validate_length length2-100"
									data-validation-optional="true">
							</div>
						</div>

						<div class="form-group" id="locationDiv" style="display: block;">
							<label class="col-md-3">Location<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9" id="locationField">
								<div class="positionRelative">
									<input class="form-control positionRelative"
										id="clientLocation" name="location"
										placeholder="Enter your Location" type="text"
										field-name="Location" data-validation="required"></input> <i
										class="fa fa-refresh btn btn-primary refresh-icon"
										title="Refresh"
										onClick="refreshGoogleApiLocation('clientLocation','clientCountry','clientState','clientCity','clientZipCode');"></i>
								</div>
							</div>
						</div>
						<div class="form-group" id="orgLocationDiv" style="display: none;">
							<label class="col-md-3">Location</label>
							<div class="col-md-9">
								<div class="positionRelative">
									<input class="form-control positionRelative" id="orgLocation"
										name="orgLocation" placeholder="Enter your Location"
										type="text" field-name="Location" readonly></input>
								</div>
							</div>
						</div>

						<div class="form-group" id="countryDiv" style="display: block;">
							<label class="col-md-3">Country</label>
							<div class="col-md-9">
								<input class="form-control" id="clientCountry" name="country"
									placeholder="Enter your Country" value="${loginCountryName}"
									type="text" readonly></input>
							</div>
						</div>

						<div class="form-group" id="orgCountryDiv" style="display: none;">
							<label class="col-md-3">Country</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="orgCountry"
									name="orgCountry" value="" readonly="readonly">
							</div>
						</div>

						<div class="form-group" id="stateDiv" style="display: block;">
							<label class="col-md-3">State</label>
							<div class="col-md-9">
								<input class="form-control" id="clientState" name="state"
									placeholder="Enter your State" type="text" readonly></input>
							</div>
						</div>

						<div class="form-group" id="orgStateDiv" style="display: none;">
							<label class="col-md-3">State</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="orgState"
									placeholder="Enter your State" name="orgState" value=""
									readonly="readonly">
							</div>
						</div>

						<div class="form-group" id="cityDiv" style="display: block;">
							<label class="col-md-3">City</label>
							<div class="col-md-9">
								<input class="form-control" id="clientCity" name="city"
									placeholder="Enter your City" type="text" readonly></input>
							</div>
						</div>

						<div class="form-group" id="orgCityDiv" style="display: none;">
							<label class="col-md-3">City</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="orgCity"
									placeholder="Enter your City" name="orgCity" value=""
									readonly="readonly">
							</div>
						</div>

						<div class="form-group" id="zipCodeDiv" style="display: block;">
							<label class="col-md-3">ZipCode</label>
							<div class="col-md-9">
								<input class="form-control" id="clientZipCode" name="zipcode"
									placeholder="Enter your Zip code" type="text" readonly onclick="zipcodeMask('${loginCountryName}','clientZipCode');"></input>
							</div>
						</div>

						<div class="form-group" id="orgZipCodeDiv" style="display: none;">
							<label class="col-md-3">ZipCode2</label>
							<div class="col-md-9">
								<input class="form-control" id="orgZipCode" name="orgZipcode"
									placeholder="Enter your Zip code" type="text" readonly ></input>
							</div>
						</div>

						<div class="form-group hidden">
							<label class="col-md-3">Lat</label>
							<div class="col-md-9">
								<input class="form-control" id="clientLatitude" name="latitude"
									type="text"></input>
							</div>
						</div>

						<div class="form-group hidden">
							<label class="col-md-3">Long</label>
							<div class="col-md-9">
								<input class="form-control" id="clientLongitude"
									name="longitude" type="text"></input>
							</div>
						</div>




						<div class="form-group">
							<label class="col-md-3">Tier</label>
							<div class="col-md-9">
								<select class="form-control" id="level" name="level">
									<option value="Tier-1">Tier-1</option>
									<option value="Tier-2">Tier-2</option>
									<option value="Tier-3">Tier-3</option>
									<option value="Tier-4">Tier-4</option>
									<option selected="selected" value="Tier-5">Tier-5</option>
								</select>
							</div>
						</div>

						<div class="form-group ">
							<label class="col-md-3">Address</label>
							<div class="col-md-9">
								<textarea id="addressId" name="address" rows="4"
									field-name="Address" class="form-control"
									data-validation="validate_length length2-250"
									data-validation-optional="true"></textarea>
							</div>
						</div>

						<div class="form-group ">
							<label class="col-md-3">Billing Address</label>
							<div class="col-md-9">
								<textarea id="billingAddressId" rows="4" name="billingAddress"
									field-name="Billing Address" class="form-control"
									data-validation="validate_length length2-250"
									data-validation-optional="true"></textarea>
							</div>
						</div>

						<div class="clearfix mb"></div>
						<div class="form-group">
							<label class="col-md-3">Payment Term</label>
							<div class="col-md-9">
								<select class="form-control" id="PaymentTermId"
									name="paymentTerms">
									<option selected="selected" disabled value="">Select
										Payment Term</option>
									<option value="10 days">10 days</option>
									<option value="15 days">15 days</option>
									<option value="30 days">30 days</option>
									<option value="45 days">45 days</option>
									<option value="60 days">60 days</option>
									<option value="90 days">90 days</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-3">Onboard Templates</label>
							<div class="col-md-9 candAdvsrch">
								<select data-placeholder="Select Templates" multiple
									class="chosen-select form-control" name="templateId" id="templateId"
									field-name="Template" data-validation-optional="true">
									<c:forEach var="template" items="${templatesList}">
										<option value="${template.id}">${template.templateName}</option>
									</c:forEach>
								</select>
							</div>
						</div>

					</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
						<h3>Contact Details</h3>

						<div class="form-group" id="EmailIdDiv">
							<label class="col-md-3" for="Project Manager">Email<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="email" name="email"
									value="${emailId}" field-name="Email Id"
									data-validation="required validate_Space validate_length length1-100 validate_email"
									onblur="validateEmail();getSpocUserDetails();"> <span
									class="hidden">Valid Email</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">First Name<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="firstName"
									name="firstName" pattern="[A-Za-z\s]*" maxlength="100" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100">

							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Last Name</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="lastName"
									name="lastName" pattern="[A-Za-z\s]*" maxlength="100" data-validation-optional="true" field-name="Last Name" data-validation="validate_Space validate_AlphaNumber validate_length length1-100">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Phone Number</label>
							<div class="col-md-9">
							 <div class="row">
							 <div class="col-md-7 paddingRight">
								<input type="text" class="form-control" name="phoneNumber"
									id="phoneNumber"
									data-validation="validate_Space validate_length length9-20 validate_phone"
									data-validation-optional="true">
							  </div>
							  
							 <div class="col-md-5 paddingLeft">
				             <div class="row">
				             <label class="col-md-5">Extension </label>
				             <div class="col-md-7">
				             <input type="text" class="form-control" id="extensionNumber" name="extensionNumber" data-validation="validate_Space validate_length length4-5" data-validation-optional="true">
				             </div>
				             </div>
			             </div>
							  
							  		
							</div>		
							</div>
						</div>
						
						
						<div class="form-group">
							<label class="col-md-3">Status</label>
							<div class="col-md-9">
								<select class="form-control" id="spocStatus"
									name="spocStatus">
									<option value="Active">Active</option>
									<option value="In-Active">In-Active</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Owner Name<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9 candAdvsrch">
								<select data-placeholder="select owner" multiple
									class="chosen-select form-control" name="owner" id="owner"
									field-name="Owner" data-validation="required">
									<c:forEach var="user" items="${userList}">
										<option value="${user.id}"
											${user.id  == SourceLeadWeb_userId ? 'selected' : ''}>${user.fullName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="clearfix mb"></div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
						<!-- <div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9 checkBox">
								<label> <input type="checkbox" value="Y"
									name="isMailSent" checked="checked"> Send Invitation
								</label>
							</div>
						</div> -->

						

						<div class="form-group" id="userRoleDiv" style="display: none;">
							<label class="col-md-3">Role <span class="text-danger">&nbsp;
									*</span></label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="userRoleText"
									readonly="readonly"> <input type="hidden" id="userRole"
									name="roleTypeId" value="" />

							</div>
						</div>

					</div>
				</div>
			</div>
			<div class="form-footer clearfix">
				<div class="col-lg-12">
					<div class="pull-right">
						<input type="button" value="Save & Continue" id="save_vendor"
							onclick="saveVendor(); flushSaveClient()" class="btn btn-primary">
						
						<input type="button" value="Save & Continue" id="update_vendor" style="display: none"
							onclick="updateVendor(); flushSaveClient()" class="btn btn-primary">
						
						<input type="button" value="Cancel"
							onClick="javascript:window.location.href = '<c:url value='/vendorsList'/>'"
							class="btn btn-default ml">
					</div>
				</div>
			</div>
		</form:form>
			  </div>
	   <jsp:include page="vendorOnboardDocuments.jsp" />
	   <jsp:include page="vendorOnboardTasks.jsp" />
		  </div>
		</div>
	</div>

	


</section>
<!-- Content Area Ends-->

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->



<script type="text/javascript">
function getOrgName(){
	var orgCode = '${SourceLeadWeb_orgCode}';
	$( "#orgLocationId" ).val( '' );
	
	$("#webSite").attr("disabled", false);
	$("#webSite" ).val(''); 
	
	$("#orgCountry").val('');
	$("#orgState").val('');
	$("#orgCity").val('');
	$("#orgCity").val('');
	$("#orgLocation").val('');
	$("#orgZipCode").val('');
	
	document.getElementById("countryDiv").style.display="";
	document.getElementById("orgCountryDiv").style.display="none";
	
	document.getElementById("stateDiv").style.display="";
	document.getElementById("orgStateDiv").style.display="none";
	
	document.getElementById("cityDiv").style.display="";
	document.getElementById("orgCityDiv").style.display="none";
	
	document.getElementById("locationDiv").style.display="";
	document.getElementById("orgLocationDiv").style.display="none";
	
	document.getElementById("zipCodeDiv").style.display="";
	document.getElementById("orgZipCodeDiv").style.display="none";
	
	/* $("#role" ).val('');
	$("#role").attr("data-validation", "required");
	document.getElementById("roleDiv").style.display=""; */
	document.getElementById("userRoleDiv").style.display="none";
	
	$.ajax({                           
    	type: "POST",
    	url : sourceleadUrl+"/getOrgs?"+tokenParamName+"="+tokenValue+"&orgCode="+orgCode,
        success: function(data) {
        	var json = JSON.parse(data);
        	var org = [];
        	for(var i=0;i<json.length;i++){
       		 var label = json[i][0]; 
       		 var value = json[i][1];
       	     tmp = {
       	        'label': label,
       	        'value': value
       	      };
       	  	org.push(tmp);
       		}
        	$("#orgName").autocomplete({
                
                minLength: 0,
                source: org,
                focus: function( event, ui ) {
                       $( "#orgName" ).val( ui.item.label );
                       $( "#orgLocationId" ).val( ui.item.value );
                       return false;
                   },
                select: function( event, ui ) {
                    return false;
                }  
             })
        },
        error: function(e){
        }
    });
}
</script>


<script type="text/javascript">
function getOrgDetails()
{
	var orgName = document.getElementById("orgName").value;
	orgName = encodeURIComponent(orgName);
	var orgLocationId = document.getElementById("orgLocationId").value;
	if(orgName){
   	 $.ajax({
   	    	type: "POST",
   	    	url : sourceleadUrl+"/getOrgDetails?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&orgName="+orgName,
   	        contentType: "application/json; charset=utf-8",
   	        dataType: "json",
   	        success: function(data) {
   	        	if(data != null && data != ''){
   	        		var json = JSON.parse(data);
   	        		if(json['country'] != null && json['country'] != ''){
   	        			document.getElementById("countryDiv").style.display="none";
   	        			document.getElementById("orgCountryDiv").style.display="";
   	        		    $("#orgCountry").val(json['country'] );
   	        		}
   	        		
   	        		if(json['state'] != null && json['state'] != ''){
        			document.getElementById("stateDiv").style.display="none";
        			document.getElementById("orgStateDiv").style.display="";
  	        		    $("#orgState").val(json['state']);
  	        		}
   	        		if(json['city'] != null && json['city'] != ''){
	       			document.getElementById("cityDiv").style.display="none";
	       			document.getElementById("orgCityDiv").style.display="";
   	        		    $("#orgCity").val(json['city']);
   	        		}
		   	        
			      	if(json['website'] != null && json['website'] != ''){
			      		$("#webSite").val("www."+json['website']);
			      		$("#webSite").attr("disabled", true);
			      	}
			      	
			      	if(json['location'] != null && json['location'] != ''){
			      	$('#clientLocation').attr("data-validation-optional", "true");  
			      	document.getElementById("locationDiv").style.display="none";
   	        		document.getElementById("orgLocationDiv").style.display="";
   	        		    $("#orgLocation").val(json['location']);
			      	}
			      	var zipCode = json['zipCode'];
			      	if(zipCode != null && zipCode != ""){
			      		document.getElementById("zipCodeDiv").style.display="none";
	   	        		document.getElementById("orgZipCodeDiv").style.display="";
	   	        	    $("#orgZipCode").val(json['zipCode']);
			      	}
			      	
   	        	}
   			 },
   	        error: function(e){
   	        }
   	    });
   	 
   	validateEmail();
	getSpocUserDetails();
	
	}
	
}
</script>



<script type="text/javascript">
function validateEmail(){
	var email = document.getElementById("email").value;
	if(email != null){
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/validateClientOrVendorEmail?"+tokenParamName+"="+tokenValue+"&email="+email,
			success : function(data) {
				var result = data;
				if(result == 'Invalid Email') {
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					$("input#email").after( "<p class='jquery_form_error_message'>Email should not your org user email</span>");
					document.getElementById("email").setAttribute( 'record-exist', 'yes');
					document.getElementById("email").setAttribute( 'record-exist-errorMsg', "Email should not your org user email");
					
				}else if(result == 'Valid Email') {
					$('input#email').removeAttr("class record-exist record-exist-errormsg");
					$('input#email').attr('class','valid form-control');
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
				}
			},

		});
	}
}
</script>


<!-- <script type="text/javascript">
function getRolesList(){
	var orgName = document.getElementById("orgName").value;
	var orgLocationId = document.getElementById("orgLocationId").value;
	var sourceleadUrl ='${sourceleadUrl}';
	
	if(orgName){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getRolesList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&moduleType=ATS", 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-- Select Role --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.displayName+"</option>";
				}
	        	$("#role").empty();
		        $("#role").append(output);
		        $('#role').trigger("chosen:updated");
		        $("#loading").removeAttr("style"); 
	        },
	    });
	}else{
		var output = "";
    	 output = output + "<option value=''>-- Select Role --</option>"; 
    	 $("#role").empty();
	     $("#role").append(output);
	     $('#role').trigger("chosen:updated");
	}
	 
}
</script> -->

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

<script type="text/javascript">
	function saveVendor() {
		if ($('#client_form').validate(false, validationSettings)) {
			var orgLocationId = document.getElementById("orgLocationId").value;
		  	var formData = $($('#client_form')[0]).serialize();
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
					$.post(sourceleadUrl+"/saveVendor?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&responderType=Vendor", formData, function(data) {
						$("#vendorOrgLocationId").val(data.id); 
						$("#orgRelationId").val(data.vendorOrgRelationId);
						$("#vendorOnboardId").val(data.vendorOnboardId);
						$("#loading").removeAttr("style");
						 var vendorDocuments = document.getElementById("vendorDocuments");
						 $("#vendorDocuments").removeClass("disabledLink");
						 vendorDocuments.href="#tab-b";
						 vendorDocuments.addEventListener("click", function() {
							 getOnboardDocuments();
						  });
						 vendorDocuments.click();
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#client_form', function() {
		$("#client_form").showHelpOnFocus();
		$("#client_form").validateOnBlur(false, validationSettings);
	});
</script>


<script type="text/javascript">

function updateVendor(){
	
	if ($('#client_form').validate(false, validationSettings)) {
		var orgRelationId = document.getElementById("orgRelationId").value;
	  	var formData = $($('#client_form')[0]).serialize();
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				$.post(sourceleadUrl+"/updateVendor?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId, formData, function(data) {
					 $("#loading").removeAttr("style");
					 var vendorDocuments = document.getElementById("vendorDocuments");
					 $("#vendorDocuments").removeClass("disabledLink");
					 vendorDocuments.href="#tab-b"; 
					 
					 vendorDocuments.addEventListener("click", function() {
						 getOnboardDocuments();
					});
					 
					 vendorDocuments.click();
					 
				}); 
		return true;
	} else {
		return false;
	}
}
$('body').on('blur', '#client_form', function() {
	$("#client_form").showHelpOnFocus();
	$("#client_form").validateOnBlur(false, validationSettings);
});
</script>


<script type="text/javascript">
function getSpocUserDetails(){
	var email = document.getElementById("email").value;
	var orgLocationId = document.getElementById("orgLocationId").value;
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getSpocUserDetails?"+tokenParamName+"="+tokenValue+"&email="+email+"&orgLocationId="+orgLocationId,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		 if(json[0] != null && json[0] != ''){
	        			$("#firstName").attr("disabled", true);
	        		    $("#firstName").val(json[0]);
	        		 }else{
	        			 $("#firstName").attr("disabled", false);
	        			 //$("#firstName").val('');
	        		 }
	        		 
	        		 if(json[1] != null && json[1] != ''){
		        			$("#lastName").attr("disabled", true);
		        		    $("#lastName").val(json[1]);
	        		 }else{
	        			    $("#lastName").attr("disabled", false);
	        			    //$("#lastName").val('');
	        		 }
		        		
	        		 if(json[2] != null && json[2] != ''){
		        			$("#phoneNumber").attr("disabled", true);
		        		    $("#phoneNumber").val(json[2]);
	        		 }else{
	        			    $("#phoneNumber").attr("disabled", false);
	        			    //$("#phoneNumber").val('');
	        		 }
	        		 
	        		 
	        		 if(json[3] != null && json[3] != ''){
		        			/* $("#role").removeAttr("data-validation");
		        			$("#role" ).val('');
	        			    document.getElementById("roleDiv").style.display="none"; */
	   	        			document.getElementById("userRoleDiv").style.display="";
	   	        			$("#userRole").val(json[3]);
	   	        			$("#userRoleText").val(json[4]);
		        		 }else{
		        			/*  $("#role").attr("data-validation", "required"); */
		        			 $("#userRole" ).val('');
		        			/*  document.getElementById("roleDiv").style.display=""; */
		        			 document.getElementById("userRoleDiv").style.display="none";
		        		 }
	        	}
			 },
	        error: function(e){
	        }
	    });
	}else{
		$("#firstName").attr("disabled", false);
	    $("#firstName").val('');
	    
	    $("#lastName").attr("disabled", false);
	    $("#lastName").val('');
	    
	    $("#phoneNumber").attr("disabled", false);
	    $("#phoneNumber").val('');
	    
	   /*  $("#role").attr("data-validation", "required"); */
	    $("#userRole" ).val('');
	    /* document.getElementById("roleDiv").style.display=""; */
		document.getElementById("userRoleDiv").style.display="none";
	   
	}
	
}
</script>

<script type="text/javascript">
function validateClientName(){
	
	var clientName = (document.getElementById("orgName").value).replace(/[{}]/g, "");
	var vendor = encodeURIComponent(clientName);
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	if(vendor.match(/^[^\s-](.*)*$/) != null){
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (vendor != null && vendor != '') {
		
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/ws/isClientNameValid?"+tokenParamName+"="+tokenValue+"&clientName="+vendor+"&responderType=Vendor",
			success : function(data) {
				var result = data;
				if (result == 'true') {
					//duplicate client
					$("#save_vendor").removeAttr("disabled");
			        $("#save_vendor").attr("disabled", "disabled");
					$("input#orgName").after( "<p class='jquery_form_error_message'>" + clientName + " is already existed as vendor</span>");
					document.getElementById("orgName").setAttribute( 'record-exist', 'yes');
					document.getElementById("orgName").setAttribute( 'record-exist-errorMsg', clientName +"  already existed as vendor");
					$('input#orgName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else if(result == 'sameorg') {
					//duplicate client
					$("#save_vendor").removeAttr("disabled");
			        $("#save_vendor").attr("disabled", "disabled");
					$("input#orgName").after( "<p class='jquery_form_error_message'>" + clientName + " is already existed as sameorg</span>");
					document.getElementById("orgName").setAttribute( 'record-exist', 'yes');
					document.getElementById("orgName").setAttribute( 'record-exist-errorMsg', clientName +"  already existed as sameorg");
					$('input#orgName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else {
					//New WebSite class="form-control"
					//$("#save_vendor").removeAttr("disabled");
					$("#save_vendor").removeAttr("disabled");
					$('input#orgName').removeAttr("class record-exist record-exist-errormsg");
					$('input#orgName').attr('class','valid form-control');
					$('#clientNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_vendor").click(function() {
						/* if ($(this).validate(false, validationSettings)) {
						} */
					});
				}
				
				$("#loading").removeAttr("style");
			},

		});
	}
  }
}
</script>





<script type="text/javascript">
function validateOrgName(){
	
	var vendorName = (document.getElementById("orgName").value).replace(/[{}]/g, "");
	var vendor = encodeURIComponent(vendorName);
	if(vendor.match(/^[^\s-](.*)*$/) != null){
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (vendor != null && vendor != '') {
		
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/ws/isInvitedClientOrVendorOrgValid?"+tokenParamName+"="+tokenValue+"&orgName="+vendor,
			success : function(data) {
				var result = data;
				if (result == 'invalid') {
					$("#save_vendor").removeAttr("disabled");
			        $("#save_vendor").attr("disabled", "disabled");
					$("input#orgName").after( "<p class='jquery_form_error_message'>" + vendorName + " is invalid</span>");
					document.getElementById("orgName").setAttribute( 'record-exist', 'yes');
					document.getElementById("orgName").setAttribute( 'record-exist-errorMsg', vendorName +"  is invalid");
					$('input#orgName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else {
					$("#save_vendor").removeAttr("disabled");
					$('input#orgName').removeAttr("class record-exist record-exist-errormsg");
					$('input#orgName').attr('class','valid form-control');
					$('#clientNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_vendor").click(function() {
					});
					
					validateClientName();
				}
				
			},

		});
	}
  }
}
</script>



<script type="text/javascript">
	function validateWebsite() {
		var website = document.getElementById("webSite").value;
		if(website.match(/^[^\s-](.*)*$/) !== null){
		var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
		
		if (website != null && website != '') {

			$.ajax({
				type : "GET",
				url : sourceleadUrl + "/ws/validateClientWebsite?website="+ website + "&sourceleadBLockedwebsites="+ sourceleadBLockedwebsites,
				success : function(data) {
					var result = data;
					if (result == "Website Duplicated" || result == "Invalid Website") {
						//existed website
						$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
						$("input#webSite").after("<p class='jquery_form_error_message'>"+ website+ " is "+result+"</span>");
						document.getElementById("webSite").setAttribute('record-exist', 'yes');
						document.getElementById("webSite").setAttribute('record-exist-errorMsg',website + ' is '+result);
						$('input#webSite').removeAttr( "record-exist record-exist-errormsg");
						
					} else {
						//New WebSite class="form-control"
						$('input#webSite').removeAttr("class record-exist record-exist-errormsg");
						$('input#webSite').attr('class','valid form-control');
						$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
				
					}
				},

			});
		}
	  }
	}
</script>

<script type="text/javascript">
	function flushSaveClient() {
		$("#save_vendor").removeAttr("disabled");
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
function getAllCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/sourceLeadCountryAutoComplete?"+tokenParamName+"="+tokenValue+"",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---select---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == '${loginCountryName}'){
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
} 
</script>


<script>
function getStates(){
	var countryName = $("#orgCountry").val();
	if(countryName == null || countryName == 'undefined' || countryName == ''){
		countryName = $("#country").val();
	}
	
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select state---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').append('<option value="">---select city---</option>');
	            $('#city').trigger("chosen:updated");
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
	var countryName = $("#orgCountry").val();
	if(countryName == null || countryName == 'undefined' || countryName == ''){
		countryName = $("#country").val();
	}
	
	var stateName = $("#orgState").val();
	if(stateName == null || stateName == 'undefined' || stateName == ''){
		stateName = $("#state").val();
	}
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select city---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
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
	       $("#owner").chosen();
	       
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
