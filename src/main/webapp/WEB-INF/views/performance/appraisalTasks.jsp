<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
function getAppraisalTaskDetails(){
	
	 document.getElementById("tasksWidget").className = 'activeApprisalwidget';
	 document.getElementById("feedbackWidget").className = 'activeApprisalwidget';
	 document.getElementById("goalsWidget").className = 'activeApprisalwidget';
	  
	var result = "";  
    result = result + '<div class="goalssummar_widget goalssummar_widgetPad clearfix">';
    result = result + '<div class="newRolLeft">';
    result = result + '<ul id="nav" class="clearfix">';
    result = result + '<li><a id="projectDetails" onclick="getProjectDetails(\''+assignedUUID+'\');" class="active">Projects</a></li>';
    result = result + '<li><a id="jobDetails" onclick="getJobsDetails(\''+assignedUUID+'\');" class="">Jobs</a></li>';
    result = result + '<li><a href="#" class="">General Tasks</a></li>';
    result = result + '</ul>';
    result = result + '</div>';
    
    result = result + '<div class="newRolLRight" id="tasksLoading">';
    result = result + '<div class="clearfix" id="tab-content">';
    result = result + '<div id="appraisalTasksDiv">';
    result = result + '</div>';
    result = result + '</div>';
    
    result = result + '</div>';
    
    $("#performanceAppraisalDiv").empty(); 
    $("#performanceAppraisalDiv").append(result);
    
    getProjectDetails(assignedUUID);
}


function getProjectDetails(assignedUUID){
	 document.getElementById("jobDetails").removeAttribute("class");
	 document.getElementById("projectDetails").className = 'active';
	 $("#appraisalTasksDiv").empty(); 
	 
    $("#tasksLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		  type: "GET",
		    url: pmsUrl+"/getAssignedFeedbackProjectTasksDetailsList?assignedUUID="+assignedUUID,
		     success :function(response) {
		    	 
		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	    		    showError(response.responseMessage);
	    		    $("#tasksLoading").removeAttr('style');
	    		}
        	else if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
        		
        	    if(response.object != null){
        	    	var result = "";  
        	    	result = result + '<div id="tab1" class="appraQueOut">';
        	    	result = result + '<ul class="assiTaskWrap clearfix">';
        	    	
        	    	result = result + '<li>';
        	    	result = result + '<p>Total Tasks</p>';
        	    	result = result + '<span>'+response.object.totalTasks+'</span>';
        	    	result = result + '</li>';
        	    	
    					var report = response.object;
    					if(report != null && report != "" && report != "undefined"){
    						for(var k in report.assignMembersMap){
    							result = result + '<li>';
    		        	    	result = result + '<p>'+k+'</p>';
    		        	    	result = result + '<span>'+report.assignMembersMap[k]+'</span>';
    		        	    	result = result + '</li>';
    		        	    	
    						}
    					}
        	    	
        	    	result = result + '</ul>'; 
        	    	
        	    	
        	    	result = result + '<span class="positionRelative reportHead clearfix"><h4 class="col-md-12">Assigned Projects</h4></span>';   
        	    	result = result + '<span class="project_tabs_inn_table">';
        	    	result = result + '<table>';
        	    	result = result + '<thead>';
        	    	result = result + '<tr>';
        	    	result = result + '<th>Project Title</th>';
        	    	result = result + '<th>Duration</th>';
        	    	result = result + '<th>Tasks Assigned</th>';
        			for (var i=0; i<1; i++)
       				{
    					var report = response.object;
    					if(report != null && report != "" && report != "undefined"){
    						for(var k in report.assignMembersMap){
    							result = result +'     <th>'+k+'</th>';
    						}
    					}
       				}
        			
        				
        	    	result = result + '</tr>';
        	    	result = result + '</thead>';
        	    	result = result + '<tbody>';
        	    	
        	    	
					for (var i=0; i< response.object.appraisalProjectsTasksList.length; i++){
						result = result + '<tr>';
						var report = response.object.appraisalProjectsTasksList[i];
						var duration = '';
						if(report.endDate != null && report.endDate != '' && report.endDate != 'undefined'){
							duration = report.startDate+" to "+report.endDate
						}else{
							duration = report.startDate
						}
        				
						result = result + '<td><span class="tbldata-dotdot">'+report.projectName+'</span></td>';
	        	    	result = result + '<td> <span class="tblminwidth-125">'+duration+'</span></td>';
	        	    	result = result + '<td><span class="tblminwidth-50 text-primary"><b>'+report.totalProjectTasks+'</b></span></td>';
	        	    	
	        	    	 for(var k in report.assignProjectsMap){
	        	    		 result = result + '<td><span class="tblminwidth-50">'+report.assignProjectsMap[k]+'</span></td>';
	 			        }
	        	    	 result = result + '</tr>';
        			}
        	    	
        	    	result = result + '</tbody>';
        	    	result = result + '</table>';
        	    	result = result + '</span>';
        	    	
        	    	
        	        result = result + '</div> ';
        	        $("#appraisalTasksDiv").append(result);
        	        
        	        $("#tasksLoading").removeAttr("style");
        	    }else{
        	    	 var result = "";  
        	    	 result = result + '<div class="odd"><div valign="top" colspan="3" class="dataTables_empty"></div></div>';
         	         $("#appraisalTasksDiv").append(result);
         	        $("#tasksLoading").removeAttr("style");
        	    }
        		
        	}else if (response.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
			},
	}); 
    
}


