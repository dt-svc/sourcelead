<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link type="text/css" rel="stylesheet" href="/resources/css/comment/froala_editor.css"/>
<link type="text/css" rel="stylesheet" href="/resources/css/comment/jquery.atwho.css"/>

<script type="text/javascript" src="/resources/js/editor.js"></script>
<link href="/resources/css/editor.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="/resources/js/comment/jquery.atwho.js"></script>
<script type="text/javascript" src="/resources/js/comment/jquery.caret.min.js"></script>


<script>
var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';	
var goalId = '${goalId}';
window.onload = function () {
	var goalUUID = '${goalUUID}';
	performanceGoals.getPerformanceGoalsSummaryDetails(goalUUID);
	performanceGoals.getPerformanceSubGoalsList(goalUUID);
	performanceGoals.getPerformanceGoalsAssignToList(goalUUID);
	performanceGoals.goalsCommentsTableList();
 };
 
</script>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Goal Summary</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}"> Home</a>
			&raquo;<a href="${SourceLeadWeb_sourceleadUrl}/performanceGoalsList"> Self Goals List</a>
			&raquo;<a href="${SourceLeadWeb_sourceleadUrl}/performanceTeamGoalsList"> Team Goals List</a>
			&raquo; Goal Summary
		</div>
	</section>
	 <div id="goalsDetailsLoading"></div>
	<div class="goalssummar_widget">
				<div class="clearfix col-md-3 pull-right">
				    <span id="markAsComplete"></span>
				    <span id="goaledit"></span>
				    <span id="goalDelete"></span>
				</div>
				
				<div class="clearfix">
					<div class='col-md-5'>
						<a href="javascript:void(0)">
							<h4 class="media-heading" id="goalName"></h4>
						</a>
						<p class="text-muted mb0 fontSmall"><span class="paddingRight">Created Date (<small class="custom-small" id="createdDateFormat"></small>) :&nbsp;&nbsp;<span id="createdDate" class="textBold"></span></span></p>
					</div>
					<div class='col-md-7'>
						<div class="row">
							<div class="col-md-7">
								<div class="clearfix">
									<div class="progress mb0 progressGoals">
										<span id="progressBar"></span>
									</div>
									<p class="mb0 fontSmall mt5" id="goalPercentage">Complete (info)</p>
								</div>
							</div>
							<div class="clearfix mt mb"></div>
							<div class="col-md-8 mt">
								<div class="mb0"><span class="mr10 text-muted">Start Date (<small class="custom-small" id="startDateFormat"></small>) :</span><span
										class="textBold" id="goalStartDate"></span></div>
							</div>
							<div class="col-md-8 mt">
								<div class="mb0"><span class="mr10 text-muted ">End Date (<small class="custom-small" id="endDateFormat"></small>) :</span><span
										class="textBold" id="goalEndDate"></span></div>
							</div>

						</div>
					</div>
				</div>
				<div class="clearfix"></div>
	</div>
	
	
	
	<div class="goalssummar_widget mt">

				<div class="clearfix">

					<div class="clearfix mb5">
						<div class="col-md-9">
							<h4 class="media-heading text-muted">Sub Goals</h4>
						</div>
						<div class="clearfix"></div>
					</div>

					<div id="subGoalsList"></div>
					
				</div>
	 </div>
	 
	 <div class="goalssummar_widget mt">
				<div class="clearfix">
					<div class="clearfix mb5">
						<div class="col-md-12">
							<h4 class="media-heading text-muted">Assign to</h4>
						</div>
						<div class="clearfix"></div>
					</div>
					<div id="goalsAssignToList"></div>
				</div>
	 </div>
	 
	 <div class="goalssummar_widget mt">
						<div class="clearfix mt mb">
						  <div class="clearfix mr">
						    <button type="button" class="btn btn-primary btn-sm pull-right" onclick="performanceGoals.addGoalsCommentsPopUp()">Add Comment</button>
						  </div>
						</div>
						<!-- TimeEntry Modal Pop -->
						<div class="modal-Time commentPopUp clearfix"> 
						<div id="commnetPopUpDivId"></div>						  
						</div>
						
						<div class="clearfix project_wrap_table taskComments projecttask-div">
						<div id="goalsCommentsListDivId"></div>
						</div>
						
	 </div>
			
</section>

<script type="text/javascript" src="/resources/js/performance/performanceGoals.js"></script>

<jsp:include page="../masterFooter.jsp" />