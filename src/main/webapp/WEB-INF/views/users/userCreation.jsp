<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="${SourceLeadWeb_atsUrl}/resources/js/jquery.maskedinput-1.3.min.js?staticTime=${staticTime}"></script>

<!-- <script src="resources/js/masklist.js" type="text/javascript"></script>
<script src="resources/js/jquery.inputmask.js" type="text/javascript"></script> 

<script type="text/javascript">
	$(document).ready(function(){
		masklist();
	});
</script>  -->
<script type="text/javascript">
var atsUrl ='${SourceLeadWeb_atsUrl}';
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
		window.onload = function () {
			initLocationAuto('residenceLocation','residenceCountry','residenceState','residenceCity','residenceZipCode','residenceLatitude','residenceLongitude','editAssociate','','address');
			getDesignationType();
			getDepartmentsList();
			getResourceManagers();
			maskme();
			editMe(this);
			var country = '${SourceLeadWeb_country}';
			if(country == 'United States'){
				 $('#ssnNumber').attr("data-validation","required"); 
			}
		 };
	</script>
<script>	
	$(document).ready(function() {
		var location = "${location}";
		document.getElementById("createUser_form").reset();
			<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
	   		  <c:if test="${sectionOrder.index == 0}">
				  var sectionId = '${section.id}';
				  getCustomDependentField(sectionId);
			  </c:if>
			 </c:forEach>
	});
