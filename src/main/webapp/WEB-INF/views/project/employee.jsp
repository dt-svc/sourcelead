<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
           
<div class="side_mask">
    <div class="proj_close"></div>
</div> 
<div id="associatesList"></div>
 <input type="hidden" id="employeeEmptyListId" name="employeeEmptyListId">
 

 
 <script>
 function projectEmployee(projectId,startDateStr,endDateStr,status,projectName){
	 
	 var projectUUID = '${projectUUID}';
	 var sourceleadUrl = '${sourceleadUrl}';
	 var postedOrgLocationId = '${postedOrgLocationId}';
	 var loginOrgLocationId = '${SourceLeadWeb_orgLocations.id}'
	 var projectClientUUID = '${projectClientUUID}';
	 var postedSharedProjectOrgLocationId = '${postedSharedProjectOrgLocationId}';
	   $("#employeesLoadingImg").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); 
 	 
	   var resourceRequestsCount = '${resourceRequestsCount}';
	   var projectMembersCount = '${projectMembersCount}';
	   var projectBudgetCount = '${projectBudgetCount}';
	   
	   var result = "";
 	 
 	result = result + '    	<div class="col-md-12 mt">';
 	
	result = result + '     </div>';
	result = result + '       <div class="clearfix"></div>';
	result = result + '<div class="newPrTabs col-md-12">';
	result = result + '<ul class="newPrTabsMenu">';
	/* Dont delete this commented code. Functionality is in progress '+projectBudgetCount+' */
	 result = result + '<li class="current"><a href="#tab-b" id="resourceTab" onclick="resourceRequestsTab();">Resource Requests ('+resourceRequestsCount+')</a></li>'; 
	
	 if((postedOrgLocationId == loginOrgLocationId) || (postedSharedProjectOrgLocationId == loginOrgLocationId)){
		result = result + '<li class=""><a href="#tab-c" id="budgetRequestsTab" onclick="budgetRequests();">Budget Requests ';
		result = result + '(<b class="countClose">'+projectBudgetCount+'</b><input class="countShow" type="text" id="projectBudgetCount" readonly/>)';
		result = result + '</a></li>';
	 }
	result = result + '<div class="clearfix"></div>';
	result = result + '</ul>';
	
	result = result + '<div class="project_tabs mb">';
		
	     resourceRequestsTab();
	//	Resource Requests Tab
		result = result + '<div id="tab-b" class="newPrTabs_inn" style="display: block;">';
		result = result + ' <div id="resourceRequestsTabDiv"></div>';
		result = result + '</div>';
		
		result = result + '<div id="tab-c" class="newPrTabs_inn">';
		result = result + ' <div id="budgetRequestsTabDiv"></div>';
		result = result + '</div>';
	
	result = result + '</div>';
	
	result = result + '</div>';

	result = result + '</div>';
	result = result + ' <div class="clearfix"></div>';
		
		$("#tab-3").empty();  
		$("#tab-2").empty();  
	    $("#tab-1").empty();
	    $("#tab-4").empty();
	    $("#employeesLoadingImg").empty();
	    $("#tab-2").append(result);  
	    
	    $("#tab-2").show();
		$(".project-tabs-menu a").click(function(event) {
		     event.preventDefault();
		     $(this).parent().addClass("current");
		     $(this).parent().siblings().removeClass("current");
		     var tab = $(this).attr("href");
		     $(".project_tabs_inn").not(tab).css("display", "none");
		     $(tab).fadeIn();
		 });
		$("#tab-a").show();
	    $(".newPrTabsMenu a").click(function(event) {
	        event.preventDefault();
	        $(this).parent().addClass("current");
	        $(this).parent().siblings().removeClass("current");
	        var tab = $(this).attr("href");
	        $(".newPrTabs_inn").not(tab).css("display", "none");
	        $(tab).fadeIn();
	    });		
		
 }
 </script>
    
<script>
	function docMsgFadein3() {
		$("#selector3").fadeIn(200);
        $('#selector3').delay(2000).fadeOut('slow');
	}
</script> 

