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
 
 <style type="text/css">
 .fa.pull-right {
    margin-left: -1.7em !important;
}
 </style>
 
 <script> 
	$(document).ready(function(){
		getBudgetRequestListCount();
		getBudgetRequestList("Waiting For Approval");
	});
</script>
 
 
    <section class="clearfix">
      <div class="content-dashboard clearfix"> 
      	  <div class="clearfix dashboard-widget mb col-md-12">
      	    <h4>Budget Requests</h4>
      	    
      	    <div class="project_side_wrap_inn_tabs">
      	    <div id="resourceRequestsDivLoading"></div>
			    <ul class="project-tabs-menu">
			        <li class="current" id="budgetWaiting"><a data-toggle="tab" href="javascript:void(0)" onclick="getBudgetRequestList('Waiting For Approval')">Waiting (<span id="budgetWaitingCount"></span>)</a></li>
			        <li id="budgetApproved"><a data-toggle="tab" href="javascript:void(0)"  onclick="getBudgetRequestList('Approved')">Approved (<span id="budgetApprovedCount"></span>)</a></li>
			        <li id="budgetRejected"><a data-toggle="tab" href="javascript:void(0)" onclick="getBudgetRequestList('Rejected')">Rejected (<span id="budgetRejectedCount"></span>)</a></li>
			        <li id="budgetOnhold"><a data-toggle="tab" href="javascript:void(0)" onclick="getBudgetRequestList('On Hold')">On hold (<span id="budgetOnholdCount"></span>)</a></li>
			        <div class="clearfix"></div>
			    </ul>
			    <div id="budgetRequestListDiv"></div>
			    <div class="vendor_wrap_mask"></div>
   				<div id="budgetRejectedReasonDiv" ></div>
   				<div id="budgetOnHoldReasonDiv"></div>
           </div>
      	  </div>
      </div>
    <div id="editBudgetRequestPopUpid"></div>
 </section>
 
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
	    		result = result + '		<div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter overflow_inherint mrgTp">';
	    		result = result + '		<table id="budgetRequestsPaginationList" cellspacing="0" width="100%">';
	    		result = result + '	    <thead>';
	    		result = result + '	     	<tr>';
	    		result = result + '	         	<th>Request Id</th>';
	    		result = result + '	             <th>Base Line</th>';
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
	    			result = result + '     <td><div class="tbldata-dotdot_80" title="'+estimatedBudgetValue+'">'+estimatedBudgetValue+'</div></td>';
					result = result + '     <td><div  class="tbldata-dotdot_80">'+estimatedHours+'</div></td>';
					result = result + '     <td><div  class="tbldata-dotdot-vsm" title="'+report.createdUserName+'">'+report.createdUserName+'</div></td>';
					result = result + '     <td><div  class="tbldata-dotdot_80">'+report.createdStr+'</div></td>';
					
					if(requestType == 'Waiting For Approval'){
						result = result + '     <td><div class="tblminwidth-75">';
						result = result + '     <i class="fa fa-check" alt="Approve" title="Approved" onclick="acceptBudgetRequset(\''+report.id+'\',\''+requestType+'\')"></i>|';
						result = result + '     <i class="fa fa-times" alt="Rejected" title="Rejected" onclick="deleteBudgetRequaet(\''+report.id+'\',\''+requestType+'\');"></i>';
						result = result + '     <i class="fa fa-clock-o marOne" alt="On hold" title="On hold" onclick="onholdBudgetRequaet(\''+report.id+'\',\''+requestType+'\');"></i></div></td>';
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
						result = result + '     <td><div class="tblminwidth-75">';
						result = result + '     <i class="fa fa-check" alt="Approve" title="Approved" onclick="acceptBudgetRequset(\''+report.id+'\',\''+requestType+'\')"></i>|';
						result = result + '     <i class="fa fa-times" alt="Rejected" title="Rejected" onclick="deleteBudgetRequaet(\''+report.id+'\',\''+requestType+'\');"></i>';
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
	    	    	 $("#budgetWaiting").addClass("current");
	    	    	 $("#budgetApproved").removeClass("current");
	    	    	 $("#rejected").removeClass("current");
	    	    	 $("#budgetOnhold").removeClass("current");
	    	    }
	    	   
	    	    else if(requestType == 'Approved'){
	    	    	 $("#budgetApproved").addClass("current");
	    	    	 $("#budgetWaiting").removeClass("current");
	    	    	 $("#budgetRejected").removeClass("current");
	    	    	 $("#budgetOnhold").removeClass("current");
	    	    }
	    	    
	    	    else if(requestType == 'Rejected'){
	    	    	 $("#budgetRejected").addClass("current");
	    	    	 $("#budgetWaiting").removeClass("current");
	    	    	 $("#budgetApproved").removeClass("current");
	    	    	 $("#budgetOnhold").removeClass("current");
	    	    }
	    	    
	    	    else if(requestType == 'On Hold'){
	    	    	 $("#budgetOnhold").addClass("current");
	    	    	 $("#budgetWaiting").removeClass("current");
	    	    	 $("#budgetApproved").removeClass("current");
	    	    	 $("#budgetRejected").removeClass("current");
	    	    	
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
	result = result +"		<textarea  id='reasonForProjectBudget' name='reasonForProjectBudget'  placeholder='Comments here' field-name='Reason' data-validation='validate_length length2-5000' data-validation-optional='true'></textarea>";
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
	var sourceleadUrl = '${sourceleadUrl}';
     $("#delete_budget").attr("disabled", "disabled");
	 $("#delete_budget").attr("disabled", "disabled");
	 var formData = getFormData("deleteBudget_form");
	 $.ajax({
				type : "POST",
				data :formData,
				url : sourceleadUrl+"/saveBudgetRequestReason?${_csrf.parameterName}=${_csrf.token}",
				success : function(result) {
					cancelTermination();
					getBudgetRequestList(requestType);
					getBudgetRequestListCount();
				},
			}); 
}
$('body').on('blur', '#deleteBudget_form', function() {
	$("#deleteBudget_form").showHelpOnFocus();;
	$("#deleteBudget_form").validateOnBlur(false, validationSettings);  
}); 

