<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="/resources/js/jquery.simplePagination.js"></script>

<script>
$( document ).ready(function() {
    getProjectListBasedOnStartAndEndDate();
    datePickeerTest();
});
</script>

<body>
    <div id="loadingId"></div>
	<div id="dayEntryformData">
	<section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Project Time Entries Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>"> Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/hrReportsList'/>"> HR Reports
</a> &raquo; Project Time Entries Report</div>
    </section>
    <form id="timesheetExportForm" name="timesheetExportForm">
    <div class="contentareaInner-styles clearfix" >
       <input type="hidden" id="allProjectIds"/>
      <div class="clearfix form-horizontal minHeight_225 minHeightcol5">
        <div class="row min">
            <div class="col-md-5">
              <label class="clearfix">Type of Project</label>
              <div class="clearfix">
                <select class="form-control sel" onchange="getSelectedProjectDetailsList();" id="projectType">
                  <option value="all" selected>All</option>
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
            
             <div class="col-md-5">
              <label class="clearfix">Associate Status</label>
              <div class="clearfix">
                <select class="form-control sel" id = "associateSatus" onchange="getProjectMemberFromProjectId();" id="projectType">
                  <option>Select</option>
                  <option value="all" selected>All</option>
                  <option value="ACTIVE">Active</option>
                  <option value="INACTIVE">InActive
                  </option>
                </select>
              </div>
            </div>
            
            
            
			<div class="clearfix"></div>
             <div class="col-md-5">
              <label class="clearfix">By Associate</label>
              <div class="clearfix">
                <select class="form-control chosen-select" id="projectMemberList" field-name="Associate" name="projectMemberListId" data-placeholder="Select Associate" multiple onclick="test();"></select>
              </div>
              <div>
              
              </div>
            </div>
        <div class="clearfix"></div>
      </div>
</div>
</div>

	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" id="runDiv" class="btn btn-success btnSuccess" type="button" onclick="validateProjectTimeEntriesReport();">
            <input value="Reset" class="btn btn-default ml10" type="button" onclick="resetValues();">
          </div>
        </div>
      </div>      
</form>		
      <!-- Content Area Ends--> 
    
    
  </section>
 </div> 
</body>


<div id="expensesExportListDiv" style="display: none;">
	 <jsp:include page="projectEntriesReportList.jsp"></jsp:include> 
</div>

<script>
function validateProjectTimeEntriesReport(){
	var projectIds  = $('#projectList').val();
	var projectMemberList = $('#projectMemberList').val();
	var templateType = "";
	
	/* if((projectIds == null || projectIds =='' || projectIds=='undefined') && (projectMemberList == null || projectMemberList =='' || projectMemberList=='undefined')){

		 projectIds = $('#projectList option').map(function() { return $(this).val(); });
		 $('#projectList').val(projectIds);
	} */
	
	
	if(projectIds == null || projectIds =='' || projectIds=='undefined'){
		 projectIds = $('#projectList option').map(function() { return $(this).val(); });
		$('#projectList').val(projectIds);
		
	}
	
	if(projectMemberList == null || projectMemberList =='' || projectMemberList=='undefined'){
		 projectMemberListId = $('#projectMemberList option').map(function() { return $(this).val(); }); 
		 $('#projectMemberList').val(projectMemberListId);
	}
	
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var exportType = $('#exportType').val();
	if(projectMemberList == null || projectMemberList =='' || projectMemberList=='undefined'){
		projectMemberList = '';
	}
	  if ($('#timesheetExportForm').validate(false, validationSettings)){
		  $("#loadingId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
		  var formData = getFormData1("timesheetExportForm");
		  $.ajax({
	            url: sourceleadUrl+"/projectTimeEntriesReportList",
	            type: "POST",
	            data: formData,
 		        processData: false,
 		        contentType:false, 
	            success: function (data) {
	            	 $("#dayEntryformData").hide();
	            	 $("#expensesExportListDiv").show();
	            	 $("#expensesExportListDiv").empty();
	    			 $("#expensesExportListDiv").append(data);
	    			 $("#loadingId").removeAttr('style');
	    			 preparePaginationEmployee(1);
	            }
	        });
		  
		 //$('#timesheetExportForm').submit();
		 $('#runDiv').attr('disabled','disabled');
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
					dt.setDate(dt.getDate());
					$("#toDate").datepicker("option", "minDate", dt);
				}
			});
			$('#toDate').datepicker({
				dateFormat : finalDateFormatProjectSave,
				changeMonth: true,
				changeYear: true, 
				onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '-1d');
					dt.setDate(dt.getDate());
					$("#fromDate").datepicker("option", "maxDate", dt);
				}
			});
}
</script>

