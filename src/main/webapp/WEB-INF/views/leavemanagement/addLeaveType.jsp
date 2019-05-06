<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="/resources/js/summernote.js?staticTime=${staticTime}"></script>
<link href="/resources/css/summernote.css?staticTime=${staticTime}" rel="stylesheet" />
<script src="/resources/js/editor.js?staticTime=${staticTime}"></script>
<link href="/resources/css/editor.css?staticTime=${staticTime}" type="text/css" rel="stylesheet"/>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


		<link href="/resources/css/bootstrap-colorpicker.min.css" rel="stylesheet">
		<script src="/resources/js/bootstrap-colorpicker.min.js"></script>
		   
	
	
<script>
	$(document).ready(function(){
		var leaveTypeId = '${leaveType.id}';
		if(leaveTypeId != null && leaveTypeId != '' && leaveTypeId != 'undefined'){
			$("#leaveTypeId").val(leaveTypeId);
		}else{
			$("#leaveTypeId").val(0);
		}
		var policy = '${leaveType.leavePolicy}';
		if (policy == 'Y') {
			leavePolicy();
			leavepolicyDescription();
		}
		var applicable = '${leaveType.applicableToUsers}';
		if (applicable == 'N') {
			noUsers();
		}else if(applicable == 'Y'){
			yesUsers();
		}
		var leaveEffective = '${leaveType.leaveEffective}';
		if (leaveEffective == 'All Employees' || leaveEffective == '') {
			hideExperienced();
			}else{
			showExperienced();
		}
		var priorLeave = '${leaveType.priorLeaveApplying}';
		if (priorLeave == 'Y') {
			$("#priorDaysdiv").show();
			$('#priorDays').removeAttr('data-validation-optional');
			$('#priorDays').attr('data-validation', "required validate_length length1-250");
			$('#priorLeaveDaysDiv').find('p.jquery_form_error_message').remove();
			
		}
		var leaveDur = '${leaveType.leaveDuration}';
		if (leaveDur == 'Y') {
			$("#minMaxDaysdiv").show();
			$('#minDays').removeAttr('data-validation-optional');
			$('#minDays').attr('data-validation', "required validate_length length1-250");
			$('#maxDays').removeAttr('data-validation-optional');
			$('#maxDays').attr('data-validation', "required validate_length length1-250");
			$('#minimumDaysDiv').find('p.jquery_form_error_message').remove();
			$('#maximumDaysDiv').find('p.jquery_form_error_message').remove();
		}
		
		var autoAppr = "${leaveType.autoApprove}";
		if(autoAppr == 'Y'){
			if("${leaveType.autoApproveType}" == "Grade"){
				$("#chosenRole").hide();
				$("#chosenGrade").show();
			}
			if("${leaveType.autoApproveType}" == "Role"){
				 $("#chosenGrade").hide();
				 $("#chosenRole").show();
			}
			$('#autoApproveDiv').show();
		}else{
			$('#autoApproveDiv').hide();
		}
		carryForwardYesUsers();
		
	});
</script>
    
    
    <script>
    function removeErrMsg(){
    	$("#departmentDiv").find("p.jquery_form_error_message").remove();
    }
    function removeRoleErrMsg(){
    	$("#rolesDiv").find("p.jquery_form_error_message").remove();
    }
    	function validateColor(){
    	$("#cp").find("p.jquery_form_error_message").remove();
    	}
    	
    </script>
<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Leave Type</h2>
		</div>
		<c:if test="${empty leaveType.id}">
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a 
			    href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo;<a
				href="<c:url value='${appPath}/leaveConfiguration'/>"> Leave Configuration</a>
			&raquo;<a href="<c:url value='${appPath}/viewLeaveTypes'/>">Leave type List</a>
			&raquo; Add Leave Type
		</div>
		</c:if>
		<c:if test="${!empty leaveType.id}">
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='${appPath}/leaveConfiguration'/>"> Leave Configuration</a>
			&raquo;<a href="<c:url value='${appPath}/viewLeaveTypes'/>">Leave type List</a>
			&raquo; Update Leave Type
		</div>
		</c:if>
	</section>
	<div class="content-area content-new clearfix">
		<form:form id="addLeaveType_form" name="addLeaveType_form"
			method="post">
			<div id="loading"></div>
			<div class="clearfix form-horizontal inner-content">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-7 col-md-offset-3">
						<div class="form-group">
							<label class="col-md-4">Name<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-6" id="leaveTypeDiv">
								<input type="hidden" id="leaveTypeId" name="leaveTypeId" value="" />
								 <input type="text" class="form-control" id="leaveTypeName" name="name" value="${leaveType.name}" onblur="validateName();" field-name="Name"
									data-validation="required validate_Space validate_length length1-250">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4">Duration<span class="text-danger">&nbsp;*</span>
							<div class="tooltip">
							<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
							<span class="tooltiptext">User can apply leave for both full day & half-day for this leave type.</span>
							</span>
							</div>
							</label>
							<div class="col-md-6 dropdown-select">
								<div class="paddingLeft paddingRight"> <c:if
											test="${!empty leaveType.duration}">
