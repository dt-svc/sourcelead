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
 <script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
 
 <style type="text/css">
 .fa.pull-right { margin-left: -1.7em !important; }
 </style>
 
    <div id="editBudgetRequestPopUpid"></div>
 
 <script>
 function getBudgetRequestListCount(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var approverUserId = '${SourceLeadWeb_user.id}';
	 var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getBudgetRequestListCount?${_csrf.parameterName}=${_csrf.token}&approverUserId="+approverUserId+"&orgLocationId="+orgLocationId, 
	        success: function(json) {
	        	$("#budgetWaitingCount").html(json.waitingCount);
	        	$("#budgetApprovedCount").html(json.approvedCount);
	        	$("#budgetRejectedCount").html(json.rejectedCount);
	        	$("#budgetOnholdCount").html(json.onholdCount);
	        }
	   
     });
 }
 </script>
 
 <script>
function getBudgetRequestList(requestType){
	  getBudgetRequestListCount();
	
	  $('#newResourceRequestTabDiv').hide();
	  $('#assocaiteRequestsTabDiv').hide();
	  $("#budgetTabDiv").show();
	  $("#associateDiv").removeClass("active");
	  $("#newResourceDiv").removeClass("active");
 	  $("#budgetDiv").addClass("active");
	  
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
	        url: sourceleadUrl+"/getBudgetRequsetListByBudgetStatusAndOrgLocationId?${_csrf.parameterName}=${_csrf.token}&approverUserId="+approverUserId+"&orgLocationId="+orgLocationId+"&requestType="+requestType, 
	        success: function(json) {
	        	var result = "";
	        	
	      		
	    		result = result + '		<div class="project_tabs_inn_table datatable_filter overflow_inherint mrgTp dataSeaNew">';
	    		result = result + '		<table id="budgetRequestsPaginationList" cellspacing="0" width="100%">';
	    		result = result + '	    <thead>';
	    		result = result + '	     	<tr>';
	    		result = result + '	         	<th>Request Id</th>';
	    		result = result + '	             <th>Description</th>';
	    		result = result + '	             <th>Project</th>';
	    		result = result + '	             <th>Est Hours</th>';
	    		result = result + '	             <th>Est Budget</th>';
	    		result = result + '	             <th>Initiator</th>';
	    		result = result + '	             <th>Date<br>(<small class="custom-small">'+resListDate+'</small>)</th>';
	    		result = result + '	             <th>Action</th>';
	    		result = result + '	         </tr>';
	    		result = result + '	    </thead>';
	    		result = result + '	 	<tbody>';
	    		
	    		 for (var i=0; i<json.length; i++){
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
	    			result = result + '<tr>';
		    		
	    			if((report.approverUserIdlLong == '${SourceLeadWeb_user.id}') && (requestType == "On Hold" || requestType == "Rejected" || requestType == "Waiting For Approval")){
	    				result = result + ' 	<td><div class="tbldata-dotdot-vsm"> <div class="tbldata-dotdot-100 pull-left">'+report.budgetRequestedId+'</div> <i class="fa fa-pencil ml10 pull-right" onclick="editBudgetrequest(\''+report.id+'\',\''+requestType+'\')"></i></div></td>';
	    			}else{
	    				result = result + '  <td><div class="tbldata-dotdot-vsm"> <div class="tbldata-dotdot-100 pull-left">'+report.budgetRequestedId+'</div></div></td>';
	    			}
	    			
	    			result = result + '     <td><div class="tbldata-dotdot-sm" title="'+budgetDescription+'">'+budgetDescription+'</div></td>';
	    			result = result + '     <td><div class="tbldata-dotdot-sm" title="'+report.projectName+'">'+report.projectName+'</div></td>';
	    			result = result + '     <td><div  class="tbldata-dotdot_80">'+estimatedHours+'</div></td>';
	    			result = result + '     <td><div class="tbldata-dotdot_80" title="'+estimatedBudgetValue+'">'+estimatedBudgetValue+'</div></td>';
					result = result + '     <td><div  class="tbldata-dotdot-vsm" title="'+report.createdUserName+'">'+report.createdUserName+'</div></td>';
					result = result + '     <td><div  class="tblminwidth-100">'+report.createdStr+'</div></td>';
					
					if(requestType == 'Waiting For Approval'){
						result = result + '     <td><div class="tblminwidth-90">';
						result = result + '     <i class="fa fa-check text-success" alt="Approve" title="Approve" onclick="acceptBudgetRequset(\''+report.id+'\',\''+requestType+'\')"></i>|';
						result = result + '     <i class="fa fa-times text-danger" alt="Rejected" title="Reject" onclick="deleteBudgetRequaet(\''+report.id+'\',\''+requestType+'\');"></i>|';
						result = result + '     <i class="fa fa-clock-o text-primary" alt="On hold" title="On hold" onclick="onholdBudgetRequaet(\''+report.id+'\',\''+requestType+'\');"></i></div></td>';
					}
					
					if(requestType == "Rejected"){
						result = result + '<td><div class="tblminwidth-90 paTp"><b class="Rejec">Rejected</b>';
						 result = result+"  <i class='glyphicon glyphicon-comment colB' id='"+report.id+"' onclick='rejectedMessage(\""+report.rejectedReason+"\");'>";
					 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+report.id+"'>"+report.rejectedReason+"</div></i>";
					 	 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
						result = result + '</div></td>';
					}
					if(requestType == "Approved"){
						result = result + '<td><div class="tblminwidth-90 paTp"><b>Approved</b>';
						 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
						 result = result + '</div></td>';
					}
					
					
					
					if((report.approverUserIdlLong ==  '${SourceLeadWeb_user.id}') &&  requestType == "On Hold"){
					
						result = result + '     <td><div class="tblminwidth-75 paTp">';
						 result = result+"  <i class='glyphicon glyphicon-comment colB' id='"+report.id+"' onclick='rejectedMessage(\""+report.rejectedReason+"\");'>";
					 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+report.id+"'>"+report.rejectedReason+"</div></i>|";
					 	result = result + '     <i class="fa fa-check text-success" alt="Approve" title="Approve" onclick="acceptBudgetRequset(\''+report.id+'\',\''+requestType+'\')"></i>|';
						result = result + '     <i class="fa fa-times text-danger" alt="Rejected" title="Reject" onclick="deleteBudgetRequaet(\''+report.id+'\',\''+requestType+'\');"></i>';
						result = result + '     </div></td>';
												
						
					}
					if(requestType == "On Hold" && (report.approverUserIdlLong !=  '${SourceLeadWeb_user.id}')){
						result = result + '<td><div class="tblminwidth-90 paTp"><b class="processs-hold">On Hold</b>';
						 result = result+"  <i class='glyphicon glyphicon-comment colB' id='"+report.id+"' onclick='rejectedMessage(\""+report.rejectedReason+"\");'>";
					 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+report.id+"'>"+report.rejectedReason+"</div></i>";
					 	 result = result+" <br><span class='rejNam' title='"+report.updatedUserName+"'>By : "+report.updatedUserName+"</span>";
						result = result + '</div></td>';
					}
					
					
	    			result = result + '</tr>';
	    		} 
	    		
	    		
	    		result = result + '	     </tbody>';
	    		result = result + '	 </table>';
	    		result = result + '</div>';
	    	    $("#budgetRequestListDiv").empty();
	    	    $("#budgetRequestListDiv").append(result); 
	    	    budgetRequsetPagination();
	    	    
	    	    if(requestType == 'Waiting For Approval'){
	    	    	 $("#budgetWaiting").addClass("activeIcon");
	    	    	 $("#budgetApproved").removeClass("activeIcon");
	    	    	 $("#rejected").removeClass("activeIcon");
	    	    	 $("#budgetOnhold").removeClass("activeIcon");
	    	    }
	    	   
	    	    else if(requestType == 'Approved'){
	    	    	 $("#budgetApproved").addClass("activeIcon");
	    	    	 $("#budgetWaiting").removeClass("activeIcon");
	    	    	 $("#budgetRejected").removeClass("activeIcon");
	    	    	 $("#budgetOnhold").removeClass("activeIcon");
	    	    }
	    	    
	    	    else if(requestType == 'Rejected'){
	    	    	 $("#budgetRejected").addClass("activeIcon");
	    	    	 $("#budgetWaiting").removeClass("activeIcon");
	    	    	 $("#budgetApproved").removeClass("activeIcon");
	    	    	 $("#budgetOnhold").removeClass("activeIcon");
	    	    }
	    	    
	    	    else if(requestType == 'On Hold'){
	    	    	 $("#budgetOnhold").addClass("activeIcon");
	    	    	 $("#budgetWaiting").removeClass("activeIcon");
	    	    	 $("#budgetApproved").removeClass("activeIcon");
	    	    	 $("#budgetRejected").removeClass("activeIcon");
	    	    	
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
	
function acceptBudgetRequset(budgetRequestedId,requestType){
	var sourceleadUrl = '${sourceleadUrl}';
	var userId = '${SourceLeadWeb_user.id}';
	 $.ajax({
				type : "POST",
				url : sourceleadUrl+"/acceptBudgetRequest?${_csrf.parameterName}=${_csrf.token}&budgetRequestedId="+budgetRequestedId+"&userId="+userId,
				success : function(result) {
					getBudgetRequestList(requestType);
					getBudgetRequestListCount();
				},
			}); 
}
function deleteBudgetRequaet(budgetRequestedId,requestType){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<form id='deleteBudget_form' name='deleteBudget_form' >";
	result = result +"<input type='hidden' id='budgetRequestedId' name='budgetRequestedId' value='"+budgetRequestedId+"'>";
	result = result +"<input type='hidden' id='loginUserid' name='loginUserid' value='${SourceLeadWeb_user.id}'>";
	result = result +"		<p>Are you sure you want to reject ?</p>";
	result = result +"		<textarea  id='reasonForProjectBudget' name='reasonForProjectBudget'  placeholder='Comments here' field-name='Reason' data-validation='required validate_length length2-5000'></textarea>";
	result = result +"</form>";
	result = result +'		<b href="javascript:void(0)"><input id="delete_budget-id" type="button" value="Delete" onclick="saveBudgetReason(\''+budgetRequestedId+'\',\''+requestType+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";

	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);

}
function cancelTermination(){
	$("#popUp").empty();
	 $(".vendor_wrap_mask").fadeOut(200);
}
function saveBudgetReason(budgetRequestedId,requestType){
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
					getBudgetRequestList(requestType);
					getBudgetRequestListCount();
				},
			}); 
