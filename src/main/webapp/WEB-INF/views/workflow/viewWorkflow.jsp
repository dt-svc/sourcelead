<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="wraper"> 
<div id="popUp"></div>
<form id="requirementWorkFlowForm" name="requirementWorkFlowForm">
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
            <input class="repSearchBar" placeholder="Search" id="searchtext" type="text">
            <span class="repSearchIcon" onclick="getViewWorkflowStagesList();"></span>
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
					<ul class="list-inline list-unstyled pull-right mb0">
						<c:if test = "${requirementWorkFlowStages.editable == 'Y'}">
						<li>
							<a title="Edit" href="${SourceLeadWeb_sourceleadUrl}/addStage"><i class="fa fa-pencil"></i></a>
						</li>
						<li>
							<a title="Delete" 
							onclick = 'deletepopup("${requirementWorkFlowStages.uuid}",
							"${requirementWorkFlowStages.stageName}",
							"${requirementWorkFlowStages.requirementWorkFlowUuid}")'>
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
		<div class="footerstyelsNew">
	<div class="clearfix">
		<div class="pull-right">
		<input value="Save" id="saveReqWorkFlow" 
		onclick="saveRequirementWorkFlow('+<c:out value='${requirementWorkFlowStagesDTO[0].requirementWorkFlowUuid}'/>+');"
		 class="btn btn-primary" type="button">
		<input value="Cancel" class="btn btn-default ml" type="button" onclick = "cancelViewWorkflow();">
		</div>
	</div>
	</div>
	</section>
	</form>
    </div>
  </section>
</div>
<script>

function cancelViewWorkflow(){
	window.location.href = "${SourceLeadWeb_sourceleadUrl}/workflowList";
}


function saveRequirementWorkFlow(workflowUuid){
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
      var validationSettings = {
           errorMessagePosition: 'element',
           borderColorOnError: '',
           scrollToTopOnError: true,
           dateFormat: 'yyyy/mm/dd'
       };
     
	  if ($('#requirementWorkFlowForm').validate(false, validationSettings)) {
		  var formData = getFormData("requirementWorkFlowForm");
		   $("#saveReqWorkFlow").attr("disabled", "disabled");
           $("#saveReqWorkFlow").addClass('button').val('Processing..'); 
	 $.ajax({
           type: "POST",
           data : formData,
           url:sourceleadUrl+"/saveWorkFlowDetails?uuid="+workflowUuid,
           success: function (result) {
				if (result.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (result.response === "SUCCESS") {
	        		if(result.object === "DataMissing") {
						$("#saveReqWorkFlow").removeAttr("disabled");
						$("#saveReqWorkFlow").removeAttr('button').val('Save');	
						showError("Invalid data");
					}else{
						
						location.replace(sourceleadUrl+"/workflowList");	
						
					}
	        	} else if (result.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
           },
       });
     } 
}

function deletepopup(stageUuid, stageName, workflowUuid){
	var result = "";
	$("#popUp").empty();
	result = result +" <div class='project_inn_mask'></div>"; 
	result = result +" <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +" <p>Do you want to delete "+stageName+"?</p>";
	result = result +' <b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="deleteRequirementWorkflowStage(\''+stageUuid+'\', \''+workflowUuid+'\', \''+stageName+'\');"><input class="u_can_color" type="button" value="Cancel" onClick="cancelTermination();"></b>';
	result = result +" </div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	}
	</script>
	<script>
	function getViewWorkflowStagesList() {
		var filter = $("#searchtext").val();
		$("li.widgetDiv").each(function() {
			if ($(this).text().search(new RegExp(filter, "i")) < 0) {
				$(this).hide();
			} else {
				$(this).show();
			}
		});
	}

	$("#searchtext").keyup(function(e) {
		if (e.which == 13) {
			getViewWorkflowStagesList();

		}
	});
	</script>
	<script>
	function deleteRequirementWorkflowStage(stageUuid, workflowUuid, stageName){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/deleteRequirementWorkflowStage?uuid="+stageUuid+"&requirementWorkFlowUuid="+workflowUuid,
			success : function(dataList){
				if(dataList == "Success"){
					$("ul#stageList li:contains('"+stageName+"')").remove();
					 $("#popUp").empty();
				     $(".project_inn_mask").fadeOut(200);
				}else{
					 showError("Operation failed, please try again");
				}
				}
			});
			
	}
	
	function cancelTermination(){
		 $("#popUp").empty();
	     $(".project_inn_mask").fadeOut(200);
	}

</script>
<jsp:include page="../masterFooter.jsp" /> 