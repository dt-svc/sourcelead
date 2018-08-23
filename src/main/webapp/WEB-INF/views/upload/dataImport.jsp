
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- Content Area -->
<!-- <section class="content-header clearfix">
  <div class="pull-left">
    <h2>Jobs Import</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; Jobs &raquo; Jobs Import</div>
</section> -->
<div class="content-area content-new clearfix" id="dataFieldsDiv">

	<div class="col-xs-12 col-md-12 col-lg-12 mt">
		<div class="well">
			<p>
				<strong>Please Note</strong>
			</p>
			<ul class="liststyels-custom">
				<li>Please map the fields appropriately to import the data.</li>
				<li>Choose the excel columns from the dropdown to map with the
					SourceLead Fields.</li>
				<li>Click on Import to start the importing.</li>
			</ul>
			
			<%-- <c:choose>
			  <c:when test="${importStatus == 'success'}">
			    <span class="text-green">Data imported successfully</span>
			  </c:when>
			  <c:otherwise>
			    <c:if test="${!empty importStatus}"> 
					<span class="colorRed">Some data is failed to import <a download class="colorRed glyphicon glyphicon-download-alt" target="_blank" href="${importStatus}" title="Download error file"></a></span>
				</c:if>
			  </c:otherwise>
			</c:choose> --%>
			
			
		</div>
	</div>
	<div class="clearfix"></div>
	<form:form id="dataMigration_form" name="dataMigration_form">
	<div class="jobsimort-header clearfix">
		<h4 class="col-md-10 mt5">Fields</h4>
		<div class="col-md-2">
			<div class="pull-right">
				<c:if test="${uploadType == 'submmissionTemplateUpload'}">
				<button type="button" class="btn btn-sm btn-primary mr"
					onclick="saveSubmissionTemplateDataFields();">Import</button>
				</c:if>
				
				<c:if test="${uploadType != 'submmissionTemplateUpload'}">
				<button type="button" class="btn btn-sm btn-primary mr"
					onclick="saveDataFields();">Import</button>
				</c:if>
				
				<button type="button" class="btn btn-sm btn-primary"
					onclick="cancelFileImport();">Cancel</button>
			</div>
		</div>
	</div>
	<div class="form-horizontal myForm row">
	<input type="hidden" name="projectUUID" value="${projectUUID}">
	<input type="hidden" name="projectClientUUID" value="${projectClientUUID}">
		<div class="clearfix">
			<!-- Select Dropdowns -->
			<div class="col-xs-12 col-sm-6 col-md-12 col-lg-12">
			
			<c:if test="${empty timesheetMode}"> 
				<jsp:include page="dataImportFormFields.jsp"></jsp:include>
			</c:if>
			<c:if test="${!empty timesheetMode}">
				<jsp:include page="dataImportTimeSheetFormFields.jsp"></jsp:include>
			</c:if>  
			<c:if test="${uploadType eq 'assetUpload'}">
			<jsp:include page="dataImportAssetFormFields.jsp"/>
			</c:if>
			<c:if test="${uploadType eq 'immigrationUpload'}">
			<jsp:include page="dataImportImmigrationFormFields.jsp"/>
			</c:if>
			<c:if test="${uploadType eq 'lcaUpload'}">
			<jsp:include page="dataImportLcaFormFields.jsp"/>
			</c:if>
				<input type="hidden"  readonly id="exportFilePath" name="exportFilePath" value="${exportFilePath}">
				<input type="hidden"  readonly id="uploadType" name="uploadType" value="${uploadType}">
				<input type="hidden"  readonly id="headerPosition" name="headerPosition" value="${headerPosition}">
				<input type="hidden"  readonly id="timesheetMode" name="timesheetMode" value="${timesheetMode}">
				<input type="hidden"  readonly id="importStatus" name="importStatus" value="${importStatus}">
			</div>
			<!-- Select Dropdowns -->
		</div>
	</div>
	</form:form>
</div>

