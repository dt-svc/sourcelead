<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="requirementDetails.jsp"></jsp:include>
<jsp:include page="requirementClientDetails.jsp"></jsp:include>
<jsp:include page="requirementInternalDetails.jsp"></jsp:include>

<c:if test="${empty requirement.id}">
	<jsp:include page="requirementAssignedTo.jsp"></jsp:include>
</c:if>

<script src="/resources/js/editor.js?staticTime=${staticTime}"></script>
<link href="/resources/css/editor.css?staticTime=${staticTime}"
	type="text/css" rel="stylesheet" />

<script>
var atsUrl = '${SourceLeadWeb_atsUrl}';
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		$(document).ready(function() {
			//getProjectsList();
			$("#txtEditor").Editor();
			 <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
			  var sectionId = '${section.id}';
			  getCustomDependentField(sectionId);
			 </c:forEach>
		});
    </script>
<script>

</script>
<script>
function validateWorkflowEditable(requirementId){
	if(requirementId){ // dont need to validate for adding new job. Becuase we dont have any requirementid for that before save.
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/validateWorkflowEditable?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId, 	   
        success: function(response) {
           //if false disable  the workflow name for editing.
           if(response == false){
        	   $("#workflowId").attr("disabled", "disabled");
        	   $("#workflowId").trigger("chosen:updated");
           }
        }
     });
	}
}
</script>
<script>
$(document).ready(function() {
	initLocationAuto('ReqLocation','ReqCountry','ReqState','ReqCity','ReqZipCode','','','');
	//addLocationAuto('addlocation','0');
	zipcodeMask('${requirement.country}','ReqZipCode');
    validateWorkflowEditable('${requirement.id}');
});
</script>
<script>
  $(function() { 
		tagIt("skills", 'Press Enter-Key Or Comma(,) To Add New Skill:', 'tagItBykeyForSkills','skills','Select Skills');
		tagIt("education", 'Press Enter-Key Or Comma(,) To Add New Education:', 'tagItBykeyForSkills','skills','Select Education Options');

	});
 </script>
<script type="text/javascript">
		window.onload = function () {
			getNoticePeriodList();
			getJobLocations();
			var jobType = '${requirement.jobType}';
			if(jobType !=null && jobType != "" && jobType != "undefined" && jobType == 'Other'){
				  document.getElementById("jobTypeDiv").style.display="block";
				  $("#jobOtherType").attr("data-validation-optional", "true");
				  $("#jobOtherType").attr("data-validation", "validate_Space validate_length length2-250");
			}else{
				document.getElementById("jobTypeDiv").style.display="none";
				  $("#jobOtherType").attr("data-validation-optional", "true");
				  $("#jobTypeDiv").find('p.jquery_form_error_message').remove();
				  $("#jobOtherType").val('');
			}
			
			  if(jobType == 'C2C' || jobType == 'C2H' || jobType == 'Contract'){
					 
					
					 document.getElementById("projectDurationYearsDiv").style.display="block";
					 document.getElementById("projectDurationMonthsDiv").style.display="block";
					 
					 
				  
				}else{
					document.getElementById("projectDurationYearsDiv").style.display="none";
					document.getElementById("projectDurationMonthsDiv").style.display="none";
					
				}
			
			
			/* removeEditButton(); 
			disableMonths(); */
			
			$(".cd1").click(function(){  
		        $(".col-md-12 clearfix").toggleClass('hidden', 500);
		    });
			
		 };
</script>

<script>
		$(document).ready(function() {
			document.getElementById("requirement_form").reset();
			
			var requirementStatus = '${requirement.requirementSubmissionStatus}';
			if(requirementStatus == "OPEN" ||  requirementStatus == "ONHOLD"){
				$(function() {
				    enable_cb();
				    $("#apprverReq").click(enable_cb);
				    $("#parallelapprovers").click(enable_cb);
				    $("#seqentialApprovers").click(enable_cb);
				    /* $('#sequentialApprover1').click(enable_cb);
					$('#sequentialApprover2').click(enable_cb); */
				});
				function enable_cb() {
					$("#parallelApprover").prop('disabled', true).trigger('chosen:updated');
				    $("#parallelApprover").off('click');
				    
					$('#apprverReq').prop("disabled", !this.checked);
					$('#parallelapprovers').prop("disabled", !this.checked);
					$('#seqentialApprovers').prop("disabled", !this.checked);
					/* $('#sequentialApprover1').prop("disabled", !this.checked); 
					$('#sequentialApprover2').prop("disabled", !this.checked); 
					$('#sequentialApprover1').attr("data-validation-optional","true"); 
					$('#sequentialApprover2').attr("data-validation-optional","true");  */
					$('#seqentialApprovers').attr("data-validation-optional","true");
					$('#parallelApprover').attr("data-validation-optional","true"); 
					
				}
				
			}else{
				$('#apprverReq').removeAttr("disabled");
				$('#parallelapprovers').removeAttr("disabled")
				$('#seqentialApprovers').removeAttr("disabled")
				$('#sequentialApprover1').removeAttr("disabled")
				$('#sequentialApprover2').removeAttr("disabled")
				
			}
		});
</script>

