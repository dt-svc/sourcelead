<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<link href="/resources/css/candidate-common.css?staticTime=${staticTime}" rel="stylesheet">

	
	<script type="text/javascript">
		window.onload = function () {
			initLocationAuto('associateLocation','associateCountry','associateState','associateCity','associateZipCode','associateLatitude','associateLongitude','','address');
			disableMonths();
			getUsersByRole();
			var type = '${type}';
		 };
	</script>
	 
	 <script>
		$(document).ready(function() {
			getCandidatesList('Candidate');
			document.getElementById("candidate_form").reset();
		});
	</script>
<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Add Associate</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='/associatesList'/>"> Associate List</a> &raquo; Add Associate</div>
  </section>
  <div class="content-area content-new clearfix">
  <div id="loading"></div> 
  <form:form id="candidate_form" name="candidate_form">
    <div class="row form-horizontal mt myForm">
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
        
        <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
        <div class="col-md-12 clearfix">
        <div class="well clearfix">
        <div class="row">
      
          <div class="col-md-6 radioInline hidden">
	          <label class="mr"><input type="radio"  id="canId" checked="true" value="Candidate" name="candidateType" onclick="getCandidatesList(this.value);" >Candidates</label>
	          <label class="mr"><input type="radio"  id="newId" value="New" name="candidateType" onclick="getCandidatesList(this.value);" >Add New Associate </label>
	          <label class="mr"><input type="radio"  id="parsId" value="parseCandidate" name="candidateType" onclick="parseResume();getCandidatesList(this.value);">Import Associate</label>
          </div>
          <input type="hidden" name="isAssociate" id="isAssociate" value="Y"/> 
          <div id="parsResumeDivId"></div>
          
          </div>
          
          <div class="row mt10">
          <div class="col-md-6" id="selectCandidateDiv" style="display: block;">
           <select class="form-control chosen-select" onchange="getCandidateDetails(); getCandidateResumes();"  name="candidateId" id="candidateId" data-validation="required" field-name="Applicant" class="form-control  block mt10">
             <option value="">-Select Candidate-</option>
           </select>
          </div>
          
          <div class="col-md-6 hidden" id="resumesDiv"  style="display: none;">
          <select id="candidateResumesList" name="candidateResume" field-name="Resume" class="form-control block"></select>
          </div>
          </div>
          </div>
        </div>
        <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
        
        <div class="form-group">
          <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="firstName" name="firstName" value="${firstName}"  field-name="First Name" data-validation="required validate_AlphaNumber validate_Space validate_length length1-100" value="">
            <div class="input-group hidden">
              <div class="input-group-btn">
                <button type="button" class="btn btn-danger dropdown-toggle"
                                	data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Action <span class="caret"></span> </button>
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
           <input type="text" class="form-control" id="lastName" name="lastName" value="${lastName}"  field-name="Last Name" data-validation="validate_AlphaNumber validate_Space validate_length length1-100" data-validation-optional="true" value="">
          </div>
        </div>
        
        <div class="form-group" id="emailDiv">
          <label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="email" name="email" value="${email}"  field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email" onblur="validateCandidateEmail();" readonly="true">
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
          <input class="form-control positionRelative" id="associateLocation" name="location" placeholder="Enter your address" type="text" field-name="Address" data-validation="required"></input>
          <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('associateLocation','associateCountry','associateState','associateCity','associateZipCode','address');"></i>
          </div>
          </div>
        </div>
		
		<div class="form-group">
          <label class="col-md-3">Street Address</label>
          <div class="col-md-9">
           <input type="text" class="form-control" id="address" name="address" value="${address}"  placeholder="Enter your Street Addres" field-name="Street Address" data-validation="validate_Space validate_length length2-250" data-validation-optional="true">
          </div>
        </div>
        
		 <div class="form-group">
          <label class="col-md-3">City</label>
          <div class="col-md-9">
          <input class="form-control" id="associateCity" name="city" placeholder="Enter your City" type="text" ></input>
          </div>
        </div>
        
         <div class="form-group">
          <label class="col-md-3">State</label>
          <div class="col-md-9">
          <input class="form-control" id="associateState" name="state" placeholder="Enter your State" type="text" ></input>
          </div>
        </div>
        
		<div class="form-group">
          <label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9">
          <input class="form-control" id="associateCountry" name="country" placeholder="Enter your Country" field-name="Country" data-validation="required" value ="${loginCountryName}"  type="text" ></input>
          </div>
        </div>
        
       
       
        <div class="form-group">
          <label class="col-md-3">ZipCode</label>
          <div class="col-md-9">
          <input class="form-control" id="associateZipCode" name="zipcode" placeholder="Enter your Zip code" type="text" ></input>
          </div>
        </div>
        
		
		<div class="form-group hidden">
          <label class="col-md-3">Lat</label>
          <div class="col-md-9">
          <input class="form-control" id="associateLatitude" name="latitude" type="text"></input>
          </div>
        </div>
        
        <div class="form-group hidden" >
          <label class="col-md-3">Long</label>
          <div class="col-md-9">
          <input class="form-control" id="associateLongitude" name="longitude"  type="text"></input>
          </div>
        </div> 
        
        
        
         <div class="form-group">
         <label class="col-md-3">Designation</label>
         <div class="col-md-9 ratetype">
             <input type="text" id="designation" name="designation" value="${designation}" field-name="Designation"  data-validation="validate_Space validate_Name validate_length length2-250" data-validation-optional="true"  value="" class="form-control">
         </div>
       </div>
       
       <div class="form-group">
         <label class="col-md-3">Employment Type<span class="text-danger">*</span></label>
         <div class="col-md-9">
             <select class="selectpicker form-control" id="designationType" name="designationType" onChange="getDesignationType(this);" data-validation="required" field-name="Employment type">
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
             		<select class="form-control selectpicker" id="experienceYears" name="experienceYears" onchange="disableMonths();">
             		  <option value="" >Years</option>
             		  <c:forEach begin="0" end="50" varStatus="loop">
						    <option>${loop.index}</option>
						</c:forEach>
             		     
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
       
       
       <div class="clearfix" id="hideVendor" style="display:none">
       <div class="form-group">
			<label class="col-md-3" ><span class="pull-left">Vendor</span><span class="text-danger pull-left" id="man1">*</span></label>
			<div class="col-md-9 chooRadius">
			<div class="input-group col-md-10 pull-left">
			     <select  class="chosen-select form-control" name="vendors" id="vendors" placeholder="Select Vendor" field-name="Vendor" onchange="getVendorContactList();">
                 </select>
                 <span class="input-group-addon cursorPointer bg-blue" onclick="getVendorsByOrgLocation();associateFromCandEmptyVendorContact();"><i class="fa fa-refresh "></i></span>
			</div>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR')"> 
		           <a href="<c:url value="/addVendor" />" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
              </sec:authorize>
		</div>
		</div>
		 <div class="form-group">
			<label class="col-md-3" ><span class="pull-left">Vendor Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
			<div class="col-md-9 chooRadius">
			<div class="input-group col-md-10 pull-left">
			     <select  class="chosen-select form-control" name="vendorcontact" id="vendorcontact" field-name="Vendor Contact"  placeholder="Select Vendor Contact">
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
        <div class="form-group designationTypeDiv" id="designationTypeDiv" style="display:none">
            <label class="col-md-3">Employment Type</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="designationOtherType" name="designationOtherType" value="${candidate.designationOtherType}"  field-name="Job Type" value="">
            </div>
          </div>
          
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
                 <input type="text" id="currentRate" name="currentRate" value="${currentRate}" field-name="Current Rate" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true" value="" class="form-control">
                <div class="input-group-btn tblminwidth-75 ratetype">
                  <select class="selectpicker" id="currentRateType" name="currentRateType" field-name="Rate Per">
                    <option value="">Rate Per</option>
                    <option ${currentRateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
                    <option ${currentRateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
                    <option ${currentRateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
                    <option ${currentRateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
                    <option ${currentRateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
                    <option ${currentRateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
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
                 <input type="text" id="expectedRate" name="expectedRate" value="${expectedRate}" field-name="Expected Rate" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true" value="" class="form-control">
                <div class="input-group-btn tblminwidth-75 ratetype">
                  <select class="selectpicker" id="rateType" name="rateType" field-name="Rate Per">
                    <option value="">Rate Per</option>
                    <option ${rateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
                    <option ${rateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
                    <option ${rateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
                    <option ${rateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
                    <option ${rateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
                    <option ${rateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
                    <option ${rateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
                  </select>
                </div>
                 <div class="clearfix"></div> <div class="clearfix"></div>
              </div>
            </div>
          </div>
        
        
         <div class="form-group" id="emplist1" style="display: block;">
          <label class="col-md-3">Employer's</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="candidateEmployers" name="candidateEmployers" value="${candidateEmployers}"  field-name="Employers" data-validation="validate_Space validate_length length2-250" data-validation-optional="true"  value="">
          </div>
        </div>
        
        <div class="form-group" id="emplist2" style="display: none;">
          <label class="col-md-3">Employer's</label>
          <div class="col-md-9" >
            <input type="text" class="form-control" id="candidateEmployers2"  value="${candidateEmployers}"  field-name="Employers" data-validation="validate_Space validate_length length2-250" data-validation-optional="true"  value="" readonly="readonly">
          </div>
        </div>
        
        
        <div class="form-group">
         <label class="col-md-3">Notice Period</label>
          <div class="col-md-9">
               <select class="form-control valid selectpicker" id="noticePeriod" name="noticePeriod">
                <option value="">--Select Notice Period--</option>
                 <option ${noticePeriod  == 'Immediately available' ? 'selected' : ''}>Immediately available</option>
                 <option ${noticePeriod  == '15 Days' ? 'selected' : ''}>15 Days</option>
                 <option ${noticePeriod  == '1 Month' ? 'selected' : ''}>1 Month</option>
                 <option ${noticePeriod  == '2 Months' ? 'selected' : ''}>2 Months</option>
                 <option ${noticePeriod  == '3 Months' ? 'selected' : ''}>3 Months</option>
                </select>
             
           </div>
         </div>
         
         

      
         
<div class="form-group innerForm_formGroup">
	<div class="form-group">
              <label class="col-md-3">Skills</label>
              <div class="col-md-9 mySkillsUl">
                <select multiple data-placeholder="Select Skills" class="chosen-select form-control" name="candidateSkills" id="candidateSkills"  data-validation-optional="true">
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

</div>         
<div class="form-group innerForm_formGroup">
		
		<div class="form-group">
		              <label class="col-md-3">Education</label> 
		              <div class="col-md-9 mySkillsUl">
		                <select multiple data-placeholder="Select Education" class="chosenone-selectone form-control" name="candidateEducation" id="candidateEducation" data-validation-optional="true">
		                <c:forEach var="edu" items="${educationsList}">
		             <option value="${edu.name}">${edu.name}</option>
		       </c:forEach>
		       </select>
		     </div>
		</div>
		
		 <div class="form-group">
          <label class="col-md-3">Summary</label>
          <div class="col-md-9">
            <textarea rows="2" class="form-control" id="summary" name="summary" value="${summary}" field-name="Summary" data-validation=" validate_length length2-5000" data-validation-optional="true"></textarea>
          </div>
        </div>

</div>            
        
         <div class="clearfix" id="newResumeDiv"  style="display: none;">
         <div class="form-group">
            <label class="col-md-3">Add Resume</label>
            <div class="col-md-9"> 
			 <div class="input-group col-md-12 errorInptgroup"  id="addResumeId" style="margin:0 auto">
               <label class="input-group-btn"> <span class="btn btn-danger"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                <input type="file" style="display: none;" multiple name ="uploadFileBtn" id="uploadFileBtn"  class="upload">
                 </span> </label>
                 <input type="text" class="form-control" readonly id="uploadProfile" name="docFile" type="file"  placeholder="Import Resume ..." field-name="Upload Resume" data-validation="validatedoc " data-validation-optional="true" >
           </div>
         </div>
        </div>
        </div> 
        
        <div class="clearfix"></div>
       <!--  <div class="form-group">
            <label class="col-md-3"></label>
             <div class="col-md-9">
               <div class="checkbox checkBox">
                 <label>
                   <input type="checkbox" value="Y" name="email">
                   Invite to SourceLead</label>
               </div>
                </div>
         </div> -->
         
              <div class="clearfix"></div>
					<div class="form-group">
						<label class="col-md-3"></label>
						<div class="col-md-9">
							<div class="checkbox checkBox">
								<label> <input type="checkbox" value="Y"
									name="inviteEmail"> Invite to SourceLead
								</label>
							</div>
						</div>
					</div>
        <div class="clearfix"></div>
      </div> 
    </div>
    
    <!-- this "candidateCreationType" field is used for candidate creation type -->
         <input type="hidden" id="candidateCreationType" name="candidateCreationType" value="manual">
         
    <div class="form-footer clearfix mt">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="button" value="Save" id="store_candidate" onclick="storeCandidate();" class="btn btn-primary">
          <input type="button" value="Cancel" onClick="cancelAssociate();" class="btn btn-default ml">
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
     @author : Laxman.
     this script is for save/store candidate.
     1.usage     : onclick.
     2.parameter : csrf token,con.
     3.ajax url implementation in CandidateRestController.
    --%>
 <script>
 function cancelAssociate(){
	 var sourceleadUrl = '${sourceleadUrl}';
	 location.replace(sourceleadUrl+"/associatesList");
 }
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
		
		 if ($('#candidate_form').validate(false, validationSettings)){ 
			
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#candidate_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#candidate_form')[0];
      		var formData = new FormData(form); 
			//It will escape all the special characters
			$("#store_candidate").attr("disabled", "disabled");
			$("#store_candidate").addClass('button').val('Processing..');
			$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeCandidate?"+tokenParamName+"="+tokenValue+"&json="+jsonData,
						success : function(result) {
								location.replace(sourceleadUrl+"/associatesList");
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
		tagIt("candidateEducation", 'Press Enter-Key Or Comma(,) To Add New Education:', 'tagItBykeyForSkills','skills','Select Education');

	});
  </script>

<script type="text/javascript">
			 
   	$("#candidateSkills").val('').trigger("chosen:updated");
    $("#candidateEducation").val('').trigger("chosen:updated");
      
    
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
		        url: sourceleadUrl+"/validateCandidateEmail?"+tokenParamName+"="+tokenValue+"&email="+email+"&candidateType=Y", 
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
        	 /* $('#experienceMonths').attr("disabled", true);
             $('#experienceMonths').val("disabled", true); */
         }else{
        	 $("#experienceMonths").val('');  
        	 $('#experienceMonths').selectpicker('refresh');
        	 $("#experienceMonths").attr("disabled", false); 
         }
     });
 }
	      

</script>



<script>

function getUsersByRole(){
	var sourceleadUrl ='${sourceleadUrl}';
	var roleId = document.getElementById("role").value;
	if(roleId != 'All' && roleId != ''  && roleId != 'undefined') {
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUserByRoleId?"+tokenParamName+"="+tokenValue+"&roleId="+roleId, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var json = response.data;
			        	var output = "";
			        	output = output + "<option value=''>-- Select User --</option>"; 
			        	for (var i=0; i<json.length; i++) {
			        		var opt = json[i];
			        		var name = '';
			        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
			        			name = opt.firstName+" "+opt.lastName;
			        		}else{
			        			name = opt.firstName;
			        		}
			        		output = output + "<option value='" +opt.id+ "'>"+name+"</option>";
						}
			        	$("#consultantUser").empty();
				        $("#consultantUser").append(output);
				        $('#consultantUser').trigger("chosen:updated");
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	} else if(roleId == 'All') {
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllRoleUsers?"+tokenParamName+"="+tokenValue+"", 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var json = response.data;
			        	var output = "";
			        	output = output + "<option value=''>-- Select User --</option>"; 
			        	for (var i=0; i<json.length; i++) {
			        		var opt = json[i];
			        		var name = '';
			        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
			        			name = opt.firstName+" "+opt.lastName;
			        		}else{
			        			name = opt.firstName;
			        		}
			        		output = output + "<option value='" +opt.id+ "'>"+name+"&nbsp;("+opt.userType+")</option>";
						}
			        	$("#consultantUser").empty();
				        $("#consultantUser").append(output);
				        $('#consultantUser').trigger("chosen:updated");
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
	} else {
		 var output = "";
    	 output = output + "<option value=''>-- Select User --</option>"; 
    	 $("#consultantUser").empty();
	     $("#consultantUser").append(output);
	     $('#consultantUser').trigger("chosen:updated");
	}
	
	$("#firstName").val('');
    $("#lastName").val('');
    $("#email").val('');
    $("#mobilePhone").val('');
    $("#uploadFile").val('');
    
    /* Clear the skills */
    $("#candidateSkills").val('').trigger("chosen:updated");
}

</script>
<script>
var selectedFile = document.getElementById("uploadFileBtn");
selectedFile.addEventListener("change", function() {
	 $("#uploadProfile").focus();
	 if(validate_fileupload(this.value)){
		 var docSize = this.files[0].size;
	      $("#addResumeId").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("uploadProfile").value = '';
	    	 /*  $("#uploadFileGroup").append("<p class='jquery_form_error_message'></p>"); */
	    	  $('#addResumeId').find('p.jquery_form_error_message').remove();
			  $("input#uploadProfile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  document.getElementById("uploadProfile").setAttribute('record-exist','yes');
			  document.getElementById("uploadProfile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#uploadProfile').removeAttr( "record-exist record-exist-errormsg");
  		 $('#addResumeId').find('p.jquery_form_error_message').remove(); 
	    }
	 }
});
document.getElementById("uploadFileBtn").onchange = function () {
 	$('#uploadProfile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
   // document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();
 };
</script>


<!-- Experience Years and Months validation Script End here -->

<script>
function getDesignationType(designationType){
	$('#vendorcontact').val('').trigger("chosen:updated");
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
		// $("#vendors").attr("data-validation", "true");
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







<!-- =================================================Laxman =========================================== -->
<script type="text/javascript">
 function getCandidatesList(value) {
	 candidateType = value;
	 $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
	 $('#newResumeDiv').hide();
	  $("#candidateEducation").val('').trigger("chosen:updated");
      $("#candidateSkills").val('').trigger("chosen:updated");
	 $('#candidateId').removeAttr('data-validation-optional');
	 $("#candidateId").attr("data-validation", "required");
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType != 'New' && candidateType != 'parseCandidate'){
		 $("#selectCandidateDiv").css("display","block");
		 document.getElementById("newResumeDiv").style.display="none";
		 var skillsDiv = document.getElementById('skillsDiv'); 
		 if(skillsDiv != null){
			 document.getElementById("skillsDiv").style.display="none";
			 $("#candidateSkills").val('').trigger("chosen:updated");
		 }
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/getAssociateList?"+tokenParamName+"="+tokenValue+"&candidateType="+candidateType, 
			        success: function(data) {
			        	var json = JSON.parse(data);
			        	var output = "";
			        	output = output + "<option value=''>-- Select "+candidateType+" --</option>"; 
			        	for (var i=0; i<json.length; i++)
						{
				        		var opt = json[i];
				        		var lastName = "";
				        		if(opt.isAssociate == 'N'){
				        			if(opt.lastName != null && opt.lastName != "" && opt.lastName != 'undefined'){
					        			lastName = opt.lastName;
					        		}
					        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+lastName+"</option>";
				        		}
						}
			        	$("#candidateId").empty();
				        $("#candidateId").append(output);
				        $('#candidateId').trigger("chosen:updated");
				        getCandidateResumes();
			        },
			    }); 
	 }else{
		 var output = "";
     	 $("#candidateId").empty();
	     $("#candidateId").append(output);
	     $('#candidateId').trigger("chosen:updated");
	     $("#selectCandidateDiv").css("display","none");
	     
	     $("#candidateId").removeAttr("data-validation");
	     $('#candidateId').attr("data-validation-optional","true"); 
	     
	     $("#candidateResumesList").removeAttr("data-validation");
	     $('#candidateResumesList').attr("data-validation-optional","true"); 
	     
	     $("#candidateEducation").val('').trigger("chosen:updated");
   	     $("#candidateSkills").val('').trigger("chosen:updated");
	     
	    /*  if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'parseCandidate'){
	    	 $("#candidate_form").val('').trigger("chosen:updated");
	    	 document.getElementById("candidate_form").reset();
	    	 $(".selectpicker").selectpicker('refresh');
	    	 $('#state').trigger("chosen:updated");
	    	 $( "#parsId" ).attr( "checked", true );
	    	 $( "#newId" ).attr( "checked", false );
	    	 $( "#canId" ).attr( "checked", false );
	    	 
	    	 document.getElementById("resumesDiv").style.display="none";
	    	 document.getElementById("browseDiv").style.display="";
	    	 $('#uploadFile').removeAttr('data-validation-optional');
			 $('#uploadFile').attr("data-validation","required validatedoc "); 
			 $("#email").removeAttr('readonly','readonly');
			 //document.getElementById("newResumeDiv").style.display="none";
			 document.getElementById("skillsDiv").style.display="";
			 document.getElementById("associateResumeDivId").style.display="none";
		     $("#candidateSkills").val('').trigger("chosen:updated");
			 $('#uploadProfile').removeAttr('data-validation');
			 $('#uploadProfile').attr("data-validation-optional","true"); 
	     }else if(candidateType != null && candidateType != "" && candidateType != 'undefined' && candidateType == 'New'){
	    	 $("#candidate_form").val('').trigger("chosen:updated");
	    	 document.getElementById("candidate_form").reset();
	    	 $(".selectpicker").selectpicker('refresh');
	    	 $( "#newId" ).attr( "checked", true );
	    	 $( "#parsId" ).attr( "checked", false );
	    	 $( "#canId" ).attr( "checked", false );
	    	 $("#email").removeAttr('readonly','readonly');
	    	 document.getElementById("newResumeDiv").style.display="block";
	    	 document.getElementById("resumesDiv").style.display="none";
	    	 document.getElementById("browseDiv").style.display="none";
	    	 //$('#uploadFile').removeAttr('data-validation');
			 //$('#uploadFile').attr("data-validation-optional","true");
			 
			 //document.getElementById("associateResumeDivId").style.display="block";
			
	    	// document.getElementById("skillsDiv").style.display="";
		     //$("#candidateSkills").val('').trigger("chosen:updated");
			 //$('#uploadProfile').removeAttr('data-validation-optional');
			 //$("#uploadProfile").attr("data-validation", "required validatedoc");
	     } */
	     
	     document.getElementById("resumesDiv").style.display="none";
		
	 }
	 
	 $("input[name=firstName]").val('');
	 $("input[name=lastName]").val('');
	 $("input[name=email]").val('');
	 $("input[name=email]").removeAttr("readonly");
	 
	 $("input[name=alternateMobilePhone]").val('');
	 $("input[name=mobilePhone]").val('');
	 $("input[name=designation]").val('');
	 $("select[name=state]").val('');
	 $("select[name=city]").val('');
	 $("select[name=noticePeriod]").val('');
	 $("select[name=experienceYears]").val(0);
	 $("select[name=experienceMonths]").val('');
	 $("input[name=currentRate]").val('');
	 $("input[name=currentRateType]").val('');
	 $("input[name=expectedRate]").val('');
	 $("input[name=rateType]").val('');
	 $("input[name=candidateEducation]").val('');
	 
	 $("#uploadProfile").val('');
	 $("#uploadFile").val(''); 
	
 }
</script>


<script type="text/javascript">
function getCandidateDetails(){
	$("#candidate_form").val('').trigger("chosen:updated");
	$(".selectpicker").selectpicker('refresh');
	var candidateId = document.getElementById("candidateId").value
	var sourceleadUrl ='${sourceleadUrl}';
	if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getCandidateDetailsInAssociate?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	 
		        	 $("input[name=firstName]").val(json['firstName']);
		        	 $("input[name=email]").val(json['email']);
		        	 $("input[name=email]").attr('readonly', 'readonly');
		        	 
		        	 if(json['lastName'] != null && json['lastName'] != "" && json['lastName'] != 'undefined'){
		        		 $("input[name=lastName]").val(json['lastName']);
		        	 }else{
		        		 $("input[name=LastName]").val(''); 
		        	 }
		        	 
		        	 if(json['mobilePhone'] != null && json['mobilePhone'] != "" && json['mobilePhone'] != 'undefined'){
		        		 $("input[name=mobilePhone]").val(json['mobilePhone']);
		        	 }else{
		        		 $("input[name=mobilePhone]").val(''); 
		        	 }
		        	 
		        	 if(json['alternateMobilePhone'] != null && json['alternateMobilePhone'] != "" && json['alternateMobilePhone'] != 'undefined'){
		        		 $("input[name=alternateMobilePhone]").val(json['alternateMobilePhone']);
		        	 }else{
		        		 $("input[name=alternateMobilePhone]").val(''); 
		        	 }
		        	 
		        	 if(json['designation'] != null && json['designation'] != "" && json['designation'] != 'undefined'){
		        		 $("input[name=designation]").val(json['designation']);
		        	 }else{
		        		 $("input[name=designation]").val(''); 
		        	 }
		        	
		        	 if(json['designationType'] != null && json['designationType'] != "" && json['designationType'] != 'undefined'){
		        		 $("#designationType").val(json['designationType']);
		        		 $("#designationType").selectpicker('refresh');
		        		 employementType(json['designationType']);
		        	 }else{
		        		 $("#designationType").val(''); 
		        		 $("#designationType").selectpicker('refresh');
		        	 }
		        	 
		        	 if(json['address'] != null && json['address'] != "" && json['address'] != 'undefined'){
		        		 $("#address").val(json['address']);
		        	 }else{
		        		 $("#address").val('');
		        	 }
		        	 
		        	 if(json['summary'] != null && json['summary'] != "" && json['summary'] != 'undefined'){
		        		 $("#summary").val(json['summary']);
		        	 }else{
		        		 $("#summary").val('');
		        	 }
		        	 
		        	 if(json['employeList'] != null && json['employeList'] != "" && json['employeList'] != 'undefined'){
		        		 $("#candidateEmployers2").val(json['employeList']);
		        		 document.getElementById("emplist1").style.display="none";
		        		 document.getElementById("emplist2").style.display="block";
		        	 }else{
		        		 $("#candidateEmployers").val('');
		        		 document.getElementById("emplist1").style.display="block";
		        		 document.getElementById("emplist2").style.display="none";
		        	 }
		        	 
		        	 if(json['primarySkill'] != null && json['primarySkill'] != "" && json['primarySkill'] != 'undefined'){
		        		 $("#primarySkill").val(json['primarySkill']);
		        	 }else{
		        		 $("#primarySkill").val('');
		        	 }
		        	 
		        	 
		        	/*  $('#candidateEducation').find('option').remove().end();
		        	  $.each( json.allAndCandidateEducation, function( i, educt){
						  var existedEdu = 0;
			                if(json.candidateEdu != null && json.candidateEdu != 'undefined' && json.candidateEdu != ''){
				                var res = [];
				                res = json.candidateEdu.split(",");
				                res.forEach(function(item) {
				                	if(educt == item){
				                		existedEdu = 1;
				                		$('#candidateEducation').append('<option value=\''+educt+'\' selected>'+educt+'</option>');
				                	}
				                });
			                }
			                if(existedEdu == 0){
			                	$('#candidateEducation').append('<option value=\''+educt+'\'>'+educt+'</option>');
			                }
						  
						});
		        	  $("#candidateEducation").trigger("chosen:updated"); */
		        	  
		        	 if(json['candidateEdu'] != null && json['candidateEdu'] != "" && json['candidateEdu'] != 'undefined'){
		        		var eduaction = json['candidateEdu'];
				    	var customizeEducation = new Array();
				    	customizeEducation = eduaction.split(",");
				        for (var i = 0; i < customizeEducation.length; i++) { 
				        	 var customEdu = customizeEducation[i];
				        	 $('#candidateEducation').append('<option value=\''+customEdu+'\' selected>'+customEdu+'</option>');
				        }
 		        		$("#candidateEducation").trigger("chosen:updated");
		     
		        	 }else{
		        		 $("#candidateEducation").val('').trigger("chosen:updated");
		        		 $("input[name=candidateEducation]").val(''); 
		        	 } 
		        	  
		      		
		      		$('#candidateSkills').find('option').remove().end();
		      		
		        	  $.each( json.allAndCandidateSkills, function( i, skill ){
						  var existed = 0;
			                if(json.skillsName != null && json.skillsName != 'undefined' && json.skillsName != ''){
				                var res = [];
				                res = json.skillsName.split(",");
				                res.forEach(function(item) {
				                	if(skill == item){
				                		existed = 1;
				                		$('#candidateSkills').append('<option value=\''+skill+'\' selected>'+skill+'</option>');
				                	}
				                });
			                }
			                if(existed == 0){
			                	$('#candidateSkills').append('<option value=\''+skill+'\'>'+skill+'</option>');
			                }
						  
						});
		        	  $("#candidateSkills").trigger("chosen:updated");
		        	 
		        	  
		        	  $('#visa').find('option').remove().end();
		        	   $.each( json.allAndCandidateVisa, function( i, visa ){
						  var existed = 0;
			                if(json.visaType != null && json.visaType != 'undefined' && json.visaType != ''){
				                var res = [];
				                res = json.visaType.split(",");
				                res.forEach(function(item) {
				                	if(visa == item){
				                		existed = 1;
				                		$('#visa').append('<option value=\''+visa+'\' selected>'+visa+'</option>');
				                	}
				                });
			                }
			                if(existed == 0){
			                	$('#visa').append('<option value=\''+visa+'\'>'+visa+'</option>');
			                }
						  
						});
		        	  $("#visa").trigger("chosen:updated");
		        	  
		        	  
		        	  
		        	 if(json['country'] != null && json['country'] != "" && json['country'] != 'undefined'){
		        		 $("#associateCountry").val(json['country']);
		        	 }else{
		        		 $("#associateCountry").val('');
		        	 }
		         
		        	 if(json['state'] != null && json['state'] != "" && json['state'] != 'undefined'){
		        		 $("#associateState").val(json['state']);
		        	 }else{
		        		 $("#associateState").val('');
		        	 }

		        	 if(json['city'] != null && json['city'] != "" && json['city'] != 'undefined'){
		        		 $("#associateCity").val(json['city']);
		        	 }else{
		        		 $("#associateCity").val('');
		        	 }
		        	 
		        	 if(json['location'] != null && json['location'] != "" && json['location'] != 'undefined'){
		        		 $("#associateLocation").val(json['location']);
		        	 }else{
		        		 $("#associateLocation").val('');
		        	 }
		        	 
		        	 if(json['zipCode'] != null && json['zipCode'] != "" && json['zipCode'] != 'undefined'){
		        		 $("#associateZipCode").val(json['zipCode']);
		        	 }else{
		        		 $("#associateZipCode").val('');
		        	 }
		        	 
		        	 if(json['latitude'] != null && json['latitude'] != "" && json['latitude'] != 'undefined'){
		        		 $("#associateLatitude").val(json['latitude']);
		        	 }else{
		        		 $("#associateLatitude").val('');
		        	 }

		        	 if(json['longitude'] != null && json['longitude'] != "" && json['longitude'] != 'undefined'){
		        		 $("#associateLongitude").val(json['longitude']);
		        	 }else{
		        		 $("#associateLongitude").val('');
		        	 }
		        	 
		        	 
		        	 if(json['vendorName'] != null && json['vendorName'] != "" && json['vendorName'] != 'undefined'){
		        		 $("select[name=vendors]").val(json['vendorName']);
		        		 getVendors(json['vendorName'],json['vendorOrgRelationId']);
		        	 }else{
		        		 $("#vendors").val('').trigger("chosen:updated");
		        		 $("select[name=vendors]").val('');
		        	 }
		        	 
		        	
		        	 if(json['vendorContactName'] != null && json['vendorContactName'] != "" && json['vendorContactName'] != 'undefined'){
		        		 $("select[name=vendorcontact]").val(json['vendorContactName']);
		        		 vendorContactList(json['vendorContactName'],json['vendorOrgRelationId'],json['vendorSpocId']);
		        	 }else{
		        		 $("#vendorcontact").val('').trigger("chosen:updated");
		        		 $("select[name=vendorcontact]").val('');
		        	 }
		        	 
		        	 
		        	 if(json['noticePeriod'] != null && json['noticePeriod'] != "" && json['noticePeriod'] != 'undefined'){
		        		 $("select[name=noticePeriod]").val(json['noticePeriod']);
		        		 $("#noticePeriod").selectpicker('refresh');
		        	 }else{
		        		 $("select[name=noticePeriod]").val('');
		        	 }
		        	
		        	 if(json['experienceYears'] != null && json['experienceYears'] != "" && json['experienceYears'] != 'undefined'){
		        		 $("select[name=experienceYears]").val(json['experienceYears']);
		        		 $("#experienceYears").selectpicker('refresh');
		        	 }else{
		        		 $("select[name=experienceYears]").val('');
		        		 $("#experienceYears").selectpicker('refresh');
		        	 }
		        	 
		        	 if(json['experienceMonths'] != null && json['experienceMonths'] != "" && json['experienceMonths'] != 'undefined'){
		        		 $("select[name=experienceMonths]").val(json['experienceMonths']);
		        		 $("#experienceMonths").selectpicker('refresh');
		        	 }else{
		        		 $("select[name=experienceMonths]").val('');
		        		 $("#experienceMonths").selectpicker('refresh');
		        	 }
		        	 
		        	 
		        	 if(json['expectedRate'] != null && json['expectedRate'] != "" && json['expectedRate'] != 'undefined'){
		        		 $("input[name=expectedRate]").val(json['expectedRate']);
		        	 }else{
		        		 $("input[name=expectedRate]").val(''); 
		        	 }
		        	 		        	 
		        	 if(json['rateType'] != null && json['rateType'] != "" && json['rateType'] != 'undefined'){
		        		 $("select[name=rateType]").val(json['rateType']);
		        		 $("#rateType").selectpicker('refresh');
		        		 validateExpectedCTCRatePer();
		        	 }else{
		        		 $("select[name=rateType]").val('');
		        		 $("#rateType").selectpicker('refresh');
		        		 validateExpectedCTCRatePer();
		        	 }
		        	 
		        	 if(json['expectedRateCurrencey'] != null && json['expectedRateCurrencey'] != "" && json['expectedRateCurrencey'] != 'undefined'){
		        		 $("select[name=expectedRateCurrencey]").val(json['expectedRateCurrencey']);
		        	 }
		        	 
		        	 if(json['currentRate'] != null && json['currentRate'] != "" && json['currentRate'] != 'undefined'){
		        		 $("input[name=currentRate]").val(json['currentRate']);
		        		 validateCurrentCTC();
		        	 }else{
		        		 $("input[name=currentRate]").val(''); 
		        		 validateCurrentCTC();
		        	 }
		        	 
		        	 
		        	 if(json['currentRateType'] != null && json['currentRateType'] != "" && json['currentRateType'] != 'undefined'){
		        		 $("select[name=currentRateType]").val(json['currentRateType']);
		        		 $("#currentRateType").selectpicker('refresh');
		        		 validateExpectedCTC();
		        		 validateCurrentCTCRatePer();
		        	 }else{
		        		 $("select[name=currentRateType]").val(''); 
		        		 $("#currentRateType").selectpicker('refresh');
		        		 validateExpectedCTC();
		        		 validateCurrentCTCRatePer();
		        	 }
		        	 
		        	 if(json['currentRateCurrency'] != null && json['currentRateCurrency'] != "" && json['currentRateCurrency'] != 'undefined'){
		        		 $("select[name=currentRateCurrency]").val(json['currentRateCurrency']);
		        	 }
		        	 
		        },
		    });
	 }else{
		 
		 $("input[name=firstName]").val('');
		 $("input[name=lastName]").val('');
		 $("input[name=email]").val('');
		 $("input[name=email]").removeAttr("readonly");
		 
		 $("input[name=alternateMobilePhone]").val('');
		 $("input[name=mobilePhone]").val('');
		 $("input[name=designation]").val('');
		 $("select[name=state]").val('');
		 $("select[name=city]").val('');
		 $("select[name=noticePeriod]").val('');
		 $("select[name=experienceYears]").val(0);
		 $("select[name=experienceMonths]").val('');
		 $("input[name=currentRate]").val('');
		 $("input[name=currentRateType]").val('');
		 $("input[name=expectedRate]").val('');
		 $("input[name=rateType]").val('');
		 $("input[name=candidateEducation]").val('');
		 
		 $("#uploadProfile").val('');
		 $("#uploadFile").val(''); 
		 
	 }
}
</script>



<script type="text/javascript">
 function getCandidateResumes() {
	 $("#save_button").removeAttr("disabled");
	 $('.jquery_form_error_message').remove();
	 var candidateId = document.getElementById("candidateId").value
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(candidateId != null && candidateId != "" && candidateId != 'undefined'){
 		 document.getElementById("resumesDiv").style.display="";
 		/*	 document.getElementById("browseDiv").style.display="none";
		 $("#uploadFile").removeAttr("data-validation");
		 $("#uploadFile").val('');
		 
 */		 
 
 $('.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getAssociateResumes?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	if( json.length > 0){
			        	var output = "";
			        	if(json.length == 1){
			        		
			        		var opt = json[0];
			        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
			        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
			            	}else{
			            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
			            	}
			        	}else{
			        		output = output + "<option value=''>-- Select Resume --</option>"; 
				        	for (var i=0; i<json.length; i++)
							{
					        		var opt = json[i];
					        		if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
					        			output = output + "<option value='" +opt.id+ "'>"+opt.title+"</option>";
					            	}else{
					            		output = output + "<option value='" +opt.id+ "'>"+opt.fileName+"</option>";
					            	}
							}
			        	}
			        	
			        	$("#candidateResumesList").empty();
				        $("#candidateResumesList").append(output);
				        
				        document.getElementById("newResumeDiv").style.display="none";
				        document.getElementById("skillsDiv").style.display="none";
				        
				        $('#uploadProfile').removeAttr('data-validation');
		        		$('#uploadProfile').attr("data-validation-optional","true"); 
		        		
		        		 $('#candidateResumesList').removeAttr('data-validation-optional');
		    			 $("#candidateResumesList").attr("data-validation", "required"); 
		        	}else{
		        		 $('#candidateResumesList').removeAttr('data-validation');
		        		 $('#candidateResumesList').attr("data-validation-optional","true"); 
		        		 document.getElementById("newResumeDiv").style.display="block";
		        		 document.getElementById("resumesDiv").style.display="none";
		        		 //document.getElementById("browseDiv").style.display="none";
		        		
		        		 
		        		 document.getElementById("skillsDiv").style.display="";
		        		 
		        		 $('#uploadProfile').removeAttr('data-validation-optional');
		        		 $("#uploadProfile").attr("data-validation", "required validatedoc");
		        		 
		        		 $('#uploadFile').removeAttr('data-validation');
		        		 $('#uploadFile').attr("data-validation-optional","true"); 
		        	}
		        	
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Resume --</option>"; 
     	 $("#candidateResumesList").empty();
     	 $("#candidateResumesList").removeAttr("data-validation");
		 document.getElementById("resumesDiv").style.display="none";
		 document.getElementById("browseDiv").style.display="none";
		 $('#uploadFile').removeAttr('data-validation');
		 $('#uploadFile').attr("data-validation-optional","true"); 
	     $("#candidateResumesList").append(output);
	 }
	
 }
</script>


<script>
 function validateCurrentCTC(){
	 var currentCTC = $("input[name=currentRate]").val();
	 if(currentCTC){
		 $("#currentRateType").removeAttr("data-validation-optional");
		 $("#currentRateType").attr("data-validation", "required");
	 }else{
		 $("#currentRateType").removeAttr("data-validation");
		 $("#currentRateType").attr("data-validation-optional", "true");
	 }
 }
 </script>
 
  <script>
 function validateExpectedCTC(){
	 var expectedCTC = $("input[name=expectedRate]").val();
	 if(expectedCTC){
		 $("#rateType").removeAttr("data-validation-optional");
		 $("#rateType").attr("data-validation", "required");
	 }else{
		 $("#rateType").removeAttr("data-validation");
		 $("#rateType").attr("data-validation-optional", "true");
	 }
 }
 </script>
 
 <script>
 function validateExpectedCTCRatePer(){
	 var expectedCTCRatePer = document.getElementById("rateType").value;
	 if(expectedCTCRatePer){
		 $("input[name=expectedRate]").removeAttr("data-validation-optional");
		 $("input[name=expectedRate]").attr("data-validation", "required validate_Space validate_float validate_length length1-8");
	 }else{
		 $("input[name=expectedRate]").removeAttr("data-validation");
		 $("input[name=expectedRate]").attr("data-validation-optional", "true");
		 $("input[name=expectedRate]").attr("data-validation", "validate_Space validate_float validate_length length1-8");
	 }
 }
 </script>
 
 <script>
 function validateCurrentCTCRatePer(){
	 var currentCTCRatePer = document.getElementById("currentRateType").value;
	 if(currentCTCRatePer){
		 $("input[name=currentRate]").removeAttr("data-validation-optional");
		 $("input[name=currentRate]").attr("data-validation", "required validate_Space validate_float validate_length length1-8");
	 }else{
		 $("input[name=currentRate]").removeAttr("data-validation");
		 $("input[name=currentRate]").attr("data-validation-optional", "true");
		 $("input[name=currentRate]").attr("data-validation", "validate_Space validate_float validate_length length1-8");
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
		        	$('#vendors').append('<option value="">--Select Vendor --</option>');
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
	var educationList = [];
	var skillList = [];
	$(function() {
		 var i= 0;
		 <c:forEach var="skill" items="${skillList}">
		 	skillList[i++] = "${skill.name}";
		 </c:forEach>
		 var i= 0;
		 <c:forEach var="edu" items="${educationsList}">
		 	educationList[i++] = "${edu.name}";
		 </c:forEach>
	});
</script>
 
  <script>
 function getVendorContactList(){
	 $('#vendorcontact').val('').trigger("chosen:updated");
	 var orgRelationID = $('#vendors').val();
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
			    type: "POST",
		        url: sourceleadUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
		        success: function(json) {
		        	//var json = JSON.parse(data);
		        	$('#vendorcontact').empty();
		        	$('#vendorcontact').append('<option value="">--Select Vendor contact--</option>');
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
 function parseResume(){
	  var result = "";
	result = result + ' 	 <div class="col-md-5 pull-right"> ';
	result = result + ' 	 <div class="col-md-12" id="browseDiv"  style="display: none;"> ';
	result = result + ' 		 <div class="input-group" id="uploadFileGroup"> ';
	result = result + ' 		 <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File ';
	result = result + ' 		 <input type="file" style="display: none;" name="locationMapFile" multiple id="uploadBtn"  class="upload"> ';
	result = result + ' 		 </span> </label> ';
	result = result + ' 		 <input type="text" class="form-control" data-validation="validatedoc" data-validation-optional="true" field-name="Resume" readonly id="uploadFile" placeholder="Import Resume">'; 
	result = result + ' 		 </div> ';
	result = result + ' 	 </div> 	';
	result = result + '   </div>  ';
	$("#parsResumeDivId").append(result);   
	
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
   		var form = $('#candidate_form')[0];
     		var formData = new FormData(form); 
   		var sourceleadUrl = '${sourceleadUrl}';
    	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
   		   $.ajax({
   					type : "POST",
   	        	    data: formData,
   	        	    contentType : 'application/json; charset=utf-8',
   	        	    enctype: 'multipart/form-data',
   	    	        processData: false,
   	    	        contentType:false,
   	        	    dataType : 'json',
   				 	url : sourceleadUrl+"/parseResume?"+tokenParamName+"="+tokenValue+"", 
   					success : function(response) {
   						if (response.response === "AWKWARD") {
   						    location.href = "/errorPage";
   						} else if (response.response === "SUCCESS") {
   							if(response.object !== null) {
   								var json = response.object;
   	   	   						var name = json.name;
		   	   	   				var firstName = json.firstName;
								var lastName = json.lastName;
   	   	  						
   	   	  						
								$("input[name='FirstName']").val(firstName);
					        	 $("input[name='LastName']").val(lastName);
					        	 $("input[name='EmailId']").val(json.email.split("<<>>")[0]);
					        	 
					        	 for(var e in json.phoneNumbers) {
					        		 if(e === "0")
						        	 	$("input[name='PhoneNumber']").val(json.phoneNumbers[e]);
					        		 else
					        			 $("input[name='AlternateNumber']").val(json.phoneNumbers[e]);
					     		 }
					        	 
					        	 $("input[name='country']").val(json.country === null && json.country !== "" ? json.country : "");
					        	 $("input[name='state']").val(json.state === null && json.state !== "" ? json.state : "");
					        	 $("input[name='city']").val(json.city === null && json.city !== "" ? json.city : "");
					        	 $("input[name='zipcode']").val(json.zipcode === null && json.zipcode !== "" ? json.zipcode : "");
					        	 
					        	 $("#DOB").val(json.dob !== null && json.dob !== "" ? json.dob : "");
					        	 $("input[name='Current/LastCompany']").val(json.employeers);
					        	 
					        	 $("input[name='Designation']").val(json.designation !== null && json.designation !== "" ? json.designation : "");
					        	 
					        	 $("input[name='Education']").val(json.educations);
					        	 
				        	 	if(json.totalYearsExp > 0) {
				        			$("select[name='WorkExperience']").val(json.totalYearsExp);
				        		} else if(json.totalMonthsExp > 0) {
				        			$("select[name='WorkExperience']").val(0);
				        		}
				        		
				        		if(json.totalMonthsExp > 0) {
				        			$("select[name='monthExp']").val(json.totalMonthsExp);
				        		} else if(json.totalYearsExp > 0) {
				        			$("select[name='monthExp']").val(0);
				        		}
					        	 
					        	 $("#candidateSkills").empty();
		 						
		 						var internalOutput = "";
		 						var skills = json.skills;
		 						if(skills != null && skills != 'undefined' && skills != ''){
		 							var str_array = skills;
		 						} else {
		 							str_array = [];
		 						}
		 						
		 					   for(var j = 0; j< skillList.length; j++){
		 						    var existed = 0;
		 							for(var i = 0; i < str_array.length; i++) {
		 							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
		 							   if(str_array[i] == skillList[j]){
		 								   existed = 1;
		 								   internalOutput = internalOutput+"<option value='"+str_array[i]+ "' selected>"+str_array[i]+"</option>";
		 								   break;
		 							   }
		 							}
		 							if(existed == 0){
		 								internalOutput = internalOutput+"<option value='"+skillList[j]+"'>"+skillList[j]+"</option>";
		 					        }
		 						 }
		 					   
		 					   for(var e in str_array) {
		 							internalOutput = internalOutput+"<option value='" + str_array[e] + "' selected>" + str_array[e] + "</option>";
		 						}
		 						
		 						$("#candidateSkills").append(internalOutput);
		 		        		$("#candidateSkills").trigger("chosen:updated");
		 		        		
   	   	   						validateCandidateEmail();
   							} else {
   								showError("Resume Parsing Failed");
   							}
   	   						$("#loading").removeAttr("style");
   						} else if (response.indexOf('Sign In') !== -1) {
   							location.replace(sourceleadUrl);
   						} else {
   						    showError("Operation failed, please try again");
   						}
   					},
   				});
    	 	}
    	 }
    });

   /* upload file script */
   document.getElementById("uploadBtn").onchange = function () {
   	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
     // document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();
   };
	
 }
 
 </script>
 
 <script>
 function employementType(type){
if(type == 'C2C'){
	 document.getElementById("hideVendor").style.display="block";
	 $('#vendors').attr("data-validation","required"); 
	 $('#vendorcontact').attr("data-validation","required"); 
	 
	 document.getElementById("man1").style.display="block";
	 document.getElementById("man2").style.display="block";
	 
	// $("#vendors").attr("data-validation", "true");
	  getVendorsByOrgLocation();
}else{
	 document.getElementById("hideVendor").style.display="none";
	 $('#vendors').removeAttr("data-validation","required"); 
	 $('#vendorcontact').removeAttr("data-validation","required"); 
}


if(type == 'C2C-1099'){
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
		        	$('#vendors').append('<option value="">--Select Vendor --</option>');
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
		        	$('#vendorcontact').append('<option value="">--Select Vendor contact--</option>');
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
			fileUpload("uploadBtn", "resumes", "candidate_form");
		});
	 });
 </script>
 <script>
function associateFromCandEmptyVendorContact(){
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
 