<!-- script for converting dateformat in datepicker -->
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<script> 
//  New project Tabs
	$("#tab-a").show();
    $(".newPrTabsMenu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".newPrTabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
</script>

<!-- script for dateformat in datepicker End -->


<!-- ====================================Resource RequestsTab Start=============================================== -->
<script>
function resourceRequestsTab(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var projectUUID = '${projectUUID}';
	 var projectClientUUID = '${projectClientUUID}';
	 
	 var postedOrgLocationId = '${postedOrgLocationId}';
	 var loginOrgLocationId = '${SourceLeadWeb_orgLocations.id}';
	 var postedSharedProjectOrgLocationId = '${postedSharedProjectOrgLocationId}';
	 
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  resourceReqListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  resourceReqListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  resourceReqListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  resourceReqListDate = 'YYYY-DD-MM'; 
	  }
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPlanningRequestListByProjectUUID?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID, 
	        success: function(data) {
	        	
	        	if (data && data.response === "INVALID_DATA") {
					$("#associateRequestLoading").removeAttr("style");
                    showError(data.responseMessage);
                } else if (data.response === "AWKWARD") {
                    location.href = "/errorPage";
                } else if (data.response === "SUCCESS") {
                	var json = data.data;
                	
	        	  var result = "";
	        	
	    		result = result + '	<div class="timesheet_head anchorTag-black">';
	    		result = result + '	<ul>';
	    		
	    		if('${isClientOrgActive}' == "N" || postedOrgLocationId == loginOrgLocationId || postedSharedProjectOrgLocationId == loginOrgLocationId){  
	    			+	    
	    		 '<sec:authorize access="hasRole(\'ROLE_ADD_RESOURCE_REQUEST\')">';
		    		result = result + '	      <li><a href="'+sourceleadUrl+'/requestAssociates?projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">';
		    		result = result + '	       <i class="glyphicon glyphicon-plus"></i>Associate Request</a></li>';
	    		 '</sec:authorize>';
	    		
	    		 '<sec:authorize access="hasRole(\'ROLE_ADD_REQUEST_ASSOCIATE\')">';
		    		result = result + '	      <li><a href="'+sourceleadUrl+'/newResourceRequest?projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">';
		    		result = result + '	       <i class="glyphicon glyphicon-plus"></i>New Resource Request</a>&nbsp; &nbsp;</li>';
	    		 '</sec:authorize>';
	    		}
		    		 
	    		result = result + '	</ul>';
	    		result = result + '	       </div>';
	    		result = result + '		<div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter overflow_inherint">';
	    		result = result + '		<table id="planningRequestsPaginationList" cellspacing="0" width="100%">';
	    		result = result + '	    <thead>';
	    		result = result + '	     	<tr>';
	    		result = result + '	         	<th>Request Id</th>';
	    		result = result + '	             <th>Req. Resources</th>';
	    		result = result + '	             <th>Allocated Resource</th>';
	    		result = result + '	             <th>Estimated Hours<br></th>';
	    		result = result + '	             <th>Estimated Budget<br></th>';
	    		result = result + '	             <th>%Of Work<br></th>';
	    		result = result + '	             <th>Req. On<br>(<small class="custom-small">'+resourceReqListDate+'</small>)</th>';
	    		result = result + '	             <th>Req. By</th>';
	    		/* result = result + '	             <th>Action</th>'; */
	    		result = result + '	         </tr>';
	    		result = result + '	    </thead>';
	    		result = result + '	 	<tbody>';
	    		
	    		for (var i=0; i<json.length; i++){
	    			var report = json[i];
	    			result = result + '<tr>';
	    			if(report.requestType == 'Associate Request'){
						if(report.requestStatus == 'Draft' || report.requestStatus == 'Rejected'){
							'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_RESOURCE_REQUEST\',\'ROLE_VIEW_EDIT_REQUEST_ASSOCIATE\')">';
							    result = result + '<td><div><a href="'+sourceleadUrl+'/editResourceRequest?requestUUID='+report.requestUUID+'&projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">'+report.requestId+'</a></div></td>';
							'</sec:authorize>';
							
							'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_RESOURCE_REQUEST\',\'ROLE_VIEW_EDIT_REQUEST_ASSOCIATE\')">';
							    result = result + '<td><div><a href="#">'+report.requestId+'</a></div></td>';
							'</sec:authorize>';
						}else{
							'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_RESOURCE_REQUEST\',\'ROLE_VIEW_EDIT_REQUEST_ASSOCIATE\')">';
							     result = result + '<td><div><a href="'+sourceleadUrl+'/viewResourceRequest?requestUUID='+report.requestUUID+'&projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">'+report.requestId+'</a></div></td>';
							'</sec:authorize>';
							
							'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_RESOURCE_REQUEST\',\'ROLE_VIEW_EDIT_REQUEST_ASSOCIATE\')">';
						        result = result + '<td><div>'+report.requestId+'</div></td>';
						    '</sec:authorize>';
						}
	    			}else{
	    				if(report.requestStatus == 'Draft' || report.requestStatus == 'Rejected'){
	    					result = result + '<td><div><a href="'+sourceleadUrl+'/editNewResourceRequest?requestUUID='+report.requestUUID+'&projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">'+report.requestId+'</a></div></td>';
						}else{
							result = result + '<td><div><a href="'+sourceleadUrl+'/viewNewResourceRequest?requestUUID='+report.requestUUID+'&projectUUID='+projectUUID+'&projectClientUUID='+projectClientUUID+'">'+report.requestId+'</a></div></td>';
						}
	    				
	    			}
		    		result = result + '	             <td><div>'+report.requestedResources+'</div></td>';
		    		result = result + '	             <td><div>'+report.allocatedResources+'</div></td>';
		    		result = result + '	             <td><div>'+report.estimatedHours+' hrs</div></td>';
		    		result = result + '	             <td><i class="pull-left"></i>'+report.estimatedBudgetCurrency+'<div class="pull-right">'+report.estimatedBudget+'</div></td>';
		    		result = result + '	             <td><div>'+(report.percentageOfWork != null ? report.percentageOfWork : '--') +'</div></td>';
		    		result = result + '	             <td><div class="tblminwidth-75">'+report.requestedDateStr+'</div></td>';
		    		result = result + '	             <td><div class="tbldata-dotdot-vsm">'+report.requestedUser+'</div></td>';
		    	
		    		/* result = result + '	             <td><div><b>'+report.requestStatus+'</b></div>';
		    		if(report.requestStatus == 'Rejected' && report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
		    			 result = result +"<i class='glyphicon glyphicon-comment edit-reason'><div class='edit-status'><p>"+report.rejectedReason+"</p></div></i>";
		    		}
		    		result = result + '</td>'; */
		    		result = result + '	         </tr>';
	    		}
	    		
	    		
	    		result = result + '	     </tbody>';
	    		result = result + '	 </table>';
	    		result = result + '</div>';
	    	    $("#resourceRequestsTabDiv").empty();
	    	    $("#resourceRequestsTabDiv").append(result); 
	    	    planningRequestsPagination();
	    	    
               }else if (data.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
                   showError("Operation failed, please try again");
               }
	        }
	   
     });
		 
}
</script>


