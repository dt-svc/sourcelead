<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style type="text/css">
.selectChosen .chosen-container {
	width: 100% !important;
	}
.icon1 {
	right: 15px;
	}
.trashbackground{
    background-color: #ffff !important;
</style>

<!--success msg -->

<div class="success_pop" id="mailMsg" style="display: none;">
	<i class="fa fa-check-circle"></i><span>Invitation sent successfully</span>
</div>


<!-- end files for autocomplete with tagit -->
<script type="text/javascript">
window.onload = function () {
	editOrgLocationAuto('orgEditState','orgEditCity','orgEditZipCode');
	getBusinessTypeValue();
	getAllCountries();
	colourpicker();
	orgShortCode();
 };
</script>

<script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>

<script>
	$(document).ready(function() {
		document.getElementById("orgSettings_form").reset();
		zipcodeMask('${SourceLeadWeb_country}','orgEditZipCode');
		
		if('${tabType}' == 'documents'){
			var documentsClick = document.getElementById("documentsClick");
			documentsClick.click();
		}
		
		
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
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelOrgImg();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelOrgImg();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".mask").show();

}

</script>

<script type="text/javascript">
function deleteImage(){
	var result = "";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to delete image?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteOrgImage();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelOrgImg();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".mask").show();
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
	$(".mask").hide();
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
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/settings">Settings</a>&raquo; Account Settings</div>
    </section>
    
    <div class="project_wrap clearfix">
      <div class="clearfix form-area form-horizontal customedTabStyles">
      
      <div id="loading"></div>
      <div id="regLoading"></div>
      <div id="popUp"></div>  
      <div id="signatureLoading"></div>
      
<div class="success_pop" id="resendOrg" style="display: none;"><i class="fa fa-check-circle"></i><span>Invitation sent successfully</span></div>
		
        <!-- Tab Pills navbar -->
      <ul class="nav nav-tabs newTabs">
        <li class="active"><a data-toggle="tab" href="#orgsetting">Settings</a></li>
        
        <c:if test="${SourceLeadWeb_isSuperAdmin == 'Y'}">
        	<li><a data-toggle="tab" href="#orgRegisteredlocations" onclick="loadRegisteredOrgs();">Locations</a></li>
        </c:if>
        <c:if test="${SourceLeadWeb_orgLocations.country != 'India'}">
        <li><a data-toggle="tab" href="#federalAndStateTaxes" onclick="getStateTaxes();">Taxes</a></li>
        </c:if>
        <c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
        <li><a data-toggle="tab" href="#informationDetails" onclick="resetInformation();">Information</a></li>
        </c:if>
         
        <li><a data-toggle="tab" id="documentsClick" href="#orglocationDocuments" onclick="resetDocument();">Documents</a></li>
        <li><a data-toggle="tab" href="#orglocationSignatory" onclick="getAllSignatory();">Signatory</a></li>
        <li><a data-toggle="tab" href="#emailSign" onclick="loadSignature();">Email Signature</a></li>

        <li><a data-toggle="tab" href="#configCoolingOff" onclick="configCoolingOff();getProbationPeriod();">Configure</a></li>
		<li><a data-toggle="tab" href="#emailNotifications" onclick="resetEmailConfguration();">Email Configuration</a></li> 
       
        <!-- <li><a data-toggle="tab" href="#emailNotifications">Email Configuration</a></li> -->
         <%-- <li><a href="${appPath}/emailNontificationConfigure" >Email Notifications</a></li> --%>

<%--         <c:if test="${SourceLeadWeb_isSuperAdmin == 'Y'}"> --%>

        <li><a data-toggle="tab" href="#whiteListIp" onclick="getWhiteListIps();">Whitelist Ips</a></li>
                <%--<c:if test="${SourceLeadWeb_orgLocations.country == 'United States' || SourceLeadWeb_orgLocations.country == 'USA'}">--%>
		<li><a data-toggle="tab" href="#paymentAccount" onclick="getPaymentAccounts();">Payment Accounts</a></li>
                <%--</c:if>--%>
<%--         </c:if> --%>
      </ul>
      
      
      <!-- Tab Pills navbar -->
      <div class="tab-content">
      
        <div id="orgsetting" class="tab-pane fade in active">  
         
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab org-settings-area noMar">
		  <div class="tab-content panel-body noBor">
		  
		  	<form:form class="login-form mt10"  id="orgImage_form"  name="orgImage_form" method="post" > 
		  <div class="col-xs-12 col-sm-3 col-md-3">
				 <div class="well well-sm text-center">
				  <div class="profile-img-container" style="position:relative;">
				   <img id="orgImage" data-id="orgLogoImage" onerror="predefineOrgImage(this);" src="${SourceLeadWeb_fileUrl}" alt="Org Image" class="comp-image img-responsive">
				  
				  <div class="btn-group uploadfile">
					   <label class="btn btn-sm btn-primary">
	                    <i class="fa fa-upload"></i> Upload / Change<input type="file" name="file" id="uploadedImage" data-id='orgLogo'  accept="image/*" onchange="cropImage(this, 'orgImage_form', '', true);" style="visibility:hidden;height:0;width:10px;overflow:hidden" >
	                  </label>
	 				  <c:if test="${!empty SourceLeadWeb_fileUrl}">
		                  <button type="button" class="btn btn-sm btn-danger trashbackground" onclick="deleteImage()">
						  <i class="fa fa-trash"></i></button>
					  </c:if>
				  </div>
				  </div>
				  </div>
				  <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>
				  <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not allowed</p>
				  <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>
			  </div>
			  </form:form>
			  
			  
			<div class="clearfix">
			  <form:form class="login-form  mt10"  id="orgSettings_form"  name="orgSettings_form" method="post" > 
			  <div id="popUp" ></div>
				<div class="col-xs-12 col-sm-9 col-md-9 clearfix">
					<div class="row">
						<div class="col-md-6">
							<div class="clearfix mb" id="companyNameDiv">
							<label class="labText">Company <b class="valStar">*</b></label>
							<div class="clearfix">
							<input type="text" id="orgLocationCompanyName" name="companyName" class="form-control"  field-name="Company" data-validation="required validate_Space validate_length length2-250" onblur="validateCompanyName();"  value="${SourceLeadWeb_orgName}" />
							</div>
							</div>
							
							 
							<div class="clearfix mb">
								<label class="labText">Country <b class="valStar">*</b></label>
								<div class="clearfix">
								 <input type="text" class=" form-control" name="country" value="${SourceLeadWeb_country}" id="orgEditCountry" field-name="Country" data-validation="required" readonly="readonly" >
								</div>
							</div>
								
							<div class="clearfix mb">
								<label class="labText">City <b class="valStar">*</b></label>
								<div class="clearfix positionRelative">
								 <input type="text" class=" form-control" name="city" value="${SourceLeadWeb_city}" id="orgEditCity" field-name="City" data-validation="required">
								 <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshOrgEditGoogleApiLocation('orgEditState','orgEditCity','orgEditZipCode');"></i>
								</div>
							</div>
							<div class="clearfix mb">
								<label class="labText">State <b class="valStar">*</b></label>
								<div class="clearfix">
								 <input type="text" class=" form-control" name="state" value="${SourceLeadWeb_state}" id="orgEditState" field-name="State" data-validation="required" >
								</div>
							</div>
								
							<div class="clearfix mb">
								<label class="labText">ZipCode</label>
								<div class="clearfix">
								<input type="text"  class="form-control" name="zipCode" id="orgEditZipCode" field-name="Zip" data-validation="validate_Space" data-validation-optional="true" value="${SourceLeadWeb_zipCode}" />
								</div>
							</div>
							
						<div class="clearfix mb">
							<label class="labText">Email for Purchase Order</label>
							<div class="clearfix">
							<input type="text"  class="form-control" name="poEmail" field-name="Email for Purchase Order" data-validation="validate_email validate_Space" data-validation-optional="true" value="${SourceLeadWeb_poEmail}" />
							</div>
						</div>
						
						<div class="clearfix mb">
							<label class="labText">Email for Remittance</label>
							<div class="clearfix">
							<input type="text"  class="form-control" name="remittanceEmail" field-name="Remittance Email" data-validation="validate_email validate_Space" data-validation-optional="true" value="${SourceLeadWeb_remittanceEmail}" />
							</div>
						</div>
							
							<div class="clearfix mb">
							<label class="labText">Address</label>
							<div class="clearfix">
							<input type="text" id="address" name="address" class="form-control"  field-name="Address" data-validation="validate_Space" data-validation-optional="true" value="${SourceLeadWeb_address}" />
							</div>
							</div>
							
							<div class="clearfix mb">
							<label class="labText">Time Zone <b class="valStar">*</b></label>
							<div class="clearfix">
								 <select class="chosen-select form-control" data-placeholder="Time Zone" field-name="Timezone" data-validation="required" id="timeZone" name="timeZone">
									 <option value="" >Select Timezone</option>
									 <c:forEach items="${timezoneList}" var="timezone">
			                            <c:choose>
			                                <c:when test="${timezone.timeZoneId eq SourceLeadWeb_timeZone}">
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
							
						<div class="clearfix mb">
							<label class="labText">Date Format</label>
							<div class="clearfix">
							 <select class="chosen-select form-control" id="dateFormat1" name="dateFormat1">
							 <option ${SourceLeadWeb_dateFormat  == 'dd-MM-yyyy' ? 'selected' : ''} value="dd-MM-yyyy">dd-MM-yyyy</option>
							 <option ${SourceLeadWeb_dateFormat  == 'MM-dd-yyyy' ? 'selected' : ''} value="MM-dd-yyyy">MM-dd-yyyy</option>
							 <option ${SourceLeadWeb_dateFormat  == 'yyyy-dd-MM' ? 'selected' : ''} value="yyyy-dd-MM">yyyy-dd-MM</option>
							 <option ${SourceLeadWeb_dateFormat  == 'yyyy-MM-dd' ? 'selected' : ''} value="yyyy-MM-dd">yyyy-MM-dd</option>
							 </select>
							</div>
							</div>
							
						<div class="clearfix mb mt" id="generatedUserIdDiv">
							<label class="labText">Org Short Code <b class="valStar">* </b> <i class='fa fa-info-circle' title="The internal unique code used to auto generate the ID's for jobs, candidates, etc"></i></label>
							<div class="clearfix">
							<input type="text"  class="form-control" name="generatedUserId" id="generatedUserId" field-name="Org Short Code" style="text-transform:uppercase" readonly="readonly" data-validation="required validate_length length1-4" maxlength="4" value="${SourceLeadWeb_orgLocations.generatedUserId}" />
							</div>
						</div>
						<input type="hidden" id="existingCity" value="${SourceLeadWeb_city}" >
				   </div>
				
					<div class="col-md-6">
							
						<div class="clearfix mb">
							<label class="labText">DBA </label>
							<div class="clearfix">
								 <div class="col-md-11 noPad">
				                    <input id="dba" name="dba" class="form-control" type="text" value="${SourceLeadWeb_dba}" field-name="DBA" data-validation="validate_Space validate_length length2-250" data-validation-optional="true">
				                </div>
				                <div class="col-md-1 dbaCheck dbaCheck-cs">
				                     
				                   <div class="ml10">
                                                <input id="dbaCheckbox" name="dbaCheckbox" ${empty SourceLeadWeb_dba ? 'checked' : SourceLeadWeb_dba == SourceLeadWeb_orgLocationName ? 'checked' : ''} class="checkbox-custom" onclick="getDBAName();" type="checkbox">
						              			<label for="dbaCheckbox" class="checkbox-custom-label" title="Make company name as dba"></label>
				          			</div>
				                </div>
							</div>
						</div>
							
							<div class="clearfix mb">
							<label class="labText">Website <b class="valStar">*</b></label>
							<div class="clearfix" id="websiteDiv">							
							<c:if test="${SourceLeadWeb_website == null}">
							   <input type="text"  class="form-control"  onblur="validateOrgLocationWebsite();" data-validation="required" field-name="Website" id="website"  name="website" value="${SourceLeadWeb_website}">
							</c:if>
							
							<c:if test="${SourceLeadWeb_website != null}">
							   <input type="text" readonly="readonly" class="form-control" name="website" value="${SourceLeadWeb_website}">
							</c:if>
							
							</div>
							</div>
							
							
						<div class="clearfix mb">
							<label class="labText">Week Off Days</label>
							<div class="clearfix">
							 <select multiple class="chosen-select form-control" id="weekOffDays" name="weekOffDays" data-validation="required" field-name="Week Off Days">
								 <option  value='Sun' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Sun') ? 'selected' : ''}>Sunday</option>
								 <option  value='Mon' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Mon') ? 'selected' : ''}>Monday</option>
								 <option  value='Tue' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Tue') ? 'selected' : ''}>Tuesday</option>
								 <option  value='Wed' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Wed') ? 'selected' : ''}>Wednesday</option>
								 <option  value='Thu' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Thu') ? 'selected' : ''}>Thursday</option>
								 <option  value='Fri' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Fri') ? 'selected' : ''}>Friday</option>
								 <option  value='Sat' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Sat') ? 'selected' : ''}>Saturday</option>
							 </select>
							</div>
						</div>
							
						
						<div class="clearfix mb" id="averageWorkingHoursDiv">
							<label class="labText">Max hours per day<b class="valStar">*</b></label>
							<div class="clearfix">
							<input type="text" class="form-control" maxlength="2" name="averageWorkingHours" id="averageWorkingHours" data-validation="required validate_int" field-name="Max hours per day" value="${SourceLeadWeb_orgLocations.averageWorkingHours}"/>
							</div>
						</div>
						
						<div class="clearfix mb">
								<div class="col-md-11">
									<div class="checkBox">
										<label><input type="checkbox" value="Y" ${SourceLeadWeb_isTimesheetAvgHoursRequired == 'Y' ? 'checked' : ''} name="isTimesheetAvgHoursRequired" id="isTimesheetAvgHoursRequired"> Average Hours Validation Is Required</label> 
									</div>
								</div>
					    </div>
							
						<div class="clearfix mb">
							<label class="labText">Phone </label>
							<div class="clearfix">
							<input type="text"  class="form-control" name="phone" field-name="phone" data-validation="validate_phone validate_Space validate_length length9-20" data-validation-optional="true" value="${SourceLeadWeb_phone}" />
							</div>
						</div>
						<div class="clearfix mb">
							<label class="labText">Fax </label>
							<div class="clearfix">
							<input type="text"  class="form-control" name="fax" id="faxId" field-name="fax" data-validation="validate_phone validate_Space validate_length length9-20" data-validation-optional="true" value="${SourceLeadWeb_fax}" />
							</div>
						</div>
						
						
						<div class="clearfix mb">
							<label class="labText">Business type <b class="valStar">*</b></label>
							<div class="clearfix">
									<select class="chosen-select" id="businessType" name="businessType" data-validation="required" onchange="getBusinessTypeValue();"  field-Name="Business Type">
									<option value="">--Select Business type--</option>
									<c:forEach var="bt" items="${bussinessTypesList}">
								      <c:choose>
						                      <c:when test="${bt.id eq SourceLeadWeb_businessTypeId}">
						                          <option value= "${bt.id}" selected="selected">${bt.name}</option>
						                      </c:when>
						                      <c:otherwise>
						                         <option value="${bt.id}">${bt.name}</option>
						                      </c:otherwise>
						              </c:choose>
					                </c:forEach>  
					                
					                <c:if test="${empty SourceLeadWeb_businessTypeId && !empty SourceLeadWeb_otherBusinessType}">
										<option value="Other" selected="selected">Other</option>
									</c:if>
									
								  <c:if test="${empty SourceLeadWeb_otherBusinessType}">
										<option value="Other">Other</option>
									</c:if>
									
								  </select>
							</div>
						</div>
						
						<div class="clearfix mb" id="otherBusinessTypeDiv" style="display: none;">
							<label class="labText">Other Business type <b class="valStar">*</b></label>
							<div class="clearfix">
							<input type="text"  class="form-control" name="otherBusinessType" id="otherBusinessType" value="${SourceLeadWeb_otherBusinessType}" field-name="Other Business Type"  data-validation-optional="true" value="${SourceLeadWeb_zipCode}" />
							</div>
						</div>
						
					    
					    <div class="clearfix mb comp-textarea">
							<label class="labText">Company Description</label>
							<div class="clearfix">
							   <textarea rows="6" name="aboutCompany" field-name="Company Descrption" data-validation-optional="true" data-validation="validate_length length5-5000" class="form-control">${SourceLeadWeb_orgDescription}</textarea>
							</div>
						</div>
							
						</div>
					</div>
					<div class="clearfix">
					<div class="pull-right">
					<sec:authorize access="hasAnyRole('ROLE_VIEW_EDIT_ORG_SETTINGS')">
					<input type="submit" value="Save" id="save_button" class="btn btn-primary mr">
					</sec:authorize>
					<input type="button" value="Cancel" class="btn btn-default" onclick="javascript:window.location.href ='${SourceLeadWeb_sourceleadUrl}/settings'"></input>
					</div>
					</div>
					</div>
			</form:form>
			</div>

		   </div>
		</div>
		</div>
		
		<div id="orgRegisteredlocations" class="tab-pane fade form-horizontal"> 
			<div class="accountsettings-tab noMar">
	                        <div class="tab-content panel-body noBor">
	                            <div class="clearfix mb10">
	                                <div class="pull-left">
	                                    <h4 id="locationOrgsName" class="loaHeadtxt"></h4>
	                                </div>
                               <div class="pull-right">
                                <button class="btn btn-default btm-sm pull-right" id="orgsList" data-toggle="tab" href="#orgRegisteredlocations" onclick="loadRegisteredOrgs();" style="display: none"><i class="fa fa-map-marker"></i>
	                                         &nbsp;&nbsp;Locations List</button>
                                &nbsp;&nbsp;<button class="btn btn-default btm-sm" onclick="createOrg();" id="enrollNewOrgLocation"><i class="fa fa-map-marker"></i>
                                    &nbsp;&nbsp;Add Location</button>
                               </div>
	                            </div>
	                			<div class="clearfix form-area form-horizontal">
	                			
	                			<form:form class="login-form clearfix"  id="createOrg_form"  name="createOrg_form" method="post" >
	                			<div id="registeredOrgs" class="newLocaWrap clearfix"></div>  
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
                <div id="recommLoading"></div>
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
                                    <h4 id="profilePercentageHeading">Profile Percentage</h4>
                                    <div class="success_pop" id="profilePercentageSelector"  style="display: none;">
		       								  <i class="fa fa-check-circle"></i><span id="successMsgDiv"></span>
									</div>
                                    <div id="profilePercentageLoading"></div>
                                </div>
                            </div>
                			<form id="profilePercentage_form"  name="profilePercentage_form" method="post">
                			<div class="form-group">
                			<label class="col-md-3">Profile Percentage&nbsp;&nbsp;<i class="fa fa-info-circle" title="A Profile Percentage is for an applicant is eligible for job submission or not."></i></label>  
                			<div class="col-md-4"><input type="text" name="profilePercentage" value="${SourceLeadWeb_profilePercentage}" id="profilePercentage" onblur="validateProfilePercentage()" class="form-control input-sm" data-validation="required validate_space validate_int" field-name="Profile Percentage">
                			<div class="row mxrateerror" id="profilePercentageValidateId">
                				  <input type="hidden" name="profilePercentageValidation" id="profilePercentageValidation">
             			    </div>
             			    </div>
                			</div>
                			        
                			<div class="clearfix"></div>
                			 <div class="pull-right">
								<input type="submit" value="Save" id="profilePercentage_button" class="btn btn-primary mr">
								<input type="button" value="Cancel" class="btn btn-default" onClick="cancelBtn();">
							</div> 
                			<div class="clearfix"></div> 
                			</form>
                			
            			</div>
            			
            			<sec:authorize access="hasRole('ROLE_SHARE_PROFILE')">
            			<div class="tab-content panel-body">
                			<h4>Marketplace</h4>
                			<div class="form-group">
	                			<label class="col-md-3">Display company name on marketplace profiles</label>  
	                			<div class="col-md-4">
	                				<label class="switch" style="position:relative; top:5px;">
	                				 <input onclick="changeMarketplaceCompanyNameConfig(this)" type="checkbox"  ${orgLocations.showCompanyNameInMarkeplace == "Y"? 'checked' : ''}>
	                				 <span class="slider round"></span>
									</label>
	                			</div>
                			</div>
                		</div>
            			</sec:authorize>
                        <div class="tab-content panel-body">
                            <div class="clearfix mb">
                                <div class="pull-left">
                                    <h4 id="orgShortCodeHeading">Organization Short Code</h4>
                                    <div id="orgShortCodeLoading"></div>
                                </div>
                            </div>
                			<div class="form-group">
                			<label class="col-md-3">Organization Short Code<i class="" title="System Generated Id"></i></label>  
                			<div class="col-md-4"><input type="text" name="generatedUserId" value="${SourceLeadWeb_orgShortCode}" id="generatedUserId" class="form-control input-sm" readonly></div>
                			</div>         
            			</div> 
            			
            			<c:if test="${SourceLeadWeb_isSuperAdmin == 'Y'}">
	            			<div class="tab-content panel-body">
	                           <div class="clearfix mb">
	                               <div class="pull-left">
	                                   <h4>Login Security</h4>
	                               </div>
	                           </div>
	               			<div class="form-group">
		               			<label class="col-md-3">Login Security<i class="" title="Login Security"></i></label>  
		               			<div class="col-md-7 toggle-button">
			                      <label class="switch">
			                      <c:if test="${SourceLeadWeb_orgLocationLoginSecurity == 'Y'}">
			                      	<input type="checkbox" checked id="loginSecurityId" onclick="switchLoginSecurity();">
			                      </c:if>
			                      <c:if test="${SourceLeadWeb_orgLocationLoginSecurity == 'N'}">
			                      	<input type="checkbox" id="loginSecurityId" onclick="switchLoginSecurity();">
			                      </c:if>
			                      <div class="slider round"></div>
			                      </label>
	                   			</div>
	               			</div>         
	           			</div> 
                                        <div class="tab-content panel-body">
                                            <div class="clearfix mb">
                                                <div class="pull-left">
                                                    <h4 id="careerPageKeyHeading">Career Page Key</h4>
                                                    <div id="careerPageKeyLoading"></div>
                                                </div>
                                                <div class="pull-right">
                                                    <span title="Click to copy key to clipboard"><i id="copyCareerKey" class="fa fa-clipboard copy-icon hidden {SourceLeadWeb_careerPageKey == null ? 'hidden' : ''}" aria-hidden="true"></i></span>
                                                    <input type="button" value="${SourceLeadWeb_careerPageKey == null ? 'Generate Key' : 'Regenerate Key'}" id="generateCareerPageKey" class="btn btn-primary"/>
                                                </div>
                                            </div>
                                            <div class="form-group" id="careerPageInnerContent">
                                                <c:if test="${SourceLeadWeb_careerPageKey != null}">
                                                    <div class="clearfix">
                                                        <div class="col-md-2">Career Page Key</div>
                                                        <div class="col-md-9 break_all">${SourceLeadWeb_careerPageKey}</div>
                                                        <div class="col-md-1">
                                                            <div class="toggle-button">
                                                                <label class="switch">
                                                                    <input type="checkbox" ${SourceLeadWeb_careerPageKeyActive ? 'checked' : ''} id="changeCareerPageKeyStatus"/>
                                                                    <div class="slider round"></div>
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </div>  
                                                <div class='clearfix'>
                                                    <span>Please copy & paste this in your career page</span>
                                                    <textarea class='form-control' rows="3" readonly="readonly" id='careerPageCode'>
                                                        
                                                    </textarea>
                                                </div>
                                        </div> 
           			</c:if>
           			
           			<c:if test="${SourceLeadWeb_isSuperAdmin == 'Y'}">
	            			<div class="tab-content panel-body">
	               			<h4>Financial Year</h4>
	               			<div class="form-group">
		               			<label class="col-md-3">Financial Year<i class="" title="Login Security"></i></label>  
		               			<div class="col-md-2 toggle-button" >
			                      <select class="chosen-select" id="financialStartYear" onChange="validateFinancialYear();">
			                      <option value = "">--Select Month--</option>
			                      <option ${SourceLeadWeb_startMonth == 'January' ? 'selected' : ''}>January</option>
			                      <option ${SourceLeadWeb_startMonth == 'February' ? 'selected' : ''}>February</option>
			                      <option ${SourceLeadWeb_startMonth == 'March' ? 'selected' : ''}>March</option>
			                      <option ${SourceLeadWeb_startMonth == 'April' ? 'selected' : ''}>April</option>
			                      <option ${SourceLeadWeb_startMonth == 'May' ? 'selected' : ''}>May</option>
			                      <option ${SourceLeadWeb_startMonth == 'June' ? 'selected' : ''}>June</option>
			                      <option ${SourceLeadWeb_startMonth == 'July' ? 'selected' : ''}>July</option>
			                      <option ${SourceLeadWeb_startMonth == 'August' ? 'selected' : ''}>August</option>
			                      <option ${SourceLeadWeb_startMonth == 'September' ? 'selected' : ''}>September</option>
			                      <option ${SourceLeadWeb_startMonth == 'October' ? 'selected' : ''}>October</option>
			                      <option ${SourceLeadWeb_startMonth == 'November' ? 'selected' : ''}>November</option>
			                      <option ${SourceLeadWeb_startMonth == 'December' ? 'selected' : ''}>December</option>
			                      </select>
	                   			</div>
	                   			<div class="col-md-2 toggle-button">
			                      <select class="chosen-select" id="financialEndYear" disabled="disabled" readonly="">
			                      <option value = "">--Select Month--</option>
			                      <option ${SourceLeadWeb_endMonth == 'January' ? 'selected' : ''}>January</option>
			                      <option ${SourceLeadWeb_endMonth == 'February' ? 'selected' : ''}>February</option>
			                      <option ${SourceLeadWeb_endMonth == 'March' ? 'selected' : ''}>March</option>
			                      <option ${SourceLeadWeb_endMonth == 'April' ? 'selected' : ''}>April</option>
			                      <option ${SourceLeadWeb_endMonth == 'May' ? 'selected' : ''}>May</option>
			                      <option ${SourceLeadWeb_endMonth == 'June' ? 'selected' : ''}>June</option>
			                      <option ${SourceLeadWeb_endMonth == 'July' ? 'selected' : ''}>July</option>
			                      <option ${SourceLeadWeb_endMonth == 'August' ? 'selected' : ''}>August</option>
			                      <option ${SourceLeadWeb_endMonth == 'September' ? 'selected' : ''}>September</option>
			                      <option ${SourceLeadWeb_endMonth == 'October' ? 'selected' : ''}>October</option>
			                      <option ${SourceLeadWeb_endMonth == 'November' ? 'selected' : ''}>November</option>
			                      <option ${SourceLeadWeb_endMonth == 'December' ? 'selected' : ''}>December</option>
			                      </select>
	                   			</div>
	               			</div>
	               			<div class="mt clearfix">
           			  	<div class="pull-right">
           			  		<button type="button" class="btn btn-primary btn-sm mr" onclick="financialYear();">Save</button>
           			  		<!-- <button type="button" class="btn btn-default btn-sm" onclick="cancel();">Cancel</button> -->
           			  	</div>
           			  </div>
           			  
           			  <c:if test="${!empty msg}">
	                       <div class="success_pop" id="selector"  style="display: none;">
		                   <i class="fa fa-check-circle"></i><span>${msg}</span>
			               </div>
	              </c:if>
	                 <div class="success_pop" id="selector1" style="display: none;">
 		            	<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
                     </div>
	               			         
	           			</div> 
           			</c:if>
           			
					<!--   Recommendations Configure starts -->
           			   <div class="tab-content panel-body positionRelative">
           			   <div class="alert alert-success alert-dismissible recommendations-msgs text-center" id="recommSuccessDiv" style="display:none">
						  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						  Recommendation details updated successfully ..!
						</div>
						<div class="alert alert-danger alert-dismissible recommendations-msgs text-center" id="recommErrorDiv" style="display:none">
						  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						  Total recommendations should not be more than 100.
						</div>
           			   
                            <div class="clearfix mb">
                                    <h4 id="coolingOffHeading">Recommendations Configure</h4>
                            </div>
                            <form id="recommendations_form"  name="recommendations_form" method="post">
                            <div class="row">
                           <div class="col-md-3 mb">
						<label class="clearfix">Skills</label>
						<div class="clearfix dropdown-select mt5">
							<select class="form-control chosen-select" name="recommSkill" id="recommSkill">
							<option value="0">-- Select Skills --</option>
							<option ${SourceLeadWeb_recommendedSkillPercent == '10' ? 'selected' : ''}>10</option>
							<option ${SourceLeadWeb_recommendedSkillPercent == '20' ? 'selected' : ''}>20</option>
							<option ${SourceLeadWeb_recommendedSkillPercent == '30' ? 'selected' : ''}>30</option>
							<option ${SourceLeadWeb_recommendedSkillPercent == '40' ? 'selected' : ''}>40</option>
							<option ${SourceLeadWeb_recommendedSkillPercent == '50' ? 'selected' : ''}>50</option>
							<option ${SourceLeadWeb_recommendedSkillPercent == '60' ? 'selected' : ''}>60</option>
							<option ${SourceLeadWeb_recommendedSkillPercent == '70' ? 'selected' : ''}>70</option>
							<option ${SourceLeadWeb_recommendedSkillPercent == '80' ? 'selected' : ''}>80</option>
							<option ${SourceLeadWeb_recommendedSkillPercent == '90' ? 'selected' : ''}>90</option>
							<option ${SourceLeadWeb_recommendedSkillPercent == '100' ? 'selected' : ''}>100</option>
							</select>
						</div>
						</div> 
                            
						<div class="col-md-3 mb">
						<label class="clearfix">Location</label>
						<div class="clearfix dropdown-select mt5">
							<select class="form-control chosen-select" name="recommLocation" id="recommLocation">
							<option value="0">-- Select Location --</option>
							<option ${SourceLeadWeb_recommendedLocationPercent == '10' ? 'selected' : ''}>10</option>
							<option ${SourceLeadWeb_recommendedLocationPercent == '20' ? 'selected' : ''}>20</option>
							<option ${SourceLeadWeb_recommendedLocationPercent == '30' ? 'selected' : ''}>30</option>
							<option ${SourceLeadWeb_recommendedLocationPercent == '40' ? 'selected' : ''}>40</option>
							<option ${SourceLeadWeb_recommendedLocationPercent == '50' ? 'selected' : ''}>50</option>
							<option ${SourceLeadWeb_recommendedLocationPercent == '60' ? 'selected' : ''}>60</option>
							<option ${SourceLeadWeb_recommendedLocationPercent == '70' ? 'selected' : ''}>70</option>
							<option ${SourceLeadWeb_recommendedLocationPercent == '80' ? 'selected' : ''}>80</option>
							<option ${SourceLeadWeb_recommendedLocationPercent == '90' ? 'selected' : ''}>90</option>
							<option ${SourceLeadWeb_recommendedLocationPercent == '100' ? 'selected' : ''}>100</option>
							</select>
						</div>
						</div>
						
						<div class="col-md-3 mb">
						<label class="clearfix">Notice Period</label>
						<div class="clearfix dropdown-select mt5">
							<select class="form-control chosen-select" name="recommNoticePeriod" id="recommNoticePeriod">
							<option value="0">-- Select Notice Period --</option>
							<option ${SourceLeadWeb_recommendedNoticePercent == '10' ? 'selected' : ''}>10</option>
							<option ${SourceLeadWeb_recommendedNoticePercent == '20' ? 'selected' : ''}>20</option>
							<option ${SourceLeadWeb_recommendedNoticePercent == '30' ? 'selected' : ''}>30</option>
							<option ${SourceLeadWeb_recommendedNoticePercent == '40' ? 'selected' : ''}>40</option>
							<option ${SourceLeadWeb_recommendedNoticePercent == '50' ? 'selected' : ''}>50</option>
							<option ${SourceLeadWeb_recommendedNoticePercent == '60' ? 'selected' : ''}>60</option>
							<option ${SourceLeadWeb_recommendedNoticePercent == '70' ? 'selected' : ''}>70</option>
							<option ${SourceLeadWeb_recommendedNoticePercent == '80' ? 'selected' : ''}>80</option>
							<option ${SourceLeadWeb_recommendedNoticePercent == '90' ? 'selected' : ''}>90</option>
							<option ${SourceLeadWeb_recommendedNoticePercent == '100' ? 'selected' : ''}>100</option>
							</select>
						</div>
						</div>
						
						<div class="col-md-3 mb">
						<label class="clearfix">Experience</label>
						<div class="clearfix dropdown-select mt5">
							<select class="form-control chosen-select" name="recommExp" id="recommExp">
							<option value="0">-- Select Experience --</option>
							<option ${SourceLeadWeb_recommendedExpPercent == '10' ? 'selected' : ''}>10</option>
							<option ${SourceLeadWeb_recommendedExpPercent == '20' ? 'selected' : ''}>20</option>
							<option ${SourceLeadWeb_recommendedExpPercent == '30' ? 'selected' : ''}>30</option>
							<option ${SourceLeadWeb_recommendedExpPercent == '40' ? 'selected' : ''}>40</option>
							<option ${SourceLeadWeb_recommendedExpPercent == '50' ? 'selected' : ''}>50</option>
							<option ${SourceLeadWeb_recommendedExpPercent == '60' ? 'selected' : ''}>60</option>
							<option ${SourceLeadWeb_recommendedExpPercent == '70' ? 'selected' : ''}>70</option>
							<option ${SourceLeadWeb_recommendedExpPercent == '80' ? 'selected' : ''}>80</option>
							<option ${SourceLeadWeb_recommendedExpPercent == '90' ? 'selected' : ''}>90</option>
							<option ${SourceLeadWeb_recommendedExpPercent == '100' ? 'selected' : ''}>100</option>
							</select>
						</div>
						</div> 
						<div class="col-md-3 mb">
						<label class="clearfix">Visa Type</label>
						<div class="clearfix dropdown-select mt5">
							<select class="form-control chosen-select" name="recommVisaType" id="recommVisaType">
							<option value="0">-- Select Visa Type --</option>
							<option ${SourceLeadWeb_recommendedVisatypePercent == '10' ? 'selected' : ''}>10</option>
							<option ${SourceLeadWeb_recommendedVisatypePercent == '20' ? 'selected' : ''}>20</option>
							<option ${SourceLeadWeb_recommendedVisatypePercent == '30' ? 'selected' : ''}>30</option>
							<option ${SourceLeadWeb_recommendedVisatypePercent == '40' ? 'selected' : ''}>40</option>
							<option ${SourceLeadWeb_recommendedVisatypePercent == '50' ? 'selected' : ''}>50</option>
							<option ${SourceLeadWeb_recommendedVisatypePercent == '60' ? 'selected' : ''}>60</option>
							<option ${SourceLeadWeb_recommendedVisatypePercent == '70' ? 'selected' : ''}>70</option>
							<option ${SourceLeadWeb_recommendedVisatypePercent == '80' ? 'selected' : ''}>80</option>
							<option ${SourceLeadWeb_recommendedVisatypePercent == '90' ? 'selected' : ''}>90</option>
							<option ${SourceLeadWeb_recommendedVisatypePercent == '100' ? 'selected' : ''}>100</option>
							</select>
						</div>
						</div> 
						<div class="col-md-3 mb">
						<label class="clearfix">Education</label>
						<div class="clearfix dropdown-select mt5">
							<select class="form-control chosen-select" name="recommEducation" id="recommEducation">
							<option value="0">-- Select Education --</option>
							<option ${SourceLeadWeb_recommendedEducationPercent == '10' ? 'selected' : ''}>10</option>
							<option ${SourceLeadWeb_recommendedEducationPercent == '20' ? 'selected' : ''}>20</option>
							<option ${SourceLeadWeb_recommendedEducationPercent == '30' ? 'selected' : ''}>30</option>
							<option ${SourceLeadWeb_recommendedEducationPercent == '40' ? 'selected' : ''}>40</option>
							<option ${SourceLeadWeb_recommendedEducationPercent == '50' ? 'selected' : ''}>50</option>
							<option ${SourceLeadWeb_recommendedEducationPercent == '60' ? 'selected' : ''}>60</option>
							<option ${SourceLeadWeb_recommendedEducationPercent == '70' ? 'selected' : ''}>70</option>
							<option ${SourceLeadWeb_recommendedEducationPercent == '80' ? 'selected' : ''}>80</option>
							<option ${SourceLeadWeb_recommendedEducationPercent == '90' ? 'selected' : ''}>90</option>
							<option ${SourceLeadWeb_recommendedEducationPercent == '100' ? 'selected' : ''}>100</option>
							</select>
						</div>
						</div> 
						<div class="col-md-3 mb">
						<label class="clearfix">Rate</label>
						<div class="clearfix dropdown-select mt5">
							<select class="form-control chosen-select" name="recommRate" id="recommRate">
							<option value="0">-- Select Rate --</option>
							<option ${SourceLeadWeb_recommendedRatePercent == '10' ? 'selected' : ''}>10</option>
							<option ${SourceLeadWeb_recommendedRatePercent == '20' ? 'selected' : ''}>20</option>
							<option ${SourceLeadWeb_recommendedRatePercent == '30' ? 'selected' : ''}>30</option>
							<option ${SourceLeadWeb_recommendedRatePercent == '40' ? 'selected' : ''}>40</option>
							<option ${SourceLeadWeb_recommendedRatePercent == '50' ? 'selected' : ''}>50</option>
							<option ${SourceLeadWeb_recommendedRatePercent == '60' ? 'selected' : ''}>60</option>
							<option ${SourceLeadWeb_recommendedRatePercent == '70' ? 'selected' : ''}>70</option>
							<option ${SourceLeadWeb_recommendedRatePercent == '80' ? 'selected' : ''}>80</option>
							<option ${SourceLeadWeb_recommendedRatePercent == '90' ? 'selected' : ''}>90</option>
							<option ${SourceLeadWeb_recommendedRatePercent == '100' ? 'selected' : ''}>100</option>
							</select>
						</div>
						</div> 
						<div class="col-md-3 mb">
						<label class="clearfix">Keywords</label>
						<div class="clearfix dropdown-select mt5">
							<select class="form-control chosen-select" name="recommKeywords" id="recommKeywords">
							<option value="0">-- Select Keywords --</option>
							<option ${SourceLeadWeb_recommendedKeywordsPercent == '10' ? 'selected' : ''}>10</option>
							<option ${SourceLeadWeb_recommendedKeywordsPercent == '20' ? 'selected' : ''}>20</option>
							<option ${SourceLeadWeb_recommendedKeywordsPercent == '30' ? 'selected' : ''}>30</option>
							<option ${SourceLeadWeb_recommendedKeywordsPercent == '40' ? 'selected' : ''}>40</option>
							<option ${SourceLeadWeb_recommendedKeywordsPercent == '50' ? 'selected' : ''}>50</option>
							<option ${SourceLeadWeb_recommendedKeywordsPercent == '60' ? 'selected' : ''}>60</option>
							<option ${SourceLeadWeb_recommendedKeywordsPercent == '70' ? 'selected' : ''}>70</option>
							<option ${SourceLeadWeb_recommendedKeywordsPercent == '80' ? 'selected' : ''}>80</option>
							<option ${SourceLeadWeb_recommendedKeywordsPercent == '90' ? 'selected' : ''}>90</option>
							<option ${SourceLeadWeb_recommendedKeywordsPercent == '100' ? 'selected' : ''}>100</option>
							</select>
						</div>
						</div> 
                            </div>
                           <div class="pull-right">
								<input type="button" value="Save" id="saveRecommendationSettings" onclick="validateTotalRecommValue();" class="btn btn-primary mr">
								<input type="button" value="Cancel" class="btn btn-default" onClick="cancelBtn();">
							</div>
							</form>
            			</div>
           			      <!-- Recommendations Configure ends -->     		
           			      
           			      
           			      <!-- Probation Period Starts -->
           			      
           			       <div class="tab-content panel-body">
                            <div class="clearfix mb">
                                <div class="pull-left">
                                    <h4 id="probationPer">Probation Period</h4>
                                    <div id="probationPeriodLoading"></div>
                                </div>
                            </div>
                			<div id="probationPeriodDiv"></div>            
            			</div>
            			
            			<!-- Probation Period Ends -->
           			           			
        			</div>
    			</div>
    			
    			<div id="whiteListIp" class="tab-pane fade form-horizontal"> 
                <!-- Tab for Profile setting-area -->
                    <div class="accountsettings-tab noMar">
                        <div class="tab-content panel-body noPad noBor">
                        <div id="whiteListLoading"></div>
                            <div class="reportHead reportHeadDataTab clearfix">
                                <ul class="pull-right list-unstyled list-inline">
                                	<li><a class="action newAdBtn" onClick="addipPopup();" href="javascript:void(0)">+ IP</a></li>
                                </ul>
                             </div>
                            
                            <div class="clients_wrap_table datatable_filter dataSeaTop">
                				<div id="whiteListIpsDiv"></div>   
                			</div>
                			
                			</div>         
            			</div>
            			
        			</div>
        			<div id="paymentAccount" class="tab-pane fade form-horizontal"> 
                <!-- Tab for Profile setting-area -->
                    
                    <div class="accountsettings-tab noMar">
                        <div class="tab-content panel-body noPad noBor">
                        <div id="paymentAccountLoading"></div>
                        
                            <div class="reportHead clearfix">
                                <ul class="pull-right list-unstyled list-inline">
                                	<li><a class="action newAdBtn mr5" onClick="showPaymentAccountPopup();" href="javascript:void(0)">+ Payment Account</a></li>
                                </ul>
                             </div>                        
                            
                            <div class="require_wrap_table datatable_filter">
                				<div id="payemntAccountsDiv"></div>   
                			</div>
                			
                			</div>         
            			</div>
            			
        			</div>
   			 <jsp:include page="federalAndStateTaxes.jsp" />
   			 <jsp:include page="locationInformationDetails.jsp" />
     		 <jsp:include page="orglocationDocuments.jsp" />
     		 <jsp:include page="orglocationSignatory.jsp" />
     		 <jsp:include page="googleApiLocation.jsp" />
     		 
   			<jsp:include page="emailNontificationConfigure.jsp" />

     		 <%--  <jsp:include page="emailNontificationConfigure.jsp" /> --%>

     		 
     		 
    			</div>
     		
  	</div>
  </div>
  

		
		
			<!-- Tab for Profile setting-area ends -->
  </section>
  <!-- Tab for Profile setting-area starts Ends -->
  <!-- Content Area Ends-->
  <style>
      .clients_wrap_table.table1 tr td div { position: unset !important; }
      </style>
</body>
<!-- Add Payment Account Modal -->
  <div class="popupWrapMid" id="paymentAccountPopup" role="dialog">
  	<div class="popupHead">Add/Edit Payment Account <i class="closePop pull-right popClose" onclick="closePaymentAccountPopup();"></i></div>
  
        <div class="popupContent popupContentFixed">
        <form:form class="clearfix"  id="paymentaccount_form"  name="paymentaccount_form" method="POST" >
         <inpu type="hidden" id='paymentAccountId' value='0'/>
         <input type="hidden" name="id" id="paymentAccountId"/>
          <div class="col-md-6 minHe">
           <label class="noPad">Account Type <b class="valStar">*</b></label>
           <div class="clearfix">
                <select onchange="showOrHidePaymentFormFields($(this).val()); changefunction();" id="paymentAccountType" name="accountType" class="form-control" data-validation="required">
                    <option value="0">--Select Location--</option>
                    <option value="SAVINGS" data-type="1">Savings</option>
                    <option value="CHECKING" data-type="2">Checking</option>
                      <option value="WEPAY" data-type="3">WePay</option>
                </select>
           </div>
          </div>
           
         <div data-fieldtype="account" data-fieldfor="1,2,3" class="col-md-6 minHe">
           <label class="noPad">Location <b class="valStar">*</b></label>
           <div class="clearfix" id = "acclocationErr">
                <select id="paymentAccountLocation" name="orgLocationId" class="form-control" field-name="Select Acc Location" data-validation="required">
               <option value="">select location</option>
                </select>
           </div>
         </div>
           
        <div data-fieldtype="account"  data-fieldfor="1,2" class="col-md-6 minHe">
           <label class="noPad">Bank Name <b class="valStar">*</b></label>
           <div class="clearfix" id = "bankNameErr">
                <input type="text" id="paymentAccountBankName" name="bankName" field-name="Bank Name" placeholder="Bank Name" class="form-control" data-validation="required validate_length length1-50"/>
           </div>
           </div>
          <div data-fieldtype="account"  data-fieldfor="1,2" class="col-md-6 minHe">
           <label class="clearfix">Account Number<span class="text-danger">&nbsp;*</span></label>
           <div class="clearfix" id ="accnoErr">
                <input type="text" id="paymentAccountNumber" placeholder="Account Number" field-name="Account Number"  name="accountNumber" class="form-control" data-validation="required validate_int validate_length length9-15"/>
           </div>
           </div>
          <c:if test="${SourceLeadWeb_orgLocations.country == 'India'}"> 
           <div data-fieldtype="account"  data-fieldfor="1,2" class="col-md-6 minHe">
              <label class="clearfix">BSR Number <span class="text-danger">&nbsp;*</span></label>
	           <div class="clearfix" id="BSRErr">
	                <input type="text" id="paymentBSRNumber" placeholder="BSR Number"  name="bsrNumber" field-name="BSR Number"  class="form-control" data-validation="required validate_int validate_length length2-9"/>
	           </div>
           </div>
           
           <div data-fieldtype="account"  data-fieldfor="1,2" class="col-md-6 minHe">
              <label class="noPad">MICR Code <b class="valStar">*</b></label>
	           <div class="clearfix" id="MICRErr">
	                <input type="text" id="paymentMICRCode" placeholder="MICR Code" name="micrCode" field-name="MICR Code"  class="form-control" data-validation="required validate_int validate_length length9-9"/>
	           </div>
           </div>
           </c:if>
        
        <div data-fieldtype="account" data-fieldfor="1,2" class="col-md-6 minHe">
           <c:if test="${SourceLeadWeb_orgLocations.country != 'India'}">
              <label class="noPad">Routing Number <b class="valStar">*</b></label>
              <div class="clearfix" id="routing">
                <input type="text" id="paymentAccountRoutingNumber" placeholder="Routing Number" field-name="Routing Number" name="routingNumber" class="form-control" data-validation="required validate_length length9-9"/>
           </div>
           </c:if>
           <c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
              <label class="noPad">IFSC Code <b class="valStar">*</b></label>
              <div class="clearfix" id="ifsc">
                <input type="text" id="paymentAccountRoutingNumber" placeholder="IFSC Code" field-name="IFSC Code" name="paymentAccountRoutingNumber" class="form-control" data-validation="required validate_length length11-11"/>
           </div>
           </c:if>
           
           </div>
           <div data-fieldtype="account" data-fieldfor="3" class="col-md-6 minHe">
           <label class="noPad">Email <b class="valStar">*</b></label>
           <div class="clearfix" id="emailErr">
                <input type="text" id="paymentAccountEmail" name="email" placeholder="Email" field-name="Email" class="form-control" data-validation="required"/>
           </div>
           </div>
        <div data-fieldtype="account" data-fieldfor="1,2,3" class="col-md-6 minHe">
           <label class="noPad">First Name <b class="valStar">*</b></label>
           <div class="clearfix" id="firstnameErr">
                <input type="text" id="paymentAccountFirstName" name="firstName" placeholder="First Name" field-name="First Name" class="form-control" data-validation="required validate_length length1-25"/>
           </div>
           </div>
        
        <div data-fieldtype="account" data-fieldfor="1,2,3" class="col-md-6 minHe">
           <label class="noPad">Last Name <b class="valStar">*</b></label>
           <div class="clearfix" id="lastnameErr">
                <input type="text" id="paymentAccountLastName" name="lastName" placeholder="Last Name" field-name="Last Name" class="form-control" data-validation="required validate_length length1-25"/>
           </div>
           </div>
        
        
        </form:form>
          
        </div>
        
        <div class="popupFoot text-right">
          <button type="button" class="slBtn md colorBtn" id="savePaymentButton" onclick="savePaymentAccountTestRun();" >Save</button>
          <button type="button" class="slBtn md popClose" onClick="closePaymentAccountPopup();">Cancel</button>
        </div>
  </div>
  
<!-- Add IP Modal -->
  <div class="popupWrap" id="addipPopup" role="dialog">
	<div class="popupHead">Add IP <i class="closePop pull-right close" onclick="closeWhiteListIp();"></i></div>
          

        <div class="popupContent">
        <form:form class="clearfix"  id="whiteListIp_form"  name="whiteListIp_form" method="GET" >
        <input type="hidden" name="id" id="whiteListId"/>
         <div class="clearfix mb" id="fromIpAddressDiv">
           <label class="noPad">From IP <b class="valStar">*</b></label>
           <div class="clearfix">
           		<input type="text" id="fromIpAddress" name="fromIpAddress" class="form-control" onblur="ValidateIPaddress(); validateDuplicateIpAddress('fromIpAddr');">
           </div>
           </div>
           <div class="clearfix mb" id="toIpAddressDiv">
           <label class="noPad">To IP <b class="valStar">*</b></label>
           <div class="clearfix">
           		<input type="text" id="toIpAddress" name="toIpAddress" class="form-control" onblur="ValidateIPaddress(); validateDuplicateIpAddress('toIpAddr');">
           </div>
           </div>
        </form:form>
        </div>
        
        <div class="popupFoot text-right">
          <button type="button" class="slBtn md colorBtn" id="saveWhiteListbtn" onclick="saveWhiteListIps();" >Save</button>
          <input type="button" value="Cancel" class="slBtn md" onclick="closeWhiteListIp();">
        </div>
  </div>
  <!-- Add IP Modal -->

<script>
$("#faxId").keyup(function () {
    this.value = this.value.replace(/ /g, "-");
});
</script>
<script> 
// function myFunction(){
// var abc = $("#paymentAccountNumber").val();
// if(abc.match(/\D+/)){
// 	alert("enter numbers only")
// }
// }
// function myFunction1(){
// 	var abc = $("#paymentBSRNumber").val();
// 	if(abc.match(/\D+/)){
// 		alert("enter numbers only")
// 	}
// 	}
// function myFunction2(){
// 	var abc = $("#paymentMICRCode").val();
// 	if(abc.match(/\D+/)){
// 		alert("enter numbers only")
// 	}
// 	}
</script>


<script>
function validateProfilePercentage(){
	var profilePercentage = $('#profilePercentage').val();
	
	 if(profilePercentage > 100 || profilePercentage < 0){
		 if(profilePercentage < 0){
			    $('#profilePercentageValidateId').find('p.jquery_form_error_message').remove(); 
				$('input#profilePercentageValidation').attr('class','error signin_fild');
				$("input#profilePercentageValidation").after("<p class='jquery_form_error_message'>Profile percentage should not in negitive</p>");
				$('input#profilePercentageValidation').attr('record-exist','yes');
				$('input#profilePercentageValidation').attr('record-exist-errorMsg','Profile percentage should not in negitive'); 
		 }else{
			 $('#profilePercentageValidateId').find('p.jquery_form_error_message').remove(); 
				$('input#profilePercentageValidation').attr('class','error signin_fild');
				$("input#profilePercentageValidation").after("<p class='jquery_form_error_message'>Profile percentage should not more than 100</p>");
				$('input#profilePercentageValidation').attr('record-exist','yes');
				$('input#profilePercentageValidation').attr('record-exist-errorMsg','Profile percentage should not more than 100'); 
		 }
	 }else{
		 
	     $('#profilePercentageValidateId').find('p.jquery_form_error_message').remove(); 
    	 $('input#profilePercentageValidation').removeAttr( "class record-exist record-exist-errormsg");
		 $('#profilePercentageValidateId').find('p.jquery_form_error_message').remove();
	 }
}


var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#profilePercentage_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
        		var formData = getFormData("profilePercentage_form");
         		var sourceleadUrl ='${sourceleadUrl}';
         		var profilePercentage = document.getElementById("profilePercentage").value;
         		 	
         		 $.ajax({
       				type : "POST",
       				url : sourceleadUrl+"/saveProfilePercentage?${_csrf.parameterName}=${_csrf.token}&profilePercentage="+profilePercentage,
       				success : function(result) {
       					
       					if (result && result.response === "INVALID_DATA") {
                            showError(result.responseMessage);
                        }else if (result.response === "AWKWARD") {
    		        	    location.href = "/errorPage";
    		        	} else if (result.response === "SUCCESS") {
    		        		$('#successMsgDiv').html("Profile Percentage Updated Successfully");
    		        		 $("#profilePercentageSelector").fadeIn(200);
    		        		  $('#profilePercentageSelector').delay(2000).fadeOut('slow');
    		        	} else if (result.indexOf('Sign In') !== -1) {
    		        		location.replace(sourceleadUrl);
    		        	} else {
    		        	    showError("Operation failed, please try again");
    		        	}
       				},
       			});    
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
            
         $('body').on('blur', '#profilePercentage_form', function() {
     		$("#profilePercentage_form").showHelpOnFocus();
     		$("#profilePercentage_form").validateOnBlur(false, validationSettings);
     	});
</script>

<script>
function switchLoginSecurity(){
	var ckbox = $('#loginSecurityId');
	var locationId = '${SourceLeadWeb_orgLocationId}';
	var security = "";
	if (ckbox.is(':checked')) {
		security = "Y";
	}
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/updateLoginSecurity?security="+security+"&locationId="+locationId,        
	       success :function(data) {
	       if(data == "success"){
	       		//TO-DO
	       	} 
	      },
	  }); 
	
}
$(function() {
    $("#generateCareerPageKey").click(function() {
        $.post(sourceleadUrl+"/generate-career-page-key", {}, function(data) {
            if(data && data.indexOf("key") === 0) {
                $("#copyCareerKey").show();
                $("#generateCareerPageKey").val("Regenerate Key");
                var text = '<div class="clearfix">';
                text += '<div class="col-md-2">Career Page Key</div>';
                text += '<div class="col-md-9 break_all">'+(data.split(';')[1])+'</div>';
                text += '<div class="col-md-1"><div class="toggle-button"><label class="switch"><input type="checkbox" checked id="changeCareerPageKeyStatus"/><div class="slider round"></div></label></div></div>';
                text += '</div>';
                $("#careerPageInnerContent").html(text);
                applyCareerPageCode(data.split(';')[1]);
                applyCareerPageStatusEvent();
            } else  if(data && (data === "logout" || data.indexOf("Sign In") !== -1)) {
                showError("Session timedout");
                window.setTimeout('location.href='+sourceleadLoginUrl, 3000);
            } else {
                showError("Operation failed, please try again");
            }
        });
    });
    
    applyCareerPageCode('${SourceLeadWeb_careerPageKey}')
    applyCareerPageStatusEvent();
});
function applyCareerPageStatusEvent() {
    $("#copyCareerKey").click(function() {
        $("#careerKeyDummy").remove();
       $("#copyCareerKey").append("<input type='text' style='display:none' id='careerKeyDummy'/>");
       var copyText = document.getElementById("careerKeyDummy");
       copyText.value = $(".col-md-9.break_all").html();
       copyText.select();
       document.execCommand("Copy");
    });
    
    $("#changeCareerPageKeyStatus").click(function() {
        $.post(sourceleadUrl+"/career-page-key-status", {}, function(data) {
            if(data && data.indexOf("status") === 0) {
                var status = data.split(";")[1];
                if(parseInt(status) === 1)
                    $("#changeCareerPageKeyStatus").prop('checked', 'checked');
                else
                    $("#changeCareerPageKeyStatus").removeProp('checked');
            } else  if(data && (data === "logout" || data.indexOf("Sign In") !== -1)) {
                showError("Session timedout");
                window.setTimeout('location.href='+sourceleadLoginUrl, 3000);
            } else {
                showError("Operation failed, please try again");
            }
        });
    });
}

