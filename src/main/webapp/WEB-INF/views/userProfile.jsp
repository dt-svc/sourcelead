<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::Org Settings</title>
</head>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<meta charset="utf-8">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 
 <script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
    <script type="text/javascript">
		window.onload = function () {
			initLocationAuto('userLocation','','','','','','');
		 };
	</script>
<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofile-img.png');
	}
</script>

<script>
	$(document).ready(function() {
		document.getElementById("profileSettings_form").reset();
		document.getElementById("passwordSettings_form").reset();
		getLoginUserProfile();
	});
</script>

<script>
function resetPassword(){
	
	 $('input#currentPassword').val('');
	 $('input#password').val('');
	 $('input#confirmPassword').val('');
	
    $('#currentPwdDiv').find('p.jquery_form_error_message').remove();
    $('#newPwdDiv').find('p.jquery_form_error_message').remove();
    $('#confirmPwdDiv').find('p.jquery_form_error_message').remove();
    $('#profile').show();
    $('#updatepassword').hide();
    $('#configure').hide();
    $('#unsubscribe').hide();
    $('#mylinkaccounts').hide();
    $("#save_button").removeAttr("disabled");
    
}

function resetProfile(){
	$('#profile').hide();
    $('#updatepassword').show();
    $('#configure').hide();
    $('#unsubscribe').hide();
    $('#mylinkaccounts').hide();
	document.getElementById("profileSettings_form").reset();
	
	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
	$('#lastNameDiv').find('p.jquery_form_error_message').remove();
	$('#designationDiv').find('p.jquery_form_error_message').remove();
	$('#phoneDiv').find('p.jquery_form_error_message').remove();
	$('#loactionDiv').find('p.jquery_form_error_message').remove();
	
	$('#linkedInDiv').find('p.jquery_form_error_message').remove();
	$('#twitterDiv').find('p.jquery_form_error_message').remove();
	$('#fbDiv').find('p.jquery_form_error_message').remove();
	$('#gplusDiv').find('p.jquery_form_error_message').remove();
	
}
function getConfiguredList(){
	$('#profile').hide();
    $('#updatepassword').hide();
    $('#configure').show();
    $('#unsubscribe').hide();
    $('#mylinkaccounts').hide();
    $('#myBankDetails').hide();
    $('#associateBankData').hide();
    $("#taxDate").hide();
}

function getUnsubscribeList(){
	$('#profile').hide();
    $('#updatepassword').hide();
    $('#configure').hide();
    $('#unsubscribe').show();
    $('#mylinkaccounts').hide();
    $('#myBankDetails').hide();
    $('#associateBankData').hide();
    $("#taxDate").hide();
}

function getLinkAccounts(){
	$('#profile').hide();
    $('#updatepassword').hide();
    $('#configure').hide();
    $('#unsubscribe').hide();
    $('#myBankDetails').hide();
    $('#mylinkaccounts').show();
    $('#associateBankData').hide();
    $("#taxDate").hide();
}

function getBankDetails(){
	$('#profile').hide();
    $('#updatepassword').hide();
    $('#configure').hide();
    $('#unsubscribe').hide();
    $('#mylinkaccounts').hide();
    $("#taxDate").hide();
//     $('#myBankDetails').show();
    $('.tabsMenu_tabs_inn').show();
      associateBankData();
}

 function getTax(){
	$('#profile').hide();
    $('#updatepassword').hide();
    $('#configure').hide();
    $('#unsubscribe').hide();
    $('#mylinkaccounts').hide();
    $('#associateBankData').hide();
    $('#myBankDetails').hide();
//     $("#myTax").show();
    $('.tabsMenu_tabs_inn').show();
     associateTaxSummary();
     getResidenceLocation();
} 
 
 function getDocuments(){
	 $('#profile').hide();
	    $('#updatepassword').hide();
	    $('#configure').hide();
	    $('#unsubscribe').hide();
	    $('#myBankDetails').hide();
	    $('#mylinkaccounts').hide();
	    $('#associateBankData').hide();
	    $("#taxDate").hide();
	    $("#myTax").hide();
	    $('#myBankDetails').hide();
// 	    $("#myDocuments").show();
// 	    $(".welCo1").show();
	    candidateDocuments();
 }
</script>



