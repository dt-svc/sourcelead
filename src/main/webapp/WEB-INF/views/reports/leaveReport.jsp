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
<input type="hidden" id="noReports" name="noReports"	 value="0"/>
    <div id="loadingId"></div>
	<div id="dayEntryformData">
	<section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>"> Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/hrReportsList'/>"> HR Reports
</a> &raquo; Leave Report</div>
 <div id="popUp"></div>
    </section>
   <h4 class="panel-title clearfix fontSmall mb10" id="showReport" style="display:none;">
          <a href="javascript:void(0)"><i class="fa fa-angle-double-left paddingRight"></i>Back to Filters</a>
<!--           <a href="javascript:void(0)" onclick="searchReportCollapse();" id="searchReport" style="display:none;"><i class="fa fa-angle-double-left paddingRight"></i>Search Report</a> -->
    </h4>
    

    <form id="leaveReportForm" name="leaveReportForm">
    <div class="contentareaInner-styles clearfix">
    <div id="reportCollapse" class="rundiv">
      <div class="clearfix form-horizontal minHeight_225 minHeightcol5">
        <div class="row min">
            <div class="col-md-5">
             <div class="row">
            	  <div class="col-md-6">
              <label class="clearfix">From Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix input-group errorInptgroup">
                <input type="text" readonly class="form-control calendar" name="startDate" value="" field-name="From Date" id="fromDate" placeholder="From Date" data-validation="required" onclick="datePickeerTest();">
              </div> 
              </div>
            	  <div class="col-md-6">
              <label class="clearfix">To Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix input-group errorInptgroup">
                <input type="text" readonly class="form-control calendar" name="endDate" value="" field-name="To Date" id="toDate" placeholder="To Date" data-validation="required" onclick="datePickeerTest();">
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
                <select id="leaveTypeList" name="leaveTypeIds"  class="form-control chosen-select" field-name="Leave Type" data-placeholder="Select LeaveType" multiple></select>
              </div>
            </div>
            
                    <div class="col-md-5">
              <label class="clearfix">Status</label>
              <div class="clearfix">
                <select class="form-control chosen-select" id="status" name="status" data-placeholder="Select Status" multiple >
	                 <option value="PENDING">PENDING</option>
	                 <option value="APPROVED">APPROVED</option>
	                 <option value="REJECTED">REJECTED</option>
	                 <option value="CANCELLED">CANCELLED</option>
                </select>
              </div>
            </div>

        <div class="clearfix"></div>
      </div>
</div>

	<div class="clearfix footerstyelsNew margin-top0">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" id="runDiv" class="btn btn-success btnSuccess" type="button" onclick="leaveReport();">
            <input value="Reset" class="btn btn-warning ml10" type="button" onclick="resetReportCollapse();">
            <input value="cancel" class="btn btn-default ml10" type="button" onclick="resetValues();">
          </div>
        </div>
      </div>
      </div>
      </div>     
</form>		
      <!-- Content Area Ends-->
     <div class="positionRelative reportHead clearfix" style="display:none" id="hearderId">
      <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="searchReportCollapse();" oncontextmenu="return false"></a></span>
         
      <ul class="pull-right list-unstyled list-inline">
        <input type="hidden" name="usersOffset" id="usersOffset" value="0"/>
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();" oncontextmenu="return false"></a>
					<div class="newActPop">
						<span>
							<a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a>
						</span>
					</div>
				</li>
		    
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='leaveReportDataTableSearch'/><span class="repSearchIcon" onClick="leaveReport();"></span></li>
        </ul>
      </div> 
        <div class="candidate_wrap_table datatable_filter">
	           <div id="leaveReportsList"></div>
          </div>
  </section>
 </div>
