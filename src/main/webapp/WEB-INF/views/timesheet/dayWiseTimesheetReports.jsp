<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- <script src="/resources/js/dayWiseTimeSheetReport.js"></script> -->
<script>
$( document ).ready(function() {
    getProjectListBasedOnStartAndEndDate();
    getAllUserListBasedOnOrgLocationId();
    datePickeerTest();
});
</script>

<body>
	<section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Daily Timehseet</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Daily Timehseet</div>
    </section>
    <form id="timesheetExportForm" name="timesheetExportForm" action="/app/dayEntriesReportList" method="post">
    <div class="contentareaInner-styles clearfix">
      <div class="clearfix form-horizontal minHeight_225 minHeightcol5">
        <div class="row min">
           
            <div class="col-md-5">
              <label class="clearfix">Type of Project</label>
              <div class="clearfix">
                <select class="form-control sel" onchange="getSelectedProjectDetailsList();" id="projectType">
                  <option>Select</option>
                  <option selected>All</option>
                  <option value="client">Client</option>
                  <option value="internal">Internal</option>
                </select>
              </div>
            </div>
            <div class="col-md-5">
             <div class="row">
            	  <div class="col-md-6">
              <label class="clearfix">From Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix input-group errorInptgroup">
                <input type="text" readonly class="form-control calendar" name="startDate" value="" field-name="From Date" id="fromDate" onclick="datePickeerTest();" data-validation="required">
                 <span class="input-group-addon">
                 	<i class="colorRed fa fa-times cursorPointer" onclick="clearDate('from');"></i>
                 </span>
              </div> 
              </div>
            	  <div class="col-md-6">
              <label class="clearfix">To Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix input-group errorInptgroup">
                <input type="text" readonly class="form-control calendar" name="endDate" value="" field-name="To Date" id="toDate" onclick="datePickeerTest();" data-validation="required">
                 <span class="input-group-addon">
                 	<i class="colorRed fa fa-times cursorPointer" onclick="clearDate('to');"></i>
                 </span>
              </div>
              </div>
            
            </div>
            </div>
            
           <div class="col-md-5">
              <label class="clearfix">By Project</label>
              <div class="clearfix">
                <select id="projectList" name="projectIds" class="form-control chosen-select" field-name="Project" data-placeholder="Select Project" multiple onchange="getProjectMemberFromProjectId();"></select>
              </div>
            </div><div class="col-md-5">
              <label class="clearfix">Emp Type</label>
              <div class="clearfix">
                <select class="form-control chosen-select" id="designationType" name="employmentType" data-placeholder="Select Emp Type" multiple field-name="Employment type" onchange="getProjectMemberFromProjectId();">
	                 <option value="">--Select Employment type--</option>
	                 <option value="C2C">C2C</option>
	                 <option value="C2C-1099">C2C-1099</option>
	                 <option value="W-2">W-2</option>
	                 <option value="C2H">C2H</option>
	                 <option value="C2H -1099">C2H -1099</option>
	                 <option value="Part Time">Part Time</option>
	                 <option value="Full Time">Full Time</option>
                </select>
              </div>
            </div>
			<div class="clearfix"></div>
             <div class="col-md-5">
              <label class="clearfix">By Associate</label>
              <div class="clearfix">
                <select class="form-control chosen-select" id="projectMemberList" field-name="Associate" name="projectMemberListId" data-placeholder="Select Associate" multiple></select>
              </div>
              <div>
              
              </div>
            </div>
             <div class="col-md-5">
              <label class="clearfix">Export to</label>
              <div class="clearfix">
                <select class="form-contro sel" id="exportType">
                 <!--  <option value="pdf">PDF</option> -->
                  <option value="excel">Excel</option>
                </select>
              </div>
            </div>
        <div class="clearfix"></div>
												
      </div>
</div>
</div>

	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" onclick="runExport();">
            <input value="Reset" class="btn btn-default ml10" type="button" onclick="resetValues();">
          </div>
        </div>
      </div>      
</form>		
      <!-- Content Area Ends--> 
    
    
  </section>
</body>


<script>
function validateDayEntriesReport(){
	var projectIds  = $('#projectList').val();
	var templateType = "";
	
	if(projectIds == null || projectIds =='' || projectIds=='undefined'){
		projectIds = $("#projectList option[name='all']").val();
		$('#projectList').val(projectIds);
	}
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var exportType = $('#exportType').val();
	var projectMemberList = $('#projectMemberList').val();
	if(projectMemberList == null || projectMemberList =='' || projectMemberList=='undefined'){
		projectMemberList = '';
	}
	  if ($('#timesheetExportForm').validate(false, validationSettings)){
		 $('#timesheetExportForm').submit();
		 return true;
	 }else{ 
			return false;
	 }  
}

var validationSettings = {
    errorMessagePosition : 'element',
    borderColorOnError : '',
	scrollToTopOnError : true,
	dateFormat : 'yyyy/mm/dd' 
};

