<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<script>
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
$(document).ready(function() {
	requirementWorkflow.getWorkflowList();
});
</script>
<script src="/resources/js/requirementWorkflow.js?staticTime=${staticTime}"></script>
<div class="wraper"> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Application Workflow</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/workflowDashboard">Workflow Dashboard</a> &raquo;
      Application Workflow
      </div>
    </section>
    <div class="project_wrap clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
                <li>
                    <a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/defaultWorkflow" title="Add">+ Workflow</a>	
                    </li>
            
              <li>
				<a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/questionnaireList" title="Add">+ Questionnaire</a>	
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="requirementWorkflow.getWorkflowList();"></span></li>
            </ul>
          </div>
          
          <!-- dynamic content starts here.. -->
          <div class="project_wrap_table datatable_filter" id = "dynamicWorkFlowList">
            
            </div>
          <!-- dynamic content ends here.. -->
          
        </div>
      </div>
    </div>
  </section>
</div>
<div id="popUp"></div>
<jsp:include page="../masterFooter.jsp" /> 	