<script>
function getAssociates(){
	var result = '';
	
	result = result +'<div class="clients_side_wrap">';
	result = result +'<div id="projectDetailsLoading"></div>';
	result = result +'<div class="new_side_wrap_inn">';
	result = result +'<div class="timesheet_head" style="border-bottom: 1px solid #ededed; background: #f5f5f5;">';
	      
	result = result +'<ul class="pull-right">';
	

	        
	result = result +'<li class="dropdown pull-left">';
	result = result +'<span class="dropdown-toggle mt5" id="menu1" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter mt5"></i><span>Location</span><i class="glyphicon glyphicon-play mt10"></i></span>|';
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu bootstrap_dropdownMenu-right" role="menu" aria-labelledby="menu1">';
	result = result +'<ul class="location-filter">';
	result = result +'<c:forEach var="locations" items="${orgLocationsList}">';
	result = result +'<li>';
	result = result +'<input class="ml10" value="${locations.id}" onclick="getOrgLocationProjects();"  name="orgLocations" ${SourceLeadWeb_orgLocations.id  == locations.id ? "checked" : ''} type="radio">';
	result = result +'<label class="radioAlign"><img src="/resources/images/${locations.country}.gif">${locations.orgLocationName}</label>';
	result = result +'</li>';
	result = result +'</c:forEach>';
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li>';
	
	result = result +'<li class="pull-left">';  
	result = result +'<select class="form-control chosen-select" name="orgLocationProjects" id="orgLocationProjects" onchange="getProjectDetails();">';
	result = result +'<option value="">-- Select Project --</option>';
	result = result +'</select>';
	result = result +'</li>';  
	

	
	result = result +'<div class="clearfix"></div>';
	result = result +'</ul>';
	result = result +'<div class="clearfix"></div>';
	result = result +'</div>';
	
	result = result +'<div id="projectDetailsDiv"></div>';
	result = result +'<div id="membersChoicepopUp"></div>';
	result = result +'</div>';
	result = result +'</div>';
	
	
	 $("#associatesList").empty();
     $("#associatesList").append(result);
     
     $(".clients_side_wrap").addClass("clients_side_wrap_active");
	 $(".side_mask").fadeIn(200);
	 $("body").css('overflow-y', 'hidden');
    
	 prepareChosenScripts();
     getOrgLocationProjects();
     getProjectDetails();
 	
}
</script>


<script type="text/javascript">
function getOrgLocationProjects(){
	emptyRecords();
	var orgLocations = [];
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocations.push($(this).val());
	    });
	
	 var sourceleadUrl = '${sourceleadUrl}';
	 var projectClientUUID = '${projectClientUUID}';
	 $("#projectDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getProjectsListByOrgLocationForTransfer?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocations+"&projectClientUUID="+projectClientUUID, 
	        success: function(json) {
	        
	        	var output = "";
	        	output = output + '<option value="">--Select Project--</option>';
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		output = output + '<option value="'+opt.id+'">'+opt.projectName+'</option>';
				}
	        	
	        	$("#orgLocationProjects").empty();
		        $("#orgLocationProjects").append(output);
		        $('#orgLocationProjects').trigger("chosen:updated");
		        $("#projectDetailsLoading").removeAttr("style"); 
	        }
	   
     });
		 
}
</script>

