<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="resources/js/formvalidator.js"></script>

<style type="text/css">
.chosen-container {
    width: 100% !important;
}
.fullWid {width: 100% !important;}
</style>

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<script type="text/javascript">
$(document).ready(function(){
	if("" != localStorage.getItem("stage") && null != localStorage.getItem("stage")){
		displyContract(localStorage.getItem("stage"));
	}
});
</script>

<!-- Content Area -->
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>View Contract</h2>
      </div>
     <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/contractListbyStatus'/>">Contracts</a> &raquo; View Contract</div>
    </section>
    <div class="content-area content-new clearfix">
     <div id="loading"></div>
      <div class="clearfix form-horizontal">
       
        <div class="row newContrWrap">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           	<h2>Contract Details</h2>
			<div id="viewPopupDiv"></div>
            
            
        	</div>
      	</div>
       </div>      
     </div>
     <!-- Content Area Ends--> 
</section>

<script>
function displyContract(stage){
	var sourceleadUrl ='${sourceleadUrl}';
	var contractUUID = getParameterByName('contractUUID');
	 $.ajax({
      type : "GET",
       url: sourceleadUrl+"/getContractByContractUUID?contractUUID="+contractUUID,       
       success :function(data) {
    	   
    	    var result = '';
    	    var json = JSON.parse(data);
			result = result +	'<div class="contPopupHead clearfix">';
			result = result +	'<h4>'+json.contractTitle+'</h4>';
			result = result +	'<span>';
			
			if(json.templateSubmissionStatus == 'DRAFT' || json.templateSubmissionStatus == 'REJECTED' || json.templateSubmissionStatus == 'WAITING_FOR_APPROVER'){
				if('New' == json.typeOfContract){
					result = result +	'	<a href="/sourcelead/addNewContract?contractUUID='+contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
				}else if('Existing' == json.typeOfContract){
					result = result +	'';
				}else if('Custom' == json.typeOfContract){
					result = result +	'	<a href="/sourcelead/addCustomContract?contractUUID='+contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
				}
			}
			
			result = result +	'</span>';
			result = result +	'</div>';
		
			result = result +	'<div class="modal-body clearfix">';
			result = result +	'<div class="col-xs-12 col-md-12 col-lg-12 noPad clearfix mt10">';
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Contract Title</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contractTitle+'</div>';
			result = result +	'	</div>';
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Contract Type</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contractType+'</div>';
			result = result +	'	</div>';
			
			if(json.templateName != null && 'Custom' != json.typeOfContract && 'Existing' != json.typeOfContract){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Template Title</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.templateName+' <a href="#" title="Preview"><i  title="View Template" class="fa fa-eye" onclick="previewTemplatePDF(\''+json.tempUUID+'\', \''+json.templateName+'\')"></i></a></div>';
				result = result +	'	</div>';
			}
			if(null != json.startDateStr && "" != json.startDateStr && 'undefined' != json.startDateStr){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Start Date</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.startDateStr+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.joinedDateStr && "" != json.joinedDateStr && 'undefined' != json.joinedDateStr){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Join Date</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.joinedDateStr+'</div>';
				result = result +	'	</div>';
			}
			if(null != json.workLocation && "" != json.workLocation && 'undefined' != json.workLocation){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Work Location</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.workLocation+'</div>';
				result = result +	'	</div>';
			}
			
			if(json.contractType!="Full time offer"){
					if(null != json.endDateStr && "" != json.endDateStr && 'undefined' != json.endDateStr){
						result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
						result = result +	'		<div class="col-md-4 col-sm-4">End Date </div>';
						if(stage!="SIGN" &&  stage!="VERIFY" && stage!="REJECTED"){
							result = result +	'		<div class="col-md-8 col-sm-8 popRela" id="endVal">: '+json.endDateStr+' <i class="fa fa-pencil popEdCo"  onclick="editOne(\''+json.startDateStr+'\',\''+json.endDateStr+'\');"></i>';
							result = result +	'<div class="editNewDat"><input type="text" class="form-control calendar" value="'+json.endDateStr+'" id="endDate" name="endDate" placeholder="End date" readonly="readonly"><i class="fa fa-floppy-o" onclick="editClo('+json.id+');"></i></div>';
						}
						else{
							result = result +	'		<div class="col-md-8 col-sm-8 popRela" id="endVal">: '+json.endDateStr+'</div>';	
						}
						result = result +	'	</div>';
						result = result +	'	</div>';
					}
					else{
						result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
						result = result +	'		<div class="col-md-4 col-sm-4">End Date </div>';
						if(stage!="SIGN" &&  stage!="VERIFY" && stage!="REJECTED"){
							result = result +	'		<div class="col-md-8 col-sm-8 popRela" id="endVal">: --  <i class="fa fa-pencil popEdCo"  onclick="editOne(\''+json.startDateStr+'\',\''+json.endDateStr+'\');"></i>';
							result = result +	'<div class="editNewDat"><input type="text" class="form-control calendar" value="" id="endDate" name="endDate" placeholder="End date" readonly="readonly"><i class="fa fa-floppy-o" onclick="editClo('+json.id+');"></i></div>';
						}
						else{
							result = result +	'		<div class="col-md-8 col-sm-8 popRela" id="endVal">: -- </div>';	
						}
						result = result +	'	</div>';
						result = result +	'	</div>';
					}
				
			}
			if(json.contractType =="Full time offer"){
				if(null != json.billingAmount && "" != json.billingAmount && 'undefined' != json.billingAmount){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Offer Rate</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.billingRateType+' '+json.billingAmount+' / '+json.billingRatePer+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.ownerName && "" != json.ownerName && 'undefined' != json.ownerName){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Owner</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.ownerName+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.approverUserName && "" != json.approverUserName && 'undefined' != json.approverUserName){
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Approver</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.approverUserName+'</div>';
			result = result +	'	</div>';
			}
			
			if(null != json.typeOfContract && (json.contractType.includes("SOW") || json.contractType =="Full time offer")){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Requirement Title</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.requirementName+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.typeOfContract && (json.contractType.includes("SOW") || json.contractType =="Full time offer")){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Applicant Name</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.applicantName+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.offerValue && "" != json.offerValue && 'undefined' != json.offerValue){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Offer Rate</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.offerValue+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.duration && "" != json.duration && 'undefined' != json.duration){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Duration</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: </div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.initiatorType && "Client" == json.initiatorType && json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Vendor</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contractWith+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.initiatorType && "Vendor" == json.initiatorType && json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Client</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contractWith+'</div>';
				result = result +	'	</div>';
			}
			if(json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Contact</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contactUserName+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.stateJurisdiction && "" != json.stateJurisdiction && 'undefined' != json.stateJurisdiction){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">State of Jurisdiction</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.stateJurisdiction+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.description && "" != json.description && 'undefined' != json.description && json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Description</div>';
				result = result +	'		<div class="col-md-8 col-sm-8"><i style="display: inline-block; font-style:normal; float:left;">:</i><span style=" padding: 0px 0px 0px 3px; display: inline-block; width:90%; float:left;">'+json.description+'</span></div>';
				result = result +	'	</div>';
			}
			
			result = result +	'<div class="clearfix"/>';
			
		if(json.contractType != "Vendor NDA" && json.contractType != "Vendor NSA" && json.contractType!="Full time offer"){
			result = result +	'<h4>Billing Details</h4>';
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.hoursPerWeek && "" != json.hoursPerWeek && 'undefined' != json.hoursPerWeek){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Hours Per Week</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.hoursPerWeek+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.overTimeHours && "" != json.overTimeHours && 'undefined' != json.overTimeHours){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Overtime Hours</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.overTimeHours+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.overtimeRate && "" != json.overtimeRate && 'undefined' != json.overtimeRate){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Overtime rate</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.overtimeRate+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.totalHours && "" != json.totalHours && 'undefined' != json.totalHours){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Total Estimated Hours</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.totalHours+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.billingAmount && "" != json.billingAmount && 'undefined' != json.billingAmount){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Billing Amount</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.billingRateType+' '+json.billingAmount+' / '+json.billingRatePer+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.invoiceFrequency && "" != json.invoiceFrequency && 'undefined' != json.invoiceFrequency){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Billing Cycle</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.invoiceFrequency+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.paymentTerm && "" != json.paymentTerm && 'undefined' != json.paymentTerm){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Payment Term</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.paymentTerm+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.tax && "" != json.tax && 'undefined' != json.tax){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Tax</div>';
				var abc = json.taxType;
				if(abc == "Percentage"){
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.tax+' %</div>';
				}else{
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.tax+' '+json.taxType+'</div>';
				}
				
				result = result +	'	</div>';
			}
			
			result = result +	'<div class="clearfix"/>';
		}
			result = result +	'<h4>History</h4>';
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Created On</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.createdDateStr+'</div>';
			result = result +	'	</div>';
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Created By</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.createdByName+'</div>';
			result = result +	'	</div>';
			if(null != json.updatedDateStr && 'undefined' != json.updatedDateStr && json.updatedDateStr != json.createdDateStr){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Updated On</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.updatedDateStr+'</div>';
				result = result +	'	</div>';
				
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Updated By</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.updatedByName+'</div>';
				result = result +	'	</div>';
			}
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Added From</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.typeOfContract+'</div>';
			result = result +	'	</div>';
			
			result = result +	'</div>';
			result = result +	'</div>';
			
			$("#viewPopupDiv").empty();
			$("#viewPopupDiv").append(result);
       },
	  }); 
}


function previewTemplatePDF(uuid, templateName){
	var sourceleadUrl ='${sourceleadUrl}';
	if(uuid != null && uuid != ""){
		window.open(sourceleadUrl+'/viewContractTemplatePdf/'+uuid+'/'+templateName+'','_newtab');
	}
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
</script>

<script>
function editOne(startDate,endDate){
	$('.editNewDat').show();
	$('#endDate').datepicker({
        dateFormat: 'dd-mm-yy',
        changeMonth: true,
          changeYear: true,
          minDate: startDate,
          onSelect: function (selected) {
              var dt = toDate("#endDate");
              dt.setDate(dt.getDate() - 1);
          }
    });
}
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}

function editClo(contractId){
	$('.editNewDat').hide();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#endDate").val();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    type : "POST",
     url: sourceleadUrl+"/updateContractEndDate?${_csrf.parameterName}=${_csrf.token}&contractId="+contractId+"&endDate="+endDate,       
     success :function(data) {
    	 $("#endVal").empty();
		  $("#endVal").append(": "+endDate);
    },
});  
	
}
</script>

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->