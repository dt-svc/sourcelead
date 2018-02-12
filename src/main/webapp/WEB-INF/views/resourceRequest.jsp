<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
 <script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
 
 <script> 
	$(document).ready(function(){
		getResourceReqestsListCount();
		resourceRequestList("Waiting For Approval");
	});
</script>
 
 
    <section class="clearfix">
      <div class="content-dashboard clearfix"> 
      	  <div class="clearfix dashboard-widget mb col-md-12">
      	    <h4>Resource Requests</h4>
      	    
      	    <div class="project_side_wrap_inn_tabs">
      	    <div id="resourceRequestsDivLoading"></div>
			    <ul class="project-tabs-menu">
			        <li class="current" id="waiting"><a data-toggle="tab" href="javascript:void(0)" onclick="resourceRequestList('Waiting For Approval')">Waiting (<span id="waitingCount"></span>)</a></li>
			        <li id="requestApproved"><a data-toggle="tab" href="javascript:void(0)"  onclick="resourceRequestList('Approved')">Approved (<span id="approvedCount"></span>)</a></li>
			        <li id="requestRejected"><a data-toggle="tab" href="javascript:void(0)" onclick="resourceRequestList('Rejected')">Rejected (<span id="rejectedCount"></span>)</a></li>
			        <li id="requestOnhold"><a data-toggle="tab" href="javascript:void(0)" onclick="resourceRequestList('On hold')">On hold (<span id="onholdCount"></span>)</a></li>
			        <div class="clearfix"></div>
			    </ul>
			    <div id="resourceRequestList"></div>
			    <div class="vendor_wrap_mask"></div>
   				<div id="requestRejectedReasonDiv"></div>
   				<div id="requestOnHoldReasonDiv"></div>
           </div>
      	  </div>
      </div>
    
 </section>
 
 <script>
 function getResourceReqestsListCount(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var approverUserId = '${SourceLeadWeb_user.id}';
	 var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	 alert
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getResourceRequestsCounts?${_csrf.parameterName}=${_csrf.token}&approverUserId="+approverUserId+"&orgLocationId="+orgLocationId, 
	        success: function(json) {
	        	$("#waitingCount").html(json.waitingCount);
	        	$("#approvedCount").html(json.approvedCount);
	        	$("#rejectedCount").html(json.rejectedCount);
	        	$("#onholdCount").html(json.onholdCount);
	        }
	   
     });
 }
 </script>
 
 <script>
function resourceRequestList(requestType){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var approverUserId = '${SourceLeadWeb_user.id}';
	 var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  resListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  resListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  resListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  resListDate = 'YYYY-DD-MM'; 
	  }  
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
	    		result = result + '	             <th>Date<br>(<small class="custom-small">'+resListDate+'</small>)</th>';
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
	    				result = result + '<td><div  class="tbldata-dotdot-vsm"><a href="'+sourceleadUrl+'/viewNewResourceRequestDashborad?requestUUID='+report.requestUUID+'">'+report.requestId+'</a></div></td>';
	    			}
		    		 result = result + '	             <td><div>'+report.projectName+'</div></td>';
		    		result = result + '	             <td><div>'+report.estimatedHours+' hrs</div></td>';
		    		result = result + '	             <td><i class="pull-left"></i>'+report.estimatedBudgetCurrency+'<div class="pull-right">'+report.estimatedBudget+'</div></td>';
		    		result = result + '	             <td><div>'+report.requestType+'</div></td>';
		    		result = result + '	             <td><div class="tbldata-dotdot-vsm">'+report.requestedUser+'</div></td>';
		    		result = result + '	             <td><div class="tblminwidth-75">'+report.requestedDateStr+'</div></td>';
		    		result = result + '<td><div class="tblminwidth-100">';
		    		 
		    		if(report.requestStatus == 'Waiting For Approval' || report.requestStatus == 'On hold'){
		    			result = result + '<a href="javascript:void(0)" onclick="acceptAssociateRequest(\''+report.requestUUID+'\');"><i class="fa fa-check text-success" alt="Approve" title="Approve"></i></a>|';
			    		result = result + '<a href="javascript:void(0)" onclick="rejectAssociateRequest(\''+report.requestUUID+'\',\''+report.requestedUser+'\');"><i class="fa fa-times text-danger" alt="Reject" title="Reject"></i></a>';
			    		
			    		if(report.requestStatus == 'Waiting For Approval'){
			    			result = result + '|<a href="javascript:void(0)" onclick="onholdAssociateRequest(\''+report.requestUUID+'\',\''+report.requestedUser+'\');"><i class="fa fa-clock-o" alt="On hold" title="On hold"></i></a>';
			    		}
			    		
		    		}else{
		    			if(report.requestStatus == 'Rejected'){
		    				result = result + '<b class="bg-red">'+report.requestStatus+'</b>';
		    			}else{
		    				result = result + '<b>'+report.requestStatus+'</b>';
		    			}
		    			
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
	    	    	 $("#requestApproved").removeClass("current");
	    	    	 $("#requestRejected").removeClass("current");
	    	    	 $("#requestOnhold").removeClass("current");
	    	    }
	    	   
	    	    else if(requestType == 'Approved'){
	    	    	 $("#requestApproved").addClass("current");
	    	    	 $("#waiting").removeClass("current");
	    	    	 $("#requestRejected").removeClass("current");
	    	    	 $("#requestOnhold").removeClass("current");
	    	    }
	    	    
	    	    else if(requestType == 'Rejected'){
	    	    	 $("#requestRejected").addClass("current");
	    	    	 $("#waiting").removeClass("current");
	    	    	 $("#requestApproved").removeClass("current");
	    	    	 $("#requestOnhold").removeClass("current");
	    	    }
	    	    
	    	    else if(requestType == 'On hold'){
	    	    	 $("#requestOnhold").addClass("current");
	    	    	 $("#waiting").removeClass("current");
	    	    	 $("#requestApproved").removeClass("current");
	    	    	 $("#requestRejected").removeClass("current");
	    	    	
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


<script>
function acceptAssociateRequest(requestUUID){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var userId = '${SourceLeadWeb_userId}';
     var orglocationId = '${SourceLeadWeb_orgLocationId}';
	 $("#resourceRequestsDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 $.ajax({
		    type : "POST",
	    	url: sourceleadUrl+"/acceptAssociateRequest?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId, 
	        success: function(result) {
	        	location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_orgLocationId}");  
	        },
	    });
	 
}
</script>

