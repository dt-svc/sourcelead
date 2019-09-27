<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
var uploadflag= true;
		window.onload = function () {
			var refreshId = setInterval(function(){
				var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';
				if(uploadflag == true){
					xlsupload.getUploadInprogressProgressBar("Associates",dateFormat);
				  }else{
					     clearInterval(refreshId);
						 $(".browseProgress").hide();
						 $("#upload_button").removeAttr("disabled");
						 xlsupload.getUploadInprogressProgressBar("Associates",dateFormat);
				  }
			},1000);
		 };
</script>


<section class="content-wraper">
	<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Import Associates Deduction Contribution</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="${appPath}/usersList">Associates List</a> &raquo; Import Associates Deduction Contribution</div>
    </section>
	<div id="loading"></div>
	<div class="content-area clearfix" id="dataFileImportDiv">
		<form:form id="upload_job_form" name="upload_job_form" method="post" enctype="multipart/form-data">
			<div class="clearfix form-area form-horizontal toggle-div remove-div mt">
			<div class="row minHyewtriuwe rwrghek	fghjqwehjgfwej tkjreight">
				<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
					<!-- Download File button -->
					<div class="clearfix mb row">
					<div class="col-xs-3 col-md-3">
						<input type="text" class="form-control" placeholder="Header Index" id="headerPosition" name="headerPosition"  field-name="Header index" data-validation="required validate_Space validate_length length1-6 validate_int">
					</div>
					
						<div class="col-xs-5 col-md-5">
							<a href="${appPath}/downloadAssociateDeductionContribitionDetailsTemplate"
								class="btn btn-success btn-block" title="Download Associate Template"> <i class="fa fa-download"></i>&nbsp;&nbsp; Associate Deduction Contribution Detail Template
							</a>
						</div>
						<div class="col-xs-4 col-md-4">
							<div class="btn-group btn-group-justified">
								<div class="btn-group">
									<input type="button" class="btn btn-primary" id="upload_button"
										name="upload_button" onclick="importAssociateDeductionContributionDetail();"
										value="Import">
								</div>
								<div class="btn-group">
									<input type="reset" class="btn btn-danger" value="Reset"
										onClick="javascript:window.location.href = '${appPath}/uploadAssociateDeductionContributionDetails'">
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
									style="display: none;" id="uploadBton"
									data-validation="required validate_importFile" multiple>
								</label>
								<div class="clearfix"></div>
								<label id="newUploadFiles"></label> 
								<input type="text" hidden readonly id="dataFilePaths" value="">
							</div>
							<div class="center-div">
								<input id="uploadFiles" placeholder="" disabled="disabled"
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
							  <label><i class="fa fa-user"></i>&nbsp;&nbsp;Associate Email</label>
							  </p>
						</span>
					</div>
				</div>
			</div>
		</div>
		</form:form>
	</div>
	<div class="require_wrap_table datatable_filter">
		<div id="errorDataDIV"></div>
        </div>
	<div id="dataFieldsDiv" style="display: none"></div>
<div class="project_tabs_inn_table project_tabs_inn_table_a datatable_filter dataSeaTop">
	<h5 class="mt25" id="uploadXlsHistoryDivTitle" hidden>Recent Uploads History</h5>
	    	<table id="uploadXlsHistoryDiv" class="display" cellspacing="0" width="100%"></table>
	    	<input type="hidden"  id="uploadedPercentage" value="0">
		</div>	 	
</section>

<div class="success_pop" id="selector11" style="display: none;">
    <i class="fa fa-check-circle"></i><span id="msgDivvv"></span>
</div>

<script type="text/javascript" src="/resources/js/xlsupload.js"></script>

<script type="text/javascript">
document.getElementById("uploadBton").onchange = function () {
	$('#newUploadFiles').text($(this).val().replace(/C:\\fakepath\\/i, ''));
	fileUpload("uploadBton", "dataMigration");
};
</script>

