<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Submission List</div>
    </section>
    
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
        <div class="timesheet_head">
        <div class="pull-left ml mt10">
				<p><span class="select-fild">
				    <select id="reqList">
                  		<option value="appList" onclick="loadRequirementApplicants();" >Applicant List</option>
                        <option value="subList" selected="selected" onclick="loadRequirementSubmissions();">Submission List</option> 
                        <option value="interList" onclick="loadRequirementInterviews();">Interview List</option>
                        <option value="offerlist" onclick="loadRequirementOffers();">Offer List</option> 
                    </select>
				</span>
				</p>
			</div>
			 <div class="pull-left ml mt10">
				<p><span class="select-fild">
				    <c:if test="${tab == 'today'}">
				    <select id="tabList" onchange="tabsList(this);" >
                  		<option value="today" selected="selected">Today</option>
                        <option value="previous">Previous</option> 
                    </select>
                </c:if>
                <c:if test="${tab == 'previous'}">
				    <select id="tabList" onchange="tabsList(this);" >
                  		<option value="today">Today</option>
                        <option value="previous" selected="selected">Previous</option> 
                    </select>
                </c:if>
				</span>
				</p>
			</div>
          <div class="clearfix"></div>
          <div class="remove_search hidden">
            <input type="text" placeholder="search" />
          </div>
        </div>
        <div class="clients_wrap_table datatable_filter">
                <table id="appTable">
            
            <thead>
              <tr>
                <th>Applicant name</th>
                <th>Job</th>
                <th>Client</th>
                <th>Applied On</th>
                <th>Submitted On</th>
                <th>Resume</th>
                <th>Exp</th>
                <th>Sub rate</th>
                <th>Source</th>
                <th>Score</th>
                <th>Submitted by</th>
                <th>Current Status</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="reqSub" items="${toDayReqSubmissionsList}">
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
              <td><div><span>${reqSub.submissionId.createdDateStr}</span></div></td>
              <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.submittedOn}">
	             	 ${reqSub.submissionId.submittedOn}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.submittedOn}">
	             	 --
	              </c:if>
              </span></div>
              </td>
             
               <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.candidateResumes.resumeUUID}">
	              <a href='<c:url value='/downloadResume?resumeUUID=${reqSub.submissionId.candidateResumes.resumeUUID}'/>'><i class='fa fa-download'></i></a>
	               <%-- <a href="<c:url value='/downloadResumeCandidate/${reqSub.submissionId.candidateResumes.resumeUUID}'/>"><i class="fa fa-download"></i> </a> --%>
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.candidateResumes.resumeUUID}">
	             	 --
	              </c:if>
              </span></div>
              </td>
              
               <td><div><span>
	              <c:if test="${not empty reqSub.submissionId.candidate.experienceYears && not empty reqSub.submissionId.candidate.experienceMonths}">
	               ${reqSub.submissionId.candidate.experienceYears} Year/s ${reqSub.submissionId.candidate.experienceMonths}  Month/s
	              </c:if>
	                <c:if test="${not empty reqSub.submissionId.candidate.experienceYears && empty reqSub.submissionId.candidate.experienceMonths}">
	             	  ${reqSub.submissionId.candidate.experienceYears} Year/s
	              </c:if>
	               <c:if test="${empty reqSub.submissionId.candidate.experienceYears && not empty reqSub.submissionId.candidate.experienceMonths}">
	               		${reqSub.submissionId.candidate.experienceMonths} Year/s
	               </c:if>
	                 <c:if test="${empty reqSub.submissionId.candidate.experienceYears && empty reqSub.submissionId.candidate.experienceMonths}">
	               		--
	               </c:if>
              </span></div>
              </td>
              
              
              <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.finalizedRate}">
	            	  ${reqSub.submissionId.finalizedRate}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.finalizedRate}">
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
	            	  ${reqSub.submissionId.candidateScore}%
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.candidateScore}">
	             	 --
	              </c:if>
              </span></div></td> 
            
             <td><div><span>
	               <c:if test="${not empty reqSub.submissionId.submittedBy}">
	            	  ${reqSub.submissionId.submittedBy}
	              </c:if>
	              <c:if test="${empty reqSub.submissionId.submittedBy}">
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

<script type="text/javascript">
	function loadRequirementApplicants(){
		var tab = document.getElementById("tabList").value;
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/requirementApplicantsList?&tab="+tab+"&orgLocationId="+orgLocId;
	}
	function loadRequirementSubmissions(){
		var tab = document.getElementById("tabList").value;
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/requirementSubmissionsList?&tab="+tab+"&orgLocationId="+orgLocId;
	}
	function loadRequirementInterviews(){
		var tab = document.getElementById("tabList").value;
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/requirementInterviewsList?&tab="+tab+"&orgLocationId="+orgLocId;
	}
	function loadRequirementOffers(){
		var tab = document.getElementById("tabList").value;
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		window.location.href = sourceleadUrl + "/requirementOffersList?&tab="+tab+"&orgLocationId="+orgLocId;
	}

	function tabsList(sel){
		var tab = sel.value;
		var reqList = document.getElementById("reqList").value;
		var orgLocId = '${SourceLeadWeb_orgLocationId}';
		if(reqList=="appList"){
			var sourceleadUrl = '${sourceleadUrl}';
			window.location.href = sourceleadUrl + "/requirementApplicantsList?&tab="+tab+"&orgLocationId="+orgLocId;
		}
		if(reqList=="subList"){
			var sourceleadUrl = '${sourceleadUrl}';
			window.location.href = sourceleadUrl + "/requirementSubmissionsList?&tab="+tab+"&orgLocationId="+orgLocId;
			 }
		if(reqList=="interList"){
			var sourceleadUrl = '${sourceleadUrl}';
			window.location.href = sourceleadUrl + "/requirementInterviewsList?&tab="+tab+"&orgLocationId="+orgLocId;
		}
		if(reqList=="offerlist"){
			var sourceleadUrl = '${sourceleadUrl}';
			window.location.href = sourceleadUrl + "/requirementOffersList?&tab="+tab+"&orgLocationId="+orgLocId;
		}
	}
	</script> 