<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
$(document).ready(function() {
	 var startDate = '${startDate}'; 
	 var endDate = '${endDate}'; 
	 var requirementStatus = '${requirementStatus}'; 
	 professionalDashboardPortletListCounts(startDate,endDate,requirementStatus);
	 professionalDashboardPortletList(startDate,endDate,requirementStatus);
	 
	 var applicantStatus = "";
	 if(requirementStatus == "SHORTLIST"){
		 applicantStatus ="Shortlisted";
	 }
	 if(requirementStatus == "OFFERMADE"){
		  applicantStatus = "Offered";
	 }
	 if(requirementStatus == "INTERVIEW"){
		  applicantStatus = "Interviews";
	 }
	 if(requirementStatus == "REJECTED"){
		  applicantStatus = "Rejected";
	 }
	 if(requirementStatus == "referByMe"){
		  applicantStatus = "Referred By Me";
	 }
	 if(requirementStatus == "" || requirementStatus =='undefined'  || requirementStatus == null){
		  applicantStatus = "Applied";
	 }
	 $(".applicantStatus").append(applicantStatus); 
	 
});	
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Referred By Me</h2>
    </div>
     <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/professionalDashboard'/>">Home</a> » <span class="applicantStatus"></span>&nbsp; List </div>
     </c:if>
     
     <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/recruiterDashboard'/>">Home</a> » <span class="applicantStatus"></span>&nbsp; List </div>
     </c:if>
    
  </section>
  <section class="clearfix">
  <div class="timesheet_out timesheet_background crowdDash_btn">
	<div class="timesheet_head pull-left zIndex col-md-8 paddingBtm">
  	      <h4 class="pull-left"> <span class="applicantStatus"></span> List</h4>
  	      <div id ="statusDropDown" class="pull-right mt10"></div>
  	</div>
  	<div id="referredByMePortletList"></div>
  </div>
  </section>
<script>
function myFunction(){
	var startDate = '${startDate}'; 
	 var endDate = '${endDate}'; 
	 var requirementStatus = $("#navyOp").val();
	 $(".applicantStatus").empty('');
	 var applicantStatus = "";
	 if(requirementStatus == "SHORTLIST"){
		 applicantStatus ="Shortlisted";
	 }
	 if(requirementStatus == "OFFERMADE"){
		  applicantStatus = "Offered";
	 }
	 if(requirementStatus == "INTERVIEW"){
		  applicantStatus = "Interviews";
	 }
	 if(requirementStatus == "REJECTED"){
		  applicantStatus = "Rejected";
	 }
	 if(requirementStatus == "referByMe"){
		  applicantStatus = "Referred By Me";
	 }
	 if(requirementStatus == "" || requirementStatus =='undefined'  || requirementStatus == null){
		  applicantStatus = "Applied";
	 }
	 $(".applicantStatus").append(applicantStatus); 
	 
	
    professionalDashboardPortletList(startDate,endDate,requirementStatus);
}

</script>  
<script>
function portletListFilter(requirementStatus){
	 var startDate = '${startDate}'; 
	 var endDate = '${endDate}'; 
	professionalDashboardPortletList(startDate,endDate,requirementStatus);
  
}

