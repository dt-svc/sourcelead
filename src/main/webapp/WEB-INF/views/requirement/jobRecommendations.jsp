<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- Content Area Starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
    	<c:if test="${!empty msg}">
			<div class="success_pop" id="selectorReleaseMsg" style="display: block;">
				<i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
		</c:if>
      
      <div class="success_pop" id="selector" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
    </section>
    
    <div class="newPrDetails newPrDetailsPMO"> 
    <div class="newPrTabs">
    <ul class="newPrTabsMenu">
				<li><a href="<c:url value='${appPath}/requirement?requirementUUID=${requirementUUID}'/>">Job Details</a></li>
				<li><a href="<c:url value='${appPath}/jobReleaseManagement?requirementUUID=${requirementUUID}'/>">Job Release</a></li>
				<li class="current"><a href="<c:url value='${appPath}/jobRecommendations?requirementUUID=${requirementUUID}'/>">Recommendations</a></li>
				<div class="clearfix mb"></div>
			  </ul>
	   <div class="clearfix"></div>
    </div>
    </div>
    <div class="content-area clearfix">
    <jsp:include page="jobRecommendationCandidtesListTable.jsp"></jsp:include>
    
    <div class="form-footer clearfix row">
              <div class="col-lg-12">
                <div class="pull-right">
                  <input type="button" value="Go to list"  class="btn btn-primary ml" onClick="javascript:window.location.href = '<c:url value='${appPath}/jobs'/>'"  class="btn btn-default ml">
                </div>
              </div>
    </div>
    
    </div>
    
  </section>
<!-- Content Area Starts -->



<jsp:include page="../masterFooter.jsp" />