return true;
}else{
	return false;
}}
/* $('body').on('blur', '#deleteBudget_form', function() {
	$("#deleteBudget_form").showHelpOnFocus();;
	$("#deleteBudget_form").validateOnBlur(false, validationSettings);  
});  */

function editBudgetrequest(budgetRequestedId,requestType){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
				type : "POST",
				url : sourceleadUrl+"/getBudgetRequestDetailsById?${_csrf.parameterName}=${_csrf.token}&budgetRequestedId="+budgetRequestedId,
				success : function(json) {
					var result = "";
					result = result + '	<div class="budgetModalpop"> <i class="fa fa-close close-div" onClick="editCloseDiv();"></i>';
					result = result + '		<div class="form-horizontal clearfix">';
					result = result + '<form id="budgetRequest-form" name="budgetRequest-form">';
					result = result + '	    <input type="hidden" id="projectUUID" name="projectUUID" value="'+json.projectIdUUID+'">';
					result = result + '	    <input type="hidden" id="userId" name="loginUserid" value="${SourceLeadWeb_user.id}">';
					result = result + '	    <input type="hidden" id="budgetRequestedId" name="budgetRequestedId" value="'+json.id+'">';
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + '	        <input type="text" class="form-control" value="'+json.budgetDescription+'" id="baseLine" name="baseLine" placeholder="Base Line *" field-name="Base Line" data-validation="required validate_Space validate_length length1-250">';
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
					result = result + '	        <input type="text" class="form-control inlineField" value="'+json.estimatedHours+'" id="estimatedHours" placeholder="Estimated Hours *" name="estimatedHours" field-name="Estimated Hours" data-validation="required">';
					result = result + '	      </div>';
					result = result + '	    </div>';
				  
					result = result + '	    <div class="clearfix mb10">';
					result = result + '	      <div class="col-md-12">';
					result = result + ' <div class="input-group">';
					result = result + ' <div class="input-group-btn selp-custom-min selp-customMin">';
					result = result + '       <select class="selectpicker" id="estimatedBudgetCurrency" name="estimatedBudgetCurrency" >';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "INR" ? "selected" : '') +' value="INR" >INR</option>';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "AUD" ? "selected" : '') +' value="AUD" >AUD</option>';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "USD" ? "selected" : '') +' value="USD" >USD</option>';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "GBP" ? "selected" : '') +' value="GBP" >GBP</option>';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "SGD" ? "selected" : '') +' value="SGD" >SGD</option>';
					result = result + '          <option '+ (json.estimatedBudgetCurrency == "EUR" ? "selected" : '') +' value="EUR" >EUR</option>';
					result = result + '     </select>';
					result = result + '     </div>';
					result = result + '	        <input type="text" class="form-control " value="'+json.estimatedBudgetValue+'" id="estimatedBudget" name="estimatedBudget" placeholder="Estimated Budget *" field-name="Estimated Budget" data-validation="required validate_Space validate_float validate_length length1-16">';
					result = result + ' </div>';
					result = result + ' </div>';
					
					result = result + '	    <div class="clearfix mt10 mb10"></div>';
					 
					result = result + '	    <div class="clearfix mr">';
					result = result + '	      <button type="button" class="btn btn-sm btn-primary pull-right" id="save_budget" onclick="saveBudgetRequest(\''+requestType+'\');">Update</button>';
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
				    
					$(".vendor_wrap_mask").fadeIn(200);
					$(".budgetModalpop").fadeIn(200);
					
				    
						$('.chosen-select').each(function() {
							var $this = $(this);
							$this.next().css({'width': '100%'});
							});
						
				},
			}); 
	 
	
			
}
function editCloseDiv(){
	$(".budgetModalpop").hide();
	$(".vendor_wrap_mask").hide(); 	
}