<body>
  <!-- Content Area -->
  <!-- Tab for Profile setting-area starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Account Settings</h2>
      </div>
    
     <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
       <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard">Home</a> &raquo; Account Settings</div>
     </c:if>
   
      <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/professionalDashboard">Home </a> &raquo; Account Settings  </div>
      </c:if>
      
      <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/recruiterDashboard">Home </a> &raquo; Account Settings  </div>
      </c:if>
    
    </section>
    
    <div class="project_wrap clearfix">
      <div class="clearfix form-area form-horizontal customedTabStyles">
        	<div id="loading"></div> 
		  <ul class="nav nav-tabs newTabs">
			<li class="active" onclick="resetPassword();"><a data-toggle="tab" href="#profile">Details</a></li>
			<li onclick="resetProfile();"><a data-toggle="tab" href="#updatepassword">Password</a></li>
			<li onclick="getConfiguredList();"><a data-toggle="tab" href="#configure">Configure</a></li>
			<li onclick="getUnsubscribeList();"><a data-toggle="tab" href="#unsubscribe">Unsubscribe</a></li>
			<li onclick="getLinkAccounts();"><a data-toggle="tab" href="#myLinkaccounts">Accounts</a></li>
			<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
			<li onclick="getBankDetails();"><a data-toggle="tab" href="#myBankDetails">Bank Details</a></li>
			<li onclick="getTax();"><a data-toggle="tab" href="#myTax">Tax</a></li>
			</c:if>
			<li onclick="getDocuments();"><a data-toggle="tab" href="#myDocuments">Documents</a></li>
		  </ul>        
<div class="accountsettings-tab noMar">

		  <div class="tab-content noBor">
			<div id="profile" class="tab-pane panel-body fade in active">
			  <div class="col-xs-12 col-sm-3 col-md-3">
			  
			  <form:form class="login-form clearfix"  id="profileImage_form"  name="profileImage_form" method="post" >
		<div class="well well-sm text-center">
			  <p><strong>${SourceLeadWeb_firstName} ${SourceLeadWeb_lastName}</strong></p>
				  <div class="profile-img-container profile-image" style="position:relative;">
				   <img  id="userImage" data-id="userProfileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_userImgUrl}" alt="Profile Image" class="img-circle img-responsive">
				  <div class="btn-group uploadfile">
				  <label class="btn btn-sm btn-primary">
                    <i class="fa fa-upload"></i> Upload / Change<input type="file" name="file" id="uploadedImage"  accept="image/*" onchange="cropImage(this, 'profileImage_form', '', true);" style="visibility:hidden;height:0;width:10px;overflow:hidden" >
                  </label>
                  <c:if test="${!empty SourceLeadWeb_userImgUrl}">
                  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage()">
				  <i class="fa fa-trash"></i></button>
				  </c:if>
				  </div>
				  </div>
				 </div> 
				 
				 </form:form>	
				  <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>
			  		<div class="clearfix"></div>
			  		<div class="clearfix text-center mt">
						 <a href="javascript:void(0)" onclick="generateJson()" class="btn btn-primary" title="Download">
						 	Download Profile<i class="fa fa-download ml"></i>
						 </a>
					</div>
			  </div>
			 <div id="popUp" ></div>  
			  
		   <div class="col-xs-12 col-sm-9 col-md-9">
		   <div id="editor"></div>