<%-- 											<c:if test = "${fn:contains(leaveType.duration, 'Full Day')}"> --%>
											
											<c:if test = "${fn:contains(leaveType.duration, 'Full Day') && !fn:contains(leaveType.duration, 'Half Day')}">
												<input type="checkbox" checked="true" id="fullDayId" name="duration" value="Full Day" >
												<span class="paddingLeft">Full Day</span>
												<input type="checkbox" id="halfDayId" name="duration" value="Half Day">
												<span class="paddingLeft">Half Day</span>
												 
											</c:if>
											<c:if test = "${fn:contains(leaveType.duration, 'Half Day') && !fn:contains(leaveType.duration, 'Full Day') }">
											<input type="checkbox" id="fullDayId" name="duration" value="Full Day">
												<span class="paddingLeft">Full Day</span>
												
												<input type="checkbox" checked="true" id="halfDayId" name="duration" value="Half Day" >
												<span class="paddingLeft">Half Day</span>
											</c:if>
											<c:if test = "${fn:contains(leaveType.duration, 'Half Day') && fn:contains(leaveType.duration, 'Full Day') }">
											<input type="checkbox" checked="true"  id="fullDayId" name="duration" value="Full Day">
												<span class="paddingLeft">Full Day</span>
												
												<input type="checkbox" checked="true" id="halfDayId" name="duration" value="Half Day">
												<span class="paddingLeft">Half Day</span>
											</c:if>
										</c:if> <c:if test="${empty leaveType.duration}">
											<input type="checkbox" id="fullDayId" name="duration" value="Full Day">
											<span class="paddingLeft">Full Day</span>
											<input type="checkbox"  id="halfDayId" name="duration" value="Half Day">
											<span class="paddingLeft">Half Day</span>
										</c:if>
									</div>
									<input type="hidden"  id="leaveValue" name="leaveValue"  field-name="Duration" data-validation="required"/>
									<%-- <option ${leaveType.duration  == 'Full Day' ? 'selected' : ''}>Full Day</option>
									<option ${leaveType.duration  == 'Half Day' ? 'selected' : ''}>Half Day</option> --%>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4">Type</label>
							<div class="col-md-6">
								<div class="clearfix mb">
									<label class="paddingLeft paddingRight"> <c:if
											test="${!empty leaveType.leaveType}">
											<c:if test="${leaveType.leaveType == 'Billable'}">
												<input checked="true" type="radio" id="billableId"
													name="billingType" value="Billable">
												<span class="paddingLeft">Billable</span>
												<input type="radio" id="nonBillableId" name="billingType"
													value="Non Billable">
												<span class="paddingLeft">Non Billable</span>
											</c:if>
											<c:if test="${leaveType.leaveType == 'Non Billable'}">
												<input type="radio" id="billableId" name="billingType"
													value="Billable">
												<span class="paddingLeft">Billable</span>
												<input checked="true" type="radio" id="nonBillableId"
													name="billingType" value="Non Billable">
												<span class="paddingLeft">Non Billable</span>
											</c:if>
										</c:if> <c:if test="${empty leaveType.leaveType}">
											<input checked="true" type="radio" id="billableId"
												name="billingType" value="Billable">
											<span class="paddingLeft">Billable</span>
											<input type="radio" id="nonBillableId" name="billingType"
												value="Non Billable">
											<span class="paddingLeft">Non Billable</span>
										</c:if>

									</label>
								</div>
							</div>
						</div>
						<div class="form-group">

							<label class="col-md-4">Applicable to All Users
							<div class="tooltip"><span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
							<span class="tooltiptext">If set to yes leave type will be applicable to all users in the organization.If set to no leave type will be applicable to users based on configured criteria</span>
							</span>
							</div>
							</label>
							<div class="col-md-6">

								<div class="clearfix mb">
									<c:if test="${!empty leaveType.applicableToUsers}">
										<c:if test="${leaveType.applicableToUsers == 'Y'}">
											<label class="paddingLeft paddingRight" onClick="yesUsers();">
												<input type="radio" checked="true" value="Y"
												name="applicableToUsers"> <span class="paddingLeft">Yes</span>
											</label>
											<label class="paddingLeft"> <input
												type="radio" id="noUsers" value="N" name="applicableToUsers" onClick="noUsers();">
												<span class="paddingLeft">No</span></label>
										</c:if>

										<c:if test="${leaveType.applicableToUsers == 'N'}">
											<label class="paddingLeft paddingRight" onClick="yesUsers();">
												<input type="radio" value="Y" name="applicableToUsers">
												<span class="paddingLeft">Yes</span>
											</label>
											<label class="paddingLeft" onClick="noUsers();"> <input
												type="radio" checked="true" id="noUsers" value="N"
												name="applicableToUsers"> <span class="paddingLeft">No</span></label>
										</c:if>

									</c:if>

									<c:if test="${empty leaveType.applicableToUsers}">
										<label class="paddingLeft paddingRight" onClick="yesUsers();">
											<input type="radio" checked="true" value="Y"
											name="applicableToUsers"> <span class="paddingLeft">Yes</span>
										</label>
										<label class="paddingLeft" onClick="noUsers();"> <input
											type="radio" id="noUsers" value="N" name="applicableToUsers">
											<span class="paddingLeft">No</span></label>
									</c:if>

								</div>
							</div>
						</div>

						<div class="clearfix" id="noUsersDiv" style="display: none;" class="deprolemp">
							<div class="clearfix"></div>
							<div class="form-group" id="departmentDiv">
								<label class="col-md-4">Select Department</label>
								<div class="col-md-6 dropdown-select">
									<select class="chosen-select form-control" multiple name="department" onchange="removeErrMsg();"
										id="department" field-name="Department" data-placeholder="Select Departmnets"
										data-validation-optional="true">
										<c:forEach var="report" items="${departmentList}">
										    <c:set var="NUM" value="${0}" />
											<c:forEach var="existedDepartment" items="${leaveType.departmentIds}">
												<c:if test="${existedDepartment eq report.id}">
													<c:set var="NUM" value="${1}" />
													<option selected value="${report.id}">${report.departmentName}</option>
												</c:if>
											</c:forEach>
											<c:if test="${NUM == 0}">
												<option value="${report.id}">${report.departmentName}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group" id="rolesDiv">
								<label class="col-md-4">Select Roles</label>
								<div class="col-md-6 dropdown-select" id="roleErrMsg">
								<select class="form-control chosen-select choose" multiple id="roles" name="roles"
										field-name="Roles" data-validation-optional="true" data-placeholder="Select Roles" onchange="removeRoleErrMsg()">
									
										<c:forEach var="role" items="${rolesList}">
											<c:set var="NUM" value="${0}" />
											<c:forEach var="existedRoles" items="${existedRoles}">
												<c:if test="${existedRoles.id eq role.id}">
													<c:set var="NUM" value="${1}" />
													<option selected value="${role.id}">${existedRoles.displayName}</option>
												</c:if>
											</c:forEach>
											<c:if test="${NUM == 0}">
												<option value="${role.id}">${role.displayName}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
							
										
							<div class="form-group" id="rolesDiv">
								<label class="col-md-4">Select Employement Type</label>
								<div class="col-md-6 dropdown-select" id="empTypeErrMsg">
									<select class="chosen-select form-control"  multiple id="leveEmpType" name="leveEmpType" value="${leaveType.leaveEmpType}"
										field-name="Employement Type" data-placeholder="Select Employement Type" data-validation-optional="true">
						                 <c:forEach var="employmentType" items="${employmentTypeList}">
											<c:set var="NUM" value="${0}" />
											<c:forEach var="dbEmpType" items="${leaveType.leaveEmpType}">
												<c:if test="${dbEmpType eq employmentType}">
													<c:set var="NUM" value="${1}" />
													<option selected >${dbEmpType}</option>
												</c:if>
											</c:forEach>
											<c:if test="${NUM == 0}">
												<option >${employmentType}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
								<p Style="color: red; display: none;" id="applicableId" >Select Atleast One Field</p>
							</div>
							

						</div>
						<div class="form-group">

							<label class="col-md-4">Leaves Carryforward
							<div class="tooltip">
								<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
								  <span class="tooltiptext">Leaves will be carry forwarded based on leave type accrual period</span>
								  </span>
								</div>
							</label>
							<div class="col-md-6">	

								<div class="clearfix mb">
									<c:if test="${!empty leaveType.leavesCarryforward}">
										<c:if test="${leaveType.leavesCarryforward == 'Y'}">
											<label class="paddingLeft paddingRight" onClick="carryForwardYesUsers();"> <input
												type="radio" checked value="Y" name="carryforward">
												<span class="paddingLeft">Yes</span></label>
											<label class="paddingLeft" onClick="carryForwardNoUsers();"> <input type="radio"
												value="N" name="carryforward"> <span
												class="paddingLeft">No</span></label>
										</c:if>
										<c:if test="${leaveType.leavesCarryforward == 'N'}">
											<label class="paddingLeft paddingRight" onClick="carryForwardYesUsers();"> <input
												type="radio" value="Y" name="carryforward"> <span
												class="paddingLeft">Yes</span></label>
											<label class="paddingLeft" onClick="carryForwardNoUsers();"> <input type="radio"
												checked value="N" name="carryforward"> <span
												class="paddingLeft">No</span></label>
										</c:if>
									</c:if>
									<c:if test="${empty leaveType.leavesCarryforward}">
										<label class="paddingLeft paddingRight" onClick="carryForwardYesUsers();"> <input
											type="radio" checked value="Y" name="carryforward"> <span
											class="paddingLeft">Yes</span></label>
										<label class="paddingLeft" onClick="carryForwardNoUsers();"> <input type="radio"
											 value="N" name="carryforward"> <span
											class="paddingLeft">No</span></label>
									</c:if>
								</div>
							</div>

						</div>
						
						<div class="form-group" id="noOfDaysDiv">
							<label class="col-md-4">Max no of Days to Carry forward
							<div class="tooltip">
							<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
							<span class="tooltiptext">No of days will be carry forward for next year.</span>
							</span>
							</div>
							</label>
							<div class="col-md-3" id="errorNoOfdaysToCarryforward">
								<input type="text" id="noOfdaysToCarryforward" name="noOfdaysToCarryforward" field-name="No Of Days To CarryForward" onblur="validateNoDays();" placeholder="Enter no of Days" class="form-control" value="${leaveType.maxDaysToCarryforward}">
							</div>
						</div>
						
						
						<div class="form-group">
						 <label class="col-md-4">Leave Effective<span
								class="text-danger paddingLeft">*</span>
								<div class="tooltip">
								<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
								<span class="tooltiptext">Configure that leave type applicable to users based on experience level in organisation.</span>
								</span>
								</div>
								</label>
							<div class="col-md-6">
							
							
							<c:if test="${!empty leaveType.leaveEffective}">
										<c:if test="${leaveType.leaveEffective == 'All Employees'}">
											<label class="paddingLeft paddingRight" onClick="hideExperienced();">
												<input type="radio" checked="true" value="All Employees" name="leaveEffective"> <span class="paddingLeft">All Employees</span>
											</label>
											<label class="paddingLeft" onClick="showExperienced();">
											 <input type="radio" value="Experienced" name="leaveEffective">
												<span class="paddingLeft">Experienced</span></label>
										</c:if>

										<c:if test="${leaveType.leaveEffective == 'Experienced'}">
											<label class="paddingLeft paddingRight" onClick="hideExperienced();">
												<input type="radio" value="All Employees" name="leaveEffective"> <span class="paddingLeft">All Employees</span>
											</label>
											<label class="paddingLeft" onClick="showExperienced();">
											 <input type="radio" value="Experienced" checked="true" name="leaveEffective">
												<span class="paddingLeft">Experienced</span></label>
										</c:if>
									</c:if>

									<c:if test="${empty leaveType.leaveEffective}">
										<label class="paddingLeft paddingRight" onClick="hideExperienced();">
												<input type="radio" checked="true" value="All Employees" name="leaveEffective"> <span class="paddingLeft">All Employees</span>
											</label>
											<label class="paddingLeft" onClick="showExperienced();">
											 <input type="radio" value="Experienced" name="leaveEffective">
												<span class="paddingLeft">Experienced</span></label>
									</c:if>
							</div>
						</div>
						
						<div class="form-group" id="noOfDaysAnnuallyDiv">
							<label class="col-md-4">No Of Days Annually<span class="text-danger paddingLeft" >*</span>
							<div class="tooltip">
							<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
							<span class="tooltiptext">No of leaves are applicable for this leave type annually</span>
							</span>
							</div>
							</label>
							<div class="col-md-3">
							   <input type="hidden" name="previousNoOfdays" id="previousNoOfdays" value="${leaveType.noOfDays}"/>
								<input type="text" id="noOfDays" name="noOfDays" field-name="No Of Days" placeholder="Enter no of Days" onblur="getNoOfDaysPerPeriod();" class="form-control" value="${leaveType.noOfDays}">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4">Accrual Period<span
								class="text-danger paddingLeft">*</span>
								<div class="tooltip">
								<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
								<span class="tooltiptext">Configure how the leave type should calculate Annually/Half-yearly/Quarterly/Monthly.</span>
								</span>
								</div>
								</label>
							<div class="col-md-6 dropdown-select">
								<select class="form-control" name="period" id="period" onchange="getNoOfDaysPerPeriod();">
								<option ${leaveType.period == 'Annually' ? 'selected' : ''}>Annually</option>
								<option ${leaveType.period == 'HalfYearly' ? 'selected' : ''}>HalfYearly</option>
								<option ${leaveType.period == 'Quarterly' ? 'selected' : ''}>Quarterly</option>
								<option ${leaveType.period == 'Monthly' ? 'selected' : ''}>Monthly</option>