function onholdBudgetRequaet(budgetRequestedId,requestType){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<form id='onHoldBudget_form' name='onHoldBudget_form' >";
	result = result +"<input type='hidden' id='budgetRequestedId' name='budgetRequestedId' value='"+budgetRequestedId+"'>";
	result = result +"<input type='hidden' id='loginUserid' name='loginUserid' value='${SourceLeadWeb_user.id}'>";
	result = result +"		<p>Are you sure you want to On-Hold ?</p>";
	result = result +"		<textarea  id='reasonForProjectBudget' name='reasonForProjectBudget'  placeholder='Comments here' field-name='Reason' data-validation='required validate_length length2-5000' ></textarea>";
	result = result +"</form>";
	result = result +'		<b href="javascript:void(0)"><input id="onHold_budget-id" type="button" value="On-Hold" onclick="saveOnHoldBudgetReason(\''+requestType+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";

	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
	}
function saveOnHoldBudgetReason(requestType){
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
	   			 	//$(".project_inn_mask").fadeOut(200);
	   			 	$(".vendor_wrap_mask").fadeOut(200);
					//cancelTermination();
					getBudgetRequestList(requestType);
					getBudgetRequestListCount();
				},
			}); 
	 return true;
	}else{
		return false;
}}
	/* }
$('body').on('blur', '#deleteBudget_form', function() {
	$("#deleteBudget_form").showHelpOnFocus();;
	$("#deleteBudget_form").validateOnBlur(false, validationSettings);  
});  */
</script>
<script>
function saveBudgetRequest(requestType){
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
							getBudgetRequestList(requestType);
							getBudgetRequestListCount();
							 editCloseDiv();
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
<script>
function budgetRequsetPagination(){  
    $('#budgetRequestsPaginationList').DataTable( { 
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
    $('#budgetRequestsPaginationList').wrap("<div class='overflow_Auto'></div>");
    $("#budgetRequestsPaginationList_filter input").attr("placeholder", "Budget search");
}
</script>
<script>
function getApproversList(approverUserId){
var sourceleadUrl ='${sourceleadUrl}';
var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	$.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getResourceRequestApproversList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
	    		success: function(json) {
	     		var output = "";
	     			output = output + "<option value=''>--Select Approver--</option>";
	     		for (var i=0; i<json.length; i++)
	     		{
		     		var opt = json[i];
		     		 
		     		 if(opt.id == approverUserId){
		        			output = output + "<option value='"+opt.id+"' selected>"+opt.fullName+" ("+opt.userType+")</option>";
		        		}else{
		        			output = output + "<option value='"+opt.id+"'> "+opt.fullName+" ("+opt.userType+")</option>";
		        		}
		     		 
	     		}
	     		$("#budgetApprover").empty();
	     		$("#budgetApprover").append(output);
	     		$('#budgetApprover').trigger("chosen:updated");
	         }
	 });
}
</script>
 