<!-- 		   <button id="cmd">generate PDF</button> -->
		   <form:form class="login-form clearfix"  id="profileSettings_form"  name="profileSettings_form" method="post" >
			   <div class="row">
				<div class="col-md-6">
					<div class="form-group" id="firstNameDiv">
					<label class="col-md-4">First Name<span class="text-danger paddingLeft">*</span></label>
					<div class="col-md-8">
					  <input type="text" name="firstName" id="firstName" value="${SourceLeadWeb_loginUserFirstName}" field-name="First Name" class="form-control" data-validation="required validate_Alphanumeric validate_Space validate_length length1-100"  >
					</div>
					</div>
				   <div class="form-group" id="lastNameDiv">
					<label class="col-md-4">Last Name</label>
					<div class="col-md-8">
					  <input type="text" name="lastName" id="lastName" value="${SourceLeadWeb_loginUserLastName}" field-name="Last Name" class="form-control" data-validation="validate_Alphanumeric validate_Space validate_length length1-100" data-validation-optional="true" />
					</div>
				   </div>
				  <div class="form-group">
					<label class="col-md-4">Email<span class="text-danger paddingLeft">*</span></label>
					<div class="col-md-8">
					<input type="text" readonly="readonly" id="email" value="${SourceLeadWeb_loginUserEmail}" class="form-control">
					</div>
				  </div>
		
				</div>
		
				<div class="col-md-6">
						  <div class="form-group" id="designationDiv">
							<label class="col-md-4">Designation</label>
							<div class="col-md-8">
							  <input type="text" name="designation" id="designation" field-name="Designation" value="${SourceLeadWeb_designation}" ${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER' ? 'readonly' : ''} class="form-control" data-validation-optional="true" data-validation="validate_length length2-100" />
							</div>
						  </div>
						  <div class="form-group" id="phoneDiv">
							<label class="col-md-4">Phone</label>
							<div class="col-md-8">
							  <input type="text" name="mobilePhone" id="mobilePhone" field-name="Mobile Phone" value="${SourceLeadWeb_mobilePhone}" class="form-control" data-validation-optional="true" data-validation="validate_Space validate_length length9-20 validate_phone" />
							</div>
						  </div>
						  <div class="form-group" id="loactionDiv">
							<label class="col-md-4">Location</label>
							<div class="col-md-8">
							  <%-- <textarea rows="1" name="location" field-name="Location"  id="userLocation" class="form-control"  />${SourceLeadWeb_user.location}</textarea> --%>
							  <input type="text" value="${SourceLeadWeb_location}" class="form-control" name="location" field-name="Location"  id="userLocation" />
							  <i class="fa fa-refresh btn btn-primary refresh-icon mr" title="Refresh" onclick="refreshLocation();"></i>
							</div>
						</div>
					</div>
			
			
			
			<div class="col-md-12 clearfix">
			<h4>Social Links</h4>
			</div>
					
			<div class="col-md-6">
					<div class="form-group" id="linkedInDiv">
					<label class="col-md-4">LinkedIn</label>
					<div class="col-md-8">
					  <input type="text" name="linkedIn" id="linkedIn" value="${SourceLeadWeb_linkedIn}" field-name="Linked In" class="form-control" data-validation="validate_url validate_Space" data-validation-optional="true" >
					</div>
					</div>
				   <div class="form-group" id="fbDiv">
					<label class="col-md-4">Facebook</label>
					<div class="col-md-8">
					  <input type="text" name="facebook" id="facebook" value="${SourceLeadWeb_facebook}" field-name="Facebook" class="form-control" data-validation="validate_url validate_Space" data-validation-optional="true">
					</div>
				   </div>
				</div>
				
				<div class="col-md-6" id="twitterDiv">
					<div class="form-group">
					<label class="col-md-4">Twitter</label>
					<div class="col-md-8">
					  <input type="text" name="twitter" id="twitter" value="${SourceLeadWeb_twitter}" field-name="Twiter" class="form-control" data-validation="validate_url validate_Space" data-validation-optional="true" >
					</div>
					</div>
				   <div class="form-group" id="gplusDiv">
					<label class="col-md-4">Google+</label>
					<div class="col-md-8">
					  <input type="text" name="googlePlus"  id="googlePlus" value="${SourceLeadWeb_googlePlus}" field-name="Google+" class="form-control" data-validation="validate_url validate_Space" data-validation-optional="true">
					</div>
				   </div>
		
				</div>
				 
					 <div class="col-md-12">
						 <hr>
							<div class="pull-right">
							  <input type="submit" class="btn btn-primary mr" id="profile_submit" value="Save">
							  <input type="button" class="btn btn-default" value="Cancel" onClick="self.location='${SourceLeadWeb_sourceleadUrl}/dashboard'" >
							</div>
					  </div>
			 </div>
			 </form:form>	
			</div>
			</div>
			
			
			<div id="updatepassword" class="tab-pane panel-body fade">
			  <div class="col-xs-12 col-sm-3 col-md-3">
				  <div class="password-icon mb text-center well">
				    <i class="fa fa-lock" aria-hidden="true"></i>
				  </div>
			  </div>
			<div class="col-xs-12 col-sm-9 col-md-9">
				  <div class="row">
					<form:form class="login-form clearfix"  id="passwordSettings_form"  name="passwordSettings_form" method="post" >
					<div class="col-md-8">
					 <div id=passwordDiv>	
						<div class="form-group" id="currentPwdDiv">
						<label class="col-md-4">Current Password<span class="text-danger paddingLeft">*</span></label>
						<div class="col-md-6">
						  <input type="password" id="currentPassword" name="currentPassword" field-name="Current Password"   class="form-control"  onblur="validateCurrentPassword();" data-validation="required" />
						</div>
						</div>
						<div class="form-group " id="newPwdDiv">
						<label class="col-md-4">New Password<span class="text-danger paddingLeft">*</span></label>
						
						<div class="col-md-6 posiRela positionRelative">
						  <input type="password" name="password" id="password" field-name="New Password" class="form-control" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d.*)(?=.*\W.*)[a-zA-Z0-9\S]{8,}$" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" data-validation="required validate_length length8-100"  onkeyup="passwordValidation_pf('password','np_letter','np_capital','np_number','np_special','np_length');" />
	             	    <div id="np_message" class="passHintNew">
				  			<p  id="np_letter" class="invalid">At least one lowercase letter</p>
				  			<p  id="np_capital" class="invalid">At least one uppercase letter</p>
				  			<p  id="np_number" class="invalid">At least one number</p>
				  			<p  id="np_special" class="invalid">At least one special character</p>
				  			<p  id="np_length" class="invalid">Minimum 8 characters required</p>
						</div>
	             			<i class="fa fa-eye showPassword-usr" onclick="showPassword();"></i>
						</div> 
				       </div>
					  <div class="form-group" id="confirmPwdDiv">
						<label class="col-md-4">Confirm Password<span class="text-danger paddingLeft">*</span></label>
						<div class="col-md-6 positionRelative">
						<input type="password"  value="" id="confirmPassword" class="form-control" field-name="Confirm Password" onblur="checkPass();" data-validation="required"/>
						<i class="fa fa-eye showPassword-usr" onclick="showConfirmPassword();"></i>
						</div>
				      </div>
				     </div> 
					</div>
				 <div class="col-md-7">
				 <hr class="mr">
					<div class="pull-right mr">
					  <input type="submit" value="Save" id="save_button" onclick="checkPass();" class="btn btn-primary mr" />
					  <input type="button" class="btn btn-default" value="Cancel" onClick="self.location='${SourceLeadWeb_sourceleadUrl}/dashboard'">
					</div>
				  </div>
				  </form:form>
				  
				 </div>
			</div>
			</div>
			
			<div id="configure" class="tab-pane panel-body fade">
				<div class="col-xs-12 col-sm-9 col-md-12">
					<div class="popHeadTwo mb10"></div>
					<div class="form-group">
						<label class="col-md-3">Login Security<i class="" title="Login Security"></i></label>
						<div class="col-md-7 toggle-button">
							<label class="switch">
							<c:if test="${SourceLeadWeb_userLoginSecurity == 'Y'}">
							<input type="checkbox" checked id="loginSecurityId" onclick="switchLoginSecurity();">
							</c:if>
							<c:if test="${SourceLeadWeb_userLoginSecurity == 'N'}">
							<input type="checkbox" id="loginSecurityId" onclick="switchLoginSecurity();">
							</c:if>	
							<c:if test="${empty SourceLeadWeb_userLoginSecurity}">
							<input type="checkbox" id="loginSecurityId" onclick="switchLoginSecurity();">
							</c:if>	
								<div class="slider round"></div>
							</label>
						</div>
					</div>
				</div>
		   </div>
		   
		   <div id="unsubscribe" class="tab-pane panel-body fade">
					<div class="col-xs-12 col-sm-9 col-md-12">
					<div class="popHeadTwo mb10">Unsubscribe</div>
						<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
							<%-- <c:if test="${userTabs == 'Jobs' || userTabs == 'Projects' || userTabs == 'Contracts' || userTabs == 'Campaigns'}">
								<div class="form-group">
									<label class="col-md-3">${userTabs}<i class="" title="${userTabs}"></i></label>
									<div class="col-md-7 toggle-button">
										<label class="switch">
											<c:set var="disabled" value=""/>
											<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
										      	 <c:if test="${userTabs.id == unsubscriptionTabId}">
										      	 	<c:set var="disabled" value="checked"/>
										      	 </c:if>
			   								</c:forEach>
											<input name="chkChangeSubscription" type="checkbox" ${disabled} id="${userTabs}" value="${userTabs.id}" onclick="changeSubscription(this);">
											<div class="slider round"></div>
										</label>
									</div>
								</div>
							</c:if> --%>
							
							<c:if test="${userTabs == 'My Timesheets'}">
								<div class="form-group">
									<label class="col-md-3">Timesheets<i class="" title="Timesheets"></i></label>
									<div class="col-md-7 toggle-button">
										<label class="switch">
											<c:set var="disabled" value=""/>
											<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
										      	 <c:if test="${4 == unsubscriptionTabId}">
										      	 	<c:set var="disabled" value="checked"/>
										      	 </c:if>
			   								</c:forEach>
											<input name="chkChangeSubscription" type="checkbox" id="Timesheets" ${disabled} value="4" onclick="changeSubscription(this);">
											<div class="slider round"></div>
										</label>
									</div>
								</div>
							</c:if>
							
							<c:if test="${userTabs == 'My Expenses'}">
								<div class="form-group">
									<label class="col-md-3">Expenses<i class="" title="Expenses"></i></label>
									<div class="col-md-7 toggle-button">
										<label class="switch">
											<c:set var="disabled" value=""/>
											<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
										      	 <c:if test="${23 == unsubscriptionTabId}">
										      	 	<c:set var="disabled" value="checked"/>
										      	 </c:if>
			   								</c:forEach>
											<input name="chkChangeSubscription" type="checkbox" id="Expenses" ${disabled} value="23" onclick="changeSubscription(this);">
											<div class="slider round"></div>
										</label>
									</div>
								</div>
							</c:if>
						</c:forEach>
						
						<c:if test="${SourceLeadWeb_billingTabsCount > 1}">
								<div class="form-group">
									<label class="col-md-3">Invoice & Billing<i class="" title="Invoice & Billing"></i></label>
									<div class="col-md-7 toggle-button">
										<label class="switch">
											<c:set var="disabled" value=""/>
											<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
										      	 <c:if test="${20 == unsubscriptionTabId}">
										      	 	<c:set var="disabled" value="checked"/>
										      	 </c:if>
			   								</c:forEach>
											<input name="chkChangeSubscription" type="checkbox" id="Finance" ${disabled} value="20" onclick="changeSubscription(this);">
											<div class="slider round"></div>
										</label>
									</div>
								</div>
						</c:if>
						
						<div class="form-group">
							<label class="col-md-3">Others<i class="" title="Others"></i></label>
							<div class="col-md-7 toggle-button">
								<label class="switch">
									<c:set var="disabled" value=""/>
									<c:set var="othersId" value="0"/>
									<c:forEach var="unsubscriptionTabId" items="${unsubscriptionTabIds}">
								      	 <c:if test="${unsubscriptionTabId == othersId}">
								      	 	<c:set var="disabled" value="checked"/>
								      	 </c:if>
	   								</c:forEach>
									<input name="chkChangeSubscription" type="checkbox" id="Other" ${disabled} value="0" onclick="changeSubscription(this);">
									<div class="slider round"></div>
								</label>
							</div>
						</div>
				</div>
		   </div>
		   
		  <jsp:include page="userAccounts.jsp" />
		  <jsp:include page="userBankDetails.jsp" />
		  <jsp:include page="userTax.jsp" />
		  <jsp:include page="userDocuments.jsp" />
		   
		</div>
			<!-- Tab for Profile setting-area ends -->
		  </div>        
        
        
		  </div>
		  </div>
  </section>
  </body>
  <!-- Tab for Profile setting-area starts Ends -->
  <!-- Content Area Ends--> 

