<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Content Area Starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
    	<div class="pull-left">
	      <c:if test="${empty requirementId}"><h2>Add Job</h2></c:if>
	      <c:if test="${!empty requirementId}"><h2>Update Job</h2></c:if>
	    </div>
    	<c:if test="${!empty msg}">
			<div class="success_pop" id="selectorReleaseMsg" style="display: block;">
				<i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
		</c:if>
      
      <div class="success_pop" id="selector" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
     <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a>&raquo;<a href="${SourceLeadWeb_atsUrl}/jobsList"> Jobs List  </a>&raquo; <c:if test="${empty requirementId}">Add Job</c:if> <c:if test="${!empty requirementId}">Update Job</c:if> </div>
    </section>
    
    <div class="newPrDetails newPrDetailsPMO"> 
    <div class="newPrTabs">
    <ul class="newPrTabsMenu">
				<li><a href="${SourceLeadWeb_atsUrl}/requirement?requirementUUID=${requirementUUID}">Job Details</a></li>
				<li class="current"><a href="${SourceLeadWeb_atsUrl}/jobReleaseManagement?requirementUUID=${requirementUUID}">Job Release</a></li>
				<li><a href="${SourceLeadWeb_atsUrl}/jobRecommendations?requirementUUID=${requirementUUID}">Recommendations</a></li>
				<div class="clearfix mb"></div>
			  </ul>
	   <div class="clearfix"></div>
    </div>
    </div>
    <jsp:include page="releaseJobToVendorOrCrowdsourcing.jsp" />
  </section>
<!-- Content Area Starts -->



<jsp:include page="../masterFooter.jsp" />

