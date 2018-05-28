<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.maskedinput-1.3.min.js?staticTime=${staticTime}"></script>

<!-- <script src="resources/js/masklist.js" type="text/javascript"></script>
<script src="resources/js/jquery.inputmask.js" type="text/javascript"></script> 

<script type="text/javascript">
	$(document).ready(function(){
		masklist();
	});
</script>  -->
<script type="text/javascript">
		window.onload = function () {
			initLocationAuto('residenceLocation','residenceCountry','residenceState','residenceCity','residenceZipCode','residenceLatitude','residenceLongitude','editAssociate','');
			getDesignationType();
			getDepartmentsList();
			maskme();
			editMe(this);
		 };
	</script>
<script>
	$(document).ready(function() {
		var location = "${location}";
		document.getElementById("createUser_form").reset();
		<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
		  var sectionId = '${section.id}';
		  getCustomDependentField(sectionId);
		 </c:forEach>
	});
</script>
<style type="text/css">
.ratetype p {
    left: 0 !important;
    position: absolute;
    bottom: -14px;
}
</style>
  <!-- Content Area -->
  <section class="content-wraper contentWraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Add Associate</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/usersList'/>">Associates List</a> &raquo; Add Associate </div>
        </section>
        <input type="hidden" id="associateId" name="associateId">
        <!--New theme-->
        <div class="user_summ_wrap contentareaInner-styles">
	      <div class="tabsMenu">
	        <ul class="list-unstyled">
	          <li class="current"><a href="#tab-1">Associates</a></li>
	           <li class=""><a href="#tab-6" data-toggle="tab" id="associateSalary" >Salary</a></li>
	          <li class=""><a href="#tab-2" data-toggle="tab" id="associateBanking" >Banking</a></li>
	          <li class=""><a href="#tab-3" data-toggle="tab" id="associateTax" >TAX</a></li>
	          <li class=""><a href="#tab-4" data-toggle="tab" id="associateDeduction">Deduction</a></li>
	          <li class=""><a href="#tab-5" data-toggle="tab" id="associateContribution">Contribution</a></li>