<script>
function getProjectDetails(){
	var orgLocationProjects = document.getElementById("orgLocationProjects").value;
	var orgLocations = [];
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocations.push($(this).val());
	    });
	
	if(orgLocationProjects){
		$("#projectDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl = '${sourceleadUrl}';
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getProjectDetailsForResouceTransfer?${_csrf.parameterName}=${_csrf.token}&projectId="+ orgLocationProjects+"&orgLocationId="+orgLocations,
					success : function(json) {
						var endDateStr = "";
						var description = "";
						var clientName = "";
						var projectSequeneNumber = "";
						var invoiceFrequency = "";
						
						
					
						if (json.endDateStr != null && json.endDateStr != ''
								&& json.endDateStr != 'undefined') {
							endDateStr = json.endDateStr;
						} else {
							endDateStr = "--";
						}
						if (json.description != null && json.description != ''
								&& json.description != 'undefined') {
							description = json.description;
						} else {
							description = "----";
						}
						
						if (json.invoiceFrequency != null && json.invoiceFrequency != '' && json.invoiceFrequency != 'undefined') {
							invoiceFrequency = json.invoiceFrequency;
					     } else {
					    	 invoiceFrequency = "----";
					    }
						
						if (json.projectSequeneNumber != null
								&& json.projectSequeneNumber != ''
								&& json.projectSequeneNumber != 'undefiend') {
							projectSequeneNumber = json.projectSequeneNumber;
						} else {
							projectSequeneNumber = "--";
						}
						if (json.client != null && json.client != ''
								&& json.client != 'undefined') {
							clientName = json.client
						} else {
							clientName = "--";
						}
						
						if (json.overtimeRateType != null && json.overtimeRateType != '' && json.overtimeRateType != 'undefined') {
							flatPercentageId = json.overtimeRateType
						} else {
							flatPercentageId = "";
						}
						
						var proStatus ='';
						if(json.projectStatus == 'NOTSTARTED'){
							proStatus = 'Not Started';
						}else if(json.projectStatus == 'INPROGRESS'){
							proStatus = 'In-Progress';
						}else if(json.projectStatus == 'ONHOLD'){
							proStatus = 'On-Hold';
						}else if(json.projectStatus == 'COMPLETED'){
							proStatus = 'Completed';
						}else if(json.projectStatus == 'DROPPED'){
							proStatus = 'Dropped';
						}
						
						
						
						var result = "";
						result = result	+ '<div class="project_tabs_inn_table">';
	   		    		
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>Status:</strong><span>';
						result = result +  proStatus;
						result = result + '</span>';
						result = result	+ '</div><div class="clearfix"></div>';
						
						result = result + '<input type="hidden" name="dbProjectStatus" id="dbProjectStatus" value="'+json.projectStatus+'" >';
						result = result + '<div class="clearfix"></div>';

						result = result	+ '<div class="project_details_cta" id="ProjectIdDiv">';
						result = result + '<strong>Project ID:</strong>';
						result = result + '<span>'+projectSequeneNumber+'</span>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';

						result = result	+ '	<div class="project_details_cta" id="projectNameId">';
						result = result + '	<strong>Project Name:</strong>';
						result = result + '	<span title="'+json.projectName+'">'+json.projectName+'</span><div class="clearfix"></div>';
						result = result + '	</div>';

					
						result = result + '<div class="project_details_cta">';
						result = result + '	<strong>Manager 1 Name:</strong>';
						result = result + '	<span title="'+json.manager1Name+'">'+json.manager1Name+'</span><div class="clearfix"></div>';
						result = result + '	</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '	<strong>Manager 2 Name:</strong>';
						if(json.manager2Name != null && json.manager2Name != 'undefined' && json.manager2Name != ''){
							var manager2Name = json.manager2Name;
						}else{
							var manager2Name = '------';
						}
						result = result + '	<span title="'+manager2Name+'">'+manager2Name+'</span><div class="clearfix"></div>';
						result = result + '	</div>';

						
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client:</strong>';
						result = result + '<span title="'+clientName+'">'+clientName+'</span><div class="clearfix"></div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						
						
						
						result = result + '<div class="clearfix"></div>';
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>Start date:</strong>';
						result = result + '<span title="'+json.startDateStr+'">'+json.startDateStr+'</span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result	+ '<div class="project_details_cta">';
						result = result + '<strong>End date:</strong>';
						result = result + '<span title="'+endDateStr+'">'+endDateStr+'</span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="clearfix"></div>';
						
						result = result + '<div class="clearfix"></div>';
						
						result = result + '<div class="form-footer clearfix miniFoot">';
						result = result + '<div class="col-lg-12">';
						result = result + '<div class="pull-right">';
						result = result + '<input type="button" value="Transfer" onClick="transferToProject()" class="btn btn-success ml10">';
						result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
						
						result = result + '</div>';
						result = result + ' </div>';
						result = result + '</div>';

						$("#projectDetailsDiv").empty();
						$("#projectDetailsDiv").append(result);
						$("#projectDetailsLoading").removeAttr("style"); 

					}
				});
	}else{
		emptyRecords();
	}
	
}
</script>

<script>
function emptyRecords(){
	var result = '';
	 result = result+"   <div class='row'>"; 
	 result = result+"	<div class='col-lg-12'>"; 
	 result = result+"		<div class='dataTables_empty'>"; 
	 result = result+"		</div>"; 
	 result = result+"	</div>"; 
	 result = result+"  </div> ";
	 
	 $("#projectDetailsDiv").empty();
    $("#projectDetailsDiv").append(result);
}
</script>


<script>
function transferToProject(){
	var projectMembers = [];
	 $("input[name='projectMembers[]']:checked").each( function (i) {
		 projectMembers.push($(this).val());
	});
	 
	var orgLocations = [];
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocations.push($(this).val());
	    });
	 
	 var projectUUID = '${projectUUID}';
	 var projectOrgLocationId = document.getElementById("orgLocationProjects").value;
	 var projectId = document.getElementById("orgLocationProjects").value;
	 if(projectMembers.length > 0){
		 
		 $("#projectDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 var projectUUID = '${projectUUID}';
		 var sourceleadUrl = '${sourceleadUrl}';
		 var projectClientUUID = '${projectClientUUID}';
		 
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/transferProjectMembers?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID+"&projectId="+projectId+"&projectOrgLocationId="+orgLocations+"&projectMembers="+projectMembers, 
		        success: function() {
		        
		        	location.replace(sourceleadUrl+"/projectSummary?projectUUID="+projectUUID+"&projectClientUUID="+projectClientUUID+"&tabType=team");
			        $("#projectDetailsLoading").removeAttr("style"); 
		        }
		   
	     });
			 
		 
	 }else{
		 alertProjectMembersMessage();
	 }
	
}
</script>

<script>
function alertProjectMembersMessage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Select atleast one project member</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='popupClose();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#membersChoicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function cancel(){
	$("#membersChoicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	
}
</script>

<script>
function popupClose(){
	
	$("#membersChoicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	
	$(".clients_side_wrap").removeClass("clients_side_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
    $('.addcandidate-popup').fadeOut(200);
}
</script>
	


<script type="text/javascript">
 function prepareChosenScripts(){ 
		var config = {
				  '.chosen-select'           : {},
				  '.chosen-select-deselect'  : {allow_single_deselect:true},
				  '.chosen-select-no-single' : {disable_search_threshold:10},
				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				  '.chosen-select-width'     : {width:"95%"}
				}
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({'width': '100%'});
					});
 }
</script>


  <script type="text/javascript">
    $(document).ready(function() {
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
        $('.addcandidate-popup').fadeOut(200);
	});	
});
</script>

