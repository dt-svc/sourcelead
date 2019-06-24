<!DOCTYPE html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="icon" type="image/png" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->

<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">


<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${googleMapsLocationApiKey}" type="text/javascript"></script>

<script type="text/javascript">
		window.onload = function () {
			initLocationAuto('onBoardLocatin','onBoardCountry','onBoardState','onBoardCity','onBoardZipCode')
			getOnBoardCandidateCountry('${SourceLeadWeb_candidateOnBoarding.country}');
			getOnBoardCandidateStates('${SourceLeadWeb_candidateOnBoarding.country}','${SourceLeadWeb_candidateOnBoarding.state}');
			getOnBoardCandidateCities('${SourceLeadWeb_candidateOnBoarding.country}','${SourceLeadWeb_candidateOnBoarding.state}','${SourceLeadWeb_candidateOnBoarding.city}');
		 };
</script>

<script type="text/javascript">
/*  var sourceleadUrl ='${sourceleadUrl}'; */
 var atsUrl = '${SourceLeadWeb_atsUrl}';
</script>

<div class="wraper"> 
  <!--   Welcome Screens   -->
  
	<section class="welcoBg">
		<section class="welcoInn">
		  <div class="welcoHead">
				
		 <jsp:include page="../userManageAccounts.jsp" />
				
				<c:if test="${!empty SourceLeadWeb_fileUrl}">
					<div class="welLogo">
					<img class="logo" data-id="orgLogoImage" src="${SourceLeadWeb_fileUrl}" title="${SourceLeadWeb_orgLocationName}" />
					</div>
				</c:if>
				
				<c:if test="${empty SourceLeadWeb_fileUrl}">
					<div class="welLogoText">
					<span  title="${SourceLeadWeb_orgLocationName}">${SourceLeadWeb_orgLocationName}</span>
					</div>
				</c:if>
				
				<div class="welSiO" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out"></i></div>
			  <ul>
				  <li class="actLi"><span class="fill">1</span><b>Welcome</b><i class="line"></i></li>
				  <li class="actLi"><span>2</span><b>Step two</b><i class="line"></i></li>
				  <li><span>3</span><b>Step three</b><i class="line"></i></li>
				  <li><span>4</span><b>Step four</b><i class="line"></i></li>
				  <li><span>5</span><b>Step five</b><i class="line"></i></li>
				  <li><span>6</span><b>Finish</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
 <form:form class="login-form clearfix"  id="onboardCandidate_form"  name="onboardCandidate_form" method="post" >
 <div id="loading"></div>
