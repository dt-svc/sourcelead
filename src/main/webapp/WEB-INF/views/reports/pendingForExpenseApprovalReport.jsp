<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 
 <script>
$(document).ready(function() {
	getPendingApprovalExpenseReports();
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
    <section class="content-header clearfix dummywraper">
      <div class="pull-left">
        <h2>Pending for Approval Expenses</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo;<a href="${SourceLeadWeb_sourceleadUrl}/reportsDashboard"> Reports Dashboard </a>&raquo; <a href="${SourceLeadWeb_sourceleadUrl}/hrReportsList">HR Reports</a> &raquo; Pending for Approval</div>
    </section>

<div class="clearfix backgroundWhite ReqRepHead"> 
            <ul class="list-unstyled mb5">
            <li>
            <div class="clearfix mt5">
            <div class="col-md-8">
            <select class="chosen-select form-control" multiple data-placeholder="Select Project" name="projectIds" id="projectIds">
            <c:forEach var="option" items="${projectList}">
				<option value="${option.id}">${option.projectName}</option>
			</c:forEach>
          </select>
          </div>
        <div class="positionRelative col-md-3">
        <select class="form-control sel" name="dateSelection" id="dateSelection" onchange="getJobsReportCustomDates();">
            <option value='Last_7_days' >Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month' selected>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD' >YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
    <!-- Top Leave Takers Custom Date -->
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display:none; width:402px; z-index: 10000; ">
            <div class="pull-left">
              <div class="pull-left mr5" style="width: 140px" id="errMsgFrm">
                <input type="text" class="form-control calendar margin-top0" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
                <p id="fromDateDiv" style="display: none; font-size: 12px;"><font color="red">From Date Required</font></p>
              </div>
              
              <div class="pull-left mr5" style="width: 140px" id="errMsgTo">
                <input type="text" class="form-control calendar margin-top0" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
                <p id="toDateDiv" style="display: none; font-size: 12px;"><font color="red">To Date Required</font></p>
              </div>
              <button type="button" class="btn btn-primary btn-sm" onclick="getPendingApprovalExpenseReports();"><i class="fa fa-search"></i></button>
               <button type="button" class="btn btn-default btn-sm" onclick="resetDatepickers();">Reset</button>
            </div>
          </div>
      </div>

     <div class="col-md-1">
        <button type="button" class="btn btn-primary btn-sm	mt2 btn-block" onclick="getPendingApprovalExpenseReports();">Run</button>
		</div>
		</div>
            </li>
            </ul>
          </div>
          
          <div class="clearfix"></div>
          
          <div class="timesheet_head clearfix backgroundWhite dummywraper">
          	<ul>
          	<li><a href="javascript:void(0)" class="overIcon" id="exportOptions" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span class="noPad">
							<a href="javascript:void(0)" onclick="reportExport('pdf');" title="pdf">Export Pdf</a>      
							<a href="javascript:void(0)" onclick="reportExport('Excel');" title="Excel">Export Excel</a>         
						</span>
					</div>
				</li>
          	</ul>
          
          </div>

  <div class="vendor_wrap dummywraper">
      <div class="clients_wrap_table datatable_scroll datatable_filter">
      <div id="loadingId"></div>
         <div id="pendingForApprovalExpenseDivDiv"></div>
      </div>
	<div class="clearfix"></div>
	</div>
    
  </section>
 
 <script>
function getPendingApprovalExpenseReports(){
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
		  $("#loadingId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	     $.ajax({ 
		   	type: "POST",
		   	url: sourceleadUrl+"/getpendingForExpenseReportList?${_csrf.parameterName}=${_csrf.token}&projectIds="+projectIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate,
		    success: function(data) {
		    	var result = "";
		    	result = result	+ '<table class="noMar" id="pendingForApprovalExpenseDiv">';
		        result = result	+ '<thead> ';
		        result = result	+ '  <tr>  ';
		        result = result	+ '    <th>Emp Id</th> ';
		        result = result	+ '    <th>Emp Name</th> ';
		        result = result	+ '    <th>Emp Type</th> ';
		        result = result	+ '    <th>Project</th>  ';
		       // result = result	+ '    <th>Duration</th>';
		        result = result	+ '    <th>Approval<br/>Mechanism</th> ';
		        result = result	+ '    <th>Manager One</th> ';
		        result = result	+ '    <th>Manager Two</th> ';
		        result = result	+ '    <th>Submitted<br/>Amount</th> ';
		        result = result	+ '    <th>Submitted On</th> ';
		        result = result	+ '    <th>Approved By</th>';
		     //   result = result	+ '    <th>no.of days Pending</th>';
		        result = result	+ '  </tr> ';
		        result = result	+ '  </thead>';
		        result = result +'<tbody>';
		        
		       for (var i=0; i<data.length; i++)
   				{
		    	   var report = data[i];
		    	   var employeId="";
	        		  if(report.employeId != null && report.employeId != '' && report.employeId != 'undefined'){
	        			  employeId = report.employeId;
	        		  }else{
	        			  employeId = "--";
	        		  }
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
	        		 /*  if(report.frequency != null && report.frequency != '' && report.frequency != 'undefined'){
	        			  frequency = report.frequency;
	        		  }else{
	        			  frequency = "--";
	        		  } */
	        		  if(report.approvalMechanism != null && report.approvalMechanism != '' && report.approvalMechanism != 'undefined'){
	        			  approvalMechanism = report.approvalMechanism;
	        		  }else{
	        			  approvalMechanism = "--";
	        		  }
	        		  if(report.managerOne != null && report.managerOne != '' && report.managerOne != 'undefined'){
	        			  managerOne = report.managerOne;
	        		  }else{
	        			  managerOne = "--";
	        		  }
	        		  if(report.managerTwo != null && report.managerTwo != '' && report.managerTwo != 'undefined'){
	        			  managerTwo = report.managerTwo;
	        		  }else{
	        			  managerTwo = report.managerOne;
	        			 // managerOne = "--";
	        		  }
	        		  if(report.submittedAmount != null && report.submittedAmount != '' && report.submittedAmount != 'undefined'){
	        			  submittedAmount = report.submittedAmount;
	        		  }else{
	        			  submittedAmount = "--";
	        		  }
	        		  if(report.submittedOn != null && report.submittedOn != '' && report.submittedOn != 'undefined'){
	        			  submittedOn = report.submittedOn;
;
	        		  }else{
	        			  submittedOn = "--";
	        		  }
	        		  if(report.approvedBy != null && report.approvedBy != '' && report.approvedBy != 'undefined'){
	        			  approvedBy = report.approvedBy;
	        		  }else{
	        			  approvedBy = "--";
	        		  }
	        		 /*  if(report.reportingManager != null && report.reportingManager != '' && report.reportingManager != 'undefined'){
	        			  reportingManager = report.reportingManager;
	        		  }else{
	        			  reportingManager = "";
	        		  } */
	        		  /*   if(report.noofDaysPending != null && report.noofDaysPending != '' && report.noofDaysPending != 'undefined'){
	        			  noofDaysPending = report.noofDaysPending;
	        		  }else{
	        			  noofDaysPending = "--";
	        		  }   */
		    	   
		    	  result = result	+ '<tr>';
		    	  result = result	+ '	  <td style="vertical-align: middle;"><div title ="'+report.employeId +'" class="tblminwidth-50">'+employeId+'</div></td>';
				  result = result	+ '	  <td style="vertical-align: middle;"><div title ="'+report.employeName +'" class="tbldata-dotdot-sm">'+employeName+'</div></td>';
	              result = result	+ '   <td style="vertical-align: middle;"><div title ="'+report.employeType +'" class="tblminwidth-50">'+employeType+'</div></td>';
	              result = result	+ '   <td style="vertical-align: middle;"><div title ="'+report.projectName +'" class="tbldata-dotdot-sm">'+projectName+'</div></td>';
	              //result = result	+ '   <td><div title ='+report.employeId +'><class="tbldata-dotdot-me">'+frequency+'</div></td>';
	              result = result	+ '   <td style="vertical-align: middle;"><div title ="'+report.approvalMechanism +'" class="tbldata-dotdot-sm">'+approvalMechanism+'</div></td>';
	              result = result	+ '   <td style="vertical-align: middle;"><div title ="'+report.managerOne +'" class="tbldata-dotdot-sm">'+managerOne+'</div></td>';
	             /*  result = result	+ '   <td style="vertical-align: middle;"><div title ="'+report.managerOne +','+report.reportingManager+'" class="tbldata-dotdot-vsm">'+managerOne + (reportingManager != null ? ','+reportingManager: "")+' </div></td>'; */
	              result = result	+ '   <td style="vertical-align: middle;"><div title ="'+report.managerTwo +'" class="tbldata-dotdot-vsm">'+managerTwo+'</div></td>';
	              result = result	+ '   <td style="vertical-align: middle;"><div title ="'+report.submittedAmount +'" class="tblminwidth-50">'+submittedAmount+'</div></td>';
	              result = result	+ '   <td style="vertical-align: middle;"><div title ="'+report.submittedOn +'" class="tbldata-dotdot-vsm">'+submittedOn+'</div></td>';
	              result = result	+ '   <td style="vertical-align: middle;"><div title ="'+report.approvedBy +'" class="tbldata-dotdot-vsm">'+approvedBy+'</div></td>';
	             // result = result	+ '   <td style="vertical-align: middle;"><div title ="'+report.noofDaysPending +'" class="width300px">'+noofDaysPending+'</div></td>';
	              result = result	+ '	</tr>';
   				}
		        result = result +'</tbody>';
		        result = result + '</table>';
		        
		        $("#pendingForApprovalExpenseDivDiv").empty();
		        $("#loadingId").removeAttr('style');
				$("#pendingForApprovalExpenseDivDiv").append(result);
				dynamicMissingTimesheetReportPagination();
		    }
		 });
	  }	  
}
</script>


