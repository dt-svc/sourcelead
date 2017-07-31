<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
		<div class="pull-right brud-crum"><a href="<c:url value='/projectDashboard'/>">Home</a> &raquo; <a href="<c:url value='/projectSummary?projectUUID=${projectUUID}&tabType=resources'/>">Planning</a> &raquo; View Request Associates</div>
</section>

 <div class="content-area content-new clearfix">
   <div id="requestedAssociatesList" class="col-md-12"></div>
   <div class="clearfix"></div>
   <div class="modal-footer">
   <button class="btn btn-primary" type="button" value="Back to list" onClick="javascript:window.location.href = '<c:url value='/projectSummary?projectUUID=${projectUUID}&tabType=resources'/>'" >Back to list</button>
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
				result = result +'<label>${SourceLeadWeb_currencyCode}&nbsp;<strong>'+json.estimatedBudget+'</strong></label>  ';
				result = result +' </div>';    
				result = result +'</div>';
				
				result = result +'<div class="col-md-6">'; 
				result = result +'<label class="col-md-4 control-label">Description :</label>';
				result = result +'<div class="col-md-8">';
				result = result +'<label><strong>'+json.description+'</strong></label>  ';
				result = result +' </div>';    
				result = result +'</div>';
				
				result = result +'<div class="col-md-6">'; 
				result = result +'<label class="col-md-4 control-label">Status :</label>';
				result = result +'<div class="col-md-8">';
				result = result +'<label><strong>'+json.requestStatus+'</strong></label>  ';
				result = result +' </div>';    
				result = result +'</div>';
				
				result = result +'</div>';
				result = result +'</div> ';  
				
				result = result +'	     <div class="require_wrap_table invoiceGridview clearfix">';
				result = result +'         <table width="100%" cellpadding="0" cellspacing="0">';
				result = result +'             <thead>';
				result = result +'                 <tr>';
				result = result +'                     <th>Name</th>';
				result = result +'                     <th>Pay Rate</th>';
				result = result +'                     <th>Bill Rate</th>';
				result = result +'                     <th>Start Date</th>';
				result = result +'                     <th>End Date</th>';
				result = result +'                     <th>Consulting</th>';
				result = result +'                     <th>Est Hours</th>';
				result = result +'                     <th>Est Budget</th>';
				result = result +'                     <th>Status</th>';
				result = result +'                 </tr>';
				result = result +'             </thead>';
				result = result +'             <tbody>';
				
				
				 for(var i=0; i<json.resourceRequestList.length; i++){
					 var opt = json.resourceRequestList[i];
					 
					 result = result +'<tr>';
					    
					    result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot" title="'+opt.candidateName+'">'+opt.candidateName+'</div>';
						result = result +'</td>'; 
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot-vsm" title="'+opt.payRateStr+'">'+opt.payRateStr+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot-vsm" title="'+opt.billRateStr+'">'+opt.billRateStr+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tblminwidth-100" title="'+opt.startDateStr+'">'+opt.startDateStr+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tblminwidth-100" title="'+opt.endDateStr+'">'+opt.endDateStr+'</div>';
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
						if(opt.resourceStatus == null){
							result = result +'<div class="tbldata-dotdot_vsm text-warning">Sent For Approval</div>';
						}else{
							result = result +'<div class="tbldata-dotdot_80 text-success" title="'+opt.resourceStatus+'"><strong>'+opt.resourceStatus+'<strong></div>';
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