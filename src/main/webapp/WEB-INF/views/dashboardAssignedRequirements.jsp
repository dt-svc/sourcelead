<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<div class="content-dashboard clearfix" id="assignedRequirementsDiv" style="display: none;"> 
      <!-- Assigned Requirements -->
      <div class="clearfix dashboard-widget mb col-md-12" >
        <h4 class="clearfix">
        Assigned Jobs
        &nbsp;&nbsp;<!-- <span title="Calendar"><i aria-hidden="true" class="fa fa-calendar" id="datepicker"></i>
        </span> -->
        <div class="pull-right" id="viewmoresDiv" style="display: none;"> 
      <a href="/sourcelead/requirementList" class="btn btn-sm btn-primary pull-right" title="View more"> View More</a>
        </div>
        </h4>
      <!-- <a href="#" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a> -->
        <div class="clearfix require_wrap_table dashboard-data filter_position"> 
        		 <div id="requirementList"></div>
        </div>
        
      </div>
    </div>
    
    <div class="side_mask">
      <div class="proj_close"></div>
    </div>
    
    
 <%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>

<script> 
$(document).ready(function(){
	requirementList();
});
</script>

<style>
#resumesDiv select{
width: 100% !important;
}
</style>

<script>
function requirementList(){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  var orgATS = '${SourceLeadWeb_ats}';
	  var orgVMS = '${SourceLeadWeb_vms}';
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAssignedRequirementListByOrgLocationIDAndUserId?${_csrf.parameterName}=${_csrf.token}",    
            success :function(json) {
                 if(json != null && json != 'undefined' && json != "" && json != '[]'){
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<table id='requirementPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>ID</th>";
	                     result = result +"    <th>Title</th>";
	                     if(orgATS == 'Y'){
	                     result = result +"    <th>Client</th>";
	                     }
	                     result = result +"    <th>Rate</th>"; 
	                     result = result +"    <th>Priority</th>"; 
	                     result = result +"    <th>Applicants</th>"; 
	                     result = result +"    <th>Shortlisted</th>"; 
	                     result = result +"    <th>Submitted</th>"; 
	                     result = result +"    <th>Interview</th>"; 
	                     result = result +"    <th>Offered</th>"; 
	                     result = result +"<th>Actions</th>";
// 	                     result = result +"    <th>Release</th>"; 
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
	                 	 
	                 	var checkSize = json.length;
	                     if(checkSize>10){
	                         checkSize = 10;
	                     }
	                     else{
	                         checkSize = json.length;
	                     }
	                     for (var i = 0; i < checkSize; i++) { 
	                    	 var opt = json[i]; 
	                    	 
	                    	 result = result +"<tr>";
	                    	 if(opt.jobId!=null && opt.jobId !='' && opt.jobId !='undefined'){
	                    		 result = result +"<td><div class=''><span title='"+opt.jobId+"'>"+opt.jobId+"</span></a></div></td>";
	                    	 }
	                    	 else{
	                    		 result = result +"<td><div>---</div></td>";
	                    	 }
	                    	 if(opt.jobId!=null && opt.jobId !='' && opt.jobId !='undefined'){
	                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";
	                    	 }else{
		                    	 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";
		                     }
		                     if(orgATS == 'Y'){
			                     if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
			                    	 result = result +"<td><div><span title='"+opt.clientName+"'>"+opt.clientName+"</span></div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
		                     }
		                     if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined' && opt.maxRate != null && opt.maxRate != '' && opt.maxRate != 'undefined'){
			                     result = result +"<td><div><span  title='"+opt.currencyCode+"&nbsp;"+opt.rate+"-"+opt.maxRate+"&nbsp;"+opt.rateType+"'>"+opt.currencyCode+"&nbsp;"+opt.rate+"-"+opt.maxRate+"&nbsp;"+opt.rateType+"</span></div></td>";
		                     }else if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
			                     result = result +"<td><div><span  title='"+opt.currencyCode+"&nbsp;"+opt.rate+"&nbsp;"+opt.rateType+"'>"+opt.currencyCode+"&nbsp;"+opt.rate+"&nbsp;"+opt.rateType+"</span></div></td>";
	 
		                     }
		                     else{
			                     result = result +"<td><div><span><span>---</span></span></div></td>";
		                     }
		                     if(opt.priority != null && opt.priority != '' && opt.priority != 'undefined'){
		                    	 result = result +"<td><div>"+opt.priority+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>---</div></td>";
		                     }
		                     if(opt.noOfApplicants != null && opt.noOfApplicants != '' && opt.noOfApplicants != 'undefined'){		                     
	                    	 result = result +"<td><a href='<c:url value='/requirementSummary?type=applicants&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfApplicants+"</div></a></td>";
		                     }
		                     if(opt.noOfShortlistedApplicants != null && opt.noOfShortlistedApplicants != '' && opt.noOfShortlistedApplicants != 'undefined'){
		                     result = result +"<td><a href='<c:url value='/requirementSummary?type=SHORTLIST&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfShortlistedApplicants+"</div></a></td>";
		                     }
		                     if(opt.noOfSubmittedApplicants != null && opt.noOfSubmittedApplicants != '' && opt.noOfSubmittedApplicants != 'undefined'){
			                 result = result +"<td><a href='<c:url value='/requirementSummary?type=SUBMIT_TO_CLIENT&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfSubmittedApplicants+"</div></a></td>";
		                     }
		                     if(opt.noOfInterviewedApplicants != null && opt.noOfInterviewedApplicants != '' && opt.noOfInterviewedApplicants != 'undefined'){
		                     result = result +"<td><a href='<c:url value='/requirementSummary?type=INTERVIEW&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfInterviewedApplicants+"</div></a></td>";
		                     }
		                     if(opt.noOfOfferedApplicants != null && opt.noOfOfferedApplicants != '' && opt.noOfOfferedApplicants != 'undefined'){
		                     result = result +"<td><a href='<c:url value='/requirementSummary?type=OFFERMADE&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfOfferedApplicants+"</div></a></td>";
		                     }

		                     result = result +"<td>";
		                     result = result + '<div class="tblminwidth-75">';
		         	        '<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
			         	       if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
			         	    	  result = result +'<div class="text-center pull-left ml mr"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant('+opt.id+',\''+opt.reqCoolingOffPeriod+'\');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>';
			         	       }else{
			         	    	  result = result +"<div class='text-center'>---</div>";
			         	       }
		         	        '</sec:authorize>';
		         	       '<sec:authorize access="hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
		                     if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
		                    	 if(opt.isRequirementReleasedByuser == 'Y'){
		                       		 result = result +"<div class='pull-left'><a href='<c:url value='/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>'><i class='fa fa-bullhorn success' title='Release Job'></i></a></div>"; 
		                       	 }else{	
		                       	 result = result +"<div class='pull-left'><a href='<c:url value='/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>'><i class='fa fa-bullhorn' title='Release Job'></i></a></div>";
		                       	 }
		                     }else{
			                     result = result +"<div>---</div>";
		                     } 
		                     '</sec:authorize>';
		                     result = result + '</div>';
		                     result = result +"</td>";
		                     if(checkSize>=10 && i==9 ){
		                    	 $("#viewmoresDiv").css("display", "block");
			 	   	   	    	 } 
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#requirementList").append(result); 
	                     $("#assignedRequirementsDiv").css("display", "block");
	                     
	                	 $(".proj_close, .side_mask").click(function(){
								$(".clients_side_wrap").removeClass("clients_side_wrap_active");
								$(".side_mask").fadeOut(200);
								$("body").css('overflow-y', 'auto');
						 });	
			 			 
	                     pagination(); 
                 }
          },
            
       }); 
  } 
</script>   

 <script>
function pagination(){ 
    $('#requirementPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"bFilter": false,
    	"bPaginate":false,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script> 

<jsp:include page="addApplicantFromDashboard.jsp" />
