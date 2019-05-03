<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	
<link href="/resources/css/candidate-common.css?staticTime=${staticTime}" rel="stylesheet">
	
	<script type="text/javascript">
		window.onload = function () {
			initLocationAuto('candidateLocation','candidateCountry','candidateState','candidateCity','candidateZipCode','candidateLatitude','candidateLongitude','','address');
			disableMonths();
		 };
	</script>
	 
	 <script>
		$(document).ready(function() {
			$(".clearUpload").hide();
			document.getElementById("addcandidate_form").reset();
		});
	</script>
	

<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Add Associate</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='/associatesList'/>"> Associate List</a> &raquo; Add Associate</div>
  </section>
  <div class="content-area content-new clearfix">
  <div id="loading"></div> 
  <form:form id="addcandidate_form" name="addcandidate_form">
			<div class="row form-horizontal mt myForm">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<input type="hidden" name="isAssociate" id="isAssociate" value="Y"/>
					<div class="form-group">
						<label class="col-md-3">First Name<span
							class="text-danger">&nbsp;*</span></label>
						<div class="col-md-9">
							<input type="text" class="form-control" id="firstName" name="firstName" value="${firstName}" field-name="First Name" data-validation="required validate_AlphaNumber validate_length length1-100" value="">
							<div class="input-group hidden">
								<div class="input-group-btn">
									<button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										Action <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li><a href="#">Action</a></li>
										<li><a href="#">Another action</a></li>
										<li><a href="#">Something else here</a></li>
										<li role="separator" class="divider"></li>
										<li><a href="#">Separated link</a></li>
									</ul>
								</div>
								<!-- /btn-group -->
								<input type="text" class="form-control" aria-label="...">
							</div>
							<!-- /input-group -->
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3">Last Name</label>
						<div class="col-md-9">
							<input type="text" class="form-control" id="lastName" name="lastName" value="${lastName}" field-name="Last Name"
								data-validation="validate_AlphaNumber validate_length length1-100" data-validation-optional="true" value="">
						</div>
					</div>
					<div class="form-group" id="emailDiv">
						<label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>
						<div class="col-md-9">
							<input type="text" class="form-control" id="email" name="email" value="${email}" field-name="Email"
								data-validation="required validate_length length2-100 validate_email" onblur="validateCandidateEmail();"
								onmouseout="validateCandidateEmail();" value=""> <span class="hidden">Valid Email</span>
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3">Mobile</label>
						<div class="col-md-9">
							<input type="text" class="form-control" id="mobilePhone" name="mobilePhone" value="${phone}" field-name="Mobile Number"
								data-validation="validate_length length9-20 validate_phone" data-validation-optional="true" value="">
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3">Alternative Number</label>
						<div class="col-md-9">
							<input type="text" class="form-control" id="alternateMobilePhone" name="alternateMobilePhone" field-name="Alternative Number"
								data-validation="validate_length length9-20 validate_phone" data-validation-optional="true" value="">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3">Address<span class="text-danger">&nbsp;*</span></label>
						<div class="col-md-9" id="locationField">
							<div class="positionRelative">
								<input class="form-control positionRelative" id="candidateLocation" name="location" 
									field-name="Address" data-validation="required"></input> <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh"
									onClick="refreshGoogleApiLocation('candidateLocation','candidateCountry','candidateState','candidateCity','candidateZipCode','address');"></i>
							</div>
						</div>
					</div>


	                <div class="form-group">
						<label class="col-md-3">Street Address</label>
						<div class="col-md-9">
							<input type="text" class="form-control" id="address"
								name="address" value="${address}" field-name="Street Address" placeholder="Enter your Street Addres" data-validation="validate_Space validate_length length2-250" data-validation-optional="true">
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3">City</label>
						<div class="col-md-9">
							<input class="form-control" id="candidateCity" name="city" placeholder="City" type="text" ></input>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3">State</label>
						<div class="col-md-9">
							<input class="form-control" id="candidateState" name="state" placeholder="State" type="text" ></input>
						</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>
						<div class="col-md-9">
							<input class="form-control" id="candidateCountry" name="country" data-validation="required" field-name="Country" placeholder="Country" value="${loginCountryName}" type="text"></input>
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3">ZipCode</label>
						<div class="col-md-9">
							<input class="form-control" id="candidateZipCode" name="zipcode" placeholder="Zip code" type="text"></input>
						</div>
					</div>


					<div class="form-group hidden">
						<label class="col-md-3">Lat</label>
						<div class="col-md-9">
							<input class="form-control" id="candidateLatitude"
								name="latitude" type="text"></input>
						</div>
					</div>

					<div class="form-group hidden">
						<label class="col-md-3">Long</label>
						<div class="col-md-9">
							<input class="form-control" id="candidateLongitude"
								name="longitude" type="text"></input>
						</div>
					</div>


				
					<div class="form-group">
						<label class="col-md-3">Designation</label>
						<div class="col-md-9 ratetype">
							<input type="text" id="designation" name="designation" value="${designation}" field-name="Designation"
								data-validation="validate_Space validate_Name validate_length length2-250" data-validation-optional="true" value="" class="form-control">
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3">Employment Type<span
							class="text-danger">&nbsp;*</span></label>
						<div class="col-md-9">
							<select class="selectpicker form-control" id="designationType" name="designationType" onChange="getDesignationType(this);" field-name="Employment type" data-validation="required">
								<option value="">--Select Employment Type--</option>
								<option>C2C</option>
								<option>C2C-1099</option>
								<option>W-2</option>
								<option>C2H</option>
								<option>C2H -1099</option>
								<option>Part Time</option>
								<option>Full Time</option>
								<option>Other</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3">Experience</label>
						<div class="col-md-9">
							<div class="row exp-drop">
								<div class="col-xs-6 col-md-6">
									<select class="form-control selectpicker" id="experienceYears"
										name="experienceYears" onchange="disableMonths();">
										<option value="">Years</option>
										<c:forEach begin="0" end="50" varStatus="loop">
											<option>${loop.index}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-xs-6 col-md-6">
									<select class="form-control selectpicker" id="experienceMonths"
										name="experienceMonths">
										<option value="">Months</option>
										<c:forEach begin="0" end="11" varStatus="loop">
											<option>${loop.index}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>

					<div class="form-group designationTypeDiv" id="designationTypeDiv"
						style="display: none">
						<label class="col-md-3">Employment Type</label>
						<div class="col-md-9">
							<input type="text" class="form-control" id="designationOtherType"
								name="designationOtherType"
								value="${candidate.designationOtherType}" field-name="Job Type"
								value="">
						</div>
					</div>
					<c:if test="${SourceLeadWeb_userType != 'Recruiter'}">
						<div class="clearfix" id="hideVendor" style="display: none">
							<div class="form-group">
								<label class="col-md-3"><span class="pull-left">Vendor</span><span
									class="text-danger pull-left" id="man1">*</span></label>
								<div class="col-md-9 chooRadius" id="errorvendor">
								<div class="input-group col-md-10 pull-left">
									<select class="chosen-select form-control" name="vendors"
										id="vendors" placeholder="Select Vendor" field-name="Vendor"
										onchange="getVendorContactList();removeValiationVendor();">
									</select>
									<span class="input-group-addon cursorPointer bg-blue" onclick="getVendorsByOrgLocation();addassociateEmptyVendorContact();"><i class="fa fa-refresh "></i></span>
								</div>
								<sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR')"> 
		                     	<a href="<c:url value="/addVendor" />" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
			                     </sec:authorize>
			                   </div>	
							</div>

							<div class="form-group">
								<label class="col-md-3"><span class="pull-left">Vendor
										Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
								<div class="col-md-9 chooRadius" id="errorvendorContact">
								<div class="input-group col-md-10 pull-left">
									<select class="chosen-select form-control" name="vendorcontact"
										id="vendorcontact" field-name="Vendor Contact"
										placeholder="Select Vendor Contact"
										onchange="removeValiationVendorContact();">
									</select>
									<span class="input-group-addon cursorPointer bg-blue" onclick="getVendorContactList();"><i class="fa fa-refresh "></i></span>
								</div>
								<span class="col-md-2 noPad clearfix" onclick="selectedVendor();">
			                       <sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR_CONTACT')"> 
			                          <a href="javascript:void(0)"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
			                       </sec:authorize>
		                         </span>
								</div>
							</div>
						</div>
					</c:if>
					<div class="form-group">
						<label class="col-md-3">Current Rate</label>
						<div class="col-md-9">
							<div class="input-group er ratetype">
								<div class="input-group-btn tblminwidth-75">
									<select class="selectpicker" id="currentRateCurrency" name="currentRateCurrency">
										<option ${currencyCode  == 'INR' ? 'selected' : ''}>INR</option>
										<option ${currencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
										<option ${currencyCode  == 'USD' ? 'selected' : ''}>USD</option>
										<option ${currencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
										<option ${currencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
										<option ${currencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
									</select>

								</div>
								<input type="text" id="currentRate" name="currentRate"
									value="${currentRate}" field-name="Current Rate"
									data-validation="validate_Space validate_float validate_length length1-8"
									data-validation-optional="true" value="" class="form-control">
								<div class="input-group-btn tblminwidth-75 ratetype">
									<select class="selectpicker" id="currentRateType" name="currentRateType" field-name="Rate Per">
										<option value="">Rate Per</option>
										<option ${currentRateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
										<option ${currentRateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
										<option ${currentRateType  == 'Biweekly' ? 'selected' : ''}>Bi-weekly</option>
										<option ${currentRateType  == 'SemiMonthly' ? 'selected' : ''}>Semi-Monthly</option>
										<option ${currentRateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
										<option ${currentRateType  == 'BiMonthly' ? 'selected' : ''}>Bi-Monthly</option>
										<option ${currentRateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
									</select>
								</div>
								<div class="clearfix"></div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>



					<div class="form-group">
						<label class="col-md-3">Expected Rate</label>
						<div class="col-md-9">
							<div class="input-group er ratetype">
								<div class="input-group-btn tblminwidth-75">
									<select class="selectpicker" id="expectedRateCurrencey" name="expectedRateCurrencey">
										<option ${currencyCode  == 'INR' ? 'selected' : ''}>INR</option>
										<option ${currencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
										<option ${currencyCode  == 'USD' ? 'selected' : ''}>USD</option>
										<option ${currencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
										<option ${currencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
										<option ${currencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>

									</select>

								</div>
								<input type="text" id="expectedRate" name="expectedRate" value="${expectedRate}" field-name="Expected Rate"
									 data-validation="validate_Space validate_int validate_length length1-8" data-validation-optional="true" value="" class="form-control">
								<div class="input-group-btn tblminwidth-75 ratetype">
									<select class="selectpicker" id="rateType" name="rateType" field-name="Rate Per">
										<option value="">Rate Per</option>
										<option ${rateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
										<option ${rateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
										<option ${rateType  == 'Biweekly' ? 'selected' : ''}>Bi-weekly</option>
										<option ${rateType  == 'SemiMonthly' ? 'selected' : ''}>Semi-Monthly</option>
										<option ${rateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
										<option ${rateType  == 'BiMonthly' ? 'selected' : ''}>Bi-Monthly</option>
										<option ${rateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
									</select>
								</div>
								<div class="clearfix"></div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>


					<div class="form-group">
						<label class="col-md-3">Employer's</label>
						<div class="col-md-9">
							<input type="text" class="form-control" id="candidateEmployers"
								name="candidateEmployers" value="${candidateEmployers}"
								field-name="Employers"
								data-validation="validate_Space validate_length length2-250"
								data-validation-optional="true" value="">
						</div>
					</div>




					<div class="form-group">
						<label class="col-md-3">Notice Period</label>
						<div class="col-md-9">
							<select class="form-control valid selectpicker" id="noticePeriod" name="noticePeriod">
								<option value="">---Select Notice Period---</option>
								<option
									${noticePeriod  == 'Immediately available' ? 'selected' : ''}>Immediately
									available</option>
								<option ${noticePeriod  == '15 Days' ? 'selected' : ''}>15
									Days</option>
								<option ${noticePeriod  == '1 Month' ? 'selected' : ''}>1
									Month</option>
								<option ${noticePeriod  == '2 Months' ? 'selected' : ''}>2
									Months</option>
								<option ${noticePeriod  == '3 Months' ? 'selected' : ''}>3
									Months</option>
							</select>

						</div>
					</div>

					<div class="clearfix"></div>
					<div class="form-group innerForm_formGroup">

						<div class="form-group">
							<label class="col-md-3">Attach Resume</label>
							<div class="col-md-9 positionRelative">
								<div class="input-group col-md-12" id="uploadFileGroup"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadBtn" class="upload"> </span>
									</label> 
									
									<input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Resume ..." field-name="Upload Resume"
										data-validation="validatedoc " data-validation-optional="true">
										<div class="clearUpload"></div>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Skills</label>
							<div class="col-md-9 mySkillsUl">
								<select multiple data-placeholder="Select Skills" class="chosen-select form-control" name="candidateSkills" id="candidateSkills" data-validation-optional="true">
									<c:forEach var="skill" items="${skillsList}">
										<option value="${skill.name}">${skill.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Immigration Status</label>
							<div class="col-md-9">
								<select multiple class="chosen-select form-control" name="visa" id="visa" data-placeholder="Select Visa" data-validation-optional="true">
									<c:forEach var="visaList" items="${visaList}">
										<c:set var="NUM" value="${0}" />
										<c:forEach var="existedVisa" items="${requirement.visaType}">
											<c:if test="${existedVisa eq visaList}">
												<c:set var="NUM" value="${1}" />
												<option selected value="${visaList}">${visaList}</option>
											</c:if>
										</c:forEach>

										<c:if test="${NUM == 0}">
											<option value="${visaList}">${visaList}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Primary Skill</label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="primarySkill" name="primarySkill" value="${primarySkill}"
									field-name="Primary Skill" data-validation="validate_Space validate_length length2-200" data-validation-optional="true">
							</div>
						</div>

					</div>

					<div class="form-group innerForm_formGroup">
						<div class="form-group">
							<label class="col-md-3">Education</label>
							<div class="col-md-9 mySkillsUl">
								<select multiple data-placeholder="Select Education" class="chosenone-selectone form-control"
									name="candidateEducation" id="candidateEducation" data-validation="validate_Space" data-validation-optional="true">
									<c:forEach var="edu" items="${educationsList}">
										<option value="${edu.name}">${edu.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Summary</label>
							<div class="col-md-9">
								<textarea rows="2" class="form-control" id="summary" name="summary" value="${summary}" field-name="Summary"
									data-validation=" validate_length length2-5000" data-validation-optional="true"></textarea>
							</div>
						</div>
					</div>

					<div class="clearfix"></div>
					<div class="form-group">
						<label class="col-md-3"></label>
						<div class="col-md-9">
							<div class="checkbox checkBox">
								<label> <input type="checkbox" value="Y" name="inviteEmail"> Invite to SourceLead
								</label>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<!-- this "candidateCreationType" field is used for candidate creation type -->
         			<input type="hidden" id="candidateCreationType" name="candidateCreationType" value="manual">
			
			<div class="form-footer clearfix">
				<div class="col-lg-12">
					<div class="pull-right">
						<input type="button" value="Save" id="store_candidate" onclick="storeCandidate();" class="btn btn-primary"> 
						<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/associatesList'/>'" class="btn btn-default ml">
					</div>
				</div>
			</div>

		</form:form>
     
    </div>
    
    <!-- Content Area Ends--> 
  </section>
	<!-- Footer Area -->
	<jsp:include page="../masterFooter.jsp" />
	<!-- Footer Area -->
	
	<%-- 
     @author : prasad k.
     this script is for save/store candidate.
     1.usage     : onclick.
     2.parameter : csrf token,con.
     3.ajax url implementation in CandidateRestController.
    --%>
    
 <script>
 $(function() {
		$("#uploadBtn").change(function() {
			var sourceleadUrl = '${sourceleadUrl}';
			fileUpload("uploadBtn", "resumes", "addcandidate_form");
		});
	 });
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
	function storeCandidate() {
		
		rateValidations();
		
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		
		 if ($('#addcandidate_form').validate(false, validationSettings)){ 
			
			var sourceleadUrl = '${sourceleadUrl}';
			var form = $('#addcandidate_form')[0];
			var formData = new FormData(form); 
			//It will escape all the special characters
			$("#store_candidate").attr("disabled", "disabled");
			$("#store_candidate").addClass('button').val('Processing..');
			$.ajax({
						type : "POST",
		        	    data: formData,
		    	        processData: false,
		    	        contentType:false,
						url : sourceleadUrl+"/storeCandidate?"+tokenParamName+"="+tokenValue+"",
						success : function(result) {
								location.replace(sourceleadUrl+"/associatesList");
						},
					});
			return true;
		 }else{ 
				return false;
		 } 
	} 
	
	$('body').on('blur', '#addcandidate_form', function() {
		$("#addcandidate_form").showHelpOnFocus();
		$("#addcandidate_form").validateOnBlur(false, validationSettings);
	});
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

<!-- Country,State,City Script Starts Here -->
<script type="text/javascript">
function validate_fileupload(fileName)
{
    var allowed_extensions = new Array("docx","doc","pdf","rtf");
    var file_extension = fileName.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i]==file_extension)
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>

<!-- Eductions and Skills Autocomplete Scripts Starts Here -->
<script>
  $(function() { 
	  tagIt("candidateSkills", 'Press Enter-Key Or Comma(,) To Add New Skill:', 'tagItBykeyForSkills','skills','Select Skills');
		tagIt("candidateEducation", 'Press Enter-Key Or Comma(,) To Add New Education:', 'tagItBykeyForSkills','skills','Select Education');;

	});
  </script>

<script type="text/javascript">
    $("#candidateSkills").chosen().change(function(event) {
    	 var sourceleadUrl ='${sourceleadUrl}';
    	var allSkills = $(event.target).val();
    	$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/profileBySkillSet?"+tokenParamName+"="+tokenValue+"&allSkills="+allSkills, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		var primarySkill = response.object;
	        	    $("#primarySkill").val(primarySkill);
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        },
	    });
    });
   	$("#candidateSkills").val('').trigger("chosen:updated");
	$("#candidateEducation").val('').trigger("chosen:updated");
</script> 
<!-- Eductions and Skills Autocomplete Scripts End Here -->

<!-- Candidate Email validation script starts here -->

<script type="text/javascript">
 function validateCandidateEmail() {
	 var email = document.getElementById("email").value; 
	 $('#emailDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(email != null && email != ""){
		
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateCandidateEmail?"+tokenParamName+"="+tokenValue+"&email="+email+"&candidateType=N", 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		if(response.object == 'true'){
			        		  $('#emailDiv').find('p.jquery_form_error_message').remove();
							  $("input#email").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
							  document.getElementById("email").setAttribute('record-exist','yes');
							  document.getElementById("email").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
			        	}
			        	else{
			        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			        		 $("#store_candidate" ).addClass('button').val('Save');
	                         $("#store_candidate").removeAttr("disabled");
			        	}
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
 
 <!-- Candidate Email validation script ends here -->
 
 <!-- Experience Years and Months validation Script years greater than  20 the months dropdown disabled script here -->
 <script type="text/javascript">
 function disableMonths() {
     $("#experienceYears").change(function() {
         if ($("#experienceYears").val() == '50'){
        	 $("#experienceMonths").val('');  
        	 $('#experienceMonths').selectpicker('refresh');
        	 $('#experienceMonths').attr("disabled", true);
             $('#experienceMonths').val("disabled", true);
         }else{
        	 $("#experienceMonths").val('');  
        	 $('#experienceMonths').selectpicker('refresh');
        	 $("#experienceMonths").attr("disabled", false); 
         }
     });
 }
 
 var selectedFile = document.getElementById("uploadBtn");
	      selectedFile.addEventListener("change", function() {
	      	 $("#uploadFile").focus();
	      	 if(validate_fileupload(this.value)){
	      		 var docSize = this.files[0].size;
	     	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	     	      if(docSize > 2097152){
	     	    	  document.getElementById("uploadFile").value = '';
	     	    	 /*  $("#uploadFileGroup").append("<p class='jquery_form_error_message'></p>"); */
	     	    	  $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
	     			  $("input#uploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
	     			  document.getElementById("uploadFile").setAttribute('record-exist','yes');
	     			  document.getElementById("uploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	     	      }else{
	     	    	 $('input#uploadFile').removeAttr( "record-exist record-exist-errormsg");
	        		 $('#uploadFileGroup').find('p.jquery_form_error_message').remove(); 
	      	    }
	      	 }
	      });

	     /* upload file script */
	     document.getElementById("uploadBtn").onchange = function () {
	     	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	       // document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();
	     };

 
</script>
<!-- Experience Years and Months validation Script End here -->

<script>
function getDesignationType(designationType){
	var existingDesignationType = '${requirement.designationType}';
	if(designationType.value == 'Other'){
		 document.getElementById("designationTypeDiv").style.display="block";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationOtherType").attr("data-validation", "validate_Space validate_length length2-250");
	}else{
		document.getElementById("designationTypeDiv").style.display="none";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationTypeDiv").find('p.jquery_form_error_message').remove();
		  if(existingDesignationType ==null || existingDesignationType == "" || existingDesignationType == "undefined" || existingDesignationType != 'Other'){
			  $("#designationOtherType").val('');
		  }
	}
	
	if(designationType.value == 'C2C'){
		 document.getElementById("hideVendor").style.display="block";
		 $('#vendors').attr("data-validation","required"); 
		 $('#vendorcontact').attr("data-validation","required"); 
		 
		 document.getElementById("man1").style.display="block";
		 document.getElementById("man2").style.display="block";
		 
		  getVendorsByOrgLocation();
	}else{
		 document.getElementById("hideVendor").style.display="none";
		 $('#vendors').removeAttr("data-validation","required"); 
		 $('#vendorcontact').removeAttr("data-validation","required"); 
	}
	
	if(designationType.value == 'C2C-1099'){
		 document.getElementById("hideVendor").style.display="block";
		 document.getElementById("man1").style.display="none";
		 document.getElementById("man2").style.display="none";
		 $('#vendors').attr("data-validation-optional", "true"); 
		 $('#vendorcontact').attr("data-validation-optional", "true");  
		 $("#errorvendor").find("p.jquery_form_error_message").remove();
			$("#errorvendorContact").find("p.jquery_form_error_message").remove();
		  getVendorsByOrgLocation();
	}else{
		// document.getElementById("hideVendor").style.display="none";
		 $('#vendors').removeAttr("data-validation-optional", "true"); 
		 $('#vendorcontact').removeAttr("data-validation-optional", "true");  
	} 
	
	
	$('#designationType + p').remove();
}
</script>


<!-- This script for Current Rate ,Rate Type,Expected Rate, Expected Rate Type validation checks -->
<script>

function rateValidations(){
	var rateType = document.getElementById("rateType").value; 
	if(rateType != null && rateType != ""){
		 $('#expectedRate').removeAttr('data-validation-optional');
		 $('#expectedRate').attr("data-validation","required validate_Space validate_int validate_length length1-8"); 
	}else{
		 $('#expectedRate').removeAttr('data-validation');
		 $('#expectedRate').attr("data-validation-optional","true");
	}

	var expectedRate = document.getElementById("expectedRate").value; 
	if(expectedRate != null && expectedRate != ""){
		
		 $('#rateType').removeAttr('data-validation-optional');
		 $('#rateType').attr("data-validation","required"); 
		 $('#expectedRate').attr("data-validation","required validate_Space validate_int validate_length length1-8");
	}else{
	$('#rateType').removeAttr('data-validation');
	$('#rateType').attr("data-validation-optional","true"); 
	}



	var currentRateType = document.getElementById("currentRateType").value; 
		if(currentRateType != null && currentRateType != ""){
			$('#currentRate').removeAttr('data-validation-optional');
			 $('#currentRate').attr("data-validation","required validate_Space validate_int validate_length length1-8"); 

		}else{
			$('#currentRate').removeAttr('data-validation');
		 	$('#currentRate').attr("data-validation-optional","true"); 
		} 


	var currentRate = document.getElementById("currentRate").value; 
		 if(currentRate != null && currentRate != ""){
			
			 $('#currentRateType').removeAttr('data-validation-optional');
			 $('#currentRateType').attr("data-validation","required"); 
			 $('#currentRate').attr("data-validation","required validate_Space validate_int validate_length length1-8");

			 
		 }else{
			 
				 $('#currentRateType').removeAttr('data-validation');
				 $('#currentRateType').attr("data-validation-optional","true"); 
		 
		 }
		 
	}
	</script>

<script>
 function getVendorsByOrgLocation(){
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getVendorsByOrgLocation?", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#vendors').empty();
		        	$('#vendors').append('<option value="">--select Vendor --</option>');
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            		$('#vendors').append('<option value='+opt.id+' >'+opt.orgName+'</option>');
		            }
		            $('#vendors').trigger("chosen:updated");
		            
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
	var orgRelationID = $('#vendors').val();
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
			    type: "POST",
		        url: sourceleadUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
		        success: function(json) {
		        //	var json = JSON.parse(data);
		        	$('#vendorcontact').empty();
		        	$('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
		             for(var i=0;i<json.data.length;i++){
		            	 var opt = json.data[i];
		            	 $('#vendorcontact').append('<option value='+opt.id+' >'+opt.spocName+' ('+opt.ownerName+')</option>');
		            } 
		            $('#vendorcontact').trigger("chosen:updated");
		        }
		 }); 
 }
 </script>

 <script>
 function employementType(type){
if(type == 'C2C' || type == 'C2C-1099'){
	 document.getElementById("hideVendor").style.display="block";
	 $('#vendors').attr("data-validation","required"); 
	 $('#vendorcontact').attr("data-validation","required"); 
	// $("#vendors").attr("data-validation", "true");
	  getVendorsByOrgLocation();
}else{
	 document.getElementById("hideVendor").style.display="none";
	 $('#vendors').removeAttr("data-validation","required"); 
	 $('#vendorcontact').removeAttr("data-validation","required"); 
}
 }
 </script>
 
 <script>
 function getVendors(vendorName,vendorOrgRelationId){
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getVendorsByOrgLocation?", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#vendors').empty();
		        	$('#vendors').append('<option value="">--select Vendor --</option>');
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            	  if(vendorName != null && vendorName != 'undefined' && vendorName  != ''){
		            		  $('#vendors').append('<option '+ (opt.id == vendorOrgRelationId ? "selected" : '') +' value="'+opt.id+'">'+opt.orgName+'</option>');
			            	}else{
			            		$('#vendors').append('<option value='+opt.id+' >'+opt.orgName+'</option>');	
			            	}
		            }
		            $('#vendors').trigger("chosen:updated");
		        }
		 });
 }
 </script>
 <script>
 function vendorContactList(vendorContactName,vendorOrgRelationId,vendorSpocId){
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
			    type: "POST",
		        url: sourceleadUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+vendorOrgRelationId+"&type=Vendor",  
		        success: function(json) {
		        	//var json = JSON.parse(data);
		        	$('#vendorcontact').empty();
		        	$('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
		             for(var i=0;i<json.data.length;i++){
		            	 var opt = json.data[i];
		            	 if(vendorContactName != null && vendorContactName != 'undefined' && vendorContactName  != ''){
		            		 $('#vendorcontact').append('<option '+ (opt.id == vendorSpocId ? "selected" : '') +' value="'+opt.id+'">'+opt.spocName+' ('+opt.ownerName+')</option>');
			            	}else{
			            		$('#vendorcontact').append('<option value='+opt.id+' >'+opt.spocName+'</option>');
			            	}
		            	 
		            } 
		            $('#vendorcontact').trigger("chosen:updated");
		        }
		 }); 
 }
 </script>
 
 
 <script>
 $(function() {
		$("#uploadBtn").change(function() {
			fileUpload("uploadBtn", "resumes", "addcandidate_form");
			$(".clearUpload").show();
		});
	 });
 </script>

 <script>
function addassociateEmptyVendorContact(){
	$("#vendorcontact").empty();
	 $('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
   $('#vendorcontact').trigger("chosen:updated");
}
</script>
<script>
	 function selectedVendor(){
		 var vid =  $('#vendors').val();
		 window.open(sourceleadUrl+"/addVendorContact?vid="+vid,'_blank');
	 }
	 </script>

 
 <script>
	$(".clearUpload").click(function(){
		$("#uploadFile").val("");
		 $(".clearUpload").hide();
	        $("#addcandidate_form input[data-id*=uploadBtn]").remove();
	});
</script>

