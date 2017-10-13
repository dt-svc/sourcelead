<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Offers List</div>
    </section>
    
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
        <jsp:include page="JobCountFilter.jsp"></jsp:include>
        <div class="clients_wrap_table datatable_filter">
           <table id="appTable">
            <thead>
              <tr>
                <th>Applicant name</th>
                <th>Job</th>
                <th>Client</th>
                <th>Resume</th>
                <th>Join Date</th>
                <th>Rate</th>
                <th>Position Type</th>
                <th>Attachment</th>
                <th>Offered On</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="reqSub" items="${toDayReqOfferscount}">
              <tr>
			 <td><div><span>${reqSub.submissionId.candidate.firstName}</span></div></td>
              <sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')"> 
			 	<td><div class="proj_show"><span><a href='<c:url value='/requirementSummary?requirementUUID=${reqSub.submissionId.requirement.requirementUUID}'/>'>${reqSub.submissionId.requirement.jobTitle}<i class='fa fa-binoculars'></i></a></span></div></td>
            </sec:authorize>
            <sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">
            	<td><div><span>${reqSub.submissionId.requirement.jobTitle}</span></div></td>
            </sec:authorize>
              <td><div><span>
              <c:if test="${not empty reqSub.submissionId.requirement.parentRequirement.orgLocationId.org.orgName}">
              	${reqSub.submissionId.requirement.parentRequirement.orgLocationId.org.orgName}
              </c:if>
              <c:if test="${empty reqSub.submissionId.requirement.parentRequirement.orgLocationId.org.orgName}">
              	--
              </c:if>
              </span></div></td>
               <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.candidateResumes.resumeUUID}">
	                <a href='<c:url value='/downloadResume?resumeUUID=${reqSub.submissionId.candidateResumes.resumeUUID}'/>'><i class='fa fa-download'></i></a>
	                <a class="fa fa-eye ml10" onclick="viewInterviewListResumes('${reqSub.submissionId.candidateResumes.resumeUUID}','${reqSub.submissionId.candidateResumes.fileName}')"></a>
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.candidateResumes.resumeUUID}">
	             	 --
	              </c:if>
              </span></div>
              </td>
               <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.offerJoinDate}">
	               ${reqSub.submissionId.offerJoinDate}
	              </c:if>
	                <c:if test="${empty reqSub.submissionId.offerJoinDate}">
	              	 --
	              </c:if>
              </span></div>
              </td>
              <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.offerRate}">
	            	  ${reqSub.submissionId.offerRate} ${reqSub.submissionId.ratePer}  ${reqSub.submissionId.rateType} 
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.offerRate}">
	             	 --
	              </c:if>
              </span></div></td>
         
              
               <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.positionType}">
	            	  ${reqSub.submissionId.positionType}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.positionType}">
	             	 --
	              </c:if>
              </span></div></td>
              
               <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.attachment}">
	            	 <a href="<c:url value='/downloadOfferAttachment?offerId=${reqSub.submissionId.offerId}'/>"><i class="fa fa-paperclip"></i> </a>
	            	 <a class="fa fa-eye ml10" onclick="viewOffersListAttachment('${reqSub.submissionId.offerId}','shiva')"></a>
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.attachment}">
	             	 --
	              </c:if>
              </span></div></td> 
            
             <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.createdDateStr}">
	            	  ${reqSub.submissionId.createdDateStr}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.createdDateStr}">
	             	 --
	              </c:if>
              </span></div></td> 
              
               <td><div><span>
	               <c:if test="${not empty reqSub.toStage}">
	            	 ${reqSub.toStage}
	              </c:if>
	              <c:if test="${empty reqSub.toStage}">
	             	 --
	              </c:if>
              </span></div></td>
            
               </tr>
			  </c:forEach>
            </tbody>
            
          </table>
        </div>
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
  </section>
  
  
<script>
function viewInterviewListResumes(resumeUUID,title){
	if(resumeUUID != null && resumeUUID != ""){
		window.open(sourceleadUrl+'/viewInterviewListResumes/'+title+'?resumeUUID='+resumeUUID,title,"height=600,width=600");
	}  
}
</script>

<script>
function viewOffersListAttachment(offerId,title){
	if(offerId != null && offerId != ""){
		window.open(sourceleadUrl+'/viewOffersListAttachment/'+title+'?offerId='+offerId,title,"height=600,width=600");
	}  
}
</script>


  
  <script>
function pagination1(){ 
    $('#appTable').DataTable( { 
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
    $("#appTable").wrap("<div class='overflow_Auto'></div>");
}
</script>
<script type="text/javascript">
window.onload = function () {
	pagination1();
 };
 
</script>

