<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
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
		
       <div class="positionRelative reportHead clearfix mb">
        <ul class="pull-right list-unstyled list-inline">
          <li><a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/addStage" title="Add">+ Add</a></li>
        <li class="">
            <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
            <span class="repSearchIcon" onclick="getDynamciUserList();"></span>
		</li>
        </ul>
        <div class="clearfix"></div>
      </div>
    </div>
    
    <form id="requirementWorkFlowForm" name="requirementWorkFlowForm">
<div class="clearfix backgroundWhite mb">
  <div class="row">
      <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Name</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="workFlowName" name="workFlowName">
            </div>
          </div>
        </div>
        <div class="clearfix mt col-md-6">
          <div class="form-group clearfix">
            <label class="col-md-3">Description</label>
            <div class="col-md-9">
              <textarea rows="2" class="form-control" name="description" id="description" ></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
    

      <div class="clearfix"></div>
      
      <!-- dynamic stages content starts here-->
      
      <div id = "dynamicWorkflow">
		
		<%--
		<ul class="list-unstyled widgetDivLi">
		
		 <li class="widgetDiv mb clearfix">
		<!-- Cardmodel widget -->
			<div class="row">
				<div class="col-md-10">
					<span class="submissionShare pull-left"></span><h4 class="pull-left mt10 ml">Submission</h4>
				</div>
				<div class="col-md-2">
					<ul class="list-inline list-unstyled pull-right mb0">
						<li>
							<a title="Edit" href="${SourceLeadWeb_sourceleadUrl}/addStage"><i class="fa fa-pencil"></i></a>
						</li>
						<li>
							<a title="Delete" href="addWorkflow.html"><i class="fa fa-trash"></i></a>
						</li>
					</ul>
			</div>
		</div>
		<!-- Cardmdoel Widget ends -->
		</li>
		
		 <li class="widgetDiv mb clearfix">
			<div class="row">
				<div class="col-md-10">
					<span class="shortlilstedIC pull-left"></span><h4 class="pull-left mt10 ml">Short List</h4>
				</div>
				<div class="col-md-2">
					<ul class="list-inline list-unstyled pull-right mb0 mt10">
						<li>
							<a title="Edit" href="${SourceLeadWeb_sourceleadUrl}/addStage"><i class="fa fa-pencil"></i></a>
						</li>
						<li>
							<a title="Delete" href="addWorkflow.html"><i class="fa fa-trash"></i></a>
						</li>
					</ul>				</div>
			</div>
		</li>
		<li class="widgetDiv mb clearfix">
			<div class="row">
				<div class="col-md-10">
				<span class="shortlilstedIC pull-left"></span><h4 class="pull-left mt10 ml">Submit to Client</h4>
				</div>
				<div class="col-md-2">
					<ul class="list-inline list-unstyled pull-right mb0 mt10">
						<li>
							<a title="Edit" href="${SourceLeadWeb_sourceleadUrl}/addStage"><i class="fa fa-pencil"></i></a>
						</li>
						<li>
							<a title="Delete" href="addWorkflow.html"><i class="fa fa-trash"></i></a>
						</li>
					</ul>				</div>
			</div>
		</li>
		<li class="widgetDiv mb clearfix">
			<div class="row">
				<div class="col-md-10">
				<span class="invterviewIC pull-left"></span><h4 class="pull-left mt10 ml">Interview</h4>
				</div>
				<div class="col-md-2">
					<ul class="list-inline list-unstyled pull-right mb0">
						<li>
							<a title="Edit" href="${SourceLeadWeb_sourceleadUrl}/addStage"><i class="fa fa-pencil"></i></a>
						</li>
						<li>
							<a title="Delete" href="addWorkflow.html"><i class="fa fa-trash"></i></a>
						</li>
					</ul>				</div>
			</div>
		</li>
		<li class="widgetDiv mb clearfix">
			<div class="row">
				<div class="col-md-10">
				<span class="jobOfferIC pull-left"></span><h4 class="pull-left mt10 ml">Offer</h4>
				</div>
				<div class="col-md-2">
					<ul class="list-inline list-unstyled pull-right mb0">
						<li>
							<a title="Edit" href="${SourceLeadWeb_sourceleadUrl}/addStage"><i class="fa fa-pencil"></i></a>
						</li>
						<li>
							<a title="Delete" href="addWorkflow.html"><i class="fa fa-trash"></i></a>
						</li>
					</ul>
				</div>
			</div>
		</li>
		<li class="widgetDiv mb clearfix">
			<div class="row">
				<div class="col-md-10">
				<span class="joinedIC pull-left"></span><h4 class="pull-left mt10 ml">Joined</h4>
				</div>
				<div class="col-md-2">
					<ul class="list-inline list-unstyled pull-right mb0">
						<li>
							<a title="Edit" href="${SourceLeadWeb_sourceleadUrl}/addStage"><i class="fa fa-pencil"></i></a>
						</li>
						<li>
							<a title="Delete" href="addWorkflow.html"><i class="fa fa-trash"></i></a>
						</li>
					</ul>
				</div>
			</div>
		</li> 
		
		
		
		</ul> --%>
		</div>
		
		 <!-- dynamic stages content ends here-->
		
		
		<div class="footerstyelsNew">
	<div class="clearfix">
		<div class="pull-right">
		 <input type="button" value="Save" id="saveReqWorkFlow" onclick="saveRequirementWorkFlow();" class="btn btn-primary">
		<input value="Cancel" class="btn btn-default ml" type="button">
		</div>
	</div>
	</div>
	</form>
    </div>
  </section>
