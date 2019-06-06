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
      <div class="pull-left">
        <h2>Release Job</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href='${appPath}/jobs'> Jobs List  </a> &raquo; Release Job</div>
      <div class="success_pop" id="selector" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
    </section>
    <jsp:include page="releaseJobToVendorOrCrowdsourcing.jsp" />
  </section>
<!-- Content Area Starts -->



<jsp:include page="../masterFooter.jsp" />
