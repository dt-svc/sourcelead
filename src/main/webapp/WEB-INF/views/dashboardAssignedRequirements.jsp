<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/formvalidator.js"></script>

<div class="content-dashboard clearfix" id="assignedRequirementsDiv" style="display: none;"> 
      <!-- Assigned Requirements -->
      <div class="clearfix dashboard-widget mb col-md-12 hdrPosition">
        <h4 class="clearfix">
        Assigned Requirements
        &nbsp;&nbsp;<!-- <span title="Calendar"><i aria-hidden="true" class="fa fa-calendar" id="datepicker"></i>
        </span> -->
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
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAssignedRequirementListByOrgLocationIDAndUserId?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<table id='requirementPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>ID-Title</th>";
	                     if(orgATS == 'Y'){
	                     result = result +"    <th>Client</th>";
	                     }
	                     result = result +"    <th>Rate</th>"; 
	                     result = result +"    <th>Priority</th>"; 
	                     result = result +"    <th>Applicants</th>"; 
	                     result = result +"    <th>Shortlisted</th>"; 
	                     result = result +"    <th>Submitted</th>"; 
	                     result = result +"    <th>Interviewed</th>"; 
	                     result = result +"    <th>Offered</th>"; 
	                     '<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
	                     result = result +"<th>Action</th>";
	                    '</sec:authorize>';
	                     result = result +"    <th>Release</th>"; 
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
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>";
	                    	 if(opt.jobId!=null && opt.jobId !='' && opt.jobId !='undefined'){
	                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobId+"-"+opt.jobTitle+"'>"+opt.jobId+"-"+opt.jobTitle+"</span></a></div></td>";
	                    	 }else{
		                    	 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";
		                     }
		                     if(orgATS == 'Y'){
			                     if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
			                    	 result = result +"<td><div><span title='"+opt.clientName+"'>"+opt.clientName+"</span></div></td>";
			                     }else{
			                    	 result = result +"<td><div>-----</div></td>";
			                     }
		                     }
		                     if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
			                     result = result +"<td><div><span><span>"+opt.rate+"</span></span></div></td>";
		                     }else{
			                     result = result +"<td><div><span><span>-----</span></span></div></td>";
		                     }
		                     if(opt.priority != null && opt.priority != '' && opt.priority != 'undefined'){
		                    	 result = result +"<td><div>"+opt.priority+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>----</div></td>";
		                     }
	                    	 result = result +"<td><div>"+opt.noOfApplicants+"</div></td>";

		                     result = result +"<td><div>"+opt.noOfShortlistedApplicants+"</div></td>";
			                 result = result +"<td><div>"+opt.noOfSubmittedApplicants+"</div></td>";
		                     result = result +"<td><div>"+opt.noOfInterviewedApplicants+"</div></td>";
		                     result = result +"<td><div>"+opt.noOfOfferedApplicants+"</div></td>";

		         	        '<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
		         	       result = result +'<td><div><a href="javascript:void(0)" title="" onclick="getAddApplicant('+opt.id+',\''+opt.reqCoolingOffPeriod+'\');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div></td>';
		         	        '</sec:authorize>';
		                     
		                     if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
		                    	 if(opt.isRequirementReleasedByuser == 'Y'){
		                       		 result = result +"<td><div><a href='<c:url value='/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>'><i class='fa fa-bullhorn success'></i></a></div></td>"; 
		                       	 }else{	
		                       	 result = result +"<td><div><a href='<c:url value='/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>'><i class='fa fa-bullhorn'></i></a></div></td>";
		                       	 }
		                     }else{
			                     result = result +"<td><div>--</div></td>";
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
                 }else{
                	 $("#requirementList").removeAttr("style");
  	    	      /*    var result ="";
		    	    
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Requirement Found</p>";
					 result = result+"			 </div>"; */
					
					$("#requirementList").empty();
				    //$("#requirementList").append(result); 
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
    	"ordering": false,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<jsp:include page="addApplicantFromDashboard.jsp" />
