<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link href="resources/css/register-style.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="resources/js/summernote.js"></script>
<link href="resources/css/summernote.css" rel="stylesheet" />
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="resources/js/editor.js"></script>
<link href="resources/css/editor.css" type="text/css" rel="stylesheet"/>


<style>
.clsDatePicker {
	z-index: 100000 !important;
}

.project_tabs {
    border:none !important;
    }
    
.disabledLink
{
	color: #333;
	text-decoration : none;
}
</style>


<script type="text/javascript">
	function validateProjectSequeneNumber() {
		var ProjectSequeneNumber = document.getElementById("ProjectSequeneNumber").value.trim();
		 //space value  consider this condition 
		if(ProjectSequeneNumber.match(/^[^\s-](.*)*$/) !== null){
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocations.id}';
		$('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
		if (ProjectSequeneNumber != null && ProjectSequeneNumber != "") {
			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/validateProjectSequeneNumberById?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="+ ProjectSequeneNumber+"&orgLocId="+orgLocId,
						success : function(data) {
							if (data == true) {
								$("input#ProjectSequeneNumber").after("<p class='jquery_form_error_message'>"+ ProjectSequeneNumber+ " is Already Existed</span>");
								document.getElementById("ProjectSequeneNumber").setAttribute('record-exist', 'yes');
								document.getElementById("ProjectSequeneNumber").setAttribute('record-exist-errorMsg',ProjectSequeneNumber+ ' is Already Existed');
								$('input#ProjectSequeneNumber').removeAttr( "record-exist record-exist-errorMsg");
							} else {
								$('input#ProjectSequeneNumber').removeAttr("record-exist record-exist-errormsg");
							}
						},
					});
	     	}
		}
	}
	
	
	var fileCounter = 0;
	function addFileUpload(){
		/* var result = '';
		result = result + '<div class="pull-left" style="float:left; width:325px; margin-right:15px; margin-bottom:15px; border:1px solid #ddd; padding:5px;">';
		result = result + '<div style="width:125px; margin-bottom:5px;"><lable class="paddingRight small">Is Confidential ?</label> <input type="checkbox" name="isclient" value="N" class="pull-right" /></div>';
		
		result = result + '<div style="margin:0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff;" >';
		result = result + ' <input id="file" name = "project_files" type="file" class="pull-left" /> <a id="Button' + fileCounter + '"  value="" onclick = "RemoveFileUpload(this)"><i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i></a>';
		result = result + '</div>';
		
		result = result + '</div>';
		fileCounter++;
		 $("#FileUploadContainer").append(result); */
		
// 		$('<div>').attr('style','width:150px; margin:0; padding:0 0 5px 0;').append('<lable class="paddingRight">Is Confidential ?</label>' + '<input type="checkbox" name="isclient" value="N" class="pull-right" />').appendTo($('#FileUploadContainer'));
		$('<div>').attr('style','float:left; width:325px; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#367fa9; color:#fff; ').append('<input id="file' +  '" name = "project_files' + '" type="file" class="pull-left" />' +
	            '<a id="Button' + fileCounter + '' +
	            'value="" onclick = "RemoveFileUpload(this)"><i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i></a>').appendTo($('#FileUploadContainer'));

	    		fileCounter++; 
	    		
	}
	function RemoveFileUpload(div)
	{
		 document.getElementById("FileUploadContainer").removeChild(div.parentNode);
	}
