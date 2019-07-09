<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link href="/resources/css/candidate-common.css?staticTime=${staticTime}" rel="stylesheet">
	<script type="text/javascript">
		window.onload = function () {
			initLocationAuto('profileLocation','profileCountry','profileState','profileCity','profileZipCode','profileLatitude','profileLongitude','','address');
			disableMonths();
		 };
	</script>
	 
	 <script>
		$(document).ready(function() {
			document.getElementById("candidate_form").reset();
		});
	</script>
	 
<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Profile Update</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Profile Update</div>
  </section>
  <div class="content-area content-new clearfix">
  <div id="loading"></div> 
  <form:form id="candidate_form" name="candidate_form"  enctype='multipart/form-data' >
    <div class="row form-horizontal mt myForm">
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
        
        <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
        <div class="col-md-12 clearfix">
        <div class="well">
          <div class="input-group col-md-6" id="uploadFileGroup" style="margin:0 auto; position:relative;">
            <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File<span class="text-danger">&nbsp;*</span>
              <input type="file" name="uploadBtn" id="uploadBtn" class="hidden upload">
              </span> 
           </label>

             <input type="text" class="form-control" readonly id="uploadFile" name="docFile" type="file"  name="" placeholder="Upload Resume ..." field-name="Resume" data-validation="required validate_Space validatedoc " >
         </div>
          </div>
        </div>
        <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
        
        <div class="form-group">
          <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="firstName" name="firstName" readonly="readonly" value="${SourceLeadWeb_loginUserFirstName}"  field-name="First Name" data-validation="required validate_AlphaNumber validate_Space validate_length length1-100" >
          </div>
        </div>
        
        <div class="form-group">
						<label class="col-md-3">Middle Name</label>
						<div class="col-md-9">
							<input type="text" class="form-control" id="middleName"
								name="middleName" value="" field-name="Middle Name"
								data-validation="validate_AlphaNumber validate_length length1-100"
								data-validation-optional="true" value="">
						</div>
					</div>
        
        <div class="form-group">
          <label class="col-md-3">Last Name</label>
          <div class="col-md-9">
          <c:if test="${!empty SourceLeadWeb_loginUserLastName}">
           <input type="text" class="form-control" readonly="readonly" value="${SourceLeadWeb_loginUserLastName}" field-name="Last Name" data-validation="validate_AlphaNumber validate_Space validate_length length1-100" data-validation-optional="true">
          </c:if>
          
          <c:if test="${empty SourceLeadWeb_loginUserLastName}">
           <input type="text" class="form-control" id="lastName" name="lastName" value="${SourceLeadWeb_loginUserLastName}"  field-name="Last Name" data-validation="validate_AlphaNumber validate_Space validate_length length1-100" data-validation-optional="true">
          </c:if>
          </div>
        </div>
        <div class="form-group" id="emailDiv">
          <label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="email" name="email" readonly="readonly" value="${SourceLeadWeb_loginUserEmail}"  field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email">
          <span class="hidden">Valid Email</span>
          </div>
        </div>
						
        <div class="form-group">
          <label class="col-md-3">Mobile</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="mobilePhone" name="mobilePhone" value="${phone}"  field-name="Mobile Number" data-validation="validate_Space validate_length length9-20 validate_phone" data-validation-optional="true" value="">
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-md-3">Alternative Number</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="alternateMobilePhone" name="alternateMobilePhone"   field-name="Alternative Number" data-validation="validate_Space validate_length length9-20 validate_phone" data-validation-optional="true" value="">
          </div>
        </div>
        
        
        <div class="form-group">
          <label class="col-md-3">Address<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9" id="locationField">
          <div class="positionRelative">
          <input class="form-control positionRelative" id="profileLocation" name="location" placeholder="Enter your address"  type="text" field-name="Address" data-validation="required"></input>
          <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('profileLocation','profileCountry','profileState','profileCity','profileZipCode','address');"></i>
          </div>
          </div>
        </div>
		
		 <div class="form-group">
          <label class="col-md-3">Street Address</label>
          <div class="col-md-9">
           <input type="text" class="form-control" id="address" name="address" value="${address}"  field-name="Street Address" placeholder="Enter your Street Addres" data-validation="validate_Space validate_length length2-250" data-validation-optional="true">
          </div>
        </div>
		
		<div class="form-group">
          <label class="col-md-3">City</label>
          <div class="col-md-9">
          <input class="form-control" id="profileCity" name="city" placeholder="Enter your City" type="text"></input>
          </div>
        </div>
        
         <div class="form-group">
          <label class="col-md-3">State</label>
          <div class="col-md-9">
          <input class="form-control" id="profileState" name="state" placeholder="Enter your State" type="text"></input>
          </div>
        </div>
        
        
		<div class="form-group">
          <label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9">
          <input class="form-control" id="profileCountry" name="country" placeholder="Enter your Country" field-name="Country"  data-validation="required"  value ="${loginCountryName}" type="text"></input>
          </div>
        </div>
        
        
        <div class="form-group">
          <label class="col-md-3">ZipCode</label>
          <div class="col-md-9">
          <input class="form-control" id="profileZipCode" name="zipcode" placeholder="Enter your Zip code" type="text"></input>
          </div>
        </div>
        
        	<div class="form-group">
                      <label class="col-md-3">Date of Birth</label>
                         <div class="col-md-9">
                           <div class="input-group">
                              <input type="text" value="" name="dateofBirth" class="form-control calendar" id="dateofBirth" readonly="readonly">
                              <span class="input-group-addon" onClick="clearDateOfBirth();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                           </div>
                        </div>
                  </div>
					
					<div class="form-group">
						<label class="col-md-3">Marital Status</label>
						<div class="col-md-9">
						<label class="paddingRight"><input type="radio"  value="Single" name="maritalStatus"><span class="mr5"></span>Single</label>
						<label class="paddingLeft paddingRight"><input type="radio"  value="Married" name="maritalStatus"><span class="mr5"></span>Married</label>
						<label class="paddingRight"><input type="radio"  value="Divorsed" name="maritalStatus"><span class="mr5"></span>Divorsed</label>
						<label class="paddingLeft paddingRight"><input type="radio"  value="Separated" name="maritalStatus"><span class="mr5"></span>Separated</label>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3">Gender</label>
						<div class="col-md-9" id= "errorGenderMsg">
						<label class="paddingRight"><input type="radio"  value="Male"  name="gender" onblur="genderCheckValidation(this)"><span class="mr5"></span>Male</label>
						<label class="paddingLeft paddingRight"><input type="radio"  value="Female" name="gender" onblur="genderCheckValidation(this)"><span class="mr5"></span>Female</label>
						<label class="paddingRight"><input type="radio"  value="Transgender" name="gender" onblur="genderCheckValidation(this)"><span class="mr5"></span>Transgender</label>
						<input type="hidden"  id="genderValue" name="genderValue"  field-name="Gender" data-validation="required"/> 		
						</div>
					</div>
        
        <!-- <div class="form-group">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
				 <select class="chosen-select form-control select-bg" name="country" id="professionalCountry" field-name="Country"  >
                 </select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-3">State</label>
				<div class="col-md-9">
				<select  class="chosen-select form-control" name="state" id="professionalState" field-name="State"  >
                 </select>
				</div>
		 </div>
		
		<div class="form-group">
			<label class="col-md-3">City</label>
			<div class="col-md-9">
			     <select  class="chosen-select form-control" name="city" id="professionalCity" field-name="City"  >
                 </select>
			</div>
		</div> -->
        
       
        
        <div class="form-group">
         <label class="col-md-3">Designation</label>
         <div class="col-md-9 ratetype ratetypeError">
             <input type="text" id="designation" name="designation" value="${designation}" field-name="Designation"  data-validation="validate_Space validate_Name validate_length length2-250" data-validation-optional="true"  value="" class="form-control">
         </div>
       </div>
       
        <div class="form-group">
         <label class="col-md-3">Employment type<span class="text-danger">&nbsp;*</span></label>
         <div class="col-md-9">
              <select class="selectpicker form-control" id="designationType" field-name="Employment Type" name="designationType" data-validation="required" onChange="getDesignationType(this);">
                <option value="">--Select--</option>
                 <option>C2C</option>
                 <option>W-2</option>
                 <option>C2H</option>
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
             		<select class="form-control selectpicker" id="experienceYears" name="experienceYears" onchange="disableMonths();">
             		  <option value="">Years</option>
             		  <c:forEach begin="0" end="24" varStatus="loop">
						    <option>${loop.index}</option>
						</c:forEach>
             		     <option>25+</option>
             		</select>
             </div>
             
             <div class="col-xs-6 col-md-6">
             		<select class="form-control selectpicker" id="experienceMonths" name="experienceMonths">
             		<option value="">Months</option>
             		 <c:forEach begin="0" end="11" varStatus="loop">
						    <option>${loop.index}</option>
						</c:forEach>
             		</select>
             </div>
            </div>
          </div>
        </div>
       
        <div class="form-group designationTypeDiv" id="designationTypeDiv" style="display:none">
            <label class="col-md-3">Employment Type</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="designationOtherType" name="designationOtherType" value="${candidate.designationOtherType}"  field-name="Job Type" value="">
            </div>
          </div>
       
       
       <div class="form-group">
            <label class="col-md-3">Current Rate</label>
            <div class="col-md-9">
             <div class="input-group er ratetype ratetypeError">
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
                 <input type="text" id="currentRate" name="currentRate" value="${currentRate}" field-name="Current Rate" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true" value="" class="form-control">
                <div class="input-group-btn tblminwidth-75 ratetype ratetypeError">
                  <select class="selectpicker" id="currentRateType" name="currentRateType" field-name="Rate Type">
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
                 <div class="clearfix"></div> <div class="clearfix"></div>
              </div>
            </div>
          </div>
       
        
        
        <div class="form-group">
            <label class="col-md-3">Expected Rate</label>
            <div class="col-md-9">
             <div class="input-group er ratetype ratetypeError">
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
                 <input type="text" id="expectedRate" name="expectedRate" value="${expectedRate}" field-name="Expected Rate" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true" value="" class="form-control">
                <div class="input-group-btn tblminwidth-75 ratetype ratetypeError">
                  <select class="selectpicker" id="rateType" name="rateType" field-name="Rate Type">
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
                 <div class="clearfix"></div> <div class="clearfix"></div>
              </div>
            </div>
          </div>
        
        
         <div class="form-group">
          <label class="col-md-3">Employer's</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="candidateEmployers" name="candidateEmployers" value="${candidateEmployers}"  field-name="Employers" data-validation="validate_Space validate_length length2-250" data-validation-optional="true"  value="">
          </div>
        </div>

        <div class="form-group">
         <label class="col-md-3">Notice Period</label>
          <div class="col-md-9">
               <select class="form-control valid selectpicker" id="noticePeriod" name="noticePeriod">
                 <option value="">--Select--</option>
                 <option ${noticePeriod  == 'Immediately available' ? 'selected' : ''}>Immediately available</option>
                 <option ${noticePeriod  == '15 Days' ? 'selected' : ''}>15 Days</option>
                 <option ${noticePeriod  == '1 Month' ? 'selected' : ''}>1 Month</option>
                 <option ${noticePeriod  == '2 Months' ? 'selected' : ''}>2 Months</option>
                 <option ${noticePeriod  == '3 Months' ? 'selected' : ''}>3 Months</option>
               </select>
           </div>
         </div>
      <div class="clearfix">
          <div class="form-group">
              <label class="col-md-3">Skills</label>
              <div class="col-md-9">
                <select multiple data-placeholder="select Skills" class="chosen-select form-control" name="candidateSkills" id="candidateSkills"  data-validation-optional="true">
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
               		<c:set var = "NUM" value = "${0}"/>
               		<c:forEach var="existedVisa" items="${requirement.visaType}">
					    <c:if test="${existedVisa eq visaList}">
					    	<c:set var = "NUM" value = "${1}"/>
					       <option  selected value="${visaList}">${visaList}</option>
					    </c:if>
					</c:forEach>
					
					<c:if test="${NUM == 0}">
						<option  value="${visaList}">${visaList}</option>
					</c:if>
                </c:forEach>
                </select>
              </div>
            </div>
            
            
          <div class="form-group">
          <label class="col-md-3">Primary Skill</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="primarySkill" name="primarySkill" value="${primarySkill}"  field-name="Primary Skill" data-validation="validate_Space validate_length length2-200" data-validation-optional="true">
          </div>
         </div>
        
        
         <div class="form-group">
              <label class="col-md-3">Education</label>
              <div class="col-md-9">
                <select multiple data-placeholder="select Education" class="chosenone-selectone form-control" name="candidateEducation" id="candidateEducation" data-validation="validate_Space" data-validation-optional="true">
                <c:forEach var="edu" items="${educationsList}">
	                     <option value="${edu.name}">${edu.name}</option>
                </c:forEach>
                </select>
              </div>
            </div>
            </div>

        <div class="form-group mb">
          <label class="col-md-3">Summary</label>
          <div class="col-md-9">
            <textarea rows="2" class="form-control" id="summary" name="summary" value="${summary}" field-name="Summary" data-validation=" validate_length length2-5000" data-validation-optional="true"></textarea>
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-md-3"></label>
         <!--  <div class="col-md-9 checkbox checkBox">
            <label>
              <input type="checkbox" value="">
              Send Invitation </label>
          </div> -->
        </div> 
        <div class="clearfix"></div>
      </div> 
    </div>
    
    <div class="clearfix"></div>
    <div class="col-md-12">
    <div class="form-footer clearfix mt">
      
        <div class="pull-right mr">
          <input type="button" value="Save" id="store_candidate" onclick="storeCandidate();" class="btn btn-primary">
          <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/professionalProfile'/>'" class="btn btn-default ml">
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
<script type="text/javascript">
	function storeCandidate() {
		var candidateId = '${SourceLeadWeb_candidateId}';
		rateValidations();
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		
		 if ($('#candidate_form').validate(false, validationSettings)){ 
			
			var form = $('#candidate_form')[0];
      		var formData = new FormData(form); 
			
			$("#store_candidate").attr("disabled", "disabled");
			$("#store_candidate").addClass('button').val('Processing..');

			$.ajax({
						type : "POST",
		        	    data: formData,
		        	    processData: false,
		    	        contentType:false,
						url : sourceleadUrl+"/storeProfessionalParseCandidate?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,
						success : function(response) {
							if (response && response.response === "INVALID_DATA") {
			                    $("#store_candidate").removeAttr("disabled");
			                    $("#store_candidate").removeAttr('button').val('Save');
			                    showError(result.responseMessage);
			                } else if (response.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (response.response === "SUCCESS") {
			                	location.replace(sourceleadUrl+"/professionalProfile");	
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
	
	$('body').on('blur', '#candidate_form', function() {
		$("#candidate_form").showHelpOnFocus();
		$("#candidate_form").validateOnBlur(false, validationSettings);
	});
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
</script>
<script>
  $( function() {
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  // written common method in slead-common.js for date formats start
	  var finalDateFormat = getJSDateFormat(locationDate);
	  //end
	  $('#dateofBirth').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true,
	  	  yearRange: "-100:+0",
	  	  maxDate:0
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
  } );
  
  function clearDateOfBirth(){
		$("#dateofBirth").val("");
		};
		
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

<script>
  $(function() { 
		tagIt("candidateSkills", 'Press Enter-Key Or Comma(,) To Add New Skill:', 'tagItBykeyForSkills','skills','Select Skills');
		tagIt("candidateEducation", 'Press Enter-Key Or Comma(,) To Add New Education:', 'tagItBykeyForSkills','skills','select Education');

	});
 </script>
 
<!-- Eductions and Skills Autocomplete Scripts Starts Here -->
<script type="text/javascript">
		    $("#candidateSkills").chosen().change(function(event) {
		    	var allSkills = $(event.target).val();
		   
		    	
		    	$.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/profileBySkillSet?${_csrf.parameterName}=${_csrf.token}&allSkills="+allSkills, 
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
	 if(email != null && email != ""){
		
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateCandidateEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&candidateType=Candidate", 
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
         if ($("#experienceYears").val() == '25+'){
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
}
	
	 var educationList = [];
	   var skillList = [];
	   $(function() {
		 var i= 0;
		 <c:forEach var="skill" items="${skillsList}">
		 	skillList[i++] = "${skill.name}";
		 </c:forEach>
		 var i= 0;
		 <c:forEach var="edu" items="${educationsList}">
		 	educationList[i++] = "${edu.name}";
		 </c:forEach>
		 
		 uploadResumeOnSelect("candidate_form");
		 
	 });
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
		 $('#rateType').removeAttr('data-validation');
		 $('#rateType').attr("data-validation-optional","true"); 
	} 

var expectedRate = document.getElementById("expectedRate").value; 
	if(expectedRate != null && expectedRate != ""){
		 $('#rateType').removeAttr('data-validation-optional');
		 $('#rateType').attr("data-validation","required"); 
		 $('#expectedRate').attr("data-validation", "required validate_Space validate_int validate_length length1-8");
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
$("#uploadBtn").change(function() {
    fileUpload("uploadBtn", "resumes", "candidate_form");
});
</script>
