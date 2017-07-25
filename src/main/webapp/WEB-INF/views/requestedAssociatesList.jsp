<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style>
.settPop .form-control {
    height: auto !important; 
}
</style>


<script type="text/javascript">
	window.onload = function () {
		getRequestedAssociatesList();
	 };
 </script>
 
<script type="text/javascript">
	$(document).ready(function() {
	    $(".glyphicon-edit").click(function(){
			$(".settPop").slideDown(200);
			$(".vendor_wrap_mask").fadeIn(200);
		});
		$(".settPop i, .btn-default").click(function(){
			$(".settPop").slideUp(200);
			$(".vendor_wrap_mask").fadeOut(200);
		});
	});		
	
</script> 

<section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Requested Associates</h2>
      </div>
		<div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;Requested Associates</div>
</section>

 <div class="content-area content-new clearfix">
   <div id="requestedAssociatesList"></div>
   
   <div class="vendor_wrap_mask"></div>
   <div id="rejectedReasonDiv"></div>
   <div class="modal-footer">
   <button class="btn btn-primary" type="button" value="Back to list" onClick="javascript:window.location.href = '<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>'" >Back to list</button>
   </div>
					
 </div>


</section>



<jsp:include page="masterFooter.jsp" />

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
				
				result = result +'	     <div class="invoiceGridview clearfix">';
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
				result = result +'                     <th>Action</th>';
				result = result +'                 </tr>';
				result = result +'             </thead>';
				result = result +'             <tbody>';
				
				
				 for(var i=0; i<json.resourceRequestList.length; i++){
					 var opt = json.resourceRequestList[i];
					 var startDate = '';
					 var endDate = '';
					 var payRate = ''
					 var estimatedHours = '';
					 var estimatedBudget = '';
					
					 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
						 startDate = opt.startDateStr;
					 }else{
						 startDate = '-----';
					 }
					 
					 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
						 endDate = opt.endDateStr;
					 }else{
						 endDate = '-----';
					 }
					 
					 if(opt.payRate != null && opt.payRate != '' && opt.payRate != 'undefined'){
						 payRate = opt.payRateCurrency+" "+opt.payRate+" / "+opt.payRateType;
					 }else{
						 payRate = '-----';
					 }
					 
					 var billRate = opt.billRateCurrency+" "+opt.billRate+" / "+opt.billRateType;
					 
					 if(opt.estimatedHours != null && opt.estimatedHours != '' && opt.estimatedHours != 'undefined'){
						 estimatedHours = opt.estimatedHours;
					 }else{
						 estimatedHours = '-----';
					 }
					 
					 if(opt.estimatedBudget != null && opt.estimatedBudget != '' && opt.estimatedBudget != 'undefined'){
						 estimatedBudget = opt.estimatedBudget;
					 }else{
						 estimatedBudget = '-----';
					 }
					 
					 
					    result = result +'<tr>';
					    
					    result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot" title="'+opt.candidateName+'">'+opt.candidateName+'</div>';
						result = result +'</td>'; 
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot-vsm" title="'+payRate+'">'+payRate+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tbldata-dotdot-vsm" title="'+billRate+'">'+billRate+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tblminwidth-50" title="'+startDate+'">'+startDate+'</div>';
						result = result +'</td>';
						
						result = result +'<td>';
					 	result = result +'<div class="tblminwidth-50" title="'+endDate+'">'+endDate+'</div>';
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
						
						result = result +'<td id="rejection'+opt.requestUUID+'">';
						if(opt.resourceStatus == null){
			    			result = result + '<a href="javascript:void(0)" onclick="acceptAssociate();"><i class="fa fa-check text-success" alt="Approve" title="Approved"></i></a>&nbsp;|&nbsp;';
				    		result = result + '<a href="javascript:void(0)" onclick="rejectAssociate(\''+opt.requestUUID+'\',\''+opt.candidateName+'\');"><i class="fa fa-times text-danger" alt="Rejected" title="Rejected"></a></i>';
			    		}else{
			    			result = result + ''+opt.resourceStatus+'';
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
function rejectAssociate(requestUUID,candidateName){
	
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
	result = result + '<input type="hidden" name="requestUUID" value="'+requestUUID+'">';
	result = result + '	<h3>Associate Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Candidate Name">Name :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+candidateName+'</label> ';
	result = result + '		</div>  ';

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
function cancelRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>


<script>
function acceptAssociate(){
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
      
        $("#submit_rejectedReason").attr("disabled", "disabled");
		
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 $.ajax({
	 		    type : "POST",
	    	    data: formData,
	    	    contentType : 'application/json; charset=utf-8',
		        processData: false,
		        contentType:false, 
	    	    dataType : 'json',
		    	url: sourceleadUrl+"/rejectRequestedAssociate?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(result) {
		        	cancelRejectedReason();
		        	$("#rejection"+requestUUID).html("<span>Rejected</span>")
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

<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script>
  