</script>

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Project</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo;<a href="<c:url value='/projectDashboard'/>"> Projects List</a>
			&raquo; Create Project
	</section>
	<div class="content-area clearfix">
		<div id="loading"></div>
		
		<div class="newPrDetails newPrDetailsPMO"> 
      		<div class="newPrTabs">
   			  <ul class="newPrTabsMenu">
				<li class="current" onclick="getProjectDetails();"><a href="#tab-a" data-toggle="tab">Details</a></li>
				<li><a href="#tab-b" data-toggle="tab" id="assignedMembers" onclick="getAssignedMembers();">Assign Members</a></li>
				<li><a href="#tab-c" data-toggle="tab" id="additionalDetails" onclick="getAdditionalDetails();">Additional Details</a></li>
				<div class="clearfix mb"></div>
			  </ul>
			 <input type="hidden" name="projectId" id=projectId value=""/>
			  <div class="clearfix"></div>
		
		<div class="project_tabs">
		<div class="clearfix form-area form-horizontal toggle-div remove-div  newPrTabs_inn mt" id="tab-a">
			<form:form id="project_form" name="project_form" method="post"
				enctype="multipart/form-data">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">

						<div class="form-group" id="ProjectIdDiv">
							<label class="col-lg-3">Project Id</label>
							<div class="col-lg-9">
								<input type="text" value="" id="ProjectSequeneNumber"
									name="ProjectSequeneNumber"
									data-validation="validate_Space validate_length length2-50"
									data-validation-optional="true" class="form-control" placeholder="Project Id"
									onblur="validateProjectSequeneNumber();">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"> Date <span class="text-danger">*</span></label>
							<div class="col-md-9">
								<div class="row">
									<div class="col-lg-6 positionRelative" id="StartDateDiv">
										<input type="text" class="form-control" value="" id="StartDate" name="StartDate" field-name="Start Date" placeholder="Start Date" data-validation="required" readonly="readonly" > <span
											class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
									</div>
									<div class="col-lg-6 positionRelative" id="endDateDiv">
										<input type="text" class="form-control" value="" id="EndDate"
											name="EndDate" field-name="End Date" placeholder="End Date" readonly="readonly">
										<span class="input-group-addon input-groupAddon"><i
											class="fa fa-calendar"></i></span>
									</div>
									<span class="hidden">Enter Project Id</span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3" for="Project Id">Status<span
								class="text-danger">*</span></label>
							<div class="col-md-9 select-field">
								<select class="form-control" name="Satatus" id="Satatus" onchange="getStatus();" field-name="Status" data-validation="required">
									<option value="">Select Status</option>
									<option>Not Started</option>
									<option>In-Progress</option>
									<option>Completed</option>
									<option>On-Hold</option>
									<option>Dropped</option>
								</select> <span class="hidden">Select Status</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Timesheet Frequency <span
								class="text-danger">*</span></label>
							<div class="col-md-9" id="chosen-frequency">
								<select class="form-control chosen-select"
									id="timeSheetFrequency" name="timeSheetFrequency"
									onchange="removeFrequencyValidation();"
									field-name="Timesheet Frequency" data-validation="required">
									<option value="">--Timesheet Frequency-</option>
									<option value="Weekly">Weekly</option>
									<option value="Bi-Weekly">Bi-Weekly</option>
									<option value="Semi-Monthly">Semi-Monthly</option>
									<option value="Monthly">Monthly</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowFreelanceDevelopers"> Allow Freelance Developers </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowRemoteWork"> Allow Remote Work </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowOverTime" id="isAllowOverTime" onchange="allowOverTime();"> Allow Over Time </label>
								</div>
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="input-group">
									<div class="input-group-btn selp-custom-min tblminwidth-125" id="flatPercentageId" >
										<select class="form-control chosen-select"  name="flatPercentageId">
											<option value="flat">FLAT</option>
											<option value="percentage">PERCENTAGE</option>
										</select>
									</div>
									<input type="number" min="0" max="100" step="0.5" value="0"
										id="flatPercentage" name="flatPercentage" maxlength="5"
										class="form-control" data-validation-optional="true" onblur="">
								</div>
							</div>
						</div>


					</div>

					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
						<c:if test="${empty user_List}">
							<p style="font-size: 11px; color: red;" class="text-muted small">
								<i class="fa fa-info-circle"></i>&nbsp;All Admin's in Org will have access to Edit/Approve/Reject TimeSheet
							</p>
						</c:if>

						<div class="form-group">
							<label class="col-md-3"> Project Name <span
								class="text-danger">*</span>
							</label>
							<div class="col-md-9">

								<input type="text" class="form-control" name="ProjectName" placeholder="Project name" data-validation="required validate_Space validate_length length2-250"
									field-name="Project Name" value=""> <span class="hidden">Project Name</span> <span class="hidden" >Enter Project Id</span>

							</div>
						</div>

						<c:if test="${not empty user_List}">
							<div class="form-group">
								<label class="col-md-3" for="Project Manager">Manager <span
									class="text-danger">*&nbsp;</span></label>
								<div class="col-md-9">
									<div class="form-icon-min" id="chosen-level1Manager">
										<select class="form-control chosen-select"
											name="level1Manager" id="level1Manager" field-name="Manager"
											data-validation="required" onchange="getLevel2Approvers();">
											<option value="">-- Select Level1 Manager --</option>
											<c:forEach var="option" items="${user_List}">
												<option value="${option.id},${option.userType}">${option.firstName}
													${option.lastName}</option>
											</c:forEach>
										</select> <span class="btn btn-default formic1 addLevel"
											id="level2Toggle" onclick="getLevel2Approvers();">Add
											Level-II</span>
									</div>
									<span class="hidden">Select Project Manager</span>
								</div>
							</div>
							<div class="form-group level-second" id="level2Div">
								<label class="col-md-3" for="Level-II">Level-II</label>
								<div class="col-md-9">
									<select class="form-control chosen-select" name="level2Manager"
										id="level2Manager">
									</select>
								</div>
							</div>
						</c:if>

						<sec:authorize
							access="hasAnyRole('ROLE_ASSIGN_EMPLOYEE_TO_PROJECT')">
							<div class="form-group">
								<label class="col-md-3">Assign Team &nbsp;<i
									class=" text-info fa fa-info-circle"
									title="Before Assinging Team Project Start Date & Manager is Mandatory"></i></label>

								<div id="assignTeamSavingDiv" style="display: none;"></div>
								<input type="hidden" name="team" value="" />

								<div class="col-md-9">
									<span class="icon-inside"> <i class="fa fa-search-plus"></i>
										<input type="text" value="" placeholder="Select Team"
										id="assignTeamModal" class="form-control"
										data-target="#assignTeam" data-toggle="modal"
										onclick="getTeamMembers();" readonly="readonly">
									</span> <span data-target="#addTeam" data-toggle="modal"> </span> <span
										class="hidden">Select Team</span>
									<!-- Modal content Assign Team-->
									<div role="dialog" id="assignTeam" class="modal fade">
										<div class="modal-dialog modal-lg">
											<div class="modal-content">
												<div class="modal-header">
													<button data-dismiss="modal" class="close" type="button">x</button>
													<h4 class="modal-title">Assign Employees</h4>
												</div>
												<div class="modal-body clearfix">
													<div class="form-horizontal row custom-form hidden">
														<div class="col-xs-12 col-md-12 col-lg-12">
															<div class="input-group">
																<input type="search" class="form-control"
																	placeholder="Name / Email" id="searchByName"> <span
																	class="input-group-addon bt-primary"
																	onclick="getTeamMembers();"><i
																	class="fa fa-search"></i></span>
															</div>
														</div>
													</div>
													<div class="modal-body-custom mt clearfix over"
														id="employeesList"></div>
												</div>
												<div class="modal-footer">
													<button data-dismiss="modal" class="btn btn-primary"
														type="button" onclick="assignEmployee();">Assign</button>
													<button data-dismiss="modal" class="btn btn-default"
														type="button" onclick="assignEmployeeCancel();">Cancel</button>
												</div>
											</div>
										</div>
									</div>
									<!-- Assign Team modal popup ends -->
								</div>
							</div>
						</sec:authorize>

						<div class="form-group">
							<label class="col-md-3">Invoice Frequency <span
								class="text-danger">*</span></label>
							<div class="col-md-9" id="chosen-InvoiceFrequency">
								<select class="form-control chosen-select" id="invoiceFrequency"
									name="invoiceFrequency" onchange="removeInvoiceFrequencyValidation();"
									field-name="Invoice Frequency" data-validation="required">
									<option value="">-Invoice Frequency-</option>
									<option value="Weekly">Weekly</option>
									<option value="Bi-Weekly">Bi-Weekly</option>
									<option value="Semi-Monthly">Semi-Monthly</option>
									<option value="Monthly">Monthly</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isTimesheetAttachmentRequired"> Timesheet Attachment Required </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowOffshoreResource"> Allow Offshore Resource </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isTimeEntryAfterDeadline"> Time Entry after Deadline </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowBidding"> Allow Bidding </label>
								</div>
							</div>
						</div>
					</div>





				</div>
				<!--  div row end -->
