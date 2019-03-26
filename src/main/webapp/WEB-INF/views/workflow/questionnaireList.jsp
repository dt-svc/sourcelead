<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
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
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div class="require_wrap_table clearfix">
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Created By</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
					  <div class="tbldata-dotdot-me" title="Rajkumar Santhoshi Konidala"><a href="${SourceLeadWeb_sourceleadUrl}/questionnaire-view.html">Sample</a></div>
					</td>
                <td>
					<div class="tbldata-dotdot-verybig" title="Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.">
						  Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.
					</div>
				</td>
                  <td><div class="tbldata-dotdot-me" title="Murali Krishnamurthy Avanapu">Murali Krishnamurthy Avanapu</div></td>
                  <td>
                  <div class="width90px">
                  	<ul class="list-inline list-unstyled">
                  		<li><a href="editworkflow.html"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
                  		<li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
                  	</ul>	
                  </div>
                  </td>
                </tr>
				  <tr>
          <td>
					  <div class="tbldata-dotdot-me"><a href="questionnaire-view.html">Sample2</a></div>
					</td>
                <td>
					<div class="tbldata-dotdot-verybig" title="Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.">
						  Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.
					</div>
				</td>
                  <td><div class="tbldata-dotdot-me" title="Murali Krishnamurthy Avanapu">Murali Krishnamurthy Avanapu</div></td>
                  <td>
                  <div class="width90px">
                  	<ul class="list-inline list-unstyled">
                  		<li><a href="editworkflow.html"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
                  		<li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
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