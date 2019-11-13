<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

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
	<div id="loading"></div>
	<div class="content-area clearfix" id="dataFileImportDiv">
		<form:form id="upload_timesheet_form" name="upload_timesheet_form" method="post" enctype="multipart/form-data">
		<div class="clearfix form-area form-horizontal toggle-div remove-div mt">
			<div class="row minHyewtriuwe rwrghek	fghjqwehjgfwej tkjreight">
				<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
					<!-- Download File button -->
					<div class="clearfix mb row">
					<div class="col-xs-3 col-md-3">
						<input type="text" class="form-control" placeholder="Header Index" id="headerPosition" name="headerPosition"  field-name="Header index" data-validation="required validate_Space validate_length length1-6 validate_int">
					</div>
					<div class="col-xs-3 col-md-3 paddingLeft">
							<select class="selectpicker form-control" id="timesheetMode" name="timesheetMode" field-name="Upload mode" data-validation="required" >
								<option value="" disabled selected>--- Select Mode ---</option>
								<!-- <option value="BiMonthly">Bi-Monthly</option>
								<option value="Monthly">Monthly</option>  -->
								<option value="MonthlyWeekWise">Monthly-Week wise</option>
								<!-- <option value="Weekly">Weekly</option> -->
							</select>
					</div>
						<div class="col-xs-3 col-md-3 paddingLeft">
							<a href="#"
								class="btn btn-success btn-block" title="Download Timesheet Template" > <i class="fa fa-download"></i>&nbsp;&nbsp;
								Template
							</a>
						</div>
						<div class="col-xs-3 col-md-3 paddingLeft">
							<div class="btn-group btn-group-justified">
								<div class="btn-group">
									<input type="button" class="btn btn-primary" id="upload_button"
										name="upload_button" onclick="fetchFieldsWithMatchedColumn();"
										value="Import">
								</div>
								<div class="btn-group">
									<input type="reset" class="btn btn-danger" value="Reset"
										onClick="javascript:window.location.href = '<c:url value='${appPath}/uploadTimesheet'/>'">
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
									class="fa fa-paperclip" title="Attach file"></i> <br /> <input
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
					<div id="errorDownloadDiv" style="display: none"></div>
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
		</form:form>
	</div>
	<div id="dataFieldsDiv" style="display: none">
	</div>
	<%-- <jsp:include page="importJobs.jsp"></jsp:include> --%>
	
</section>
<div class="success_pop" id="selector11" style="display: none;">
    <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
</div>

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
	var headerPosition = $("#headerPosition").val();
        if (($('#upload_timesheet_form').validate(false, validationSettings))){
      	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
          	$.ajax({
    			type: "POST",
    			url: sourceleadUrl+"/dataMigrationCommon?"+tokenParamName+"="+tokenValue+"&filePath="+fileUrl+"&uploadType="+uploadType+"&timesheetMode="+timesheetMode+"&headerPosition="+headerPosition, 
    		    success: function(data) {
    		    	$("#dataFileImportDiv").attr("style","display:none");
    		    	$('#dataFieldsDiv').removeAttr("style");
    		    	$('#dataFieldsDiv').append(data);
    		    },
    		});
          	$('#loading').removeAttr("style");
        }
        else{
      	  return false;	
        }
}

$('body').on('blur', '#upload_timesheet_form', function() {
	$("#upload_timesheet_form").showHelpOnFocus();
	$("#upload_timesheet_form").validateOnBlur(false, validationSettings);
	
});
</script>

<script type="text/javascript">
function cancelFileImport(){
	location.replace(sourceleadUrl+"/uploadTimesheet");
}
</script>


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

