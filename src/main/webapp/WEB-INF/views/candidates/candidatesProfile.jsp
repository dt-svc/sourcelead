<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/candidate-common.css?staticTime=${staticTime}" rel="stylesheet">

<%-- 
     @author : David Raju.
     this script is for edit Candidate.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
	
	<script>
	function candUpdateMsgFadeinFunction() {
		$("#selector11").fadeIn(200);
        $('#selector11').delay(2000).fadeOut('slow');
	}
</script> 

<c:if test="${!empty msg}">
       <div class="success_pop" id="selector"  style="display: block;">
         <i class="fa fa-check-circle"></i><span>${msg}</span>
	 </div>
</c:if>

<script>
function editCandidate(candidateId){
	
	  $("#profileEditPopup").show();
	  var loginCountryName = '${loginCountryName}';
       
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
            			 	
            			 	
            			 	var otherNoticePeriod = '';
            			 	if(json.otherNoticePeriod != null && json.otherNoticePeriod != '' && json.otherNoticePeriod != 'undefined'){
            			 		otherNoticePeriod = json.otherNoticePeriod;            			 		
            			 	}else{
            			 		otherNoticePeriod = '';  
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
            			 	
            				var panNumber = '';
            			 	if(json.panNumber != null && json.panNumber != '' && json.panNumber != 'undefined'){
            			 		
            			 		panNumber = json.panNumber;            			 		
            			 	}
            			 	
            			 	var aadharNumber = '';
            			 	if(json.aadharNumber != null && json.aadharNumber != '' && json.aadharNumber != 'undefined'){
            			 		aadharNumber = json.aadharNumber;            			 		
            			 	}
            			 	
            			 	var ssnNumber = '';
            			 	if(json.ssnNumber != null && json.ssnNumber != '' && json.ssnNumber != 'undefined'){
            			 		ssnNumber = json.ssnNumber;            			 		
            			 	}
            			 	var candidateSource = '';
            			 	if(json.candidateSource != null && json.candidateSource != '' && json.candidateSource != 'undefined'){
            			 		candidateSource = json.candidateSource;            			 		
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
							if(loginCountryName == 'India'){
								result = result + '<div class="clearfix mb10" id="errPanNoDiv">';
								result = result + '<div class="col-md-6 custom-fields">';
								result = result + '<input class="form-control" id="panNumber" name="panNumber" value="'+panNumber+'" placeholder="Pan Number" type="text" onblur="validatePanNumber();" field-name="Pan Number" data-validation="validate_Space validate_panNumber"data-validation-optional="true" ></input>';
								result = result + '<input type="hidden" id="tempPanNumber" value="'+json.panNumber+'" />';
								result = result + '</div>';
								
								result = result + '<div class="col-md-6 custom-fields" id="errAadharNoDiv">';
								result = result + '<input class="form-control" id="aadharNumber" name="aadharNumber" value="'+aadharNumber+'" placeholder="Aadhar Number" type="text" onblur="validateAadharNumber();" field-name="Aadhar Number" data-validation="validate_Space validate_aadharNumber"data-validation-optional="true"></input>';
								result = result + '<input type="hidden" id="tempAadharNumber" value="'+json.aadharNumber+'" />';
								result = result + '</div>';
								result = result + '</div>';
	              		        }
								
								result = result + '<div class="clearfix mb10">';
								if(loginCountryName == 'United States'){
									result = result + '<div class="clearfix mb10" id="errSsnNoDiv">';
									result = result + '<div class="col-md-6 custom-fields">';
									result = result + '<input class="form-control" id="ssnNumberUs" name="ssnNumber" value="'+ssnNumber+'" onkeyup="validateSsnNumberDuplicateChek();" onblur="validateSsnNumberDuplicateChek();" placeholder="000-00-0000" type="text"></input>';
									result = result + '<input type="hidden" id="tempSsnNumber" value="'+json.ssnNumber+'" />';
									result = result + '</div>';
									result = result + '</div>';
		              		        }
								result = result + '</div>';
							result = result + '<div class="clearfix mb10">';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow select-field">';
							result = result + '<select class="form-control" id="noticePeriodEdit" name="noticePeriod" field-name="Notice Period" onchange="updateOtherNoticePeriod();">';
							result = result + '<option value="">--Select Notice Period--</option>';
							result = result + ' <option '+(json.noticePeriod == "Immediately available" ? "selected" : '') +'>Immediately available</option>';
							result = result + '<option  '+ (json.noticePeriod == "15 Days" ? "selected" : '') +'>15 Days</option>';
							result = result + '<option  '+(json.noticePeriod == "1 Month" ? "selected" : '') +'>1 Month</option>';
							result = result + '<option  '+(json.noticePeriod == "2 Months" ? "selected" : '') +'>2 Months</option>';
							result = result + '<option  '+(json.noticePeriod == "3 Months" ? "selected" : '') +'>3 Months</option>';
							result = result + '<option  '+(json.noticePeriod == "Other" ? "selected" : '') +'>Other</option>';
							result = result + ' </select>';
							result = result + ' </div>';
							
							
                             
							
							result = result + '<div class="col-md-6 noticePeriodUpdateDiv" id="noticePeriodUpdateDiv" style="display:none">';
							result = result + '<input type="text" class="form-control input-sm" name="otherNoticePeriod"  id="otherNoticePeriodEdit" value="'+otherNoticePeriod+'" placeholder=" Notice Period Other" field-name="Other Notice Period" data-validation="validate_Space validate_length length1-150" data-validation-optional="true">';
							result = result + '</div>';
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
							
							result = result + '<div class="input-group-btn tblminwidth-100 ratetype-right select-field">';
							result = result + '<select class="selct-picker tblminwidth-100" id="currentRateType" name="currentRateType" field-name="Rate Per">';
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
							result = result + '<div class="input-group-btn tblminwidth-100 select-field">';
			                  
							result = result + ' <select class="selct-picker tblminwidth-100" id="expectedRateCurrencey" name="expectedRateCurrencey">';
							result = result + ' <option '+(json.expectedRateCurrencey == "INR" ? "selected" : '') +'><a href="#">INR</a></option>';
							result = result + '<option   '+(json.expectedRateCurrencey == "AUD" ? "selected" : '') +'><a href="#">AUD</a></option>';
							result = result + ' <option  '+(json.expectedRateCurrencey == "USD" ? "selected" : '') +'><a href="#">USD</a></option>';
							result = result + '  <option '+(json.expectedRateCurrencey == "GBP" ? "selected" : '') +'><a href="#">GBP</a></option>';
							result = result + '  <option '+(json.expectedRateCurrencey == "SGD" ? "selected" : '') +'><a href="#">SGD</a></option>';
							result = result + '<option   '+(json.expectedRateCurrencey == "EUR" ? "selected" : '') +'><a href="#">EUR</a></option>'; 
			                result = result + ' </select>';
			                
			                result = result + '</div>';
							
							result = result + '<input type="text" class="form-control input-sm" name="expectedRate" id="expectedRate" value="'+expectedRate+'" placeholder="Expected Rate" field-name="Expected Rate" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true">';
							
							result = result + '<div class="input-group-btn tblminwidth-100 ratetype-right select-field">';
							result = result + '<select class="selct-picker tblminwidth-100" id="expectedRateType" name="rateType" field-name="Rate Per">';
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
							result = result + '<div class="col-md-6 custom-fields select-arrow chosen-select-anchor" id="errorvendor">';
							result = result + '<select  class="chosen-select form-control select-bg" name="vendors" data-validation-optional="true"  id="vendorName" value="'+vendorName+'" placeholder="select Vendor" field-name="Vendor" onchange="getVendorContactList();removeValiationVendor();">';
							result = result + '</select>';
							result = result + '<input type="hidden" name="updateVendorName" id="updateVendorName" value="'+vendorName+'" />';
							result = result + '<input type="hidden" name="updateVendorOrgRelationId" id="updateVendorOrgRelationId" value="'+json.vendorOrgRelationId+'" />';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow mt chosen-select-anchor" id="errorvendorContact">';
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
							result = result + '<div class="col-md-6">';
							
							result = result + '<label class="text-muted fontSmall">Source</label>';
							result = result + '<select class="form-control valid width100percnt" name="candidateSource" field-name="Source" id="candidateSource" data-validation="validate_Space" data-validation-optional="true">';
							var candidateSourceList = json.candidateSourceList;
							result = result + '<option value="select source" >--Select Source--</option>';
							for(var e in candidateSourceList) {
								
								result = result + '<option value="'+candidateSourceList[e].id+'" '+(candidateSourceList[e].id == json.candidateSource ? "selected" : " ")+'>'+ candidateSourceList[e].name +'</option>';
							}
							result = result + '</select>';
							
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
							
							if(json.noticePeriod == "Other"){
								$("#noticePeriodUpdateDiv").show();
						      }
							employementType();
							getVendorsByOrgLocation();
							$(".candidate-editdetails").addClass("candidate-editdetails-active");
// 							$(".collapse-div").removeClass("edit-details-active");
							
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
</script>

	
<script>
function preapareCustomFieldDetails(candidateId){
	$.ajax({
        type : "POST",
         url: atsUrl+"/getAllASectionCustomFields?&candidateId="+candidateId+"&requestFrom=candidate",        
         success :function(data) {
        	 $('#customFieldDataId').empty();
        	 $('#customFieldDataId').append(data);
           },
     }); 
}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token, candidateId
     3.ajax url implementation in CandidateListRestController.
--%>

<script>

function deleteCandidate(candidateId,candidateType){
	if ($('#terminate_candidate_form').validate(false, validationSettings)) {
		var reason = document.getElementById("rejectReason").value;
		if(reason.length > 250){
			$(".rejectErrorMsgs").append("<p class='jquery_form_error_message'>The values should be between 2-250 characters</p>");
		}else{
			$(".rejectErrorMsgs").find('p.jquery_form_error_message').remove();
			$.post(atsUrl+"/deleteCandidateByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&reason="+reason+"&candidateType="+candidateType,{},function(result){
				if (result && result.response === "INVALID_DATA") {
				    $("#update_candidate").removeAttr("disabled");
				    $("#update_candidate").removeAttr('button').val('Save');
				    showError(result.responseMessage);
				} else if (result.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					showSuccessStatusPopup("Candidate marked as teminated");
					var json = result.object;
					if(candidateType == 'Associates') {
						location.replace(atsUrl+"/associatesList");
					} else {
						location.replace(atsUrl+"/candidatesList");
					}
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    $("#update_candidate").removeAttr("disabled");
				    showError("Operation failed, please try again");
				}	
			});
		}
	
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#terminate_candidate_form', function() {
	$("#terminate_candidate_form").showHelpOnFocus();
	$("#terminate_candidate_form").validateOnBlur(false, validationSettings);
});


</script>
<script>
   function candiateRemoveSpacesForRejectReason() {
	    var output = document.getElementById("rejectReason").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#rejectReason").removeAttr("data-validation");
	    }
	}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate alert
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
$('#selector').delay(2000).fadeOut('slow');
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
function removeCandidateAlertMessage(candidateId,candidateType){
	$("#profileEditPopup").fadeOut();
	var result = "";
	result = result +"<form:form id='terminate_candidate_form' name='terminate_candidate_form' method='post'> ";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a' style='width:500px'>";
	result = result +"	<div class='clearfix'>";
	result = result +"		<textarea id='rejectReason' class='form-control mt mb5' field-name='Reject Reason'  data-validation='required validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2' onkeypress='candiateRemoveSpacesForRejectReason();'></textarea>";
	result = result +"	</div>";
	result = result +"	<div class='rejectErrorMsgs'/>";
	result = result +'		<b href="javascript:void(0)" class="mt10"><input style="width:50%" id="confirm-id" type="button" value="Ok" onclick="deleteCandidate('+candidateId+',\''+candidateType+'\');"><input style="width:50%" class="u_can_color" type="button"  value="Cancel" onClick="cancelAlert();"></b>';
	result = result +"	</div>";
	result = result +"</form:form>";
	
	$("#removeCandidateAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>


<script type="text/javascript">
function deleteCandidateAlertMessage(candidateId){
	$("#profileEditPopup").fadeOut();
	var result = "";
	result = result +"<form:form id='delete_candidate_form' name='delete_candidate_form' method='post'> ";
	result = result +"	<div id='deletePopupLoading'></div>";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a' style='width:500px'>";
	result = result +"	<p><i class='fa fa-exclamation-triangle text-danger' aria-hidden='true'></i>&nbsp;&nbsp;<i class='text-danger'><strong>The candidate will be removed permanently. Are you sure to delete?</strong></i></p>";
	result = result +"	<div class='clearfix'>";
	result = result +"		<textarea id='rejectReason' class='form-control mt mb5' field-name='Delete Reason'  data-validation='required validate_length length2-250' name='rejectReason' value='' placeholder='Delete Reason' rows='2' onkeypress='candiateRemoveSpacesForRejectReason();'></textarea>";
	result = result +"	</div>";
	result = result +"	<div class='rejectErrorMsgs'/>";
	result = result +'		<b href="javascript:void(0)" class="mt10"><input style="width:50%" id="candidatePermanentDelete" type="button" value="Ok" onclick="deleteCandidatePermanently('+candidateId+');"><input style="width:50%" class="u_can_color" type="button"  value="Cancel" onClick="cancelAlert();"></b>';
	result = result +"	</div>";
	result = result +"</form:form>";
	
	$("#removeCandidateAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>

function deleteCandidatePermanently(candidateId){
	if ($('#delete_candidate_form').validate(false, validationSettings)) {
		var reason = document.getElementById("rejectReason").value;
		if(reason.length > 250){
			$(".rejectErrorMsgs").append("<p class='jquery_form_error_message'>The values should be between 2-250 characters</p>");
		}else{
			$(".rejectErrorMsgs").find('p.jquery_form_error_message').remove();
			$("#candidatePermanentDelete").attr("disabled", "disabled");
			$("#candidatePermanentDelete").addClass('button').val('Processing..');
			 $.ajax({  
		         type : "POST",
		         url: atsUrl+"/deleteCandidatePermanentlyByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&reason="+reason,        
		         success :function(result) {
		        	 if (result.response === "SUCCESS") {
							showSuccessStatusPopup("Candidate deleted permanently");
							location.replace(atsUrl+"/candidatesList");
						}
		       },
		    });
			
		}
	
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#delete_candidate_form', function() {
	$("#delete_candidate_form").showHelpOnFocus();
	$("#delete_candidate_form").validateOnBlur(false, validationSettings);
});


</script>



<%-- 
     @author :Sammeta David Raju
    
     1.usage     : used in reActivateCandidateAlertMessage() / candidateProfile Line number 388
     2.parameters : candidateId
--%>

<script type="text/javascript">
function reActivateCandidateAlertMessage(candidateId){
	var result = "";
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	
		var reactivate = '1';
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(atsUrl+"/reActivateCandidateByCandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&reActivate="+reactivate,{},function(result){
			if (result && result.response === "INVALID_DATA") {
			    $("#update_candidate").removeAttr("disabled");
			    $("#update_candidate").removeAttr('button').val('Save');
			    showError(result.responseMessage);
			} else if (result.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				location.replace(atsUrl+"/candidatesList");
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			}			
		});
	
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
										   //result = result + ' <a href="javascript:void(0)" onClick="deleteCandidateAlertMessage('+candidateId+');"><i class="glyphicon glyphicon-trash"></i>Delete</a>';
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
								
// 								 $(".addUser-org").click(function(){
// 									 $(".edit-details").toggleClass("edit-details-active");
// 									 $(".collapse-div").removeClass("edit-details-active");
// 								 });
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
}
</script>

<script>
function saveCandidateTags(candidateId){
	 var tagName = $("#tagName").val();
	 var jsonData = encodeURIComponent(tagName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 if(tagName != null && tagName != '' && tagName !='undefined') {
		 Urlcheck = atsUrl+"/saveOrUpdateTagsBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&tagName="+jsonData
	 } else {
		 Urlcheck = atsUrl+"/saveOrUpdateTagsBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&tagName="+""
	 }
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
        	$("#loading").removeAttr('style');
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        		showError(result.responseMessage);
        	} else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		showSuccessStatusPopup("Tags details updated successfully");
        		var candidateType = "Candidates";
          	  	candidateProfile(candidateId,candidateType);
          	  	candidateQuickViewProfile(candidateId);
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
	function editSkill(candidateId){
		$("#skillName").prop('disabled', false).trigger("chosen:updated");
		$("#editSkillBtn").remove();
		$("#editSkillBtnSpan").hide();
		window.skillCountBefore = $("#skillName :selected").length;
		var result = '';
		result = result + '<button type="button" id="saveSkillBtn" name="saveSkillBtn" class="btn btn-primary btn-sm" onclick="saveSkill('+candidateId+');">Save Skill</button>';
		$("#saveSkillBtnSpan").empty();
		$("#saveSkillBtnSpan").append(result);
		$("#saveSkillBtnSpan").show();
		
		//Adding Customized Skills to the list this method is slead-common.js
		tagIt("skillName", 'Press Enter-Key Or Comma(,) To Add New Skill:', 'tagItBykeyForSkills','skills','');
		
	}

	function saveSkill(candidateId){
		$("#skillName").prop('disabled', true).trigger("chosen:updated");
		window.skillCountAfter = $("#skillName :selected").length;
		if(skillCountBefore != skillCountAfter){
			saveSkils(candidateId);
		}
		$("#saveSkillBtn").remove();
		$("#saveSkillBtnSpan").hide();
		var result = '';
		result = result + '<button type="button" id="editSkillBtn" name="editSkillBtn" class="btn btn-primary btn-sm" onclick="editSkill('+candidateId+');">Edit Skill</button>';
		$("#editSkillBtnSpan").empty();
		$("#editSkillBtnSpan").append(result);
		$("#editSkillBtnSpan").show();
		skillCountAfter = skillCountBefore = 0;
	}
</script>


<%-- 
     @author : sarath kumar.
     @author : venkat kesav (modified on 02/18/2017)
     this script is for save Skils in Candidate.
     1.usage     :  used in editCandidate() function  line 13
     2.parameter : csrf token ,candidateId.
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
 function saveSkils(candidateId){
	 var skillName = $("#skillName").val();
	 var jsonData = encodeURIComponent(skillName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 if(skillName != null && skillName != '' && skillName !='undefined') {
		 Urlcheck = atsUrl+"/saveOrUpdateOnlySkillBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&skillName="+jsonData
	 } else {
		 Urlcheck = atsUrl+"/saveOrUpdateOnlySkillBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&skillName="+""
	 }
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
        	$("#loading").removeAttr('style');
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        		showError(result.responseMessage);
        	} else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		showSuccessStatusPopup("Visa details updated successfully");
        		var candidateType = "Candidates";
          	  	candidateProfile(candidateId,candidateType);
          	  	candidateQuickViewProfile(candidateId);
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
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
		rateValidations();
		 $('#emailDiv').find('p.jquery_form_error_message').remove();
		 $('#errSsnNoDiv').find('p.jquery_form_error_message').remove();
		 if ($('#candidateProfileEdit_form').validate(false, validationSettings)){
      		var formData = getFormData("candidateProfileEdit_form");
			$("#update_candidate").attr("disabled", "disabled");
			$("#update_candidate").addClass('button').val('Processing..');
			$.post(atsUrl+"/updateCandidate?"+tokenParamName+"="+tokenValue+"", formData, function(result) {
				if (result && result.response === "INVALID_DATA") {
				    $("#update_candidate").removeAttr("disabled");
				    $("#update_candidate").removeAttr('button').val('Save');
				    showError(result.responseMessage);
				} else if (result.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
				    $("#selector11").removeAttr("style");
				    $("#selector11").css({ display: "block" });
				    $("#candMsgDivSum").text("Candidate Updated Successfully");
				    candUpdateMsgFadeinFunction();
				    candidateQuickViewProfile(candidateId);
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    $("#update_candidate").removeAttr("disabled");
				    showError("Operation failed, please try again");
				}
			});
			
			return true;
		 } else { 
			return false;
		 } 
	}
		 $('body').on('blur','#candidateProfileEdit_form',
					function() {
						$("#candidateProfileEdit_form").showHelpOnFocus();
						$("#candidateProfileEdit_form").validateOnBlur(false,validationSettings);
			});
	 
</script>

<script>
function cancelEdit(){
	$("#profileEditPopup").empty();
	$(".location-alert").fadeOut();
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
	  $.ajax({
        type : "POST",
         url: atsUrl+"/removeSkillBySkillId?"+tokenParamName+"="+tokenValue+"&skillId="+skillId,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != ""){
            			 candidateSkillsList(candidateId);
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
			var value = local.value;
			var name = local.id;
			$.ajax({
						type : "POST",
						url : atsUrl+"/fieldUpdateCandidate?"+tokenParamName+"="+tokenValue+"&candidateSkillId="+candidateSkillId+"&name="+name+"&value="+value,
						success : function(result) {
							 
						},
			}); 	 	
	} 
</script>

<script>
	function employeeSubmissionProfile(candidateId) {
				$.ajax({
					type : "POST",
					url : atsUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId=" + candidateId,
					success : function(data) {
						if (data && data.response === "INVALID_DATA") {
						    $("#update_candidate").removeAttr("disabled");
						    $("#update_candidate").removeAttr('button').val('Save');
						    showError(data.responseMessage);
						} else if (data.response === "AWKWARD") {
						    location.href = "/errorPage";
						}  else if (data.response === "SUCCESS") {
							$("#employeeSubmissionProfile").empty();
							var result = "";
							result = result +'        <div class="clearfix design-view">';
							result = result +'          <h2 class="education"><i class="fa fa-black-tie"></i>&nbsp;&nbsp;Employers</h2>';
							result = result +'          <div class="row">';
							result = result +'            <div class="col-md-12">';
							result = result +'              <ul class="list-unstyled personal-info">';
						//	var json = JSON.parse(data);
							for (var i = 0; i < data.data.length; i++) {
								var opt = data.data[i];
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
								
								
								
								if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
									startYear=   opt.startYear;
								}else{
									startYear = " ";
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
							    if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined' && opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined'){
							    	result = result + startYear +' To '+endYear; 
							    }else if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined'){
							    	result = result + startYear; 
							    }else if(opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined'){
							    	result = result + endYear; 
							    }
								result = result +'  </p>';
								result = result +'  </div>';
								result = result +'</li>';
							}
							result = result +'              </ul>';
							result = result +'            </div>';
							result = result +'          </div>';
							result = result +'        </div>';
							$("#employeeSubmissionProfile").empty(); 
							$("#employeeSubmissionProfile").append(result);
						} else if (data.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} 
					},
				});
	}
</script>

<script>
function educationSubmissionProfile(candidateId){
	 $.ajax({  
          type : "GET",
          url: atsUrl+"/getCandidateEducationListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#educationSubmissionProfile").empty();
               	 var result = "";
               	     result = result +'        <div class="clearfix design-view">';
						result = result +'          <h2 class="education"><i class="fa fa-graduation-cap"></i>&nbsp;&nbsp;Education</h2>';
						result = result +'          <div class="row">';
						result = result +'            <div class="col-md-12">';
						result = result +'              <ul class="list-unstyled personal-info">';
	                     for (var i = 0; i < response.data.length; i++) { 
	                    	 var opt = response.data[i];  
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
	                    		 EducationstartDateStr = opt.startDateStr;
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
	                    	    if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined' && opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
	                    	    result = result +'                    <p>'+EducationstartDateStr+' To '+EducationendDateStr+'</p>';
	                    	    }else if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
	                    	    	result = result +'                    <p>'+EducationstartDateStr+'</p>';
	                    	    }else if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
	                    	    	result = result +'                    <p>'+EducationendDateStr+'</p>';
	                    	    }
	                    	    result = result +'                  </div>';
								result = result +'                </li>';
	                     }
						result = result +'              </ul>';
						result = result +'            </div>';
						result = result +'          </div>';
						result = result +'        </div>';
					 $("#educationSubmissionProfile").empty(); 
                    $("#educationSubmissionProfile").append(result); 
        		} else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
        },
     }); 
}
</script> 

<script>
function certificationSubmissionProfile(candidateId) {
	
	 $.ajax({  
          type : "POST",
          url: atsUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(data) {
        	  if (data && data.response === "INVALID_DATA") {
      		    showError(data.responseMessage);
      		} else if (data && data.response === "INVALID_USER") {
      		    showError(data.responseMessage);
      		} else if (data.response === "AWKWARD") {
      		    location.href = "/errorPage";
				} else if (data.response === "SUCCESS") {
					$("#certificationSubmissionProfile").empty();
               	 	var result = "";
               	 	result = result +'<div class="clearfix design-view">';
					result = result +'<h2 class="education"><i class="fa fa-certificate"></i>&nbsp;&nbsp;Certification</h2>';
					result = result +'<div class="row">';
					result = result +'<div class="col-md-12">';
					result = result +'<ul class="list-unstyled personal-info">';
//						var json = JSON.parse(data);
					if(data.data.length > 0) {
						for (var i = 0; i < data.data.length; i++) {
							var opt = data.data[i]; 
		                    if(opt.score != null && opt.score != 'undefined' && opt.score != "") {
				            	score =' ( ' + opt.score;
				            } else {
				            	score = " ";
				            }
				            if(opt.scoreType != null && opt.scoreType != 'undefined' && opt.scoreType != "") {
				            	scoreType = opt.scoreType +' ) ';
				            } else {
				            	scoreType = " ";
				            }
				            if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "") {
				            	candidatevalidFromDateToString = opt.candidatevalidFromDateToString;
				            } else {
				            	candidatevalidFromDateToString = " ";
				            }
				            if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != "") {
				            	candidatevalidToDateToString = opt.candidatevalidToDateToString;
				            } else {
				            	candidatevalidToDateToString = " ";
				            }
				            	
							result = result +'<li>';
							result = result +'<div class="clearfix">';
							result = result +'<h3>'+opt.name+' <span class="small text-muted">&nbsp;'+score+'  '+scoreType+'</span></h3>';
							result = result +'<p>'+opt.provider+' </p>';
							if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "" && 
									opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != "") {
									result = result +'<p> '+candidatevalidFromDateToString+' TO '+candidatevalidToDateToString+'</p>';
							} else if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "") {
								result = result +'<p> '+candidatevalidFromDateToString+'</p>';
							} else if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != "") {
								result = result +'<p> '+candidatevalidToDateToString+'</p>';
							}
							result = result +'</div>';
							result = result +'</li>';
						}
					}
					result = result +'</ul>';
					result = result +'</div>';
					result = result +'</div>';
					result = result +'</div>';
						
					$("#certificationSubmissionProfile").empty();
					$("#certificationSubmissionProfile").append(result); 
				} else if (data.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} 
        },
     }); 
}
</script>
<script>
$('.selectpicker').selectpicker('refresh');
</script>    
     
