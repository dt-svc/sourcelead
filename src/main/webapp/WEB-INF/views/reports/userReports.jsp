<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

<!-- Amchart style sheet and script: For billing Report for demo only -->
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" />
<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script src="https://www.amcharts.com/lib/3/serial.js"></script>
<script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<script src="https://www.amcharts.com/lib/3/themes/light.js"></script>
<!-- Amchart style sheet and script: For billing Report for demo only -->


<style>
#billing_chartdiv {
  	width:100%; height:300px;
}																		
</style>
<script type="text/javascript">
	window.onload = function () {
		usersList();
	 };
</script>
   
   
<input type="hidden" id="noRecruiters" name="noRecruiters"/>
<section class="content-wraper">
  <section class="content-header clearfix">
   <c:if test="${!empty msg}">
	        <div class="success_pop" id="sucesspop"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
   <c:if test="${requirementTabAccess == true }">
    
    <div class="pull-left">
      <h2> Reports Dashboard</h2>
    </div>
    	<div class=" pull-left reportsH">
			<a title="Reports"><i class="fa fa-pie-chart"></i></a>
			<div class="RePop">
				<ul>
					<li><a href="<c:url value='/requirementListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Posted</a></li>
					<li><a href="<c:url value='/requirementStagesListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Stages</a></li>
<%-- 					<li><a href="<c:url value='/applicantsReceivedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Received</a></li> --%>
				    <li><a href="<c:url value='/applicantsListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants</a></li>
				    <li><a href="<c:url value='/applicantsSubmittedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Submitted Applicants</a></li>
<%-- 				    <li><a href="<c:url value='/requirementAssignToReports'/>"><span><i class="fa fa-briefcase"></i></span>Job Assign To</a></li> --%>
				    <li><a href="<c:url value='/candidateListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Added Candidates</a></li>
				    <li><a href="<c:url value='/timeSheetReports'/>"><span><i class="fa fa-briefcase"></i></span>TimeSheet Reports</a></li>
				    <li><a href="<c:url value='/expensesReportsList'/>"><span><i class="fa fa-briefcase"></i></span>Expenses Reports</a></li>
				</ul>
			</div>
		</div> 
		
   </c:if>		 
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo;   Reports Dashboard</div>
 <div class="clearfix"></div>
 
 
 
 
  </section>
   
 
 <c:if test="${requirementTabAccess == true }">
  <div style="overflow: auto;" class=" vendor_wrap"> 
   
   <div class="pull-left reportBtns mt5 mb10">
<h4 class="pull-left">Recruiters Report</h4>
		  <div class="reportBtn select-field  pull-left ml">
			<select class="form-control" id="reportTypeId" onChange="flushCustom(); getDayWiseReports(); custDates();">
<!-- 			 <option value="">select</option> -->
			  <option value="today">Today</option>
			  <option value="yesterday">Yesterday</option>
			  <option value="week">1 Week ago</option>
			  <option value="twoweeks">2 Weeks ago</option>
			  <option value="month">1 Month ago</option>
			  <option value="twomonths">2 Months ago</option>
			   <option value="custom">Custom</option> 
			</select>
		 </div>	
		 <span class="dashboard-datepkr cusShow">
		 <input type="text" class="form-control" value="" id="ReportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
		 <p id="reportsStartDate" class="ReError"></p>
		 </span>
		<span class="dashboard-datepkr cusShow">
		<input type="text"  class="form-control" value="" id="ReportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
		<p id="reportsEndDate" class="ReError"></p>
		</span>
		<span class="cusShow"><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="getCustomReports();" /></span>
		<div class="clearfix"></div>
  </div>
      
      <div class="candidate_wrap_table recruiterReports_table">
        <div id="usersList" class="datatable_filter"></div>
      </div> 
 </div>
 </c:if>
 <c:if test="${vendorTabAccess == true }">
 <div class="timesheet_out mt40">
    <div class="vendor_wrap">
      <div class="timesheet_head">
<div class="pull-left reportBtns mt5 mb10">
<h4 class="pull-left mr">Vendors Report</h4>
	  <div class="reportBtn select-field pull-left">
		<select class="form-control" id="vendorreportTypeId" onChange="flushVendorCustomDates(); getVendorsReportList(); custVendorDates();">
		  <option value="today">Today</option>
		  <option value="yesterday">Yesterday</option>
		  <option value="week">1 Week ago</option>
		  <option value="twoweeks">2 Weeks ago</option>
		  <option value="month">1 Month ago</option>
		  <option value="twomonths">2 Months ago</option>
		   <option value="custom">Custom</option> 
		</select>
	 </div>	
	 <!-- <span class="dashboard-datepkr"><input type="text" class="input-sm hasDatepicker" id="datepicker"><i class="fa fa-calendar"></i></span>
	 <span class="dashboard-datepkr"><input type="text" class="input-sm hasDatepicker" id="datepicker1"><i class="fa fa-calendar"></i></span>	 -->
	 <div id="dateShow" style="display:none">
	 <span class="dashboard-datepkr">
	 <input type="text" class="form-control" value="" id="vendorStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
	 <p id="vendorreportStartDate" class="ReError"></p>
	 </span>
	<span class="dashboard-datepkr">
	<input type="text"  class="form-control" value="" id="vendorEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
	<p id="vendorreportEndDate" class="ReError"></p>
	</span>
	<span class=""><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="getVendorCustomReports();" /></span>
	</div>
	
        <div class="clearfix"></div>
      </div>
  
 <div class="clearfix">
