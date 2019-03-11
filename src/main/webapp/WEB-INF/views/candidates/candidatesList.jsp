<!doctype html>
<%-- <jsp:include page="masterSideNav.jsp" /> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
<meta charset="utf-8">
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link href="/resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<link href="/resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/timesheet.css">
<link href="/resources/css/sourcelead-bundle.css" rel="stylesheet">

<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/masterHeaderAction.js"></script>
<script src="/resources/js/jquery-ui.js"></script>
<script src="/resources/js/formvalidator.js"></script>
<script src="/resources/js/bootstrap-select.min.js"></script>
<script src="/resources/js/chosen.jquery.min.js"></script>
<script src="/resources/js/jquery.dataTables.min.js"></script>
<script src="/resources/js/sourcelead-app.js"></script>
<script src="/resources/js/jquery.timepicker.js"></script>
<script src="/resources/js/masterHeaderAction.js"></script>
<script src="/resources/js/masterSideNav.js"></script>
<script src="/resources/js/masterHeader.js"></script>
<script src="${SourceLeadWeb_atsUrl}/resources/js/candidates.js"></script>
</head>
<c:if test="${!empty msg}"> <!-- candidatesProfile.jsp script -->
       <div class="success_pop" id="selector"  style="display: block;"> 
         <i class="fa fa-check-circle"></i><span>${msg}</span>
	 </div>
</c:if>
 <script>
 var  tabAccessArray = '${SourceLeadWeb_tabsList}';
 var roleActionsArray = '${SourceLeadWeb_actionsList}';
 var sourceleadUrl ='${sourceleadUrl}';
 var atsUrl = '${SourceLeadWeb_atsUrl}';
 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
 var loginUserRole = '${SourceLeadWeb_loginUserRole}';
 var userId =  '${SourceLeadWeb_userId}';
 var orgLocationId  = '${SourceLeadWeb_orgLocationId}';

 var orgLocationCode = '${SourceLeadWeb_locationCode}';// snigitha copied from the dashboard.js
 var orgCode = '${SourceLeadWeb_orgCode}';// snigitha copied from the dashboard.js

 var isAuthenticated = '${SourceLeadWeb_isAuthenticated}'
 var userSessionId = '${SourceLeadWeb_actionUUID}';
 var userImgUrl =  '${SourceLeadWeb_userImgUrl}'; 
 var locationCode ='${SourceLeadWeb_locationCode}';
 var loginUser = '${SourceLeadWeb_loginUserFullName}';
 var locationList = '${SourceLeadWeb_dateFormat}';	
 var country = '${SourceLeadWeb_country}';
 var timezone = '${SourceLeadWeb_timezone}';
 var timezoneId ='${SourceLeadWeb_timeZoneId}'; 
 var candidateId = '${SourceLeadWeb_candidateId}';
 var isGlobalAccess = '${SourceLeadWeb_isGlobalAccess}';

 // below are related variable used in showing tabs in dashboard.
 var bothTimesheets ='${bothTimesheets}';
 var onlyTimesheets ='${onlyTimesheets}';
 var onlyApprovalTimesheets ='${onlyApprovalTimesheets}';
 // reruirement list variables.
 var orgATS = '${SourceLeadWeb_ats}';
 var orgVMS = '${SourceLeadWeb_vms}';
 var requirementId = '${requirementId}'; 
 var locationDate = '${SourceLeadWeb_dateFormat}';
 </script>
	<script>
	var isAssociate = 'N';
	var candidateIDsArr=[];
	var _csrfParam = '${_csrf.parameterName}';
	var _csrfToken = '${_csrf.token}';
	var associateFlag = false;
	'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_HOTLIST\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
		associateFlag = true;
	'</sec:authorize>';
	var name1Flag = false;
	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CANDIDATE\',\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_VIEW_DELETE_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
	name1Flag = true;
	'</sec:authorize>';
	var name2Flag = false;
	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CANDIDATE\',\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_VIEW_DELETE_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
	name2Flag = true;
	'</sec:authorize>';
	 window.onload = function() {
	//	 candidateListCheck("${serachValue}","${universalCanddateList}","${candidateKeyword}","${candidateIdSearchId}");
		/*  if('${candidateUUID}' != null && '${candidateUUID}' != '' && '${candidateUUID}' != 'undefined'){
				candidateQuickViewProfile('${candidateId}');
			} */
	};
	</script>
<script>

function noAccess(id){ 	
    var result = "";
	result = result + '	  <div class="blank-pages-tech">';
	result = result + '   <p class="text-center text-danger permissondenied mb">';
	result = result + '       <img src="/resources/images/sourcelead-load.png" alt="Source Lead" title="Source Lead">';
	result = result + '   </p>';
	result = result + '   <p class="big-txt mt">PERMISSION DENIED</p>';
	result = result + '   <p class="text-muted">You dont have permission to access this page.</p>';
	result = result + '   <p class="positionRelative move-btn btn-group mt">';
	result = result + '   <i class="fa fa-home btn btn-primary"></i>';
	result = result + '   <a href="<c:url value='/dashboard'/>" type="button" class="btn btn-primary">Take me home<span>&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
	result = result + '   </span></a>';
	result = result + '   </p>';
	result = result + ' </div>';
	    
   $("#tab-"+id).empty();
   $("#tab-"+id).append(result);
}

</script>
<script>
var serachValue ="${serachValue}";
var universalCanddateList = "${universalCanddateList}";
var candidateKeyword = "${candidateKeyword}";
var candidateIdSearchId = "${candidateIdSearchId}";
var candidateUUID = "${candidateUUID}";
 $(document).ready(function() {
	   // USE head.js for loading resources
		$('body').load(atsUrl+"/resources/sourceleadHtmls/candidatesListHtml.html");
		 
	});