function applyCareerPageCode(code) {
    var url = '${sourceleadUrl}'.replace('app','');
    $("#careerPageCode").text("<div id='jobList'></div>\n<script type='text/javascript' src='"+url+"resources/careers.js?key="+code+"'></script"+">");
}
</script>

<script>

function validateTotalRecommValue(){
	var skillPercent = parseInt($("#recommSkill").val());
	var locationPercent = parseInt($("#recommLocation").val());
	var visatypePercent = parseInt($("#recommVisaType").val());
	var educationPercent = parseInt($("#recommEducation").val());
	var noticePercent = parseInt($("#recommNoticePeriod").val());
	var expPercent = parseInt($("#recommExp").val());
	var ratePercent = parseInt($("#recommRate").val());
	var keyWordPercent = parseInt($("#recommKeywords").val());
	
	var totalPercecnt = skillPercent + locationPercent + visatypePercent + educationPercent + noticePercent + expPercent + ratePercent + keyWordPercent;
	
	if(totalPercecnt <= 100){
		saveRecomSettings();
	}else{
		$("#recommErrorDiv").removeAttr("style");
    	$("#recommErrorDiv").attr("style","display:block");
    	$("#recommErrorDiv").fadeOut(2500);
	}
}

function saveRecomSettings(){
	var sourceleadUrl = '${sourceleadUrl}';
	var formData = $($('#recommendations_form')[0]).serialize();
	    $("#recommLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    $.post(sourceleadUrl + "/saveCustomRecommendation", formData, function(data){
	    	$("#recommSuccessDiv").removeAttr("style");
	    	$("#recommLoading").removeAttr('style');
	    	$("#recommSuccessDiv").attr("style","display:block");
	    	$("#recommSuccessDiv").fadeOut(2500);
		});  
}
</script>


