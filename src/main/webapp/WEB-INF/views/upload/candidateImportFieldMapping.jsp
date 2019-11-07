
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	

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
					onclick="saveCandiateDataFields();" id="candBulkImport">Import</button>
				
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
				<input type="hidden"  readonly id="couchbaseQueueKey" name="couchbaseQueueKey" value="${couchbaseQueueKey}">
			</div>
		</div>
	</div>
	</form:form>
</div>

<script type="text/javascript" src="/resources/js/xlsBulkUpload.js"></script>

<script type="text/javascript">
	function saveCandiateDataFields() {
		if ($('#dataMigration_form').validate(false, validationSettings)) {
			var formData = getFormData1("dataMigration_form");
			/* $("#candBulkImport").attr("disabled",true); */
			$('#candBulkImport').prop('disabled', true);
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
			
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			document.getElementById("LoadingTextMsg").style.display="block"; 
			$('#LoadingTextMsg').text('Your file is now uploading. Please be patient'); 
			 $.ajax({
				type : "POST",
				data : formData,
				processData : false,
				contentType : false,
				url : sourceleadUrl + "/storeCandidateBulkUploadToCouchbase?"
						+ tokenParamName + "=" + tokenValue+"&inviteEmail="+inviteEmail+"&uploadid="+uploadid+"&sessionId="+sessionId,
				success : function(data) {
			    	$('#dataFieldsDiv').append(data);
			    	$('#headerPosition').empty();
			    	var couchbaseQueueKey = $('#couchbaseQueueKey').val();
			    	if(couchbaseQueueKey != null && couchbaseQueueKey != '' && couchbaseQueueKey != 'undefined'){
			    		document.getElementById("selector").style.display=""; 
			    		$('#successMsg').text('The file has successfully uploaded. Changes will be updated soon.');
			    		$("#selector").fadeIn(200);
			    		$('#selector').delay(3000).fadeOut('slow');
			    		var uploadedSize = $('#sizeinbytes').val();
			    		
			    		if(uploadedSize <= 2000000){
			    			storeCandidateBulkUpload(couchbaseQueueKey);
			    		}else{ 
			    			bulkUploadEmails(couchbaseQueueKey,uploadedSize,"Candidtate");
			    		}
			    		
			    	}
			    	$('#errorDownloadDiv').val('');
			    	$('#newUploadFile').text('');
			    	flag = false;
			    	$("#upload_button").removeAttr("disabled");
			    	$('#dataFieldsDiv').removeAttr("style");
			    	$('#dataFieldsDiv').empty();
			    	$("#dataFileImportDiv").removeAttr("style");
			    	$('#loading').removeAttr("style");
			    	document.getElementById("LoadingTextMsg").style.display="none"; 
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
function bulkUploadEmails(couchbaseQueueKey,uploadedSize,type){
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/bulkUploadEmails?couchbaseQueueKey="+couchbaseQueueKey+"&uploadedSize="+uploadedSize+"&type="+type, 
	        success: function(result) {
	        	var dateFormat ='${SourceLeadWeb_dateFormat}';	
	        	xlsupload.getFailureUploadStats("Candidates",dateFormat);
	        },
	    });
}

</script>

<script>
function storeCandidateBulkUpload(couchbaseQueueKey){
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/storeCandidateBulkUpload?couchbaseQueueKey="+couchbaseQueueKey, 
	        success: function(result) {
	        	var dateFormat ='${SourceLeadWeb_dateFormat}';	
	        	xlsupload.getFailureUploadStats("Candidates",dateFormat);
	        },
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

<script>
    $(".chosen-select").chosen();
</script>
