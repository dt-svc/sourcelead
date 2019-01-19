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
