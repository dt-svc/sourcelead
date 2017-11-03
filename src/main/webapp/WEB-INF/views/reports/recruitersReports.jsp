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

<style>
#billing_chartdiv {
  	width:100%; height:300px;
}
.reportBtns {
    width: 65%;
}
.reportBtn {
    width: 20%;
}																		
</style>
<script type="text/javascript">
	window.onload = function () {
		usersList();
	 };
</script>

 <script>
function getDayWiseReports(){
	var timePeriod = $('#reportTypeId').val(); 
	if(timePeriod != "custom"){
		usersList(timePeriod);
	}
}

</script>


<section class="content-wraper">

<section class="content-header clearfix">
			<div class="pull-left">
				<h2>Recruiters Report </h2>
			</div>
			<div class="pull-right brud-crum"><a href="/sourcelead/dashboard/3">Home</a>»Client List</div>
</section>


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
 </section>
 
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
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatRepo = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatRepo = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatRepo = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatRepo = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatRepo = 'yy-dd-mm'; 
		  }
		$('#ReportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			onSelect : function(selected) {
				/* var dt = new Date(selected);
				//var dt = toDate("#ReportStartDate");
				dt.setDate(dt.getDate() + 1);
				$("#ReportEndDate").datepicker("option", "minDate", dt); */
				$("#ReportEndDate").datepicker("option", "minDate", selected);
				addValidClass();
			}
		
		});
		
		$('#ReportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				/* //var dt = toDate("#ReportEndDate");
				dt.setDate(dt.getDate() - 1);
				$("#ReportStartDate").datepicker("option", "maxDate", dt);
				 $( "#ReportStartDate" ).datepicker({  maxDate: new Date() }); */
				 $("#ReportStartDate").datepicker("option", "maxDate", selected);
				addValidClass();
			}
		});
		
	});
</script>	
 <script>
function getCustomReports(){
	var timePeriod = $("#reportTypeId").val();
	var startDate = $("#ReportStartDate").val();
	var endDate = $("#ReportEndDate").val();
	
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#reportsStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#reportsEndDate").empty().append("End Date is Required") ; 
	}else{
		$.ajax({
	         type : "POST",
	         url: sourceleadUrl+"/getAllUsersByOrgLocationsId?"+tokenParamName+"="+tokenValue+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate,     
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

<script type="text/javascript">
	function flushCustom() {
		$('#ReportStartDate').val('');
		$('#ReportEndDate').val('');
		$.datepicker._clearDate('#ReportStartDate');
		$.datepicker._clearDate('#ReportEndDate'); 
	}
</script>
<script>
function usersList(){ 
	var timePeriod = $('#reportTypeId').val(); 
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAllUsersByOrgLocationsId?"+tokenParamName+"="+tokenValue+"&timePeriod="+timePeriod,    
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


<jsp:include page="../masterFooter.jsp" />