<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<style type="text/css">
.center-div {
	width: 300px;
	height: 30px;
	margin: 10px 220px 0;
}

.center-div input {
	border: none;
	background: transparent
}
</style>


<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Import Timesheets</h2>
		</div>
		<div class="remove_pop" id="selector" style="display: none;">
			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
		</div>
		<div class="pull-right brud-crum">
			<a href="#">Home</a> &raquo; <a href="${appPath}/timesheet">Timesheets</a>
			&raquo; Import Timesheets
		</div>
	</section>
	<div class="content-area clearfix" id="jobFileImportDiv">
		<div
			class="clearfix form-area form-horizontal toggle-div remove-div mt">
			<div class="row minHyewtriuwe rwrghek	fghjqwehjgfwej tkjreight">
				<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
					<!-- Download File button -->
					<div class="clearfix mb row">
					<div class="col-xs-3 col-md-3">
						<div id="timesheetModeDiv">
							<select class="selectpicker form-control" id="timesheetMode" name="timesheetMode">
								<option value="" disabled selected>--- Select Mode ---</option>
								<option value="BiMonthly">Bi-Monthly</option>
								<option value="Monthly">Monthly</option>
								<option value="MonthlyWeekWise">Monthly-Week wise</option>
								<option value="Weekly">Weekly</option>
							</select>
					</div>
					</div>
						<div class="col-xs-6 col-md-6">
							<a href="#"
								class="btn btn-success btn-block"> <i class="fa fa-download"></i>&nbsp;&nbsp;Download
								Timesheets Template
							</a>
						</div>
						<div class="col-xs-3 col-md-3">
							<div class="btn-group btn-group-justified">
								<div class="btn-group">
									<input type="button" class="btn btn-primary" id="upload_button"
										name="upload_button" onclick="fetchFieldsWithMatchedColumn();"
										value="Import">
								</div>
								<div class="btn-group">
									<input type="reset" class="btn btn-danger" value="Reset"
										onClick="javascript:window.location.href = '<c:url value='${appPath}/timesheet'/>'">
								</div>
							</div>
						</div>
					</div>
					<!-- Attach File button -->

					

					<div class="dropzone">
						<div class="clearfix"></div>
						<div class="file-upload clearfix text-center">
							<div class="col-md-12" id="">
								<label class="bg-default"> <i aria-hidden="true"
									class="fa fa-paperclip" title="Attach file" onclick="alertDialog();"></i> <br /> <input
									type="file" name="file" field-name="File Upload"
									style="display: none;" id="uploadBtn"
									data-validation="required validate_importFile">
								</label>
								<div class="clearfix"></div>
								<label id="newUploadFile"></label> 
								<input type="text" hidden readonly id="dataFilePath" value="">
							</div>
							<div class="center-div">
								<input id="uploadFile" placeholder="" disabled="disabled"
									style="width: 100%;" />
							</div>
						</div>

						<!--  Downloaded File show here -->
						<div class="col-xs-3 alert alert-success fade in hidden"
							style="position: absolute; top: 10px;" id="fileAttached">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<div>File Holder</div>
						</div>
						<!--  Downloaded File show here -->
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
					<div class="bgInfo bg-info clearfix text-center">
						<span><i class="fa fa-question" aria-hidden="true"></i></span> <span
							class="info-msg clearfix mt">
							<p class="clearfix mt">
								According to this template, the Users need to <span
									class="text-danger"><strong>Import Required
										Fields</strong></span>
							</p>
							<p>
								<label><i class="fa fa-user"></i>&nbsp;&nbsp;Emp Name</label> <label><i
									class="fa fa-envelope"></i>&nbsp;&nbsp;Email</label> <label><i
									class="fa fa-calendar"></i>&nbsp;&nbsp;Start Date</label> <label><i
									class="fa fa-calendar"></i>&nbsp;&nbsp;End Date</label><label><i
									class="fa fa-clock-o"></i>&nbsp;&nbsp;Status</label>
							</p>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="dataFieldsDiv" style="display: none">
	</div>
	<%-- <jsp:include page="importJobs.jsp"></jsp:include> --%>
	
</section>


<script type="text/javascript">
document.getElementById("uploadBtn").onchange = function () {
	$('#newUploadFile').text($(this).val().replace(/C:\\fakepath\\/i, ''));
	fileUpload("uploadBtn", "dataMigration");
};
</script>

<script type="text/javascript">
function fetchFieldsWithMatchedColumn(){
	var fileUrl = $("#dataFilePath").val();
	var timesheetMode = $("#timesheetMode").val();
	var uploadType = "timesheetUpload";
	if(fileUrl != "" && fileUrl != null && fileUrl != 'undefined' && timesheetMode != "" && timesheetMode != null && timesheetMode != 'undefined'){
		$('#timesheetModeDiv').find('p.jquery_form_error_message').remove();
		$.ajax({
			type: "POST",
		    url: sourceleadUrl+"/dataMigrationCommon?"+tokenParamName+"="+tokenValue+"&filePath="+fileUrl+"&uploadType="+uploadType+"&timesheetMode="+timesheetMode, 
		    success: function(data) {
		    	$("#jobFileImportDiv").attr("style","display:none");
		    	$('#dataFieldsDiv').removeAttr("style");
		    	$('#dataFieldsDiv').append(data);
		    },
		});
	}else{
		$('#timesheetModeDiv').find('p.jquery_form_error_message').remove();
		if(fileUrl == "" || fileUrl == null || fileUrl == 'undefined'){
			$('#newUploadFile').html("<p class='jquery_form_error_message'>File selection is required</p>");
		}
		if(timesheetMode == "" || timesheetMode == null || timesheetMode == 'undefined'){
			$('#timesheetModeDiv').append("<p class='jquery_form_error_message'>Timesheet Mode is required</p>");
		}
	}
	
}
</script>

<script type="text/javascript">
function cancelFileImport(){
	location.replace(sourceleadUrl+"/uploadTimesheet");
}

function alertDialog(){
	alert('Should maintain top row as header');
}
</script>


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