<section class="candidate_wrap_table recruiterReports_table">

<div id="vendorReportsDiv" class="datatable_filter"></div>

<div id="vendorSpocReportsDiv" class="repoPositiNoCount"></div>

</section>    
 </div>
      
 </div>


  </div>
  </div>
  </c:if>
  
  <%-- <c:if test="${billingTabAccess == true }"> --%>
 <div class="timesheet_out mt40">
    <div class="vendor_wrap clearfix">
      <div class="timesheet_head">
<div class="pull-left reportBtns mt5 mb10">
<h4 class="pull-left mr">Billing Report</h4>
	  <div class="reportBtn select-field pull-left">
		<select class="form-control" id="billingReportType" onChange="flushBillingCustom(); getBillingReports('Receivable');custBillingDates();">
		  <option value="THIS_MONTH">This Month</option>
		  <option value="THIS_QUARTER">This quarter</option>
		  <option value="THIS_YEAR">This Year</option>
		  <option value="LAST_30DAYS">Last 30 days</option>
		  <option value="LAST_MONTH">Last Month</option>
		  <option value="LAST_QUARTER">Last quarter</option>
		  <option value="LAST_YEAR">Last Year</option>
		   <option value="CUSTOM">Custom</option> 
		</select>
	 </div>	
	 
	  <span class="dashboard-datepkr cusShowBilling">
	 <input type="text" class="form-control" value="" id="billingReportStartDate" name="billingReportStartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
	 <p id="billingErrorReportStartDate" class="ReError"></p>
	 </span>
	<span class="dashboard-datepkr cusShowBilling">
	<input type="text"  class="form-control" value="" id="billingReportEndDate" name="billingReportEndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
	<p id="billingErrorReportEndDate" class="ReError"></p>
	</span>
	<span class="cusShowBilling"><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="getCustomBillingReports('Receivable');" /></span>
        <div class="clearfix"></div>
      </div>
 </div>

<!--  Report Div starts here: Rajashekar -->

<!-- Report Chart desing -->
<div class="clearfix"></div>
          <div class="clearfix mt10">
              <div class="col-md-3">
                  
                  <div class="clearfix billing_count">
                      <h5>${SourceLeadWeb_currencyCode}</h5><span class="rupee_usd" id="netIncome"></span>
                      <span class="text-info">Net Income</span>
                  </div>
                  
                  <div class="clearfix billingCount borderGreen">
                      <h5>${SourceLeadWeb_currencyCode}</h5><span class="rupee_usd" id="receivedAmount"></span>
                      <span class="text-info">Income</span>
                  </div>
                  <div class="clearfix billingCount borderBlue">
                      <h5>${SourceLeadWeb_currencyCode}</h5><span class="rupee_usd" id="payableAmount"></span>
                      <span class="text-info">Expenses</span>
                  </div>
                  
              </div>
<!--               <div class="col-md-9">
                  <div id="billing_chartdiv">
                    
                  </div>
              </div> -->
              
              <div class="col-md-9 RepoPad">
<div class="project_side_wrap_inn_tabs">
          <div class="clearfix">
            <div id="billing_chartdiv">
            
            <!-- <div id="tab-1" class="project_tabs_inn datatable_filter">
            </div> -->

			<div id="tab-2" class="project_tabs_inn datatable_filter">
          </div>
        </div> 
              </div>
              
          </div>
          <!-- Report Chart desing -->

<!--  Report Div starts here Ends: Rajashekar -->  
  
  
  
  
  </div>
  </div>
 <%--  </c:if> --%>
  
  
  
  </section>
   
<div class="mask_time"></div>

<div class="pavPop">
<span class="pavClose" onclick="pavClose();"><i class="fa fa-times"></i></span>
		<div id="vendorspc" class="clients_wrap_table datatable_filter"></div>
</div> 
    
    
 <%-- 
     @author : prasad K.
     this script is for get Recruiters Report.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>

<script> 
$(document).ready(function(){
	usersList();
	var invoiceTYpe = "Receivable";
	getBillingReports(invoiceTYpe);
});
</script>

<style>
#resumesDiv select{
width: 100% !important;
}
.reportBtns {
width: 65%;
}
.reportBtn  {
width: 20%;
}
</style>



