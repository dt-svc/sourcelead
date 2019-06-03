<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
	window.onload = function () {
		getRequestedAssociatesList();
	 };
 </script>

<section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>View New Request Resources</h2>
      </div>
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/projectDashboard'/>">Home</a>  <a href="<c:url value='${appPath}/projectSummary?projectUUID=${projectUUID}&tabType=resources'/>"></a> &raquo; View New Request Resources</div>
</section>

 <div class="content-area content-new clearfix">
 <div id="requestedAssociatesDivLoading"></div>
   <div id="requestedAssociatesList"></div>
   <div class="vendor_wrap_mask"></div>
   <div id="rejectedReasonDiv"></div>
    <div id="newRequestOnHoldReasonDiv"></div>
   <div class="modal-footer">
   <button class="btn btn-primary" type="button" value="Back to list" onClick="javascript:window.location.href = '<c:url value='${appPath}/dashboard'/>'" >Back to list</button>
   </div>
					
 </div>


</section>



<jsp:include page="../masterFooter.jsp" />

<script>
function getRequestedAssociatesList(){
	var requestUUID = '${requestUUID}';
	
	var sourceleadUrl ='${sourceleadUrl}';
	    	 
	$.ajax({
    	type: "POST",
    	 url: sourceleadUrl+"/getResourceRequestDetailsByRequestUUID?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID, 
         success: function(json) {
        	
        	 var result = "";
        	 
        	 result = result +'<div class="invoiceview_area clearfix">';
				result = result +'<div class="form-horizontal row">';   
				
				result = result +'<div class="col-md-6">'; 
				result = result +'<label class="col-md-4 control-label">RequestId :</label>';
				result = result +'<div class="col-md-8">';
				result = result +'<label><strong>'+json.requestId+'</strong></label>  ';
				result = result +' </div>';    
				result = result +'</div>';
				
				result = result +'<div class="col-md-6">'; 
				result = result +'<label class="col-md-4 control-label">Total Hours :</label>';
				result = result +'<div class="col-md-8">';
				result = result +'<label><strong>'+json.estimatedHours+'</strong> hours</label>  ';
				result = result +' </div>';    
				result = result +'</div>';
				
				result = result +'<div class="col-md-6">'; 
				result = result +'<label class="col-md-4 control-label">Approver :</label>';
				result = result +'<div class="col-md-8">';
				result = result +'<label><strong>'+json.approverName+'</strong></label>  ';
				result = result +' </div>';    
				result = result +'</div>';
				
				result = result +'<div class="col-md-6">'; 
				result = result +'<label class="col-md-4 control-label">Total Budget :</label>';
				result = result +'<div class="col-md-8">';
				result = result +'<label><strong>'+json.estimatedBudget+'</strong>&nbsp;${SourceLeadWeb_currencyCode}</label>  ';
				result = result +' </div>';    
				result = result +'</div>';
				
				result = result +'<div class="col-md-6">'; 
				result = result +'<label class="col-md-4 control-label">Description :</label>';
				result = result +'<div class="col-md-8">';
				result = result +'<label><strong>'+json.description+'</strong></label>  ';
				result = result +' </div>';    
				result = result +'</div>';
				/* 
				result = result +'<div class="col-md-6">'; 
				result = result +'<label class="col-md-4 control-label">Status :</label>';
				result = result +'<div class="col-md-8">';
				result = result +'<label><strong>'+json.requestStatus+'</strong></label>  ';
				result = result +' </div>';    
				result = result +'</div>'; */
				
				result = result +'</div>';
				result = result +'</div> ';  
				
				result = result +'	     <div class="clearfix project_tabs_inn_table overflow_Auto">';
				result = result +'         <table width="100%" cellpadding="0" cellspacing="0">';
				result = result +'             <thead>';
				result = result +'                 <tr>';
				result = result +'                     <th>Role Name</th>';
				result = result +'                     <th>No Resources</th>';
				result = result +'                     <th>Exp</th>';
				result = result +'                     <th>Primary Skills</th>';
				result = result +'                     <th>Bill/Pay Rate</th>';
				result = result +'                     <th>Duration</th>';
				result = result +'                     <th>Consulting</th>';
				result = result +'                     <th>Est Hours</th>';
				result = result +'                     <th>Est Budget</th>';
				result = result +'                     <th>Status</th>';
				result = result +'                 </tr>';
				result = result +'             </thead>';
				result = result +'             <tbody>';
				
				
				 for(var i=0; i<json.newResourceRequestList.length; i++){
					 var opt = json.newResourceRequestList[i];
					 var startDate = '';
					 var endDate = '';
					 var payRate = ''
					 var estimatedHours = '';
					 var estimatedBudget = '';
					 var minExperience = '';
					 var maxExperience = '';
					 var skills = '';
					 var billRate = '';
					 var payRate = '';
					 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
						 startDate = opt.startDateStr;
					 }else{
						 startDate = '--';
					 }
					 
					 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
						 endDate = opt.endDateStr;
					 }else{
						 endDate = '--';
					 }
					 
					 if(opt.payRate != null && opt.payRate != '' && opt.payRate != 'undefined'){
						 payRate = opt.payRateCurrency+" "+opt.payRate+" / "+opt.payRateType;
					 }else{
						 payRate = '--';
					 }
					 
					 var billRate = opt.billRateCurrency+" "+opt.billRate+" / "+opt.billRateType;
					 
					 if(opt.estimatedHours != null && opt.estimatedHours != '' && opt.estimatedHours != 'undefined'){
						 estimatedHours = opt.estimatedHours;
					 }else{
						 estimatedHours = '--';
					 }
					 
					 if(opt.estimatedBudget != null && opt.estimatedBudget != '' && opt.estimatedBudget != 'undefined'){
						 estimatedBudget = opt.estimatedBudget;
					 }else{
						 estimatedBudget = '--';
					 }
					 if(opt.minExperience != null && opt.minExperience != '' && opt.minExperience != 'undefined'){
						 minExperience = "Min Exp. "+opt.minExperience;
					 }else{
						 minExperience = '--';
					 }
					if(opt.maxExperience != null && opt.maxExperience != '' && opt.maxExperience !='undefined'){
						maxExperience = "Max Exp. "+opt.maxExperience;
					}else{
						maxExperience = '--';
					}
					 if(opt.skills != null && opt.skills != '' && opt.skills !='undefined'){
						 skills = opt.skills;
					 }else{
						 skills = '--';
					 }
					 
					 if(opt.billRate != null && opt.billRate != '' && opt.billRate != 'undefined'){
							billRate = opt.billRate;
					 }else{
						 billRate = '--';
					 }
					 if(opt.payRate != null && opt.payRate != '' && opt.payRate != 'undefined'){
						payRate = opt.payRate;
					 }else{
						 payRate = '--';
					 }
					 result = result +'<tr>';
					    
					    result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot" title="'+opt.roleName+'">'+opt.roleName+'</div>';
						result = result +'</td>'; 
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot-vsm" title="'+opt.noOfResource+'">'+opt.noOfResource+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
						result = result +'<div class="width90px inr_values">';
						result = result +'<small>'+minExperience+'</small> <small>'+maxExperience+'</small>';
						result = result +'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot" title="'+skills+'">'+skills+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
							result = result +'<div class="width180px">';
						    result = result +'     <small class="text-muted paddingRight pull-left mt10" id="currencyCodeRow1">'+opt.rateCurrency+'</small>';
						    result = result +'    <div class="width90px inr_values">';
							result = result +'        <small>'+billRate+'</small>';
							result = result +'        <div class="clearfix"></div>';
							result = result +'        <small>'+payRate+'</small>';
						    result = result +'    </div>';
						    result = result +'    <small class="text-muted pull-right paddingLeft payrateType mt10" id="rateTypeRow1" title="Hourly">Hourly</small>';
						    result = result +' </div>';
						result = result +'</td>';
						
						result = result +'<td>';
						result = result +'<div class="width90px">'+startDate+'</div>';
						result = result +'<div  class="width90px">'+endDate+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
						result = result +'<div class="tbldata-dotdot_80" title="'+opt.consultingType+'">'+opt.consultingType+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot_80" title="'+estimatedHours+'">'+estimatedHours+'</div>';
						result = result +'</td>';

						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot_80" title="'+estimatedBudget+'">'+estimatedBudget+'</div>';
						result = result +'</td>';
						
						result = result +'<td id="action'+opt.requestUUID+'">';
						if(opt.resourceStatus == 'Waiting For Approval' || opt.resourceStatus == 'On hold'){
							result = result +'<div class="tbldata-dotdot_80">'; 
							if(opt.resourceStatus == 'On hold'){
				    			  result = result +"<i class='glyphicon glyphicon-comment edit-reason'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>&nbsp;|&nbsp;";
				    			}
			    			result = result + '<a href="javascript:void(0)" onclick="acceptNewResourceRequest(\''+opt.requestUUID+'\');"><i class="fa fa-check text-success" alt="Approve" title="Approved"></i></a>&nbsp;|&nbsp;';
				    		result = result + '<a href="javascript:void(0)" onclick="rejectAssociate(\''+opt.requestUUID+'\',\''+opt.roleName+'\');"><i class="fa fa-times text-danger" alt="Rejected" title="Rejected"></i></a>';
			    		
				    		if(opt.resourceStatus == 'Waiting For Approval'){
				    			result = result + '&nbsp;|&nbsp;<a href="javascript:void(0)" onclick="onholdNewAssociateRequest(\''+opt.requestUUID+'\',\''+opt.roleName+'\');"><i class="fa fa-clock-o text-primary" alt="Onhold" title="Onhold"></i></a>';
				    		}
				    		result = result +'</div>';
						}else{
			    			result = result +'<div class="tbldata-dotdot_80 text-success" title="'+opt.resourceStatus+'"><strong>'+opt.resourceStatus+'</strong></div>';
			    		}
						result = result +'</td>';
					 result = result +'</tr>';
				 }
				
				result = result +'</tbody>';
				result = result +'</table>';
				result = result +' </div>';
					
				$("#requestedAssociatesList").empty();  
			    $("#requestedAssociatesList").append(result);
				
        	 
        	
        	 
         }
 });
}
</script>

