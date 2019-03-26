<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Goals</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo; Goals
		</div>
	</section>
	
	 <div class="project_wrap">
    <div class="perfTabs">
    	<ul class="clearfix">
    		<li><a href="goals.html" class="Act">My Goals</a></li>
    		<li><a href="teamGoals.html">Team Goals</a></li>
    		<li><a href="departmentGoals.html">Department Goals</a></li>
    	</ul>
    </div>  
    
    <div class="goalsDash clearfix">
    	<div class="row clearfix">
    		<div class="col-md-2">
						<div class="clearfix text-center">
								<h5 class="margin-top0 mb0">Total Goals</h5>
						</div>
					<div id="totalGoals" class="goalChat"></div>
    		</div>
    		<div class="col-md-2">
						<div class="clearfix text-center">
								<h5 class="margin-top0 mb0">In-progress Goals</h5>
						</div>
					<div id="inProgress" class="goalChat"></div>
				</div>
				
				
				<div class="col-md-2">
						<div class="clearfix text-center">
								<h5 class="margin-top0 mb0">Completed Goals<span class="ml">40</span></h5>
						</div>
					<div id="completedGoals" class="goalChat"></div>
				</div>
				<div class="col-md-2">
						<div class="clearfix text-center">
								<h5 class="margin-top0 mb0">Overdue Goals</h5>
						</div>
					<div id="overdueGoals" class="goalChat"></div>
				</div>
				<div class="col-md-2">
						<div class="clearfix text-center">
								<h5 class="margin-top0 mb0">Goals Not Started</h5>
						</div>
					<div id="goalsNotstarted" class="goalChat"></div>
				</div>
    </div>  
	</div>
    </div>
		
<div class="positionRelative reportHead clearfix mt">
      	<span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh"></a></span>
      	<ul class="pull-right list-unstyled list-inline">
						<li>
								<a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a>
							</li>
					<li>
					<a class="newAdBtn mr5" href="${SourceLeadWeb_pmsUrl}/addNewGoal" >+ Goal</a>
	  		</li>
		  	<li class=""><input class="repSearchBar" placeholder="Search" type="text"><span class="repSearchIcon"></span></li>
        </ul>
</div> 

	</section>
	
<jsp:include page="../masterFooter.jsp" />
