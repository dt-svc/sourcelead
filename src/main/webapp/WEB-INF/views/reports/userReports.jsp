<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

<style>
#billing_chartdiv {
  	width:100%; height:300px;
}																		
</style>

<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Reports Dashboard</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Reports Dashboard</div>
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
                      <small class="text-muted">All Recruiters Reports</small>
                      <div class="clearfix mt10">
                           <a class="btn btn-primary btn-sm" title="View" href="<c:url value="/recruitersReports" />">View</a>
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
                      <small class="text-muted">All Vendors Reports</small>
                      <div class="clearfix mt10">
                       <a class="btn btn-primary btn-sm" title="View" href="<c:url value="/vendorsReports" />">View</a>

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
                      <h4 class="text-primary">Billing</h4>
                      <small class="text-muted">All Billing Reports</small>
                      <div class="clearfix mt10">
                       <a class="btn btn-primary btn-sm" title="View" href="<c:url value="/billingReports" />">View</a>
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
                      <small class="text-muted">All Job Posted Reports</small>
                      <div class="clearfix mt10">
                           <a class="btn btn-primary btn-sm" title="View" href="<c:url value="/requirementListForReports" />">View</a>
                      </div>
                  </div>
                      </div>
              <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-handshake-o fa-2x text-warning"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-warning">Jobs Stages</h4>
                      <small class="text-muted">All Job Stages Reports</small>
                      <div class="clearfix mt10">
                           <a class="btn btn-primary btn-sm" title="View" href="<c:url value="/requirementStagesListForReports" />">View</a>
                      </div>
                      </div>
                  </div>
                       <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-address-book-o fa-2x text-success"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-success">Applicants Received</h4>
                      <small class="text-muted">All Received Applicants Reports</small>
                      <div class="clearfix mt10">
                           <a class="btn btn-primary btn-sm" title="View" href="<c:url value="/applicantsReceivedListForReports" />">View</a>
                      </div>
                      </div>
                  </div>
                      <div class="col-md-3  mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-calculator fa-2x text-muted"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-muted">Expenses</h4>
                      <small class="text-muted">All Expenses Reports</small>
                      <div class="clearfix mt10">
                           <a class="btn btn-primary btn-sm" title="View" href="<c:url value="/expensesReportsList" />">View</a>
                      </div>
                  </div>
                      </div>
                      <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-clock-o fa-2x text-info"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-info">TimeSheet</h4>
                      <small class="text-muted">All TimeSheet Reports</small>
                      <div class="clearfix mt10">
                       <a class="btn btn-primary btn-sm" title="View" href="<c:url value="/timeSheetReports" />">View</a>

                      </div>
                  </div>
                      </div>
                      
                      <div class="col-md-3 mb">
                  <div class="pull-left mr10">
                      <i class="fa fa-group fa-2x text-info"></i>
                  </div>
                  <div class="pull-left">
                      <h4 class="text-info">Added Candidates</h4>
                      <small class="text-muted">All Added Candidates Reports</small>
                      <div class="clearfix mt10">
                      <a class="btn btn-primary btn-sm" title="View" href="<c:url value="/candidateListForReports" />">View</a>

                      </div>
                  </div>
                      </div>                      
      </div>
        </div>
      <!-- Content Area Ends--> 
      </div>
      </section>


<jsp:include page="../masterFooter.jsp" />