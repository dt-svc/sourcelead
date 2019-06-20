<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="wraper"> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Workflow Dashboard</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a>&raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/settings">Settings </a>&raquo; Workflow Dashboard
      </div>
    </section>
          <section class="landTemp reportNewDash">
              <div class="landTempCta nomr">
                  <div class="pull-left mr10">
                           <i class="fa fa-briefcase fa-2x text-muted"></i>
                        </div>
                    <div class="pull-left">
                    <h2 class="text-muted">Job</h2>
                    <p>Manage Your Job Workflow</p>
                    <span>
                     <sec:authorize access="hasRole('ROLE_ADD_WORKFLOW')"> 
                      <a class="btn btn-info btn-sm mr10" href="${SourceLeadWeb_sourceleadUrl}/defaultWorkflow">Add</a> 
                      </sec:authorize>
                      <sec:authorize access="hasRole('ROLE_VIEW_MY_WORKFLOW')"> 
                      <a class="btn btn-primary btn-sm" href="${SourceLeadWeb_sourceleadUrl}/workflowList">View</a>
                      </sec:authorize>
                    </span>
                    </div>
                  </div>
          </section>
    </div>
  </section>
</div>
<jsp:include page="../masterFooter.jsp" /> 	