<jsp:include page="masterFooter.jsp" />

<script>
function showPassword(){
	var passwordInput = document.getElementById('password');
	if(passwordInput.type === "password"){
		passwordInput.type = "text"
	}else{
		passwordInput.type = "password"
	}
	
	
}
</script>

<script>
function showConfirmPassword(){
	var confPasswordInput = document.getElementById('confirmPassword');
	if(confPasswordInput.type === "password"){
		confPasswordInput.type = "text"
	}else{
		confPasswordInput.type = "password"
	}
	
}
</script>


<script type="text/javascript">
	 function validateCurrentPassword() {   
		var currentPassword = $('input#currentPassword').val();
		currentPassword = encodeURIComponent(currentPassword);
		if(currentPassword != null  && currentPassword != '' && currentPassword != 'undefined'){
			var sourceleadUrl ='${sourceleadUrl}';
			
		     $.ajax({
			    	type: "POST",
			    	 url: sourceleadUrl+"/validateCurrentPassword?${_csrf.parameterName}=${_csrf.token}&currentPassword="+currentPassword, 
			        success: function(result) {
			         	 if(result == 'valid'){
				    		$("#save_button").removeAttr("disabled");
				    		$('input#currentPassword').removeAttr( "class record-exist record-exist-errormsg");
							$('#passwordDiv').find('p.jquery_form_error_message').remove();
							$('input#currentPassword').attr('class','valid form-control');
							$( "#save_button" ).removeAttr("style");
							$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
							$( "#save_button" ).click(function() {
							});
							
				    	}
				    	else if(result == 'invalid'){
			    		$("#save_button").attr("disabled", "disabled");
				    		$('#passwordDiv').find('p.jquery_form_error_message').remove(); 
				    		$('input#currentPassword').attr('class','error form-control');
				    		$("input#currentPassword").after("<p class='jquery_form_error_message'>Wrong Password Entered</p>");
							$('input#currentPassword').attr('record-exist','yes');
							$('input#currentPassword').attr('record-exist-errorMsg',' Wrong Password Entered'); 
							$("#save_button" ).addClass('btn btn-primary mr').val('Save');
				    	} 
			        },
			    }); 
				
		}
		
	 }
 </script>