<%-- 								<option ${leaveType.period == 'SemiMonthly' ? 'selected' : ''}>SemiMonthly</option> --%>
<%-- 								<option ${leaveType.period == 'BiWeekly' ? 'selected' : ''}>BiWeekly</option> --%>
								</select>
							</div>
						</div>
						
						<div class="form-group" id="calculatedNoOfDaysDiv">
							<label class="col-md-4">No Of Days per Period<span class="text-danger paddingLeft" ></span></label>
							<div class="col-md-3">
							    <input type="hidden" value="${leaveType.noOfDaysPerPeriod}" id="previousNoOfDaysPerPeriod" name="previousNoOfDaysPerPeriod"/>
								<input type="text" id="noOfDaysPerPeriod" name="noOfDaysPerPeriod" field-name="No Of Days" readonly placeholder="Enter no of Days" class="form-control" value="${leaveType.noOfDaysPerPeriod}">
							</div>
						</div>
						

						
						
				<!-- experiencedGroup -->
					<div id="experiencedGroup" style="display:none">
						<div class="form-group" id="newJoineeDiv">
							<label class="col-md-4">No.of Days for Fresher<span class="text-danger paddingLeft" >*</span></label>
							<div class="col-md-3">
								<input type="hidden" name="previousNewJoinee" id="previousNewJoinee" value="${!empty leaveType.newJoinee ? leaveType.newJoinee : 0}"/>
								<input type="text" id="newJoinee" name="newJoinee" placeholder="Enter no.of Days" field-name="ApplicableDays" onblur="getNoOfDaysPerPeriodFresher();" class="form-control" value="${leaveType.newJoinee}">
							</div>
						</div>
						
						<div class="form-group" id="newJoineeDiv2">
							<label class="col-md-4">No.of Days for Fresher Period</label>
							<div class="col-md-3">
								<input type="hidden" name="previousFresherPeriod" id="previousFresherPeriod" value="${!empty leaveType.newJoinPerPeriod ? leaveType.newJoinPerPeriod : 0}"/>
								<input type="text" id="fresherPeriod" name="fresherPeriod" placeholder="Enter Per Day"  class="form-control" value="${leaveType.newJoinPerPeriod}" readonly>
							</div>
						</div>
						
						<div class="form-group" id="experiencedDiv">
							<label class="col-md-4">Experienced<span class="text-danger paddingLeft" >*</span></label>
							<div class="col-md-6">
								<div class="row">
								<div class="col-md-4 padding_right">
									<input type="text" class="form-control" id="experiencedDuration" name="experiencedDuration" placeholder="Experience" field-name="Duration" value="${leaveType.experiencedDuration}">
								</div>
								<div class="col-md-4 dropdown-select padding_right padding_left">
									<select id="experiencedType" name="experiencedType" field-name="experiencedType" >
									<option ${leaveType.experiencedType == 'Year' ? 'selected' : ''}>Year</option>
									<option ${leaveType.experiencedType == 'Month' ? 'selected' : ''}>Month</option>
									<option ${leaveType.experiencedType == 'Days' ? 'selected' : ''}>Days</option>
									</select>
								</div>
								<div class="col-md-4 padding_left" id="experiencedApplicableDaysDiv">
									<input type="hidden" name="previousExpApplicableDays" id="previousExpApplicableDays" value="${!empty leaveType.experiencedApplicableDays ? leaveType.experiencedApplicableDays : 0}"/>
									<input type="text" class="form-control" id="experiencedApplicableDays" name="experiencedApplicableDays" placeholder="No. of Days" onblur="getNoOfDaysPerPeriodExperienced();" field-name="ApplicableDays" value="${leaveType.experiencedApplicableDays}">
								</div>
							</div>
						</div>
						<div class="clearfix mt mb"></div>
						<label class="col-md-4">No.of Days for Experienced Period</label>
						<div class="col-md-3">
						<input type="hidden" name="previousExpPeriodDays" id="previousExpPeriodDays" value="${!empty leaveType.experiencedPerPeriod ? leaveType.experiencedPerPeriod : 0}"/>
						<input type="text" class="form-control" id="experiencedPeriod" name="experiencedPeriod" placeholder="Experience" field-name="Duration" value="${leaveType.experiencedPerPeriod}" readonly>
						</div>
						</div>
						
					</div>
