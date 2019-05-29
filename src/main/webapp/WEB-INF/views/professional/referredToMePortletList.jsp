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
	 if(requirementStatus == "referToMe"){
		  applicantStatus = "Referred To Me";
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
      <h2>Referred To Me</h2>
    </div>
    <div class="pull-right brud-crum"><a href="${appPath}/professionalDashboard">Home</a> » <span class="applicantStatus"></span>&nbsp; List </div>
  </section>
  <section class="clearfix">
  <div class="timesheet_out timesheet_background crowdDash_btn">
	<div class="timesheet_head pull-left zIndex col-md-8 paddingBtm">
  	      <h4 class="pull-left"> <span class="applicantStatus"></span> List</h4>
  	      <div id ="statusDropDown" class="pull-right mt10"></div>
  	</div>
  	<div id="professionalDashboardPortletList"></div>
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
	 if(requirementStatus == "referToMe"){
		  applicantStatus = "Referred To Me";
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
			url: atsUrl+"/getJobSummaryPortlets?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
		    success: function(data) {
	    	 var json = JSON.parse(data);
	    	 var result ='';
	    	 result =result +'<span class="select-field"><i class="fa fa-filter"></i>';
	    	 result =result +'<select name = "navyOp" id="navyOp" onChange="myFunction();">'; 
	    	 result =result +'<option '+ (requirementStatus == "" ? "selected" : 'referToMe') +' value = "referToMe">Refer To Me ('+json.referToMeJobs+')</span></option>';
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
	  $.ajax({ 
		   	type: "GET",
		   	url: atsUrl+"/getJobSummaryPortletsList?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate+"&requirementStatus="+requirementStatus,
		    success: function(data) {
	    	 var json = JSON.parse(data);
	    	 var result =""; 
	    	   result = result + '<div class="require_wrap_table overflow-data filter-position repoPositi repo_positi">';
	    	   result = result + '<div class="clerfix">';
	    	   result = result + '<table id="professionalDashboardPortletPagenation" class="display" cellspacing="0" width="100%"">';
	           result = result + '<thead>';
	           result = result + '<tr>';
	           result = result + '<th>Req. Name</th>';
	           result = result + '<th>Company</th>';
	           result = result + '<th>Emp. Type</th>';
	           result = result + '<th>Location</th>';
	           result = result + '<th>Rate</th>';
	           result = result + '<th>Req. Status</th>';
		       result = result + '<th>Stage</th>';
		       result = result + '<th>Referred By</th>';
		      /*  result = result + '<th>Referred On</th>'; */
	           result = result + '</tr>';
	           result = result + '</thead>';
	           result = result + '<tbody>';
	           if(requirementStatus  != "referToMe"){
	        	   for (var i=0; i<json.crowdSourcingPortLetList.length; i++)
	  				{
		        	   var report = json.crowdSourcingPortLetList[i];
		        	   
		        	   if(report.requirementJobId != null && report.requirementJobId != '' && report.requirementJobId != 'undefined'){
		        		   requirementJobId = report.requirementJobId +' - ';
		        	   }else{
		        		   requirementJobId = "";
		        	   }
		        	   if(report.employeeType != null && report.employeeType != '' && report.employeeType != 'undefined'){
		        		   employeeType = report.employeeType;
		        	   }else{
		        		   employeeType = "--"
		        	   }
		        	   
		        	   if(report.state != null && report.state != '' && report.state != 'undefined'){
		        		   state = ' , '+report.state;
		        	   }else{
		        		   state = "";
		        	   }
		        	   
		        	   if(report.city != null && report.city != '' && report.city != 'undefined'){
		        		   city = ' , '+report.city;
		        	   }else{
		        		   city = "";
		        	   }
		        	   if(report.country != null && report.country != '' && report.country != 'undefined'){
		        		   country = ' , '+report.country;
		        	   }else{
		        		   country = "";
		        	   }
		        	   
		        	   if(report.rate != null && report.rate != '' && report.rate != 'undefined'){
		        		   rate = report.rate;
		        	   }else{
		        		   rate = "--";
		        	   }
		        	   if(report.rateType != null && report.rateType != '' && report.rateType != 'undefined'){
		        		   rateType =report.rateType;
		        	   }else{
		        		   rateType = "";
		        	   }
		        	   	        	
		        	   if(report.referredBy != null && report.referredBy != '' && report.referredBy != 'undefined'){
		        		   referredBy = report.referredBy;
		        	   }else{
		        		   referredBy= "--";
		        	   }
		        	  /*  if(report.referredOn != null && report.referredOn != '' && report.referredOn != 'undefined'){
		        		   referredOn = report.referredOn;
		        	   }else{
		        		   referredOn= "--";
		        	   } */
		        	   result = result +'         <tr>';
		        	   result = result +'         <td><a href="<c:url value="/app/requirementProfile/'+report.requirementUUID+'/'+report.referUUID+'"/>"><div class="tbldata-dotdot-sm" title="'+requirementJobId+' '+report.requirementName+'">'+requirementJobId+' '+report.requirementName+'</div></a></td>';
		        	   result = result +'         <td><div class="tbldata-dotdot" title="'+report.orgName+'">'+report.orgName+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+employeeType+'</div></td>';
		        	   
// 		        	   result = result +'         <td><div class="tbldata-dotdot-100">'+country+' '+state+' '+city+'</div></td>';
		        	   
		        	   
		        	   result = result +'<td>';
	   		        	result = result +'<div class="positionRelative">';
		   		         for(var k=0; k<1; k++){
		   		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+report.locationsList[k]+'</p>';
		   		         }
	   		    	
		   		        result = result +'<div class="show_Empdetails showjoblist">';
	   		        	result = result +'<i class="fa fa-ellipsis-v"></i>';

	   		        	result = result +'<div class="emailvalues emailvaluesNew">';
	   		        	
						for(var k=0; k<report.locationsList.length; k++){
							result = result +'<p><i class="fa fa-map-marker"></i>'+report.locationsList[k]+'</p>';
		   		        	
	   		    	 	}
	   		        	result = result +'</div>';
	   		        	result = result +'</div>';
	   

  		        	result = result +'</div>';
  		        	result = result +'</td>';
  		        	
		        	   result = result +'         <td><div class="tblminwidth-dotdot">'+rate+' '+rateType+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+report.requirementSubmissionStatus+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+report.submissionStatus+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-dotdot">'+referredBy+'</div></td>';
		        	  /*  result = result +'         <td><div class="tblminwidth-50">'+referredOn+'</div></td>'; */
		        	   result = result +'         </tr>';
		        	   
	  				}
	           }else{
	        	  
	        	    for (var i=0; i<json.referToMeList.length; i++)
	  				{ 
		        	   var report = json.referToMeList[i];
		        	   
		        	   if(report.requirementJobId != null && report.requirementJobId != '' && report.requirementJobId != 'undefined'){
		        		   requirementJobId = report.requirementJobId +' - ';
		        	   }else{
		        		   requirementJobId = "";
		        	   }
		        	   if(report.employeeType != null && report.employeeType != '' && report.employeeType != 'undefined'){
		        		   employeeType = report.employeeType;
		        	   }else{
		        		   employeeType = "--"
		        	   }
		        	   
		        	   if(report.requirementState != null && report.requirementState != '' && report.requirementState != 'undefined'){
		        		   state = ' , '+report.requirementState;
		        	   }else{
		        		   state = "";
		        	   }
		        	   
		        	   if(report.requirementCity != null && report.requirementCity != '' && report.requirementCity != 'undefined'){
		        		   city = ' , '+report.requirementCity;
		        	   }else{
		        		   city = "";
		        	   }
		        	   if(report.requirementJobType != null && report.requirementJobType != '' && report.requirementJobType != 'undefined'){
		        		   requirementJobType = report.requirementJobType;
		        	   }else{
		        		   requirementJobType = "--"
		        	   }

		        	   if(report.requirementRate != null && report.requirementRate != '' && report.requirementRate != 'undefined'){
		        		   rate = report.requirementRate;
		        	   }else{
		        		   rate = "--";
		        	   }
		        	   if(report.requirementRateType != null && report.requirementRateType != '' && report.requirementRateType != 'undefined'){
		        		   rateType =report.requirementRateType;
		        	   }else{
		        		   rateType = "";
		        	   }
		        	   	        	
		        	   if(report.referredBy != null && report.referredBy != '' && report.referredBy != 'undefined'){
		        		   referredBy = report.referredBy;
		        	   }else{
		        		   referredBy= "--";
		        	   }
		        	  if(report.submissionStatus != null && report.submissionStatus != '' && report.submissionStatus != 'undefined'){
		        		  submissionStatus = report.submissionStatus;
		        	  }else{
		        		  submissionStatus = "NEW"
		        	  }
		        	   result = result +'         <tr>';
		        	   result = result +'         <td><a href="<c:url value="/app/requirementProfile/'+report.requirementUUID+'/'+report.referUUID+'"/>"><div class="tbldata-dotdot-sm" title="'+requirementJobId+' '+report.requirementName+'">'+requirementJobId+' '+report.requirementName+'</div></a></td>';
		        	   result = result +'         <td><div class="tbldata-dotdot" title="'+report.requirementOrgName+'">'+report.requirementOrgName+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+requirementJobType+'</div></td>';
		        	   result = result +'         <td><div class="tbldata-dotdot-100">'+report.requirementCountry+' '+state+' '+city+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-dotdot">'+rate+' '+rateType+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+report.requirementSubmissionStatus+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-50">'+submissionStatus+'</div></td>';
		        	   result = result +'         <td><div class="tblminwidth-dotdot">'+referredBy+'</div></td>';
		        	   result = result +'         </tr>';
		        	   
	  				 } 
	           }
	          
	    	 
	           result = result + '       </tbody>';
	           result = result + '     </table>';
	           result = result + '   </div>';
	           $("#professionalDashboardPortletList").empty();
	           $("#professionalDashboardPortletList").append(result);
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
    	                title: 'Referred To Me List',
    	            },
    	            {
    	                extend: 'pdf',
    	                title: 'Referred To Me List',
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