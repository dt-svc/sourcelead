<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <script>
 var atsUrl = '${SourceLeadWeb_atsUrl}';
 </script>

<script>
$(document).ready(function() {
	 var startDate = '${startDate}'; 
	 var endDate = '${endDate}'; 
	 var requirementStatus = '${requirementStatus}'; 
	 recruiterDashboardPortletListCounts(startDate,endDate,requirementStatus);
	 recruiterDashboardPortletList(startDate,endDate,requirementStatus);
	 
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
		  applicantStatus = "Submitted";
	 }
	 $(".applicantStatus").append(applicantStatus); 
	 
});	
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Submitted By Me</h2>
    </div>
    <div class="pull-right brud-crum"><a href="${appPath}/recruiterDashboard">Home</a> » <span class="applicantStatus"></span>&nbsp; List </div>
  </section>
  <section class="clearfix">
  <div class="timesheet_out timesheet_background crowdDash_btn">
	<div class="timesheet_head pull-left zIndex col-md-8 paddingBtm">
  	      <h4 class="pull-left"> <span class="applicantStatus"></span> List</h4>
  	      <div id ="statusDropDown" class="pull-right mt10"></div>
  	</div>
  	<div id="submittedList"></div>
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
		  applicantStatus = "Submitted";
	 }
	 $(".applicantStatus").append(applicantStatus); 
	 
	
	 recruiterDashboardPortletList(startDate,endDate,requirementStatus);
}

</script>  
<script>
function portletListFilter(requirementStatus){
	 var startDate = '${startDate}'; 
	 var endDate = '${endDate}'; 
	 recruiterDashboardPortletList(startDate,endDate,requirementStatus);
  
}

</script>	
   
   
   
   
<script type="text/javascript">
  function recruiterDashboardPortletListCounts(startDate,endDate,requirementStatus){
	  $.ajax({ 
		   	type: "GET",
			url: atsUrl+"/getRecruiterSubmttedJobSummaryPortlets?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
		    success: function(data) {
	    	 var json = JSON.parse(data);
	    	 var result ='';
	    	 result =result +'<span class="select-field"><i class="fa fa-filter"></i>';
	    	 result =result +'<select name = "navyOp" id="navyOp" onChange="myFunction();">'; 
	    	 result =result +'<option '+ (requirementStatus == "" ? "selected" : '') +' value = "">Submitted ('+json.submittedByMeJobs+')</span></option>';
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
  function recruiterDashboardPortletList(startDate,endDate,requirementStatus){
	  var locationCode ='${SourceLeadWeb_locationCode}'; 
	  $.ajax({ 
		   	type: "GET",
		   	url: atsUrl+"/getRecruiterSubmttedJobSummaryPortletList?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate+"&requirementStatus="+requirementStatus+"&locationCode="+locationCode,
		    success: function(data) {
	    	 var json = JSON.parse(data);
	    	 var result =""; 
	    	   result = result + '<div class="require_wrap_table overflow-data filter-position">';
	    	   result = result + '<div class="clerfix">';
	    	   result = result + '<table id="professionalDashboardPortletPagenation" class="display" cellspacing="0" width="100%"">';
	           result = result + '<thead>';
	           result = result + '<tr>';
	           result = result + '<th>Name</th>';
	           result = result + '<th>Email</th>';
	           result = result + '<th>Phone</th>';
	           result = result + '<th>Job</th>';
	           result = result + '<th>Status</th>';
	           result = result + '<th>Stage</th>';
		       result = result + '<th>Submitted on</th>';
	           result = result + '</tr>';
	           result = result + '</thead>';
	           result = result + '<tbody>';
	           
	        	   for (var i=0; i<json.submittedList.length; i++)
	  				{
		        	   var report = json.submittedList[i];
		        	   
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
		        	  
		        	   if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
		        		   createdDateStr = report.createdDateStr;
		        	   }else{
		        		   createdDateStr= "--";
		        	   }
		        	   
		        	   result = result +'         <tr>';
		        	   result = result +'         <td><div class="tbldata-dotdot" title="'+report.candidateName+'">'+report.candidateName+'</div></td>';
		        	   result = result +'         <td><div class="tbldata-dotdot" title="'+candidateEmail+'">'+candidateEmail+'</div></td>';
		        	   result = result +'         <td><div class="tbldata-dotdot-100" title="'+phoneNumber+'">'+phoneNumber+'</div></td>';
		        	   result = result +'         <td><a href="<c:url value="/app/requirementProfile/'+report.requirementUUID+'"/>"><div class="tbldata-dotdot-sm" title="'+requirementJobId+' '+report.requirementName+'">'+requirementJobId+' '+report.requirementName+'</div></a></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+requirementSubmissionStatus+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+report.submissionStatus+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-100">'+createdDateStr+'</div></td>';
		        	   result = result +'         </tr>';
		        	   
	  				}
	    	 
	           result = result + '       </tbody>';
	           result = result + '     </table>';
	           result = result + '   </div>';
	           $("#submittedList").empty();
	           $("#submittedList").append(result);
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
  	                title: 'Submitted By Me List',
  	            },
  	            {
  	                extend: 'pdf',
  	                title: 'Submitted By Me List',
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
    } );
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