<!-- experiencedGroup -->

						<div class="form-group">

							<label class="col-md-4">Color Picker<span
								class="text-danger">&nbsp;* </span>
								<div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Color coding for the leave type throughout the application.</span>
									</span>
								</div>
								</label>
							<div class="col-md-6">

							
								<div id="cp"
									class="input-group errorInptgroup">
									<input type="text" name="colorCode" field-name="Color Picker"
										placeholder="Select Color Picker" readonly="readonly"
										value="${leaveType.colorCode}" class="form-control" id="colorCode" 
										data-validation="required"> 
											<span class="input-group-addon colPicBox"><input type="color" class="" 
											
											value="${leaveType.colorCode}" 
											
											id="favcolor" onclick="validateColor(); colorPicker();"></span>
								</div>
								<div>
								
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4">Effective Gender<span
								class="text-danger">&nbsp;* </span></label>
							<div class="col-md-6 dropdown-select">
								<select class="form-control" name="effectiveGender" field-name="Effective Gender" data-validation="required">
									<option ${leaveType.effectiveGender == 'All' ? 'selected' : ''}>All</option>
									<option ${leaveType.effectiveGender == 'Male' ? 'selected' : ''}>Male</option>
									<option ${leaveType.effectiveGender == 'Female' ? 'selected' : ''}>Female</option>
									<option ${leaveType.effectiveGender == 'Transgender' ? 'selected' : ''}>Transgender</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4">Allowability
							<div class="tooltip">
							<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
							<span class="tooltiptext">User can apply for this leave type number of times configured</span>
							</span>
							</div>
							</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="leaveAllowability" placeholder="Enter no.of Times" id="leaveAllowability" value="${leaveType.allowability}" field-name="Allowability" data-validation-optional="true" data-validation="validate_int validate_length length1-10">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4">Description</label>
							<div class="col-md-6">
								<textarea class="form-control" rows="2" name="description"
									data-validation="validate_length length1-500"
									data-validation-optional="true">${leaveType.description}</textarea>
							</div>
						</div>
										
										
						<div class="form-group">
							<label class="col-md-4 checkBox">
								<c:if test="${leaveType.autoApprove == 'Y'}">
									<input type="checkbox" name="autoApprove" value="Y" checked id="autoApproveChekBox" onclick="validateAutoApprove();">Auto approve
									<div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Leave will be a auto approve for the configured roles/grades.</span>
									</span>
									</div>
								</c:if>
								<c:if test="${leaveType.autoApprove == 'N' || empty leaveType.autoApprove}">
									<input type="checkbox" name="autoApprove" value="Y" id="autoApproveChekBox" onclick="validateAutoApprove();">Auto approve
									<div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Leave will be a auto approve for the configured roles/grades.</span>
									</span>
									</div>
								</c:if>
							</label>
							<div class="col-md-6">
								<div class="clearfix" id="autoApproveDiv">
									<label class="radioIput">
										<c:if test="${leaveType.autoApproveType == 'Grade'}">
											<input type="radio" name="autoApproveType" id="grade" onclick="getGradeList();" checked value="Grade">Grade
										</c:if>
										<c:if test="${leaveType.autoApproveType != 'Grade'}">
											<input type="radio" name="autoApproveType" id="grade" onclick="getGradeList();" value="Grade">Grade
										</c:if>
									</label>
									<label class="radioIput ml">
										<c:if test="${leaveType.autoApproveType == 'Role'}">
											<input type="radio" name="autoApproveType" id="role" onclick="getRoleList();" checked value="Role">Role
										</c:if>
										<c:if test="${leaveType.autoApproveType != 'Role'}">
											<input type="radio" name="autoApproveType" id="role" onclick="getRoleList();" value="Role">Role
										</c:if>
									</label>
									
									<div class="clearfix"></div>
									<div class="row mt10">
										<div class="col-md-12" id="chosenGrade" style="display:none;">
											<select class="chosen-select" multiple id="gradeListId" data-placeholder="Select Grade" name="autoApproveTypeList" field-name="Grade">
												<c:forEach var="grade" items="${gradelist}">
													<c:set var="NUM" value="${0}" />
													<c:forEach var="existedGrade" items="${existGrade}">
														<c:if test="${existedGrade.id eq grade.id}">
															<c:set var="NUM" value="${1}" />
															<option selected value="${existedGrade.id}">${existedGrade.gradeName}</option>
														</c:if>
													</c:forEach>
													<c:if test="${NUM == 0}">
														<option value="${grade.id}">${grade.gradeName}</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-12" id="chosenRole" style="display:none;">
											<select class="chosen-select" multiple id="roleListId" data-placeholder="Select Role" name="autoApproveTypeList" field-name="Role">
												<c:forEach var="role" items="${rolesList}">
													<c:set var="NUM" value="${0}" />
													<c:forEach var="existedRoles" items="${autoApproveRole}">
														<c:if test="${existedRoles.id eq role.id}">
															<c:set var="NUM" value="${1}" />
															<option selected value="${role.id}">${existedRoles.displayName}</option>
														</c:if>
													</c:forEach>
													<c:if test="${NUM == 0}">
														<option value="${role.id}">${role.displayName}</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
										
										
										
							<div class="form-group">
								<div class="col-md-12">
								<div class="row clearfix">
								<span class="checkBox col-md-4">
								<c:if test="${leaveType.reasonRequired == 'Y'}">
									<input type="checkbox" checked="true" name="reasonRequired" id="reasonRequired" 
									value="Y"> Reason Required
									 <div class="tooltip">
										<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
										<span class="tooltiptext">Reason is required while applying for the leave.</span>
										</span>
										</div>
								</c:if>
								     <c:if test="${leaveType.reasonRequired == 'N'}">
								      <input type="checkbox" name="reasonRequired" id="reasonRequired" value="Y"> Reason Required 
								      <div class="tooltip">
										<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
										<span class="tooltiptext">Reason is required while applying for the leave.</span>
										</span>
										</div>
                                </c:if>
                                <c:if test="${empty leaveType.reasonRequired}">
								      <input type="checkbox" name="reasonRequired" id="reasonRequired"
									   value="Y"> Reason Required
									   <div class="tooltip">
										<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
										<span class="tooltiptext">Reason is required while applying for the leave.</span>
										</span>
										</div> 
                                </c:if>
<!-- 												<input type="checkbox" checked="true" name="reasonRequired" value="Y"> Reason Required -->
								</span>
								<span class="checkBox col-md-4"> 
								<c:if test="${leaveType.attachmentRequired == 'Y'}">
									<input type="checkbox" checked="true" name="attachmentRequired" id="attachmentRequired" 
									value="Y"> Attachment Required
									<div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Attachment is required while applying for the leave.</span>
									</span>
									</div>
								</c:if>
								     <c:if test="${leaveType.attachmentRequired == 'N'}">
								      <input type="checkbox" name="attachmentRequired" id="attachmentRequired"
									   value="Y"> Attachment Required 
									   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Attachment is required while applying for the leave.</span>
									</span>
									</div>
                                </c:if>
                                <c:if test="${empty leaveType.attachmentRequired}">
								      <input type="checkbox" name="attachmentRequired" id="attachmentRequired"
									   value="Y"> Attachment Required 
									   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Attachment is required while applying for the leave.</span>
									</span>
									</div>
                                </c:if>
<!-- 												<input type="checkbox" checked="true" name="attachmentRequired" value="Y"> Attachment Required -->
								</span>
								<span class="checkBox col-md-4">
								<c:if test="${leaveType.overdrawLeaves == 'Y'}">
									<input type="checkbox" checked="true" name="overdrawLeaves" id="overdrawLeaves" 
									value="Y"> Overdraw Leaves
								</c:if>
								     <c:if test="${leaveType.overdrawLeaves == 'N'}">
								      <input type="checkbox" name="overdrawLeaves" id="overdrawLeaves"
									   value="Y"> Overdraw Leaves 
                                </c:if>
                                <c:if test="${empty leaveType.overdrawLeaves}">
								      <input type="checkbox" name="overdrawLeaves" id="overdrawLeaves"
									   value="Y"> Overdraw Leaves 
                                </c:if>