<!-- dont remove the code because next implement code Laxman -->
				<!--  div row start -->
				<!-- <div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h5>Budget Details</h5>
						<div class="form-group" id="">
							<label class="col-md-3"> </label>
							<div class="col-md-9"></div>
						</div>
					</div>
				</div> -->


          
				<div class="row">
					<%-- <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">

						<div class="form-group" id="EstimatedHoursDiv">
							<label class="col-md-3">Estimated hours</label>
							<div class="col-md-9">
								<input type="number" value="" id="EstimatedHours" min="1"
									max="99999" name="EstimatedHours" class="form-control"
									data-validation-optional="true" class="form-control" onblur="">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Budget Approver</label>
							<div class="col-md-9" id="chosen-budgetapprover">
								<select class="form-control chosen-select" id="budgetApprover"
									name="budgetApprover" onchange="" field-name="Budget Approver">

									<option value="">-- Budget Approver --</option>
									<c:forEach var="option" items="${user_List}">
										<option value="${option.id},${option.userType}">${option.firstName}
											${option.lastName}</option>
									</c:forEach>

								</select>
							</div>
						</div>


					</div> --%>


					<!-- <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">

						<div class="form-group" id="EstimatedBudgetDiv">
							<label class="col-md-3">Estimated Budget</label>
							<div class="col-md-9">
								<div class="input-group">
									<div class="input-group-btn selp-custom-min"
										id="estimatedBudgetId">
										<select class="form-control chosen-select">
											<option value="c2c">INR</option>
											<option value="w2">USD</option>
										</select>
									</div>
									<input type="number" value="" id="EstimatedBudget"
										name="EstimatedBudget" class="form-control"
										data-validation="validate_Space validate_length length2-50"
										data-validation-optional="true" class="form-control" onblur="">
								</div>
							</div>
						</div>
					</div> -->


					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12  txtEditor_p">
						<h5>Description</h5>
						<textarea id="txtEditor" name="ProjectDescription" value="" class="form-control newContrtextarea" data-validation="validate_length length2-5000" data-validation-optional="true"></textarea>