</script>	
   
   
   
   
<script type="text/javascript">
  function professionalDashboardPortletListCounts(startDate,endDate,requirementStatus){
	  $.ajax({ 
		   	type: "GET",
			url: sourceleadUrl+"/getReferByMeJobSummaryPortlets?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
		    success: function(data) {
	    	 var json = JSON.parse(data);
	    	 var result ='';
	    	 result =result +'<span class="select-field"><i class="fa fa-filter"></i>';
	    	 result =result +'<select name = "navyOp" id="navyOp" onChange="myFunction();">'; 
	    	 result =result +'<option '+ (requirementStatus == "" ? "selected" : 'referByMe') +' value = "referByMe">Referred By Me ('+json.referByMeJobs+')</span></option>';
	    	 result =result +'<option '+ (requirementStatus == "" ? "selected" : '') +' value = "">Applied ('+json.appliedJobs+')</span></option>';
	    	 result =result +'<option '+ (requirementStatus == "SHORTLIST" ? "selected" : '') +' value = "SHORTLIST">Shortlisted ('+json.shortlistJobs+')</option>';
	    	 result =result +'<option '+ (requirementStatus == "INTERVIEW" ? "selected" : '') +' value = "INTERVIEW">Interviews ('+json.interviews+') </option>';
	    	 result =result +'<option '+ (requirementStatus == "OFFERMADE" ? "selected" : '') +' value = "OFFERMADE">Offered ('+json.offeredJobs+') </option>';
	    	 result =result +'<option '+ (requirementStatus == "REJECTED" ? "selected" : '') +' value = "REJECTED">Rejected ('+json.rejectedJobs+') </option>';
	    	 result =result +'</select>';
	    	 result =result +'</span>';
	    	   $("#statusDropDown").empty();
	           $("#statusDropDown").append(result);
		    }
	  });
	 
  }
  </script>
   
   
   
            
  <script type="text/javascript">
  function professionalDashboardPortletList(startDate,endDate,requirementStatus){
	  var locationCode ='${SourceLeadWeb_locationCode}'; 
	  $.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getReferByMeJobSummaryPortletList?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate+"&requirementStatus="+requirementStatus+"&locationCode="+locationCode,
		    success: function(data) {
	    	 var json = JSON.parse(data);
	    	 var result =""; 
	    	   result = result + '<div class="require_wrap_table overflow-data filter-position repoPositi repo_positi">';
	    	   result = result + '<div class="clerfix">';
	    	   result = result + '<table id="professionalDashboardPortletPagenation" class="display" cellspacing="0" width="100%"">';
	           result = result + '<thead>';
	           result = result + '<tr>';
	           result = result + '<th>Name</th>';
	           result = result + '<th>Email</th>';
	           result = result + '<th>Phone</th>';
	           result = result + '<th>Job</th>';
	           result = result + '<th>Status</th>';
	           if(requirementStatus  != "referByMe"){ 
	        	   result = result + '<th>Stage</th>';
	           }
		       result = result + '<th>Referred on</th>';
	           result = result + '</tr>';
	           result = result + '</thead>';
	           result = result + '<tbody>';
	           if(requirementStatus  != "referByMe"){ 
	        	   for (var i=0; i<json.referredByMeList.length; i++)
	  				{
		        	   var report = json.referredByMeList[i];
		        	   
		        	   if(report.requirementJobId != null && report.requirementJobId != '' && report.requirementJobId != 'undefined'){
		        		   requirementJobId = report.requirementJobId +' - ';
		        	   }else{
		        		   requirementJobId = "";
		        	   }
		        	   if(report.requirementSubmissionStatus != null && report.requirementSubmissionStatus != '' && report.requirementSubmissionStatus != 'undefined'){
		        		   requirementSubmissionStatus = report.requirementSubmissionStatus;
		        	   }else{
		        		   requirementSubmissionStatus = "--"
		        	   }
		        	 
		        	   if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
		        		   candidateEmail = report.candidateEmail;
		        	   }else{
		        		   candidateEmail= "--";
		        	   }
		        	   
		        	   if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
		        		   phoneNumber = report.phoneNumber;
		        	   }else{
		        		   phoneNumber= "--";
		        	   }
		        	  
		        	   if(report.referredOn != null && report.referredOn != '' && report.referredOn != 'undefined'){
		        		   referredOn = report.referredOn;
		        	   }else{
		        		   referredOn= "--";
		        	   }
		        	   
		        	   result = result +'         <tr>';
		        	   result = result +'         <td><div class="tbldata-dotdot" title="'+report.candidateName+'">'+report.candidateName+'</div></td>';
		        	   result = result +'         <td><div class="tbldata-dotdot" title="'+candidateEmail+'">'+candidateEmail+'</div></td>';
		        	   result = result +'         <td><div class="tbldata-dotdot" title="'+phoneNumber+'">'+phoneNumber+'</div></td>';
		        	   result = result +'         <td><a href="<c:url value="/app/requirementProfile/'+report.requirementUUID+'"/>"><div class="tbldata-dotdot-sm" title="'+requirementJobId+' '+report.requirementName+'">'+requirementJobId+' '+report.requirementName+'</div></a></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+requirementSubmissionStatus+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+report.submissionStatus+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-100">'+referredOn+'</div></td>';
		        	   result = result +'         </tr>';
		        	   
	  				}
	            }else{
	        	    for (var i=0; i<json.referByMeJobsList.length; i++)
	  				{ 
		        	   var report = json.referByMeJobsList[i];
		        	   
		        	   if(report.requirementJobId != null && report.requirementJobId != '' && report.requirementJobId != 'undefined'){
		        		   requirementJobId = report.requirementJobId +' - ';
		        	   }else{
		        		   requirementJobId = "";
		        	   }
		        	   
		        	   if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
		        		   candidateEmail = report.candidateEmail;
		        	   }else{
		        		   candidateEmail= "--";
		        	   }
		        	   
		        	   if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
		        		   phoneNumber = report.phoneNumber;
		        	   }else{
		        		   phoneNumber= "--";
		        	   }
		        	  
		        	   if(report.referredOn != null && report.referredOn != '' && report.referredOn != 'undefined'){
		        		   referredOn = report.referredOn;
		        	   }else{
		        		   referredOn= "--";
		        	   }
		        	   result = result +'         <tr>';
		        	   result = result +'         <td><div class="tbldata-dotdot" title="'+report.candidateName+'">'+report.candidateName+'</div></td>';
		        	   result = result +'         <td><div class="tbldata-dotdot" title="'+candidateEmail+'">'+candidateEmail+'</div></td>';
		        	   result = result +'         <td><div class="tbldata-50" title="'+phoneNumber+'">'+phoneNumber+'</div></td>';
		        	   result = result +'         <td><a href="<c:url value="/app/requirementProfile/'+report.requirementUUID+'"/>"><div class="tbldata-dotdot-sm" title="'+requirementJobId+' '+report.requirementName+'">'+requirementJobId+' '+report.requirementName+'</div></a></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+report.requirementState+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-100">'+referredOn+'</div></td>';
		        	   result = result +'         </tr>';
		        	   
	  				 } 
	           } 
	          
	    	 
	           result = result + '       </tbody>';
	           result = result + '     </table>';
	           result = result + '   </div>';
	           $("#referredByMePortletList").empty();
	           $("#referredByMePortletList").append(result);
	    	 rejectedPagenatio();
		    }
	  });
	 
  }
  </script>

  </section>

<script>
function rejectedPagenatio(){  
    $('#professionalDashboardPortletPagenation').DataTable( { 
    	dom: 'Bfrtip',
    	buttons: [
  	        	{
  	                extend: 'excel',
  	                title: 'Referred By Me List',
  	            },
  	            {
  	                extend: 'pdf',
  	                title: 'Referred By Me List',
  	            }
  	        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
	$(".require_wrap_table").find('a.buttons-excel').prop("title", "Excel");
	$(".require_wrap_table").find('a.buttons-pdf').prop("title", "PDF");
}
</script>
<script>
//Dropdown Pop
$(function(){
	$('#referred_pop').click(function(){
		$("#exportPop").toggle();
	});
});
$('html').click(function(){
	$(".referred_pop").hide();
});
$('#referred_pop, #exportPop, .referred_pop').click(function(e){
	e.stopPropagation();
});
</script>

<jsp:include page="../masterFooter.jsp" />