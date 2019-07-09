<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script>
var assignedUUID = '${assignedUUID}'; 
var pmsUrl = '${SourceLeadWeb_pmsUrl}';
</script>

 <script>
$(document).ready(function() {
	getTemplateOverViewDetails('manager');
});
</script>

<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Appraisal View</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo; <a href="<c:url value='${SourceLeadWeb_pmsUrl}/employeePerformanceAppraisalList'/>">Appraisal List</a>
			&raquo; <a href="<c:url value='${SourceLeadWeb_pmsUrl}/appraisalEmployeesList?templateUUID=${templateUUID}'/>">Employee List</a>
			&raquo; Appraisal View
		</div>
	</section>
	
	<div class="content-area content-new clearfix  backgroundWhite">
        <div class="project_wrap">
              <div class="immiStepsHead clearfix">
               <div id="appraisalLoading">
                <div class="selfAppraisalWidget clearfix">
                <ul class="list-unstyled list-inline">
                      <li class="activeApprisalwidget" title="overview">
                        <a onclick="getTemplateOverViewDetails('manager');" class="clearfix mb0"> 
                        <div class="overviewIcon"></div>
                          Overview
                        </a>
                      </li>
                      <li title="Feedback" id="feedbackWidget">
                          <a onclick="getAppraisalFeedbackDetails();"  class="clearfix mb0">
                            <div class="categoryIcon"></div>
                            <div class="clearfix"></div>
                            Feedback
                        </a>
                      </li>
                      
                      <c:if test="${isGolasConfigured == 'Y'}">
                      <li title="Goals" id="goalsWidget">
                          <a onclick="getAppraisalGoalsDetails();" class="clearfix mb0">
                            <div class="goalsIcon"></div>
                            <div class="clearfix"></div>
                             Goals
                          </a>
                      </li>
                      </c:if>
                      
                      <li title="Project"  id="tasksWidget">
                          <a onclick="getAppraisalTaskDetails();" class="clearfix mb0">
                          <div class="taksIcon"></div>
                          <div class="clearfix"></div>
                          Tasks
                      </a>
                      </li>
                      <li title="Final Score" id="finalScoreWidget">
                          <a onclick="getAppraisalFinalScoreDetails('self');" class="clearfix mb0">
                          <div class="finalScoreIcon"></div>
                          <div class="clearfix"></div>
                        Final Score
                        </a>
                      </li>
                      <div class="clearfix"></div>
                    </ul>
                  </div>
              </div>
              
              
              <div id="performanceAppraisalDiv"></div>
              
              </div>
              </div>
             </div>
	
	</section>
	
	<jsp:include page="../performance/templateOverView.jsp" />  
	<jsp:include page="../performance/appraisalEmployeeFeedback.jsp" />   