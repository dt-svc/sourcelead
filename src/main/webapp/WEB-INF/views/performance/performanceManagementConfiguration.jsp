<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<script src="/resources/js/summernote.js?staticTime=${staticTime}"></script>
<link href="/resources/css/summernote.css?staticTime=${staticTime}" rel="stylesheet" />
<script src="/resources/js/editor.js?staticTime=${staticTime}"></script>
<link href="/resources/css/editor.css?staticTime=${staticTime}" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="/resources/js/performance/performanceManagement.js"></script>
<script type="text/javascript" src="/resources/js/performance/performanceFeedbackTemplate.js"></script>
<script src="/resources/js/performanceFeedbackForm-html-generator.js" type="text/javascript" ></script> <!-- form builder -->

<section class="content-wraper contentWraper" id="performanceManagementDiv">
    <section class="content-header clearfix">
	    <div class="pull-left">
	      <!-- <h2>Submission Templates</h2> -->
	      <h2 class="pull-left">Performance Management Configuration</h2>
	    </div>
	    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Performance Management Configuration</div>
	 	<div class="clearfix"></div>
    </section>
    
<div class="clearfix"></div>
   <div class="reportNewDash clearfix">
	  <div class="leaveConfigure newRepGrid clerfixs">
			<div class="col-md-4 perfDashCta">
				<img src="/resources/images/per-manage.svg" alt=""/>
				<h3>Appraisal Cycle</h3>
				<span><a onclick="performanceManagement.addNewAppraisalCycle();" class="colB">Add</a><a onclick="performanceManagement.viewAppraisalCycle();" class="">View</a></span>
			</div>
			<div class="col-md-4 perfDashCta">
				<img src="/resources/images/per-manage.svg" alt=""/>
				<h3>Performance Template</h3>
				<span><a href="#" class="colB">Add</a><a href="#" class="">View</a></span>
			</div>
			<div class="col-md-4 perfDashCta">
				<img src="/resources/images/per-manage.svg" alt=""/>
				<h3>Goals</h3>
				<span><a href="#" class="colB">Add</a><a href="#" class="">View</a></span>
			</div>
			<div class="col-md-4 perfDashCta">
				<img src="/resources/images/per-manage.svg" alt=""/>
				<h3>Feedback</h3>
				<span><a onclick="performanceManagement.addFeedbackTemplate();" class="colB">Add</a><a onclick="performanceManagement.getFeedbackList();" class="">View</a></span>
			</div>
			<div class="col-md-4 perfDashCta">
				<img src="/resources/images/per-manage.svg" alt=""/>
				<h3>Competence</h3>
				<span><a href="#" class="colB">Add</a><a href="#" class="">View</a></span>
			</div>
			<div class="col-md-4 perfDashCta">
				<img src="/resources/images/per-manage.svg" alt=""/>
				<h3>Rewards & Badges</h3>
				<span><a onclick="performanceManagement.addBadgePopup();" class="colB">Add</a><a onclick="performanceManagement.getBadgesList();" class="">View</a></span>
			</div>															
		</div>	
	</div>
    
<jsp:include page="../performance/addNewPerformanceBadge.jsp" />      
</section>


<jsp:include page="../masterFooter.jsp" />