<script>
$(document).ready(function () {
   /*  var selectedUserRole = document.getElementById('SelectedUserRole').value; */
    var approverMode = '${approverMode}';
    var parallelApproverIds = '${parallelApproverIds}';
    var sequentialApprover1 = '${sequentialApprover1}';
    var sequentialApprover2 = '${sequentialApprover2}';
	if(approverMode != null && approverMode != '' && approverMode != 'undefined'){
			/*  $("#apprverReq").prop('checked', true); */
		    if(approverMode == 'Sequential'){
		    		$('#apprverReq').click();
		    	    approvalType('Sequential');     
		    	    var str_array = sequentialApprover1.split(',');
		    	    for (var i = 0; i < str_array.length; i++) {
		    	        str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
		    	    }
		    	    $("#sequentialApprover1").val(str_array).trigger("chosen:updated");
		    	    
		    	    
		    	    var str_array2 = sequentialApprover2.split(',');
		    	    for (var i = 0; i < str_array2.length; i++) {
		    	    	str_array2[i] = str_array2[i].replace(/^\s*/, "").replace(/\s*$/, "");
		    	    }
		    	    /*$("#sequentialApprover2").val(str_array2).trigger("chosen:updated");  */
		    	    
		    		var currentApproverId = document.getElementById("sequentialApprover1").value;
		    		
		    		if(currentApproverId != null && currentApproverId != ''  && currentApproverId != 'undefined'){
		    			 $.ajax({
		    			    	type: "POST",
		    			        url: atsUrl+"/getApproverTwoByUserId?"+tokenParamName+"="+tokenValue+"&currentApproverId="+currentApproverId,  
		    			        success: function(data) {
		    			        	var json = JSON.parse(data);
		    			        	var output = "";
		    			        	output = output + "<option value=''>-Select Approver-</option>"; 
		    			        	for (var i=0; i<json.length; i++)
		    						{
		    				        		var opt = json[i];
		    				        		if(str_array2 == opt.id){
		    				        			output = output + "<option value='"+opt.id+"' selected>"+opt.firstName+" "+opt.lastName+"</option>";
		    				        		}else{
		    				        			output = output + "<option value='"+opt.id+"'>"+opt.firstName+" "+opt.lastName+"</option>";
		    				        		}
		    						}
		    			        	$("#sequentialApprover2").empty();
		    				        $("#sequentialApprover2").append(output);
		    				        $('#sequentialApprover2').trigger("chosen:updated");
		    			        },
		    			        error: function(e){
		    			            
		    			        }
		    			    });
		    		 }
		    	    
		    } 
			if(approverMode == 'Parallel'){
				$('#apprverReq').click();
				approvalType('Parallel');
				var str_array = parallelApproverIds.split(',');
			    for (var i = 0; i < str_array.length; i++) {
			        str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
			    }
			    $("#parallelApprover").val(str_array).trigger("chosen:updated");
		    	
		    } 
			if(approverMode == 'noApprovers'){
				 $("#apprverReq").prop('checked', false); 
			}
	}
	getIsClientVal();
});    
</script>

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<c:if test="${empty requirementId}">
				<h2>Add Job</h2>
			</c:if>
			<c:if test="${!empty requirementId}">
				<h2>Update Job</h2>
			</c:if>
		</div>
		<div class="success_pop" id="selector2" style="display: none;">
			<i class="fa fa-check-circle"></i><span id="msgJobDiv"></span>
		</div>
		<div class="pull-right brud-crum">
			<a
				href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home
			</a>&raquo;<a href="${SourceLeadWeb_atsUrl}/jobsList"> Jobs List </a>&raquo;
			<c:if test="${empty requirementId}">Add Job</c:if>
			<c:if test="${!empty requirementId}">Update Job</c:if>
		</div>

	</section>
	<div class="content-area content-new clearfix">
		<div id="requirementLoading"></div>

		<div class="newPrDetails newPrDetailsPMO">
			<div class="newPrTabs">
				<ul class="newPrTabsMenu">
					<c:if test="${empty requirementUUID}">
						<li class="current"><a href="#tab-a" data-toggle="tab">Job
								Details</a></li>
						<li><a href="#" id="jobRelease">Job Release</a></li>
						<li><a href="#" id="recommendations">Recommendations</a></li>
					</c:if>
					<c:if test="${!empty requirementUUID}">
						<li class="current"><a href="${SourceLeadWeb_atsUrl}/requirement?requirementUUID=${requirementUUID}">Job Details</a></li>
						<c:if test="${requirementStatus == 'OPEN'}">
							<li><a href="${SourceLeadWeb_atsUrl}/jobReleaseManagement?requirementUUID=${requirementUUID}">Job Release</a></li>
							<li><a href="${SourceLeadWeb_atsUrl}/jobRecommendations?requirementUUID=${requirementUUID}">Recommendations</a></li>
						</c:if>
						<c:if test="${requirementStatus != 'OPEN'}">
							<li><a href="#" id="jobRelease">Job Release</a></li>
							<li><a href="#" id="recommendations">Recommendations</a></li>
						</c:if>
					</c:if>
					<div class="clearfix mb"></div>
				</ul>
				<div class="clearfix"></div>

				<form:form id="requirement_form" name="requirement_form">
					<input type="hidden" id="actionId" value="${actionId}">
					<div class="form-horizontal req-dtls myForm row">
						<div class="clearfix mt10">
							<!-- <div class="col-lg-12">
          <h3>Job Details</h3>
        </div> -->
							<div class="col-md-12 clearfix">
								<div class="col-md-12">
									<div style="margin: 0 auto" class="input-group col-md-6">
										<label class="input-group-btn"> <span
											class="btn btn-primary">Select Template </span>
										</label> <select class="form-control chosen-select" id="template"
											onChange="setTemplateValues();">
											<option value="" selected>--- Select template ---</option>
											<c:forEach var="title" items="${jobsTemplateList}">
												<option value="${title.id}">${title.jobTitle}</option>
											</c:forEach>
										</select>
									</div>

									<div class="text-center clearfix mt text-muted">
										<div class="clearfix text-center mb">
											<span class="text-info"> <strong> <b
													class="hidden-xs">---------------------------------</b><span
													class="r-circle">OR</span> <b class="hidden-xs">------------------------------</b>
											</strong>
											</span>
										</div>
									</div>
								</div>
							</div>

							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
								<div class="form-group pras req-title" id="jobTitleDiv">
									<label class="col-md-3">Title<span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-md-9 validLabel">
										<input type="text" id="jobTitle" name="jobTitle"
											value="${fn:escapeXml(requirement.jobTitle)}"
											field-name="Job Title"
											data-validation="required validate_Space validate_length length1-250"
											value="" class="form-control" onblur="getTemplateVal();">
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Employment Type</label>
									<div class="col-md-9">
										<select class="selectpicker" id="jobType" name="jobType"
											onChange="getJobType(this);">
											<option value="" disabled selected>--- Select Employment Type ---</option>
											<option ${requirement.jobType  == 'C2C' ? 'selected' : ''}>C2C</option>
											<option ${requirement.jobType  == 'W-2' ? 'selected' : ''}>W-2</option>
											<option ${requirement.jobType  == 'C2H' ? 'selected' : ''}>C2H</option>
											<option ${requirement.jobType  == 'Contract' ? 'selected' : ''}>Contract</option>
											<option ${requirement.jobType  == 'Part Time' ? 'selected' : ''}>Part Time</option>
											<option ${requirement.jobType  == 'Full Time' ? 'selected' : ''}>Full Time</option>
											<option ${requirement.jobType  == 'Other' ? 'selected' : ''}>Other</option>
										</select>
									</div>
								</div>


								<div class="form-group" id="jobIdDiv">
									<label class="col-md-3">Job Id</label>
									<div class="col-md-9">
										<input type="text" class="form-control" id="jobId"
											name="jobId" value="${requirement.jobId}" field-name="JobId"
											data-validation="validate_Space validate_length length2-50"
											data-validation-optional="true" onkeyup="validateJobId();"
											value=""> <input type="hidden" name="requirementId"
											id="requirementId" value="${requirement.id}" />
									</div>
								</div>


								<div class="form-group jobTypeDiv" id="jobTypeDiv"
									style="display: none">
									<label class="col-md-3">Other</label>
									<div class="col-md-9">
										<input type="text" class="form-control" id="jobOtherType"
											name="jobOtherType" value="${requirement.otherJobType}"
											field-name="Job Type" value="">
									</div>
								</div>

								<div class="form-group" id="projectDurationYearsDiv"
									style="display: none">
									<label class="col-md-3">Project Duration </label>
									<div class="col-md-9">
										<div class="row">
											<div class="col-md-6 dropdown-select">
												<select class="form-control" id="projectDurationYears"
													name="projectDurationYears">
													<option value="">--Select Years--</option>
													<c:forEach var="durationYears" begin="0" end="30">
														<option
															${requirement.projectDurationYears  == durationYears ? 'selected' : ''}>${durationYears}</option>
													</c:forEach>

												</select>
											</div>
											<div class="col-md-6 dropdown-select"
												id="projectDurationMonthsDiv" style="display: none">
												<select class="form-control" id="projectDurationMonths"
													name="projectDurationMonths">
													<option value="">--Select Months--</option>
													<c:forEach var="durationMonths" begin="0" end="11">
														<option
															${requirement.projectDurationMonths  == durationMonths ? 'selected' : ''}>${durationMonths}</option>
													</c:forEach>

												</select>
											</div>
										</div>
									</div>
								</div>
								<c:if test="${ empty requirement.positions}">
									<div class="form-group">
										<label class="col-md-3">No. of Positions</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="positions"
												name="positions" value="1" field-name="Positions"
												data-validation="required validate_Space validate_length length1-5 validate_int"
												data-validation-optional="true">
										</div>
									</div>
								</c:if>
								<c:if test="${! empty requirement.positions}">
									<div class="form-group">
										<label class="col-md-3">No. of Positions</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="positions"
												name="positions" value="${requirement.positions}"
												field-name="Positions"
												data-validation="validate_Space validate_length length1-5 validate_int"
												data-validation-optional="true" value="">
										</div>
									</div>
								</c:if>

								<div class="form-group">
									<label class="col-md-3">Profile Percentage<span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-md-9">
										<input type="text" class="form-control" id="profilePercentage"
											name="profilePercentage" onblur="validateProfilePercentage()"
											value="${!empty requirement.profilePercentage  ? requirement.profilePercentage : SourceLeadWeb_profilePercentage}"
											field-name="Profile Percentage"
											data-validation="required validate_Space validate_int">
									</div>
									<div class="row mxrateerror" id="profilePercentageValidateId">
										<input type="hidden" name="profilePercentageValidation"
											id="profilePercentageValidation">
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Project</label>
									<div class="col-md-9 select-field">
										<select class="chosen-select form-control" id="projectId"
											name="projectClientID" field-name="Project Name">
											<option value="">--Select Project--</option>

											<c:forEach var="projectClient" items="${projectList}">
												<%-- 	<c:if test="${report.id == loginOrgLocId}">   --%>
												<c:if test="${! empty projectClient.clientName}">
													<option value="${projectClient.id}"
														${projectClient.id  == requirement.projectClientID.id  ? 'selected' : ''}>${projectClient.projectId.projectName}
														(${projectClient.clientName})</option>
												</c:if>
												<c:if test="${empty projectClient.clientName}">
													<option value="${projectClient.id}"
														${projectClient.id  == requirement.projectClientID.id ? 'selected' : ''}>${projectClient.projectId.projectName}
													</option>
												</c:if>

												<%--    </c:if>   --%>
											</c:forEach>

										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Education</label>
									<div class="col-md-9">
										<select multiple class="chosen-select form-control"
											name="education" data-placeholder="Select Education Options"
											id="education" data-validation-optional="true">
											<c:forEach var="education" items="${educationsList}">
												<c:set var="NUM" value="${0}" />
												<c:forEach var="existedEducation"
													items="${requirement.reqEducationListString}">
													<c:if test="${existedEducation eq education}">
														<c:set var="NUM" value="${1}" />
														<option selected value="${education}">${education}</option>
													</c:if>
												</c:forEach>
												<c:if test="${NUM == 0}">
													<option value="${education}">${education}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Experience Level</label>
									<div class="col-md-9 dropdown-select">
										<select name="experienceLevel" id="experienceLevel">
											<option value="">--Select Level--</option>
											<c:forEach var="level" items="${experienceLevelList}">
												<option value="${level.uniqueKey}"
													${requirement.experienceLevel.name  == level.name ? 'selected' : ''}>${level.name}</option>
											</c:forEach>
										</select>

									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Experience</label>
									<div class="col-md-9">
										<div class="row">
											<div class="col-md-6">
												<input type="text" class="form-control" placeholder="MinExp"
													id="minExp" name="minExp" value="${requirement.minExp}"
													field-name="MinExp"
													data-validation="validate_Space validate_float validate_length length1-3"
													data-validation-optional="true"
													onblur="minMaxExperience();" value="">
											</div>
											<div class="col-md-6">
												<input type="text" class="form-control" id="maxExp"
													name="maxExp" placeholder="MaxExp"
													value="${requirement.maxExp}" field-name="MaxExp"
													data-validation="validate_Space validate_float validate_length length1-3"
													data-validation-optional="true"
													onblur="minMaxExperience();" value="">
											</div>
										</div>
										<div class="row" id="expId">
											<input type="hidden" name="expValidation" id="expValidation">
										</div>


									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Industry</label>
									<div class="col-md-9">
										<select class="chosen-select form-control" name="industryName"
											id="industryName" data-placeholder="--Select Industry--"
											field-name="Industry" data-validation-optional="true">
											<option value="">--Select Industry--</option>
											<%-- <c:forEach var="industry" items="${industryList}">
	                     <option value="${industry.name}">${industry.name}</option>
                </c:forEach>  --%>

											<c:forEach var="industry" items="${industryList}">
												<c:set var="NUM" value="${0}" />
												<c:forEach var="existedIndustry"
													items="${requirement.industrySavedName}">
													<c:if test="${existedIndustry eq industry}">
														<c:set var="NUM" value="${1}" />
														<option selected value="${industry}">${industry}</option>
													</c:if>
												</c:forEach>
												<c:if test="${NUM == 0}">
													<option value="${industry}">${industry}</option>
												</c:if>
											</c:forEach>

										</select>
										<%-- <input type="hidden" id="existingState" value="${requirement.state}" > --%>
									</div>
								</div>

								<div class="form-group pras">
									<label class="col-md-3">Min / Max Rate</label>
									<div class="col-md-9">
										<div class="input-group payrate_p">
											<div class="input-group-btn selp-custom-min selp-customMin">

												<select class="selectpicker" id="currencyCode"
													name="currencyCode">

													<c:if test="${empty requirement.id}">
														<option
															${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
														<option
															${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
														<option
															${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
														<option
															${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
														<option
															${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
														<option
															${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
													</c:if>

													<c:if test="${! empty requirement.id}">
														<option
															${requirement.currencyCode  == 'INR' ? 'selected' : ''}>INR</option>
														<option
															${requirement.currencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
														<option
															${requirement.currencyCode  == 'USD' ? 'selected' : ''}>USD</option>
														<option
															${requirement.currencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
														<option
															${requirement.currencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
														<option
															${requirement.currencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
													</c:if>
												</select>
											</div>
											<div class="pull-left input_minmax">
												<input type="text" class="form-control input-sm" id="rate"
													name="rate" field-name="Min Rate" placeholder="Min Rate"
													value="${requirement.rate}"
													data-validation="validate_Space validate_length length1-8 validate_int"
													data-validation-optional="true" value=""
													onblur="minMaxRate();"> <input type="text"
													class="form-control input-sm" id="maxRate" name="maxRate"
													field-name="Max Rate" placeholder="Max Rate"
													value="${requirement.maxRate}"
													data-validation="validate_Space validate_length length1-8 validate_int"
													data-validation-optional="true" value=""
													onblur="minMaxRate();">
											</div>

											<div class="input-group-btn selp-custom-min payrate_p">
												<select class="selectpicker ratetype" id="rateType"
													name="rateType" field-name="Rate Per">
													<c:if test="${SourceLeadWeb_currencyCode == 'INR'}">
														<option value="">Rate Per</option>
														<option
															${requirement.rateType  == 'Monthly' ? 'selected' : 'selected'}>Monthly</option>
														<option
															${requirement.rateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
														<option
															${requirement.rateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
														<option
															${requirement.rateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
														<option
															${requirement.rateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
														<option
															${requirement.rateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
														<option
															${requirement.rateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
													</c:if>

													<c:if test="${SourceLeadWeb_currencyCode != 'INR'}">
														<option value="">Rate Per</option>
														<option
															${requirement.rateType  == 'Hourly' ? 'selected' : 'selected'}>Hourly</option>
														<option
															${requirement.rateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
														<option
															${requirement.rateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
														<option
															${requirement.rateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
														<option
															${requirement.rateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
														<option
															${requirement.rateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
														<option
															${requirement.rateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
													</c:if>

												</select>
											</div>
										</div>
										<div class="row mxrateerror" id="rateValidateId">
											<input type="hidden" name="rateValidation"
												id="rateValidation">
										</div>
									</div>
								</div>



								<div class="form-group">
									<label class="col-md-3">Date of Closure</label>
									<div class="col-md-9">
										<div class="input-group">
											<input type="text" name="closedDate"
												class="form-control calendar" id="closedDate"
												readonly="readonly"
												value="${requirement.closedDateStringFormat}"> <span
												class="input-group-addon" onClick="clearCloseDate();"><i
												class="colorRed fa fa-times cursorPointer"></i></span>

										</div>
									</div>
								</div>

								<c:if test="${empty requirement.id}">
									<div class="form-group" id="receivingApplicantsDiv"
										style="display: none">
										<label class="col-md-3"></label>
										<div class="col-md-9 checkBox">
											<label> <input type="checkbox"
												id="receivingApplicantsId" value="Y"
												${requirement.isreceivingApplicants  == 'Y' ? "checked" : ''}
												name="isreceivingApplicants" checked
												onclick="receivingApplicants();"> Allow Receiving
												Applicants
											</label>
										</div>
									</div>
								</c:if>
								<c:if test="${!empty requirement.id}">
									<c:if test="${! empty requirement.closedDateStringFormat}">
										<div class="form-group" id="receivingApplicantsDiv">
											<label class="col-md-3"></label>
											<div class="col-md-9 checkBox">
												<label> <input type="checkbox"
													id="receivingApplicantsId"
													value="${requirement.isreceivingApplicants}"
													${requirement.isreceivingApplicants  == 'Y' ? "checked" : ''}
													name="isreceivingApplicants"
													onclick="receivingApplicants();"> Allow Receiving
													Applicants
												</label>
											</div>
										</div>
									</c:if>
									<c:if test="${empty requirement.closedDateStringFormat}">
										<div class="form-group" id="receivingApplicantsDiv"
											style="display: none">
											<label class="col-md-3"></label>
											<div class="col-md-9 checkBox">
												<label> <input type="checkbox"
													id="receivingApplicantsId"
													value="${requirement.isreceivingApplicants}"
													${requirement.isreceivingApplicants  == 'Y' ? "checked" : ''}
													name="isreceivingApplicants"
													onclick="receivingApplicants();"> Allow Receiving
													Applicants
												</label>
											</div>
										</div>
									</c:if>
								</c:if>

								<%-- <div class="form-group">
				<label class="col-md-3">Address<span class="text-danger paddingLeft">*</span></label>
				<div class="col-md-9">
				 <input class="form-control positionRelative" id="ReqLocation" name="location" placeholder="Enter your address"  value="${requirement.location}" type="text" field-name="Address" data-validation="required"></input>
          			<i class="fa fa-refresh btn btn-primary refresh-icon mr6" title="Refresh" onClick="refreshGoogleApiLocation('ReqLocation','ReqCountry','ReqState','ReqCity','ReqZipCode');"></i>
				</div>
		</div> --%>

								<%-- <div class="form-group">
			<label class="col-md-3">City</label>
			<div class="col-md-9">
			     <input type="text"  class="form-control" name="city" id="ReqCity" value="${requirement.city}" placeholder="City" field-name="City" >
			</div>
		</div> --%>

								<%-- <div class="form-group">
				<label class="col-md-3">State</label>
				<div class="col-md-9">
				<input type="text"  class="form-control" name="state" id="ReqState" value="${requirement.state}" placeholder="State" field-name="State">
				</div>
		 </div> --%>

								<%-- <div class="form-group">
			<label class="col-md-3">Zip Code</label>
			<div class="col-md-9">
			     <input type="text"  class="form-control" name="zipCode" id="ReqZipCode" value="${requirement.zipCode}" placeholder="Zip Code" field-name="zip Code">
			</div>
		</div> --%>

								<%-- <div class="form-group">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
				 <input type="text" class="form-control"  name="countryReq" id="ReqCountry" placeholder="Country" value="${requirement.country}" field-name="Country">
				</div>
		</div> --%>

								<div class="form-group">
									<label class="col-md-3">Skills</label>
									<div class="col-md-9">
										<select multiple class="chosen-select form-control"
											name="skills" id="skills" data-placeholder="Select Skills"
											data-validation-optional="true">
											<c:forEach var="skill" items="${skillsList}">
												<c:set var="NUM" value="${0}" />
												<c:forEach var="existedSkill"
													items="${requirement.reqSkillsListString}">
													<c:if test="${existedSkill eq skill}">
														<c:set var="NUM" value="${1}" />
														<option selected value="${skill}">${skill}</option>
													</c:if>
												</c:forEach>
												<c:if test="${NUM == 0}">
													<option value="${skill}">${skill}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Immigration Status</label>
									<div class="col-md-9">
										<select multiple class="chosen-select form-control"
											name="visa" id="visa" data-placeholder="Select Visa"
											data-validation-optional="true">
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
									<label class="col-md-3">Travel Allowance</label>
									<div class="col-md-9">
										<select class="selectpicker" name="travelAllowance"
											id="travelAllowance" field-name="Travel Allowance">
											<option
												${requirement.travelAllowance  == '0%' ? 'selected' : ''}>0%</option>
											<option
												${requirement.travelAllowance  == '10%' ? 'selected' : ''}>10%</option>
											<option
												${requirement.travelAllowance  == '15%' ? 'selected' : ''}>15%</option>
											<option
												${requirement.travelAllowance  == '20%' ? 'selected' : ''}>20%</option>
											<option
												${requirement.travelAllowance  == '25%' ? 'selected' : ''}>25%</option>
											<option
												${requirement.travelAllowance  == '30%' ? 'selected' : ''}>30%</option>
											<option
												${requirement.travelAllowance  == '35%' ? 'selected' : ''}>35%</option>
											<option
												${requirement.travelAllowance  == '40%' ? 'selected' : ''}>40%</option>
											<option
												${requirement.travelAllowance  == '45%' ? 'selected' : ''}>45%</option>
											<option
												${requirement.travelAllowance  == '50%' ? 'selected' : ''}>50%</option>
										</select>

									</div>
								</div>


								<!--  Verificaiton Radio Buttons -->

								<div class="form-group" id="addmedical" style="display: block">
									<label class="col-md-3">Medical Clearance </label>
									<div class="col-md-9">
										<c:if test="${requirement.medicalClearence == 'Y'}">
											<span class="paddingRight"><input type="radio" checked
												value="Y" name="medicalClearence"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio"
												value="N" name="medicalClearence"><span class="mr5"></span>No</span>
										</c:if>
										<c:if test="${requirement.medicalClearence == 'N'}">
											<span class="paddingRight"><input type="radio"
												value="Y" name="medicalClearence"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio" checked
												value="N" name="medicalClearence"><span class="mr5"></span>No</span>
										</c:if>
										<c:if test="${empty requirement.medicalClearence}">
											<span class="paddingRight"><input type="radio"
												value="Y" name="medicalClearence"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio"
												value="N" checked name="medicalClearence"><span
												class="mr5"></span>No</span>
										</c:if>
									</div>
								</div>
								<div class="form-group" id="medicalTemplate"
									style="display: none">
									<div id="medicalClearenceDivId"></div>
								</div>


								<div class="form-group" id="addSecurityClearence"
									style="display: block">
									<label class="col-md-3">Security Clearance</label>
									<div class="col-md-9">
										<c:if test="${requirement.securityClearance == 'Y'}">
											<span class="paddingRight"><input type="radio" checked
												value="Y" name="securityClearance"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio"
												value="N" name="securityClearance"><span class="mr5"></span>No</span>
										</c:if>
										<c:if test="${requirement.securityClearance == 'N'}">
											<span class="paddingRight"><input type="radio"
												value="Y" name="securityClearance"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio" checked
												value="N" name="securityClearance"><span class="mr5"></span>No</span>
										</c:if>

										<c:if test="${empty requirement.securityClearance}">
											<span class="paddingRight"><input type="radio"
												value="Y" name="securityClearance"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio"
												value="N" checked name="securityClearance"><span
												class="mr5"></span>No</span>
										</c:if>
									</div>
								</div>
								<div class="form-group" id="securityClearenceTemplate"
									style="display: none">
									<div id="securityClearenceDivId"></div>
								</div>

								<div class="form-group" id="addOverTime" style="display: block">
									<label class="col-md-3">OT</label>
									<div class="col-md-9">
										<c:if test="${requirement.overTime == 'Y'}">
											<span class="paddingRight"><input type="radio" checked
												value="Y" name="overTime"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio"
												value="N" name="overTime"><span class="mr5"></span>No</span>
										</c:if>
										<c:if test="${requirement.overTime == 'N'}">
											<span class="paddingRight"><input type="radio"
												value="Y" name="overTime"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio" checked
												value="N" name="overTime"><span class="mr5"></span>No</span>
										</c:if>
										<c:if test="${empty requirement.overTime}">
											<span class="paddingRight"><input type="radio"
												value="Y" name="overTime"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio"
												value="N" checked name="overTime"><span class="mr5"></span>No</span>
										</c:if>

									</div>
								</div>
								<div class="form-group" id="overTimeTemplate"
									style="display: none">
									<div id="overTimeDivId"></div>
								</div>

								<div class="form-group" id="addBackgroundCheck"
									style="display: block">
									<label class="col-md-3">Background Check</label>
									<div class="col-md-9">
										<c:if test="${requirement.backgroundCheck == 'Y'}">
											<span class="paddingRight"><input type="radio" checked
												value="Y" name="backgroundCheck"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio"
												value="N" name="backgroundCheck"><span class="mr5"></span>No</span>
										</c:if>
										<c:if test="${requirement.backgroundCheck == 'N'}">
											<span class="paddingRight"><input type="radio"
												value="Y" name="backgroundCheck"><span class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio" checked
												value="N" name="backgroundCheck"><span class="mr5"></span>No</span>
										</c:if>
										<c:if test="${empty requirement.backgroundCheck}">
											<span class="paddingRight"><input type="radio"
												value="Y" checked name="backgroundCheck"><span
												class="mr5"></span>Yes</span>
											<span class="paddingLeft"><input type="radio"
												value="N" name="backgroundCheck"><span class="mr5"></span>No</span>
										</c:if>
									</div>
								</div>
								<div class="form-group" id="backgroundCheckTemplate"
									style="display: none">
									<div id="backgroundCheckDivId"></div>
								</div>

								<div class="form-group" id="addGender" style="display: block">
									<label class="col-md-3">Gender</label>
									<div class="col-md-9">
										<c:if test="${requirement.gender == 'Male'}">
											<span class="paddingRight"><input type="radio" checked
												value="Male" name="gender"><span class="mr5"></span>Male</span>
											<span class="paddingLeft paddingRight"><input
												type="radio" value="Female" name="gender"><span
												class="mr5"></span>Female</span>
											<span class="paddingLeft"><input type="radio"
												value="All" name="gender"><span class="mr5"></span>All</span>
										</c:if>
										<c:if test="${requirement.gender == 'Female'}">
											<span class="paddingRight"><input type="radio"
												value="Male" name="gender"><span class="mr5"></span>Male</span>
											<span class="paddingLeft paddingRight"><input
												type="radio" checked value="Female" name="gender"><span
												class="mr5"></span>Female</span>
											<span class="paddingLeft"><input type="radio"
												value="All" name="gender"><span class="mr5"></span>All</span>
										</c:if>
										<c:if test="${requirement.gender == 'All'}">
											<span class="paddingRight"><input type="radio"
												value="Male" name="gender"><span class="mr5"></span>Male</span>
											<span class="paddingLeft paddingRight"><input
												type="radio" value="Female" name="gender"><span
												class="mr5"></span>Female</span>
											<span class="paddingLeft"><input type="radio" checked
												value="All" name="gender"><span class="mr5"></span>All</span>
										</c:if>
										<c:if test="${empty requirement.gender}">
											<span class="paddingRight"><input type="radio"
												value="Male" name="gender"><span class="mr5"></span>Male</span>
											<span class="paddingLeft paddingRight"><input
												type="radio" value="Female" name="gender"><span
												class="mr5"></span>Female</span>
											<span class="paddingLeft"><input type="radio"
												value="All" name="gender" checked><span class="mr5"></span>All</span>
										</c:if>

									</div>
								</div>
								<div class="form-group" id="genderTemplate"
									style="display: none">
									<div id="genderDivId"></div>
								</div>




								<!--  Radio Buttons -->
								<div class="form-group pras">
									<input type="hidden" name="templateId" id="templateId" value="" />
									<label class="col-md-3">Attachment</label>
									<!-- upload file html need to add bottom JavaScript otherwise it will not work -->
									<div class="col-md-9 positionRelative">
										<c:if test="${empty requirement.id}">
											<div class="input-group attachfile" id="uploadFileGroup">
												<label class="input-group-btn"> <span
													class="btn btn-primary"><i
														class="fa fa-folder-open-o"></i>&nbsp;Browse File <input
														type="file" id="uploadBtn" class="hidden upload">
												</span>
												</label> <input type="text" class="form-control" readonly
													id="uploadFile" name="docFile"
													placeholder="Upload File ..." field-name="Upload Resume"
													data-validation="validate_docimgpdf"
													data-validation-optional="true">
												<div class="clearfix"></div>
												<div class="clearfix"></div>
												<div class="clearUpload"></div>
											</div>
										</c:if>
										<c:if test="${!empty requirement.id}">

											<c:if test="${empty requirement.fileName}">
												<div class="input-group attachfile" id="uploadFileGroup">
													<label class="input-group-btn"> <span
														class="btn btn-primary" onchange="uploadButton();"><i
															class="fa fa-folder-open-o"></i>&nbsp;Browse File <input
															type="file" id="uploadBtn" class="hidden upload">
													</span>
													</label> <input type="text" class="form-control input-sm" readonly
														id="uploadFile" name="docFile"
														placeholder="Upload File ..." field-name="Upload Resume"
														data-validation="validate_docimgpdf"
														data-validation-optional="true">
													<div class="clearfix"></div>
													<div class="clearfix"></div>
													<div class="clearUpload"></div>
												</div>
											</c:if>

											<c:if test="${!empty requirement.fileName}">

												<div class="input-group attachfile" id="uploadFileGroup123"
													style="display: none;">
													<label class="input-group-btn"> <span
														class="btn btn-primary"><i
															class="fa fa-folder-open-o"></i>&nbsp;Browse File <input
															type="file" id="uploadBtn" class="hidden upload">
													</span>
													</label> <input type="text" class="form-control input-sm" readonly
														id="uploadFile" name="docFile"
														placeholder="Upload File ..." field-name="Upload Resume"
														data-validation="validate_docimgpdf"
														data-validation-optional="true">
													<div class="clearfix"></div>
													<div class="clearfix"></div>
													<div class="clearUpload"></div>
												</div>

												<div class="col-md-6" id="uploadDocNameDiv">
													<label id="uploadDocumentName" name="uploadDocumentName"
														class="text-primary tbldata-dotdot-vsm pull-left"
														title="${requirement.fileName}">${requirement.fileName}</label>&nbsp;&nbsp;<span
														id="delete_shiftAFile"
														onclick="removeReqDoc(${requirement.id});"><i
														class="fa fa-trash removeIcon"></i></span> <input type="hidden"
														id="uploadExisitngFile" name="uploadExisitngFile"
														value="${requirement.fileName}">
												</div>

											</c:if>
										</c:if>
									</div>
									<div class="clearfix"></div>
									<!-- upload file html need to add bottom JavaScript otherwise it will not work -->
								</div>
								<div class="form-group pras req-title" id="jobTitleDiv">
									<label class="col-md-3">Process</label>
									<div class="col-md-9 validLabel">
										<input type="text" id="process" name="process"
											value="${requirement.process}" field-name="Job Process"
											data-validation="validate_Space validate_length length1-250"
											data-validation-optional="true" value="" class="form-control">
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="clearfix mb">
									<label class="col-md-3">Description</label>
									<div class="col-md-12 txtEditor_p">
										<%-- <textarea rows="5" class="form-control" id="bodytext" name="description" value="${requirement.description}" field-name="Description" data-validation="" data-validation-optional="true">${requirement.description}</textarea> --%>
										<textarea id="txtEditor" name="description"
											value="${requirement.description}"
											class="form-control newContrtextarea">${requirement.description}</textarea>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Notice Period</label>
									<div class="col-md-9">
										<select multiple class="chosen-select form-control"
											name="noticePeriod" data-placeholder="Select Notice Period"
											id="noticePeriod" data-validation-optional="true"
											onclick="getNoticeperiod();">
											<option
												${requirement.noticePeriod  == 'Immediately Available' ? 'selected' : ''}
												value="Immediately Available">Immediately Available</option>
											<option
												${requirement.noticePeriod  == '15-Days' ? 'selected' : ''}
												value="15 Days">15 Days</option>
											<option
												${requirement.noticePeriod  == '1-Month' ? 'selected' : ''}
												value="1 Month">1 Month</option>
											<option
												${requirement.noticePeriod  == '2-Month' ? 'selected' : ''}
												value="2 Months">2 Months</option>
											<option
												${requirement.noticePeriod  == '3-Month' ? 'selected' : ''}
												value="3 Months">3 Months</option>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Cooling-Off Period &nbsp;&nbsp;<i
										class="fa fa-info-circle"
										title="The time period during which the applicant will not be able to apply for new jobs (due to awaiting feedbacks or other reasons)"></i></label>
									<div class="col-md-9 selectpicker_maxheight">
										<select class="selectpicker" id="coolingOff" name="coolingOff">

											<c:if
												test="${requirement.requirementSubmissionStatus == 'OPEN' ||  requirement.requirementSubmissionStatus == 'ONHOLD'}">
												<option
													${requirement.reqCoolingOffPeriod  == '0 Days' ? 'selected' : ''}
													value="0 Days">0 Days</option>
												<option
													${requirement.reqCoolingOffPeriod  == '1 Week' ? 'selected' : ''}
													value="1 Week">1 Week</option>
												<option
													${requirement.reqCoolingOffPeriod  == '2 Weeks' ? 'selected' : ''}
													value="2 Weeks">2 Weeks</option>
												<option
													${requirement.reqCoolingOffPeriod  == '3 Weeks' ? 'selected' : ''}
													value="3 Weeks">3 Weeks</option>
												<option
													${requirement.reqCoolingOffPeriod  == '1 Month' ? 'selected' : ''}
													value="1 Month">1 Month</option>
												<option
													${requirement.reqCoolingOffPeriod  == '2 Months' ? 'selected' : ''}
													value="2 Months">2 Months</option>
												<option
													${requirement.reqCoolingOffPeriod  == '3 Months' ? 'selected' : ''}
													value="3 Months">3 Months</option>
												<option
													${requirement.reqCoolingOffPeriod  == '4 Months' ? 'selected' : ''}
													value="4 Months">4 Months</option>
												<option
													${requirement.reqCoolingOffPeriod  == '5 Months' ? 'selected' : ''}
													value="5 Months">5 Months</option>
												<option
													${requirement.reqCoolingOffPeriod  == '6 Months' ? 'selected' : ''}
													value="6 Months">6 Months</option>
												<option
													${requirement.reqCoolingOffPeriod  == '7 Months' ? 'selected' : ''}
													value="7 Months">7 Months</option>
												<option
													${requirement.reqCoolingOffPeriod  == '8 Months' ? 'selected' : ''}
													value="8 Months">8 Months</option>
												<option
													${requirement.reqCoolingOffPeriod  == '9 Months' ? 'selected' : ''}
													value="9 Months">9 Months</option>
												<option
													${requirement.reqCoolingOffPeriod  == '10 Months' ? 'selected' : ''}
													value="10 Months">10 Months</option>
												<option
													${requirement.reqCoolingOffPeriod  == '11 Months' ? 'selected' : ''}
													value="11 Months">11 Months</option>
												<option
													${requirement.reqCoolingOffPeriod  == '1 Year' ? 'selected' : ''}
													value="1 Year">1 Year</option>
											</c:if>
											<c:if
												test="${requirement.requirementSubmissionStatus != 'OPEN'  &&  requirement.requirementSubmissionStatus != 'ONHOLD'}">
												<option
													${loginOrgLocCoolingOffPeriod  == '0 Days' ? 'selected' : ''}
													value="0 Days">0 Days</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '1 Week' ? 'selected' : ''}
													value="1 Week">1 Week</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '2 Weeks' ? 'selected' : ''}
													value="2 Weeks">2 Weeks</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '3 Weeks' ? 'selected' : ''}
													value="3 Weeks">3 Weeks</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '1 Month' ? 'selected' : ''}
													value="1 Month">1 Month</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '2 Months' ? 'selected' : ''}
													value="2 Months">2 Months</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '3 Months' ? 'selected' : ''}
													value="3 Months">3 Months</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '4 Months' ? 'selected' : ''}
													value="4 Months">4 Months</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '5 Months' ? 'selected' : ''}
													value="5 Months">5 Months</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '6 Months' ? 'selected' : ''}
													value="6 Months">6 Months</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '7 Months' ? 'selected' : ''}
													value="7 Months">7 Months</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '8 Months' ? 'selected' : ''}
													value="8 Months">8 Months</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '9 Months' ? 'selected' : ''}
													value="9 Months">9 Months</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '10 Months' ? 'selected' : ''}
													value="10 Months">10 Months</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '11 Months' ? 'selected' : ''}
													value="11 Months">11 Months</option>
												<option
													${loginOrgLocCoolingOffPeriod  == '1 Year' ? 'selected' : ''}
													value="1 Year">1 Year</option>
											</c:if>
										</select>
									</div>
								</div>

								<div class="clearfix"></div>
								<div class="form-group">
									<div class="ml">
										<%-- 					<c:set var="val" value="${requirement.isClientJob}" /> --%>
										<c:choose>
											<c:when test="${val == 'Y'}">
												<input type="checkbox" name="isTemplate" id="isTemplate"
													value="" onclick="getTemplateVal();">
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="isTemplate" id="isTemplate"
													value="" onclick="getTemplateVal();">
											</c:otherwise>
										</c:choose>
										<label>&nbsp;&nbsp;<strong>Save as Template</strong>
											&nbsp;&nbsp;
										</label>
									</div>
								</div>



								<c:if test="${!empty sourceleadSectionList}">
									<c:forEach var="section" items="${sourceleadSectionList}"
										varStatus="sectionOrder">
										<c:if test="${sectionOrder.index == 0}">
											<div id="customDiv${section.id}"></div>
										</c:if>
									</c:forEach>
								</c:if>

							</div>

							<div class="clearfix">
								<div class="col-xs-12 col-sm-6 col-md-12 col-lg-12">
									<div class="ml">
										<input type="checkbox" name="jobFair" id="isJobFair"
											${requirement.jobFairList != null && requirement.jobFairList.size() > 0 ? "checked='checked'" : ""}>
										<label for="isJobFair">&nbsp;&nbsp;<strong>Job
												Fair</strong> &nbsp;&nbsp;
										</label>
									</div>
									<div id="selectJobFairDiv"
										${requirement.jobFairList != null && requirement.jobFairList.size() > 0 ? "" : "style='display: none;'"}>
										<%-- <label class="col-md-3">Select Drive Day(s)</label>
					<div class="col-md-6">
						<select multiple class="chosen-select form-control"
							name="walkInDays" data-placeholder="Select WalkIn Day(s)"
							id="walkInDays" field-name="Drive Day">
							<c:forEach
								items="Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday"
								var="day">
								<c:set var="selected" value="false"></c:set>
								<c:if
									test="${requirement.walkInDays!=null && requierment.walkInDays != ''}">
									<c:forEach items="${requirement.walkInDays.split(',')}"
										var="selectedDay">
										<c:if test="${selectedDay eq day}">
											<c:set var="selected" value="true"></c:set>
										</c:if>
									</c:forEach>
								</c:if>
								<option value="${day}" ${selected?"selected='selected'":""}>${day}</option>
							</c:forEach>
						</select>
					</div> --%>
										<div class="clearfix">
											<h3>Choose Job Fair</h3>
										</div>
										<div class="row">
											<div class="col-md-7">
												<label class="col-md-3">Job Fair<span
													class="text-danger paddingLeft">*</span></label>
												<div class="col-md-9 chooRadius">
													<div class="row">
														<div class="col-md-9">
															<select multiple class="chosen-select form-control"
																name="jobFairId" id="jobFairList" field-name="Job Fair">

															</select>
														</div>
														<div class="col-md-3">
															<div title="Clear"
																onclick="getJobFairList();getSavedJobFairsForRequirement();"
																class="fa fa-refresh btn btn-primary pull-left"></div>
															<div title="Add" onclick="addNewJobFair();"
																class="fa fa-plus btn btn-default pull-right"></div>
														</div>
													</div>
												</div>
												<%-- <div class="col-md-9 chooRadius">
                  				<div class="input-group col-md-9 pull-left">
									<select class="chosen-select form-control select-bg" data-validation-optional="true" field-name="Client" name="jobFairId" id="selectJobFair" onchange="getJobFairDates(this);">
					                    
		                   		    </select>
								</div>
								<span class="col-md-3 noPad clearfix"><a href="<c:url value='/addJobFair'/>" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn" title="add new JobFair"></i></a>
								<span class="input-group-addon cursorPointer bg-blue refreshBtn" onclick="getJobFairList();"><i class="fa fa-refresh "></i></span>
								</span>
							</div> --%>

												<%-- <div id="jobFairDatesDiv" class="multiJob" ${requirement.jobFairList != null && requirement.jobFairList.size() > 0 ? "" : "style='display: none;'"}> --%>
												<div id="jobFairDatesDiv" class="multiJob">
													<c:set var="datesCount" value="0"></c:set>
													<c:forEach items="${requirement.jobFairList}"
														var="jobFairs">
														<div class="multiJobMain" data-id="${jobFairs.id}"
															name="jobFairMap[0].key">
															<!-- <i class="fa fa-times" onclick="removeJobFair($(this))"></i> -->
															<div>${jobFairs.name}</div>
															<div class="multiJobTitle clearfix">
																<h2 class="pull-left">Fair runs from
																	${jobFairs.startDateString} to
																	${jobFairs.endDateString}</h2>
																<span class="pull-right"> <input
																	class="form-control calendar jobFairCalendar calendarIcon"
																	readonly="readonly" type="text"
																	data-date_format="${jobFairs.dateFormat}"
																	data-start_date="${jobFairs.startDateString}"
																	data-end_date="${jobFairs.endDateString}"
																	data-job_fair_id="${jobFairs.id}">
																</span>
															</div>

															<div class="multiJobDate clearfix">
																<c:forEach items="${jobFairs.jobFairRequirementList}"
																	var="jobFairRequirement">
																	<c:if
																		test="${jobFairRequirement.requirement.id == requirement.id}">
																		<c:choose>
																			<c:when
																				test="${jobFairRequirement.fairDates != null && jobFairRequirement.fairDates != ''}">
																				<c:forEach
																					items="${jobFairRequirement.fairDates.split(',')}"
																					var="fairDate">
																					<span class="reqDate" data-value="${fairDate}">${fairDate}<i
																						class="fa fa-times"
																						onclick="removeJobFairDate($(this))"></i> <input
																						value="${jobFairs.id}:${fairDate}"
																						name="jobFairDates[${datesCount}]" type="hidden">
																						<%-- <input value="${fairDate}" name="jobFairMap[0].value[0]" type="hidden"> --%>
																					</span>
																					<c:set var="datesCount" value="${datesCount + 1}"></c:set>
																				</c:forEach>
																			</c:when>
																			<c:otherwise>
																				<div class="reqDate">
																					<input value="${jobFairs.id}"
																						name="jobFairDates[0]" type="hidden">
																					Select optional JobFair dates(s)
																				</div>
																			</c:otherwise>
																		</c:choose>
																	</c:if>
																</c:forEach>
															</div>
														</div>
													</c:forEach>
												</div>
												<!-- <div class="pull-left input_minmax">
							    <input class="form-control valid" id="" name="" field-name="" value="" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true" placeholder="Min Rate" type="text">
							</div> -->
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="clearfix mt">
								<div class="col-xs-12 col-sm-6 col-md-12 col-lg-12">
									<c:if
										test="${(SourceLeadWeb_vms == 'Y' && SourceLeadWeb_ats == 'Y') || (SourceLeadWeb_vms != 'Y')}">

										<c:set var="val" value="${requirement.isClientJob}" />
										<c:choose>
											<c:when test="${val == 'Y'}">
												<div class="ml">
													<!-- here the isClient checkbox name is changes to isClientEdit because, readonly check box is not working and onclick methods are effected (Venkat Kesav) -->
													<input type="checkbox" name="isClient" id="isClientJob"
														class="isClient" value="${requirement.isClientJob}"
														${requirement.isClientJob  == 'Y' ? "checked" : ''}
														onclick="getIsClientVal();" hidden="true"
														checked="checked"> <label>&nbsp;&nbsp;<strong>Is
															Client Job <i class='fa fa-info-circle'
															title='Post the job including the related client information'></i>
													</strong> &nbsp;&nbsp;
													</label>
												</div>
											</c:when>
											<c:otherwise>
												<div class="ml">
													<input type="checkbox" name="isClient" id="isClientJob"
														class="isClient" value="${requirement.isClientJob}"
														${requirement.isClientJob  == 'Y' ? "checked" : ''}
														onclick="getIsClientVal();"> <label>&nbsp;&nbsp;<strong>Client
															Job <i class='fa fa-info-circle'
															title='Post the job including the related client information'></i>
													</strong> &nbsp;&nbsp;
													</label>
												</div>
											</c:otherwise>
										</c:choose>
									</c:if>

									<div id="clientDetails" style="display: none;">
										<div class="clearfix">
											<h3>Client Details</h3>
										</div>
										<div class="clearfix">
											<div class="form-group">
												<label class="col-md-3">Client<span
													class="text-danger paddingLeft">*</span></label>
												<div class="col-md-9 chooRadius">
													<div class="input-group col-md-9 pull-left">
														<c:set var="val" value="${requirement.isClientJob}" />
														<c:choose>
															<c:when test="${val == 'Y'}">
																<select class="chosen-select form-control select-bg"
																	data-validation-optional="true" field-name="Client"
																	name="selectClient" id="addClientId">
																	<c:set var="report" value="${orgClient}" />
																	<c:if test="${report.locationId.id == loginOrgLocId}">
																		<option
																			value="${report.id},${report.responderLocationId.id}"
																			selected="selected">${report.responderLocationId.org.orgName}</option>
																	</c:if>
																	<c:if
																		test="${report.responderLocationId.id == loginOrgLocId}">
																		<option value="${report.id},${report.locationId.id}"
																			selected="selected">${report.locationId.org.orgName}</option>
																	</c:if>
																</select>
															</c:when>
															<c:otherwise>
																<select class="chosen-select form-control select-bg"
																	data-validation-optional="true" field-name="Client"
																	name="selectClient" id="addClientId"
																	onchange="getContactList(this); getClientSubmissionTemplates(this); ">
																	<option value="">-- Select Client --</option>
																	<c:forEach var="report" items="${add_clientList}">
																		<c:if test="${report.locationId.id == loginOrgLocId}">
																			<option
																				value="${report.id},${report.responderLocationId.id}"
																				${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.responderLocationId.org.orgName}</option>
																		</c:if>
																		<c:if
																			test="${report.responderLocationId.id == loginOrgLocId}">
																			<option value="${report.id},${report.locationId.id}"
																				${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option>
																		</c:if>
																	</c:forEach>
																</select>
															</c:otherwise>
														</c:choose>
													</div>
													<c:if test="${requirement.isClientJob != 'Y'}">
														<sec:authorize access="hasAnyRole('ROLE_CREATE_CLIENT')">
															<span class="col-md-3 noPad clearfix"><a
																href="${SourceLeadWeb_vmsUrl}/client" target="_blank"><i
																	class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
																<span
																class="input-group-addon cursorPointer bg-blue refreshBtn"
																onclick="refreshRequirementClientList();"><i
																	class="fa fa-refresh "></i></span> </span>
														</sec:authorize>
													</c:if>
												</div>
											</div>
											<div class="form-group ">
												<label class="col-md-3">Client Contact <span
													class="text-danger paddingLeft">*</span></label>
												<div class="col-md-9 chooRadius">
													<div
														class="input-group col-md-10 pull-left seleOverFlow clearfix">
														<c:set var="val" value="${requirement.isClientJob}" />
														<div class="col-md-10 noPad">
															<c:choose>
																<c:when test="${val == 'Y'}">
																	<select class="chosen-select form-control"
																		name="selectContact" data-validation-optional="true"
																		field-name="Client Contact" id="selectContact">
																		<c:if test="${!empty clientContactList}">
																			<c:set var="found" value="0" scope="page" />
																			<c:forEach var="report" items="${clientContactList}">
																				<c:if test="${report.id  == requirement.spocId.id}">
																					<c:set var="found" value="${found + 1}"
																						scope="page" />
																					<option value="${report.id}" selected>${report.spocName}
																						(${report.ownerName})</option>
																				</c:if>
																				<c:if test="${report.id  != requirement.spocId.id}">
																					<option value="${report.id}">${report.spocName}
																						(${report.ownerName})</option>
																				</c:if>
																			</c:forEach>
																			<c:if test="${found == 0}">
																				<option value="${requirement.spocId.id}" selected>${requirement.spocName}
																					(${requirement.spocOwnerName})</option>
																			</c:if>
																		</c:if>
																		<c:if test="${empty clientContactList}">
																			<option value="${requirement.spocId.id}">${requirement.spocName}
																				(${requirement.spocOwnerName})</option>
																		</c:if>
																	</select>
																</c:when>
																<c:otherwise>
																	<select class="chosen-select form-control"
																		name="selectContact" data-validation-optional="true"
																		field-name="Client Contact" id="selectContact">
																		<option value=''>-- Select Contact --</option>
																		<c:forEach var="report" items="${clientContactList}">
																			<%-- <option value="${report.id}" ${report.id  == requirement.spocId.id ? 'selected' : ''} >${report.spocId.SpocUser.firstName}&nbsp;${report.spocId.SpocUser.lastName}</option> --%>
																			<option value="${report.id}"
																				${report.id  == requirement.spocId.id ? 'selected' : ''}>${report.spocName}
																				(${report.ownerName})</option>
																		</c:forEach>
																	</select>
																</c:otherwise>
															</c:choose>
														</div>
														<c:if test="${requirement.isClientJob != 'Y'}">
															<div class="col-md-2 noPad">
																<span
																	class="input-group-addon cursorPointer bg-blue refreshBtn"
																	onclick="refreshRequirementClientContactList();"><i
																	class="fa fa-refresh "></i></span>
															</div>
														</c:if>
													</div>
													<c:if test="${requirement.isClientJob != 'Y'}">
														<span class="col-md-2 noPad clearfix"
															onclick="selectedClient();"> <sec:authorize
																access="hasAnyRole('ROLE_CREATE_CLIENT_CONTACT')">
																<a href="javascript:void(0)"><i
																	class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
															</sec:authorize>
														</span>
													</c:if>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-3">Bill Rate / Salary&nbsp;<i
													class='fa fa-info-circle'
													title='Client proposed bill rate for the particular job (strictly for internal reference only)'></i></label>
												<div class="col-md-9 ratetype">
													<div class="input-group">
														<c:set var="val" value="${requirement.isClientJob}" />
														<c:choose>
															<c:when test="${val == 'Y'}">
																<div class="input-group-btn selp-custom-min">
																	<select class="chosen-select" disabled="disabled"
																		id="clientCurrencyCode" name="clientCurrencyCode">
																		<option
																			${requirement.clientCurrencyCode  == 'INR' ? 'selected' : ''}>INR</option>
																		<option
																			${requirement.clientCurrencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
																		<option
																			${requirement.clientCurrencyCode  == 'USD' ? 'selected' : ''}>USD</option>
																		<option
																			${requirement.clientCurrencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
																		<option
																			${requirement.clientCurrencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
																		<option
																			${requirement.clientCurrencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
																	</select>

																</div>
																<div class="pull-left input_minmax">
																	<input type="text" disabled="disabled"
																		class="form-control" id="clientRate" name="clientRate"
																		field-name="Client Rate"
																		value="${requirement.clientRate}"
																		data-validation="validate_Space validate_float validate_length length1-8"
																		data-validation-optional="true" placeholder="Min Rate"
																		onblur="minMaxRate();"> <input type="text"
																		disabled="disabled" class="form-control"
																		id="clientMaxRate" name="clientMaxRate"
																		field-name="Client Max Rate"
																		value="${requirement.clientMaxRate}"
																		data-validation="validate_Space validate_float validate_length length1-8"
																		data-validation-optional="true" placeholder="Max Rate"
																		onblur="minMaxRate();">
																</div>
																<div class="input-group-btn selp-custom-min">
																	<select disabled="disabled"
																		class="chosen-select ratetype" id="clientRateType"
																		field-name="Client Rate Per"
																		value="${requirement.clientRateType}"
																		name="clientRateType">
																		<c:if test="${SourceLeadWeb_currencyCode == 'INR'}">
																			<option value="">Rate Per</option>
																			<option
																				${requirement.clientRateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
																			<option
																				${requirement.clientRateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
																			<option
																				${requirement.clientRateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
																			<option
																				${requirement.clientRateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
																		</c:if>
																		<c:if test="${SourceLeadWeb_currencyCode != 'INR'}">
																			<option value="">Rate Per</option>
																			<option
																				${requirement.clientRateType  == 'Hourly' ? 'selected' : 'selected'}>Hourly</option>
																			<option
																				${requirement.clientRateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
																			<option
																				${requirement.clientRateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
																			<option
																				${requirement.clientRateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
																		</c:if>
																	</select>
																</div>
															</c:when>
															<c:otherwise>

																<div class="input-group-btn selp-custom-min">
																	<select class="selectpicker" id="clientCurrencyCode"
																		name="clientCurrencyCode">

																		<c:if test="${empty requirement.id}">
																			<option
																				${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
																			<option
																				${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
																			<option
																				${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
																			<option
																				${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
																			<option
																				${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
																			<option
																				${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
																		</c:if>

																		<c:if test="${! empty requirement.id}">
																			<option
																				${requirement.clientCurrencyCode  == 'INR' ? 'selected' : ''}>INR</option>
																			<option
																				${requirement.clientCurrencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
																			<option
																				${requirement.clientCurrencyCode  == 'USD' ? 'selected' : ''}>USD</option>
																			<option
																				${requirement.clientCurrencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
																			<option
																				${requirement.clientCurrencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
																			<option
																				${requirement.clientCurrencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
																		</c:if>
																	</select>

																</div>
																<div class="pull-left input_minmax">
																	<input type="text" class="form-control" id="clientRate"
																		name="clientRate" field-name="Client Rate"
																		value="${requirement.clientRate}"
																		data-validation="validate_Space validate_float validate_length length1-8"
																		data-validation-optional="true" placeholder="Min Rate"
																		onblur="minMaxRate();"> <input type="text"
																		class="form-control" id="clientMaxRate"
																		name="clientMaxRate" field-name="Client Max Rate"
																		value="${requirement.clientMaxRate}"
																		data-validation="validate_Space validate_float validate_length length1-8"
																		data-validation-optional="true" placeholder="Max Rate"
																		onblur="minMaxRate();">
																</div>
																<div class="input-group-btn selp-custom-min">
																	<select class="selectpicker ratetype"
																		id="clientRateType" field-name="Client Rate Per"
																		value="${requirement.clientRateType}"
																		name="clientRateType">
																		<c:if test="${SourceLeadWeb_currencyCode == 'INR'}">
																			<!--  <option value="">Rate Per</option> -->
																			<option
																				${requirement.clientRateType  == 'Monthly' ? 'selected' : 'selected'}>Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
																			<option
																				${requirement.clientRateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
																			<option
																				${requirement.clientRateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
																			<option
																				${requirement.clientRateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
																		</c:if>
																		<c:if test="${SourceLeadWeb_currencyCode != 'INR'}">
																			<!-- <option value="">Rate Per</option> -->
																			<option
																				${requirement.clientRateType  == 'Hourly' ? 'selected' : 'selected'}>Hourly</option>
																			<option
																				${requirement.clientRateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
																			<option
																				${requirement.clientRateType  == 'Bi-weekly' ? 'selected' : ''}>Bi-weekly</option>
																			<option
																				${requirement.clientRateType  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
																			<option
																				${requirement.clientRateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
																		</c:if>
																	</select>
																</div>
															</c:otherwise>
														</c:choose>

													</div>
												</div>
												<div class="row mxrateerror" id="rateValidateId">
													<input type="hidden" name="rateValidation"
														id="rateValidation">
												</div>
												<div class="clearfix"></div>
												<div class="clearfix"></div>
											</div>
											<div class="form-group">
												<label class="col-md-3 paddingRight">Submission Form<span
													class="text-danger">*</span><i
													class='fa fa-info-circle'
													title="Select client specific Submission Template to provide the applicant's information"></i></label>
												<div class="col-md-9">
													<div>
														<c:set var="val" value="${requirement.isClientJob}" />
														<c:choose>
															<c:when test="${val == 'Y'}">
																<select class="chosen-select form-control"
																	onchange="false" id="clientSubmissionForm"
																	name="clientSubmissionForm"
																	field-name="Submission Form"
																	data-validation-optional="true">
																	<c:set var="report" value="${clientSubmissionTemplete}" />
																	<option value="${report.id}">${report.templateName}</option>
																</select>
															</c:when>
															<c:otherwise>
																<select class="chosen-select form-control"
																	id="clientSubmissionForm" name="clientSubmissionForm"
																	field-name="Submission Form"
																	data-validation-optional="true">
																	<option value="">-- Select Submission Form --</option>
																	<c:forEach var="report"
																		items="${clientSubmissionTempletesList}">
																		<%-- 	<c:if test="${report.id == loginOrgLocId}">   --%>
																		<option value="${report.id}"
																			${report.id  == requirement.clientSubmissionTemplateId.id  ? 'selected' : ''}>${report.templateName}</option>
																		<%--    </c:if>   --%>
																	</c:forEach>
																</select>
															</c:otherwise>
														</c:choose>
													</div>
												</div>
											</div>
											<c:if test="${!empty sourceleadSectionList}">
												<c:forEach var="section" items="${sourceleadSectionList}"
													varStatus="sectionOrder">
													<c:if test="${sectionOrder.index == 1}">
														<div id="customDiv${section.id}" name="fieldVisible"
															data-visible="${section.isDefaultVisible}"></div>
													</c:if>
												</c:forEach>
											</c:if>
											
											<div class="form-group">
												<label class="col-md-3">Job Rating</label>
												<div class="col-md-9">
													<div>
														<input type="text" class="form-control"
															placeholder="Job Rating" id="jobRate" name="jobRate"
															data-validation="validate_Space validate_float validate_length length1-10"
															data-validation-optional="true" field-name="Job Rating"
															value="${requirement.jobRate}">
													</div>
												</div>
											</div>
											
											<div class="form-group">
												<label class="col-md-3">Incentivized</label>
												<div class="col-md-9 toggle-button">
													<label class="switch"> <input type="checkbox"
														${requirement.incentivized == 'Y' ? 'checked' : ''}
														id="incentivized" name="incentivized"
														value="${!empty requirement.incentivized ? requirement.incentivized : 'N'}"
														class="nofJoinersDIv">
														<div class="slider round"></div>
													</label>
												</div>
											</div>
											<div class="form-group" id="nofJoiners"
												${requirement.incentivized == 'Y' ? '' : 'style="display:none;"'}>
												<label class="col-md-3">No. of Joiners <span
													class="text-danger paddingLeft">*</span></label>

												<c:if test="${!empty requirementIncentiveList}">
													<c:forEach var="inc" items="${requirementIncentiveList}"
														varStatus="rowCount">

														<div
															class="col-md-9 ${rowCount.index > 0 ? 'col-md-offset-3 mt' : ''}"
															id="nofJoinersDiv[${rowCount.index}].rowId">
															<div class="row">
																<div class="col-md-3">
																	<input type="text" class="form-control"
																		id="requirementIncentive[${rowCount.index}].joiners"
																		name="requirementIncentive[${rowCount.index}].joiners"
																		value="${inc.joiners}" placeholder="Joiners"
																		field-name="Joiners"
																		data-validation="required validate_Space validate_length length1-5 validate_int">
																</div>
																<div class="col-md-9">
																	<div class="row">
																		<div class="col-md-4 padding_right">
																			<div class="btn-group">
																				<button type="button"
																					class="btn ${!empty inc.joinersAmountType && inc.joinersAmountType == 'flat' ? 'btn-primary' : !empty inc.joinersAmountType && inc.joinersAmountType == 'percentage' ? 'btn-default' : 'btn-primary'}"
																					id="requirementIncentive[${rowCount.index}].flatAmount"
																					value="flat" onClick="getJoinersAmountType(this)">Flat</button>
																				<button type="button"
																					class="btn ${!empty inc.joinersAmountType && inc.joinersAmountType == 'percentage' ? 'btn-primary' : 'btn-default'}"
																					id="requirementIncentive[${rowCount.index}].percentageAmount"
																					value="percentage"
																					onClick="getJoinersAmountType(this)">%</button>
																				<input type="hidden"
																					id="requirementIncentive[${rowCount.index}].joinersAmountType"
																					name="requirementIncentive[${rowCount.index}].joinersAmountType"
																					value="${inc.joinersAmountType}"> <input
																					type="hidden"
																					id="requirementIncentive[${rowCount.index}].id"
																					name="requirementIncentive[${rowCount.index}].id"
																					value="${inc.id}">
																			</div>
																		</div>
																		<div class="col-md-6 paddingLeft paddingRight">
																			<input type="text" class="form-control"
																				value="${inc.joinersAmount}"
																				id="requirementIncentive[${rowCount.index}].joinersAmount"
																				name="requirementIncentive[${rowCount.index}].joinersAmount"
																				placeholder="Amount" field-name="Amount"
																				data-validation="required validate_Space validate_length ${!empty inc.joinersAmountType && inc.joinersAmountType == 'percentage' ? 'length1-2' : 'length1-10'} validate_int">
																		</div>
																		<div class="col-md-1 text-center mt10">
																			<c:if test="${rowCount.index == 0}">
																				<label><i
																					class="fa fa-plus paddingRight cursorPointer"
																					onClick="addJoinersRow();"></i></label>
																			</c:if>
																			<c:if test="${rowCount.index > 0}">
																				<i class="fa fa-times colorRed cursorPointer"
																					onclick="removeJoinersRow(${rowCount.index}); getDeletedIds(${inc.id})"></i>
																			</c:if>
																		</div>
																	</div>
																</div>
															</div>
															<div class="clearfix"></div>
														</div>
													</c:forEach>
												</c:if>

												<c:if test="${empty requirementIncentiveList}">

													<div class="col-md-9" id="nofJoinersDiv[0].rowId">
														<div class="row">
															<div class="col-md-3">
																<input type="text" class="form-control"
																	id="requirementIncentive[0].joiners"
																	name="requirementIncentive[0].joiners" value=""
																	placeholder="Joiners" field-name="Joiners"
																	data-validation="required validate_Space validate_length length1-5 validate_int"
																	data-validation-optional="true">
															</div>
															<div class="col-md-9">
																<div class="row">
																	<div class="col-md-4 padding_right">
																		<div class="btn-group">
																			<button type="button" class="btn btn-primary"
																				id="requirementIncentive[0].flatAmount" value="flat"
																				onClick="getJoinersAmountType(this)">Flat</button>
																			<button type="button" class="btn btn-default"
																				id="requirementIncentive[0].percentageAmount"
																				value="percentage"
																				onClick="getJoinersAmountType(this)">%</button>
																			<input type="hidden"
																				id="requirementIncentive[0].joinersAmountType"
																				name="requirementIncentive[0].joinersAmountType"
																				value="flat">
																		</div>
																	</div>
																	<div class="col-md-6 paddingLeft paddingRight">
																		<input type="text" class="form-control" value=""
																			id="requirementIncentive[0].joinersAmount"
																			name="requirementIncentive[0].joinersAmount"
																			placeholder="Amount" field-name="Amount"
																			data-validation="required validate_Space validate_length length1-10 validate_int"
																			data-validation-optional="true">
																	</div>
																	<div class="col-md-1 text-center mt10">
																		<label><i
																			class="fa fa-plus paddingRight cursorPointer"
																			onClick="addJoinersRow();"></i></label>
																	</div>
																</div>
															</div>
														</div>
														<div class="clearfix"></div>
													</div>
												</c:if>
												<div id="nofJoinersAddRow" class="clearfix mt">

													<input type="hidden" id="joinersCount"
														value="${requirementIncentiveListSize}">
												</div>

											</div>
											<div class="form-group">
												<label class="col-md-3">Pay out Period (Days)</label>
												<div class="col-md-9">
													<input type="text" value="${requirement.payOutPeriod}"
														id="payOutPeriod" name="payOutPeriod" class="form-control"
														data-validation="validate_Space validate_length length1-3 validate_int"
														data-validation-optional="true">
												</div>
											</div>
											<input type="hidden" id="arrDeletedIds" name="arrDeletedIds"
												value="">
										</div>
									</div>
								</div>
							</div>
						</div>


						<!-- Locations -->

						<div class="clearfix">
							<div class="col-lg-12">
								<h3>Locations</h3>
							</div>
							<div class="col-md-6">
								<label class="col-md-12"> City, State, Country</label>
								<div class="clearfix"></div>
								<div id="timesheetDocumentsId"
									class="timesheetDocs timesheetDocs-custom jobdocsCustom">
									<div
										class="col-md-8 timesheet_week_comm onboardDocs width100percnt">
										<span class="uploadAttachments">
											<div id="jobLocationsList"></div>
										</span>
									</div>
								</div>

							</div>
						</div>

						<!-- Internal Details html -->
						<div class="clearfix">
							<div class="col-lg-12">
								<h3 class="margin-top0">Internal Details</h3>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-12 col-lg-12  myForm">

								<!-- Approver Required Check box html -->
								<div class="clearfix mb">
									<div class="ml">

										<div class="pull-left">

											<c:if
												test="${requirement.requirementSubmissionStatus == 'OPEN' ||  requirement.requirementSubmissionStatus == 'ONHOLD'}">
												<input type="checkbox" name="cd" id="apprverReq" readonly
													checked>
											</c:if>

											<c:if
												test="${requirement.requirementSubmissionStatus != 'OPEN'  &&  requirement.requirementSubmissionStatus != 'ONHOLD'}">
												<input type="checkbox" name="cd" id="apprverReq"
													onClick="approvalType('Default');removeApprovers();"
													checked>
											</c:if>
											<label>&nbsp;&nbsp;<strong>Approver Required</strong></label>
										</div>

										<!-- Radio buttons for Sequential or Parallel function -->
										<div class="ml pull-left apprverReq" style="display: none;">
											<label class="rad" id="parallel"> <input type="radio"
												name="approver" id="parallelapprovers" checked="checked"
												onClick="approvalType('Parallel');"> <i
												class="fa fa-align-center" title="Parallel"></i>
											</label> <label class="rad" id="sequential"> <input
												type="radio" name="approver" id="seqentialApprovers"
												onClick="approvalType('Sequential');"> <i
												class="fa fa-retweet" title="Sequential"></i>
											</label>

											<div id="navbar"
												class="glyphicon glyphicon-info-sign text-info ml"></div>
										</div>
										<!-- Radio buttons for Sequential or Parallel function -->

									</div>
								</div>

								<div class="form-group parallel" style="display: none;">
									<label class="col-md-3">Approver<span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-md-9">
										<select multiple class="chosen-select form-control"
											id="parallelApprover" name="parallelApprover"
											field-name="Approver" data-validation="required"
											data-validation-optional="true"
											data-placeholder="Select Approver">
											<c:if
												test="${requirement.requirementSubmissionStatus == 'OPEN' ||  requirement.requirementSubmissionStatus == 'ONHOLD'}">
												<option disabled>Select Approver</option>
												<c:forEach var="report" items="${approverList}">
													<option value="${report.id}" disabled="disabled">${report.firstName}&nbsp;${report.lastName}</option>
												</c:forEach>
											</c:if>
											<c:if
												test="${requirement.requirementSubmissionStatus != 'OPEN' &&  requirement.requirementSubmissionStatus != 'ONHOLD'}">
												<option disabled>Select Approver</option>
												<c:forEach var="report" items="${approverList}">
													<option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>


								<div class="form-group sequential" id="addLvl"
									style="display: none;">
									<label class="col-md-3">Approver1<span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-md-9">

										<c:if
											test="${requirement.requirementSubmissionStatus == 'OPEN' ||  requirement.requirementSubmissionStatus == 'ONHOLD'}">
											<select disabled="disabled"
												class="chosen-select form-control" id="sequentialApprover1"
												name="sequentialApprover1" field-name="Approver"
												data-validation="required" data-validation-optional="true"
												onchange="getLevel2Approvers();">
												<option value="">Select Approver1</option>
												<c:forEach var="report" items="${approverList}">
													<option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if
											test="${requirement.requirementSubmissionStatus != 'OPEN' &&  requirement.requirementSubmissionStatus != 'ONHOLD'}">
											<select class="chosen-select form-control"
												id="sequentialApprover1" name="sequentialApprover1"
												field-name="Approver" data-validation="required"
												data-validation-optional="true"
												onchange="getLevel2Approvers();">
												<option value="">Select Approver1</option>
												<c:forEach var="report" items="${approverList}">
													<option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>
												</c:forEach>
											</select>
										</c:if>
										<!-- <span onClick="addLvl();"><i class="fa fa-level-down btn btn-default formic1" data-toggle="tooltip" data-placement="left" title="Add Level"></i></span> -->
									</div>

									<label class="col-md-3 mt">Approver2<span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-md-9 mt">

										<c:if
											test="${requirement.requirementSubmissionStatus == 'OPEN' ||  requirement.requirementSubmissionStatus == 'ONHOLD'}">
											<select disabled="disabled"
												class="chosen-select form-control" id="sequentialApprover2"
												name="sequentialApprover2" field-name="Approver 2"
												data-validation="required" data-validation-optional="true">
												<option value="">Select Approver2</option>
												<c:forEach var="report" items="${approverList}">
													<option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if
											test="${requirement.requirementSubmissionStatus != 'OPEN' &&  requirement.requirementSubmissionStatus != 'ONHOLD'}">
											<select class="chosen-select form-control"
												id="sequentialApprover2" name="sequentialApprover2"
												field-name="Approver 2" data-validation="required"
												data-validation-optional="true">
												<option value="">Select Approver2</option>
												<c:forEach var="report" items="${approverList}">
													<option value="${report.id}">${report.firstName}&nbsp;${report.lastName}</option>
												</c:forEach>
											</select>
										</c:if>

									</div>

								</div>

								<div class="form-group">
									<label class="col-md-3">Manager<span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-md-9">

										<select class="chosen-select form-control"
											data-validation="required" field-name="Manager"
											name="manager" id="managerId"
											onchange="checkManagerAndPrimaryRecruiterSelection(this);">

											<c:if
												test="${SourceLeadWeb_prodSupportLoginUserRole == null}">
												<option selected="selected" value="${SourceLeadWeb_user.id}">${SourceLeadWeb_user.firstName}&nbsp;${SourceLeadWeb_user.lastName}</option>
											</c:if>

											<c:forEach var="report" items="${approverList}">
												<c:if test="${SourceLeadWeb_user.id != report.id}">
													<option value="${report.id}"
														${report.id  == requirement.managerUserId.id ? 'selected' : ''}>${report.firstName}&nbsp;${report.lastName}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Primary Recruiter</label>
									<div class="col-md-9">
										<c:choose>
											<c:when
												test="${loginUser.id == requirement.primaryRecruiterUserId.id}">
												<select style="display: none;"
													class="chosen-select form-control"
													name="primaryRecruiterUserId" id="primaryRecruiterUserId">
													<option value="${requirement.primaryRecruiterUserId.id}">${requirement.primaryRecruiterUserId.fullName}</option>
												</select>
											</c:when>
											<c:otherwise>
												<select class="chosen-select form-control"
													name="primaryRecruiterUserId" id="primaryRecruiterUserId"
													onchange="checkManagerAndPrimaryRecruiterSelection(this);">
													<option value="0">Select Primary Recruiter</option>
													<c:forEach var="report" items="${approverList}">
														<option value="${report.id}"
															${report.id == requirement.primaryRecruiterUserId.id ? 'selected' : ''}>${report.firstName}&nbsp;${report.lastName}</option>
													</c:forEach>
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-3">Priority<span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-md-9">
										<select class="selectpicker" id="priority" name="priority"
											data-validation="required" field-name="priority">
											<option
												${requirement.priority  == 'Medium' ? 'selected' : ''}>Medium</option>
											<option ${requirement.priority  == 'Low' ? 'selected' : ''}>Low</option>
											<option
												${requirement.priority  == 'Urgent' ? 'selected' : ''}>Urgent</option>
											<option ${requirement.priority  == 'High' ? 'selected' : ''}>High</option>
										</select>
									</div>
								</div>
								<!-- added for workflow integration for job start here -->   
								<div class="form-group">
									<label class="col-md-3">Workflow<span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-md-9">
										<select class="chosen-select form-control"
											data-validation="required" field-name="Workflow"
											name="workflow" id="workflowId">
											<c:forEach var="workflow" items="${workflowList}">
												<c:choose>
											       <c:when test="${empty requirement.workflowId.id}"><!-- for new requirement below optios are generated. -->
													<option value="${workflow.id}"
														${workflow.configureType=='Default'? 'selected' : ''}>${workflow.workFlowName}</option>
												   </c:when>
												   <c:otherwise>
												   		<option value="${workflow.id}"
														${workflow.id==requirement.workflowId.id ? 'selected' : ''}>${workflow.workFlowName}</option>
												   </c:otherwise>
													</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<!--  added for workflow integration for job end here -->
								<div class="form-group" id="internalSubmissionFrom">
									<label class="col-md-3">Application Form<span
										class="text-danger paddingLeft">* </span><i
										class='fa fa-info-circle'
										title='The applicant provides the required information for the job application'></i></label>
									<div class="col-md-9">

										<select class="chosen-select form-control select-bg"
											name="applicationFrom" id="applicationFrom"
											data-validation="required" field-name="Application Form">
											<c:forEach var="report" items="${submissionTempletesList}">
												<option value="${report.id}"
													${report.id  == requirement.submissionTemplateId.id ? 'selected' : ''}>${report.templateName}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group">

									<!-- <div id="assignedToSavingDiv"></div>
            <div id="defaultAssignedToSavingDiv"></div> -->

									<label class="col-md-3">Assign To <i
										class='fa fa-info-circle'
										title='Assign the job to the internal team to process the resources for the job'></i></label>

									<div class="col-md-9">
										<div class="form-icon">
											<c:if test="${empty requirement.id}">
												<a href="javascript:void(0)" data-toggle="modal"
													data-target="#myModal" onclick="getTeamsList('');">Assigned
													Members</a>
												<button type="button"
													class="btn btn-default btn-sm ml10 btnShowlist"
													id="showGrid" disabled>
													<span id="sl">Show List</span><span
														class="badge ml10 showGrid">0</span>
												</button>
											</c:if>
											<span data-target="#addTeam" data-toggle="modal"
												title="Add Team" class="btn btn-default formic1"
												onclick="flushStoreTeam();getTeamOwners();"> <i
												class="fa fa-plus paddingRight"></i>Add Team
											</span>
										</div>
									</div>
									<input type="hidden" name="assignedTeamId" id="assignedTeamId"
										value="" />
								</div>
								<div class="col-md-12 assigned-Table">
									<div class="assignTableScroll" id="assignedTeamsOrUsers"
										style="display: none"></div>
								</div>

								<div class="clearfix"></div>
								<div class="form-group">
									<div class="ml">
										<c:set var="val" value="${requirement.releaseJobToAllUsers}" />
										<c:choose>
											<c:when test="${val == 'Y'}">
												<input disabled="disabled" type="checkbox" checked
													name="releaseToAllUsers" id="releaseToAllUsers" value="Y">
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="releaseToAllUsers"
													id="releaseToAllUsers" value="Y">
											</c:otherwise>
										</c:choose>
										<label>&nbsp;&nbsp;<strong>Assign to all <i
												class='fa fa-info-circle'
												title='Job will be made visible to all users within the organization. Users will be able to add the applicants for the job'></i></strong>
											&nbsp;&nbsp;
										</label>
									</div>
								</div>
								<c:if test="${!empty sourceleadSectionList}">
									<c:forEach var="section" items="${sourceleadSectionList}"
										varStatus="sectionOrder">
										<c:if test="${sectionOrder.index == 2}">
											<div id="customDiv${section.id}"></div>
										</c:if>
									</c:forEach>
								</c:if>
							</div>
						</div>
						<!-- Internal Details html Ends -->


					</div>
			</div>
			<%--  <input type="hidden" value="${requirement.id}" name ="requirementId"  id ="requirementId"> --%>
			<div class="form-footer clearfix">
				<div class="col-lg-12">
					<div class="pull-right">
						<c:if test="${ requirement.requirementSubmissionStatus != 'OPEN'}">
							<input type="button" value="Submit for Approval"
								id="submitForApproval"
								onclick="storeRequirement('submitForApproval');"
								class="btn btn-primary" style="display: none;">
						</c:if>

						<c:if
							test="${empty requirement.requirementSubmissionStatus || requirement.requirementSubmissionStatus == 'DRAFT' || requirement.requirementSubmissionStatus == 'REJECTED'}">
							<input type="button" value="Draft" id="draft"
								onclick="storeRequirement('draft'); " class="btn btn-danger ml">
						</c:if>
						<c:if test="${requirement.requirementSubmissionStatus == 'OPEN'}">
							<!-- <input type="button" value="Save" id="save" onclick="storeRequirement('save');" class="btn btn-primary ml" >  -->
							<input type="button" value="Save & Continue" id="save"
								onclick="storeRequirement('saveAndContinue');"
								class="btn btn-primary ml">
						</c:if>
						<c:if test="${requirement.requirementSubmissionStatus != 'OPEN'}">
							<!-- <input type="button" value="Save" id="save" onclick="storeRequirement('save');" class="btn btn-primary ml" >  -->
							<input type="button" value="Save & Continue" id="save"
								onclick="storeRequirement('saveAndContinue');"
								class="btn btn-primary ml">
						</c:if>


						<input type="button" value="Cancel"
							onClick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/jobsList'"
							class="btn btn-default ml">
					</div>
				</div>
			</div>
			</form:form>

		</div>
	</div>
	<!-- Content Area Ends-->
	<div id="addContact" class="modal fade" role="dialog"></div>

	<!-- Modal content Demo Popup: Need to Update -->
	<div role="dialog" id="addTeam" class="modal fade">
		<div class="modal-dialog">
			<form role="form" id="team_form" name="team_form" method="post">
				<div class="modal-content" id="assignTeamDiv">
					<div class="modal-header">
						<button data-dismiss="modal" class="close" type="button"><i class="fa fa-times"></i></button>
						<h4 class="modal-title">Add Team</h4>
					</div>
					<div class="modal-body clearfix">
						<div class="form-horizontal custom-form clearfix">
							<div class="col-xs-12 col-md-12 col-lg-12">
								<div class="form-group">
									<label class="col-lg-4">Team Name <span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-lg-8" id="teamNameDiv">
										<!-- <input type="text" class="form-control" value=""> -->
										<input type="text" class="form-control"
											placeholder="Team Name" id="nameOfCandidate" name="name"
											data-validation="required validate_Space validate_AlphaNumaric validate_Name validate_length length2-100"
											field-name="Team Name" value="" onblur="validateTeamName();">
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-4">Team Lead <span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-lg-8 select-field">
										<select class="form-control" name="ownerUserId"
											id="ownerUserId" placeholder="Team Lead *"
											data-validation="required validate_Space"
											field-name="Team Lead" onchange="getTeamMembers();">

										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-lg-4">Team Members <span
										class="text-danger paddingLeft">*</span></label>
									<div class="col-lg-8">
										<select multiple class="chosen-select form-control select-bg"
											name="memberUserId" id="memberUserId"
											data-placeholder="Select Team Member"
											data-validation="required validate_Space"
											field-name="Team Member">

										</select>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer mt">
						<button class="btn btn-primary" type="button" id="add_team"
							onclick="storeTeam();">Save</button>
						<button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- Modal content Demo Popup: Need to Update ends -->

	</div>
</section>

<div id="divLocTd1" style="display: none; float: left;">
	<div class='positionRelative' id="locationErrMsgREPLACEROWID">
		 <input type="text" placeholder="Location"
			onclick="addLocationAuto('REPLACEROWIDaddlocation','REPLACEROWID','REPLACEROWIDlongitude','REPLACEROWIDlatitude')"
			class="form-control joblocinput" id="REPLACEROWIDaddlocation"
			name="locationInfo[REPLACEROWID].locationName"
			data-validation="required" field-name="Location"> 
		<div class="fa fa-refresh btn btn-primary refresh-icon" title="Clear"
			onClick="refreshLocation('REPLACEROWID');"></div>
		 <input type="hidden" name="locationInfo[REPLACEROWID].country"
			id="REPLACEROWIDcountry"> <input type="hidden"
			name="locationInfo[REPLACEROWID].state" id="REPLACEROWIDstate"> 
		<input type="hidden" name="locationInfo[REPLACEROWID].city"
			id="REPLACEROWIDcity"> <input type="hidden"
			name="locationInfo[REPLACEROWID].zipcode" id="REPLACEROWIDzipcode">
		<input type="hidden" name="locationInfo[REPLACEROWID].longitude"
			id="REPLACEROWIDlongitude"> <input type="hidden"
			name="locationInfo[REPLACEROWID].latitude" id="REPLACEROWIDlatitude">
	</div>
</div>

<div id="divLocTd2" style="display: none; float: left;">
	<i class="fa fa-times" onclick="removeLocationRow(REPLACEROWID);"></i>
</div>

<script>
function validateProfilePercentage(){
	var profilePercentage = $('#profilePercentage').val();
	
	 if(profilePercentage > 100 || profilePercentage < 0){
		 if(profilePercentage < 0){
			    $('#profilePercentageValidateId').find('p.jquery_form_error_message').remove(); 
				$('input#profilePercentageValidation').attr('class','error signin_fild');
				$("input#profilePercentageValidation").after("<p class='jquery_form_error_message'>Profile percentage should not in negitive</p>");
				$('input#profilePercentageValidation').attr('record-exist','yes');
				$('input#profilePercentageValidation').attr('record-exist-errorMsg','Profile percentage should not in negitive'); 
		 }else{
			 $('#profilePercentageValidateId').find('p.jquery_form_error_message').remove(); 
				$('input#profilePercentageValidation').attr('class','error signin_fild');
				$("input#profilePercentageValidation").after("<p class='jquery_form_error_message'>Profile percentage should not more than 100</p>");
				$('input#profilePercentageValidation').attr('record-exist','yes');
				$('input#profilePercentageValidation').attr('record-exist-errorMsg','Profile percentage should not more than 100'); 
		 }
	 }else{
		 
	     $('#profilePercentageValidateId').find('p.jquery_form_error_message').remove(); 
    	 $('input#profilePercentageValidation').removeAttr( "class record-exist record-exist-errormsg");
		 $('#profilePercentageValidateId').find('p.jquery_form_error_message').remove();
	 }
}
</script>

<script>
	var orgLocationDateFormat = "${orgLocationDateFormat}";
	  var locationDate ='${SourceLeadWeb_dateFormat}'; 
	  var jsDateFormat= '';
	  if(locationDate.match("yyyy-MM-dd")){
		  jsDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  jsDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  jsDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  jsDateFormat = 'yy-dd-mm'; 
	  }
	  $('#closedDate').datepicker({
	      dateFormat: jsDateFormat,
	      changeMonth: true,
	  	  changeYear: true,
	  	  minDate: 1,
	  	onSelect : function(selected) {
	  		  document.getElementById("receivingApplicantsDiv").style.display="block";
	  	}
	  });
	  
	function initializeDatePicker() {
		$('.jobFairCalendar').each(function () {
			var datePickerDateFormat = '';
			var dateFormat = $(this).data("date_format");
			var startDate = $(this).data("start_date");
			var endDate = $(this).data("end_date");
			var stDate = new Date(startDate);
			var enDate = new Date(endDate);
/* 			var finalStartDate = "";
			var finalEndDate = "";
			if(dateFormat.match("yyyy-MM-dd")) {
				finalStartDate = startDate.split("-")[0] + "-" + startDate.split("-")[1] + "-" + startDate.split("-")[2];
				finalEndDate = endDate.split("-")[0] + "-" + endDate.split("-")[1] + "-" + endDate.split("-")[2];
			} if(dateFormat.match("dd-MM-yyyy")) {
				finalStartDate = startDate.split("-")[2] + "-" + startDate.split("-")[1] + "-" + startDate.split("-")[0];
				finalEndDate = endDate.split("-")[2] + "-" + endDate.split("-")[1] + "-" + endDate.split("-")[0];
			} if(dateFormat.match("MM-dd-yyyy")) {
				finalStartDate = startDate.split("-")[2] + "-" + startDate.split("-")[0] + "-" + startDate.split("-")[1];
				finalEndDate = endDate.split("-")[2] + "-" + endDate.split("-")[0] + "-" + endDate.split("-")[1];
			} if(dateFormat.match("yyyy-dd-MM")) {
				finalStartDate = startDate.split("-")[0] + "-" + startDate.split("-")[2] + "-" + startDate.split("-")[1];
				finalEndDate = endDate.split("-")[0] + "-" + endDate.split("-")[2] + "-" + endDate.split("-")[1];
			} */
			$(this).removeClass('hasDatepicker').datepicker({
				dateFormat : jsDateFormat,
				changeMonth : true,
				changeYear : true,
				minDate : startDate,
				maxDate : endDate,
				onSelect : function(selected) {
					var parentDiv = $(this).parents(".multiJobMain").find(".multiJobDate");
					parentDiv.find("div").remove();
					if(parentDiv.find("span[data-value='" + $(this).val() + "']").length === 0) {
						var text = "<span class='reqDate' data-value='" + $(this).val() + "'>" + $(this).val() + "<i class='fa fa-times' onclick='removeJobFairDate($(this))'></i>";
						text += "<input value=" + $(this).data("job_fair_id") + ":" + $(this).val() + " name='jobFairDates[0]' type='hidden'>";
						text += "</span>";
						parentDiv.append(text);
						updateJobFairDatesIndex();
					}
				}
			});
		});
	}
	var requirementId = "";
	$(document).ready(function () {
		requirementId = "${requirement.id}";
		if(${requirement.jobFairList != null && requirement.jobFairList.size() > 0})
			initializeDatePicker();
		
		getJobFairList();
		
		$(document).on('change', '#jobFairList', function(evt, params) {
			if (params.selected && params.selected != undefined) {
				addOrRemoveSelectedJobFair(params.selected, false);
			}
			if (params.deselected && params.deselected != undefined) {
				addOrRemoveSelectedJobFair(params.deselected, true);
			}
		});
	});
</script>

<script type="text/javascript">
	function checkManagerAndPrimaryRecruiterSelection(ele) {
		if($("#managerId").val() === $("#primaryRecruiterUserId").val()){
			$(ele).parent().append("<p class='jquery_form_error_message'>Manager & Primary Recruiter can't be same</p>");
		} else {
			$("#managerId").parent().find("p").remove();
			$("#primaryRecruiterUserId").parent().find("p").remove();
		}
	}
	function getSavedJobFairsForRequirement() {
		if(requirementId > 0) {
			$.ajax({
			   	type: "POST",
			    url: atsUrl+"/jobFairsByRequirementId?requirementId="+requirementId, 	   
				    success: function(result) {
				    	if(result != null && result != 'undefined' && result !='' ){
				    		if (result.response === "SESSION_TIMEDOUT") {
								location.replace(sourceleadUrl);
							} else if (result.response === "AWKWARD") {
								location.href = "/errorPage";
							} else if (result.response === "SUCCESS") {
								var data = result.data;
					    		var text = "";
					    		for(var e in data) {
					    			var index = 0;
					    			text += "<div class='multiJobMain' data-id='" + data[e].id + "' name='jobFairMap[0].key'>";
					    			text += "	<div>" + data[e].name + "</div>";										
					    			text += "	<div class='multiJobTitle clearfix'>";
					    			text += "		<h2 class='pull-left'>Fair runs from " + data[e].startDate + " to " + data[e].endDate + "</h2>";
					    			text += "		<span class='pull-right'>";
					    			/* text += "			<input class='form-control calendar jobFairCalendar calendarIcon' readonly='readonly' type='text' data-date_format='dd-MM-yyyy' data-start_date='28-07-2018' data-end_date='05-08-2018' data-job_fair_id=2>"; */
					    			text += "			<input class='form-control calendar jobFairCalendar calendarIcon' readonly='readonly' type='text' data-date_format='" + data[e].dateFormat + "' data-start_date='" + data[e].startDateStr + "' data-end_date='" + data[e].endDateStr + "' data-job_fair_id=" + data[e].id + ">";
					    			text += "		</span>";
					    			text += "	</div>";

					    			text += "	<div class='multiJobDate clearfix'>";
					    			for(var e1 in data[e].jobFairRequirementList) {
					    				if(data[e].jobFairRequirementList[e1].requirementId == requirementId) {
					    					if(data[e].jobFairRequirementList[e1].fairDates != null && data[e].jobFairRequirementList[e1].fairDates != '') {
					    						var fairDates = data[e].jobFairRequirementList[e1].fairDates.split(",");
					    						for(var d in fairDates) {					    							
						    						text += "<span class='reqDate' data-value='" + fairDates[d] + "'>" + fairDates[d] + "<i class='fa fa-times' onclick='removeJobFairDate($(this))'></i>";
						    						text += "<input value='" + data[e].id + ":" + fairDates[d] + "' name='jobFairDates[" + (index++) + "]' type='hidden'>";
						    						text += "</span>";	
					    						}
					    					} else {
					    						text += "<div class='reqDate'><input value='" + data[e].id + "' name='jobFairDates[0]' type='hidden'>";
					    						text += "	Select optional JobFair dates(s)</div>";
					    					}
					    				}
					    			}
					    			text += "	</div>";
					    			text += "</div>";
								}
					            $("#jobFairDatesDiv").empty();
					            $("#jobFairDatesDiv").append(text);
					            /* $("#jobFairList").trigger("chosen:updated"); */
					            /* $("#jobFairDatesDiv").hide(); */
					            initializeDatePicker();
							} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
								showError("Operation failed, please try again");
							}
				    	} 
				    }
				});
		} else {
			$("#jobFairDatesDiv").empty();
		}
	}
	
	function getJobFairList() {
		var selectedJobFairs = "${selectedJobFairsString}";
		var requirementId = "${requirement.id}";
		$.ajax({
		   	type: "POST",
		    url: atsUrl+"/searchJobFairs", 	   
			    success: function(result) {
			    	if(result != null && result != 'undefined' && result !='' ){
			    		if (result.response === "SESSION_TIMEDOUT") {
							location.replace(sourceleadUrl);
						} else if (result.response === "AWKWARD") {
							location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
							var data = result.data;
				    		var output = "";
				    		/* output = output + "<option value=''>--Select JobFair--</option>"; */ 
				    		for(var e in data) {
				    			var selected = false;
				    			if(selectedJobFairs && selectedJobFairs != "") {
				    				for(var i in selectedJobFairs.split(",")) {
										if(parseInt((selectedJobFairs.split(",")[i]).split(":")[0]) === data[e].id) {
						    				selected = true;
										}												    					
				    				}
				    			}
				        		output = output + "<option " + (selected === true ? "selected='selected'":"") + " value='" + data[e].id + "' data-date_format=" + data[e].dateFormat + " data-start_date=" + data[e].startDate + " data-end_date=" + data[e].endDate + ">" + data[e].name + "</option>";
							}
				            $("#jobFairList").empty();
				            $("#jobFairList").append(output);
				            $("#jobFairList").trigger("chosen:updated");
				            /* $("#jobFairDatesDiv").hide(); */
						} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
							showError("Operation failed, please try again");
						}
			    	} 
			    }
			});
	}
	
	$("#isJobFair").change(function(){
		if($(this).is(":checked")) {
			/* getJobFairList(); */
			$("#jobFairList").attr("data-validation","required");
			$("#selectJobFairDiv").show();
		} else {
			$("#jobFairList").removeAttr("data-validation");
			$("#selectJobFairDiv").hide();
			/* $("#jobFairList").empty(); */
			/* $("#jobFairDatesDiv").hide(); */
		}
	});
	var index = 0;
	var datesCount = "${datesCount}";
	
	function updateJobFairDatesIndex() {
		var i = 0;
		$(".reqDate").each(function() {
			$(this).find("input").attr("name","jobFairDates[" + (i++) + "]");
		});
		datesCount = i;
	}
	
	function addOrRemoveSelectedJobFair(id, removedFlag) {
		if(removedFlag) {
			$(".multiJobMain[data-id=" + id + "]").remove();		
		} else {
			$("#jobFairList option").each(function () {
				if($(this).val() === id) {					
					var text = "<div class='multiJobMain' name='jobFairMap[0].key' data-id=" + id + ">";
					/* text += "		<i class='fa fa-times' onclick='removeJobFair($(this))'></i>"; */
					text += "			<div>" + $(this).text() + " </div>";	
					text += "			<div class='multiJobTitle clearfix'>";
					text += "				<h2 class='pull-left'>Fair runs from " + $(this).data("start_date") + " to " + $(this).data("end_date") + "</h2>";
					text += "				<span class='pull-right'>";
					text += "					<input class='form-control calendar jobFairCalendar calendarIcon' readonly='readonly' type='text' data-date_format='" + $(this).data("date_format") + "' data-start_date='" + $(this).data("start_date") + "' data-end_date='" + $(this).data("end_date") + "' data-job_fair_id=" + id + ">";
					text += "				</span>";
					text += "			</div>";
			
					text += "			<div class='multiJobDate clearfix'>";
					text += "				<div class='reqDate'><input value='" + id + "' name='jobFairDates[" + (datesCount++) + "]' type='hidden'>Select optional JobFair date(s)</div>";
					text += "			</div>";
					text += "		</div>";
					
					$("#jobFairDatesDiv").append(text);
				}
			});
			initializeDatePicker();
		}
		updateJobFairDatesIndex();
	}
	
	function removeJobFairDate(ele) {
		var parentDiv = $(ele).parents(".multiJobDate");
		$(ele).parent().remove();
		updateJobFairDatesIndex();
		if(parentDiv.find("span").length > 0) {
			/* parentDiv.find("div").remove();
			var i = 0;
			$(parentDiv.find("span")).each(function () {
				$(this).find("input").attr("name","jobFairDates[" + (i++) + "]")	
			});
			index = i; */
		} else {
			parentDiv.append("<div class='reqDate'><input value='" + $(parentDiv).parents(".multiJobMain").data("id") + "' name='jobFairDates[0]' type='hidden'>Select optional JobFair dates(s)</div>");
		}
	}
	
	function updateSelectedJobRows() {
		
	}
	
	function addNewJobFair() {
		window.open(atsUrl + "/addJobFair", "_blank");
	}
</script>

<script>
function changeValidationType() {
	var isVisible	= $("div[name=fieldVisible]").attr("data-visible");
	if(isVisible == 'N'){
		$("div[name=fieldVisible] input").each(function() {
			if($(this).attr('data-check') && ($(this).attr('data-check') === true || $(this).attr('data-check') === "true")){
				if($(this).attr('data-validation') && $(this).attr('data-validation').indexOf("required") !== -1) {
					$(this).attr('data-validation-optional', 'true');
					$(this).attr('data-valid1', $(this).attr('data-validation'))
					$(this).removeAttr('data-validation');
				} else if($(this).attr('data-validation-optional')) {
					$(this).attr('data-validation', $(this).attr('data-valid1'));
					$(this).removeAttr('data-valid1');
					$(this).removeAttr('data-validation-optional');
				}
			}
		});
	}
}
</script>

<script>
function getCustomDependentField(sectionId){
	var requirementId = '${requirement.id}';
	 $.ajax({
			type : "POST" , 
				 url: atsUrl+"/getAllASectionCustomFields?sectionId="+sectionId+"&requirementId="+requirementId+"&requestFrom=requirement",
				 success: function(data) {
		        	$('#customDiv'+sectionId).empty();
		        	$('#customDiv'+sectionId).append(data);
		        	changeValidationType();
				 }
		  });
}
</script>

<script>
var selectedFile = document.getElementById("uploadBtn");
selectedFile.addEventListener("change", function() {
	 $("#uploadFile").focus();
	 if(validate_fileupload(this.value)){
		 var docSize =  $("#uploadBtn")[0].files[0].size;
		  $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("uploadFile").value = '';
	    	  $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
			  $("input#uploadFile").after("<p class='jquery_form_error_message'>File size should be less then 2MB </span>");
			  document.getElementById("uploadFile").setAttribute('record-exist','yes');
			  document.getElementById("uploadFile").setAttribute('record-exist-errorMsg','File size should be less then 2MB');
			  document.getElementById("uploadFile").value="";
	      }else{
	    	 $('input#uploadFile').removeAttr( "record-exist record-exist-errormsg");
  		 $('#uploadFileGroup').find('p.jquery_form_error_message').remove(); 
	    }
	 }
});

/* upload file script */
document.getElementById("uploadBtn").onchange = function () {
	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
};

</script>

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


<script type="text/javascript">
 function receivingApplicants(){
	if (document.getElementById('receivingApplicantsId').checked){
		 $("#receivingApplicantsId").val('Y');
	}
	else{
		 $("#receivingApplicantsId").val('N');
		}
 } 
</script>

<script type="text/javascript">
	function storeRequirement(saveType) {
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		var ee = $("#txtEditor").val($('.Editor-editor').html()); 
		checkTemplateforDouplicate();
		validateJobId();
		$('#addClient').empty();
		$('#addContact').empty();
		//$('#addTeam').empty(); /* commented by Venkat Kesav because of issue #2673 */ 
          if(saveType=='draft'){
			
			//For internal details in draft
			 $('#managerId').removeAttr('data-validation');
			 $('#managerId').attr("data-validation-optional","true"); 
			 
			 
			 $('#assignedTo').removeAttr('data-validation');
			 $('#assignedTo').attr("data-validation-optional","true");
			 
			 
			 $('#priority').removeAttr('data-validation');
			 $('#priority').attr("data-validation-optional","true");
			
			 
			 $('#applicationFrom').removeAttr('data-validation');
			 $('#applicationFrom').attr("data-validation-optional","true");
			 
			 //for client details in draft
			 
			 /* $('#addClientId').removeAttr('data-validation');
			 $('#addClientId').attr("data-validation-optional","true");
			 
			 $('#selectContact').removeAttr('data-validation');
			 $('#selectContact').attr("data-validation-optional","true");
			 
			 $('#clientSubmissionForm').removeAttr('data-validation');
			 $('#clientSubmissionForm').attr("data-validation-optional","true"); */
		}
		
          /* var rateType = document.getElementById("rateType").value; 
  		 if(rateType != null && rateType != ""){
  			 $('#rate').removeAttr('data-validation-optional');
  			 $('#rate').attr("data-validation","required validate_Space validate_int validate_length length1-8"); 
  		 }else{
  			 $('#rate').removeAttr('data-validation');
  			 $('#rate').attr("data-validation-optional","true"); 
  		 }
  		  */
 		 var rate = document.getElementById("rate").value; 
 		 if(rate != null && rate != ""){
 			 $('#rateType').removeAttr('data-validation-optional');
 			 $('#rateType').attr("data-validation","required");
 			 $('#rate').attr("data-validation","required validate_Space validate_int validate_length length1-8");
 		 }else{
 			 $('#rateType').removeAttr('data-validation');
 			 $('#rateType').attr("data-validation-optional","true"); 
 		 }
 		var maxRate = document.getElementById("maxRate").value; 
		 if(maxRate != null && maxRate != ""){
			 $('#rateType').removeAttr('data-validation-optional');
			 $('#rateType').attr("data-validation","required");
			 $('#rate').attr("data-validation","required validate_Space validate_int validate_length length1-8");
		 }else{
			 $('#rateType').removeAttr('data-validation');
			 $('#rateType').attr("data-validation-optional","true"); 
		 }
		 
			 var jobSaveType = '';
			 if(saveType == 'saveAndContinue'){
				 jobSaveType = 'save';
			 }else{
				 jobSaveType = saveType;
			 }
			 
		    if ($('#requirement_form').validate(false, validationSettings)) {
		    	checkManagerAndPrimaryRecruiterSelection($("#managerId"));
		    	if(($("#managerId").parent().find("p").length === 0) && ($("#primaryRecruiterUserId").parent().find("p").length === 0)){
		    		var formData = getFormData1("requirement_form");
					$( "#submitForApproval" ).addClass('button').val('Processing..');
					$("#requirementLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:10000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				         $.ajax({
							 type : "POST",
							 data: formData,
			  		         processData: false,
			  		         contentType:false, 
							  url : atsUrl+"/storeRequirement?"+tokenParamName+"="+tokenValue+"&saveType="+jobSaveType,
							  	success : function(result) {
							  		if(result.object === 'MissingData'){
							  			showError("Invalid data");
							  			$("#requirementLoading").removeAttr("style");	
							  		}else{
							  			if (result.response === "SESSION_TIMEDOUT") {
							  				location.replace(sourceleadUrl);
						                } else if (result.response === "AWKWARD") {
						                    location.href = "/errorPage";
						                } else if (result.response === "SUCCESS") {
										  	if(saveType != 'saveAndContinue'){
										  		location.replace(atsUrl+"/jobsList");
										  	}
											$("#selector2").removeAttr("style");
								    		$("#selector2").css({ display: "block" });
								    		if(saveType == 'save'){
								    			$("#msgJobDiv").html("Successfully job  saved..");
								    		}else if(saveType == 'draft'){
								    			$("#msgJobDiv").html("Successfully job  saved to Draft");
								    		}
								    		else if(saveType == 'submitForApproval'){
								    			$("#msgJobDiv").html("Successfully job  submitted for approval");
								    		}else if(saveType == 'saveAndContinue'){
								    			if(result.object != 'MissingData'){
								    				$("#msgJobDiv").html("Job saved successfully");
								    				location.replace(atsUrl+"/jobReleaseManagement?requirementUUID="+result.object);	
								    			}
								    		}
							     			msgFadein();
						     			
						                } else if (result.indexOf('Sign In') !== -1) {
											location.replace(sourceleadUrl);
										} else {
						                    showError("Operation failed, please try again");
						                }
							  		}
							},
						});
					return true;
		    	} else {
		    		
		    	}
		   } 
		return false;
	} 
	
	$('body').on('blur', '#requirement_form', function() {
		$("#requirement_form").showHelpOnFocus();
		$("#requirement_form").validateOnBlur(false, validationSettings);
	});
	
	function msgFadein() {
		$("#selector2").fadeIn(200);
	    $('#selector2').delay(2000).fadeOut('slow');
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
<script>
function storeTeam(){
	   var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         };
	   if ($('#team_form').validate(false, validationSettings)){ 
		   
		   var formData = getFormData1("team_form");
			
			$("#add_team").attr("disabled", "disabled");
			
			$.ajax({
				 type : "POST",
				 data: formData,
 	    	     contentType : 'application/json; charset=utf-8',
 		         processData: false,
 		         contentType:false, 
				url : atsUrl+ "/storeTeam?"+tokenParamName+"="+tokenValue+"",
				success : function(result) {
					$('#addTeam').hide();
					//$('.modal-backdrop').hide();
					$("#addTeam").modal('hide');

				},
			});
	        
			return true; 
	   }else{ 
			return false;
	        } 
		} 
 
		$('body').on('blur', '#team_form', function() {
			$("#team_form").showHelpOnFocus();
			$("#team_form").validateOnBlur(false, validationSettings);
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


<!-- Eductions and Skills Autocomplete Scripts Starts Here -->
<script type="text/javascript">
			/* upload file script */
			document.getElementById("uploadBtn").onchange = function () {
			//document.getElementById("uploadFile").value = this.value;
			$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
			};
				
</script>
<!-- Eductions and Skills Autocomplete Scripts End Here -->

<script>
function getTemplateVal(){
    if(document.getElementById('isTemplate').checked) {
        $("#isTemplate").val('Y');
        $('#jobTitle').removeAttr('data-validation-optional');
		$('#jobTitle').attr("data-validation","required validate_Space validate_length length2-250"); 
		checkTemplateforDouplicate();
		
    }else{
    	
        $("#isTemplate").val('N');
       /*  $('#jobTitle').removeAttr('data-validation');
		$('#jobTitle').attr("data-validation-optional","true");  */
        $('#jobTitleDiv').find('p.jquery_form_error_message').remove();

    }
}
</script>
<script>
function getRequirementAndLookUpSkills(requirementid){
	
	$.ajax({
   	type: "POST",
    url: atsUrl+"/getRequirementAndLookUpSkills?"+tokenParamName+"="+tokenValue+"&requirementid="+requirementid, 	   
	    success: function(data) {
	    	if(data != null && data != 'undefined' && data !='' ){ 
	    		var json = JSON.parse(data);
	    		var output = "";
	    		for (var i = 0; i < json.length; i++) { 
               	    var skill = json[i];
               
	        		output = output + "<option value='" +skill+ "'>"+skill+"</option>";
	    		}
	            $("#skills").empty();
	            $("#skills").append(output);
	            $('#skills').trigger("chosen:updated");
	            
	    	} 
	    }
	});
}
</script>
<script>
function setTemplateValues(){
	var requirementId = document.getElementById('template').value;
	getEditRequirementDetails(requirementId);
	if(requirementId != null && requirementId !='' && requirementId != 'undefined'){
		
// 		getRequirementAndLookUpSkills(requirementId);
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/getRequirementById?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId, 	   
		    success: function(json) {
		    	if(json != null && json != 'undefined' && json !='' ){ 
		    		if(json.fileName != null && json.fileName != '' && json.fileName !='undefined'){
		    			$("#templateId").val(json.id);
		    		}
			    	$("#jobTitle").val(json.jobTitle);
			    	$("#jobType").val(json.jobType);
			    	$("#experienceLevel").val(json.experienceLevelStr);
			    	
			    	$("#coolingOff").val(json.reqCoolingOffPeriod);
			    	$('#coolingOff').trigger('chosen:updated');
			    	$("#industryName").val(json.industryName);
			    	$("#industryName").trigger("chosen:updated");
			    	
			    	$("#projectId").val(json.projectId);
			    	$("#projectId").trigger("chosen:updated");
			    	
			    	$('#skills').val('').trigger('chosen:updated');
					if(json.skills != null && json.skills != '' && json.skills != 'undefined'){
						var skills = [];
			            skills = json.skills.split(",");
			            $("#skills").val(skills);
			            $('#skills').trigger("chosen:updated");
					}

					
			    	$("#jobOtherType").val(json.otherJobType);
			    	$('.selectpicker').selectpicker('refresh');
			    	$("#jobId").val(json.jobId);
			    	$("#positions").val(json.positions);
			    	$("#travelAllowance").val(json.travelAllowance);
			    	$("#minExp").val(json.minExp);
			    	$("#maxExp").val(json.maxExp);
			    	if(json.medicalClearence !=  null && json.medicalClearence != '' && json.medicalClearence != 'undefined'){
			    		 $("#medicalTemplate").css("display","block"); 
			    		 $("#addmedical").css("display","none"); 
			    		var result = "";
			    		result = result + '<label class="col-md-3">Medical  Clearance </label>';
			    		result = result + '<div class="col-md-9">';
			    		if(json.medicalClearence == "Y"){
			    			result = result + '<span class="paddingRight"><input type="radio" checked value="Y" name="medicalClearence"><span class="mr5"></span>Yes</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" value="N" name="medicalClearence"><span class="mr5"></span>No</span>';
			    		}else {
			    			result = result + '<span class="paddingRight"><input type="radio"  value="Y" name="medicalClearence"><span class="mr5"></span>Yes</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" checked value="N" name="medicalClearence"><span class="mr5"></span>No</span>';
			    		}
			    		result = result + '<div>';
			    		$("#medicalClearenceDivId").empty();
			    		$("#medicalClearenceDivId").append(result);
			    	}else{
			    		 $("#addmedical").css("display","block"); 
			    		 $("#medicalTemplate").css("display","none"); 
			    	}
			    	
			    	if(json.securityClearance !=  null && json.securityClearance != '' && json.securityClearance != 'undefined'){
			    		 $("#securityClearenceTemplate").css("display","block"); 
			    		 $("#addSecurityClearence").css("display","none"); 
			    		var result = "";
			    		result = result + '<label class="col-md-3">Security Clearance</label>';
			    		result = result + '<div class="col-md-9">';
			    		if(json.securityClearance == "Y"){
			    			result = result + '<span class="paddingRight"><input type="radio" checked value="Y" name="securityClearance"><span class="mr5"></span>Yes</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" value="N" name="securityClearance"><span class="mr5"></span>No</span>';
			    		}else {
			    			result = result + '<span class="paddingRight"><input type="radio"  value="Y" name="securityClearance"><span class="mr5"></span>Yes</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" checked value="N" name="securityClearance"><span class="mr5"></span>No</span>';
			    		}
			    		result = result + '<div>';
			    		$("#securityClearenceDivId").empty();
			    		$("#securityClearenceDivId").append(result);
			    	}else{
			    		 $("#addSecurityClearence").css("display","block"); 
			    		 $("#securityClearenceTemplate").css("display","none"); 
			    	}
			    	
			    	if(json.overTime !=  null && json.overTime != '' && json.overTime != 'undefined'){
			    		 $("#overTimeTemplate").css("display","block"); 
			    		 $("#addOverTime").css("display","none"); 
			    		var result = "";
			    		result = result + '<label class="col-md-3">Overtime</label>';
			    		result = result + '<div class="col-md-9">';
			    		if(json.overTime == "Y"){
			    			result = result + '<span class="paddingRight"><input type="radio" checked value="Y" name="overTime"><span class="mr5"></span>Yes</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" value="N" name="overTime"><span class="mr5"></span>No</span>';
			    		}else {
			    			result = result + '<span class="paddingRight"><input type="radio"  value="Y" name="overTime"><span class="mr5"></span>Yes</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" checked value="N" name="overTime"><span class="mr5"></span>No</span>';
			    		}
			    		result = result + '<div>';
			    		$("#overTimeDivId").empty();
			    		$("#overTimeDivId").append(result);
			    	}else{
			    		 $("#addOverTime").css("display","block"); 
			    		 $("#overTimeTemplate").css("display","none"); 
			    	}
			    	
			    	
			    	if(json.backgroundCheck !=  null && json.backgroundCheck != '' && json.backgroundCheck != 'undefined'){
			    		 $("#backgroundCheckTemplate").css("display","block"); 
			    		 $("#addBackgroundCheck").css("display","none"); 
			    		var result = "";
			    		result = result + '<label class="col-md-3">Background Check</label>';
			    		result = result + '<div class="col-md-9">';
			    		if(json.backgroundCheck == "Y"){
			    			result = result + '<span class="paddingRight"><input type="radio" checked value="Y" name="backgroundCheck"><span class="mr5"></span>Yes</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" value="N" name="backgroundCheck"><span class="mr5"></span>No</span>';
			    		}else {
			    			result = result + '<span class="paddingRight"><input type="radio"  value="Y" name="backgroundCheck"><span class="mr5"></span>Yes</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" checked value="N" name="backgroundCheck"><span class="mr5"></span>No</span>';
			    		}
			    		result = result + '<div>';
			    		$("#backgroundCheckDivId").empty();
			    		$("#backgroundCheckDivId").append(result);
			    	}else{
			    		 $("#addBackgroundCheck").css("display","block"); 
			    		 $("#backgroundCheckTemplate").css("display","none"); 
			    	}
			    	
			    	if(json.gender !=  null && json.gender != '' && json.gender != 'undefined'){
			    		 $("#genderTemplate").css("display","block"); 
			    		 $("#addGender").css("display","none"); 
			    		var result = "";
			    		result = result + '<label class="col-md-3">Gender</label>';
			    		result = result + '<div class="col-md-9">';
			    		if(json.gender == "Male"){
			    			result = result + '<span class="paddingRight"><input type="radio" checked value="Male" name="gender"><span class="mr5"></span>Male</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" value="Female" name="gender"><span class="mr5"></span>Female</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" value="All" name="gender"><span class="mr5"></span>All</span>';
			    		}else if(json.gender == "Female"){
			    			result = result + '<span class="paddingRight"><input type="radio" value="Male" name="gender"><span class="mr5"></span>Male</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" checked value="Female" name="gender"><span class="mr5"></span>Female</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" value="All" name="gender"><span class="mr5"></span>All</span>';
			    		}else if(json.gender == "All"){
			    			result = result + '<span class="paddingRight"><input type="radio" value="Male" name="gender"><span class="mr5"></span>Male</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" value="Female" name="gender"><span class="mr5"></span>Female</span>';
				    		result = result + '<span class="paddingLeft"><input type="radio" checked value="All" name="gender"><span class="mr5"></span>All</span>';
			    		}
			    		result = result + '<div>';
			    		$("#genderDivId").empty();
			    		$("#genderDivId").append(result);
			    	}else{
			    		 $("#addGender").css("display","block"); 
			    		 $("#genderTemplate").css("display","none"); 
			    	}
			    	if(null != json.jobType && json.jobType != "" && json.jobType != "undefined" && json.jobType == 'Other'){
						  $( ".jobTypeDiv" ).show();
						  $("#jobOtherType").attr("data-validation-optional", "true");
						  $("#jobOtherType").attr("data-validation", "validate_Space validate_length length2-250"); 
					}else{
						  $( ".jobTypeDiv" ).hide();
						  $("#jobOtherType").attr("data-validation-optional", "true");
						  $("#jobTypeDiv").find('p.jquery_form_error_message').remove();
						  $("#jobOtherType").val('');
					}
			    	
			    	var country = '';
					 var state = '';
					 var city = '';
					 validateJobId(json.jobId);
					
						if(json.country != null && json.country !='' && json.country !='undefined'){
							$("#existingCountry").val(json.country);
							$("#countryReq").val(json.country);
						}else{
							$("#existingCountry").val('');
						}
						
						if(json.state != null && json.state !='' && json.state !='undefined'){
							 $("#existingState").val(json.state);
							 $("#state").val(json.state);
							
						}else{
							$("#existingState").val('');
						}
						if(json.city != null && json.city !='' && json.city !='undefined'){
							 $("#existingCity").val(json.city);
							 $("#city").val(json.city);
						} else{
							$("#existingCity").val('');
						}
						
					 		
						var ee = $("#txtEditor").val($('.Editor-editor').html()); 
					$('#description').val('');
					$('#rate').val('');
					$('#education').val('').trigger('chosen:updated');
					
					if(json.education != null && json.education != '' && json.education != 'undefined'){
			            var educations = [];
			            educations = json.education.split(",");
			            $("#education").val(educations);
			            $('#education').trigger("chosen:updated");
					}
			    	
			    	//$("textarea#description").val(json.description);
			    	if(json.description != null && json.description != '' && json.description != 'undefined'){
			    	$('#txtEditor').html(json.description);
			    	$('.Editor-editor').html(json.description);
			    	}else{
			    	$('#txtEditor').text('');	
			    	$('.Editor-editor').text('');
			    	}
			    	$("#rate").val(json.rate);
			    	$("#maxRate").val(json.maxRate);
			    	
			    	if(json.currencyCode != null && json.currencyCode != '' && json.currencyCode != 'undefined'){
			    	$("#currencyCode").val(json.currencyCode);
			    	}
			    	$('.selectpicker').selectpicker('refresh');
			    	$("#rateType").val(json.rateType);
			    	$('.selectpicker').selectpicker('refresh');
			    	 existedFile = json.fileName;
			    	 $("#uploadFile").val(existedFile);
			    	 $('#uploadFile').trigger("chosen:updated");
			   
			    	 //$( ".jobTypeDiv" ).hide();
		    	}else{
		    		$("#jobTitle").val('');
			    	$("#jobType").val('');
			    	$("#travelAllowance").val('');
			    	$("#jobOtherType").val('');
			    	$("#jobId").val('');
			    	$("#positions").val('');
			    	$("#industryName").val('');
			    	$("#industryName").trigger("chosen:updated");
			    	$("#projectId").val('');
			    	$("#projectId").trigger("chosen:updated");
			    	$("#minExp").val('');
			    	$("#maxExp").val('');
					$("#countryReq").val('');
					$("#state").val('');
					$("#city").val('');
					
					$("#existingCountry").val('');
					$("#existingState").val('');
					$("#existingCity").val('');
					$("#coolingOff").val('');
		            $("#skills").val('');
		            $("#education").val('');
		            $(".Editor-editor").text('');
			    	$("#rate").val('');
			     	$("#currencyCode").val('');
			    	$("#rateType").val('');
			    	$("#maxRate").val('');
			    	$( ".jobTypeDiv" ).show();
			    	$(".")
			    	
			    	existedFile = json.fileName;
			    	$("#fileName").val(existedFile);
			    	$('#fileName').trigger("chosen:updated");
		    	}
		    }
     });
	}else{
		 document.getElementById("requirement_form").reset();
		 $(".Editor-editor").text('');
		 $("#skills").trigger("chosen:updated");
		 $("#education").trigger("chosen:updated");
		 $("#existingCountry").val('');
		 $("#existingState").val('');
		 $("#existingCity").val('');
		 $("#rate").val('');
		
		 
	}
}

$(function(){
    var config = {
		  '.chosen-select'           : {},
		  '.chosen-select-deselect'  : {allow_single_deselect:true},
		  '.chosen-select-no-single' : {disable_search_threshold:10},
		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		  '.chosen-select-width'     : {width:"95%"}
		}
    $("#countryReq").chosen();
    $("#state").chosen();
    $("#city").chosen();
    $("#owner").chosen();
    
    $("#countryReq").on('change', function (e) {
 	   getStates();
    });
   
    $("#state").on('change', function (e) {
 	   getCities();
    });
   
    $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
});
</script>

<script>
// script for tooltip
$('[data-toggle="tooltip"]').tooltip();
///////////////////// Back To Top 
$(document).ready(function() {
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
    $(".clearUpload").hide();
});

// Show hide Approver script
$("#apprverReq").removeAttr("checked");
$("#sequential input").removeAttr("checked");
function addLvl(){
	$(".lvlTow").toggle();
}
</script>

<!-- This Method used to remove the  selected Approvers values  when  the approval mechanism is unchecked-->

<script>
function removeApprovers(){
	
	$('#parallelApprover').val('');
	$('#parallelApprover').trigger('chosen:updated');
	
	$('#sequentialApprover1').val('');
	$('#sequentialApprover1').trigger('chosen:updated');
	
	$('#sequentialApprover2').val('');
	$('#sequentialApprover2').trigger('chosen:updated');
	
	 
	if(!$("#apprverReq").is(':checked')){
         $("#navbar").removeClass("glyphicon glyphicon-info-sign text-info ml");
         $("#parallelapprovers").hide();
         $("#seqentialApprovers").hide();
    }else{
		 $("#navbar").addClass("glyphicon glyphicon-info-sign text-info ml");
		 $("#parallelapprovers").show();
         $("#seqentialApprovers").show();
    } 
}
</script>

<script>
function approvalType(type){
	
	    if($("#apprverReq").is(':checked')){
	    	
	    	$("#parallelApprover").val('');
			$('#parallelApprover').trigger("chosen:updated");
	    	
			var requiremenStatus  = '${requirement.requirementSubmissionStatus}';
			
			if(requiremenStatus !='OPEN'){
				$("#submitForApproval").removeAttr("style");
			    $("#save").attr("style","display:none"); 
			}
			
		    $(".parallel, #parallel, .apprverReq").show();
			$(".sequential").hide();
			$("#parallel i").addClass("apprverReq-active");
			$("#sequential i").removeClass("apprverReq-active");
			
			
			if(type == "Default" || type == "Parallel"){
				$("#parallel i").show();
			    $("#sequential i").show();
				 
			    $(".parallel").show();
			    $("#parallel i").addClass("apprverReq-active");
			    
				$(".sequential").hide();
				$("#sequential i").removeClass("apprverReq-active"); 
				
				$("#sequentialApproverDiv").hide();
				
				
				$('#sequentialApprover1').attr("data-validation-optional","true"); 
				$('#sequentialApprover2').attr("data-validation-optional","true"); 
				$('#parallelApprover').removeAttr('data-validation-optional');
				
				$('#sequentialApprover1').val('').trigger('chosen:updated');
				$('#sequentialApprover2').val('').trigger('chosen:updated');
				
		  }
		  if(type == "Sequential"){
			    $(".parallel").hide();
			    $("#parallel i").removeClass("apprverReq-active");
			    
				$(".sequential").show();
				$("#sequential i").addClass("apprverReq-active");
				
				$('#parallelApprover').attr("data-validation-optional","true"); 
				
				$('#sequentialApprover1').removeAttr('data-validation-optional');
				$('#sequentialApprover2').removeAttr('data-validation-optional');
				
				$('#parallelApprover').val('').trigger('chosen:updated');
		  }
		}
		else{
			$("#save").removeAttr("style");
		    $("#submitForApproval").attr("style","display:none"); 
		    
		    $(".parallel").hide();
		    $("#parallel i").hide();
		    
		    $(".sequential").hide();
			$("#sequential i").hide();
			
			$('#sequentialApprover1').attr("data-validation-optional","true"); 
			$('#sequentialApprover2').attr("data-validation-optional","true"); 
			$('#parallelApprover').attr("data-validation-optional","true"); 
		}
	  
	    //Written by mamatha ,purpose : added tooltip
	    if(type == "Default" || type == "Parallel"){
	    	
	    	$('#navbar').attr("title","Either of the approver can approve the job");
	    	
	    	
	    }else if(type == "Sequential"){
	    	$('#navbar').attr("title","The job approval is required from both the approver levels");
	    	
	    }
	   
	  
	       
	  
}
</script>

<script type="text/javascript">
	function flushStoreTeam() { 
		document.getElementById("team_form").reset();
		$('#assignTeamDiv').find('p.jquery_form_error_message').remove();
		document.getElementById("add_team").removeAttribute("disabled");
	}
</script>

<script type="text/javascript">
function getTeamOwners(){ 
	 
	 var teamMemberUserId = document.getElementById("memberUserId").value;
     $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getAllUsersByOrgLocationCodeExceptTeamOwner?"+tokenParamName+"="+tokenValue+"&teamOwnerUserId="+teamMemberUserId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-Select Team Lead-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#ownerUserId").empty();
			        $("#ownerUserId").append(output);
			        $("#ownerUserId").trigger("chosen:updated");
			        getTeamMembers(); 
		        },
		        error: function(e){
		            
		        }
		    });  
} 
</script>


<script type="text/javascript">
function getTeamMembers(){ 
	 
	 var teamOwnerUserId = document.getElementById("ownerUserId").value;
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getAllUsersByOrgLocationCodeExceptTeamOwner?"+tokenParamName+"="+tokenValue+"&teamOwnerUserId="+teamOwnerUserId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option disabled >-Select Team Members-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#memberUserId").empty();
			        $("#memberUserId").append(output);
			        $("#memberUserId").trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
} 
</script>

<script>
function getIsClientVal(){
/* $("#selectContact").empty().trigger('chosen:updated');
$("#clientSubmissionForm").empty().trigger('chosen:updated'); */
var existingValue = document.getElementById('isClientJob').value;

	if (document.getElementById('isClientJob').checked){
		changeValidationType();
		$("#isClientJob").val('Y');
		 
		// $("#addClientId").val('');
		 $('#addClientId').trigger("chosen:updated");
		 
		 $("#selectContact").trigger('chosen:updated');
		
		 $("#clientCurrencyCodge").trigger('chosen:updated');
		 
		 $("#clientCurrencyCode").val('${currencyCode}').trigger('change');
		 
		 var isEdit = '${val}'
		 if(isEdit != 'Y'){
			//$("#clientRateType").val("").trigger('change');
		 }
		 $('#clientDetails').find('p.jquery_form_error_message').remove();

		 /* $('select[name=clientCurrencyCodgetIsClientVale]').val(1);
		 $('.selectpicker').selectpicker('refresh');
		  */
		 //$('#clientRate').val('');
		 
		$("#clientRateType").trigger('chosen:updated');

		 /* $('select[name=clientRateType]').val(1);
		 $('.selectpicker').selectpicker('refresh');
		  */
		// $("#clientSubmissionForm").val('');
		 $('#clientSubmissionForm').trigger("chosen:updated");
		 existingValue = document.getElementById('isClientJob').value;
	}else {
		changeValidationType();
		 $("#isClientJob").val('N');
		 
		 $("#addClientId").val('');
		 $('#addClientId').trigger("chosen:updated");
		 
		// $("#selectContact").find('.chosen-results').val('').trigger('chosen:updated');
		
		$("#selectContact").val('');
		$('#selectContact').trigger("chosen:updated");
		
		 
		/*  $('select[name=clientCurrencyCodgetIsClientVale]').val(1);
		 $('.selectpicker').selectpicker('refresh');
		  */
		  
		 // $("#clientCurrencyCode").val('');
			
		 $('#clientRate').val('');
		 $('#clientMaxRate').val('');
		 $("#clientCurrencyCode").val('${currencyCode}').trigger('change');
		 $("#clientRateType").val("Monthly").trigger('change');
		 $('#clientDetails').find('p.jquery_form_error_message').remove();
		 
		 //$('select[name=clientRateType]').val(1);
		// $('.selectpicker').selectpicker('refresh');
		 $("#clientSubmissionForm").val('');
		 $('#clientSubmissionForm').trigger("chosen:updated");
		 
		 existingValue = document.getElementById('isClientJob').value;
		 
    // Adding the Internal  Submission Form  as we are Unselecting the Client
		 document.getElementById("internalSubmissionFrom").style.display = '';
		 $("#applicationFrom").attr("data-validation", "required");
		 
	}
	 if ((document.getElementById('isClientJob').checked) || (existingValue != null && existingValue != '' && existingValue !='undefined' && existingValue == 'Y') ) {
		 document.getElementById("clientDetails").style.display="";
		 $("#isClientJob").val('Y');
		 
		 
		//to remove the  Internal  Submission Form  as we are selecting the Client 
		 document.getElementById("internalSubmissionFrom").style.display = 'none';
		 $('#applicationFrom').attr("data-validation-optional","true"); 
		
		 
         $('#addClientId').removeAttr('data-validation-optional');
	     $('#addClientId').attr("data-validation","required"); 
	   
	     $('#selectContact').removeAttr('data-validation-optional');
	     $('#selectContact').attr("data-validation","required"); 
	   
	     $('#clientSubmissionForm').removeAttr('data-validation-optional');
	     $('#clientSubmissionForm').attr("data-validation","required"); 
	 } else {
		 document.getElementById("clientDetails").style.display="none";
		 $("#isClientJob").val('N');
		 
		 $('#addClientId').removeAttr('data-validation');
		 $('#addClientId').attr("data-validation-optional","true"); 
		 
		 $('#selectContact').removeAttr('data-validation');
		 $('#selectContact').attr("data-validation-optional","true"); 
		 
		 $('#clientSubmissionForm').removeAttr('data-validation');
		 $('#clientSubmissionForm').attr("data-validation-optional","true"); 
		 
		 $('#applicationFrom').removeAttr("data-validation-optional"); 
	 }
}

</script>

<script type="text/javascript">
function getLevel2Approvers(currentApproverId){
	var currentApproverId = document.getElementById("sequentialApprover1").value;
	 
	 if(currentApproverId != null && currentApproverId != ''  && currentApproverId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/getApproverTwoByUserId?"+tokenParamName+"="+tokenValue+"&currentApproverId="+currentApproverId,  
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-Select Approver-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(opt.lastName != null && opt.lastName !='undefined'){
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+"</option>";	
			        		}
					}
		        	$("#sequentialApprover2").empty();
			        $("#sequentialApprover2").append(output);
			        $('#sequentialApprover2').trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
	 }
} 
</script>


<!-- Job Id validation script starts here -->

<script type="text/javascript">
 function validateJobId(jobId) {
	 $('input#jobId').removeAttr( "record-exist record-exist-errormsg");
	 var requirementid = document.getElementById("requirementId").value;
	/*  if(requirementid != null && requirementid != '' && requirementid != 'undefined' ){ */
		 if(jobId == null || jobId == "" || jobId == "undefined"){
			 jobId = document.getElementById("jobId").value; 
		 }
		 var specialCharacter = jobId.match(/^[A-Za-z0-9][A-Z a-z 0-9]*$/);
		 $('#jobIdDiv').find('p.jquery_form_error_message').remove();
		 
		 if(jobId != null && jobId != "" && specialCharacter != null){
			 $("#draft").attr("disabled", "disabled");
			 $("#save").attr("disabled", "disabled");
			 $("#submitForApproval").attr("disabled", "disabled");
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/validateJobId?"+tokenParamName+"="+tokenValue+"&jobId="+jobId+"&requirementid="+requirementid, 
			        success: function(data) {
			        	if(data == true){
							  $("input#jobId").after("<p class='jquery_form_error_message'>"+jobId+" is Already Existed</span>");
							  document.getElementById("jobId").setAttribute('record-exist','yes');
							  document.getElementById("jobId").setAttribute('record-exist-errorMsg',jobId+' is Already Existed'); 
			        	}
			        	else{
			        		 $('input#jobId').removeAttr( "record-exist record-exist-errormsg");
			        		//$('#jobIdDiv').find('p.jquery_form_error_message').remove(); 
	                         $("#draft").removeAttr("disabled");
	                         $("#save").removeAttr("disabled", "disabled");
	                		 $("#submitForApproval").removeAttr("disabled", "disabled");
			        	}
			        },
			    });
		 }
 }
 </script>

<script>
//Function to validate if maximum exp value is more the minimum exp value
function minMaxExperience(){
    var maxExp = ($('#maxExp').val());
    var minExp = ($('#minExp').val());
    
    if (parseFloat(maxExp) <= parseFloat(minExp)){
        $('#expId').find('p.jquery_form_error_message').remove(); 
		$('input#expValidation').attr('class','error signin_fild');
		$("input#expValidation").after("<p class='jquery_form_error_message'>Max Experience should be greater than Min Experience</p>");
		$('input#expValidation').attr('record-exist','yes');
		$('input#expValidation').attr('record-exist-errorMsg','Max Experience should be greater than Min Experience'); 
    }  else{
    	$('#expId').find('p.jquery_form_error_message').remove(); 
    	$('input#expValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#expId').find('p.jquery_form_error_message').remove();
    }
}

</script>


<script>
//Function to validate if maximum rate value is more the minimum rate value
function minMaxRate(){
    var maxRate = ($('#maxRate').val());
    var minRate = ($('#rate').val());
    
    if (parseFloat(maxRate) <= parseFloat(minRate)){
        $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
		$('input#rateValidation').attr('class','error signin_fild');
		$("input#rateValidation").after("<p class='jquery_form_error_message'>Max Rate should be greater than Min Rate</p>");
		$('input#rateValidation').attr('record-exist','yes');
		$('input#rateValidation').attr('record-exist-errorMsg','Max Rate should be greater than Min Rate'); 
    }  else{
    	$('#rateValidateId').find('p.jquery_form_error_message').remove(); 
    	$('input#rateValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#rateValidateId').find('p.jquery_form_error_message').remove();
    }
}

</script>
<script>
//Function to validate if maximum rate value is more the minimum rate value
function clientMinMaxRate(){
	var maxRate = ($('#clientMaxRate').val());
    var minRate = ($('#clientRate').val());
    
    if (parseFloat(maxRate) <= parseFloat(minRate)){
        $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
		$('input#rateValidation').attr('class','error signin_fild');
		$("input#rateValidation").after("<p class='jquery_form_error_message'>Max Rate should be greater than Min Rate</p>");
		$('input#rateValidation').attr('record-exist','yes');
		$('input#rateValidation').attr('record-exist-errorMsg','Max Rate should be greater than Min Rate'); 
    }  else{
    	$('#rateValidateId').find('p.jquery_form_error_message').remove(); 
    	$('input#rateValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#rateValidateId').find('p.jquery_form_error_message').remove();
    }
}

</script>
<script type="text/javascript">
function checkTemplateforDouplicate(){
	if(document.getElementById('isTemplate').checked) {
		var templateName = document.getElementById("jobTitle").value; 
		
		// This will allow all special chars
         var jsonData = encodeURIComponent(templateName);	  
       
		$('#jobTitleDiv').find('p.jquery_form_error_message').remove();
		
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/validatetemplateName?"+tokenParamName+"="+tokenValue+"&templateName="+jsonData, 
			        success: function(data) {
			        	if(data == "true"){
			        		  $('#jobTitleDiv').find('p.jquery_form_error_message').remove();
							  $("input#jobTitle").after("<p class='jquery_form_error_message'><span>Template Already Exists</span>");
							  document.getElementById("jobTitle").setAttribute('record-exist','yes');
							  document.getElementById("jobTitle").setAttribute('record-exist-errorMsg','Template Already Exists'); 
			        	}
			        	else{
			        		 $('input#jobTitle').removeAttr( "record-exist record-exist-errormsg");
			        		// $('#jobTitleDiv').find('p.jquery_form_error_message').remove(); 
			        		 $("#requirement_form" ).addClass('button').val('Save');
		                    $("#requirement_form").removeAttr("disabled");
			        	}
			        },
			    });
	}else{
		$('input#jobTitle').removeAttr( "record-exist record-exist-errormsg");
		$('#jobTitleDiv').find('p.jquery_form_error_message').remove();
	}

}
</script>

<script>
function getJobType(jobType){
	var existingJobType = '${requirement.jobType}';
	if(jobType.value == 'Other'){
		 document.getElementById("jobTypeDiv").style.display="block";
		  $("#jobOtherType").attr("data-validation-optional", "true");
		  $("#jobOtherType").attr("data-validation", "validate_Space validate_length length2-250");
	}else{
		document.getElementById("jobTypeDiv").style.display="none";
		  $("#jobOtherType").attr("data-validation-optional", "true");
		  $("#jobTypeDiv").find('p.jquery_form_error_message').remove();
		  if(existingJobType ==null || existingJobType == "" || existingJobType == "undefined" || existingJobType != 'Other'){
			  $("#jobOtherType").val('');
		  }
		  
		  if(jobType.value == 'C2C' || jobType.value == 'C2H' || jobType.value == 'Contract'){
	
				 document.getElementById("projectDurationYearsDiv").style.display="block";
				 document.getElementById("projectDurationMonthsDiv").style.display="block";
			  
			}else{
				document.getElementById("projectDurationYearsDiv").style.display="none";
				document.getElementById("projectDurationMonthsDiv").style.display="none";
				
			}
		  
	}
}
</script>

<script>
function getNoticePeriodList(){
var notice = '${requirement.noticePeriod}';	
var splitNotice = notice.split(",");
for (var i = 0; i < splitNotice.length; i++) {
	splitNotice[i] = splitNotice[i].replace(/^\s*/, "").replace(/\s*$/, "");
}
	$("#noticePeriod").val(splitNotice).trigger("chosen:updated");
}
$('.chosen-select').each(function() {
	var $this = $(this);
	$this.next().css({'width': '100%'});
})
</script>

<script>
function getEditRequirementDetails(requirementId){
	
	if(templateId !=''){
		$.ajax({
			type : "GET",
			url : atsUrl + "/getEditRequirementDetails?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId,
			success : function(data) {
				var json = JSON.parse(data);
				$(".Editor-container").find('.Editor-editor').html(json['description']);
			},
		}); 
	}
}
</script>

<script type="text/javascript">
 function validateTeamName() {
	 var teamName = $('#nameOfCandidate').val(); 
	 $('#teamNameDiv').find('p.jquery_form_error_message').remove();
	 if(teamName != null && teamName != ""){
		 $.ajax({
		    	type: "POST",
		        url: atsUrl+"/validateTeamName?"+tokenParamName+"="+tokenValue+"&name="+teamName, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $("input#nameOfCandidate").after("<p class='jquery_form_error_message'>"+teamName+" is Already Existed</span>");
						  $("#add_team").attr("disabled", "disabled");
						  $("#add_team").addClass('button').val('Processing..');
		        	}
		        	else{
		        		 $('input#nameOfCandidate').removeAttr("record-exist record-exist-errormsg");
		        		 $('#teamNameDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#add_team" ).addClass('button').val('Save');
                         $("#add_team").removeAttr("disabled");
		        	}
		        },
		    });
	 } 
 }
 </script>


<script>
function getProjectsList(projectName){
	var orgRelationId = $("#vendorOrgRelationId").val();
	$.ajax({
	type : "GET",
	url: atsUrl+"/getAllProjectsByOrgRelationIdAndInProgress?"+tokenParamName+"="+tokenValue+"",
	success :function(json) {
	var output = "";
	output = output + "<option value=''>--Select Project--</option>";
	for (var i=0; i<json.length; i++){
	var opt = json[i];
	var selected = "";
	if(projectName != null && projectName != "" && projectName !="undefined" && projectName == opt.projectName){
		selected = 'selected';
	}
	output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.projectName+"</option>";
	}
	$("#projectId").empty();
	$("#projectId").append(output);
	$('#projectId').trigger("chosen:updated");
	},
	});

}
</script>


<script type="text/javascript">
$(document).ready(function(){
	var requirementId  = "${requirementId}";
	getEditRequirementDetails(requirementId);
	
});
</script>

<script type="text/javascript">
	$("#uploadBtn").change(function() {
		fileUpload("uploadBtn", "requirementDocuments", "requirement_form");
		$(".clearUpload").show();
	});
</script>


<script>

	

function clearCloseDate(){

	$("#closedDate").val("");
	document.getElementById("receivingApplicantsDiv").style.display="none";
	if (document.getElementById('receivingApplicantsId').checked){
		 $("#receivingApplicantsId").val('N');
	}
	};
	$(".clearUpload").click(function(){
		$("#uploadFile").val('');
		$(".clearUpload").hide();
		$("#requirement_form input[data-id*=uploadBtn]").remove();
	});
	
	</script>

<script>
$("#showGrid").click(function(){
	var shogridlist = $('#showGrid').find(".showGrid");
	if($(shogridlist).text() > 0){
		$("#assignedTeamsOrUsers").slideToggle();
	}
	});
$("button.btnShowlist").click(function(){
    if ($("#sl").text() === 'Hide List') {
        $("#sl").text('Show List');
    } else {
        $("#sl").text('Hide List');        
    }
    return false; 
});

</script>
<script type="text/javascript">
function removeReqDoc(requirementId){
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/deleteJobDoc?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId, 	   
		    success: function(data) {
		    	$("#uploadDocNameDiv").hide();
		     	$("#uploadFileGroup123").show();
		     	$("#uploadExisitngFile").val('');
		     	$('#uploadFile').attr("data-validation-optional","true"); 
		     	$("#uploadFile").attr("data-validation", "validatedoc"); 
		    },
		    });		
	
}
</script>

<script>
function refreshRequirementClientList(){
		 var selectedOrgId = '${contract.orgRelations.id}';
		 var loginOrgLocId = '${SourceLeadWeb_orgLocationId}';
		 $.ajax({
	      type : "POST",
	       url: vmsUrl+"/refreshRequirementClientList",       
	       success: function(result) {
	    	   if (result.response === "SESSION_TIMEDOUT") {
	  				location.replace(sourceleadUrl);
               } else if (result.response === "AWKWARD") {
                   location.href = "/errorPage";
               } else if (result.response === "SUCCESS") {
            	    if(result.data != null && result.data != "" && result.data != 'undefined'){
            	    	var json = result.data;
			        	var output = "";
			        	output = output + "<option value=''>--Select Client--</option>"; 
			        	for (var i=0; i<json.length; i++)
						{
			        		var opt = json[i];
			        		output = output + "<option value='"+opt.id+","+opt.locationID+"' >"+opt.orgName+"</option>";
						}
            	    }
		        	$("#addClientId").empty();
		        	$("#selectContact").empty();
		        	$("#selectContact").trigger("chosen:updated");
			        $("#addClientId").append(output);
			        $('#addClientId').trigger("chosen:updated");
               } else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
			   } else {
                   showError("Operation failed, please try again");
               }
	        }
		 });
	}
</script>

<script>
	 function selectedClient(){
		 var a = $("#addClientId").val();
		 var valueArray = a.split(",");
		 var cid = valueArray[0]             
		 window.open(vmsUrl+"/addClientContact?cid="+cid,'_blank');
	 }
</script>

<script>
function refreshRequirementClientContactList(){
	 var a = $("#addClientId").val();
	 var valueArray = a.split(",");
	if(valueArray[0] != ""){
		clientContactList(valueArray[0],valueArray[1]);
	}
}
</script>


<script>
function getJobLocations(){
	var requirementUUID = '${requirementUUID}';
	if(requirementUUID){
		$("#requirementLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:10000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "POST",
		    	 url: atsUrl+"/getRequirementLocations?${_csrf.parameterName}=${_csrf.token}&requirementUUID="+requirementUUID, 
		        success: function(json) {
		        	
		        	 var result = "";
		        	 var addlocation = 'addlocation0';
		        	 var locCount = json.length;
		        	 
		         	 for (var i = 0; i < json.length; i++) { 
		         		 var opt = json[i]; 
		         		 var j = i+1000;
		         		 result = result +"<div class='positionRelative mt5' id='location"+opt.id+"'>";
		         		 result = result +'<input type="text" value="'+opt.locationName+'" placeholder="Location" id="'+j+'addlocation" onclick="addLocationAuto(\''+j+'addlocation\','+j+',\''+j+'longitude\',\''+j+'latitude\')" class="form-control joblocinput"  data-validation="required" field-name="Location" name="savedLocationInfo['+i+'].locationName">';
		         		 result = result +'<div class="fa fa-refresh btn btn-primary refresh-icon pull-right" title="Clear" onClick="refreshLocation('+j+');"></div>';
		         		 result = result +"<span class='fa fa-trash jobloc-trash cursorPointer' onclick='deleteSavedLocation("+opt.id+")'></span>";
		         		 result = result +'<input type="hidden" value="'+opt.country+'"  name="savedLocationInfo['+i+'].country" id="'+j+'country">';
			        	 result = result +'<input type="hidden" value="'+opt.state+'"  name="savedLocationInfo['+i+'].state" id="'+j+'state">';
			        	 result = result +'<input type="hidden" value="'+opt.city+'" name="savedLocationInfo['+i+'].city" id="'+j+'city">';
			        	 result = result +'<input type="hidden" value="'+opt.zipcode+'" name="savedLocationInfo['+i+'].zipcode" id="'+j+'zipcode">';
			        	 result = result +'<input type="hidden" value="'+opt.longitude+'" name="savedLocationInfo['+i+'].longitude" id="'+j+'longitude">';
			        	 result = result +'<input type="hidden" value="'+opt.latitude+'" name="savedLocationInfo['+i+'].latitude" id="'+j+'latitude">';
			        	 result = result +'<input type="hidden" value="'+opt.locationUUID+'" name="savedLocationInfo['+i+'].locationUUID" >';
	   				     result = result +"</div>";
		         	 }
		        	 
		        	 
		         	 result = result +"<input type='hidden' name='savedlocationsMaxRowNumber'  id='savedlocationsMaxRowNumber' value='"+locCount+"' />";
		        	 result = result +"<input type='hidden' name='locationsMaxRowNumber'  id='locationsMaxRowNumber' value='1' />";
		             result = result +"<input type='hidden' id='locationsRowCount' value='1' />";
		        	 result = result +"<table class='timesheetAttachment'  id='locationTable' cellpadding='0' cellspacing='0' style='width:108% !important;'>";
		        	 result = result +"<tr id='divAttachmentTsRow0'>";
		        	 result = result +"<td>";
		        	 result = result +"<div class='positionRelative mt5'>";
		        	 result = result +'<input type="text"  placeholder="Location" id="0addlocation" onclick="addLocationAuto(\'0addlocation\',0,\'0longitude\',\'0latitude\')" class="form-control joblocinput"  data-validation-optional="true" field-name="Location" name="locationInfo[0].locationName">';
		        	 result = result +'<div class="fa fa-refresh btn btn-primary refresh-icon" title="Clear" onClick="refreshLocation(0);"></div>';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].country" id="0country">';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].state" id="0state">';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].city" id="0city">';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].zipcode" id="0zipcode">';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].longitude" id="0longitude">';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].latitude" id="0latitude">';
		        	 result = result +"</td>";
		        	 result = result +"</div>";
		        	 result = result +"<td><i class='fa fa-plus' id='addLocRow' onclick='addNewLocation()'></i></td>";
		        	 result = result +"</tr>";
		        	 result = result +"</table>";
		        	
		        	 $("#jobLocationsList").empty();
		        	 $("#jobLocationsList").append(result);
		        	 $("#requirementLoading").removeAttr("style");	
		        }
		 });
	}else{
		
		 
		        	 var result = "";
		        	 var addlocation = 'addlocation0'
		        	 result = result +"<input type='hidden' name='locationsMaxRowNumber'  id='locationsMaxRowNumber' value='1' />";
		             result = result +"<input type='hidden' id='locationsRowCount' value='1' />";
		        	 result = result +"<table class='timesheetAttachment'  id='locationTable' cellpadding='0' cellspacing='0'>";
		        	 result = result +"<tr id='divAttachmentTsRow0'>";
		        	 result = result +"<td>";
		        	 result = result +"<div class='positionRelative'>";
		        	 result = result +'<input type="text"  placeholder="Location" id="0addlocation" onclick="addLocationAuto(\'0addlocation\',0,\'0longitude\',\'0latitude\')" class="form-control joblocinput"  data-validation="required" field-name="Location" name="locationInfo[0].locationName">';
		        	 result = result +'<div class="fa fa-refresh btn btn-primary refresh-icon" title="Clear" onClick="refreshLocation(0);"></div>';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].country" id="0country">';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].state" id="0state">';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].city" id="0city">';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].zipcode" id="0zipcode">';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].longitude" id="0longitude">';
		        	 result = result +'<input type="hidden"  name="locationInfo[0].latitude" id="0latitude">';
		        	 result = result +"</td>";
		        	 result = result +"</div>";
		        	 result = result +"<td><i class='fa fa-plus' id='addLocRow' onclick='addNewLocation()'></i></td>";
		        	 result = result +"</tr>";
		        	 result = result +"</table>";
		        	
		        	 $("#jobLocationsList").empty();
		        	 $("#jobLocationsList").append(result);
		
	}
	
	 
	
	 
	 
	
}
</script>

<script>
function addNewLocation(){
	
	var checkMaxTsRowNum = $("#locationsMaxRowNumber").val();
	var tbl = document.getElementById("locationTable");
	var maxRowNum = parseInt($("input[name = 'locationsMaxRowNumber']").val());
	$("input[name = 'locationsMaxRowNumber']").val(maxRowNum + 1);
	$("input[id = 'locationsRowCount']").val(maxRowNum + 1);
	
	var row = tbl.insertRow(maxRowNum);
	row.setAttribute("id", "divLocationRow" + maxRowNum);
	
	
	
	var cell0 = row.insertCell(0);
	var divDocTypeTdStr = document.getElementById("divLocTd1").innerHTML;
	divDocTypeTdStr = divDocTypeTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell0.innerHTML = divDocTypeTdStr;
	
	var cell2 = row.insertCell(1);
	var divDocCheckTdStr = document.getElementById("divLocTd2").innerHTML;
	divDocCheckTdStr = divDocCheckTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell2.innerHTML = divDocCheckTdStr;
}
</script>


<script>
function removeLocationRow(rowId){
	$('#locationErrMsg'+rowId).find('p.jquery_form_error_message').remove();
	
	 document.getElementById("divLocationRow"+rowId).style.display = 'none';
	 document.getElementById("divLocationRow"+rowId).value= '';
	 var  rId = "divLocationRow"+rowId;
	  var r = document.getElementById("locationTable").rows[rId];
     var count = 2*(r.cells.length);
	    for(i=0;i<=count;i++){
	        r.deleteCell(0);
	    } 
	    
	/* var addedDocumentsRowCount = document.getElementById("locationsRowCount").value;
	 $("input[id = 'locationsRowCount']").val(parseInt(addedDocumentsRowCount) - 1);
	 
	 var documentsRow = document.getElementById("locationsRowCount").value;
	 
	 document.getElementById("divLocationRow"+rowId).style.display = 'none';
	 document.getElementById("divLocationRow"+rowId).value= '';
	 var  rId = "locationsRowCount"+rowId;
	  var r = document.getElementById("locationsRowCount").rows[rId];
	  
     var count = 2*(r.cells.length);
	    for(i=0;i<=count;i++){
	        r.deleteCell(0);
	    }  */
}
</script>

<script>
function refreshLocation(rowId){
	  $("#"+rowId+"addlocation").val('');
	  $("#"+rowId+"country").val('');
	  $("#"+rowId+"state").val('');
	  $("#"+rowId+"city").val('');
	  $("#"+rowId+"zipcode").val('');

}


function deleteSavedLocation(id){
	var elem =document.getElementById("location"+id);
	elem.parentNode.removeChild(elem);
	
	var savedlocationsMaxRowNumber = parseInt($("input[name = 'savedlocationsMaxRowNumber']").val());
	$("input[name = 'savedlocationsMaxRowNumber']").val(savedlocationsMaxRowNumber - 1);
	
	var savedlocMaxRowNumber = parseInt($("input[name = 'savedlocationsMaxRowNumber']").val());
	if(savedlocMaxRowNumber == 0){
		$("#0addlocation").removeAttr("data-validation-optional");
		$("#0addlocation").attr("data-validation","required");
	}
	
	
}
</script>

<script>
function addLocationAuto(locationId,rowId,longtitudeId,latitudeId) {
	initialize1(locationId,rowId);
    geolocate1(rowId);
}
function initialize1(locationId,rowId,latitudeId,longtitudeId) {
	autocomplete = new google.maps.places.Autocomplete((document.getElementById(locationId)), {
		types: ['geocode'],
  
    });
   
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
  place = autocomplete.getPlace();
  var address = {};
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    switch (addressType) {
       case "route":
    	 address.name=place.address_components[i].long_name;
    	 break;
      case "locality":
        address.city = place.address_components[i].long_name;
        break;
      case "administrative_area_level_1":
        address.state = place.address_components[i].long_name;
        break;
      case "country":
        address.country = place.address_components[i].long_name;
        break;
      case "postal_code":
        address.zipcode = place.address_components[i].long_name;
        
    }
  }
 
  $("#"+rowId+"country").val(address.country).change();
  $("#"+rowId+"state").val(address.state).change();
  $("#"+rowId+"city").val(address.city).change();
  $("#"+rowId+"zipcode").val(address.zipcode).change();
  $("#"+rowId+"latitude").val(place.geometry.location.lat());
  $("#"+rowId+"longitude").val(place.geometry.location.lng());
  
});
}
function geolocate1(rowId) {
	if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var geolocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
       	     $("#"+rowId+"latitude").val(latitude);
             $("#"+rowId+"longitude").val(longitude);
            autocomplete.setBounds(new google.maps.LatLngBounds(geolocation, geolocation));
        });
    }
}
</script>
<script>
	var ckbox = $('#incentivized');
	$('.nofJoinersDIv').on('click',function () {
        if (ckbox.is(':checked')) {
            $("#nofJoiners").css('display','block');
            $('#incentivized').val('Y');
            document.getElementById("requirementIncentive[0].joiners").removeAttribute('data-validation-optional');
            document.getElementById("requirementIncentive[0].joinersAmount").removeAttribute('data-validation-optional');
        } else {
            $("#nofJoiners").css('display','none');
            $('#incentivized').val('N');
            document.getElementById("requirementIncentive[0].joiners").setAttribute('data-validation-optional','true');
            document.getElementById("requirementIncentive[0].joinersAmount").setAttribute('data-validation-optional','true');
            $("#nofJoinersAddRow").empty();
        }
    });
	
	/* if ($("#incentivized").attr('checked') == true){
		$("#nofJoiners").css('display','block');
	} */
	
</script>
<!-- <script>
$("#percentageAmount").click(function(){
	$(this).toggleClass("btn-default btn-primary");
	$("#flatAmount").toggleClass("btn-primary btn-default");
});
$("#flatAmount").click(function(){
	$(this).toggleClass("btn-default btn-primary");
	$("#percentageAmount").toggleClass("btn-primary btn-default");
});
</script> -->
<script type="text/javascript">
function getJoinersAmountType(e){
	var amountType = e.value;
	 if(amountType == 'flat'){
		 $(e).removeClass('btn btn-default');
		 $(e).attr('class','btn btn-primary');
		 document.getElementById(e.id.split('.')[0]+".percentageAmount").className = "btn btn-default";
		 document.getElementById(e.id.split('.')[0]+".joinersAmountType").value = "flat";
		 
		 var rId = e.id.split('[').pop().split(']').shift();
		 var x = document.getElementById("nofJoinersDiv["+rId+"].rowId");
		 $(x).find("p.jquery_form_error_message").remove();
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").value = "";
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").removeAttribute('field-name');
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").setAttribute("field-name", "Amount");
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").removeAttribute('placeholder');
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").setAttribute("placeholder", "Amount");
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").removeAttribute('data-validation');
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").setAttribute("data-validation", "required validate_Space validate_length length1-10 validate_int");
		 
	 } 
	 
	 if(amountType == 'percentage'){
		 $(e).removeClass('btn btn-default');
		 $(e).attr('class','btn btn-primary');
		 document.getElementById(e.id.split('.')[0]+".flatAmount").className = "btn btn-default";
		 document.getElementById(e.id.split('.')[0]+".joinersAmountType").value = "percentage";
		 
		 /* var rId = e.id.split('[').pop().split(']').shift();
		 var x = document.getElementById("nofJoinersDiv["+rId+"].rowId");
		 x.querySelectorAll("p.jquery_form_error_message")[1].remove(); */
		 var rId = e.id.split('[').pop().split(']').shift();
		 var x = document.getElementById("nofJoinersDiv["+rId+"].rowId");
		 $(x).find("p.jquery_form_error_message").remove();
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").value= "";
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").removeAttribute('field-name');
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").setAttribute("field-name", "Percentage");
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").removeAttribute('placeholder');
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").setAttribute("placeholder", "Percentage");
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").removeAttribute('data-validation');
		 document.getElementById("requirementIncentive["+rId+"].joinersAmount").setAttribute("data-validation", "required validate_Space validate_between between0-100 validate_int");
	 }
}

function addJoinersRow(){
	var rowNo = Number($("#joinersCount").val()) + Number(1);
	var result = "";
	result = result +'<div class="col-md-9 col-md-offset-3 mt" id="nofJoinersDiv['+rowNo+'].rowId">';
	result = result +'<div class="row">';
	result = result +'<div class="col-md-3">';
	result = result +'	<input type="text" class="form-control" id="requirementIncentive['+rowNo+'].joiners" name="requirementIncentive['+rowNo+'].joiners" value="" placeholder="Joiners" field-name="Joiners" data-validation="required validate_Space validate_length length1-5 validate_int">';
	result = result +'</div>';
	result = result +'<div class="col-md-9">';
	result = result +'	<div class="row">';
	result = result +'  	<div class="col-md-4 padding_right">';
	result = result +'		<div class="btn-group">';
	result = result +'	    	<button type="button" class="btn btn-primary" id="requirementIncentive['+rowNo+'].flatAmount" value="flat" onClick="getJoinersAmountType(this)">Flat</button>';
	result = result +'	    	<button type="button" class="btn btn-default" id="requirementIncentive['+rowNo+'].percentageAmount" value="percentage" onClick="getJoinersAmountType(this)">%</button>';
	result = result +'	    	<input type="hidden"  id="requirementIncentive['+rowNo+'].joinersAmountType" name="requirementIncentive['+rowNo+'].joinersAmountType" value="flat">';
	result = result +'		</div>';
	result = result +'	</div>';
	result = result +'  <div class="col-md-6 paddingLeft paddingRight">';
	result = result +'		<input type="text" class="form-control" value="" id="requirementIncentive['+rowNo+'].joinersAmount" name="requirementIncentive['+rowNo+'].joinersAmount" placeholder="Amount" field-name="Amount" data-validation="required validate_Space validate_length length1-10 validate_int">';
	result = result +'  </div>';
	result = result +'	<div class="col-md-1 text-center mt10">';
	result = result +'		<i class="fa fa-times colorRed cursorPointer" onclick="removeJoinersRow('+rowNo+');"></i>';
	result = result +'	</div>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
	
	$("#nofJoinersAddRow").append(result);
	$("#joinersCount").val(rowNo);
}

function removeJoinersRow(rowNo){
	document.getElementById("nofJoinersDiv["+rowNo+"].rowId").remove();
}

function getDeletedIds(id){
	var idList = $("#arrDeletedIds").val();
	if(idList == ''){
		$("#arrDeletedIds").val(id);
	}else{
		$("#arrDeletedIds").val(idList + "," + id);
	}
	
}
</script>
<jsp:include page="../masterFooter.jsp" />