function getJobsDetails(assignedUUID){
	
	   document.getElementById("projectDetails").removeAttribute("class");
	   document.getElementById("jobDetails").className = 'active';
	   $("#appraisalTasksDiv").empty(); 
	
	$("#tasksLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		  type: "GET",
		    url: pmsUrl+"/getAssignedFeedbackJobsDetailsList?assignedUUID="+assignedUUID,
		     success :function(response) {
		    	 
		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	    		    showError(response.responseMessage);
	    		    $("#tasksLoading").removeAttr('style');
	    		}
        	else if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
        		
        	    if(response.object != null){
        	    	var result = "";  
        	    	 result = result + '<div id="tab1" class="appraQueOut">';
        	    	result = result + '<ul class="assiTaskWrap clearfix">';
        	    	
        	    	result = result + '<li>';
        	    	result = result + '<p>Submimissions</p>';
        	    	result = result + '<span>'+response.object.submittedCandidatesCount+'</span>';
        	    	result = result + '</li>';
        	    	
        	    	result = result + '<li>';
        	    	result = result + '<p>Offer Made</p>';
        	    	result = result + '<span>'+response.object.offerMadeCount+'</span>';
        	    	result = result + '</li>';
        	    	
        	    	
        	    	result = result + '<li>';
        	    	result = result + '<p>Offer Accepted</p>';
        	    	result = result + '<span>'+response.object.offerAcceptedCount+'</span>';
        	    	result = result + '</li>';
        	    	
        	    	result = result + '<li>';
        	    	result = result + '<p>Client Offer Made</p>';
        	    	result = result + '<span>'+response.object.clientOfferMadeCount+'</span>';
        	    	result = result + '</li>';
        	    	
        	    	result = result + '<li>';
        	    	result = result + '<p>Client Offer Accepted</p>';
        	    	result = result + '<span>'+response.object.clientOfferAcceptedCount+'</span>';
        	    	result = result + '</li>';
        	    	
        	    	result = result + '<li>';
        	    	result = result + '<p>Joined</p>';
        	    	result = result + '<span>'+response.object.joinedCount+'</span>';
        	    	result = result + '</li>';
        	    	
        	    	result = result + '</ul>'; 
        	    	
        	    	
        	    	result = result + '<span class="positionRelative reportHead clearfix"><h4 class="col-md-12">Assigned Jobs</h4></span>';   
        	    	result = result + '<span class="project_tabs_inn_table">';
        	    	result = result + '<table>';
        	    	result = result + '<thead>';
        	    	result = result + '<tr>';
        	    	result = result + '<th>Job Title</th>';
        	    	result = result + '<th>Client</th>';
        	    	result = result + '<th>Submissions</th>';
        	    	result = result + '<th>Offer Made</th>';
        	    	result = result + '<th>Offer Accepted</th>';
        	    	result = result + '<th>Client Offer Made</th>';
        	    	result = result + '<th>Client Offer Accepted</th>';
        	    	result = result + '<th>Joined</th>';
        				
        	    	result = result + '</tr>';
        	    	result = result + '</thead>';
        	    	result = result + '<tbody>';
        	    	
        	    	
					for (var i=0; i< response.object.appraisalJobDetailsList.length; i++){
						result = result + '<tr>';
						var report = response.object.appraisalJobDetailsList[i];
						result = result + '<td><span class="tbldata-dotdot" title="'+report.requirementName+'">'+report.requirementName+'</span></td>';
						if(report.clientName != null){
							result = result + '<td><span class="tbldata-dotdot-vsm" title="'+report.clientName+'">'+report.clientName+'</span></td>';
						}else{
							result = result + '<td><span class="tbldata-dotdot-vsm" >----</span></td>';
						}
	        	    	result = result + '<td><span class="tblminwidth-50 text-primary"><b>'+report.submittedCandidatesCount+'</b></span></td>';
	        	    	result = result + '<td><span class="tblminwidth-50"><b>'+report.offerMadeCount+'</b></span></td>';
	        	    	result = result + '<td><span class="tblminwidth-50"><b>'+report.offerAcceptedCount+'</b></span></td>';
	        	    	result = result + '<td><span class="tblminwidth-50"><b>'+report.clientOfferMadeCount+'</b></span></td>';
	        	    	result = result + '<td><span class="tblminwidth-50"><b>'+report.clientOfferAcceptedCount+'</b></span></td>';
	        	    	result = result + '<td><span class="tblminwidth-50"><b>'+report.joinedCount+'</b></span></td>';
	        	    	result = result + '</tr>';
        			}
        	    	
        	    	
        	    	
        	    	 
        	    	
        	    	result = result + '</tbody>';
        	    	result = result + '</table>';
        	    	result = result + '</span>';
        	        result = result + '</div> '; 
        	        $("#appraisalTasksDiv").append(result);
        	        
        	        $("#tasksLoading").removeAttr("style");
        	    }else{
        	    	 var result = "";  
        	    	 result = result + '<div class="odd"><div valign="top" colspan="3" class="dataTables_empty"></div></div>';
         	         $("#appraisalTasksDiv").append(result);
         	        $("#tasksLoading").removeAttr("style");
        	    }
        		
        	}else if (response.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
			},
	}); 
}

</script>