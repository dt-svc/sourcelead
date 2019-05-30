<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
 
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="/resources/css/candidate-common.css?staticTime=${staticTime}" rel="stylesheet">
<%-- 
     @author : David Raju.
     this script is for edit Candidate.
     1.usage     :  used in professionalProfile.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>

<c:if test="${!empty msg}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>

<script>
function editCandidate(candidateId){
	  $("#profileEditPopup").show();
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
         success :function(result) {  
      	   		if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					var json = result.object;
              		if(json != null ){  
              			
              			var loginUserRole  = '${SourceLeadWeb_loginUserRole}'
            			 var readonly = '';	
	              			if(loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'){
	              				readonly = 'readonly';
	              			}
              			
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
            			 	var primarySkill = '';
            			 	if(json.primarySkill != null && json.primarySkill != '' && json.primarySkill != 'undefined'){
            			 		primarySkill = json.primarySkill;            			 		
            			 	}
            			 	var summary = '';
            			 	if(json.summary != null && json.summary != '' && json.summary != 'undefined'){
            			 		summary = json.summary;            			 		
            			 	}
            			 	
            			 	var location = '';
            			 	if(json.location != null && json.location != '' && json.location != 'undefined'){
            			 		location = json.location;            			 		
            			 	}
            			 	
            				var zipCode = '';
            			 	if(json.zipcode != null && json.zipcode != '' && json.zipcode != 'undefined'){
            			 		zipCode = json.zipcode;            			 		
            			 	}
            			 	
            			 	var state = '';
            			 	if(json.state != null && json.state != '' && json.state != 'undefined'){
            			 		state = json.state;            			 		
            			 	}
            			 	
            			 	var city = '';
            			 	if(json.city != null && json.city != '' && json.city != 'undefined'){
            			 		city = json.city;            			 		
            			 	}
            				var address = '';
            			 	if(json.address != null && json.address != '' && json.address != 'undefined'){
            			 		address = json.address;            			 		
            			 	}
            			 	
            			 	
							var result ='';
							result  = result + '<div class="success_pop" id="selector1" style="display: none;">';
						    result  = result + '<i class="fa fa-check-circle"></i><span id="candMsgDiv"></span>';
						    result  = result + '</div>';
							result = result + '		    <div class="edit-details col-md-8 edit-candaite"> <i class="fa fa-close close-div" onclick="cancelEdit();"></i>';
							result  = result + '         <form id="candidateProfileEdit_form" name="candidateProfileEdit_form" method="post">';
							result  = result + '<input type="hidden" id="orgloationId" name="orgloationId" value="${SourceLeadWeb_orgLocations.id}" />';
							result  = result + '<input type="hidden" id="loginUserId" name="loginUserId" value="${SourceLeadWeb_user.id}" />';
							result = result + '		      <div class="form-horizontal clearfix">';
							result = result + '<input type="hidden" class="form-control input-sm" name="candidateId"  value="'+candidateId+'">';
							
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-4">';
							result = result + '		            <input type="text" class="form-control input-sm" name="firstName" value="'+json.firstName+'" placeholder="First Name *" field-name="First Name"  data-validation="validate_AlphaNumber required validate_Space validate_length length1-100">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-4">';
							result = result + '		            <input type="text" class="form-control input-sm" name="lastName" value="'+lastName+'" placeholder="Last Name" data-validation="validate_AlphaNumber validate_Space validate_length length1-100" data-validation-optional="true">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-4">';
							result = result + '		            <input type="text" class="form-control input-sm" name="middleName" value="'+middleName+'" placeholder="Middle Name" data-validation="validate_AlphaNumber validate_Space validate_length length1-100" data-validation-optional="true">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							
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
								result = result+'		<label class="paddingLeft"><input type="radio"   value="Male" name="gender"><span class="mr5"></span>Male</label>                   ';
								result = result+'		<label class="paddingLeft paddingRight"><input type="radio"  value="Female" name="gender"><span class="mr5"></span>Female</label>   ';
								result = result+'		<label class="paddingRight"><input type="radio" value="Transgender" name="gender"><span class="mr5"></span>Transgender</label>     ';
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
								result = result + ' <input type="text" value="'+json.dateofBirth+'" name="dateofBirth" id="dateofbirth-candidate" placeholder="DateOfBirth" readonly="readonly" class="form-control calendar">';
								result = result + '   <span class="input-group-addon" onClick="cleardateofBirth();"><i class="colorRed fa fa-times cursorPointer"></i></span> ';

							}else{
								result = result + ' <input type="text" value="" name="dateofBirth" id="dateofbirth-candidate" readonly="readonly" placeholder="DateOfBirth" class="form-control calendar">';
								result = result + '   <span class="input-group-addon" onClick="cleardateofBirth();"><i class="colorRed fa fa-times cursorPointer"></i></span> ';

							}
							result = result + ' </div></div>';
							result = result + '<div class="clearfix"></div>';
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6">';
						    result = result + '<input type="text" class="form-control input-sm" name="email" value="'+json.email+'" placeholder="Email*" field-name="Email"  readonly data-validation="validate_Space required validate_email validate_length length2-100" >';
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
							result = result + '<input type="text" class="form-control input-sm" name="designation"  '+readonly+' value="'+designation+'" field-name="Designation"  data-validation="validate_Space validate_Name validate_length length2-250" data-validation-optional="true" placeholder="Designation">';
							result = result + '</div>';
							
							result = result + '</div>';
							
							
							result = result + '<div class="clearfix mb10">';
							 
							
							result = result + '<div class="col-md-6" id="locationField">';
							result = result + '<input type="text" class="form-control input-sm" name="location"  id="pfLocation"  value="'+location+'" placeholder="Enter Address" field-name="Address" data-validation="required">';
							result = result + '<i class="fa fa-refresh btn btn-primary profilerefresh-icon" title="Refresh" onclick="refreshGoogleApiLocation(\'pfLocation\',\'pfCountry\',\'pfState\',\'pfCity\',\'pfZipCode\',\'pfAddress\');"></i>';
							result = result + '</div>'; 
							
							result = result + '<div class="col-md-6 custom-fields mb10">';
							result = result + '<input class="form-control input-sm" id="pfCountry" name="country" value="'+json.country+'" field-name="Country" data-validation="required"  placeholder="Enter Country" type="text"  ></input>';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow">';
							result = result + '<input class="form-control input-sm" id="pfState" name="state" value="'+state+'" placeholder="Enter State" type="text"  ></input>';
							result = result + '</div>';
							
							result = result + '<div class="clearfix mb10">';
							result = result + '<div class="col-md-6 custom-fields">';
							result = result + '<input class="form-control input-sm" id="pfCity" name="city" value="'+city+'" placeholder="Enter City" type="text"  ></input>';
							result = result + '</div>';
							
							result = result + '<div class="col-md-6 custom-fields mt10">';
							result = result + '<input class="form-control input-sm" id="pfZipCode" name="zipcode" value="'+zipCode+'" placeholder="Enter Zip code" type="text"></input>';
							result = result + '</div>';
							

							result = result + '<div class="col-md-6 custom-fields mt10"">';
							result = result + '<input type="text" class="form-control input-sm" name="address" id="pfAddress" value="'+address+'" field-name="Street Address" placeholder="Enter Street Addres" data-validation="validate_Space validate_length length2-250" data-validation-optional="true">';
							result = result + '</div>';
							
							result = result + '</div>';
							result = result + '</div>';
							
							result = result + '<div class="clearfix mb10">';
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
							
							result = result + '<div class="clearfix mb10 mt">';
							result = result + '<div class="col-md-6">';
							result = result + '<div class="row">';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow select-field">';
							
							result = result + '<select  class="input-sm form-control input-sm" name="experienceYears"  id="experienceYears" field-name="Experience Years" onchange="javascript:disableMonths();">';
							result = result + '<option value="">Select Years</option>';
							
							<c:forEach var="i" begin="0" end="24" >
								result = result  +'<option value="${i}" '  + (json.experienceYears == "${i}" ? "selected" : '') +  '>${i}</option>';
							</c:forEach>
							
							if(json.experienceYears == "25+"){
								result = result  +'<option value="25+" '+ (json.experienceYears == "25+" ? "selected" : '') +'>25+</option>';
							}else{
								result = result  +'<option>25+</option>';
							}
							
							result = result + '</select>';
							result = result + '</div>';
							
							
							
							
							result = result + '<div class="col-md-6 custom-fields select-arrow select-field">';
							if(json.experienceYears == "25+"){
							result = result + '<select  class="input-sm form-control" name="experienceMonths"  id="experienceMonths" field-name="experienceMonths" disabled="disabled">';
							result = result + '<option  value="">Select Months</option>';
							
							<c:forEach var="i" begin="0" end="11" >
							 	result = result  +'<option  value="${i}" '  + (json.experienceMonths == "${i}" ? "selected" : '') +  '>${i}</option>';
						    </c:forEach> 
							
							
							result = result + '</select>';
							}else{
								result = result + '<select  class="input-sm form-control" name="experienceMonths"  id="experienceMonths" field-name="experienceMonths">';
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
							result = result + '		            <input type="text" class="form-control input-sm" name="primarySkill" value="'+primarySkill+'" placeholder="Primary Skill" field-name="Primary Skill" data-validation="validate_Space validate_length length2-200" data-validation-optional="true">';
							result = result + '		          </div>';
							
							result = result + '<div class="clearfix mb10">';
							
							result = result + '<div class="col-md-6 custom-fields select-arrow select-field mt10">';
							result = result + '<select class="input-sm form-control" id="noticePeriod" name="noticePeriod" field-name="Notice Period">';
							result = result + '<option value="">-- Select Notice Period --</option>';
							result = result + ' <option '+(json.noticePeriod == "Immediately available" ? "selected" : '') +'>Immediately Available</option>';
							result = result + '<option  '+ (json.noticePeriod == "15 Days" ? "selected" : '') +'>15 Days</option>';
							result = result + '<option  '+(json.noticePeriod == "1 Month" ? "selected" : '') +'>1 Month</option>';
							result = result + '<option  '+(json.noticePeriod == "2 Months" ? "selected" : '') +'>2 Months</option>';
							result = result + '<option  '+(json.noticePeriod == "3 Months" ? "selected" : '') +'>3 Months</option>';
							result = result + ' </select>';
							result = result + ' </div>';
							
                            result = result + '<div class="col-md-6 custom-fields mt10">';
							result = result + '<div class="input-group-btn tblminwidth-75 ratetype select-field">';
							result = result + '<select class="form-control input-sm" id="designationType" name="designationType" field-name="Designation Type" onchange="updateDesignationType(this);">';
							result = result + '<option value="">-- Select Employement Type --</option>';
							result = result + ' <option '+(json.designationType == "C2C" ? "selected" : '') +'>C2C</option>';
							result = result + '<option  '+ (json.designationType == "W-2" ? "selected" : '') +'>W-2</option>';
							result = result + '<option  '+ (json.designationType == "C2H" ? "selected" : '') +'>C2H</option>';
							result = result + '<option  '+(json.designationType == "Part Time" ? "selected" : '') +'>Part Time</option>';
							result = result + '<option  '+(json.designationType == "Full Time" ? "selected" : '') +'>Full Time</option>';
							result = result + '<option  '+(json.designationType == "Other" ? "selected" : '') +'>Other</option>';
							result = result + ' </select>';
							result = result + '</div>';
							
							result = result + '</div>';
							 
							result = result + '<div class="col-md-6 designationTypeDiv mt10" id="designationTypeDiv" style="display:none">';
							result = result + '<input type="text" class="form-control input-sm" name="designationOtherType"  id="designationOtherType" value="'+designationOtherType+'" placeholder="Employement Other Type" field-name="Designation Other Type" data-validation="validate_Space validate_length length2-50" data-validation-optional="true">';
							result = result + '</div>';
						
							result = result + '</div>';
							result = result + '<div class="col-md-6">';
							result = result + '<textarea placeholder="Summary" class="form-control input-sm"  name="summary" rows="2" cols="5" field-name="Summary" data-validation=" validate_length length2-5000" data-validation-optional="true">'+summary+'</textarea>';
							result = result + ' </div>';
							result = result + '</div>';
							result = result + '</div>';
							
							
							result = result + '<div class="clearfix mr">';
							result = result + '<div class="pull-right">';
							result = result + '<button type="button" id="update_candidate" onClick ="upDateCandidate('+candidateId+');" class="btn btn-sm btn-primary mr">Save</button>';
							result = result + '<button type="button" class="btn btn-sm btn-danger" onClick ="cancelEdit();">Cancel</button>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		      </div>';
							result  = result + '              </form>';
							result = result + '		    </div>';
							result = result + '		  </div>';
							
							
							$("#profileEditPopup").empty(); 
							$("#profileEditPopup").append(result); 
							initLocationAuto('pfLocation','pfCountry','pfState','pfCity','pfZipCode','pfLatitude','pfLongitude','','pfAddress');
							
							updateDesignationType();
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
							  
							    $('.chosen-select').each(function() {
										var $this = $(this);
										$this.next().css({'width': '100%'});
								  })
								  
								  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
								  // written common method in slead-common.js for date formats start
										var finalDateFormat = getJSDateFormat(locationDate); 
									  $("#dateofbirth-candidate").datepicker({
								      dateFormat: finalDateFormat,
									      changeMonth: true,
										  	  changeYear: true, 
									      //minDate: 0  this line for to restrict selection of previous dates
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
function professionalProfile(candidateId){
	  var skillsList = '${skillsList}'; 
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getCandidateById?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
         success :function(result) { 
      	   	 if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					 var json = result.object;
              		if(json != null ){  
							var result ="";
							var checked = "";
							if(json.isProfilePublic == 'Y'){
    							checked = "checked"+" "+"value = 'Y'";
    						}else{
    							checked = "value = 'N'";
    						}
							
							result = result+'<div id="removeCandidateAlert" ></div>  ';
							result = result+'<div id="choicepopUp"></div>';
							result = result+'<div id="loading"></div>';
						    result = result + '<div class="project_tabs_inn_head mt10">'; 
						    
						    
						    result = result + '<div class="anchor-inline clearfix">';
						    
						    result = result + '<div class="col-md-4 pull-right">';
						    result = result + '<div class="toggle-button pull-left">';
						    result = result + '<span class="pull-left paddingRight">Public Visibility</span>';
						    result = result + '<label class="switch">';
						    result = result + '<input type="checkbox" id="isProfilePublic" '+checked+' onclick="makeProfileAsPublic();">';
						    result = result + '<div class="slider round"></div>';
						    result = result + '</label>';
						    result = result + '</div>';
						    
						    
						    result = result + '<a href="javascript:void(0)" onClick="editCandidate('+candidateId+');" onChange="updateDesignationType(this);" class="mr addUser-org"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';  
						    result = result + '</div>';
						    result = result + '</div>';

						    result = result + '<div id="profileEditPopup"></div>';
							result = result +'<div class="require_wrap_details candidate_wrap_details">';	 
							result = result +'		<h1>Summary:</h1>';
							if(json.summary != null && json.summary != '' && json.summary != 'undifined'){
							result = result +'	    <textarea placeholder="Enter description" readonly>'+json.summary+'</textarea>';
							}
							else{
					           	 result = result +'   <textarea placeholder="Enter description" readonly> --- </textarea>';
			    	        }
							
							result = result +'		<div class="clearfix dashboard-widget candidate-profile mt">';
							result = result +'		  <ul class="nav nav-tabs">                                                                                                                   ';
							result = result +'		    <li class="active"><a data-toggle="tab" href="#skills">General View</a></li>                                                              ';
							result = result +'		  </ul>                                                                                                                                       ';
							result = result +'		  <div class="tab-content">                                                                                                                   ';
							result = result +'		    <div id="skills" class="tab-pane fade in active">                                                                                         ';
							result = result +'		      <div class="skills-summary positionRelative">                                                                                                            ';
							result = result +'		        <div class="design-view skillsresult">                                                                                                             ';
							
							result = result +'		          <!-- Select Skills -->                                                                                                              ';
							
							result = result +'		<div class="clearfix">';
							result = result +'		          <h2 class="education  pull-left">Skills:</h2>';
							result = result +'		          <span id="editSkillBtnSpan" class="pull-right mt10 mb"><button type="button" id="editSkillBtn" name="editSkillBtn" class="btn btn-primary btn-sm" onclick="editSkill('+candidateId+');">Edit Skill</button></span>';
							result = result +'		          <span id="saveSkillBtnSpan" class="pull-right mt10 mb" style.display = "none";></span>';
							result = result +'		</div>';
							
							result = result + '<div class="clearfix"></div> ';
							
							result = result +'<div class="clearfix">';
							
							
							result = result + '<select multiple class="chosen-select form-control" name="skillName" id="skillName" data-validation="validate_Space" data-validation-optional="true">';
							$.each( json.allAndCandidateSkills, function( i, skill ){
								  var existed = 0;
					                if(json.skillsName != null && json.skillsName != 'undefined' && json.skillsName != ''){
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
							result = result +'</div>';
							result = result +'<div class="clearfix"></div>';
							
							
							//visa type start
							result = result +'		    <div id="visa" class="tab-pane fade in active">';
							result = result +'		      <div class="visa-summary positionRelative">';
							result = result +'		        <div class="design-view visaresult">';
							
							result = result +'		<div class="clearfix">';
							result = result +'<h2 class="education  pull-left">Immigration Status:</h2>';
							 if(json.candidateType == "Candidate"){ 
								result = result +'		          <span id="editVisaBtnSpan" class="pull-right mt10 mb"><button type="button" id="editVisaBtn" name="editVisaBtn" class="btn btn-primary btn-sm" onclick="editVisa('+candidateId+');">Edit Visa</button></span>';
							 }else{ 
								result = result +'		          <span id="editVisaBtnSpan" class="pull-right mt10 mb"><button type="button" id="editVisaBtn" name="editVisaBtn" class="btn btn-primary btn-sm" onclick="editVisa('+candidateId+');">Edit Visa</button></span>';
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

function cleardateofBirth(){
	$("#dateofbirth-candidate").val("");
	};
	
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
		 if(skillName != null && skillName != '' && skillName !='undefined'){
			 Urlcheck = sourceleadUrl+"/saveOrUpdateOnlySkillBycandidateId?${_csrf.parameterName}=${_csrf.token}&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&skillName="+jsonData
		 }else{
			 Urlcheck = sourceleadUrl+"/saveOrUpdateOnlySkillBycandidateId?${_csrf.parameterName}=${_csrf.token}&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&skillName="+""
		 }
		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
			type : "POST",
	        url: Urlcheck,        
	        success :function(response) {
	        	if (response && response.response === "INVALID_DATA") {
	        		showError(response.responseMessage);
	        	} else if (response.response === "AWKWARD") {
	        		location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		getProfessionalInfo();
	  	        	$("#loading").removeAttr('style');
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        		showError("Operation failed, please try again");
	        	}
	        },
	     }); 
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
		var expectedRate = document.getElementById("expectedRate").value; 
		 if(expectedRate != null && expectedRate != ""){
			 $('#expectedRateType').removeAttr('data-validation-optional');
			 $('#expectedRateType').attr("data-validation","required"); 
		 }
		 else{
			 $('#expectedRateType').removeAttr('data-validation');
			 $('#expectedRateType').attr("data-validation-optional","true"); 
		 }
		 
		 var expectedRateType =  document.getElementById("expectedRateType").value; 
		 if(expectedRateType != null && expectedRateType != "" && expectedRateType != 'undefined'){
			 $('#expectedRate').removeAttr('data-validation-optional');
			 $('#expectedRate').attr("data-validation","required"); 
		 }else{
			 $('#expectedRate').removeAttr('data-validation');
			 $('#expectedRate').attr("data-validation-optional","true");
		 }
		 
		 var currentRate = document.getElementById("currentRate").value; 
			 if(currentRate != null && currentRate != ""){
				
				 $('#currentRateType').removeAttr('data-validation-optional');
				 $('#currentRateType').attr("data-validation","required"); 
			 }else{
				 $('#currentRateType').removeAttr('data-validation');
				 $('#currentRateType').attr("data-validation-optional","true"); 
			 }
			 
			 
			 
			 var currentRateType =  document.getElementById("currentRateType").value; 
			 if(currentRateType != null && currentRateType != "" && currentRateType != 'undefined'){
				 $('#currentRate').removeAttr('data-validation-optional');
				 $('#currentRate').attr("data-validation","required"); 
			 }else{
				 $('#currentRate').removeAttr('data-validation');
				 $('#currentRate').attr("data-validation-optional","true");
			 }
			 
			 
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		
		
		 if ($('#candidateProfileEdit_form').validate(false, validationSettings)){ 
			var form = $('#candidateProfileEdit_form')[0];
      		var formData = new FormData(form); 
			
			$("#update_candidate").attr("disabled", "disabled");
			$("#update_candidate").addClass('button').val('Processing..');
			$.ajax({
						type : "POST",
						data : formData,
						processData: false,
		    	        contentType:false,
						url : sourceleadUrl+"/updateCandidate?${_csrf.parameterName}=${_csrf.token}",
						success : function(response) {
							if (response && response.response === "INVALID_DATA") {
							    showError(response.responseMessage);
							} else if (response.response === "AWKWARD") {
							    location.href = "/errorPage";
							} else if (response.response === "SUCCESS") {
								if(response.object === "success") {
									$("#selector1").removeAttr("style");
				         			 $("#selector1").css({ display: "block" });
						        	 $("#candMsgDiv").html("Success!!  Candidate Updated");
						        	 candUpdateMsgFadeinFunction();
									getProfessionalInfo();
								}else{
									$("#update_candidate").removeAttr("disabled");
									showError("Invalid data");
								}
							} else if (response.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
							    showError("Operation failed, please try again");
							}
						},
					});
			return true;
		 }else{ 
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
	function candUpdateMsgFadeinFunction() {
		$("#selector1").fadeIn(200);
        $('#selector1').delay(2000).fadeOut('slow');
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
     1.usage     :  used in professionalProfile() function  line 258
     2.parameter : csrf token candidateId.
     3.ajax url implementation in CandidateListRestController.
--%>  

<%-- 
     @author : Venkat Kesav
     this script is for Remove Candidate Skill.
     1.usage     :  used in professionalProfile.
     2.parameter : csrf token, candidateId, Skill Name
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function removeSkillById(candidateId, skillId){
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/removeSkillBySkillId?${_csrf.parameterName}=${_csrf.token}&skillId="+skillId,        
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
						url : sourceleadUrl+"/fieldUpdateCandidate?${_csrf.parameterName}=${_csrf.token}&candidateSkillId="+candidateSkillId+"&name="+name+"&value="+value,
						success : function(result) {
							 
						},
			}); 	 	
	} 
</script>

           
           

<script>
	function employeeSubmissionProfile(candidateId) {
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId=" + candidateId,
					success : function(response) {
						if (response && response.response === "INVALID_DATA") {
						    showError(response.responseMessage);
						} else if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							$("#employeeSubmissionProfile").empty();
							var result = "";
							result = result +'        <div class="clearfix design-view">';
							result = result +'          <h2 class="education"><i class="fa fa-black-tie"></i>&nbsp;&nbsp;Employers</h2>';
							result = result +'          <div class="row">';
							result = result +'            <div class="col-md-12">';
							result = result +'              <ul class="list-unstyled personal-info">';
						//	var json = JSON.parse(data);
							for (var i = 0; i < response.data.length; i++) {
								var opt = response.data[i];
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
								
								
								if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined' && opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
									startYear=   opt.startYear  +' To '+opt.endYear;
								}else if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined'){
									startYear=   opt.startYear;
								}else if(opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined'){
									startYear = opt.endYear;  
								}else{
									startYear = " ";
								}
								
								result = result +'   <li>';
								result = result +'   <div class="clearfix">';
								result = result +' <h3>'+name+'</h3>';
								result = result +' <p>'+designation+'</p>';
								result = result +' <p>';
							    result = result + startYear ; 
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
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					}
				});
	}
</script>

<script>
function educationSubmissionProfile(candidateId){
	 $.ajax({  
          type : "GET",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
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
					//	 var json = JSON.parse(data);
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
	                    	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined' && opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
	                    		 EducationstartDateStr = opt.startDateStr +' To '+opt.endDateStr;
	                    	 }else if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
	                    		 EducationstartDateStr = opt.startDateStr;
	                    	 }else if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
	                    		 EducationstartDateStr = opt.endDateStr;
	                    	 }else{
	                    		 EducationstartDateStr ='';
	                    	 }
		                        result = result +'                <li>';
								result = result +'                  <div class="clearfix">';
	                    	    result = result +'                    <h3>'+EducationName+' <span class="small text-muted">&nbsp;'+specialization+'</span></h3>';
	                    	    result = result +'                    <p>'+EducationgradeType + ' '+Educationgrade+' '+institute+' '+Educationaddress+'</p>';
								result = result +'                    <p>'+EducationstartDateStr+'</p>';
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
function certificationSubmissionProfile(candidateId){
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			if(response.data.length > 0){
        				$("#certificationSubmissionProfile").empty();
	                	 var result = "";
	                	 result = result +'        <div class="clearfix design-view">';
							result = result +'          <h2 class="education"><i class="fa fa-certificate"></i>&nbsp;&nbsp;Certification</h2>';
							result = result +'          <div class="row">';
							result = result +'            <div class="col-md-12">';
							result = result +'              <ul class="list-unstyled personal-info">';
							for (var i = 0; i < response.data.length; i++) { 
		                    	var opt = response.data[i];  
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
				            	if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "" && opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
				            		candidatevalidFromDateToString = opt.candidatevalidFromDateToString +' To '+opt.candidatevalidToDateToString;
				            	}else if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != ""){
				            		candidatevalidFromDateToString = opt.candidatevalidFromDateToString;
				            	}else if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
				            		candidatevalidFromDateToString = opt.candidatevalidToDateToString;
				            	}else{
				            		candidatevalidFromDateToString = " ";
				            	}
				            	
								result = result +'                <li>';
								result = result +'                  <div class="clearfix">';
								result = result +'                    <h3>'+opt.name+' <span class="small text-muted">&nbsp;'+score+'  '+scoreType+'</span></h3>';
								result = result +'                    <p>'+opt.provider+' </p>';
								result = result +'                    <p> '+candidatevalidFromDateToString+'</p>';
								result = result +'                  </div>';
								result = result +'                </li>';
							}
							result = result +'              </ul>';
							result = result +'            </div>';
							result = result +'          </div>';
							result = result +'        </div>';
							$("#certificationSubmissionProfile").empty();
							$("#certificationSubmissionProfile").append(result); 
        			}
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
$('.selectpicker').selectpicker('refresh');

</script>    
     
<script>
 function disableMonths() {
	
         if ($("#experienceYears").val() == "25+"){
             $("#experienceMonths").attr("disabled", true);
             $("#experienceMonths").val("disabled", true);
         }else{
        	 $("#experienceMonths").attr("disabled", false); 
		 }    
 }
</script>
  
  
<script>
function updateDesignationType(){
	var designationType = $("#designationType").val();
	if(designationType == 'Other'){
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


<script>
function makeProfileAsPublic(){
	var checked = document.getElementById("isProfilePublic").checked
	if(checked == true){
		alertMessageForPublicVisibility("Do you want to make your profile as public visibility?",checked);
	}else{
		alertMessageForPublicVisibility("Do you want to remove your profile from public visibility?",checked);
	}
}
</script>

<script type="text/javascript">
function alertMessageForPublicVisibility(message,checked){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='makeAsPublicVisibility();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPublicVisibility("+checked+");'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelPublicVisibility(checked){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	if(checked){
		document.getElementById("isProfilePublic").checked = false;
	}else{
		document.getElementById("isProfilePublic").checked = true;
	}
	
}
</script>


<script type="text/javascript">
function makeAsPublicVisibility(){
	
	 $("#choicepopUp").empty();
 	 $(".project_inn_mask").fadeOut(200);
 	
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/setCrowdSourcingProfessionalProfileAsPublicVisibility?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(data) {
	        	$("#loading").removeAttr("style");
	        },
	    });
	
}
</script>

 <!-- Visa Type Script View/edit script start here -->
 <script>
	function editVisa(candidateId){
		$("#visaName").prop('disabled', false).trigger("chosen:updated");
		$("#editVisaBtn").remove();
		$("#editVisaBtnSpan").hide();
		window.visaCountBefore = $("#visaName :selected").length;
		var result = '';
		result = result + '<button type="button" id="saveVisaBtn" name="saveVisaBtn" class="btn btn-primary btn-sm" onclick="saveVisa('+candidateId+');">Save Visa</button>';
		$("#saveVisaBtnSpan").empty();
		$("#saveVisaBtnSpan").append(result);
		$("#saveVisaBtnSpan").show();
		
	    
	    //Adding Customized Skills to the list
	}

	function saveVisa(candidateId){
		$("#visaName").prop('disabled', true).trigger("chosen:updated");
		window.visaCountAfter = $("#visaName :selected").length;
		if(visaCountBefore != visaCountAfter){
			saveVisas(candidateId);
		}
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
 function saveVisas(candidateId){
	 var visaName = $("#visaName").val();
	 var jsonData = encodeURIComponent(visaName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 if(visaName != null && visaName != '' && visaName !='undefined'){
		 Urlcheck = sourceleadUrl+"/saveOrUpdateOnlyVisaBycandidateId?${_csrf.parameterName}=${_csrf.token}&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&visaName="+jsonData
	 }else{
		 Urlcheck = sourceleadUrl+"/saveOrUpdateOnlyVisaBycandidateId?${_csrf.parameterName}=${_csrf.token}&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&visaName="+""
	 }
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
        	 	if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
           			showError(result.responseMessage);
           		} else if (result.response === "AWKWARD") {
           	    	location.href = "/errorPage";
           		} else if (result.response === "SUCCESS") {
           			getProfessionalInfo();
              	  $("#loading").removeAttr('style');
           		} else if (result.indexOf('Sign In') !== -1) {
              		location.replace(sourceleadUrl);
              	} else {
              	    showError("Operation failed, please try again");
              	}
       },
    }); 
 }  
</script>

