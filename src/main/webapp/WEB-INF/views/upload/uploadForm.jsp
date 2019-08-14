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
        <h2>Import Associates</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="${appPath}/usersList">Associates List</a> &raquo; Import Associates </div>
    </section>
	<div id="loading"></div>
	<!--  New div with dropdown -->
	
	<div class="content-area clearfix backgroundWhite">
		
	<!-- 	<div class="clearfix width215px" style="position: absolute; top:15px; left: 15px; z-index: 1;">
			<select class="sel" id="importassociteSel" onChange="importassociteSel();">
			    <option value="addAssociate">Add Associate</option>
			    <option value="associateBnkdet">Associate Banking Details</option>
			    <option value="associateInfo">Associate Information</option>
			    <option value="associateSalary">Associate Salary</option>
			</select>
		</div> -->

		<!--         Dropdown div for import associate           -->
		
		<div id="addAssociate" style="display:block;">
			<div class="clearfix"></div>
		<div class="clearfix" id="dataFileImportDiv">
		<form:form id="upload_job_form" name="upload_job_form" method="post" enctype="multipart/form-data">
			<div class="clearfix form-area form-horizontal toggle-div remove-div mt">
			<div class="row minHyewtriuwe rwrghek	fghjqwehjgfwej tkjreight">
				<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
					<!-- Download File button -->
					<div class="clearfix mb row">
					<div class="col-xs-2 col-md-3">
						<input type="text" class="form-control" placeholder="Header Index" id="headerPosition" name="headerPosition"  field-name="Header index" data-validation="required validate_Space validate_length length1-6 validate_int">
					</div>			
						<div class="col-xs-2 col-md-2">
							<a href="<c:url value='${appPath}/downloadDataImportTemplateForAssociate'/>"
								class="btn btn-success btn-block" title="Download Associate Template"> <i class="fa fa-download"></i>&nbsp;&nbsp; Template
							</a>
						</div>				
						
						<div class="col-xs-2 col-md-5">
							<div class="btn-group btn-group-justified">
								<div class="btn-group" id="import">
									<input type="button" class="btn btn-primary" id="upload_button"
										name="upload_button" onclick="fetchFieldsWithMatchedColumn();"
										value="Import">
								</div>
								
								<div class="btn-group">
									<input type="reset" class="btn btn-danger" value="Reset"
										onClick="javascript:window.location.href = '<c:url value='${appPath}/uploadForm'/>'">
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
								According to this template, the Users need to
								<span class="text-danger"><strong>Import Required Fields</strong></span>
							</p>
							<p>
							  <label><i class="fa fa-user"></i>&nbsp;&nbsp;First Name</label>
							  <label><i class="fa fa-envelope"></i>&nbsp;&nbsp;Email</label>
							  <label><i class="fa fa-check"></i>&nbsp;&nbsp;Role</label>
							  <label><i class="fa fa-check"></i>&nbsp;&nbsp;Shift</label>
							  <label><i class="fa fa-check"></i>&nbsp;&nbsp;Manager(Email)</label>
							  <label><i class="fa fa-check"></i>&nbsp;&nbsp;Employment Type</label>
							  <label><i class="fa fa-check"></i>&nbsp;&nbsp;Designation</label>
							  <label><i class="fa fa-check"></i>&nbsp;&nbsp;Department</label>
							  </p>
						</span>
					</div>
				</div>
			</div>
		</div>		<div class="clearfix"></div>
		</form:form>
	</div>
	</div>
	
	

<!--         Dropdown div for Associate banking details  -->
	
	
		<div id="associateBnkdet" style="display:none;">
			Associate banking details        
			</div>
	
			
	<!--      Dropdown div for Associate information      -->		
			
		<div id="associateInfo" style="display:none;">
			 Associate information    
	
	</div>
		
	<!--      Dropdown div for Associate salary      -->			
		
		<div id="associateSalary" style="display:none;">
		Associate salary
	</div>

		
	<!--  new div with dropdown ends here -->
	
	
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
document.getElementById("uploadBtn1").onchange = function () {
	$('#newUploadFile1').text($(this).val().replace(/C:\\fakepath\\/i, ''));
	fileUpload("uploadBtn1", "dataMigration");
};

</script>


<script type="text/javascript">
function fetchFieldsWithMatchedColumn(){
	var fileUrl = $("#dataFilePath").val();
	var uploadType = "associateUpload";
	var headerPosition = $("#headerPosition").val();
        if (($('#upload_job_form').validate(false, validationSettings))){
        	$('#upload_button').prop('disabled', true);
      	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
          	$.ajax({
    			type: "POST",
    		    url: sourceleadUrl+"/dataMigrationCommon?"+tokenParamName+"="+tokenValue+"&filePath="+fileUrl+"&uploadType="+uploadType+"&headerPosition="+headerPosition, 
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
</script>

<script type="text/javascript">
function cancelFileImport(){
	/* $("#jobFieldsDiv").attr("style","display:none");
	$('#jobFileImportDiv').removeAttr("style");
	$('#jobFieldsDiv').empty(); */
	location.replace(sourceleadUrl+"/usersList");
}
</script>


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
				})
			
 		</script>
 		
	<script>
	//import Associate dropdown script
	function importassociteSel(){
		var impassValue = $("#importassociteSel option:selected").val();
		if(impassValue == "associateBnkdet") {
			$("#associateBnkdet").show();
			$("#addAssociate, #associateInfo, #associateSalary").hide();
			
		}else if(impassValue == "associateInfo"){
			$("#associateInfo").show();
			$("#addAssociate, #associateBnkdet, #associateSalary").hide();
		
		}else if(impassValue == "associateSalary"){
			$("#associateSalary").show();
			$("#addAssociate, #associateBnkdet, #associateInfo").hide();
		
		}else if(impassValue == "addAssociate"){
			$("#addAssociate").show();
			$("#associateSalary, #associateBnkdet, #associateInfo").hide();
		} else {
			$("#addAssociate").show();
		}

	}
	</script>
	
	<script>
	function msgFadein() {
		$("#msgDiv").fadeIn(200);
        $('#msgDiv').delay(2000).fadeOut('slow');
	}
    </script> 
	
<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