<script>
function rejectAssociate(requestUUID){
	$("#newRequestOnHoldReasonDiv").empty();
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>New Resource Request Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_rejectedReason"  onclick="saveRejectAssociate(\''+requestUUID+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#rejectedReasonDiv").empty();
    $("#rejectedReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
</script>

<script>
function saveRejectAssociate(requestUUID){
	
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
		
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/rejectNewRequestedResource?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orglocationId="+orglocationId,
		        success: function(result) {
		        	cancelRejectedReason();
		        	$("#action"+requestUUID).html("<span>Rejected</span>")
		        	$("#loading").removeAttr("style"); 
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
<script>
function cancelRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
	$("#rejectedReasonDiv").empty();
}
</script>

<script>
function acceptNewResourceRequest(requestUUID){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var userId = '${SourceLeadWeb_userId}';
     var orglocationId = '${SourceLeadWeb_orgLocationId}';
	 $("#requestedAssociatesDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 $.ajax({
		    type : "POST",
	    	url: sourceleadUrl+"/acceptNewRequestedResource?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId,
	        success: function(result) {
	        	$("#action"+requestUUID).html("<span>Accepted</span>");
	        	$("#requestedAssociatesDivLoading").removeAttr("style"); 
	        },
	    });
}
</script>


<script>
function onholdNewAssociateRequest(requestUUID,roleName,requestType){
	var result = "";
	result = result + '	<div class="settPop settPopFormcontrol">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="newRequestOnHold_form" name="newRequestOnHold_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Request On Hold Reason<i class="fa fa-times" onclick="cancelRequestOnholdReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Initiator Name">Role :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+roleName+'</label> ';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	result = result + '			<textarea class="form-control" name="onholdReason" id="onholdReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="button" value="Save" id="submit_onHold"  onclick="saveOnHoldNewRequest(\''+requestUUID+'\',\''+requestType+'\');" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRequestOnholdReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#newRequestOnHoldReasonDiv").empty();
    $("#newRequestOnHoldReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
	 
}
</script>

<script>
function saveOnHoldNewRequest(requestUUID,requestType){
	
if ($('#newRequestOnHold_form').validate(false, validationSettings)){ 
	  	
		var frm = $('#newRequestOnHold_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
	    var form = $('#newRequestOnHold_form')[0];
	    var formData = new FormData(form);
	  	formData.append("json",jsonData)
	  	
        var sourceleadUrl ='${sourceleadUrl}';
        var userId = '${SourceLeadWeb_userId}';
        var orglocationId = '${SourceLeadWeb_orgLocationId}';
        
        $("#submit_onHold").attr("disabled", "disabled");
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/onholdRequestedNewAssociate?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID+"&userId="+userId+"&orglocationId="+orglocationId,
		        success: function(result) {
		        	
		        	cancelRequestOnholdReason();
		        	$("#action"+requestUUID).html("<span>Onhold</span>")
		        	$("#loading").removeAttr("style"); 
		        },
		    });  
      return true; 
		
	}else{
		return false;
	  }
	
	  $('body').on('blur', '#newRequestOnHold_form', function() {
	   		$("#newRequestOnHold_form").showHelpOnFocus();
	   		$("#newRequestOnHold_form").validateOnBlur(false, validationSettings);
	   	});
}

</script>

<script>
function cancelRequestOnholdReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>