<script>
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	
</script>
<script>
function exportToPdf(){
	var ReportsListEmpty = $("#noReports").val();
	var exportType = "pdf";
	var type="leaveReport"
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var searchValue = $("#leaveReportDataTableSearch").val();
	var status="";
	var leaveTypeIds="";
	var departmentIds=""
	var reportRolesId="";
	var employmentType="";
	var associateListId="";
	if($("#status").val() != null){
		status = $("#status").val();
		}
	if($("#leaveTypeList").val() != null){
		leaveTypeIds = $("#leaveTypeList").val();
		}
	if($("#departmentList").val() != null){
		departmentIds = $("#departmentList").val();
		}
	if($("#reportRoles").val() != null){
		reportRolesId = $("#reportRoles").val();
		}
	if($("#designationType").val() != null){
		employmentType = $("#designationType").val();
		}
	if($("#associateList").val() != null){
		associateListId = $("#associateList").val();
		}
	if(ReportsListEmpty != 0){
		window.location.href = sourceleadUrl + "/getExportLeaveReport?"+tokenParamName+"="+tokenValue+"&startDate="+startDate+"&endDate="+endDate+"&exportType="+exportType+"&searchValue="+searchValue+"&type="+type+"&status="+status+"&leaveTypeIds="+leaveTypeIds+"&departmentIds="+departmentIds+"&reportRolesId="+reportRolesId+"&employmentType="+employmentType+"&associateListId="+associateListId;
	}else{
		var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Leave Report List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	
}
</script>
<script>
function exportUserData(){
	var ReportsListEmpty = $("#noReports").val();
	var exportType = "xls";
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	var startDate = $('#fromDate').val();
	var endDate = $('#toDate').val();
	var searchValue = $("#leaveReportDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var status="";
	var leaveTypeIds="";
	var departmentIds="";
	var reportRolesId="";
	var employmentType="";
	var associateListId="";
		if($("#status").val() != null){
			status = $("#status").val();
			}
		if($("#leaveTypeList").val() != null){
			leaveTypeIds = $("#leaveTypeList").val();
			}
		if($("#departmentList").val() != null){
			departmentIds = $("#departmentList").val();
			}
		if($("#reportRoles").val() != null){
			reportRolesId = $("#reportRoles").val();
			}
		if($("#designationType").val() != null){
			employmentType = $("#designationType").val();
			}
		if($("#associateList").val() != null){
			associateListId = $("#associateList").val();
			}
	if(ReportsListEmpty > 0){
		window.location.href = sourceleadUrl + "/getExportLeaveReport?"+tokenParamName+"="+tokenValue+"&startDate="+startDate+"&endDate="+endDate+"&exportType="+exportType+"&searchValue="+searchValue+"&status="+status+"&leaveTypeIds="+leaveTypeIds+"&departmentIds="+departmentIds+"&reportRolesId="+reportRolesId+"&associateListId="+associateListId;
	}else{
		 var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Leave Report List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	

}
</script> 
<script>
function leaveReport(){
// 	var formData = getFormData("leaveReportForm");
//document.getElementById("hearderId").style.display="block";
var reportRolesId="";
var employmentType="";
var associateListId="";
var leaveTypeIds="";
var departmentIds="";
var status="";
	var searchValue = $("#leaveReportDataTableSearch").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	if($("#reportRoles").val() != null){
		reportRolesId = $("#reportRoles").val();
	}
	if($("#designationType").val() != null){
		employmentType = $("#designationType").val();

	}
	if($("#associateList").val() != null){
	associateListId = $("#associateList").val();
	}
	if($("#leaveTypeList").val() != null){
	leaveTypeIds = $("#leaveTypeList").val();
	}
	if($("#departmentList").val() != null){
    departmentIds = $("#departmentList").val();
	}
	if($("#status").val() != null){
	status = $("#status").val();
	}
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/leaveReportsList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&startDate="+fromDate+"&endDate="+toDate+"&departmentIds="+departmentIds+"&reportRolesId="+reportRolesId+"&employmentType="+employmentType+"&associateListId="+associateListId+"&leaveTypeIds="+leaveTypeIds+"&status="+status;
//     var url = sourceleadUrl+"/leaveReportsList?searchValue="+searchValue;
    if ($('#leaveReportForm').validate(false, validationSettings)){
    	document.getElementById("showReport").style.display="block";
    	$("#hearderId").show();
    	prepareAllLeaveReportList(url);
    }else{ 
		return false;
 } 
	reportCollapse();
	//$("#showReport, #reportCollapse").hide();
	$("#reportCollapse, #leaveReportForm").hide();
	$("#searchReport, #leaveReportsList").show();
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
		result = result + '<th>No Of Days</th>';
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
	            { "data": "noOfDays"},
	            { "data": "status"},
	            { "data": "approver"},
	            { "data": "approvedOn"},
	            
	            
	        ],
	        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "id",
	    	  	"render": function(data, type, user, meta) {
	    	  		$("#noReports").val(1);
	    	  		var result = '';
	        	  result = result + ' <td>';
		          if(user.eid !=null && user.eid !='' && user.eid !='undefined'){
						 result = result +'<div class="tblminwidth-75" title="'+user.eid+'">'+user.eid+'</div>';
		          }else{
						result = result + ' <div class="tblminwidth-75">--</div>';
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
						 result = result +'<div class="tbldata-dotdot-vsm" title="'+user.name+'">'+user.name+'</div>';
		          }else{
						result = result + ' <div class="tbldata-dotdot-vsm">--</div>';
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
						 result = result + '<div class="tbldata-dotdot-vsm">'+user.userDeptName+'</div>';
		          }else{
						result = result + ' <div class="tbldata-dotdot-vsm">--</div>';
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
						 result = result + '<div class="tblminwidth-75">'+user.appliedOnDateStr+'</div>';
		          }else{
						result = result + '<div class="tblminwidth-75">--</div>';
					}
		          	result = result + ' </td>';
			        return result;
			          }
	      },{
	    	  	"targets": colIndex++,
	    	  	"data":"noOfDays",
	    	  	"render": function(data, type, user, meta) {
	    	  		var result = '';
		        	result = result + ' <td>';
		          	if(user.noOfDays !=null && user.noOfDays !='' && user.noOfDays !='undefined'){
						 result = result + '<div class="tblminwidth-75">'+user.noOfDays+'</div>';
		          }else{
						result = result + '<div class="tblminwidth-75">0</div>';
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
		          }
		          	else if(user.approver == null && user.reportingManager !=null && user.reportingManager !='' && user.reportingManager !='undefined'){
						 result = result + '<div class="tbldata-dotdot-vsm" title="'+user.reportingManager+'">'+user.reportingManager+'</div>';

			          }
		          	else{
						result = result + '<div class="tbldata-dotdot-vsm">System Approver</div>';
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
		          	if(user.approvedOn !=null && user.approvedOnDateStr !='' && user.approvedOnDateStr !='undefined'){
						 result = result + '<div class="tbldata-dotdot-vsm" title="'+user.approvedOnDateStr+'">'+user.approvedOnDateStr+'</div>';
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
	    });
$("#leaveReportsListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
}
$("#leaveReportDataTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   leaveReport();
	   }
	});

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
<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
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
	window.location.href =sourceleadUrl+"/hrReportsList"
}
</script>

<script>
function getAllUserListBasedOnOrgLocationId(){
	var ReportsListEmpty = $("#noReports").val(0);
	$.ajax({
        url: sourceleadUrl + "/getAllApprovers",
        type: "POST",
        success: function (obj) {
        	 var output = "";
        	 /*output = output + "<option>--select Associate--</option>"; */
        	if(obj != null && obj !='' && obj !='undefined'){
        		$.each(obj.data, function(i, item) {
       				output = output + "<option value='" +item.userId+ "'>"+item.userName+"</option>";
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
	        		if(name != null && name !='undefined' && name !=''){
	        			output = output + "<option value='" +opt.id+ "'>"+name+"</option>";
	        		}
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

 <script>
function resetFilter(){
	var fromDate = $("#fromDate").val();
    var toDate = $("#toDate").val();
	leaveReport(fromDate,toDate);
	$("#leaveReportDataTableSearch").val('');
	$("#fromDate").val('');
	$("#toDate").val('');
	$("#departmentList").val('');
	$("#reportRoles").val('');
	$("#designationType").val('');
	$("#associateList").val('');
	$("#leaveTypeList").val('');
	$('#departmentList').trigger("chosen:updated");
	$('#reportRoles').trigger("chosen:updated");
	$('#designationType').trigger("chosen:updated");
	$('#associateList').trigger("chosen:updated");
	$('#leaveTypeList').trigger("chosen:updated");
	$("#leaveTypeList").val('');
}
function reportCollapse() {
	$("#reportCollapse").slideToggle(300);
	//$("#leaveReportsList").slideUp();
}
function resetReportCollapse() {
	$("#hearderId").hide();
	document.getElementById("showReport").style.display="none";
// 	document.getElementById("hearderId").style.display="none";
// 	var fromDate = $("#fromDate").val();
//     var toDate = $("#toDate").val();
// 	leaveReport(fromDate,toDate);

	
	$('#fromDate').attr('value', '');
	$('#fromDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$('#toDate').attr('value', '');
	$('#toDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$("#leaveReportDataTableSearch").val('');
	$("#leaveReportForm").val('');
	$("#fromDate").val('');
	$("#toDate").val('');
	$("#departmentList").val('');
	$("#reportRoles").val('');
	$("#designationType").val('');
	$("#associateList").val('');
	$("#leaveTypeList").val('');
 	$("#status").val('');
 	$('#status').trigger("chosen:updated");
	$('#departmentList').trigger("chosen:updated");
	$('#reportRoles').trigger("chosen:updated");
	$('#designationType').trigger("chosen:updated");
	$('#associateList').trigger("chosen:updated");
	$('#leaveTypeList').trigger("chosen:updated");
	$("#leaveTypeList").val('');
	$("#leaveReportsList, #searchReport").hide();
	$("#reportCollapse").show();
	
}
function searchReportCollapse() {
	$("#leaveReportDataTableSearch").val('');
	leaveReport();
}

</script>
<script>
$("#showReport").click(function() {
	document.getElementById("hearderId").style.display = "block";
	document.getElementById("leaveReportForm").style.display = "block";
	$("#showReport").hide();
	$("#reportCollapse").show();
})

</script>

<jsp:include page="../masterFooter.jsp" />
 
      
    