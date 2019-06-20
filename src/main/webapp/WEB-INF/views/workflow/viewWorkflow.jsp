<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<script>
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
	<script src="/resources/js/requirementWorkflow.js?staticTime=${staticTime}"></script>
<div class="wraper"> 
<div id="popUp"></div>
<form id="requirementWorkFlowForm" name="requirementWorkFlowForm">
  <section class="content-wraper">
   <div class="success_pop" id="selector" >
	       <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
			</div>
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
    
    <c:if test="${not empty requirementWorkFlowStagesDTO}">
    <div class="clearfix">
		 
       <div class="positionRelative reportHead clearfix mb">
        <ul class="pull-right list-unstyled list-inline">
         <c:if test = "${requirementWorkFlowStagesDTO[0].workFlowName != 'Default Job WorkFlow'}">
          <li>
          <c:if test="${not empty requirementWorkFlowStagesDTO}">
          <a class="newAdBtn mr5"  href="${SourceLeadWeb_sourceleadUrl}/addStage?uuid=<c:out value="${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}" />" title="Add">+ Stage</a>
          </c:if>
          </li>
         </c:if>
        <li class="">
            <input class="repSearchBar" placeholder="Search" id="searchtext" type="text">
            <span class="repSearchIcon" onclick="getViewWorkflowStagesList();"></span>
		</li>
        </ul>
        <div class="clearfix"></div>
      </div>
       
    </div>
	</c:if>

    
    <c:if test="${not empty requirementWorkFlowStagesDTO}">
    <div class="clearfix backgroundWhite mb">
  <div class="row">
      <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Name<span class="text-danger paddingLeft">*</span></label>
            <div class="col-md-9" id = "workflowNameDiv">
									<input type="text"
										value='<c:out value="${requirementWorkFlowStagesDTO[0].workFlowName}"/>'
										class="form-control" id="workFlowName" name="workFlowName"
										placeholder="WorkFlow Name" field-name="WorkFlow Name"
										data-validation="required validate_length length1-100"
										onkeyup="requirementWorkflow.validateWorkflowName('${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}');"
										<c:choose>
							<c:when test = "${requirementWorkFlowStagesDTO[0].workFlowName == 'Default Job WorkFlow'}">
							readonly>
							</c:when>
								<c:otherwise>
							>
							</c:otherwise>
						</c:choose>
								</div>
          </div>
        </div>
        <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Description</label>
            <div class="col-md-9">
              <textarea rows="2" 
              value = ''
              class="form-control" name="description" id="description" placeholder="Description" field-name="Description"
								data-validation="validate_length length1-500" data-validation-optional="true"
								<c:choose>
							<c:when test = "${requirementWorkFlowStagesDTO[0].workFlowName == 'Default Job WorkFlow'}">
							readonly> <c:out value="${requirementWorkFlowStagesDTO[0].workflowDescription}"/>
							</c:when>
								<c:otherwise>
							> <c:out value="${requirementWorkFlowStagesDTO[0].workflowDescription}"/>
							</c:otherwise>
						</c:choose>
								</textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
    </c:if>
    
  <div class="clearfix"></div>
  
  <!-- dynamic content starts here -->
  
 		 <c:if test="${not empty requirementWorkFlowStagesDTO}"> 
		<ul class="list-unstyled widgetDivLi" id = "stageList">
		
		<c:forEach var="requirementWorkFlowStages" items="${requirementWorkFlowStagesDTO}">	
		<li class="widgetDiv mb clearfix">
		<!-- Cardmodel widget -->
			<div class="row">
				<div class="col-md-10">
				<div>
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
				</div>
				<div class="col-md-2">
				
					<c:if test = "${requirementWorkFlowStagesDTO[0].workFlowName != 'Default Job WorkFlow'}">
					
					<ul class="list-inline list-unstyled pull-right mb0">
						<c:if test = "${requirementWorkFlowStages.editable == 'Y'}">
						    <sec:authorize access="hasRole('ROLE_EDIT_VIEW_WORKFLOW')">
						<li>
							<a title="Edit" 
							onclick = 'requirementWorkflow.editStage("${requirementWorkFlowStages.uuid}",
							"${requirementWorkFlowStages.requirementWorkFlowUuid}")'>
							<i class="fa fa-pencil"></i></a>
						</li>
						</sec:authorize>
                             <sec:authorize access="hasRole('ROLE_DELETE_WORKFLOW')">
						<li>
							<a title="Delete" 
							onclick = 'requirementWorkflow.deletepopup("${requirementWorkFlowStages.uuid}",
							"${requirementWorkFlowStages.stageName}",
							"${requirementWorkFlowStages.requirementWorkFlowUuid}")'>
							<i class="fa fa-trash"></i></a>
						</li>
						</sec:authorize>
						</c:if>	
						<c:if test = "${requirementWorkFlowStages.editable == 'N'}">	
						<li></li>
						</c:if>	
						
					</ul>
					</c:if>
					
			</div>
			
		</div>
		<!-- Cardmdoel Widget ends -->
		</li>
		</c:forEach>
		</ul>
		</c:if>
		
		<!-- dynamic content ends here -->
			<c:if test="${not empty requirementWorkFlowStagesDTO}">
				<div class="footerstyelsNew">
					<div class="clearfix">
						<div class="pull-right">
						<c:choose>
							<c:when
								test="${requirementWorkFlowStagesDTO[0].workFlowName != 'Default Job WorkFlow'}">
									
									<input value="Save" id="saveReqWorkFlow"
										onclick="requirementWorkflow.saveRequirementViewWorkFlow('+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+');"
										class="btn btn-primary" type="button"> <input
										value="Cancel" class="btn btn-default ml" type="button"
										onclick="requirementWorkflow.cancelViewWorkflow();">
													</c:when>
							<c:otherwise>
							<input value="<< Back To Application Workflow" 
										onclick="requirementWorkflow.cancelViewWorkflow();"
										class="btn btn-primary" type="button">
		
							</c:otherwise>
						</c:choose>


						</div>
					</div>
				</div>
			</c:if>

		</section>
	</form>
    </div>
  </section>
</div>
<jsp:include page="../masterFooter.jsp" /> 