<script>
 function disableMonths() {
	
         if ($("#experienceYears").val() == "50"){
        	 $("#experienceMonths").val('');  
        	 $('#experienceMonths').attr("disabled", false);
         }else{
        	 $("#experienceMonths").val('');  
        	 $('#experienceMonths').selectpicker('refresh');
        	 $("#experienceMonths").attr("disabled", false); 
		 }    
 }
</script>
  
<!-- Employment Type equals Other then Text Box will Open script here -->
<script>
function updateDesignationOtherType(){
	 var candidateType = $('#designationType1').val(); 
	if(candidateType == 'Other' &&  candidateType != '' && candidateType !='undefined'){
		  document.getElementById("designationTypeDiv").style.display="block";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationOtherType").attr("data-validation", "validate_Space validate_length length2-250");
	}else{
		   document.getElementById("designationTypeDiv").style.display="none";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationTypeDiv").find('p.jquery_form_error_message').remove();
	} 
}
</script>

<!-- Notice Period Type Other Script written By Prasad K-->

<script>
function updateOtherNoticePeriod(){
	 var noticePeriod = $('#noticePeriodEdit').val(); 
	 var editVal = $('#otherNoticePeriodEdit').val();
	/* if(editVal != null && editVal != '' && editVal != 'undefined'){
		 document.getElementById("noticePeriodUpdateDiv").style.display="block";
		  $("#otherNoticePeriodEdit").attr("data-validation-optional", "true");
		  $("#otherNoticePeriodEdit").attr("data-validation", "validate_Space validate_length length1-150");
	} */
	if(noticePeriod == 'Other' &&  noticePeriod != '' && noticePeriod !='undefined'){
		  document.getElementById("noticePeriodUpdateDiv").style.display="block";
		  $('#otherNoticePeriodEdit').removeAttr('data-validation-optional');
          $("#otherNoticePeriodEdit").attr("data-validation", "validate_Space required validate_length length1-150");
	}else{
		   document.getElementById("noticePeriodUpdateDiv").style.display="none";
		  $("#otherNoticePeriodEdit").attr("data-validation-optional", "true");
		  $("#noticePeriodUpdateDiv").find('p.jquery_form_error_message').remove();
	} 
}
</script>



