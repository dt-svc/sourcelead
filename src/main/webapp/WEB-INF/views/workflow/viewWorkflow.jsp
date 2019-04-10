<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="wraper"> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
      
      <c:if test="${not empty requirementWorkFlowStagesDTO}">
        <h2><c:out value="${requirementWorkFlowStagesDTO[0].workFlowName}" /></h2>
       </c:if>
      
      </div>
      
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a>&raquo;  
      <a href="${SourceLeadWeb_sourceleadUrl}/workflowList">Application Workflow</a> &raquo;
      
      <c:if test="${not empty requirementWorkFlowStagesDTO}">
      <c:out value="${requirementWorkFlowStagesDTO[0].workFlowName}" />
      </c:if>
      
      </div>
    </section>
    <div class="clearfix">
		
       <div class="positionRelative reportHead clearfix mb">
        <ul class="pull-right list-unstyled list-inline">
          <li>
          <c:if test="${not empty requirementWorkFlowStagesDTO}">
          <a class="newAdBtn mr5"  href="${SourceLeadWeb_sourceleadUrl}/addStage?uuid=<c:out value="${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}" />" title="Add">+ Add</a>
          </c:if>
          </li>
        <li class="">
            <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
            <span class="repSearchIcon" onclick="getDynamciUserList();"></span>
		</li>
        </ul>
        <div class="clearfix"></div>
      </div>
    </div>
    
    <c:if test="${not empty requirementWorkFlowStagesDTO}">
    <div class="clearfix backgroundWhite mb">
  <div class="row">
      <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Name<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-9">
              <input type="text" 
              value='<c:out value="${requirementWorkFlowStagesDTO[0].workFlowName}"/>'
              class="form-control" id="workFlowName" name="workFlowName" placeholder="WorkFlow Name" field-name="WorkFlow Name"
								data-validation="required validate_length length1-100">
            </div>
          </div>
        </div>
        <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Description</label>
            <div class="col-md-9">
              <textarea rows="2" 
              value = '<c:out value="${requirementWorkFlowStagesDTO[0].workflowDescription}"/>'
              class="form-control" name="description" id="description" placeholder="Description" field-name="Description"
								data-validation="validate_length length1-500" data-validation-optional="true"></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
    </c:if>
    
  <div class="clearfix"></div>
  
  <!-- dynamic content starts here -->
  
 		 <c:if test="${not empty requirementWorkFlowStagesDTO}"> 
		<ul class="list-unstyled widgetDivLi">
		
		<c:forEach var="requirementWorkFlowStages" items="${requirementWorkFlowStagesDTO}">	
		<li class="widgetDiv mb clearfix">
		<!-- Cardmodel widget -->
			<div class="row">
				<div class="col-md-10">
				
					<c:if test = "${requirementWorkFlowStages.stageName == 'Submission'}">
						<span class="submissionShare pull-left"></span>
					</c:if>
					<c:if test = "${requirementWorkFlowStages.stageName == 'Short List'}">
						<span class='shortlilstedIC pull-left'></span>
					</c:if>
					<c:if test = "${requirementWorkFlowStages.stageName == 'Submit to Client'}">
						<span class='shortlilstedIC pull-left'></span>
					</c:if>
					<c:if test = "${requirementWorkFlowStages.stageName == 'Interview'}">
						<span class='invterviewIC pull-left'></span>
					</c:if>
					<c:if test = "${requirementWorkFlowStages.stageName == 'Offer'}">
						<span class='jobOfferIC pull-left'></span>
					</c:if>
					<c:if test = "${requirementWorkFlowStages.stageName == 'Reject'}">
						<span class='submissionShare pull-left'></span>
					</c:if>
					<c:if test = "${requirementWorkFlowStages.stageName == 'Joined'}">
						<span class='joinedIC pull-left'></span>
					</c:if>
					<c:if test = "${requirementWorkFlowStages.stageName == 'Backout'}">
						<span class='submissionShare pull-left'></span>
					</c:if>
					<c:if test = "${requirementWorkFlowStages.stageName == 'On-hold'}">
						<span class='submissionShare pull-left'></span>
					</c:if>
					<c:if test = "${requirementWorkFlowStages.stageName == 'No Response'}">
						<span class='submissionShare pull-left'></span>
					</c:if>
					
										
					<h4 class="pull-left mt10 ml">${requirementWorkFlowStages.stageName}</h4>
				</div>
				<div class="col-md-2">
					<ul class="list-inline list-unstyled pull-right mb0">
						<c:if test = "${requirementWorkFlowStages.editable == 'Y'}">
						<li>
							<a title="Edit" href="${SourceLeadWeb_sourceleadUrl}/addStage"><i class="fa fa-pencil"></i></a>
						</li>
						<li>
							<a title="Delete" 
							onclick = 'deleteRequirementWorkflowStage("${requirementWorkFlowStages.uuid}","${requirementWorkFlowStages.requirementWorkFlowUuid}")'>
							<i class="fa fa-trash"></i></a>
						</li>
						</c:if>	
						<c:if test = "${requirementWorkFlowStages.editable == 'N'}">	
						<li></li>
						</c:if>	
						
					</ul>
			</div>
		</div>
		<!-- Cardmdoel Widget ends -->
		</li>
		</c:forEach>
		</ul>
		</c:if>
		
		<!-- dynamic content ends here -->
		<c:if test="${not empty requirementWorkFlowStagesDTO}">
		<div style="display: none;" id = "stagesDto" value = "{requirementWorkFlowStagesDTO}"></div>
		</c:if>
		<div class="footerstyelsNew">
	<div class="clearfix">
		<div class="pull-right">
		<input value="Save" class="btn btn-primary" type="button">
		<input value="Cancel" class="btn btn-default ml" type="button" onclick = "cancelViewWorkflow();">
		</div>
	</div>
	</div>
    </div>
  </section>
</div>
<script>
function deleteRequirementWorkflowStage(stageUuid, workflowUuid){
	var stageDto = $("#stagesDto").val();
	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/deleteRequirementWorkflowStage?uuid="+stageUuid+"&requirementWorkFlowUuid="+workflowUuid,
		success : function(dataList){
			if(dataList == "Success"){
				location.reload();
			}else{
				 showError("Operation failed, please try again");
			}
			}
		});
		
}

function cancelViewWorkflow(){
	
}

</script>
<jsp:include page="../masterFooter.jsp" /> 