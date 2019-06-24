<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- <script type="text/javascript">
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
</script> -->


<section class="content-wraper">
	<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Import Associates Salary</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_atsUrl}/usersList">Associates List</a> &raquo; Import Associates </div>
    </section>
	<div id="loading"></div>
	<div class="content-area clearfix" id="dataFileImportDiv">
		<form:form id="upload_salary_form" name="upload_salary_form"
					method="post" enctype="multipart/form-data">
					<div
						class="clearfix form-area form-horizontal toggle-div remove-div mt">
						<div class="row minHyewtriuwe rwrghek	fghjqwehjgfwej tkjreight">
							<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
								<!-- Download File button -->
								<div class="clearfix mb row">
									<div class="col-xs-2 col-md-2 col-md-offset-4"></div>
									<div class="col-xs-2 col-md-2">
										<a
											href="${SourceLeadWeb_atsUrl}/downloadDataImportTemplateForAssociateSalary"
											class="btn btn-success btn-block"
											title="Download Associate Template"> <i
											class="fa fa-download"></i>&nbsp;&nbsp; Template
										</a>
									</div>

									<div class="col-xs-4 col-md-4">
										<div class="btn-group btn-group-justified">
											<div class="btn-group" id="import1">
												<input type="button" class="btn btn-primary"
													id="upload_button" name="upload_button"
													onclick="importAssoSalarydeatils();" value="Import">
											</div>

											<div class="btn-group">
												<input type="reset" class="btn btn-danger" value="Reset"
													onClick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/uploadAssociateSalary'">
											</div>
										</div>
									</div>
								</div>

								<!-- Attach File button -->

								<div class="dropzone">
								<p class="textSuccess textBold mb0" id="docSuccess" style="display:none;">Data Uploaded Successfully</p>
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
											<label id="newUploadFile"></label> <input type="text" hidden
												readonly id="dataFilePath" value="">
										</div>
										<div class="center-div">
											<input id="uploadFile" placeholder="" disabled="disabled"
												style="width: 100%;" />
										</div>
									</div>

									<!--  Downloaded File show here -->
									<div class="col-xs-3 alert alert-success fade in hidden"
										style="position: absolute; top: 10px;" id="fileAttached">
										<a href="#" class="close" data-dismiss="alert"
											aria-label="close">&times;</a>
										<div>File Holder</div>
									</div>
									<!--  Downloaded File show here -->
								</div>
								<div id="errorDownloadDiv" style="display: none"></div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
								<div class="bgInfo bg-info clearfix text-center">
									<span><i class="fa fa-question" aria-hidden="true"></i></span>
									<span class="info-msg clearfix mt">
										<p class="clearfix mt">
											According to this template, the Users need to <span
												class="text-danger"><strong>Import Required
													Fields</strong></span>
										</p>
										<p>
											<label><i class="fa fa-envelope"></i>&nbsp;&nbsp;Associate
												Email</label> <label><i class="fa fa-check"></i>&nbsp;&nbsp;Pay
												Type</label> <label><i class="fa fa-check"></i>&nbsp;&nbsp;Pay
												Frequency</label> <label><i class="fa fa-check"></i>&nbsp;&nbsp;Standard
												Hours</label> <label><i class="fa fa-check"></i>&nbsp;&nbsp;Basic
												Salary</label>
										</p>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</form:form>
	</div>
	<div class="require_wrap_table datatable_filter">
		<h5 id="uploadXlsHistoryDivTitle" style="display: none;">Failed Records</h5>
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
    <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
</div>

<script type="text/javascript" src="/resources/js/xlsupload.js"></script>

<script type="text/javascript">
document.getElementById("uploadBtn").onchange = function () {
	$('#newUploadFile').text($(this).val().replace(/C:\\fakepath\\/i, ''));
	fileUpload("uploadBtn", "dataMigration");
};
</script>

<script type="text/javascript">
function importAssoSalarydeatils(){
if ($('#upload_salary_form').validate(false, validationSettings)){
	var frm = $('#upload_salary_form').serializeFormJSON();
	var con = JSON.stringify(frm);
	con = con.replace(/[{}]/g, "");
	var sourceleaAtsdUrl = '${SourceLeadWeb_atsUrl}';
	//It will escape all the special characters
	var jsonData = encodeURIComponent(con);
	var form = $('#upload_salary_form')[0];
	var formData = new FormData(form); 
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
		   	type: "POST",
		   	data: formData,
    	    contentType : 'application/json; charset=utf-8',
    	    enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
    	    dataType : 'json',
		    url : sourceleaAtsdUrl+"/saveAssociateSalaryDetailsTemplate?"+tokenParamName+"="+tokenValue+"", 	   
 		    success : function(json) {
	 		    		$("#loading").removeAttr("style");
	 		    		if(json.response != null && json.response != '')
	 		    			$("#upload_button").prop("disabled", true);
		 		    	    $("#msgDiv").removeAttr("style");
		 		 			$("#msgDiv").css({ display: "block" });
		 		 			$("#msgDiv").html(json.response+ " Associates Salary details updated ..!");
		 		 			$("#selector11").removeAttr('style');
		 			    	msgFadein();
		 		    	    //closeImportFile();
		 		    	   // resetUpload();
		 			    	if(json.data.length > 0){
			 		    		showFailedExcelData(json);
			 		    	}else if(json.response == "0"){
			 		    		$("#docSuccess").fadeIn();
			 		    		$('#docSuccess').text('Data Updation failed');
			 		    		$('#docSuccess').removeClass('textSuccess').addClass('textDanger');
			 		    		}else{
			 		    		$("#docSuccess").fadeIn();
			 		    		$('#docSuccess').text('Data Uploaded Successfully');
			 		    		$('#docSuccess').removeClass('textDanger').addClass('textSuccess');
			 		    		}
		 		    },
		 		   error: function(e){
			        }
			 });
		}
}

$('body').on('blur', '#upload_job_form', function() {
	$("#upload_job_form").showHelpOnFocus();
	$("#upload_job_form").validateOnBlur(false, validationSettings);
	
});
</script>
<script>
	function msgFadein() {
		$("#msgDiv").fadeIn(200);
        $('#msgDiv').delay(2000).fadeOut('slow');
	}
    </script>
<script>
  function showFailedExcelData(json){
		  var result = "";
	    	 result += '<table id="errorDataList" class="display" cellspacing="0" width="100%" >';
	         result += '<thead>';
	         result += '  <tr>';
	         result += '    <th>Associate Email*</th>';
	         result += '    <th>Error Message</th>';
	         result += '  </tr>';
	         result += '</thead>';
	         result += '<tbody>';
	         var data = json.data;
	    	 for(i=0;i < data.length;i++){
	    		 var report = data[i];
	    		 result += '<tr>';
		         result += '<td><div class="tblminwidth-175">'+report.associateEmail+'</div></td>';
		         result += '<td><div class="tbldata-dotdot-me">'+report.errorMessage+'</div></td>';
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
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	}
	    });
		$("#errorDataList").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  

	}
  </script>
<script type="text/javascript">
function cancelFileImport(){
	var sourceleaAtsdUrl = '${SourceLeadWeb_atsUrl}';
	location.replace(sourceleaAtsdUrl+"/usersList");
}
</script>


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