<script>
function usersList(){ 
	var timePeriod = $('#reportTypeId').val(); 
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAllUsersByOrgLocationsId?${_csrf.parameterName}=${_csrf.token}&timePeriod="+timePeriod,    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
	                	
                	     $("#usersList").empty();
                	     
	                	 var result = "";
	                	 result = result +"<table id='reportPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"<th><i class='fa fa-address-book-o fa-2x'></i> <div class='small'>Name</div></th>";
	                     result = result +"<th><i class='fa fa-briefcase fa-2x'></i><div class='small'>Jobs</div></th>";
	                     result = result +"<th><i class='fa fa-user-circle fa-2x'></i><div class='small'>Candidates</div></th>";
	                     result = result +"<th><i class='fa fa-users fa-2x'></i><div class='small'>Applicants</div></th>"; 
	                     result = result +"<th><i class='fa fa-list-ul fa-2x'></i><div class='small'>Shortlisted</div></th>"; 
	                     result = result +"<th><i class='fa fa-send fa-2x'></i><div class='small'>Submitted</div></th>"; 
	                     result = result +"<th><i class='fa fa-handshake-o fa-2x'></i><div class='small'>Interviewed</div></th>"; 
	                     result = result +"<th><i class='fa fa-envelope-open-o fa-2x'></i><div class='small'>Offered</div></th>"; 
	                     result = result +"<th><i class='fa fa-ban fa-2x'></i><div class='small'>Rejected</div></th>";
	                     result = result +"<th><i class='fa fa-arrow-circle-left fa-2x'></i> <div class='small'>Back-Out</div></th>";
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     result = result + '<div class="new_side_wrap">';
	                 	 result = result + '<div class="new_side_wrap_inn">';
	                 	 result = result + '<div class="new_side_wrap_cta">';
	                 	 result = result +'<div id="addApplicantDiv"></div>';
	                 	 result = result +'</div>';
	                 	 result = result +'</div>';
	                 	 result = result +'</div>';
	                 	 
	                 	var json = JSON.parse(data);
	                 	
	                 	
	                 	var checkSize = json.length;
	                    
	                     for (var i = 0; i < checkSize; i++) { 
	                    	 var opt = json[i]; 
	                    	 
	                    	 if(opt.lastName != null && opt.lastName !='' && opt.lastName != 'undefined'){
	          			 		lastName = opt.lastName;
		          			 	}else{
		          			 		lastName = "";
		          			 	}
	                    	 result = result +"<tr>";
	                    	 
	                    	 if(opt.firstName !=null && opt.firstName !='' && opt.firstName !='undefined'){
	                    		 result = result +"<td><div class='tbldata-dotdot-me'><span title='"+opt.firstName+"'>"+opt.firstName+" "+lastName+"</span></a></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div> <span>----</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userRequirementsCount !=null && opt.userRequirementsCount !='' && opt.userRequirementsCount !='undefined'){
	                    		  result = result +"<td><div class='small'><span title='"+opt.userRequirementsCount+"'>"+opt.userRequirementsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
		                    
	                    	 if(opt.userCandidatesCount !=null && opt.userCandidatesCount !='' && opt.userCandidatesCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userCandidatesCount+"'>"+opt.userCandidatesCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userApplicantsCount !=null && opt.userApplicantsCount !='' && opt.userApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userApplicantsCount+"'>"+opt.userApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 
	                    	 if(opt.userShortlisteApplicantsCount !=null && opt.userShortlisteApplicantsCount !='' && opt.userShortlisteApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userShortlisteApplicantsCount+"'>"+opt.userShortlisteApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userSubmittedApplicantsCount !=null && opt.userSubmittedApplicantsCount !='' && opt.userSubmittedApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userSubmittedApplicantsCount+"'>"+opt.userSubmittedApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userInterviewedApplicantsCount !=null && opt.userInterviewedApplicantsCount !='' && opt.userInterviewedApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userInterviewedApplicantsCount+"'>"+opt.userInterviewedApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userOfferedApplicantsCount !=null && opt.userOfferedApplicantsCount !='' && opt.userOfferedApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userOfferedApplicantsCount+"'>"+opt.userOfferedApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userRejectedApplicantsCount !=null && opt.userRejectedApplicantsCount !='' && opt.userRejectedApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userRejectedApplicantsCount+"'>"+opt.userRejectedApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userApplicantsBackOutCount !=null && opt.userApplicantsBackOutCount !='' && opt.userApplicantsBackOutCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userApplicantsBackOutCount+"'>"+opt.userApplicantsBackOutCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 
		                    
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#noRecruiters").val('');
	                     $("#usersList").append(result); 
	                     reportpagination();
			 			 
	                	
                 }
          },
            
       }); 
  } 
</script>   