<!-- Candidate Email validation script starts here -->

<script type="text/javascript">
 function validateCandidateEmail() {
	 var email = $('#email').val(); 
	 var tempEmail = $('#tempEmail').val(); 
	 
	 if(tempEmail != email){
	 
	 var candidateType = $('#candidateType').val(); 
	 $('#emailDiv').find('p.jquery_form_error_message').remove();
	 $("#update_candidate").removeAttr("disabled");
	 if(email != null && email != ""){
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/validateCandidateEmail?"+tokenParamName+"="+tokenValue+"&email="+email+"&candidateType="+candidateType, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		if(response.object == 'true'){
			        		  $('#candidateType').val(); 
							  $("input#email").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
							  $("#update_candidate").attr("disabled", "disabled");
							  $("#update_candidate").addClass('button').val('Processing..');
			        	}
			        	else{
			        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			        		 $("#update_candidate" ).addClass('button').val('Save');
	                         $("#update_candidate").removeAttr("disabled");
	                         validateEmailForExistenceUniversally(email, "input#email");
			        	}
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	 }else{
		 $("#emailStatusIconDiv").find("i").remove();
   		 $("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>'); 
	 }
	 }else{
		 $('#update_candidate').removeAttr('disabled');
	 }
 }
 </script>     
 
 
 <!-- Candidate Email validation script ends here -->
 
 