<!-- 												<input type="checkbox" checked="true" name="overdrawLeaves" value="Y"> Overdraw Leaves -->
								</span>
								<div class="clearfix mb"></div>
								<span class="checkBox col-md-4">
								<c:if test="${leaveType.donateLeave == 'Y'}">
									<input type="checkbox" checked="true" name="donateLeave" id="donateLeave"
									 value="Y"> Donate Leave
									 <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">User can Donate leave for other users in the organization.</span>
									</span>
									</div>
								</c:if>
								     <c:if test="${leaveType.donateLeave == 'N'}">
								      <input type="checkbox" name="donateLeave" id="donateLeave"
									   value="Y"> Donate Leave 
									    <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">User can Donate leave for other users in the organization.</span>
									</span>
									</div>
                                </c:if>
                                <c:if test="${empty leaveType.donateLeave}">
								      <input type="checkbox" name="donateLeave" id="donateLeave"
									   value="Y"> Donate Leave 
									    <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">User can Donate leave for other users in the organization.</span>
									</span>
									</div>
                                </c:if>
								</span>
								
								<span class="checkBox col-md-4">
								<c:if test="${leaveType.timeSheetAutofill == 'Y'}">
									<input type="checkbox" checked="true" name="timeSheetAutofill" id="timeSheetAutoFill" 
									value="Y"> TimeSheet AutoFill
									<div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Time sheet will be auto fill once the leave got approved.</span>
									</span>
									</div>
								</c:if>
								     <c:if test="${leaveType.timeSheetAutofill == 'N'}">
								      <input type="checkbox" name="timeSheetAutofill" id="timeSheetAutoFill"
									   value="Y"> TimeSheet AutoFill
									   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Time sheet will be auto fill once the leave got approved.</span>
									</span>
									</div>
                                </c:if>
                                <c:if test="${empty leaveType.timeSheetAutofill}">
								      <input type="checkbox" name="timeSheetAutofill" id="timeSheetAutoFill"
									   value="Y"> TimeSheet AutoFill
									   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Time sheet will be auto fill once the leave got approved.</span>
									</span>
									</div>
                                </c:if>
								</span>
								
								
								<span class="checkBox col-md-4">
									<c:if test="${leaveType.includeWeekoff == 'Y'}">
										<input type="checkbox" checked="true" name="includeWeekoff" id="includeWeekoff" 
										value="Y">Include Week off 
									</c:if>
									     <c:if test="${leaveType.includeWeekoff == 'N'}">
									      <input type="checkbox" name="includeWeekoff" id="includeWeekoff"
										   value="Y">Include Week off 
	                                </c:if>
	                                <c:if test="${empty leaveType.includeWeekoff}">
									      <input type="checkbox" name="includeWeekoff" id="includeWeekoff"
										   value="Y">Include Week off 
	                                </c:if>
								</span>
								
								<div class="clearfix mb"></div>
								
								<span class="checkBox col-md-4">
									<c:if test="${leaveType.includeHolidays == 'Y'}">
										<input type="checkbox" checked="true" name="includeHoliday" id="includeHoliday" 
										value="Y">Include Holiday 
									<div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Leave duration will include Organisation holidays while applying for this leave type.</span>
									</span>
									</div>	
									</c:if>
									     <c:if test="${leaveType.includeHolidays == 'N'}">
									      <input type="checkbox" name="includeHoliday" id="includeHoliday"
										   value="Y">Include Holiday 
										   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Leave duration will include Organisation holidays while applying for this leave type.</span>
									</span>
									</div>	
	                                </c:if>
	                                <c:if test="${empty leaveType.includeHolidays}">
									      <input type="checkbox" name="includeHoliday" id="includeHoliday"
										   value="Y">Include Holiday
										   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Leave duration will include Organisation holidays while applying for this leave type.</span>
									</span>
									</div>	 
	                                </c:if>
								</span>
								<span class="checkBox col-md-4">
									<c:if test="${leaveType.visibleInBalanceLeaves == 'Y'}">
										<input type="checkbox" checked="true" name="visibleInBalanceLeaves" id="visibleInBalanceLeaves" 
										value="Y">Visible in Balance Leaves
											   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">You can choose to show or hide this type of leave balances.Even if this is not shown on balance leaves this leave type will be available in the leave type dropdown Apply Leave is clicked for any other leave type.</span>
									</span>
									</div>
									</c:if>
									     <c:if test="${leaveType.visibleInBalanceLeaves == 'N'}">
									      <input type="checkbox" name="visibleInBalanceLeaves" id="visibleInBalanceLeaves"
										   value="Y">Visible in Balance Leaves
										   	   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">You can choose to show or hide this type of leave balances.Even if this is not shown on balance leaves this leave type will be available in the leave type dropdown Apply Leave is clicked for any other leave type.</span>
									</span>
									</div>
	                                </c:if>
	                                <c:if test="${empty leaveType.visibleInBalanceLeaves}">
									      <input type="checkbox" checked="true"  name="visibleInBalanceLeaves" id="visibleInBalanceLeaves"
										   value="Y">Visible in Balance Leaves
										   	   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">You can choose to show or hide this type of leave balances.Even if this is not shown on balance leaves this leave type will be available in the leave type dropdown Apply Leave is clicked for any other leave type.</span>
									</span>
									</div>
	                                </c:if>
								</span>
								
								<span class="checkBox col-md-4">
									<c:if test="${leaveType.includeInPayroll == 'Y'}">
										<input type="checkbox" checked="true" name="includeInPayRoll" id="includeInPayRoll" 
										value="Y">Include in PayRoll
										  <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Configure this leave type should add into Payroll.</span>
									</span>
									</div>
									</c:if>
									     <c:if test="${leaveType.includeInPayroll == 'N'}">
									      <input type="checkbox" name="includeInPayRoll" id="includeInPayRoll"
										   value="Y">Include in PayRoll
										     <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Configure this leave type should add into Payroll.</span>
									</span>
									</div>
	                                </c:if>
	                                <c:if test="${empty leaveType.includeInPayroll}">
									      <input type="checkbox" checked name="includeInPayRoll" id="includeInPayRoll"
										   value="Y">Include in PayRoll
										     <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Configure this leave type should add into Payroll.</span>
									</span>
									</div>
	                                </c:if>
								</span>
								
					</div>
				</div>
			</div>
										
				<div class="form-group">
					<span class="checkBox col-md-4" onclick="showLeaveEncashmentDiv();">
						<c:if test="${leaveType.leaveEncashment == 'Y'}">
							<input type="checkbox" checked="true" name="leaveEncashment" id="leaveEncashment" 
							value="Y">Leave En-cashment
								   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Leave Encashable will be calculated for this leave type based on user balance leaves.</span>
									</span>
									</div>
						</c:if>
					     <c:if test="${leaveType.leaveEncashment == 'N'}">
						      <input type="checkbox" name="leaveEncashment" id="leaveEncashment"
							   value="Y">Leave En-cashment
							   	   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Leave Encashable will be calculated for this leave type based on user balance leaves.</span>
									</span>
									</div>
	                             </c:if>
	                             <c:if test="${empty leaveType.leaveEncashment}">
						      <input type="checkbox" name="leaveEncashment" id="leaveEncashment"
						   		value="Y">Leave En-cashment
						   			   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Leave Encashable will be calculated for this leave type based on user balance leaves.</span>
									</span>
									</div>
                           </c:if>
					</span>
				
				<div class="col-md-8" id="leaveEncashmentDiv" style="display:none">
						<div class="row">
							<div class="col-md-6">
							
								<label class="col-md-6 noPad">Min.Days<span
									class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6 paddingRight" id=leaveEncashMinDaysDiv>
									<input type="text" class="form-control" name="leaveEncashMinDays" 
										id="leaveEncashMinDays" value="${leaveType.leaveEncashMinDays}"
										field-name="Min Days" data-validation-optional="true">
								</div>
							</div>
							<div class="col-md-6">
							
								<label class="col-md-6 noPad">Max. Days<span
									class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6" id=leaveEncashMaxDaysDiv>
									<input type="text" class="form-control" name="leaveEncashMaxDays"
										id="leaveEncashMaxDays" value="${leaveType.leaveEncashMaxDays}"
										field-name="Max Days" data-validation-optional="true">
								</div>
					 </div>
				 </div>
			 </div>
			</div>
				<div class="form-group">
					<span class="checkBox col-md-4">
						<div class="checkBox" onClick="showPriorDaysDiv();">
						<c:if test="${leaveType.priorLeaveApplying == 'Y'}">
							<input type="checkbox" checked="true" name="priorLeaveApplying"
								id="priorLeaveApplying" value="Y">Prior Leave Intimation
								   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">User need to plan & apply for this leave type in prior in days.</span>
									</span>
									</div>
                        </c:if>
						<c:if test="${leaveType.priorLeaveApplying == 'N'}">
								<input type="checkbox" name="priorLeaveApplying" id="priorLeaveApplying"
									value="Y">Prior Leave Intimation
									   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">User need to plan & apply for this leave type in prior in days.</span>
									</span>
									</div>
                        </c:if>
                        <c:if test="${empty leaveType.priorLeaveApplying}">
								<input type="checkbox" name="priorLeaveApplying" id="priorLeaveApplying"
									value="Y">Prior Leave Intimation
									   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">User need to plan & apply for this leave type in prior in days.</span>
									</span>
									</div>
                        </c:if>
					</div>
					</span>
					<div class="col-md-4" id="priorDaysdiv" style="display:none">
								<label class="col-md-6 noPad">Prior Days<span
									class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6 paddingRight" id=priorLeaveDaysDiv>
									<input type="text" class="form-control" name="priorDays"
										id="priorDays" value="${leaveType.priorDays}"
										field-name="Prior Days" data-validation-optional="true">
								</div>
					</div>
				</div>
				<div class="form-group">	
				
					<span class="checkBox col-md-4">
						<div class="checkBox" onClick="showLeaveDurationDiv();">
						<c:if test="${leaveType.leaveDuration == 'Y'}">
							<input type="checkbox" checked="true" name="leaveDuration"
								id="leaveDuration" value="Y">Leave Duration
								 <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext"> Minimum no of days that can be applied for this leave type.Maximum number of days can be applied for this leave type.</span>
									</span>
									</div>
                           </c:if>
						<c:if test="${leaveType.leaveDuration == 'N'}">
							<input type="checkbox" name="leaveDuration" id="leaveDuration"
								value="Y">Leave Duration 
								 <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext"> Minimum no of days that can be applied for this leave type.Maximum number of days can be applied for this leave type.</span>
									</span>
									</div>
                           </c:if>
                           <c:if test="${empty leaveType.leaveDuration}">
							<input type="checkbox" name="leaveDuration" id="leaveDuration"
								value="Y">Leave Duration 
								 <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext"> Minimum no of days that can be applied for this leave type.Maximum number of days can be applied for this leave type.</span>
									</span>
									</div>
                           </c:if>
					  	</div>
					</span>
					
					<div class="col-md-8" id="minMaxDaysdiv" style="display:none">
						<div class="row">
							<div class="col-md-6">
							
								<label class="col-md-6 noPad">Min.Days<span
									class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6 paddingRight" id=minimumDaysDiv>
									<input type="text" class="form-control" name="minDays" onblur="validateDuration();"
										id="minDays" value="${leaveType.minDays}"
										field-name="Min Days" data-validation-optional="false">
								</div>
							</div>
							<div class="col-md-6">
							
								<label class="col-md-6 noPad">Max. Days<span
									class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6" id=maximumDaysDiv>
									<input type="text" class="form-control" name="maxDays" onblur="validateDuration();"
										id="maxDays" value="${leaveType.maxDays}"
										field-name="Max Days" data-validation-optional="false">
								</div>
					 </div>
					   <div class="row pull-right" id="expId">
                  <input type="hidden" name="expValidation" id="expValidation">
              </div> 
				 </div>
			 </div>
		</div>
		
		              <div class="form-group">
						<div class="col-md-12">
						<div class="row clearfix">
		                        <span class="checkBox col-md-4">
								<c:if test="${leaveType.accuralPolicy == 'Y'}">
									<input type="checkbox" checked="true" name="accuralPolicy" id="accPolicy" 
									value="Y"> Accrual Policy
									   <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">No of balance leaves should add in accrual basis.</span>
									</span>
									</div>
								</c:if>
								     <c:if test="${leaveType.accuralPolicy == 'N'}">
								      <input type="checkbox" name="accuralPolicy" id="accPolicy" value="Y"> Accrual Policy 
								         <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">No of balance leaves should add in accrual basis.</span>
									</span>
									</div>
                                </c:if>
                                <c:if test="${empty leaveType.accuralPolicy}">
								      <input type="checkbox" name="accuralPolicy" id="accPolicy"
									   value="Y"> Accrual Policy
									      <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">No of balance leaves should add in accrual basis.</span>
									</span>
									</div> 
                                </c:if>