<script>
function switchLoginSecurity(){
	var ckbox = $('#loginSecurityId');
	var security = "";
	if (ckbox.is(':checked')) {
		security = "Y";
	}
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/updateUserLoginSecurity?loginSecurity="+security,        
	       success :function(data) {
	       if(data == "success"){
	       		//TO-DO
	       	} 
	      },
	  }); 
}
</script>

<script>
function changeSubscription(source){
	var tabIds = [];
	$('input:checkbox[name=chkChangeSubscription]:checked').each(function(){
		tabIds.push($(this).val());
	});
	
	$('#unsubscribe :input').attr('disabled', true);
	$.ajax({
	      type : "POST",
	       url: sourceleadUrl+"/updateUserEmailUnsubscription?tabIds="+tabIds,        
	       success :function(data) {
	       if(data == "Success"){
	    	   $('#unsubscribe :input').removeAttr('disabled');
	       	} 
	      },
	  });
}
</script>


<script type="text/javascript">
	 function checkPass() {   
		var pass1 = $('input#password').val();  
	    var pass2 = $('input#confirmPassword').val();
	    if((pass1 != null && pass1 != '' && pass1 != 'undefined') && (pass2 != null && pass2 != '' && pass2 != 'undefined')){
	    	if(pass1 == pass2){
	    		$("#save_button").removeAttr("disabled");
	    		$('input#confirmPassword').removeAttr( "class record-exist record-exist-errormsg");
				$('#passwordDiv').find('p.jquery_form_error_message').remove();
				$('input#confirmPassword').attr('class','valid form-control');
				$( "#save_button" ).removeAttr("style");
				$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
				$( "#save_button" ).click(function() {
				 if ($(this).validate(false, validationSettings)){
				 }
				});
				
	    	}
	    	else{
	    		$("#save_button").attr("disabled", "disabled");
	    		$('#passwordDiv').find('p.jquery_form_error_message').remove(); 
	    		$('input#confirmPassword').attr('class','error form-control');
	    		$("input#confirmPassword").closest('b').after("<p class='jquery_form_error_message'> Passwords Do Not Match</p>");
				$('input#confirmPassword').attr('record-exist','yes');
				$('input#confirmPassword').attr('record-exist-errorMsg',' Passwords Do Not Match'); 
				$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
				$("#save_button").removeAttr("disabled");
	    	
	    	}
	    }
   }
	
   
