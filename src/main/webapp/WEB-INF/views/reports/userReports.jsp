<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<script src="/resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Reports Dashboard</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Reports Dashboard</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content mt mb col-md-12">
          <div class="leaveConfigure clerfix">
             <c:if test="${requirementTabAccess == true }">
          
             <div class="col-md-3 mb">
                  <div class="pull-left mr10 positionRelative">
                      <i class="fa fa-search fa-2x text-info"></i>
                      <i class="fa fa-user-circle search-usericon text-info"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-info">Recruiters</h4>
                      <small class="text-muted">All Recruiters Report</small>
                      <div class="clearfix mt10">
                           <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/recruitersReports" />">View</a>
                      </div>
                  </div>
                  
                  </div>
                  </c:if>
                  <c:if test="${vendorTabAccess == true }">
              <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-suitcase fa-2x text-muted"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-muted">Vendors</h4>
                      <small class="text-muted">All Vendors Report</small>
                      <div class="clearfix mt10">
                       <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/vendorsReports" />">View</a>

                      </div>
                  </div>
                  </div>
                  </c:if>
                 <%--  <c:if test="${billingTabAccess == true }"> --%>
               <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-money fa-2x text-primary"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-primary">Finance</h4>
                      <small class="text-muted">Finance Report</small>
                      <div class="clearfix mt10">
                       <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/billingReports" />">View</a>
                      </div>
                  </div>
                  </div>
             <%--  </c:if> --%>
                  <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-briefcase fa-2x text-danger"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-danger">Jobs Posted</h4>
                      <small class="text-muted">All Jobs Posted Report</small>
                      <div class="clearfix mt10">
                           <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/requirementListForReports" />">View</a>
                      </div>
                  </div>
                      </div>
              <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-handshake-o fa-2x text-warning"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-warning">Jobs Stages</h4>
                      <small class="text-muted">All Job Stages Report</small>
                      <div class="clearfix mt10">
                           <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/requirementStagesListForReports" />">View</a>
                      </div>
                      </div>
                  </div>
                       <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-address-book-o fa-2x text-success"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-success">Applicants Received</h4>
                      <small class="text-muted">All Received Applicants Report</small>
                      <div class="clearfix mt10">
                           <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/applicantsReceivedListForReports" />">View</a>
                      </div>
                      </div>
                  </div>
                      <div class="col-md-3  mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-calculator fa-2x text-muted"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-muted">Expenses</h4>
                      <small class="text-muted">All Expenses Report</small>
                      <div class="clearfix mt10">
                           <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/expensesReportsList" />">View</a>
                      </div>
                  </div>
                      </div>
                      <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-clock-o fa-2x text-info"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-info">TimeSheet</h4>
                      <small class="text-muted">All TimeSheets Report</small>
                      <div class="clearfix mt10">
                       <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/timeSheetReports" />">View</a>

                      </div>
                  </div>
                      </div>
                      
                      <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-group fa-2x text-info"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-info">Candidates Added</h4>
                      <small class="text-muted">All Candidates Report</small>
                      <div class="clearfix mt10">
                      <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/candidateListForReports" />">View</a>

                      </div>
                  </div>
                      </div>  
                      
                      <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-usd fa-2x text-info"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-info">Employee ROI</h4>
                      <small class="text-muted">Each Employee ROI Reports</small>
                      <div class="clearfix mt10">
                      <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/payrollReport" />">View</a>

                      </div>
                  </div>
                      </div>   
                      
                       <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-usd fa-2x text-info"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-info">All Employee ROI</h4>
                      <small class="text-muted">All Employees ROI Reports</small>
                      <div class="clearfix mt10">
                      <a class="btn btn-primary btn-sm" title="View" href="<c:url value="${appPath}/payrollAllBalanceReport" />">View</a>

                      </div>
                  </div>
                      </div>   
                      
                      <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-bullhorn fa-2x text-info"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-info">Campaign Reports</h4>
                      <small class="text-muted">All Campaign Stats</small>
                      <div class="clearfix mt10">
                      <a class="btn btn-primary btn-sm" title="View" href="<c:url value="campaignStats" />">View</a>

                      </div>
                  </div>
                      </div>                   
      </div>
        </div>
      <!-- Content Area Ends--> 
      </div>
      </section>


<jsp:include page="../masterFooter.jsp" />