<!-- 												<input type="checkbox" checked="true" name="reasonRequired" value="Y"> Reason Required -->
								</span>
							</div>
						</div>
					</div>
								
					
					
										
						<div class="clearfix"></div>
						<div class="form-group mt10">
							<div class="col-md-12 checkBox" onClick="leavePolicy();">
								<c:if test="${!empty leaveType.leavePolicy}">
									<input type="checkbox" checked="true" name="leavePolicy" id="leavePolicy" value="Y">Leave Policy
	                           </c:if>
								<c:if test="${empty leaveType.leavePolicy}">
									<input type="checkbox" name="leavePolicy" id="leavePolicy" value="N">Leave Policy
									    <div class="tooltip">
									<span class="positionRelative"><i class='fa fa-info-circle text-primary'></i>
									<span class="tooltiptext">Provides the leave policy for a particluar leave type.</span>
									</span>
									</div>

									

	              </c:if>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="clearfix req-dtls" id="leavepolicy-div"
							style="display: none;">
							<h3>Leave Policy</h3>
							<div class="form-group">
								<label class="col-md-4">Policy Name<span
									class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6" id=policyNameDiv>
									<input type="text" class="form-control" name="policyName"
										id="policyName" value="${leaveType.policyName}"
										field-name="Policy Name" data-validation-optional="true">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4"></label>
								<div class="col-md-6 checkBox">
									<c:if test="${!empty leaveType.visibilityToAllUsers && leaveType.visibilityToAllUsers == 'Y'}">
										<input type="checkbox" checked="true"
											name="visibilityToAllUsers" value="Y" id="visibilityToAllUsers">Visibility
										All Users
									</c:if>
									<c:if test="${empty leaveType.visibilityToAllUsers || leaveType.visibilityToAllUsers == 'N'}">
										<input type="checkbox" name="visibilityToAllUsers"  id="visibilityToAllUsers" value="Y">Visibility
										All Users
									</c:if>
								</div>
							</div>
							
					<div class="row">		
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12  txtEditor_p">
						<h5>Description<span class="text-danger">&nbsp;*</span></h5>
						<textarea id="txtEditor" name="leavePolicyDescription"   field-name="Description" class="form-control newContrtextarea" data-validation="required validate_length length2-5000" data-validation-optional="true"></textarea>
					</div>
</div>
						</div>

					</div>
				</div>
			</div>
			<div class="form-footer clearfix mt">
				<div class="col-lg-12">
					<div class="pull-right">
						<input type="button" id="saveLeaveType" value="Save"
							onclick="submitLeaveType();" class="btn btn-primary"> 
							<input type="button" value="Cancel" onclick="redirect();" class="btn btn-default ml10">
					</div>
				</div>
			</div>
		</form:form>
		<!-- Content Area Ends-->
	</div>
</section>

<script>

function validateDuration(){
	 var maxDays = ($('#maxDays').val());
	    var minDays = ($('#minDays').val());
	    var days=($('#noOfDays').val());
	  if((parseFloat(maxDays)>parseFloat(days))||(parseFloat(maxDays) <= parseFloat(minDays))){
	        $('#expId').find('p.jquery_form_error_message').remove(); 
			$('input#expValidation').attr('class','error signin_fild');
			$("input#expValidation").after("<p class='jquery_form_error_message'>Max Days should be greater than Min Days and also max days should be less than no Of Days Per Period  </p>");
			$('input#expValidation').attr('record-exist','yes');
			$('input#expValidation').attr('record-exist-errorMsg','Max Days should be greater than Min Days');
	    }  else{
	    	$('#expId').find('p.jquery_form_error_message').remove(); 
	    	$('input#expValidation').removeAttr( "class record-exist record-exist-errormsg");
			$('#expId').find('p.jquery_form_error_message').remove();
	    }

}

function showLeaveEncashmentDiv(){
	var ckbox = $('#leaveEncashment');
	if (ckbox.is(':checked')) {
		$("#leaveEncashmentDiv").show();
		$('#leaveEncashMinDays').removeAttr('data-validation-optional');
		$('#leaveEncashMinDays').attr('data-validation', "required validate_length  length1-3 validate_float");
		$('#leaveEncashMaxDays').removeAttr('data-validation-optional');
		$('#leaveEncashMaxDays').attr('data-validation', "required validate_length  length1-3 validate_float");
		$('#leaveEncashMinDays').attr('data-validation', "validate_float required validate_length length1-3");
		$('#leaveEncashMaxDays').attr('data-validation', "validate_float required validate_length length1-3");
		$('#leaveEncashMinDaysDiv').find('p.jquery_form_error_message').remove();
		$('#leaveEncashMaxDaysDiv').find('p.jquery_form_error_message').remove();
	}else{
		$("#leaveEncashmentDiv").hide();
		$('#leaveEncashMinDaysDiv').find('p.jquery_form_error_message').remove();
		$('#leaveEncashMaxDaysDiv').find('p.jquery_form_error_message').remove();
		$('#leaveEncashMinDays').removeAttr('data-validation');
		$('#leaveEncashMinDays').attr('data-validation-optional', "true");
		$('#leaveEncashMaxDays').removeAttr('data-validation');
		$('#leaveEncashMaxDays').attr('data-validation-optional', "true");
		$("#leaveEncashMinDays").val("");
		$("#leaveEncashMaxDays").val("");
	}
}

	function validateName() {
	
		var locationId = '${SourceLeadWeb_orgLocationId}';
		var id = $("#leaveTypeId").val();
		var name = $("#leaveTypeName").val();
		if (name != null && name != '' && name != 'undefined') {

			$.get(sourceleadUrl + "/validateName?&orgLocationId="+ locationId + "&from=LeaveType&name="+name+"&id="+id,
							function(data) {
								if (data == true) {
									$('#leaveTypeDiv').find(
											'p.jquery_form_error_message')
											.remove();
									$("#saveLeaveType").removeAttr("disabled");
									$("#saveLeaveType").attr("disabled","disabled");
									$("input#leaveTypeName")
											.after(
													"<p class='jquery_form_error_message'>"
															+ name
															+ " is already existed as Leave Type</span>");
								} else {
									$("#saveLeaveType").removeAttr("disabled");
									if(!(name.length > 250)){
										$('#leaveTypeDiv').find('p.jquery_form_error_message').remove();
									}
								}
							});
		}
	}
