<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%-- 
     @author : David Raju.
     this script is to get filter popup
     1.usage     : onclick on requirementList() ; 
     
--%>
<script>
$(document).ready(function() {
	 $("#filter1ApprovePop").click(function(){
			$("#filter1ApprovedPop").slideToggle(200);
			$(".export_pop, .action-pop, #exportPop, #filter2ApprovedPop").slideUp(200);
		});
	    $("html").click(function() {
	        $("#filter1ApprovedPop").slideUp(200);
	    });
	    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
	        e.stopPropagation();
	    });	
	    
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
		$(".export_pop, .action-pop, #exportPop, #filter1ApprovedPop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
}); 

</script>


<style>
/*.tbldata-dotdot span{
max-width:220px;
} */
.clsDatePicker {
	z-index: 100000 !important;
}

.side-bar .side-nav ul li span {
	display: inline-block;
	float: none;
}

.project_wrap_table span {
	display: inline;
}

.timesheet_out {
	overflow-x: auto;
	background: #f5f5f5;
	box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
	top: -45px;
}

.vendor_wrap {
	box-shadow: none;
}

.require_wrap_table {
	overflow: inherit;
	min-height: 175px;
}

.require_wrap_table select {
	width: 100% !important;
}
</style>
<script>
 window.onload = function() { 
 	requirementListCheck("${searchValue}","${universalRequirementList}","universalSearch",'${sourceleadUrl}');
};
</script>

<script>
function statusvalidating(id){
	changeStatusAuthorisation(id);
}
</script>
<jsp:include page="requirementTable.jsp" />
<!-- <script type="text/javascript" src="resources/js/requirementTable.js"></script> -->
<input type="hidden" name="noReq" id="noReq" />
<input type="hidden" id="requirementEmptyListMessage"
	name="requirementEmptyListMessage" />