<script>
function reportpagination(){ 
    $('#reportPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script> 

<!-- Filter scripts start here -->
<script>
	$(function() {
		$('#billingReportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : 'dd-mm-yy',
			onSelect : function(selected) {
				var dt = new Date(selected);
				var dt = toDate("#billingReportStartDate");
				dt.setDate(dt.getDate() + 1);
				$("#billingReportEndDate").datepicker("option", "minDate", dt);
				addBillingValidClass();
			}
		
		});
		
		$('#billingReportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : 'dd-mm-yy',
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				var dt = toDate("#billingReportEndDate");
				dt.setDate(dt.getDate() - 1);
				$("#billingReportStartDate").datepicker("option", "maxDate", dt);
				 $( "#billingReportStartDate" ).datepicker({  maxDate: new Date() });
				addBillingValidClass();
			}
		});
		
	});
</script>	

<script type="text/javascript"> 
	function addBillingValidClass(){
		 var StartDate = document.getElementById("billingReportStartDate").value;
		 if(StartDate){
			 $('#billingErrorReportStartDate').empty() 
		 }  
		 
		 var EndDate = document.getElementById("billingReportEndDate").value;
		 if(EndDate){
			 $('#billingErrorReportEndDate').empty(); 
		 }
}
</script>

<script>
	$(function() {
		$('#ReportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : 'dd-mm-yy',
			onSelect : function(selected) {
				var dt = new Date(selected);
				var dt = toDate("#ReportStartDate");
				dt.setDate(dt.getDate() + 1);
				$("#ReportEndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		
		});
		
		$('#ReportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : 'dd-mm-yy',
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				var dt = toDate("#ReportEndDate");
				dt.setDate(dt.getDate() - 1);
				$("#ReportStartDate").datepicker("option", "maxDate", dt);
				 $( "#ReportStartDate" ).datepicker({  maxDate: new Date() });
				addValidClass();
			}
		});
		
	});
</script>	

<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script> 


<script type="text/javascript">
	function flushCustom() {
		$('#ReportStartDate').val('');
		$('#ReportEndDate').val('');
		$.datepicker._clearDate('#ReportStartDate');
		$.datepicker._clearDate('#ReportEndDate'); 
	}
</script>


<script type="text/javascript">
	function flushBillingCustom() { 
		$('#billingReportStartDate').val('');
		$('#billingReportEndDate').val('');
		$.datepicker._clearDate('#billingReportStartDate');
		$.datepicker._clearDate('#billingReportEndDate'); 
		addBillingValidClass();
	}
</script>
 <script>
function getDayWiseReports(){
	var timePeriod = $('#reportTypeId').val(); 
	if(timePeriod != "custom"){
		usersList(timePeriod);
	}
}

</script>
<script>
function custBillingDates(){
	var billingReportType = $("#billingReportType").val();
	if(billingReportType=="CUSTOM"){
	    $(".cusShowBilling").show();
	}
	if(billingReportType!="CUSTOM"){
	    $(".cusShowBilling").hide();
	}
}
</script>

