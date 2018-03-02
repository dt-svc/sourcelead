<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
.reportsCount.req_Crowd ul li {
    width: 25% !important;
}
</style>

<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Requested Companies</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Requested Companies </div>
           <div class="clearfix"></div> 
		      <div class="reportsCount req_Crowd clearfix">
		          <ul class="clearfix">
		          <li id="crowd"><label class="mr10"><span><i class="fa fa-registered"></i></span><b id="crowdActive"><input class="myCheckbox valid" type="radio"  name="sourceleadOrgType" id="sourceleadOrg" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/enrolledOrgsList'/>'" >&nbsp;Registered Companies</label></b></li>
		          <li id="crowd"><label class="mr10"><span><i class="fa fa-cubes"></i></span><b id="savedActive"><input class="myCheckbox valid" type="radio" checked name="sourceleadOrgType" id="sourceleadOrg" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/requestedOrgs'/>'" >&nbsp;Requested Companies</label></b></li>
		          <li id="crowd"><label class="mr10"><span><i class="fa fa-laptop"></i></span><b id="savedActive"><input class="myCheckbox valid" type="radio" name="sourceleadOrgType" id="sourceleadOrg" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/demoRequests'/>'">&nbsp;Demo Requests</label></b></li>
		          </ul>
		      </div> 
        </section>
     
     <div class="clearfix"></div>     
        <div class="content-area clearfix">
        
        </div>
 </section>

<jsp:include page="../masterFooter.jsp" />