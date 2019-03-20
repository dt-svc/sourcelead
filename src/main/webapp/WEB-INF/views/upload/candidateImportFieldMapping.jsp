
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
			
			
		</div>
	</div>
	<div class="clearfix"></div>
	<form:form id="dataMigration_form" name="dataMigration_form">
	<div class="jobsimort-header clearfix">
		<h4 class="col-md-10 mt5">Fields</h4>
		<div class="col-md-2">
			<div class="pull-right">
				<button type="button" class="btn btn-sm btn-primary mr"
					onclick="saveCandiateDataFields();">Import</button>
				
				<button type="button" class="btn btn-sm btn-primary"
					onclick="cancelFileImport();">Cancel</button>
			</div>
		</div>
	</div>
	<div class="form-horizontal myForm row">
		<div class="clearfix">
			<div class="col-xs-12 col-sm-6 col-md-12 col-lg-12">
			
				<jsp:include page="dataImportFormFields.jsp"></jsp:include>
				<input type="hidden"  readonly id="exportFilePath" name="exportFilePath" value="${exportFilePath}">
				<input type="hidden"  readonly id="uploadType" name="uploadType" value="${uploadType}">
				<input type="hidden"  readonly id="headerPosition" name="headerPosition" value="${headerPosition}">
				<input type="hidden"  readonly id="importStatus" name="importStatus" value="${importStatus}">
			</div>
		</div>
	</div>
	</form:form>
</div>

