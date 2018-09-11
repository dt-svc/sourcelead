<jsp:include page="../newMasterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%-- 
     @author : David Raju.
     this script is to get filter popup
     1.usage     : onclick on requirementList() ; 
     
--%>
<script>
function clearList(){
	location.reload();
}

function selectAllPriority(){
	$("ul#priority > li input:checkbox").prop("checked",true);
	requirementList('list','${sourceleadUrl}');
}
function clearAllPriority(){
	$("ul#priority > li input:checkbox").prop("checked",false);
	requirementList('list','${sourceleadUrl}');
}
function selectAllJobType(){
	$("ul#jobType > li input:checkbox").prop("checked",true);
	requirementList('list','${sourceleadUrl}');
}
function clearAlljobType(){
	$("ul#jobType > li input:checkbox").prop("checked",false);
	requirementList('list','${sourceleadUrl}');
}
function selectAllStatus(){
	$("ul#status > li input:checkbox").prop("checked",true);
	requirementList('list','${sourceleadUrl}');
}
function clearAllStatus(){
	$("ul#status > li input:checkbox").prop("checked",false);
	requirementList('list','${sourceleadUrl}');
}

</script>
<script>
$(document).ready(function() {
	getcites('apiLocation','city','recommendation');
});
</script>


<style>

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
.ref-top{
top: 18px;height: 32px; line-height: 18px;
}
</style>
<script>
 window.onload = function() {
	 var viewTab = "${viewTab}";
		if(viewTab === 'jobFair') {
			/* alert("viewTab : " + viewTab); */
			jobFairListCheck();
		} else {
 			requirementListCheck("${searchValue}","${universalRequirementList}","universalSearch",'${sourceleadUrl}');
		}
};
</script>

<script>
function statusvalidating(id){
	changeStatusAuthorisation(id);
}
</script>

<script>
   function getSourceleadTags(){
 		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getSourceleadTagsList?"+tokenParamName+"="+tokenValue, 
		        success: function(json) {
		        	var output = "";
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt+ "'>"+opt+"</option>";
			        		
					}
		        	$("#jobsTags").empty();
			        $("#jobsTags").append(output);
			        $('#jobsTags').trigger("chosen:updated");
		        },
		    }); 
   }
   </script>
   
<div class='project_inn_mask'></div>

<div id='changeStatusPopup' class='remove_user_pop remove_user_pop_a'>
	<p>Are are sure you want to delete the JobFair?</p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmId' type='button' value='Ok' onclick='changeJobFairStatus()' class="pull-left">
		<input id="cancelStatusPopup" class='u_can_color' type='button' value='Cancel' onClick='closeChangeStatusPopup()'>
	</b>
</div>


<jsp:include page="requirementTable.jsp" />
<jsp:include page="requirementAssignedTo.jsp"/>
<!-- <script type="text/javascript" src="/resources/js/requirementTable.js"></script> -->
<input type="hidden" name="noReq" id="noReq" />
<input type="hidden" id="requirementEmptyListMessage" name="requirementEmptyListMessage" />
<div id="popUp"></div>
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
        	<h2>Jobs 
					<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT')">
						<a href="<c:url value='${appPath}/professionalRequirements'/>" class="jobHeadBtn">Crowdsourcing Jobs</a>
					</sec:authorize>        	
        	</h2>
      	</div>
		<c:if test="${!empty msg}">
			<div class="success_pop" id="selector2" style="display: block;">
				<div class="pull-left"><i class="fa fa-check-circle"></i></div>
				<div class="pull-left" style="width:325px; padding:10px 0;"><span>${msg}</span></div>
			</div>
		</c:if>
		
		<%-- <div class="pull-left">
			<h2 class="pull-left paddingRight">Jobs List</h2>
			<span class="paddingLeft pull-left">|<a class="paddingLeft"
				href="<c:url value='${appPath}/professionalRequirements'/>">Crowdsourcing
					Jobs</a></span>
		</div> --%>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Job List
		</div>
	</section>
	<div class="clearfix">
	<ul class="project-tabs-menu projecttabs-custom clearfix">
         <li class="current" id="jobsTab"><a href="javascript:void(0)" onclick="requirementListCheck('','','','${sourceleadUrl}');">Jobs</a></li>
         <li id="jobFairTab"><a href="javascript:void(0)"  onclick="jobFairListCheck();">Job Fair</a></li>
         <div class="clearfix"></div>
     </ul>
	</div>