<!-- 	          <li class=""><a href="#tab-6" data-toggle="tab" id="associateAdvances">Advances</a></li> -->
	          <div class="clearfix"></div>
	        </ul>
			</div>
			
			<div class="tabsMenu_tabs">
			<div id="tab-1" class="tabsMenu_tabs_inn" style="display: block;">
        
        <div class="clearfix">
        <div id="loading"></div> 
        <div id="candidatesLoading"></div> 
          
        <div class="clearfix">
          <form:form id="createUser_form"  name="createUser_form" method="post">
          <input type="hidden" id="actionId" value="${actionId}">
            <div class="row clearfix minHeightcol5">
              <div class="clearfix ">
              
		        <div class="col-md-10 clearfix">
		        <div class="well clearfix">
		          
		          <div class="col-md-10 er selectUser col-md-offset-1">
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
		           <div class="col-md-8 paddingLeft">
		           <select class="chosen-select pull-left" id="employeementType" name="employeementType" field-name="EmployeementType" onchange="getAssociateUserDetails();" data-validation-optional="true" >
			            <option value=" " selected>-Select Candidate-</option>
		           </select>
		          </div> </div>
		          </div>
		           <input type="hidden" id="associateId" name="associateId"/>
		          </div>
		        </div>  
              
                <div class="col-md-5" id="EmployeeIdDiv">
                  <label class="clearfix">Employee Id</label>
                  <div class="clearfix">
                    <input type="text" class="form-control" id="EmployeeId" name="employeeId" data-validation="validate_Space validate_length length2-20" data-validation-optional="true" value="" onKeyUp="validateEmployeeId();" onblur="validateEmployeeId();">
                    </div>
                </div>

                <div class="col-md-5">
                      <label class="clearfix">Date of Join</label>
                         <div class="clearfix">
                           <div class="input-group errorInptgroup">
                              <input type="text" value="" name="dateofJoin" class="form-control calendar" id="DateofJoin" readonly="readonly">
                              <span class="input-group-addon" onClick="clearDateofJoin();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                           </div>
                        </div>
                  </div>
                  

                 <div class="col-md-5" id="EmailIdDiv">
                  <label class="clearfix">Email Id<span class="text-danger">&nbsp;*</span></label>
                  <div class="clearfix">
	                  <div class="input-group errorInptgroup">
	                    <input type="text" class="form-control" id="EmailId" name="email" value="" field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email"  onblur="getUserByUserEmail(); validateUserEmail();" onKeyUp="validateUserEmail();" >
	                    <span id="emailStatusIconDiv" class="input-group-addon">
	                    	<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>
	                  	</span>
	                  </div>
                  </div>
                  </div>
                  <div class="col-md-5" id="EmailIdDiv">
                  <label class="clearfix">SSN<span class="text-danger">&nbsp;*</span></label>
                  <div class="clearfix">
                    <input type="text" class="form-control" id="ssn" name="SSN" field-name="First Name" data-validation="required validate_Space validate_int validate_length length9-9">
                  </div>
                  </div>
                  
                <div class="col-md-5" id="FirstNameDiv">
                  <label class="clearfix">First Name<span class="text-danger">&nbsp;*</span></label>
                  <div class="clearfix">
                    <input type="text" class="form-control" id="FirstName" name="firstName" pattern="[A-Za-z\s]*"  field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100">
                  </div>
                </div>
                
				
				 <div class="col-md-5">
                  <label class="clearfix">Middle Name</label>
                  <div class="clearfix">
                    <input type="text" class="form-control" id="middleName" name="middleName" pattern="[A-Za-z\s]*"   field-name="Middle Name" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true">
                  </div>
                </div>

                <div class="col-md-5">
                  <label class="clearfix">Last Name</label>
                  <div class="clearfix">
                    <input type="text" class="form-control" id="lastName" name="lastName" pattern="[A-Za-z\s]*"   field-name="Last Name" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true">
                  </div>
                </div>
                
               <div class="col-md-5">
                  <label class="clearfix">Work Location<span class="text-danger">&nbsp;*</span></label>
                  <div class="clearfix">
                   	<select class="form-control chosen-select" id="workLocation" name="workLocation" field-name="Work Location" data-validation="required" >
                        <option value="">--Select Location--</option>
                        <c:forEach var="option" items="${locationList}">
							<option value="${option.id}">${option.orgLocationAddress}</option>
						</c:forEach>
              		</select>
                  </div>
                </div>
                 
                 <div class="col-md-5">
                  <label class="clearfix">Residence Location<span class="text-danger">&nbsp;*</span></label>
                  <div class="clearfix positionRelative">
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
                  
                 <div class="col-md-5">
                  <label class="clearfix">Phone Number</label>
                   <div class="clearfix">
                   <div class="row">
                    <div class="col-xs-12 col-md-6 paddingRight">
                    <input type="text" class="form-control" id="phone" name="phoneNumber" data-validation="validate_Space validate_phone validate_length length9-20" data-validation-optional="true">
                    <span class="hidden">Phone Number</span>
                     <input type="hidden" name="country" id="CountryAutocomplete" value ="${SourceLeadWeb_country}" />
                    </div>
                    <div class="col-xs-12 col-md-6 paddingLeft">
                    <div class="row">
                    <div class="col-md-12">
                    <input type="text" class="form-control" id="extensionNumber" placeholder="Extension Number" name="extensionNumber" data-validation="validate_Space validate_extno validate_length length1-5" data-validation-optional="true">
                    </div>
                    </div>
                    </div>
                     </div>
                  </div>
                </div> 
				
				
				<div class="col-md-5">
					<label class="clearfix">Gender<span class="text-danger">&nbsp;*</span></label>
					<div class="clearfix" id= "errorGenderMsg">
					<label class="paddingRight"><input type="radio"  value="Male"  name="gender" onblur="genderCheckValidationAssociate(this)"><span class="mr5"></span>Male</label>
					<label class="paddingLeft paddingRight"><input type="radio"  value="Female" name="gender" onblur="genderCheckValidationAssociate(this)"><span class="mr5"></span>Female</label>
					<label class="paddingRight"><input type="radio"  value="Transgender" name="gender" onblur="genderCheckValidationAssociate(this)"><span class="mr5"></span>Transgender</label>
					<input type="hidden"  id="genderValue" name="genderValue"  field-name="Gender" data-validation="required"/> 		
					</div>
				</div>
					
					<div class="col-md-5">
						<label class="clearfix">Marital Status</label>
						<div class="clearfix">
						<label class="paddingRight"><input type="radio"  value="Single" name="maritalStatus"><span class="mr5"></span>Single</label>
						<label class="paddingLeft paddingRight"><input type="radio"  value="Married" name="maritalStatus"><span class="mr5"></span>Married</label>
						<label class="paddingRight"><input type="radio"  value="Divorsed" name="maritalStatus"><span class="mr5"></span>Divorsed</label>
						<label class="paddingLeft paddingRight"><input type="radio"  value="Separated" name="maritalStatus"><span class="mr5"></span>Separated</label>
						</div>
					</div>
					
					<div class="col-md-5">
                      <label class="clearfix">Date of Birth</label>
                         <div class="clearfix">
                           <div class="input-group">
                              <input type="text" value="" name="dateofBirth" class="form-control calendar" id="dateofBirth" readonly="readonly">
                              <span class="input-group-addon" onClick="clearDateOfBirth();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                           </div>
                        </div>
                  </div>
                
               <div class="col-md-5">
                  <label class="clearfix">Department<span class="text-danger">*</span> </label>
                  <div class="clearfix chooRadius" id="chosen-role">
                   <div class="input-group col-md-10 pull-left">
                         <select class="form-control chosen-select" id="department" name="department" field-name="Department" onchange="getReportingManager()" data-validation="required" >
			              </select>
			               <span class="input-group-addon cursorPointer bg-blue" onclick="getDepartmentsList();"><i class="fa fa-refresh "></i></span>
                  </div>
                  <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')"> 
			<a href="<c:url value="/app/addDepartment " />" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
			</sec:authorize>
                </div> 
                 </div> 
                <div class="col-md-5">
         <label class="clearfix">Employment type<span class="text-danger">&nbsp;*</span></label>
         <div class="clearfix">
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
                
                <div class="col-md-5 errorInptgroup">
                  <label class="clearfix">Role<span class="text-danger">&nbsp;*</span></label>
                  <div class="clearfix chooRadius chooErrorMessage" id="chosen-role">
                   <div class="input-group col-md-10 pull-left">
                         <select class="form-control select-bg selectField" id="role" name="roleType" field-name="Role" onchange="getReportingManager()" data-validation="required" >
		                        <option value="">-Select Role-</option>
		                        <c:forEach var="option" items="${rolesList}">
									<option value="${option.id}">${option.displayName}</option>
								</c:forEach>
			              </select>
			               <span class="input-group-addon cursorPointer bg-blue" onclick="refreshRoleList();managerListEmpty();"><i class="fa fa-refresh "></i></span>
                  </div>
                       <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE ')"> 
			             <a href="<c:url value="/app/orgRolesList" />" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
			           </sec:authorize>
                </div>
                </div> 
               
               <div class="col-md-5 designationTypeDiv" id="designationTypeDiv" style="display:none">
            <label class="clearfix">Employment Other Type</label>
            <div class="clearfix">
              <input type="text" class="form-control" id="designationOtherType" name="designationOtherType"   field-name="Job Type" value="">
            </div>
          </div>
       
       <span id="hideVendor" style="display:none">
       <div class="col-md-5">
			<label class="clearfix" ><span class="pull-left">Vendor <i class='fa fa-info-circle' title='Select/Add the vendor details of the respective associate. (This applies to C2C and C2C 1099 employment types)'></i></span><span class="text-danger pull-left" id="man1">*</span></label>
			<div class="clearfix chooRadius" id="errorvendor">
			 <div class="input-group col-md-10 pull-left">
			     <select  class="chosen-select form-control" name="vendors" id="vendors" placeholder="select Vendor" field-name="Vendor" onchange="getVendorContactList();vendorContactEmpty();removeValiationVendor();">
                 </select>
                  <span class="input-group-addon cursorPointer bg-blue" onclick="getVendorsByOrgLocationId();vendorContactEmpty();"><i class="fa fa-refresh "></i></span>
			</div>
			
			<span class="col-md-2 noPad clearfix">
			<sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR')"> 
			<a href="<c:url value="/app/addVendor" />" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
			</sec:authorize>
			</span>
		</div>
		</div>
		 <div class="col-md-5">
			<label class="clearfix" ><span class="pull-left">Vendor Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
			<div class="clearfix chooRadius" id="errorvendorContact">
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
               
              <div class="col-md-5">
                  <label class="clearfix">Manager<span class="text-danger">&nbsp;* </span><i class='fa fa-info-circle' title='This will represent the reporting manager for a associate in organisation'></i></label>
                  <div class="clearfix">
                      <select class="form-control chosen-select" name="reportingManager" id="reportingManager" field-name="Manager" data-validation="required">
                        <option value="">-Select Manager-</option>
                        <%-- <c:forEach var="option" items="${reportingManagersList}">
								<option value="${option.id}">${option.firstName} ${option.lastName} (${option.userType})</option>
						</c:forEach> --%>
                      </select>
                  </div>
            </div>
            		
            
                
                 <div class="col-md-5">
                     <label class="clearfix">Rate</label>
                     <div class="clearfix">
                         <div class="input-group er ratetype">
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
                
            <div class="col-md-5" id = "DayHoursDiv">
                  <label class="clearfix">Day hours</label>
                  <div class="clearfix">
                    <input type="text" class="form-control" id="DayHours" placeholder="HH:MM" value="${averageWorkingHours}" name="DayHours" data-validation="validate_Space validate_length length1-9" data-validation-optional="true" onblur="editMe(this);validateDayHours();">
                  </div>
                </div>
            
            <div class="col-md-5">
		             <label class="clearfix"></label>
		              <div class="clearfix">
		                <div class="checkbox checkBox">
		                  <label>
		                    <input type="checkbox" value="Y" name="isMailSent" checked="checked">
		                    Send Mail to Associate</label>
		                </div>
		                 </div>
               </div>
                <c:if test="${!empty sourceleadSectionList}">
	            	<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
	               		<c:if test="${sectionOrder.index == 0}">
	                		  <div id="customDiv${section.id}"></div>
	              		 </c:if>
	            	</c:forEach>
	   			</c:if>
            </div>
            </div>
                
            <div class="clearfix footerstyelsNew row">
              <div class="col-lg-12">
                <div class="pull-right">
                  <input type="button" value="Save & Continue" id="create_user" onclick="createUser();" class="btn btn-success btnSuccess">
                  <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/usersList'/>'"  class="btn btn-default ml">
                </div>
              </div>
            </div>
            </form:form>
          </div>
        </div>
        </div>
         <jsp:include page="associateSalary.jsp" />
       <jsp:include page="associateBankData.jsp" />
       <jsp:include page="associateTax.jsp" />
       <jsp:include page="associateDeduction.jsp" />
        <jsp:include page="associateContribution.jsp" />
        <!--New theme ends -->
			</div>
      </div>
      </section>
      <!-- Content Area Ends--> 

      <!-- Footer Area -->
      <jsp:include page="../masterFooter.jsp" />
      <!-- Footer Area -->