<script>
function closeWhiteListIp(){
	$("#fromIpAddress").val("");
	 $("#toIpAddress").val("");
	 $("#whiteListId").val("");
	$("#addipPopup").fadeOut(100);
	$(".mask").hide();	 
}

function saveWhiteListIps(){
	 $("#saveWhiteListbtn").attr("disabled", "disabled");
	var fromIpAddress = $("#fromIpAddress").val();
	var toIpAddress = $("#toIpAddress").val();
	var whiteListId = $("#whiteListId").val();
	if(fromIpAddress != null && fromIpAddress != '' && fromIpAddress !='undefined' && toIpAddress != null && toIpAddress != '' && toIpAddress !='undefined'){
		$('#fromIpAddressDiv').find('p.jquery_form_error_message').remove();
		$('#toIpAddressDiv').find('p.jquery_form_error_message').remove();
		var url = "";
		if(whiteListId != '' && whiteListId != null && whiteListId != 'undefined'){
	    	  url += sourceleadUrl+"/saveWhiteListIps?fromIpAddress="+fromIpAddress+"&toIpAddress="+toIpAddress+"&id="+whiteListId;  
	      }else{
	    	  url += sourceleadUrl+"/saveWhiteListIps?fromIpAddress="+fromIpAddress+"&toIpAddress="+toIpAddress;
	      }
			$.ajax({
			      type : "GET",
			      url : url,
			               
			       success :function(result) {
			    		if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
	      	    		    showError(result.responseMessage);
	      	    		    $("#loading").removeAttr('style');
	      	    		}
			    		else if(result.response === "AWKWARD"){
			        		 location.href = "/errorPage";
			        	}
			    		else if(result.response === "SUCCESS"){
			    	   $("#fromIpAddress").val("");
			    	   $("#toIpAddresavePaymentAccountTestRunss").val("");
			    	   $("#addipPopup").fadeOut(100);
			    	   $(".mask").hide();
			    	   closeWhiteListIp();
			    	   getWhiteListIps();
			       	} 
			    		else if (result.indexOf('Sign In') !== -1) {
	      					location.replace(sourceleadUrl);
	      				} else {
	      				    showError("Operation failed, please try again");
	      				    $("#loading").removeAttr('style');
	      				}
			      },
			  }); 
		
	}else{
		if(fromIpAddress == null || fromIpAddress == '' || fromIpAddress =='undefined'){
			$('#fromIpAddressDiv').find('p.jquery_form_error_message').remove();
			$("input#fromIpAddress").after( "<p class='jquery_form_error_message'>From IP Address is required</span>");
			document.getElementById("fromIpAddress").setAttribute( 'record-exist', 'yes');
			document.getElementById("fromIpAddress").setAttribute( 'record-exist-errorMsg',"From IP Address is required");
		}
		if(toIpAddress == null || toIpAddress == '' || toIpAddress =='undefined'){
			$('#toIpAddressDiv').find('p.jquery_form_error_message').remove();
			$("input#toIpAddress").after( "<p class='jquery_form_error_message'>To IP Address is required</span>");
			document.getElementById("toIpAddress").setAttribute( 'record-exist', 'yes');
			document.getElementById("toIpAddress").setAttribute( 'record-exist-errorMsg',"To IP Address is required");
		}
	}
}
</script>
<script>
function changefunction(){
	var paymentAccountType = $("#paymentAccountType").val();
	if(paymentAccountType == "SAVINGS" || paymentAccountType == "CHECKING"){
	$('#emailErr').find('p.jquery_form_error_message').remove(); 
	$("#paymentAccountEmail").val('');
	$('#paymentAccountEmail').removeAttr('data-validation');

	$('#paymentAccountBankName').attr('data-validation',"required validate_length length1-25");
	$('#paymentAccountNumber').attr('data-validation', "required validate_int validate_length length9-15");
	$('#paymentBSRNumber').attr('data-validation', "validate_int validate_length length2-9");
	$('#paymentMICRCode').attr('data-validation', "validate_int validate_length length9-9");
	$('#paymentAccountRoutingNumber').attr('data-validation', "required validate_Space validate_Alphanumeric fixed_length length11");
	$("#firstName").attr('data-validation',"required validate_Alphanumeric validate_length length1-25");
	$("#lastName").attr('data-validation',"required validate_Alphanumeric validate_length length1-25");
	}else if(paymentAccountType == "WEPAY"){
	$('#paymentAccountEmail').attr('data-validation', "required validate_length length2-100 validate_email");
	$("#paymentAccountBankName").val('');
	$("#paymentAccountNumber").val('');
	$("#paymentBSRNumber").val('');
	$("#paymentMICRCode").val('');
	$("#paymentAccountRoutingNumber").val('');
	$('#paymentAccountBankName').removeAttr('data-validation');
	$('#paymentAccountNumber').removeAttr('data-validation');
	$('#paymentBSRNumber').removeAttr('data-validation');
	$('#paymentMICRCode').removeAttr('data-validation');
	$('#paymentAccountRoutingNumber').removeAttr('data-validation');

	}
	}
