<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 
 <script>
$(document).ready(function() {
	getPendingForApprovalTimesheetReports();
});	
</script>
 <script>
function getJobsReportCustomDates(){
	 var dateSelection = $("#dateSelection").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 $("#jobReportList_custome").attr("disabled", "disabled");
		 
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
	 }
}
function reloadFunction(){
	location.reload();
}
</script>
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Pending for Approval</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>"> Report(s) Dashboard </a>&raquo; Pending for Approval</div>
    </section>

    <div class="ReqRepHead clearfix">
		<div class="col-md-3"></div>
	<div class="col-md-4">
	      <select class="chosen-select form-control" multiple data-placeholder="Select Job" name="projectIds" id="projectIds">
            <c:forEach var="option" items="${projectList}">
				<option value="${option.id}">${option.projectName}</option>
			</c:forEach>
          </select>
	</div>
	
   <div class="col-md-2 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" name="dateSelection" id="dateSelection" onchange="getJobsReportCustomDates();">
            <option value=''>Select Date</option>
            <option value='Last_7_days' selected >Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD' >YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
    <!-- Top Leave Takers Custom Date -->
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display: none; width: 346px;">
            <div class="pull-left">
              <div class="pull-left mr5" style="width: 140px" id="errMsgFrm">
                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
                <p id="fromDateDiv" style="display: none; font-size: 12px;"><font color="red">From Date Required</font></p>
              </div>
              
              <div class="pull-left mr5" style="width: 140px" id="errMsgTo">
                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
                <p id="toDateDiv" style="display: none; font-size: 12px;"><font color="red">To Date Required</font></p>
              </div>
              <button type="button" class="btn btn-primary btn-sm" onclick="getPendingForApprovalTimesheetReports();"><i class="fa fa-search"></i></button>
            </div>
          </div>
      </div>

        </div>
     <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="getPendingForApprovalTimesheetReports();">Run</button>
		</div>
      <div class="col-md-1 noPad">
        <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
          <button type="button" class="btn btn-primary" title="Reload" onclick="reloadFunction();"><i class="fa fa-retweet"></i></button>
        </div>
      </div>
      
    </div>
     
  <div class="vendor_wrap mt">
	<div class="tableStyles">
      <div class="clearfix datatable_filter positionRelative dataFilterNew mt10 exportButton_custom">
         <div id="pendingForApprovalTimesheetDiv"></div>
      </div>

	<div class="clearfix"></div>
	</div>
	</div>
    
  </section>
 
 <script>
function getPendingForApprovalTimesheetReports(){
	var flag = true;
	  var projectIds = $("#projectIds").val();
	  if(projectIds == null){
		  projectIds = '';
	  }
	  var dateSelection = $("#dateSelection").val(); 
	  var fromDate = '';
	  var toDate = '';
	  if(dateSelection == 'custom'){
		   fromDate = $("#fromDate").val();
		   toDate = $("#toDate").val();
		  
		  if(fromDate == '' || toDate == ''){
			  flag = false;
			  if(fromDate == ''){
				  document.getElementById("fromDateDiv").style.display="block";
			  }else{
				  document.getElementById("fromDateDiv").style.display="none";
			  }
			  if(toDate == ''){
				  document.getElementById("toDateDiv").style.display="block";
			  }else{
				  document.getElementById("toDateDiv").style.display="none";
			  }
		  }else{
			  document.getElementById("fromDateDiv").style.display="none";
			  document.getElementById("toDateDiv").style.display="none";
		  }
		  
	  }
	  if(flag){
	     $.ajax({ 
		   	type: "POST",
		   	url: sourceleadUrl+"/getpendingForApprovalTimesheetReportList?${_csrf.parameterName}=${_csrf.token}&projectIds="+projectIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate,
		    success: function(data) {
		    	var result = "";
		    	result = result	+ '<table class="table table-bordered noMar" id="pendingForApprovalTimesheet">';
		        result = result	+ '<thead> ';
		        result = result	+ '  <tr>  ';
		        result = result	+ '    <th>Emp Name</th> ';
		        result = result	+ '    <th>Emp Type</th> ';
		        result = result	+ '    <th>Project</th>  ';
		        result = result	+ '    <th>Duration</th>';
		        result = result	+ '    <th>Manager</th> ';
		        result = result	+ '    <th>Due</th>';
		        result = result	+ '  </tr> ';
		        result = result	+ '  </thead>';
		        result = result +'<tbody>';
		       for (var i=0; i<data.length; i++)
   				{
		    	   var report = data[i];
		    	  result = result	+ '<tr>';
				  result = result	+ '	  <td><div class="tbldata-dotdot-sm">'+report.employeName+'</div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm">'+report.employeType+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm">'+report.projectName+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-me">'+report.frequency+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm">'+report.managerName+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-vsm">'+report.dueDays+'  Days</div></td>';
	              result = result	+ '	</tr>';
   				}
		        result = result +'</tbody>';
		        result = result + '</table>';
		        
		        $("#pendingForApprovalTimesheetDiv").empty();
				$("#pendingForApprovalTimesheetDiv").append(result);
				dynamicMissingTimesheetReportPagination();
		    }
		 });
	  }	  
}
</script>

<script>
function dynamicMissingTimesheetReportPagination(){  
    $('#pendingForApprovalTimesheet').DataTable( {
    	"dom": 'Bfrtip',
    	"responsive": true,
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"buttons": [
    		'pageLength', 'excel', 'pdf'
        ],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#pendingForApprovalTimesheetDiv").find('a.buttons-excel').prop("title", "Export to Excel");
	$("#pendingForApprovalTimesheetDiv").find('a.buttons-pdf').prop("title", "Export to PDF");
}
</script>


 <script>
  $( function() {
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
				removeErrsgFrom();
				
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
				removeErrTo();
			}
		});
  } );
  
  function removeErrsgFrom (){
	  document.getElementById("fromDateDiv").style.display="none";
	  
  }
  function removeErrTo(){
	  document.getElementById("toDateDiv").style.display="none";
  }
  </script>
<jsp:include page="../masterFooter.jsp" />