function datePickeerTest(){
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
		
		  $('#fromDate').datepicker({
				dateFormat : finalDateFormatProjectSave,
				changeMonth: true,
				changeYear: true, 
				onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate() + 1);
					$("#toDate").datepicker("option", "minDate", dt);
				}
			});
			$('#toDate').datepicker({
				dateFormat : finalDateFormatProjectSave,
				changeMonth: true,
				changeYear: true, 
				onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '-1d');
					dt.setDate(dt.getDate() - 1);
					$("#fromDate").datepicker("option", "maxDate", dt);
				}
			});
}
</script>

<script>
function resetValues(){
	$('#fromDate').val('');
	$('#toDate').val('');
	$('#projectMemberList').val('');
	$('#designationType').val('');
	$('#projectList').val('');
	$('#projectList').trigger("chosen:updated");
	 $('#projectMemberList').trigger("chosen:updated");
}
</script>

<script>
function getProjectListBasedOnStartAndEndDate(){
	var allProjectId = [];
	var typeOfProject = 'All';
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var projectType = $('#projectType').val();
	if(projectType != null && projectType != '' && projectType !='undefined'){
		typeOfProject = projectType;
	}
		$.ajax({
            url: sourceleadUrl + "/getProjectListBasedOnStartAndEndDate?startDate=" + startDate +"&endDate="+ endDate+"&typeOfProject="+typeOfProject,
            type: "POST",
            success: function (json) {
            	var output = "";
            	$.each(json, function(i, item) {
            		output = output + "<option value='" +item.id+ "'>"+item.projectName+"</option>";
            		allProjectId.push(item.id);
        		});
            	output = output + "<option value='" +allProjectId+ "' name='all'></option>";
            	$("#projectList").empty();
                $("#projectList").append(output);
                $('#projectList').trigger("chosen:updated");
            }
        });
	}
</script>

<script>
function getSelectedProjectDetailsList(){
	getProjectListBasedOnStartAndEndDate();
}
</script>
<script>
function getProjectMemberFromProjectId(){
	var projectIds  = $('#projectList').val();
	if(projectIds == null || projectIds =='' || projectIds=='undefined'){
		projectIds = '';
	}
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var designationType = $('#designationType').val();
	if(designationType == null || designationType =='' || designationType=='undefined'){
		designationType = '';
	}
	var projectType = $('#projectType').val();
	
	$.ajax({
        url: sourceleadUrl + "/getProjectMembersBasedOnProjectId?&projectIds="+projectIds+"&startDate="+startDate+"&endDate="+endDate+"&employmentType="+designationType+"&typeOfProject="+projectType,
        type: "POST",
        success: function (result) {
        	var output = "";
        	output = output + "<option value=''>--select Associate--</option>";
        	if(result != null && result !='' && result !='undefined'){
        		$.each(result, function(i, item) {
            		output = output + "<option value='" +item.id+ "'>"+item.candidateName+"</option>";
        		});
        	}
        	$("#projectMemberList").empty();
            $("#projectMemberList").append(output);
            $('#projectMemberList').trigger("chosen:updated");
        }
    });
}
</script>


<script>
function runExport(){
	var projectIds  = $('#projectList').val();
	var templateType = "";
	
	if(projectIds == null || projectIds =='' || projectIds=='undefined'){
		projectIds = $("#projectList option[name='all']").val();
		templateType = "proJectMembersExport";
	}
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var exportType = $('#exportType').val();
	var projectMemberList = $('#projectMemberList').val();
	if(projectMemberList == null || projectMemberList =='' || projectMemberList=='undefined'){
		projectMemberList = '';
	}
	if ($('#timesheetExportForm').validate(false, validationSettings)){
		if(startDate != null && startDate !='' && startDate !='undefined' && endDate != null && endDate !='' && endDate !='undefined'){
			window.location.href = sourceleadUrl+"/exportDaywiseTimesheetReport?&projectIds="+projectIds+"&startDate="+startDate+"&endDate="+endDate+"&exportType="+exportType+"&projectMemberListId="+projectMemberList+"&templateType="+templateType;
		}
			return true;
		}else{
			return false;
	}
}

function getAllUserListBasedOnOrgLocationId(){
	$.ajax({
        url: sourceleadUrl + "/getAllUserListBasedOnOrgLocationId",
        type: "POST",
        success: function (result) {
        	var obj = jQuery.parseJSON(result);
        	var output = "";
        	output = output + "<option value=''>--select Associate--</option>";
        	if(obj != null && obj !='' && obj !='undefined'){
        		$.each(obj, function(i, item) {
        			if(item.firstName != null && item.firstName !='' && item.firstName !='undefined'){
        				output = output + "<option value='" +item.id+ "'>"+item.firstName+" "+item.lastName+"</option>";
        			}else{
        				output = output + "<option value='" +item.id+ "'>"+item.firstName+"</option>";
        			}
            		
        		});
        	}
        	$("#projectMemberList").empty();
            $("#projectMemberList").append(output);
            $('#projectMemberList').trigger("chosen:updated");
        }
    });
}

</script>

<script>
var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>

<script>
function clearDate(requestFrom){
	if(requestFrom == 'form'){
		$('#fromDate').val('');
	}else if(requestFrom == 'to'){
		$('#toDate').val('');
	}
}
</script>

<jsp:include page="../masterFooter.jsp" />
 
      
    