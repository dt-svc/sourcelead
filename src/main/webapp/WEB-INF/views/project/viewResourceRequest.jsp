<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

<script type="text/javascript">
	window.onload = function () {
		getRequestedAssociatesList();
	 };
 </script>

<section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>View Request Associates</h2>
      </div>
		<div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/projectDashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/projectSummary?projectUUID=${projectUUID}&projectClientUUID=${projectClientUUID}&tabType=resources">Planning</a> &raquo; View Request Associates</div>
</section>

 <div class="content-area content-new clearfix">
   <div id="requestedAssociatesList" class="col-md-12"></div>
   <div class="clearfix"></div>
   <div class="modal-footer">
   <button class="btn btn-primary" type="button" value="Back to list" onClick="javascript:window.location.href = '${SourceLeadWeb_pmsUrl}/projectSummary?projectUUID=${projectUUID}&projectClientUUID=${projectClientUUID}&tabType=resources'" >Back to list</button>
   </div>
					
 </div>


</section>



<jsp:include page="../masterFooter.jsp" />

<script>
function getRequestedAssociatesList(){
	var requestUUID = '${requestUUID}';
	
	$.ajax({
    	type: "POST",
    	 url: pmsUrl+"/getResourceRequestDetailsByRequestUUID?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID, 
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
				result = result +'<label class="col-md-4 control-label">Total Budget :</label>';
				result = result +'<div class="col-md-8">';
				result = result +'<label>${SourceLeadWeb_currencyCode}&nbsp;<strong>'+json.estimatedBudget+'</strong></label>  ';
				result = result +' </div>';    
				result = result +'</div>';
				
				result = result +'<div class="col-md-6">'; 
				result = result +'<label class="col-md-4 control-label">Description :</label>';
				result = result +'<div class="col-md-8">';
				result = result +'<label><strong>'+json.description+'</strong></label>  ';
				result = result +' </div>';    
				result = result +'</div>';
				
				
				
				result = result +'</div>';
				result = result +'</div> ';  
				
				result = result +'	     <div class="require_wrap_table invoiceGridview clearfix">';
				result = result +'         <table width="100%" cellpadding="0" cellspacing="0">';
				result = result +'             <thead>';
				result = result +'                 <tr>';
				result = result +'                     <th>Name</th>';
				result = result +'                     <th>Location</th>';
				result = result +'                     <th>Approver</th>';
				result = result +'                     <th>Pay Rate</th>';
				result = result +'                     <th>Bill Rate</th>';
				result = result +'                     <th>Start Date</th>';
				result = result +'                     <th>End Date</th>';
				result = result +'                     <th>Consulting</th>';
				result = result +'                     <th>Est Hours</th>';
				result = result +'                     <th>Est Budget</th>';
				result = result +'                     <th>%Of Work</th>';
				result = result +'                     <th>Status</th>';
				result = result +'                 </tr>';
				result = result +'             </thead>';
				result = result +'             <tbody>';
				
				
				 for(var i=0; i<json.resourceRequestList.length; i++){
					 var opt = json.resourceRequestList[i];
					 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != "undefined"){
						 startDateStr = opt.startDateStr;
					 }else{
						 startDateStr = "--";
					 }
					 if(opt.endDateStr != null && opt.endDateStr != "" && opt.endDateStr != "undefined"){
						 endDateStr = opt.endDateStr;
					 }else{
						 endDateStr = "--";
					 }
					 result = result +'<tr>';
					    
					    result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot" title="'+opt.candidateName+'">'+opt.candidateName+'</div>';
						result = result +'</td>'; 
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot" title="'+opt.candidateLocation+'">'+opt.candidateLocation+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot" title="'+opt.approverName+'">'+opt.approverName+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot-vsm" title="'+opt.payRateStr+'">'+opt.payRateStr+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot-vsm" title="'+opt.billRateStr+'">'+opt.billRateStr+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tblminwidth-100" title="'+startDateStr+'">'+startDateStr+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tblminwidth-100" title="'+endDateStr+'">'+endDateStr+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot_80" title="'+opt.consultingType+'">'+opt.consultingType+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot_80" title="'+opt.estimatedHours+'">'+opt.estimatedHours+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot_80" title="'+opt.estimatedBudget+'">'+opt.estimatedBudget+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot_80" title="'+(opt.percentageOfWork != null ? opt.percentageOfWork :'--') +'">'+(opt.percentageOfWork != null ? opt.percentageOfWork :'--')+'</div>';
						result = result +'</td>';
						
						
						result = result +'<td>';
						if(opt.rejectedReason==null){
						result = result +'<div class="tbldata-dotdot-vsm paTp">';
			     		result = result +'<p class="text-success" title="'+opt.resourceStatus+'" >'+opt.resourceStatus+'</p>';
						result = result +'</div>';
						}
						if(opt.rejectedReason!=null){
						result = result +'<div class="tbldata-dotdot-vsm paTp">';
			     		result = result +"<i class='glyphicon glyphicon-comment edit-reason colB'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>";
	    				result = result +'<p class="holdPro" title="'+opt.resourceStatus+'" >'+opt.resourceStatus+'</p>';
						result = result +'</div>';
						}
						result = result +'</td>';
						
						
						/* result = result +'<td>';
						if(opt.resourceStatus == null){
							result = result +'<div class="tbldata-dotdot_vsm text-warning">Sent For Approval</div>';
						}else{
							result = result +'<div class="tbldata-dotdot_80" title="'+opt.resourceStatus+'"><strong>'+opt.resourceStatus+'<strong></div>';
						}
					 	
						result = result +'</td>'; */
						
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