<div class="welCo1">
<h5>Employee Details</h5>
<div class="form-horizontal padBot">
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">First Name <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="firstName" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100"  id="firstName" value="${SourceLeadWeb_user.firstName}">
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Last Name</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="lastName" id="lastName" data-validation-optional="true" data-validation="validate_Space validate_AlphaNumber validate_length length1-100" value="${SourceLeadWeb_user.lastName}">
			  </div>
			</div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">Street Address 1 <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="address1" data-validation="required validate_Space" field-name="Street Address 1" id="address1" value="${!empty candidateOnBoarding.address1 ? candidateOnBoarding.address1 : ''}">
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Street Address 2</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" name="address2" data-validation-optional="true" data-validation="validate_Space" id="address1" value="${candidateOnBoarding.address2}">
			  </div>
			</div>
		</div>
	</div>
	
	
	
	<div class="clearfix">
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
				  <div class="form-group">
				  <label class="col-md-4 control-label">Location <span class="text-danger paddingLeft">*</span></label>
				  <div class="col-md-8">
					<input type="text" class="form-control" data-placeholder="Location" id="onBoardLocatin"  name="location" value="${candidateOnBoarding.location}">
					<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('onBoardLocatin','onBoardCountry','onBoardState','onBoardCity','onBoardZipCode');"></i>
				  </div>
				</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			  <div class="form-group">
			  <label class="col-md-4 control-label">Country <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control"  data-placeholder="Country" id="onBoardCountry"  name="country" value="${candidateOnBoarding.country}" readonly>
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">State</label>
			  <div class="col-md-8">
				 <input type="text" class="form-control" data-placeholder="State"  id="onBoardState" name="state" value="${candidateOnBoarding.state}" readonly>
			  </div>
			</div>
		</div>
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">City</label>
			  <div class="col-md-8">
				 <input type="text" class="form-control"  data-placeholder="City" id="onBoardCity" name="city" name="state" value="${candidateOnBoarding.city}" readonly>
			  </div>
			</div>
		</div>
	</div>
	<div class="clearfix">
 		
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
			<div class="form-group">
			  <label class="col-md-4 control-label">Zipcode</label>
			  <div class="col-md-8">
				 <input type="text" class="form-control input-sm" id="onBoardZipCode" name="zipCode" value="${!empty candidateOnBoarding.zipCode ? candidateOnBoarding.zipCode : '' }"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-10" >
			  </div>
			</div>
		</div>	
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Mobile number <span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="mobileNumber" value="${candidateOnBoarding.mobileNumber}" name="mobileNumber" >
			  </div>
			</div>
		</div>
	</div>	
	<div class="clearfix">
 		
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
			<div class="form-group">
			  <label class="col-md-4 control-label">Personal Email</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="personalEmail" value="${SourceLeadWeb_candidateOnBoarding.personalEmail}" name="personalEmail" readonly="readonly">
			  </div>
			</div>
		</div>	
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Company Email</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="companyEmail" value="${SourceLeadWeb_candidateOnBoarding.companyEmail}" name="companyEmail" readonly="readonly" data-validation-optional="true" >
			  </div>
			</div>
		</div>
		
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Date of Birth</label>
			  <div class="col-md-8">
			    <input type="text" class="form-control input-sm calendar" id="employeeDateOfBirth" name="dateOfBirth" value="${SourceLeadWeb_candidateOnBoarding.dateOfBirth}" readonly="readonly">
			  </div>
			</div>
		</div>
		
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Gender</label>
			  <div class="col-md-8">
			  <c:if test="${SourceLeadWeb_candidateOnBoarding.gender eq 'Male'}">
				  <label class="paddingRight"><input type="radio" id="male" value="Male" checked name="gender" ><span class="mr5"></span>Male</label>
				  <label class="paddingLeft paddingRight"><input type="radio" id="female"  value="Female" name="gender" ><span class="mr5"></span>Female</label>
				  <label class="paddingRight"><input type="radio"  value="Transgender" id="transgender" name="gender"><span class="mr5"></span>Transgender</label>
			  </c:if>
			  <c:if test="${SourceLeadWeb_candidateOnBoarding.gender eq 'Female'}">
				  <label class="paddingRight"><input type="radio" id="male" value="Male"  name="gender" ><span class="mr5"></span>Male</label>
				  <label class="paddingLeft paddingRight"><input type="radio" id="female"  checked value="Female" name="gender" ><span class="mr5"></span>Female</label>
				  <label class="paddingRight"><input type="radio"  value="Transgender" id="transgender" name="gender"><span class="mr5"></span>Transgender</label>
			  </c:if>
			  <c:if test="${SourceLeadWeb_candidateOnBoarding.gender eq 'Transgender'}">
				  <label class="paddingRight"><input type="radio" id="male" value="Male" checked name="gender" ><span class="mr5"></span>Male</label>
				  <label class="paddingLeft paddingRight"><input type="radio" id="female"  value="Female" name="gender" ><span class="mr5"></span>Female</label>
				  <label class="paddingRight"><input type="radio"  value="Transgender" checked id="transgender" name="gender"><span class="mr5"></span>Transgender</label>
			  </c:if>
			  <c:if test="${empty SourceLeadWeb_candidateOnBoarding.gender}">
				  <label class="paddingRight"><input type="radio" id="male" value="Male" checked name="gender" ><span class="mr5"></span>Male</label>
				  <label class="paddingLeft paddingRight"><input type="radio" id="female"  value="Female" name="gender" ><span class="mr5"></span>Female</label>
				  <label class="paddingRight"><input type="radio"  value="Transgender" id="transgender" name="gender"><span class="mr5"></span>Transgender</label>
			  </c:if>
			  </div>
			</div>
		</div>
		<div class="clearfix"></div>
		<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
	   <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Employment Eligibility </label>
			  <div class="col-md-8">
				  <select class="chosen-select" data-placeholder="Select Citizenship" name="employmentEligibility" id="employmentEligibility"   onchange="changeCitizenShip();" field-name="Role">
				  <option value="">-- Select Citizenship --</option>
				  <option ${SourceLeadWeb_candidateOnBoarding.employmentEligibility eq 'Citizenship of United States' ? 'selected' : '' } >Citizenship of United States</option>
				  <option ${SourceLeadWeb_candidateOnBoarding.employmentEligibility eq 'Resident Card' ? 'selected' : '' }>Resident Card</option>
				  <option ${SourceLeadWeb_candidateOnBoarding.employmentEligibility eq 'An alien authorized to work' ? 'selected' : '' }>An alien authorized to work</option>
				  <option ${SourceLeadWeb_candidateOnBoarding.employmentEligibility eq 'Non-citizen' ? 'selected' : '' } >Non-citizen</option>
				  </select>
			  </div>
			</div>
		</div>
	 
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" id="residentCardId" style="display:none">               
			<div class="form-group">
			  <label class="col-md-4 control-label">USCIS /Alien registration Number</label>
			  <div class="col-md-8">
					<input type="text" class="form-control input-sm" id="uscisAlien" name="uscisAlien"  data-validation-optional="true"  value="${SourceLeadWeb_candidateOnBoarding.uscisAlien}" data-validation="validate_length length9-13 validate_int">
			  </div>
			</div>
		</div>
		
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" id="nonCittizenId" style="display:none">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Authorized work until</label>
			  <div class="col-md-8">
					<input type="text" class="form-control input-sm calendar" id="authorizedDate" name="authorizedWorkUntil" value="${SourceLeadWeb_candidateOnBoarding.authorizedWorkUntil}" readonly="readonly">
			  </div>
			</div>
		</div>
		</c:if>
		
	</div>
	 