<!-- This script for Current Rate ,Rate Type,Expected Rate, Expected Rate Type validation checks -->
<script type="text/javascript">

function rateValidations(){
	
var rateType = document.getElementById("expectedRateType").value; 
	if(rateType != null && rateType != ""){
		 $('#expectedRate').removeAttr('data-validation-optional');
		 $('#expectedRate').attr("data-validation","required validate_Space validate_int validate_length length1-8"); 
	}else{
		 $('#expectedRate').removeAttr('data-validation');
		 $('#expectedRate').attr("data-validation-optional","true");
		 $('#expectedRateType').removeAttr('data-validation');
		 $('#expectedRateType').attr("data-validation-optional","true"); 
	}

var expectedRate = document.getElementById("expectedRate").value; 
	if(expectedRate != null && expectedRate != ""){
		 $('#expectedRateType').removeAttr('data-validation-optional');
		 $('#expectedRateType').attr("data-validation","required"); 
		 $('#expectedRate').attr("data-validation", "required validate_Space validate_int validate_length length1-8");
	}else{
		$('#expectedRateType').removeAttr('data-validation');
		$('#expectedRateType').attr("data-validation-optional","true"); 
	}

var currentRateType = document.getElementById("currentRateType").value; 
	if(currentRateType != null && currentRateType != ""){
		$('#currentRate').removeAttr('data-validation-optional');
		$('#currentRate').attr("data-validation","required validate_Space validate_int validate_length length1-8"); 
	}else{
		$('#currentRate').removeAttr('data-validation');
	 	$('#currentRate').attr("data-validation-optional","true"); 
		$('#currentRateType').removeAttr('data-validation');
	 	$('#currentRateType').attr("data-validation-optional","true"); 
	}
	
var currentRate = document.getElementById("currentRate").value; 
	 if(currentRate != null && currentRate != ""){
		 $('#currentRateType').removeAttr('data-validation-optional');
		 $('#currentRateType').attr("data-validation","required"); 
		 $('#currentRate').attr("data-validation", "required validate_Space validate_int validate_length length1-8");
	 }else{
		 $('#currentRateType').removeAttr('data-validation');
		 $('#currentRateType').attr("data-validation-optional","true"); 
	 }
}
</script>