<script>
function planningRequestsPagination(){  
    $('#planningRequestsPaginationList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 25, 30, -1], [5, 15, 25, 30, "All"]],
		"aaSorting": [[4,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#planningRequestsList').wrap("<div class='overflow_Auto'></div>");
}
</script>
<!-- ====================================Resource RequestsTab End=============================================== -->




<!-- ====================================Budget Requests Tab End=============================================== -->



<script>
function budgetRequests(){
	$(".countClose").hide();
	$(".countShow").show();
	 var sourceleadUrl ='${sourceleadUrl}';
	 var projectUUID = '${projectUUID}';
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getBudgetRequestListByProjectUUID?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID, 
	        success: function(data) {
	        	if (data && data.response === "INVALID_DATA") {
						$("#associateRequestLoading").removeAttr("style");
	                    showError(data.responseMessage);
	                } else if (data.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (data.response === "SUCCESS") {
	                	var json = data.data;
			        	var result = "";
			    		result = result + '	<div class="timesheet_head anchorTag-black">';
			    		result = result + '	<ul>';
			    		result = result + '	<li><a href="javascript:void(0)" onclick="addBudgetRequestPopUp();getApproversList();"><i class="glyphicon glyphicon-plus paddingRight"></i>Budget Request</a></li>';
			    		result = result + '	</ul>';
			    		result = result + '	       </div>';
			    		result = result + '	       <div id="budgetRequestPopUpid"></div>';
			    		result = result + '	       <div id="editBudgetRequestPopUpid"></div>';
			    		result = result + '		<div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter overflow_inherint">';
			    		result = result + '		<table id="projectBudgetRequestList" cellspacing="0" width="100%">';
			    		result = result + '	    <thead>';
			    		result = result + '	     	<tr>';
			            result = result + '	 	<th>Request Id</th>';
			            result = result + '	   <th>Description</th>';
			            result = result + '	   <th>Estimated Budget</th>';
			            result = result + '	   <th>Estimated Hours</th>';
			            result = result + '	   <th>Date</th>';
			            result = result + '	   <th>Initiator</th>';
			            result = result + '	   <th>Action</th>';
			    		result = result + '	         </tr>';
			    		result = result + '	    </thead>';
			    		result = result + '	 	<tbody>';
			    		for(i=0;i<json.length;i++){
			    			var report = json[i];
			    			if(report.estimatedBudgetValue != null  && report.estimatedBudgetValue != "" && report.estimatedBudgetValue != "undefined"){
			    				estimatedBudgetValue = report.estimatedBudgetValue;
			    			}else{
			    				estimatedBudgetValue = "--";
			    			}
			    			
			    			if(report.estimatedHours != null && report.estimatedHours != "" && report.estimatedHours !="undefined"){
			    				estimatedHours = report.estimatedHours;
			    			}else{
			    				estimatedHours = "--";
			    			}
			    			if(report.budgetDescription != null && report.budgetDescription != "" && report.budgetDescription !="undefined"){
			    				budgetDescription = report.budgetDescription;
			    			}else{
			    				budgetDescription = "--";
			    			}
			    			result = result + '	<tr>';
			    			if((report.approverUserIdlLong == '${SourceLeadWeb_user.id}') && (report.budgetStatus == "On Hold" || report.budgetStatus == "Rejected" || report.budgetStatus == "Waiting For Approval")){
			    				result = result + ' 	<td><div class="tbldata-dotdot-sm"> <div class="tbldata-dotdot-100 pull-left">'+report.budgetRequestedId+'</div> <i class="fa fa-pencil ml10 pull-right" onclick="editBudgetrequest('+report.id+')"></i></div></td>';
			    			}else{
			    				result = result + '  <td><div class="tbldata-dotdot-sm"> <div class="tbldata-dotdot-100 pull-left">'+report.budgetRequestedId+'</div></div></td>';
			    			}
							
							result = result + '     <td><div class="tbldata-dotdot-me" title=\''+budgetDescription+'\'>'+budgetDescription+'</div></td>';
							result = result + '     <td><div class="tbldata-dotdot_80" title="'+estimatedBudgetValue+'">'+estimatedBudgetValue+'</div></td>';
							result = result + '     <td><div  class="tbldata-dotdot_80">'+estimatedHours+'</div></td>';
							result = result + '     <td><div  class="tbldata-dotdot_80">'+report.createdStr+'</div></td>';
							result = result + '     <td><div  class="tbldata-dotdot-vsm" title="'+report.createdUserName+'">'+report.createdUserName+'</div></td>';
							if(report.budgetStatus == "Waiting For Approval"){
								result = result + '     <td><div class="tblminwidth-90">';
								result = result + '     <i class="fa fa-check" alt="Approve" title="Approve" onclick="acceptBudgetRequset('+report.id+')"></i>|';
								result = result + '     <i class="fa fa-times" alt="Rejected" title="Reject" onclick="deleteBudgetRequaet('+report.id+');"></i>|';
								result = result + '     <i class="fa fa-clock-o" alt="On hold" title="On hold" onclick="onholdBudgetRequaet('+report.id+');"></i></div></td>';
							}
							if(report.budgetStatus == "Rejected"){
								result = result + '<td><div class="tblminwidth-90 paTp"><b class="Rejec">Rejected</b>';
								 result = result+"  <i class='glyphicon glyphicon-comment colB' id='"+report.id+"' onclick='rejectedMessage(\""+report.rejectedReason+"\");'>";
							 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+report.id+"'>"+report.rejectedReason+"</div></i>";
							 	 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
								result = result + '</div></td>';
							}
							if(report.budgetStatus == "Approved"){
								result = result + '<td><div class="tblminwidth-90 paTp"><b>Approved</b>';
								 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
								 result = result + '</div></td>';
							}
							
							if((report.approverUserIdlLong ==  '${SourceLeadWeb_user.id}') &&  report.budgetStatus == "On Hold"){
								result = result + '     <td><div class="tblminwidth-75">';
								result = result + '     <i class="fa fa-check" alt="Approve" title="Approve" onclick="acceptBudgetRequset('+report.id+')"></i>|';
								result = result + '     <i class="fa fa-times" alt="Rejected" title="Reject" onclick="deleteBudgetRequaet('+report.id+');"></i>';
								result = result + '     </div></td>';
								
							}
							if(report.budgetStatus == "On Hold" && (report.approverUserIdlLong !=  '${SourceLeadWeb_user.id}')){
								result = result + '<td><div class="tblminwidth-90 paTp"><b class="processs-hold">On Hold</b>';
								 result = result+"  <i class='glyphicon glyphicon-comment colB' id='"+report.id+"' onclick='rejectedMessage(\""+report.rejectedReason+"\");'>";
							 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+report.id+"'>"+report.rejectedReason+"</div></i>";
							 	 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
								result = result + '</div></td>';
							}
							
							result = result + ' </tr>';
			    		}
			    		result = result + '	     </tbody>';
			    		result = result + '	 </table>';
			    		result = result + '</div>';
			    	    $("#budgetRequestsTabDiv").empty();
			    	    $("#budgetRequestsTabDiv").append(result); 
			    	    
			    	    $("#projectBudgetCount").val(json.length); 
	    	             budgetRequestPagination();
	                }else if (data.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
					} else {
	                    showError("Operation failed, please try again");
	                }
	        }
	   
     });
}
$(document).on("mouseover", ".glyphicon-comment", function() { 
	var localId = $(this).attr('id');
	$("#rejectedMessage"+localId).addClass('app_error_mes_active');
	});	
	$(document).on("mouseleave", ".glyphicon-comment", function() { 
	var localId = $(this).attr('id');
	$("#rejectedMessage"+localId).removeClass('app_error_mes_active');
});	
	
function rejectedMessage(local){ 
	   $("#"+local).slideToggle(200);
	   $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
	       e.stopPropagation();
	   });	
	   
	}
