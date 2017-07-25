<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <script src="resources/js/formvalidator.js"></script>
 
 <script> 
	$(document).ready(function(){
		resourceRequestList("Waiting For Approval");
	});
</script>
 
 
    <section class="clearfix">
      <div class="content-dashboard clearfix"> 
      	  <div class="clearfix dashboard-widget mb col-md-12">
      	    <h4>Resource Requests</h4>
      	    
      	    <div class="project_side_wrap_inn_tabs">
			    <ul class="project-tabs-menu">
			        <li class="current" id="waiting"><a data-toggle="tab" href="javascript:void(0)" onclick="resourceRequestList('Waiting For Approval')">Waiting (10)</a></li>
			        <li id="approved"><a data-toggle="tab" href="javascript:void(0)"  onclick="resourceRequestList('Approved')">Approved (10)</a></li>
			        <li id="rejected"><a data-toggle="tab" href="javascript:void(0)" onclick="resourceRequestList('Rejected')">Rejected (10)</a></li>
			        <li id="onhold"><a data-toggle="tab" href="javascript:void(0)" onclick="resourceRequestList('On hold')">On hold (10)</a></li>
			        <div class="clearfix"></div>
			    </ul>
			    <div id="resourceRequestList"></div>
           </div>
      	  </div>
      </div>
    
 </section>
 
 
 
 <script>
function resourceRequestList(requestType){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var approverUserId = '${SourceLeadWeb_user.id}';
	 var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	 
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPlanningRequestListByApproverIdAndOrgLocationId?${_csrf.parameterName}=${_csrf.token}&approverUserId="+approverUserId+"&orgLocationId="+orgLocationId+"&requestType="+requestType, 
	        success: function(json) {
	        	
	        	
	        	var result = "";

	    		result = result + '		<div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter overflow_inherint">';
	    		result = result + '		<table id="planningRequestsPaginationList" cellspacing="0" width="100%">';
	    		result = result + '	    <thead>';
	    		result = result + '	     	<tr>';
	    		result = result + '	         	<th>Request Id</th>';
	    		result = result + '	             <th>Project</th>';
	    		result = result + '	             <th>Est Hours</th>';
	    		result = result + '	             <th>Est Budget</th>';
	    		result = result + '	             <th>Type</th>';
	    		result = result + '	             <th>Initiator</th>';
	    		result = result + '	             <th>Date</th>';
	    		result = result + '	             <th>Action</th>';
	    		result = result + '	         </tr>';
	    		result = result + '	    </thead>';
	    		result = result + '	 	<tbody>';
	    		
	    		
	    		for (var i=0; i<json.length; i++){
	    			var report = json[i];
	    			
	    			result = result + '<tr>';
	    			if(report.requestType == 'Associate Request'){
	    				result = result + '<td><div  class="tbldata-dotdot-vsm"><a href="'+sourceleadUrl+'/requestedAssociatesList?requestUUID='+report.requestUUID+'">'+report.requestId+'</a></div></td>';
	    			}else{
	    				result = result + '<td><div  class="tbldata-dotdot-vsm"><a href="#">'+report.requestId+'</a></div></td>';
	    			}
		    		 result = result + '	             <td><div>'+report.projectName+'</div></td>';
		    		result = result + '	             <td><div>'+report.estimatedHours+' hrs</div></td>';
		    		result = result + '	             <td><i class="pull-left"></i>'+report.estimatedBudgetCurrency+'<div class="pull-right">'+report.estimatedBudget+'</div></td>';
		    		result = result + '	             <td><div>'+report.requestType+'</div></td>';
		    		result = result + '	             <td><div class="tbldata-dotdot-vsm">'+report.requestedUser+'</div></td>';
		    		result = result + '	             <td><div class="tblminwidth-75">'+report.requestedDateStr+'</div></td>';
		    		result = result + '<td><div class="tblminwidth-90">';
		    		 
		    		if(report.requestStatus == 'Waiting For Approval'){
		    			result = result + '<i class="fa fa-check" alt="Approve" title="Approved"></i>|';
			    		result = result + '<i class="fa fa-times" alt="Rejected" title="Rejected"></i>|';
			    		result = result + '<i class="fa fa-clock-o" alt="On Hold" title="On Hold"></i>';
		    		}else{
		    			result = result + '<b>'+report.requestStatus+'</b>';
		    		}
		    		
		    		result = result + '</div></td>';
		    		result = result + '</tr>';
	    		}
	    		
	    		
	    		result = result + '	     </tbody>';
	    		result = result + '	 </table>';
	    		result = result + '</div>';
	    	    $("#resourceRequestList").empty();
	    	    $("#resourceRequestList").append(result); 
	    	    planningRequestsPagination();
	    	    
	    	    if(requestType == 'Waiting For Approval'){
	    	    	 $("#waiting").addClass("current");
	    	    	 $("#approved").removeClass("current");
	    	    	 $("#rejected").removeClass("current");
	    	    	 $("#onhold").removeClass("current");
	    	    }
	    	   
	    	    else if(requestType == 'Approved'){
	    	    	 $("#approved").addClass("current");
	    	    	 $("#waiting").removeClass("current");
	    	    	 $("#rejected").removeClass("current");
	    	    	 $("#onhold").removeClass("current");
	    	    }
	    	    
	    	    else if(requestType == 'Rejected'){
	    	    	 $("#rejected").addClass("current");
	    	    	 $("#waiting").removeClass("current");
	    	    	 $("#approved").removeClass("current");
	    	    	 $("#onhold").removeClass("current");
	    	    }
	    	    
	    	    else if(requestType == 'On hold'){
	    	    	 $("#onhold").addClass("current");
	    	    	 $("#waiting").removeClass("current");
	    	    	 $("#approved").removeClass("current");
	    	    	 $("#rejected").removeClass("current");
	    	    	
	    	    }
	    	    
	        }
	   
     });
		 
}
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
 
 