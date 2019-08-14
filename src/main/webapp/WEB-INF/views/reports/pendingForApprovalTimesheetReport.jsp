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
	 if(dateSelection === 'custom'){
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
        <h2>Pending for Approval Timesheets</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo;<a href="${SourceLeadWeb_sourceleadUrl}/reportsDashboard"> Reports Dashboard </a>&raquo; <a href="${SourceLeadWeb_sourceleadUrl}/hrReportsList">HR Reports</a> &raquo; Pending for Approval</div>
    </section>

    <div class="ReqRepHead clearfix">
    <div class="col-md-8 clearfix pull-right">
	<div class="col-md-6 noPad">
	      <select class="chosen-select form-control" multiple data-placeholder="Select Project" name="projectIds" id="projectIds">
            <c:forEach var="option" items="${projectList}">
				<option value="${option.id}">${option.projectName}</option>
			</c:forEach>
          </select>
	</div>
	
   <div class="col-md-5 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" name="dateSelection" id="dateSelection" onchange="getJobsReportCustomDates();">
            <option value='Last_7_days' >Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month' selected>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD' >YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
    <!-- Top Leave Takers Custom Date -->
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="width: 346px;">
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
     <div class="pull-left">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick="getPendingForApprovalTimesheetReports();">Run</button>
		</div>
		<div class="clearfix"></div>
		</div>
    </div>
    
     
   <div class="vendor_wrap mt">
	<div class="tableStyles overflow_inherint">
      <div class="clearfix datatable_filter positionRelative mt10 exportButton_custom">
      	<div class="positionRelative timesheet_head clearfix minHeight50">
      	<ul class="pull-right list-unstyled list-inline">
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" id ="exportDiv" onclick="newActions();"></a>
					<div class="newActPop" style="display: none;">
						<span>
		           	   		<a href="javascript:void(0)" onclick="timesheetsPendingExport('Pdf');" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="timesheetsPendingExport('Excel');" title="Excel">Export Excel</a>            
						</span>
					</div>
				</li>
        </ul>
      	</div>
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
		        result = result	+ '    <th>Manager 1</th> ';
		        result = result	+ '    <th>Manager 2</th> ';
		        result = result	+ '    <th>Approval Mechanism</th> ';
		        result = result	+ '    <th>No.of hours</th> ';
		        result = result	+ '    <th>Approved By</th> ';
		        result = result	+ '    <th>Approved Date</th> ';
		        result = result	+ '    <th>Submitted On</th> ';
		        result = result	+ '    <th>no.of days</th>';
		        result = result	+ '  </tr> ';
		        result = result	+ '  </thead>';
		        result = result +'<tbody>';
		       for (var i=0; i<data.length; i++)
   				{
		    	   var report = data[i];
		    	   var employeName="";
	        		  if(report.employeName != null && report.employeName != '' && report.employeName != 'undefined'){
	        			  employeName = report.employeName;
	        		  }else{
	        			  employeName = "--";
	        		  }
	        		  if(report.employeType != null && report.employeType != '' && report.employeType != 'undefined'){
	        			  employeType = report.employeType;
	        		  }else{
	        			  employeType = "--";
	        		  }
	        		  if(report.projectName != null && report.projectName != '' && report.projectName != 'undefined'){
	        			  projectName = report.projectName;
	        		  }else{
	        			  projectName = "--";
	        		  }
	        		  if(report.frequency != null && report.frequency != '' && report.frequency != 'undefined'){
	        			  frequency = report.frequency;
	        		  }else{
	        			  frequency = "--";
	        		  }
	        		  if(report.manager1 != null && report.manager1 != '' && report.manager1 != 'undefined'){
	        			  manager1 = report.manager1;
	        		  }else{
	        			  manager1 = "--";
	        		  }
	        		  if(report.manager2 != null && report.manager2 != '' && report.manager2 != 'undefined'){
	        			  manager2 = report.manager2;
	        		  }else{
	        			  manager2 = manager1;
	        			  manager1 = "--";
	        		  }
	        		  if(report.approvalMechanism != null && report.approvalMechanism != '' && report.approvalMechanism != 'undefined'){
	        			  approvalMechanism = report.approvalMechanism;
	        		  }else{
	        			  approvalMechanism = "--";
	        		  }
	        		  if(report.noOfHours != null && report.noOfHours != '' && report.noOfHours != 'undefined'){
	        			  noOfHours = report.noOfHours;
	        		  }else{
	        			  noOfHours = "--";
	        		  }
	        		  if(report.approved_By != null && report.approved_By != '' && report.approved_By!= 'undefined'){
	        			  approved_By = report.approved_By;
	        		  }else{
	        			  approved_By = "--";
	        		  }
	        		  if(report.approvedDate != null && report.approvedDate != '' && report.approvedDate!= 'undefined'){
	        			  approvedDate = report.approvedDate;
	        		  }else{
	        			  approvedDate = "--";
	        		  }
	        		  if(report.dueDays != null && report.dueDays != '' && report.dueDays != 'undefined'){
	        			  dueDays = report.dueDays;
	        		  }else{
	        			  dueDays = "--";
	        		  }
		    	   
		    	  result = result	+ '<tr>';
				  result = result	+ '	  <td><div class="tbldata-dotdot-sm" title="'+employeName+'">'+employeName+'</div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm" title="'+employeType+'">'+employeType+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm" title="'+projectName+'">'+projectName+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-me" title="'+frequency+'">'+frequency+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm" title="'+manager1+'">'+manager1+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm" title="'+manager2+'">'+manager2+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm" title="'+approvalMechanism+'">'+approvalMechanism+' </div></td>';
	              result = result   + '   <td><div class="tbldata-dotdot-sm" title="'+noOfHours+'">'+noOfHours+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm" title="'+approved_By+'">'+approved_By+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm" title="'+approvedDate+'">'+approvedDate+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-sm" title="'+report.submittedOn+'">'+report.submittedOn+' </div></td>';
	              result = result	+ '   <td><div class="tbldata-dotdot-vsm" title="'+dueDays+' Days">'+dueDays+'  Days</div></td>';
	              result = result	+ '	</tr>';
   				}
		        result = result +'</tbody>';
		        result = result + '</table>';
		        
		        if(report != "" && report != null && report != 'undefined'){
		        	$("#pendingForApprovalTimesheetDiv").empty();
					$("#pendingForApprovalTimesheetDiv").append(result);
					$("#exportDiv").show();
		        }else{
		        	$("#pendingForApprovalTimesheetDiv").empty();
					$("#pendingForApprovalTimesheetDiv").append(result);
		        	$("#exportDiv").hide();
		        }
				dynamicMissingTimesheetReportPagination();
				document.getElementById("customSearch").style.display="none";
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
    	"buttons": ['excel', 'pdf'],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#pendingForApprovalTimesheet').wrap("<div class='overflow_Auto width100percnt'></div>");
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
  
  <script>
  function getActions(){
  	$(".action-pop").slideToggle(200);
  	$(".newActPop").slideUp(200);
  }	
  $("html").click(function(){
  	$(".action-pop").slideUp(200);
  });
  $(".action, .action-pop").click(function(e){
   	e.stopPropagation();
  });	


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
  
  <!-- ============================================= Export related functionality  By Akshay   ====================================== -->
  
  <script>
  function timesheetsPendingExport(exportType){
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
		window.location.href = sourceleadUrl+"/getTimesheetsPendingExportListByOrgLocationId?${_csrf.parameterName}=${_csrf.token}&projectIds="+projectIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate+"&exportType="+exportType;
		}
	}
  </script>
<jsp:include page="../masterFooter.jsp" />