<script>
function resetValues(){
	$("#fromDate").val('');
	$('#fromDate').attr('value', '');
	$('#fromDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$("#toDate").val('');
	$('#toDate').attr('value', '');
	$('#toDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$('#projectMemberList').val('');
	$('#designationType').val('');
	$('#projectList').val('');
	$('#projectList').trigger("chosen:updated");
	$('#projectMemberList').trigger("chosen:updated");
	$('#designationType').trigger("chosen:updated");
}
</script>

<script>
function getProjectListBasedOnStartAndEndDate(){
	var typeOfProject = 'All';
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var projectType = $('#projectType').val();
	if(projectType != null && projectType != '' && projectType !='undefined'){
		typeOfProject = projectType;
	}
	
	$("#loadingId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
            url: sourceleadUrl + "/getProjectListForProjectTimeEntriesReportBasedOnStartAndEndDate?startDate=" + startDate +"&endDate="+ endDate+"&typeOfProject="+typeOfProject,
            type: "POST",
            success: function (json) {
            	var output = "";
            	var allProjectId  = [];
            	for (var i=0; i<json.length; i++)
				{
            		    var opt = json[i];
            		    allProjectId[i]=opt.projId;
		        		output = output + "<option value='" +opt.projId+ "' name='all'>"+opt.projectName+"</option>";
		        		
				}
            	$("#allProjectIds").val('');
            	$("#allProjectIds").val(allProjectId);
            	$("#loadingId").removeAttr('style');
            	getProjectMemberFromProjectId();
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
	var associateStatus = $('#associateSatus').val();
	var projectIds  = $('#projectList').val();
    if(projectIds == null || projectIds =='' || projectIds=='undefined'){
		projectIds = $("#allProjectIds").val();
	}
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var designationType = $('#designationType').val();
	if(designationType == null || designationType =='' || designationType=='undefined'){
		designationType = '';
	}
	var projectType = $('#projectType').val();
	 $("#loadingId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
        url: sourceleadUrl + "/getProjectMembersBasedOnProjectId?&projectIds="+projectIds+"&startDate="+startDate+"&endDate="+endDate+"&employmentType="+designationType+"&typeOfProject="+projectType+"&associateStatus="+associateStatus,
        type: "POST",
        success: function (result) {
        	var output = "";
        	if(result != null && result !='' && result !='undefined'){
        		$.each(result, function(i, item) {
        			if(item.masterCandidateId != null && item.masterCandidateId !='null' && typeof item.masterCandidateId != 'undefined'){
            		output = output + "<option value='" +item.masterCandidateId.id+ "'>"+item.candidateName+"</option>";
        			}
        		});
        	}
        	$("#projectMemberList").empty();
            $("#projectMemberList").append(output);
            $('#projectMemberList').trigger("chosen:updated");
            $("#loadingId").removeAttr('style');
        }
    });
}
</script>


<!-- <script>
function runExport(){
	var projectIds  = $('#projectList').val();
	var projectMemberList = $('#projectMemberList').val();
	var templateType = "";
	
	if((projectIds == null || projectIds =='' || projectIds=='undefined')&& (projectMemberList == null || projectMemberList =='' || projectMemberList=='undefined')){
		projectIds = $("#projectList option[name='all']").val();
		templateType = "proJectMembersExport";
	}
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var exportType = $('#exportType').val();
	
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



</script> -->

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
	if(requestFrom == 'from'){
		$('#fromDate').val('');
		$('#toDate').val('');
	}else if(requestFrom == 'to'){
		$('#toDate').val('');
	}
}
</script>
<jsp:include page="../masterFooter.jsp" />
 
      
    