<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
.share a .fa-linkedin-square {
    color: #1686b0;
}
.share a .fa-twitter-square {
    color: #1cb7eb;
}
.share a .fa-facebook-square {
    color: #4e71a8;
}
.text-center {
    text-align: center !important;
}
.paddingLeft {position: relative;}
.paddingLeft .fa-clipboard {
font-size: 16px; position: absolute; border: 1px solid #ccc; background-color: #eee; text-align:center;
box-shadow: inset 0 1px 1px rgba(0,0,0,.075); color: #555; height: 30px; width: 30px; top:0; right:-20px;
border-radius:3px; cursor: pointer; padding:6px 0px 0px 0px;
}
</style>

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
     <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a>&raquo;<a href="<c:url value='${appPath}/jobs'/>"> Jobs List  </a>&raquo; <c:if test="${empty requirementId}">Add Job</c:if> <c:if test="${!empty requirementId}">Update Job</c:if> </div>
    </section>
    
    <div class="newPrDetails newPrDetailsPMO"> 
    <div class="newPrTabs">
    <ul class="newPrTabsMenu">
				<li><a href="<c:url value='${appPath}/requirement?requirementUUID=${requirementUUID}'/>">Job Details</a></li>
				<li class="current"><a href="<c:url value='${appPath}/jobReleaseManagement?requirementUUID=${requirementUUID}'/>">Job Release</a></li>
				<li><a href="<c:url value='${appPath}/jobRecommendations?requirementUUID=${requirementUUID}'/>">Recommendations</a></li>
				<div class="clearfix mb"></div>
			  </ul>
	   <div class="clearfix"></div>
    </div>
    </div>
    <jsp:include page="releaseJobToVendorOrCrowdsourcing.jsp" />
  </section>
<!-- Content Area Starts -->



<jsp:include page="../masterFooter.jsp" />