<!-- 						<textarea maxlength="5000" cols="80" rows="10" id="ProjectDescription" name="ProjectDescription"></textarea> -->

					</div>

					<!--  Documents Section -->
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="row mt mb10">
						<h5 class="col-md-1 paddingRight">Documents</h5>
						<div class="col-md-10 paddingLeft">
						<input id="Button1" type="button" value="Add Document" onclick="addFileUpload()" class="btn btn-sm btn-primary" />
						</div>
							</div>
						<div id="FileUploadContainer">
							<!--FileUpload Controls will be added here -->
						</div>
						<br />
						<!--<input type="file" name="project_files" id="project_files" multiple="multiple"/>-->
					</div>
					<div class="clearfix col-md-12">
						<span class="paddingRight"><input type="checkbox" id="isMailToTeamMembers" name="isMailToTeamMembers">&nbsp;Mail to team members</span> 
						<span class="paddingLeft"><input type="checkbox" id="isMailToProjectManager" name="isMailToProjectManager">&nbsp;Mail to manager</span>
					</div>
					

				</div>

				<!--  div row end -->


				<div class="form-footer clearfix row mt">
					<div class="col-lg-12">
						<div class="pull-right">
							<input type="button" value="Save" id="save_project"
								onclick="saveProject();flushSaveProject()"
								class="btn btn-primary"> <input type="button"
								value="Cancel"
								onClick="javascript:window.location.href = '<c:url value='/projectDashboard'/>'"
								class="btn btn-default ml">
						</div>
					</div>
				</div>



			</form:form>
		</div>
	   <jsp:include page="projectAssignedMembers.jsp" />
   </div>
		</div>
   	</div>
</div>
</section>
<!-- Content Area Ends-->
<script>
function removeFrequencyValidation(){
	$('#chosen-frequency').find('p.jquery_form_error_message').remove();
}

function allowOverTime(){
	if ($('#isAllowOverTime').is(":checked"))
	{
		$('#flatPercentageId').attr('style','visibility:visible');
		$('#flatPercentage').attr('style','visibility:visible');
	}else{
		$('#flatPercentageId').attr('style','visibility:hidden');
		$('#flatPercentage').attr('style','visibility:hidden');
	}
	
}
function removeInvoiceFrequencyValidation(){
	$('#chosen-InvoiceFrequency').find('p.jquery_form_error_message').remove();
}
</script>
<script>
function getStatus(){
	$('#endDateDiv').find('p.jquery_form_error_message').remove(); 
	var status = $("#Satatus").val();
	if(status != null && status != "" && status != "undefined" && status == "Completed"){
		 $('#EndDate').removeAttr('data-validation-optional');
		 $('#EndDate').attr("data-validation","required"); 
	 }else{
		 $('#EndDate').removeAttr('data-validation');
		 $('#EndDate').attr("data-validation-optional","true"); 
	 }
}

</script>

<!-- Add Client Model DialogBox ends-->

<jsp:include page="../masterFooter.jsp" />
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