<script type="text/javascript">
	function saveCandiateDataFields() {
		if ($('#dataMigration_form').validate(false, validationSettings)) {
			var formData = getFormData1("dataMigration_form");
			var sessionId = '${SourceLeadWeb_sessionId}';
			var inviteEmail = '';
			if($("#inviteEmail").val() != null && $("#inviteEmail").val() != 'undefined' && $("#inviteEmail").val() != ''){
				inviteEmail = $("#inviteEmail").val();
			}
			
	        $('#dataFileImportDiv').removeAttr("style");
	    	$('#dataFieldsDiv').removeAttr("style");
	    	$('#dataFieldsDiv').empty();
	    	var flag= true;
			var uploadid = new Date().getTime();
			var refreshId = setInterval(function(){
				if(flag == true){
					getUploadProgressBar();
				  }else{
					  clearInterval(refreshId);
					  $(".browseProgress").hide();
					  getUploadProgressBar();
				  }
				},1000);
			
			$.ajax({
				type : "POST",
				data : formData,
				processData : false,
				contentType : false,
				url : sourceleadUrl + "/storeCandidateBulkUpload?"
						+ tokenParamName + "=" + tokenValue+"&inviteEmail="+inviteEmail+"&uploadid="+uploadid+"&sessionId="+sessionId,
				success : function(data) {
			    	$('#dataFieldsDiv').append(data);
			    	var importStatus = $('#importStatus').val();
			    	$('#errorDownloadDiv').empty();
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
			    	flag = false;
			    	$("#upload_button").removeAttr("disabled");
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
			    var sessionId = '${SourceLeadWeb_sessionId}';
			    var inviteEmail = '';
			    
			    $('#dataFileImportDiv').removeAttr("style");
		    	$('#dataFieldsDiv').removeAttr("style");
		    	$('#dataFieldsDiv').empty();
		    	var flag= true;
				
				var uploadid = new Date().getTime();
				var refreshId = setInterval(function(){
					if(flag == true){
						getUploadProgressBar();
					  }else{
						  clearInterval(refreshId);
						  $(".browseProgress").hide();
						  getUploadProgressBar();
					  }
					},1000);
				
			if($("#inviteEmail").val() != null && $("#inviteEmail").val() != 'undefined' && $("#inviteEmail").val() != ''){
				inviteEmail = $("#inviteEmail").val();
			}
			$.ajax({
				type : "POST",
				data : formData,
				processData : false,
				contentType : false,
				url : sourceleadUrl + "/getDataByMatchedFields?"
						+ tokenParamName + "=" + tokenValue+"&jsonData="+jsonData+"&requirementId="+requirementId+"&uploadid="+uploadid+"&sessionId="+sessionId,
				success : function(data) {
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
			    	flag = false;
			    	$("#upload_button").removeAttr("disabled");
			    	
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


<script>
function getUploadProgressBar(){
	var uploadType = '${uploadType}'
	
	if(uploadType == 'candidatesUpload'){
		uploadType = 'Candidates';
	}else if(uploadType == 'submmissionTemplateUpload'){
		uploadType = 'Submissions';
	}else if(uploadType == 'jobUpload'){
		uploadType = 'Jobs';
	}else if(uploadType == 'timesheetUpload'){
		uploadType = 'Timesheets';
	}else if(uploadType == 'assetUpload'){
		uploadType = 'Assets';
	}else if(uploadType == 'projectTasks'){
		uploadType = 'ProjectTasks';
	}else if(uploadType == 'associateUpload'){
		uploadType = 'Associates';
	}else if(uploadType == 'immigrationUpload'){
		uploadType = 'Immigration';
	}else if(uploadType == 'lcaUpload'){
		uploadType = 'LCA';
	}
	 var uploadedPercentage = document.getElementById("uploadedPercentage").value;
	
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getXlsProcessingHistroyListByEntityType?"+tokenParamName+"="+tokenValue+"&entityType="+uploadType, 
        success: function(json) {
        	
        	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
 	   		  if(locationList.match("yyyy-MM-dd")){
 	   			  uploadedDateStr = 'YYYY-MM-DD';
 	   		  }if(locationList.match("dd-MM-yyyy")){
 	   			uploadedDateStr = 'DD-MM-YYYY'; 
 	   		  }if(locationList.match("MM-dd-yyyy")){
 	   			uploadedDateStr = 'MM-DD-YYYY'; 
 	   		  }if(locationList.match("yyyy-dd-MM")){
 	   			uploadedDateStr = 'YYYY-DD-MM'; 
 	   		  }
 	   		  
        	
        	   var result = "";
        	   result = result + '     <div class="clearfix"></div>';
	           result = result + '   </div>';
	           result = result + '   <div class="project_tabs_inn_table project_tabs_inn_table_a mt40 tableData">';
	           result = result + '     <table class="display" cellspacing="0" width="100%">';
	           result = result + '       <thead>';
	           result = result + '         <tr>';
	           result = result + '           <th>File Name</th>';
	           result = result + '           <th>Uploaded</th>';
	           result = result + '           <th>Success</th>';
	           result = result + '           <th>Failed</th>';
	           result = result + '           <th>Date / Time<br>(<small class="custom-small">'+uploadedDateStr+'</small>)</th>';
	           result = result + '           <th>Status</th>';
	           result = result + '         </tr>';
	           result = result + '       </thead>';
	           result = result + '       <tbody>';
	           if(json != null && json != '' && json.length > 0){
	        	   
	        	   var progressRow = '';
	        	   for(var i=0; i<json.length; i++){
	        		   var report = json[i];
	        		   
	        		   if(report.uploadProcessingStatus == 'Inprogress'){
	        			   progressRow = json[i];
	        			   $("#upload_button").attr("disabled", "disabled");
	        		   }
	        		    
	        		    result = result + '<tr>';
	        		    result = result + '<tr>';
	        		    result = result + '<td><div class="tbldata-dotdot-big text-center"><span title="'+report.fileName+'">'+report.fileName+'</span></div></td>';
	        		    result = result + '<td><div class="tbldata-dotdot_100">'+report.totalCount+'</div></td>';
	        		    result = result + '<td><div class="tbldata-dotdot-100">'+report.successCount+'</div></td>';
	        		    result = result + '<td><div class="tbldata-dotdot-100">'+report.failureCount+'</div></td>';
	        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.date+'&nbsp;&nbsp;'+report.time+'</div></td>';
	        		   
	        		  
		        		    if(report.uploadProcessingStatus == 'Completed'){
		        		    	  if(report.fileUrl != null && report.fileUrl != '' && report.fileUrl != 'undefined'){
		        		    		  result = result+'<td><div class="tbldata-dotdot-50 1538051356458tblicons_4"> <a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+report.fileUrl+'\" download title="Download"></a>';
		        		    	  }else{
		 	        		    	 result = result + '<td><div class="tbldata-dotdot_100"><span class="text-danger">Uploaded Empty file</span></div></td>';
		  	        		       }
		        		    	
		        		    }else{
		        		    
		        		    	  result = result + '<td>';
		         		    	  result = result + '<div class="browseProgress">';
		         		    	  result = result + '<div class="progress progress-striped active">';
		         		    	  result = result + '<div class="progress-bar progress-bar-success" id="progressBar">'+uploadedPercentage+'</div>';
		         		    	  result = result + '</div>';
		         		    	  result = result + '<div class="progressCount">Uploading <span id="currentUploadedCount"></span> of <span id="totalUploadedCount"></span></div>';
		         		    	  result = result + '</div>';
		                          result = result + '</td>';
	                        
		        		    } 
	        		    
	        		 
		    		}
			           result = result + '       </tbody>';
			           result = result + '       </table>';
			           result = result + '</div>';
			           result = result + '</div>';
		    			
		    			$("#uploadXlsHistoryDiv").empty();
		    			$("#uploadXlsHistoryDiv").append(result);
		    			
		    			if(progressRow != ''){
		    				  $(".browseProgress").show();
	                  		  $("#progressBar").html(uploadedPercentage+"%");
	                  		  $(".progress div").css("width", uploadedPercentage+"%");
	                          getXlsProgressBar(progressRow);
		    			}
	           }
        	
        	
		 },
        error: function(e){
        }
    });
	
	function getXlsProgressBar(json){
		
		var totalCount = json.totalCount == undefined? 0: json.totalCount;
		var succesCount = json.successCount == undefined? 0: json.successCount;
		var failureCount = json.failureCount == undefined? 0: json.failureCount;  
		var uploadingCount = parseInt(succesCount) + parseInt(failureCount);
		
		percentComplete = parseInt(uploadingCount)/parseInt(totalCount);
		$(".progress div").css("width", parseInt(percentComplete*100) + "%");
		$("#progressBar").html(parseInt(percentComplete*100)+'%');
		$("#totalUploadedCount").html(totalCount);
		$("#currentUploadedCount").html(uploadingCount);
		$("#uploadedPercentage").val(parseInt(percentComplete*100));
		
			
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

<script>
    $(".chosen-select").chosen();
</script>