</div>
<script>

$(document).ready(function() {
	getDefaultWorkflow();
	
});
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
<script>

	function getDefaultWorkflow() {
		var formData = getFormData("requirementWorkFlowForm");
		$.ajax({
			type : "GET",
			data : formData,
			url : sourceleadUrl + "/createRequirementDefaultWorkFlow",
			success : function(dataList) {
				console.log(dataList);
				var result = "";
				if(dataList != null && dataList.length > 0){
					 result = result + "<ul class='list-unstyled widgetDivLi'>"; 
					for(var i = 0 ; i < dataList.length; i++){
					result = result + "<li class='widgetDiv mb clearfix'>";
					result = result + "<div class='row'>";
					result = result + "<div class='col-md-10'>";
					result = result + "<span class='submissionShare pull-left'></span><h4 class='pull-left mt10 ml'>"+dataList[i].stageName+"</h4>";
					result = result + "</div>";
					result = result + "<div class='col-md-2'>";
					result = result + "<ul class='list-inline list-unstyled pull-right mb0'>";
					result = result + "<li>";
					result = result + "<a title='Edit' href='${SourceLeadWeb_sourceleadUrl}/addStage'><i class='fa fa-pencil'></i></a>";
					result = result + "</li>";
					result = result + "<li>";
					result = result + "<a title='Delete' href='addWorkflow.html'><i class='fa fa-trash'></i></a>";
					result = result + "</li></ul></div></div></li>";
					}
					 result = result + "</ul>"; 
				}
				$("#dynamicWorkflow").empty().append(result);
			},
		});

	}
	
</script>
<script type="text/javascript">
function saveRequirementWorkFlow(){
	 
      var validationSettings = {
           errorMessagePosition: 'element',
           borderColorOnError: '',
           scrollToTopOnError: true,
           dateFormat: 'yyyy/mm/dd'
       };
      
	/*  if ($('#requirementWorkFlowForm').validate(false, validationSettings)) { */
		  var formData = getFormData("requirementWorkFlowForm");
		 /*  $("#saveReqWorkFlow").attr("disabled", "disabled");
         $("#saveReqWorkFlow").addClass('button').val('Processing..'); */
	 $.ajax({
           type: "POST",
           data : formData,
           url:sourceleadUrl+"/saveWorkFlowDetailsPage",
           success: function (data) {
           alert("success")
           },
       });
    /*  } */
}
</script>
<jsp:include page="../masterFooter.jsp" /> 	