<script>
function resetDatepickers() {
location.reload(true);
}
$('.content-wraper, .content-header, .reportHead, .vendor_wrap').click(function(){
	$("#customSearch").hide();
})
$("#customSearch, .clearfix, .pull-lefet, .col-md-12, .paddingRight, .customLeaves, #errMsgFrm, #errMsgTo, #toDate, #fromDate").click(function(e) {
	e.stopPropagation();
});
</script>

<script>
function dynamicMissingTimesheetReportPagination(){  
    $('#pendingForApprovalExpenseDiv').DataTable( {
    	//"dom": 'Bfrtip',
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
    $('#pendingForApprovalExpenseDiv').wrap("<div class='width100percnt overflow_Auto'> </div>")
    $("#pendingForApprovalExpenseDivDiv").find('a.buttons-excel').prop("title", "Export to Excel");
	$("#pendingForApprovalExpenseDivDiv").find('a.buttons-pdf').prop("title", "Export to PDF");
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
      function reportExport(type){
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
    		  window.location.href =  sourceleadUrl+"/reportExport?&type="+type+"&projectIds="+projectIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate;
s  	}
      }
      </script>
<jsp:include page="../masterFooter.jsp" />

<script>
	function newActions() {
		$(".newActPop").slideToggle(200);
		$(".action-pop").slideUp(200);
	}
	$(document).ready(function(){
		$('html').click(function(){
			$(".newActPop").slideUp(200);
		});
		$(".dummywraper").click(function(){
			$(".newActPop").slideUp(200);
		});
	 });
	$(".overIcon, .newActPop").click(function(e) {
		e.stopPropagation();
	});
</script>