</div>
 
 <h5>Position Details</h5>
 <div class="form-horizontal">
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">Role</label>
			  <div class="col-md-8">
				  <input type="text" class="form-control input-sm" id="role" value="${SourceLeadWeb_displayName}"  readonly="readonly"  >
			  </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Date Of Join</label>
			  <div class="col-md-8">
				<input type="text" class="form-control input-sm" id="startDate" value="${SourceLeadWeb_candidateOnBoarding.joiningDateStr}"  readonly="readonly"  >
			  </div>
			</div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
                  <label class="col-md-4 control-label">End date</label>
                  <div class="col-md-8">
                   <input type="text" class="form-control input-sm" id="endDate" value="${SourceLeadWeb_candidateOnBoarding.endDateStr}"  readonly="readonly"  >
                  </div>
                </div>
			</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                <div class="form-group">
                  <label class="col-md-4 control-label">Rate / Salary</label>
                  <div class="col-md-8">
                  <div class="input-group er ratetype">
			                <div class="input-group-btn tblminwidth-75" style="min-width: 65px !important;">
				                <input type="text" class="pull-left btn btn-primary btn-block" value="${SourceLeadWeb_candidateOnBoarding.paymentType}" readonly>
				                </div>
                    <input type="text" class="form-control" id="salaryRate" name="salaryRate" readonly="readonly" value="${SourceLeadWeb_candidateOnBoarding.salaryRate}">
                  <div class="input-group-btn tblminwidth-75 ratetype">
			                <input type="text" class="pull-left btn btn-primary btn-block" value="${SourceLeadWeb_candidateOnBoarding.paymentMode}" readonly>
			                </div>
                  </div>
                </div>
		</div>
		      
	</div>
		
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <div class="form-group">
                  <label class="col-md-4 control-label">Manager</label>
                  <div class="col-md-8">
                   <input type="text" class="form-control input-sm" id="reportingManager" name="reportingManager" readonly="readonly" value="${SourceLeadWeb_managerName}">
                  </div>
                </div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                <div class="form-group">
                  <label class="col-md-4 control-label">Team</label>
                  <div class="col-md-8">
                    <input type="text" class="form-control input-sm" id="team" name="team" readonly="readonly" value="${SourceLeadWeb_teamName}">
                  </div>
                </div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                <div class="form-group">
                  <label class="col-md-4 control-label">Employment Type</label>
                  <div class="col-md-8">
                    <input type="text" class="form-control input-sm" id="employmentType" name="employmentType" readonly="readonly" value="${SourceLeadWeb_candidateOnBoarding.employmentType == 'Other' ? SourceLeadWeb_candidateOnBoarding.employmentOtherType : SourceLeadWeb_candidateOnBoarding.employmentType}">
                  </div>
                </div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Probation Notice</label>
                  <div class="col-md-8">
                    <input type="text" class="form-control input-sm" id="probationNotice" name="probationNotice" readonly="readonly" value="${SourceLeadWeb_candidateOnBoarding.probationNotice}">
                  </div>
                </div>
		</div>	
	</div>	
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Termination Notice</label>
                  <div class="col-md-8">
                     <input type="text" class="form-control input-sm" id="terminationNotice" name="terminationNotice" readonly="readonly" value="${SourceLeadWeb_candidateOnBoarding.terminationNotice}">
                  </div>
                </div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">                               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Due Date</label>
                  <div class="col-md-8">
                   <input type="text" class="form-control input-sm" id="dueDate" value="${SourceLeadWeb_candidateOnBoarding.dueDateStr}"  readonly="readonly"  >
                  </div>
                </div>
		</div>	
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
                  <div class="form-group">
                  <label class="col-md-4 control-label">Special Conditions</label>
                  <div class="col-md-8">
                      <textarea rows="2" class="form-control" id="specialConditions" name="specialConditions" readonly="readonly"  >${SourceLeadWeb_candidateOnBoarding.specialConditions}</textarea>
                  </div>
                </div>
		</div>
 			
	</div>				
	
	
