<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<script type="text/javascript">
var uploadflag= true;
		function getSubmissionsUploadedHistory() {
			var refreshId = setInterval(function(){
				var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';
				if(uploadflag == true){
					xlsupload.getUploadInprogressProgressBar("Submissions",dateFormat);
				  }else{
					     clearInterval(refreshId);
						 $(".browseProgress").hide();
						 $("#upload_button").removeAttr("disabled");
						 xlsupload.getUploadInprogressProgressBar("Submissions",dateFormat);
				  }
			},1000);
		 }
</script>

	<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Import Applicants</h2>
      </div>
    </section>
    	<div id="loading"></div>
	<div class="content-area clearfix" id="dataFileImportDiv">
		<form:form id="upload_candidate_form" name="upload_candidate_form" method="post" enctype="multipart/form-data">
			<div class="clearfix form-area form-horizontal toggle-div remove-div mt">
			<div class="row minHyewtriuwe rwrghek	fghjqwehjgfwej tkjreight">
				<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
					<!-- Download File button -->
					<div class="clearfix mb row">
					<div class="col-xs-3 col-md-3">
						<input type="text" class="form-control" placeholder="Header Index" id="headerPosition" name="headerPosition"  field-name="Header index" data-validation="required validate_Space validate_length length1-6 validate_int">
					</div>
					
						<div class="col-xs-5 col-md-5">
							<a href="<c:url value='${appPath}/downloadRequirenetSubmissionTemplateByRequirementId/${requirementId}'/>"
								class="btn btn-success btn-block" title="Submission Template"> <i class="fa fa-download"></i>&nbsp;&nbsp; Submission Template
							</a>
						</div>
						<div class="col-xs-4 col-md-4">
							<div class="btn-group btn-group-justified">
								<div class="btn-group">
									<input type="button" class="btn btn-primary" id="upload_button"
										name="upload_button" onclick="fetchFieldsWithMatchedColumn(${requirementId});"
										value="Import">
								</div>
								<div class="btn-group">
									<input type="reset" class="btn btn-danger" value="Reset"
										onClick="resetSubmissionTemplate();">
								</div>
							</div>
						</div>
					</div>
					
					<!-- Attach File button -->

					<div class="dropzone">
						<div class="clearfix"></div>
						<div class="file-upload clearfix text-center">
							<div class="col-md-12" id="uploadDocDiv">
								<label class="bg-default"> <i aria-hidden="true"
									class="fa fa-paperclip" title="Attach file"></i> <br /> <input
									type="file" name="file" field-name="File Upload"
									style="display: none;" id="uploadBtn"
									data-validation="required validate_xls_importFile">
								</label>
								<div class="clearfix"></div>
								<label id="newUploadFile"></label> 
								<input type="text" hidden readonly id="dataFilePath" value="">
							</div>
							<!-- <div class="center-div">
								<input id="uploadFile" placeholder="" disabled="disabled"
									style="width: 100%;" />
							</div>  -->
							 <p class="text-danger small" style="font-size: 12px; display: none;" id="errorImage" ><i class="fa fa-exclamation-triangle" ></i><b>&nbsp;Document size should be less than 10 MB</b></p>
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
								According to this template, the user need to <span
									class="text-danger"><strong>Import Required
										Fields</strong></span>
							</p>
							<p>
								<label><i class="fa fa-user"></i>&nbsp;&nbsp;First Name</label> <label><i
									class="fa fa-envelope"></i>&nbsp;&nbsp;Email</label> <label><i
									class="fa fa-mobile"></i>&nbsp;&nbsp;Phone Number</label>
							</p>
						</span>
					</div>
				</div>
				
			</div>
		</div>
		</form:form>
	</div>
	<div id="dataFieldsDiv" style="display: none"></div>
	
	<div class="project_tabs_inn_table project_tabs_inn_table_a datatable_filter dataSeaTop">
		<h5 class="mt25" id="uploadXlsHistoryDivTitle" hidden>Recent Uploads History</h5>
	    	<table id="uploadXlsHistoryDiv" class="display" cellspacing="0" width="100%"></table>
		</div>
	
<script type="text/javascript" src="/resources/js/xlsupload.js"></script>	
<script type="text/javascript">
function cancelFileImport(){
	location.replace(sourceleadUrl+"/uploadCandidateForm");
}

function resetSubmissionTemplate(){
	$('#upload_candidate_form')[0].reset();
	$('#newUploadFile').text('');
}
</script>
	
<script type="text/javascript">
function fetchFieldsWithMatchedColumn(requirementId){
	var fileUrl = $("#dataFilePath").val();
	var uploadType = "submmissionTemplateUpload";
	var headerPosition = $("#headerPosition").val();
        if (($('#upload_candidate_form').validate(false, validationSettings))){
      	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
          	$.ajax({
    			type: "POST",
    		    url: sourceleadUrl+"/dataMigrationCommon?"+tokenParamName+"="+tokenValue+"&filePath="+fileUrl+"&uploadType="+uploadType+"&headerPosition="+headerPosition+"&requirementId="+requirementId, 
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

$('body').on('blur', '#upload_candidate_form', function() {
	$("#upload_candidate_form").showHelpOnFocus();
	$("#upload_candidate_form").validateOnBlur(false, validationSettings);
	
});
</script>

<script type="text/javascript">
document.getElementById("uploadBtn").onchange = function () {
	  var sizeinbytes = document.getElementById('uploadBtn').files[0].size;
	  if(sizeinbytes > 10485760){
		  document.getElementById("errorImage").style.display="";
		  $('#newUploadFile').text('');
		  $("#upload_button").attr("disabled","disabled");
		  $('#uploadDocDiv').find('p.jquery_form_error_message').remove();
	  }else{
		  document.getElementById("errorImage").style.display="none";
		  $('#newUploadFile').text($(this).val().replace(/C:\\fakepath\\/i, ''));
		  fileUpload("uploadBtn", "dataMigration");
		  $("#upload_button").removeAttr("disabled");
		  $('#uploadDocDiv').find('p.jquery_form_error_message').remove();
	  }
	
};
</script>

<script type="text/javascript">
function cancelFileImport(){
	var requirementId = '${requirementId}';
	importApplicantsToJob(requirementId);
}
</script>