</script>
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
<script>
function deleteBudgetRequaet(budgetRequestedId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<form id='deleteBudget_form' name='deleteBudget_form' >";
	result = result +"<input type='hidden' id='budgetRequestedId' name='budgetRequestedId' value='"+budgetRequestedId+"'>";
	result = result +"<input type='hidden' id='loginUserid' name='loginUserid' value='${SourceLeadWeb_user.id}'>";
	result = result +"		<p>Are you sure you want to reject ?</p>";
	result = result +"		<textarea  id='reasonForProjectBudget' name='reasonForProjectBudget'  placeholder='Comments here' field-name='Reason' data-validation='required validate_length length2-5000'></textarea>";
	result = result +"</form>";
	result = result +'		<b href="javascript:void(0)"><input id="delete_budget-id" type="button" value="Reject" onclick="saveBudgetReason('+budgetRequestedId+');"><input class="u_can_color" type="button"  value="Cancel" onClick="CancelTermination();"></b>';
	result = result +"	</div>";

	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200); 


}
function CancelTermination(){
	$("#popUp").empty();
	$("#remove_user_pop, .project_inn_mask").fadeOut();
	$(".project_inn_mask").slideUp(200);
	}
function saveBudgetReason(budgetRequestedId){
	if ($('#deleteBudget_form').validate(false, validationSettings)) {
	var sourceleadUrl = '${sourceleadUrl}';
     $("#delete_budget").attr("disabled", "disabled");
	 $("#delete_budget").attr("disabled", "disabled");
	 var formData = getFormData("deleteBudget_form");
	 $.ajax({
				type : "POST",
				data :formData,
				url : sourceleadUrl+"/saveBudgetRequestReason?${_csrf.parameterName}=${_csrf.token}",
				success : function(result) {
					$("#popUp").empty();
	   			 	$(".project_inn_mask").fadeOut(200);
	   			   cancelTermination();
	   			 	budgetRequests();
				},
			}); 
	 return true;
	}else{
		return false;
}}
/* $('body').on('blur', '#deleteBudget_form', function() {
	$("#deleteBudget_form").showHelpOnFocus();;
	$("#deleteBudget_form").validateOnBlur(false, validationSettings);  
}); 
 */