<script>
function employementType(){
	 var employementType = $('#designationType1').val(); 
	 var preval = document.getElementById("hiddenId").value;
		if(employementType == 'C2C' ||  employementType == 'Offshore C2C' ){
			/* document.getElementById("hideVendor").style.display="block";
			 $('#vendorName').attr("data-validation","required"); 
			 $('#vendorcontactName').attr("data-validation","required"); 
			 getVendorsByOrgLocation(); */
			 if(employementType == preval){
					document.getElementById("hideVendor").style.display="block";
					 getVendorsByOrgLocation();
				}else{
					document.getElementById("hideVendor").style.display="block";
				  $('#vendorName').val(''); 
				 
		            $('#vendorName').trigger("chosen:updated");
		            $('#vendorcontactName').empty();
			//	 $('#vendorcontact').val(''); 
		           $('#vendorcontactName').trigger("chosen:updated");
				} 
			 
		}else{
			 document.getElementById("hideVendor").style.display="none";
			 
			 $('#vendorName').val(''); 
			 $('#vendorcontactName').val(''); 
			 $('#vendorName').trigger("chosen:updated");
	            $('#vendorcontactName').empty();
		//	 $('#vendorcontact').val(''); 
	           $('#vendorcontactName').trigger("chosen:updated");
			 
		}

		if(employementType == 'C2C-1099'){
			if(employementType == preval){
				document.getElementById("hideVendor").style.display="block";
				 getVendorsByOrgLocation();
			}else{
				document.getElementById("hideVendor").style.display="block";
			    $('#vendorName').val(''); 
			 
	            $('#vendorName').trigger("chosen:updated");
	            $('#vendorcontactName').empty();
	            $('#vendorcontactName').trigger("chosen:updated");
			} 
		}else{
			 
			 $('#vendorName').val(''); 
			 $('#vendorcontactName').val(''); 
			 
			 $('#vendorName').trigger("chosen:updated");
	            $('#vendorcontactName').empty();
	           $('#vendorcontactName').trigger("chosen:updated");
			 
		} 
		$('#designationType1 + p').remove();
}
</script>


