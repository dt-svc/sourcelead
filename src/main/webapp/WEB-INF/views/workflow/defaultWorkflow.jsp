<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<script>
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';

$(document).ready(function() {
	requirementWorkflow.getDefaultWorkflowStages();
	
});
</script>
<script src="/resources/js/requirementWorkflow.js?staticTime=${staticTime}"></script>
<div class="wraper"> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Workflow</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/workflowList">Application Workflow</a> &raquo;
      Add Workflow</div>
    </section>
    <div class="clearfix">
		
      <%--  <div class="positionRelative reportHead clearfix mb">
        <ul class="pull-right list-unstyled list-inline">
          <li><a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/addStage" title="Add">+ Add</a></li>
        <li class="">
            <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
            <span class="repSearchIcon" onclick="getDynamciUserList();"></span>
		</li>
        </ul>
        <div class="clearfix"></div>
      </div> --%>
    </div>
    
    <form id="requirementWorkFlowForm" name="requirementWorkFlowForm">
<div class="clearfix backgroundWhite mb">
  <div class="row">
      <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Name<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="workFlowName" name="workFlowName" placeholder="WorkFlow Name" field-name="WorkFlow Name"
								data-validation="required validate_length length1-100">
            </div>
          </div>
        </div>
        <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Description</label>
            <div class="col-md-9">
              <textarea rows="2" class="form-control" name="description" id="description" placeholder="Description" field-name="Description"
								data-validation="validate_length length1-500" data-validation-optional="true"></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="clearfix"></div>
      
      <!-- dynamic stages content starts here-->
        <div id ="dynamicWorkflow">
		</div>
	<!-- dynamic stages content ends here-->
		
	<div class="footerstyelsNew">
	<div class="clearfix">
		<div class="pull-right" >
		 <input type="button" value="Save" id="saveReqWorkFlow" onclick="requirementWorkflow.saveRequirementWorkFlow();" class="btn btn-primary">
		 <input type="button" value="Cancel"
							onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/workflowList'"
							class="btn btn-default ml">
		</div>
	</div>
	</div>
	</form>
    </div>
  </section>
</div>
<jsp:include page="../masterFooter.jsp" /> 	