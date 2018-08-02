<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="/resources/js/jquery.simplePagination.js"></script>

<script>
$( document ).ready(function() {
    refreshRoleList();
    getLeaveTypesList();
    getDepartmentsList();
    getAllUserListBasedOnOrgLocationId();
    datePickeerTest();
    
    
});
</script>
    <div id="loadingId"></div>
	<div id="dayEntryformData">
	<section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>"> Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/hrReportsList'/>"> HR Reports
</a> &raquo; Leave Report</div>
    </section>
    <form id="leaveReportForm" name="leaveReportForm">
    <div class="contentareaInner-styles clearfix">
    <h4 class="panel-title">
          <a data-toggle="collapse" href="#collapse1">Show Report</a>
    </h4>
    <div id="collapse1" class="rundiv panel-collapse collapse mt in">
      <div class="clearfix form-horizontal minHeight_225 minHeightcol5">
        <div class="row min">
            <div class="col-md-5">
             <div class="row">
            	  <div class="col-md-6">
              <label class="clearfix">From Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix input-group errorInptgroup">
                <input type="text" readonly class="form-control calendar" name="startDate" value="" field-name="From Date" id="fromDate" data-validation="required" onclick="datePickeerTest();">
                 <span class="input-group-addon">
                 	<i class="colorRed fa fa-times cursorPointer" onclick="clearDate('from');"></i>
                 </span>
              </div> 
              </div>
            	  <div class="col-md-6">
              <label class="clearfix">To Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix input-group errorInptgroup">
                <input type="text" readonly class="form-control calendar" name="endDate" value="" field-name="To Date" id="toDate" data-validation="required" onclick="datePickeerTest();">
                 <span class="input-group-addon">
                 	<i class="colorRed fa fa-times cursorPointer" onclick="clearDate('to');"></i>
                 </span>
              </div>
              </div>
            
            </div>
            </div>
            
           <div class="col-md-5">
              <label class="clearfix">Department</label>
              <div class="clearfix">
                <select id="departmentList" name="departmentIds" class="form-control chosen-select" field-name="Department" data-placeholder="Select Department" multiple></select>
              </div>
            </div>
            
             <div class="col-md-5">
              <label class="clearfix">Role</label>
              <div class="clearfix">
                <select id="reportRoles" name="reportRolesId" class="form-control chosen-select" field-name="Role" data-placeholder="Select Role" multiple></select>
              </div>
            </div>
            
        <div class="col-md-5">
              <label class="clearfix">Emp Type</label>
              <div class="clearfix">
                <select class="form-control chosen-select" id="designationType" name="employmentType" data-placeholder="Select Emp Type" multiple field-name="Employment type" >
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
              <label class="clearfix">Associate Name</label>
              <div class="clearfix">
                <select class="form-control chosen-select" id="associateList" field-name="Associate" name="associateListId" data-placeholder="Select Associate" multiple onclick="test();"></select>
              </div>
            </div>
            
            <div class="col-md-5">
              <label class="clearfix">Leave Type</label>
              <div class="clearfix">
                <select id="leaveTypeList" name="leaveTypeIds" class="form-control chosen-select" field-name="Leave Type" data-placeholder="Select LeaveType" multiple></select>
              </div>
            </div>

        <div class="clearfix"></div>
      </div>
</div>

	<div class="clearfix footerstyelsNew margin-top0">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" id="runDiv" class="btn btn-success btnSuccess" type="button" onclick="leaveReport();">
            <input value="cancel" class="btn btn-default ml10" type="button" onclick="resetValues();">
          </div>
        </div>
      </div>
      </div>
      </div>     
</form>		
      <!-- Content Area Ends--> 
        <div class="candidate_wrap_table datatable_filter">
	           <div id="leaveReportsList"></div>
          </div>
  </section>
 </div>


<%-- <div id="expensesExportListDiv" style="display: none;">
	 <jsp:include page="dayEntriesReportList.jsp"></jsp:include> 