</script>
<script type="text/javascript">
	function LeaveCheckValidationAssociate(){
		
		 if($('#fullDayId').is(":checked") || $('#halfDayId').is(":checked")){
				$('#leaveValue').removeAttr('data-validation');
		  }else{
			  $('#leaveValue').attr('data-validation',"required");
		  }
		
	} 
</script>
<script type="text/javascript">
	function submitLeaveType() {
		
		LeaveCheckValidationAssociate();
		
		
		validateName();
			var ee = $("#txtEditor").val($('.Editor-editor').html()); 
			var locationId = '${SourceLeadWeb_orgLocationId}';
			var loginUserId = '${SourceLeadWeb_user.id}';
			if ($('#addLeaveType_form').validate(false, validationSettings)) {
				var formData = $($('#addLeaveType_form')[0]).serialize();
				$("#loading")
						.attr(
								'style',
								'position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				$.post(sourceleadUrl+"/saveNewLeaveType?&orgLocationId="+locationId+"&loginUserId="+loginUserId,formData,function(result) {
					if (result && result.response === "INVALID_DATA") {
		    		    showError(result.responseMessage);
		    		} else if (result.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (result.response === "SUCCESS") {
		    			location.replace(sourceleadUrl + "/viewLeaveTypes");
		    		} else if (result.indexOf('Sign In') !== -1) {
		    			location.replace(sourceleadUrl);
		    		} else {
		    		    showError("Operation failed, please try again");
		    		}
					 $("#loading").hide();
					 $('#saveLeaveType').attr('disabled','disabled');
				});

				return true;
			}
	}
	$('body').on('blur', '#addLeaveType_form', function() {
		$("#addLeaveType_form").showHelpOnFocus();
		$("#addLeaveType_form").validateOnBlur(false, validationSettings);
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

<script>
	function redirect() {
		window.location.href = sourceleadUrl + "/viewLeaveTypes";
	}
</script>
<script>
function leavePolicy(){
	  var flag = document.getElementById("leavePolicy").checked;
	 if(flag == true){
		 $("#leavePolicy").val('Y'); 
		 $("#leavepolicy-div").show();
			$('#policyName').removeAttr('data-validation-optional');
			$('#policyName').attr('data-validation', "required validate_length length1-250");
			$('#txtEditor').removeAttr('data-validation-optional');
			$('#txtEditor').attr('data-validation', "required required validate_length length1-5000");
			$('#policyNameDiv').find('p.jquery_form_error_message').remove();
			$('#leavePolicyDescDiv').find('p.jquery_form_error_message').remove();
			
	 }else{
		 $('.Editor-editor').text('');
		 $("#leavePolicy").val('N'); 
		 $("#leavepolicy-div").hide();
			$('#policyNameDiv').find('p.jquery_form_error_message').remove();
			$('#leavePolicyDescDiv').find('p.jquery_form_error_message').remove();
			$('#policyName').removeAttr('data-validation');
			$('#policyName').attr('data-validation-optional', "true");
			$('#txtEditor').removeAttr('data-validation');
			$('#txtEditor').attr('data-validation-optional', "true");
			$('#policyName').val('');
			$("#visibilityToAllUsers").prop("checked",false);
			
	 } 
	 
	 
}
</script>
<script>
function leavepolicyDescription(){
	var leaveTypeId = '${leaveType.id}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/leavepolicyDescription?${_csrf.parameterName}=${_csrf.token}&leaveTypeId="+leaveTypeId, 	   
		    success: function(result) {
		    	$('#txtEditor').html(result.policyDescription);
		    	$('.Editor-editor').html(result.policyDescription);
		    }
		    	 
     });
}



</script>
<script>
	function showPriorDaysDiv() {
			var ckbox = $('#priorLeaveApplying');
			if (ckbox.is(':checked')) {
				$("#priorDaysdiv").show();
				$('#priorDays').removeAttr('data-validation-optional');
				$('#priorDays').attr('data-validation', "required validate_length length1-250 validate_int");
				$('#priorDays').attr('data-validation', "validate_int required validate_length length1-3");
				$('#priorLeaveDaysDiv').find('p.jquery_form_error_message').remove();
			} else {
				$("#priorDaysdiv").hide();
				$('#priorLeaveDaysDiv').find('p.jquery_form_error_message').remove();
				$('#priorDays').removeAttr('data-validation');
				$('#priorDays').attr('data-validation-optional', "true");
				$("#priorDays").val("");
			} 
	}
	
	function showLeaveDurationDiv(){
		var ckbox = $('#leaveDuration');
		if (ckbox.is(':checked')) {
			$("#minMaxDaysdiv").show();
			$('#minDays').removeAttr('data-validation-optional');

			$('#minDays').attr('data-validation', "required validate_length  length1-3 validate_float");
			$('#maxDays').removeAttr('data-validation-optional');
			$('#maxDays').attr('data-validation', "required validate_length  length1-3 validate_float");
			$('#minDays').attr('data-validation', "validate_float required validate_length length1-3");
			$('#maxDays').attr('data-validation', "validate_float required validate_length length1-3");
			$('#minimumDaysDiv').find('p.jquery_form_error_message').remove();
			$('#maximumDaysDiv').find('p.jquery_form_error_message').remove();
			
		} else {
			$("#minMaxDaysdiv").hide();
			$('#minimumDaysDiv').find('p.jquery_form_error_message').remove();
			$('#maximumDaysDiv').find('p.jquery_form_error_message').remove();
			$('#minDays').removeAttr('data-validation');
			$('#minDays').attr('data-validation-optional', "true");
			$('#maxDays').removeAttr('data-validation');
			$('#maxDays').attr('data-validation-optional', "true");
			$("#minDays").val("");
			$("#maxDays").val("");
		} 
		
	}

	//Select All Users
	function yesUsers() {
		$("#department").val('').trigger("chosen:updated");
		$("#roles").val('').trigger("chosen:updated");
		$("#leveEmpType").val('').trigger("chosen:updated");
		$('#departmentDiv').find('p.jquery_form_error_message').remove();
		$('#rolesDiv').find('p.jquery_form_error_message').remove();
		$('#department').removeAttr('data-validation');
		$('#department').attr('data-validation-optional', "true");
		$('#roles').removeAttr('data-validation');
		$('#roles').attr('data-validation-optional', "true");
		$("#noUsersDiv").hide();
		$("#department").val();
		$("#roles").val();
		$("#leveEmpType").val();
		document.getElementById("applicableId").style.display="block";
		$("#saveLeaveType").attr('disabled',false);
		
	}
	function noUsers() {
		document.getElementById("applicableId").style.display="block";
		var department = $('#department').val();
		
		 	if((department == "" || department == null) && $('#roles').val() == null && $('#leveEmpType').val() == null){
				$("#saveLeaveType").attr('disabled','disabled');
		 	}else{
				document.getElementById("applicableId").style.display="none";
		 	}
		 	$("#noUsersDiv").show();
	}
	 $("#noUsers").click(function() {
		 document.getElementById("applicableId").style.display="block";
		 var department = $('#department').val();
		 	if((department == "" || department == null)  && $('#roles').val() == null && $('#leveEmpType').val() == null){
				$("#saveLeaveType").attr('disabled','disabled');
		 	}else{
				document.getElementById("applicableId").style.display="none";
		 	}
		 	$("#noUsersDiv").show();
	 });
	 
	
