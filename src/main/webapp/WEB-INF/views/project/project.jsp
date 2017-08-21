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
						
						 <input type="hidden" name="projectId" id=projectId value=""/>
						 <input type="hidden" name="projectUUID" id="projectUUID" value=""/>

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
									<option value="Not Started">Not Started</option>
									<option value="In-Progress">In-Progress</option>
									<option value="Completed">Completed</option>
									<option value="On-Hold">On-Hold</option>
									<option value="Dropped">Dropped</option>
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
									<label> <input type="checkbox" value="Y" name="isAllowFreelanceDevelopers" id="isAllowFreelanceDevelopers" > Allow Freelance Developers </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowRemoteWork" id="isAllowRemoteWork" > Allow Remote Work </label>
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
										<select class="form-control chosen-select"  name="flatPercentageId" id="flatPercentId">
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
											<%-- <c:forEach var="option" items="${user_List}">
												<option value="${option.id},${option.userType}">${option.firstName}
													${option.lastName}</option>
											</c:forEach> --%>
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
									<label> <input type="checkbox" value="Y" name="isTimesheetAttachmentRequired" id="isTimesheetAttachmentRequired"> Timesheet Attachment Required </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowOffshoreResource" id="isAllowOffshoreResource"> Allow Offshore Resource </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isTimeEntryAfterDeadline" id="isTimeEntryAfterDeadline"> Time Entry after Deadline </label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3"></label>
							<div class="col-md-9">
								<div class="checkBox">
									<label> <input type="checkbox" value="Y" name="isAllowBidding" id="isAllowBidding"> Allow Bidding </label>
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
						<textarea id="txtEditor" name="ProjectDescription"  id="projectDescription"  value="" class="form-control newContrtextarea" data-validation="validate_length length2-5000" data-validation-optional="true"></textarea>
<!-- 						<textarea maxlength="5000" cols="80" rows="10" id="ProjectDescription" name="ProjectDescription"></textarea> -->

					</div>
					
					<div id="timesheetDocumentsId">
			       		<div class="timesheet_week_comm invoiceDocs clearfix">
			       			<span>
			         			 <div id="savedProjectDocuments" class="col-md-6 mt"></div>
			         		</span>
			           </div>
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
						<span><input type="checkbox" id="isMailToProjectManager" name="isMailToProjectManager">&nbsp;Mail to manager</span>
					</div>
					

				</div>

				<!--  div row end -->


				<div class="form-footer clearfix mt">
					<div class="col-lg-12">
						<div class="pull-right">
							<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/projectDashboard'/>'" class="btn btn-danger ml">
							<input type="button" value="Save & Continue" id="save_project" onclick="saveProject();flushSaveProject()" class="btn btn-primary">
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
		var projectUUID = '${projectUUID}';
		if(savedProjectId != null && savedProjectId != '' && savedProjectId != 'undefined'){
			$("#projectId").val(savedProjectId);
			$("#projectUUID").val(projectUUID);
			
			getProjectDetails();
			
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
			getProjectLevel1Approvers();
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
		var savedProjectId = '${savedProjectId}';
		if(savedProjectId == null || savedProjectId == '' || savedProjectId == 'undefined'){
			validateProjectSequeneNumber();
		}
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
     		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');


			$.ajax({
						 type : "POST",
						 data: formData,
		  	    	     contentType : 'application/json; charset=utf-8',
		  		         processData: false,
		  		         contentType:false, 
		  	    	     dataType : 'json',
						url : sourceleadUrl+ "/storeProject?${_csrf.parameterName}=${_csrf.token}",
						success : function(Projects) {
								 $("#projectId").val(Projects.id);
								 $("#projectUUID").val(Projects.projectUUID);
								 $("#loading").removeAttr("style");
								 var assignedMembers = document.getElementById("assignedMembers");
								 $("assignedMembers").removeClass("disabledLink");
								 assignedMembers.href="#tab-b"; 
								 assignedMembers.click();

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
		$("#FileUploadContainer").empty();
		
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl ='${sourceleadUrl}';
        $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getProjectDetailsByProjectId?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId, 
	        success: function(json) {
	        	
	        	var level1ApproverUser = '';
	        	var level2ApproverUser = '';
	        	
	        	if(json.projectSequeneNumber != null && json.projectSequeneNumber != 'undefined'){
	        		 $("input[name=ProjectSequeneNumber]").val(json.projectSequeneNumber);
	        		 $("input[name=ProjectSequeneNumber]").attr("readonly","readonly");
	        		 $('#ProjectSequeneNumber').removeAttr('onblur');
	        	}
	        	
	        	 $("input[name=ProjectName]").val(json.projectName);
	        	 $("input[name=StartDate]").val(json.startDateStr);
	        	 $("input[name=EndDate]").val(json.endDateStr);
	        	 
	        	 
	        	 
	           var projectStatus = '';
	           if(json.projectStatus == 'NOTSTARTED'){
	        	   projectStatus = "Not Started"
	           }else if(json.projectStatus == 'INPROGRESS'){
	        	   projectStatus = "In-Progress"
	           }else if(json.projectStatus == 'COMPLETED'){
	        	   projectStatus = "Completed"
	           }else if(json.projectStatus == 'ONHOLD'){
	        	   projectStatus = "On-Hold"
	           }else if(json.projectStatus == 'DROPPED'){
	        	   projectStatus = "Dropped"
	           }
	           
	           
        	   $("select[name=Satatus]").val(projectStatus);
        	   
        	   $('#timeSheetFrequency').val(json.timesheetFrequency).trigger('chosen:updated');
        	   $('#invoiceFrequency').val(json.invoiceFrequency).trigger('chosen:updated');
        	   
        	   if(json.allowFreeLance == 1){
        		   document.getElementById("isAllowFreelanceDevelopers").checked = true;
        	   }
        	   
        	   if(json.allowRemote == 1){
        		   document.getElementById("isAllowRemoteWork").checked = true;
        	   }
        	   
        	   if(json.allowOvertime == 1){
        		   document.getElementById("isAllowOverTime").checked = true;
        		   $('#flatPercentageId').attr('style','visibility:visible');
        		   $('#flatPercentage').attr('style','visibility:visible');
        		   $('#flatPercentId').val(json.overtimeRateType).trigger('chosen:updated');
        		   $("input[name=flatPercentage]").val(json.flatPercent);
        		   
        	   }
        	   
        	   
        	   if(json.isTimesheetAttachmentRequired == 1){
        		   document.getElementById("isTimesheetAttachmentRequired").checked = true;
        	   }
        	   
        	   if(json.allowOffShore == 1){
        		   document.getElementById("isAllowOffshoreResource").checked = true;
        	   }
        	   
        	   if(json.allowTimeEntryAfterDeadLine == 1){
        		   document.getElementById("isTimeEntryAfterDeadline").checked = true;
        	   }
        	   
        	   if(json.allowBidding == 1){
        		   document.getElementById("isAllowBidding").checked = true;
        	   }
        	   
        	   $("textarea[name=ProjectDescription]").val(json.description);
        	   $(".Editor-editor").val(json.description);
        	   
        	   
        	   if(json.projectDocumentsList.length > 0){
        		   var result = '';
        		   for(var i=0; i<json.projectDocumentsList.length; i++){
        			   var opt = json.projectDocumentsList[i];
        			  
        			     result = result +"<strong id='projectDocument"+opt.id+"'>";
      	    		     result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\""+sourceleadUrl+"/downloadProjectDoc?porjectDocumentId="+opt.id+"\"></a></i>";
      					 result = result +"<input type='button' value='"+opt.fileName+"'>";
      					 result = result +"</a>";
      					 result = result +"<input class='dele' type='button' onClick='removeProjectDocuments("+opt.id+","+json.id+");' >";
      					 result = result +"</strong>";
        		   }
        		   
        		   
        		   $("#savedProjectDocuments").empty();
      			   $("#savedProjectDocuments").append(result);
        	   }
      			   
      			   for(var i=0; i<json.approverHierarchyList.length; i++){
	        		  var opt = json.approverHierarchyList[i];
	        		  if(opt.level == "1"){
	        			  level1ApproverUser = opt.projectApproverUserId;
	        			  getProjectLevel1Approvers(opt.projectApproverUserId);
	        		  }
	        		  
	        		  if(opt.level == "2"){
	        				 $("#level2Toggle").hide(); 
	        				 document.getElementById("level2Div").removeAttribute("class");
	        				 document.getElementById('level2Div').className = 'form-group level-second level-second-active';
	        				 level2ApproverUser = opt.projectApproverUserId;
	        		  }
	        	  }  
      			   
      			   if(level1ApproverUser != '' && level2ApproverUser != ''){
      				  getChildLevel1Approvers(level1ApproverUser,level2ApproverUser);
      			   }
      			 
      			   
      			   $("#loading").removeAttr("style");
      			 
        	
        	   
		        
	        },
	    });  
	}
}
</script>

