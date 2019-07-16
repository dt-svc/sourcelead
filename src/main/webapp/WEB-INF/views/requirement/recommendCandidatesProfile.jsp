<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 
     @author : David Raju.
     this script is for edit Candidate.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>


<style type="text/css">
#skillName { width: 100%; }
.chosen-container { width: 100% !important; }
</style>


<!-- Edit Recommended Candidate Script Start Here -->

<script>
function editCandidate(candidateId){
	  
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
	         url: sourceleadUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
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
	            			 	
	            			 	var address = '';
	            			 	if(json.address != null && json.address != '' && json.address != 'undefined'){
	            			 		address = json.address;            			 		
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
	            			 	
	            			 	var primarySkill = '';
	            			 	if(json.primarySkill != null && json.primarySkill != '' && json.primarySkill != 'undefined'){
	            			 		primarySkill = json.primarySkill;            			 		
	            			 	}
	            			 	if(json.vendorName != null && json.vendorName != '' && json.vendorName != 'undefined'){
	            			 		vendorName = json.vendorName;
	            			 	}else{
	            			 		vendorName = "";
	            			 	}
	            			 	
	            			 	var location = '';
	            			 	if(json.location != null && json.location != '' && json.location != 'undefined'){
	            			 		location = json.location;            			 		
	            			 	}
	            			 	
	            			 	var zipCode = '';
	            			 	if(json.zipCode != null && json.zipCode != '' && json.zipCode != 'undefined'){
	            			 		zipCode = json.zipCode;            			 		
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
								result = result + '		    <div class="edit-details col-md-8 edit-candaite canprofile-popright"> <i class="fa fa-close close-div" onclick="cancelEdit();"></i>';
								result  = result + '         <form id="candidateProfileEdit_form" name="candidateProfileEdit_form" method="post">';
								result = result + '		      <div class="form-horizontal clearfix">';
								result = result + '<input type="hidden" class="form-control input-sm" name="candidateId"  value="'+candidateId+'">';
								result  = result + '<input type="hidden" id="orgloationId" name="orgloationId" value="${SourceLeadWeb_orgLocations.id}" />';
								result  = result + '<input type="hidden" id="loginUserId" name="loginUserId" value="${SourceLeadWeb_user.id}" />';
								result = result + '		        <div class="clearfix mb10">';
								result = result + '		          <div class="col-md-6">';
								result = result + '		            <input type="text" class="form-control input-sm" name="firstName" value="'+json.firstName+'" placeholder="First Name *" field-name="First Name"  data-validation="required validate_AlphaNumber validate_Space validate_length length1-100">';
								result = result + '		          </div>';
								result = result + '		          <div class="col-md-6">';
								result = result + '		            <input type="text" class="form-control input-sm" name="lastName" value="'+lastName+'" placeholder="Last Name" data-validation="validate_AlphaNumber validate_Space validate_length length1-100" data-validation-optional="true">';
								result = result + '		          </div>';
								result = result + '		        </div>';
								if(json.isAssociate == 'Y'){
								result = result + '	           <input type="hidden" name="isAssociate" id="isAssociate" value="Y"/>';
								}
								// If Candidate Type equals Consultant email field should be Disable mode
								
								result = result + '<div class="clearfix mb10">';
								result = result + '<div class="col-md-6" id="emailDiv">';
								if(json.candidateType == "Candidate"){
									result = result + '<input type="hidden" name="candidateType" id="candidateType" value="'+json.candidateType+'" />';
									result = result + '<input type="text" class="form-control input-sm" id="email" name="email" value="'+json.email+'" placeholder="Email*" field-name="Email" data-validation="validate_Space required validate_email validate_length length2-100" onblur="validateCandidateEmail();">';
									result = result + '<input type="hidden" id="tempEmail" value="'+json.email+'" />';
								}
								 
								if(json.candidateType == "Consultant"){
								     result = result + '<input type="text" class="form-control input-sm" name="email" value="'+json.email+'" placeholder="Email*" field-name="Email" data-validation="validate_Space required validate_email validate_length length2-100" readonly="true">';
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
								result = result + '<input type="text" class="form-control input-sm" name="address"  value="'+address+'" placeholder="Address" field-name="Address" data-validation="validate_Space validate_length length2-250" data-validation-optional="true">';
								result = result + '</div>';
								
								result = result + '<div class="col-md-6" id="locationField">';
								result = result + '<input type="text" class="form-control input-sm" name="location"  id="cpLocation"  value="'+location+'" placeholder="Location" field-name="Locatin" data-validation="required">';
								result = result + '<i class="fa fa-refresh btn btn-primary profilerefresh-icon" title="Refresh" onclick="refreshGoogleApiLocation(\'cpLocation\',\'cpCountry\',\'cpState\',\'cpCity\',\'cpZipCode\');"></i>';
								result = result + '</div>'; 
								
								result = result + '</div>';
								
								result = result + '<div class="clearfix mb10">';
								
								result = result + '<div class="col-md-6 custom-fields">';
								result = result + '<input class="form-control" id="cpCountry" name="country" value="'+json.country+'"  placeholder="Enter your Country" type="text" readonly></input>';
								result = result + '</div>';
								
								result = result + '<div class="col-md-6 custom-fields select-arrow">';
								result = result + '<input class="form-control" id="cpState" name="state" value="'+state+'" placeholder="Enter State" type="text" readonly></input>';
								result = result + '</div>';
								
								result = result + '</div>';
								
								result = result + '<div class="clearfix mb10">';
								result = result + '<div class="col-md-6 custom-fields">';
								result = result + '<input class="form-control" id="cpCity" name="city" value="'+city+'" placeholder="Enter City" type="text" readonly></input>';
								result = result + '</div>';
								
								result = result + '<div class="col-md-6 custom-fields">';
								result = result + '<input class="form-control" id="cpZipCode" name="zipcode" value="'+zipCode+'" placeholder="Enter Zip code" type="text"></input>';
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
								
								result = result + '<div class="input-group-btn tblminwidth-75 ratetype select-field">';
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
								
								result = result + '<div class="clearfix mb">';
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
								
								result = result + '<div class="input-group-btn tblminwidth-75 ratetype select-field">';
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
								result = result + '<option value="">Select Year</option>';
								
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
								result = result + '<select  class="form-control" name="experienceMonths"  id="experienceMonths" field-name="experienceMonths" disabled="disabled">';
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
								result = result + '<select class="form-control" id="designationType1" name="designationType" field-name="Employment type" data-validation="required"  onchange="employementType();updateDesignationOtherType();">';
								result = result + '<option value="">--Select Employment Type--</option>';
								result = result + ' <option '+(json.designationType == "C2C" ? "selected" : '') +'>C2C</option>';
								result = result + ' <option '+(json.designationType == "C2C-1099" ? "selected" : '') +'>C2C-1099</option>';
								result = result + '<option  '+ (json.designationType == "W-2" ? "selected" : '') +'>W-2</option>';
								result = result + ' <option '+(json.designationType == "C2H" ? "selected" : '') +'>C2H</option>';
								result = result + ' <option '+(json.designationType == "C2H -1099" ? "selected" : '') +'>C2H -1099</option>';
								result = result + '<option  '+(json.designationType == "Part Time" ? "selected" : '') +'>Part Time</option>';
								result = result + '<option  '+(json.designationType == "Full Time" ? "selected" : '') +'>Full Time</option>';
								result = result + '<option  '+(json.designationType == "Other" ? "selected" : '') +'>Other</option>';
								result = result + ' </select>';
								result = result + '</div>';
								result = result + '</div>';
								result = result + '<div class="col-md-6 designationTypeDiv" id="designationTypeDiv" style="display:none">';
								result = result + '<input type="text" class="form-control input-sm" name="designationOtherType"  id="designationOtherType1" value="'+designationOtherType+'" placeholder="Employment Other Type" field-name="Employment Other Type" data-validation="validate_Space validate_length length2-50" data-validation-optional="true">';
								result = result + '</div>';
							    result = result + '<span class="" id="hideVendor" style="display:none">'; 
								result = result + '<div class="col-md-6 custom-fields select-arrow" id="errorvendor">';
								result = result + '<select  class="form-control" name="vendors"  id="vendorName" value="'+vendorName+'" placeholder="select Vendor" field-name="Vendor" onchange="getVendorContactList();removeValiationVendor();">';
								result = result + '</select>';
								result = result + '<input type="hidden" name="updateVendorName" id="updateVendorName" value="'+vendorName+'" />';
								result = result + '<input type="hidden" name="updateVendorOrgRelationId" id="updateVendorOrgRelationId" value="'+json.vendorOrgRelationId+'" />';
								result = result + '</div>';
								result = result + '<div class="col-md-6 custom-fields select-arrow mt10" id="errorvendorContact">';
								result = result + '<select  class="form-control" name="vendorcontact"  value="'+json.vendorContactName+'" id="vendorcontactName" field-name="Vendor Contact"  placeholder="select Vendor Contact" onchange="removeValiationVendorContact();">';
								result = result + '</select>';
								result = result + '<input type="hidden" name="updateVendorContactName" id="updateVendorContactName" value="'+json.vendorContactName+'" />';
								result = result + '<input type="hidden" name="updateSpocId" id="updateSpocId" value="'+json.vendorSpocId+'" />';
								result = result + '</div>';
								result = result + '</div>';
								
								result = result + '</span>';
								
								
								result = result + '<div class="clearfix mb10 mt">';
								result = result + '<div class="col-md-12">';
								result = result + '<textarea placeholder="Summary" class="form-control input-sm"  name="summary" rows="2" cols="5">'+summary+'</textarea>';
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
								/* updateDesignationType(json.designationType); */
								initLocationAuto('cpLocation','cpCountry','cpState','cpCity','cpZipCode','cpLatitude','cpLongitude');
								getVendorsByOrgLocation();
								employementType();
								$(".edit-details").toggleClass("edit-details-active");
								$(".collapse-div").removeClass("edit-details-active");
								
								  
								  $("#vendorName").chosen();
								  $("#vendorName").trigger("chosen:update");
								  
     							  $("#vendorcontactName").chosen();
     							  $("#vendorcontactName").trigger("chosen:update");
     							 $('.chosen-select').each(function() {
										var $this = $(this);
										$this.next().css({'width': '100%'});
								  })
								   $(".chosen-container").css("width","100%");
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
     @author : Venkat Kesav
     this script is for Delete Candidate.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token, candidateId
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function deleteCandidate(candidateId){
	 
	var requirementId = $("#requirementId").val();
	  var reason = document.getElementById("rejectReason").value;
	  reason = reason.replace(/\s/g, "") 
	 
	  if(reason.length == 0){
		  $(".rejectErrorMsgs").find('p.jquery_form_error_message').remove();
    	  $(".rejectErrorMsgs").append("<p class='jquery_form_error_message'>Reason is required</p>");
		}
	  
	  if(reason.match(/^[^\s-](.*)*$/) != null){
		  $(".rejectErrorMsgs").find('p.jquery_form_error_message').remove();
		  $(".rejectErrorMsgs").empty();
			  
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/deleteCandidateByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&reason="+reason+"&candidateType=Candidates",        
         success :function(response) {
        	 if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			 $("#recommendationsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        			 setTimeout(function () {
        				requirementRecommendations(requirementId);
         			 	$(".clients_side_wrap").removeClass("clients_side_wrap_active");
         			 	$(".side_mask").fadeOut();
         			 	$(".project_inn_mask").fadeOut();
                       }, 100);
        		} else if (response.indexOf('Sign In') !== -1) {
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
     @author : Venkat Kesav
     this script is for Delete Candidate alert
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
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
function removeCandidateAlertMessage(candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result +"		<div class='rejectErrorMsgs'/>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteCandidate("+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
	result = result +"	</div>";
	$("#removeCandidateAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200).css({'z-index':'1000'});
}
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
	var message = confirm("You are choosing to ReActivate the Candidate");
	if (message == true) {
		var reactivate = '1';
		$.ajax({
	        type : "POST",
	         url: sourceleadUrl+"/reActivateCandidateByCandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&reActivate="+reactivate,        
	         success :function(result) {
	        	 if (result && result.response === "INVALID_DATA") {
	 			    $("#update_candidate").removeAttr("disabled");
	 			    $("#update_candidate").removeAttr('button').val('Save');
	 			    showError(result.responseMessage);
	 			} else if (result.response === "AWKWARD") {
	 			    location.href = "/errorPage";
	 			} else if (result.response === "SUCCESS") {
	 				location.replace(sourceleadUrl+"/candidatesList");
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
function candidateProfile(candidateId){
	  var skillsList = '${skillsList}'; 
	  
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
         success :function(result) {
        	var json = result.object;
      	   if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
              		if(json != null){  
							var result ="";
							var summary= "";
							if(json.summary != null && json.summary != '' && json.summary != 'undefined'){
								summary = json.summary;
							}else{
								summary = "NO DATA";
							}
							result = result+'<div id="removeCandidateAlert" ></div>  ';
							if(json.status == 0){
								result = result + '<div class="project_tabs_inn_head"><a href="javascript:void(0)" class="positionRelative showInfo"><i class="glyphicon glyphicon-info-sign"></i>Terminated<span class="infoContent col-xs-12 col-md-5"> '+json.terminateReason+' </span></a><a href="javascript:void(0)" onClick="reActivateCandidateAlertMessage('+candidateId+');"><i class="glyphicon glyphicon-trash"></i>ReActivate</a> ';
							}else{
								result = result + '<div class="project_tabs_inn_head "> <a href="javascript:void(0)" onClick="editCandidate('+candidateId+');" class="mr addUser-org iconpostion"><i class="glyphicon glyphicon-pencil"></i>Edit</a> <a href="javascript:void(0)" onClick="removeCandidateAlertMessage('+candidateId+');"><i class="glyphicon glyphicon-trash"></i>Remove</a> ';

							}
							result = result + '<div id="profileEditPopup"></div>';
							result = result +'<div class="require_wrap_details require_wrap_details_text_left">';	 
							result = result +'		<h1>Summary:</h1>';
							result = result +'	    <textarea placeholder="Enter description" readonly>'+summary+'</textarea>';
							
							
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
							// here get lookup data skills and manually enter skills 
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
							result = result +'<div class="clearfix"></div>';
							//visa type start
							result = result +'		    <div id="visa" class="tab-pane fade in active">                                                                                         ';
							result = result +'		      <div class="visa-summary positionRelative">                                                                                                            ';
							result = result +'		        <div class="design-view visaresult">                                                                                                             ';
							result = result +'		          <!-- Select Visa -->                                                                                                              ';
							
							result = result +'		<div class="clearfix">';
							result = result +'	<h2 class="education  pull-left">Immigration Status:</h2>';
								result = result +'<span id="editVisaBtnSpan" class="pull-right mt10 mb"><button type="button" id="editVisaBtn" name="editVisaBtn" class="btn btn-primary btn-sm" onclick="editVisa('+candidateId+');">Edit Visa</button></span>';
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
							
							
							//Employee Div
							result = result + '<div id="employeeSubmissionProfile"></div> '; 
							
							//Education Div
							result = result + '<div id="educationSubmissionProfile"></div> ';
							
							//Certification Div
							result = result + '<div id="certificationSubmissionProfile"></div> ';
							
							result = result + '    </div>';
							result = result + '  </div>';
							$("#tab-11").empty();
							$("#tab-12").empty(); 
							$("#tab-13").empty();  
							$("#tab-14").empty();  
							$("#tab-15").empty();  
							$("#tab-16").empty();  
							$("#tab-11").append(result);  
							    
							candidateSkillsList(candidateId);
							employeeSubmissionProfile(candidateId);
							educationSubmissionProfile(candidateId);
							certificationSubmissionProfile(candidateId);
							
							$("#tab-11").show();
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
// 									$("#skillName").val('');
									 
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


<%-- 
     @author : sarath kumar.
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
		 Urlcheck = sourceleadUrl+"/saveOrUpdateOnlySkillBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&skillName="+jsonData
	 }else{
		 Urlcheck = sourceleadUrl+"/saveOrUpdateOnlySkillBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&skillName="+""
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
       			//candidateProfile(candidateId);
             	  //candidateQuickViewProfile(candidateId);
             	  clientSideBar(candidateId);
             	  $("#loading").removeAttr('style');        			
       		} else if (response.indexOf('Sign In') !== -1) {
       			location.replace(sourceleadUrl);
       		} else {
       		    showError("Operation failed, please try again");
       		}
       	},
    }); 
	 
	/*  
	 var skillName = $("#skillName").val();
	 if(skillName != null && skillName != '' && skillName !='undefined'){
		 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({  
	          type : "POST",
	          url: sourceleadUrl+"/saveSkilsBycandidateID?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&skillName="+skillName,        
	          success :function(data) {
	               if(data != null && data != 'undefined' && data != ""){
	            	   candidateSkillsList(candidateId);
	            	   removeSkillPopUp(candidateId);
	               }
	        },
	     }); 
	 }else{
		 alert("Enter Skill");
	 } */
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
            
   
<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in editCandidate() function  line 78
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>         
<script type="text/javascript">
	function upDateCandidate(candidateId) {
	
		
		//This is commented by Laxman Because form is not submiting  check the  rates condiditons first and then open comment 
		
		/*  var rateType = document.getElementById("rateType").value; 
		 if(rateType != null && rateType != ""){
			 $('#expectedRate').removeAttr('data-validation-optional');
			 $('#expectedRate').attr("data-validation","required"); 
		 }else{
			 $('#expectedRate').removeAttr('data-validation');
			 $('#expectedRate').attr("data-validation-optional","true");
			 $('#rateType').removeAttr('data-validation');
			 $('#rateType').attr("data-validation-optional","true"); 
		 }
		 
		var expectedRate = document.getElementById("expectedRate").value; 
		 if(expectedRate != null && expectedRate != ""){
			 $('#rateType').removeAttr('data-validation-optional');
			 $('#rateType').attr("data-validation","required"); 
		 }else{
		 $('#rateType').removeAttr('data-validation');
		 $('#rateType').attr("data-validation-optional","true"); 
		 }
		 
		 var currentRateType = document.getElementById("currentRateType").value; 
			if(currentRateType != null && currentRateType != ""){
				$('#currentRate').removeAttr('data-validation-optional');
				$('#currentRate').attr("data-validation","required"); 
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
			 }else{
			 $('#currentRateType').removeAttr('data-validation');
			 $('#currentRateType').attr("data-validation-optional","true"); 
			 }
			  */
		 
			
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
			  rateValidations();
		 if ($('#candidateProfileEdit_form').validate(false, validationSettings)){ 
			
			var formData = getFormData('candidateProfileEdit_form');
			$.ajax({
						type : "POST",
						data : formData,
						url : sourceleadUrl+"/updateCandidate?"+tokenParamName+"="+tokenValue+"",
						success : function(result) {
							if(result && result === "failed") {
								 //$("#update_candidate").removeAttr("disabled");
								showError("Invalid data");
							} else{
							clientSideBar(candidateId);
							}
						},
					});
			return true;
		 }else{ 
				return false;
		 } 
	} 
	$('body').on('blur','#candidateProfileEdit_form', function() {
		$("#candidateProfileEdit_form").showHelpOnFocus();
		$("#candidateProfileEdit_form").validateOnBlur(false,validationSettings);
	});
</script>

<script>
function cancelEdit(){
	$("#profileEditPopup").empty();
}
</script>
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

<%-- 
     @author : sarath Kumar.
     this script is for upDate Candidate Details .
     1.usage     :  used in candidateProfile() function  line 258
     2.parameter : csrf token candidateId.
     3.ajax url implementation in CandidateListRestController.
--%>  
<script>
function candidateSkillsList(candidateId){  
	// getCandidatesSkillsBycandidateID
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(result) {
        	  var json = result.object;
       	   		if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
               		if(json != null){
	                	 $("#candidateSkillList").empty();
	                	 var result = "";
                	     result = result + '      <table class="table table-responsive table-hover clearfix">';
                		 result = result + '        <thead>';
                		 result = result + '          <tr>';
                		 result = result + '            <th>Skills</th>';
                		 result = result + '            <th>Level</th>';
                		 result = result + '            <th>Experience</th>';
                		 result = result + '            <th>Action</th>';
                		 result = result + '          </tr>';
                		 result = result + '        </thead>';
                		 result = result + '        <tbody>';
	                    // var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) {  
	                    	 var opt = json[i];   
	                    	    result = result + '<tr>';
		                		result = result + '<td><p class="form-control-static">'+opt.name+'</p></td>';
		                		
		                		if(window.candidateStatusGlobal == '0'){
		                			result = result + '<td><select id="scale" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);" >';
		                			if(opt.scale == 'undefined'|| opt.scale==null || opt.scale== 0){
			                			 result = result + '<option selected=""disabled="">select</option>'; 
				                	 }else{
		                			result = result + '<option selected="" value="'+opt.scale+'" disabled="">'+opt.scale+'</option>';
				                	 }
		                		}else{
		                		
		                		result = result + '<td><select id="scale" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);" >';
		                		result = result + '<option selected="" value="'+opt.scale+'" disabled="">Select</option>';
		                		
		                		if(opt.scale == "Beginner"){
		                			result = result + '<option selected>Beginner</option>';
		                		}else{
		                			result = result + '<option>Beginner</option>';
		                		}
		                		if(opt.scale == "Familiar"){
		                		    result = result +'<option selected>Familiar</option>';
		                		}else{
		                			result = result +'<option>Familiar</option>';
		                		}
		                		if(opt.scale == "proficient"){
		                			result = result + '<option selected>proficient</option>';
		                		}else{
		                			result = result + '<option>proficient</option>';
		                		}
		                		if(opt.scale == "Expert"){
		                			result = result + '<option selected>Expert</option>';
		                		}else{
		                			result = result + '<option>Expert</option>';
		                		}
		                		if(opt.scale == "Master"){
		                			result = result + '<option selected>Master</option>';
		                		}else{
		                			result = result + '  <option>Master</option>';
		                		}
		                		}
		                		result = result + '              </select></td>';
		                		
		                		 if(window.candidateStatusGlobal == '0'){ 
		                			 result = result + '<td><select id="totalExperience" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);">';
				                	 if(opt.totalExperience == 'undefined'|| opt.totalExperience==null || opt.totalExperience== 0){
			                			 result = result + '<option selected=""disabled="">select</option>'; 
				                	 }else{
		                			 result = result + '<option selected="" value="'+opt.totalExperience+'" disabled="">'+opt.totalExperience+'</option>'; 
		                		 }
				                }else{
		                		result = result + '<td><select id="totalExperience" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);">';
		                		result = result + '<option selected="" disabled="">Select</option>';
		                		if(opt.totalExperience == "0"){
		                			result = result + '<option selected>0</option>';   
		                		}else{
		                			result = result + '<option>0</option>';
		                		}
		                		if(opt.totalExperience == "1"){
		                			result = result + '<option selected>1</option>';   
		                		}else{
		                			result = result + '<option>1</option>';
		                		}
		                		if(opt.totalExperience == "2"){
		                			result = result + '<option selected>2</option>';   
		                		}else{
		                			result = result + '<option>2</option>';
		                		}
		                		if(opt.totalExperience == "3"){
		                			result = result + '<option selected>3</option>';   
		                		}else{
		                			result = result + '<option>3</option>';
		                		}
		                		if(opt.totalExperience == "4"){
		                			result = result + '<option selected>4</option>';   
		                		}else{
		                			result = result + '<option>4</option>';
		                		}
		                		if(opt.totalExperience == "5"){
		                			result = result + '<option selected>5</option>';   
		                		}else{
		                			result = result + '<option>5</option>';
		                		}
		                		if(opt.totalExperience == "6"){
		                			result = result + '<option selected>6</option>';   
		                		}else{
		                			result = result + '<option>6</option>';
		                		}
		                		}
		                		result = result + '              </select></td>';
		                		
		                		 if(window.candidateStatusGlobal == '0'){ 
				                result = result + '            <td><div class="text-center"><a href="javascript:void(0)"> <i class="fa fa-trash"></i></a></div></td>';
		                		 }else{
		                		result = result + '            <td><div class="text-center"><a onclick="removeSkillById('+candidateId+', '+opt.id+');" href="javascript:void(0)"> <i class="fa fa-trash"></i></a></div></td>';
		                		 }

		                		result = result + '          </tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateSkillList").append(result); 
	                     
	                     $("#candidateSkills").trigger("chosen:updated");
               }else{
	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Skills Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#candidateSkillList").empty();
				    $("#candidateSkillList").append(result); 
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
         url: sourceleadUrl+"/removeSkillBySkillId?"+tokenParamName+"="+tokenValue+"&skillId="+skillId,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != ""){
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{
            			 candidateSkillsList(candidateId);
            		 }
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
						url : sourceleadUrl+"/fieldUpdateCandidate?"+tokenParamName+"="+tokenValue+"&candidateSkillId="+candidateSkillId+"&name="+name+"&value="+value,
						success : function(result) {
							 
						},
			}); 	 	
	} 
</script>

<%-- 
@author : prasad k.
If Experience Years 25+ when months will be Disabled 
--%>        
<script>
 function disableMonths() {
	
         if ($("#experienceYears").val() == "50"){
             $("#experienceMonths").attr("disabled", true);
             $("#experienceMonths").val("disabled", true);
         }else{
        	 $("#experienceMonths").attr("disabled", false); 
		 }    
 }
</script>
  
 <%-- 
@author : prasad k.
If designationType is 'Other' when designationOtherType will be open(text box) then enter designationOtherType also.
--%>   
<script>
function updateDesignationType(){
	
	if(designationType.value == 'Other'){
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

<!-- Candidate Email validation script starts here -->

<script type="text/javascript">
 function validateCandidateEmail() {
	 var email = $('#email').val(); 
	 
     var tempEmail = $('#tempEmail').val(); 
	 if(tempEmail != email){
	 var candidateType = $('#candidateType').val();
	 $('#emailDiv').find('p.jquery_form_error_message').remove();
	 if(email != null && email != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateCandidateEmail?"+tokenParamName+"="+tokenValue+"&email="+email+"&candidateType="+candidateType, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		if(response.object == 'true'){
							  $("input#email").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
							  $("#update_candidate").attr("disabled", "disabled");
							  $("#update_candidate").addClass('button').val('Processing..');
			        	} else{
			        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			        		 $("#update_candidate" ).addClass('button').val('Save');
	                         $("#update_candidate").removeAttr("disabled");
			        	}
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	 }
	 }else{
		 $('#update_candidate').removeAttr('disabled');
	 }
 }
 </script>     
 
 <!-- Candidate Email validation script ends here -->
 
 
 
 <script>
	function employeeSubmissionProfile(candidateId) {
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId=" + candidateId,
					success : function(response) {
						if (response && response.response === "INVALID_DATA") {
						    showError(response.responseMessage);
						} else if (response && response.response === "INVALID_USER") {
						    showError(response.responseMessage);
						} else if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							$("#employeeSubmissionDetailsList").empty();
							var result = "";
							result = result +'        <div class="clearfix design-view">';
							result = result +'          <h2 class="education"><i class="fa fa-black-tie"></i>&nbsp;&nbsp;Employers</h2>';
							result = result +'          <div class="row">';
							result = result +'            <div class="col-md-12">';
							result = result +'              <ul class="list-unstyled personal-info">';
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
								
								
								/*  if (opt.startMonth != null && opt.startMonth != '' && opt.startMonth != 'undefined') {
									startMonth=   opt.startMonth +' - ';
								}else{
									startMonth = " ";
								} */
								
								if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined' && opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
									startYear=   opt.startYear  +' To '+opt.endYear;
								}else if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined'){
									startYear=   opt.startYear;
								}else if(opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined'){
									startYear = opt.endYear;  
								}else{
									startYear = " ";
								}
								
								/* if (opt.endMonth != null	&& opt.endMonth != '' && opt.endMonth != 'undefined') {
									endMonth = opt.endMonth +' - ';  
								} else {
									endMonth = " ";
								} */
								
								result = result +'   <li>';
								result = result +'   <div class="clearfix">';
								result = result +' <h3>'+name+'</h3>';
								result = result +' <p>'+designation+'</p>';
								result = result +' <p>';
							    result = result + startYear; 
								result = result +'  </p>';
								result = result +'  </div>';
								result = result +'</li>';
							}
							result = result +'              </ul>';
							result = result +'            </div>';
							result = result +'          </div>';
							result = result +'        </div>';
							$("#employeeSubmissionProfile").append(result);
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
function educationSubmissionProfile(candidateId){
	 $.ajax({  
          type : "GET",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#educationSubmissionDetailsList").empty();
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
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
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
						$("#certificationSubmissionProfile").append(result); 
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

<script>
function employementType(){
	 var employementType = $('#designationType1').val(); 
	 var preval = document.getElementById("hiddenId").value;
		if(employementType == 'C2C'){
			/* document.getElementById("hideVendor").style.display="block";
			 $('#vendorName').attr("data-validation","required"); 
			 $('#vendorcontactName').attr("data-validation","required"); 
			 getVendorsByOrgLocation(); */
			 if(employementType == preval){
					document.getElementById("hideVendor").style.display="block";
					 $('#vendorName').attr("data-validation","required"); 
					 $('#vendorcontactName').attr("data-validation","required");
					 getVendorsByOrgLocation();
				}else{
					document.getElementById("hideVendor").style.display="block";
				 $('#vendorName').attr("data-validation","required"); 
				 $('#vendorcontactName').attr("data-validation","required"); 
				  $('#vendorName').val(''); 
				 
		            $('#vendorName').trigger("chosen:updated");
		            $('#vendorcontactName').empty();
			//	 $('#vendorcontact').val(''); 
		           $('#vendorcontactName').trigger("chosen:updated");
				} 
			 
		}else{
			 document.getElementById("hideVendor").style.display="none";
			 $('#vendorName').removeAttr("data-validation","required"); 
			 $('#vendorcontactName').removeAttr("data-validation","required"); 
			 
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
				$('#vendorName').removeAttr("data-validation-optional", "true"); 
				 $('#vendorcontactName').removeAttr("data-validation-optional", "true"); 
				 getVendorsByOrgLocation();
			}else{
				document.getElementById("hideVendor").style.display="block";
				$('#vendorName').removeAttr("data-validation-optional", "true"); 
				 $('#vendorcontactName').removeAttr("data-validation-optional", "true");  
			  $('#vendorName').val(''); 
			 
	            $('#vendorName').trigger("chosen:updated");
	            $('#vendorcontactName').empty();
		//	 $('#vendorcontact').val(''); 
	           $('#vendorcontactName').trigger("chosen:updated");
			} 
		}else{
			 $('#vendorName').removeAttr("data-validation-optional", "true"); 
			 $('#vendorcontactName').removeAttr("data-validation-optional", "true");  
			 
			 $('#vendorName').val(''); 
			 $('#vendorcontactName').val(''); 
			 
			 $('#vendorName').trigger("chosen:updated");
	            $('#vendorcontactName').empty();
		//	 $('#vendorcontact').val(''); 
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
		        url: sourceleadUrl+"/getVendorsByOrgLocation?", 
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
			        url: sourceleadUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
			        success: function(json) {
			        //	var json = JSON.parse(data);
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
		window.visaCountBefore = $("#visaName :selected").length;
		var result = '';
		result = result + '<button type="button" id="saveVisaBtn" name="saveVisaBtn" class="btn btn-primary btn-sm" onclick="saveVisa('+candidateId+');">Save Visa</button>';
		$("#saveVisaBtnSpan").empty();
		$("#saveVisaBtnSpan").append(result);
		$("#saveVisaBtnSpan").show();
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
		 Urlcheck = sourceleadUrl+"/saveOrUpdateOnlyVisaBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&visaName="+jsonData
	 }else{
		 Urlcheck = sourceleadUrl+"/saveOrUpdateOnlyVisaBycandidateId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&visaName="+""
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
           	  		$("#loading").removeAttr('style');
        			$(".proj_close").click();
        			/* getProfessionalInfo(); */
        		} else if (result.indexOf('Sign In') !== -1) {
           			location.replace(sourceleadUrl);
	           	} else {
	           	    showError("Operation failed, please try again");
	           	}
       },
         
    }); 
 }  
</script>
 
 <!--  Type Script View/edit script end here -->