</script>


<script type="text/javascript">
  var loadFile = function(event) {
    var output = document.getElementById('userImage');
    
    var uploadedImage = document.getElementById('uploadedImage').value;
    var sizeinbytes = document.getElementById('uploadedImage').files[0].size;
    document.getElementById("errorImage").style.display="none"; 
    
    var extension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1).toLowerCase();
    
    if(extension != 'jpg'){
    	output.src = URL.createObjectURL(event.target.files[0]);
    	
    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
 		var msg = "."+extensionName+" extension not supported";
 		alertMessage(msg);
 		document.getElementById("errorImage").style.display=""; 
 		$("#profile_submit").attr("disabled", "disabled");
    	
    }else if(sizeinbytes > 200000){
    	output.src = URL.createObjectURL(event.target.files[0]);
 		var msg = "Image size should be less than 200 KB";
 		alertMessage(msg);
 		document.getElementById("errorImage").style.display=""; 
 		$("#profile_submit").attr("disabled", "disabled");
    	
    }else{
    	output.src = URL.createObjectURL(event.target.files[0]);
    	$("#profile_submit").removeAttr("disabled")
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
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
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
	result = result +"		<p>Are you sure you want to delete this image ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteProfileImage();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function deleteProfileImage(){
	var sourceleadUrl ='${sourceleadUrl}';
	var userId = 0;
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteProfileImage?&userId"+userId,
	        success: function(data) {
	        	 var result = data;
     		if(result == "success"){
     			location.replace(sourceleadUrl+"/userProfile"); 
        	 }
	        
	       },
	        
     }); 
}
</script>