<script>
 function getVendorsByOrgLocation(){
	 var vendorName = $("#updateVendorName").val();
	 var vendorOrgRelationId = $("#updateVendorOrgRelationId").val();
		 $.ajax({
		    	type: "GET",
		        url: atsUrl+"/getVendorsByOrgLocation?", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#vendorName').empty();
		        	$('#vendorName').append('<option value="">--select Vendor --</option>');
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            	  if(vendorName != null && vendorName != 'undefined' && vendorName  != ''){
		            		  $('#vendorName').append('<option '+ (opt.id == vendorOrgRelationId ? "selected" : '') +' value="'+opt.id+'">'+opt.orgName+'</option>');
			            	}else{
			            		$('#vendorName').append('<option value='+opt.id+' >'+opt.orgName+'</option>');	
			            	}
		            	  
		            }
		            $('#vendorName').trigger("chosen:updated");
		            getVendorContactList();
		            
		        }
		 });
 }
 </script>
 <script>
 function  removeValiationVendor() {
	$("#errorvendor").find("p.jquery_form_error_message").remove();
}
</script>
<script>
function  removeValiationVendorContact() {
	 $("#errorvendorContact").find("p.jquery_form_error_message").remove();
}
</script>

 <script>
 function getVendorContactList(){
	$('#vendorcontactName').val('').trigger("chosen:updated");
		 var orgRelationID = $('#vendorName').val();
		 if(orgRelationID != null && orgRelationID != '' && orgRelationID != 'undefined'){
			 var vendorContactName = $("#updateVendorContactName").val();
			 var vendorSpocId = $("#updateSpocId").val();
			 $.ajax({
				    type: "POST",
			        url: vmsUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
			        success: function(json) {
			        	//var json = JSON.parse(data);
			        	$('#vendorcontactName').empty();
			        	$('#vendorcontactName').append('<option value="">--select Vendor contact--</option>');
			             for(var i=0;i<json.data.length;i++){
			            	 var opt = json.data[i];
			            	 if(vendorContactName != null && vendorContactName != 'undefined' && vendorContactName  != ''){
			            		 $('#vendorcontactName').append('<option '+ (opt.id == vendorSpocId ? "selected" : '') +' value="'+opt.id+'">'+opt.spocName+' ('+opt.ownerName+')</option>');
				            	}else{
				            		$('#vendorcontactName').append('<option value='+opt.id+' >'+opt.spocName+' ('+opt.ownerName+')</option>');
				            	}
			            } 
			            $('#vendorcontactName').trigger("chosen:updated");
			        }
			 });  
		 }
		 
 }
 </script>
 
 <!-- Visa Type Script View/edit script start here -->
 <script>
	function editVisa(candidateId){
		$("#visaName").prop('disabled', false).trigger("chosen:updated");
		$("#editVisaBtn").remove();
		$("#editVisaBtnSpan").hide();
// 		window.visaCountBefore = $("#visaName :selected").length;
		var result = '';
		result = result + '<button type="button" id="saveVisaBtn" name="saveVisaBtn" class="btn btn-primary btn-sm" onclick="saveVisa('+candidateId+');">Save Visa</button>';
		$("#saveVisaBtnSpan").empty();
		$("#saveVisaBtnSpan").append(result);
		$("#saveVisaBtnSpan").show();
	    
	}

	function saveVisa(candidateId){
		$("#visaName").prop('disabled', true).trigger("chosen:updated");
// 		window.visaCountAfter = $("#visaName :selected").length;
// 		if(visaCountBefore != visaCountAfter){
		saveVisas(candidateId);
// 		}
		$("#saveVisaBtn").remove();
		$("#saveVisaBtnSpan").hide();
		var result = '';
		result = result + '<button type="button" id="editVisaBtn" name="editVisaBtn" class="btn btn-primary btn-sm" onclick="editVisa('+candidateId+');">Edit Visa</button>';
		$("#editVisaBtnSpan").empty();
		$("#editVisaBtnSpan").append(result);
		$("#editVisaBtnSpan").show();
		visaCountAfter = visaCountBefore = 0;
	}