</script>
  <!-- Content Area -->
  <section class="content-wraper contentWraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Add Associate</h2>
          </div>
          <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_atsUrl}/usersList">Associates List</a> &raquo; Add Associate </div>
        </section>
        
        <!--New theme-->
        <div class="user_summ_wrap contentareaInner-styles">
        
        
        <!--  Tabe Menu Starts -->
        <div class="tabsMenu">
        <ul class="list-unstyled">
          <li class="current"><a href="#tab-1" id="associates">Associates</a></li>

          <c:if test="${SourceLeadWeb_country == 'India'}">
         		 <li><a href="#tab-2" data-toggle="tab" id="indBankingDetails" onclick="getIndAssociateBankingDetails();">Banking Details</a></li>
       		     <li><a href="#tab-3" data-toggle="tab" id="indInformationDetails" onclick="getIndAssociateInformationDetails();">Information</a></li>
       		     <li><a href="#tab-4" data-toggle="tab" id="indLoanAndAdvancesDetails" onclick="loanAdv.getIndAssociateLoanAndAdvancesDetails();">Loan / Advances</a></li>
       		     <li><a href="#tab-6" data-toggle="tab" id="indLeaveTypeDetails" onclick="getIndAssociateLeaveTypeDetails();">Leave Type</a></li>
       		     <li><a href="#tab-5" data-toggle="tab" id="indSalaryDetails" onClick="indAssociateSalarySummary();">Salary</a></li>
          </c:if>

          <c:if test="${SourceLeadWeb_country == 'United States'}">
            <li class=""><a href="#tab-6" data-toggle="tab" id="associateSalary" >Salary</a></li>
	          <li class=""><a href="#tab-2" data-toggle="tab" id="associateBanking" >Banking</a></li>
	          <li class=""><a href="#tab-3" data-toggle="tab" id="associateTax" >TAX</a></li>
	          <li class=""><a href="#tab-4" data-toggle="tab" id="associateDeduction">Deduction</a></li>
	          <li class=""><a href="#tab-5" data-toggle="tab" id="associateContribution">Contribution</a></li>
              <li class=""><a href="#tab-7" data-toggle="tab" id="associateLeaveType" >Leave Type</a></li> 
	      </c:if>
          <div class="clearfix"></div>
        </ul>
		</div>
		 <!--  Tabe Menu Starts -->
        
        <div class="tabsMenu_tabs">
          <div id="tab-1" class="tabsMenu_tabs_inn" style="display: block;">
            <div class="project_tabs_inn_table">
        
        <div class="clearfix">
        <div id="loading"></div> 
        <div id="candidatesLoading"></div>
        
        
        
        <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
          <form:form id="createUser_form"  name="createUser_form" method="post">
          <input type="hidden" id="actionId" value="${actionId}">
          <input type="hidden" id="associateId" name="associateId">
          <input type="hidden" id="associateCandidateId" >
          <input type="hidden" id="empType" name="empType">
            <div class="row clearfix minHeightcol5">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
		        <div class="col-md-12 clearfix">
		        <div class="well clearfix">
		          
		          <div class="col-md-8 er selectUser col-md-offset-3">
		          <div class="row">
		          <div class="col-md-4 paddingRight">
		             <select class="selectpicker pull-left form-control" id="employmentDesignationType" name="employmentDesignationType" onChange="getDesignationType();">
		                 <option value="employeementType" selected>--Employment Type--</option>
		                 <option value="All">All</option>
		                 <option value="C2C">C2C</option>
		                 <option value="C2C-1099">C2C-1099</option>
		                 <option value="W-2">W-2</option>
		                 <option value="C2H">C2H</option>
		                 <option value="C2H -1099">C2H -1099</option>
		                 <option value="Part Time">Part Time</option>
		                 <option value="Full Time">Full Time</option>
		                 <option value="Other">Other</option>
		             </select>
		             </div>
		           <div class="col-md-5 paddingLeft">
		           <select class="chosen-select pull-left" id="employeementType" name="employeementType" field-name="EmployeementType" onchange="getAssociateUserDetails();" data-validation-optional="true" >
			            <option value=" " selected>-Select Candidate-</option>
			            
		           </select>
		          </div> </div>
		          </div>
		          </div>
		        </div>  
              
                <div class="form-group" id="EmployeeIdDiv">
                  <label class="col-md-3">Employee Id</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="EmployeeId" name="employeeId" data-validation="validate_Space validate_length length2-20" data-validation-optional="true" value="" onKeyUp="validateEmployeeId();" onblur="validateEmployeeId();">
                    </div>
                </div>
                <div class="form-group">
                      <label class="col-md-3">Date of Joining  <span class="text-danger">*</span></label>
                         <div class="col-md-9" id=DateofJoinDiv>
                           <div class="input-group errorInptgroup">
                              <input type="text" value="" name="dateofJoin" class="form-control calendar" id="DateofJoin" readonly="readonly" field-name="Date of join" data-validation="required">
                              <span class="input-group-addon" onClick="clearDateofJoin();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                           </div>
                        </div>
                  </div>
                  
                   <div class="form-group" id="EmailIdDiv">
                  <label class="col-md-3">Email Id<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
	                  <div class="input-group errorInptgroup">
	                    <input type="text" class="form-control" id="EmailId" name="email" value="" field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email"  onblur="getUserByUserEmail(); validateUserEmail();" onKeyUp="validateUserEmail();" >
	                    <span id="emailStatusIconDiv" class="input-group-addon">
	                    	<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>
	                  	</span>
	                  </div>
                  </div>
                  </div>
                  
             <div class="form-group">
                  <label class="col-md-3">Probation Period <i class='fa fa-info-circle' title='This will represent the probation period for a associate in organisation'></i></label>
                  <div class="col-md-9">
                      <select class="form-control chosen-select" name="probationPeriod" id="probationPeriod" field-name="Probation Period" onchange="calculateConfirmationDate();">
                        <option value="">-Select Probation Period-</option>
                        <c:if test="${!empty probationPeriod}">
	                        <option value="15 Days" ${probationPeriod  == '15 Days' ? 'selected' : ''}>15 Days</option>
	                        <option value="30 Days" ${probationPeriod  == '30 Days' ? 'selected' : ''}>1 Month</option>
	                        <option value="60 Days" ${probationPeriod  == '60 Days' ? 'selected' : ''}>2 Months</option>
	                        <option value="90 Days" ${probationPeriod  == '90 Days' ? 'selected' : ''}>3 Months</option>
                        </c:if>
                        <c:if test="${empty probationPeriod}">
	                        <option value="15 Days">15 Days</option> 
	                  		<option value="30 Days">1 Month</option>
	                  		<option value="60 Days" selected>2 Months</option> 
	                        <option value="90 Days">3 Months</option>
                        </c:if>
                        
                      </select>
                  </div>
            </div>
            
                  
              <div class="form-group">
                  <label class="col-md-3">Status</label>
                  <div class="col-md-9">
                      <select class="form-control chosen-select" name="probationStatus" id="probationStatus" field-name="Status">
                        <option value="">-Select Status-</option>
                        <option value="Probation" selected>Probation</option>
                        <option value="Confirmed">Confirmed</option>
                      </select>
                  </div>
            </div>
            
            
              <div class="form-group">
                      <label class="col-md-3">Confirmation Date</label>
                         <div class="col-md-9">
                           <div class="input-group errorInptgroup">
                              <input type="text" value="" name="confirmationDate" class="form-control calendar" id="confirmationDate" field-name="Confirmation Date" readonly="readonly">
                              <span class="input-group-addon" onClick="clearConfirmationDate();"></span>
                           </div>
                        </div>
                  </div>
                  
                  
                  <c:if test="${SourceLeadWeb_country == 'United States'}">
                  <div class="form-group" id="ssnNumberDiv">
                  <label class="col-md-3">SSN<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="ssnNumber" name="ssn" field-name="SSN number" onblur="validateSSNNumber();">
                  </div>
                  </div>
                  </c:if>
                  
                <div class="form-group" id="FirstNameDiv">
                  <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="FirstName" name="firstName" pattern="[A-Za-z\s]*"  field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100">

                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3">Middle Name</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="middleName" name="middleName" pattern="[A-Za-z\s]*"   field-name="Middle Name" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true">
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3">Last Name</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="lastName" name="lastName" pattern="[A-Za-z\s]*"   field-name="Last Name" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true">
                  </div>
                </div>
                
             <div class="form-group">
                  <label class="col-md-3">Work Location<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9" id= "workLocationDiv">
                   	<select class="form-control chosen-select" id="workLocation" name="workLocation" field-name="Work Location" onchange="removeLocationValidation();" data-validation="required" >
                        <option value="">--Select Location--</option>
                        <c:forEach var="option" items="${locationList}">
							<option value="${option.id}">${(option.orgLocationAddress != '' && option.orgLocationAddress != null) ? option.orgLocationAddress : option.registeredLocation}</option>
						</c:forEach>
              		</select>
                  </div>
                </div>
                 
                 <div class="form-group">
                  <label class="col-md-3">Residence Location<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9 positionRelative">
                    <input type="text" class="form-control" id="residenceLocation" name="residenceAddress" field-name="Residence Location" data-validation="required">
                     <input type="hidden" id="residenceCountry" name="residenceCountry" />
                     <input type="hidden" id="residenceState" name="residenceState" />
                     <input type="hidden" id="residenceCity" name="residenceCity" />
					 <input type="hidden" id="residenceZipCode" name="residenceZipCode" />
                     <input type="hidden" id="residenceLatitude" name="residenceLatitude" />
                     <input type="hidden" id="residenceLongitude" name="residenceLongitude" />
                  <i class="fa fa-refresh btn btn-primary refresh-icon bg-blue" title="Refresh" onClick="refreshGoogleApiLocation('residenceLocation','residenceCountry','residenceState','residenceCity','residenceZipCode','');"></i>
                  </div>
                </div>
                
                
          <!--       <div class="form-group">
                  <label class="col-md-3">Zip Code<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9 positionRelative" id="ZipCode">
						<input  class="form-control" id="residenceZipCode" name="residenceZipCode" placeholder="Enter Zip code" field-name="Zip Code" type="text" data-validation="required">                    
                  </div>
                </div> -->
                
                  
                 <div class="form-group">
                  <label class="col-md-3">Phone Number</label>
                   <div class="col-md-9">
                   <div class="row">
                    <div class="col-md-6 paddingRight">
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" data-validation="validate_Space validate_phone validate_length length9-20" data-validation-optional="true">
                    <span class="hidden">Phone Number</span>
                     <input type="hidden" name="country" id="CountryAutocomplete" value ="${SourceLeadWeb_country}" />
                    </div>
                    <div class="col-md-6 paddingLeft">
                    <div class="row">
                    <div class="col-md-12">
                    <input type="text" class="form-control" id="extensionNumber" placeholder="Extension Number" name="extensionNumber" data-validation="validate_Space validate_extno validate_length length1-5" data-validation-optional="true">
                    </div>
                    </div>
                    </div>
                     </div>
                  </div>
                </div> 
                <div class="form-group" id="genderDiv">
						<label class="col-md-3">Gender<span class="text-danger">&nbsp;*</span></label>
						<div class="col-md-9" id= "errorGenderMsg">
						<label class="paddingRight"><input type="radio"  value="Male"  name="gender" onblur="genderCheckValidationAssociate(this)" onchange="removeGenderValidation();"><span class="mr5"></span>Male</label>
						<label class="paddingLeft paddingRight"><input type="radio"  value="Female" name="gender" onblur="genderCheckValidationAssociate(this)" onchange="removeGenderValidation();"><span class="mr5"></span>Female</label>
						<label class="paddingRight"><input type="radio"  value="Transgender" name="gender" onblur="genderCheckValidationAssociate(this)" onchange="removeGenderValidation();"><span class="mr5"></span>Transgender</label>
						<input type="hidden"  id="genderValue" name="genderValue"  field-name="Gender" data-validation="required"/> 		
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3">Marital Status</label>
						<div class="col-md-9">
						<label class="paddingRight"><input type="radio"  value="Single" name="maritalStatus"><span class="mr5"></span>Single</label>
						<label class="paddingLeft paddingRight"><input type="radio"  value="Married" name="maritalStatus"><span class="mr5"></span>Married</label>
						<label class="paddingRight"><input type="radio"  value="Divorsed" name="maritalStatus"><span class="mr5"></span>Divorced</label>
						<label class="paddingLeft paddingRight"><input type="radio"  value="Separated" name="maritalStatus"><span class="mr5"></span>Separated</label>
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
                  
                  <c:if test="${SourceLeadWeb_country == 'India'}">
		         		<div class="form-group">
		                  <label class="col-md-3">PAN Number<span class="text-danger">&nbsp;*</span></label>
		                  <div class="col-md-9">
		                    <input type="text" class="form-control" id="panNumber" name="panNumber" field-name="PAN Number" data-validation="required validate_Space validate_panNumber" value="">
		                    </div>
		                </div>
		                
		                <div class="form-group">
		                  <label class="col-md-3">Aadhar Number<span class="text-danger">&nbsp;*</span></label>
		                  <div class="col-md-9">
		                    <input type="text" class="form-control" id="aadharNumber" name="aadharNumber" field-name="Aadhar Number" data-validation="required validate_Space validate_aadharNumber" value="">
		                    </div>
		                </div>
		          </c:if>
                  
               <div class="form-group">
                  <label class="col-md-3">Department <span class="text-danger">*</span></label>
                  <div class="col-md-9 chooRadius" id="departments">
                   <div class="input-group col-md-10 pull-left errorInptgroup">
                         <select class="form-control chosen-select" id="department" name="department" field-name="Department" onchange="removeValidation();" data-validation="required" >
			              </select>
			               <span class="input-group-addon cursorPointer bg-blue" onclick="getDepartmentsList();"><i class="fa fa-refresh "></i></span>
                  </div>
                  <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')"> 
			<a href="${SourceLeadWeb_sourceleadUrl}/addDepartment" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
			</sec:authorize>
                </div> 
                 </div> 
                <div class="form-group">
         <label class="col-md-3">Employment type<span class="text-danger">&nbsp;*</span></label>
         <div class="col-md-9">
             <select class="selectpicker form-control" id="designationType" name="designationType" onChange="getEmployeementType(this);" field-name="Employment type" data-validation="required">
                <option value="">--Select Employment type--</option>
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
                
                <div class="form-group errorInptgroup">
                  <label class="col-md-3">Role<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9 chooRadius" id="chosen-role">
                   <div class="input-group col-md-10 pull-left">
                         <select class="form-control chosen-select" id="role" name="roleType" field-name="Role" onchange="getReportingManager()" data-validation="required" >
		                        <option value="">-Select Role-</option>
		                        <c:forEach var="option" items="${rolesList}">
									<option value="${option.id}">${option.displayName}</option>
								</c:forEach>
			              </select>
			               <span class="input-group-addon cursorPointer bg-blue" onclick="refreshRoleList();managerListEmpty();"><i class="fa fa-refresh "></i></span>
                  </div>
                       <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE ')"> 
			             <a href="${SourceLeadWeb_sourceleadUrl}/orgRolesList" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
			           </sec:authorize>
                </div>
                </div> 
                
                <div class="form-group">
                  <label class="col-md-3">Designation<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="designation" name="designation"  field-name="Desgination" data-validation="required validate_Space validate_Name validate_length length2-250" >
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3">Grade </label>
                  <div class="col-md-9 chooRadius" id="chosen-role">
                   <div class="input-group col-md-10 pull-left">
                         <select class="form-control chosen-select" id="grade" name="grade" field-name="Grade"  data-validation-optional="true" >
		                        <option value="">-Select Grade-</option>
		                        <c:forEach var="option" items="${gradesList}">
									<option value="${option.id}">${option.gradeName}</option>
								</c:forEach>
			              </select>
			               <span class="input-group-addon cursorPointer bg-blue" onclick="refreshGradesList();"><i class="fa fa-refresh "></i></span>
                  </div>
		             <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')"> 
					<a href="${SourceLeadWeb_sourceleadUrl}/addGrade" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
					</sec:authorize>
                </div> 
                 </div> 
                
                
           <div class="form-group designationTypeDiv" id="designationTypeDiv" style="display:none">
            <label class="col-md-3">Employment Other Type</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="designationOt$("#empType").val(result.object.designationType);herType" name="designationOtherType"   field-name="Job Type" value="">
            </div>
          </div>
       
       <span id="hideVendor" style="display:none">
       <div class="form-group">
			<label class="col-md-3" ><span class="pull-left">Vendor <i class='fa fa-info-circle' title='Select/Add the vendor details of the respective associate. (This applies to C2C and C2C 1099 employment types)'></i></span><span class="text-danger pull-left" id="man1">*</span></label>
			<div class="col-md-9 chooRadius" id="errorvendor">
			 <div class="input-group col-md-10 pull-left">
			     <select  class="chosen-select form-control" name="vendors" id="vendors" placeholder="select Vendor" field-name="Vendor" onchange="getVendorContactList();vendorContactEmpty();removeValiationVendor();">
                 </select>
                  <span class="input-group-addon cursorPointer bg-blue" onclick="getVendorsByOrgLocationId();vendorContactEmpty();"><i class="fa fa-refresh "></i></span>
			</div>
			
			<span class="col-md-2 noPad clearfix">
			<sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR')"> 
			<a href="${SourceLeadWeb_vmsUrl}/addVendor" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
			</sec:authorize>
			</span>
		</div>
		</div>
		 <div class="form-group">
			<label class="col-md-3 paddingRight" ><span class="pull-left">Vendor Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
			<div class="col-md-9 chooRadius" id="errorvendorContact">
			<div class="input-group col-md-10 pull-left">
			     <select  class="chosen-select form-control" name="vendorcontact" id="vendorcontact" field-name="Vendor Contact"  placeholder="select Vendor Contact" onchange="removeValiationVendorContact();">
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
		</span>
               
              <div class="form-group">
                  <label class="col-md-3">Reporting Manager<span class="text-danger">&nbsp;* </span><i class='fa fa-info-circle' title='This will represent the reporting manager for a associate in organisation'></i></label>
                  <div class="col-md-9"  id="reportingManagerDiv">
                      <select class="form-control chosen-select" name="reportingManager" id="reportingManager" field-name="Reporting Manager" onchange="removeValidationmsg();" data-validation="required">
                        <option value="">-Select Manager-</option>
                      </select>
                  </div>
            </div>
            
            <div class="form-group " >
              <label class="col-md-3">Shift<span class="text-danger">&nbsp;* </span></label>
              <div class="col-md-9">
					<select class="form-control chosen-select" name="shiftId" id="shiftId" field-name="Shift" data-validation="required">
                        <option value="">-Select Shift-</option>
                        <c:forEach var="shift" items="${shiftList}">
                          <option value="${shift.id}" ${shift.defaultStatus eq 'Y' ? 'selected' : ''}>${shift.shiftName}</option>
                        </c:forEach>
                     </select>
                 </div>
            </div>
            
            <div class="form-group">
                  <label class="col-md-3">Resource Manager<span class="text-danger">&nbsp;* </span><i class='fa fa-info-circle' title='This will represent the resource manager for a associate in organisation'></i></label>
                  <div class="col-md-9"  id="resourceManagerDiv">
                      <select class="form-control chosen-select" name="resourceManager" id="resourceManager" field-name="Resource Manager"  onchange=" removeResourceValidation();" data-validation="required">
                        <option value="">-Select Manager-</option>
                      </select>
                  </div>
            </div>
            
                 <div class="form-group">
                     <label class="col-md-3">Rate</label>
                     <div class="col-md-9">
                         <div class="input-group er ratetype ratetype-error-styles">
							<div class="input-group-btn tblminwidth-75 displyBlock">
							 <select class="selectpicker" id="currentCurrency" name="currentCurrency">
								<option ${currencyCode  == 'INR' ? 'selected' : ''}>INR</option>
								<option ${currencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
								<option ${currencyCode  == 'USD' ? 'selected' : ''}>USD</option>
								<option ${currencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
								<option ${currencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
								<option ${currencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
							</select>
							</div>
				                    <input type="text" class="form-control" id="Rate" field-name="Rate" name="rate" value="" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true">
				                    <span class="hidden">Rate</span>
		                        <div class="input-group-btn tblminwidth-75 ratetype">
				                     <select class="selectpicker" id="RatePer" name="RatePer" field-name="Rate Per">
				                        <option value="">-Rate Per-</option>
				                        <option>Hourly</option>
				                        <option>Daily</option>
				                        <option>Weekly</option>
				                        <option>Bi-Weekly</option>
				                        <option>Semi-Monthly</option>
				                        <option>Bi-Monthly</option>
				                        <option>Monthly</option>
				                        <option>Yearly</option>
			                         </select>
		                       </div>
                          </div>
                     </div>
                </div>
                <div class="form-group">
		             <label class="col-md-3"></label>
		              <div class="col-md-9">
		                <div class="checkbox checkBox">
		                  <label>
		                    <input type="checkbox" value="Y" name="isGlobalAccess" id="isGlobalAccess">
		                     Global Manager</label>
		                </div>
		                 </div>
               </div>
                <div class="form-group " id = "DayHoursDiv">
                  <label class="col-md-3">Day hours</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="DayHours" placeholder="HH:MM" value="${averageWorkingHours}" name="DayHours" data-validation="validate_Space validate_length length1-9" data-validation-optional="true" onblur="editMe(this);validateDayHours();">
                  </div>
                </div>
                
            
            <div class="form-group">
		             <label class="col-md-3"></label>
		              <div class="col-md-9">
		                <div class="checkbox checkBox">
		                  <label>
		                    <input type="checkbox" value="Y" name="isMailSent" checked="checked">
		                    Send Mail to Associate</label>
		                </div>
		                 </div>
               </div>
               <c:if test="${SourceLeadWeb_country == 'United States'}">
               <div class="form-group">
		             <label class="col-md-3"></label>
		              <div class="col-md-9">
		                <div class="checkbox checkBox">
		                  <label>
		                    <input type="checkbox" value="Y" name="allowEmployeeToFill" checked="checked" id="allowEmployeeToFill">
		                    Allow Employee To Fill</label>
		                </div>
		                 </div>
               </div>
               </c:if>
                <c:if test="${!empty sourceleadSectionList}">
	            	<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
	               		<c:if test="${sectionOrder.index == 0}">
	                		  <div id="customDiv${section.id}"></div>
	              		 </c:if>
	            	</c:forEach>
	   			</c:if>
            </div>
            </div>
                
            <div class="footerstyelsNew clearfix">
                <div class="pull-right">
                  <input type="button" value="Save &amp; Continue" id="create_user" onclick="createUser();" class="btn btn-primary">
                  <input type="button" value="Cancel" onClick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/usersList'"  class="btn btn-default ml">
                </div>
            </div>
            </form:form>
          </div>
          
          </div>
          </div>
        </div>
       
      
        <c:if test="${SourceLeadWeb_country == 'United States'}">
	       <jsp:include page="associateSalary.jsp" />
	       <jsp:include page="associateBankData.jsp" />
	       <jsp:include page="associateTax.jsp" />
	       <jsp:include page="associateDeduction.jsp" />
	       <jsp:include page="associateContribution.jsp" />
	       <jsp:include page="associateLeaveType.jsp" />
        </c:if>
        
          <c:if test="${SourceLeadWeb_country == 'India'}">
              <jsp:include page="associateIndiaBankingDetails.jsp" />
              <jsp:include page="associateIndiaInformationDetails.jsp" />
              <jsp:include page="associateIndiaLoanAndAdvancesDetails.jsp" />
              <jsp:include page="associateIndLeaveType.jsp" />
              <jsp:include page="associateIndiaSalaryDetails.jsp" />
         </c:if> 
        
      </section>
      <!-- Content Area Ends--> 

      <!-- Footer Area -->
      <jsp:include page="../masterFooter.jsp" />
      <!-- Footer Area -->
 
 <div id="candidateSalaryDiv"></div> 

<script>
function getCustomDependentField(sectionId){
	 $.ajax({
			type : "POST" , 
				 url: atsUrl+"/getAllASectionCustomFields?sectionId="+sectionId,
				 success: function(data) {
		        	$('#customDiv'+sectionId).empty();
		        	$('#customDiv'+sectionId).append(data);
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
	 // getVendorsByOrgLocationId();
}else{
	 document.getElementById("hideVendor").style.display="none";
	 $('#vendors').removeAttr("data-validation","required"); 
	 $('#vendorcontact').removeAttr("data-validation","required"); 
}
}
 
function validateSSNNumber(){
	var ssnNumber = $("#ssnNumber").val();
	var length  = $("#ssnNumber").val().length;
	if(ssnNumber.search("-") > 0){
		ssnNumber = ssnNumber.replace(/-/g, "");
		length = ssnNumber.length;
		calculateSSNLength(length,ssnNumber);
	}else{
		calculateSSNLength(length,ssnNumber);
	}
	
}
function calculateSSNLength(length,ssnNumber){
	$('#ssnNumberDiv').find('p.jquery_form_error_message').remove();
	$("#ssnNumber").removeAttr("data-validation");
	if(length == 0){
		$("#ssnNumberDiv").find('span.ssn_errmsg').remove();
		$("#ssnNumber").after("<span class='ssn_errmsg' style='color:red'>SSN number is required</span>");
	}else if(length != 9){
		$("#ssnNumberDiv").find('span.ssn_errmsg').remove();
		$("#ssnNumber").after("<span class='ssn_errmsg' style='color:red'>SSN number should be 9 digits</span>");
	}else{
		$("#ssnNumberDiv").find('span.ssn_errmsg').remove();
		var value = "";
		var ssn = ssnNumber.substring(0,3);
		value += ssn+"-";
		ssn = ssnNumber.substring(3,5);
		value += ssn+"-";
		ssn = ssnNumber.substring(5,9);
		value += ssn;
		$("#ssnNumber").val(value);
		if(value.match(/^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$/) === null){
			$("#ssnNumberDiv").find('span.ssn_errmsg').remove();
			$("#ssnNumber").after("<span class='ssn_errmsg' style='color:red'>Invalid SSN number</span>");
		}else{
			$("#ssnNumberDiv").find('span.ssn_errmsg').remove();
		}
		
	}
	validateSSNDuplicate($("#ssnNumber").val());
}
 </script>
 
 <script>
 function validateSSNDuplicate(ssnNumber){
	 var associateId = $("#associateId").val();
	 $('#ssnNumberDiv').find('p.jquery_form_error_message').remove();
	 if(ssnNumber != null && ssnNumber != ""){
		 $("#create_user").attr("disabled", "disabled");
         //$("#create_user" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		    	url: atsUrl+"/validateSsnNumber?ssnNumber="+ssnNumber+"&candidateId="+associateId, 
		        success: function(data) {
		        	if(data == true){
		        		  $('#ssnNumberDiv').find('p.jquery_form_error_message').remove();
						  $("input#ssnNumber").after("<p class='jquery_form_error_message'>"+ssnNumber+" is Already Existed</span>");
						  document.getElementById("ssnNumber").setAttribute('record-exist','yes');
						  document.getElementById("ssnNumber").setAttribute('record-exist-errorMsg',ssnNumber+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#ssnNumber').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#ssnNumberDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#create_user" ).addClass('us-btn').val('Save & Continue');
                         $("#create_user").removeAttr("disabled");
		        	}
		        },
		    });
	 }	 
 }
 </script>

<script>
 function getVendorsByOrgLocationId(){
	 
	 $.ajax({
		    	type: "GET",
		        url: atsUrl+"/getVendorsByOrgLocationId?", 
		        success: function(json) {
		        	$('#vendors').empty();
		        	$('#vendors').append('<option value="">--Select Vendor --</option>');
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            		$('#vendors').append('<option value='+opt.orgRelId+' >'+opt.vendorName+'</option>');
		            }
		            $('#vendors').trigger("chosen:updated");
		        }
		 });
 }
 </script>
 <script>
 function getVendorContactList(){
	 var orgRelationID = $('#vendors').val();
	 
	 if(orgRelationID != null && orgRelationID !='' && orgRelationID !='undefined'){
		 $.ajax({
			    type: "POST",
		        url: vmsUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
		        success: function(json) {
		        	//var json = JSON.parse(data);
		        	$('#vendorcontact').empty();
		        	$('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
		             for(var i=0;i<json.data.length;i++){
		            	 var opt = json.data[i];
		            	 $('#vendorcontact').append('<option value='+opt.id+' >'+opt.spocName+'</option>');
		            } 
		            $('#vendorcontact').trigger("chosen:updated");
		        }
		 });
	 }else{
		 $('#vendorcontact').empty();
		 $('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
		 $('#vendorcontact').trigger("chosen:updated");
	 }
		  
 }
 </script>

<script>
function getEmployeementType(designationType){
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
		 getVendorsByOrgLocationId();
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
			getVendorsByOrgLocationId();
	}else{
		// document.getElementById("hideVendor").style.display="none";
		 $('#vendors').removeAttr("data-validation-optional", "true"); 
		 $('#vendorcontact').removeAttr("data-validation-optional", "true");  
	} 
	
	$('#designationType + p').remove();
}
</script>
      
 <script type="text/javascript">
 function validateDayHours() {
	 var DayHours = document.getElementById("DayHours").value; 
	 $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
	 var military = /^\s*([01]?\d|2[0-3]):[0-5]\d\s*$/i;
     var standard = /^\s*(0?\d|1[0-2]):[0-5]\d(\s+(AM|PM))?\s*$/i;
	if(DayHours.match(military) || DayHours.match(standard)){
		 $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
		 $('input#DayHours').removeAttr( "record-exist record-exist-errormsg"); 
	/* 	$("input#DayHours").after("<p class='jquery_form_error_message'>Day Hours should less than 24 Hours</span>");
		 document.getElementById("DayHours").setAttribute('record-exist','yes');
		 document.getElementById("DayHours").setAttribute('record-exist-errorMsg','Day Hours should less than 24 Hours');  */
	}else{
		$("input#DayHours").after("<p class='jquery_form_error_message'>Day Hours should less than 24 Hours</span>");
		 document.getElementById("DayHours").setAttribute('record-exist','yes');
		 document.getElementById("DayHours").setAttribute('record-exist-errorMsg','Day Hours should less than 24 Hours'); 
	
		/*  $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
		 $('input#DayHours').removeAttr( "record-exist record-exist-errormsg");  */
	}
	
 }
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

<!-- <script type="text/javascript">
 function createUser() {
	 var loginCurrencyCode = '${SourceLeadWeb_currencyCode}'; 
	 var loginLocationCountry = '${SourceLeadWeb_orgLocations.country}';
	 var country = '${SourceLeadWeb_loginCountryName}'; 

	 	var RatePer = document.getElementById("RatePer").value; 
		if(RatePer != null && RatePer != ""){
			$('#Rate').removeAttr('data-validation-optional');
			$('#Rate').attr("data-validation","required validate_Space validate_float validate_length length1-8"); 
		}else{
			$('#Rate').removeAttr('data-validation');
			$('#Rate').attr("data-validation-optional","true"); 
		}
		 
		var Rate = document.getElementById("Rate").value; 
		if(Rate != null && Rate != ""){
			$('#RatePer').removeAttr('data-validation-optional');
			$('#RatePer').attr("data-validation","required");
			$('#Rate').attr("data-validation","required validate_Space validate_float validate_length length1-8");
		}else{
			$('#RatePer').removeAttr('data-validation');
			$('#RatePer').attr("data-validation-optional","true"); 
		}
	 
	   if ($('#createUser_form').validate(false, validationSettings)){
		    var formData = getFormData("createUser_form");
// 			$("#create_user").attr("disabled", "disabled");
			$("#loading").attr('style','position:absolute; width:100%; height:210%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
//             $( "#create_user" ).addClass('button').val('Processing..');	
            $.ajax({
				type : "POST",
        	    data: formData,
				url : sourceleadUrl+"/createUser?"+tokenParamName+"="+tokenValue+"",
				success : function(result) {
					if(result && result === "DataMissing") {
						$("#create_user").removeAttr("disabled");
						$("#create_user").removeAttr('button').val('Save');	
						showError("Invalid data");
						$("#loading").removeAttr('style');
					} 
					else if(loginLocationCountry == 'India'){
						    $("#associateId").val(result.object.userId);
						    $("#associateCandidateId").val(result.object.candidateId); 
							$("#empType").val(result.object.designationType);
							 var indBankingDetails = document.getElementById("indBankingDetails");
							 document.getElementById("indBankingDetails").removeAttribute("class");
							 indBankingDetails.href="#tab-2";
							 $("#indBankingDetails").click();
					}
					else if(loginLocationCountry == 'United States') {
						$("#associateId").val(result.object.userId);
						$("#empType").val(result.object.designationType);
						var associateSalary = document.getElementById("associateSalary");
						document.getElementById("associateSalary").removeAttribute("class"); 
						associateSalary.href="#tab-6"; 
						$("#associateSalary").click();
						 getPayItemList();
					}
				}
            });
	        return true;
		
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#createUser_form', function() {
		$("#createUser_form").showHelpOnFocus();
		$("#createUser_form").validateOnBlur(false, validationSettings);  
   });
  
 
</script>   -->



<script type="text/javascript">
 function createUser() {
	 var country = '${SourceLeadWeb_country}'; 
 	 	var RatePer = document.getElementById("RatePer").value; 
		if(RatePer != null && RatePer != ""){
			$('#Rate').removeAttr('data-validation-optional');
			$('#Rate').attr("data-validation","required validate_Space validate_float validate_length length1-8"); 
		}else{
			$('#Rate').removeAttr('data-validation');
			$('#Rate').attr("data-validation-optional","true"); 
		}
		var Rate = document.getElementById("Rate").value; 
		if(Rate != null && Rate != ""){
			$('#RatePer').removeAttr('data-validation-optional');
			$('#RatePer').attr("data-validation","required");
			$('#Rate').attr("data-validation","required validate_Space validate_float validate_length length1-8");
		}else{
			$('#RatePer').removeAttr('data-validation');
			$('#RatePer').attr("data-validation-optional","true"); 
		}
	 
	   if ($('#createUser_form').validate(false, validationSettings)){
		    var formData = getFormData("createUser_form");
// 			$("#create_user").attr("disabled", "disabled");
			$("#loading").attr('style','position:absolute; width:100%; height:250%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
//             $( "#create_user" ).addClass('button').val('Processing..');
            
            $.ajax({
				type : "POST",
        	    data: formData,
				url : atsUrl+"/createUser?"+tokenParamName+"="+tokenValue+"",
				success : function(result) {
					if(result && result === "DataMissing") {
						$("#create_user").removeAttr("disabled");
						$("#create_user").removeAttr('button').val('Save');	
						showError("Invalid data");
						$("#loading").removeAttr('style');
					} 
					else if(country == 'India') {
						  $("#associateId").val(result.object.userId);
						    $("#associateCandidateId").val(result.object.candidateId); 
							$("#empType").val(result.object.designationType);
							 var indBankingDetails = document.getElementById("indBankingDetails");
							 document.getElementById("indBankingDetails").removeAttribute("class");
							 indBankingDetails.href="#tab-2";
							 $("#indBankingDetails").click();
					}else{
					    $("#associateId").val(result.object.userId);
						$("#empType").val(result.object.designationType);
						var associateSalary = document.getElementById("associateSalary");
						document.getElementById("associateSalary").removeAttribute("class"); 
						associateSalary.href="#tab-6"; 
						$("#associateSalary").click();
						getPayItemList();
					}
				},
			});
	        return true;
		
	  }else{
		return false;
	  } 
    }
 
   $('body').on('blur', '#createUser_form', function() {
		$("#createUser_form").showHelpOnFocus();
		$("#createUser_form").validateOnBlur(false, validationSettings);  
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
 function validateEmployeeId() {
     var EmpId = document.getElementById("EmployeeId").value;
     $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove();
     var con = document.getElementById("EmployeeId").value;
     var EmployeeId = encodeURIComponent(con);
     if(EmployeeId != null && EmployeeId != ""){
         $("#create_user").attr("disabled", "disabled");
        // $("#create_user" ).addClass('button').val('Processing..');
         $.ajax({
                type: "POST",
                url: atsUrl+"/validateEmployeeId?"+tokenParamName+"="+tokenValue+"&EmployeeId="+EmployeeId,
                success: function(data) {
                    if(data == true){
                          $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove();
                          $("input#EmployeeId").after("<p class='jquery_form_error_message'>"+EmpId+" is Already Existed</span>");
                          document.getElementById("EmployeeId").setAttribute('record-exist','yes');
                          document.getElementById("EmployeeId").setAttribute('record-exist-errorMsg',EmpId+' is Already Existed');
                    }
                    else{
                         $('input#EmployeeId').removeAttr( "record-exist record-exist-errormsg");
                         $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove();
                         $("#create_user" ).addClass('us-btn').val('Save & Continue');
                         $("#create_user").removeAttr("disabled");
                       
                    }
                },
            });
     }
 }
 </script> 
 
<script type="text/javascript">
 function validateUserEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 if(EmailId != null && EmailId != ""){
		 $("#create_user").attr("disabled", "disabled");
         //$("#create_user" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/validateUserEmail?"+tokenParamName+"="+tokenValue+"&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#create_user" ).addClass('us-btn').val('Save & Continue');
                         $("#create_user").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>  
 
 <script type="text/javascript">
 function getUserByUserEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 if(EmailId != null && EmailId != ""){
		 $("#loading").attr('style','position:absolute; width:100%; height:210%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getUserByUserEmail?"+tokenParamName+"="+tokenValue+"&EmailId="+EmailId, 
		        success: function(json) {
		        		
		        		if(json.firstName != null && json.firstName != ''){
		        			$("#FirstName").val(json.firstName);
				        	$("#FirstName").attr("readonly", "readonly");
				        	$('#FirstNameDiv').find('p.jquery_form_error_message').remove();
		        		}else{
		        			 $("#FirstName").removeAttr("readonly", "readonly");
		        			 //$("#FirstName").val('');
		        		}
		        		
		        		if(json.lastName != null && json.lastName != ''){
		        			$("#lastName").attr("readonly", "readonly");
		        			$("#lastName").val(json.lastName);
		        		}else{
		        			 $("#lastName").removeAttr("readonly", "readonly");
		        			 //$("#lastName").val('');
		        		}
		        		
		        		if(json.mobilePhone != null && json.mobilePhone != ''){
		        			$("#phoneNumber").val(json.mobilePhone);
				        	$("#phoneNumber").attr("readonly", "readonly");
		        		}else{
		        			 $("#phoneNumber").removeAttr("readonly", "readonly");
		        		}
		        		
		        		if(json.isGlobalUser != null && json.isGlobalUser != '' && json.isGlobalUser == 'Y'){
		        			$("#isGlobalAccess").attr("disabled", "disabled");
		        		}else{
		        			$("#isGlobalAccess").removeAttr("disabled");
		        		}
		        		$("#loading").removeAttr('style');
		        		
		        		validateEmailForExistenceUniversally(EmailId, "input#EmailId");
		        		
		        },
		    });
	 }else{
		 $("#FirstName").attr("disabled", false);
		 $("#FirstName").val('');
		 
		 $("#lastName").attr("disabled", false);
		 $("#lastName").val('');
		 
		 $("#phoneNumber").attr("disabled", false);
		 $("#phoneNumber").val('');
		 
		
		 $("#role").val('');
		 $("#role").trigger("chosen:updated");
		 
		  
		 $("#reportingManager").val('');
		 $("#reportingManager").trigger("chosen:updated");
		 
		 $("#resourceManager").val('');
		 $("#resourceManager").trigger("chosen:updated");
		 
		 $("#employeementType").val('');
	     $('#employeementType').trigger("chosen:updated");
	     
	     $("#emailStatusIconDiv").find("i").remove();
		 $("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>');
	 }
 }
 </script>   
 
 <script>
function getAssociateUserDetails(){
	var role = document.getElementById("role").value;
	 var reportingManager = document.getElementById("reportingManager").value;  
	var employeementType = document.getElementById("employeementType").value;
	
	if(employeementType == null || employeementType == ''){
        $("#designationType").val('');
		 $("#designationType").selectpicker('refresh');
		 document.getElementById("hideVendor").style.display="none";
		 document.getElementById("createUser_form").reset();
		
	}
		$.ajax({
	    	type: "POST",
	        url: atsUrl+"/getActiveAssociateDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+employeementType, 
	        /* contentType: "application/json; charset=utf-8",
	        dataType: "json", */
	        
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);

	        		  $("#associateId").val(json['id']);
	        		  $("#FirstName").val(json['firstName']);
	        		  $("#lastName").val(json['lastName']);
	        		  $("#EmailId").val(json['email']);
	        		  $("#phoneNumber").val(json['mobilePhone']);
	        		  $("#designationType").val(json['designationType']);
	        		  if(json['currentRate'] != null && json['currentRate'] !='undefined' && json['currentRate'] !=''){
	        		  $("#Rate").val(json['currentRate']);
	        		  $("select[name=RatePer]").val(json['currentRateType']);
		        	  $("#RatePer").selectpicker('refresh');
		        	  
	        		  }else{
	        			  $("#Rate").val('');
	        		  }
	        		  
	        		  $("select[name=currentCurrency]").val(json['currentRateCurrency']);
		        	  $("#currentCurrency").selectpicker('refresh');
		        	  
		        	  if(json['designation'] != null && json['designation'] != "" && json['designation'] != "undefined"){
		        		  $("#designation").val(json['designation']);
		        	  }
		        	  
		        	  if(json['maritalStatus'] != null && json['maritalStatus'] != "" && json['maritalStatus'] != "undefined"){
				    		 $("input[name='maritalStatus'][value='"+json['maritalStatus']+"']").prop('checked', true); 
				    	 }
		        	  
		        	  if(json['gender'] != null && json['gender'] != "" && json['gender'] != "undefined"){
				    		 $("input[name='gender'][value='"+json['gender']+"']").prop('checked', true); 
				    		 $("#genderValue").val(json['gender']);
				    	 }
				    	 
				    	 if(json['dateofBirthString'] != null && json['dateofBirthString'] != "" && json['dateofBirthString'] != "undefined"){
				    		 $("#dateofBirth").val(json['dateofBirthString']); 
				    	 }
				    	 
				    	 if(json['panNumber'] != null && json['panNumber'] != "" && json['panNumber'] != "undefined"){
				    		 $("#panNumber").val(json['panNumber']); 
				    	 }
				    	 
				    	 if(json['aadharNumber'] != null && json['aadharNumber'] != "" && json['aadharNumber'] != "undefined"){
				    		 $("#aadharNumber").val(json['aadharNumber']); 
				    	 }
				    	 
				    	 if(json['ssnNumber'] != null && json['ssnNumber'] != "" && json['ssnNumber'] != "undefined"){
				    		 $("#ssnNumber").val(json['ssnNumber']); 
				    	 }
	        		  
	        		  $("#designationType").selectpicker('refresh');
	        		  employementType(json['designationType']);
	        		  if(json['vendorOrgRelationId'] != null && json['vendorOrgRelationId'] !='undefined' && json['vendorOrgRelationId'] !=''){
	        		  getVendors(json['vendorName'],json['vendorOrgRelationId']);
	        		  vendorContactList(json['vendorContactName'],json['vendorOrgRelationId'],json['vendorSpocId']);
	        		  }
	        		  
	        		  if(json['designationOtherType'] != null && json['designationOtherType'] !='undefined' && json['designationOtherType'] !=''){
	        		  $("#designationOtherType").val(json['designationOtherType']);
	        		  otherType(json['designationType']);
	        		  }
	        		  $("#role").val('');
		        		 $('#role').trigger("chosen:updated");
		        		 $("#reportingManager").val('');
			        	 $('#reportingManager').trigger("chosen:updated");
	        		  /* (val(json['designationType'])); */
	        		  validateUserEmail();
	        	}
			 },
	        error: function(e){
	        }
	    });

	
}
</script>
 <script>
 function otherType(type){
	 if(type == 'Other'){
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
 </script>
 <script>
 function getVendors(vendorName,vendorOrgRelationId){
		 $.ajax({
		    	type: "GET",
		        url: atsUrl+"/getVendorsByOrgLocation?", 
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
		 $.ajax({
			    type: "POST",
		        url: vmsUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+vendorOrgRelationId+"&type=Vendor",  
		        success: function(json) {
		        	//var json = JSON.parse(data);
		        	$('#vendorcontact').empty();
		        	$('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
		             for(var i=0;i<json.data.length;i++){
		            	 var opt = json.data[i];
		            	 if(vendorContactName != null && vendorContactName != 'undefined' && vendorContactName  != ''){
		            		 $('#vendorcontact').append('<option '+ (opt.id == vendorSpocId ? "selected" : '') +' value="'+opt.id+'">'+opt.spocName+'</option>');
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
 function getAssociateList(){
	 var employeementType = document.getElementById("employeementType").value;
	 $.ajax({
	    	type: "POST",
	    	url: atsUrl+"/getActiveAssociateList?"+tokenParamName+"="+tokenValue+"&employeementType="+employeementType,
	        success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>-- Select Associate --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		output = output + "<option value='" +opt.candId+ "'>"+opt.candidateName+"</option>";
				}
	        	$("#employeementType").empty();
		        $("#employeementType").append(output);
		        $('#employeementType').trigger("chosen:updated");
	        },
	    });
 }
 
 </script>  
 
 
 <script>
 function getDepartmentsList(){
	 $.ajax({
	    	type: "POST",
	    	url: atsUrl+"/getDepartmentsList?"+tokenParamName+"="+tokenValue+"",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-- Select Department --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		var name = '';
	        		name = opt.departmentName;
	        		output = output + "<option value='" +opt.id+ "'>"+name+"</option>";
				}
	        	$("#department").empty();
		        $("#department").append(output);
		        $('#department').trigger("chosen:updated");
	        },
	    });
 }
 
 </script>  
 
 <script>
 function getDesignationType(){
	 var employmentDesignationType = $("#employmentDesignationType").val();
	 if(employmentDesignationType){
	 $("#candidatesLoading").attr('style','position:absolute; width:100%; height:210%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
	    	type: "POST",
	    	url: atsUrl+"/getActiveAssociateList?"+tokenParamName+"="+tokenValue+"&employeementType="+employmentDesignationType,
	        success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>-- Select Candidate --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		output = output + "<option value='" +opt.candId+ "'>"+opt.candidateName+"</option>";
				}
	        	$("#employeementType").empty();
		        $("#employeementType").append(output);
		        $('#employeementType').trigger("chosen:updated");
		        $("#candidatesLoading").removeAttr('style');
	        },
	    });
	 
	 }else{
		 $("#employeementType").empty();
	 }
 }
 
 
 </script>
 
 
<!-- start script for datepicker -->
<script>
  $( function() {
	  var locationDate ='${SourceLeadWeb_dateFormat}'; 
	  // written common method in slead-common.js for date formats start
	  var finalDateFormat = getJSDateFormat(locationDate);
	  //end
	  $('#DateofJoin').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  maxDate:0,
	  	  onSelect : function(selected) {
	  		calculateConfirmationDate();
	  		removeDateofJoinValid();
	 	  }
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	/*   $('#confirmationDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
// 	  	  maxDate:0
	      //minDate: 0  this line for to restrict selection of previous dates
	  }); */
	  
  } );
</script>
<!-- end script for datepicker -->
<script>
function removeDateofJoinValid(){
	$('#DateofJoinDiv').find('p.jquery_form_error_message').remove();
	}
</script>

 
<script type="text/javascript">
 function getReportingManager() {
	 $('#chosen-role').find('p.jquery_form_error_message').remove();
	 var role = document.getElementById("role").value; 
	 if(role != null && role != "" && role != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getAssociateReportingManager?"+tokenParamName+"="+tokenValue+"&role="+role, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Manager --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(opt.lastName !=null && opt.lastName !='undefined' && opt.lastName !=''){
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"&nbsp;("+opt.userType+")</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+"&nbsp;("+opt.userType+")</option>";
			        		}
			        		
					}
		        	$("#reportingManager").empty();
			        $("#reportingManager").append(output);
			        $('#reportingManager').trigger("chosen:updated");
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Manager --</option>"; 
     	 $("#reportingManager").empty();
	     $("#reportingManager").append(output);
	     $('#reportingManager').trigger("chosen:updated");
	 }
	
 }
</script>



<script type="text/javascript">
 function getResourceManagers() {
	 $('#chosen-role').find('p.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getAssociateResourceManager?"+tokenParamName+"="+tokenValue, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Manager --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.fullName+"&nbsp;("+opt.userType+")</option>";
			        		
					}
		        	$("#resourceManager").empty();
			        $("#resourceManager").append(output);
			        $('#resourceManager').trigger("chosen:updated");
		        },
		    });
 }
</script>


<script>
function maskme(){
	$.mask.definitions['H'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['M'] = "[0-9]";
    $.mask.definitions['m'] = "[0-9]";
    $.mask.definitions['P'] = "[AaPp]";
    $.mask.definitions['p'] = "[Mm]";
    $("#DayHours").mask("Hh:Mm"); 
}
</script>

<script>
function editMe(local){
	
 var id = local.id;
	 var inputValue =  $("#"+id).val();
	 if(inputValue =='undefined'|| inputValue =='' || inputValue==null){
		var inputValue1 = '${averageWorkingHours}';
		inputValue = inputValue1+":00";
		id ='DayHours';
	 }
	 var split = inputValue.split(':');
	 var hours = split[0];
	 var minutes = split[1];
	 var hours1 = hours.charAt(0);
	 var hours2 = hours.charAt(1);
	 var min1 = minutes.charAt(0);
	 var min2 = minutes.charAt(1);
	 if(!isNaN(hours1) && isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("0"+hours1+":00");
	 }
	 if(isNaN(hours1) && !isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("0"+hours2+":00");
	 }
	 else if(isNaN(hours)  && !isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("00:"+min1+"0");
	 }
	 else if(isNaN(hours)  && isNaN(min1) && !isNaN(min2)){
		 $("#"+id).val("00:0"+min2);
	 }
	 else if(!isNaN(hours)  && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val(hours+":00");
	 }
	 else if(!isNaN(hours)&& !isNaN(min1) && isNaN(min2)){
		 $("#"+id).val(hours+":"+"0"+min1);
	 }
	 else if(!isNaN(hours)&& isNaN(min1) && !isNaN(min2)){
		 $("#"+id).val(hours+":"+"0"+min2);
	 }
	 else if(!isNaN(hours)&& !isNaN(minutes)){
		
		  var hrmin= hours * 60;
		  var totalMin = parseInt(hrmin) + parseInt(minutes);
		  hours = Math.floor(totalMin / 60); 
		  minutes = totalMin % 60;
		  if(hours<=9){
			  hours = "0"+hours;
		  }
		  if(minutes<=9){
			  minutes = "0"+minutes;
		  }
		  $("#"+id).val(hours+":"+minutes);
	 }
	 }
	 </script>

	 <script>
	 function vendorContactEmpty(){
		 $('#vendorcontact').empty();
		 $('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
		 $('#vendorcontact').trigger("chosen:updated");
	 }
	 </script>

<script>
function refreshRoleList(){
		 $.ajax({
	      type : "POST",
	       url: atsUrl+"/refreshRoleList",       
	       success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>Select Role</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	       			output = output + "<option value='"+opt.id+"'>"+opt.displayName+"</option>";
				}
	        	$("#role").empty();
		        $("#role").append(output);
		        $('#role').trigger("chosen:updated");
	        }
		 });
	
}
</script>

<script>
function refreshGradesList(){
	 $.ajax({
	      type : "POST",
	       url: sourceleadUrl+"/refreshGradesList",       
	       success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>-Select Grade-</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	       			output = output + "<option value='"+opt.id+"'>"+opt.gradeName+"</option>";
				}
	        	$("#grade").empty();
		        $("#grade").append(output);
		        $('#grade').trigger("chosen:updated");
	        }
		 });
}
</script>



<script>
	 function managerListEmpty(){
		 $('#reportingManager').empty();
		 $('#reportingManager').append('<option value="">--Select Manager--</option>');
		 $('#reportingManager').trigger("chosen:updated");
	 }
	 </script>
	 
	 
	 
	 <script>
	 function selectedVendor(){
		 var vid = $('#vendors').val();
		 window.open(vmsUrl+"/addVendorContact?vid="+vid,'_blank');
	 }
	 </script>
	
<script type="text/javascript">
	function genderCheckValidationAssociate(id){
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
	  var locationDate ='${SourceLeadWeb_dateFormat}'; 
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
<script>
function clearDateofJoin(){
	$("#DateofJoin").val("");
	};
	
 /*  function clearConfirmationDate(){
	  $("#confirmationDate").val("");
  } */
</script>

<script>
var loginLocationCountry = '${SourceLeadWeb_country}';

              var associateLeaveType = document.getElementById("associateLeaveType");
				if(associateLeaveType){
					associateLeaveType.href = "#";
					associateLeaveType.className = "disabledLink";
				}
				
		if(loginLocationCountry == 'United States'){		
				
				var associateBanking = document.getElementById("associateBanking");
				if(associateBanking){
					associateBanking.href = "#";
					associateBanking.className = "disabledLink";
				}
				var associateTax = document.getElementById("associateTax");
				if(associateTax){
					associateTax.href = "#";
					associateTax.className = "disabledLink";
				}
				
				var associateDeduction = document.getElementById("associateDeduction");
				if(associateDeduction){
					associateDeduction.href = "#";
					associateDeduction.className = "disabledLink";
				} 
				
				var associateContribution = document.getElementById("associateContribution");
				if(associateContribution){
					associateContribution.href = "#";
					associateContribution.className = "disabledLink";
				} 
				
				var associateAdvances = document.getElementById("associateAdvances");
				if(associateAdvances){
					associateAdvances.href = "#";
					associateAdvances.className = "disabledLink";
				} 
				
				var associateSalary = document.getElementById("associateSalary");
				if(associateSalary){
					associateSalary.href = "#";
					associateSalary.className = "disabledLink";

				} 
		
		}  
				
				var associateLeaveType = document.getElementById("associateLeaveType");
				if(associateLeaveType){
					associateLeaveType.href = "#";
					associateLeaveType.className = "disabledLink";
				}  


		if(loginLocationCountry == 'India'){
		    
			var indBankingDetails = document.getElementById("indBankingDetails");
			if(indBankingDetails){
				indBankingDetails.href = "#";
				indBankingDetails.className = "disabledLink";
			}
			
		   var indInformationDetails = document.getElementById("indInformationDetails");
			if(indInformationDetails){
				indInformationDetails.href = "#";
				indInformationDetails.className = "disabledLink";
			} 
			
			var indLoanAndAdvancesDetails = document.getElementById("indLoanAndAdvancesDetails");
			if(indLoanAndAdvancesDetails){
				indLoanAndAdvancesDetails.href = "#";
				indLoanAndAdvancesDetails.className = "disabledLink";
			}
			
			var indLeaveTypeDetails = document.getElementById("indLeaveTypeDetails");
			if(indLeaveTypeDetails){
				indLeaveTypeDetails.href = "#";
				indLeaveTypeDetails.className = "disabledLink";
			}
			
			var indSalaryDetails =  document.getElementById("indSalaryDetails");
			if(indSalaryDetails){
				indSalaryDetails.href = "#";
				indSalaryDetails.className = "disabledLink";
			}   
			
		}


$(document).ready(function() {
	$("#tab-1").show()
    $(".tabsMenu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".tabsMenu_tabs_inn").not(tab).css("display", "none");
        $("#loading").removeAttr('style');
        $(tab).fadeIn();
    });
});
</script> 

<script>
function removeValidationmsg(){
	 $('#reportingManagerDiv').find('p.jquery_form_error_message').remove();
}
</script>

<script>
function removeLocationValidation(){
	 $('#workLocationDiv').find('p.jquery_form_error_message').remove();
}
</script>

<script>
function removeValidation(){
	 $('#departments').find('p.jquery_form_error_message').remove();
}
</script>

<script>
function removeResourceValidation(){
	 $('#resourceManagerDiv').find('p.jquery_form_error_message').remove();
}
</script>

<script>
function removeGenderValidation(){
	 $('#genderDiv').find('p.jquery_form_error_message').remove();
}
</script>


<script>
function calculateConfirmationDate(){

	 var joiningDate = document.getElementById("DateofJoin").value;
	 var probationperiod = document.getElementById("probationPeriod").value;
	 if(joiningDate && probationperiod){
	    $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    $.ajax({
			type : "GET",
			url: atsUrl+"/getConfirmationDateBasedOnJoiningDate?"+tokenParamName+"="+tokenValue+"&joiningDate="+joiningDate+"&probationperiod="+probationperiod,
			success :function(confirmationDate) {
				$("#loading").removeAttr("style");
				$("#confirmationDate").val(confirmationDate);
			}
		});
		
	}else{
		$("#confirmationDate").val('');
	}

}

</script>