<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>




<!-- <script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#profileSettings_form', function(e){
        	  if ($(profileSettings_form).validate(false, validationSettings)){
        		    e.preventDefault(); 
            		
             		var formData = getFormData('profileSettings_form');
             		
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            		
                    $.ajax({
                	      type: "POST",
                	      data: formData,
                	      
                      	  url: sourceleadUrl+"/saveUserProfile?${_csrf.parameterName}=${_csrf.token}",
                        	success: function(result) {
    			        	 if(result == 'home'){
    			        		location.replace(sourceleadUrl+"/userProfile");  
    			        	}
    			        },  
                    });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#profileSettings_form').showHelpOnFocus();
         $('#profileSettings_form').validateOnBlur(false, validationSettings); 
     
	</script>  -->
	
	
<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#passwordSettings_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
        		    var formData = getFormData("passwordSettings_form");
            		var sourceleadUrl ='${sourceleadUrl}';
            		
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            		
                    $.ajax({
                	      type: "POST",
                      	  url: sourceleadUrl+"/passwordSettings?${_csrf.parameterName}=${_csrf.token}",
                      	  data: formData,
                        	success: function(data) {
    			        	 
                        		if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
             		        		showError(data.responseMessage);
                  	    		    $("#loading").removeAttr('style');
                  	    		}
                            		else if (data.response === "AWKWARD") {
                            			location.href = "/errorPage";
                  	    		}
                            		else if(data.response === "SUCCESS"){
                            			$("#loading").removeAttr('style');
                            			location.replace(sourceleadUrl+"/dashboard"); 
            			        	    }
                            		else if (data.indexOf('Sign In') !== -1) {
                            			location.replace(sourceleadUrl);
                  				} else {
                  					showError("Operation failed, please try again");
                  				    $("#loading").removeAttr('style');
                  				}
                        		
                        		
                        		
                        /* 		if(result == 'success'){
    			        		location.replace(sourceleadUrl+"/dashboard");  
    			        	}else if(result == 'Invalid Password'){
    			        		 $('#passwordDiv').find('span.jquery_form_error_message').remove();
                 		         document.getElementById("currentPassword").setAttribute('class','error form-control');
                 		         $("input#currentPassword").after("<span class='jquery_form_error_message'> <font color='red'>Invalid Password</font></span>");
                 				 document.getElementById("currentPassword").setAttribute('pass-exist','yes');
                 				 document.getElementById("currentPassword").setAttribute('pass-exist-errorMsg', ' Invalid Password'); 
                 				 $("input#currentPassword").removeAttr("style");
                 				 $("#loading").removeAttr("style");
    			        	}  */
    			        },  
                    });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#passwordSettings_form').showHelpOnFocus();
         $('#passwordSettings_form').validateOnBlur(false, validationSettings); 
            
     
	</script> 
<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#profileSettings_form', function(e){
        	 var userId = '${SourceLeadWeb_user.id}';
        	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
        	 
        	  if ($('#profileSettings_form').validate(false, validationSettings)){
        		    e.preventDefault(); 
            		
             		var formData = getFormData('profileSettings_form');
             		
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
            		
                    $.ajax({
                	      type: "POST",
                	      data: formData,
                      	  url: sourceleadUrl+"/saveUserProfile?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&orgLocationId="+orgLocationId,
                        	success: function(data) {
                        		if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
             		        		showError(data.responseMessage);
                  	    		    $("#loading").removeAttr('style');
                  	    		}
                            		else if (data.response === "AWKWARD") {
                            			location.href = "/errorPage";
                  	    		}
                            		else if(data.response === "SUCCESS"){
                            			$("#loading").removeAttr('style');
                            			location.replace(sourceleadUrl+"/userProfile");  
            			        	    }
                            		else if (data.indexOf('Sign In') !== -1) {
                            			location.replace(sourceleadUrl);
                  				} else {
                  					showError("Operation failed, please try again");
                  				    $("#loading").removeAttr('style');
                  				}
                        	
    			        },  
                    });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#profileSettings_form').showHelpOnFocus();
         $('#profileSettings_form').validateOnBlur(false, validationSettings); 
     
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
function refreshLocation(){
	$("#userLocation").val('');
}
</script>