</script>



<script>  
 function saveVisas(candidateId) {
	 var visaName = $("#visaName").val();
	 var jsonData = encodeURIComponent(visaName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 if(visaName != null && visaName != '' && visaName !='undefined'){
		 Urlcheck = atsUrl+"/saveOrUpdateOnlyVisaBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&visaName="+jsonData
	 }else{
		 Urlcheck = atsUrl+"/saveOrUpdateOnlyVisaBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&visaName="+""
	 }
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
            	  	$("#loading").removeAttr('style');
      				if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
              		showError(result.responseMessage);
              		} else if (result.response === "AWKWARD") {
              	    	location.href = "/errorPage";
              		} else if (result.response === "SUCCESS") {
              			showSuccessStatusPopup("Visa details updated successfully");
              			var candidateType = "Candidates";
              	  	  	candidateProfile(candidateId,candidateType);
               	  	//candidateQuickViewProfile(candidateId);
              		} else if (result.indexOf('Sign In') !== -1) {
	              		location.replace(sourceleadUrl);
	              	} else {
	              	    showError("Operation failed, please try again");
	              	}            	  
       },
    }); 

	 
 }  
 
 function cleardateofBirth(){
		$("#dateofbirth-candidate").val("");
		};
		
</script>

<script type="text/javascript">
	function genderCheckValidation(id){
		 var gendersList = [];
 		 $("input[name='gender']:checked").each( function (i) {
 			gendersList[i] = $(this).val();
 		});
 		 
		if (gendersList.length > 0) { 
		document.getElementById("genderValue").value = id.value;
		}else{
			$('#genderValue').val(''); 
		}
	} 
	
	
