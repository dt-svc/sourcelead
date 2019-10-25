<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<script>
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
	<script src="/resources/js/requirementWorkflow.js?staticTime=${staticTime}"></script>
	<script>
	$(document).ready(function() {
		
		$("#searchtext").keyup(function(e) {
			if (e.which == 13) {
				requirementWorkflow.getViewWorkflowStagesList();

			}
		});
	});
</script>
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

		 <c:if test ="${isWorkflowEditable == true}">	
         <c:choose>
         <c:when test="${requirementWorkFlowStagesDTO[0].stageCount == 15 || requirementWorkFlowStagesDTO[0].stageCount > 15}">
         <input value="+ Stage" id="stageButton" onclick="requirementWorkflow.alertStageCount();" class="btn btn-primary margin-top0" type="button">
         </c:when>
         <c:otherwise>
              <input value="+ Stage" id="stageButton" 
        	       onclick="requirementWorkflow.saveRequirementAndAddAStage('<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>','${requirementWorkFlowStages.stageName}','<c:out value="${requirementWorkFlowStagesDTO[0].workFlowName}" />');" class="btn btn-primary margin-top0" type="button">
         </c:otherwise>
         </c:choose>
			</c:if>
          </c:if>
          </li>
         </c:if>
        <li class="">
            <input class="repSearchBar" placeholder="Search" id="searchtext" type="text">
            <span class="repSearchIcon" onclick="requirementWorkflow.getViewWorkflowStagesList();"></span>
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
						
						<c:choose>
						<c:when test = "${requirementWorkFlowStagesDTO[0].workFlowName == 'Default Job WorkFlow' || isWorkflowEditable == false}">
						<input type="text"
										value='<c:out value="${requirementWorkFlowStagesDTO[0].workFlowName}"/>'
										class="form-control" id="workFlowName" name="workFlowName"
										placeholder="WorkFlow Name" field-name="WorkFlow Name"
										data-validation="required validate_length length1-100"
										onkeyup="requirementWorkflow.validateWorkflowName('${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}');" disabled>
						</c:when>
						<c:otherwise>
						<input type="text"
										value='<c:out value="${requirementWorkFlowStagesDTO[0].workFlowName}"/>'
										class="form-control" id="workFlowName" name="workFlowName"
										placeholder="WorkFlow Name" field-name="WorkFlow Name"
										data-validation="required validate_length length1-100"
										onkeyup="requirementWorkflow.validateWorkflowName('${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}');">
						</c:otherwise>
						</c:choose>
						
								</div>
          </div>
        </div>
        <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Description</label>
            <div class="col-md-9">
            
            <c:choose>
            <c:when test = "${requirementWorkFlowStagesDTO[0].workFlowName == 'Default Job WorkFlow' || isWorkflowEditable == false}">
            <textarea rows="2"  value = '' class="form-control" name="description" id="description" placeholder="Description" field-name="Description"
			data-validation="validate_length length1-500" data-validation-optional="true" disabled><c:out value="${requirementWorkFlowStagesDTO[0].workflowDescription}"/></textarea>
            </c:when>
            <c:otherwise>
            <textarea rows="2"  value = '' class="form-control" name="description" id="description" placeholder="Description" field-name="Description"
			data-validation="validate_length length1-500" data-validation-optional="true"><c:out value="${requirementWorkFlowStagesDTO[0].workflowDescription}"/></textarea>
            </c:otherwise>
            </c:choose>
								
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
				<c:choose>
					<c:when test = "${requirementWorkFlowStages.stageName == 'Submission'}">
						<i class='fa fa-check-square-o fa-2x text-muted pull-left'></i>
					</c:when>
					<c:when test = "${requirementWorkFlowStages.stageName == 'Short List'}">
						<i class='fa fa-list-ul fa-2x text-muted pull-left'></i>
					</c:when>
					<c:when test = "${requirementWorkFlowStages.stageName == 'Submit to Client'}">
						<i class='fa fa-send fa-2x text-muted pull-left'></i>
					</c:when>
					<c:when test = "${requirementWorkFlowStages.stageName == 'Interview'}">
						<i class='fa fa-handshake-o fa-2x text-muted pull-left'></i>
					</c:when>
					<c:when test = "${requirementWorkFlowStages.stageName == 'Offer'}">
						<i class='fa fa-envelope-open-o fa-2x text-muted pull-left'></i>
					</c:when>
					<c:when test = "${requirementWorkFlowStages.stageName == 'Reject'}">
						<i class='fa fa-ban pull-left fa-2x text-muted'></i>
					</c:when>
					<c:when test = "${requirementWorkFlowStages.stageName == 'Joined'}">
						<i class='fa fa-id-badge pull-left fa-2x text-muted'></i>
					</c:when>
					<c:when test = "${requirementWorkFlowStages.stageName == 'Backout'}">
						<i class='fa fa-arrow-circle-left pull-left fa-2x text-muted'></i>
					</c:when>
					<c:when test = "${requirementWorkFlowStages.stageName == 'On-hold'}">
						<i class='fa fa-pause-circle-o pull-left fa-2x text-muted'></i>
					</c:when>
					<c:when test = "${requirementWorkFlowStages.stageName == 'No Response'}">
						<i class='fa fa-exclamation-circle pull-left fa-2x text-muted'></i>
					</c:when>
					
					<c:otherwise>
						<i class='fa fa-sitemap pull-left fa-2x text-muted'></i>
					</c:otherwise>
					</c:choose>
					<h4 class="pull-left mt10 ml">${requirementWorkFlowStages.stageName}</h4>
				</div>
				</div>
				<div class="col-md-2">
				
					<c:if test = "${requirementWorkFlowStagesDTO[0].workFlowName != 'Default Job WorkFlow' && isWorkflowEditable == true}">
					<ul class="list-inline list-unstyled pull-right mb0">
						<c:if test = "${requirementWorkFlowStages.configureType == 'Client'}">
						    <sec:authorize access="hasRole('ROLE_EDIT_VIEW_WORKFLOW')">
						<li>
							<a title="Edit" 
							onclick = 'requirementWorkflow.editStage("${requirementWorkFlowStages.uuid}",
							"${requirementWorkFlowStages.requirementWorkFlowUuid}",
							"<c:out value="${requirementWorkFlowStagesDTO[0].workFlowName}" />")'>
							<i class="fa fa-pencil"></i></a>
						</li>
						</sec:authorize>
                             <sec:authorize access="hasRole('ROLE_DELETE_WORKFLOW')">
                        
                        <c:choose>
                        <c:when test="${requirementWorkFlowStages.configureType == 'Client'}">
                        <li>
							<a title="Delete" 
							onclick = 'requirementWorkflow.deletepopup("${requirementWorkFlowStages.uuid}",
							"${requirementWorkFlowStages.stageName}",
							"${requirementWorkFlowStages.requirementWorkFlowUuid}")'>
							<i class="fa fa-trash"></i></a>
							</li>
							</c:when>
                        <c:otherwise>
                        <li></li><li></li>
                        </c:otherwise>
                        </c:choose>   
						
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
							<c:when test="${requirementWorkFlowStagesDTO[0].workFlowName != 'Default Job WorkFlow' && isWorkflowEditable == true}">
									<input value="Save" id="saveReqWorkFlow"
										onclick="requirementWorkflow.saveRequirementViewWorkFlow('+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+','${requirementWorkFlowStages.stageName}');"
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
<jsp:include page="../masterFooter.jsp" /> 