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
					<li><a href="<c:url value='/requirementListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Requirements Posted</a></li>
					<li><a href="<c:url value='/requirementStagesListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Requirements Stages</a></li>
<%-- 					<li><a href="<c:url value='/applicantsReceivedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Received</a></li> --%>
				    <li><a href="<c:url value='/applicantsListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants</a></li>
				    <li><a href="<c:url value='/applicantsSubmittedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Submitted</a></li>
<%-- 				    <li><a href="<c:url value='/requirementAssignToReports'/>"><span><i class="fa fa-briefcase"></i></span>Requirement Assign To</a></li> --%>
				    <li><a href="<c:url value='/candidateListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Added Candidates</a></li>
				    <li><a href="<c:url value='/timeSheetReports'/>"><span><i class="fa fa-briefcase"></i></span>TimeSheet Reports</a></li>
				</ul>
			</div>
		</div> 
		
   </c:if>		 
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo;   Reports Dashboard</div>
 <div class="clearfix"></div>
 
 
 
 
  </section>
   
 
 <c:if test="${requirementTabAccess == true }">
  <div style="overflow: auto;" class=" vendor_wrap"> 
   
   <div class="pull-left reportBtns mt10">
<h4 class="pull-left">Recruiters Report</h4>
		  <div class="reportBtn select-field  pull-left ml">
			<select class="form-control" id="reportTypeId" onChange="flushCustom(); getDayWiseReports(); custdates();">
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
        <div id="usersList"></div>
      </div> 
 </div>
 </c:if>
 <c:if test="${vendorTabAccess == true }">
 <div class="timesheet_out mt40">
    <div class="vendor_wrap">
      <div class="timesheet_head">
<div class="pull-left reportBtns mt10">
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

<div id="vendorReportsDiv" class=""></div>

<div id="vendorSpocReportsDiv" class="repoPositiNoCount"></div>

</section>    
 </div>
      
 </div>


  </div>
  </div>
  </c:if>
  </section>
   
<div class="mask_time"></div>

<div class="pavPop">
<span class="pavClose" onclick="pavClose();"><i class="fa fa-times"></i></span>
		<div id="vendorspc" class="clients_wrap_table"></div>
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
	                     result = result +"<th><i class='fa fa-briefcase fa-2x'></i><div class='small'>Requirements</div></th>";
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
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
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
 <script>
function getDayWiseReports(){
	var timePeriod = $('#reportTypeId').val(); 
	if(timePeriod != "custom"){
		usersList(timePeriod);
	}
}

</script>
<script>
function custdates(){
	var reportType = $("#reportTypeId").val();
	if(reportType=="custom"){
	    $(".cusShow").show();
	}
	if(reportType!="custom"){
	    $(".cusShow").hide();
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
                     result = result +"<th><i class='fa fa-briefcase fa-2x'></i><div class='small'>Requirements</div></th>";
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

<jsp:include page="vendorsReport.jsp" />

<jsp:include page="../masterFooter.jsp" />