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
	
	if(goalUUID != null && goalUUID != 'undefined' && goalUUID != ''){
		 document.getElementById("performanceTeamsDiv").style.display="none";
		 $("#goalPercentage").attr("data-validation-optional", "true");
		 $("#goalPercentage").removeAttr("data-validation");
		 document.getElementById("performanceTeamNameDiv").style.display="block";
		performanceGoals.getPerformanceGoalsDetails(goalUUID);
	}else{
		performanceGoals.getPerformanceGoalTeams();
	}
	
	performanceGoals.goalStartDateAndEndDatePickers(dateFormat);
	performanceGoals.goalsDescriptionEditor();
	
 };
 
</script>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Team Goals</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}"> Home</a>
			&raquo;<a href="${SourceLeadWeb_pmsUrl}/performanceTeamGoalsList">Team Goals List</a>
			&raquo; Add Team Goal
		</div>
	</section>
	
	<div class="project_wrap">
	<div id="choicepopUp"></div>
	 <div id="goalsLoading"></div>
	 	<div class="clearfix newAddWidth">
		    <div class="form-horizontal mt myForm">
		    	 <form:form id="addGoal_form"  name="addGoal_form" method="post">
		    	 	 <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt"> 
		    	 	 <input type="hidden" name="goalId" id="goalId" />
		    	 	 <input type="hidden" name="goalType" id="goalType" />
		    	 	 <input type="hidden" name="performanceGoalType" id="performanceGoalType" value="Team_Goal" />
		    	 	 
		    	 	 	<div class="form-group">
				          <label class="col-md-3">Goal Name<span class="text-danger paddingLeft">&nbsp;*</span></label>
				          <div class="col-md-9"><input type="text" class="form-control" name="goalName" id="goalName" field-name="Goal Name" data-validation="required validate_Space validate_Alphanumeric validate_length length2-200"></div>
				        </div>
				        
				        <div class="form-group" id="goalStartDateDiv">
					          <label class="col-md-3">Start Date<span class="text-danger paddingLeft">&nbsp;*</span></label>
					         <div class="col-md-9">
					         <div class="input-group errorInptgroup">
					         <input type="text" class="form-control calendar" value="" id="goalStartDate" name="goalStartDate" field-name="Start Date" data-validation="required" readonly="readonly" >
					         <span class="input-group-addon" onClick="performanceGoals.clearGoalStartDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
					         </div>
					         </div>
				        </div>
				        
				        <div class="form-group" id="goalEndDateDiv">
					          <label class="col-md-3">End Date<span class="text-danger paddingLeft">&nbsp;*</span></label>
					         <div class="col-md-9">
					         <div class="input-group errorInptgroup">
					         <input type="text" class="form-control calendar" value="" id="goalEndDate" name="goalEndDate" field-name="End Date" data-validation="required" disabled readonly="readonly" >
					         <span class="input-group-addon" onClick="performanceGoals.clearGoalEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
					         </div>
					         </div>
				        </div>
				        
						<div class="form-group" id="performanceTeamsDiv" style="display: block;">
							<label class="col-md-3">Team<span class="text-danger paddingLeft">&nbsp;*</span></label>
							<div class="col-md-9">
								<select class="selectpicker form-control" id="performanceTeams"
									name="teamId" field-name="Team" data-validation="required">
									<option value="">--Select Team--</option>
								</select>
							</div>
						</div>
					
					<div class="form-group" id="performanceTeamNameDiv" style="display: none;">
				          <label class="col-md-3">Team</label>
				          <div class="col-md-9"><span id="performanceTeamName"></span></div>
			        </div>
					
						
					<div class="form-group" id="percentageDoneDiv" style="display: none;" >
						<label class="col-md-3">Percentage Done (%)</label>
					<div class="col-md-9">
						<div class="clearfix">
			                 <input type="text" class="form-control" name="goalPercentage" onblur="performanceGoals.validateGoalPercentage();" data-validation-optional="ture" field-name="Percentage Done" id="goalPercentage">
			              </div>
			              <div class="row mxrateerror" id="goalPercentageId">
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
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12  txtEditor_p">
									<textarea id="txtEditor" name="description"   value="" class="form-control newContrtextarea"  data-validation-optional="true"></textarea>
								</div>
						</div>
					</div>
					
				 <div class="clearfix"></div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
					  <label class="col-md-3">Attach Documents<span class="text-danger paddingLeft"></span></label>
					<div class="clearfix"></div>
					<div id="goalsDocumentsId">
			       		<div class="timesheet_week_comm invoiceDocs clearfix width100percnt pull-left">
			       			<span>
			         			 <div id="savedGoalsDocuments" class="pull-left col-md-6"></div>
			         		</span>
			           </div>
			         </div>
						<div class="clearfix"></div>
						<div class="clearfix mb10">
							<div class="col-md-10 paddingRight">
							<input id="Button1" type="button" value="Add Document" onclick="performanceGoals.addGoalsDocumets();" class="btn btn-sm btn-primary" />
							</div>
						</div>
						<div id="FileUploadContainer">
						</div>
						<br/>
					</div>
		 <div class="clearfix"></div>       
	        <div class="popupFoot text-right">
	           <button type="button" class="slBtn md colorBtn" id="savePerformanceGoals" onclick="performanceGoals.savePerformanceGoals();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="javascript:window.location.href = '${SourceLeadWeb_pmsUrl}/performanceTeamGoalsList'" >Cancel</button>
	        </div>
		    	 </form:form>
		    </div>
	    </div>
    </div>
	
	</section>
	
<jsp:include page="../masterFooter.jsp" />