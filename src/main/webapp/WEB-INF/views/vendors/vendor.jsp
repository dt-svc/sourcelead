<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript">
window.onload = function () {
	initLocationAuto('vendorLocation','vendorCountry','vendorState','vendorCity','vendorZipCode','clientLatitude','clientLongitude','invite','addressId');
 };
</script>
<script>
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
</script>

<script>
	$(document).ready(function() {
		document.getElementById("client_form").reset();
		
		var orgRelationId = '${orgRelationId}';
		
		<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
		  var sectionId = '${section.id}';
		  getCustomDependentField(sectionId);
		</c:forEach>
		
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
			/*  vendorDocuments.addEventListener("click", function() {
				 getOnboardDocuments();
			  }); */
			
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
				/*  vendorTasks.addEventListener("click", function() {
					 getVendorOnboardTasksList();
				  }); */
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
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var vendorOrgLocationId = document.getElementById("vendorOrgLocationId").value;
	var orgRelationId = document.getElementById("orgRelationId").value;
	if(orgRelationId){
		$("#vendorDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			
	        $.ajax({
		    	type: "GET",
		    	 url: vmsUrl+"/getOrgRelationDetailsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId, 
		        success: function(json) {
		        	
		        	   $("input[name=orgName]").val(json.orgName);
		        	   $("input[name=orgName]").attr("readonly","readonly");
	        		   $('#orgName').removeAttr('onblur');
		        	   var isLawFirm = json.isLawFirm;
		        	   var isAccountingFirm = json.isAccountingFirm;
	        		   $("#isLawFirm").attr('checked', isLawFirm);
				        $("#isAccountingFirm").attr('checked', isAccountingFirm);
				        $("#isLawFirm").val(isLawFirm?'Y':'N');
				        $("#isAccountingFirm").val(isAccountingFirm?'Y':'N');
	        		   
	        		   if(json.webSite != null && json.webSite != 'undefined' && json.webSite != ''){
	        			   $("input[name=webSite]").val(json.webSite);
	        		   }
	        		   
	        		   $("input[name=location]").val(json.vendorOrgLocation);
	        		   $("#vendorCountry").val(json.country);
	        		   $("#vendorCountry").trigger("chosen:updated"); 
	        		   
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
	        		   
	      			   $("#vendorDetailsLoading").removeAttr("style");
			        
		        },
		    }); 
	        
	        document.getElementById("update_vendor").style.display="";
	    	document.getElementById("save_vendor").style.display="none";
	    	allowIsLawFirm();
     } 
}
</script>


<style>
.ui-front { z-index: 9999999; }
</style>




<!-- Content Area -->
<section class="content-wraper">
<input type="hidden" id="actionId" value="${actionId}">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Vendor</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>&raquo;<a
				href="${SourceLeadWeb_vmsUrl}/vendorsList"> Vendor List</a> &raquo; Create
			Vendor
		</div>
	</section>
	
	<div id="vendorDetailsLoading"></div>
	<div class="newPrDetails newPrDetailsPMO"> 
      		<div class="newPrTabs">
   			  <ul class="newPrTabsMenu">
				<li class="current" onclick="getVendorDetails();"><a href="#tab-a" data-toggle="tab">Vendor Details</a></li>
				<li><a href="#tab-b" data-toggle="tab" id="vendorDocuments" onclick="getOnboardDocuments();">Documents</a></li>
				<li><a href="#tab-c" data-toggle="tab" id="vendorTasks" onclick="getVendorOnboardTasksList();">Tasks</a></li>
				<div class="clearfix mb"></div>
			  </ul>
			  <div class="clearfix"></div>
			  
		 <div class="project_tabs projectTabs_brdnone">
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
									data-validation="required validate_length length2-100"
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
							<label class="col-md-3">Address<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9" id="locationField">
								<div class="positionRelative">
									<input class="form-control positionRelative"
										id="vendorLocation" name="location"
										placeholder="Enter Address" type="text"
										field-name="Address" data-validation="required"></input> <i
										class="fa fa-refresh btn btn-primary refresh-icon"
										title="Refresh"
										onClick="refreshGoogleApiLocation('vendorLocation','vendorCountry','vendorState','vendorCity','vendorZipCode','addressId');"></i>
								</div>
							</div>
						</div>
						<div class="form-group" id="orgLocationDiv" style="display: none;">
							<label class="col-md-3">Address</label>
							<div class="col-md-9">
								<div class="positionRelative">
									<input class="form-control positionRelative" id="orgLocation"
										name="orgLocation" placeholder="Enter your Address"
										type="text" field-name="Address" readonly></input>
								</div>
							</div>
						</div>

                        <div class="form-group ">
							<label class="col-md-3">Street Address</label>
							<div class="col-md-9">
								<input type="text" id="addressId" name="address"   field-name="Street Address" class="form-control" placeholder="Enter Street Addres" data-validation="validate_length length2-250" data-validation-optional="true"/>
							</div>
						</div>
						
						<div class="form-group" id="cityDiv" style="display: block;">
							<label class="col-md-3">City</label>
							<div class="col-md-9">
								<input class="form-control" id="vendorCity" name="city" placeholder="Enter City" type="text"  ></input>
							</div>
						</div>

						<div class="form-group" id="orgCityDiv" style="display: none;">
							<label class="col-md-3">City</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="orgCity" placeholder="Enter your City" name="orgCity" value="" readonly="readonly">
							</div>
						</div>
						
						<div class="form-group" id="stateDiv" style="display: block;">
							<label class="col-md-3">State</label>
							<div class="col-md-9">
								<input class="form-control" id="vendorState" name="state" placeholder="Enter State" type="text" ></input>
							</div>
						</div>

						<div class="form-group" id="orgStateDiv" style="display: none;">
							<label class="col-md-3">State</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="orgState" placeholder="Enter your State" name="orgState" value="" readonly="readonly">
							</div>
						</div>

						

						<div class="form-group" id="zipCodeDiv" style="display: block;">
							<label class="col-md-3">ZipCode</label>
							<div class="col-md-9">
								<input class="form-control" id="vendorZipCode" name="zipcode"
									placeholder="Enter Zip code" type="text"   onclick="zipcodeMask('${loginCountryName}','vendorZipCode');"></input>
							</div>
						</div>

						<div class="form-group" id="orgZipCodeDiv" style="display: none;">
							<label class="col-md-3">ZipCode</label>
							<div class="col-md-9">
								<input class="form-control" id="orgZipCode" name="orgZipcode"
									placeholder="Enter your Zip code" type="text" readonly ></input>
							</div>
						</div>
						
						<div class="form-group" id="countryDiv" style="display: block;">
							<label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9">
							<select class="form-control chosen-select" name="country" id="vendorCountry" field-name="Country" data-validation="required" >
				                   <option  ${loginCountryName  == 'Australia' ? 'selected' : ''}>Australia</option>
				                   <option  ${loginCountryName  == 'France' ? 'selected' : ''}>France</option>
				                   <option  ${loginCountryName  == 'India' ? 'selected' : ''}>India</option>
				                   <option  ${loginCountryName  == 'Singapore' ? 'selected' : ''}>Singapore</option>
				                   <option  ${loginCountryName  == 'United Kingdom' ? 'selected' : ''}>United Kingdom</option>
				                   <option  ${loginCountryName  == 'United States' ? 'selected' : ''}>United States</option>
			                 </select>
							</div>
						</div>

						<div class="form-group" id="orgCountryDiv" style="display: none;">
							<label class="col-md-3">Country</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="orgCountry" name="orgCountry" value="" readonly="readonly">
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
						<div class="clearfix mb">
							<label class="col-md-3">Tier <i class='fa fa-info-circle' title='Classify the vendors basing on their performance'></i></label>
							<div class="col-md-9 dropdown-select">
								<select class="form-control" id="level" name="level">
									<option value="Tier-1">Tier-1</option>
									<option value="Tier-2">Tier-2</option>
									<option value="Tier-3">Tier-3</option>
									<option value="Tier-4">Tier-4</option>
									<option selected="selected" value="Tier-5">Tier-5</option>
								</select>
							</div>
							</div>
							
							<div class="clearfix width100Pecnt">
							<label class="col-md-3">Payment Term</label>
							<div class="col-md-9 dropdown-select">
								<select class="form-control" id="PaymentTermId"
									name="paymentTerms">
									<option value="">Select Payment Term</option>
									<option value="10 days">10 days</option>
									<option value="15 days">15 days</option>
									<option value="30 days">30 days</option>
									<option value="45 days">45 days</option>
									<option value="60 days">60 days</option>
									<option value="90 days">90 days</option>
								</select>
							</div>
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
							<label class="col-md-3">Onboard Templates</label>
							<div class="col-md-9 candAdvsrch">
								<select data-placeholder="Select Templates" multiple
									class="chosen-select form-control" name="templateId" id="templateId"
									field-name="Template" data-validation-optional="true">
									<c:forEach var="template" items="${templatesList}">
										<option value="${template.id}" ${template.isDefaultTemplate  == 'Y' ? 'selected' : ''}>${template.templateName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
					<div class="form-group ">
					<label class="col-md-3">Generate Bill </label>
					<div class="col-md-9">
					<select class="form-control selectpicker" id="generateBill" name="generateBill">
	                    <option value="Bulk">Bulk</option>
	                    <option value="Individual">Individual</option>
	                  </select>				
	                  </div>
				</div>
					
					<div class="clearfix mb"></div>	
					 <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
		               <c:if test="${sectionOrder.index == 0}">
		                  <div id="customDiv${section.id}"></div>
		               </c:if>
		            </c:forEach>
						
					</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
						<h3>Contact Details</h3>

						<div class="form-group" id="EmailIdDiv">
							<label class="col-md-3" for="Project Manager">Email<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9">
							<div class="input-group errorInptgroup">
								<input type="text" class="form-control" id="email" name="email"
									value="${emailId}" field-name="Email"
									data-validation="required validate_Space validate_length length1-100 validate_email"
									onblur="validateEmail();getSpocUserDetails();"> <span
									class="hidden">Valid Email</span>
									<span id="emailStatusIconDiv" class="input-group-addon">
			                    	<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>
			                  	</span>
								</div>	
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">First Name<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9" id="nameIdDiv">
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
							 <div class="col-md-6 paddingRight">
								<input type="text" class="form-control" name="phoneNumber"
									id="phoneNumber"
									data-validation="validate_Space validate_length length9-20 validate_phone"
									data-validation-optional="true">
							  </div>
							  
							 <div class="col-md-6 paddingLeft erMesMid">
				             <div class="row">
				             <label class="col-md-5">Extension </label>
				             <div class="col-md-7 posSta">
				             <input type="text" class="form-control" id="extensionNumber" name="extensionNumber" data-validation="validate_extno validate_length length1-5" data-validation-optional="true">
				             </div>
				             </div>
			             </div>
							  
							  		
							</div>		
							</div>
						</div>
						
						
						<div class="form-group">
							<label class="col-md-3">Status</label>
							<div class="col-md-9">
								<select class="form-control valid sel" id="spocStatus"
									name="spocStatus">
									<option value="Active">Active</option>
									<option value="In-Active">In-Active</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Owner Name<span
								class="text-danger">&nbsp;*</span><i class='fa fa-info-circle' title='The internal point of contact to the vendor. A single contact can have multiple owners'></i></label>
							<div class="col-md-9 candAdvsrch">
								<select data-placeholder="select owner" multiple
									class="chosen-select form-control" name="owner" id="owner"
									field-name="Owner Name" data-validation="required">
									<c:forEach var="user" items="${userList}">
										<option value="${user.id}"
											${user.id  == SourceLeadWeb_userId ? 'selected' : ''}>${user.fullName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
		             <label class="col-md-3"></label>
		              <div class="col-md-9">
		                <div class="checkbox checkBox">
		                  <label>
		                   <input id="isLawFirm" title="Is law firm" name="isLawFirm"  type="checkbox" value = "N" onclick = "allowIsLawFirm();">
		                   <!--  <input type="checkbox" value="N" name="isLawFirm"> -->
		                  Law firm</label>
		                </div>
		                 </div>
		                 </div>
		                 
		                 <div class="form-group">
		                 <label class="col-md-3"></label>
		              <div class="col-md-9">
		                <div class="checkbox checkBox">
		                  <label>
		                 <input id="isAccountingFirm" title="Is law firm" name="isAccountingFirm"  type="checkbox" value = "N" onclick = "allowIsAccountFirm();">
		                  
		                   Accounting Firm</label>
		                </div>
		                 </div>
               		</div>
               		
               		<div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkBox">
                 <label> <input type="checkbox" value="N" name="invoiceOrBillSent" id= "invoiceOrBillSent" onclick = "allowInvoiceEmailSent();"> Billing Email </label>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkBox">
                 <label> <input type="checkbox" value="N" name="apOrArSent" id="apOrArSent" onclick = "allowApEmailSent();"> AR Email </label>
              </div>
            </div>
              <div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkBox">
                 <label> <input type="checkbox" value="N" name="isBackgroundCheck" id="isBackgroundCheck" onclick = "allowBackgroundCheck();"> Background Check </label>
              </div>
            </div>  		
						<div class="clearfix"></div>	
					 <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
		               <c:if test="${sectionOrder.index == 1}">
		                  <div id="customDiv${section.id}"></div>
		               </c:if>
		            </c:forEach>
						
					</div>
					<div class="clearfix mb"></div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
						<!-- <div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9 checkBox">
								<label> <input type="checkbox" value="Y"
									name="isMailSent"> Send Invitation
								</label>
							</div>
						</div>  -->

						

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
							onclick="saveVendor(); saveVendorContact();flushSaveClient();" class="btn btn-primary">
						
						<input type="button" value="Save & Continue" id="update_vendor" style="display: none"
							onclick="updateVendor(); flushSaveClient()" class="btn btn-primary">
						
						<input type="button" value="Cancel"
							onClick="javascript:window.location.href = '${SourceLeadWeb_vmsUrl}/vendorsList'"
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


<script>

function allowIsLawFirm(){
	var checked = document.getElementById("isLawFirm").checked
	if(checked == true){
		$("#isLawFirm").val("Y");
	}else{
		$("#isLawFirm").val("N");
	}
}

function allowIsAccountFirm(){
	var checked = document.getElementById("isAccountingFirm").checked
	if(checked == true){
		$("#isAccountingFirm").val("Y");
	}else{
		$("#isAccountingFirm").val("N");
	}
}

function allowBackgroundCheck(){
	var checked = document.getElementById("isBackgroundCheck").checked
	if(checked == true){
		$("#isBackgroundCheck").val("Y");
	}else{
		$("#isBackgroundCheck").val("N");
	}
}
function getCustomDependentField(sectionId){
	 $.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getAllASectionCustomFields?sectionId="+sectionId,
				 success: function(data) {
		        	$('#customDiv'+sectionId).empty();
		        	$('#customDiv'+sectionId).append(data);
				 }
		  });
}


function allowApEmailSent(){
	var checked = document.getElementById("apOrArSent").checked
	if(checked == true){
		$("#apOrArSent").val("Y");
	}else{
		$("#apOrArSent").val("N");
	}
}


function allowInvoiceEmailSent(){
	var checked = document.getElementById("invoiceOrBillSent").checked
	if(checked == true){
		$("#invoiceOrBillSent").val("Y");
	}else{
		$("#invoiceOrBillSent").val("N");
	}
}

</script>


<script type="text/javascript">
function getOrgName(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var orgCode = '${SourceLeadWeb_orgCode}';
	$.ajax({                           
    	type: "POST",
    	url : vmsUrl+"/getOrgs?"+tokenParamName+"="+tokenValue+"&orgCode="+orgCode,
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
                	$( "#orgName" ).val( ui.item.label );
                    $( "#orgLocationId" ).val( ui.item.value );
			        $("#save_vendor").attr("disabled", "disabled");
                    $("#vendorDetailsLoading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
                	getOrgDetails(); 
                	validateOrgName();
                	$("input[name=address]").val('');
                	$("#vendorDetailsLoading").removeAttr("style");
                	$("#save_vendor").removeAttr("disabled");
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
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
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
	
	document.getElementById("userRoleDiv").style.display="none";
	
	var orgName = document.getElementById("orgName").value;
	orgName = encodeURIComponent(orgName);
	var orgLocationId = document.getElementById("orgLocationId").value;
	if(orgName){
   	 $.ajax({
   	    	type: "POST",
   	    	url : vmsUrl+"/getOrgDetails?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&orgName="+orgName,
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
			      	$('#vendorLocation').attr("data-validation-optional", "true");  
			      	document.getElementById("locationDiv").style.display="none";
   	        		document.getElementById("orgLocationDiv").style.display="";
   	        		    $("#orgLocation").val(json['location']);
			      	}
			      	var zipCode = json['zipCode'];
			      	if(zipCode != null && zipCode != ""){
			      		document.getElementById("zipCodeDiv").style.display="none";
	   	        		document.getElementById("orgZipCodeDiv").style.display="";
			      	}
			      	
   	        	}
   			 },
   	        error: function(e){
   	        }
   	    });
   	 
   /* 	validateEmail();
	getSpocUserDetails(); */
	
	}
	
}
</script>



<script type="text/javascript">
function validateEmail(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var email = document.getElementById("email").value;
	if(email != null && email != ''){
		$.ajax({
			type : "POST",
			url : vmsUrl + "/validateClientOrVendorEmails?"+tokenParamName+"="+tokenValue+"&email="+email,
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
					 validateEmailForExistenceUniversally(email, "input#email");
				}
			},

		});
	}else{
		$("#emailStatusIconDiv").find("i").remove();
  		$("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>');
	}
}
</script>




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
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		if ($('#client_form').validate(false, validationSettings)) {
			var orgLocationId = document.getElementById("orgLocationId").value;
		  	var formData = $($('#client_form')[0]).serialize();
			$("#vendorDetailsLoading").attr('style','position:absolute; width:100%; height:195%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
					$.post(vmsUrl+"/saveVendor?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&responderType=Vendor", formData, function(data) {
						$("#vendorOrgLocationId").val(data.id); 
						$("#orgRelationId").val(data.vendorOrgRelationId);
						$("#vendorOnboardId").val(data.vendorOnboardId);
						$("#vendorDetailsLoading").removeAttr("style");
						 var vendorDocuments = document.getElementById("vendorDocuments");
						 document.getElementById("vendorDocuments").removeAttribute("class");
						 vendorDocuments.href="#tab-b";
						/*  vendorDocuments.addEventListener("click", function() {
							 getOnboardDocuments();
						  }); */
						 
						 $("#vendorDocuments").click();
// 						 vendorDocuments.click();
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
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if ($('#client_form').validate(false, validationSettings)) {
		var orgRelationId = document.getElementById("orgRelationId").value;
	  	var formData = $($('#client_form')[0]).serialize();
		$("#vendorDetailsLoading").attr('style','position:absolute; width:100%; height:185%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				$.post(vmsUrl+"/updateVendor?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId, formData, function(data) {
					 $("#vendorDetailsLoading").removeAttr("style");
					 var vendorDocuments = document.getElementById("vendorDocuments");
					 $("#vendorDocuments").removeClass("disabledLink");
					 vendorDocuments.href="#tab-b"; 
					 
					/*  vendorDocuments.addEventListener("click", function() {
						 getOnboardDocuments();
					}); */
					 
					 $("#vendorDocuments").click();
					 
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
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var email = document.getElementById("email").value;
	var orgLocationId = document.getElementById("orgLocationId").value;
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : vmsUrl+"/getSpocUserDetails?"+tokenParamName+"="+tokenValue+"&email="+email+"&orgLocationId="+orgLocationId,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		 if(json[0] != null && json[0] != ''){
	        			$("#firstName").attr("disabled", true);
	        		    $("#firstName").val(json[0]);
	        		    $('#nameIdDiv').find('p.jquery_form_error_message').remove();
	        		 }else{
	        			 $("#firstName").attr("disabled", false);
	        			 $("#firstName").val('');
	        		 }
	        		 
	        		 if(json[1] != null && json[1] != ''){
		        			$("#lastName").attr("disabled", true);
		        		    $("#lastName").val(json[1]);
	        		 }else{
	        			    $("#lastName").attr("disabled", false);
	        			    $("#lastName").val('');
	        		 }
		        		
	        		 if(json[2] != null && json[2] != ''){
		        			$("#phoneNumber").attr("disabled", true);
		        		    $("#phoneNumber").val(json[2]);
	        		 }else{
	        			    $("#phoneNumber").attr("disabled", false);
	        			    $("#phoneNumber").val('');
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
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var clientName = (document.getElementById("orgName").value).replace(/[{}]/g, "");
	var vendor = encodeURIComponent(clientName);
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	if(vendor.match(/^[^\s-](.*)*$/) != null){
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (vendor != null && vendor != '') {
		
		$("#vendorDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
		$.ajax({
			type : "GET",
			url : vmsUrl + "/isClientNameValid?"+tokenParamName+"="+tokenValue+"&clientName="+vendor+"&responderType=Vendor",
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
				
				$("#vendorDetailsLoading").removeAttr("style");
			},

		});
	}
  }
}
</script>





<script type="text/javascript">
function validateOrgName(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var vendorName = (document.getElementById("orgName").value).replace(/[{}]/g, "");
	var vendor = encodeURIComponent(vendorName);
	if(vendor.match(/^[^\s-](.*)*$/) != null){
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (vendor != null && vendor != '') {
		
		$.ajax({
			type : "GET",
			url : vmsUrl + "/isInvitedClientOrVendorOrgValid?"+tokenParamName+"="+tokenValue+"&orgName="+vendor,
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
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		var website = document.getElementById("webSite").value;
		if(website.match(/^[^\s-](.*)*$/) !== null){
		var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
		
		if (website != null && website != '') {

			$.ajax({
				type : "GET",
				url : vmsUrl + "/validateClientWebsite?website="+ website + "&sourceleadBLockedwebsites="+ sourceleadBLockedwebsites,
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
 