<script>
function getCustomDependentField(sectionId){
	 $.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getAllASectionCustomFields?sectionId="+sectionId,
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
 </script>

<script>
 function getVendorsByOrgLocationId(){
	 
	 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getVendorsByOrgLocationId?", 
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
 function getVendorContactList(){
	 var orgRelationID = $('#vendors').val();
	 
	 if(orgRelationID != null && orgRelationID !='' && orgRelationID !='undefined'){
		 $.ajax({
			    type: "POST",
		        url: sourceleadUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
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

<script type="text/javascript">
 function createUser() {
	 
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
			$("#create_user").attr("disabled", "disabled");
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            $( "#create_user" ).addClass('button').val('Processing..');
            
            $.ajax({
				type : "POST",
        	    data: formData,
				url : sourceleadUrl+"/createUser?"+tokenParamName+"="+tokenValue+"",
				success : function(result) {
					if(result.response === "DataMissing") {
						$("#create_user").removeAttr("disabled");
						$("#create_user").removeAttr('button').val('Save');	
						showError("Invalid data");
						$("#loading").removeAttr('style');
					} 
					else {
						$("#associateId").val(result.object.userId);
						var associateBanking = document.getElementById("associateBanking");
						document.getElementById("associateBanking").removeAttribute("class"); 
						associateBanking.href="#tab-2"; 
						$("#associateBanking").click();
						/* location.replace(sourceleadUrl+"/usersList"); */
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
                url: sourceleadUrl+"/validateEmployeeId?"+tokenParamName+"="+tokenValue+"&EmployeeId="+EmployeeId,
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
                         $("#create_user" ).addClass('us-btn').val('Save');
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
		        url: sourceleadUrl+"/validateUserEmail?"+tokenParamName+"="+tokenValue+"&EmailId="+EmailId, 
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
		        		 $("#create_user" ).addClass('us-btn').val('Save');
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
		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUserByUserEmail?"+tokenParamName+"="+tokenValue+"&EmailId="+EmailId, 
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
		        			 //$("#phoneNumber").val('');
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
	        url: sourceleadUrl+"/getActiveAssociateDetails?"+tokenParamName+"="+tokenValue+"&candidateId="+employeementType, 
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
// 	        		  $("#RatePer").val(json['currentRateType']);
	        		  $("select[name=RatePer]").val(json['currentRateType']);
		        	  $("#RatePer").selectpicker('refresh');
		        	  
	        		  }else{
	        			  $("#Rate").val('');
	        		  }
	        		  
	        		  $("select[name=currentCurrency]").val(json['currentRateCurrency']);
		        	  $("#currentCurrency").selectpicker('refresh');
		        	  
		        	  
// 	        		  $("#currentCurrency").val(json['currentRateCurrency']);
	        		  
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
	    	url: sourceleadUrl+"/getActiveAssociateList?"+tokenParamName+"="+tokenValue+"&employeementType="+employeementType,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-- Select Associate --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		var name = '';
	        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
	        			name = opt.firstName+" "+opt.lastName;
	        		}else{
	        			name = opt.firstName;
	        		}
		        		
	        		output = output + "<option value='" +opt.id+ "'>"+name+"</option>";
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
	    	url: sourceleadUrl+"/getDepartmentsList?"+tokenParamName+"="+tokenValue+"",
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
	 $("#candidatesLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
	    	type: "POST",
	    	url: sourceleadUrl+"/getActiveAssociateList?"+tokenParamName+"="+tokenValue+"&employeementType="+employmentDesignationType,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-- Select Candidate --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		var name = '';
	        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
	        			name = opt.firstName+" "+opt.lastName;
	        		}else{
	        			name = opt.firstName;
	        		}
		        		
	        		output = output + "<option value='" +opt.id+ "'>"+name+"</option>";
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
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  // written common method in slead-common.js for date formats start
	  var finalDateFormat = getJSDateFormat(locationDate);
	  //end
	  $('#DateofJoin').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  maxDate:0
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
  } );
</script>
<!-- end script for datepicker -->

 
<script type="text/javascript">
 function getReportingManager() {
	 $('#chosen-role').find('p.jquery_form_error_message').remove();
	 var role = document.getElementById("role").value; 
	 if(role != null && role != "" && role != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getReportingManager?"+tokenParamName+"="+tokenValue+"&role="+role, 
		        success: function(data) {
		        	var json = JSON.parse(data);
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
	       url: sourceleadUrl+"/refreshRoleList",       
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
	 function managerListEmpty(){
		 $('#reportingManager').empty();
		 $('#reportingManager').append('<option value="">--Select Manager--</option>');
		 $('#reportingManager').trigger("chosen:updated");
	 }
	 </script>
	 
	 
	 
	 <script>
	 function selectedVendor(){
		 var vid = $('#vendors').val();
		 window.open(sourceleadUrl+"/addVendorContact?vid="+vid,'_blank');
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
<script>
function clearDateofJoin(){
	$("#DateofJoin").val("");
	};
</script>

<script>
// script for tabs

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
				
				// associateContribution associateAdvances

$(document).ready(function() {
	$("#tab-1").show()
    $(".tabsMenu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".tabsMenu_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});
</script> 