<script>
function getCustomReports(){
	var timePeriod = $("#reportTypeId").val();
	var startDate = $("#ReportStartDate").val();
	var endDate = $("#ReportEndDate").val();
	
	var sourceleadUrl ='${sourceleadUrl}';
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#reportsStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#reportsEndDate").empty().append("End Date is Required") ; 
	}else{
		$.ajax({
	         type : "POST",
	         url: sourceleadUrl+"/getAllUsersByOrgLocationsId?${_csrf.parameterName}=${_csrf.token}&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate,     
	         success :function(data) {
	        	 if(data != null && data != 'undefined' && data != "" && data != '[]'){
	                	
            	     $("#usersList").empty();
                	 var result = "";
                	 result = result +"<table id='reportPagination' class='display' cellspacing='0' width='99.9%' >"; 
                     result = result +"<thead>"; 
                     result = result +"  <tr>"; 
                     result = result +"<th><i class='fa fa-address-book-o fa-2x'></i> <div class='small'>Name</div></th>";
                     result = result +"<th><i class='fa fa-briefcase fa-2x'></i><div class='small'>Jobs</div></th>";
                     result = result +"<th><i class='fa fa-user-circle fa-2x'></i><div class='small'>Candidates</div></th>";
                     result = result +"<th><i class='fa fa-users fa-2x'></i><div class='small'>Applicants</div></th>"; 
                     result = result +"<th><i class='fa fa-list-ul fa-2x'></i><div class='small'>Shortlisted</div></th>"; 
                     result = result +"<th><i class='fa fa-send fa-2x'></i><div class='small'>Submitted</div></th>"; 
                     result = result +"<th><i class='fa fa-handshake-o fa-2x'></i><div class='small'>Interviewed</div></th>"; 
                     result = result +"<th><i class='fa fa-envelope-open-o fa-2x'></i><div class='small'>Offered</div></th>"; 
                     result = result +"<th><i class='fa fa-ban fa-2x'></i><div class='small'>Rejected</div></th>";
                     result = result +"<th><i class='fa fa-arrow-circle-left fa-2x'></i> <div class='small'>Back-Out</div></th>";
                     result = result +"  </tr>"; 
                     result = result +"</thead>";
                     result = result +"<tbody>"; 
                     
                     result = result + '<div class="new_side_wrap">';
                 	 result = result + '<div class="new_side_wrap_inn">';
                 	 result = result + '<div class="new_side_wrap_cta">';
                 	 result = result +'<div id="addApplicantDiv"></div>';
                 	 result = result +'</div>';
                 	 result = result +'</div>';
                 	 result = result +'</div>';
                 	 
                 	var json = JSON.parse(data);
                 	
                 	
                 	var checkSize = json.length;
                    
                     for (var i = 0; i < checkSize; i++) { 
                    	 var opt = json[i]; 
                    	 result = result +"<tr>";
                    	 
                    	 if(opt.firstName !=null && opt.firstName !='' && opt.firstName !='undefined'){
                    		 result = result +"<td><div class='tbldata-dotdot-me'><span title='"+opt.firstName+"'>"+opt.firstName+" "+opt.lastName+"</span></a></div></td>";
                    	 }else{
                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <span>----</span></div></td>"; 
                    	 }
                    	 
                    	 if(opt.userRequirementsCount !=null && opt.userRequirementsCount !='' && opt.userRequirementsCount !='undefined'){
                    		  result = result +"<td><div class='small'><span title='"+opt.userRequirementsCount+"'>"+opt.userRequirementsCount+"</span></div></td>";
                    	 }else{
                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
                    	 }
	                    
                    	 if(opt.userCandidatesCount !=null && opt.userCandidatesCount !='' && opt.userCandidatesCount !='undefined'){
                    		  result = result +"<td><div class='small'> <span title='"+opt.userCandidatesCount+"'>"+opt.userCandidatesCount+"</span></div></td>";
                    	 }else{
                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
                    	 }
                    	 
                    	 if(opt.userApplicantsCount !=null && opt.userApplicantsCount !='' && opt.userApplicantsCount !='undefined'){
                    		  result = result +"<td><div class='small'> <span title='"+opt.userApplicantsCount+"'>"+opt.userApplicantsCount+"</span></div></td>";
                    	 }else{
                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
                    	 }
                    	 
                    	 
                    	 if(opt.userShortlisteApplicantsCount !=null && opt.userShortlisteApplicantsCount !='' && opt.userShortlisteApplicantsCount !='undefined'){
                    		  result = result +"<td><div class='small'> <span title='"+opt.userShortlisteApplicantsCount+"'>"+opt.userShortlisteApplicantsCount+"</span></div></td>";
                    	 }else{
                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
                    	 }
                    	 
                    	 if(opt.userSubmittedApplicantsCount !=null && opt.userSubmittedApplicantsCount !='' && opt.userSubmittedApplicantsCount !='undefined'){
                    		  result = result +"<td><div class='small'> <span title='"+opt.userSubmittedApplicantsCount+"'>"+opt.userSubmittedApplicantsCount+"</span></div></td>";
                    	 }else{
                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
                    	 }
                    	 
                    	 if(opt.userInterviewedApplicantsCount !=null && opt.userInterviewedApplicantsCount !='' && opt.userInterviewedApplicantsCount !='undefined'){
                    		  result = result +"<td><div class='small'> <span title='"+opt.userInterviewedApplicantsCount+"'>"+opt.userInterviewedApplicantsCount+"</span></div></td>";
                    	 }else{
                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
                    	 }
                    	 
                    	 if(opt.userOfferedApplicantsCount !=null && opt.userOfferedApplicantsCount !='' && opt.userOfferedApplicantsCount !='undefined'){
                    		  result = result +"<td><div class='small'> <span title='"+opt.userOfferedApplicantsCount+"'>"+opt.userOfferedApplicantsCount+"</span></div></td>";
                    	 }else{
                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
                    	 }
                    	 
                    	 if(opt.userRejectedApplicantsCount !=null && opt.userRejectedApplicantsCount !='' && opt.userRejectedApplicantsCount !='undefined'){
                    		  result = result +"<td><div class='small'> <span title='"+opt.userRejectedApplicantsCount+"'>"+opt.userRejectedApplicantsCount+"</span></div></td>";
                    	 }else{
                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
                    	 }
                    	 
                    	 if(opt.userApplicantsBackOutCount !=null && opt.userApplicantsBackOutCount !='' && opt.userApplicantsBackOutCount !='undefined'){
                   		  result = result +"<td><div class='small'> <span title='"+opt.userApplicantsBackOutCount+"'>"+opt.userApplicantsBackOutCount+"</span></div></td>";
	                   	 }else{
	                   		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                   	 }
	                    
	                     result = result +"</tr>";
                     }
                     result = result +" </tbody>";
                     result = result +"</table>"; 
                     $("#noRecruiters").val('');
                     $("#usersList").append(result); 
                     reportpagination();
             	}
	        },
	          
	     });  
	}
}
</script>
<script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("ReportStartDate").value;
		 if(StartDate){
			 $('#reportsStartDate').empty() 
		 }
		 
		 var EndDate = document.getElementById("ReportEndDate").value;
		
		 if(EndDate){
			 $('#reportsEndDate').empty(); 
		 }
}
</script>

