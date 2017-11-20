<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Applicant List</div>
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
                <th>Email</th>
                <th>Phone Number</th>
                <th>Resume</th>
                <th>Primary Skill</th>
                <th>Source</th>
                <th>Score</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="reqSub" items="${toDayReqApplicantscount}">
               <tr>
			 <td><div><span>${reqSub.submissionId.candidate.firstName}</span></div></td>
			  <sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')"> 
			 	<td><div class="proj_show"><span><a href='<c:url value='/requirementSummary?type=applicants&requirementUUID=${reqSub.submissionId.requirement.requirementUUID}&candidateUUID=${reqSub.submissionId.candidate.candidateUUID}&submissionId=${reqSub.submissionId.id}'/>'>${reqSub.submissionId.requirement.jobTitle}<i class='fa fa-binoculars'></i></a></span></div></td>
            </sec:authorize>
            <sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">
            	<td><div><span>${reqSub.submissionId.requirement.jobTitle}</span></div></td>
            </sec:authorize>
             <%--  <td><div><span>${reqSub.submissionId.requirement.jobTitle}</span></div></td> --%>
              <td><div><span>
              <c:if test="${not empty reqSub.submissionId.requirement.parentRequirement.orgLocationId.orgLocationName}">
              	${reqSub.submissionId.requirement.parentRequirement.orgLocationId.orgLocationName}
              </c:if>
              <c:if test="${empty reqSub.submissionId.requirement.parentRequirement.orgLocationId.orgLocationName}">
              	--
              </c:if>
              </span></div></td>
              <td><div><span>${reqSub.submissionId.candidate.email}</span></div></td>
              <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.candidate.mobilePhone}">
	             	 ${reqSub.submissionId.candidate.mobilePhone}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.candidate.mobilePhone}">
	             	 --
	              </c:if>
              </span></div>
              </td>
              
              <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.candidateResumes.resumeUUID}">
	               <a target="_blank" href="${reqSub.submissionId.candidateResumes.fileURL}"/><i class='fa fa-download'></i></a>
	               <a href="javascript:void(0)" onclick="viewFile('${reqSub.submissionId.candidateResumes.fileURL}','${reqSub.submissionId.candidateResumes.fileName}','${reqSub.submissionId.candidateResumes.fileContentType}');" class="fa fa-eye ml10 paddingRight"></a>
	               <%-- <a href="<c:url value='/downloadResumeCandidate/${reqSub.submissionId.candidateResumes.resumeUUID}'/>"><i class="fa fa-download"></i> </a> --%>
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.candidateResumes.resumeUUID}">
	             	 --
	              </c:if>
              </span></div>
              </td>
              
              
              <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.candidate.primarySkill}">
	            	  ${reqSub.submissionId.candidate.primarySkill}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.candidate.primarySkill}">
	             	 --
	              </c:if>
              </span></div></td>
         
              
               <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.createdUser}">
	            	  ${reqSub.submissionId.createdUser}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.createdUser}">
	             	 --
	              </c:if>
              </span></div></td>
              
               <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.candidateScore}">
	            	  ${reqSub.submissionId.candidateScore}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.candidateScore}">
	             	 --
	              </c:if>
              </span></div></td> 
              
             <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.submissionStatus}">
	            	  ${reqSub.submissionId.submissionStatus}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.submissionStatus}">
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
 function applicatDocumentListDocumentView(resumeUUID,title){
	 var sourceleadUrl ='${sourceleadUrl}';
		if(resumeUUID != null && resumeUUID != ""){
			window.open(sourceleadUrl+'/viewInterviewListResumes/'+title+'?resumeUUID='+resumeUUID,title,"height=600,width=600");
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
<jsp:include page="../masterFooter.jsp" />