<script>
function getLoginUserProfile(){
		$.ajax({
		   	type: "GET",
		   	url: sourceleadUrl+"/getLoginUserProfile?"+tokenParamName+"="+tokenValue,	    	   
		    success: function(data) {
		    }
	   });
}
</script>
<script>
function generateJson() {
	$.ajax({
	   	type: "GET",
	   	url: sourceleadUrl+"/downloadLoginUserProfile?"+tokenParamName+"="+tokenValue,	    	   
	    success: function(data) {
	    	
	    	 
	var search = {}
				search["FirstName"] = data.firstName;
				if (data.lastName != null) {
					search["LastName"] = data.lastName;
				} else {
					search["LastName"] = "";
				}
				if (data.email != null) {
					search["Email"] = data.email;
				} else {
					search["Email"] = "";
				}
				if (data.designation != null) {
					search["Designation"] = data.designation;
				} else {
					search["Designation"] = " ";
				}
				if (data.mobileNumber != null) {
					search["MobilePhone"] = data.mobileNumber;
				} else {
					search["MobilePhone"] = "";
				}
				if (data.location != null) {
					search["Location"] = data.location;

				} else {
					search["Location"] = "";

				}
				if (data.linkedinURL != null) {
					search["LinkedIn"] = data.linkedinURL;
				} else {
					search["LinkedIn"] = " ";
				}

				if (data.facebookURL != null) {
					search["Facebook"] = data.facebookURL;

				} else {
					search["Facebook"] = "";

				}
				if (data.twitterURL != null) {
					search["Twitter"] = data.twitterURL;

				} else {
					search["Twitter"] = " ";

				}
				if (data.googlePlusURL != null) {
					search["GooglePlus"] = data.googlePlusURL;

				} else {
					search["GooglePlus"] = " ";

				}
				if (data.ipaddresses != null) {
					search["Ipaddresses"] = data.ipaddresses;
				} else {
					search["Ipaddresses"] = " ";
				}
				var dataStr = JSON.stringify(search);
				var dataUri = 'data:application/json;charset=utf-8,'
						+ encodeURIComponent(dataStr);
				var exportFileDefaultName = 'Profile.json';
				var linkElement = document.createElement('a');
				linkElement.setAttribute('href', dataUri);
				linkElement.setAttribute('download', exportFileDefaultName);
			    document.body.appendChild(linkElement);
			    linkElement.click();
			    linkElement.remove();

			}
		});

	}
</script>

<script>
$(document).ready(function(){
	$(".posiRela input").focus(function(){
		$(".passHintNew").show();
	});
	$(".posiRela input").blur(function(){
		$(".passHintNew").hide();
	});	
});
</script>

<script>
function passwordValidation_pf(idValue,np_letter,np_capital,np_number,np_special,np_length){
	var myInput = $("#"+idValue).val();
	 
	if(myInput.match(/[a-z]/g)){
		$("#"+np_letter).removeClass("invalid");
		$("#"+np_letter).addClass("actTe");
	}else{
		$("#"+np_letter).removeClass("actTe");
		$("#"+np_letter).addClass("invalid");
	} 
	if(myInput.match(/[A-Z]/g)){
		$("#"+np_capital).removeClass("invalid");
		$("#"+np_capital).addClass("actTe");
	}else{
		$("#"+np_capital).removeClass("actTe");
		$("#"+np_capital).addClass("invalid");
	}
	if(myInput.match(/[0-9]/g)){
		$("#"+np_number).removeClass("invalid");
		$("#"+np_number).addClass("actTe");
	}else{
		$("#"+np_number).removeClass("actTe");
		$("#"+np_number).addClass("invalid");
	}
	if(myInput.match(/[$&+,:;=?@#|'<>.^*()%!-]/g)){
		$("#"+np_special).removeClass("invalid");
		$("#"+np_special).addClass("actTe");
	}else{
		$("#"+np_special).removeClass("actTe");
		$("#"+np_special).addClass("invalid");
	}
	if(myInput.length >= 8){
		$("#"+np_length).removeClass("invalid");
		$("#"+np_length).addClass("actTe");
	}else{
		$("#"+np_length).removeClass("actTe");
		$("#"+np_length).addClass("invalid");
	}
	
}


</script>

<script type="text/javascript">

var doc = new jsPDF();
var specialElementHandlers = {
    '#editor': function (element, renderer) {
        return true;
    }
};

$('#cmd').click(function () {
	doc.fromHTML($('#content').html(), 15, 15, {
        'width': 170,
            'elementHandlers': specialElementHandlers
    });
    doc.save('sample-file.pdf');
});
</script>

</html>