<div id="popUp"></div>
<section class="content-wraper">
	<section class="content-header clearfix">
		<c:if test="${!empty msg}">
			<div class="success_pop" id="selector2" style="display: block;">
				<i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
		</c:if>
		<div class="pull-left">
			<h2 class="pull-left paddingRight">Jobs List</h2>
			<span class="paddingLeft pull-left">|<a class="paddingLeft"
				href="<c:url value='/professionalRequirements'/>">Crowdsourcing
					Jobs</a></span>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo; Job List
		</div>
	</section>
	<div class="clearfix">
		<div class="pull-left cand-advsrch advsrch_chosen">
			<a href="javascript:void(0)" class="btn btn-primary btn-sm"
				title="Advanced Search"
				onClick="openPop();loadChosenRequirementScripts();"> Advanced
				Search&nbsp;&nbsp; <i class="fa fa-search"></i>
			</a>
			<form:form id="requirement_form" name="requirement_form"
				enctype='multipart/form-data'>
				<div class="cand-advsrch-popup reqadvsrch candAdvsrch">
					<a href="javascript:void(0)" class="close-div" onClick="closePop()"><i
						class="fa fa-close"></i></a>

					<div class="row">
						<div class="clearfix">
							<div class="col-md-8">
								<label>Title</label>
								<div class="clearfix">
									<select class="chosen-select form-control input-sm" multiple
										data-placeholder="Job Title" id="requirementTitle"
										name="requirementTitle">
									</select>
								</div>
							</div>
							<div class="col-md-4">
								<label>Id</label>
								<div class="clearfix">
									<select class="chosen-select form-control input-sm" multiple
										data-placeholder="Job Id" id="jobId" name="jobId">
									</select>
								</div>
							</div>

						</div>
						<div class="clearfix">

							<div class="col-md-4">
								<label>Job status </label>
								<div class="clearfix">
									<select id="requirementStatu" name="requirementStatus"
										class="chosen-select" data-placeholder="Job status " multiple>
										<option>NEW</option>
										<option>DRAFT</option>
										<option>SUBMITTED</option>
										<option>OPEN</option>
										<option>CANCEL</option>
										<option>ONHOLD</option>
										<option>FILLEDCLOSED</option>
										<option>UNFILLEDCLOSED</option>
										<option>REJECTED</option>
									</select>
								</div>
							</div>
							<div class="col-md-4">
								<label>Posted On</label>
								<div class="clearfix">
									<select class="chosen-select" id="updatedOn" name="updatedOn"
										data-placeholder="Posted On">
										<option>All</option>
										<option>1 Day Ago</option>
										<option>3 Days Ago</option>
										<option>1 Week Ago</option>
										<option>2 Weeks Ago</option>
										<option>1 Month Ago</option>
										<option>3 Months Ago</option>
									</select>
								</div>
							</div>

							<div class="col-md-4">
								<label>Client</label>
								<div class="clearfix">
									<select class="chosen-select" id="client" name="client"
										data-placeholder="Select Client" multiple>
										<c:forEach var="report" items="${add_clientList}">
											<c:if test="${report.locationId.id == loginOrgLocId}">
												<option
													value="${report.id},${report.responderLocationId.org.orgName}"
													${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.responderLocationId.org.orgName}</option>
											</c:if>
											<c:if
												test="${report.responderLocationId.id == loginOrgLocId}">
												<option
													value="${report.id},${report.responderLocationId.org.orgName}"
													${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="clearfix">
							<div class="col-md-4">
								<label>Country</label>
								<div class="clearfix adv-srh-chosen">
									<select class="chosen-select" name="country" id="country"
										field-name="Country">
										<c:forEach var="cntry" items="${countryList}">
											<option value="${cntry.name}"
												${cntry.name  == loginCountryName ? 'selected' : ''}>${cntry.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="col-md-4">
								<label>State</label>
								<div class="clearfix adv-srh-chosen">
									<select class="chosen-select" name="state" id="state"
										field-name="State">
										<option value="">---select state---</option>
										<c:forEach var="state" items="${statesList}">
											<option value="${state.stateName}">${state.stateName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-4">
								<label>City</label>
								<div class="clearfix adv-srh-chosen">
									<select class="chosen-select" name="city" id="city"
										field-name="City">
										<option value="">---select city---</option>
									</select>
								</div>
							</div>
						</div>
						<div class="clearfix">
							<div class="col-md-4 hidden">
								<label>Client</label>
								<div class="clearfix">
									<select class="chosen-select" id="client" name="client"
										data-placeholder="Client" multiple>
										<c:forEach var="report" items="${add_clientList}">
											<c:if test="${report.locationId.id == loginOrgLocId}">
												<option
													value="${report.id},${report.responderLocationId.org.orgName}"
													${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.responderLocationId.org.orgName}</option>
											</c:if>
											<c:if
												test="${report.responderLocationId.id == loginOrgLocId}">
												<option
													value="${report.id},${report.responderLocationId.org.orgName}"
													${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<label>Notice Period</label>
							<div class="clearfix">
								<select multiple class="chosen-select form-control"
									id="noticePeriod" name="noticePeriod"
									data-validation-optional="true"
									data-placeholder="Select Notice Period">
									<option
										${requirement.noticeperiodList  == 'Immediately Available' ? 'selected' : ''}>Immediately Available</option>
									<option
										${requirement.noticeperiodList  == '15-Days' ? 'selected' : ''}>15-Days</option>
									<option
										${requirement.noticeperiodList  == '1-Month' ? 'selected' : ''}>1-Month</option>
									<option
										${requirement.noticeperiodList  == '2-Month' ? 'selected' : ''}>2-Month</option>
									<option
										${requirement.noticeperiodList  == '3-Month' ? 'selected' : ''}>3-Month</option>
								</select>
							</div>
						</div>
					</div>
					<div class="row form-footer">
						<div class="pull-right mr">
							<input type="button" value="Search" id="search_requiremet"
								onclick="requirementListCheck('','','advanceSearch','${sourceleadUrl}');" class="btn btn-sm btn-success" />
							<input type="button" value="Clear" id="clear_search"
								onclick="clearSearch();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>
			</form:form>

		</div>
	</div>
	<div class="timesheet_out">
		<div class="vendor_wrap">
			<div class="timesheet_head timesheetHead_Custom topfilters">
				<ul>
					<sec:authorize
						access="hasAnyRole('ROLE_CREATE_REQUIREMENT','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_EDIT_REQUIREMENT')">
							<div id="filter1ApprovedPop" class="approved_pop">
								<ul>
									<c:forEach var="report" items="${add_clientList}">
										<c:if test="${report.locationId.id == loginOrgLocId}">
											<li><input id="clientName${report.id}"
												name="clientNameById" value="${report.id}"
												onclick="requirementList('list','${sourceleadUrl}','${sourceleadUrl}');" type="checkbox"><label
												title="${report.responderLocationId.org.orgName}">${report.responderLocationId.org.orgName}</label></li>
											<%-- 			                    	<option value="${report.id},${report.responderLocationId.org.orgName}" ${report.id  == requirement.clientId.id ? 'selected' : ''} >${report.responderLocationId.org.orgName}</option> --%>
										</c:if>
										<c:if test="${report.responderLocationId.id == loginOrgLocId}">
											<li><input id="clientName${report.id}"
												name="clientNameById" value="${report.id}"
												onclick="requirementList('list','${sourceleadUrl}','${sourceleadUrl}');" type="checkbox"><label
												title="${report.locationId.org.orgName}">${report.locationId.org.orgName}</label></li>
											<%-- 			                  	 	<option value="${report.id},${report.responderLocationId.org.orgName}" ${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option> --%>
										</c:if>
									</c:forEach>
								</ul>
						 </div>
						<!-- for status counts add this function to below.. onClick ="requirementListIndividualStatusCounts();" -->
						<li><b id="filter2ApprovePop"><i class="fa fa-filter"></i><span>Status</span><i
								class="glyphicon glyphicon-play"></i></b>
							<div id="filter2ApprovedPop" class="approved_pop">
								<ul>
									<li><input id="c6" type="checkbox"
										name="requirementStatus" value="NEW"
										onclick="requirementList('list','${sourceleadUrl}','${sourceleadUrl}');"><label for="c6">New<span
											id="newRequirementCount"></span></label></li>
									<li><input id="c7" name="requirementStatus"
										type="checkbox" value="DRAFT" onclick="requirementList('list','${sourceleadUrl}');"><label
										for="c7">Draft<span id="draftRequirementCount"></span></label></li>
									<li><input id="c8" type="checkbox"
										name="requirementStatus" type="checkbox" value="SUBMITTED"
										onclick="requirementList('list','${sourceleadUrl}');"><label for="c8">Submitted
											<span id="submittedRequirementCount"></span>
									</label></li>
									<li><input id="c9" type="checkbox"
										name="requirementStatus" type="checkbox" value="OPEN"
										onclick="requirementList('list','${sourceleadUrl}');"><label for="c9">Open
											<span id="openRequirementCount"></span>
									</label></li>
									<li><input id="c10" type="checkbox"
										name="requirementStatus" type="checkbox" value="ONHOLD"
										onclick="requirementList('list','${sourceleadUrl}');"><label for="10">On
											Hold <span id="onHoldRequirementCount"></span>
									</label></li>
									<li><input id="c11" type="checkbox"
										name="requirementStatus" type="checkbox" value="CANCEL"
										onclick="requirementList('list','${sourceleadUrl}');"><label for="c11">Cancel
											<span id="cancelRequirementCount"></span>
									</label></li>
									<li><input id="c11" type="checkbox"
										name="requirementStatus" type="checkbox" value="FILLEDCLOSED"
										onclick="requirementList('list','${sourceleadUrl}');"><label for="c11">Filled
											Closed<span id="filledClosedRequirementCount"></span>
									</label></li>
									<li><input id="c11" type="checkbox"
										name="requirementStatus" type="checkbox"
										value="UNFILLEDCLOSED" onclick="requirementList('list','${sourceleadUrl}');"><label
										for="c11">UnFilled Closed<span
											id="unFilledClosedRequirementCount"></span></label></li>
									<li><input id="c11" type="checkbox"
										name="requirementStatus" type="checkbox" value="REJECTED"
										onclick="requirementList('list','${sourceleadUrl}');"><label for="c11">Rejected<span
											id="rejectedRequirementCount"></span></label></li>
									<li><input id="c12" type="checkbox"
										name="requirementStatus" type="checkbox" value="CLOSED"
										onclick="requirementList('list','${sourceleadUrl}');"><label for="c12">Closed<span
											id="closedRequirementCount"></span></label></li>
								</ul>
							</div></li>
					</sec:authorize>

					<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT')">
						<li><a href="<c:url value='/requirement'/>"><i
								class="fa fa-plus"></i>Add Job</a></li>
					</sec:authorize>
					<sec:authorize access="hasAnyRole('ROLE_ADD_TEAM')">
						<li><a href="<c:url value='/team'/>"><i
								class="fa fa-plus"></i>Team</a></li>
					</sec:authorize>

					<li><sec:authorize
							access="hasAnyRole('ROLE_CREATE_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_VIEW_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_EDIT_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_CREATE_REQUIREMENT_TEMPLATE','ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE','ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE')">
							<a onclick="getActions();" class="action"
								href="javascript:void(0)">Template <i
								class="fa fa-caret-down"></i>
							</a>
							<div class="action-pop">
								<span> <sec:authorize
										access="hasAnyRole('ROLE_CREATE_REQUIREMENT_TEMPLATE','ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE','ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE')">
										<a href="<c:url value='/template'/>"><i class="fa fa-plus"></i>Job</a>
									</sec:authorize> <sec:authorize
										access="hasAnyRole('ROLE_CREATE_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_VIEW_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_EDIT_INTEVIEW_FEEDBACK_TEMPLATE')">
										<a href="<c:url value='/interViewFeedBackTemplate'/>"><i
											class="fa fa-plus"></i>Interview</a>
									</sec:authorize>
								</span>
							</div>
						</sec:authorize></li>

					<sec:authorize access="hasAnyRole('ROLE_EXPORT_REQUIREMENTS')">
						<li title="Export" class="mrPOP" id="mypop"><i
							class="glyphicon glyphicon-export"></i>
							<div id="exportPop">
								<ul>
									<li onclick="exportToPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;PDF</li>
									<li onclick="exportToExcel();"><i
										class="fa fa-file-excel-o text-primary"></i>&nbsp;&nbsp;Excel</li>
								</ul>
							</div></li>
					</sec:authorize>

					<div class="clearfix"></div>
				</ul>
				 <div class="clearfix"></div>
		        <div class='remove_search'>
		        		<input type='text' placeholder='search' id='dataTableSearch'/>
		        </div>
			</div>
			<div class="require_wrap_table datatable_filter">
				<div id="requirementList"></div>
			</div>
		</div>
	</div>
</section>

<jsp:include page="addApplicantFromRequirementList.jsp" />
<!-- 

<script type="text/javascript">
function changeStatusAuthorisation(requirementId){
	var status = document.getElementById('changeStatusId' + requirementId).value;
	 var sourceleadUrl ='${sourceleadUrl}';
	 var userId = '${SourceLeadWeb_userId}';
	   $.ajax({
         type : "POST",
          url: sourceleadUrl+"/changeStatusAuthorisation?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&status="+status+"&userId="+userId,        
          success :function(data) {
        	  if(data == "true"){
        		  document.getElementById("changeStatusId" + requirementId).disabled=false;
        		  
        	  }else if(data == "false"){
        		  document.getElementById("changeStatusId" + requirementId).disabled=true;
        		  
        	  }
        	  
          }
}); 
}
</script>
 -->


<script type="text/javascript">
function changeStatus(requirementId, requirementStatus){
	var status = document.getElementById('changeStatusId' + requirementId).value;
	 var sourceleadUrl ='${sourceleadUrl}';
	 var userId ='${SourceLeadWeb_userId}';
	 
     if(requirementStatus == "DRAFT"){
        	 $.ajax({
             type : "POST",
        	 url: sourceleadUrl+"/saveChangedStatusFromDraft?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&status="+status+"&userId="+userId,
	        	 success :function(data) {
	       		  $("#changeStatusId" +requirementId).val(status);
	           	  //location.replace(sourceleadUrl+"/requirementList");
	         	}
        	});
         }else{
        	 $.ajax({
             type : "POST",
        	 url: sourceleadUrl+"/saveChangedStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&status="+status,
	        	 success :function(data) {
	       		  $("#changeStatusId" +requirementId).val(status);
	           	 // location.replace(sourceleadUrl+"/requirementList");
	         	}
        	});
         }
	 var statusArray = [];
	 $("input:checkbox[name=requirementStatus]:checked").each(function () {
		 statusArray.push($(this).val());
     });
	 if(statusArray.indexOf(status) > -1){
		
	 }else{
		 var table = $('#requirementPagination').DataTable();
		 $('#Row_'+requirementId).remove();
	 }
     
	}
</script>
<script>
$('#mypop').click(function(){
		$("#exportPop").toggle();
		$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, .action-pop").slideUp();
 });
 $("html").click(function(){
	$("#exportPop").hide(200);
 });
 $("#exportPop, #mypop").click(function(e) {  
     e.stopPropagation();
 });
</script>
<script>
function exportToPdf(){
	var req = $("#noReq").val();
	 var sourceleadUrl ='${sourceleadUrl}';
	 var requirementEmptyMessage = $("#requirementEmptyListMessage").val();
	  var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() 
	    {
			status.push($(this).val());
	    });
	 var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() 
	    {
			clients.push($(this).val());
	    });
		if(requirementEmptyMessage > 0){
		window.location.href = sourceleadUrl+"/getRequirementExportListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&status="+status+"&clients="+clients+"&exportType=pdf";
		}else{
			 var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='empty_requirement_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"		<p>Jobs List is Empty</p>";
				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
				result = result +"	</div>";
				$("#popUp").append(result);
				$("#empty_requirement_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
		}
}
</script>
<script>
function exportToExcel(){
	var req = $("#noReq").val();
	var requirementEmptyMessage = $("#requirementEmptyListMessage").val();
	var sourceleadUrl ='${sourceleadUrl}';
	  var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() 
	    {
			status.push($(this).val());
	    });
	 var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() 
	    {
			clients.push($(this).val());
	    });
		if(requirementEmptyMessage > 0){
			window.location.href = sourceleadUrl+"/getRequirementExportListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&status="+status+"&clients="+clients+"&exportType=xls";	
		}else{
			 var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='empty_requirement_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"		<p>Jobs List is Empty</p>";
				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
				result = result +"	</div>";
				$("#popUp").append(result);
				$("#empty_requirement_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
		}
}
</script>
<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script>
       function msgFadein1() {
           setTimeout(function(){
                   $('#selector2').fadeOut('slow'); }, 1000);
       }
</script>
<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, #exportPop").slideUp(200);
	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
 	});	
}
</script>