<script>
function removeProjectDocuments(documentId,projectId){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/terminateProjectDoc?${_csrf.parameterName}=${_csrf.token}&porjectDocumentId="+documentId, 	   
		    success: function(data) {
		    	projectDocuments(projectId);
		    },
     });

}
</script>

<script>
function getAdditionalDetails(){
	var projectId = document.getElementById("projectId").value;
	if(projectId){
		
	}
}
</script>

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
function getLevel2Approvers(level1ApproverUser){
	var currentApproverId = '';
	$('#chosen-level1Manager').find('p.jquery_form_error_message').remove();
	if(level1ApproverUser != null && level1ApproverUser != '' && level1ApproverUser != 'undefined'){
		currentApproverId = level1ApproverUser;
	}else{
		currentApproverId = document.getElementById("level1Manager").value;
	}
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
function getChildLevel1Approvers(currentApproverId,level2ApproverUserId){
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
			        		var selected = '';
			        		if(level2ApproverUserId != null && level2ApproverUserId != 'undefied' && level2ApproverUserId != '' && level2ApproverUserId == opt.id){
			        			selected = "selected";
			        		}
			        		output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.firstName+" "+opt.lastName+"</option>";
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
function getProjectLevel1Approvers(approverUserId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#level1Manager").empty();
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getProjectLevel1Approvers?${_csrf.parameterName}=${_csrf.token}",
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Level1 Manager --</option>";
		        	for (var i=0; i<json.length; i++)
					{
		        		var selected = "";
		        		var opt = json[i];
		        		if(approverUserId != null && approverUserId != 'undefied' && approverUserId != '' && approverUserId == opt.id){
		        			selected = "selected";
		        		}
			        		output = output + "<option value='" +opt.id+ ","+opt.userType+"' "+selected+">"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	
			        $("#level1Manager").append(output);
			        $('#level1Manager').trigger("chosen:updated");
		        }
		    });
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