</script>
<script>
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
<script>
function editCandidate(candidateId){ //1. candidatesProfile.jsp 
	
	  $("#profileEditPopup").show();
	  var loginCountryName = country;
     
	  $.ajax({
      type : "POST",
       url: atsUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
       success :function(result) {
         var json = result.object;
    	   if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
            		if(json != null){  
          			 	var lastName = '';
          			 	if(json.lastName != null && json.lastName != '' && json.lastName != 'undefined'){
          			 		lastName = json.lastName;            			 		
          			 	}
          			 	var middleName = '';
          			 	if(json.middleName != null && json.middleName != '' && json.middleName != 'undefined'){
          			 		middleName = json.middleName;            			 		
          			 	}
      			 	    var mobilePhone = '';
          			 	if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone != 'undefined'){
          			 		mobilePhone = json.mobilePhone;            			 		
          			 	}
          			 	
          			 	var alternateMobilePhone = '';
          			 	if(json.alternateMobilePhone != null && json.alternateMobilePhone != '' && json.alternateMobilePhone != 'undefined'){
          			 		alternateMobilePhone = json.alternateMobilePhone;            			 		
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
          			 	
          			 	var experienceMonths = '';
          			 	if(json.experienceMonths != null && json.experienceMonths != '' && json.experienceMonths != 'undefined'){
          			 		experienceMonths = json.experienceMonths;            			 		
          			 	}else{
          			 		experienceMonths = '';  
          			 	 }
          			 	
          			 	var designationOtherType = '';
          			 	if(json.designationOtherType != null && json.designationOtherType != '' && json.designationOtherType != 'undefined'){
          			 		designationOtherType = json.designationOtherType;            			 		
          			 	}else{
          			 		designationOtherType = '';  
          			 	 }
          			 	
          			 	var summary = '';
          			 	if(json.summary != null && json.summary != '' && json.summary != 'undefined'){
          			 		summary = json.summary;            			 		
          			 	}
          			 	
          			 	var address = '';
          			 	if(json.address != null && json.address != '' && json.address != 'undefined'){
          			 		address = json.address;            			 		
          			 	}
          			 	
          			 	var location = '';
          			 	if(json.location != null && json.location != '' && json.location != 'undefined'){
          			 		location = json.location;            			 		
          			 	}
          			 	
          			 	var primarySkill = '';
          			 	if(json.primarySkill != null && json.primarySkill != '' && json.primarySkill != 'undefined'){
          			 		primarySkill = json.primarySkill;            			 		
          			 	}
          			 	if(json.vendorName != null && json.vendorName != '' && json.vendorName != 'undefined'){
          			 		vendorName = json.vendorName;
          			 	}else{
          			 		vendorName = "";
          			 	}
          			 	
          			 	var zipCode = '';
          			 	if(json.zipcode != null && json.zipcode != '' && json.zipcode != 'undefined'){
          			 		zipCode = json.zipcode;            			 		
          			 	}
          			 	var country = '';
          			 	if(json.country != null && json.country != '' && json.country != 'undefined'){
          			 		country = json.country;            			 		
          			 	}
          			 	
          			 	var state = '';
          			 	if(json.state != null && json.state != '' && json.state != 'undefined'){
          			 		state = json.state;            			 		
          			 	}
          			 	
          			 	var city = '';
          			 	if(json.city != null && json.city != '' && json.city != 'undefined'){
          			 		city = json.city;            			 		
          			 	}
          			 	
          			 	
							var result ='';
							result  = result + '<div class="success_pop" id="selector11" style="display: none;">';
						    result  = result + '<i class="fa fa-check-circle"></i><span id="candMsgDivSum"></span>';
						    result  = result + '</div>';
							result = result + '		    <div class="candidate-editdetails col-md-10 edit-candaite canprofile-popright"> <i class="fa fa-close close-div" onclick="cancelEdit();"></i>';
							result  = result + '         <form id="candidateProfileEdit_form" name="candidateProfileEdit_form" method="post">';
							result  = result + '<input type="hidden" id="orgloationId" name="orgloationId" value="${SourceLeadWeb_orgLocationId}" />';
							result  = result + '<input type="hidden" id="loginUserId" name="loginUserId" value="${SourceLeadWeb_user.id}" />';
							result = result + '		      <div class="form-horizontal clearfix">';
							result = result + '<input type="hidden" class="form-control input-sm" name="candidateId"  value="'+candidateId+'">';
							
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-4">';
							result = result + '		            <input type="text" class="form-control input-sm" name="firstName" value="'+json.firstName+'" placeholder="First Name *" field-name="First Name"  data-validation="required validate_AlphaNumber validate_Space validate_length length1-100">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-4">';
							result = result + '		            <input type="text" class="form-control input-sm" name="lastName" value="'+lastName+'" placeholder="Last Name" data-validation="validate_AlphaNumber validate_Space validate_length length1-100" data-validation-optional="true">';
							result = result + '		          </div>';
							
							result = result + '		          <div class="col-md-4">';
							result = result + '		            <input type="text" class="form-control input-sm" name="middleName" value="'+middleName+'" placeholder="Middle Name" data-validation="validate_AlphaNumber validate_Space validate_length length1-100" data-validation-optional="true">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							if(json.isAssociate == 'Y'){
							result = result + '	           <input type="hidden" name="isAssociate" id="isAssociate" value="Y"/>';
							}
							// If Candidate Type equals Consultant email field should be Disable mode
							result = result+'		<div class="col-md-6">';
							result = result+'		<div class="row">';
							result = result+'		<label class="col-md-3">Gender</label>  ';
							result = result+'		<div class="col-md-9">    ';
							if(json.gender == "Male"){
							result = result+'		<label class="paddingLeft"><input type="radio"  checked value="Male" name="gender"><span class="mr5"></span>Male</label>                   ';
							result = result+'		<label class="paddingLeft paddingRight"><input type="radio"  value="Female" name="gender"><span class="mr5"></span>Female</label>   ';
							result = result+'		<label class="paddingRight"><input type="radio"  value="Transgender" name="gender"><span class="mr5"></span>Transgender</label>     ';
							}
							else if(json.gender == "Female"){
								result = result+'		<label class="paddingLeft"><input type="radio"   value="Male" name="gender"><span class="mr5"></span>Male</label>                   ';
								result = result+'		<label class="paddingLeft paddingRight"><input type="radio" checked value="Female" name="gender"><span class="mr5"></span>Female</label>   ';
								result = result+'		<label class="paddingRight"><input type="radio"  value="Transgender" name="gender"><span class="mr5"></span>Transgender</label>     ';
								}
							else if(json.gender == "Transgender"){
								result = result+'		<label class="paddingLeft"><input type="radio"   value="Male" name="gender"><span class="mr5"></span>Male</label>                   ';
								result = result+'		<label class="paddingLeft paddingRight"><input type="radio"  value="Female" name="gender"><span class="mr5"></span>Female</label>   ';
								result = result+'		<label class="paddingRight"><input type="radio"  checked value="Transgender" name="gender"><span class="mr5"></span>Transgender</label>     ';
								}
							else{
								result = result+'		<label class="paddingLeft"><input type="radio"   value="Male" name="gender" onblur="genderCheckValidation(this)"><span class="mr5"></span>Male</label>                   ';
								result = result+'		<label class="paddingLeft paddingRight"><input type="radio"  value="Female" name="gender" onblur="genderCheckValidation(this)"><span class="mr5"></span>Female</label>   ';
								result = result+'		<label class="paddingRight"><input type="radio" value="Transgender" name="gender" onblur="genderCheckValidation(this)"><span class="mr5"></span>Transgender</label>     ';
								result = result+' <input type="hidden"  id="genderValue" name="genderValue"  field-name="Gender" data-validation="required"/> 		';

							}
							result = result+'		</div>    ';
							result = result+'		</div>    ';
							result = result+'		</div>    ';
							
							result = result+'		<div class="col-md-6">';
							result = result+'		<div class="row">';
							result = result+'		<label class="col-md-3 paddingRight">Marital Status</label>  ';
							result = result+'		<div class="col-md-9">';
							if(json.maritalStatus == "Single"){
							result = result+'		<label class="paddingRight"><input type="radio"  value="Single" checked name="maritalStatus"><span class="mr5"></span>Single</label>                  ';
							result = result+'		<label class="paddingLeft paddingRight"><input type="radio"  value="Married" name="maritalStatus"><span class="mr5"></span>Married</label>   ';
							result = result+'		<label class="paddingRight"><input type="radio"  value="Divorsed" name="maritalStatus"><span class="mr5"></span>Divorsed</label>     ';
							result = result+' 		<label class="paddingRight"><input type="radio"  value="Separated" name="maritalStatus"><span class="mr5"></span>Separated</label>';
							}
							else if(json.maritalStatus == "Married"){
								result = result+'		<label class="paddingRight"><input type="radio"  value="Single" name="maritalStatus"><span class="mr5"></span>Single</label>                  ';
								result = result+'		<label class="paddingLeft paddingRight"><input type="radio"  checked value="Married" name="maritalStatus"><span class="mr5"></span>Married</label>   ';
								result = result+'		<label class="paddingRight"><input type="radio"  value="Divorsed" name="maritalStatus"><span class="mr5"></span>Divorsed</label>     ';
								result = result+' 		<label class="paddingRight"><input type="radio"  value="Separated" name="maritalStatus"><span class="mr5"></span>Separated</label>';
								}
							else if(json.maritalStatus == "Divorsed"){
								result = result+'		<label class="paddingRight"><input type="radio"  value="Single" name="maritalStatus"><span class="mr5"></span>Single</label>                  ';
								result = result+'		<label class="paddingLeft paddingRight"><input type="radio"  value="Married" name="maritalStatus"><span class="mr5"></span>Married</label>   ';
								result = result+'		<label class="paddingRight"><input type="radio"  value="Divorsed" checked name="maritalStatus"><span class="mr5"></span>Divorsed</label>     ';
								result = result+' 		<label class="paddingRight"><input type="radio"  value="Separated" name="maritalStatus"><span class="mr5"></span>Separated</label>';
								}
							else if(json.maritalStatus == "Separated"){
								result = result+'		<label class="paddingRight"><input type="radio"  value="Single" name="maritalStatus"><span class="mr5"></span>Single</label>                  ';
								result = result+'		<label class="paddingLeft paddingRight"><input type="radio"  value="Married" name="maritalStatus"><span class="mr5"></span>Married</label>   ';
								result = result+'		<label class="paddingRight"><input type="radio"  value="Divorsed" name="maritalStatus"><span class="mr5"></span>Divorsed</label>     ';
								result = result+' 		<label class="paddingRight"><input type="radio"  checked value="Separated" name="maritalStatus"><span class="mr5"></span>Separated</label>';
								}
							else{
								result = result+'		<label class="paddingRight"><input type="radio"  value="Single" name="maritalStatus"><span class="mr5"></span>Single</label>                  ';
								result = result+'		<label class="paddingLeft paddingRight"><input type="radio"  value="Married" name="maritalStatus"><span class="mr5"></span>Married</label>   ';
								result = result+'		<label class="paddingRight"><input type="radio"  value="Divorsed" name="maritalStatus"><span class="mr5"></span>Divorsed</label>     ';
								result = result+' 		<label class="paddingRight"><input type="radio"  value="Separated" name="maritalStatus"><span class="mr5"></span>Separated</label>';
								}
							result = result+'		</div> ';
							result = result+'		</div> ';
							result = result+'		</div> ';
							
							result = result + ' <div class="col-md-6 mb10">';
							result = result + '<div class="input-group">';
							if(json.dateofBirth != null && json.dateofBirth != "" && json.dateofBirth != "undefined"){
								result = result + ' <input type="text" value="'+json.dateofBirth+'" name="dateofBirth" id="dateofbirth-candidate" placeholder = "DateOfBirth" readonly="readonly" class="form-control calendar" readonly>';
								result = result + '   <span class="input-group-addon" onClick="cleardateofBirth();"><i class="colorRed fa fa-times cursorPointer"></i></span> ';

							}else{
								result = result + ' <input type="text" value="" name="dateofBirth" id="dateofbirth-candidate"  placeholder = "DateOfBirth" class="form-control calendar">';
								result = result + '   <span class="input-group-addon" onClick="cleardateofBirth();"><i class="colorRed fa fa-times cursorPointer"></i></span> ';

							}
							result = result + ' </div></div>';
							
							result = result + '<div class="clearfix"></div>';
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6" id="emailDiv">';
							if(json.candidateType == "Candidate"){
								result = result + '<div class="input-group errorInptgroup">';
								result = result + '<input type="hidden" name="candidateType" id="candidateType" value="'+json.candidateType+'" />';

								
								result = result + '<input type="text" class="form-control input-sm" id="email" name="email" value="'+json.email+'" placeholder="Email*" field-name="Email" data-validation="validate_Space required validate_email validate_length length2-100" onblur="validateCandidateEmail();">';
								

								result = result + '<input type="hidden" id="tempEmail" value="'+json.email+'" />';
								result = result + ' <span id="emailStatusIconDiv" class="input-group-addon">';
								result = result + '<i class="fa fa-check text-muted" style="color:#f6f6f6">';
								result = result + '</i>';
								result = result + '</span>';
								result = result + '		        </div>';
							}
							if(json.candidateType == "Consultant"){
							     result = result + '<input type="text" class="form-control input-sm" name="email" readonly value="'+json.email+'" placeholder="Email*" field-name="Email" data-validation="validate_Space required validate_email validate_length length2-100" readonly="true">';
							}
							result = result + '</div>';
							
							result = result + '<div class="col-md-6">';
							result = result + '<input type="text" class="form-control input-sm" name="mobilePhone"  value="'+mobilePhone+'" placeholder="Mobile" field-name="Mobile" data-validation="validate_Space validate_length length9-20 validate_phone" data-validation-optional="true">';
							result = result + '</div>';
							
							result = result + '</div>'; 
							
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6">';
							result = result + '<input type="text" class="form-control input-sm" name="alternateMobilePhone"  value="'+alternateMobilePhone+'" placeholder="Alternative Number" field-name="Alternative Number" data-validation="validate_Space validate_length length9-20 validate_phone" data-validation-optional="true">';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6">';
							result = result + '<input type="text" class="form-control input-sm" name="designation"  value="'+designation+'" placeholder="Designation">';
							result = result + '</div>';
							
							result = result + '</div>';
							
							
							result = result + '<div class="clearfix mb10">';
							
							result = result + '<div class="col-md-6">';
							result = result + '<input type="text" class="form-control input-sm" name="address" id="address"  value="'+address+'" placeholder="Street Address" field-name="Address" data-validation="validate_Space validate_length length2-250" data-validation-optional="true">';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6" id="locationField">';
							result = result + '<input type="text" class="form-control input-sm" name="location"  id="cpLocation"  value="'+location+'" placeholder="Address" field-name="Location" data-validation="required">';
							result = result + '<i class="fa fa-refresh btn btn-primary profilerefresh-icon" title="Refresh" onclick="refreshGoogleApiLocation(\'cpLocation\',\'cpCountry\',\'cpState\',\'cpCity\',\'cpZipCode\',\'address\');"></i>';
							result = result + '</div>'; 
							
							result = result + '</div>';
							
							result = result + '<div class="clearfix mb10">';
							
							result = result + '<div class="col-md-6 custom-fields">';
							result = result + '<input class="form-control" id="cpCountry" name="country" value="'+country+'" data-validation="required" field-name="Country"  placeholder="Country" type="text"></input>';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow">';
							result = result + '<input class="form-control" id="cpState" name="state" value="'+state+'" placeholder="State" type="text" ></input>';
							result = result + '</div>';
							
							result = result + '</div>';
							
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6 custom-fields">';
							result = result + '<input class="form-control" id="cpCity" name="city" value="'+city+'" placeholder="City" type="text" ></input>';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6 custom-fields">';
							result = result + '<input class="form-control" id="cpZipCode" name="zipcode" value="'+zipCode+'" placeholder="Zip code" type="text"></input>';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6 custom-fields hidden">';
							result = result + '<input class="form-control" id="cpLatitude" name="latitude" value="'+json.latitude+'" type="text"></input>';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6 custom-fields hidden">';
							result = result + '<input class="form-control" id="cpLongitude" name="longitude" value="'+json.longitude+'" type="text"></input>';
							result = result + '</div>';
							
							result = result + '</div>';
							
							result = result + '<div class="clearfix mb10">';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow select-field">';
							result = result + '<select class="form-control" id="noticePeriod" name="noticePeriod" field-name="Notice Period">';
							result = result + '<option value="">--Select Notice Period--</option>';
							result = result + ' <option '+(json.noticePeriod == "Immediately available" ? "selected" : '') +'>Immediately available</option>';
							result = result + '<option  '+ (json.noticePeriod == "15 Days" ? "selected" : '') +'>15 Days</option>';
							result = result + '<option  '+(json.noticePeriod == "1 Month" ? "selected" : '') +'>1 Month</option>';
							result = result + '<option  '+(json.noticePeriod == "2 Months" ? "selected" : '') +'>2 Months</option>';
							result = result + '<option  '+(json.noticePeriod == "3 Months" ? "selected" : '') +'>3 Months</option>';
							result = result + ' </select>';
							result = result + ' </div>';
							
							result = result + '</div>';

							
							result = result + '<div class="clearfix mb">';
							result = result + '<div class="col-md-6">';
							result = result + '<div class="input-group er ratetype">';
							result = result + '<div class="input-group-btn tblminwidth-75 select-field">';
							result = result + ' <select class="selct-picker" id="currentRateCurrency" name="currentRateCurrency">';
							result = result + ' <option '+(json.currentRateCurrency == "INR" ? "selected" : '') +'><a href="#">INR</a></option>';
							result = result + '<option   '+(json.currentRateCurrency == "AUD" ? "selected" : '') +'><a href="#">AUD</a></option>';
							result = result + ' <option  '+(json.currentRateCurrency == "USD" ? "selected" : '') +'><a href="#">USD</a></option>';
							result = result + '  <option '+(json.currentRateCurrency == "GBP" ? "selected" : '') +'><a href="#">GBP</a></option>';
							result = result + '  <option '+(json.currentRateCurrency == "SGD" ? "selected" : '') +'><a href="#">SGD</a></option>';
							result = result + '<option   '+(json.currentRateCurrency == "EUR" ? "selected" : '') +'><a href="#">EUR</a></option>'; 
			                result = result + ' </select>';
			                
			                result = result + '</div>';
							
			                result = result + '<input type="text" class="form-control input-sm" name="currentRate" id="currentRate" value="'+currentRate+'" placeholder="Current Rate" field-name="Current Rate" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true">';
							
							result = result + '<div class="input-group-btn tblminwidth-75 ratetype-right select-field">';
							result = result + '<select class="selct-picker" id="currentRateType" name="currentRateType" field-name="Rate Per">';
							result = result + '<option value="">Rate Per</option>';
							result = result + ' <option '+(json.currentRateType == "Hourly" ? "selected" : '') +'>Hourly</option>';
							result = result + '<option  '+ (json.currentRateType == "Weekly" ? "selected" : '') +'>Weekly</option>';
							result = result + '<option  '+(json.currentRateType == "Bi-weekly" ? "selected" : '') +'>Bi-weekly</option>';
							result = result + '<option  '+(json.currentRateType == "Semi-Monthly" ? "selected" : '') +'>Semi-Monthly</option>';
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
							result = result + ' <option '+(json.expectedRateCurrencey == "INR" ? "selected" : '') +'><a href="#">INR</a></option>';
							result = result + '<option   '+(json.expectedRateCurrencey == "AUD" ? "selected" : '') +'><a href="#">AUD</a></option>';
							result = result + ' <option  '+(json.expectedRateCurrencey == "USD" ? "selected" : '') +'><a href="#">USD</a></option>';
							result = result + '  <option '+(json.expectedRateCurrencey == "GBP" ? "selected" : '') +'><a href="#">GBP</a></option>';
							result = result + '  <option '+(json.expectedRateCurrencey == "SGD" ? "selected" : '') +'><a href="#">SGD</a></option>';
							result = result + '<option   '+(json.expectedRateCurrencey == "EUR" ? "selected" : '') +'><a href="#">EUR</a></option>'; 
			                result = result + ' </select>';
			                
			                result = result + '</div>';
							
							result = result + '<input type="text" class="form-control input-sm" name="expectedRate" id="expectedRate" value="'+expectedRate+'" placeholder="Expected Rate" field-name="Expected Rate" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true">';
							
							result = result + '<div class="input-group-btn tblminwidth-75 ratetype-right select-field">';
							result = result + '<select class="selct-picker" id="expectedRateType" name="rateType" field-name="Rate Per">';
							result = result + '<option value="">Rate Per</option>';
							result = result + ' <option '+(json.rateType == "Hourly" ? "selected" : '') +'>Hourly</option>';
							result = result + '<option  '+ (json.rateType == "Weekly" ? "selected" : '') +'>Weekly</option>';
							result = result + '<option  '+(json.rateType == "Bi-weekly" ? "selected" : '') +'>Bi-weekly</option>';
							result = result + '<option  '+(json.rateType == "Semi-Monthly" ? "selected" : '') +'>Semi-Monthly</option>';
							result = result + '<option  '+(json.rateType == "Monthly" ? "selected" : '') +'>Monthly</option>';
							result = result + '<option  '+(json.rateType == "Bi-Monthly" ? "selected" : '') +'>Bi-Monthly</option>';
							result = result + '<option '+(json.rateType == "Yearly" ? "selected" : '') +'>Yearly</option>';
							result = result + ' </select>';
							result = result + '</div>';
							
							result = result + '</div>';
							result = result + '</div>';
							result = result + '</div>';
							
							result = result + '<div class="clearfix">';
							result = result + '<div class="col-md-6">';
							result = result + '<div class="form-group">';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow select-field">';
							
							result = result + '<select  class="form-control" name="experienceYears"  id="experienceYears" field-name="Experience Years" onchange="javascript:disableMonths();">';
							result = result + '<option value="">Select Years</option>';
							
							<c:forEach var="i" begin="0" end="49" >
								result = result  +'<option value="${i}" '  + (json.experienceYears == "${i}" ? "selected" : '') +  '>${i}</option>';
							</c:forEach>
							
							if(json.experienceYears == "50"){
								result = result  +'<option value="50" '+ (json.experienceYears == "50" ? "selected" : '') +'>50</option>';
							}else{
								result = result  +'<option>50</option>';
							}
							
							result = result + '</select>';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow select-field">';
							if(json.experienceYears == "50"){
							result = result + '<select  class="form-control" name="experienceMonths"  id="experienceMonths" field-name="experienceMonths" >';
							result = result + '<option  value="">Select Months</option>';
							
							<c:forEach var="i" begin="0" end="11" >
							 	result = result  +'<option  value="${i}" '  + (json.experienceMonths == "${i}" ? "selected" : '') +  '>${i}</option>';
						    </c:forEach> 
							
							
							result = result + '</select>';
							}else{
								result = result + '<select  class="form-control" name="experienceMonths"  id="experienceMonths" field-name="experienceMonths">';
								result = result + '<option  value="">Select Months</option>';
								
								<c:forEach var="i" begin="0" end="11" >
								 result = result  +'<option  value="${i}" '  + (json.experienceMonths == "${i}" ? "selected" : '') +  '>${i}</option>';
							    </c:forEach>
								
								result = result + '</select>';
							}
							
							result = result + '</div>';
							
							result = result + '</div>';
							result = result + '</div>';
							
							
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control" name="primarySkill" value="'+primarySkill+'" placeholder="Primary Skill" data-validation="validate_Space validate_length length1-100" data-validation-optional="true">';
							result = result + '		          </div>';
							
							
							result = result + '</div>';
							result = result + '<input type="hidden" value="'+json.designationType+'" id="hiddenId" >';

							
							result = result + '<div class="clearfix mb10">';
                          result = result + '<div class="col-md-6 custom-fields">';
							
                          result = result + '<div class="input-group-btn tblminwidth-75 ratetype select-field">';
							result = result + '<select class="form-control" id="designationType1" name="designationType"  onchange="employementType();updateDesignationOtherType();" field-name="Employment type" data-validation="required">';
							result = result + '<option value="">--Select Employment Type--</option>';
							result = result + ' <option '+(json.designationType == "C2C" ? "selected" : '') +'>C2C</option>';
							result = result + ' <option '+(json.designationType == "C2C-1099" ? "selected" : '') +'>C2C-1099</option>';
							result = result + '<option  '+ (json.designationType == "W-2" ? "selected" : '') +'>W-2</option>';
							result = result + ' <option '+(json.designationType == "C2H" ? "selected" : '') +'>C2H</option>';
							result = result + ' <option '+(json.designationType == "C2H -1099" ? "selected" : '') +'>C2H -1099</option>';
							result = result + '<option  '+(json.designationType == "Part Time" ? "selected" : '') +'>Part Time</option>';
							result = result + '<option  '+(json.designationType == "Full Time" ? "selected" : '') +'>Full Time</option>';
							result = result + '<option  '+(json.designationType == "Offshore C2C" ? "selected" : '') +'>Offshore C2C</option>';
							result = result + '<option  '+(json.designationType == "Other" ? "selected" : '') +'>Other</option>';
							result = result + ' </select>';
							result = result + '</div>';
							
							result = result + '</div>';
							 
							
							result = result + '<div class="col-md-6 designationTypeDiv" id="designationTypeDiv" style="display:none">';
							result = result + '<input type="text" class="form-control input-sm" name="designationOtherType"  id="designationOtherType1" value="'+designationOtherType+'" placeholder="Employment Other Type" field-name="Employment Other Type" data-validation="validate_Space validate_length length2-50" data-validation-optional="true">';
							result = result + '</div>';
							
							
						    result = result + '<span class="" id="hideVendor" style="display:none">'; 
							result = result + '<div class="col-md-6 custom-fields select-arrow" id="errorvendor">';
							result = result + '<select  class="chosen-select form-control select-bg" name="vendors" data-validation-optional="true"  id="vendorName" value="'+vendorName+'" placeholder="select Vendor" field-name="Vendor" onchange="getVendorContactList();removeValiationVendor();">';
							result = result + '</select>';
							result = result + '<input type="hidden" name="updateVendorName" id="updateVendorName" value="'+vendorName+'" />';
							result = result + '<input type="hidden" name="updateVendorOrgRelationId" id="updateVendorOrgRelationId" value="'+json.vendorOrgRelationId+'" />';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow mt" id="errorvendorContact">';
							result = result + '<select  class="chosen-select form-control select-bg" name="vendorcontact" data-validation-optional="true"  value="'+json.vendorContactName+'" id="vendorcontactName" field-name="Vendor Contact"  placeholder="select Vendor Contact" onchange="removeValiationVendorContact();">';
							result = result + '</select>';
							result = result + '<input type="hidden" name="updateVendorContactName" id="updateVendorContactName" value="'+json.vendorContactName+'" />';
							result = result + '<input type="hidden" name="updateSpocId" id="updateSpocId" value="'+json.vendorSpocId+'" />';
							result = result + '</div>';
							result = result + '</div>';
							
							result = result + '</span>';
							
							result = result + '<div id="customFieldDataId" class="myForm columnfields-custom"></div>';
							result = result + '<div class="clearfix"></div>';
							result = result + '<div class="clearfix mb10 mt">';
							result = result + '<div class="col-md-6">';
							result = result + '<textarea placeholder="Summary" class="form-control input-sm"  name="summary" rows="2" cols="5" field-name="Summary" data-validation=" validate_length length2-5000" data-validation-optional="true">'+summary+'</textarea>';
							result = result + ' </div>'; 
							result = result + ' </div>'; 
							result = result + '</div>';
							
							result = result + '<div class="clearfix mr">';
							result = result + '<div class="pull-right">';
							result = result + '<button type="button" id="update_candidate" onClick ="upDateCandidate('+candidateId+');" class="btn btn-sm btn-primary mr">Save</button>';
							result = result + '<button type="button" class="btn btn-sm btn-danger" onClick ="cancelEdit();">Cancel</button>';
							result = result + '		          </div>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		      </div>';
							result  = result + '              </form>';
							result = result + '		    </div>';
							result = result + '		  </div>';
							
							$("#profileEditPopup").empty(); 
							$("#profileEditPopup").append(result);
							preapareCustomFieldDetails(candidateId);
							initLocationAuto('cpLocation','cpCountry','cpState','cpCity','cpZipCode','cpLatitude','cpLongitude','','address');
							zipcodeMask(json.country,'cpZipCode');
							/* updateDesignationType(json.designationType); */
							
							if(json.designationType == "Other"){
								$("#designationTypeDiv").show();
						      }
							employementType();
							getVendorsByOrgLocation();
							$(".candidate-editdetails").addClass("candidate-editdetails-active");
//							$(".collapse-div").removeClass("edit-details-active");
							
							$(function(){
							    var config = {
									  '.chosen-select'           : {},
									  '.chosen-select-deselect'  : {allow_single_deselect:true},
									  '.chosen-select-no-single' : {disable_search_threshold:10},
									  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
									  '.chosen-select-width'     : {width:"95%"}
									}
							    
							    $("#vendorName").chosen();
							    $("#vendorcontactName").chosen();
							   
							    
							    
							    $('.chosen-select').each(function() {
										var $this = $(this);
										$this.next().css({'width': '100%'});
								  })
							 	  var locationDate ='${SourceLeadWeb_dateFormat}'; 
	  // written common method in slead-common.js for date formats start
	  							var finalDateFormat = getJSDateFormat(locationDate); 
								  $("#dateofbirth-candidate").datepicker({
								      dateFormat: finalDateFormat,
								      changeMonth: true,
								  	  changeYear: true, 
								  	  maxDate:0
								  });
							});
          		 
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

					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}

               },
                 
            }); 
	  
	  
	  //end
	 
		 
}
function candidateProfile(candidateId,candidateType,isAssociate){
	  var skillsList = '${skillsList}'; 
	  $.ajax({
      type : "POST",
       url: atsUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
       success :function(result) { 
      	 var json = result.object;
      	   if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
            		if(json != null) {
							var result = "";
							result = result+'<div id="removeCandidateAlert" ></div>';
							result = result+'<div id="loading"></div>';
							if(json.status == '0') {
								'<sec:authorize access="hasAnyRole(\'ROLE_REACTIVATE_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
								result = result + '<div class="project_tabs_inn_head mt10"><div class="anchor-inline"><a href="#" class="positionRelative showInfo"><i class="glyphicon glyphicon-info-sign"></i>Terminated<span class="infoContent col-xs-12 col-md-5"> '+json.terminateReason+' </span></a><a href="javascript:void(0)" onClick="reActivateCandidateAlertMessage('+candidateId+');"><i class="glyphicon glyphicon-trash"></i>ReActivate</a> </div>';
								'</sec:authorize>';
							} else {
								   result = result + '<div class="project_tabs_inn_head mt10"> <div class="anchor-inline">';
								   if(candidateType == "Candidates"){
									   '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
									   result = result + ' <a href="javascript:void(0)" onClick="editCandidate('+candidateId+');" class="mr addUser-org"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
									   '</sec:authorize>'; 
									   '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
									   if(isAssociate != "Y"){
										   result = result + ' <a href="javascript:void(0)" onClick="removeCandidateAlertMessage('+candidateId+',\''+candidateType+'\');"><i class="glyphicon glyphicon-trash"></i>Remove</a>'; 
									   }
									   '</sec:authorize>';
								   } else {
									   '<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE\')">';
									   result = result + ' <a href="javascript:void(0)" onClick="editCandidate('+candidateId+');" class="mr addUser-org"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
									   '</sec:authorize>'; 
									   '<sec:authorize access="hasRole(\'ROLE_VIEW_DELETE_ASSOCIATE\')">';
									   if(isAssociate != 'Y'){
										   result = result + ' <a href="javascript:void(0)" onClick="removeCandidateAlertMessage('+candidateId+',\''+candidateType+'\');"><i class="glyphicon glyphicon-trash"></i>Remove</a>'; 
									   }
									   '</sec:authorize>';
								   }
								result = result + '</div>';
							}
							result = result + '<div id="profileEditPopup"></div>';
							result = result +'<div class="require_wrap_details candidate_wrap_details">';	 
							result = result +'		<h1>Summary:</h1>';
							if(json.summary != null && json.summary != '' && json.summary != 'undifined'){
								result = result +'	    <textarea placeholder="Enter description" readonly>'+json.summary+'</textarea>';
							}
							else {
					        	result = result +'   <textarea placeholder="Enter description" readonly> --</textarea>';
				    		}
							result = result +'		<div class="clearfix dashboard-widget candidate-profile mt">';
							result = result +'		  <ul class="nav nav-tabs">                                                                                                                   ';
							result = result +'		    <li class="active"><a data-toggle="tab" href="#skills">General View</a></li>                                                              ';
							//result = result +'		    <li><a data-toggle="tab" href="#milestone">Milestone View</a></li>                                                                        ';
							result = result +'		  </ul>                                                                                                                                       ';
							result = result +'		  <div class="tab-content">                                                                                                                   ';
							 
							result = result +'		    <div id="skills" class="tab-pane fade in active">                                                                                         ';
							result = result +'		      <div class="skills-summary positionRelative">                                                                                                            ';
							result = result +'		        <div class="design-view skillsresult">                                                                                                             ';
							result = result +'		          <!-- Select Skills -->                                                                                                              ';
							result = result +'		<div class="clearfix">';
							result = result +'		          <h2 class="education  pull-left">Skills:</h2>';
							if(candidateType == "Candidates") {
								'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
								result = result +'		          <span id="editSkillBtnSpan" class="pull-right mt10 mb"><button type="button" id="editSkillBtn" name="editSkillBtn" class="btn btn-primary btn-sm" onclick="editSkill('+candidateId+');">Edit Skill</button></span>';
								 '</sec:authorize>';
							} else {
								'<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE\')">';
								result = result +'		          <span id="editSkillBtnSpan" class="pull-right mt10 mb"><button type="button" id="editSkillBtn" name="editSkillBtn" class="btn btn-primary btn-sm" onclick="editSkill('+candidateId+');">Edit Skill</button></span>';
								'</sec:authorize>';
							}
							result = result +'		          <span id="saveSkillBtnSpan" class="pull-right mt10 mb" style.display = "none";></span>';
							result = result +'<div class="clearfix">';
							result = result + '<select multiple class="chosen-select form-control" name="skillName" id="skillName" data-validation="validate_Space" data-validation-optional="true">';
							// here get lookup data skills and manually enter skills 
							$.each( json.allAndCandidateSkills, function( i, skill ) {
							  var existed = 0;
				                if(json.skillsName != null && json.skillsName != 'undefined' && json.skillsName != '') {
					                var res = [];
					                res = json.skillsName.split(",");
					                res.forEach(function(item) {
					                	if(skill == item){
					                		existed = 1;
					                		result = result + ' <option selected value="'+skill+'">'+skill+'</option>';
					                	}
					                });
				                }
				                if(existed == 0){
				                	result = result + ' <option value="'+skill+'">'+skill+'</option>';
				                }
							});
			                result = result + '</select>';
							result = result +'</div>';
							result = result +'</div>';
							result = result +'		</div>';
							result = result + '<div class="clearfix"></div> ';
							
							//visa type start
							result = result +'		    <div id="visa" class="tab-pane fade in active">                                                                                         ';
							result = result +'		      <div class="visa-summary positionRelative">                                                                                                            ';
							result = result +'		        <div class="design-view visaresult">                                                                                                             ';
							result = result +'		          <!-- Select Visa -->                                                                                                              ';
							result = result +'		<div class="clearfix">';
							result = result +'		          <h2 class="education  pull-left">Immigration Status:</h2>';
							if(candidateType == "Candidates"){
								'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
								result = result +'		          <span id="editVisaBtnSpan" class="pull-right mt10 mb"><button type="button" id="editVisaBtn" name="editVisaBtn" class="btn btn-primary btn-sm" onclick="editVisa('+candidateId+');">Edit Visa</button></span>';
								 '</sec:authorize>';
							}else{
								'<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE\')">';
								result = result +'		          <span id="editVisaBtnSpan" class="pull-right mt10 mb"><button type="button" id="editVisaBtn" name="editVisaBtn" class="btn btn-primary btn-sm" onclick="editVisa('+candidateId+');">Edit Visa</button></span>';
								'</sec:authorize>';
							}
							result = result +'		          <span id="saveVisaBtnSpan" class="pull-right mt10 mb" style.display = "none";></span>';
							result = result +'		</div>';
	                     	result = result +'<div class="clearfix">';
							result = result + '<select multiple class="chosen-select form-control" name="visaType" id="visaName" data-validation="validate_Space" data-validation-optional="true">';
							// here get lookup data skills and manually enter skills 
							$.each( json.allAndCandidateVisa, function( i, visa ){
								  var existed = 0;
					                if(json.visaType != null && json.visaType != 'undefined' && json.visaType != ''){
						                var res = [];
						                res = json.visaType.split(",");
						                res.forEach(function(item) {
						                	if(visa == item){
						                		existed = 1;
						                		result = result + ' <option selected value="'+visa+'">'+visa+'</option>';
						                	}
						                });
					                }
					                if(existed == 0){
					                	result = result + ' <option value="'+visa+'">'+visa+'</option>';
					                }
								});
			                result = result + '</select>';
							result = result +'</div>';
							//visa Type End
							result = result +'</div>';
							result = result +'</div>';
							result = result +'</div> ';						
							result = result +'<div class="design-view visaresult">';                                                                                                             		          
							result = result +'<div class="clearfix">';		  
							result = result +'<h2 class="education  pull-left">Additional Details:</h2>';		
							result = result +'</div>';
							result = result +'<ul class="list-unstyled listUlLi">';
							if(json.candidateCustomFieldData != null && json.candidateCustomFieldData != '' && json.candidateCustomFieldData != 'undefined'){
					              $.each(json.candidateCustomFieldData, function(key, value) {
					            	  var labelNameAndlabelData = value.split("$");
					            	  result = result + '	<div class="project_details_cta">';
					            	  result = result + '	<strong>'+labelNameAndlabelData[0]+':</strong><span class="fix">'+labelNameAndlabelData[1]+'</span><div class="clearfix"></div>';
					            	  result = result + '	</div>';
					              });
				              }
							result = result +'<div class="clearfix"></div>';
							result = result +'</ul>';
							result = result +'</div>';
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
							result = result + '    </div>';
							result = result + '  </div>';
							$("#tab-1").empty();
							$("#tab-2").empty(); 
							$("#tab-3").empty();  
							$("#tab-4").empty();  
							$("#tab-5").empty();  
							$("#tab-6").empty();  
							$("#tab-1").append(result);  
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
								$("#skillName").val('').trigger("chosen:updated");
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
							
							
							
							//tags type
							$(".btn-addtag").click(function(){
								$(".add-tag").css({"display":"block","opacity":"1"});
								$("#tagName").val('');
								$("#tagName").val('').trigger("chosen:updated");
								/* tag auto complete */
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
							//end of tags type
							
							
								//visa type
							$(".btn-addvisa").click(function(){
								$(".add-visa").css({"display":"block","opacity":"1"});
								$("#visaName").val('');
								$("#visaName").val('').trigger("chosen:updated");
								/* visa auto complete */
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
								//end visa type
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
							
							$("#skillName").chosen();
							$("#skillName").prop('disabled', true).trigger("chosen:updated");
							$(".close-div").click(function(){
							$(".add-skills").removeClass('add-skills-active');
							});
							$(".add-skills").click(function(e){
								e.stopPropagation();
							});
							
							
							//tag type
							$("#tagName").chosen();
							$("#tagName").prop('disabled', true).trigger("chosen:updated");
							$(".close-div").click(function(){
								$(".add-tag").removeClass('add-tag-active');
							});
							$(".add-tag").click(function(e){
								e.stopPropagation();
							});
								//end tag type
								
								
							//visa type
							$("#visaName").chosen();
							$("#visaName").prop('disabled', true).trigger("chosen:updated");
							$(".close-div").click(function(){
								$(".add-visa").removeClass('add-visa-active');
							});
							$(".add-visa").click(function(e){
								e.stopPropagation();
							});
								//end visa type
								
//								 $(".addUser-org").click(function(){
//									 $(".edit-details").toggleClass("edit-details-active");
//									 $(".collapse-div").removeClass("edit-details-active");
//								 });
							$(".close-div").click(function(){
								$(this).parent().toggleClass("edit-details-active");
							});
							$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details, .edit-details-active").click(function(e){
								e.stopPropagation();
							});
								/*  $('html').click(function(){
								  $(".edit-details").removeClass("edit-details-active");
							     });	 */
							$("#endDate").click(function(){
								$(".endDate").toggle(200);
							});
							     /* event.preventDefault(); */
		              } else {
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
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
		},
               
	}); 
} //1. candidatesProfile.jsp END >>>>>>>>>>>>>>>
</script>
<script>
function closeCandiatesSubmitedList() {
	$("#candidateRejectedDiv").empty();  
    $(".modal.paymentaccount-modal").fadeOut();
    $(".comMask").fadeOut();
}
</script>
<script>
$(document).ready(function() {
    $(".proj_show").click(function(){  
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
		
		clearJobAdvSearch();
		$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".req_side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});
function predefineProfileImage(imageId){   
	$(imageId).attr('src','/resources/images/userprofile-img.png');
}
function deleteImage(candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to delete this image ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteProfileImage("+candidateId+");this.disabled=true'><input class='u_can_color' type='button'  value='Cancel' onClick='imageCancel("+candidateId+");'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>