</div> --%>
<script>
function leaveReport(){
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var departmentIds  = $('#departmentList').val();
	var reportRolesId  = $('#reportRoles').val();
	var associateListId  = $('#associateList').val();
	var leaveTypeIds  = $('#leaveTypeList').val();
	
	var employmentType  = $('#designationType').val();
// 	var formData = getFormData("leaveReportForm");
	var url = sourceleadUrl+"/leaveReportsList?"+tokenParamName+"="+tokenValue+"&departmentIds="+departmentIds+"&reportRolesId="+reportRolesId+"&associateListId="+associateListId+"&leaveTypeIds="+leaveTypeIds+"&employmentType="+employmentType+"&startDate="+startDate+"&endDate="+endDate;
	prepareAllLeaveReportList(url);	
}
function prepareAllLeaveReportList(url){
	var result = "";
		result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
		result = result + '<table id="leaveReportsListView" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee Id</th>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Departement</th>';
		result = result + '<th>Leave Type</th>';
		result = result + '<th>Type</th>';
		result = result + '<th>From Date</th>';
		result = result + '<th>To Date</th>';
		result = result + '<th>Applied on</th>';
		result = result + '<th>Status</th>';
		result = result + '<th>Approver</th>';
		result = result + '<th>Approved on</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#leaveReportsList").empty();
		$("#leaveReportsList").append(result);
		leaveReportsListPagination(url);
}
</script>