<script>
	$(document).ready(function() {
		$('#flatPercentageId').attr('style','visibility:hidden');
		$('#flatPercentage').attr('style','visibility:hidden');
		$('#ProjectDescription').summernote({height:300});
		
			
		var savedProjectId = '${savedProjectId}';
		if(savedProjectId != null && savedProjectId != '' && savedProjectId != 'undefined'){
			$("#projectId").val(savedProjectId);
			
			var isProjectDetailsFilled = '${isProjectDetailsFilled}';
			if(isProjectDetailsFilled == 'N'){
				var assignedMembers = document.getElementById("assignedMembers");
				if(assignedMembers){
					assignedMembers.href = "#";
					assignedMembers.className = "disabledLink";
				}
			}
			var isProjectMembersFilled = '${isProjectMembersFilled}';
			if(isProjectMembersFilled == 'N'){
				var additionalDetails = document.getElementById("additionalDetails");
				if(additionalDetails){
					additionalDetails.href = "#";
					additionalDetails.className = "disabledLink";
				}
			}
			
		}else{
			var assignedMembers = document.getElementById("assignedMembers");
			if(assignedMembers){
				assignedMembers.href = "#";
				assignedMembers.className = "disabledLink";
			}
			
			var additionalDetails = document.getElementById("additionalDetails");
			if(additionalDetails){
				additionalDetails.href = "#";
				additionalDetails.className = "disabledLink";
			}
		}
		
		$("#tab-a").show()
	    $(".newPrTabsMenu a").click(function(event) {
	        event.preventDefault();
	        $(this).parent().addClass("current");
	        $(this).parent().siblings().removeClass("current");
	        var tab = $(this).attr("href");
	        $(".newPrTabs_inn").not(tab).css("display", "none");
	        $(tab).fadeIn();
	    });
	});
</script>




