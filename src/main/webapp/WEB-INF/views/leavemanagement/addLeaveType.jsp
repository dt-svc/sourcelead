<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link href="/resources/css/register-style.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/register-style.css?staticTime=${staticTime}" rel="stylesheet">
<script src="/resources/js/summernote.js?staticTime=${staticTime}"></script>
<link href="/resources/css/summernote.css?staticTime=${staticTime}" rel="stylesheet" />
<script src="/resources/js/editor.js?staticTime=${staticTime}"></script>
<link href="/resources/css/editor.css?staticTime=${staticTime}" type="text/css" rel="stylesheet"/>



		<link href="/resources/css/bootstrap-colorpicker.min.css" rel="stylesheet">
		<script src="/resources/js/bootstrap-colorpicker.min.js"></script>
		   
	
	
<script>
	$(document).ready(function(){
		var policy = '${leaveType.leavePolicy}';
		if (policy == 'Y') {
			leavePolicy();
		}
		var applicable = '${leaveType.applicableToUsers}';
		if (applicable == 'N') {
			noUsers();
		}
		var leaveEffective = '${leaveType.leaveEffective}';
		if (leaveEffective == 'AllEmployees') {
			hideExperienced();
		/* 	$('#newJoineeDiv').find('p.jquery_form_error_message').remove();
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
			$("#noOfDaysDiv").show();
			$('#noOfDays').removeAttr('data-validation-optional');
			$('#noOfDays').attr('data-validation', "required");
 */		}else{
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
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='${appPath}/leaveConfiguration'/>"> Leave Configuration</a>
			&raquo;<a href="<c:url value='${appPath}/viewLeaveTypes'/>">Leave type List</a>
			&raquo; Add Leave Type
		</div>
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
								<input type="hidden" id="leaveTypeId" name="leaveTypeId" value="${leaveType.id}" />
								 <input type="text" class="form-control" id="leaveTypeName" name="name" value="${leaveType.name}" onblur="validateName();" field-name="Name"
									data-validation="required validate_Space validate_length length1-250">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4">Duration<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-6 dropdown-select">
								<select class="form-control" name="duration"
									field-name="Duration" data-validation="required">
									<option ${leaveType.duration  == 'Full Day' ? 'selected' : ''}>Full Day</option>
									<option ${leaveType.duration  == 'Half Day' ? 'selected' : ''}>Half Day</option>
								</select>
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

							<label class="col-md-4">Applicable to All Users<i class='fa fa-info-circle text-muted paddingLeft' title='Leave policies will be visible to users while applying the respective leave type'></i></label>
							<div class="col-md-6">

							

								<div class="clearfix mb">
									<c:if test="${!empty leaveType.applicableToUsers}">
										<c:if test="${leaveType.applicableToUsers == 'Y'}">
											<label class="paddingLeft paddingRight" onClick="yesUsers();">
												<input type="radio" checked="true" value="Y"
												name="applicableToUsers"> <span class="paddingLeft">Yes</span>
											</label>
											<label class="paddingLeft" onClick="noUsers();"> <input
												type="radio" id="noUsers" value="N" name="applicableToUsers">
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

						<div class="clearfix" id="noUsersDiv" style="display: none;">
							<div class="clearfix"></div>
							<div class="form-group" id="departmentDiv">
								<label class="col-md-4">Select Department<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6 dropdown-select">
									<select class="chosen-select form-control" name="department" onchange="removeErrMsg();"
										id="department" field-name="Department"
										data-validation-optional="true">
										<option value="">Select Department</option>
										<c:forEach var="report" items="${departmentList}">
											<option value="${report.id}"
												${report.id  == departmentId ? 'selected' : ''}>${report.departmentName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group" id="rolesDiv">
								<label class="col-md-4">Select Roles<span
								class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6 dropdown-select" id="roleErrMsg">
									<select multiple data-placeholder="Select Roles"
										class="chosen-select form-control" id="roles" name="roles"
										field-name="Roles" data-validation-optional="true" onchange="removeRoleErrMsg();">
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

						</div>
						<div class="form-group">

							<label class="col-md-4">Leaves Carryforward <i class='fa fa-info-circle text-muted' title='Describes whethers leaves are carryforward to next year'></i></label>
							<div class="col-md-6">	

								<div class="clearfix mb">
									<c:if test="${!empty leaveType.leavesCarryforward}">
										<c:if test="${leaveType.leavesCarryforward == 'Y'}">
											<label class="paddingLeft paddingRight"> <input
												type="radio" checked value="Y" name="carryforward">
												<span class="paddingLeft">Yes</span></label>
											<label class="paddingLeft"> <input type="radio"
												value="N" name="carryforward"> <span
												class="paddingLeft">No</span></label>
										</c:if>
										<c:if test="${leaveType.leavesCarryforward == 'N'}">
											<label class="paddingLeft paddingRight"> <input
												type="radio" value="Y" name="carryforward"> <span
												class="paddingLeft">Yes</span></label>
											<label class="paddingLeft"> <input type="radio"
												checked value="N" name="carryforward"> <span
												class="paddingLeft">No</span></label>
										</c:if>
									</c:if>
									<c:if test="${empty leaveType.leavesCarryforward}">
										<label class="paddingLeft paddingRight"> <input
											type="radio" checked value="Y" name="carryforward"> <span
											class="paddingLeft">Yes</span></label>
										<label class="paddingLeft"> <input type="radio"
											 value="N" name="carryforward"> <span
											class="paddingLeft">No</span></label>
									</c:if>
								</div>
							</div>

						</div>
						<div class="form-group">
							<label class="col-md-4">Period<span
								class="text-danger paddingLeft">*</span></label>
							<div class="col-md-6 dropdown-select">
								<select class="form-control" name="period" onchange="getPeriod();">
								<option ${leaveType.period == 'Annually' ? 'selected' : ''}>Annually</option>
								<option ${leaveType.period == 'HalfYearly' ? 'selected' : ''}>HalfYearly</option>
								<option ${leaveType.period == 'Quarterly' ? 'selected' : ''}>Quarterly</option>
								<option ${leaveType.period == 'Monthly' ? 'selected' : ''}>Monthly</option>
								<option ${leaveType.period == 'SemiMonthly' ? 'selected' : ''}>SemiMonthly</option>
								<option ${leaveType.period == 'BiWeekly' ? 'selected' : ''}>BiWeekly</option>
								</select>
							</div>
						</div>

						<div class="form-group">
						 <label class="col-md-4">Leave Effective<span
								class="text-danger paddingLeft">*</span></label>
							<div class="col-md-6">
							
							
							<c:if test="${!empty leaveType.leaveEffective}">
										<c:if test="${leaveType.leaveEffective == 'AllEmployees'}">
											<label class="paddingLeft paddingRight" onClick="hideExperienced();">
												<input type="radio" checked="true" value="AllEmployees" name="leaveEffective"> <span class="paddingLeft">All Employees</span>
											</label>
											<label class="paddingLeft" onClick="showExperienced();">
											 <input type="radio" value="Experienced" name="leaveEffective">
												<span class="paddingLeft">Experienced</span></label>
										</c:if>

										<c:if test="${leaveType.leaveEffective == 'Experienced'}">
											<label class="paddingLeft paddingRight" onClick="hideExperienced();">
												<input type="radio" value="AllEmployees" name="leaveEffective"> <span class="paddingLeft">All Employees</span>
											</label>
											<label class="paddingLeft" onClick="showExperienced();">
											 <input type="radio" value="Experienced" checked="true" name="leaveEffective">
												<span class="paddingLeft">Experienced</span></label>
										</c:if>
									</c:if>

									<c:if test="${empty leaveType.leaveEffective}">
										<label class="paddingLeft paddingRight" onClick="hideExperienced();">
												<input type="radio" checked="true" value="AllEmployees" name="leaveEffective"> <span class="paddingLeft">All Employees</span>
											</label>
											<label class="paddingLeft" onClick="showExperienced();">
											 <input type="radio" value="Experienced" name="leaveEffective">
												<span class="paddingLeft">Experienced</span></label>
									</c:if>
							</div>
						</div>
						
						<div class="form-group" id="noOfDaysDiv">
							<label class="col-md-4">No Of Days<span class="text-danger paddingLeft" >*</span></label>
							<div class="col-md-3">
								<input type="text" id="noOfDays" name="noOfDays" field-name="No Of Days" class="form-control" value="${leaveType.noOfDays}">
							</div>
						</div>
				
				<!-- experiencedGroup -->
					<div id="experiencedGroup" style="display:none">
						<div class="form-group" id="newJoineeDiv">
							<label class="col-md-4">New Joinee<span class="text-danger paddingLeft" >*</span></label>
							<div class="col-md-3">
								<input type="text" id="newJoinee" name="newJoinee" field-name="New Joinee" class="form-control" value="${leaveType.newJoinee}">
							</div>
						</div>
						
						<div class="form-group" id="experiencedDiv">
							<label class="col-md-4">Experienced<span class="text-danger paddingLeft" >*</span></label>
							<div class="col-md-6">
								<div class="row">
								<div class="col-md-3 padding_right">
									<input type="text" class="form-control" id="experiencedDuration" name="experiencedDuration" field-name="experiencedDuration" value="${leaveType.experiencedDuration}">
								</div>
								<div class="col-md-5 dropdown-select padding_left">
									<select id="experiencedType" name="experiencedType" field-name="experiencedType" >
									<option ${leaveType.experiencedType == 'Year' ? 'selected' : ''}>Year</option>
									<option ${leaveType.experiencedType == 'Month' ? 'selected' : ''}>Month</option>
									<option ${leaveType.experiencedType == 'Days' ? 'selected' : ''}>Days</option>
									</select>
								</div>
								<div class="col-md-4" id="experiencedApplicableDaysDiv">
								<input type="text" class="form-control" id="experiencedApplicableDays" name="experiencedApplicableDays" field-name="experiencedApplicableDays" value="${leaveType.experiencedApplicableDays}">
								</div>
								
							</div>
						</div>
						</div>
					</div>
<!-- experiencedGroup -->

						<div class="form-group">

							<label class="col-md-4">Color Picker<span
								class="text-danger">&nbsp;* </span><i class='fa fa-info-circle text-muted' title='Picked color will be repersent for this leave type throughout application'></i></label>
							<div class="col-md-6">

							
								<div id="cp"
									class="input-group colorpicker-component errorInptgroup">
									<input type="text" name="colorCode" field-name="Color Picker"
										placeholder="Select Color Picker" readonly="readonly"
										value="${leaveType.colorCode}" class="form-control" id="colorCode"
										data-validation="required" > <span class="input-group-addon colPicBox"><i>
										
										</i></span>
								</div>
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
											<div class="col-md-12">
											<div class="row clearfix">
											<span class="checkBox col-md-4">
											<c:if test="${leaveType.reasonRequired == 'Y'}">
												<input type="checkbox" checked="true" name="reasonRequired" id="reasonRequired" 
												value="Y"> Reason Required
											</c:if>
											     <c:if test="${leaveType.reasonRequired == 'N'}">
											      <input type="checkbox" name="reasonRequired" id="reasonRequired"
												   value="Y"> Reason Required 
			                                </c:if>
			                                <c:if test="${empty leaveType.reasonRequired}">
											      <input type="checkbox" name="reasonRequired" id="reasonRequired"
												   value="Y"> Reason Required 
			                                </c:if>
<!-- 												<input type="checkbox" checked="true" name="reasonRequired" value="Y"> Reason Required -->
											</span>
											<span class="checkBox col-md-4 padding_left padding_right"> 
											<c:if test="${leaveType.attachmentRequired == 'Y'}">
												<input type="checkbox" checked="true" name="attachmentRequired" id="attachmentRequired" 
												value="Y"> Attachment Required
											</c:if>
											     <c:if test="${leaveType.attachmentRequired == 'N'}">
											      <input type="checkbox" name="attachmentRequired" id="attachmentRequired"
												   value="Y"> Attachment Required 
			                                </c:if>
			                                <c:if test="${empty leaveType.attachmentRequired}">
											      <input type="checkbox" name="attachmentRequired" id="attachmentRequired"
												   value="Y"> Attachment Required 
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
											</c:if>
											     <c:if test="${leaveType.donateLeave == 'N'}">
											      <input type="checkbox" name="donateLeave" id="donateLeave"
												   value="Y"> Donate Leave 
			                                </c:if>
			                                <c:if test="${empty leaveType.donateLeave}">
											      <input type="checkbox" name="donateLeave" id="donateLeave"
												   value="Y"> Donate Leave 
			                                </c:if>
												</span>
											
											<span class="checkBox col-md-4 padding_left padding_right">
											
											<div class="checkBox" onClick="showPriorDaysDiv();">
										<c:if test="${leaveType.priorLeaveApplying == 'Y'}">
											<input type="checkbox" checked="true" name="priorLeaveApplying"
												id="priorLeaveApplying" value="Y">Prior Leave Applying
			                        </c:if>
										<c:if test="${leaveType.priorLeaveApplying == 'N'}">
											<input type="checkbox" name="priorLeaveApplying" id="priorLeaveApplying"
												value="Y">Prior Leave Applying 
			                        </c:if>
			                        <c:if test="${empty leaveType.priorLeaveApplying}">
											<input type="checkbox" name="priorLeaveApplying" id="priorLeaveApplying"
												value="Y">Prior Leave Applying 
			                        </c:if>
									</div>
											</span>
											
											
											<span class="checkBox col-md-4">
											<div class="checkBox" onClick="showLeaveDurationDiv();">
										<c:if test="${leaveType.leaveDuration == 'Y'}">
											<input type="checkbox" checked="true" name="leaveDuration"
												id="leaveDuration" value="Y">Leave Duration
			                            </c:if>
										<c:if test="${leaveType.leaveDuration == 'N'}">
											<input type="checkbox" name="leaveDuration" id="leaveDuration"
												value="Y">Leave Duration 
			                            </c:if>
			                            <c:if test="${empty leaveType.leaveDuration}">
											<input type="checkbox" name="leaveDuration" id="leaveDuration"
												value="Y">Leave Duration 
			                            </c:if>
									  </div>

									</span>
								</div>
							</div>
						</div>
										
				<div class="form-group">
				
					<div class="col-md-4 paddingRight" id="priorDaysdiv" style="display:none">
					     <div class="form-group">
								<label class="col-md-6 paddingRight text-right mt5">Prior Days<span
									class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6" id=priorLeaveDaysDiv>
									<input type="text" class="form-control" name="priorDays"
										id="priorDays" value="${leaveType.priorDays}"
										field-name="Prior Days" data-validation-optional="true">
								</div>
							</div>
					</div>
					
					<div class="col-md-8 paddingLeft" id="minMaxDaysdiv" style="display:none">
						<div class="row">
							<div class="col-md-6 paddingRight">
							
								<label class="col-md-6  paddingRight text-right mt5">Min.Days<span
									class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6" id=minimumDaysDiv>
									<input type="text" class="form-control" name="minDays"
										id="minDays" value="${leaveType.minDays}"
										field-name="Min Days" data-validation-optional="true">
								</div>
							</div>
							<div class="col-md-6 paddingLeft">
							
							<div class="form-group">
								<label class="col-md-6  paddingRight text-right mt5">Max. Days<span
									class="text-danger">&nbsp;*</span></label>
								<div class="col-md-6" id=maximumDaysDiv>
									<input type="text" class="form-control" name="maxDays"
										id="maxDays" value="${leaveType.maxDays}"
										field-name="Max Days" data-validation-optional="true">
								</div>
						 </div>
					 </div>
				 </div>
			 </div>
						</div>
										
										
						<div class="clearfix"></div>
						<div class="form-group mt10">
							<div class="col-md-12 checkBox" onClick="leavePolicy();">
								<c:if test="${!empty leaveType.leavePolicy}">
									<input type="checkbox" checked="true" name="leavePolicy"
										id="leavePolicy" value="Y">Leave Policy
	              </c:if>
								<c:if test="${empty leaveType.leavePolicy}">
									<input type="checkbox" name="leavePolicy" id="leavePolicy"

										value="Y">Leave Policy <i class='fa fa-info-circle text-muted' title='Leave policy for particluar leave type'></i>

									

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
									<c:if test="${!empty leaveType.visibilityToAllUsers}">
										<input type="checkbox" checked="true"
											name="visibilityToAllUsers" value="Y">Visiblity
										All Users
									</c:if>
									<c:if test="${empty leaveType.visibilityToAllUsers}">
										<input type="checkbox" name="visibilityToAllUsers" value="Y">Visiblity
										All Users
									</c:if>
								</div>
							</div>
							
					<div class="row">		
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12  txtEditor_p">
						<h5>Description</h5>
						<textarea id="txtEditor" name="leavePolicyDescription"   
						value="" field-name="Description" class="form-control newContrtextarea" data-validation="validate_length length2-5000" data-validation-optional="true"></textarea>
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
							onclick="submitLeaveType();" class="btn btn-primary"> <input
							type="button" value="Cancel" onclick="redirect();"
							class="btn btn-default ml10">
					</div>
				</div>
			</div>
		</form:form>
		<!-- Content Area Ends-->
	</div>
</section>

<script>
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
									$("#saveLeaveType").attr("disabled",
											"disabled");
									$("input#leaveTypeName")
											.after(
													"<p class='jquery_form_error_message'>"
															+ name
															+ " is already existed as Leave Type</span>");
								} else {
									$("#saveLeaveType").removeAttr("disabled");
									$('#leaveTypeDiv').find(
											'p.jquery_form_error_message')
											.remove();
								}
							});
		}
	}
