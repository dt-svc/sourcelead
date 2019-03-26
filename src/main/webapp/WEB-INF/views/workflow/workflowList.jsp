<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
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
                    <a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/defaultWorkflow" title="Add">+ Add</a>	
                    </li>
            
              <li>
				<a class="newAdBtn mr5" href="${SourceLeadWeb_sourceleadUrl}/questionnaireList" title="Add">+ Questionnaire</a>	
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div class="require_wrap_table clearfix">
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Stages</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="tbldata-dotdot-verybig"><a href="${SourceLeadWeb_sourceleadUrl}/viewWorkflow" title="view">Default job workflow</a></div></td>
                  <td><div class="tbldata-dotdot-vsm">8</div></td>
                  <td><div class="tbldata-dotdot-vsm">Active</div></td>
                  <td>
                  <div class="width215px">
                  	<ul class="list-inline list-unstyled">
                  		<li>
							<label class="switch mr positionRelativeTop10" title="Default">
								<input checked="" type="checkbox" name="">
								<div class="slider round"></div>
							</label>
                  		</li>
                  		<li><a href="${SourceLeadWeb_sourceleadUrl}/defaultWorkflow"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
                  		<li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
                  		<li><i class="fa fa fa-clone cursorPointer text-primary" title="Clone"></i></li>
                  	</ul>	
                  </div>
                  </td>
                </tr>
                 <tr>
                  <td><div class="tbldata-dotdot-verybig"><a href="${SourceLeadWeb_sourceleadUrl}/viewWorkflow" title="view">Executive job workflow</a></div></td>
                  <td><div class="tbldata-dotdot-vsm">8</div></td>
                  <td><div class="tbldata-dotdot-vsm">In-Active</div></td>
                  <td>
                  <div class="width215px">
                  	<ul class="list-inline list-unstyled">
                  		<li>
							<label class="switch mr positionRelativeTop10" title="Default">
								<input type="checkbox" name="">
								<div class="slider round"></div>
							</label>
                  		</li>
                  		<li><a href="${SourceLeadWeb_sourceleadUrl}/defaultWorkflow"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
                  		<li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
                  		<li><i class="fa fa fa-clone cursorPointer text-primary" title="Clone"></i></li>
                  	</ul>	
                  </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <nav>
            <ul id="paging" class="pagination">
              <li><a href="#">Previous</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">3</a></li>
              <li><a href="#">4</a></li>
              <li><a href="#">5</a></li>
              <li class="disabled"><a href="#">6</a></li>
              <li data-page="last"><a href="#">Next</a></li>
            </ul>
            <div class="clearfix"></div>
          </nav>
        </div>
      </div>
    </div>
  </section>
</div>
<jsp:include page="../masterFooter.jsp" /> 	