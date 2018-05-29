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
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/projectDashboard'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/projectSummary?projectUUID=${projectUUID}&projectClientUUID=${projectClientUUID}&tabType=resources'/>">Planning</a> &raquo; View New Request Resources</div>
</section>

 <div class="content-area content-new clearfix">
   <div id="requestedAssociatesList"></div>
   <div class="modal-footer">
   <button class="btn btn-primary" type="button" value="Back to list" onClick="javascript:window.location.href = '<c:url value='${appPath}/projectSummary?projectUUID=${projectUUID}&projectClientUUID=${projectClientUUID}&tabType=resources'/>'" >Back to list</button>
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
					 var rateType='';
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
						 minExperience = "Min Exp."+opt.minExperience;
					 }else if(opt.minExperience == 0){
						 minExperience = "Min Exp."+opt.minExperience;
					 }else{
						 minExperience = '--';
					 }
					 
					if(opt.maxExperience != null && opt.maxExperience != '' && opt.maxExperience !='undefined'){
						maxExperience = "Max Exp."+opt.maxExperience;
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
					 if(opt.rateType != null && opt.rateType != '' && opt.rateType != 'undefined'){
						 rateType = opt.rateType;
						 }else{
							 rateType = '--';
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
						    result = result +'    <small class="text-muted pull-right paddingLeft payrateType mt10" id="rateTypeRow1" title="Hourly">'+rateType+'</small>';
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
						
						result = result +'<td>';
						if(opt.rejectedReason==null){
						result = result +'<div class="tbldata-dotdot-vsm paTp">';
			     		result = result +'<b class="text-success" title="'+opt.resourceStatus+'" >'+opt.resourceStatus+'</b>';
						result = result +'</div>';}
						if(opt.rejectedReason!=null){
						result = result +'<div class="tbldata-dotdot-vsm paTp">';
			     		result = result +"<i class='glyphicon glyphicon-comment edit-reason colB'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i>";
	    				result = result +'<b class="holdPro" title="'+opt.resourceStatus+'" >'+opt.resourceStatus+'</b>';
						result = result +'</div>';}
						result = result +'</td>';
						
					 result = result +'</tr>';
				 }
				
				result = result +'</tbody>';
				/* result = result +'		<tfoot class="table-footer">';
			    result = result +'     <tr>';
			    result = result +'         <td><strong>Total</strong></td>';
			    result = result +'         <td><input type="text" value="00" class="ReadTextFli" readonly name="totalResourcesValue" id="totalResourcesId"></td>';
			    result = result +'         <td colspan="5"></td>';
			    result = result +'         <td><input type="text" value="00" class="ReadTextFli" name="totalEstHours" readonly id="totalEstHours"></td>';
			    result = result +'         <td><input type="text" value="00" class="ReadTextFli" name="totalEstBudget" readonly id="totalEstBudget"></td>';
			    result = result +'     </tr>';
			    result = result +' </tfoot>'; */
				result = result +'</table>';
				result = result +' </div>';
					
				$("#requestedAssociatesList").empty();  
			    $("#requestedAssociatesList").append(result);
				
        	 
        	
        	 
         }
 });
}
</script>