<script type="text/javascript">
	function saveDataFields() {
		if ($('#dataMigration_form').validate(false, validationSettings)) {
			var formData = getFormData1("dataMigration_form");
			var inviteEmail = '';
			if($("#inviteEmail").val() != null && $("#inviteEmail").val() != 'undefined' && $("#inviteEmail").val() != ''){
				inviteEmail = $("#inviteEmail").val();
			}
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
				type : "POST",
				data : formData,
				processData : false,
				contentType : false,
				url : sourceleadUrl + "/getDataByMatchedFields?"
						+ tokenParamName + "=" + tokenValue+"&inviteEmail="+inviteEmail,
				success : function(data) {
					$("#dataFileImportDiv").attr("style","display:none");
			    	$('#dataFieldsDiv').removeAttr("style");
			    	$('#dataFieldsDiv').empty();
			    	$('#dataFieldsDiv').append(data);
			    	var importStatus = $('#importStatus').val();
			    	if(importStatus == "success"){
			    		$('#selector11').removeClass('bg-red').addClass('success_pop');
          				$("#msgDiv").html("Data imported successfully");
          				$('#errorDownloadDiv').removeAttr("style");
          				$('#errorDownloadDiv').prepend("<span class='text-green'>Data imported successfully</span>");
          			}else if(importStatus == "fail"){
          				$('#selector11').removeClass('success_pop').addClass('bg-red');
          				$("#msgDiv").html("Should upload file with valid data");
          				$('#errorDownloadDiv').removeAttr("style");
          				$('#errorDownloadDiv').prepend("<span class='colorRed'>Should upload file with valid data</span>");
          			}else if(importStatus != ''){
          				$('#selector11').removeClass('success_pop').addClass('bg-red');
          				$("#msgDiv").html("Some data is failed to import");
          				$('#errorDownloadDiv').removeAttr("style");
          				$('#errorDownloadDiv').prepend("<span class='colorRed'>Some data is failed to import <a download class='colorRed glyphicon glyphicon-download-alt' target='_blank' href='"+importStatus+"' title='Download error file'></a></span>");
          			}
			    	$('#newUploadFile').text('');
			    	
			    	var uploadType = $('#uploadType').val();
			    	if(uploadType == 'jobUpload'){
			    		$('#upload_job_form')[0].reset();
			    	}if(uploadType == 'assetUpload'){
			    		$('#upload_asset_form')[0].reset();
			    	}if(uploadType == 'timesheetUpload'){
			    		$('#upload_timesheet_form')[0].reset();
			    	}
			    	$('#dataFieldsDiv').removeAttr("style");
			    	$('#dataFieldsDiv').empty();
			    	$("#dataFileImportDiv").removeAttr("style");
			    	$('#loading').removeAttr("style");
			    	$("#selector11").fadeOut(3000);
				}
			});
		}
	}
	$('body').on('blur', '#dataMigration_form', function() {
		$("#dataMigration_form").showHelpOnFocus();
		$("#dataMigration_form").validateOnBlur(false, validationSettings);
	});
</script>


<script type="text/javascript">
	function saveSubmissionTemplateDataFields() {
		if ($('#dataMigration_form').validate(false, validationSettings)) {
			
			    var frm = $('#dataMigration_form').serializeFormJSON();
			    var con = JSON.stringify(frm);
			    con = con.replace(/[{}]/g, "");
			    var jsonData = encodeURIComponent(con);
			    var formData = getFormData1("dataMigration_form");
			    
			    var requirementId = '${requirementId}'
			    
			var inviteEmail = '';
			if($("#inviteEmail").val() != null && $("#inviteEmail").val() != 'undefined' && $("#inviteEmail").val() != ''){
				inviteEmail = $("#inviteEmail").val();
			}
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
				type : "POST",
				data : formData,
				processData : false,
				contentType : false,
				url : sourceleadUrl + "/getDataByMatchedFields?"
						+ tokenParamName + "=" + tokenValue+"&jsonData="+jsonData+"&requirementId="+requirementId,
				success : function(data) {
					$("#dataFileImportDiv").attr("style","display:none");
			    	$('#dataFieldsDiv').removeAttr("style");
			    	$('#dataFieldsDiv').empty();
			    	$('#dataFieldsDiv').append(data);
			    	var importStatus = $('#importStatus').val();
			    	if(importStatus == "success"){
			    		$('#selector11').removeClass('bg-red').addClass('success_pop');
          				$("#msgDiv").html("Data imported successfully");
          				$('#errorDownloadDiv').removeAttr("style");
          				$('#errorDownloadDiv').prepend("<span class='text-green'>Data imported successfully</span>");
          				
          				$(".import_submission_side_wrap").removeClass("import_submission_side_wrap_active");
          				$(".side_mask").fadeOut(200);
          				$("body").css('overflow-y', 'auto');
          				requirementPipeline('${requirementId}','all')
          				
          			}else if(importStatus == "fail"){
          				$('#selector11').removeClass('success_pop').addClass('bg-red');
          				$("#msgDiv").html("Should upload file with valid data");
          				$('#errorDownloadDiv').removeAttr("style");
          				$('#errorDownloadDiv').prepend("<span class='colorRed'>Should upload file with valid data</span>");
          			}else if(importStatus != ''){
          				$('#selector11').removeClass('success_pop').addClass('bg-red');
          				$("#msgDiv").html("Some data is failed to import");
          				$('#errorDownloadDiv').removeAttr("style");
          				$('#errorDownloadDiv').prepend("<span class='colorRed'>Some data is failed to import <a download class='colorRed glyphicon glyphicon-download-alt' target='_blank' href='"+importStatus+"' title='Download error file'></a></span>");
          			}
			    	$('#newUploadFile').text('');
			    	
			    	var uploadType = $('#uploadType').val();
			    	if(uploadType == 'jobUpload'){
			    		$('#upload_job_form')[0].reset();
			    	}if(uploadType == 'assetUpload'){
			    		$('#upload_asset_form')[0].reset();
			    	}if(uploadType == 'timesheetUpload'){
			    		$('#upload_timesheet_form')[0].reset();
			    	}
			    	$('#dataFieldsDiv').removeAttr("style");
			    	$('#dataFieldsDiv').empty();
			    	$("#dataFileImportDiv").removeAttr("style");
			    	$('#loading').removeAttr("style");
			    	$("#selector11").fadeOut(3000);
				}
			}); 
		}
		
	}
	$('body').on('blur', '#dataMigration_form', function() {
		$("#dataMigration_form").showHelpOnFocus();
		$("#dataMigration_form").validateOnBlur(false, validationSettings);
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
    $(".chosen-select").chosen();
</script>