</script>
<script>
function savePaymentAccountTestRun(){
	 if ($('#paymentaccount_form').validate(false, validationSettings)){
		 var data =  {};
		 var val = $("#paymentAccountType").val();
		 data["email"] = $("#paymentAccountEmail").val();
		 data["micrCode"] = $("#paymentMICRCode").val().trim();
		 data["bsrNumber"] = $("#paymentBSRNumber").val().trim();
         data["accountNumber"] = $("#paymentAccountNumber").val().trim();
         data["bankName"] = $("#paymentAccountBankName").val().trim();
         data["routingNumber"] = $("#paymentAccountRoutingNumber").val().trim();
         data["firstName"] = $("#paymentAccountFirstName").val().trim();	
         data["lastName"] = $("#paymentAccountLastName").val().trim();
         data["orgLocationId"] = $("#paymentAccountLocation").val();
         data["accountType"] = val;
         data["id"] = $("#paymentAccountId").val();
         $.post(sourceleadUrl+"/save-payment-account", data, function(data) {
        	   showError(data.responseMessage);
             if(data.response === "SUCCESS" || data.response === "UNABLE_SET_PAYMENT_ACCOUNT") {
            	 closePaymentAccountPopup(val);
            	 $("#savePaymentButton").removeAttr("disabled", "disabled");
             }
             getPaymentAccounts();
         });
         return true;
	 }else{
		 return false;
	 }
}
$('body').on('blur', '#paymentaccount_form', function() {
	$("#paymentaccount_form").showHelpOnFocus();
	$("#paymentaccount_form").validateOnBlur(false, validationSettings);  
});