<script type="text/javascript">
	function saveProject() {
		validateProjectSequeneNumber();
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		
		var ee = $("#txtEditor").val($('.Editor-editor').html()); 

		$('#addTask').find('p.jquery_form_error_message').remove();
		if ($('#project_form').validate(false, validationSettings)) {
			var frm = $('#project_form').serializeFormJSON();
			 
// 			frm.ProjectDescription = projDesc;
			var jsonData = JSON.stringify(frm);
		    var form = $('#project_form')[0];
		    var formData = new FormData(form);
		  	formData.append("json",jsonData)

			//It will escape all the special characters
		//	var jsonData = encodeURIComponent(con);
			
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_project").attr("disabled", "disabled");
			$("#save_project").addClass('button').val('Processing..');
     		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');


			$.ajax({
						 type : "POST",
						 data: formData,
		  	    	     contentType : 'application/json; charset=utf-8',
		  		         processData: false,
		  		         contentType:false, 
		  	    	     dataType : 'json',
						url : sourceleadUrl+ "/storeProject?${_csrf.parameterName}=${_csrf.token}",
						success : function(result) {
							if (result == projectId') {
								 $("#projectId").val(projectId);
								  $("#loading").removeAttr("style");
								 var assignedMembers = document.getElementById("assignedMembers");
								 $("assignedMembers").removeClass("disabledLink");
								 assignedMembers.href="#tab-b"; 
								 assignedMembers.click();
							}

						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#project_form', function() {
		$("#project_form").showHelpOnFocus();
		$("#project_form").validateOnBlur(false, validationSettings);
	});
</script>
<script type="text/javascript">
	function flushSaveProject() {
		$("#save_project").removeAttr("disabled");
	}
</script>


<script>
function getProjectDetails(){
	var projectId = document.getElementById("projectId").value;
	if(projectId){
		var sourceleadUrl ='${sourceleadUrl}';
/*         $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getOnboardTemplateById?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId, 
	        success: function(json) {
	        	
	        	 $("input[name=templateName]").val(json.templateName);
	        	 $("textarea[name=description]").val(json.description);
	        	 
	        	 $("#onboardTasksList").empty();
		        
	        },
	    });  */
	}
}
</script>

<script>
function getAssignedMembers(){
	var projectId = document.getElementById("projectId").value;
	if(projectId){
		
	}
}
</script>

<script>
function getAdditionalDetails(){
	var projectId = document.getElementById("projectId").value;
	if(projectId){
		
	}
}
</script>


<!-- this script is for Get the TeamMembers functionality Start -->
<script type="text/javascript">
	function getTeamMembers() {
		var level1Manager = $("#level1Manager").val(); 
		var level1ManagerArray = level1Manager.split(',');
		level1Manager = level1ManagerArray[0];
		
		var level2Manager = $("#level2Manager").val();
		var startDate     =    $("#StartDate").val();
		var searchByName = $("#searchByName").val();
		var sourceleadUrl = '${sourceleadUrl}';
		if(startDate != null  && startDate != '' && level1Manager != null && level1Manager != '' ){
			
			if(level2Manager != null){
				level2Manager = level2Manager;
			}else{
				level2Manager = "";
			}
			if(level1Manager != null){
				level1Manager = level1Manager;
			}else{
				level1Manager = "";
			}
			
			var orgLocId = '${SourceLeadWeb_orgLocations.id}';
			
			  $("#assignTeamModal").attr('data-target','#assignTeam');
			$.ajax({
						type : "POST",
						url : sourceleadUrl + "/getEmployeesListByExcludingManagers?${_csrf.parameterName}=${_csrf.token}&level1Manager="+level1Manager+"&level2Manager="+level2Manager+"&searchByName="+searchByName+"&orgLocId="+orgLocId,
						success : function(data) {
							var json = JSON.parse(data);
							var output = "";
							output = output+' <div class="wrap_employeeTable">';
							output = output + ' <table cols="0" id="assignEmployeePagination" class="clearfix table-bordered">';
							output = output + ' <thead>';
							output = output + ' <tr>';
				            output = output+' <th><input type="checkbox" name="emp" id="projectChekId" onclick="totalCheck();"></th>';
							output = output + ' <th>Name</th>';
							output = output + ' <th>Email</th>';
							output = output + ' <th>Role</th>';
							output = output + ' <th>Start Date</th>';
							output = output + ' <th>End Date</th>';
							output = output + ' </tr>';
							output = output + ' </thead>';
							output = output + ' <tbody>';
							for (var i = 0; i < json.length; i++) {
								var projectStartDate = $('#StartDate').val();
								var projectEndDate = $('#EndDate').val();
								if(projectEndDate == null ||  projectEndDate == "" || projectEndDate == "undefined"){
									projectEndDate = "";
								}
								var opt = json[i];
								var role = '';
								if (opt.userType != null
										&& opt.userType != ''
										&& opt.userType != 'undefined') {
									role = opt.userType;
								} else {
									role = 'Employee';
								}
	
								var count = 1;
								$(".myCheckbox").each(
												function() {
													var localId = this.id;
													
													if ($(this).is(':checked')) {
														var startDate = $("#startDate"+ localId).val();
														var endDate = $("#endDate"+ localId).val();
														result = result+ "<input type='hidden'  name='employeeId"+count+"' id='employeeId"+count+"' value='"+localId+"' />";
														result = result+ "<input type='hidden'  name='employeestartDate"+count+"' id='employeestartDate"+count+"' value='"+startDate+"' />";
														result = result+ "<input type='hidden'  name='employeeendDate"+count+"' id='employeeendDate"+count+"' value='"+endDate+"' />";
														count++;
													}
												});
	
								if (opt.user.userType != 'Admin') {
	
									output = output + "<tr>";
									output = output+ "<td><input class='myCheckbox' id="+opt.id+" type='checkbox' name='team"+i+"' value="+opt.id+"></td>";
									if(opt.user.lastName != null && opt.user.lastName !='Undefined' && opt.user.lastName != ''){
										output = output + "<td><span class='tbldata-dotdot-sm' title='"+opt.user.firstName+"&nbsp;"+opt.user.lastName+"'>" + opt.user.firstName+ "&nbsp;" + opt.user.lastName+ "</span></td>";	
									}else{
										output = output + "<td><span class='tbldata-dotdot-sm' title='"+opt.user.firstName+"'>" + opt.user.firstName+ "</span></td>";
									}
									
									output = output + "<td><span class='tbldata-dotdot-vsm' title='"+opt.user.email+"'>" + opt.user.email+ "</span></td>";
									output = output + "<td><span class='tbldata-dotdot-vsm' title='"+role+"'>" + role + "</span></td>";
									output = output+ "<td><span class='' title='"+projectStartDate+"'><div class='input-group tblminwidth-150'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
	
									output = output+ "<input  id='startDate"+ opt.id+ "' type='text' name='Date' value='"+ projectStartDate+ "' class='form-control clsDatePicker' readonly='readonly'>";
									output = output + "</div></span></td>";
									output = output+ "<td><div class='input-group tblminwidth-150'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
									output = output + "<input id='endDate" + opt.id + "'  value='"+projectEndDate+"' type='text' name='Date' value='' class='form-control clsDatePicker' readonly='readonly'>";
									output = output + "</div></td>";
									output = output + "</tr>";
								}
							}
							output = output + ' </tbody>';
							output = output + ' </table>';
							output = output+' </div>';
							$("#employeesList").empty();
							$("#employeesList").append(output);

							assignEmployeespagination();	
						  //StartDate And EndDate Pickers
						    
					       if(json.length > 0){
						    	for (var i = 0; i < json.length; i++) {
									var opt = json[i];
									var projectStartDate = $("#StartDate").val();
									var projectEndDate = $("#EndDate").val();
									
									var datepickerStartDate = toDate("#StartDate");
									datepickerStartDate.setDate(datepickerStartDate.getDate() + 1);
									
									$('#startDate'+opt.id).datepicker({
										dateFormat : finalDateFormatProjectSave,
										changeMonth: true,
										changeYear: true, 
										minDate:projectStartDate,
										maxDate:projectEndDate,
										onSelect : function(selected) {
											var actualId = $(this).attr('id').split('startDate')[1];
											var dt = toDate('#startDate'+actualId);
											//var id = $(this).attr('id');
											dt.setDate(dt.getDate() + 1);
											$("#endDate"+actualId).datepicker("option", "minDate", dt);
										}
									});
		
									$('#endDate'+opt.id).datepicker({
										dateFormat : finalDateFormatProjectSave,
										changeMonth: true,
										changeYear: true, 
										maxDate:projectEndDate,
										minDate: projectStartDate,
										onSelect : function(selected) {
											var actualId = $(this).attr('id').split('endDate')[1];
											var dt = toDate('#endDate'+actualId);
											//var id = $(this).attr('id');
											dt.setDate(dt.getDate() - 1);
											$("#startDate"+actualId).datepicker("option", "maxDate", dt);
										}
									});
								}
					      }
							
							
						},
						error : function(e) {
						}
					});
		}else{
			
			$("#assignTeamModal").removeAttr('data-target');
			
		}
		// $('#add_employee').attr('data-dismiss','modal');
		$('#firstName').val('');
		$('#lastName').val('');
		$('#employeeEmail').val('');
	}
</script>
<!-- this script is for Get the TeamMembers functionality Ends -->

<script>
function totalCheck() {
	var checked = document.getElementById("projectChekId").checked  
	
    if(checked) {
        $('.myCheckbox').each(function() {
            this.checked = true;   
        });
    }else{
        $('.myCheckbox').each(function() {
            this.checked = false;      
        });         
    } 
}
</script>

<script>
function assignEmployeespagination(){  
    $('#assignEmployeePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10000, 15, 20, -1], [10000, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"aaSorting": [[1,'desc']],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
		
    } );
}
</script>

<!-- this script is for Assign Employee functionality Start -->
<script>
	function assignEmployee() {

		//var table = $('#assignEmployeePagination').DataTable();

		$('#assignEmployeePagination').DataTable().search('').draw();
		var result = "";
		var count = 1;
		$(".myCheckbox").each(function() {
							var localId = this.id;
							if ($(this).is(':checked')) {
								$(this).removeAttr('emp-assigned');
								$(this).attr('emp-assigned','true');
								var startDate = $("#startDate" + localId).val();
								var endDate = $("#endDate" + localId).val();
								result = result
										+ "<input type='hidden'  name='employeeId"+count+"' id='employeeId"+count+"' value='"+localId+"' />";
								result = result
										+ "<input type='hidden'  name='employeestartDate"+count+"' id='employeestartDate"+count+"' value='"+startDate+"' />";
								result = result
										+ "<input type='hidden'  name='employeeendDate"+count+"' id='employeeendDate"+count+"' value='"+endDate+"' />";
								count++;
							}
						});
		result = result
				+ "<input type='hidden' name='employeeMaxCount' id='employeeMaxCount' value='"+count+"'/>";
		$("#assignTeamSavingDiv").empty();
		$("#assignTeamSavingDiv").append(result);
	}
</script>
<!-- this script is for Assign Employee functionality Ends -->

<script type="text/javascript">
	function flushProject() {
		$("#save_client").removeAttr("disabled");
		$('#orgName').val('');
		$('#companyWebsite').val('');
		$(".error").removeClass("error");
		$('#newClient').find('p.jquery_form_error_message').remove();
	}
</script>
<script>
function assignEmployeeCancel(){
	$(".myCheckbox").each(function() {
		var empAssigned = $(this).attr('emp-assigned');
		if(empAssigned != null && empAssigned != 'undefined' && empAssigned != '' && empAssigned == 'true'){
		}
		else{
			$(this).prop('checked', false);
		}
		
    });	
}
</script>


<script>
	$(function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatProjectSave = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatProjectSave = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatProjectSave = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatProjectSave = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatProjectSave = 'yy-dd-mm'; 
		  }
		$('#StartDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				//var dt = new Date(selected);
				var dt = $(this).datepicker('getDate', '+1d');
				//var dt = toDate("#StartDate");
				dt.setDate(dt.getDate() + 1);
				$("#EndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		});
		$('#EndDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				//var dt = new Date(selected);
				var dt = $(this).datepicker('getDate', '-1d');
			//	var dt = toDate("#EndDate");
				dt.setDate(dt.getDate() - 1);
				$("#StartDate").datepicker("option", "maxDate", dt);
				addValidClass();
				//this is section is for compare project end date with current date
				//if project end date is less than current date, the project state should be COMPLETED
				var dummyDate = $(this).val();
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth()+1; //January is 0!
                var yyyy = today.getFullYear();
                if(dd<10){
                    dd='0'+dd
                } 
                if(mm<10){
                    mm='0'+mm
                } 
                
                var today = yyyy+'-'+mm+'-'+dd;
                
                var proStartDateTP = today.split("-");
                var d1 = new Date(proStartDateTP[0], proStartDateTP[1] - 1, proStartDateTP[2]);
                
                var proEndDateTP = dummyDate.split("-");
                if(locationDate.match("yyyy-MM-dd")){
                	 var d2 = new Date(proEndDateTP[0], proEndDateTP[1] - 1, proEndDateTP[2]);
      		    }if(locationDate.match("dd-MM-yyyy")){
      		    	var d2 = new Date(proEndDateTP[2], proEndDateTP[1] - 1, proEndDateTP[0]);
      		    }if(locationDate.match("MM-dd-yyyy")){
      		    	var d2 = new Date(proEndDateTP[2], proEndDateTP[0] - 1, proEndDateTP[1]);
      		    }if(locationDate.match("yyyy-dd-MM")){
      		    	var d2 = new Date(proEndDateTP[0], proEndDateTP[2] - 1, proEndDateTP[1]);
      		    }
                
				if(d1 > d2){
					$("#Satatus").empty();
					$('#Satatus').append('<option >Completed</option>');
				}else{
					$("#Satatus").empty();
					$('#Satatus').append('<option >In-Progress</option>');
					$('#Satatus').append('<option >Not Started</option>');
					$('#Satatus').append('<option >Completed</option>');
					$('#Satatus').append('<option >On-Hold</option>');
					$('#Satatus').append('<option >Dropped</option>');
				}
			}
		});

	});