<script>
function getBillingReportsChart(invoiceType,json){
	/* ;;; */
	 $("#netIncome").html(json.netAmount);
	 $("#receivedAmount").html(json.receivedAmount);
	 $("#payableAmount").html(json.payableAmount); 
	 
	 //startatatat============
	 var chartData = [];

/* function generateChartData() {  ;;;*/
  var firstDate = new Date();
  for (var i = 0; i < json.billingGraphBean.length; i++) { 
		 var opt = json.billingGraphBean[i]; 
        chartData.push({
        	country: opt.date,
        	year2004: opt.income,
        	year2005: opt.expenses
      });
  }
	var chart = AmCharts.makeChart("billing_chartdiv", {
	    "theme": "light",
	    "type": "serial",
	    "dataProvider":chartData ,
	    "valueAxes": [{
	        "unit": "",
	        "position": "left",
	        "title": "GDP growth rate",
	    }],
	    "startDuration": 1,
	    "graphs": [{
	        "balloonText": "GDP grow in [[category]] (2004): <b>[[value]]</b>",
	        "fillAlphas": 0.9,
	        "lineAlpha": 0.2,
	        "title": "2004",
	        "type": "column",
	         "columnWidth":0.5,
	        "valueField": "year2004"
	    }, {
	        "balloonText": "GDP grow in [[category]] (2005): <b>[[value]]</b>",
	        "fillAlphas": 0.9,
	        "lineAlpha": 0.2,
	        "title": "2005",
	        "type": "column",
	        "clustered":true,
	        "columnWidth":0.5,
	        "valueField": "year2005"
	    }],
	    "plotAreaFillAlphas": 0.1,
	    "categoryField": "country",
	    "categoryAxis": {
	        "gridPosition": "start"
	    },
	    "export": {
	        "enabled": true
	     }

	});
}
</script>

 <script>
function getBillingReports(invoiceType){
	 $("#netIncome").html(0);
	 $("#receivedAmount").html(0);
	 $("#payableAmount").html(0);
	$('#billingErrorReportStartDate').empty();
	$('#billingErrorReportEndDate').empty(); 
		var sourceleadUrl = '${sourceleadUrl}';
		var billingReportType = $("#billingReportType").val();
		var startDate = null;
		var endDate = null;
		if(billingReportType!="CUSTOM"){
			$.ajax({
		    	type: "GET",
		    	 url: sourceleadUrl+"/getBillingReportByFrequency?${_csrf.parameterName}=${_csrf.token}&billingReportType="+billingReportType+"&invoiceType="+invoiceType+"&startDate="+startDate+"&endDate="+endDate, 
		         success: function(data) {
		        	 /* alert(data); */
		        	 var json = JSON.parse(data);
		        	 getBillingReportsChart(invoiceType,json);
		         }
			});	
		}
		
}

</script> 

<script>
function getPayableBillingReports(invoiceType){
		var sourceleadUrl = '${sourceleadUrl}';
		var billingReportType = $("#billingReportType").val();
		var startDate = null;
		var endDate = null;
		$.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getBillingReportByFrequency?${_csrf.parameterName}=${_csrf.token}&billingReportType="+billingReportType+"&invoiceType="+invoiceType+"&startDate="+startDate+"&endDate="+endDate, 
	         success: function(data) {
	        	 var json = JSON.parse(data);
	        	 var result = "";
	        	 result = result +'<div class="project_tabs_inn_head">';
	        	 result = result +'</div>';
	        	 result = result +'<div class="require_wrap_table dataFilterNew">';
	        	 result = result +"<table id='billingPayableReportPagination' class='display' cellspacing='0' width='99.9%' >"; 
	        	 result = result +'<thead>';
	        	 result = result +'<tr>';
	        	 result = result +'<th>Bill Id</th>';
	        	 result = result +'<th>Vendor</th>';
	        	 result = result +'<th>Bill Date</th>';
	        	 result = result +'<th>Total</th>';
	        	 result = result +'<th>Due Date</th>';                    
	        	 result = result +'<th>Due Amount</th>';
	        	 result = result +'</tr>';
	        	 result = result +'</thead>';	
	        	 result = result +'<tbody>';
	        	 for (var i = 0; i < json.length; i++) { 
	        		 var opt = json[i]; 
	        		 $("#netIncome").html(opt.netIncome);
		        	 $("#receivedAmount").html(opt.recievableAmount);
		        	 $("#payableAmount").html(opt.payableAmount); 
	        		 result = result +'<tr>';
		        	 result = result +'<td><div>'+opt.invoiceId+'</div></td>';
		        	 result = result +'<td><div>'+opt.orgName+'</div></td>';
		        	 result = result +'<td><div>'+opt.invoiceDateStr+'</div></td>';
		        	 result = result +'<td><div>'+opt.total+'</div></td> ';
		        	 result = result +'<td><div>'+opt.dueDateStr+'</div></td> ';
		        	 result = result +'<td><div>'+opt.dueAmount+'</div></td> ';
		        	 result = result +'</tr>'; 
	        	 }
	        	 
	                result = result +'</tbody>';
	                result = result +'</table>';
	                result = result +'</div>';
	                $("#tab-2").empty();
	        	   $("#tab-2").append(result);
	        	   billingPayableReportPagination();
	         }
		});
}