// 	${leaveType.leavesCarryforward == 'Y'}
	/* $("#roles").chosen().change({
		("#roles").attr("disabled", false);
	  }); */
	/* function roles(){
		var role = $('#roles').val();
		if(roles != null && roles != "" && roles != "undefined"){
			alert("hhhhh")
			$("#roles").attr("disabled", false);
		}
	} */
	//Select Leave Carry Forward Users
	function carryForwardNoUsers(){
		$('#noOfdaysToCarryforward').attr('data-validation-optional', "true");
		$('#errorNoOfdaysToCarryforward').find('p.jquery_form_error_message').remove();
		$("#noOfDaysDiv").hide();
// 		$("#noOfdaysToCarryforward").val();
	}
	
	function carryForwardYesUsers(){
		$('#noOfdaysToCarryforward').attr('data-validation-optional', "true");
		$('#noOfdaysToCarryforward').attr('data-validation', "validate_int");
		$("#noOfDaysDiv").show();
// 		$("#noOfdaysToCarryforward").val('');
	}
	
	function validateNoDays(){
		var val = $('#noOfdaysToCarryforward').val();
		if(val == ''){
			$('#noOfdaysToCarryforward').removeAttr('data-validation', "validate_int");
			$('#errorNoOfdaysToCarryforward').find('p.jquery_form_error_message').remove();
		}else{
			$('#noOfdaysToCarryforward').attr('data-validation', "validate_int");
		}
		
	}
	//Select All Employees And Experienced
	function hideExperienced() {
		
		$('#newJoineeDiv').find('p.jquery_form_error_message').remove();
		$('#experiencedDiv').find('p.jquery_form_error_message').remove();
		$('#newJoinee').removeAttr('data-validation');
		$('#newJoinee').attr('data-validation-optional', "true");
		$('#experiencedType').removeAttr('data-validation');
		$('#experiencedType').attr('data-validation-optional', "true");
		$('#experiencedDuration').removeAttr('data-validation');
		$('#experiencedDuration').attr('data-validation-optional', "true");
		$('#experiencedApplicableDays').removeAttr('data-validation');
		$('#experiencedApplicableDays').attr('data-validation-optional', "true");
		$("#experiencedGroup").hide();
// 		$("#noOfDaysDiv").show();
		$('#noOfDays').removeAttr('data-validation-optional');
		$('#noOfDays').attr('data-validation', "required validate_int");
		$("#experiencedDuration").val("");
		$("#experiencedApplicableDays").val("");
		$("#newJoinee").val("");
		
		//Writte By Laxman
		$("#noOfDaysAnnuallyDiv").show();
		$("#calculatedNoOfDaysDiv").show();
		$("#experiencedPeriod").val("");
		$("#fresherPeriod").val("");
	}
	function showExperienced() {
		$('#newJoinee').removeAttr('data-validation-optional');
		$('#newJoinee').attr('data-validation', "required validate_int validate_length length1-3");
		$('#experiencedType').removeAttr('data-validation-optional');
		$('#experiencedType').attr('data-validation', "required");
		$('#experiencedDuration').removeAttr('data-validation-optional');
		$('#experiencedDuration').attr('data-validation', "required validate_int validate_length length1-2");
		$('#experiencedApplicableDays').removeAttr('data-validation-optional');
		$('#experiencedApplicableDays').attr('data-validation', "required validate_int validate_length length1-3");
		$("#experiencedGroup").show();
		$("#noOfDays").val("");
		$('#noOfDays').removeAttr('data-validation');
		$('#noOfDays').attr('data-validation-optional', "true");
		
		//Writte By Laxman
		$("#noOfDaysAnnuallyDiv").hide();
		$("#calculatedNoOfDaysDiv").hide();
		$('#noOfDaysAnnuallyDiv').find('p.jquery_form_error_message').remove();
	}
</script>
<script>
	//Color Picker
	/* $(function() {
		$('#cp').colorpicker();
	}); */
	function colorPicker(){
		var theInput = document.getElementById("favcolor");
	    var theColor = theInput.value;
	    
	    theInput.addEventListener("input", function() {
	    	document.getElementById("colorCode").value = theInput.value;
	    }, false);
	}
</script>

<script>
$('.chosen-select').chosen().on('change', function() {
	var roles = $("#roles").val();
	var leveEmpType = $("#leveEmpType").val();
	var department = $("#department").val();
	var gradeListId = $("#gradeListId").val();
	var roleListId = $("#roleListId").val();
	if(roles != null && roles !="" && roles !="undefined"){
		$("#saveLeaveType").removeAttr('disabled');
		document.getElementById("applicableId").style.display="none";
	}else if(leveEmpType != null && leveEmpType !="" && leveEmpType !="undefined"){
		$("#saveLeaveType").removeAttr('disabled' );
		document.getElementById("applicableId").style.display="none";
	}else if(department != null && department !="" && department !="undefined"){
		$("#saveLeaveType").removeAttr('disabled');
		document.getElementById("applicableId").style.display="none";
	}else{
		$("#department").val('').trigger("chosen:updated");
		$("#roles").val('').trigger("chosen:updated");
		$("#leveEmpType").val('').trigger("chosen:updated");
		$("#saveLeaveType").prop('disabled',true);
		document.getElementById("applicableId").style.display="block";
		
	}
	if(gradeListId != "" || roleListId != ""){
		$("#saveLeaveType").removeAttr('disabled');
	}
	 if(roles == null){
		$("#roles").val('').trigger("chosen:updated");
	}else if(department == null){
		$("#department").val('').trigger("chosen:updated");
	} 
	
	
})



</script>

<jsp:include page="../masterFooter.jsp" />
<script>
$(window).load(function() {
	var leavesCarryforward = '${leaveType.leavesCarryforward}';
	if(leavesCarryforward == "N"){
		carryForwardNoUsers();
	}
});
</script>
<script>
	$(document).ready(function() {
		$("#txtEditor").Editor();
		//$("#txtEditor").find(".Editor-editor").data-validation('validate_length length2-5000');
		//$("#txtEditor").find(".Editor-editor").data-validation-optional('rule-required',true);
	});
 </script>
 
 <script>
 $("#autoApproveChekBox").click(function() {
	 $("#autoApproveDiv").toggle();
 });
function getGradeList() {
	 $("#chosenRole").hide();
	 $("#roleListId").val("");
	 $('#roleListId').trigger("chosen:updated");
	 $("#chosenGrade").show();
	 
	 $('#gradeListId').removeAttr('data-validation-optional');
	 $('#gradeListId').attr('data-validation', "required");
	 $('#roleListId').removeAttr('data-validation');
 }
function getRoleList() {
	 $("#chosenGrade").hide();
	 $("#gradeListId").val("");
	 $('#gradeListId').trigger("chosen:updated");
	 $("#chosenRole").show();
	 $('#roleListId').removeAttr('data-validation-optional');
	 $('#roleListId').attr('data-validation', "required");
	 $('#gradeListId').removeAttr('data-validation');
 }
 
function getNoOfDaysPerPeriod(){
	 var period = $("#period").val();
	 var noOfDays = $("#noOfDays").val();
	 var calculatedDays = "";
	 if(period == "Annually"){
		 calculatedDays = noOfDays;
	 }else if(period == "HalfYearly"){
		 calculatedDays = noOfDays / 2 ;
	 }else if(period == "Quarterly"){
		 calculatedDays = noOfDays / 4 ;
	 }else if(period == "Monthly"){
		 calculatedDays = noOfDays / 12 ;
	 }
	 if(period != "Annually"){
		 $("#noOfDaysPerPeriod").val(calculatedDays.toFixed(2));
	 }else{
		 $("#noOfDaysPerPeriod").val(calculatedDays);
	 }
	 getNoOfDaysPerPeriodFresher();
	 getNoOfDaysPerPeriodExperienced();
 }
 
function getNoOfDaysPerPeriodFresher(){
	 var period = $("#period").val();
	 var noOfDays = $("#newJoinee").val();
	 var calculatedDays = "";
	 if(period == "Annually"){
		 calculatedDays = noOfDays;
	 }else if(period == "HalfYearly"){
		 calculatedDays = noOfDays / 2 ;
	 }else if(period == "Quarterly"){
		 calculatedDays = noOfDays / 4 ;
	 }else if(period == "Monthly"){
		 calculatedDays = noOfDays / 12 ;
	 }
	 if(period != "Annually"){
		 $("#fresherPeriod").val(calculatedDays.toFixed(2));
	 }else{
		 $("#fresherPeriod").val(calculatedDays);
	 }
	
}
function getNoOfDaysPerPeriodExperienced(){
	 var period = $("#period").val();
	 var noOfDays = $("#experiencedApplicableDays").val();
	 var calculatedDays = "";
	 if(period == "Annually"){
		 calculatedDays = noOfDays;
	 }else if(period == "HalfYearly"){
		 calculatedDays = noOfDays / 2 ;
	 }else if(period == "Quarterly"){
		 calculatedDays = noOfDays / 4 ;
	 }else if(period == "Monthly"){
		 calculatedDays = noOfDays / 12 ;
	 }
	 if(period != "Annually"){
		 $("#experiencedPeriod").val(calculatedDays.toFixed(2));
	 }else{
		 $("#experiencedPeriod").val(calculatedDays);
	 }
}
 </script>
 <script>
 function validateAutoApprove(){
	 var ckbox = $('#autoApproveChekBox');
		if (ckbox.is(':checked')) {
			$("#grade").prop('checked', true);
			getGradeList();
		}else{
			$("#grade").removeAttr("checked");
			$('#gradeListId').removeAttr('data-validation');
			$('#roleListId').removeAttr('data-validation');
		}
 }
 
 //Tooltip script
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});
 </script>