</script>
<script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("StartDate").value;
		 if(StartDate){
			 $('#StartDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
		 
		 var EndDate = document.getElementById("EndDate").value;
		 if(EndDate){
			 $('#endDateDiv').find('p.jquery_form_error_message').remove(); 
		 }
}
</script>

<script>
	function getStartDatePickerById(id, incrementor) {
		$('#' + id).datepicker({
			dateFormat : 'yy-mm-dd',
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
			}
		});
	}
</script>
<script>
	function getEndDatePickerById(id, incrementor) {
		$('#' + id).datepicker({
			dateFormat : 'yy-mm-dd',
			onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
			}
		});
	}
</script>
<script type="text/javascript">
function getLevel2Approvers(){
	$('#chosen-level1Manager').find('p.jquery_form_error_message').remove();
	var currentApproverId = document.getElementById("level1Manager").value;
	var currentUser = currentApproverId.split(',');
	var currentUserId = currentUser[0];
	var currentUserType = currentUser[1];
	var count = 1;
	$(".myCheckbox").each(
					function() {
						var localId = this.id;
						document.getElementById(localId).checked = false; 
	});
	$(".myCheckbox").val('');
	
	$("#level2Manager").empty();    
	$('#level2Manager').val('').trigger('chosen:updated');
	if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType != 'Admin'){
	    $("#level2Toggle").show();
		getChildLevel1Approvers(currentUserId);
	}else if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType == 'Admin'){
	    $("#level2Toggle").hide();   
	    $("#level2Div").removeClass("level-second-active");
	}
	
	
} 
</script>
<script type="text/javascript">
function getChildLevel1Approvers(currentApproverId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(currentApproverId != null && currentApproverId != ''  && currentApproverId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="+currentApproverId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-Select Manager-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#level2Manager").empty();
			        $("#level2Manager").append(output);
			        $('#level2Manager').trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
	 }else{
		 getApproversLevel2();
		
	 }
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
<!-- <script src="resources/js/chosen.jquery.min.js"></script>  -->
<script type="text/javascript">
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
				});
</script>

<!-- script for converting dateformat in datepicker -->

<!-- script for dateformat in datepicker End -->

<script>
	$(document).ready(function() {
		$("#txtEditor").Editor();
		$("#txtEditor").find(".Editor-editor").data-validation('validate_length length2-5000');
		$("#txtEditor").find(".Editor-editor").data-validation-optional('rule-required',true);
	});
 </script>