</script>


<script>
function billingReportPagination(){ 
    $('#billingReportPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
}
</script> 

<script>
function billingPayableReportPagination(){ 
    $('#billingPayableReportPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
}
</script> 

<script>
function getCustomBillingReports(invoiceType){
	var billingReportType = $("#billingReportType").val();  
	var startDate = $("#billingReportStartDate").val();
	var endDate = $("#billingReportEndDate").val();
	var sourceleadUrl ='${sourceleadUrl}'; 
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#billingErrorReportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#billingErrorReportEndDate").empty().append("End Date is Required") ; 
	}else{
		$.ajax({
	         type : "GET",
	         url: sourceleadUrl+"/getBillingReportByFrequency?${_csrf.parameterName}=${_csrf.token}&billingReportType="+billingReportType+"&invoiceType="+invoiceType+"&startDate="+startDate+"&endDate="+endDate,     
	         success :function(data) {
	        	 var json = JSON.parse(data);
	        	 var result = "";
	        	 result = result +'<div class="project_tabs_inn_head">';
	        	 result = result +'</div>';
	        	 result = result +'<div class="require_wrap_table dataFilterNew">';
	        	 result = result +"<table id='customBillingReportPagination' class='display' cellspacing='0' width='99.9%' >"; 
	        	 result = result +'<thead>';
	        	 result = result +'<tr>';
	        	 result = result +'<th>Invoice Id</th>';
	        	 result = result +'<th>Client</th>';
	        	 result = result +'<th>Invoice Date</th>';
	        	 result = result +'<th>Toatl</th>';
	        	 result = result +'<th>Due Date</th>';                    
	        	 result = result +'<th>Due Amount</th>';
	        	 result = result +'</tr>';
	        	 result = result +'</thead>';	
	        	 result = result +'<tbody>';
	        	 for (var i = 0; i < json.length; i++) { 
	        		 var opt = json[i]; 
	        		 $("#netIncome").html(opt.netIncome);
		        	 $("#receivedAmount").html(opt.recievableAmount);
		        	 $("#payableAmount").html(opt.payableAmount); 
	        		 result = result +'<tr>';
		        	 result = result +'<td><div>'+opt.invoiceId+'</div></td>';
		        	 result = result +'<td><div>'+opt.orgName+'</div></td>';
		        	 result = result +'<td><div>'+opt.invoiceDateStr+'</div></td>';
		        	 result = result +'<td><div>'+opt.total+'</div></td> ';
		        	 result = result +'<td><div>'+opt.dueDateStr+'</div></td> ';
		        	 result = result +'<td><div>'+opt.dueAmount+'</div></td> ';
		        	 result = result +'</tr>'; 
	        	 }
	        	 
	                result = result +'</tbody>';
	                result = result +'</table>';
	                result = result +'</div>';
	                $("#tab-1").empty();
	        	   $("#tab-1").append(result);
	        	   customBillingReportPagination();
	        	   getCustomPayableBillingReports('payables');
	        },
	          
	     });  
	}
}
</script>

<script>
function getCustomPayableBillingReports(invoiceType){
	var billingReportType = $("#billingReportType").val();  
	var startDate = $("#billingReportStartDate").val();
	var endDate = $("#billingReportEndDate").val();
	var sourceleadUrl ='${sourceleadUrl}'; 
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#billingErrorReportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#billingErrorReportEndDate").empty().append("End Date is Required") ; 
	}else{
		$.ajax({
	         type : "GET",
	         url: sourceleadUrl+"/getBillingReportByFrequency?${_csrf.parameterName}=${_csrf.token}&billingReportType="+billingReportType+"&invoiceType="+invoiceType+"&startDate="+startDate+"&endDate="+endDate,     
	         success :function(data) {
	        	 var json = JSON.parse(data);
	        	 var result = "";
	        	 result = result +'<div class="project_tabs_inn_head">';
	        	 result = result +'</div>';
	        	 result = result +'<div class="require_wrap_table dataFilterNew">';
	        	 result = result +"<table id='customPayableReportPagination' class='display' cellspacing='0' width='99.9%' >"; 
	        	 result = result +'<thead>';
	        	 result = result +'<tr>';
	        	 result = result +'<th>Invoice Id</th>';
	        	 result = result +'<th>Client</th>';
	        	 result = result +'<th>Invoice Date</th>';
	        	 result = result +'<th>Toatl</th>';
	        	 result = result +'<th>Due Date</th>';                    
	        	 result = result +'<th>Due Amount</th>';
	        	 result = result +'</tr>';
	        	 result = result +'</thead>';	
	        	 result = result +'<tbody>';
	        	 for (var i = 0; i < json.length; i++) { 
	        		 var opt = json[i]; 
	        		 $("#netIncome").html(opt.netIncome);
		        	 $("#receivedAmount").html(opt.recievableAmount);
		        	 $("#payableAmount").html(opt.payableAmount); 
	        		 result = result +'<tr>';
		        	 result = result +'<td><div>'+opt.invoiceId+'</div></td>';
		        	 result = result +'<td><div>'+opt.orgName+'</div></td>';
		        	 result = result +'<td><div>'+opt.invoiceDateStr+'</div></td>';
		        	 result = result +'<td><div>'+opt.total+'</div></td> ';
		        	 result = result +'<td><div>'+opt.dueDateStr+'</div></td> ';
		        	 result = result +'<td><div>'+opt.dueAmount+'</div></td> ';
		        	 result = result +'</tr>'; 
	        	 }
	        	 
	                result = result +'</tbody>';
	                result = result +'</table>';
	                result = result +'</div>';
	                $("#tab-1").empty();
	        	   $("#tab-1").append(result);
	        	   customPayableReportPagination();
	        },
	          
	     });  
	}
}
</script>
<script>
function customBillingReportPagination(){ 
    $('#customBillingReportPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
}
</script> 

<script>
function customPayableReportPagination(){ 
    $('#customPayableReportPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
}
</script> 

<script>
function custDates(){
	var reportType = $("#reportTypeId").val();
	if(reportType=="custom"){
	    $(".cusShow").show();
	}
	if(reportType!="custom"){
	    $(".cusShow").hide();
	}
}
</script>


<!-- Month Graph -->
<!-- Chart code -->
<script>
var chart = AmCharts.makeChart( "billing_chartdiv", {
  "type": "serial",
  "theme": "light",
  "marginRight": 40,
  "marginLeft": 40,
  "autoMarginOffset": 20,
  "dataDateFormat": "YYYY-MM-DD",
  "valueAxes": [ {
    "id": "v1",
    "axisAlpha": 0,
    "position": "left",
    "ignoreAxisWidth": true
  } ],
  "balloon": {
    "borderThickness": 1,
    "shadowAlpha": 0
  },
  "graphs": [ {
    "id": "g1",
    "balloon": {
      "drop": true,
      "adjustBorderColor": false,
      "color": "#ffffff",
      "type": "smoothedLine"
    },
    "fillAlphas": 0.2,
    "bullet": "round",
    "bulletBorderAlpha": 1,
    "bulletColor": "#FFFFFF",
    "bulletSize": 5,
    "hideBulletsCount": 50,
    "lineThickness": 2,
    "title": "red line",
    "useLineColorForBulletBorder": true,
    "valueField": "value",
    "balloonText": "<span style='font-size:18px;'>[[value]]</span>"
  } ],
  "chartCursor": {
    "valueLineEnabled": true,
    "valueLineBalloonEnabled": true,
    "cursorAlpha": 0,
    "zoomable": false,
    "valueZoomable": true,
    "valueLineAlpha": 0.5
  },
  "valueScrollbar": {
    "autoGridCount": true,
    "color": "#000000",
    "scrollbarHeight": 50
  },
  "categoryField": "date",
  "categoryAxis": {
    "parseDates": true,
    "dashLength": 1,
    "minorGridEnabled": true
  },
  "export": {
    "enabled": true
  },
  "dataProvider": [ {
    "date": "2012-01-27",
    "value": 13
  }, {
    "date": "2012-08-01",
    "value": 13
  },  {
    "date": "2012-09-04",
    "value": 47
  }, {
    "date": "2012-10-07",
    "value": 21
  }, {
    "date": "2012-11-03",
    "value": 68
  }, {
    "date": "2012-11-04",
    "value": 65
  }, {
    "date": "2012-11-05",
    "value": 71
  }, {
    "date": "2012-11-06",
    "value": 75
  }, {
    "date": "2012-11-07",
    "value": 74
  },{
    "date": "2012-12-03",
    "value": 65
  }, {
    "date": "2013-01-02",
    "value": 67
  }, ]
} );
</script>

<jsp:include page="vendorsReport.jsp" />

<jsp:include page="../masterFooter.jsp" />