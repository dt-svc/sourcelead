<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/summernote.js?staticTime=${staticTime}"></script>
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/summernote.css?staticTime=${staticTime}" rel="stylesheet" />
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/editor.js?staticTime=${staticTime}"></script>
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/editor.css?staticTime=${staticTime}" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/performance/performanceManagement.js"></script>
<script type="text/javascript" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/performance/performanceFeedbackTemplate.js"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/performanceFeedbackFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/performanceFeedbackForm-html-generator.js" type="text/javascript" ></script> <!-- form builder -->

<script>
var locationDateFormat = '${SourceLeadWeb_dateFormat}'; 
</script>

<section class="content-wraper contentWraper" id="performanceManagementDiv">
    <section class="content-header clearfix">
	    <div class="pull-left">
	      <!-- <h2>Submission Templates</h2> -->
	      <h2 class="pull-left">Performance Management Configuration</h2>
	    </div>
	    <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
	    <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/settings'/>">Settings</a> &raquo;
	    Performance Management Configuration</div>
	 	<div class="clearfix"></div>
    </section>
    
<div class="clearfix"></div>
   
   
   <div class="reportNewDash clearfix">
		  <div class="leaveConfigure newRepGrid clerfixs">
				<div class="col-md-3 perfDashCta">
					<img src="/resources/images/appraisalCycle.svg" alt=""/>
					<h3>Appraisal Cycle</h3>
					<span><a onclick="performanceManagement.addNewAppraisalCycle();" class="colB">Add</a><a onclick="performanceManagement.viewAppraisalCycle();" class="">View</a></span>
				</div>
				<div class="col-md-3 perfDashCta">
					<img src="/resources/images/feedBack.svg" alt=""/>
					<h3>Feedback</h3>
					<span><a onclick="performanceManagement.addFeedbackTemplate();" class="colB">Add</a><a onclick="performanceManagement.getFeedbackList();" class="">View</a></span>
				</div>
				<div class="col-md-3 perfDashCta">
					<img src="/resources/images/competenceA.svg" alt=""/>
					<h3>Competency</h3>
					<span><a onclick="performanceManagement.addNewCompetence();" class="colB">Add</a><a onclick="performanceManagement.performanceCompetenceList();" class="">View</a></span>
				</div>
				<div class="col-md-3 perfDashCta">
					<img src="/resources/images/award.svg" alt=""/>
					<h3>Rewards &amp; Badges</h3>
					<span>
						<span><a onclick="performanceManagement.addBadgePopup();" class="colB">Add</a><a onclick="performanceManagement.getBadgesList();" class="">View</a></span>
					</span>
				</div>	
				      <div class="col-md-3 perfDashCta">
					<img src="/resources/images/rating-scale.svg" alt=""/>
					<h3>Rating Scale</h3>
					<span><a onclick="performanceManagement.addRatingScaleLevels();" class="colB">Add</a><a onclick="performanceManagement.performanceRatingScaleList();" class="">View</a></span>
				</div>
				  
	    	</div>
	</div>
    
<jsp:include page="../performance/addNewPerformanceBadge.jsp" />   
<jsp:include page="../performance/addNewPerformanceCompetence.jsp" />   
<jsp:include page="../performance/addNewRatingScaleLevels.jsp" /> 

  
</section>


<jsp:include page="../masterFooter.jsp" />