</script>
<script type="text/javascript">
	function submitLeaveType() {
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
			$.post(sourceleadUrl + "/saveNewLeaveType?" + tokenParamName + "="
					+ tokenValue + "&orgLocationId=" + locationId
					+ "&loginUserId=" + loginUserId, formData, function(data) {
				if (data == "success") {
					location.replace(sourceleadUrl + "/viewLeaveTypes");
				}
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
	function leavePolicy() {
		var ckbox = $('#leavePolicy');
		if (ckbox.is(':checked')) {
			$("#leavepolicy-div").show();
			$('#policyName').removeAttr('data-validation-optional');
			$('#policyName').attr('data-validation', "required validate_length length1-250");
			$('#txtEditor').removeAttr('data-validation-optional');
			$('#txtEditor').attr('data-validation', "required required validate_length length1-5000");
			$('#policyNameDiv').find('p.jquery_form_error_message').remove();
			$('#leavePolicyDescDiv').find('p.jquery_form_error_message')
					.remove();
		} else {
			$("#leavepolicy-div").hide();
			$('#policyNameDiv').find('p.jquery_form_error_message').remove();
			$('#leavePolicyDescDiv').find('p.jquery_form_error_message')
					.remove();
			$('#policyName').removeAttr('data-validation');
			$('#policyName').attr('data-validation-optional', "true");
			$('#txtEditor').removeAttr('data-validation');
			$('#txtEditor').attr('data-validation-optional', "true");
		} 
		
		var policyDesc = '${leaveType.policyDescription}';
		
		  setTimeout(function () {
			  $("textarea[name=leavePolicyDescription]").html(policyDesc);
			 	$(".Editor-editor").html(policyDesc);
	 			}, 100);
		
	}
	
	function showPriorDaysDiv() {
			var ckbox = $('#priorLeaveApplying');
			if (ckbox.is(':checked')) {
				$("#priorDaysdiv").show();
				$('#priorDays').removeAttr('data-validation-optional');
				$('#priorDays').attr('data-validation', "required validate_length length1-250 validate_int");
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
			$('#minDays').attr('data-validation', "required validate_length length1-250 validate_int");
			$('#maxDays').removeAttr('data-validation-optional');
			$('#maxDays').attr('data-validation', "required validate_length length1-250 validate_int");
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
		$('#departmentDiv').find('p.jquery_form_error_message').remove();
		$('#rolesDiv').find('p.jquery_form_error_message').remove();
		$('#department').removeAttr('data-validation');
		$('#department').attr('data-validation-optional', "true");
		$('#roles').removeAttr('data-validation');
		$('#roles').attr('data-validation-optional', "true");
		$("#noUsersDiv").hide();
	}
	function noUsers() {
		$('#department').removeAttr('data-validation-optional');
		$('#department').attr('data-validation', "required");
		$('#roles').removeAttr('data-validation-optional');
		$('#roles').attr('data-validation', "required");
		$("#noUsersDiv").show();
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
		$("#noOfDaysDiv").show();
		$('#noOfDays').removeAttr('data-validation-optional');
		$('#noOfDays').attr('data-validation', "required validate_int");
		$("#experiencedDuration").val("");
		$("#experiencedApplicableDays").val("");
		$("#newJoinee").val("");
	}
	function showExperienced() {
		$('#newJoinee').removeAttr('data-validation-optional');
		$('#newJoinee').attr('data-validation', "required validate_int");
		$('#experiencedType').removeAttr('data-validation-optional');
		$('#experiencedType').attr('data-validation', "required");
		$('#experiencedDuration').removeAttr('data-validation-optional');
		$('#experiencedDuration').attr('data-validation', "required validate_int");
		$('#experiencedApplicableDays').removeAttr('data-validation-optional');
		$('#experiencedApplicableDays').attr('data-validation', "required validate_int");
		$("#experiencedGroup").show();
		$("#noOfDays").val("");
		$("#noOfDaysDiv").hide();
		$('#noOfDays').removeAttr('data-validation');
		$('#noOfDays').attr('data-validation-optional', "true");
	}
</script>
<script>
	//Color Picker
	$(function() {
		$('#cp').colorpicker();
	});
</script>
<jsp:include page="../masterFooter.jsp" />

<script>
	$(document).ready(function() {
		$("#txtEditor").Editor();
		$("#txtEditor").find(".Editor-editor").data-validation('validate_length length2-5000');
		$("#txtEditor").find(".Editor-editor").data-validation-optional('rule-required',true);
	});
 </script>