function editBudgetrequest(budgetRequestedId,requestType){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
				type : "POST",
				url : sourceleadUrl+"/getBudgetRequestDetailsById?${_csrf.parameterName}=${_csrf.token}&budgetRequestedId="+budgetRequestedId,
				success : function(json) {
					var result = "";
					result = result + '	<div class="collapse-div-adduser prRight col-md-4 show-div prRight_new popOut"> <i class="fa fa-close close-div" onClick="editCloseDiv();"></i>';
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
				    
						$('.chosen-select').each(function() {
							var $this = $(this);
							$this.next().css({'width': '100%'});
							});
						
				},
			}); 
	 
	
			
}
function editCloseDiv(){
	$(".popOut").hide();
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
	result = result +"		<textarea  id='reasonForProjectBudget' name='reasonForProjectBudget'  placeholder='Comments here' field-name='Reason' data-validation='validate_length length2-5000' data-validation-optional='true'></textarea>";
	result = result +"</form>";
	result = result +'		<b href="javascript:void(0)"><input id="onHold_budget-id" type="button" value="Delete" onclick="saveOnHoldBudgetReason(\''+requestType+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";

	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
function saveOnHoldBudgetReason(requestType){
	var sourceleadUrl = '${sourceleadUrl}';
     $("#onHold_budget").attr("disabled", "disabled");
	 $("#onHold_budget").attr("disabled", "disabled");
	 var formData = getFormData("onHoldBudget_form");
	 $.ajax({
				type : "POST",
				data :formData,
				url : sourceleadUrl+"/onHoldBudgetRequest?${_csrf.parameterName}=${_csrf.token}",
				success : function(result) {
					cancelTermination();
					getBudgetRequestList(requestType);
					getBudgetRequestListCount();
				},
			}); 
}
$('body').on('blur', '#deleteBudget_form', function() {
	$("#deleteBudget_form").showHelpOnFocus();;
	$("#deleteBudget_form").validateOnBlur(false, validationSettings);  
}); 
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
    $('#planningRequestsList').wrap("<div class='overflow_Auto'></div>");
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


 