<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery-tagit.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
function vendorOnboardTasksList(orgRelationId){
	
		$.ajax({
		   	type: "GET",
		    url: sourceleadUrl+"/getVendorSubmittedOnboardTasksByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId,	    	   
		    success: function(json) {
	        	 var result = "";
	        	 if(json.length > 0){ 
	         		result = result +'	<div class="clearfix">';
	         		result = result +'      <h4 class="pull-left ml">Task List</h4>';
	         		result = result +'  </div>';
	 	        	for (var i=0; i<json.length; i++)
	 				{
	 	        		var report = json[i];
	 	        		
	 	        		var prority = '';
	 	        		var repeat = '';
	 	        		
	 	        		if(report.priority != null && report.priority != '' && report.priority != 'undefined'){
	 	        			prority = report.priority;
	 	        		}else{
	 	        			prority = '----';
	 	        		}
	 	        		
	 	        		if(report.taskRepeat != null && report.taskRepeat != '' && report.taskRepeat != 'undefined'){
	 	        			repeat = report.taskRepeat;
	 	        		}else{
	 	        			repeat = '----';
	 	        		}
	 	        		
	 	        		result = result +'<div class="clearfix taskwidget_inner" id="onboardTask'+report.id+'">';
	 	        		result = result +'<div class="media-body onboardTasksList">';
	 	        		result = result +'<div class="clearfix taskheder">';
	 	        		
	 	        		result = result +"<label> <span class='paddingRight'>Subject:</span> <strong class='text-primary'>"+report.subject+"</strong></label>";
	 	        		
	 	        		result = result +'<label class="small pull-right text-muted"> <span>'+report.vendorTaskDateStr+'</span> </label>';
	 	        		
	 	        		
	 	        		
	 	        		result = result +'</div>';
	 	        		
	 	        		result = result +'<ul class="list-inline list-description mt10">';
	 	        		
	 	        		result = result +'<li><span>Assign To:</span>';
	 	        		
	 	        		result = result +'<label>'+report.assignedName+'</label>';
	 	        		
	 	        		result = result +'</li>';
	 	        		
	 	        		result = result +'<li><span>Priority:</span>'+prority+'</li>';
	 	        		result = result +'<li><span>Repeat:</span> '+repeat+'</li>';
	 	        		result = result +'<li><span>Status:</span> '+report.taskStatus+'</li>';
	 	        		
	 	        		result = result +'</ul>'; 
	 	        		result = result +'</div>';
	 	        		result = result +'</div>';
	         		   
	 	        		
	 				}
	         	}else{
	         		result = result +'    <div class="col-md-12 text-center mb10">  ';
	        		result = result +'<p class="dataTables_empty"></p>';
	        		result = result +'    </div> ';
	         	}
	        	
	    		
	        	    $("#tab-1").empty();
				    $("#tab-2").empty(); 
				    $("#tab-3").empty();  
				    $("#tab-4").empty();  
				    $("#tab-6").empty();  
				    $("#tab-7").empty(); 
				    $("#tab-8").empty(); 
				    $("#tab-9").empty(); 
				    $("#tab-10").empty(); 
				    $("#tab-10").append(result);  
	    		
	        	 
		    }
	   }); 
}
</script>