</script>
<script>
function savePaymentAccountTest(){
      				 if ($('#paymentaccount_form').validate(false, validationSettings)){
		    var formData = getFormData("paymentaccount_form");
		    var paymentAccountType = $("#paymentAccountType").val();
		  var email =  $("#paymentAccountEmail").val();
		  var lastName =  $("#paymentAccountLastName").val();
		  var firstName =  $("#paymentAccountFirstName").val();
		  var bankName =  $("#paymentAccountBankName").val();
		  var accountNumber =  $("#paymentAccountNumber").val();
		  var bsrNumber =  $("#paymentBSRNumber").val();
		  var micrCode =  $("#paymentMICRCode").val();
		  var routingNumber =  $("#paymentAccountRoutingNumber").val();
		  var accountType =  $("#paymentAccountType").val();
		  var orgLocationId =  $("#paymentAccountLocation").val();
		  if(orgLocationId>0){
       //	$("#savePaymentButton").attr("disabled", "disabled");
       	$.ajax({
   			type : "POST",
       	    data: formData,
       	    processData: false, 
   	 		contentType: false,
   	 		
   			url : sourceleadUrl+"/save-payment-account?${_csrf.parameterName}=${_csrf.token}&email="+email+"&lastName="+lastName+"&firstName="+firstName+"&bankName="+bankName+"&accountNumber="+accountNumber+"&bsrNumber="+bsrNumber+"&micrCode="+micrCode+"&routingNumber="+routingNumber+"&accountType="+accountType+"&orgLocationId="+orgLocationId,
   			success : function(data) {
   			if(data.response === "SUCCESS" || data.response === "UNABLE_SET_PAYMENT_ACCOUNT") {
                   closePaymentAccountPopup(accountType);
                   $("#savePaymentButton").removeAttr("disabled", "disabled");
                   location.reload();
               }
               getPaymentAccounts(true);
   			},
       	});
           return true;
 	  }else{
 		  alert("enter location ")
 	  }
      }else{
 		return false;
 	  } 
}
   
   $('body').on('blur', '#paymentaccount_form', function() {
		$("#paymentaccount_form").showHelpOnFocus();
		$("#paymentaccount_form").validateOnBlur(false, validationSettings);  
  });
   
   var validationSettings = {
           errorMessagePosition : 'element',
           borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
           };

</script>
<script>
    function savePaymentAccount() {
    	var countryName = '${SourceLeadWeb_country}';
        var data =  {};
        var val = $("#paymentAccountType").val();
        var flag = false;
        if(validateSelect('paymentAccountType', 'Account Type')) {
            if(val === "WEPAY") {
                if(validateEmail('paymentAccountEmail')
                        && validateFirstName('paymentAccountFirstName', "First Name")
                        && validateLastName('paymentAccountLastName', "Last Name")
                        && validateSelect("paymentAccountLocation", 'Location')) {
                    data["email"] = $("#paymentAccountEmail").val();
                    flag = true;
                }
            } else {
            	if( countryName =="India"){
            		if(validate('paymentAccountBankName', "Bank Name")
                            && validatepaymentAccountNumber('paymentAccountNumber', "Account Number")
                            && validateBISRNumber('paymentBSRNumber', "BSR Number")
                            && validatepaymentMICRcode('paymentMICRCode', "MICR Code")
                            && validate('paymentAccountRoutingNumber', "IFSC Code")
                            && validateFirstName('paymentAccountFirstName', "First Name")
                            && validateLastName('paymentAccountLastName', "Last Name")
                            && validateSelect("paymentAccountLocation", 'Location')) {
                    	data["micrCode"] = $("#paymentMICRCode").val().trim();
                    	data["bsrNumber"] = $("#paymentBSRNumber").val().trim();
                        data["accountNumber"] = $("#paymentAccountNumber").val().trim();
                        data["bankName"] = $("#paymentAccountBankName").val().trim();
                        data["routingNumber"] = $("#paymentAccountRoutingNumber").val().trim();
                        flag = true;
                    }
            	}else{
            		if(validate('paymentAccountBankName', "Bank Name")
                            && validatepaymentAccountNumber('paymentAccountNumber', "Account Number")
                            && validate('paymentAccountRoutingNumber', "Routing Number")
                            && validateFirstName('paymentAccountFirstName', "First Name")
                            && validateLastName('paymentAccountLastName', "Last Name")
                            && validateSelect("paymentAccountLocation", 'Location')) {
                        data["accountNumber"] = $("#paymentAccountNumber").val().trim();
                        data["bankName"] = $("#paymentAccountBankName").val().trim();
                        data["routingNumber"] = $("#paymentAccountRoutingNumber").val().trim();
                        flag = true;
                    }
            	}
            }
            data["firstName"] = $("#paymentAccountFirstName").val().trim();
            data["lastName"] = $("#paymentAccountLastName").val().trim();
            data["orgLocationId"] = $("#paymentAccountLocation").val();
            data["accountType"] = val;
            data["id"] = $("#paymentAccountId").val();
        }
        if(flag) {
        	$("#savePaymentButton").attr("disabled", "disabled");
            $.post(sourceleadUrl+"/save-payment-account", data, function(data) {
                showError(data.responseMessage);
                if(data.response === "SUCCESS" || data.response === "UNABLE_SET_PAYMENT_ACCOUNT") {
                    closePaymentAccountPopup(val);
                    $("#savePaymentButton").removeAttr("disabled", "disabled");
                }
                getPaymentAccounts(true);
            });
        }
    }
function getPaymentAccounts(flag) {
    if(!flag) {
        var idd = ${SourceLeadWeb_orgLocationId};
        $("#paymentAccountLocation").empty();
        $("#paymentAccountLocation").append("<option value=''>Select Location</option>");
        $.post(sourceleadUrl+"/org-location-list", {}, function(data) {
            if(data && data.response === "SUCCESS") {
                for (var e in data.data) {
                    var report= data.data[e];
                    $("#paymentAccountLocation").append("<option data-country='"+report.country+"' "+(idd === report.id ? "selected='selected'" : "")+" value='"+report.id+"'>"+report.orgLocationName+"</option>");
                }
            }
        });
    }
    $.post(sourceleadUrl+"/payment-account-list", {}, function(data) {
        if(data && data.response === "SUCCESS") {
            $("#payemntAccountsDiv").empty();
            var country = '${SourceLeadWeb_orgLocations.country}'
            var result = '';
            result = result + '<table id="payemntAccountsTable" class="display" cellspacing="0" width="100%" >';
            result = result + ' <thead>';
            result = result + '   <tr>';
            result = result + '    <th>Type</th>';
            result = result + '    <th>Email</th>';
            result = result + '    <th>Bank Name</th>';
            result = result + '    <th>Account Number</th>';
            if(country == "India"){
            	 result = result + '    <th>IFSC Code</th>';
            }
            else{
            	result = result + '    <th>Routing Number</th>';
            }
            
            result = result + '    <th>First Name</th>';
            result = result + '    <th>Last Name</th>';
            result = result + '    <th>Location</th>';
            result = result + '    <th>Default</th>';
            result = result + '    <th>Active</th>';
            result = result + '    <th></th>';
            result = result + '   </tr>';
            result = result + '	</thead>';
            result = result + '	<tbody>';
            
            for (var e in data.data) {
                var report= data.data[e];
                result = result + '<tr>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-75">'+(report.accountType ? report.accountType : "")+'</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-125">'+(report.email ? report.email : "--")+'</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-125">'+(report.bankName ? report.bankName : "--")+'</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-75">'+(report.accountNumber ? report.accountNumber : "--")+'</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-75">'+(report.routingNumber ? report.routingNumber : "--")+'</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-75">'+(report.firstName ? report.firstName : "")+'</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-75">'+(report.lastName ? report.lastName : "")+'</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-125">'+(report.orgLocationName ? report.orgLocationName : "")+'</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-50"><div class="toggle-button"><label class="switch" style="top: 5px;"><input onclick="changeDefaultPaymentAccount(this, '+report.id+')" type="checkbox" '+(report.defaultAccount ? 'checked' : '')+'/> <div class="slider round"></div> </label> </div></div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-50"><div class="toggle-button"><label class="switch" style="top: 5px;"><input onclick="changeActivePaymentAccount('+report.id+')" type="checkbox" '+(!report.disabled ? 'checked' : '')+'/> <div class="slider round"></div> </label> </div></div>';
                result = result + '</td>';
                
                if(report.accountType !== 'WEPAY') {
                    result = result + '<td>';
                    result = result + '<div class="tblminwidth-50"><a href="javascript:void(0)" onclick="editPaymentAccount(\''+report.accountType+'\','+ report.id+','+report.orgLocationId+',\''+report.firstName+'\',\''+report.lastName+'\',\''+report.accountNumber+'\',\''+report.routingNumber+'\',\''+report.bankName+'\',\''+report.bsrNumber+'\',\''+report.micrCode+'\')">Edit</a></div>';
                    result = result + '</td>';
                } else {
                    result = result + '<td>';
                    result = result + '<div class="tblminwidth-50">'+(report.access ? "" : "<a href='javascript:void(0)' onclick='grantWePayAccess("+report.id+")'>Resend Mail</a>")+'</div>';
                    result = result + '</td>';
                    
                }
                result = result + '</tr>';
            }
            result = result + '	</tbody>';
            result = result + '</table>';
            $("#payemntAccountsDiv").append(result);
            $('#payemntAccountsTable').DataTable({ 
                        "scrollCollapse": false,
                "lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
                "bLengthChange": true,
                "bDestroy": true,
                "ordering": true,
                "oLanguage": {
                        "sSearch": "",
                         "sZeroRecords": "", "sEmptyTable": ""
                }
            });
        } else if(data && data.response === "SESSION_TIMEDOUT") {
            showError("Session timedout");
            window.setTimeout('location.href='+sourceleadLoginUrl, 3000);
        } else if(data) {
            showError(data.responseMessage);
        } else {
            showError("Operation failed, please try again");
        }
    });
    
}

