<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
		window.onload = function () {
			usersList();
		 };
	</script>
	
<section class="content-wraper">
  <section class="content-header clearfix">
   <c:if test="${!empty msg}">
	        <div class="success_pop" id="sucesspop"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
    <div class="pull-left">
      <h2>Recruiter Reports</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; Recruiter Reports</div>
  </section>
      <div class="candidate_wrap_table">
        <div id="usersList"></div>
      </div>
  </section>    
      
    
    
 <%-- 
     @author : prasad K.
     this script is for get Recruiters Report.
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
function usersList(){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAllUsersByOrgLocationsId?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
	                	
                	     $("#usersList").empty();
	                	 var result = "";
	                	 result = result +"<table id='' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Name</th>";
	                     result = result +"    <th>Requirements</th>";
	                     result = result +"    <th>Candidates</th>";
	                     result = result +"    <th>Applicants</th>"; 
	                     result = result +"    <th>Shortlisted</th>"; 
	                     result = result +"    <th>Submitted</th>"; 
	                     result = result +"    <th>Interviewed</th>"; 
	                     result = result +"    <th>Offered</th>"; 
	                     result = result +"<th>Rejected</th>";
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
	                    		 result = result +"<td><div class=''><span title='"+opt.firstName+"'>"+opt.firstName+"</span></a></div></td>";
	                    	 }
	                    	 
	                    	 if(opt.userRequirementsCount !=null && opt.userRequirementsCount !='' && opt.userRequirementsCount !='undefined'){
	                    		  result = result +"<td><div class='tbldata-dotdot-sm'> <span title='"+opt.userRequirementsCount+"'>"+opt.userRequirementsCount+"</span></div></td>";
	                    	 }
		                    
	                    	 if(opt.userCandidatesCount !=null && opt.userCandidatesCount !='' && opt.userCandidatesCount !='undefined'){
	                    		  result = result +"<td><div class='tbldata-dotdot-sm'> <span title='"+opt.userCandidatesCount+"'>"+opt.userCandidatesCount+"</span></div></td>";
	                    	 }
	                    	 
	                    	 if(opt.userApplicantsCount !=null && opt.userApplicantsCount !='' && opt.userApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='tbldata-dotdot-sm'> <span title='"+opt.userApplicantsCount+"'>"+opt.userApplicantsCount+"</span></div></td>";
	                    	 }
	                    	 result = result +"<td><div>--</div></td>";
			                 result = result +"<td><div>--</div></td>";
		                     result = result +"<td><div>--</div></td>";
		                     result = result +"<td><div>--</div></td>";
		                     result = result +"<td><div>--</div></td>";
		                     result = result +"<td><div>--</div></td>";

		                     
		                    
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#usersList").append(result); 
	                     
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

<jsp:include page="../masterFooter.jsp" />