<script>
function leaveReportsListPagination(url){
	var colIndex = 0;
	table = $('#leaveReportsListView').DataTable( { 
		  "processing": true,
	      "serverSide": true,
	      "searching": false,
	      "searchHighlight": true,
	      "ajax": $.fn.dataTable.pipeline( {
	    	  url: url,
	          method: 'POST',
	          pages: 10
	        }),
	        "columns": [
	        	{ "data": "id" },
	            { "data": "name" },
	            { "data": "userDeptName" },
	            { "data": "leaveTypeName"},
	            { "data": "leaveTypeBillable"},
	            { "data": "fromDate"},
	            { "data": "toDate"},
	            { "data": "appliedOn"},
	            { "data": "status"},
	            { "data": "approver"},
	            { "data": "approvedOn"},
	            
	            
	        ],
	        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "id",
	    	  	"render": function(data, type, user, meta) {
	    	  	  var result = '';
	        	  result = result + ' <td>';
		          if(user.id !=null && user.id !='' && user.id !='undefined'){
						 result = result +'<div class="tbldata-dotdot" title="'+user.id+'">'+user.id+'</div>';
		          }else{
						result = result + ' <div class="tbldata-dotdot">--</div>';
					}
		          result = result + ' </td>';
		              return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "name",
	    	  	"render": function(data, type, user, meta) {
	    	  	  var result = '';
	        	  result = result + ' <td>';
		          if(user.name !=null && user.name !='' && user.name !='undefined'){
						 result = result +'<div class="tbldata-dotdot" title="'+user.name+'">'+user.name+'</div>';
		          }else{
						result = result + ' <div class="tbldata-dotdot">--</div>';
					}
		          result = result + ' </td>';
		              return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "userDeptName",
	    	  	"render": function(data, type, user, meta) {
	    	  	  var result = '';
	        	  result = result + ' <td>';
	        	  if(user.userDeptName !=null && user.userDeptName !='' && user.userDeptName !='undefined'){
						 result = result + '<div class="tblminwidth-75">'+user.userDeptName+'</div>';
		          }else{
						result = result + ' <div class="tblminwidth-75">--</div>';
					}
	        	  result = result + ' </td>';
	        	 return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "leaveTypeName",
	    	  	"render": function(data, type, user, meta) {
	    	  	  var result = '';
	        	  result = result + ' <td>';
	        	  if(user.leaveTypeName !=null && user.leaveTypeName !='' && user.leaveTypeName !='undefined'){
						 result = result + '<div class="tbldata-dotdot-vsm" title="'+user.leaveTypeName+'">'+user.leaveTypeName+'</div>';
		          }else{
						result = result + ' <div class="tbldata-dotdot-vsm">--</div>';
					}
	        	  result = result + ' </td>';
	        	  return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "leaveTypeBillable",
	    	  	"render": function(data, type, user, meta) {
	    	  		var result = '';
	            	result = result + ' <td>';
	            	 if(user.leaveTypeBillable !=null && user.leaveTypeBillable !='' && user.leaveTypeBillable !='undefined'){
						 result = result + '<div class="tblminwidth-75">'+user.leaveTypeBillable+'</div>';
		          }else{
						result = result + '<div class="tblminwidth-75">--</div>';
					}
	            	 result = result + ' </td>';
		              return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "fromDate",
	    	  	"render": function(data, type, user, meta) {
	    	  		var result = '';
		        	result = result + ' <td>';
		        	if(user.fromDateStr !=null && user.fromDateStr !='' && user.fromDateStr !='undefined'){
						 result = result + '<div class="tblminwidth-75">'+user.fromDateStr+'</div>';
		          }else{
						result = result + '<div class="tblminwidth-75">--</div>';
					}
		        	 result = result + ' </td>';
		              return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data" :"toDate",
	    	  	"render": function(data, type, user, meta) {
	    	  		var result = '';
		        	result = result + ' <td>';
		        	if(user.toDateStr !=null && user.toDateStr !='' && user.toDateStr !='undefined'){
						 result = result + '<div class="tblminwidth-75">'+user.toDateStr+'</div>';
		          }else{
						result = result + ' <div class="tblminwidth-75">--</div>';
					}
		        	 result = result + ' </td>';
		              return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data":"appliedOn",
	    	  	"render": function(data, type, user, meta) {
	    	  		var result = '';
		        	result = result + ' <td>';
		          	if(user.appliedOnDateStr !=null && user.appliedOnDateStr !='' && user.appliedOnDateStr !='undefined'){
						 result = result + '<div class="tblminwidth-50">'+user.appliedOnDateStr+'</div>';
		          }else{
						result = result + '<div class="tblminwidth-50">--</div>';
					}
		          	result = result + ' </td>';
			        return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "status",
	    	  	"render": function(data, type, user, meta) {
	    	  		var result = '';
		        	result = result + ' <td>';
		          	if(user.status !=null && user.status !='' && user.status !='undefined'){
						 result = result + '<div class="tblminwidth-50">'+user.status+'</div>';
		          }else{
						result = result + '<div class="tblminwidth-50">--</div>';
					}
		          	result = result + ' </td>';
			        return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data":"approver",
	    	  	"render": function(data, type, user, meta) {
	    	  		var result = '';
		          	result = result + ' <td>';
		          	if(user.approver !=null && user.approver !='' && user.approver !='undefined'){
						 result = result + '<div class="tbldata-dotdot-vsm" title="'+user.approver+'">'+user.approver+'</div>';
		          }else{
						result = result + '<div class="tbldata-dotdot-vsm">--</div>';
					}
		          	result = result + ' </td>';
		          	return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data":"approvedOn",
	    	  	"render": function(data, type, user, meta) {
	    	  		var result = '';
		          	result = result + ' <td>';
		          	if(user.approvedOn !=null && user.approvedOn !='' && user.approvedOn !='undefined'){
						 result = result + '<div class="tbldata-dotdot-vsm" title="'+user.approvedOn+'">'+user.approvedOn+'</div>';
		          }else{
						result = result + '<div class="tbldata-dotdot-vsm">--</div>';
					}
		          	result = result + ' </td>';
		          	return result;
			          }
	      }],
	      
	        "scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     },
	    } );
}


</script>
<script>
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
	$('#fromDate').val('');
	$('#toDate').val('');
	$('#associateList').val('');
	$('#designationType').val('');
	$('#projectList').val('');
	$('#projectList').trigger("chosen:updated");
	$('#associateList').trigger("chosen:updated");
	$('#designationType').trigger("chosen:updated");
}
</script>
<script>
function getSelectedProjectDetailsList(){
	getProjectListBasedOnStartAndEndDate();
}
</script>

<script>
function runExport(){
	var projectIds  = $('#projectList').val();
	var associateList = $('#associateList').val();
	var templateType = "";
	
	if((projectIds == null || projectIds =='' || projectIds=='undefined')&& (associateList == null || associateList =='' || associateList=='undefined')){
		projectIds = $("#projectList option[name='all']").val();
		templateType = "proJectMembersExport";
	}
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var exportType = $('#exportType').val();
	
	if(associateList == null || associateList =='' || associateList=='undefined'){
		projectMemberList = '';
	}
	if ($('#leaveReportForm').validate(false, validationSettings)){
		if(startDate != null && startDate !='' && startDate !='undefined' && endDate != null && endDate !='' && endDate !='undefined'){
// 			window.location.href = sourcelesadUrl+"/exportDaywiseTimesheetReport?&projectIds="+projectIds+"&startDate="+startDate+"&endDate="+endDate+"&exportType="+exportTypassociateListListId="+projectMemberList+"&templateType="+templateType;
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
        success: function (obj) {
        	 var output = "";
        	 /*output = output + "<option>--select Associate--</option>"; */
        	if(obj != null && obj !='' && obj !='undefined'){
        		$.each(obj, function(i, item) {
        			if(item.candidateName != null && item.candidateName !='' && item.candidateName !='undefined'){
        				output = output + "<option value='" +item.masterCandidateId.id+ "'>"+item.candidateName+"</option>";
        			}
            		
        		});
        	}
        	$("#associateList").empty();
            $("#associateList").append(output);
            $('#associateList').trigger("chosen:updated");
        }
    });
}

</script>
<script>
function refreshRoleList(){
		 $.ajax({
	      type : "POST",
	       url: sourceleadUrl+"/refreshRoleList",       
	       success: function(json) {
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	       			output = output + "<option value='"+opt.id+"'>"+opt.displayName+"</option>";
				}
	        	$("#reportRoles").empty();
		        $("#reportRoles").append(output);
		        $('#reportRoles').trigger("chosen:updated");
	        }
		 });
	
}
</script>
<script>
 function getDepartmentsList(){
	 $.ajax({
	    	type: "POST",
	    	url: sourceleadUrl+"/getDepartmentsList?"+tokenParamName+"="+tokenValue+"",
	        success: function(obj) {
	          	var json = JSON.parse(obj);
	        	var output = "";
	        	
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		var name = '';
	        		name = opt.departmentName;
	        		output = output + "<option value='" +opt.id+ "'>"+name+"</option>";
				}
	        	
	        	$("#departmentList").empty();
	            $("#departmentList").append(output);
	            $('#departmentList').trigger("chosen:updated");
	        }
	    });
	}
	        	
 
 
 </script>  
 
 <script>
	function getLeaveTypesList() {
		var locationId = '${SourceLeadWeb_orgLocationId}';
		var output = "";
		 $.ajax({
		    	type: "GET",
		    	url: sourceleadUrl+"/getLeaveTypesByLocationid?"+tokenParamName+"="+tokenValue+"&orgLocationId="+locationId,
		        
		        success: function (response) {
		        	for (i = 0; i < response.data.length; i++) {
		        		var report = response.data[i];
		        		if (report.name != null && report.name != '' && report.name != 'undefined') {
		        			output = output + "<option value='" +report.id+ "'>"+report.name+"</option>";
		        		}
		        	}
		        	$("#leaveTypeList").empty();
		            $("#leaveTypeList").append(output);
		            $('#leaveTypeList').trigger("chosen:updated");	
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
	if(requestFrom == 'from'){
		$('#fromDate').val('');
		$('#toDate').val('');
	}else if(requestFrom == 'to'){
		$('#toDate').val('');
	}
}
</script>

 

<jsp:include page="../masterFooter.jsp" />
 
      
    