function grantWePayAccess(id) {
    $.post(sourceleadUrl+"/resend-wepay-mail", {id: id}, function(data) {
        showError(data.responseMessage);
    });
}

function changeDefaultPaymentAccount(ele, id) {
    $.post(sourceleadUrl+"/change-default-payment-account", {id: id, defaultAccount: $(ele).is(":checked")}, function(data) {
        showError(data.responseMessage);
        getPaymentAccounts();
    });
}
function changeActivePaymentAccount(id){
	 $.post(sourceleadUrl+"/change-active-payment-account", {id: id}, function(data) {
	        showError(data.responseMessage);
	        
	        getPaymentAccounts();
	    });
}

function editPaymentAccount(type, id, locationId, firstName, lastName, accountNumber, routingNumber, bankName,bsrNumber,micrCode) {
	$("[data-fieldtype=account]").hide();
	showOrHidePaymentFormFields(type);
	$("#paymentAccountPopup").fadeIn(100);
	$(".mask").show();
	$("#paymentAccountFirstName").val(firstName);
	$("#paymentAccountBankName").val(bankName);
	$("#paymentAccountLastName").val(lastName);
	$("#paymentAccountLocation").val(locationId);
	$("#paymentAccountNumber").val(accountNumber);
	$("#paymentBSRNumber").val(bsrNumber);
	$("#paymentMICRCode").val(micrCode);
	$("#paymentAccountRoutingNumber").val(routingNumber);
	$("#paymentAccountId").val(id);
}
        
function showPaymentAccountPopup() {
    $("#paymentAccountId").val('0');
    $("[data-fieldtype=account]").hide();
    showOrHidePaymentFormFields('SAVINGS');
    $("#paymentAccountPopup").fadeIn(100);
    $(".mask").show();
    $("input[id*=paymentAccount]").val("");
    $('#paymentaccount_form')[0].reset();
}
function showOrHidePaymentFormFields(type) {
    $("#paymentAccountLocation").val('');
    $("#paymentAccountLocation option").removeProp('disabled');
    if(type === "WEPAY") {
        $("#paymentAccountLocation option").prop('disabled', 'disabled');
        $("#paymentAccountLocation option").each(function() {
            if($(this).data('country') === "USA" || $(this).data('country') === "United States"
                    ||  $(this).attr('value') === '0' ||  $(this).attr('value') === 0)
                $(this).removeProp('disabled');        
        });
    }
    $("[data-fieldtype=account]").hide();
    $("#paymentAccountType").val(type);
    var id = $("#paymentAccountType option[value="+type+"]").data('type');
    $("[data-fieldtype=account]").each(function() {
        var val = $(this).data('fieldfor');
        if(typeof val === "number") {
            if(parseInt(val) === parseInt(id)) 
                $(this).show();
        } else {
            val = val.split(",");
            for(var e in val) {
                if(parseInt(val[e]) === parseInt(id)) {
                    $(this).show();
                }
            }
        }
    });
}
function closePaymentAccountPopup(type) {
    $("#savePaymentButton").removeAttr("disabled", false);
    $('#bankNameErr').find('p.jquery_form_error_message').remove();
	 $('#accnoErr').find('p.jquery_form_error_message').remove();
	 $('#BSRErr').find('p.jquery_form_error_message').remove();
	 $('#MICRErr').find('p.jquery_form_error_message').remove();
	 $('#routing').find('p.jquery_form_error_message').remove();
	 $('#ifsc').find('p.jquery_form_error_message').remove();
	 $('#firstnameErr').find('p.jquery_form_error_message').remove();
	 $('#lastnameErr').find('p.jquery_form_error_message').remove();
	 $('#emailErr').find('p.jquery_form_error_message').remove();
	// $('#acclocationErr').find('p.jquery_form_error_message').remove();
	    $("#paymentAccountPopup").fadeOut(100);
	    $(".mask").hide();

    if(type) {
    	paymentAccountLocation('0');
        showOrHidePaymentFormFields(type);
        $("input[id*=paymentAccount]").val("");
    }
}
function getWhiteListIps(){
	$.ajax({
      type : "GET",
       url: sourceleadUrl+"/getWhiteListIps",        
       success :function(data) {
    	   var result = '';
    		 	result = result +  '<table id="WhiteListIpTable" class="display" cellspacing="0" width="100%" >';
    			result = result + '	<thead>';
    			result = result + '   <tr>';
    			result = result + '    <th>From Ip</th>';
    			result = result + '    <th>To Ip</th>';
    			result = result + '    <th>Action</th>';
    			result = result + '   </tr>';
    			result = result + '	</thead>';
    			result = result + '	<tbody>';
    		if(data.length > 0){
    			    for (var i = 0; i < data.length; i++) {
    			    	var report= data[i];
    			    	result = result + '<tr>';
    			    	result = result + '<td>';
    		           	result = result + '<div class="tblminwidth-75">'+report.fromIpAddress+'</div>';
    		        	result = result + '</td>';
    		        	result = result + '<td>';
    		           	result = result + '<div class="tblminwidth-75">'+report.toIpAddress+'</div>';
    		        	result = result + '</td>';	 
    		        	result = result + '<td>';
    		        	result = result +'     <div class="tblminwidth-50">                                                                                            ';
    		        	result = result +'          <span title="Edit" class="paddingRight"><a onclick="editWhiteListIp('+report.id+');"><i class="fa fa-pencil text-primary cursorPointer"></i></a></span>             ';
    		        	result = result +'          <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer" onclick="deleteWhiteListIp(\''+report.id+'\');"></i></span>              ';
    		        	result = result +'      </div> ';
    		        	result = result + '</td>';
    			        result = result + '</tr>';
    			    }
    				
    		}
    		result = result + '	</tbody>';
    		result = result + '</table>';
    		$("#whiteListIpsDiv").empty();
    		$("#whiteListIpsDiv").append(result);
    		WhiteListIpTablePagination();
       	} 
      }); 
}


