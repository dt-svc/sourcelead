<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="/resources/js/requirementWorkflow.js?staticTime=${staticTime}"></script>
<script>
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/requirementQuestionnaireFormBuilder.js"></script>
<div class="wraper"> 
   <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Questionnaire</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a>&raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/workflowList">Application Workflow</a> &raquo;
      Questionnaire</div>
    </section>
       <sec:authorize access="hasRole('ROLE_ADD_WORKFLOW')"> 
    <div class="project_wrap clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
				<li>
				<a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/addQuestionnaire" title="Add">+ Add questionnaire</a>	
					
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="requirementWorkflow.getWorkflowQuestionnnaireList();"></span></li>
            </ul>
          </div>
           <div id ="questionnaireList" class="project_wrap_table datatable_filter"></div>
          </div>
      </div>
    </div>
    </sec:authorize>
  </section>
</div>
<div id="popUp"></div>
    <div id="choicepopUp"></div>
<script>
$(document).ready(function() {
	requirementWorkflow.getWorkflowQuestionnnaireList();
	
});

</script>
<jsp:include page="../masterFooter.jsp" /> 