function showSuccessStatusPopup(status) {
	$("#statusAlert").css("display","block");
	$("#statusAlert span").html(status);
/* 	setTimeout(function(){
		$("#statusAlert").css("display","none");				 			
	},2000); */
    $('#statusAlert').delay(2000).fadeOut('slow');
}	
</script>


 <script type="text/javascript">
function validatePanNumber() {
    var panNumber = document.getElementById("panNumber").value;
    var tempPanNumber = $('#tempPanNumber').val(); 
	 if(tempPanNumber != panNumber){
		 $('#errPanNoDiv').find('p.jquery_form_error_message').remove();
		 $("#update_candidate").removeAttr("disabled");
	if(panNumber != "" && panNumber != null && panNumber != "undefined"){
	 
		 $.ajax({
		    	type: "GET",
		    	 url:atsUrl+"/validatePanNumber?"+tokenParamName+"="+tokenValue+"&panNumber="+panNumber, 
		        success: function(data) {
		        	if(data == true){
		        		  $('#errPanNoDiv').find('p.jquery_form_error_message').remove();
		        		  $("input#panNumber").after("<p class='jquery_form_error_message'>"+panNumber+" is Already Existed</span>");
						  $("#update_candidate").attr('disabled','disabled');
		        	}
		        	else{
		        		 $('input#panNumber').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#errPanNoDiv').find('p.jquery_form_error_message').remove(); 
                        $("#update_candidate").removeAttr("disabled");
		        	}
		        }
		    });
     }
}
};
</script>

<script type="text/javascript">
function validateAadharNumber() {
	
	  var aadharNumber = document.getElementById("aadharNumber").value;
	  
	  var tempAadharNumber = $('#tempAadharNumber').val(); 
	if(tempAadharNumber != aadharNumber){
			 $('#errAadharNoDiv').find('p.jquery_form_error_message').remove();
			 $("#update_candidate").removeAttr("disabled");
	 
	  if(aadharNumber != "" && aadharNumber != null && aadharNumber != "undefined"){
		  
		 $.ajax({
		    	type: "GET",
		    	 url:atsUrl+"/validateAadharNumber?"+tokenParamName+"="+tokenValue+"&aadharNumber="+aadharNumber, 
		        success: function(data) {
		        	if(data == true){
		        		  $('#errAadharNoDiv').find('p.jquery_form_error_message').remove();
		        		  $("input#aadharNumber").after("<p class='jquery_form_error_message'>"+aadharNumber+" is Already Existed</span>");
						  $("#update_candidate").attr('disabled','disabled');
		        	}
		        	else{
		        		 $('input#aadharNumber').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#errAadharNoDiv').find('p.jquery_form_error_message').remove(); 
                        $("#update_candidate").removeAttr("disabled");
		        	}
		        }
		    });
	  }
	  
	}
};
</script>

<script type="text/javascript">
function validateSsnNumberDuplicateChek() {
	
	  var ssnNumber = document.getElementById("ssnNumberUs").value;
	  
	  var tempSsnNumber = $('#tempSsnNumber').val(); 
		if(tempSsnNumber != ssnNumber){
			 $('#errSsnNoDiv').find('p.jquery_form_error_message').remove();
			 $("#update_candidate").removeAttr("disabled");
	  
	  if(ssnNumber != "" && ssnNumber != null && ssnNumber != "undefined"){
		 $.ajax({
		    	type: "GET",
		    	 url:atsUrl+"/validateSsnNumber?"+tokenParamName+"="+tokenValue+"&ssnNumber="+ssnNumber, 
		        success: function(data) {
		        	if(data == true){
		        		  $('#errSsnNoDiv').find('p.jquery_form_error_message').remove();
		        		  $("input#ssnNumberUs").after("<p class='jquery_form_error_message'>"+ssnNumber+" is Already Existed</span>");
						  $("#update_candidate").attr('disabled','disabled');
		        	}
		        	else{
		        		 $('input#ssnNumberUs').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#errSsnNoDiv').find('p.jquery_form_error_message').remove(); 
                        $("#update_candidate").removeAttr("disabled");
		        	}
		        }
		    });
	  }
	}
};
</script>

<script type="text/javascript">
$('#ssnNumberUs').mask('000-00-0000', {
    translation: {
        '#': {
            pattern: "/[0-9]{9}/", optional: true
        }
    },
   placeholder: ""
});
</script>