</div>
</div>

		  
<div class="welBtns">
	<input type="button"  value="Previous" onClick="javascript:window.location.href = '${sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'" >
	<input type="button"  value="Proceed to next step" id="save_button"  onclick="saveOnboardingCandidateDetails();" class="Brig">
	<div class="clearfix"></div>
</div>
</form:form>
		  
		</section>
	</section>  
  </div>
  <!--   Welcome Screens   -->

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
  
  				<div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
  		  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
  <script type="text/javascript">
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

});
			  $(".loc-alertclose").click(function(){
					 $(".location-alert").fadeOut();
				})		 
</script> 

<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js"></script>
<script src="../resources/js/jquery-ui.js"></script>
<script>
$(function (){
	changeCitizenShip();
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormat = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormat = 'yy-dd-mm'; 
	  }
		  
	$('#employeeDateOfBirth').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0
		  });
	 $('#authorizedDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  });
});
</script>
<script>
	function changeCitizenShip(){
		var eligibility = $("#employmentEligibility").val();
		if(eligibility == 'Resident Card'){
			document.getElementById("residentCardId").style.display="block";
			document.getElementById("nonCittizenId").style.display="none";
			$("#authorizedDate").val('');
		}else if(eligibility == 'An alien authorized to work'){
			document.getElementById("nonCittizenId").style.display="block";
			document.getElementById("residentCardId").style.display="block";
		}else{
			document.getElementById("nonCittizenId").style.display="none";
			document.getElementById("residentCardId").style.display="none";
			$("#authorizedDate").val('');
			$("#uscisAlien").val('');
	
		}
	}
</script>

<script>
 function saveOnboardingCandidateDetails(){
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 var country = '${SourceLeadWeb_orgLocations.country}';
	 if ($('#onboardCandidate_form').validate(false, validationSettings)){ 
	        var formData = getFormData("onboardCandidate_form");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	data: formData,
		    	 url: atsUrl+"/saveOnboardingCandidateStepDetails?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(response) {
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
      	    		    showError(response.responseMessage);
      	    		    $("#loading").removeAttr('style');
      	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
			        		location.replace(atsUrl+"/onboardingCandidateBankingDetails");  
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });  
	        return true;
	  } else {
		return false;
	  }
 }
 
 $('body').on('blur', '#onboardCandidate_form', function() {
		$("#onboardCandidate_form").showHelpOnFocus();
		$("#onboardCandidate_form").validateOnBlur(false, validationSettings);  
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
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script>  

<script>
function initLocationAuto(locationId,countryId,stateId,cityId,zipCodeId) {
    initialize(locationId,countryId,stateId,cityId,zipCodeId);
}
function initialize(locationId,countryId,stateId,cityId,zipCodeId) {
 
    	autocomplete = new google.maps.places.Autocomplete((document.getElementById(locationId)), {
            types: ['geocode'],
      
        });
   
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
      case "country":
        address.country = place.address_components[i].long_name;
        break;
      case "postal_code":
        address.zipcode = place.address_components[i].long_name;
        
    }
  }
  $("#"+countryId).val(address.country).change();
  $("#"+stateId).val(address.state).change();
  $("#"+cityId).val(address.city).change();
  $("#"+zipCodeId).val(address.zipcode).change();
  
});
}
</script>
<script>
function refreshGoogleApiLocation(locationId,countryId,stateId,cityId,zipCodeId){
	$("#"+locationId).val('');
	$("#"+countryId).val('');
	$("#"+stateId).val('');
	$("#"+cityId).val('');
	$("#"+zipCodeId).val('');
}
</script>

<script>
  function navtoggle(elementRef){
	  
	 if('${SourceLeadWeb_userSpocRolesListCount}' < 6){
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area'
	     document.getElementById("spockLinksList").style.display="block";
	 }else{
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area spoclinksMore'
		 document.getElementById("spockLinksList").style.display="block";
	 }
  }
  $('html').click(function(){
	  $('#spockLinksList').hide();
  });
  
  $(".offOrg").click(function( event ) {
	  event.stopPropagation();
	});
  </script>
  
 <script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>


<script src="/ats/resources/js/slead-common.js?staticTime=${staticTime}"></script>