<script>
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
		}
		function openPop(){
			$("div.cand-advsrch-popup").toggle();
		}
	</script>



<script type="text/javascript">
			//Adding Customized Any Of Keyword to the list 
		    var select, chosen;
		    select = $("#requirementTitle");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Title:' });
		    chosen = select.data('chosen');
		    chosen.search_field.on('keydown keyup', function(e)
		    {
		    	var reqTitle = this.value.replace(",","");
		        if (tagItBykeyForSkills(e) && (reqTitle != null  && reqTitle != '' && reqTitle != 'undefined'))
		        {
		        	preventTabkey(e); 
		        	var par = this.parent;
		            var option = $("<option>").val(reqTitle).text(reqTitle);
		            select.append(option);
		            select.find(option).prop('selected', true);
                    $("#requirementTitle option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#requirementTitle").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    var selectone, chosenone;
		    selectone = $("#jobId");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Add Requirement ID:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.on('keydown keyup', function(e1)
		    {
		    	var reqId = this.value.replace(",","");
		        if (tagItBykeyForSkills(e1) && (reqId != null  && reqId != '' && reqId != 'undefined'))
		        {
		        	preventTabkey(e1); 
		        	var par = this.parent;
		            var option = $("<option>").val(reqId).text(reqId);
		            selectone.append(option);
		            selectone.find(option).prop('selected', true);
                    $("#jobId option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#jobId").trigger("chosen:updated");
	        		
		        }
		    });
		    
		      $("#country").on('change', function (e) {
		    	   getStates();
		       }); 
		       $("#state").on('change', function (e) {
		    	   getCities();
		       });
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
</script>

<script>
function loadChosenRequirementScripts(){
     $("#city").empty();
	 $("#city").val('').trigger("chosen:updated");
	 $("#requirementTitle").val('').trigger("chosen:updated");
	 $("#rate").val('').trigger("chosen:updated");
	 $("#minExp").val('').trigger("chosen:updated");
	 $("#maxExp").val('').trigger("chosen:updated");
	 $("#requirementStatu").val('').trigger("chosen:updated");
	 $("#updatedOn").val('').trigger("chosen:updated");
	 $("#education").val('').trigger("chosen:updated");
	 $("#client").val('').trigger("chosen:updated");
	 $("#skills").val('').trigger("chosen:updated"); 
	
}
</script>

<script>
function getStates(){
	var countryName = $("#country").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select state---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').append('<option value="">---select city---</option>');
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select state---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select city---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script>
<script>
function getCities(){
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select city---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select city---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>
<jsp:include page="../masterFooter.jsp" />