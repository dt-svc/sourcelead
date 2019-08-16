<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="${SourceLeadWeb_pmsUrl}/resources/js/editor.js?staticTime=${staticTime}"></script>
<link href="${SourceLeadWeb_pmsUrl}/resources/css/editor.css?staticTime=${staticTime}" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="${SourceLeadWeb_pmsUrl}/resources/js/performanceGoals.js"></script>

<script>
var pmsUrl = '${SourceLeadWeb_pmsUrl}';
var dateFormat = '${SourceLeadWeb_dateFormat}';

window.onload = function () {
	var goalUUID = '${goalUUID}';
	performanceGoals.getGoalsDetails(goalUUID);
	
 };
 
</script>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Goals</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}"> Home</a>
			&raquo;<a href="${SourceLeadWeb_pmsUrl}/performanceTeamGoalsList"> Team Goals List</a>
			&raquo; Team Goal Details
		</div>
	</section>
	
	<div class="project_wrap">
	<div id="choicepopUp"></div>
	 <div id="goalsLoading"></div>
	  <form:form id="addGoalPercentage_form"  name="addGoalPercentage_form" method="post">
	 	<div class="clearfix newAddWidth">
		    <div class="form-horizontal mt myForm">
		    	 	 <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt"> 
		    	 	 <input type="hidden" name="goalUUID" id="goalUUID" />
		    	 	 <input type="hidden" name="performanceGoalType" id="performanceGoalType" value="Team_Goal" />
		    	 	 
		    	 	 	<div class="form-group">
				          <label class="col-md-3">Goal Name :</label>
				          <div class="col-md-9" id="goalName"></div>
				        </div>
				        
				        <div class="form-group" id="goalStartDateDiv">
					          <label class="col-md-3">Start Date :</label>
					         <div class="col-md-9" id="goalStartDate">
					         </div>
				        </div>
				        
				        <div class="form-group" id="goalEndDateDiv">
					          <label class="col-md-3">End Date :</label>
					         <div class="col-md-9" id="goalEndDate">
					         </div>
				        </div>
				        
				         <div class="form-group" id="teamGoalDiv">
					          <label class="col-md-3">Team :</label>
					         <div class="col-md-9" id="performanceTeamName">
					         </div>
				        </div>
				        
					
						<div class="form-group" id="percentageDoneDiv" style="display: none;">
				          <label class="col-md-3">Percentage Done (%)</label>
				          <div class="col-md-9"><input type="text" class="form-control" name="goalPercentage" id="goalPercentage" onblur="performanceGoals.validateGoalPercentage();" data-validation-optional="ture" field-name="Goal Percentage" ></div>
				        	<div class="row" id="goalPercentageId">
			                  <input type="hidden" name="goalPercentageValidation" id="goalPercentageValidation">
			               </div>
				        </div>
					
					</div>
        
		    	 	 </div>
		    	 	 
		   	 	 <div class="clearfix"></div>		
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
						<div class="clearfix">
								<label class="col-md-12"><span class="pull-left mr">Description</span>
								</label>
								<div class="col-md-12" id="goalsDescription"></div>
						</div>
					</div>
					
				 <div class="clearfix"></div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
					  <label class="col-md-3">Documents<span class="text-danger paddingLeft"></span></label>
					<div class="clearfix"></div>
					<div id="goalsDocumentsId">
			       		<div class="timesheet_week_comm invoiceDocs clearfix width100percnt pull-left">
			       			<span>
			         			 <div id="savedGoalsDocuments" class="pull-left col-md-6"></div>
			         		</span>
			           </div>
			         </div>
						<div class="clearfix"></div>
						</div>
						<br/>
					</div>
		 <div class="clearfix"></div>       
	        <div class="popupFoot text-right">
	           <button type="button" class="slBtn md colorBtn" id="savePerformanceGoals" onclick="performanceGoals.savePerformanceGoalPercentage();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="javascript:window.location.href = '${SourceLeadWeb_pmsUrl}/performanceTeamGoalsList'" >Cancel</button>
	        </div>
		     </form:form>
		      </div>
	    </div>
    </div>
	
	</section>
	
<jsp:include page="../masterFooter.jsp" />