<form:form id="requirement_form" name="requirement_form" enctype='multipart/form-data'>
<div class="cand-advsrch-popup advancedsearch-popup reqadvsrch candAdvsrch ">
<h5 class="headerAdsrch">Advanced Search
	<a href="javascript:void(0)" class="close-div" onClick="closePop()"><i
		class="fa fa-close"></i></a>
</h5>
<div class="addInner">
	<div class="row">
		<div class="clearfix">
			<div class="col-md-4">
				<label>Generated ID</label>
				<div class="clearfix">
					<select class="chosen-select form-control input-sm" multiple
						data-placeholder="Generated ID" id="generatedId" name="generatedId">
					</select>
				</div>
			</div>
			
			<div class="col-md-4">
				<label>Title</label>
				<div class="clearfix">
					<select class="chosen-select form-control input-sm" multiple
						data-placeholder="Job Title" id="requirementTitle"
						name="requirementTitle">
					</select>
				</div>
			</div>
			
			<div class="col-md-4">
				<label>Job Id</label>
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
									value="${report.responderLocationId.org.orgName}"
									${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.responderLocationId.org.orgName}</option>
							</c:if>
							<c:if
								test="${report.responderLocationId.id == loginOrgLocId}">
								<option
									value="${report.locationId.org.orgName}"
									${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<%-- <div class="clearfix">
			<div class="col-md-4">
				<label>Country</label>
				<div class="clearfix adv-srh-chosen">
					<select class="chosen-select" name="country" id="country" field-name="Country">
						<option value="">---select Country---</option>
						<c:forEach var="cntry" items="${countryList}">
							<option value="${cntry.name},${cntry.code2}">${cntry.name}</option>
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
		</div> --%>
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
			
		<div class="col-md-4">
			<label>Skills</label>
			<div class="clearfix">
				<select class="chosen-select form-control input-sm" multiple data-placeholder="Skills" id="requirementSkills" name="requirementSkills">
				<c:forEach var="skill" items="${skillList}">
				<option value="${skill.name}">${skill.name}</option>
				</c:forEach>
				</select>
			 </div>
		</div>
		
		
		<div class="col-md-4">
			<label>Immigration Status</label>
			<div class="clearfix">
                <select multiple class="chosen-select form-control" name="visaType" id="visaType" data-placeholder="Select Visa" data-validation-optional="true">
				<c:forEach var="visaList" items="${visaList}">
				<option value="${visaList}">${visaList}</option>
				</c:forEach>
				</select>
			 </div>
		</div>
		
		<div class="col-md-12 mb">
			<label>Tags</label>
				<div class="clearfix">
					<select class="chosen-select form-control input-sm" multiple data-placeholder="Tags" id="jobsTags" name="jobsTags">
					</select>
				 </div>
			</div>
			
		<div class="clearfix">
		   <div class="col-md-4">
				<label>Choose City</label>
				<div class="clearfix">
				<div class="col-md-14">
			      <input type="text" class="form-control positionRelative"  multiple data-placeholder="select city" multiple name="apiLocation"  id="apiLocation">
				  <i class="fa fa-refresh btn btn-primary refresh-icon mr ref-top" title="Refresh" onClick="refreshGoogleApiCity('apiLocation');"></i>
				 </div>
				</div>
			</div>
			
			
			<div class="col-md-8">
				<label>Selected Cities</label>
				<div class="clearfix">
				   <select class="chosen-select form-control input-sm" multiple
						data-placeholder="sletected cites" id="city" name="city">
					</select>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="clearfix advancedsearch-footer">
		<div class="pull-right mr">
			<input type="button" value="Search" id="search_requiremet"
				onclick="requirementListCheck('','','advanceSearch','${sourceleadUrl}');" class="btn btn-sm btn-success" />
			<input type="button" value="Reset" id="clear_search"
				onclick="clearSearch();" class="btn btn-sm btn-primary" />
		</div>
	</div>
</div>
</form:form>

<div class="timesheet_out" id="jobsTabContent">
		
<div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon" id="filtersDiv">
      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="clearList();"></a>
      		<a href="javascript:void(0)" class="newAdBtn pull-left" title="Advanced Search" onClick="openPop();"> Advanced Search &nbsp; <i class="fa fa-search"></i> </a>
      </span>
      <span class="pull-left refreIcon">
      		<button type="button" class="btn btn-danger ml pull-left btn-sm" onclick="jobForceDelete();" id="forceDeleteBtn" style="display:none">Delete</button>
      </span>
      <ul class="pull-right list-unstyled list-inline">
      
					<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_EDIT_REQUIREMENT')">
							<div id="filter1ApprovedPop" class="approved_pop">
								<ul>
									<c:forEach var="report" items="${add_clientList}">
										<c:if test="${report.locationId.id == loginOrgLocId}">
											<li><input id="clientName${report.id}" name="clientNameById" value="${report.id}" onclick="requirementList('list','${sourceleadUrl}');" type="checkbox"><label
												title="${report.responderLocationId.org.orgName}">${report.responderLocationId.org.orgName}</label></li>
											<%-- 			                    	<option value="${report.id},${report.responderLocationId.org.orgName}" ${report.id  == requirement.clientId.id ? 'selected' : ''} >${report.responderLocationId.org.orgName}</option> --%>
										</c:if>
										<c:if test="${report.responderLocationId.id == loginOrgLocId}">
											<li><input id="clientName${report.id}" name="clientNameById" value="${report.id}" onclick="requirementList('list','${sourceleadUrl}');" type="checkbox"><label
												title="${report.locationId.org.orgName}">${report.locationId.org.orgName}</label></li>
											<%-- 			                  	 	<option value="${report.id},${report.responderLocationId.org.orgName}" ${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option> --%>
										</c:if>
									</c:forEach>
								</ul>
						 </div>
					</sec:authorize>  
					
					<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT')">
		                    <li>
								<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
								<div class="action-pop">
									<span> 
									<a href="<c:url value='${appPath}/requirement'/>" >Job</a>
									<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT')">
										<a href="<c:url value='${appPath}/uploadJobs'/>">Import</a>
									</sec:authorize>									
									</span>
								</div>
							</li>						
						
					</sec:authorize>	
									
				<li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="filterPop();"></a></li>
				
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>	           	   		
							<sec:authorize access="hasAnyRole('ROLE_EXPORT_REQUIREMENTS')">
							 	<a href="javascript:void(0)" onclick="exportToPdf(1);" title="PDF">Export PDF</a>
								<a href="javascript:void(0)" onclick="exportToExcel(1);" title="Excel">Export Excel</a>					
							</sec:authorize>
						</span>
					</div>
				</li>
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='dataTableSearch'/><span class="repSearchIcon" onClick="requirementListCheck('','','','${sourceleadUrl}');"></span></li>
        </ul>
      </div>		
		
<div class="require_wrap_table datatable_filter">
	<div id="requirementList"></div>
</div>

</div>
	
	<div class="timesheet_out" id="jobFairTabContent" style="display: none;">
		<div class="vendor_wrap">
<div class="positionRelative reportHead clearfix">
      
      <ul class="pull-right list-unstyled list-inline">
					
					<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT')">
						<li><a href="<c:url value='${appPath}/addJobFair'/>" class="newAdBtn">+ Job Fair</a></li>
					</sec:authorize>					
									
					<sec:authorize access="hasAnyRole('ROLE_EXPORT_REQUIREMENTS')">
						<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
							<div class="newActPop">
								<span>
									 	<a href="javascript:void(0)" onclick="exportToPdf(2);" title="PDF">Export PDF</a>
										<a href="javascript:void(0)" onclick="exportToExcel(2);" title="Excel">Export Excel</a>					
								</span>
							</div>
						</li>
					</sec:authorize>
				
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='jobFairDataTableSearch'/><span class="repSearchIcon" onClick="jobFairListCheck();"></span></li>
        </ul>
      </div>	
      	
			<div class="require_wrap_table datatable_filter">
				<div id="jobFairList"></div>
			</div>
		</div>
	</div>
	
	
	<div class="side_mask">
  <div class="proj_close" onClick="activeTab();"></div>
</div>
	
	
</section>
 <div class="advancedsearch-mask" onClick="closeAdsrch()"></div>
<jsp:include page="addApplicantFromRequirementList.jsp" />

<input type="hidden" name="assignedTeamId" id="assignedTeamId" value=""/>


<script>
function filterPop(){
	$("#requirementFilterPopup").toggle();
	$(".sideMask").fadeIn();
};
function closeFilter(){
	$("#requirementFilterPopup").hide();
	$(".sideMask").fadeOut();

}
</script>
<!-- 

<script type="text/javascript">
function changeStatusAuthorisation(requirementId){
	var status = document.getElementById('changeStatusId' + requirementId).value;
	 var sourceleadUrl ='${sourceleadUrl}';
	 var userId = '${SourceLeadWeb_userId}';
	   $.ajax({
         type : "POST",
          url: sourceleadUrl+"/changeStatusAuthorisation?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status+"&userId="+userId,        
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
	$("tr").find("td.sorting_1").removeClass("sorting_1");
	if(status == "FILLEDCLOSED" || status == "OPEN"){
		$("#job_"+requirementId).addClass("row-success");
	}else if(status == "CANCEL"){
		$("#job_"+requirementId).addClass("row-danger");
	}else{
		$("#job_"+requirementId).addClass("row-warning");
	}

	 var userId ='${SourceLeadWeb_userId}';
     if(status == "DRAFT"){
        	 $.ajax({
             type : "POST",
        	 url: sourceleadUrl+"/saveChangedStatusFromDraft?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status+"&userId="+userId,
	        	 success :function(result) {
	        		 	if (result.response === "SESSION_TIMEDOUT") {
			  				location.replace(sourceleadUrl);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	$("#changeStatusId" +requirementId).val(status);
		  	           	  //location.replace(sourceleadUrl+"/requirementList");	
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
	         	}
        	});
         }else{
        	 $.ajax({
             type : "POST",
        	 url: sourceleadUrl+"/saveChangedStatus?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status,
	        	 success :function(result) {
	        		 if (result.response === "SESSION_TIMEDOUT") {
			  				location.replace(sourceleadUrl);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	$("#changeStatusId" +requirementId).val(status);
		   	           	 //location.replace(sourceleadUrl+"/requirementList");
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
	         	}
        	});
         }
	 /* var statusArray = [];
	 $("input:checkbox[name=requirementStatus]:checked").each(function () {
		 statusArray.push($(this).val());
     });
	 if(statusArray.indexOf(status) > -1){
		
	 }else{
		 var table = $('#requirementPagination').DataTable();
		 $('#Row_'+requirementId).remove();
	 } */
	 setTM();
	}
	function setTM (){
		  setTimeout(function() { 
		  $("tr").removeClass("row-info");
		  $("tr").removeClass("row-success");
		  $("tr").removeClass("row-warning");
		  $("tr").removeClass("row-danger");
		}, 2000);
	};
</script>
<script>
	$('#mypop').click(function(){
		$("#exportPop").toggle();
		$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, .action-pop").slideUp();
 	});
	$('#mypopJobFair').click(function(){
		$("#exportPopJobFair").toggle();
		$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, .action-pop").slideUp();
 	});
	$("html").click(function(){
		$("#exportPop").hide(200);
		/* $("#exportPopJobFair").hide(200); */
	});
	$("#exportPop, #exportPopJobFair, #mypop").click(function(e) {  
	    e.stopPropagation();
	});
</script>
<script>
function exportToPdf(type) {
	if(type === 1) {
		var req = $("#noReq").val();
	 	var sourceleadUrl ='${sourceleadUrl}';
	 	var requirementEmptyMessage = $("#requirementEmptyListMessage").val();
	  	var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() {
			status.push($(this).val());
	    });
	 	var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() {
			clients.push($(this).val());
	    });
		if(requirementEmptyMessage > 0) {
			window.location.href = sourceleadUrl+"/getRequirementExportListByOrgLocationID?"+tokenParamName+"="+tokenValue+"&status="+status+"&clients="+clients+"&exportType=pdf";
		} else {
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
	} else {
		/* alert("Export JobFair to PDF"); */
		$(".buttons-pdf").click();
	}
}
</script>
<script>
function exportToExcel(type) {
	if(type === 1) {
		var req = $("#noReq").val();
		var requirementEmptyMessage = $("#requirementEmptyListMessage").val();
		var sourceleadUrl ='${sourceleadUrl}';
	  	var status = [];
		$('input:checkbox[name=requirementStatus]:checked').each(function() {
			status.push($(this).val());
	    });
	 	var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() {
			clients.push($(this).val());
	    });
		if(requirementEmptyMessage > 0) {
			window.location.href = sourceleadUrl+"/getRequirementExportListByOrgLocationID?"+tokenParamName+"="+tokenValue+"&status="+status+"&clients="+clients+"&exportType=xls";	
		} else {
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
	} else {
		/* alert("Export JobFair to Excel"); */
		$(".buttons-excel").click();
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
	$(".export_pop, #filter2ApprovedPop, #filter1ApprovedPop, #exportPop, #exportPopJobFair").slideUp(200);
	
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
			$(".advancedsearch-mask").fadeOut();
		}
		function openPop(){
			 getSourceleadTags();
			 $("#dataTableSearch").val('');
			 $("div.cand-advsrch-popup").toggle();
			 $(".advancedsearch-mask").fadeIn();
			 $("#requirementFilterPopup").toggle();
		}
		function closeAdsrch(){
			$("div.cand-advsrch-popup").fadeOut();
			$(".advancedsearch-mask").fadeOut();
		};
	</script>

<script>
  $(function() { 
	       //Adding Customized Job Title list this method is slead-common.js
		   tagIt("requirementTitle", 'Press Enter-Key Or Comma(,) Or Tab To Add Title:', 'tagItBykeyForSkills','skills','Job Title');
	       
		   //Adding Customized Job Id list this method is slead-common.js
		   tagIt("jobId", 'Press Enter-Key Or Comma(,) Or Tab To Add Add Requirement ID:', 'tagItBykeyForSkills','skills','Job Id');
		   
		 //Adding Customized Job Id list this method is slead-common.js
		   tagIt("generatedId", 'Press Enter-Key Or Comma(,) Or Tab To Add Add Generated ID:', 'tagItBykeyForSkills','skills','Generated ID');
		 
		 //Adding Customized Job Skills list this method is slead-common.js
		   tagIt("requirementSkills", 'Press Enter-Key Or Comma(,) Or Space To Add New Skills:', 'tagItBykeyForSkills','skills','Skills');

		   tagIt("city", 'Press Enter-Key Or Comma(,) Or Space To Add New Cites:', 'tagItBykeyForSkills','skills','sletected cites');

		   
  });
  </script>
  

<script type="text/javascript">
		     /*  $("#country").on('change', function (e) {
		    	  getcites('apiLocation','city','recommendation');
		    	  $("#apiLocation").val('');
		    	  $("#city").val('').trigger("chosen:updated");
		       });  */
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
function clearSearch(){
	document.getElementById("requirement_form").reset();
	$('#requirementStatu').val('').trigger('chosen:updated');
	$('#updatedOn').val('').trigger('chosen:updated');
	$('#client').val('').trigger('chosen:updated');
	$('#country').val('').trigger('chosen:updated');
	$('#visaType').val('').trigger('chosen:updated');
	$('#requirementSkills').val('').trigger('chosen:updated');
	$('#state').val('').trigger('chosen:updated');
	$('#city').val('').trigger('chosen:updated');
	$('#noticePeriod').val('').trigger('chosen:updated');
	$('#requirementTitle').val('').trigger('chosen:updated');
	$('#jobId').val('').trigger('chosen:updated');
	$('#generatedId').val('').trigger('chosen:updated');
}
</script>







<!-- ============================ Laxman Google Api Location ============================= -->

<!-- <script>
 function getcites(locatinId,cityId){
	 var countryName = $("#country").val().split(',');
	 var countryCode = countryName[1];
	 initialize(locatinId,countryCode,cityId);
 }
function initialize(locatinId,countryCode,cityId) {
    	autocomplete = new google.maps.places.Autocomplete((document.getElementById(locatinId)), {
    		types: ['geocode'],
    		componentRestrictions: {
	        	country: countryCode
	        }
        });
    	
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
  place = autocomplete.getPlace();
  var address = {};
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    switch (addressType) {
       case "route":
    	 address.name=place.address_components[i].long_name;
    	 break;
      case "locality":
        address.city = place.address_components[i].long_name;
        break;
      case "administrative_area_level_1":
        address.state = place.address_components[i].long_name;
        break;
      case "country":
        address.country = place.address_components[i].long_name;
        address.countryShortName = place.address_components[i].short_name;
        
    }
  }
 
  $("#"+locatinId).val(address.city).change();
  multicities[address.city] = address.city;
  
  for(var e in multicities) {
		 var option = $("<option>").val(multicities[e]).text(multicities[e]);
		 $("#"+cityId).append(option);
		 $("#"+cityId).find(option).prop('selected', true);
		 $("#"+cityId).find(option).siblings("[value='"+ multicities[e].replace(",","")+"']").remove();
		 $("#"+cityId).trigger("chosen:updated");
  }
});
}
function refreshGoogleApiCity(cityId){
	$("#"+cityId).val('');
}
</script> -->




<!--  Filter Pop -->



<!-- <div class="sideMask"></div> -->
<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" id="requirementFilterPopup">
<h5 class="headerAdsrch">Filters <a href="javascript:void(0)" class="close-div" onClick="closeFilter()"><i class="fa fa-close"></i></a></h5>
<div class="addInner">
<div class="row">
 
 <div class='col-md-2'>
 <div class='row'>
 <div class='col-md-12'>
 <label><strong>Assign To</strong></label>
 <div class='clearfix'>
 	<ul id="assignTo" class="list-unstyled">
		<li class="radioIput positionRelative"><input id="r1" type="radio" name="requirementAssignTo" value="Alljobs" ><label for="r1">All Jobs </label></li>
		<li class="radioIput positionRelative"><input id="r2" type="radio" name="requirementAssignTo" value="Myjobs" ><label for="r2">My Jobs </label></li>
		<!-- <li class="radioIput positionRelative"><input id="r3" type="radio" name="requirementAssignTo" value="CrowdsourcingJobs" ><label for="r3">Crowdsourcing Jobs</label></li> -->
	</ul>
	</div>
 </div>
 
 			<div  class="col-md-12">
 			<label><strong>Priority</strong></label>
 			<div class='clearfix'>
								<ul id="priority" class="list-unstyled">
									<li class="checkBox positionRelative"><input id="c1" type="checkbox" name="requirementPriority" value="Medium" ><label for="c1">Medium </label></li>
									<li class="checkBox positionRelative"><input id="c2" type="checkbox" name="requirementPriority" value="Low" ><label for="c2">Low </label></li>
									<li class="checkBox positionRelative"><input id="c3" type="checkbox" name="requirementPriority" value="Urgent" ><label for="c3">Urgent </label></li>
									<li class="checkBox positionRelative"><input id="c4" type="checkbox" name="requirementPriority" value="High" ><label for="c4">High </label></li>
								</ul>
				</div>
			</div>
			
			<div class="col-md-12">
				<label><strong>Jobs</strong></label>
					<div class='clearfix'>
				<ul id="jobs" class="list-unstyled">
					<li class="checkBox positionRelative"><input id="j1" type="checkbox" name="requirementSpecJobs" value="ClientJobs" ><label for="j1">All Client Jobs </label></li>
					<li class="checkBox positionRelative"><input id="j2" type="checkbox" name="requirementSpecJobs" value="NormalJobs" ><label for="j2">All Internal jobs</label></li>
				
				</ul>
			</div>
           </div>
			</div>
			</div>
			
			<div  class="col-md-2">
			<label><strong>Job Type</strong></label>
			<div class='clearfix'>
								<ul id="jobType" class="list-unstyled">
									<li class="checkBox positionRelative"><input id="c11" type="checkbox" name="requirementType" value="C2C" ><label for="c11">C2C </label></li>
									<li class="checkBox positionRelative"><input id="c12" type="checkbox" name="requirementType" value="W-2" ><label for="c12">W-2 </label></li>
									<li class="checkBox positionRelative"><input id="c13" type="checkbox" name="requirementType" value="C2H" ><label for="c13">C2H </label></li>
									<li class="checkBox positionRelative"><input id="c14" type="checkbox" name="requirementType" value="Contract" ><label for="c14">Contract </label></li>
									<li class="checkBox positionRelative"><input id="c15" type="checkbox" name="requirementType" value="Part Time" ><label for="c15">Part Time </label></li>
									<li class="checkBox positionRelative"><input id="c16" type="checkbox" name="requirementType" value="Full Time" ><label for="c16">Full Time </label></li>
									<li class="checkBox positionRelative"><input id="c17" type="checkbox" name="requirementType" value="Other" ><label for="c17">Other </label></li>
								</ul>
				</div>
							</div>
							
						
						
						<div  class="col-md-2">
			<label><strong>Status</strong></label>
			<div class='clearfix'>	
			
								<ul id="status" class="list-unstyled">
									<li class="checkBox positionRelative"><input id="c6" type="checkbox"
										name="requirementStatus" value="NEW"><label for="c6">New<span id="newRequirementCount"></span></label></li>
									<li class="checkBox positionRelative"><input id="c7" name="requirementStatus" type="checkbox" value="DRAFT" ><label
										for="c7">Draft<span id="draftRequirementCount"></span></label></li>
									<li class="checkBox positionRelative"><input id="c8" type="checkbox"
										name="requirementStatus" type="checkbox" value="SUBMITTED" ><label for="c8">Submitted
											<span id="submittedRequirementCount"></span>
									</label></li>
									<li class="checkBox positionRelative"><input id="c9" type="checkbox"
										name="requirementStatus" type="checkbox" value="OPEN" ><label for="c9">Open
											<span id="openRequirementCount"></span>
									</label></li>
									<li class="checkBox positionRelative"><input id="c10" type="checkbox"
										name="requirementStatus" type="checkbox" value="ONHOLD" ><label for="c10">On
											Hold <span id="onHoldRequirementCount"></span>
									</label></li>
									<li class="checkBox positionRelative"><input id="c11" type="checkbox"
										name="requirementStatus" type="checkbox" value="CANCEL" ><label for="c11">Cancel
											<span id="cancelRequirementCount"></span>
									</label></li>
									<li class="checkBox positionRelative"><input id="c12" type="checkbox"
										name="requirementStatus" type="checkbox" value="FILLEDCLOSED" ><label for="c12">Filled
											Closed<span id="filledClosedRequirementCount"></span>
									</label></li>
									<li class="checkBox positionRelative"><input id="c13" type="checkbox"
										name="requirementStatus" type="checkbox" 
										value="UNFILLEDCLOSED" ><label
										for="c13">UnFilled Closed<span
											id="unFilledClosedRequirementCount"></span></label></li>
									<li class="checkBox positionRelative"><input id="c14" type="checkbox"
										name="requirementStatus" type="checkbox" value="REJECTED" ><label for="c14">Rejected<span
											id="rejectedRequirementCount"></span></label></li>
									<li class="checkBox positionRelative"><input id="c15" type="checkbox"
										name="requirementStatus" type="checkbox" value="CLOSED" ><label for="c15">Closed<span
											id="closedRequirementCount"></span></label></li>
								</ul>
							</div>
			
			</div>
							
							
							
							
						<div class="col-md-3 mb positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myClientInput" onkeyup="myClientFunction()" placeholder="Clients" title="Type in a teams">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myClientUL">
									<c:forEach var="team" items="${add_clientList}">
									
									<c:if test="${team.locationId.id == loginOrgLocId}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="clientNameById" value ="${team.responderLocationId.org.orgName}" ><label>${team.responderLocationId.org.orgName}</label></li>
									</c:if>
									
									<c:if test="${team.responderLocationId.id == loginOrgLocId}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="clientNameById" value ="${team.locationId.org.orgName}" ><label>${team.locationId.org.orgName}</label></li>
									</c:if>
									
									</c:forEach>
								</ul>
							 </div> 
						</div>
			<!-- //projectList -->
			
			
			<div class="col-md-3 mb positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myProjectInput" onkeyup="myProjectFunction()" placeholder="Projects" title="Type in a teams">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myprojectUL">
									<c:forEach var="project" items="${projectList}">
									
									<li class="checkBox positionRelative"><input  type="checkbox" name="projectNameById" value ="${project.id}" ><label>${project.projectId.projectName}</label></li>
									
									<%-- <c:if test="${team.responderLocationId.id == loginOrgLocId}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="clientNameById" value ="${team.locationId.org.orgName}" ><label>${team.locationId.org.orgName}</label></li>
									</c:if> --%>
									
									</c:forEach>
								</ul>
							 </div> 
						</div>
			
			
			</div>
			</div>
			<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" 
								onclick="closeFilter();requirementList('list','${sourceleadUrl}');" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="resetJobFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
							
							
							
 
</div>
</div>
</div>

<script>
function myClientFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myClientInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myClientUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}


function myProjectFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myProjectInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myprojectUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}


$(document).keyup(function(e) {
	  if (e.keyCode === 27) {
		  closeFilter();
	  }
	});
</script>
<script>
function resetJobFilter(){
	$("ul#myprojectUL > li input:checkbox").prop("checked",false);
	$("ul#myClientUL > li input:checkbox").prop("checked",false);
	$("ul#jobs > li input:checkbox").prop("checked",false);
	$("ul#status > li input:checkbox").prop("checked",false);
	$("ul#jobType > li input:checkbox").prop("checked",false);
	$("ul#priority > li input:checkbox").prop("checked",false);
	$("ul#assignTo > li input:radio").prop("checked",false);
	
	requirementList('list','${sourceleadUrl}');
}
</script>
<script type="text/javascript">
	var ele = "";
	function showChangeJobFairStatusPopup(jobFairId, status, oldValue, element) {
		ele = element;
		var statusText = "";
		if(status === "0") {
			statusText = "Are you sure to make the JobFair status to 'CLOSE' ?";
		} else if(status === "1") {
			statusText = "Are you sure to make the JobFair status to 'OPEN' ?";
		} else {
			statusText = "Are you sure to delete the JobFair ?";
		}
		$("#changeStatusPopup p").html(statusText);
		$("#confirmId").data("job_fair_id", jobFairId);
		$("#confirmId").data("status",status);
		$("#cancelStatusPopup").data("old_value", oldValue);
		$("#changeStatusPopup").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	
	function closeChangeStatusPopup() {
		var oldValue = $("#cancelStatusPopup").data("old_value");
		$(ele).val(oldValue);
		$("#changeStatusPopup").slideUp(200);
		$(".project_inn_mask").fadeOut(200);
	}
	
	function changeJobFairStatus() {
		var jobFairId = $("#confirmId").data("job_fair_id");
		var status = $("#confirmId").data("status");
		var data = {
				jobFairId: jobFairId,
				status : status
		};
		$.post(sourceleadUrl + "/changeJobFairStatus?", data, function(result) {
			if (result.response === "SESSION_TIMEDOUT") {
				location.replace(sourceleadUrl);
			} else if (result.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				location.href = sourceleadUrl + "/jobs?viewTab=jobFair";
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
</script>

<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	


function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	


</script>

<jsp:include page="../masterFooter.jsp" />