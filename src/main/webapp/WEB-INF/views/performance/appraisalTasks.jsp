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
    result = result + '<li><a onclick="getProjectDetails(\''+assignedUUID+'\');" class="active">Projects</a></li>';
    result = result + '<li><a href="#" class="">Jobs</a></li>';
    result = result + '<li><a href="#" class="">General Tasks</a></li>';
    result = result + '</ul>';
    result = result + '</div>';
    
    result = result + '<div class="newRolLRight">';
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
    
    $("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		  type: "GET",
		    url: pmsUrl+"/getAssignedFeedbackProjectTasksDetailsList?assignedUUID="+assignedUUID,
		     success :function(response) {
		    	 
		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	    		    showError(response.responseMessage);
	    		    $("#appraisalLoading").removeAttr('style');
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
        	        result = result + '</div> ';
        	        $("#appraisalTasksDiv").empty(); 
        	        $("#appraisalTasksDiv").append(result);
        	        
        	        $("#appraisalLoading").removeAttr("style");
        	    }else{
        	    	 var result = "";  
        	    	 result = result + '<div class="odd"><div valign="top" colspan="3" class="dataTables_empty"></div></div>';
        	    	 $("#appraisalTasksDiv").empty(); 
         	         $("#appraisalTasksDiv").append(result);
         	        $("#appraisalLoading").removeAttr("style");
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