<script>
function onholdAssociateRequest(requestUUID,Initiator){
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="onHold_form" name="onHold_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Request On Hold Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Initiator Name">Initiator :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+Initiator+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_onHold"  onclick="saveOnHoldRequest(\''+requestUUID+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRequestRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#requestOnHoldReasonDiv").empty();
    $("#requestOnHoldReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
	 
}
</script>

<script>
function saveOnHoldRequest(requestUUID){
	
if ($('#onHold_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#onHold_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
	    var form = $('#onHold_form')[0];
	    var formData = new FormData(form);
	  	formData.append("json",jsonData)
	  	
        var sourceleadUrl ='${sourceleadUrl}';
        var userId = '${SourceLeadWeb_userId}';
        var orglocationId = '${SourceLeadWeb_orgLocationId}';
        
        $("#submit_onHold").attr("disabled", "disabled");
        $("#resourceRequestsDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/onholdAssociateRequest?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId,
		        success: function(result) {
		        	cancelRejectedReason();
		        	location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_orgLocationId}");  
		        },
		    });  
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#onHold_form', function() {
	   		$("#onHold_form").showHelpOnFocus();
	   		$("#onHold_form").validateOnBlur(false, validationSettings);
	   	});
}


</script>
<script>
function rejectAssociateRequest(requestUUID,Initiator){
	
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Request Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Initiator Name">Initiator :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+Initiator+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_rejectedReason"  onclick="saveRejectAssociateRequest(\''+requestUUID+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRequestRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#requestRejectedReasonDiv").empty();
    $("#requestRejectedReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
</script>

<script>
function cancelRequestRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>


<script>
function saveRejectAssociateRequest(requestUUID){
	
	if ($('#rejectedReason_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#rejectedReason_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		
	    var form = $('#rejectedReason_form')[0];
	    var formData = new FormData(form);
	  	formData.append("json",jsonData)
        var sourceleadUrl ='${sourceleadUrl}';
        var userId = '${SourceLeadWeb_userId}';
        
        var orglocationId = '${SourceLeadWeb_orgLocationId}';
        $("#submit_rejectedReason").attr("disabled", "disabled");
		
        $("#resourceRequestsDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/rejectAssociatesRequest?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orglocationId="+orglocationId,
		        success: function(result) {
		        	cancelRejectedReason();
		        	location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_orgLocationId}");  
		        },
		    });  
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#rejectedReason_form', function() {
	   		$("#rejectedReason_form").showHelpOnFocus();
	   		$("#rejectedReason_form").validateOnBlur(false, validationSettings);
	   	});
	
}
</script>
 
 
 