function acceptBudgetRequset(budgetRequestedId){
	var sourceleadUrl = '${sourceleadUrl}';
	var userId = '${SourceLeadWeb_user.id}';
	 $.ajax({
				type : "POST",
				url : sourceleadUrl+"/acceptBudgetRequest?${_csrf.parameterName}=${_csrf.token}&budgetRequestedId="+budgetRequestedId+"&userId="+userId,
				success : function(result) {
					budgetRequests();
				},
			}); 
}
function onholdBudgetRequaet(budgetRequestedId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<form id='onHoldBudget_form' name='onHoldBudget_form' >";
	result = result +"<input type='hidden' id='budgetRequestedId' name='budgetRequestedId' value='"+budgetRequestedId+"'>";
	result = result +"<input type='hidden' id='loginUserid' name='loginUserid' value='${SourceLeadWeb_user.id}'>";
	result = result +"		<p>Are you sure you want to On-Hold ?</p>";
	result = result +"		<textarea  id='reasonForProjectBudget' name='reasonForProjectBudget'  placeholder='Comments here' field-name='Reason' data-validation='required validate_length length2-5000'></textarea>";
	result = result +"</form>";
	result = result +'		<b href="javascript:void(0)"><input id="onHold_budget-id" type="button" value="On-Hold" onclick="onHoldBudgetRequest('+budgetRequestedId+');"><input class="u_can_color" type="button"  value="Cancel" onClick="CancelTermination();"></b>';
	result = result +"	</div>";

	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	
}
function onHoldBudgetRequest(budgetRequestedId){
	if ($('#onHoldBudget_form').validate(false, validationSettings)) {
		var sourceleadUrl = '${sourceleadUrl}';
	     $("#onHold_budget").attr("disabled", "disabled");
		 $("#onHold_budget").attr("disabled", "disabled");
		 var formData = getFormData("onHoldBudget_form");
	 $.ajax({
				type : "POST",
				data :formData,
				url : sourceleadUrl+"/onHoldBudgetRequest?${_csrf.parameterName}=${_csrf.token}",
				success : function(result) {
					$("#popUp").empty();
	   			 	$(".project_inn_mask").fadeOut(200);
	   			 	budgetRequests();
				},
			}); 
	 return true;
	}else{
		return false;
}}
function editBudgetrequest(budgetRequestedId){
	 $("#budgetRequestPopUpid").empty();
	var sourceleadUrl = '${sourceleadUrl}';
	 
	 $.ajax({
				type : "POST",
				url : sourceleadUrl+"/getBudgetRequestDetailsById?${_csrf.parameterName}=${_csrf.token}&budgetRequestedId="+budgetRequestedId,
				success : function(json) {
					var result = "";
					result = result + '	<div class="collapse-div-adduser prRight col-md-4 show-div prRight_new"> <i class="fa fa-close close-div" onClick="editCloseDiv();"></i>';
					result = result + '		<div class="form-horizontal clearfix">';
					result = result + '<form id="budgetRequest-form" name="budgetRequest-form">';
					result = result + '	    <input type="hidden" id="projectUUID" name="projectUUID" value="${projectUUID}">';
					result = result + '	    <input type="hidden" id="userId" name="loginUserid" value="${SourceLeadWeb_user.id}">';
					result = result + '	    <input type="hidden" id="budgetRequestedId" name="budgetRequestedId" value="'+json.id+'">';
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + '	        <input type="text" class="form-control" value=\''+json.budgetDescription+'\' id="baseLine" name="baseLine" placeholder="Descrption *" field-name="Description" data-validation="required validate_Space validate_length length1-250">';
					result = result + '	      </div>';
					result = result + '	    </div>    ';                        
				 
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12" id="bgApErrMsg">';
					result = result + '	        <select class="chosen-select" field-name="Approver" id="budgetApprover" name="budgetApprover" data-validation="required" onchange="removeErrMsg();">';
					result = result + '	        </select>';
					result = result + '	        </div>';
					result = result + '	      </div>'; 
					 
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + '	        <input type="text" class="form-control inlineField" value="'+json.estimatedHours+'" id="estimatedHours" placeholder="Estimated Hours *" name="estimatedHours" field-name="Estimated Hours"  data-validation="required validate_length length1-10 validate_int">';
					result = result + '	      </div>';
					result = result + '	    </div>';
				  
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + ' <div class="input-group">';
					result = result + '  <div class="input-group-btn tblminwidth-75" style="min-width: 65px !important;">';
					result = result + '  <input type="text" class="pull-left btn btn-primary btn-block" id="estimatedBudgetCurrency" name="estimatedBudgetCurrency" value="${projectCurrency}" readonly>';
					result = result + ' </div>';
					result = result + '	        <input type="text" class="form-control " value="'+json.estimatedBudgetValue+'" id="estimatedBudget" name="estimatedBudget" placeholder="Estimated Budget *" field-name="Estimated Budget" data-validation="required validate_Space validate_float validate_length length1-16">';
					result = result + ' </div>';
					result = result + ' </div>';
					
					result = result + '	    <div class="clearfix mt10 mb10"></div>';
					 
					result = result + '	    <div class="clearfix mr">';
					result = result + '	      <button type="button" class="btn btn-sm btn-primary pull-right" id="save_budget" onclick="saveBudgetRequest();">Update</button>';
					result = result + '	    </div>';
					result = result + '	  </div>';
					result = result + '</form>';
					result = result + '	</div>';
					 $("#editBudgetRequestPopUpid").empty();
					 $("#editBudgetRequestPopUpid").append(result);
					 $("#editBudgetRequestPopUpid").show();
					 getApproversList(json.approverUserIdlLong);
					 $('#estimatedBudgetCurrency').selectpicker('refresh');
				    var config = {
						  '.chosen-select'           : {},
						  '.chosen-select-deselect'  : {allow_single_deselect:true},
						  '.chosen-select-no-single' : {disable_search_threshold:10},
						  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
						  '.chosen-select-width'     : {width:"95%"}
						}
						for (var selector in config) {
						  $(selector).chosen(config[selector]);
						}
						$('.chosen-select').each(function() {
							var $this = $(this);
							$this.next().css({'width': '100%'});
							});
				},
			}); 
	 
	
			
}
function editCloseDiv(){
	$("#editBudgetRequestPopUpid").hide();
}
</script>
<script>
function budgetRequestPagination(){  
    $('#projectBudgetRequestList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 25, 30, -1], [5, 15, 25, 30, "All"]],
		"aaSorting": [[4,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#planningRequestsList').wrap("<div class='overflow_Auto'></div>");
}
</script>
<script>
function cancelTermination(){
	$("#popUp").empty();
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>
<script>
function addBudgetRequestPopUp(){
	var result = "";
	result = result + '	<div class="collapse-div-adduser prRight col-md-4 show-div prRight_new"> <i class="fa fa-close close-div" onClick="closeDiv();"></i>';
	result = result + '		<div class="form-horizontal clearfix">';
	result = result + '<form id="budgetRequest-form" name="budgetRequest-form">';
	result = result + '	    <input type="hidden" id="projectUUID" name="projectUUID" value="${projectUUID}">';
	result = result + '	    <input type="hidden" id="userId" name="loginUserid" value="${SourceLeadWeb_user.id}">';
	result = result + '	    <div class="clearfix mb10">';
	result = result + '	    <div class="clearfix mb10">';
	result = result + '	      <div class="col-md-12">';
	result = result + '	        <input type="text" class="form-control input-sm" value="" id="baseLine" name="baseLine" placeholder="Description *" field-name="Description" data-validation="required validate_Space validate_length length1-250">';
	result = result + '	      </div>';
	result = result + '	    </div>    ';                        
 
	result = result + '	    <div class="clearfix mb10">';
	result = result + '	      <div class="col-md-12" id="bgApErrMsg">';
	result = result + '	        <select class="chosen-select" field-name="Approver" id="budgetApprover" name="budgetApprover" data-validation="required" onchange="removeErrMsg()">';
	result = result + '	        </select>';
	result = result + '	        </div>';
	result = result + '	      </div>'; 
	 
	result = result + '	    <div class="clearfix mb10">';
	result = result + '	      <div class="col-md-12">';
	result = result + '	        <input type="text" class="form-control input-sm inlineField" value="" id="estimatedHours"  placeholder="Estimated Hours *" name="estimatedHours"  field-name="Estimated Hours"  data-validation="required validate_length length1-10 validate_int">';
	result = result + '	      </div>';
	result = result + '	    </div>';
 
	result = result + '	    <div class="clearfix mb10">';
	result = result + '	      <div class="col-md-12">';
	result = result + ' <div class="input-group errorInptgroup">';
	result = result + '  <div class="input-group-btn tblminwidth-75" style="min-width: 65px !important;">';
	result = result + '  <input type="text" class="pull-left btn btn-primary btn-block" id="estimatedBudgetCurrency" name="estimatedBudgetCurrency" value="${projectCurrency}" readonly>';
	result = result + ' </div>';
	result = result + ' 	<input type="text" value="" id="estimatedBudget" name="estimatedBudget" class="form-control" placeholder="Estimated Budget *"';
	result = result + ' 		data-validation="required validate_Space validate_float validate_length length1-16" field-name="Estimated Budget" >';
	result = result + ' </div>';
	result = result + ' </div>';
	
	result = result + '	    </div>';                                                                                    
	 
	result = result + '	    <div class="clearfix mr">';
	result = result + '	      <button type="button" class="btn btn-sm btn-primary pull-right" id="save_budget" onclick="saveBudgetRequest();">Add</button>';
	result = result + '	    </div>';
	result = result + '	  </div>';
	result = result + '</form>';
	result = result + '	</div>';
	 $("#budgetRequestPopUpid").empty();
	 $("#budgetRequestPopUpid").append(result);
	 $("#budgetRequestPopUpid").show();
	 $("#editBudgetRequestPopUpid").empty();
	 document.getElementById("editBudgetRequestPopUpid").style.display="none";
	 $('#estimatedBudgetCurrency').selectpicker('refresh');
	 var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				});
			
}
function closeDiv(){
	$("#budgetRequestPopUpid").hide();
}
function removeErrMsg(){
	$("#bgApErrMsg").find("p.jquery_form_error_message").remove(); 
}
</script>
<script>
function getApproversList(approverUserId){
var sourceleadUrl ='${sourceleadUrl}';
var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
var actionType = "ROLE_BUDGET_APPROVER";

	$.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getSourceLeadActionApproversList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId+"&actionType="+actionType,
	    		success: function(json) {
    			var output = "";
     			output = output + "<option value=''>--Select Approver--</option>";
	     		for (var i=0; i<json.length; i++)
	     		{
		     		var opt = json[i];
		     		
		     		 if(opt.id == approverUserId){
		        			output = output + "<option value='"+opt.id+"' selected>"+opt.name+" ("+opt.userRole+")</option>";
		        		}else{
		        			output = output + "<option value='"+opt.id+"'> "+opt.name+" ("+opt.userRole+")</option>";
		        		}
		     		 
	     		}
	     		$("#budgetApprover").empty();
	     		$("#budgetApprover").append(output);
	     		$('#budgetApprover').trigger("chosen:updated");
	         }
	 });
}
</script>
<script>
function saveBudgetRequest(){
	 var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
		}; 
	
	 if ($('#budgetRequest-form').validate(false, validationSettings)) {
			 var sourceleadUrl = '${sourceleadUrl}';
			 $("#save_budget").attr("disabled", "disabled");
			 $("#save_budget").attr("disabled", "disabled");
			 var formData = getFormData("budgetRequest-form");
			 $.ajax({
						type : "POST",
						data :formData,
						url : sourceleadUrl+"/saveBudgetRequest?${_csrf.parameterName}=${_csrf.token}",
						success : function(result) {
							if (result && result.response === "INVALID_DATA") {
  								$("#associateRequestLoading").removeAttr("style");
  			                    showError(result.responseMessage);
  			                } else if (result.response === "AWKWARD") {
  			                    location.href = "/errorPage";
  			                } else if (result.response === "SUCCESS") {
  			                	budgetRequests();
  			                } else if (result.indexOf('Sign In') !== -1) {
  								location.replace(sourceleadUrl);
  							} else {
  			                    showError("Operation failed, please try again");
  			                }
							
						},
					}); 
			return true;
		} else {
			return false;
		}
}
$('body').on('blur', '#budgetRequest-form', function() {
	$("#budgetRequest-form").showHelpOnFocus();;
	$("#budgetRequest-form").validateOnBlur(false, validationSettings);  
}); 
</script>
 


