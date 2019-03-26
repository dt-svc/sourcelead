<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<div class="wraper"> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Default Workflow</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a>&raquo;  
      <a href="${SourceLeadWeb_sourceleadUrl}/workflowList">Application Workflow</a> &raquo;
      Default Workflow</div>
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
  <div class="clearfix"></div>
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
		</ul>
		<div class="footerstyelsNew">
	<div class="clearfix">
		<div class="pull-right">
		<input value="Save" class="btn btn-primary" type="button">
		<input value="Cancel" class="btn btn-default ml" type="button">
		</div>
	</div>
	</div>
    </div>
  </section>
</div>
<jsp:include page="../masterFooter.jsp" /> 