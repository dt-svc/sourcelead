<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="tab-c" class="newPrTabs_inn">
		<div class="step4">
			 <div id="tasksLoading"></div>
			 <div id="onboardTasksList"></div>
	</div>
</div>

<script>
function getVendorOnboardTasksList(){
	var vendorOnboardId = '${vendorOnboardId}';
	 $.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getVendorOnboardTasksByVendorOnboardId?${_csrf.parameterName}=${_csrf.token}&vendorOnboardId="+vendorOnboardId, 
	        success: function(json) {
	        	var result = "";
	        	if(json.length > 0){ 
	        		
	        		 for (var i = 0; i < json.length; i++) { 
	        			 var opt = json[i];
	        			 var openSelected = '';
	        			 var closedSelected = '';
	        			 
	        			 if(opt.taskStatus == 'Open'){
	        				 openSelected = 'selected';
	        			 }else{
	        				 closedSelected = 'selected'
	        			 }
	        			 
	        			 result = result +'<ul id="task'+opt.id+'">';
		        		 result = result +'<li>Subject : '+opt.subject+'</li>';
		        		 result = result +'<li><i class="fa fa-star-o"></i>Priority : '+opt.priority+'</li>';
		        		 result = result +'<li><i class="fa fa-clock-o"></i>Task Date : '+opt.vendorTaskDateStr+'</li>';
		        		 
		        		 result = result +'<li>';
		        		 result = result +'<select class="form-control select-bg" onchange="changeTaskStatus('+opt.id+')">';
		        		 result = result +'<option '+openSelected+'>Open</option>';
		        		 result = result +'<option '+closedSelected+'>Closed</option>';
		        		 result = result +'</select>';
		        		 result = result +'</li>';
            		
		        		 result = result +'<div class="clearfix"></div>';
		        		 result = result +'</ul>';
	        		 }
	        		
	        		 
	        		
	        	}else{
	        		result = result +'<div><h4>&nbsp;&nbsp;&nbsp;No Tasks Found</h4></div>';
	        	}
	        	
	        	result = result +'<div class="form-footer clearfix mt">';
	    		result = result +'<div class="pull-right mr">';
	    		result = result +'<input type="button" value="Finish" id="finish_task" onclick="saveAndFinishVendorOnbord();" class="btn btn-sm btn-primary">';
	    		result = result +'</div>';
	    		result = result +'</div>';
	    		
	    		$("#onboardTasksList").empty();
	           	$("#onboardTasksList").append(result); 
	        }
	 });
}
</script>


<script>
function changeTaskStatus(taskId){
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/changeVendorOnboardTaskStatus?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId, 
	         success: function(result) {
	        	 $("#tasksLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        	 $("#tasksLoading").removeAttr("style");
	        }
	 });
}
</script>