<!-- <script type="text/javascript">
function saveAssociateBankDetailTemplate(){
	var fileUrl = $("#dataFilePath").val();
	var uploadType = "associateUpload";
	var headerPosition = $("#headerPosition").val();
        if (($('#upload_job_form').validate(false, validationSettings))){
      	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
          	$.ajax({
    			type: "POST",
    		    url: sourceleadUrl+"/saveAssociateBankDetailTemplate?"+tokenParamName+"="+tokenValue+"&filePath="+fileUrl+"&uploadType="+uploadType+"&headerPosition="+headerPosition, 
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

$('body').on('blur', '#upload_job_form', function() {
	$("#upload_job_form").showHelpOnFocus();
	$("#upload_job_form").validateOnBlur(false, validationSettings);
	
});
</script> -->

<script type="text/javascript">
function importAssociateDeductionContributionDetail(){
	
	 var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
	var fileUrl = $("#dataFilePaths").val();
	var uploadType = "associateUpload";
	var headerPosition = $("#headerPosition").val();

	var frm = $('#upload_job_form').serializeFormJSON();
	var con = JSON.stringify(frm);
	con = con.replace(/[{}]/g, "");
	
	
	//It will escape all the special characters
	var jsonData = encodeURIComponent(con);
	var form = $('#upload_job_form')[0];
	var formData = new FormData(form); 
	if (($('#upload_job_form').validate(false, validationSettings))){
		$('#upload_button').prop('disabled', true);
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
		   	type: "POST",
		   	data: formData,
    	    contentType : 'application/json; charset=utf-8',
    	    enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
    	    dataType : 'json',
    	    url: sourceleadUrl+"/saveAssociateDedAndContriDetailTemplate?"+tokenParamName+"="+tokenValue+"&filePath="+fileUrl+"&uploadType="+uploadType+"&headerPosition="+headerPosition, 	   
 		    success : function(json) {
 		    		$("#loading").removeAttr("style");
 		    	    if(json.updateCount != null && json.updateCount != '')
 		    	    $("#msgDivvv").removeAttr("style");
 		 			$("#msgDivvv").css({ display: "inline-block" });  
 		 			$("#msgDivvv").html("Associates are updated ..!");
 		 			$("#selector11").show();
 		 			$("#selector11").fadeOut(2000);
 		 			if(json.errorData != null && json.errorData.length >0){
 		 				showFailedExcelData(json);
 		 			}
 			    	},
		 		   error: function(e){
			        }
			 }); 
	}else{
		return false;	
	} 
}

$('body').on('blur', '#upload_job_form', function() {
	$("#upload_job_form").showHelpOnFocus();
	$("#upload_job_form").validateOnBlur(false, validationSettings);
	
});
</script>


<script type="text/javascript">
function cancelFileImport(){
	/* $("#jobFieldsDiv").attr("style","display:none");
	$('#jobFileImportDiv').removeAttr("style");
	$('#jobFieldsDiv').empty(); */
	location.replace(sourceleadUrl+"/usersList");
}
</script>

<script>
  function showFailedExcelData(json){
	 /*  var sourceleadUrl = '${sourceleadUrl}';
	  $.get( sourceleadUrl+ "/getAllSignatoriesByLocationId?${_csrf.parameterName}=${_csrf.token}", function(data) { */
		  var result = "";
	    	 result += '<table id="errorDataList" class="display" cellspacing="0" width="100%" >';
	         result += '<thead>';
	         result += '  <tr>';
	         result += '    <th>Assoicate Id</th>';
	         result += '    <th>Associate Email*</th>';
	         result += '    <th>Account Type</th>';
	         result += '    <th>Bank Name</th>';
	         result += '    <th>Account Number</th>';
	         result += '    <th>Routing Number</th>';
	         result += '    <th>Reson</th>';
	         result += '  </tr>';
	         result += '</thead>';
	         result += '<tbody>';
	         var data = json.errorData;
	    	 for(i=0;i < data.length;i++){
	    		 var report = data[i];
	    		 result += '<tr>';
		         result += '<td>'+report.associateId+'</td>';
		         result += '<td>'+report.emailId+'</td>';
		         result += '<td>'+report.accountType+'</td>';
		         result += '<td>'+report.bankName+'</td>';
		         result += '<td>'+report.accountNumber+'</td>';
		         result += '<td>'+report.routingNumber+'</td>';
		         result += '<td>'+report.reason+'</td>';
		         result += '  </tr>';
	    	 }
	    	
	         result += '  </tbody>';
    		 result += '</table> ';
    		 $("#errorDataDIV").empty();
    		 $("#errorDataDIV").append(result);
    		 $("#loading").removeAttr('style');
    		 excelDataPagination();
	     /* });  */
  }
  
  function excelDataPagination(){
		$('#errorDataList').DataTable({ 
			"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	}
	    });
		$("#errorDataList").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  

	}
  </script>


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