function WhiteListIpTablePagination(){
	$('#WhiteListIpTable').DataTable({ 
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
}
</script>

<script>
function editWhiteListIp(id){
	$("#saveWhiteListbtn").removeAttr("disabled");
	$.ajax({
	    type: "GET",
        url: sourceleadUrl+"/editWhiteListIp?whiteListId="+id,
        success: function(data) {
        	$("#addipPopup").fadeIn(100);
        	$(".mask").show();
        	if(data.id != null && data.id != '' && data.id != 'undefined'){
        		$("#whiteListId").val(data.id);
        	}
        	if(data.fromIpAddress != null && data.fromIpAddress != '' && data.fromIpAddress != 'undefined'){
        		$("#fromIpAddress").val(data.fromIpAddress);
        	}
			if(data.toIpAddress != null && data.toIpAddress != '' && data.toIpAddress != 'undefined'){
				$("#toIpAddress").val(data.toIpAddress);
        	}
        	
        }
	});
}
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
       	            url: sourceleadUrl+"/validateRegiseredCompanyName?companyName="+companyName,
			        success: function(data) {
			        	 var result = data;
			        		if(result == "Company Duplicated") {
		        			//Existed company
		    	    		$('#companyNameDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#orgLocationCompanyName').attr('class','error form-control');
		    	    		$("input#orgLocationCompanyName").closest('b').after("<p class='jquery_form_error_message'> "+company+" "+result+"</p>");
		    				$('input#orgLocationCompanyName').attr('record-exist','yes');
		    				$('input#orgLocationCompanyName').attr('record-exist-errorMsg',company+' '+result+''); 
		    				$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
			        	}
			        	else{
			        		//New company
			        		$("#save_button").removeAttr("disabled");
				    		$('input#orgLocationCompanyName').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyNameDiv').find('p.jquery_form_error_message').remove();
							$('input#orgLocationCompanyName').attr('class','valid form-control');
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
	 getDBAName();
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
	          		
	          		var sourceleadUrl ='${sourceleadUrl}';
	           		var formData = $($('#orgSettings_form')[0]).serialize();
	           		var uploadedImage = document.getElementById('uploadedImage').value;
             		var imageExtension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
             		if(imageExtension == 'gif' || imageExtension == 'png' || imageExtension == 'svg'){
		        		document.getElementById("errorImage").style.display=""; 
             		}else{
             		$("#loading").attr('style','position:absolute; width:100%; height:150%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          		
	                  $.ajax({
	              	      type: "POST",
	              	      data: formData,
	                    	  url: sourceleadUrl+"/saveOrgSettings?"+tokenParamName+"="+tokenValue+"",
	                      	success: function(result) {
	                      		if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
	            	    		    showError(result.responseMessage);
	            	    		    $("#loading").removeAttr('style');
	            	    		}
	                      		else if (result.response === "AWKWARD") {
	            	    		    location.href = "/errorPage";
	            	    		}
	                      		else if(result.response === "SUCCESS"){
	  			        		location.replace(sourceleadUrl+"/orgSettings");  
	  			        	    }
	                      		else if (result.indexOf('Sign In') !== -1) {
	            					location.replace(sourceleadUrl);
	            				} else {
	            				    showError("Operation failed, please try again");
	            				    $("#loading").removeAttr('style');
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
<script>
function configCoolingOff(){
	$("#coolingOffPeriodDiv").empty(); 
	var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCoolingOffPeriod?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(coolingOffPeriod) {
					var result = "";
					if(null != coolingOffPeriod && '' != coolingOffPeriod && 'undefined' != coolingOffPeriod){
						coolingOffPeriod = coolingOffPeriod;
					}else{
						coolingOffPeriod = '0 Days';
					}
					result = result + '<form id="coolingOff_form"  name="coolingOff_form" method="post">'; 
					
					result = result + '<div class="form-group">';
					result = result + '<label class="col-md-3">Cooling Off Period &nbsp;&nbsp;<i class="fa fa-info-circle" title="A Cooling Off Period is the time during which an applicant is disqualified from applying for Jobs, because of applied recently."></i></label>';
					result = result + '<div class="col-md-4 select-field selectChosen">';
					result = result + '<select class="form-control" name="coolingOff" id="coolingOff" field-name="CoolingOff">';
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
	 
	 $("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 
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
						
						result = result + '<div class="col-md-2 select-field" style="width:19.66666667%;">';
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
						
						result = result + '<div class="col-md-2 select-field" style="width:13.66666667%;">';
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
							result = result + '<div class="col-md-3" title="Text Box"><img id="orgImage" onerror="predefineOrgImage(this);" data-id="orgLogoImage" src="${SourceLeadWeb_fileUrl}" alt="Org Image" style="height:60px; width: 100px;"></div>';
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
						//$('#cp'+opt.id+'').colorpicker();
 					}
					
					
					
		        },
		 });
}
</script>

<script>
function resetDocument(){
	$('#uploadFileGroup').find('p.jquery_form_error_message').remove();
	$('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
	$('#uploadFileGroup2').find('p.jquery_form_error_message').remove();
	$('#uploadFileGroup3').find('p.jquery_form_error_message').remove();
	$('#uploadFileGroup4').find('p.jquery_form_error_message').remove();
}

function resetEmailConfguration(){
	//$("email_configuration_form").validate().resetForm();
}
function colourpicker(){
	//$('#cp2, #cp3').colorpicker();
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
  		    $("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      		
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
        		  var sourceleadUrl = '${sourceleadUrl}';
        		    e.preventDefault(); 
        			var frm = $('#signature_form').serializeFormJSON();
        			var con = JSON.stringify(frm);
        			con = con.replace(/[{}]/g, ""); 
        		    var jsonData = encodeURIComponent(con);
        		    $("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	           		
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
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	
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
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to restore</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='restSignature();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#restorePopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".mask").show();

}
</script>

<script type="text/javascript">
function cancel(){
	$("#restorePopUp").empty();
	$(".mask").hide();
}
</script>

<script type="text/javascript">
function restSignature(){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	$("#restorePopUp").empty();
	$(".mask").hide();
	$("#signLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	   $.ajax({
			type : "POST",
			url : atsUrl+"/restoreSignature?${_csrf.parameterName}=${_csrf.token}",
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
     $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
     
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
	 				
	       	    	result = "<div class='col-xs-12 col-sm-12 col-md-6 col-lg-4 noPad'>";
	       	    	result = result +"<div class='user-profile'>";
	       	    	result = result +"<div class='col-xs-4 col-sm-4'>";
	       	    	result = result +"<div class='text-center image-parent'>";
	       	    	result = result +"<div class='imageParent'>";
	       	    	result = result +"<img onerror='predefineOrgImage(this);' class='m-t-xs' data-id='orgLogoImage' src="+orgLoc.fileUrl+" alt='image'>";
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
    $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    
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
	    	        result = result +'<label class="col-md-4 text-right">Company :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['orgLocationName']+'</p>';
	    	        result = result +'</div>';
	    								 
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4 text-right">Website :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+website+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4 text-right">Country :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['country']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4 text-right">State :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['state']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4 text-right">City :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['city']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        if(null != json['orgLocationAddress'] &&  json['orgLocationAddress'] != 'undefined' && json['orgLocationAddress'] != ''){
	    	        	    result = result +'<div class="form-group">';
	    	    	        result = result +'<label class="col-md-4 text-right">Address :</label>';
	    	    	        result = result +'<p class="tbldata-dotdot-me">'+json['orgLocationAddress']+'</p>';
	    	    	        result = result +'</div>';	
	    	        }
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4 text-right">Enrolled Date :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['createdDateStr']+'</p>';
	    	        result = result +'</div>';
	    								
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4 text-right">Time Zone :</label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['timeZoneName']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4 text-right">Date Format :</label>';
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
	    	        result = result +'<label class="col-md-4 text-right">Contact User : </label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['orglocationUserName']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        result = result +'<div class="form-group">';
	    	        result = result +'<label class="col-md-4 text-right">Email : </label>';
	    	        result = result +'<p class="tbldata-dotdot-me">'+json['orglocationUserEmail']+'</p>';
	    	        result = result +'</div>';
	    	        
	    	        if(null != json['orglocationUserPhone'] &&  json['orglocationUserPhone'] != 'undefined' && json['orglocationUserPhone'] != ''){
	    	        	result = result +'<div class="form-group">';
	 	    	        result = result +'<label class="col-md-4 text-right">Mobile : </label>';
	 	    	        result = result +'<p class="tbldata-dotdot-me">'+json['orglocationUserPhone']+'</p>';
	 	    	        result = result +'</div>';
	    	        }
	    	        
    	            result = result +'</div>';
    	            
		    	        
    	                result = result +'</div>'; 
    	       	
    	       	    	
    	    	$("#loading").removeAttr("style"); 
    	 	    $('#registeredOrgLocationSummary').append(result);
    	 }
    }); 
}
</script>


<!-- <script type="text/javascript">
	function updateModules(orgLocationId) {
		
			var sourceleadUrl ='${sourceleadUrl}'; 
			
			
			 
	 	 
		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({ 
		    	type: "POST",
		    	 url: sourceleadUrl+"/updateOrgModules?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId+"&submodulesList="+submodulesList+"&orgLocationId="+orgLocationId,
		        success: function(result) {
		        	if((result == 'success')){
		        		loadRegisteredOrgs();
			        } 
		        },
		 });  
		  
	}

</script> -->





<script type="text/javascript">
function resendInvitation(userId){
	var sourceleadUrl ='${sourceleadUrl}';
	if(userId != null && userId != ""){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendInvitationEmail?${_csrf.parameterName}=${_csrf.token}&userId="+userId,  
			        success: function(result) {
			        	$("#user"+userId).html("<i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)'  onClick='resendInvitation("+userId+");'>&nbsp;Resend</a>");
			        	$("#loading").removeAttr("style"); 
			        	$("#resendOrg").fadeIn(100);
		   		        $('#resendOrg').delay(2000).fadeOut(100);
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
	
	var subscribedCountries = '${subscribedCountries}';
	var countries= [];
	var res = subscribedCountries.split(",");
	for(var i=0; i <res.length; i++){
		countries.push(res[i]);
	};
	
	var result = '';
	
	result = result +'<div class="row">';
	result = result +'<div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6 mt10">';
           
	result = result +'<div class="form-group" id="companyNameDiv">';
	result = result +'<label class="col-md-3">Company Name<span class="text-danger">&nbsp;*</span></label>';
	result = result +'<div class="col-md-9">';
	result = result +'<input type="text" name="companyName" id="companyName"  value="${companyName}" class="form-control" onblur="validateRegisterCompanyName();"  field-name="Company Name" data-validation="required validate_Space validate_length length2-250" />';
	result = result +'<span class="hidden">Company</span>';
	result = result +'</div>';
	result = result +'</div>';
	
	result = result +'<div class="form-group">';
	result = result +'<label class="col-md-3">DBA<span class="text-danger">&nbsp;*</span></label>';
	result = result +'<div class="col-md-9">';
	result = result +'<div class="col-md-11 noPad">';
	result = result +'<input id="dbaLoc" name="dba" class="form-control" type="text" field-name="DBA" data-validation="required validate_Space validate_length length2-250">';
	result = result +'</div>';
	result = result +'<div class="col-md-1 dbaCheck dbaCheck-cs">';
             
	result = result +'<div class="ml10">';
	result = result +'<input id="dbaLocCheckbox" name="dbaLocCheckbox"  class="checkbox-custom" onclick="getLocDBAName();"  type="checkbox">';
	result = result +'<label for="dbaLocCheckbox" class="checkbox-custom-label" title="Make company name as dba"></label>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';	
	
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Website<span class="text-danger">&nbsp;*</span></label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<input type="text" name="website"  value="${website}"   id="companyWebsite"  onblur="validateWebsite();validateCompanyEmail();"  field-name=" Website" data-validation="required validate_Space validate_domain validate_length length2-100"    class="form-control" />';
	            result = result +'<span class="hidden">Website</span>';
	            result = result +'</div>';
	            result = result +'</div>';
	            
	            result = result +'<input type="hidden" name="registeredOrgId" id="registeredOrgId"  value="${SourceLeadWeb_orgId}" />';
           
	            result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<input type="text" name="email" id="companyEmail"   value="${email}" onblur="validateCompanyEmail();validateWebsite();getUserDetails();" field-name="Email" data-validation="required validate_Space validate_email validate_length length2-100" class="form-control"  />';
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
	            result = result +'<input type="text" name="mobilePhone"  id="mobilePhone"  value="${mobilePhone}" class="form-control" field-name="Phone Number" data-validation-optional="true" data-validation="validate_Space validate_phone validate_length length9-20"  />';
	            result = result +'<span class="hidden">Phone Number</span>';
	            result = result +'</div>';
	            result = result +'</div>';
	            
	            result = result +'<div class="form-group" >';
	        	result = result +'<label class="col-md-3">Org Short Code<span class="text-danger">*</span></label>';
	        	result = result +'<div class="col-md-9">';
	        	result = result +'<div id="generatedShortCodeIdDiv">';
	        	result = result +'<input type="text"  class="form-control" name="generatedUserId" id="generatedShortCodeId" field-name="Org Short Code" style="text-transform:uppercase" data-validation="required validate_length length1-4"  onkeyup="validateOrgLocationShortCode();" maxlength="4" />';
	            result = result +'<span class="hidden">Short Code</span>';
	        	result = result +'</div>';
	        	result = result +'</div>';
	        	result = result +'</div>';
	        	
	        	
	        	result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Max hours per day<span class="text-danger">&nbsp;*</span></label>';
	            result = result +'<div class="col-md-9" id="averageLocWorkingHoursDiv">';
	            result = result +'<input type="text" class="form-control" onblur="validateWorkingHours()" id="averageLocWorkingHours" name="averageWorkingHours" maxlength="2" field-name="Max hours per day" data-validation="required validate_int" />';
	            result = result +'<span class="hidden">Max hours per day</span>';
	            result = result +'</div>';
	            result = result +'</div>';
          
        
	            result = result +'</div>';
                                 
	            result = result +'<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">';
          
				result = result +'           <div class="form-group">';
				result = result +' 			<label class="col-md-3">Address<span class="text-danger">&nbsp;*</span></label>';
				result = result +' 			 <div class="col-md-9" id="locationField">';
				result = result +' 		          <div class="positionRelative">';
				result = result +' 			          <input class="form-control positionRelative" id="orgSetLocation" name="location" placeholder="Enter Addres"  type="text" field-name="Location" onclick="orgSubscriptionApiLocation(\'orgSetLocation\',\'orgSetCountry\',\'orgSetState\',\'orgSetCity\',\'orgSetZipCode\',\''+countries+'\',\'addressId\');" data-validation="required"></input>';
				result = result +' 			          <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="addOrgRefreshGoogleApiLocation();"></i>';
				result = result +' 			        </div>';
				result = result +' 	          </div>';
				result = result +' 		 </div>';
				
			    result = result +'<div class="form-group">';
	            result = result +'<label class="col-md-3">Street Address</label>';
	            result = result +'<div class="col-md-9">';
	            result = result +'<input type="text" name="address" id="addressId" field-name="Street Address" data-validation-optional="true" placeholder="Enter Street Addres" data-validation="validate_length length5-5000" class="form-control"/>';
	            result = result +'</div>';
	            result = result +'</div>';
		            
	        	result = result +'       <div class="form-group">';
				result = result +'         <label class="col-md-3">City<span class="text-danger">&nbsp;*</span></label>';
				result = result +'         <div class="col-md-9">';
				result = result +'         <input class="form-control" id="orgSetCity" name="city" placeholder="City" type="text" field-name="City" data-validation="required"  ></input>';
				result = result +'         </div>';
				result = result +'       </div>';
				
				result = result +'       <div class="form-group">';
				result = result +'         <label class="col-md-3">State<span class="text-danger">&nbsp;*</span></label>';
				result = result +'         <div class="col-md-9">';
				result = result +'         <input class="form-control" id="orgSetState" name="state" placeholder="State" type="text" field-name="State" data-validation="required" ></input>';
				result = result +'         </div>';
				result = result +'       </div>';
				
			
				
				result = result +'       <div class="form-group">';
				result = result +'         <label class="col-md-3">ZipCode</label>';
				result = result +'         <div class="col-md-9">';
				result = result +'         <input class="form-control" id="orgSetZipCode" name="zipCode" placeholder="Zip code" field-name="Zip code" type="text"></input>';
				result = result +'         </div>';
				result = result +'       </div>';
				
				result = result +'       <div class="form-group" id="errCoutryMsg">';
				result = result +'         <label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>';
				result = result +'         <div class="col-md-9">';
			 	result = result + '   <select class="form-control chosen-select" name="country" id="orgSetCountry" field-name="Country" data-placeholder="Select Country" data-validation="required" onchange="changeCountry();">';
			 	result = result + '       <option value="">Select Country</option>';
			 	result = result +'		<c:forEach var="subscibedCountries" items="${orgSubscribedCountriesList}">';
			 	result = result + '       <option value="${subscibedCountries}">${subscibedCountries}</option>';
		        result = result +'		</c:forEach>';
                result = result + '    </select>';
				result = result +'         </div>';
				result = result +'       </div>';
				
     
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
	           
// 	            $("#orgSetCountry").chosen();
// 	            $("#orgSetCountry").trigger("chosen:updated");
	            
	 	       $("#locationTimeZone").chosen();
		    	   getTimezonesList();
		      
		       $('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({'width': '100%'});
			  })
	      	       
	
}
function changeCountry(){
	 $("#errCoutryMsg").find("p.jquery_form_error_message").remove();
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
function validateRegisterCompanyName(){
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
							$( "#register_button" ).addClass('btn btn-primary').val('Register');
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
       	            url: sourceleadUrl+"/validateOrgWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
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
       	            url: sourceleadUrl+"/validateOrgWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
			        success: function(data) {
			        	 var result = data;
			        		if (result == "Website Duplicated" || result == "Invalid Website") {
		        			//existed website
		    	    		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#companyWebsite').attr('class','error form-control');
		    	    		$("input#companyWebsite").closest('b').after("<p class='jquery_form_error_message'> "+website+" "+result+"</p>");
		    				$('input#companyWebsite').attr('record-exist','yes');
		    				$('input#companyWebsite').attr('record-exist-errorMsg',website+' '+result+''); 
		    				$( "#register_button" ).addClass('btn btn-primary').val('Register');
		    				$("#register_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New WebSite
			        		$("#register_button").removeAttr("disabled");
				    		$('input#companyWebsite').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
							$('input#companyWebsite').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('btn btn-primary').val('Register');
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
    var emailValue = document.getElementById("companyEmail").value;
    var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}'; 
    var sourceleadUrl ='${sourceleadUrl}';
		if((websiteValue != null && websiteValue != '') && (emailValue != null && emailValue != '')){
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/emailAndWebsiteRegistrationValidation?website="+websiteValue+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites+"&email="+emailValue,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid Email'){
					    	    //Invalid Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#companyEmail').attr('class','error form-control');
			    	    		$("input#companyEmail").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Invalid</p>");
			    				$('input#companyEmail').attr('record-exist','yes');
			    				$('input#companyEmail').attr('record-exist-errorMsg',emailValue+' is Invalid'); 
			    				$( "#register_button" ).addClass('btn btn-primary').val('Register');
			    				$("#register_button").removeAttr("disabled");
					     } 
					     else if(data == 'Existed Email'){
					    	   //Existed Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#companyEmail').attr('class','error form-control');
			    	    		$("input#companyEmail").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Already Existed</p>");
			    				$('input#companyEmail').attr('record-exist','yes');
			    				$('input#companyEmail').attr('record-exist-errorMsg',emailValue+' is Already Existed'); 
			    				$( "#register_button" ).addClass('btn btn-primary').val('Register');
			    				$("#register_button").removeAttr("disabled");
					    	 
					     }
				       	else{
				       		//Valid Email
				       		$("#register_button").removeAttr("disabled");
				    		$('input#email').removeAttr( "class record-exist record-exist-errormsg");
							$('#emailDiv').find('p.jquery_form_error_message').remove();
							$('input#companyEmail').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('btn btn-primary').val('Register');
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
	var email = document.getElementById("companyEmail").value;
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
        		result = result + '<input type="text" value="'+json['orglocationUserPhone']+'" name="mobilePhone" id="mobilePhone" field-name="Phone Number"  data-validation="validate_Space validate_length length9-20 validate_phone" class="form-control" data-validation-optional="true">';
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
		 
         var sourceleadUrl ='${sourceleadUrl}';
         var formData = getFormData("editPrimaryUser_form");
         $("#editPrimaryUserLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
          
          $.ajax({
             type: "POST",
             data: formData,
             url: sourceleadUrl+"/editOrgPrimaryUser?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId,
             success: function(result) {
            		if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
    	    		    showError(result.responseMessage);
    	    		    $("#editPrimaryUserLoading").removeAttr('style');
    	    		}
              		else if (result.response === "AWKWARD") {
    	    		    location.href = "/errorPage";
    	    		}
              		else if(result.response === "SUCCESS"){
              			 $("#editPrimaryUserLoading").removeAttr("style");
                    	 getOrgLocationDetails(orgLocationId); 
		        	    }
              		else if (result.indexOf('Sign In') !== -1) {
    					location.replace(sourceleadUrl);
    				} else {
    				    showError("Operation failed, please try again");
    				    $("#editPrimaryUserLoading").removeAttr('style');
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
            	
        	
         		var sourceleadUrl ='${sourceleadUrl}';
         		var formData = getFormData("createOrg_form");
         		
         		
         		$("#regLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         		 	$.ajax({
          	      type: "POST",
          	      data: formData,
                	  url: sourceleadUrl+"/companyLocationRegistration?${_csrf.parameterName}=${_csrf.token}",
                			  
                			  
                  	success: function(result) {
                  		
                  		if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        	    		    showError(result.responseMessage);
        	    		    $("#regLoading").removeAttr('style');
        	    		}
                  		else if (result.response === "AWKWARD") {
        	    		    location.href = "/errorPage";
        	    		}
                  		else if(result.response === "SUCCESS"){
                  		   $("#regLoading").removeAttr('style');
                  			loadRegisteredOrgs();  
			        	    }
                  		else if (result.indexOf('Sign In') !== -1) {
        					location.replace(sourceleadUrl);
        				} else {
        				    showError("Operation failed, please try again");
        				    $("#regLoading").removeAttr('style');
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
	  var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	  location.replace(sourceleadUrl+"/settings");
}

</script>


<script>
function validateOrgShortCode(){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgShortId = $("#generatedUserId").val();
	$('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		  $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/validateOrgShortCode?${_csrf.parameterName}=${_csrf.token}&orgShortId="+orgShortId, 
		        success: function(data) {
		        	if(data == true){
		        		$('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		        		$("input#generatedUserId").parent().after("<p class='jquery_form_error_message'><span>Org Short Code Already Exists !!!</span>");
		        		$("#saveOrgConfiguration").attr("disabled","disabled");
		        		
		        	}else{
		        		 $('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		        		 $("#saveOrgConfiguration").removeAttr("disabled","disabled");
		        	}
		        }
		 }); 
}

function validateOrgLocationShortCode(){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgLocShortId = $("#generatedShortCodeId").val();
	$('#generatedShortCodeIdDiv').parent().find('p.jquery_form_error_message').remove();
		  $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/validateOrgShortCode?${_csrf.parameterName}=${_csrf.token}&orgShortId="+orgLocShortId, 
		        success: function(data) {
		        	if(data == true){
		        		$('#generatedShortCodeIdDiv').parent().find('p.jquery_form_error_message').remove();
		        		$("input#generatedShortCodeId").parent().after("<p class='jquery_form_error_message'><span>Org Short Code Already Exists !!!</span>");
		        		$("#register_button").attr("disabled","disabled");
		        		
		        	}else{
		        		 $('#generatedShortCodeIdDiv').parent().find('p.jquery_form_error_message').remove();
		        		 $("#register_button").removeAttr("disabled","disabled");
		        	}
		        }
		 }); 
}


$(function(){
	$("#generatedUserId").keypress(function (event) {
        return event.key.length > 1 || event.ctrlKey || !!event.key.match(/[a-zA-Z]/);
    });
});


$(function(){
	$("#generatedShortCodeId").keypress(function (event) {
        return event.key.length > 1 || event.ctrlKey || !!event.key.match(/[a-zA-Z]/);
    });
});

$(function() {
	  $('#averageWorkingHoursDiv').on('keydown', '#averageWorkingHours', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
});

$(function(){
    $("#averageWorkingHours").keyup(function(event){
        var ew = $("#averageWorkingHours").val();
        $('#averageWorkingHoursDiv').parent().find('p.jquery_form_error_message').remove(); 
        if(ew > 24){
//         	$("#averageWorkingHours").val('');
        	$("input#averageWorkingHours").parent().after("<p class='jquery_form_error_message'><span>Max hours per day should be less then or equal to 24</span>");
        	$('#averageWorkingHours').removeAttr('data-validation');
        	$("#save_button").attr("disabled","true");
        }else{
        	$("#save_button").removeAttr("disabled","false");
        }
        
    });
});

$(function() {
	  $('#averageLocWorkingHoursDiv').on('keydown', '#averageLocWorkingHours', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
});

function validateWorkingHours(){
      var ew = $("#averageLocWorkingHours").val();
      $('#averageLocWorkingHoursDiv').parent().find('p.jquery_form_error_message').remove(); 
      if(ew > 24){
      	$("input#averageLocWorkingHours").parent().after("<p class='jquery_form_error_message'><span>Max hours per day should be less then or equal to 24</span>");
      	$("#register_button").attr("disabled","true");
      }else{
    	  $("#register_button").removeAttr("disabled","false");
      }
}


</script>


<script>
function getDBAName(){
	 if (document.getElementById('dbaCheckbox').checked) {
		 var companyName = document.getElementById("orgLocationCompanyName").value;
		 if(companyName != null && companyName != '' && companyName != 'undefined'){
			$("#dba").val(companyName); 
		 }
     }else{
    	 var dbaName = '${SourceLeadWeb_orgLocations.dba}';
    	 if(dbaName != null && dbaName != '' && dbaName != 'undefined'){
    	 $("#dba").val(dbaName);
    	 }else{
    	 $("#dba").val('');
    	 }
     } 
}
</script>


<script>
function getLocDBAName(){
	 if (document.getElementById('dbaLocCheckbox').checked) {
		 var companyName = document.getElementById("companyName").value;
		 if(companyName != null && companyName != '' && companyName != 'undefined'){
			$("#dbaLoc").val(companyName); 
		 }
     }else{
    	 $("#dbaLoc").val('');
     } 
}
</script>

<script>
function getBusinessTypeValue(){
 	var businessType = document.getElementById("businessType").value;
	
	if(businessType && businessType == 'Other'){
		 document.getElementById("otherBusinessTypeDiv").style.display="";
		 $("#otherBusinessType").removeAttr("data-validation-optional");
		 $("#otherBusinessType").attr("data-validation","required validate_Space validate_AlphaNumber validate_length length1-100");
	}else{
		    document.getElementById("otherBusinessTypeDiv").style.display="none";
			$("#otherBusinessType").removeAttr("data-validation-optional");
			 $("#otherBusinessType").attr("data-validation-optional","true");
			 $("#otherBusinessType").removeAttr("data-validation");
			 $("#otherBusinessType").attr("data-validation","required validate_Space validate_AlphaNumber validate_length length1-100");
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

<script>
function addipPopup(){
	$("#saveWhiteListbtn").removeAttr("disabled");
	$('#fromIpAddressDiv').val("");
	$('#toIpAddressDiv').val("");
	$('#fromIpAddressDiv').find('p.jquery_form_error_message').remove();
	$('#toIpAddressDiv').find('p.jquery_form_error_message').remove();
	$("#addipPopup").fadeIn(100);
	$(".mask").show();
}
$(function(){
$(".close").click(function(){
	$("#addipPopup").fadeOut(100);
	$(".mask").hide();
});
})
</script>

<script>
function ValidateIPaddress()  
	{
		var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;  
		var fromIpAdd = $("#fromIpAddress").val();
		var toIpAdd = $("#toIpAddress").val();
		if(fromIpAdd != '' && fromIpAdd != null && fromIpAdd != 'undefined'){
			if(!fromIpAdd.match(ipformat))  
			{
				document.getElementById("saveWhiteListbtn").disabled = true;
			$('#fromIpAddressDiv').find('p.jquery_form_error_message').remove();
				$("input#fromIpAddress").after( "<p class='jquery_form_error_message'>You have entered an invalid IP address!</span>");
			}else{
				$('#fromIpAddressDiv').find('p.jquery_form_error_message').remove();
				document.getElementById("saveWhiteListbtn").disabled = false;
			}
		}
		
		if(toIpAdd != '' && toIpAdd != null && toIpAdd != 'undefined'){
			if(!toIpAdd.match(ipformat))  
			{
				document.getElementById("saveWhiteListbtn").disabled = true;
			$('#toIpAddressDiv').find('p.jquery_form_error_message').remove();
				$("input#toIpAddress").after( "<p class='jquery_form_error_message'>You have entered an invalid IP address!</span>");
			}else{
				document.getElementById("saveWhiteListbtn").disabled = false;

				$('#toIpAddressDiv').find('p.jquery_form_error_message').remove();
			}
		}
	}  
	
	
function validateDuplicateIpAddress(fromType){
	var ipAddress;
	if(fromType == "fromIpAddr"){
		ipAddress = $("#fromIpAddress").val();
	}else{
		ipAddress = $("#toIpAddress").val();
	}
	var userRoleId = '${SourceLeadWeb_userRoleId}';
	var whiteListId = $("#whiteListId").val();
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/ws/validateWhitelistIp?userRoleId="+userRoleId+"&ipAddress="+ipAddress+"&whiteListId="+whiteListId+"&accessingType=settings",        
	       success :function(result) {
	    	 if(result == true){
	    		 document.getElementById("saveWhiteListbtn").disabled = true;
	    		 if(fromType == "fromIpAddr"){
	 	 			$('#fromIpAddressDiv').find('p.jquery_form_error_message').remove();
	  				$("input#fromIpAddress").after( "<p class='jquery_form_error_message'>"+ipAddress+" already exists.</p>");
	    		 }else{
	 	 			$('#toIpAddressDiv').find('p.jquery_form_error_message').remove();
	  				$("input#toIpAddress").after( "<p class='jquery_form_error_message'>"+ipAddress+" already exists.</p>");
	    		 }
	    		
	    	 }
	      },
	  });
}
</script>


<script>
function deleteWhiteListIp(whiteListIpId){
	var result = "";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to delete ?</p>";
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteIP(\''+whiteListIpId+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".mask").show();
}

function cancelTermination(){
	$("#popUp").empty();
	 $(".mask").hide();
}

function deleteIP(id){
			 $.ajax({
				   	type: "POST",
				    url: sourceleadUrl+"/deleteIP?"+tokenParamName+"="+tokenValue+"&id="+id,
		   		    success: function(data) {
		   		    	$("#popUp").empty();
		   			 	$(".mask").hide();
		   			 getWhiteListIps()
		   		    }
			     });
}


</script>

<script>
function validateFinancialYear(){
	var startMonth = $("#financialStartYear").val();
	if(startMonth == ""){
		$("#financialEndYear").val("");
	}else if(startMonth == "January"){
		$("#financialEndYear").val("December");
	}else if(startMonth == "February"){
		$("#financialEndYear").val("January");
	}else if(startMonth == "March"){
		$("#financialEndYear").val("February");
	}else if(startMonth == "April"){
		$("#financialEndYear").val("March");
	}else if(startMonth == "May"){
		$("#financialEndYear").val("April");
	}else if(startMonth == "June"){
		$("#financialEndYear").val("May");
	}else if(startMonth == "July"){
		$("#financialEndYear").val("June");
	}else if(startMonth == "August"){
		$("#financialEndYear").val("July");
	}else if(startMonth == "September"){
		$("#financialEndYear").val("August");
	}else if(startMonth == "October"){
		$("#financialEndYear").val("September");
	}else if(startMonth == "November"){
		$("#financialEndYear").val("October");
	}else if(startMonth == "December"){
		$("#financialEndYear").val("November");
	}
	$("#financialEndYear").trigger("chosen:updated");
}
</script>

<script>
function financialYear(){
	
	var locationId = '${SourceLeadWeb_orgLocationId}';
	var startMonth = $("#financialStartYear").val();
	var endMonth = $("#financialEndYear").val();
	if(startMonth != null && startMonth != 'undefined' && endMonth != null && endMonth != 'undefined'){
		$.ajax({
			type : "GET",
			url : sourceleadUrl+"/saveFinanacialYear?&locationId="+locationId+"&startMonth="+startMonth+"&endMonth="+endMonth,
			success : function(result) {
				$("#selector1").removeAttr("style");
    			$("#selector1").css({ display: "block" });
    			$("#msgDiv").html("Details Updated Successfully");
		    	 msgFadein();
				
			},
		});
	}
}
</script>

<script>
	function msgFadein() {
		$("#selector1").fadeIn(100);
        $('#selector1').delay(2000).fadeOut(100);
	}
function changeMarketplaceCompanyNameConfig(ele) {
	$.ajax({
			type : "POST",
			url : sourceleadUrl+"/configShowCompanyNameInMarkeplace?${_csrf.parameterName}=${_csrf.token}&showCompnayName="+$(ele).is(":checked"),
			success : function(result) {
				if (result && result.response === "INVALID_DATA") {
                showError(result.responseMessage);
            }else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		$('#successMsgDiv').html("Display company name on marketplace updated successfully");
        		 $("#profilePercentageSelector").fadeIn(200).css({"width":"485px"});
        		  $('#profilePercentageSelector').delay(2000).fadeOut('slow');
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
			},
		});    
}
</script> 




<script>
function getProbationPeriod(){
	
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getProbationData?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(data) {
	        	
					showOrgprobationData(data);
	        }    
	 	});
}

function showOrgprobationData(data){
	var result = "";
	result = result + '<form id="probation_form"  name="probation_form" method="post">'; 
	
	result = result + '<div class="form-group">';
	result = result + '<label class="col-md-3">Probation Period &nbsp;&nbsp;<i class="fa fa-info-circle" title="This will represent the probation period for a associate in organisation."></i></label>';
	result = result + '<div class="col-md-4 select-field selectChosen">';
	result = result + '<select class="form-control chosen-select" id="probationPeriod" name="probationPeriod" field-name="Probation Period" data-validation = "required">';
	
	result = result + '<option value="">--Select Probation Period--</option>';
	result = result + ' <option value="15 Days" '+(data.probationPeriod == "15 Days" ? "selected" : '') +'>15 Days</option>';
	result = result + ' <option value="30 Days" '+(data.probationPeriod == "30 Days" ? "selected" : '') +'>1 Month</option>';
	result = result + '	<option  value="60 Days" '+ (data.probationPeriod == "60 Days" ? "selected" : '') +'>2 Months</option>';
	result = result + ' <option value="90 Days" '+(data.probationPeriod == "90 Days" ? "selected" : '') +'>3 Months</option>';
	
	result = result + ' </select>';
	result = result + '</div>';
	result = result + '</div>';
	
	
	result = result + '<div class="form-group">';
	result = result + '<label class="col-md-3">No of Days &nbsp;&nbsp;</label>  ';
	result = result + '<div class="col-md-4"><input type="text" name="noOfDays" value="'+data.noOfDays+'" id="noOfDays" class="form-control input-sm" data-validation="required validate_space validate_int" field-name="No of Days">';
		result = result + '</div>';
	result = result + '</div> ';
	
	
	result = result + '<div class="pull-right">';
	result = result + '<input type="submit" value="Save" id="probation_button" class="btn btn-primary mr">';
	result = result + '<input type="button" value="Cancel" class="btn btn-default" onClick="cancelBtn();">';
	result = result + '</div>';
	result = result + ' </form>';
	$("#probationPeriodDiv").empty(); 
	$("#probationPeriodDiv").append(result);
	
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
	 
       $("#probationPeriod").chosen();
       $("#probationPeriod").trigger('chosen:update');
}


$('body').on('submit', '#probation_form', function(e){
 	  if ($(this).validate(false, validationSettings)){
 		e.preventDefault();
 		var formData = getFormData("probation_form");
  		var probationPeriod = document.getElementById("probationPeriod").value;
  		var noOfDays = document.getElementById("noOfDays").value;
  		 	
   		 $.ajax({
				type : "POST",
				data : formData,
				url : sourceleadUrl+"/saveProbationData?${_csrf.parameterName}=${_csrf.token}",
				success : function(result) {
					
					 if (result && result.response === "INVALID_DATA") {
                     showError(result.responseMessage);
                 }else if (result.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (result.response === "SUCCESS") {
		        		$('#successMsgDiv').html("Probation Data Updated Successfully");
		        		 $("#profilePercentageSelector").fadeIn(200);
		        		  $('#profilePercentageSelector').delay(2000).fadeOut('slow');
		        	} else if (result.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	} 
				},
			});    
         
     	    return true;
     }
     else{
     	return false;	
        }
     });
     
  /* $('body').on('blur', '#probation_form', function() {
		$("#probation_form").showHelpOnFocus();
		$("#probation_form").validateOnBlur(false, validationSettings);
	}); */

</script>

<!-- <script>
function loadEmailConfig(){
	alert("hello")
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	      type : "POST",
	       url: sourceleadUrl+"/emailNontificationConfigure",        
	       success :function(data) {
//	    	   $("#loading").removeAttr('style');
	    	  /*  $("#enrolledLocDiv").removeAttr("style");
	    	   $("#enrolledLocDiv").attr("style","display : block");
	    	   $("#enrolledLocDiv").empty(); */
	    	   //$("#emailNotifications").empty();
	    	   $("#emailNotifications").append(data);
	    	  /*  $(".signWrap").addClass("orgOutWrap");
	    	   $(".SignSlide").hide();
	    	   $(".orgWrap").show(); */
//	    	   $(".signWrap").addClass("orgOutWrap");
	      },
	  }); 

}

</script> -->

<jsp:include page="masterFooter.jsp" />

</html>






